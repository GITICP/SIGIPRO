/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.controladores;

import com.icp.sigipro.produccion.controladores.ControladorInventario_PT;
//import com.icp.sigipro.produccion.dao.Inventario_PTDAO;
import com.icp.sigipro.produccion.modelos.Inventario_PT;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelperFechas;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
@WebServlet(name = "ControladorInventario_PT", urlPatterns = {"/Produccion/Inventario_PT"})
public class ControladorInventario_PT extends SIGIPROServlet {

 private final int[] permisos = {602, 601, 603};
    //private final Inventario_PTDAO dao = new Inventario_PTDAO();
    private final HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

    protected final Class clase = ControladorInventario_PT.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregar");
            add("editar");
            add("eliminar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(209, listaPermisos);
        String redireccion = "Inventario_PT/index.jsp";
//        try {
//            List<Inventario_PT> pies = dao.obtenerInventario_PT();
//            request.setAttribute("pies", pies);
//        }
//        catch (SIGIPROException sig_ex) {
//            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
//        }

        redireccionar(request, response, redireccion);
    }

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(209, listaPermisos);
        String redireccion = "Inventario_PT/Agregar.jsp";
//        Inventario_PT pie = new Inventario_PT();
//        request.setAttribute("pie", pie);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Inventario_PT/Editar.jsp";
        int id_pie = Integer.parseInt(request.getParameter("id_pie"));
        request.setAttribute("accion", "Editar");
//        try {
//            Inventario_PT pie = dao.obtenerInventario_PT(id_pie);
//            request.setAttribute("pie", pie);
//        }
//        catch (SIGIPROException ex) {
//            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
//        }
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Inventario_PT/Ver.jsp";
        int id_pie = Integer.parseInt(request.getParameter("id_pie"));
//        try {
//            Inventario_PT pie = dao.obtenerInventario_PT(id_pie);
//            request.setAttribute("pie", pie);
//        }
//        catch (SIGIPROException ex) {
//            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
//        }
        redireccionar(request, response, redireccion);
    }

  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Inventario_PT/Agregar.jsp";
//        try {
//            Inventario_PT pie = construirObjeto(request);
//
//            dao.insertarInventario_PT(pie);
//
//            BitacoraDAO bitacora = new BitacoraDAO();
//            bitacora.setBitacora(pie.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
//
//            redireccion = "Inventario_PT/index.jsp";
//            request.setAttribute("mensaje", helper.mensajeDeExito("Inventario_PT agregado correctamente."));
//        }
//        catch (SIGIPROException ex) {
//            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
//        }
//
//        try {
//            List<Inventario_PT> pies = dao.obtenerInventario_PT();
//            request.setAttribute("pies", pies);
//        }
//        catch (SIGIPROException sig_ex) {
//            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
//        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Inventario_PT/Editar.jsp";
//        try {
//            Inventario_PT pie = construirObjeto(request);
//            dao.editarInventario_PT(pie);
//
//            BitacoraDAO bitacora = new BitacoraDAO();
//            bitacora.setBitacora(pie.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
//
//            redireccion = "Inventario_PT/index.jsp";
//            request.setAttribute("mensaje", helper.mensajeDeExito("Inventario_PT editado correctamente."));
//        }
//        catch (SIGIPROException ex) {
//            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
//        }
//
//        try {
//            List<Inventario_PT> pies = dao.obtenerInventario_PT();
//            request.setAttribute("pies", pies);
//        }
//        catch (SIGIPROException sig_ex) {
//            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
//        }
        redireccionar(request, response, redireccion);
    }

    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        int id_pie = Integer.parseInt(request.getParameter("id_pie"));
        String redireccion = "Inventario_PT/index.jsp";
//        Inventario_PT pie;
//        try {
//            pie = dao.obtenerInventario_PT(id_pie);
//        }
//        catch (SIGIPROException ex) {
//            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
//        }
//        try {
//            dao.eliminarInventario_PT(id_pie);
//
//            BitacoraDAO bitacora = new BitacoraDAO();
//            bitacora.setBitacora(id_pie, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
//
//            request.setAttribute("mensaje", helper.mensajeDeExito("Inventario_PT eliminado correctamente."));
//        }
//        catch (SIGIPROException ex) {
//            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
//        }
//
//        try {
//            List<Inventario_PT> pies = dao.obtenerInventario_PT();
//            request.setAttribute("pies", pies);
//        }
//        catch (SIGIPROException sig_ex) {
//            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
//        }
        redireccionar(request, response, redireccion);
    }

  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private Inventario_PT construirObjeto(HttpServletRequest request) throws SIGIPROException
    {
        Inventario_PT pie = new Inventario_PT();

//        int id_pie = Integer.parseInt(request.getParameter("id_pie"));
//        pie.setId_pie(id_pie);
//        pie.setCodigo(request.getParameter("codigo"));
//        pie.setFuente(request.getParameter("fuente"));
//        String fecha_ingreso_str = request.getParameter("fecha_ingreso");
//        String fecha_retiro_str = request.getParameter("fecha_retiro");
//
//        try {
//            HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
//            pie.setFecha_ingreso(helper_fechas.formatearFecha(fecha_ingreso_str));
//            pie.setFecha_retiro(helper_fechas.formatearFecha(fecha_retiro_str));
//        }
//        catch (ParseException ex) {
//            ex.printStackTrace();
//        }

        return pie;
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
