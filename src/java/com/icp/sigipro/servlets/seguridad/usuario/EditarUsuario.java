/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.seguridad.usuario;


import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.PuestoDAO;
import com.icp.sigipro.seguridad.dao.RolUsuarioDAO;
import com.icp.sigipro.seguridad.dao.SeccionDAO;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Puesto;
import com.icp.sigipro.seguridad.modelos.Rol;
import com.icp.sigipro.seguridad.modelos.RolUsuario;
import com.icp.sigipro.seguridad.modelos.Seccion;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        PuestoDAO pu = new PuestoDAO();

        Usuario usuario = u.obtenerUsuario(idUsuario);
        List<RolUsuario> rolesUsuario = u.obtenerRolesUsuario(id);
        List<Rol> rolesRestantes = u.obtenerRolesRestantes(id);
        List<Seccion> secciones = sec.obtenerSecciones();
        Boolean actividad = u.validarActividad(idUsuario);
        List<Puesto> puestos =pu.obtenerPuestos();

        request.setAttribute("usuario", usuario);
        request.setAttribute("rolesUsuario", rolesUsuario);
        request.setAttribute("rolesRestantes", rolesRestantes);
        request.setAttribute("secciones", secciones);
        request.setAttribute("actividad",actividad);
        request.setAttribute("puestos", puestos);
        
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
      String estado = request.getParameter("boolEstado");

      String rolesUsuario = request.getParameter("listaRolesUsuario");
      RolUsuarioDAO ru = new RolUsuarioDAO();
      List<RolUsuario> roles = ru.parsearRoles(rolesUsuario, idUsuario);

      UsuarioDAO u = new UsuarioDAO();
      boolean correo_inactivo = u.validarCorreo(correo, idUsuario);
      
      HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
      
      if (correo_inactivo) {
        boolean resultado;
        if (roles != null) {
          
          String contrasenna = request.getParameter("contrasenna");
          resultado = u.editarUsuario(idUsuario, nomCompleto, correo, cedula, Integer.parseInt(seccion), 
                  Integer.parseInt(puesto), fechaActivacion, fechaDesactivacion, roles, estado, contrasenna);
          //Para manejo de bitacora no voy a tocar este Edit porque tiene mucha logica rara. 
          Usuario usuario = new Usuario();
          usuario.setIdUsuario(idUsuario);
          usuario.setNombreCompleto(nomCompleto);
          usuario.setCorreo(correo);
          usuario.setCedula(cedula);
          usuario.setIdSeccion(Integer.parseInt(seccion));
          usuario.setIdPuesto(Integer.parseInt(puesto));
          try {
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

                    String fechaactivacion = request.getParameter("fechaActivacion");
                    java.util.Date fActivacion = formatoFecha.parse(fechaactivacion);

                    String fechadesactivacion = request.getParameter("fechaDesactivacion");
                    java.util.Date fDesactivacion = formatoFecha.parse(fechadesactivacion);

                    java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
                    java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());

                    usuario.setFechaActivacion(fActivacionSQL);
                    usuario.setFechaDesactivacion(fDesactivacionSQL);
                    usuario.setFechaActivacion(fActivacionSQL);
                    usuario.setFechaDesactivacion(fDesactivacionSQL);
        } catch (ParseException ex){
          ex.printStackTrace();
        }
         //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(usuario.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_USUARIO,request.getRemoteAddr());
        //*----------------------------*
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
