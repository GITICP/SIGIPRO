/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.seguridad.rol;

import com.icp.sigipro.seguridad.dao.PermisoDAO;
import com.icp.sigipro.seguridad.dao.PermisoRolDAO;
import com.icp.sigipro.seguridad.dao.RolDAO;
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
@WebServlet(name = "Ver", urlPatterns = {"/Seguridad/Roles/Ver"})
public class VerRol extends HttpServlet {

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
          throws ServletException, IOException, SQLException {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
     
      String id;
      id = request.getParameter("id");
      int idRol;
      idRol = Integer.parseInt(id);
      
      RolDAO r = new RolDAO();
      PermisoDAO pr =new PermisoDAO();
      
      Rol rol = r.obtenerRol(idRol);
      List<RolUsuario> usuariosRol = r.obtenerUsuariosRol(id);
      List<PermisoRol> permisosRol = pr.obtenerPermisosRol(id);

      request.setAttribute("rol", rol);
      request.setAttribute("usuariosRol", usuariosRol);
      request.setAttribute("permisosRol", permisosRol);

      ServletContext context = this.getServletContext();
      context.getRequestDispatcher("/Seguridad/Roles/Ver.jsp").forward(request, response);
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
      processRequest(request, response);
    } catch (SQLException ex) {
        ex.printStackTrace();
      Logger.getLogger(VerRol.class.getName()).log(Level.SEVERE, null, ex);
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
          throws ServletException, IOException {
    try {
      processRequest(request, response);
    } catch (SQLException ex) {
        ex.printStackTrace();
      Logger.getLogger(VerRol.class.getName()).log(Level.SEVERE, null, ex);
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
