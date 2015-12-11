/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.produccion.dao.Veneno_ProduccionDAO;
import com.icp.sigipro.produccion.modelos.Veneno_Produccion;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
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
@WebServlet(name = "ControladorVeneno_Produccion", urlPatterns = {"/Produccion/Veneno_Produccion"})
public class ControladorVeneno_Produccion extends SIGIPROServlet {

    private final int[] permisos = {605, 1, 1};
    private final Veneno_ProduccionDAO dao = new Veneno_ProduccionDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();

    protected final Class clase = ControladorVeneno_Produccion.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
            add("editar");
            add("eliminar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(605, listaPermisos);
       
        String redireccion = "Veneno_Produccion/Agregar.jsp";
        Veneno_Produccion ds = new Veneno_Produccion();
        request.setAttribute("veneno", ds);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        List<Veneno_Produccion> venenos = new Veneno_ProduccionDAO().obtenerVenenos_Produccion();
        request.setAttribute("listaVenenos", venenos);
        String redireccion = "Veneno_Produccion/index.jsp";
        
        redireccionar(request, response, redireccion);
    }
/*
    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        admin = verificarPermiso(203, listaPermisos);
        request.setAttribute("admin", admin);
        String redireccion = "SolicitudesRatonera/Ver.jsp";
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        try {
            SolicitudRatonera s = dao.obtenerSolicitudRatonera(id_solicitud);
            List<EntregaRatonera> e = dao_en.obtenerEntregasRatonera(id_solicitud);
            request.setAttribute("solicitud", s);
            request.setAttribute("entregas", e);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "SolicitudesRatonera/Editar.jsp";
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        HttpSession sesion = request.getSession();
        int id_usuario = (int) sesion.getAttribute("idusuario");
        Usuario us = dao_us.obtenerUsuario(id_usuario);
        List<Usuario> usuarios = dao_us.obtenerUsuarios(us);
        request.setAttribute("usuarios", usuarios);
        request.setAttribute("accion", "Editar");
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        try {
            SolicitudRatonera s = dao.obtenerSolicitudRatonera(id_solicitud);
            request.setAttribute("solicitud", s);
            List<Cepa> cepas = dao_ce.obtenerCepas();
            request.setAttribute("cepas", cepas);
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
*/
/*
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = false;
        String redireccion = "SolicitudesRatonera/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        admin = verificarPermiso(203, listaPermisos);
        request.setAttribute("admin", admin);
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        try {
            SolicitudRatonera solicitud = construirObjeto(request);
            solicitud.setEstado("Pendiente");
            resultado = dao.insertarSolicitudRatonera(solicitud);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(solicitud.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "SolicitudesRatonera/index.jsp";
            List<SolicitudRatonera> solicitudes_ratonera;
            try {
                List<Cepa> cepas = dao_ce.obtenerCepas();
                request.setAttribute("cepas", cepas);
                if (admin) {
                    try {
                        solicitudes_ratonera = dao.obtenerSolicitudesRatoneraAdm();
                        request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
                    } catch (SIGIPROException ex) {
                        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
                    }
                } else {
                    HttpSession sesion = request.getSession();
                    int id_usuario = (int) sesion.getAttribute("idusuario");
                    Usuario u = dao_us.obtenerUsuario(id_usuario);
                    solicitudes_ratonera = dao.obtenerSolicitudesRatonera(u.getIdSeccion());
                    request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
                }
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud agregada con éxito"));
            } catch (SIGIPROException ex) {
                request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
            }
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = false;
        String redireccion = "SolicitudesRatonera/Editar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        admin = verificarPermiso(203, listaPermisos);
        request.setAttribute("admin", admin);
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        try {
            SolicitudRatonera solicitud = construirObjeto(request);
            solicitud.setEstado("Pendiente");
            resultado = dao.editarSolicitudRatonera(solicitud);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(solicitud.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "SolicitudesRatonera/index.jsp";
            List<SolicitudRatonera> solicitudes_ratonera;
            try {
                List<Cepa> cepas = dao_ce.obtenerCepas();
                request.setAttribute("cepas", cepas);
                if (admin) {
                    try {
                        solicitudes_ratonera = dao.obtenerSolicitudesRatoneraAdm();
                        request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
                    } catch (SIGIPROException ex) {
                        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
                    }
                } else {
                    HttpSession sesion = request.getSession();
                    int id_usuario = (int) sesion.getAttribute("idusuario");
                    Usuario u = dao_us.obtenerUsuario(id_usuario);
                    solicitudes_ratonera = dao.obtenerSolicitudesRatonera(u.getIdSeccion());
                    request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
                }
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud editada con éxito"));

            } catch (SIGIPROException ex) {
                request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
            }
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postRechazar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud_rech"));
        String obs = request.getParameter("observaciones_rechazo");
        boolean resultado = false;
        admin = verificarPermiso(203, listaPermisos);
        request.setAttribute("admin", admin);
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        try {
            List<Cepa> cepas = dao_ce.obtenerCepas();
            request.setAttribute("cepas", cepas);
            SolicitudRatonera solicitud = dao.obtenerSolicitudRatonera(id_solicitud);
            solicitud.setEstado("Rechazada");
            solicitud.setObservaciones_rechazo(obs);
            resultado = dao.editarSolicitudRatonera(solicitud);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_solicitud, Bitacora.ACCION_APROBAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------* 

            if (resultado) {
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud rechazada"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
            }
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        try {
            List<SolicitudRatonera> solicitudes_ratonera;
            if (admin) {
                try {
                    solicitudes_ratonera = dao.obtenerSolicitudesRatoneraAdm();
                    request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
                } catch (SIGIPROException ex) {
                    request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
                }
            } else {
                HttpSession sesion = request.getSession();
                int id_usuario = (int) sesion.getAttribute("idusuario");
                Usuario u = dao_us.obtenerUsuario(id_usuario);
                solicitudes_ratonera = dao.obtenerSolicitudesRatonera(u.getIdSeccion());
                request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
            }
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        String redireccion = "SolicitudesRatonera/index.jsp";
        redireccionar(request, response, redireccion);
    }

    protected void postEntregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud_auth2"));
        boolean resultado = false;
        boolean resultado2 = false;
        admin = verificarPermiso(203, listaPermisos);
        request.setAttribute("admin", admin);
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        try {
            List<Cepa> cepas = dao_ce.obtenerCepas();
            request.setAttribute("cepas", cepas);
            String usuario = request.getParameter("usr");
            String contrasena = request.getParameter("passw");
            SolicitudRatonera solicitud = dao.obtenerSolicitudRatonera(id_solicitud);
            EntregaRatonera entrega = construirSubObjeto(request);
            boolean auth = dao_us.AutorizarRecibo(usuario, contrasena);
            if (auth) {
                int id_us_recibo = dao_us.obtenerIDUsuario(usuario);
                entrega.setUsuario_recipiente(dao_us.obtenerUsuario(id_us_recibo));
                solicitud.setEstado("Abierta");
                resultado = dao.editarSolicitudRatonera(solicitud);
                resultado2 = dao_en.insertarEntregaRatonera(entrega);
            } else {
                request.setAttribute("mensaje_auth", helper.mensajeDeError("El usuario o contraseña son incorrectos"));
            }
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_solicitud, Bitacora.ACCION_ENTREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------* 

            if (resultado && resultado2) {
                request.setAttribute("mensaje", helper.mensajeDeExito("Entrega registrada."));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
            }
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        try {
            List<SolicitudRatonera> solicitudes_ratonera;
            if (admin) {
                try {
                    solicitudes_ratonera = dao.obtenerSolicitudesRatoneraAdm();
                    request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
                } catch (SIGIPROException ex) {
                    request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
                }
            } else {
                HttpSession sesion = request.getSession();
                int id_usuario = (int) sesion.getAttribute("idusuario");
                Usuario u = dao_us.obtenerUsuario(id_usuario);
                solicitudes_ratonera = dao.obtenerSolicitudesRatonera(u.getIdSeccion());
                request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
            }
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        String redireccion = "SolicitudesRatonera/index.jsp";
        redireccionar(request, response, redireccion);
    }
*/
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
