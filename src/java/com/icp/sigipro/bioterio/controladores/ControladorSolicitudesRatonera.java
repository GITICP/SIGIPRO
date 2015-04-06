/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.controladores;

import com.icp.sigipro.bioterio.dao.CepaDAO;
import com.icp.sigipro.bioterio.dao.EntregaRatoneraDAO;
import com.icp.sigipro.bioterio.dao.SolicitudRatoneraDAO;
import com.icp.sigipro.bioterio.modelos.Cepa;
import com.icp.sigipro.bioterio.modelos.EntregaRatonera;
import com.icp.sigipro.bioterio.modelos.SolicitudRatonera;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorSolicitudesRatonera", urlPatterns = {"/Ratonera/SolicitudesRatonera"})
public class ControladorSolicitudesRatonera extends SIGIPROServlet {

  private final int[] permisos = {205, 203, 1};
  private SolicitudRatoneraDAO dao = new SolicitudRatoneraDAO();
  private EntregaRatoneraDAO dao_en = new EntregaRatoneraDAO();
  private CepaDAO dao_ce = new CepaDAO();
  private UsuarioDAO dao_us = new UsuarioDAO();
  private HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
  private boolean admin = false;

  protected final Class clase = ControladorSolicitudesRatonera.class;
  protected final List<String> accionesGet = new ArrayList<String>() {
    {
      add("index");
      add("ver");
      add("agregar");
      add("editar");
      add("eliminar");
      add("aprobar");
      add("verentrega");
    }
  };
  protected final List<String> accionesPost = new ArrayList<String>() {
    {
      add("agregar");
      add("editar");
      add("entregar");
      add("rechazar");
    }
  };

  protected final List<String> pesos = new ArrayList<String>() {
    {
      add("16-18");
      add("18-20");
      add("20-22");
      add("+22");
      add("Otro");

    }
  };
  protected final List<String> sexos = new ArrayList<String>() {
    {
      add("Mixto");
      add("Hembras");
      add("Machos");

    }
  };

  // <editor-fold defaultstate="collapsed" desc="Métodos Get">
  protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermiso(205, listaPermisos);
    try {
      List<Cepa> cepas = dao_ce.obtenerCepas();
      request.setAttribute("cepas", cepas);
    } catch (SIGIPROException ex) {
      Logger.getLogger(ControladorSolicitudesRatonera.class.getName()).log(Level.SEVERE, null, ex);
    }
    request.setAttribute("pesos", pesos);
    request.setAttribute("sexos", sexos);
    String redireccion = "SolicitudesRatonera/Agregar.jsp";
    SolicitudRatonera ds = new SolicitudRatonera();
    request.setAttribute("solicitud", ds);
    request.setAttribute("accion", "Agregar");

    redireccionar(request, response, redireccion);
  }

  protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    admin = verificarPermiso(203, listaPermisos);
    request.setAttribute("admin", admin);
    String redireccion = "SolicitudesRatonera/index.jsp";
    List<SolicitudRatonera> solicitudes_ratonera;
    request.setAttribute("pesos", pesos);
    request.setAttribute("sexos", sexos);
    if (admin) {
      try {
        List<Cepa> cepas = dao_ce.obtenerCepas();
        request.setAttribute("cepas", cepas);
        solicitudes_ratonera = dao.obtenerSolicitudesRatoneraAdm();
        request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
      } catch (SIGIPROException ex) {
        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
      }
    } else {
      try {
        List<Cepa> cepas = dao_ce.obtenerCepas();
        request.setAttribute("cepas", cepas);
        HttpSession sesion = request.getSession();
        String nombre_usr = (String) sesion.getAttribute("usuario");
        int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
        solicitudes_ratonera = dao.obtenerSolicitudesRatonera(id_usuario);
        request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
      } catch (SIGIPROException ex) {
        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
      }
    }
    redireccionar(request, response, redireccion);
  }

  protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    admin = verificarPermiso(203, listaPermisos);
    request.setAttribute("admin", admin);
    String redireccion = "SolicitudesRatonera/Ver.jsp";
    int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
    try {
      SolicitudRatonera s = dao.obtenerSolicitudRatonera(id_solicitud);
      List<EntregaRatonera> e = dao_en.obtenerEntregasRatonera(id_solicitud);
      request.setAttribute("solicitud", s);
      request.setAttribute("entregas", e);
    } catch (Exception ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }
  protected void getVerentrega(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    admin = verificarPermiso(203, listaPermisos);
    request.setAttribute("admin", admin);
    String redireccion = "SolicitudesRatonera/VerEntrega.jsp";
    int id_entrega = Integer.parseInt(request.getParameter("id_entrega"));
    try {
      EntregaRatonera s = dao_en.obtenerEntregaRatonera(id_entrega);
      request.setAttribute("entrega", s);
    } catch (Exception ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "SolicitudesRatonera/Editar.jsp";
    int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
    request.setAttribute("accion", "Editar");
    request.setAttribute("pesos", pesos);
    request.setAttribute("sexos", sexos);
    try {
      SolicitudRatonera s = dao.obtenerSolicitudRatonera(id_solicitud);
      request.setAttribute("solicitud", s);
      List<Cepa> cepas = dao_ce.obtenerCepas();
      request.setAttribute("cepas", cepas);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
    admin = verificarPermiso(203, listaPermisos);
    request.setAttribute("admin", admin);
    request.setAttribute("pesos", pesos);
    request.setAttribute("sexos", sexos);
    try {
      List<Cepa> cepas = dao_ce.obtenerCepas();
      request.setAttribute("cepas", cepas);
      dao.eliminarSolicitudRatonera(id_solicitud);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_solicitud, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------* 
      request.setAttribute("mensaje", helper.mensajeDeExito("SolicitudRatonera eliminada correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    try {
      List<SolicitudRatonera> solicitudes_ratonera;
      if (admin) {
        try {
          solicitudes_ratonera = dao.obtenerSolicitudesRatoneraAdm();
          request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
        } catch (SIGIPROException ex) {
          request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
      } else {
        HttpSession sesion = request.getSession();
        String nombre_usr = (String) sesion.getAttribute("usuario");
        int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
        solicitudes_ratonera = dao.obtenerSolicitudesRatonera(id_usuario);
        request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
      }
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    String redireccion = "SolicitudesRatonera/index.jsp";
    redireccionar(request, response, redireccion);
  }

  protected void getAprobar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
    boolean resultado = false;
    admin = verificarPermiso(203, listaPermisos);
    request.setAttribute("admin", admin);
    request.setAttribute("pesos", pesos);
    request.setAttribute("sexos", sexos);
    try {
      List<Cepa> cepas = dao_ce.obtenerCepas();
      request.setAttribute("cepas", cepas);
      SolicitudRatonera solicitud = dao.obtenerSolicitudRatonera(id_solicitud);
      solicitud.setEstado("Aprobada");
      resultado = dao.editarSolicitudRatonera(solicitud);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_solicitud, Bitacora.ACCION_APROBAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------* 

      if (resultado) {
        request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud aprobada"));
      } else {
        request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
      }
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    try {
      List<SolicitudRatonera> solicitudes_ratonera;
      if (admin) {
        try {
          solicitudes_ratonera = dao.obtenerSolicitudesRatoneraAdm();
          request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
        } catch (SIGIPROException ex) {
          request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
      } else {
        HttpSession sesion = request.getSession();
        String nombre_usr = (String) sesion.getAttribute("usuario");
        int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
        solicitudes_ratonera = dao.obtenerSolicitudesRatonera(id_usuario);
        request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
      }
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    String redireccion = "SolicitudesRatonera/index.jsp";
    redireccionar(request, response, redireccion);
  }

  // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos Post">
  protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    boolean resultado = false;
    String redireccion = "SolicitudesRatonera/Agregar.jsp";
    List<Integer> listaPermisos = getPermisosUsuario(request);
    admin = verificarPermiso(203, listaPermisos);
    request.setAttribute("admin", admin);
    request.setAttribute("pesos", pesos);
    request.setAttribute("sexos", sexos);
    try {
      SolicitudRatonera solicitud = construirObjeto(request);
      solicitud.setEstado("Pendiente");
      resultado = dao.insertarSolicitudRatonera(solicitud);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(solicitud.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------*
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", ex.getMessage());
    }
    if (resultado) {
      redireccion = "SolicitudesRatonera/index.jsp";
      List<SolicitudRatonera> solicitudes_ratonera;
      try {
        List<Cepa> cepas = dao_ce.obtenerCepas();
        request.setAttribute("cepas", cepas);
        if (admin) {
          try {
            solicitudes_ratonera = dao.obtenerSolicitudesRatoneraAdm();
            request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
          } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
          }
        } else {
          HttpSession sesion = request.getSession();
          String nombre_usr = (String) sesion.getAttribute("usuario");
          int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
          solicitudes_ratonera = dao.obtenerSolicitudesRatonera(id_usuario);
          request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
        }
        request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud agregada con éxito"));
      } catch (SIGIPROException ex) {
        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
      }
    } else {
      request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
    }
    redireccionar(request, response, redireccion);
  }

  protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    boolean resultado = false;
    String redireccion = "SolicitudesRatonera/Editar.jsp";
    List<Integer> listaPermisos = getPermisosUsuario(request);
    admin = verificarPermiso(203, listaPermisos);
    request.setAttribute("admin", admin);
    request.setAttribute("pesos", pesos);
    request.setAttribute("sexos", sexos);
    try {
      SolicitudRatonera solicitud = construirObjeto(request);
      solicitud.setEstado("Pendiente");
      resultado = dao.editarSolicitudRatonera(solicitud);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(solicitud.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------*
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", ex.getMessage());
    }
    if (resultado) {
      redireccion = "SolicitudesRatonera/index.jsp";
      List<SolicitudRatonera> solicitudes_ratonera;
      try {
        List<Cepa> cepas = dao_ce.obtenerCepas();
        request.setAttribute("cepas", cepas);
        if (admin) {
          try {
            solicitudes_ratonera = dao.obtenerSolicitudesRatoneraAdm();
            request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
          } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
          }
        } else {
          HttpSession sesion = request.getSession();
          String nombre_usr = (String) sesion.getAttribute("usuario");
          int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
          solicitudes_ratonera = dao.obtenerSolicitudesRatonera(id_usuario);
          request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
        }
        request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud editada con éxito"));

      } catch (SIGIPROException ex) {
        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
      }
    } else {
      request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
    }
    redireccionar(request, response, redireccion);
  }

    protected void postRechazar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud_rech"));
    String obs = request.getParameter("observaciones_rechazo");
    boolean resultado = false;
    admin = verificarPermiso(203, listaPermisos);
    request.setAttribute("admin", admin);
    request.setAttribute("pesos", pesos);
    request.setAttribute("sexos", sexos);
    try {
      List<Cepa> cepas = dao_ce.obtenerCepas();
      request.setAttribute("cepas", cepas);
      SolicitudRatonera solicitud = dao.obtenerSolicitudRatonera(id_solicitud);
      solicitud.setEstado("Rechazada");
      solicitud.setObservaciones_rechazo(obs);
      resultado = dao.editarSolicitudRatonera(solicitud);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_solicitud, Bitacora.ACCION_APROBAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------* 

      if (resultado) {
        request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud rechazada"));
      } else {
        request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
      }
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    try {
      List<SolicitudRatonera> solicitudes_ratonera;
      if (admin) {
        try {
          solicitudes_ratonera = dao.obtenerSolicitudesRatoneraAdm();
          request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
        } catch (SIGIPROException ex) {
          request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
      } else {
        HttpSession sesion = request.getSession();
        String nombre_usr = (String) sesion.getAttribute("usuario");
        int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
        solicitudes_ratonera = dao.obtenerSolicitudesRatonera(id_usuario);
        request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
      }
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    String redireccion = "SolicitudesRatonera/index.jsp";
    redireccionar(request, response, redireccion);
    }
    protected void postEntregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud_auth2"));
    boolean resultado = false;
    boolean resultado2 = false;
    admin = verificarPermiso(203, listaPermisos);
    request.setAttribute("admin", admin);
    request.setAttribute("pesos", pesos);
    request.setAttribute("sexos", sexos);
    try {
      List<Cepa> cepas = dao_ce.obtenerCepas();
      request.setAttribute("cepas", cepas);
      String usuario = request.getParameter("usr");
      String contrasena = request.getParameter("passw");
      SolicitudRatonera solicitud = dao.obtenerSolicitudRatonera(id_solicitud);
      EntregaRatonera entrega = construirSubObjeto(request);
      boolean auth = dao_us.AutorizarRecibo(usuario, contrasena);
      if (auth) {
            int id_us_recibo = dao_us.obtenerIDUsuario(usuario);
            entrega.setUsuario_recipiente(dao_us.obtenerUsuario(id_us_recibo));
            solicitud.setEstado("Abierta");
            resultado = dao.editarSolicitudRatonera(solicitud);
            resultado2 = dao_en.insertarEntregaRatonera(entrega);     
      }
      else
          { request.setAttribute("mensaje_auth", helper.mensajeDeError("El usuario o contraseña son incorrectos"));}
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_solicitud, Bitacora.ACCION_ENTREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------* 

      if (resultado && resultado2) {
        request.setAttribute("mensaje", helper.mensajeDeExito("Entrega registrada."));
      } else {
        request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
      }
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    try {
      List<SolicitudRatonera> solicitudes_ratonera;
      if (admin) {
        try {
          solicitudes_ratonera = dao.obtenerSolicitudesRatoneraAdm();
          request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
        } catch (SIGIPROException ex) {
          request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
      } else {
        HttpSession sesion = request.getSession();
        String nombre_usr = (String) sesion.getAttribute("usuario");
        int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
        solicitudes_ratonera = dao.obtenerSolicitudesRatonera(id_usuario);
        request.setAttribute("listaSolicitudesRatonera", solicitudes_ratonera);
      }
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    String redireccion = "SolicitudesRatonera/index.jsp";
    redireccionar(request, response, redireccion);
  }
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  private SolicitudRatonera construirObjeto(HttpServletRequest request) throws SIGIPROException {
    SolicitudRatonera solicitud = new SolicitudRatonera();
    solicitud.setId_solicitud(Integer.parseInt(request.getParameter("id_solicitud")));
    if (request.getParameter("fecha_solicitud").equals("")) {
      java.util.Date fecha_solicitud = new java.util.Date();
      java.sql.Date fecha_solicitudSQL;
      fecha_solicitudSQL = new java.sql.Date(fecha_solicitud.getTime());
      solicitud.setFecha_solicitud(fecha_solicitudSQL);
    } else {
      String fch_sol = request.getParameter("fecha_solicitud");
      SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
      java.util.Date fecha_solicitud;
      java.sql.Date fecha_solicitudSQL;
      try {
        fecha_solicitud = formatoFecha.parse(fch_sol);
        fecha_solicitudSQL = new java.sql.Date(fecha_solicitud.getTime());
        solicitud.setFecha_solicitud(fecha_solicitudSQL);
      } catch (ParseException ex) {
        Logger.getLogger(ControladorSolicitudesRatonera.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    solicitud.setNumero_animales(Integer.parseInt(request.getParameter("numero_animales")));
    solicitud.setPeso_requerido(request.getParameter("peso_requerido"));
    solicitud.setNumero_cajas(Integer.parseInt(request.getParameter("numero_cajas")));
    solicitud.setSexo(request.getParameter("sexo"));
    solicitud.setCepa(dao_ce.obtenerCepa(Integer.parseInt(request.getParameter("id_cepa"))));
    solicitud.setObservaciones(request.getParameter("observaciones"));
    if (request.getParameter("usuario_solicitante").equals("")) {
      HttpSession sesion = request.getSession();
      String nombre_usr = (String) sesion.getAttribute("usuario");
      int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
      solicitud.setUsuario_solicitante(dao_us.obtenerUsuario(id_usuario));
    } else {
      int id_usuario = Integer.parseInt(request.getParameter("usuario_solicitante"));
      solicitud.setUsuario_solicitante(dao_us.obtenerUsuario(id_usuario));
    }

    return solicitud;
  }

  private EntregaRatonera construirSubObjeto(HttpServletRequest request) throws SIGIPROException {
    EntregaRatonera entrega = new EntregaRatonera();
    entrega.setSolicitud(dao.obtenerSolicitudRatonera(Integer.parseInt(request.getParameter("id_solicitud_auth2"))));
    java.util.Date fecha_solicitud = new java.util.Date();
    java.sql.Date fecha_solicitudSQL;
    fecha_solicitudSQL = new java.sql.Date(fecha_solicitud.getTime());
    entrega.setFecha_entrega(fecha_solicitudSQL);
    entrega.setNumero_animales(Integer.parseInt(request.getParameter("num_an1")));
    entrega.setPeso(request.getParameter("peso1"));
    entrega.setNumero_cajas(Integer.parseInt(request.getParameter("cajas1")));
    entrega.setSexo(request.getParameter("sex1"));
    entrega.setCepa(dao_ce.obtenerCepa(Integer.parseInt(request.getParameter("cepa1"))));
    //HttpSession sesion = request.getSession();
    //String nombre_usr = (String) sesion.getAttribute("usuario");
    //int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
    //entrega.setUsuario_recipiente(dao_us.obtenerUsuario(id_usuario));

    return entrega;
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
