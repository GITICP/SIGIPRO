/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.controladores;

import com.icp.sigipro.bioterio.dao.CepaDAO;
import com.icp.sigipro.bioterio.dao.PieDAO;
import com.icp.sigipro.bioterio.dao.PiexCepaDAO;
import com.icp.sigipro.bioterio.modelos.Cepa;
import com.icp.sigipro.bioterio.modelos.Pie;
import com.icp.sigipro.bioterio.modelos.PiexCepa;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelperFechas;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
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
@WebServlet(name = "ControladorPiexCepa", urlPatterns = {"/Ratonera/PiexCepa"})
public class ControladorPiexCepa extends SIGIPROServlet {
    private final int[] permisos = {209, 1, 1};
    private final PiexCepaDAO dao = new PiexCepaDAO();
    private final PieDAO piedao = new PieDAO();
    private final CepaDAO cepadao = new CepaDAO();
    private final HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

    protected final Class clase = ControladorPiexCepa.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregar");
            add("eliminar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Cepas/Ver.jsp";
        int id_cepa = Integer.parseInt(request.getParameter("id_cepa"));
        try {
            PiexCepa pie = construirObjeto(request);

            dao.insertarPiexCepa(pie);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(pie.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

            redireccion = "Cepas/Ver.jsp";
            request.setAttribute("mensaje", helper.mensajeDeExito("Pie asociado correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        try {
        Cepa s = cepadao.obtenerCepa(id_cepa);
        PiexCepa p = dao.obtenerPiexCepa(id_cepa);
        List<Pie> pies = piedao.obtenerPiesRestantes();
        request.setAttribute("pies", pies);
        request.setAttribute("cepa", s);
        request.setAttribute("pieasociado", p);

      } catch (Exception ex) {
        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
      }

        redireccionar(request, response, redireccion);
    }


    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        int id_cepa = Integer.parseInt(request.getParameter("id_cepa"));
        String redireccion = "Cepas/Ver.jsp";
        
        try {
            dao.eliminarPiexCepa(id_cepa);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_cepa, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

            request.setAttribute("mensaje", helper.mensajeDeExito("Pie retirado correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
       try {
        Cepa s = cepadao.obtenerCepa(id_cepa);
        PiexCepa p = dao.obtenerPiexCepa(id_cepa);
        List<Pie> pies = piedao.obtenerPiesRestantes();
        request.setAttribute("pies", pies);
        request.setAttribute("cepa", s);
        request.setAttribute("pieasociado", p);

      } catch (Exception ex) {
        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
      }
        redireccionar(request, response, redireccion);
    }

  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private PiexCepa construirObjeto(HttpServletRequest request) throws SIGIPROException
    {
        PiexCepa pie = new PiexCepa();

        int id_pie = Integer.parseInt(request.getParameter("id_pie"));
        int id_cepa = Integer.parseInt(request.getParameter("id_cepa"));
        Pie p = piedao.obtenerPie(id_pie);
        Cepa c = cepadao.obtenerCepa(id_cepa);
        pie.setPie(p);
        pie.setCepa(c);
        String fecha_ingreso_str = request.getParameter("fecha_inicio");
        String fecha_retiro_str = request.getParameter("fecha_retiro");

        try {
            HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
            pie.setFecha_inicio(helper_fechas.formatearFecha(fecha_ingreso_str));
            pie.setFecha_estimada_retiro(helper_fechas.formatearFecha(fecha_retiro_str));
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }

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
