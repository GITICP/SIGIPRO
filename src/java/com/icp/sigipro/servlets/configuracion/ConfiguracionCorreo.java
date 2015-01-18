/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.configuracion;

import com.icp.sigipro.configuracion.dao.CorreoDAO;
import com.icp.sigipro.configuracion.modelos.Correo;
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
 * @author Amed
 */
@WebServlet(name = "Correo", urlPatterns = {"/Configuracion/Correo"})
public class ConfiguracionCorreo extends HttpServlet {

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
      CorreoDAO co = new CorreoDAO();
     
      Correo correo = co.obtenerCorreo();
      request.setAttribute("correo",correo);

      ServletContext context = this.getServletContext();
      context.getRequestDispatcher("/Configuracion/Correo.jsp").forward(request, response);
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
    try 
    {
      request.setCharacterEncoding("UTF-8");
      Integer idcorreo;
      idcorreo = Integer.parseInt(request.getParameter("idCorreo"));
      String host;
      host = request.getParameter("host");
      String puerto;
      puerto = request.getParameter("puerto");
      String emisor;
      emisor = request.getParameter("emisor");
      String correo;
      correo = request.getParameter("correo");
      String contrasena;
      contrasena = request.getParameter("contrasena");
      
      boolean resultado;
      CorreoDAO co = new CorreoDAO();
      resultado = co.editarConfiguracionCorreo(host, puerto, emisor, contrasena, correo, idcorreo);
      
      if (resultado) 
      {
        request.setAttribute("mensaje", "<div class=\"alert alert-success alert-dismissible\" role=\"alert\">"
                + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                + "Correo configurado correctamente."
                + "</div>");
      }
      else 
      {
        request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                + "El correo no pudo ser configurado correctamente."
                + "</div>");
      }
      processRequest(request, response);
    }
    catch (Exception ex) 
    {
      ex.printStackTrace();
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
