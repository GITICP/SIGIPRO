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

import com.icp.sigipro.ventas.dao.Intencion_ventaDAO;
import com.icp.sigipro.ventas.modelos.Intencion_venta;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.ventas.dao.ClienteDAO;
import com.icp.sigipro.ventas.dao.Producto_IntencionDAO;
import com.icp.sigipro.ventas.dao.Producto_ventaDAO;
import com.icp.sigipro.ventas.modelos.Producto_Intencion;
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
@WebServlet(name = "ControladorIntencion_venta", urlPatterns = {"/Ventas/IntencionVenta"})
public class ControladorIntencion_venta extends SIGIPROServlet {

    private final int[] permisos = {701, 702, 1};
    private final Intencion_ventaDAO dao = new Intencion_ventaDAO();
    private final Producto_ventaDAO pdao = new Producto_ventaDAO();
    private final Producto_IntencionDAO idao = new Producto_IntencionDAO();
    private final ClienteDAO cdao = new ClienteDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();

    protected final Class clase = ControladorIntencion_venta.class;
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
       
        String redireccion = "IntencionVenta/Agregar.jsp";
        Intencion_venta ds = new Intencion_venta();
        
        List<String> estados = new ArrayList<String>();
        estados.add("En proceso");
        estados.add("Aprobado");
        estados.add("Anulado");
        estados.add("Completado");
        
        List<Producto_venta> productos = pdao.obtenerProductos_venta();
        
        request.setAttribute("intencion", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("productos", productos);
        request.setAttribute("estados", estados);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        List<Intencion_venta> intenciones = dao.obtenerIntenciones_venta();
        request.setAttribute("listaIntenciones", intenciones);
        String redireccion = "IntencionVenta/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "IntencionVenta/Ver.jsp";
        int id_intencion = Integer.parseInt(request.getParameter("id_intencion"));
        try {
            Intencion_venta c = dao.obtenerIntencion_venta(id_intencion);
            List<Producto_Intencion> d = idao.obtenerProductosIntencion(id_intencion);
            request.setAttribute("productos_intencion", d);
            request.setAttribute("intencion", c);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "IntencionVenta/Editar.jsp";
        int id_intencion = Integer.parseInt(request.getParameter("id_intencion"));
        Intencion_venta ds = dao.obtenerIntencion_venta(id_intencion);
        List<Producto_Intencion> d = idao.obtenerProductosIntencion(id_intencion);
        
        List<String> estados = new ArrayList<String>();
        estados.add("En proceso");
        estados.add("Aprobado");
        estados.add("Anulado");
        estados.add("Completado");
        
        request.setAttribute("productos_intencion", d);
        request.setAttribute("intencion", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("productos", pdao.obtenerProductos_venta());
        request.setAttribute("estados", estados);
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        int resultado = 0;
        String redireccion = "IntencionVenta/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        try {
            Intencion_venta intencion_nuevo = construirObjeto(request);
            resultado = dao.insertarIntencion_venta(intencion_nuevo);
            
            String productos_intencion = request.getParameter("listaProductos");
        
            if (productos_intencion != null && !(productos_intencion.isEmpty()) ) {
                List<Producto_Intencion> p_i = idao.parsearProductos(productos_intencion, resultado);
                for (Producto_Intencion i : p_i) {
                    idao.insertarProducto_Intencion(i);
                }
            }
            
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(intencion_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_INTENCION_VENTA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado != 0){
            redireccion = "IntencionVenta/index.jsp";
            List<Intencion_venta> intenciones = dao.obtenerIntenciones_venta();
            request.setAttribute("listaIntenciones", intenciones);
            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud o Intención de Venta agregado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        boolean e = false;
        String redireccion = "IntencionVenta/Editar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        try {
            Intencion_venta intencion_nuevo = construirObjeto(request);
            
            resultado = dao.editarIntencion_venta(intencion_nuevo);
            
            String productos_intencion = request.getParameter("listaProductos");
        
            if (productos_intencion != null && !(productos_intencion.isEmpty()) ) {
                List<Producto_Intencion> p_i = idao.parsearProductos(productos_intencion, Integer.parseInt(request.getParameter("id_intencion")));
                for (Producto_Intencion i : p_i) {
                    if (!idao.esProductoIntencion(i.getProducto().getId_producto(), Integer.parseInt(request.getParameter("id_intencion")))){
                        idao.insertarProducto_Intencion(i);
                        //System.out.println("Producto ingresado.");
                        e = true;
                    }
                    else{
                        e = idao.editarProducto_Intencion(i);
                        //System.out.println("Producto editado.");
                    }
                }
                idao.asegurarProductos_Intencion(p_i, Integer.parseInt(request.getParameter("id_intencion")));
            }
            else{
                idao.eliminarProductos_Intencion(Integer.parseInt(request.getParameter("id_intencion")));
            }
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(intencion_nuevo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_INTENCION_VENTA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "IntencionVenta/index.jsp";
            List<Intencion_venta> intenciones = dao.obtenerIntenciones_venta();
            request.setAttribute("listaIntenciones", intenciones);
            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud o Intención de Venta editado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        boolean resultado2 = false;
        String redireccion = "IntencionVenta/index.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String id_intencion = request.getParameter("id_intencion"); 
        try {
            Intencion_venta intencion_a_eliminar = dao.obtenerIntencion_venta(Integer.parseInt(id_intencion));
            
            resultado = dao.eliminarIntencion_venta(intencion_a_eliminar.getId_intencion());
            resultado2 = idao.eliminarProductos_Intencion(intencion_a_eliminar.getId_intencion());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(intencion_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_INTENCION_VENTA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "IntencionVenta/index.jsp";
            List<Intencion_venta> intenciones = dao.obtenerIntenciones_venta();
            request.setAttribute("listaIntenciones", intenciones);
            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud o Intención de Venta eliminada correctamente"));
        } else {
            redireccion = "IntencionVenta/index.jsp";
            List<Intencion_venta> intenciones = dao.obtenerIntenciones_venta();
            request.setAttribute("listaIntenciones", intenciones);
            request.setAttribute("mensaje", helper.mensajeDeError("Imposible eliminar la Solicitud o Intención de Venta debido a que tiene relaciones con otras secciones."));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Método del Modelo">
    private Intencion_venta construirObjeto(HttpServletRequest request) throws SIGIPROException, ParseException {
        Intencion_venta intencion = new Intencion_venta();
        
        intencion.setId_intencion(Integer.parseInt(request.getParameter("id_intencion")));
        intencion.setEstado(request.getParameter("estado"));
        intencion.setCliente(cdao.obtenerCliente(Integer.parseInt(request.getParameter("id_cliente"))));
        intencion.setObservaciones(request.getParameter("observaciones"));
        
        return intencion;
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
