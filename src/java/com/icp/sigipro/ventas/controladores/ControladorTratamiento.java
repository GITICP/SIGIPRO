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

import com.icp.sigipro.ventas.dao.TratamientoDAO;
import com.icp.sigipro.ventas.modelos.Tratamiento;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.ventas.dao.ClienteDAO;
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
@WebServlet(name = "ControladorTratamiento", urlPatterns = {"/Ventas/Tratamiento"})
public class ControladorTratamiento extends SIGIPROServlet {

    private final TratamientoDAO dao = new TratamientoDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();
    private final ClienteDAO cdao = new ClienteDAO();

    protected final Class clase = ControladorTratamiento.class;
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
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
       
        String redireccion = "Tratamiento/Agregar.jsp";
        Tratamiento ds = new Tratamiento();
        List<String> estados = new ArrayList<String>();
        estados.add("A");
        estados.add("B");
        estados.add("C");
        
        request.setAttribute("estados", estados);
        request.setAttribute("tratamiento", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701,702,703,704,705,706, 1};
        validarPermisos(permisos, listaPermisos);

        List<Tratamiento> tratamientos = dao.obtenerTratamientos();
        request.setAttribute("listaTratamientos", tratamientos);
        String redireccion = "Tratamiento/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Tratamiento/Ver.jsp";
        int id_tratamiento = Integer.parseInt(request.getParameter("id_tratamiento"));
        try {
            Tratamiento c = dao.obtenerTratamiento(id_tratamiento);
            request.setAttribute("tratamiento", c);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Tratamiento/Editar.jsp";
        int id_tratamiento = Integer.parseInt(request.getParameter("id_tratamiento"));
        Tratamiento ds = dao.obtenerTratamiento(id_tratamiento);
        List<String> estados = new ArrayList<String>();
        estados.add("A");
        estados.add("B");
        estados.add("C");
        
        request.setAttribute("estados", estados);
        request.setAttribute("tratamiento", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        int resultado = 0;
        String redireccion = "Tratamiento/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
        try {
            Tratamiento tratamiento_nuevo = construirObjeto(request);
            resultado = dao.insertarTratamiento(tratamiento_nuevo);
            
            
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(tratamiento_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_TRATAMIENTO, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado != 0){
            redireccion = "Tratamiento/index.jsp";
            List<Tratamiento> tratamientos = dao.obtenerTratamientos();
            request.setAttribute("listaTratamientos", tratamientos);
            request.setAttribute("mensaje", helper.mensajeDeExito("Tratamiento agregado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Tratamiento/Editar.jsp";
        int[] permisos = {701, 1};
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        try {
            Tratamiento tratamiento_nuevo = construirObjeto(request);
            
            resultado = dao.editarTratamiento(tratamiento_nuevo);
            
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(tratamiento_nuevo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_TRATAMIENTO, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Tratamiento/index.jsp";
            List<Tratamiento> tratamientos = dao.obtenerTratamientos();
            request.setAttribute("listaTratamientos", tratamientos);
            request.setAttribute("mensaje", helper.mensajeDeExito("Tratamiento editado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Tratamiento/index.jsp";
        int[] permisos = {701, 1};
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String id_tratamiento = request.getParameter("id_tratamiento"); 
        try {
            Tratamiento tratamiento_a_eliminar = dao.obtenerTratamiento(Integer.parseInt(id_tratamiento));
            
            resultado = dao.eliminarTratamiento(tratamiento_a_eliminar.getId_tratamiento());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(tratamiento_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_TRATAMIENTO, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Tratamiento/index.jsp";
            List<Tratamiento> tratamientos = dao.obtenerTratamientos();
            request.setAttribute("listaTratamientos", tratamientos);
            request.setAttribute("mensaje", helper.mensajeDeExito("Tratamiento eliminado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Método del Modelo">
    private Tratamiento construirObjeto(HttpServletRequest request) throws SIGIPROException, ParseException {
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setId_tratamiento(Integer.parseInt(request.getParameter("id_tratamiento")));
        tratamiento.setCliente(cdao.obtenerCliente(Integer.parseInt(request.getParameter("id_cliente"))));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date result = df.parse(request.getParameter("fecha"));
        java.sql.Date fecha_solicitudSQL = new java.sql.Date(result.getTime());
        tratamiento.setFecha(fecha_solicitudSQL);
        tratamiento.setObservaciones(request.getParameter("observaciones"));
        tratamiento.setEstado(request.getParameter("estado"));
        return tratamiento;
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
