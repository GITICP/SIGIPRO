/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.seguridad.usuario;

import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.PuestoDAO;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.dao.RolUsuarioDAO;
import com.icp.sigipro.seguridad.dao.SeccionDAO;
import com.icp.sigipro.seguridad.modelos.Puesto;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Boga
 */
@WebServlet(name = "AgregarUsuario", urlPatterns = {"/Seguridad/Usuarios/Agregar"})
public class AgregarUsuario extends SIGIPROServlet
{

  private final int permiso = 2;

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
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {

    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {

      int idUsuario = 0;
      String id = "0";
      UsuarioDAO u = new UsuarioDAO();
      SeccionDAO sec = new SeccionDAO();
      PuestoDAO pu = new PuestoDAO();

      Usuario usuario = u.obtenerUsuario(idUsuario);
      List<RolUsuario> rolesUsuario = u.obtenerRolesUsuario(id);
      List<Rol> rolesRestantes = u.obtenerRolesRestantes(id);
      List<Seccion> secciones = sec.obtenerSecciones();
      List<Puesto> puestos =pu.obtenerPuestos();

      request.setAttribute("usuario", usuario);
      request.setAttribute("rolesUsuario", rolesUsuario);
      request.setAttribute("rolesRestantes", rolesRestantes);
      request.setAttribute("secciones", secciones);
      request.setAttribute("puestos", puestos);
      ServletContext context = this.getServletContext();
      context.getRequestDispatcher("/Seguridad/Usuarios/Agregar.jsp").forward(request, response);

    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {

    response.setContentType("text/html;charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
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
      HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
      
      boolean nombre_usuario_activo = u.validarNombreUsuario(nombreUsuario, 0);
      boolean correo_activo = u.validarCorreo(correoElectronico, 0);
      if (correo_activo && nombre_usuario_activo) {
        boolean insercionExitosa = u.insertarUsuario(nombreUsuario, nombreCompleto, correoElectronico, cedula,
                                                     Integer.parseInt(seccion), Integer.parseInt(puesto), fechaActivacion, fechaDesactivacion);

        if (insercionExitosa) {
          int id_usuario = u.obtenerIDUsuario(nombreUsuario);

          String rolesUsuario = request.getParameter("listaRolesUsuario");
          RolUsuarioDAO ru = new RolUsuarioDAO();
          if (rolesUsuario != null && !(rolesUsuario.isEmpty()) ) {
            List<RolUsuario> roles = ru.parsearRoles(rolesUsuario, id_usuario);
            boolean f = true;
            for (RolUsuario i : roles) {
              boolean e = ru.insertarRolUsuario(i.getIDUsuario(), i.getIDRol(), i.getFechaActivacion(), i.getFechaDesactivacion());
              if (!e) {
                f = false;
                break;
              }
            }
            if (f) {
              request.setAttribute("mensaje", helper.mensajeDeExito("Usuario ingresado correctamente."));
            }
            else {
              request.setAttribute("mensaje", helper.mensajeDeAdvertencia("El Usuario fue ingresado, pero sin roles."));
            }
          }
          else {
            request.setAttribute("mensaje", helper.mensajeDeAdvertencia("El Usuario fue ingresado, pero sin roles."));
          }
        }
      }
      else {
        if (!nombre_usuario_activo){
          request.setAttribute("mensaje", helper.mensajeDeError("El nombre de usuario ya está ligado a un usuario."));
        }else{
          request.setAttribute("mensaje", helper.mensajeDeError("El correo ingresado ya está ligado a un usuario."));
        }
        
      }
      request.getRequestDispatcher("/Seguridad/Usuarios/").forward(request, response);
    }
    finally {
      out.close();
    }
  }
}
