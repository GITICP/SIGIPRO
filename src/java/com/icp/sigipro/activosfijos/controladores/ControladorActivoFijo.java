/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.activosfijos.controladores;

import com.icp.sigipro.activosfijos.dao.ActivoFijoDAO;
import com.icp.sigipro.activosfijos.dao.UbicacionDAO;
import com.icp.sigipro.activosfijos.modelos.ActivoFijo;
import com.icp.sigipro.activosfijos.modelos.Ubicacion;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.configuracion.dao.SeccionDAO;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.security.sasl.AuthenticationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Walter
 */
@WebServlet(name = "ControladorActivoFijo", urlPatterns = {"/ActivosFijos/Activos"})
public class ControladorActivoFijo extends SIGIPROServlet
{

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    try {
      List<String> estados = new ArrayList<String>();
      estados.add("Normal");
      estados.add("Reubicado");
      estados.add("Desechado");
      estados.add("Por Reubicar");
      estados.add("Por Desechar");
      String redireccion = "";
      String accion = request.getParameter("accion");
      SeccionDAO sec = new SeccionDAO();
      List<Seccion> secciones = sec.obtenerSecciones();
      UbicacionDAO ubi = new UbicacionDAO();
      List<Ubicacion> ubicaciones = ubi.obtenerUbicaciones();
      ActivoFijoDAO dao = new ActivoFijoDAO();
      HttpSession sesion = request.getSession();
      List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
      int[] permisos = {31, 32, 33};

      if (accion != null) {
        if (accion.equalsIgnoreCase("ver")) {
          validarPermisos(permisos, listaPermisos);
          redireccion = "Activos/Ver.jsp";
          int id_activo_fijo = Integer.parseInt(request.getParameter("id_activo_fijo"));
          ActivoFijo activofijo = dao.obtenerActivoFijo(id_activo_fijo);
          request.setAttribute("activofijo", activofijo);
        }
        else if (accion.equalsIgnoreCase("agregar")) {
          validarPermiso(31, listaPermisos);
          redireccion = "Activos/Agregar.jsp";
          ActivoFijo activofijo = new ActivoFijo();
          request.setAttribute("secciones", secciones);
          request.setAttribute("ubicaciones", ubicaciones);
          request.setAttribute("activofijo", activofijo);
          request.setAttribute("accion", "Agregar");
          request.setAttribute("estados", estados);

        }
        else if (accion.equalsIgnoreCase("eliminar")) {
            validarPermiso(33, listaPermisos);
            int id_activo_fijo = Integer.parseInt(request.getParameter("id_activo_fijo"));
            dao.eliminarActivoFijo(id_activo_fijo);

            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_activo_fijo,Bitacora.ACCION_ELIMINAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_ACTIVOFIJO,request.getRemoteAddr());
            //*----------------------------*

            redireccion = "Activos/index.jsp";
            List<ActivoFijo> activosfijos = dao.obtenerActivosFijos();
            request.setAttribute("listaActivosFijos", activosfijos);
        }
        else if (accion.equalsIgnoreCase("editar")) {
          validarPermiso(32, listaPermisos);
          redireccion = "Activos/Editar.jsp";
          int id_activo_fijo = Integer.parseInt(request.getParameter("id_activo_fijo"));
          ActivoFijo activofijo = dao.obtenerActivoFijo(id_activo_fijo);
          request.setAttribute("secciones", secciones);
          request.setAttribute("ubicaciones", ubicaciones);
          request.setAttribute("activofijo", activofijo);
          request.setAttribute("accion", "Editar");
          request.setAttribute("estados", estados);
        }
        else {
          validarPermisos(permisos, listaPermisos);
          redireccion = "Activos/index.jsp";
          List<ActivoFijo> activosfijos = dao.obtenerActivosFijos();
          request.setAttribute("listaActivosFijos", activosfijos);
        }
      }
      else {
        validarPermisos(permisos, listaPermisos);
        redireccion = "Activos/index.jsp";
        List<ActivoFijo> activosfijos = dao.obtenerActivosFijos();
        request.setAttribute("listaActivosFijos", activosfijos);
      }

      RequestDispatcher vista = request.getRequestDispatcher(redireccion);
      vista.forward(request, response);
    }
    catch (AuthenticationException ex) {
      RequestDispatcher vista = request.getRequestDispatcher("/index.jsp");
      vista.forward(request, response);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    request.setCharacterEncoding("UTF-8");
    boolean resultado = false;
    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

    ActivoFijo activofijo = new ActivoFijo();

    activofijo.setPlaca(request.getParameter("placa"));
    activofijo.setEquipo(request.getParameter("equipo"));
    activofijo.setMarca(request.getParameter("marca"));

    activofijo.setId_seccion(Integer.parseInt(request.getParameter("seccion")));
    activofijo.setId_ubicacion(Integer.parseInt(request.getParameter("ubicacion")));

    activofijo.setEstado(request.getParameter("estado"));

    try {
      SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

      String fechamovimiento = request.getParameter("fecha_movimiento");
      java.util.Date fMovimiento = formatoFecha.parse(fechamovimiento);

      String fecharegistro = request.getParameter("fecha_registro");
      java.util.Date fRegistro = formatoFecha.parse(fecharegistro);

      java.sql.Date fMovimientoSQL = new java.sql.Date(fMovimiento.getTime());
      java.sql.Date fRegistroSQL = new java.sql.Date(fRegistro.getTime());

      activofijo.setFecha_movimiento(fMovimientoSQL);
      activofijo.setFecha_registro(fRegistroSQL);
    } catch (ParseException ex){
      ex.printStackTrace();
    }

    ActivoFijoDAO dao = new ActivoFijoDAO();
    String id = request.getParameter("id_activo_fijo");
    String redireccion;

    if (id.isEmpty() || id.equals("0")) {
      resultado = dao.insertarActivoFijo(activofijo);
      
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(activofijo.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_ACTIVOFIJO,request.getRemoteAddr());
      //*----------------------------*
      
      redireccion = "Activos/Agregar.jsp";
      request.setAttribute("mensaje", helper.mensajeDeExito("Activo Fijo ingresado correctamente"));
    }
    else {
      activofijo.setId_activo_fijo(Integer.parseInt(id));

      resultado = dao.editarActivoFijo(activofijo);
      
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(activofijo.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_ACTIVOFIJO,request.getRemoteAddr());
      //*----------------------------*
      
      redireccion = "Activos/index.jsp";
      request.setAttribute("mensaje", helper.mensajeDeExito("Activo Fijo editado correctamente"));
    }

    if (resultado) {
      request.setAttribute("activofijo", activofijo);
      redireccion = String.format("Activos/index.jsp", id);
    }
    else {
      request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
    }

    request.setAttribute("activofijo", activofijo);
    RequestDispatcher vista = request.getRequestDispatcher(redireccion);
    vista.forward(request, response);
  }

  @Override
  public String getServletInfo()
  {
    return "Short description";
  }

  @Override
  protected int getPermiso()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
