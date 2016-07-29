/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.calendario.controlador;

import com.icp.sigipro.calendario.dao.EventoDAO;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.calendario.modelos.Evento;
import com.icp.sigipro.configuracion.dao.SeccionDAO;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.RolDAO;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Rol;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorCalendario", urlPatterns = {"/Calendario/Calendario"})
public class ControladorCalendario extends SIGIPROServlet {

  private final int[] permisos = {5000, 1, 1};
  private EventoDAO dao = new EventoDAO();
  private UsuarioDAO dao_us = new UsuarioDAO();
  private SeccionDAO dao_sec = new SeccionDAO();
  private RolDAO dao_rol = new RolDAO();
  private HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

  protected final Class clase = ControladorCalendario.class;
  protected final List<String> accionesGet = new ArrayList<String>() {
    {
      add("index");
      add("agregar");
    }
  };
  protected final List<String> accionesPost = new ArrayList<String>() {
    {
      add("agregar");
      add("eliminar");
    }
  };
  protected final List<String> opcionesCompartir = new ArrayList<String>() {
    {
      add("Secciones");
      add("Usuarios");
      add("Roles");
      add("Todos");
    }
  };
   // <editor-fold defaultstate="collapsed" desc="Métodos Get">

  protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String redireccion = "Calendario/Agregar.jsp";
    request.setAttribute("accion", "Agregar");

    redireccionar(request, response, redireccion);
  }

  protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String redireccion = "Calendario/index.jsp";
    HttpSession sesion = request.getSession();
    String nombre_usr = (String) sesion.getAttribute("usuario");
    Usuario us = dao_us.obtenerUsuario(nombre_usr);
    try {
      String json = dao.getEventos(us);
      request.setAttribute("eventos", json);
    } catch (SIGIPROException e) {
      request.setAttribute("mensaje", helper.mensajeDeError(e.getMessage()));
    }
    List<Seccion> secciones = dao_sec.obtenerSecciones();
    List<Usuario> usuarios = dao_us.obtenerUsuarios();
    List<Rol> roles = dao_rol.obtenerRoles();
    
    //mesajes para POSTS
    try{
    String tipom = request.getParameter("tipo");
    if (tipom.equals("exito"))
    {request.setAttribute("mensaje",helper.mensajeDeExito(request.getParameter("mensaje")));}
    else
    {request.setAttribute("mensaje",helper.mensajeDeError(request.getParameter("mensaje")));}
    }
    catch (Exception e){}
    request.setAttribute("opcionesCompartir", opcionesCompartir);
    request.setAttribute("secciones", secciones);
    request.setAttribute("usuarios", usuarios);
    request.setAttribute("roles", roles);
    redireccionar(request, response, redireccion);
  }

  // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos Post">
  protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //index
    String redireccion = "Calendario/index.jsp";
    HttpSession sesion = request.getSession();
    String nombre_usr = (String) sesion.getAttribute("usuario");
    Usuario us = dao_us.obtenerUsuario(nombre_usr);
    try {
      String json = dao.getEventos(us);
      request.setAttribute("eventos", json);
    } catch (SIGIPROException e) {
      request.setAttribute("mensaje", helper.mensajeDeError(e.getMessage()));
    }
    List<Seccion> secciones = dao_sec.obtenerSecciones();
    List<Usuario> usuarios = dao_us.obtenerUsuarios();
    List<Rol> roles = dao_rol.obtenerRoles();
   //index
    boolean resultado = false;
    try {
      Evento evento = construirObjeto(request);
      String type = request.getParameter("whotoshare");
      String[] ids;
      Boolean shared = Boolean.parseBoolean(request.getParameter("shared"));
      if (shared){
        if (type.equals("Usuarios"))
        {ids= request.getParameterValues("usuarios");}
        else if (type.equals("Secciones"))
        {ids= request.getParameterValues("secciones");}
        else if (type.equals("Roles"))
        {ids= request.getParameterValues("roles");}
        else
        {ids=null;}
        }
      else {
        type="na";
        ids=null;
      }
      
      resultado = dao.insertarEvento(evento, us.getID(), shared, type, ids);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(evento.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_EVENTO, request.getRemoteAddr());
      //*----------------------------*
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    if (resultado) {
        redireccion= "Calendario?&tipo=exito&mensaje=" + "Evento agregado correctamente";
        request.setAttribute("mensaje", helper.mensajeDeExito("Evento agregado con éxito"));
    } else {
      redireccion= "Calendario?&tipo=error&mensaje=" + "Hubo un error al agregar el evento";
      request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
    }
    response.sendRedirect(redireccion);
    //redireccionar(request, response, redireccion);
  }
 protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //index
    String redireccion = "Calendario/index.jsp";
    HttpSession sesion = request.getSession();
    String nombre_usr = (String) sesion.getAttribute("usuario");
    Usuario us = dao_us.obtenerUsuario(nombre_usr);
    try {
      String json = dao.getEventos(us);
      request.setAttribute("eventos", json);
    } catch (SIGIPROException e) {
      request.setAttribute("mensaje", helper.mensajeDeError(e.getMessage()));
    }
    List<Seccion> secciones = dao_sec.obtenerSecciones();
    List<Usuario> usuarios = dao_us.obtenerUsuarios();
    List<Rol> roles = dao_rol.obtenerRoles();

   //index
    int id_evento = Integer.parseInt(request.getParameter("id_evento_eliminar"));
    try {
      dao.eliminarEvento(id_evento);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_evento, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_EVENTO, request.getRemoteAddr());
      //*----------------------------*
      redireccion= "Calendario?&tipo=exito&mensaje=" + "Evento eliminado correctamente";
    } catch (SIGIPROException ex) {
      redireccion= "Calendario?&tipo=error&mensaje=" + "Hubo un error al eliminar el evento";
    }
    response.sendRedirect(redireccion);
    //redireccionar(request, response, redireccion);
  }
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  private Evento construirObjeto(HttpServletRequest request) throws SIGIPROException {
    Evento evento = new Evento();

    evento.setAllDay(Boolean.parseBoolean(request.getParameter("allday")));
    evento.setTitle(request.getParameter("title"));
    evento.setDescription(request.getParameter("description"));
    
    if (!evento.getAllDay()) {

      String timestamp_start = request.getParameter("start_date") + " " + request.getParameter("start_time") + ":00";
      String timestamp_end = request.getParameter("end_date") + " " + request.getParameter("end_time") + ":00";
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

      try {
        Date parsedDate = dateFormat.parse(timestamp_start);
        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
        evento.setStart_date(timestamp);
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        Date parsedDate2 = dateFormat.parse(timestamp_end);
        Timestamp timestamp2 = new java.sql.Timestamp(parsedDate2.getTime());
        evento.setEnd_date(timestamp2);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
    String timestamp_start = request.getParameter("start_date") + " " + "00:00:00"; 
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

      try {
        Date parsedDate = dateFormat.parse(timestamp_start);
        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
        evento.setStart_date(timestamp);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return evento;
    }

  

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
  @Override
  protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    List<String> lista_acciones;
    if (accionHTTP.equals("get")) {
      lista_acciones = accionesGet;
    } else {
      lista_acciones = accionesPost;
    }
    if (lista_acciones.contains(accion.toLowerCase())) {
      String nombreMetodo = accionHTTP + Character.toUpperCase(accion.charAt(0)) + accion.substring(1);
      Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class);
      metodo.invoke(this, request, response);
    } else {
      Method metodo = clase.getDeclaredMethod(accionHTTP + "Index", HttpServletRequest.class, HttpServletResponse.class);
      metodo.invoke(this, request, response);
    }
  }

  @Override
  protected int getPermiso() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  // </editor-fold>
}
