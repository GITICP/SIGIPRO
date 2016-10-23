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
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
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
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos de los Modelos">
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
