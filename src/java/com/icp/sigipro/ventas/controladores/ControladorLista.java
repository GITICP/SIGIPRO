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
import com.icp.sigipro.ventas.dao.HistorialDAO;
import com.icp.sigipro.ventas.dao.Historial_listaDAO;
import com.icp.sigipro.ventas.dao.ObservacionDAO;
import com.icp.sigipro.ventas.dao.Observacion_listaDAO;
import com.icp.sigipro.ventas.dao.Producto_listaDAO;
import com.icp.sigipro.ventas.dao.Producto_ventaDAO;
import com.icp.sigipro.ventas.modelos.Historial;
import com.icp.sigipro.ventas.modelos.Historial_lista;
import com.icp.sigipro.ventas.modelos.Observacion;
import com.icp.sigipro.ventas.modelos.Observacion_lista;
import com.icp.sigipro.ventas.modelos.Producto_lista;
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

    private final int[] permisos = {701, 702, 1};
    private final ListaDAO dao = new ListaDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();
    private final ClienteDAO cdao = new ClienteDAO();
    private final Historial_listaDAO hdao = new Historial_listaDAO();
    private final HistorialDAO hisdao = new HistorialDAO();
    private final Observacion_listaDAO odao = new Observacion_listaDAO();
    private final ObservacionDAO obsdao = new ObservacionDAO();
    private final Producto_listaDAO pldao = new Producto_listaDAO();
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
        validarPermisos(permisos, listaPermisos);

        List<Lista> listas = dao.obtenerListas();
        request.setAttribute("listaListas", listas);
        String redireccion = "ListaEspera/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "ListaEspera/Ver.jsp";
        int id_lista = Integer.parseInt(request.getParameter("id_lista"));
        try {
            Lista c = dao.obtenerLista(id_lista);
            request.setAttribute("lista", c);
            request.setAttribute("productos_lista", pldao.obtenerProductosLista(id_lista));
            request.setAttribute("historiales_lista", hdao.obtenerHistorialesDeTratamiento(id_lista));
            request.setAttribute("observaciones_lista", odao.obtenerObservacionesDeTratamiento(id_lista));
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "ListaEspera/Editar.jsp";
        int id_lista = Integer.parseInt(request.getParameter("id_lista"));
        Lista ds = dao.obtenerLista(id_lista);
        
        request.setAttribute("lista", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("productos", pdao.obtenerProductos_venta());
        request.setAttribute("productos_lista", pldao.obtenerProductosLista(id_lista));
        request.setAttribute("historiales_lista", hdao.obtenerHistorialesDeTratamiento(id_lista));
        request.setAttribute("observaciones_lista", odao.obtenerObservacionesDeTratamiento(id_lista));
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        int resultado = 0;
        String redireccion = "ListaEspera/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        try {
            Lista lista_nuevo = construirObjeto(request);
            resultado = dao.insertarLista(lista_nuevo);
            
            String productos_lista = request.getParameter("listaProductos");
            //System.out.println("productos_lista = "+productos_lista);
            if (productos_lista != null && !(productos_lista.isEmpty()) ) {
                List<Producto_lista> p_i = pldao.parsearProductos(productos_lista, resultado);
                for (Producto_lista i : p_i) {
                    pldao.insertarProducto_lista(i);
                }
            }
            
            String observaciones_lista = request.getParameter("listaObservaciones");
            //System.out.println("observaciones_lista = "+observaciones_lista);
            if (observaciones_lista != null && !(observaciones_lista.isEmpty()) ) {
                List<Observacion> p_i = odao.parsearObservaciones(observaciones_lista, resultado);
                for (Observacion i : p_i) {
                    Observacion_lista a = new Observacion_lista();
                    a.setId_observacion(i.getId_observacion());
                    a.setId_lista(resultado);
                    odao.insertarObservacionTratamiento(a);
                }
            }
            
            String historiales_lista = request.getParameter("listaHistoriales");
            //System.out.println("historiales_lista = "+historiales_lista);
            if (historiales_lista != null && !(historiales_lista.isEmpty()) ) {
                List<Historial> p_i = hdao.parsearHistoriales(historiales_lista, resultado);
                for (Historial i : p_i) {
                    Historial_lista a = new Historial_lista();
                    a.setId_historial(i.getId_historial());
                    a.setId_lista(resultado);
                    hdao.insertarHistorialTratamiento(a);
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
        validarPermisos(permisos, listaPermisos);
        
        try {
            Lista lista_nuevo = construirObjeto(request);
            
            resultado = dao.editarLista(lista_nuevo);
            
            String productos_lista = request.getParameter("listaProductos");
            
            int id_lista = Integer.parseInt(request.getParameter("id_lista"));
            
            if (productos_lista != null && !(productos_lista.isEmpty()) ) {
                List<Producto_lista> p_i = pldao.parsearProductos(productos_lista, lista_nuevo.getId_lista());
                for (Producto_lista i : p_i) {
                    if(!pldao.esProductoLista(i.getProducto().getId_producto(), id_lista)){
                        pldao.insertarProducto_lista(i);
                    }
                    else{
                        pldao.editarProducto_lista(i);
                    }
                }
                pldao.asegurarProductos_Lista(p_i, id_lista);
            }
            
            String observaciones_lista = request.getParameter("listaObservaciones");
        
            if (observaciones_lista != null && !(observaciones_lista.isEmpty()) ) {
                List<Observacion> p_i = odao.parsearObservaciones(observaciones_lista, lista_nuevo.getId_lista());
                for (Observacion i : p_i) {
                    if(!odao.esObservacionTratamiento(id_lista, id_lista)){
                        obsdao.insertarObservacion(i);
                        Observacion_lista a = new Observacion_lista();
                        a.setId_observacion(i.getId_observacion());
                        a.setId_lista(lista_nuevo.getId_lista());
                        odao.insertarObservacionTratamiento(a);
                    }
                }
                odao.asegurarObservaciones_Tratamiento(p_i, id_lista);
            }
            
            String historiales_lista = request.getParameter("listaHistoriales");
        
            if (historiales_lista != null && !(historiales_lista.isEmpty()) ) {
                List<Historial> p_i = hdao.parsearHistoriales(historiales_lista, lista_nuevo.getId_lista());
                for (Historial i : p_i) {
                    if(!hdao.esHistorialTratamiento(id_lista, id_lista)){
                        hisdao.insertarHistorial(i);
                        Historial_lista a = new Historial_lista();
                        a.setId_historial(i.getId_historial());
                        a.setId_lista(lista_nuevo.getId_lista());
                        hdao.insertarHistorialTratamiento(a);
                    }
                }
                hdao.asegurarHistoriales_Tratamiento(p_i, id_lista);
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
        validarPermisos(permisos, listaPermisos);
        String id_lista1 = request.getParameter("id_lista"); 
        int id_lista = Integer.parseInt(id_lista1);
        try {
            Lista lista_a_eliminar = dao.obtenerLista(id_lista);
            
            resultado = dao.eliminarLista(lista_a_eliminar.getId_lista());
            pldao.eliminarProductos_Lista(id_lista);
            hdao.eliminarHistoriales_Tratamiento(id_lista);
            odao.eliminarObservaciones_Tratamiento(id_lista);
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
        lista.setCliente(cdao.obtenerCliente(Integer.parseInt(request.getParameter("id_cliente"))));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date result = df.parse(request.getParameter("fecha"));
        java.sql.Date fecha_solicitudSQL = new java.sql.Date(result.getTime());
        lista.setFecha_ingreso(fecha_solicitudSQL);
        lista.setPrioridad(Integer.parseInt(request.getParameter("prioridad")));
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
