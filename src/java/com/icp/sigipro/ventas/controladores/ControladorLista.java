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

import com.icp.sigipro.ventas.dao.ListaDAO;
import com.icp.sigipro.ventas.modelos.Lista;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.ventas.dao.ClienteDAO;
import com.icp.sigipro.ventas.dao.Producto_ventaDAO;
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
@WebServlet(name = "ControladorLista", urlPatterns = {"/Ventas/ListaEspera"})
public class ControladorLista extends SIGIPROServlet {

    private final ListaDAO dao = new ListaDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();
    private final ClienteDAO cdao = new ClienteDAO();
    private final Producto_ventaDAO pdao = new Producto_ventaDAO();

    protected final Class clase = ControladorLista.class;
    protected final List<String> productosGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
        }
    };
    protected final List<String> productosPost = new ArrayList<String>() {
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
       
        String redireccion = "ListaEspera/Agregar.jsp";
        Lista ds = new Lista();
        
        request.setAttribute("lista", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("productos", pdao.obtenerProductos_venta());
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 702,703,704,705,706,1};
        validarPermisos(permisos, listaPermisos);

        List<Lista> listas = dao.obtenerListas();
        request.setAttribute("listaListas", listas);
        String redireccion = "ListaEspera/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1,702,703,704,705,706};
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "ListaEspera/Ver.jsp";
        int id_lista = Integer.parseInt(request.getParameter("id_lista"));
        try {
            Lista c = dao.obtenerLista(id_lista);
            request.setAttribute("lista", c);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "ListaEspera/Editar.jsp";
        int id_lista = Integer.parseInt(request.getParameter("id_lista"));
        Lista ds = dao.obtenerLista(id_lista);
        
        request.setAttribute("lista", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("productos", pdao.obtenerProductos_venta());
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        int resultado = 0;
        String redireccion = "ListaEspera/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
        try {
            Lista lista_nuevo = construirObjeto(request);
            
            if (lista_nuevo.getFecha_atencion_S().equals("")){
                if (lista_nuevo.getCliente() != null){
                    resultado = dao.insertarListaFechaA0(lista_nuevo);
                }
                else{
                    resultado = dao.insertarListaClienteUnknownFechaA0(lista_nuevo);
                }
            }
            else{
                if (lista_nuevo.getCliente() != null){
                    resultado = dao.insertarLista(lista_nuevo);
                }
                else{
                    resultado = dao.insertarListaClienteUnknown(lista_nuevo);
                }
            }
            
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(lista_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_LISTA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado != 0){
            redireccion = "ListaEspera/index.jsp";
            List<Lista> listas = dao.obtenerListas();
            request.setAttribute("listaListas", listas);
            request.setAttribute("mensaje", helper.mensajeDeExito("Lista agregada correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "ListaEspera/Editar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
        
        try {
            Lista lista_nuevo = construirObjeto(request);
            
            if (lista_nuevo.getFecha_atencion_S().equals("")){
                if (lista_nuevo.getCliente() != null){
                    resultado = dao.editarListaFechaA0(lista_nuevo);
                }
                else{
                    resultado = dao.editarListaClienteUnknownFechaA0(lista_nuevo);
                }
            }
            else{
                if (lista_nuevo.getCliente() != null){
                    resultado = dao.editarLista(lista_nuevo);
                }
                else{
                    resultado = dao.editarListaClienteUnknown(lista_nuevo);
                }
            }
            
            
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(lista_nuevo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_LISTA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "ListaEspera/index.jsp";
            List<Lista> listas = dao.obtenerListas();
            request.setAttribute("listaListas", listas);
            request.setAttribute("mensaje", helper.mensajeDeExito("Lista editada correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "ListaEspera/index.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
        String id_lista1 = request.getParameter("id_lista"); 
        int id_lista = Integer.parseInt(id_lista1);
        try {
            Lista lista_a_eliminar = dao.obtenerLista(id_lista);
            
            resultado = dao.eliminarLista(lista_a_eliminar.getId_lista());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(lista_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_LISTA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "ListaEspera/index.jsp";
            List<Lista> listas = dao.obtenerListas();
            request.setAttribute("listaListas", listas);
            request.setAttribute("mensaje", helper.mensajeDeExito("Lista eliminada correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Método del Modelo">
    private Lista construirObjeto(HttpServletRequest request) throws SIGIPROException, ParseException {
        Lista lista = new Lista();
        lista.setId_lista(Integer.parseInt(request.getParameter("id_lista")));
        int id_cliente = Integer.parseInt(request.getParameter("id_cliente"));
        if (id_cliente != 0){
            lista.setCliente(cdao.obtenerCliente(id_cliente));
        }
        else{
            lista.setNombre_cliente(request.getParameter("nombre_cliente"));
            lista.setCorreo(request.getParameter("correo_electronico"));
            lista.setTelefono(request.getParameter("telefono"));
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date result = df.parse(request.getParameter("fecha_solicitud"));
        java.sql.Date fecha_solicitudSQL = new java.sql.Date(result.getTime());
        lista.setFecha_solicitud(fecha_solicitudSQL);
        if (!request.getParameter("fecha_atencion").equals("")){
            java.util.Date result2 = df.parse(request.getParameter("fecha_atencion"));
            java.sql.Date fecha_solicitudSQL2 = new java.sql.Date(result.getTime());
            lista.setFecha_atencion(fecha_solicitudSQL2);
        }
        return lista;
    }
    
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<String> lista_productos;
        if (accionHTTP.equals("get")) {
            lista_productos = productosGet;
        } else {
            lista_productos = productosPost;
        }
        if (lista_productos.contains(accion.toLowerCase())) {
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
