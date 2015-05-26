/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.controladores;

import com.icp.sigipro.bioterio.dao.ConejaDAO;
import com.icp.sigipro.bioterio.dao.CajaDAO;
import com.icp.sigipro.bioterio.modelos.Coneja;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorConejas", urlPatterns = {"/Conejera/Conejas"})
public class ControladorConejas extends SIGIPROServlet
{

    private final int[] permisos = {251, 1, 1};
    private final ConejaDAO dao = new ConejaDAO();
    private final CajaDAO caja_dao = new CajaDAO();
    private final HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

    protected final Class clase = ControladorConejas.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
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
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(251, listaPermisos);
        int id_caja = Integer.parseInt(request.getParameter("id_caja"));
        String redireccion = "Conejas/Agregar.jsp";
        Coneja ds = new Coneja();
        request.setAttribute("coneja", ds);
        request.setAttribute("id_caja", id_caja);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Conejas/Editar.jsp";
        int id_coneja = Integer.parseInt(request.getParameter("id_coneja"));
        request.setAttribute("accion", "Editar");
        try {
            Coneja s = dao.obtenerConeja(id_coneja, true);
            request.setAttribute("coneja", s);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }


  // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Conejas/Agregar.jsp";
        int id_caja = 0;
        try {
            Coneja coneja = construirObjeto(request);
            id_caja = coneja.getCaja().getId_caja();
            resultado = dao.insertarConeja(coneja);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(coneja.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------*
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {

            redireccion = "Cajas?accion=ver&mensaje=Hoja de Vida agregada correctamente&id_caja=" + id_caja;
        }
        else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        response.sendRedirect(redireccion);
        // redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Conejas/Editar.jsp";
        int id_caja = 0;
        try {
            Coneja coneja = construirObjeto(request);
            id_caja = coneja.getCaja().getId_caja();
            resultado = dao.editarConeja(coneja);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(coneja.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------*
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Cajas?accion=ver&mensaje=Hoja de Vida editada correctamente&id_caja=" + id_caja;
            request.setAttribute("mensaje", helper.mensajeDeExito("Coneja agregada con éxito"));
        }
        else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        response.sendRedirect(redireccion);
//redireccionar(request, response, redireccion);
    }
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Conejas/Agregar.jsp";
        int id_caja = 0;
        int id_coneja = Integer.parseInt(request.getParameter("id_coneja"));
        Coneja coneja;
        try {
            coneja = dao.obtenerConeja(id_coneja, true);
            id_caja = coneja.getCaja().getId_caja();
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            coneja = dao.obtenerConeja(id_coneja, true);
            java.util.Date fecha_cambio = new java.util.Date();
            java.sql.Date fecha_cambioSQL;
            fecha_cambioSQL = new java.sql.Date(fecha_cambio.getTime());
            coneja.setFecha_cambio(fecha_cambioSQL);
            coneja.setObservaciones(request.getParameter("observaciones"));
            resultado = dao.eliminarConeja(coneja);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_coneja, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------* 
           
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {

            redireccion = "Cajas?accion=ver&mensaje=Coneja retirada correctamente&id_caja=" + id_caja;
        }
        else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        response.sendRedirect(redireccion);
        // redireccionar(request, response, redireccion);
    }
  // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private Coneja construirObjeto(HttpServletRequest request) throws SIGIPROException
    {
        Coneja coneja = new Coneja();

        coneja.setId_coneja(Integer.parseInt(request.getParameter("id_coneja")));
        coneja.setCaja(caja_dao.obtenerCaja(Integer.parseInt(request.getParameter("id_caja"))));
        coneja.setId_padre(request.getParameter("id_padre"));
        coneja.setId_madre(request.getParameter("id_madre"));
        String fecha_nac = request.getParameter("fecha_nacimiento");
        String fecha_ret = request.getParameter("fecha_retiro");
        String fecha_ingr = request.getParameter("fecha_ingreso");
        String fecha_sel = request.getParameter("fecha_seleccion");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fecha_nacimiento;
        java.sql.Date fecha_nacimientoSQL;
        java.util.Date fecha_retiro;
        java.sql.Date fecha_retiroSQL;
        java.util.Date fecha_ingreso;
        java.sql.Date fecha_ingresoSQL;

        java.util.Date fecha_seleccion;
        java.sql.Date fecha_seleccionSQL;
        try {
            fecha_nacimiento = formatoFecha.parse(fecha_nac);
            fecha_nacimientoSQL = new java.sql.Date(fecha_nacimiento.getTime());
            fecha_retiro = formatoFecha.parse(fecha_ret);
            fecha_retiroSQL = new java.sql.Date(fecha_retiro.getTime());
            fecha_ingreso = formatoFecha.parse(fecha_ingr);
            fecha_ingresoSQL = new java.sql.Date(fecha_ingreso.getTime());
            fecha_seleccion = formatoFecha.parse(fecha_sel);
            fecha_seleccionSQL = new java.sql.Date(fecha_seleccion.getTime());
            coneja.setFecha_nacimiento(fecha_nacimientoSQL);
            coneja.setFecha_retiro(fecha_retiroSQL);
            coneja.setFecha_ingreso(fecha_ingresoSQL);
            coneja.setFecha_seleccion(fecha_seleccionSQL);
        }
        catch (ParseException ex) {
            Logger.getLogger(ControladorDestetes.class.getName()).log(Level.SEVERE, null, ex);
        }

        return coneja;
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
