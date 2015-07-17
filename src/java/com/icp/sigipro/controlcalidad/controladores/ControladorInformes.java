/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

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
import java.util.ArrayList;
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
    private final int[] permisos = {1, 540};
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
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getGenerar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(550, listaPermisos);
        String redireccion = "Informe/Generar.jsp";
        
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
        
        String redireccion = "Solicitud/Ver.jsp";
        
        String[] ids_resultados = request.getParameterValues("resultados");
        
        Informe informe = new Informe();
        SolicitudCC s = new SolicitudCC();
        s.setId_solicitud(Integer.parseInt(request.getParameter("id_solicitud")));
        informe.setSolicitud(s);
        Usuario u = new Usuario();
        u.setId_usuario(this.getIdUsuario(request));
        informe.setUsuario(u);
        
        for (String resultado : ids_resultados) {
            Resultado r = new Resultado();
            r.setId_resultado(Integer.parseInt(resultado));
            informe.agregarResultado(r);
        }
        
        try {
            informe = dao.ingresarInforme(informe);
            // Bitácora informe
            request.setAttribute("solicitud", daosolicitud.obtenerSolicitud(s.getId_solicitud()));
            request.setAttribute("mensaje", helper.mensajeDeExito("Informe generado correctamente."));
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
