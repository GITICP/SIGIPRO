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
import com.icp.sigipro.serpentario.dao.LoteDAO;
import com.icp.sigipro.serpentario.modelos.Lote;
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
        List<Lote> listaVenenos = new LoteDAO().obtenerLotes();
        request.setAttribute("veneno", ds);
        request.setAttribute("listaVenenos", listaVenenos);
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

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Veneno_Produccion/Ver.jsp";
        int id_veneno = Integer.parseInt(request.getParameter("id_veneno"));
        try {
            Veneno_Produccion v = dao.obtenerVeneno_Produccion(id_veneno);
            request.setAttribute("veneno", v);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Veneno_Produccion/Editar.jsp";
        int id_veneno = Integer.parseInt(request.getParameter("id_veneno"));
        Veneno_Produccion ds = dao.obtenerVeneno_Produccion(id_veneno);
        List<Lote> listaVenenos = new LoteDAO().obtenerLotes();
        request.setAttribute("veneno", ds);
        request.setAttribute("listaVenenos", listaVenenos);
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }


    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        boolean resultado = false;
        String redireccion = "Veneno_Produccion/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        try {
            Veneno_Produccion veneno_nuevo = construirObjeto(request);
            
            resultado = dao.insertarVeneno_Produccion(veneno_nuevo);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(veneno_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_INOCULO_PRODUCCION, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Veneno_Produccion/index.jsp";
            List<Veneno_Produccion> venenos = new Veneno_ProduccionDAO().obtenerVenenos_Produccion();
            request.setAttribute("listaVenenos", venenos);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Veneno_Produccion/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        try {
            Veneno_Produccion veneno_nuevo = construirObjetoEditar(request);
            
            resultado = dao.editarVeneno_Produccion(veneno_nuevo);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(veneno_nuevo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_INOCULO_PRODUCCION, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Veneno_Produccion/index.jsp";
            List<Veneno_Produccion> venenos = new Veneno_ProduccionDAO().obtenerVenenos_Produccion();
            request.setAttribute("listaVenenos", venenos);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Veneno_Produccion/index.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        try {
            Veneno_Produccion veneno_a_eliminar = dao.obtenerVeneno_Produccion(Integer.parseInt(request.getParameter("id_veneno")));
            
            resultado = dao.eliminarVeneno_Produccion(veneno_a_eliminar.getId_veneno());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(veneno_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_INOCULO_PRODUCCION, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Veneno_Produccion/index.jsp";
            List<Veneno_Produccion> venenos = new Veneno_ProduccionDAO().obtenerVenenos_Produccion();
            request.setAttribute("listaVenenos", venenos);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos del Modelo">
    private Veneno_Produccion construirObjeto(HttpServletRequest request) throws SIGIPROException, ParseException {
        Veneno_Produccion veneno = new Veneno_Produccion();
        veneno.setVeneno(request.getParameter("veneno"));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date result = df.parse(request.getParameter("fecha_ingreso"));
        java.sql.Date fecha_solicitudSQL = new java.sql.Date(result.getTime());
        veneno.setFecha_ingreso(fecha_solicitudSQL);
        LoteDAO vDAO = new LoteDAO();
        veneno.setVeneno_serpentario(vDAO.obtenerLote(Integer.parseInt(request.getParameter("id_veneno_serpentario"))));
        veneno.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
        veneno.setObservaciones(request.getParameter("observaciones"));

        return veneno;
    }
    
    private Veneno_Produccion construirObjetoEditar(HttpServletRequest request) throws SIGIPROException, ParseException {
        Veneno_Produccion veneno = new Veneno_Produccion();
        veneno.setId_veneno(Integer.parseInt(request.getParameter("id_veneno")));
        veneno.setVeneno(request.getParameter("veneno"));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date result = df.parse(request.getParameter("fecha_ingreso"));
        java.sql.Date fecha_solicitudSQL = new java.sql.Date(result.getTime());
        veneno.setFecha_ingreso(fecha_solicitudSQL);
        LoteDAO vDAO = new LoteDAO();
        veneno.setVeneno_serpentario(vDAO.obtenerLote(Integer.parseInt(request.getParameter("id_veneno_serpentario"))));
        veneno.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
        veneno.setObservaciones(request.getParameter("observaciones"));

        return veneno;
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
