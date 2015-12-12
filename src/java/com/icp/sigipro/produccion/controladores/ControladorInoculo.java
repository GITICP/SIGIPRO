/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.produccion.modelos.Inoculo;
import com.icp.sigipro.produccion.dao.InoculoDAO;
import com.icp.sigipro.produccion.dao.Veneno_ProduccionDAO;
import com.icp.sigipro.produccion.modelos.Veneno_Produccion;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Josue
 */
@WebServlet(name = "ControladorInoculo_Bueno", urlPatterns = {"/Produccion/Inoculo"})
public class ControladorInoculo extends SIGIPROServlet {

    
    private final int[] permisos = {604, 1, 1};
    private final InoculoDAO dao = new InoculoDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();
    private final Veneno_ProduccionDAO dao_vp = new Veneno_ProduccionDAO();
    
    protected final Class clase = ControladorInoculo.class;
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
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        HttpSession sesion = request.getSession();
        String nombre_usr = (String) sesion.getAttribute("usuario");
        int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
        Usuario us = dao_us.obtenerUsuario(id_usuario);
        List<Usuario> usuarios = dao_us.obtenerUsuarios(us);

        List<Veneno_Produccion> venenos = dao_vp.obtenerVenenos_Produccion();
        
        request.setAttribute("usuarios", usuarios);
        request.setAttribute("venenos", venenos);
        
        String redireccion = "Inoculo/Agregar.jsp";
        Inoculo ds = new Inoculo();
        request.setAttribute("inoculo", ds);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Inoculo/index.jsp";
        List<Inoculo> inoculos;
        try {
            inoculos = dao.obtenerInoculos();
            request.setAttribute("listaInoculos", inoculos);
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Inoculo/Ver.jsp";
        int id_inoculo = Integer.parseInt(request.getParameter("id_inoculo"));
        try {
            Inoculo i = dao.obtenerInoculo(id_inoculo);
            request.setAttribute("inoculo", i);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        HttpSession sesion = request.getSession();
        String nombre_usr = (String) sesion.getAttribute("usuario");
        int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
        Usuario us = dao_us.obtenerUsuario(id_usuario);
        List<Usuario> usuarios = dao_us.obtenerUsuarios(us);

        List<Veneno_Produccion> venenos = dao_vp.obtenerVenenos_Produccion();
        
        request.setAttribute("usuarios", usuarios);
        request.setAttribute("venenos", venenos);
        
        String redireccion = "Inoculo/Editar.jsp";
        Inoculo ds = dao.obtenerInoculo(Integer.parseInt(request.getParameter("id_inoculo")));
        request.setAttribute("inoculo", ds);
        request.setAttribute("accion", "Editar");

        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        boolean resultado = false;
        String redireccion = "Inoculo/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        try {
            Inoculo inoculo_nuevo = construirObjeto(request);
            
            resultado = dao.insertarInoculo(inoculo_nuevo);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(inoculo_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Inoculo/index.jsp";
            List<Inoculo> inoculos = dao.obtenerInoculos();
            request.setAttribute("listaInoculos", inoculos);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Inoculo/Editar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        try {
            Inoculo inoculo_nuevo = construirObjetoEditar(request);
            
            resultado = dao.editarInoculo(inoculo_nuevo);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(inoculo_nuevo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Inoculo/index.jsp";
            List<Inoculo> inoculos = dao.obtenerInoculos();
            request.setAttribute("listaInoculos", inoculos);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Inoculo/index.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        try {
            Inoculo inoculo_a_eliminar = dao.obtenerInoculo(Integer.parseInt(request.getParameter("id_inoculo")));
            
            resultado = dao.eliminarInoculo(inoculo_a_eliminar.getId_inoculo());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(inoculo_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Inoculo/index.jsp";
            List<Inoculo> inoculos = dao.obtenerInoculos();
            request.setAttribute("listaInoculos", inoculos);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos del Modelo">
    private Inoculo construirObjeto(HttpServletRequest request) throws SIGIPROException, ParseException {
        Inoculo inoculo = new Inoculo();
        inoculo.setIdentificador(request.getParameter("identificador"));
        inoculo.setPeso(Integer.parseInt(request.getParameter("peso")));
        inoculo.setVeneno(dao_vp.obtenerVeneno_Produccion(Integer.parseInt(request.getParameter("id_veneno"))));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date result = df.parse(request.getParameter("fecha_preparacion"));
        java.sql.Date fecha_solicitudSQL = new java.sql.Date(result.getTime());
        inoculo.setFecha_preparacion(fecha_solicitudSQL);
        inoculo.setEncargado_preparacion(dao_us.obtenerUsuario(Integer.parseInt(request.getParameter("encargado_preparacion"))));

        return inoculo;
    }
    
    private Inoculo construirObjetoEditar(HttpServletRequest request) throws SIGIPROException, ParseException {
        Inoculo inoculo = new Inoculo();
        inoculo.setId_inoculo(Integer.parseInt(request.getParameter("id_inoculo")));
        inoculo.setIdentificador(request.getParameter("identificador"));
        inoculo.setPeso(Integer.parseInt(request.getParameter("peso")));
        inoculo.setVeneno(dao_vp.obtenerVeneno_Produccion(Integer.parseInt(request.getParameter("id_veneno"))));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date result = df.parse(request.getParameter("fecha_preparacion"));
        java.sql.Date fecha_solicitudSQL = new java.sql.Date(result.getTime());
        inoculo.setFecha_preparacion(fecha_solicitudSQL);
        inoculo.setEncargado_preparacion(dao_us.obtenerUsuario(Integer.parseInt(request.getParameter("encargado_preparacion"))));

        return inoculo;
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
