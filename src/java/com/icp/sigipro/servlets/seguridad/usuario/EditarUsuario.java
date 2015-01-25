/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.seguridad.usuario;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.RolUsuarioDAO;
import com.icp.sigipro.seguridad.dao.SeccionDAO;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Rol;
import com.icp.sigipro.seguridad.modelos.RolUsuario;
import com.icp.sigipro.seguridad.modelos.Seccion;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletContext;
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
@WebServlet(name = "EditarUsuario", urlPatterns = {"/Seguridad/Usuarios/Editar"})
public class EditarUsuario extends SIGIPROServlet
{

  private final int permiso = 3;

  @Override
  protected int getPermiso()
  {
    return permiso;
  }

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {

    response.setContentType("text/html;charset=UTF-8");
    HttpSession sesion = request.getSession();
    if (validarPermiso((List<Integer>) sesion.getAttribute("listaPermisos"))) {
      try (PrintWriter out = response.getWriter()) {
        String id;
        id = request.getParameter("id");
        int idUsuario;
        idUsuario = Integer.parseInt(id);

        UsuarioDAO u = new UsuarioDAO();
        SeccionDAO sec = new SeccionDAO();

        Usuario usuario = u.obtenerUsuario(idUsuario);
        List<RolUsuario> rolesUsuario = u.obtenerRolesUsuario(id);
        List<Rol> rolesRestantes = u.obtenerRolesRestantes(id);
        List<Seccion> secciones = sec.obtenerSecciones();

        request.setAttribute("usuario", usuario);
        request.setAttribute("rolesUsuario", rolesUsuario);
        request.setAttribute("rolesRestantes", rolesRestantes);
        request.setAttribute("secciones", secciones);

        ServletContext context = this.getServletContext();
        context.getRequestDispatcher("/Seguridad/Usuarios/Editar.jsp").forward(request, response);
      }
    }
    else {
      request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    HttpSession sesion = request.getSession();
    if (validarPermiso((List<Integer>) sesion.getAttribute("listaPermisos"))) {
      processRequest(request, response);
    }
    else {
      request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    try {
      request.setCharacterEncoding("UTF-8");
      int idUsuario;

      idUsuario = Integer.parseInt(request.getParameter("editarIDUsuario"));
      String nomCompleto;
      nomCompleto = request.getParameter("nombreCompleto");
      String correo;
      correo = request.getParameter("correoElectronico");
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

      String rolesUsuario = request.getParameter("listaRolesUsuario");
      RolUsuarioDAO ru = new RolUsuarioDAO();
      List<RolUsuario> roles = ru.parsearRoles(rolesUsuario, idUsuario);

      UsuarioDAO u = new UsuarioDAO();
      boolean correo_inactivo = u.validarCorreo(correo, idUsuario);
      
      HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
      
      if (correo_inactivo) {
        boolean resultado;
        if (roles != null) {
          resultado = u.editarUsuario(idUsuario, nomCompleto, correo, cedula, Integer.parseInt(seccion), puesto, fechaActivacion, fechaDesactivacion, roles);
        }
        else {
          resultado = false;
        }
        if (resultado) {
          request.setAttribute("mensaje", helper.mensajeDeExito("Usuario editado correctamente."));
        }
        else {
          request.setAttribute("mensaje", helper.mensajeDeError("Usuario no pudo ser editado."));
        }
      }else{
        request.setAttribute("mensaje", helper.mensajeDeError("El correo ingresado ya está ligado a un usuario."));
      }
      request.getRequestDispatcher("/Seguridad/Usuarios/Ver?id=" + String.valueOf(idUsuario)).forward(request, response);

    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public String getServletInfo()
  {
    return "Servlet de edición";
  }
}
