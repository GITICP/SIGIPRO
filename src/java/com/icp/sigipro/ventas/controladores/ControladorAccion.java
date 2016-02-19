/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;

import com.icp.sigipro.ventas.dao.AccionDAO;
import com.icp.sigipro.ventas.modelos.Accion;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorAccion", urlPatterns = {"/Ventas/Accion"})
public class ControladorAccion extends SIGIPROServlet {

    private final int[] permisos = {701, 702, 1};
    private final AccionDAO dao = new AccionDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();

    protected final Class clase = ControladorAccion.class;
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
        validarPermisos(permisos, listaPermisos);
       
        String redireccion = "Accion/Agregar.jsp";
        Accion ds = new Accion();
        
        request.setAttribute("accion_venta", ds);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        List<Accion> accions = dao.obtenerAcciones();
        request.setAttribute("listaAcciones", accions);
        String redireccion = "Accion/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Accion/Ver.jsp";
        int id_accion = Integer.parseInt(request.getParameter("id_accion"));
        try {
            Accion c = dao.obtenerAccion(id_accion);
            request.setAttribute("accion_venta", c);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Accion/Editar.jsp";
        int id_accion = Integer.parseInt(request.getParameter("id_accion"));
        Accion ds = dao.obtenerAccion(id_accion);
        
        request.setAttribute("accion_venta", ds);
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        int resultado = 0;
        String redireccion = "Accion/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        try {
            Accion accion_nuevo = construirObjeto(request);
            resultado = dao.insertarAccion(accion_nuevo);
            
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(accion_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ACCION, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado != 0){
            redireccion = "Accion/index.jsp";
            List<Accion> accions = dao.obtenerAcciones();
            request.setAttribute("listaAcciones", accions);
            request.setAttribute("mensaje", helper.mensajeDeExito("Acción agregada correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Accion/Editar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        try {
            Accion accion_nuevo = construirObjeto(request);
            
            resultado = dao.editarAccion(accion_nuevo);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(accion_nuevo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ACCION, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Accion/index.jsp";
            List<Accion> accions = dao.obtenerAcciones();
            request.setAttribute("listaAcciones", accions);
            request.setAttribute("mensaje", helper.mensajeDeExito("Acción editada correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Accion/index.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String id_accion = request.getParameter("id_accion"); 
        try {
            Accion accion_a_eliminar = dao.obtenerAccion(Integer.parseInt(id_accion));
            
            resultado = dao.eliminarAccion(accion_a_eliminar.getId_accion());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(accion_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ACCION, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Accion/index.jsp";
            List<Accion> accions = dao.obtenerAcciones();
            request.setAttribute("listaAcciones", accions);
            request.setAttribute("mensaje", helper.mensajeDeExito("Acción eliminada correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Método del Modelo">
    private Accion construirObjeto(HttpServletRequest request) throws SIGIPROException, ParseException {
        Accion accion = new Accion();
        accion.setId_accion(Integer.parseInt(request.getParameter("id_accion")));
        accion.setAccion(request.getParameter("accion_accion"));
        return accion;
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
