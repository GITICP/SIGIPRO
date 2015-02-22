/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.controladores;


import com.icp.sigipro.bodegas.dao.InventarioDAO;
import com.icp.sigipro.bodegas.dao.SolicitudDAO;
import com.icp.sigipro.bodegas.modelos.Inventario;
import com.icp.sigipro.bodegas.modelos.Solicitud;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.sasl.AuthenticationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorSolicitudes", urlPatterns = {"/Bodegas/Solicitudes"})
public class ControladorSolicitudes extends SIGIPROServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
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
      String redireccion = "";
      String accion = request.getParameter("accion");
      SolicitudDAO dao = new SolicitudDAO();
      HttpSession sesion = request.getSession();
      List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
      int[] permisos = {24, 25, 1};
      int usuario_solicitante;
      UsuarioDAO usrDAO = new UsuarioDAO();
      boolean boolAdmin = false;
      if (verificarPermiso(25, listaPermisos))
        { usuario_solicitante = 0; 
          boolAdmin = true;}
      else
      {   String nombre_usr = (String) sesion.getAttribute("usuario");
          usuario_solicitante = usrDAO.obtenerIDUsuario(nombre_usr);
      }

      if (accion != null) {
        validarPermisos(permisos, listaPermisos);
        if (accion.equalsIgnoreCase("ver")) {
          redireccion = "Solicitudes/Ver.jsp";
          int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
          Solicitud solicitud = dao.obtenerSolicitud(id_solicitud);
          request.setAttribute("solicitud", solicitud);
        }
        else if (accion.equalsIgnoreCase("agregar")) {
          redireccion = "Solicitudes/Agregar.jsp";
          Solicitud solicitud = new Solicitud();
          InventarioDAO inventarioDAO = new InventarioDAO();
          Usuario usr = usrDAO.obtenerUsuario(usrDAO.obtenerIDUsuario((String) sesion.getAttribute("usuario")));
          List<Inventario> inventarios = inventarioDAO.obtenerInventarios(usr.getIdSeccion() );
          request.setAttribute("inventarios", inventarios);
          request.setAttribute("solicitud", solicitud);
          request.setAttribute("accion", "Agregar");
        }
        else if (accion.equalsIgnoreCase("eliminar")) {
          int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
          dao.eliminarSolicitud(id_solicitud);
          redireccion = "Solicitudes/index.jsp";
          List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
          request.setAttribute("listaSolicitudes", solicitudes);
          HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
          request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud eliminada correctamente"));
   
        }
        else if (accion.equalsIgnoreCase("editar")) {
          redireccion = "Solicitudes/Editar.jsp";
          int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
          Solicitud solicitud = dao.obtenerSolicitud(id_solicitud);
          InventarioDAO inventarioDAO = new InventarioDAO();
          List<Inventario> inventarios = inventarioDAO.obtenerInventarios(usrDAO.obtenerUsuario(usuario_solicitante).getIdSeccion() );
          request.setAttribute("inventarios", inventarios);
          request.setAttribute("solicitud", solicitud);
          request.setAttribute("accion", "Editar");
        }
        else if (accion.equalsIgnoreCase("aprobar")) {
          redireccion = "Solicitudes/index.jsp";
          int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
          Solicitud solicitud = dao.obtenerSolicitud(id_solicitud);
          solicitud.setEstado("Aprobada");
          HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
          boolean resultado;
          resultado = dao.editarSolicitud(solicitud);     
          if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud aprobada"));
          } 
          else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
          }

          List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
          request.setAttribute("booladmin", boolAdmin);
          request.setAttribute("listaSolicitudes", solicitudes);
        } 
        else if (accion.equalsIgnoreCase("rechazar")) {
          redireccion = "Solicitudes/index.jsp";
          int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
          Solicitud solicitud = dao.obtenerSolicitud(id_solicitud);
          solicitud.setEstado("Rechazada");
          HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
          boolean resultado;
          resultado = dao.editarSolicitud(solicitud);
          if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud rechazada"));
          } 
          else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
          }
          List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
          request.setAttribute("booladmin", boolAdmin);
          request.setAttribute("listaSolicitudes", solicitudes);
        } 
        else {
          validarPermisos(permisos, listaPermisos);
          redireccion = "Solicitudes/index.jsp";
          List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
          request.setAttribute("booladmin", boolAdmin);
          request.setAttribute("listaSolicitudes", solicitudes);
        }
      }
      else {
        validarPermisos(permisos, listaPermisos);
        redireccion = "Solicitudes/index.jsp";
        List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante); 
        request.setAttribute("booladmin", boolAdmin);
        request.setAttribute("listaSolicitudes", solicitudes);
      }

      RequestDispatcher vista = request.getRequestDispatcher(redireccion);
      vista.forward(request, response);
    }
    catch (AuthenticationException ex){
      RequestDispatcher vista = request.getRequestDispatcher("/index.jsp");
      vista.forward(request, response);
    }
  }

  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    
    String redireccion;
    SolicitudDAO dao = new SolicitudDAO();
    UsuarioDAO usrDAO = new UsuarioDAO();
    boolean boolAdmin = false;
    int usuario_solicitante;
    HttpSession sesion = request.getSession();
    List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
    int[] permisos = {24, 25, 1};
    if (verificarPermiso(25, listaPermisos))
      { boolAdmin = true;
        usuario_solicitante = 0;
      }
    else
      {   String nombre_usr = (String) sesion.getAttribute("usuario");
          usuario_solicitante = usrDAO.obtenerIDUsuario(nombre_usr);
      }
    String accionindex = request.getParameter("accionindex");
    if (accionindex.equals("")){      
      request.setCharacterEncoding("UTF-8");
      boolean resultado = false;
      HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

      Solicitud solicitud = new Solicitud();
      Integer id_producto = Integer.parseInt(request.getParameter("seleccionproducto"));
      Integer cantidad = Integer.parseInt(request.getParameter("cantidad"));
      String estado = request.getParameter("estado");
      String fch_sol =  request.getParameter("fecha_solicitud");
      String fch_ent =  request.getParameter("fecha_entrega");
      Integer id_us = Integer.parseInt(request.getParameter("id_usuario"));
      Integer id_us_recibo = Integer.parseInt(request.getParameter("id_usuario_recibo"));

      SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
      java.util.Date fecha_solicitud;
      java.sql.Date fecha_solicitudSQL;
      try {
        fecha_solicitud = formatoFecha.parse(fch_sol);
        fecha_solicitudSQL = new java.sql.Date(fecha_solicitud.getTime());
        solicitud.setFecha_solicitud(fecha_solicitudSQL);
      } catch (ParseException ex) {
        Logger.getLogger(ControladorSolicitudes.class.getName()).log(Level.SEVERE, null, ex);
      }
      java.util.Date fecha_entrega;
      java.sql.Date fecha_entregaSQL;
      try {
        fecha_entrega = formatoFecha.parse(fch_ent);
        fecha_entregaSQL = new java.sql.Date(fecha_entrega.getTime());
        solicitud.setFecha_entrega(fecha_entregaSQL);
      } catch (ParseException ex) {
        Logger.getLogger(ControladorSolicitudes.class.getName()).log(Level.SEVERE, null, ex);
      }

      solicitud.setId_producto(id_producto);
      solicitud.setCantidad(cantidad);
      solicitud.setEstado(estado);
      solicitud.setId_usuario(id_us);
      solicitud.setId_usuario_recibo(id_us_recibo);

      String id = request.getParameter("id_solicitud");

      if (id.isEmpty() || id.equals("0")) {
        java.util.Date hoy = new java.util.Date();
        Date hoysql = new Date(hoy.getTime());
        String nombre_usr = (String) sesion.getAttribute("usuario");
        int usuario_solicitante2 = usrDAO.obtenerIDUsuario(nombre_usr);
        solicitud.setId_usuario(usuario_solicitante2);
        solicitud.setFecha_solicitud(hoysql);
        solicitud.setEstado("Pendiente");
        resultado = dao.insertarSolicitud(solicitud);
        redireccion = "Solicitudes/Agregar.jsp";
        request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud ingresada correctamente"));
      }
      else {
        solicitud.setId_solicitud(Integer.parseInt(id));
        resultado = dao.editarSolicitud(solicitud);
        redireccion = "Solicitudes/Editar.jsp";
        request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud editada correctamente"));
      }

      if (resultado) {
        redireccion = "Solicitudes/index.jsp";
        request.setAttribute("booladmin", boolAdmin);
        List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
        request.setAttribute("listaSolicitudes", solicitudes);
         }
      else{
        request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
      }

      request.setAttribute("solicitud", solicitud);
      RequestDispatcher vista = request.getRequestDispatcher(redireccion);
      vista.forward(request, response);
    }
    else
    {
          redireccion = "Solicitudes/index.jsp";
          String usuario = request.getParameter("usr");
          String contrasena = request.getParameter("passw");
          int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud_auth2"));
          Solicitud solicitud = dao.obtenerSolicitud(id_solicitud);
          int id_seccion = solicitud.getUsuario().getIdSeccion();
          boolean auth = usrDAO.AutorizarRecibo(usuario, contrasena, id_seccion);
           HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
          if (auth) {
            java.util.Date hoy = new java.util.Date();
            Date hoysql = new Date(hoy.getTime());
            int id_us_recibo = usrDAO.obtenerIDUsuario(usuario);
            solicitud.setFecha_entrega(hoysql);
            solicitud.setId_usuario_recibo(id_us_recibo);
            solicitud.setEstado("Entregada");
            boolean resultado;
            resultado = dao.editarSolicitud(solicitud);
            if (resultado) {
              request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud entregada"));
            } 
            else {
              request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
            }
          }
          else
          { request.setAttribute("mensaje_auth", helper.mensajeDeError("El usuario o contraseña son incorrectos, o el usuario no pertenece a la sección solicitante."));
            request.setAttribute("id_solicitud_authent", id_solicitud);
            request.setAttribute("show_modal_auth", true);

          }
          List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
          request.setAttribute("booladmin", boolAdmin);
          request.setAttribute("listaSolicitudes", solicitudes);
          RequestDispatcher vista = request.getRequestDispatcher(redireccion);
          vista.forward(request, response);
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
 protected int getPermiso()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
