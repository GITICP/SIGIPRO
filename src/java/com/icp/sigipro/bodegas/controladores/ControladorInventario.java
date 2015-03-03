/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.controladores;

import com.icp.sigipro.bodegas.dao.InventarioDAO;
import com.icp.sigipro.bodegas.dao.ProductoInternoDAO;
import com.icp.sigipro.bodegas.modelos.Ingreso;
import com.icp.sigipro.bodegas.modelos.Inventario;
import com.icp.sigipro.bodegas.modelos.ProductoInterno;
import com.icp.sigipro.configuracion.dao.SeccionDAO;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
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
 * @author ld.conejo
 */
@WebServlet(name = "ControladorInventario", urlPatterns = {"/Bodegas/ControladorInventario"})
public class ControladorInventario extends SIGIPROServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControladorInventario</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorInventario at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
        try {
            
      String redireccion = "";
      String mensaje = null;
      String explicacion = null;

      String accion = request.getParameter("accion");

      InventarioDAO dao = new InventarioDAO();
      HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

      HttpSession sesion = request.getSession();
      //Ocupamos definir permisos
      List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
      int[] permisos = {27, 28, 29};
      //-------------------------*

      if (accion != null) {
        if (accion.equalsIgnoreCase("ver")) {
          validarPermisos(permisos, listaPermisos);
          redireccion = "Inventarios/Ver.jsp";

          int id = Integer.parseInt(request.getParameter("id_inventario"));

          try {
            Inventario i = dao.buscar(id);
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
          validarPermiso(27, listaPermisos);
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
          validarPermiso(28, listaPermisos);
          redireccion = "Ingresos/Editar.jsp";

          int id = 0;
          try {
            id = Integer.parseInt(request.getParameter("id_ingreso"));
          }
          catch (NumberFormatException ex) {
          }
          Ingreso ingreso;
          try {
            //ingreso = dao.buscar(id);
            request.setAttribute("ingreso", ingreso);
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
          request.setAttribute("accion", "Editar");
        }
        else {
          validarPermisos(permisos, listaPermisos);
          redireccion = "Ingresos/index.jsp";
          try {
            //List<Ingreso> ingresos = dao.obtenerTodo();
            //List<Ingreso> ingresosCuarentena = dao.obtenerPorEstado(Ingreso.CUARENTENA);
            //List<Ingreso> ingresosRechazados = dao.obtenerPorEstado(Ingreso.RECHAZADO);
            //List<Ingreso> ingresosNoDisponibles = dao.obtenerPorEstado(Ingreso.NO_DISPONIBLE);
            //request.setAttribute("listaIngresos", ingresos);
            //request.setAttribute("listaIngresosCuarentena", ingresosCuarentena);
            //request.setAttribute("listaIngresosRechazados", ingresosRechazados);
            //request.setAttribute("listaIngresosNoDisponibles", ingresosNoDisponibles);
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
            //List<Ingreso> ingresos = dao.obtenerTodo();
            //List<Ingreso> ingresosCuarentena = dao.obtenerPorEstado(Ingreso.CUARENTENA);
            //List<Ingreso> ingresosRechazados = dao.obtenerPorEstado(Ingreso.RECHAZADO);
            //List<Ingreso> ingresosNoDisponibles = dao.obtenerPorEstado(Ingreso.NO_DISPONIBLE);
            //request.setAttribute("listaIngresos", ingresos);
            //request.setAttribute("listaIngresosCuarentena", ingresosCuarentena);
            //request.setAttribute("listaIngresosRechazados", ingresosRechazados);
            //request.setAttribute("listaIngresosNoDisponibles", ingresosNoDisponibles);

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
       */ 
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
