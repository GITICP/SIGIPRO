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

import com.icp.sigipro.ventas.dao.Producto_ventaDAO;
import com.icp.sigipro.ventas.modelos.Producto_venta;

import com.icp.sigipro.produccion.dao.Inventario_PTDAO;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
@WebServlet(name = "ControladorProducto_ventas", urlPatterns = {"/Ventas/Producto_ventas"})
public class ControladorProducto_venta extends SIGIPROServlet {

    private final Producto_ventaDAO dao = new Producto_ventaDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();
    private final Inventario_PTDAO dao_inv = new Inventario_PTDAO();

    protected final Class clase = ControladorProducto_venta.class;
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
        int[] permisos = {701, 703, 1};
        validarPermisos(permisos, listaPermisos);
       
        String redireccion = "Producto_ventas/Agregar.jsp";
        Producto_venta ds = new Producto_venta();
        
        request.setAttribute("producto", ds);
        request.setAttribute("inventarios", dao_inv.obtenerInventario_PTs());
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 703, 1,704,705,706,702};
        validarPermisos(permisos, listaPermisos);

        List<Producto_venta> productos = dao.obtenerProductos_venta();
        request.setAttribute("listaProductos", productos);
        String redireccion = "Producto_ventas/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 703, 1,702,704,705,706};
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Producto_ventas/Ver.jsp";
        int id_producto = Integer.parseInt(request.getParameter("id_producto"));
        try {
            Producto_venta c = dao.obtenerProducto_venta(id_producto);
            request.setAttribute("producto", c);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 703, 1};
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Producto_ventas/Editar.jsp";
        int id_producto = Integer.parseInt(request.getParameter("id_producto"));
        Producto_venta ds = dao.obtenerProducto_venta(id_producto);
    
        request.setAttribute("producto", ds);
        request.setAttribute("inventarios", dao_inv.obtenerInventario_PTs());        
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        int resultado = 0;
        String redireccion = "Producto_ventas/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 703, 1};
        validarPermisos(permisos, listaPermisos);
        try {
            Producto_venta producto_nuevo = construirObjeto(request);
            resultado = dao.insertarProducto_venta(producto_nuevo);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(producto_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PRODUCTOS_VENTA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado != 0) {
            redireccion = "Producto_ventas/index.jsp";
            List<Producto_venta> productos = dao.obtenerProductos_venta();
            request.setAttribute("listaProductos", productos);
            request.setAttribute("mensaje", helper.mensajeDeExito("Producto de Venta agregado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Producto_ventas/Editar.jsp";
        int[] permisos = {701, 703, 1};
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        try {
            Producto_venta producto_nuevo = construirObjeto(request);
            
            resultado = dao.editarProducto_venta(producto_nuevo);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(producto_nuevo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PRODUCTOS_VENTA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Producto_ventas/index.jsp";
            List<Producto_venta> productos = dao.obtenerProductos_venta();
            request.setAttribute("listaProductos", productos);
            request.setAttribute("mensaje", helper.mensajeDeExito("Producto de Venta editado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Producto_ventas/index.jsp";
        int[] permisos = {701, 703, 1};
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String id_producto = request.getParameter("id_producto"); 
        try {
            Producto_venta producto_a_eliminar = dao.obtenerProducto_venta(Integer.parseInt(id_producto));
            
            resultado = dao.eliminarProducto_venta(producto_a_eliminar.getId_producto());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(producto_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PRODUCTOS_VENTA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Producto_ventas/index.jsp";
            List<Producto_venta> productos = dao.obtenerProductos_venta();
            request.setAttribute("listaProductos", productos);
            request.setAttribute("mensaje", helper.mensajeDeExito("Producto de Venta eliminado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Imposible eliminar el producto: El producto a eliminar tiene Solicitudes o Intenciones de Venta relacionadas."));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Métodos de los Modelos">
    private Producto_venta construirObjeto(HttpServletRequest request) throws SIGIPROException, ParseException {
        Producto_venta producto = new Producto_venta();
        producto.setId_producto(Integer.parseInt(request.getParameter("id_producto")));
        producto.setNombre(request.getParameter("nombre"));
        producto.setDescripcion(request.getParameter("descripcion"));
        producto.setLote(request.getParameter("lote"));
        return producto;
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
