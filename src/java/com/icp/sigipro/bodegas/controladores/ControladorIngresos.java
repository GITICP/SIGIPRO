/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.controladores;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bodegas.dao.IngresoDAO;
import com.icp.sigipro.bodegas.dao.ProductoInternoDAO;
import com.icp.sigipro.bodegas.modelos.Ingreso;
import com.icp.sigipro.bodegas.modelos.ProductoInterno;
import com.icp.sigipro.configuracion.dao.SeccionDAO;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.configuracion.modelos.Seccion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.security.sasl.AuthenticationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Boga
 */
@WebServlet(name = "ControladorIngresos", urlPatterns = {"/Bodegas/Ingresos"})
public class ControladorIngresos extends SIGIPROServlet
{

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      /* TODO output your page here. You may use following sample code. */
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet ControladorUbicacion</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet ControladorUbicacion at " + request.getContextPath() + "</h1>");
      out.println("</body>");
      out.println("</html>");
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    try {
      String redireccion = "";
      String accion = request.getParameter("accion");
      IngresoDAO dao = new IngresoDAO();

      HttpSession sesion = request.getSession();
      List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
      // INGRESAR PERMISOS
      int[] permisos = {1, 1, 1};

      if (accion != null) {
        if (accion.equalsIgnoreCase("ver")) {
          validarPermisos(permisos, listaPermisos);
          redireccion = "Ingresos/Ver.jsp";

          // CARGAR PRODUCTO Y SECCIÓN
        }
        else if (accion.equalsIgnoreCase("agregar")) {
          validarPermiso(11, listaPermisos);
          redireccion = "Ingresos/Agregar.jsp";
          ProductoInternoDAO productosDAO = new ProductoInternoDAO();
          SeccionDAO seccionesDAO = new SeccionDAO();
          List<ProductoInterno> productos = productosDAO.obtenerProductosYCuarentena();
          List<Seccion> secciones = seccionesDAO.obtenerSecciones();
          request.setAttribute("productos", productos);
          request.setAttribute("secciones", secciones);
          request.setAttribute("accion", "Registrar");
        }
        else {
          validarPermisos(permisos, listaPermisos);
          redireccion = "Ingresos/index.jsp";
          try {
            List<Ingreso> ingresos = dao.obtenerTodo();
            List<Ingreso> ingresosCuarentena = dao.obtenerPorEstado(Ingreso.CUARENTENA);
            List<Ingreso> ingresosRechazados = dao.obtenerPorEstado(Ingreso.RECHAZADO);
            List<Ingreso> ingresosNoDisponibles = dao.obtenerPorEstado(Ingreso.NO_DISPONIBLE);
            request.setAttribute("listaIngresos", ingresos);
            request.setAttribute("listaIngresosCuarentena", ingresosCuarentena);
            request.setAttribute("listaIngresosRechazados", ingresosRechazados);
            request.setAttribute("listaIngresosNoDisponibles", ingresosNoDisponibles);
          }
          catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }
      else {
        validarPermisos(permisos, listaPermisos);
        redireccion = "Ingresos/index.jsp";
        try {
          List<Ingreso> ingresos = dao.obtenerTodo();
          List<Ingreso> ingresosCuarentena = dao.obtenerPorEstado(Ingreso.CUARENTENA);
          List<Ingreso> ingresosRechazados = dao.obtenerPorEstado(Ingreso.RECHAZADO);
          List<Ingreso> ingresosNoDisponibles = dao.obtenerPorEstado(Ingreso.NO_DISPONIBLE);
          request.setAttribute("listaIngresos", ingresos);
          request.setAttribute("listaIngresosCuarentena", ingresosCuarentena);
          request.setAttribute("listaIngresosRechazados", ingresosRechazados);
          request.setAttribute("listaIngresosNoDisponibles", ingresosNoDisponibles);

        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
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
    boolean aprobaciones = false;
    String redireccion = "Ingresos/Agregar.jsp";
    IngresoDAO dao = new IngresoDAO();

    Map<String, String[]> mapa = request.getParameterMap();

    List<Ingreso> porAprobar = new ArrayList<Ingreso>();
    List<Ingreso> porRechazar = new ArrayList<Ingreso>();

    Iterator entries = mapa.entrySet().iterator();
    while (entries.hasNext()) {
      Entry thisEntry = (Entry) entries.next();
      String llave = (String) thisEntry.getKey();
      String valor = request.getParameter(llave);
      if (llave.startsWith("decision-")) {
        aprobaciones = true;
        int id = Integer.parseInt(llave.split("-")[1]);
        if (valor.equalsIgnoreCase("true")) {
          Ingreso i = new Ingreso();
          
          i.setId_ingreso(id);
          i.setCantidad(Integer.parseInt(request.getParameter("decision-"+id+"-cantidad")));
          ProductoInterno p = new ProductoInterno();
          Seccion s = new Seccion();
          p.setId_producto(Integer.parseInt(request.getParameter("decision-"+id+"-id_producto")));
          s.setId_seccion(Integer.parseInt(request.getParameter("decision-"+id+"-id_seccion")));
          
          i.setProducto(p);
          i.setSeccion(s);
          porAprobar.add(i);
        } else if (valor.equalsIgnoreCase("false")){
          Ingreso i = new Ingreso();
          i.setId_ingreso(id);
          
          porRechazar.add(i);
        }
      }
      else {
        aprobaciones = false;
        break;
      }
    }

    if (aprobaciones) {
      try {
        dao.decisionesCuarentena(porAprobar, porRechazar);
        resultado = true;
      }
      catch (SQLException ex) {
        ex.printStackTrace();
        resultado = false;
      }
    }
    else {
      Ingreso ingreso = new Ingreso();

      ProductoInterno producto = new ProductoInterno();
      producto.setId_producto(Integer.parseInt(request.getParameter("producto")));
      ingreso.setProducto(producto);

      Seccion seccion = new Seccion();
      seccion.setId_seccion(Integer.parseInt(request.getParameter("seccion")));
      ingreso.setSeccion(seccion);

      ingreso.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
      ingreso.setPrecio(Integer.parseInt(request.getParameter("precio")));
      ingreso.setEstado(request.getParameter("estado"));
      try {
        SingletonBD s = SingletonBD.getSingletonBD();

        String fechaIngreso = request.getParameter("fechaIngreso");
        String fechaVencimiento = request.getParameter("fechaVencimiento");
        java.util.Date fechaActual = new java.util.Date();

        ingreso.setFecha_ingreso(s.parsearFecha(fechaIngreso));
        ingreso.setFecha_registro(new java.sql.Date(fechaActual.getTime()));
        
        if (fechaVencimiento.equals("") || fechaVencimiento.isEmpty()){
          ingreso.setFecha_vencimiento(s.parsearFecha(fechaVencimiento));
        }
      }
      catch (ParseException ex) {

      }

      String id = request.getParameter("id_ingreso");

      if (id.isEmpty() || id.equals("0")) {
        try {
          if (dao.registrarIngreso(ingreso)) {
            resultado = true;
          }
        }
        catch (Exception ex) {
          ex.printStackTrace();
          resultado = false;
        }
      }
      else {
        // Tareas de sacar de cuarentena
      }
    }

    if (resultado) {
      redireccion = "Ingresos/index.jsp";
      try {
        List<Ingreso> ingresos = dao.obtenerTodo();
        List<Ingreso> ingresosCuarentena = dao.obtenerPorEstado(Ingreso.CUARENTENA);
        List<Ingreso> ingresosRechazados = dao.obtenerPorEstado(Ingreso.RECHAZADO);
        List<Ingreso> ingresosNoDisponibles = dao.obtenerPorEstado(Ingreso.NO_DISPONIBLE);
        request.setAttribute("listaIngresos", ingresos);
        request.setAttribute("listaIngresosCuarentena", ingresosCuarentena);
        request.setAttribute("listaIngresosRechazados", ingresosRechazados);
        request.setAttribute("listaIngresosNoDisponibles", ingresosNoDisponibles);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
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
