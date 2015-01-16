/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.seguridad.usuario;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.dao.RolUsuarioDAO;
import com.icp.sigipro.seguridad.dao.SeccionDAO;
import com.icp.sigipro.seguridad.modelos.Rol;
import com.icp.sigipro.seguridad.modelos.RolUsuario;
import com.icp.sigipro.seguridad.modelos.Seccion;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "AgregarUsuario", urlPatterns = {"/Seguridad/Usuarios/Agregar"})
public class AgregarUsuario extends HttpServlet {

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

      int idUsuario = 0;
      String id = "0";
      UsuarioDAO u = new UsuarioDAO();
      SeccionDAO sec = new SeccionDAO();

      Usuario usuario = u.obtenerUsuario(idUsuario);
      List<RolUsuario> rolesUsuario = u.obtenerRolesUsuario(id);
      List<Rol> rolesRestantes = u.obtenerRolesRestantes(id);
      List<Seccion> secciones = sec.obtenerSecciones();
      
      request.setAttribute("usuario", usuario);
      request.setAttribute("rolesUsuario", rolesUsuario);
      request.setAttribute("rolesRestantes", rolesRestantes);
      request.setAttribute("secciones",secciones);
      ServletContext context = this.getServletContext();
      context.getRequestDispatcher("/Seguridad/Usuarios/Agregar.jsp").forward(request, response);

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

    response.setContentType("text/html;charset=UTF-8");

    PrintWriter out;
    out = response.getWriter();

    try {
      String nombreUsuario;
      nombreUsuario = request.getParameter("nombreUsuario");
      String nombreCompleto;
      nombreCompleto = request.getParameter("nombreCompleto");
      String correoElectronico;
      correoElectronico = request.getParameter("correoElectronico");
      String cedula;
      cedula = request.getParameter("cedula");
      String seccion;
      seccion = request.getParameter("seccion");
      String puesto;
      puesto = request.getParameter("puesto");
      String fechaActivacion;
      fechaActivacion = request.getParameter("fechaActivacion");
      String fechaDesactivacion;
      fechaDesactivacion = request.getParameter("fechaDesactivacion");

      UsuarioDAO u = new UsuarioDAO();

      boolean insercionExitosa = u.insertarUsuario(nombreUsuario, nombreCompleto, correoElectronico, cedula,
              Integer.parseInt(seccion), puesto, fechaActivacion, fechaDesactivacion);

      if (insercionExitosa) {

        int id_usuario = u.obtenerIDUsuario(nombreUsuario);
        String rolesUsuario = request.getParameter("listaRolesUsuario");
        RolUsuarioDAO ru = new RolUsuarioDAO();
        List<RolUsuario> roles = ru.parsearRoles(rolesUsuario, id_usuario);
        if (roles != null) {
          boolean f = true;
          for (RolUsuario i : roles) {
            boolean e = ru.insertarRolUsuario(i.getIDUsuario(), i.getIDRol(), i.getFechaActivacion(), i.getFechaDesactivacion());
            if (!e) {
              f = false;
              break;
            }
          }
          if (f) {
            request.setAttribute("mensaje", "<div class=\"alert alert-success alert-dismissible\" role=\"alert\">"
                    + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                    + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                    + "Usuario ingresado correctamente."
                    + "</div>");
          } else {
            request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                    + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                    + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                    + "El Usuario fue ingresado, pero sin roles."
                    + "</div>");
          }
        } else {
          request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                  + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                  + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                  + "El Usuario fue ingresado, pero sin roles."
                  + "</div>");
        }
      } else {
        request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                + "Usuario no pudo ser ingresado."
                + "</div>");
      }
      request.getRequestDispatcher("/Seguridad/Usuarios/").forward(request, response);

    } finally {
      out.close();
    }
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
