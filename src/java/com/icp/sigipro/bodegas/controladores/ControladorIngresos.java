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
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
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
      String mensaje = null;
      String explicacion = null;

      String accion = request.getParameter("accion");

      IngresoDAO dao = new IngresoDAO();
      HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

      HttpSession sesion = request.getSession();
      List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
      // INGRESAR PERMISOS
      int[] permisos = {1, 1, 1};

      if (accion != null) {
        if (accion.equalsIgnoreCase("ver")) {
          validarPermisos(permisos, listaPermisos);
          redireccion = "Ingresos/Ver.jsp";

          int id = Integer.parseInt(request.getParameter("id_ingreso"));

          try {
            Ingreso i = dao.buscar(id);
            request.setAttribute("ingreso", i);
          }
          catch (SIGIPROException ex) {
            mensaje = ex.getMessage();
            request.setAttribute("mensaje", helper.mensajeDeError(mensaje));
          }
          catch (SQLException ex) {
            mensaje = "Ha ocurrido un error con la base de datos. Favor inténtelo nuevamente y si el problema persiste contacte al administrador del sistema.";
            request.setAttribute("mensaje", helper.mensajeDeError(mensaje + explicacion));
            ex.printStackTrace();
          }
          catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException ex) {
            mensaje = "Ha ocurrido un error inesperado. Favor contacte al administrador del sistema.";
            request.setAttribute("mensaje", helper.mensajeDeError(mensaje + explicacion));
            ex.printStackTrace();
          }
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
        else if (accion.equalsIgnoreCase("editar")) {
          validarPermiso(11, listaPermisos);
          redireccion = "Ingresos/Editar.jsp";
          ProductoInternoDAO productosDAO = new ProductoInternoDAO();
          SeccionDAO seccionesDAO = new SeccionDAO();
          List<ProductoInterno> productos = productosDAO.obtenerProductosYCuarentena();
          List<Seccion> secciones = seccionesDAO.obtenerSecciones();

          int id = 0;
          try {
            id = Integer.parseInt(request.getParameter("id_ingreso"));
          }
          catch (NumberFormatException ex) {

          }

          Ingreso ingreso;
          try {
            ingreso = dao.buscar(id);
            request.setAttribute("ingreso", ingreso);
            int index = 0;
            for (Seccion s : secciones) {
              if (s.getId_seccion() == ingreso.getSeccion().getId_seccion()) {
                break;
              }
              index++;
            }
            secciones.remove(index);
            index = 0;

            for (ProductoInterno p : productos) {
              if (p.getId_producto() == ingreso.getProducto().getId_producto()) {
                break;
              }
              index++;
            }
            productos.remove(index);
          }
          catch (SIGIPROException ex) {
            mensaje = ex.getMessage();
            redireccion = "Ingresos/index.jsp";
            request.setAttribute("mensaje", helper.mensajeDeError(mensaje));
          }
          catch (SQLException ex) {
            mensaje = "Ha ocurrido un error con la base de datos. Favor inténtelo nuevamente y si el problema persiste contacte al administrador del sistema.";
            redireccion = "Ingresos/index.jsp";
            request.setAttribute("mensaje", helper.mensajeDeError(mensaje + explicacion));
            ex.printStackTrace();
          }
          catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException ex) {
            mensaje = "Ha ocurrido un error inesperado. Favor contacte al administrador del sistema.";
            redireccion = "Ingresos/index.jsp";
            request.setAttribute("mensaje", helper.mensajeDeError(mensaje + explicacion));
            ex.printStackTrace();
          }
          request.setAttribute("productos", productos);
          request.setAttribute("secciones", secciones);
          request.setAttribute("accion", "Editar");
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
          System.out.println(ex.getMessage());
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
    String redireccion = "Ingresos/Agregar.jsp";
    IngresoDAO dao = new IngresoDAO();

    String accion = request.getParameter("accion");
    String mensaje = null;
    String explicacion = null;

    if (accion != null) {
      String idSinParsear = request.getParameter("id_ingreso");
      redireccion = "Ingresos/index.jsp";
      int id = Integer.parseInt(idSinParsear);
      Ingreso ingreso = null;
      try {
        ingreso = dao.buscar(id);
      }
      catch (SIGIPROException ex) {
        mensaje = ex.getMessage();
      }
      catch (SQLException ex) {
        explicacion = ex.getMessage();
        ex.printStackTrace();
      }
      catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException ex) {
        explicacion = "Ha ocurrido un error inesperado. Favor contacte al administrador del sistema.";
        ex.printStackTrace();
      }
      if (ingreso != null) {
        if (accion.equalsIgnoreCase("aprobar")) {
          mensaje = "aprobado";
          try {
            dao.decisionesCuarentena(ingreso, Ingreso.DISPONIBLE);
            resultado = true;
          }
          catch (Exception ex) {
            ex.printStackTrace();
          }
        }
        else if (accion.equalsIgnoreCase("rechazar")) {
          mensaje = "rechazado";
          try {
            dao.decisionesCuarentena(ingreso, Ingreso.RECHAZADO);
            resultado = true;
          }
          catch (Exception ex) {
            ex.printStackTrace();
          }
        }
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

      boolean valido = false;

      try {
        SingletonBD s = SingletonBD.getSingletonBD();

        ingreso.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
        ingreso.setPrecio(Integer.parseInt(request.getParameter("precio")));
        ingreso.setEstado(request.getParameter("estado"));

        String fechaIngreso = request.getParameter("fechaIngreso");
        String fechaVencimiento = request.getParameter("fechaVencimiento");
        java.util.Date fechaActual = new java.util.Date();

        ingreso.setFecha_ingreso(s.parsearFecha(fechaIngreso));
        ingreso.setFecha_registro(new java.sql.Date(fechaActual.getTime()));

        if (!(fechaVencimiento.equals("") || fechaVencimiento.isEmpty())) {
          ingreso.setFecha_vencimiento(s.parsearFecha(fechaVencimiento));
        }

        valido = true;
      }
      catch (ParseException ex) {
        explicacion = "por error en formatos de fechas o números.";
      }

      String id = request.getParameter("id_ingreso");

      if (id.isEmpty() || id.equals("0")) {
        mensaje = "registrado";
        if (valido) {
          try {
            if (dao.registrarIngreso(ingreso)) {
              resultado = true;
            }
          }
          catch (Exception ex) {
            resultado = false;
          }
        }
        else {
          resultado = false;
        }
      }
      else {
        mensaje = "editado";
        int idParseado = Integer.parseInt(id);
        ingreso.setId_ingreso(idParseado);
        int cantidadPrevia = Integer.parseInt(request.getParameter("control-cantidad"));
        String estadoPrevio = request.getParameter("control-estado");
        if (valido) {
          try {
            dao.actualizar(ingreso, cantidadPrevia, estadoPrevio);
            resultado = true;
          }
          catch (Exception ex) {
            resultado = false;
          }
        } else {
          resultado = false;
        }
      }
    }

    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

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
        request.setAttribute("mensaje", helper.mensajeDeExito("Ingreso " + mensaje + " con éxito."));
      }
      catch (Exception ex) {
        ex.printStackTrace();
        request.setAttribute("mensaje", helper.mensajeDeError("Error de comunicación con la base de datos. Favor comuníquese con el administrador del sistema."));
      }
    }
    else {
      request.setAttribute("mensaje", helper.mensajeDeError("Ingreso " + mensaje + " sin éxito. " + explicacion));
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
