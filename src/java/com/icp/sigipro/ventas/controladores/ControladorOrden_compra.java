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

import com.icp.sigipro.ventas.dao.Orden_compraDAO;
import com.icp.sigipro.ventas.modelos.Orden_compra;
import com.icp.sigipro.ventas.dao.CotizacionDAO;
import com.icp.sigipro.ventas.modelos.Cotizacion;
import com.icp.sigipro.ventas.dao.Orden_compraDAO;
import com.icp.sigipro.ventas.modelos.Orden_compra;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.ventas.dao.ClienteDAO;
import com.icp.sigipro.ventas.dao.Intencion_ventaDAO;
import com.icp.sigipro.ventas.dao.Producto_OrdenDAO;
import com.icp.sigipro.ventas.dao.Producto_ventaDAO;
import com.icp.sigipro.ventas.modelos.Intencion_venta;
import com.icp.sigipro.ventas.modelos.Producto_Orden;
import com.icp.sigipro.ventas.modelos.Producto_venta;
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
@WebServlet(name = "ControladorOrden_compra", urlPatterns = {"/Ventas/OrdenCompra"})
public class ControladorOrden_compra extends SIGIPROServlet {

    private final int[] permisos = {701, 702, 1};
    private final Orden_compraDAO dao = new Orden_compraDAO();
    private final Producto_ventaDAO pdao = new Producto_ventaDAO();
    private final Intencion_ventaDAO idao = new Intencion_ventaDAO();
    private final CotizacionDAO cotdao = new CotizacionDAO();
    private final Producto_OrdenDAO podao = new Producto_OrdenDAO();
    private final ClienteDAO cdao = new ClienteDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();

    protected final Class clase = ControladorOrden_compra.class;
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
       
        String redireccion = "OrdenCompra/Agregar.jsp";
        Orden_compra ds = new Orden_compra();
        
        List<String> estados = new ArrayList<String>();
        estados.add("En proceso");
        estados.add("Entregada");
        estados.add("Cancelada");
        
        List<Producto_venta> productos = pdao.obtenerProductos_venta();
        
        request.setAttribute("orden", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("cotizaciones", cotdao.obtenerCotizaciones());
        request.setAttribute("intenciones", idao.obtenerIntenciones_venta());
        request.setAttribute("productos", productos);
        request.setAttribute("estados", estados);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        List<Orden_compra> ordenes = dao.obtenerOrdenes_compra();
        request.setAttribute("listaOrdenes", ordenes);
        String redireccion = "OrdenCompra/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "OrdenCompra/Ver.jsp";
        int id_orden = Integer.parseInt(request.getParameter("id_orden"));
        try {
            Orden_compra c = dao.obtenerOrden_compra(id_orden);
            List<Producto_Orden> d = podao.obtenerProductosOrden(id_orden);
            request.setAttribute("productos_orden", d);
            request.setAttribute("orden", c);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "OrdenCompra/Editar.jsp";
        int id_orden = Integer.parseInt(request.getParameter("id_orden"));
        Orden_compra ds = dao.obtenerOrden_compra(id_orden);
        List<Producto_Orden> d = podao.obtenerProductosOrden(id_orden);
        
        List<String> estados = new ArrayList<String>();
        estados.add("En proceso");
        estados.add("Entregada");
        estados.add("Cancelada");
        
        request.setAttribute("productos_orden", d);
        request.setAttribute("orden", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("productos", pdao.obtenerProductos_venta());
        request.setAttribute("cotizaciones", cotdao.obtenerCotizaciones());
        request.setAttribute("intenciones", idao.obtenerIntenciones_venta());
        request.setAttribute("estados", estados);
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        int resultado = 0;
        String redireccion = "OrdenCompra/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        try {
            Orden_compra orden_nuevo = construirObjeto(request);
            
            if(orden_nuevo.getCotizacion().getId_cotizacion() == 0){
                if(orden_nuevo.getIntencion().getId_intencion() == 0){
                    resultado = dao.insertarOrden_compraAmbos0(orden_nuevo);
                }
                else{
                    resultado = dao.insertarOrden_compraCotizacion0(orden_nuevo);
                }
            }
            else{
                if(orden_nuevo.getIntencion().getId_intencion() == 0){
                    resultado = dao.insertarOrden_compraIntencion0(orden_nuevo);
                }
                else{
                    resultado = dao.insertarOrden_compra(orden_nuevo);
                }
            }
            
            String productos_orden = request.getParameter("listaProductos");
        
            if (productos_orden != null && !(productos_orden.isEmpty()) ) {
                List<Producto_Orden> p_i = podao.parsearProductos(productos_orden, resultado);
                for (Producto_Orden i : p_i) {
                    podao.insertarProducto_Orden(i);
                }
            }
            
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(orden_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ORDEN_COMPRA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado != 0){
            redireccion = "OrdenCompra/index.jsp";
            List<Orden_compra> ordenes = dao.obtenerOrdenes_compra();
            request.setAttribute("listaOrdenes", ordenes);
            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud o Intención de Venta agregado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        boolean e = false;
        String redireccion = "OrdenCompra/Editar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        try {
            Orden_compra orden_nuevo = construirObjeto(request);
            
            if(orden_nuevo.getCotizacion().getId_cotizacion() == 0){
                if(orden_nuevo.getIntencion().getId_intencion() == 0){
                    resultado = dao.editarOrden_compraAmbos0(orden_nuevo);
                }
                else{
                    resultado = dao.editarOrden_compraCotizacion0(orden_nuevo);
                }
            }
            else{
                if(orden_nuevo.getIntencion().getId_intencion() == 0){
                    resultado = dao.editarOrden_compraIntencion0(orden_nuevo);
                }
                else{
                    resultado = dao.editarOrden_compra(orden_nuevo);
                }
            }
            
            String productos_orden = request.getParameter("listaProductos");
        
            if (productos_orden != null && !(productos_orden.isEmpty()) ) {
                List<Producto_Orden> p_i = podao.parsearProductos(productos_orden, Integer.parseInt(request.getParameter("id_orden")));
                for (Producto_Orden i : p_i) {
                    if (!podao.esProductoOrden(i.getProducto().getId_producto(), Integer.parseInt(request.getParameter("id_orden")))){
                        podao.insertarProducto_Orden(i);
                        //System.out.println("Producto ingresado.");
                        e = true;
                    }
                    else{
                        e = podao.editarProducto_Orden(i);
                        //System.out.println("Producto editado.");
                    }
                }
                podao.asegurarProductos_Orden(p_i, Integer.parseInt(request.getParameter("id_orden")));
            }
            else{
                podao.eliminarProductos_Orden(Integer.parseInt(request.getParameter("id_orden")));
            }
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(orden_nuevo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ORDEN_COMPRA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "OrdenCompra/index.jsp";
            List<Orden_compra> ordenes = dao.obtenerOrdenes_compra();
            request.setAttribute("listaOrdenes", ordenes);
            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud o Intención de Venta editado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        boolean resultado2 = false;
        String redireccion = "OrdenCompra/index.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String id_orden = request.getParameter("id_orden"); 
        try {
            Orden_compra orden_a_eliminar = dao.obtenerOrden_compra(Integer.parseInt(id_orden));
            
            resultado = dao.eliminarOrden_compra(orden_a_eliminar.getId_orden());
            resultado2 = podao.eliminarProductos_Orden(orden_a_eliminar.getId_orden());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(orden_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ORDEN_COMPRA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "OrdenCompra/index.jsp";
            List<Orden_compra> ordenes = dao.obtenerOrdenes_compra();
            request.setAttribute("listaOrdenes", ordenes);
            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud o Intención de Venta eliminado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Método del Modelo">
    private Orden_compra construirObjeto(HttpServletRequest request) throws SIGIPROException, ParseException {
        Orden_compra orden = new Orden_compra();
        
        orden.setCliente(cdao.obtenerCliente(Integer.parseInt(request.getParameter("id_cliente"))));
        String cotizacion = request.getParameter("id_cotizacion");
        String intencion = request.getParameter("id_intencion");
        if(!cotizacion.equals("")){
            orden.setCotizacion(cotdao.obtenerCotizacion(Integer.parseInt(cotizacion)));
        }
        else{
            Cotizacion c = new Cotizacion();
            c.setId_cotizacion(0);
            orden.setCotizacion(c);
        }
        if(!intencion.equals("")){
            orden.setIntencion(idao.obtenerIntencion_venta(Integer.parseInt(intencion)));
        }
        else{
            Intencion_venta iv = new Intencion_venta();
            iv.setId_intencion(0);
            orden.setIntencion(iv);
        }
        orden.setRotulacion(request.getParameter("rotulacion"));
        orden.setEstado(request.getParameter("estado"));
        
        return orden;
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
