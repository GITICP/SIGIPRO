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

import com.icp.sigipro.ventas.dao.CotizacionDAO;
import com.icp.sigipro.ventas.modelos.Cotizacion;

import com.icp.sigipro.ventas.dao.Intencion_ventaDAO;
import com.icp.sigipro.ventas.modelos.Intencion_venta;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.ventas.dao.ClienteDAO;
import com.icp.sigipro.ventas.dao.Producto_CotizacionDAO;
import com.icp.sigipro.ventas.dao.Producto_ventaDAO;
import com.icp.sigipro.ventas.modelos.Producto_Cotizacion;
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
@WebServlet(name = "ControladorCotizacion", urlPatterns = {"/Ventas/Cotizacion"})
public class ControladorCotizacion extends SIGIPROServlet {

    private final int[] permisos = {701, 702, 1};
    private final CotizacionDAO dao = new CotizacionDAO();
    private final Intencion_ventaDAO ivdao = new Intencion_ventaDAO();
    private final Producto_ventaDAO pdao = new Producto_ventaDAO();
    private final Producto_CotizacionDAO idao = new Producto_CotizacionDAO();
    private final ClienteDAO cdao = new ClienteDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();

    protected final Class clase = ControladorCotizacion.class;
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
       
        String redireccion = "Cotizacion/Agregar.jsp";
        Cotizacion ds = new Cotizacion();
        
        List<Producto_venta> productos = pdao.obtenerProductos_venta();
        List<Intencion_venta> intenciones = ivdao.obtenerIntenciones_venta();
        
        request.setAttribute("intenciones", intenciones);
        request.setAttribute("cotizacion", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("productos", productos);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        List<Cotizacion> cotizaciones = dao.obtenerCotizaciones();
        request.setAttribute("listaCotizaciones", cotizaciones);
        String redireccion = "Cotizacion/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Cotizacion/Ver.jsp";
        int id_cotizacion = Integer.parseInt(request.getParameter("id_cotizacion"));
        try {
            Cotizacion c = dao.obtenerCotizacion(id_cotizacion);
            List<Producto_Cotizacion> d = idao.obtenerProductosCotizacion(id_cotizacion);
            List<Intencion_venta> intenciones = ivdao.obtenerIntenciones_venta();
        
            request.setAttribute("intenciones", intenciones);
            request.setAttribute("productos_cotizacion", d);
            request.setAttribute("cotizacion", c);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Cotizacion/Editar.jsp";
        int id_cotizacion = Integer.parseInt(request.getParameter("id_cotizacion"));
        Cotizacion ds = dao.obtenerCotizacion(id_cotizacion);
        List<Producto_Cotizacion> d = idao.obtenerProductosCotizacion(id_cotizacion);
        List<Intencion_venta> intenciones = ivdao.obtenerIntenciones_venta();
        
        request.setAttribute("intenciones", intenciones);
        request.setAttribute("productos_cotizacion", d);
        request.setAttribute("cotizacion", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("productos", pdao.obtenerProductos_venta());
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        int resultado = 0;
        String redireccion = "Cotizacion/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        try {
            Cotizacion cotizacion_nuevo = construirObjeto(request);
            if (cotizacion_nuevo.getIntencion().getId_intencion() == 0){
                resultado = dao.insertarCotizacionSinIntencion(cotizacion_nuevo);
            }
            else{
                resultado = dao.insertarCotizacion(cotizacion_nuevo);
            }
            
            String productos_cotizacion = request.getParameter("listaProductos");
        
            if (productos_cotizacion != null && !(productos_cotizacion.isEmpty()) ) {
                List<Producto_Cotizacion> p_i = idao.parsearProductos(productos_cotizacion, resultado);
                for (Producto_Cotizacion i : p_i) {
                    idao.insertarProducto_Cotizacion(i);
                }
            }
            
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(cotizacion_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_COTIZACION, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado != 0){
            redireccion = "Cotizacion/index.jsp";
            List<Cotizacion> cotizaciones = dao.obtenerCotizaciones();
            request.setAttribute("listaCotizaciones", cotizaciones);
            request.setAttribute("mensaje", helper.mensajeDeExito("Cotización agregada correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        boolean e = false;
        String redireccion = "Cotizacion/Editar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        try {
            Cotizacion cotizacion_nuevo = construirObjeto(request);
            
            resultado = dao.editarCotizacion(cotizacion_nuevo);
            
            String productos_cotizacion = request.getParameter("listaProductos");
        
            if (productos_cotizacion != null && !(productos_cotizacion.isEmpty()) ) {
                List<Producto_Cotizacion> p_i = idao.parsearProductos(productos_cotizacion, Integer.parseInt(request.getParameter("id_cotizacion")));
                for (Producto_Cotizacion i : p_i) {
                    if (!idao.esProductoCotizacion(i.getProducto().getId_producto(), Integer.parseInt(request.getParameter("id_cotizacion")))){
                        idao.insertarProducto_Cotizacion(i);
                        //System.out.println("Producto ingresado.");
                        e = true;
                    }
                    else{
                        e = idao.editarProducto_Cotizacion(i);
                        //System.out.println("Producto editado.");
                    }
                }
                idao.asegurarProductos_Cotizacion(p_i, Integer.parseInt(request.getParameter("id_cotizacion")));
            }
            else{
                idao.eliminarProductos_Cotizacion(Integer.parseInt(request.getParameter("id_cotizacion")));
            }
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(cotizacion_nuevo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_COTIZACION, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Cotizacion/index.jsp";
            List<Cotizacion> cotizaciones = dao.obtenerCotizaciones();
            request.setAttribute("listaCotizaciones", cotizaciones);
            request.setAttribute("mensaje", helper.mensajeDeExito("Cotización editada correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        boolean resultado2 = false;
        String redireccion = "Cotizacion/index.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String id_cotizacion = request.getParameter("id_cotizacion"); 
        try {
            Cotizacion cotizacion_a_eliminar = dao.obtenerCotizacion(Integer.parseInt(id_cotizacion));
            
            resultado = dao.eliminarCotizacion(cotizacion_a_eliminar.getId_cotizacion());
            resultado2 = idao.eliminarProductos_Cotizacion(cotizacion_a_eliminar.getId_cotizacion());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(cotizacion_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_COTIZACION, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Cotizacion/index.jsp";
            List<Cotizacion> cotizaciones = dao.obtenerCotizaciones();
            request.setAttribute("listaCotizaciones", cotizaciones);
            request.setAttribute("mensaje", helper.mensajeDeExito("Cotización eliminada correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Método del Modelo">
    private Cotizacion construirObjeto(HttpServletRequest request) throws SIGIPROException, ParseException {
        Cotizacion cotizacion = new Cotizacion();
        
        cotizacion.setId_cotizacion(Integer.parseInt(request.getParameter("id_cotizacion")));
        cotizacion.setCliente(cdao.obtenerCliente(Integer.parseInt(request.getParameter("id_cliente"))));
        if (!request.getParameter("id_intencion").equals("")){
            cotizacion.setIntencion(ivdao.obtenerIntencion_venta(Integer.parseInt(request.getParameter("id_intencion"))));
        }
        else{
            Intencion_venta i = new Intencion_venta();
            i.setId_intencion(0);
            cotizacion.setIntencion(i);
        }
        cotizacion.setTotal(Integer.parseInt(request.getParameter("total")));
        cotizacion.setFlete(Integer.parseInt(request.getParameter("flete")));
        
        return cotizacion;
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
