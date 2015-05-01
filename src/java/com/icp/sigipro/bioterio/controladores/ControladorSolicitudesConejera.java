/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.controladores;

import com.icp.sigipro.bioterio.dao.EntregaConejeraDAO;
import com.icp.sigipro.bioterio.dao.SolicitudConejeraDAO;
import com.icp.sigipro.bioterio.modelos.EntregaConejera;
import com.icp.sigipro.bioterio.modelos.SolicitudConejera;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorSolicitudesConejera", urlPatterns = {"/Conejera/SolicitudesConejera"})
public class ControladorSolicitudesConejera extends SIGIPROServlet
{

    private final int[] permisos = {253, 254, 1};
    private final SolicitudConejeraDAO dao = new SolicitudConejeraDAO();
    private final EntregaConejeraDAO dao_en = new EntregaConejeraDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();
    private final HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
    private boolean admin = false;

    protected final Class clase = ControladorSolicitudesConejera.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
            add("eliminar");
            add("aprobar");
            add("cerrar");
            add("verentrega");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregar");
            add("editar");
            add("entregar");
            add("rechazar");
        }
    };

    protected final List<String> pesos = new ArrayList<String>()
    {
        {
            add("2200");
            add("2300");
            add("3000");
            add("3600");
            add("Otro");

        }
    };
    protected final List<String> sexos = new ArrayList<String>()
    {
        {
            add("Mixto");
            add("Hembras");
            add("Machos");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        String redireccion = "SolicitudesConejera/Agregar.jsp";
        SolicitudConejera ds = new SolicitudConejera();
        request.setAttribute("solicitud", ds);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        admin = verificarPermiso(254, listaPermisos);
        request.setAttribute("admin", admin);
        String redireccion = "SolicitudesConejera/index.jsp";
        List<SolicitudConejera> solicitudes_conejera;
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        if (admin) {
            try {
                solicitudes_conejera = dao.obtenerSolicitudesConejeraAdm();
                request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
            }
            catch (SIGIPROException ex) {
                request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
            }
        }
        else {
            try {
                HttpSession sesion = request.getSession();
                String nombre_usr = (String) sesion.getAttribute("usuario");
                int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
                solicitudes_conejera = dao.obtenerSolicitudesConejera(id_usuario);
                request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
            }
            catch (SIGIPROException ex) {
                request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
            }
        }
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        admin = verificarPermiso(254, listaPermisos);
        request.setAttribute("admin", admin);
        String redireccion = "SolicitudesConejera/Ver.jsp";
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        try {
            SolicitudConejera s = dao.obtenerSolicitudConejera(id_solicitud);
            List<EntregaConejera> e = dao_en.obtenerEntregasConejera(id_solicitud);
            request.setAttribute("solicitud", s);
            request.setAttribute("entregas", e);
        }
        catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void getVerentrega(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        admin = verificarPermiso(254, listaPermisos);
        request.setAttribute("admin", admin);
        String redireccion = "SolicitudesConejera/VerEntrega.jsp";
        int id_entrega = Integer.parseInt(request.getParameter("id_entrega"));
        try {
            EntregaConejera s = dao_en.obtenerEntregaConejera(id_entrega);
            request.setAttribute("entrega", s);
        }
        catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "SolicitudesConejera/Editar.jsp";
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        request.setAttribute("accion", "Editar");
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        try {
            SolicitudConejera s = dao.obtenerSolicitudConejera(id_solicitud);
            request.setAttribute("solicitud", s);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        admin = verificarPermiso(254, listaPermisos);
        request.setAttribute("admin", admin);
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        try {
            dao.eliminarSolicitudConejera(id_solicitud);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_solicitud, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------* 
            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud de conejera eliminada correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        try {
            List<SolicitudConejera> solicitudes_conejera;
            if (admin) {
                try {
                    solicitudes_conejera = dao.obtenerSolicitudesConejeraAdm();
                    request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
                }
                catch (SIGIPROException ex) {
                    request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
                }
            }
            else {
                HttpSession sesion = request.getSession();
                String nombre_usr = (String) sesion.getAttribute("usuario");
                int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
                solicitudes_conejera = dao.obtenerSolicitudesConejera(id_usuario);
                request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
            }
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        String redireccion = "SolicitudesConejera/index.jsp";
        redireccionar(request, response, redireccion);
    }

    protected void getAprobar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        boolean resultado = false;
        admin = verificarPermiso(254, listaPermisos);
        request.setAttribute("admin", admin);
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        try {
            SolicitudConejera solicitud = dao.obtenerSolicitudConejera(id_solicitud);
            solicitud.setEstado("Aprobada");
            resultado = dao.editarSolicitudConejera(solicitud);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_solicitud, Bitacora.ACCION_APROBAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------* 

            if (resultado) {
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud aprobada"));
            }
            else {
                request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
            }
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        try {
            List<SolicitudConejera> solicitudes_conejera;
            if (admin) {
                try {
                    solicitudes_conejera = dao.obtenerSolicitudesConejeraAdm();
                    request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
                }
                catch (SIGIPROException ex) {
                    request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
                }
            }
            else {
                HttpSession sesion = request.getSession();
                String nombre_usr = (String) sesion.getAttribute("usuario");
                int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
                solicitudes_conejera = dao.obtenerSolicitudesConejera(id_usuario);
                request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
            }
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        String redireccion = "SolicitudesConejera/index.jsp";
        redireccionar(request, response, redireccion);
    }
    protected void getCerrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
    boolean resultado = false;
    admin = verificarPermiso(203, listaPermisos);
    request.setAttribute("admin", admin);
    request.setAttribute("pesos", pesos);
    request.setAttribute("sexos", sexos);
    try {
            SolicitudConejera solicitud = dao.obtenerSolicitudConejera(id_solicitud);
            solicitud.setEstado("Cerrada");
            resultado = dao.editarSolicitudConejera(solicitud);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_solicitud, Bitacora.ACCION_APROBAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------* 

            if (resultado) {
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud cerrada"));
            }
            else {
                request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
            }
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        try {
            List<SolicitudConejera> solicitudes_conejera;
            if (admin) {
                try {
                    solicitudes_conejera = dao.obtenerSolicitudesConejeraAdm();
                    request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
                }
                catch (SIGIPROException ex) {
                    request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
                }
            }
            else {
                HttpSession sesion = request.getSession();
                String nombre_usr = (String) sesion.getAttribute("usuario");
                int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
                solicitudes_conejera = dao.obtenerSolicitudesConejera(id_usuario);
                request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
            }
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        String redireccion = "SolicitudesConejera/index.jsp";
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "SolicitudesConejera/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        admin = verificarPermiso(254, listaPermisos);
        request.setAttribute("admin", admin);
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        try {
            SolicitudConejera solicitud = construirObjeto(request);
            solicitud.setEstado("Pendiente");
            resultado = dao.insertarSolicitudConejera(solicitud);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(solicitud.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------*
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "SolicitudesConejera/index.jsp";
            List<SolicitudConejera> solicitudes_conejera;
            try {
                if (admin) {
                    try {
                        solicitudes_conejera = dao.obtenerSolicitudesConejeraAdm();
                        request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
                    }
                    catch (SIGIPROException ex) {
                        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
                    }
                }
                else {
                    HttpSession sesion = request.getSession();
                    String nombre_usr = (String) sesion.getAttribute("usuario");
                    int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
                    solicitudes_conejera = dao.obtenerSolicitudesConejera(id_usuario);
                    request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
                }
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud agregada con éxito"));
            }
            catch (SIGIPROException ex) {
                request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
            }
        }
        else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "SolicitudesConejera/Editar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        admin = verificarPermiso(254, listaPermisos);
        request.setAttribute("admin", admin);
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        try {
            SolicitudConejera solicitud = construirObjeto(request);
            solicitud.setEstado("Pendiente");
            resultado = dao.editarSolicitudConejera(solicitud);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(solicitud.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------*
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "SolicitudesConejera/index.jsp";
            List<SolicitudConejera> solicitudes_conejera;
            try {
                if (admin) {
                    try {
                        solicitudes_conejera = dao.obtenerSolicitudesConejeraAdm();
                        request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
                    }
                    catch (SIGIPROException ex) {
                        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
                    }
                }
                else {
                    HttpSession sesion = request.getSession();
                    String nombre_usr = (String) sesion.getAttribute("usuario");
                    int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
                    solicitudes_conejera = dao.obtenerSolicitudesConejera(id_usuario);
                    request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
                }
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud editada con éxito"));

            }
            catch (SIGIPROException ex) {
                request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
            }
        }
        else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postRechazar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud_rech"));
        String obs = request.getParameter("observaciones_rechazo");
        boolean resultado = false;
        admin = verificarPermiso(254, listaPermisos);
        request.setAttribute("admin", admin);
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        try {
            SolicitudConejera solicitud = dao.obtenerSolicitudConejera(id_solicitud);
            solicitud.setEstado("Rechazada");
            solicitud.setObservaciones_rechazo(obs);
            resultado = dao.editarSolicitudConejera(solicitud);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_solicitud, Bitacora.ACCION_APROBAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------* 

            if (resultado) {
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud rechazada"));
            }
            else {
                request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
            }
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        try {
            List<SolicitudConejera> solicitudes_conejera;
            if (admin) {
                try {
                    solicitudes_conejera = dao.obtenerSolicitudesConejeraAdm();
                    request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
                }
                catch (SIGIPROException ex) {
                    request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
                }
            }
            else {
                HttpSession sesion = request.getSession();
                String nombre_usr = (String) sesion.getAttribute("usuario");
                int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
                solicitudes_conejera = dao.obtenerSolicitudesConejera(id_usuario);
                request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
            }
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        String redireccion = "SolicitudesConejera/index.jsp";
        redireccionar(request, response, redireccion);
    }

    protected void postEntregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud_auth2"));
        boolean resultado = false;
        boolean resultado2 = false;
        admin = verificarPermiso(254, listaPermisos);
        request.setAttribute("admin", admin);
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        try {
            String usuario = request.getParameter("usr");
            String contrasena = request.getParameter("passw");
            SolicitudConejera solicitud = dao.obtenerSolicitudConejera(id_solicitud);
            EntregaConejera entrega = construirSubObjeto(request);
            boolean auth = dao_us.AutorizarRecibo(usuario, contrasena);
            if (auth) {
                int id_us_recibo = dao_us.obtenerIDUsuario(usuario);
                entrega.setUsuario_recipiente(dao_us.obtenerUsuario(id_us_recibo));
                solicitud.setEstado("Abierta");
                resultado = dao.editarSolicitudConejera(solicitud);
                resultado2 = dao_en.insertarEntregaConejera(entrega);
            }
            else {
                request.setAttribute("mensaje_auth", helper.mensajeDeError("El usuario o contraseña son incorrectos"));
            }
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_solicitud, Bitacora.ACCION_ENTREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------* 

            if (resultado && resultado2) {
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud aprobada"));
            }
            else {
                request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
            }
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        try {
            List<SolicitudConejera> solicitudes_conejera;
            if (admin) {
                try {
                    solicitudes_conejera = dao.obtenerSolicitudesConejeraAdm();
                    request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
                }
                catch (SIGIPROException ex) {
                    request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
                }
            }
            else {
                HttpSession sesion = request.getSession();
                String nombre_usr = (String) sesion.getAttribute("usuario");
                int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
                solicitudes_conejera = dao.obtenerSolicitudesConejera(id_usuario);
                request.setAttribute("listaSolicitudesConejera", solicitudes_conejera);
            }
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        String redireccion = "SolicitudesConejera/index.jsp";
        redireccionar(request, response, redireccion);
    }
  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">

    private SolicitudConejera construirObjeto(HttpServletRequest request) throws SIGIPROException
    {
        SolicitudConejera solicitud = new SolicitudConejera();
        solicitud.setId_solicitud(Integer.parseInt(request.getParameter("id_solicitud")));
        if (request.getParameter("fecha_solicitud").equals("")) {
            java.util.Date fecha_solicitud = new java.util.Date();
            java.sql.Date fecha_solicitudSQL;
            fecha_solicitudSQL = new java.sql.Date(fecha_solicitud.getTime());
            solicitud.setFecha_solicitud(fecha_solicitudSQL);
        }
        else {
            String fch_sol = request.getParameter("fecha_solicitud");
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date fecha_solicitud;
            java.sql.Date fecha_solicitudSQL;
            try {
                fecha_solicitud = formatoFecha.parse(fch_sol);
                fecha_solicitudSQL = new java.sql.Date(fecha_solicitud.getTime());
                solicitud.setFecha_solicitud(fecha_solicitudSQL);
            }
            catch (ParseException ex) {
                Logger.getLogger(ControladorSolicitudesConejera.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        solicitud.setNumero_animales(Integer.parseInt(request.getParameter("numero_animales")));
        solicitud.setPeso_requerido(request.getParameter("peso_requerido"));
        solicitud.setSexo(request.getParameter("sexo"));
        solicitud.setObservaciones(request.getParameter("observaciones"));
        if (request.getParameter("usuario_solicitante").equals("")) {
            HttpSession sesion = request.getSession();
            String nombre_usr = (String) sesion.getAttribute("usuario");
            int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
            solicitud.setUsuario_solicitante(dao_us.obtenerUsuario(id_usuario));
        }
        else {
            int id_usuario = Integer.parseInt(request.getParameter("usuario_solicitante"));
            solicitud.setUsuario_solicitante(dao_us.obtenerUsuario(id_usuario));
        }

        return solicitud;
    }

    private EntregaConejera construirSubObjeto(HttpServletRequest request) throws SIGIPROException
    {
        EntregaConejera entrega = new EntregaConejera();
        entrega.setSolicitud(dao.obtenerSolicitudConejera(Integer.parseInt(request.getParameter("id_solicitud_auth2"))));
        java.util.Date fecha_solicitud = new java.util.Date();
        java.sql.Date fecha_solicitudSQL;
        fecha_solicitudSQL = new java.sql.Date(fecha_solicitud.getTime());
        entrega.setFecha_entrega(fecha_solicitudSQL);
        entrega.setNumero_animales(Integer.parseInt(request.getParameter("num_an1")));
        entrega.setPeso(request.getParameter("peso1"));
        entrega.setSexo(request.getParameter("sex1"));

        return entrega;
    }

  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        List<String> lista_acciones;
        if (accionHTTP.equals("get")) {
            lista_acciones = accionesGet;
        }
        else {
            lista_acciones = accionesPost;
        }
        if (lista_acciones.contains(accion.toLowerCase())) {
            String nombreMetodo = accionHTTP + Character.toUpperCase(accion.charAt(0)) + accion.substring(1);
            Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        }
        else {
            Method metodo = clase.getDeclaredMethod(accionHTTP + "Index", HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        }
    }

    @Override
    protected int getPermiso()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  // </editor-fold>
}
