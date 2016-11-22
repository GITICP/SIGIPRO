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

import com.icp.sigipro.ventas.dao.FacturaDAO;
import com.icp.sigipro.ventas.modelos.Factura;

import com.icp.sigipro.ventas.dao.PagoDAO;
import com.icp.sigipro.ventas.modelos.Pago;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
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
@WebServlet(name = "ControladorPago", urlPatterns = {"/Ventas/Pago"})
public class ControladorPago extends SIGIPROServlet {

    private final PagoDAO dao = new PagoDAO();
    private final FacturaDAO fdao = new FacturaDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();

    protected final Class clase = ControladorPago.class;
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
    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1,702,703,704,705,706};
        validarPermisos(permisos, listaPermisos);
        /*
        ControladorFactura cf = new ControladorFactura();
        try{
            cf.ActualizarEstadosYPagos();
        }
        catch (SIGIPROException e){
        }
        */
        String redireccion = "Pago/Ver.jsp";
        int id_pago = Integer.parseInt(request.getParameter("id_pago"));
        try {
            Pago c = dao.obtenerPago(id_pago);
            request.setAttribute("pago", c);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        /*
        ControladorFactura cf = new ControladorFactura();
        try{
            cf.ActualizarEstadosYPagos();
        }
        catch (SIGIPROException e){
        }
        */
        String redireccion = "Pago/index.jsp";
        List<Pago> listaPagos = dao.obtenerPagos();
        request.setAttribute("listaPagos", listaPagos);
        
        redireccionar(request, response, redireccion);
    }
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
       
        String redireccion = "Pago/Agregar.jsp";
        Pago ds = new Pago();
        List<Factura> facturas = fdao.obtenerFacturas();
        List<Factura> facturasOAF = new ArrayList<Factura>();
        for (Factura f:facturas){
            if (f.getTipo().equals("UCR")){
                facturasOAF.add(f);
            }
        }
        
        request.setAttribute("pago", ds);
        request.setAttribute("facturas", facturasOAF);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Pago/Editar.jsp";
        int id_pago = Integer.parseInt(request.getParameter("id_pago"));
        Pago ds = dao.obtenerPago(id_pago);
        List<Factura> facturas = fdao.obtenerFacturas();
        List<Factura> facturasOAF = new ArrayList<Factura>();
        for (Factura f:facturas){
            if (f.getTipo().equals("UCR")){
                facturasOAF.add(f);
            }
        }
        request.setAttribute("pago", ds);
        request.setAttribute("facturas", facturasOAF);
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
     protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        int resultado = 0;
        String redireccion = "Pago/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
        try {
            Pago pago_nuevo = construirObjeto(request);
            
            resultado = dao.insertarPago(pago_nuevo);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(pago_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PAGO, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado != 0) {
            redireccion = "Pago/index.jsp";
            List<Pago> pagos = dao.obtenerPagos();
            request.setAttribute("listaPagos", pagos);
            request.setAttribute("mensaje", helper.mensajeDeExito("Pago agregado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Pago/Editar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
        Pago pago_a_editar = dao.obtenerPago(Integer.parseInt(request.getParameter("id_pago")));
        try {
            Pago pago_nuevo = construirObjeto(request);
            pago_nuevo.setId_pago(pago_a_editar.getId_pago());
            resultado = dao.editarPago(pago_nuevo);
            
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(pago_nuevo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PAGO, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Pago/index.jsp";
            List<Pago> pagos = dao.obtenerPagos();
            request.setAttribute("listaPagos", pagos);
            request.setAttribute("mensaje", helper.mensajeDeExito("Pago editado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Pago/index.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
        
        try {
            Pago pago_a_eliminar = dao.obtenerPago(Integer.parseInt(request.getParameter("id_pago")));
            Factura f = pago_a_eliminar.getFactura();
            f.setMonto_pendiente((int) (f.getMonto_pendiente()+ pago_a_eliminar.getMonto()));
            fdao.actualizarMontoPendiente(f, (float)f.getMonto_pendiente());
            resultado = dao.eliminarPago(pago_a_eliminar.getId_pago());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(pago_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PAGO, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Pago/index.jsp";
            List<Pago> pagos = dao.obtenerPagos();
            request.setAttribute("listaPagos", pagos);
            request.setAttribute("mensaje", helper.mensajeDeExito("Pago eliminado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos de los Modelos">
    private Pago construirObjeto(HttpServletRequest request) throws SIGIPROException, ParseException {
        Pago pago = new Pago();
        //pago.setId_pago(Integer.parseInt(request.getParameter("id_pago")));
        pago.setMonto(Float.parseFloat(request.getParameter("pago")));
        pago.setCodigo(0);
        pago.setCodigo_remision(0);
        pago.setConsecutive("");
        pago.setConsecutive_remision("");
        pago.setNota("");
        String pattern = "dd/MM/yyyy";
        String dateInString =new SimpleDateFormat(pattern).format(new java.util.Date());
        pago.setFecha(dateInString);
        pago.setFecha_remision("");
        Factura f = fdao.obtenerFactura(Integer.parseInt(request.getParameter("id_factura")));
        fdao.actualizarMontoPendiente(f, Float.parseFloat(request.getParameter("monto_pendiente")));
        pago.setMoneda(f.getMoneda());
        pago.setFactura(f);
        
        return pago;
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
