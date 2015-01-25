/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.cuenta;

import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Rol;
import com.icp.sigipro.seguridad.modelos.RolUsuario;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Boga
 */
@WebServlet(name = "RecuperarContrasena", urlPatterns = {"/Cuenta/RecuperarContrasena"})
public class RecuperarContrasena extends SIGIPROServlet
{

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      /* TODO output your page here. You may use following sample code. */
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet RecuperarContrasena</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet RecuperarContrasena at " + request.getContextPath() + "</h1>");
      out.println("</body>");
      out.println("</html>");
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    PrintWriter out;
    out = response.getWriter();
    request.setCharacterEncoding("UTF-8");
    try {

      HttpSession sesion = request.getSession();

      List<Integer> permisos = (List<Integer>) sesion.getAttribute("listaPermisos");
      validarPermiso(17, permisos);

      String correoElectronico = request.getParameter("correoElectronico");
      String id = request.getParameter("idUsuario");

      if (correoElectronico != null && !(correoElectronico.isEmpty()) && id != null && !(id.isEmpty())) {

        int idUsuario = Integer.parseInt(id);

        int contrasena = recuperarContrasena(correoElectronico);
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        UsuarioDAO u = new UsuarioDAO();

        Usuario usuario = u.obtenerUsuario(idUsuario);
        List<RolUsuario> rolesUsuario = u.obtenerRolesUsuario(id);
        List<Rol> rolesRestantes = u.obtenerRolesRestantes(id);

        request.setAttribute("usuario", usuario);
        request.setAttribute("rolesUsuario", rolesUsuario);
        request.setAttribute("rolesRestantes", rolesRestantes);

        if (contrasena == 1) {
          request.setAttribute("mensaje", helper.mensajeDeExito("Se ha enviado un correo a la dirección " + correoElectronico + " con la nueva contraseña."));
        }
        else {
          request.setAttribute("mensaje", helper.mensajeDeError("La contraseña no se pudo restablecer por un error de comunicación con el servidor."));
        }
        request.getRequestDispatcher("/Seguridad/Usuarios/Ver.jsp").forward(request, response);
      }
      else {
        request.getRequestDispatcher("/Seguridad/Usuarios.jsp").forward(request, response);
      }

    }
    catch (AuthenticationException ex) {
      request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    catch (Exception ex){
      request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    finally {
      out.close();
    }

  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");

    PrintWriter out;
    out = response.getWriter();
    request.setCharacterEncoding("UTF-8");
    try {
      String correoElectronico = request.getParameter("correoElectronico");

      int contrasena = recuperarContrasena(correoElectronico);

      HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

      if (contrasena == 1) {
        request.setAttribute("mensaje", helper.mensajeDeExito("Se ha enviado un correo a la dirección " + correoElectronico + " con su nueva contraseña."));
      }
      else {
        request.setAttribute("mensaje", helper.mensajeDeError("El correo " + correoElectronico + " no se encuentra registrado. Por favor inténtelo nuevamente."));
      }
      request.getRequestDispatcher("/Cuenta/IniciarSesion.jsp").forward(request, response);
    }
    finally {
      out.close();
    }
  }

  public int recuperarContrasena(String correoElectronico)
  {
    UsuarioDAO u = new UsuarioDAO();
    return u.recuperarContrasena(correoElectronico);
  }

  @Override
  public String getServletInfo()
  {
    return "Short description";
  }// </editor-fold>

  @Override
  protected int getPermiso()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
