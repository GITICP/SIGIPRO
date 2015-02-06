/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.seguridad.usuario;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Rol;
import com.icp.sigipro.seguridad.modelos.RolUsuario;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Boga
 */
@WebServlet(name = "VerUsuario", urlPatterns = {"/Seguridad/Usuarios/Ver"})
public class VerUsuario extends HttpServlet {

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

      String id;
      id = request.getParameter("id");
      int idUsuario;
      idUsuario = Integer.parseInt(id);

      UsuarioDAO u = new UsuarioDAO();

      Usuario usuario = u.obtenerUsuario(idUsuario);
      List<RolUsuario> rolesUsuario = u.obtenerRolesUsuario(id);
      List<Rol> rolesRestantes = u.obtenerRolesRestantes(id);
      Boolean actividad = u.validarActividad(idUsuario);
      
      
      DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
      Date date = new Date();
      Boolean boolfechadesactivacion = date.before(usuario.getFechaDesactivacionAsDate());
      
      
      request.setAttribute("usuario", usuario);
      request.setAttribute("rolesUsuario", rolesUsuario);
      request.setAttribute("rolesRestantes", rolesRestantes);
      request.setAttribute("actividad",actividad);
      request.setAttribute("boolfechadesactivacion",boolfechadesactivacion);

      ServletContext context = this.getServletContext();
      context.getRequestDispatcher("/Seguridad/Usuarios/Ver.jsp").forward(request, response);

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

    processRequest(request, response);

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

}
