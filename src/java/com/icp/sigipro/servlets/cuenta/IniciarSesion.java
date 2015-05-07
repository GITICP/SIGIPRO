/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.cuenta;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.seguridad.dao.BarraFuncionalidadDAO;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.BarraFuncionalidad;
import com.icp.sigipro.utilidades.HelpersHTML;
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
    request.setCharacterEncoding("UTF-8");

    try {
      String usuario = request.getParameter("usuario");
      String contrasenna = request.getParameter("contrasenna");

      UsuarioDAO u = new UsuarioDAO();
      HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

      int idUsuario = u.validarInicioSesion(usuario, contrasenna);

      if (idUsuario > -1) 
      {
        if(idUsuario != 0)
        {
          try {
            HttpSession session = request.getSession(); // Creación de la sesión.
            session.setAttribute("usuario", usuario);   // Asignar atributo "usuario" a la sesión.
            session.setAttribute("idusuario", idUsuario);

            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(0,Bitacora.ACCION_LOGIN,request.getSession().getAttribute("usuario"),Bitacora.TABLA_USUARIO,request.getRemoteAddr());
            //*----------------------------*
            
            List<Integer> l = u.obtenerPermisos(idUsuario);
            session.setAttribute("listaPermisos", l);
            
            // ¡¡Terminar la barra de funcionalidad!!
            BarraFuncionalidadDAO b = new BarraFuncionalidadDAO();
            BarraFuncionalidad barra = b.obtenerModulos(idUsuario, l);
            session.setAttribute("barraFuncionalidad", barra);
            
            session.setMaxInactiveInterval(30*60);      // Asignación de máximo 30 minutos de inactividad de la sesión.
            response.sendRedirect(request.getContextPath());
          }
          catch (Exception e) {
            e.printStackTrace();
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
        if (idUsuario == -1){
          request.setAttribute("mensaje", helper.mensajeDeError("Usuario o contraseña incorrecto."));
        }else if(idUsuario == -2){
          request.setAttribute("mensaje", helper.mensajeDeError("El usuario que está intentando acceder se encuentra desactivado."));
        }else{
          request.setAttribute("mensaje", helper.mensajeDeError("Error de conexión. Contacte al administrador del sistema."));
        }
        
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
