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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
          throws ServletException, IOException, ParseException {
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
      java.util.Calendar calendario = java.util.Calendar.getInstance();
      java.util.Date hoy = calendario.getTime();
      java.util.Date hoyFormateado = df.parse(df.format(hoy));
      java.sql.Date date = new java.sql.Date(hoyFormateado.getTime());
      Boolean boolfechadesactivacion;
      Boolean permanente = usuario.getFechaActivacionAsDate().equals(usuario.getFechaDesactivacionAsDate());
      Boolean nopermanenteantesdesactivacion = date.before(usuario.getFechaDesactivacionAsDate());
      Boolean nopermanentedespuesactivacion = date.after(usuario.getFechaActivacionAsDate());
      
      if (usuario.getFechaActivacionAsDate().equals(usuario.getFechaDesactivacionAsDate()))
        { boolfechadesactivacion = usuario.getFechaActivacionAsDate().equals(date);
        }
      else
      {
      boolfechadesactivacion = date.before(usuario.getFechaDesactivacionAsDate())  
                                                      && date.after(usuario.getFechaActivacionAsDate()) ;
      }
      
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

    try {
      processRequest(request, response);
    } catch (ParseException ex) {
      Logger.getLogger(VerUsuario.class.getName()).log(Level.SEVERE, null, ex);
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
    } catch (ParseException ex) {
      Logger.getLogger(VerUsuario.class.getName()).log(Level.SEVERE, null, ex);
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
