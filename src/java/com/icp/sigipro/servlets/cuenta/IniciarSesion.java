/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.cuenta;

import com.icp.sigipro.seguridad.dao.BarraFuncionalidadDAO;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.BarraFuncionalidad;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Boga
 */
@WebServlet(name = "IniciarSesion", urlPatterns= {"/Cuenta/IniciarSesion"})
public class IniciarSesion extends HttpServlet
{
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
          throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      /* TODO output your page here. You may use following sample code. */
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet de Login</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet de login en esta dirección" + request.getContextPath() + "</h1>");
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
          throws ServletException, IOException
  {
    request.getRequestDispatcher("/Cuenta/IniciarSesion.jsp").forward(request, response);
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

    response.setContentType("text/html;charset=UTF-8");

    PrintWriter out;
    out = response.getWriter();

    try {
      String usuario = request.getParameter("usuario");
      String contrasenna = request.getParameter("contrasenna");

      UsuarioDAO u = new UsuarioDAO();

      int idUsuario = u.validarInicioSesion(usuario, contrasenna);

      if (idUsuario > -1) 
      {
        if(idUsuario != 0)
        {
          try {
            HttpSession session = request.getSession(); // Creación de la sesión.
            session.setAttribute("usuario", usuario);   // Asignar atributo "usuario" a la sesión.
            session.setAttribute("idusuario", idUsuario);

            List<Integer> l = u.obtenerPermisos(idUsuario);
            session.setAttribute("listaPermisos", l);
            
            // ¡¡Terminar la barra de funcionalidad!!
            BarraFuncionalidadDAO b = new BarraFuncionalidadDAO();
            List<BarraFuncionalidad> barra = b.obtenerModulos(idUsuario, l);
            session.setAttribute("barraFuncionalidad", barra);
            
            session.setMaxInactiveInterval(30 * 60);      // Asignación de máximo 30 minutos de inactividad de la sesión.
            response.sendRedirect(request.getContextPath());
          }
          catch (Exception e) {
            System.out.println(e);
          }
        }
        else
        {
          request.setAttribute("usuarioCaducado", usuario);
          request.setAttribute("caducada", "<script>$(window).ready(function(){contrasenaCaducada('" + usuario + "');});</script>");
          request.getRequestDispatcher("/Cuenta/IniciarSesion.jsp").forward(request, response);
        }
      }
      else {
        request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                                        + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                                        + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                                        + "Usuario o contraseña incorrecto."
                                        + "</div>");

        request.getRequestDispatcher("/Cuenta/IniciarSesion.jsp").forward(request, response);
      }
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
