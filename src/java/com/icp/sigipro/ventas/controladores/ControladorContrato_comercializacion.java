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

import com.icp.sigipro.ventas.dao.Contrato_comercializacionDAO;
import com.icp.sigipro.ventas.modelos.Contrato_comercializacion;

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
@WebServlet(name = "ControladorContrato_comercializacion", urlPatterns = {"/Ventas/ContratoComercializacion"})
public class ControladorContrato_comercializacion extends SIGIPROServlet {

    private final int[] permisos = {701, 702, 1};
    private final Contrato_comercializacionDAO dao = new Contrato_comercializacionDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();

    protected final Class clase = ControladorContrato_comercializacion.class;
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
       
        String redireccion = "ContratoComercializacion/Agregar.jsp";
        Contrato_comercializacion ds = new Contrato_comercializacion();
        
        request.setAttribute("contrato", ds);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        List<Contrato_comercializacion> contratos = dao.obtenerContratos_comercializacion();
        request.setAttribute("listaContratos", contratos);
        String redireccion = "ContratoComercializacion/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "ContratoComercializacion/Ver.jsp";
        int id_contrato = Integer.parseInt(request.getParameter("id_contrato"));
        try {
            Contrato_comercializacion c = dao.obtenerContrato_comercializacion(id_contrato);
            request.setAttribute("contrato", c);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "ContratoComercializacion/Editar.jsp";
        int id_contrato = Integer.parseInt(request.getParameter("id_contrato"));
        Contrato_comercializacion ds = dao.obtenerContrato_comercializacion(id_contrato);
        
        request.setAttribute("contrato", ds);
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        int resultado = 0;
        String redireccion = "ContratoComercializacion/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        try {
            Contrato_comercializacion contrato_nuevo = construirObjeto(request);
            resultado = dao.insertarContrato_comercializacion(contrato_nuevo);
            
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(contrato_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CONTRATO_COMERCIALIZACION, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado != 0){
            redireccion = "ContratoComercializacion/index.jsp";
            List<Contrato_comercializacion> contratos = dao.obtenerContratos_comercializacion();
            request.setAttribute("listaContratos", contratos);
            request.setAttribute("mensaje", helper.mensajeDeExito("Contrato agregado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "ContratoComercializacion/Editar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        try {
            Contrato_comercializacion contrato_nuevo = construirObjeto(request);
            
            resultado = dao.editarContrato_comercializacion(contrato_nuevo);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(contrato_nuevo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CONTRATO_COMERCIALIZACION, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "ContratoComercializacion/index.jsp";
            List<Contrato_comercializacion> contratos = dao.obtenerContratos_comercializacion();
            request.setAttribute("listaContratos", contratos);
            request.setAttribute("mensaje", helper.mensajeDeExito("Contrato editado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "ContratoComercializacion/index.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String id_contrato = request.getParameter("id_contrato"); 
        try {
            Contrato_comercializacion contrato_a_eliminar = dao.obtenerContrato_comercializacion(Integer.parseInt(id_contrato));
            
            resultado = dao.eliminarContrato_comercializacion(contrato_a_eliminar.getId_contrato());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(contrato_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CONTRATO_COMERCIALIZACION, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "ContratoComercializacion/index.jsp";
            List<Contrato_comercializacion> contratos = dao.obtenerContratos_comercializacion();
            request.setAttribute("listaContratos", contratos);
            request.setAttribute("mensaje", helper.mensajeDeExito("Contrato eliminado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Método del Modelo">
    private Contrato_comercializacion construirObjeto(HttpServletRequest request) throws SIGIPROException, ParseException {
        Contrato_comercializacion contrato = new Contrato_comercializacion();
        contrato.setId_contrato(Integer.parseInt(request.getParameter("id_contrato")));
        contrato.setNombre(request.getParameter("nombre"));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date result = df.parse(request.getParameter("fecha"));
        java.sql.Date fecha_solicitudSQL = new java.sql.Date(result.getTime());
        contrato.setFecha(fecha_solicitudSQL);
        contrato.setObservaciones(request.getParameter("observaciones"));
        return contrato;
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
