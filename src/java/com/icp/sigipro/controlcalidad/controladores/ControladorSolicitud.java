/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.AnalisisDAO;
import com.icp.sigipro.controlcalidad.dao.SolicitudDAO;
import com.icp.sigipro.controlcalidad.dao.TipoMuestraDAO;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.controlcalidad.modelos.TipoMuestra;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.serpentario.modelos.Solicitud;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ld.conejo
 */
@WebServlet(name = "ControladorSolicitud", urlPatterns = {"/ControlCalidad/Solicitud"})
public class ControladorSolicitud extends SIGIPROServlet {

    //Solicitar, Recibir, Anular
    private final int[] permisos = {1, 550, 551, 552};
    //-----------------
    private SolicitudDAO dao = new SolicitudDAO();
    private TipoMuestraDAO tipomuestradao = new TipoMuestraDAO();
    private AnalisisDAO analisisdao = new AnalisisDAO();
    private UsuarioDAO usuariodao = new UsuarioDAO();

    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
    BitacoraDAO bitacora = new BitacoraDAO();

    protected final Class clase = ControladorSolicitud.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("anular");
            add("editar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
            add("editar");
            add("recibir");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {

        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(550, listaPermisos);

        String redireccion = "Solicitud/Agregar.jsp";
        SolicitudCC s = new SolicitudCC();
        request.setAttribute("solicitud", s);

        List<TipoMuestra> tipomuestras = tipomuestradao.obtenerTiposDeMuestra();
        request.setAttribute("tipomuestras", tipomuestras);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);

    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Solicitud/index.jsp";
        List<SolicitudCC> solicitudes = dao.obtenerSolicitudes();
        request.setAttribute("boolrecibir", this.verificarRecibirSolicitud(request));
        request.setAttribute("boolrealizar", this.verificarRealizarSolicitud(request));
        request.setAttribute("listaSolicitudes", solicitudes);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Solicitud/Ver.jsp";
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        try {
            SolicitudCC s = dao.obtenerSolicitud(id_solicitud);
            request.setAttribute("solicitud", s);
            redireccionar(request, response, redireccion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(550, listaPermisos);
        String redireccion = "Solicitud/Editar.jsp";
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        SolicitudCC solicitud = dao.obtenerSolicitud(id_solicitud);
        request.setAttribute("solicitud", solicitud);
        List<TipoMuestra> tipomuestras = tipomuestradao.obtenerTiposDeMuestra();
        request.setAttribute("tipomuestras", tipomuestras);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void getAnular(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(552, listaPermisos);
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        boolean resultado = false;
        try {
            resultado = dao.anularSolicitud(id_solicitud);
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_solicitud, Bitacora.ACCION_ANULAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUDCC, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud anulada correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no pudo ser anulada ya que tiene otros objetos asociados."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no pudo ser anulada ya que tiene otros objetos asociados."));
            this.getIndex(request, response);
        }

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        boolean resultado = false;
        SolicitudCC s = construirObjeto(request);
        s.setEstado("Solicitado");
        Usuario usuario = usuariodao.obtenerUsuario((String) request.getSession().getAttribute("usuario"));
        s.setUsuario_solicitante(usuario);

        resultado = dao.entregarSolicitud(s);
        if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud agregada correctamente"));
            //Funcion que genera la bitacora
            bitacora.setBitacora(s.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUDCC, request.getRemoteAddr());
            //*----------------------------*
            this.getIndex(request, response);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no pudo ser agregada. Inténtelo de nuevo."));
            this.getAgregar(request, response);
        }

    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        boolean resultado = false;
        SolicitudCC s = construirObjeto(request);
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        s.setId_solicitud(id_solicitud);
        resultado = dao.editarSolicitud(s);
        if (resultado) {
            //Funcion que genera la bitacora
            bitacora.setBitacora(s.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUDCC, request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud editado correctamente"));
            this.getIndex(request, response);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no pudo ser editado. Inténtelo de nuevo."));
            request.setAttribute("id_solicitud", id_solicitud);
            this.getEditar(request, response);
        }
    }
  // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private SolicitudCC construirObjeto(HttpServletRequest request) {
        SolicitudCC s = new SolicitudCC();
        s.setNumero_solicitud(request.getParameter("numero_solicitud"));
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        java.sql.Date fecha_solicitud = new java.sql.Date(new Date().getTime());
        s.setFecha_solicitud(fecha_solicitud);
        return s;
    }

    private boolean verificarRecibirSolicitud(HttpServletRequest request) throws AuthenticationException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        return verificarPermiso(551, listaPermisos);
    }

    private boolean verificarRealizarSolicitud(HttpServletRequest request) throws AuthenticationException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        return verificarPermiso(552, listaPermisos);
    }

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
            Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class);
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
