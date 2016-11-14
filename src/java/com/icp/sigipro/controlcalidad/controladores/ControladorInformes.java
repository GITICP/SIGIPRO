/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.InformeDAO;
import com.icp.sigipro.controlcalidad.dao.SolicitudDAO;
import com.icp.sigipro.controlcalidad.modelos.Informe;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ld.conejo, boga
 */
@WebServlet(name = "ControladorInformes", urlPatterns = {"/ControlCalidad/Informe"})
public class ControladorInformes extends SIGIPROServlet
{

    //Falta implementar
    private final int[] permisos = {1, 557, 558, 559};
    //-----------------
    
    private final InformeDAO dao = new InformeDAO();
    private final SolicitudDAO daosolicitud = new SolicitudDAO();

    protected final Class clase = ControladorInformes.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("eliminar");
            add("editar");
            add("generar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("generar");
            add("editar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getGenerar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(557, listaPermisos);
        String redireccion = "Informe/Generar.jsp";
        
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        
        SolicitudCC solicitud = daosolicitud.obtenerSolicitud(id_solicitud);
        
        try {
            // Aunque sea generar, se necesita la misma información 
            solicitud.prepararGenerarInforme(request);
        } catch(SIGIPROException sig_ex) {
            solicitud = null;
        }
        
        if (solicitud != null) {
            request.setAttribute("solicitud", solicitud);
            request.setAttribute("accion", "Generar");
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Error al obtener la información de la solicitud. Inténtelo nuevamente."));
        }
        
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(558, listaPermisos);
        String redireccion = "Informe/Generar.jsp";
        
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        
        SolicitudCC solicitud = daosolicitud.obtenerSolicitud(id_solicitud);
        try {
            solicitud.prepararEditarInforme(request);
        } catch(SIGIPROException sig_ex) {
            solicitud = null;
        }
        
        if (solicitud != null) {
            request.setAttribute("solicitud", solicitud);
            request.setAttribute("accion", "Editar");
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Error al obtener la información de la solicitud. Inténtelo nuevamente."));
        }
        
        redireccionar(request, response, redireccion);
    }
    
    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Informe/Ver.jsp";
        
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        
        SolicitudCC solicitud = daosolicitud.obtenerSolicitud(id_solicitud);
        
        if (solicitud != null) {
            request.setAttribute("solicitud", solicitud);
            request.setAttribute("accion", "Generar");
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Error al obtener la información de la solicitud. Inténtelo nuevamente."));
        }
        
        redireccionar(request, response, redireccion);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postGenerar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        validarPermiso(557, request);
        
        String redireccion = "Solicitud/Ver.jsp";
        
        String[] ids_resultados = request.getParameterValues("resultados");
        
        Informe informe = new Informe();
        SolicitudCC s = new SolicitudCC();
        Timestamp fecha_cierre = new Timestamp(new Date().getTime());
        s.setFecha_cierre(fecha_cierre);
        s.setId_solicitud(Integer.parseInt(request.getParameter("id_solicitud")));
        informe.setSolicitud(s);
        Usuario u = new Usuario();
        u.setId_usuario(this.getIdUsuario(request));
        informe.setUsuario(u);
        
        String objeto_por_asociar = request.getParameter("objeto-relacionado");
        s.setTipoAsociacion(objeto_por_asociar);
        s.asociar(request);
        
        boolean cerrar = Boolean.parseBoolean(request.getParameter("cerrar"));
        
        for (String resultado : ids_resultados) {
            Resultado r = new Resultado();
            r.setId_resultado(Integer.parseInt(resultado));
            informe.agregarResultado(r, request);
        }
        
        try {
            informe = dao.ingresarInforme(informe, cerrar);
            bitacora.setBitacora(informe.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_INFORME, request.getRemoteAddr());
            request.setAttribute("solicitud", daosolicitud.obtenerSolicitud(s.getId_solicitud()));
            String mensaje_exito = ((cerrar) ? "Informe final generado correctamente y solicitud cerrada con éxito." : "Informe parcial generado correctamente.");
            request.setAttribute("mensaje", helper.mensajeDeExito(mensaje_exito));
            if (cerrar){dao.notificacion_informe_final(s.getId_solicitud());}
            else {dao.notificacion_informe_parcial(s.getId_solicitud());}
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }
        
        this.redireccionar(request, response, redireccion);
        
    }
    
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        validarPermiso(558, request);
        
        String redireccion = "Solicitud/Ver.jsp";
        
        String[] ids_resultados = request.getParameterValues("resultados");
        
        Informe informe = new Informe();
        SolicitudCC s = new SolicitudCC();
        s.setId_solicitud(Integer.parseInt(request.getParameter("id_solicitud")));
        informe.setSolicitud(s);
        Usuario u = new Usuario();
        u.setId_usuario(this.getIdUsuario(request));
        informe.setUsuario(u);
        informe.setId_informe(Integer.parseInt(request.getParameter("id_informe")));
        
        String objeto_por_asociar = request.getParameter("objeto-relacionado");
        s.setTipoAsociacion(objeto_por_asociar);
        s.asociar(request);
        
        boolean cerrar = Boolean.parseBoolean(request.getParameter("cerrar"));
        
        for (String resultado : ids_resultados) {
            Resultado r = new Resultado();
            r.setId_resultado(Integer.parseInt(resultado));
            informe.agregarResultado(r, request);
        }
        
        try {
            informe = dao.editarInforme(informe, cerrar);
            bitacora.setBitacora(informe.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_INFORME, request.getRemoteAddr());
            request.setAttribute("solicitud", daosolicitud.obtenerSolicitud(s.getId_solicitud()));
            String mensaje_exito = ((cerrar) ? "Informe editado y cerrado correctamente." : "Informe parcial editado correctamente.");
            if (cerrar){dao.notificacion_informe_final(s.getId_solicitud());}
            request.setAttribute("mensaje", helper.mensajeDeExito(mensaje_exito));
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }
        
        this.redireccionar(request, response, redireccion);
        
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<String> lista_acciones;
        if (accionHTTP.equals("get")) {
            lista_acciones = accionesGet;
        } else {
            lista_acciones = accionesPost;
        }
        if (lista_acciones.contains(accion.toLowerCase())) {
            String nombreMetodo = accionHTTP + Character.toUpperCase(accion.charAt(0)) + accion.substring(1);
            Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class
            );
            metodo.invoke(this, request, response);
        } else {
            Method metodo = clase.getDeclaredMethod(accionHTTP + "Index", HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        }
    }

    @Override
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // </editor-fold>
}
