/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.controladores;


import com.icp.sigipro.bodegas.dao.SolicitudDAO;
import com.icp.sigipro.bodegas.modelos.Solicitud;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author Amed
 */
@WebServlet(name = "ControladorSolicitudes", urlPatterns = {"/Bodega/Solicitudes"})
public class ControladorSolicitudes extends SIGIPROServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
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
    try {
      String redireccion = "";
      String accion = request.getParameter("accion");
      SolicitudDAO dao = new SolicitudDAO();
      HttpSession sesion = request.getSession();
      List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
      int[] permisos = {24, 25};
      int usuario_solicitante;
      UsuarioDAO usrDAO = new UsuarioDAO();
      if (verificarPermiso(25, listaPermisos))
        { usuario_solicitante = 0; }
      else
      {   String nombre_usr = (String) sesion.getAttribute("usuario");
          usuario_solicitante = usrDAO.obtenerIDUsuario(nombre_usr);
      }

      if (accion != null) {
        validarPermisos(permisos, listaPermisos);
        if (accion.equalsIgnoreCase("ver")) {
          redireccion = "Solicitudes/Ver.jsp";
          int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
          Solicitud solicitud = dao.obtenerSolicitud(id_solicitud);
          request.setAttribute("solicitud", solicitud);
        }
        else if (accion.equalsIgnoreCase("agregar")) {
          redireccion = "Solicitudes/Agregar.jsp";
          Solicitud solicitud = new Solicitud();
          request.setAttribute("solicitud", solicitud);
          request.setAttribute("accion", "Agregar");
        }
        else if (accion.equalsIgnoreCase("eliminar")) {
          int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
          dao.eliminarSolicitud(id_solicitud);
          redireccion = "Solicitudes/index.jsp";
          List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
          request.setAttribute("listaSolicitudes", solicitudes);
          HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
          request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud eliminada correctamente"));
   
        }
        else if (accion.equalsIgnoreCase("editar")) {
          redireccion = "Solicitudes/Editar.jsp";
          int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
          Solicitud solicitud = dao.obtenerSolicitud(id_solicitud);
          request.setAttribute("solicitud", solicitud);
          request.setAttribute("accion", "Editar");
        }
        else {
          redireccion = "Solicitudes/index.jsp";
          List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
          request.setAttribute("listaSolicitudes", solicitudes);
        }
      }
      else {
        validarPermisos(permisos, listaPermisos);
        redireccion = "Solicitudes/index.jsp";
        List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
        request.setAttribute("listaSolicitudes", solicitudes);
      }

      RequestDispatcher vista = request.getRequestDispatcher(redireccion);
      vista.forward(request, response);
    }
    catch (AuthenticationException ex){
      RequestDispatcher vista = request.getRequestDispatcher("/index.jsp");
      vista.forward(request, response);
    }
  }

  
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
 protected int getPermiso()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
