/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.controladores;

import com.icp.sigipro.produccion.modelos.Inoculo;
import com.icp.sigipro.produccion.dao.InoculoDAO;
import com.icp.sigipro.produccion.dao.Veneno_ProduccionDAO;
import com.icp.sigipro.produccion.modelos.Veneno_Produccion;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
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
@WebServlet(name = "ControladorInoculo", urlPatterns = {"/Produccion/Inoculo"})
public class ControladorInoculo extends SIGIPROServlet {

    private final int[] permisos = {604, 1};
    private final InoculoDAO dao = new InoculoDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();
    private boolean admin = false;

    protected final Class clase = ControladorInoculo.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
            add("eliminar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
            add("editar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(604, listaPermisos);

        HttpSession sesion = request.getSession();
        String nombre_usr = (String) sesion.getAttribute("usuario");
        int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
        Usuario us = dao_us.obtenerUsuario(id_usuario);
        List<Usuario> usuarios = dao_us.obtenerUsuarios(us);

        List<Veneno_Produccion> venenos = new Veneno_ProduccionDAO().obtenerVenenos_Produccion();
        
        request.setAttribute("usuarios", usuarios);
        request.setAttribute("venenos", usuarios);
        
        String redireccion = "Inoculo/Agregar.jsp";
        Inoculo ds = new Inoculo();
        request.setAttribute("inoculo", ds);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        request.setAttribute("admin", admin);
        String redireccion = "Inoculo/index.jsp";
        InoculoDAO iDAO = new InoculoDAO();
        List<Inoculo> inoculos;
        try {
            //HttpSession sesion = request.getSession();
            //int id_usuario = (int) sesion.getAttribute("idusuario");
            //Usuario u = dao_us.obtenerUsuario(id_usuario);
            inoculos = iDAO.obtenerInoculos();
            request.setAttribute("listaInoculos", inoculos);
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
/*
    
    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        admin = verificarPermiso(604, listaPermisos);
        request.setAttribute("admin", admin);
        String redireccion = "Inoculo/Ver.jsp";
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        try {
            Inoculo s = dao.obtenerInoculo(id_solicitud);
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
        String redireccion = "Inoculo/Editar.jsp";
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
            Inoculo s = dao.obtenerInoculo(id_solicitud);
            request.setAttribute("solicitud", s);
            List<Cepa> cepas = dao_ce.obtenerCepas();
            request.setAttribute("cepas", cepas);
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        admin = verificarPermiso(604, listaPermisos);
        request.setAttribute("admin", admin);
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        try {
            List<Cepa> cepas = dao_ce.obtenerCepas();
            request.setAttribute("cepas", cepas);
            dao.eliminarInoculo(id_solicitud);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_solicitud, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------* 
            request.setAttribute("mensaje", helper.mensajeDeExito("Inoculo eliminada correctamente."));
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        try {
            List<Inoculo> solicitudes_ratonera;
            if (admin) {
                try {
                    solicitudes_ratonera = dao.obtenerInoculoAdm();
                    request.setAttribute("listaInoculo", solicitudes_ratonera);
                } catch (SIGIPROException ex) {
                    request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
                }
            } else {
                HttpSession sesion = request.getSession();
                int id_usuario = (int) sesion.getAttribute("idusuario");
                Usuario u = dao_us.obtenerUsuario(id_usuario);
                solicitudes_ratonera = dao.obtenerInoculo(u.getIdSeccion());
                request.setAttribute("listaInoculo", solicitudes_ratonera);
            }
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        String redireccion = "Inoculo/index.jsp";
        redireccionar(request, response, redireccion);
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = false;
        String redireccion = "Inoculo/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        admin = verificarPermiso(604, listaPermisos);
        request.setAttribute("admin", admin);
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        try {
            Inoculo solicitud = construirObjeto(request);
            solicitud.setEstado("Pendiente");
            resultado = dao.insertarInoculo(solicitud);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(solicitud.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Inoculo/index.jsp";
            List<Inoculo> solicitudes_ratonera;
            try {
                List<Cepa> cepas = dao_ce.obtenerCepas();
                request.setAttribute("cepas", cepas);
                if (admin) {
                    try {
                        solicitudes_ratonera = dao.obtenerInoculoAdm();
                        request.setAttribute("listaInoculo", solicitudes_ratonera);
                    } catch (SIGIPROException ex) {
                        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
                    }
                } else {
                    HttpSession sesion = request.getSession();
                    int id_usuario = (int) sesion.getAttribute("idusuario");
                    Usuario u = dao_us.obtenerUsuario(id_usuario);
                    solicitudes_ratonera = dao.obtenerInoculo(u.getIdSeccion());
                    request.setAttribute("listaInoculo", solicitudes_ratonera);
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
        String redireccion = "Inoculo/Editar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        admin = verificarPermiso(604, listaPermisos);
        request.setAttribute("admin", admin);
        request.setAttribute("pesos", pesos);
        request.setAttribute("sexos", sexos);
        try {
            Inoculo solicitud = construirObjeto(request);
            solicitud.setEstado("Pendiente");
            resultado = dao.editarInoculo(solicitud);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(solicitud.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Inoculo/index.jsp";
            List<Inoculo> solicitudes_ratonera;
            try {
                List<Cepa> cepas = dao_ce.obtenerCepas();
                request.setAttribute("cepas", cepas);
                if (admin) {
                    try {
                        solicitudes_ratonera = dao.obtenerInoculoAdm();
                        request.setAttribute("listaInoculo", solicitudes_ratonera);
                    } catch (SIGIPROException ex) {
                        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
                    }
                } else {
                    HttpSession sesion = request.getSession();
                    int id_usuario = (int) sesion.getAttribute("idusuario");
                    Usuario u = dao_us.obtenerUsuario(id_usuario);
                    solicitudes_ratonera = dao.obtenerInoculo(u.getIdSeccion());
                    request.setAttribute("listaInoculo", solicitudes_ratonera);
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
*/
    @Override
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  // </editor-fold>
    }
