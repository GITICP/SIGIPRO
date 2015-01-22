/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.seguridad.rol;

import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.PermisoDAO;
import com.icp.sigipro.seguridad.dao.PermisoRolDAO;
import com.icp.sigipro.seguridad.dao.RolDAO;
import com.icp.sigipro.seguridad.dao.RolUsuarioDAO;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Permiso;
import com.icp.sigipro.seguridad.modelos.PermisoRol;
import com.icp.sigipro.seguridad.modelos.Rol;
import com.icp.sigipro.seguridad.modelos.RolUsuario;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amed
 */
@WebServlet(name = "AgregarRol", urlPatterns = {"/Seguridad/Roles/Agregar"})
public class AgregarRol extends SIGIPROServlet
{

  private final int permiso = 5;

  @Override
  protected int getPermiso()
  {
    return permiso;
  }

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   * @throws java.sql.SQLException
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException, SQLException
  {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {

      UsuarioDAO u = new UsuarioDAO();
      PermisoDAO p = new PermisoDAO();
      List<RolUsuario> rolesUsuario = null;
      List<PermisoRol> permisosRol = null;
      List<Usuario> usuariosRestantes = u.obtenerUsuariosRestantes("0");
      List<Permiso> permisosRestantes = p.obtenerPermisosRestantes("0");

      request.setAttribute("rolesUsuario", rolesUsuario);
      request.setAttribute("usuariosRestantes", usuariosRestantes);
      request.setAttribute("permisosRol", permisosRol);
      request.setAttribute("permisosRestantes", permisosRestantes);

      ServletContext context = this.getServletContext();
      context.getRequestDispatcher("/Seguridad/Roles/Agregar.jsp").forward(request, response);
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
          throws ServletException, IOException
  {
    try {
      processRequest(request, response);
    }
    catch (SQLException ex) {
      Logger.getLogger(AgregarRol.class.getName()).log(Level.SEVERE, null, ex);
    }
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
          throws ServletException, IOException
  {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");

    PrintWriter out;
    out = response.getWriter();

    try {
      String nombreRol;
      nombreRol = request.getParameter("nombreRol");
      String descripcionRol;
      descripcionRol = request.getParameter("descripcionRol");

      RolDAO r = new RolDAO();

      boolean Exito = r.insertarRol(nombreRol, descripcionRol);

      if (Exito) {

        int id_rol;
        id_rol = r.obtenerIDRol(nombreRol);
        String rolesUsuario = request.getParameter("listarolesUsuario");
        String permisosRol = request.getParameter("listaPermisosRol");

        RolUsuarioDAO ru = new RolUsuarioDAO();
        PermisoRolDAO pr = new PermisoRolDAO();

        List<RolUsuario> roles = ru.parsearUsuarios(rolesUsuario, id_rol);
        List<PermisoRol> permisos = pr.parsearUsuarios(permisosRol, id_rol);

        boolean nombre_valido = r.validarNombreRol(nombreRol);
        if (nombre_valido) {

          if (roles != null) {
            boolean f = true;
            for (RolUsuario i : roles) {
              boolean e = ru.insertarRolUsuario(i.getIDUsuario(), i.getIDRol(), i.getFechaActivacion(), i.getFechaDesactivacion());
              if (!e) {
                f = false;
                break;
              }
            }
            for (PermisoRol i : permisos) {
              boolean g = pr.insertarPermisoRol(i.getIDRol(), i.getIDPermiso());
              if (!g) {
                f = false;
                break;
              }
            }

            if (f) {
              request.setAttribute("mensaje", "<div class=\"alert alert-success alert-dismissible\" role=\"alert\">"
                                              + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                                              + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                                              + "Rol ingresado correctamente."
                                              + "</div>");
            }
            else {
              request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                                              + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                                              + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                                              + "El Rol fue ingresado, pero hubo errores al asociar usuarios o permisos."
                                              + "</div>");
            }
          }
          else {
            request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                                            + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                                            + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                                            + "El Rol fue ingresado, pero sin usuarios asociados."
                                            + "</div>");
          }
        }
      }
      else {
        request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                                        + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                                        + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                                        + "Ya existe un rol con el nombre ingresado."
                                        + "</div>");
      }
      request.getRequestDispatcher("/Seguridad/Roles/").forward(request, response);

    }
    catch (SQLException ex) {
      Logger.getLogger(AgregarRol.class.getName()).log(Level.SEVERE, null, ex);
      request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                                          + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                                          + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                                          + "Ya existe un rol con el nombre ingresado."
                                          + "</div>");
      request.getRequestDispatcher("/Seguridad/Roles/").forward(request, response);
    }
    finally {
      out.close();
    }
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo()
  {
    return "Short description";
  }// </editor-fold>

}
