/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.controladores;

import com.icp.sigipro.produccion.modelos.Inventario_PT;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.produccion.dao.Catalogo_PTDAO;
import com.icp.sigipro.produccion.dao.DespachoDAO;
import com.icp.sigipro.produccion.dao.Despachos_inventarioDAO;
import com.icp.sigipro.produccion.dao.Inventario_PTDAO;
import com.icp.sigipro.produccion.dao.ProtocoloDAO;
import com.icp.sigipro.produccion.dao.ReservacionDAO;
import com.icp.sigipro.produccion.dao.Reservaciones_inventarioDAO;
import com.icp.sigipro.produccion.dao.Salida_ExtDAO;
import com.icp.sigipro.produccion.dao.Salidas_inventarioDAO;
import com.icp.sigipro.produccion.modelos.Catalogo_PT;
import com.icp.sigipro.produccion.modelos.Despacho;
import com.icp.sigipro.produccion.modelos.Despachos_inventario;
import com.icp.sigipro.produccion.modelos.Protocolo;
import com.icp.sigipro.produccion.modelos.Reservacion;
import com.icp.sigipro.produccion.modelos.Reservaciones_inventario;
import com.icp.sigipro.produccion.modelos.Salida_Ext;
import com.icp.sigipro.produccion.modelos.Salidas_inventario;
import com.icp.sigipro.utilidades.HelperFechas;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorInventario_PT", urlPatterns = {"/Produccion/Inventario_PT"})
public class ControladorInventario_PT extends SIGIPROServlet {

  private final int[] permisos = {602, 607, 603};
  private final Inventario_PTDAO dao = new Inventario_PTDAO();
  private final DespachoDAO despacho_dao = new DespachoDAO();
  private final Despachos_inventarioDAO despachos_inventario_dao = new Despachos_inventarioDAO();
  private final ReservacionDAO reservacion_dao = new ReservacionDAO();
  private final Reservaciones_inventarioDAO reservaciones_inventario_dao = new Reservaciones_inventarioDAO();
  private final Salida_ExtDAO salida_ext_dao = new Salida_ExtDAO();
  private final Salidas_inventarioDAO salidas_inventario_dao = new Salidas_inventarioDAO();
  private final HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
  private final ProtocoloDAO protocolo_dao = new ProtocoloDAO();
  private final Catalogo_PTDAO catalogo_pt_dao = new Catalogo_PTDAO();
  
  protected final Class clase = ControladorInventario_PT.class;
  protected final List<String> accionesGet = new ArrayList<String>() {
    {
      add("index");
      add("ver_inventario");
      add("ver_despacho");
      add("ver_reservacion");
      add("ver_salida");
      add("agregar_despacho");
      add("agregar_inventario");
      add("agregar_reservacion");
      add("agregar_salida");
      add("editar_despacho");
      add("editar_inventario");
      add("editar_salida");
      add("editar_reservacion");
      add("firmar_despacho");
    }
  };
  protected final List<String> accionesPost = new ArrayList<String>() {
    {
      add("agregar_despacho");
      add("agregar_inventario");
      add("agregar_reservacion");
      add("agregar_salida");
      add("editar_despacho");
      add("editar_inventario");
      add("editar_salida");
      add("editar_reservacion");
      add("eliminar_despacho");
      add("eliminar_inventario");
      add("eliminar_reservacion");
      add("eliminar_salida");
    }
  };
  protected final List<String> tipos_salidas = new ArrayList<String>() {
    {
      add("Pérdida");
      add("Destrucción");
      add("Uso Interno");
      add("Otro");
    }
  };

  // <editor-fold defaultstate="collapsed" desc="Método Index">
  protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("inv_tab", "active");
    request = request_index(request);
    String redireccion = "Inventario_PT/index.jsp"; 
    redireccionar(request, response, redireccion);

  }

  protected HttpServletRequest request_index(HttpServletRequest request) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    Boolean admin = true;
    Boolean coordinador = false;
    Boolean regente = false;
    if ((verificarPermiso(603, listaPermisos) || (verificarPermiso(607, listaPermisos))) && !(verificarPermiso(602, listaPermisos))) {
      admin = false;
      if (verificarPermiso(603, listaPermisos)) {
        regente = true;
        request.setAttribute("inv_tab", "");
        request.setAttribute("des_tab", "active");
      }
      if (verificarPermiso(607, listaPermisos)) {
        coordinador = true;
        request.setAttribute("inv_tab", "");
        request.setAttribute("des_tab", "active");
      }
    };
    request.setAttribute("admin", admin);
    request.setAttribute("regente", regente);
    request.setAttribute("coordinador", coordinador);

    try {
      List<Inventario_PT> inventario = dao.obtenerInventario_PTs();
      request.setAttribute("inventario", inventario);
      List<Inventario_PT> inventario_h = dao.obtenerInventario_PTs_H();
      request.setAttribute("inventario_h", inventario_h);
      List<Despacho> despachos = despacho_dao.obtenerDespachos();
      request.setAttribute("despachos", despachos);
      List<Reservacion> reservaciones = reservacion_dao.obtenerReservaciones();
      request.setAttribute("reservaciones", reservaciones);
      List<Salida_Ext> salidas = salida_ext_dao.obtenerSalida_Exts();
      request.setAttribute("salidas", salidas);
    } catch (SIGIPROException sig_ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
    }

    return request;
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Agregar">
  protected void getAgregar_inventario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Inventario_PT/Agregar_inventario.jsp";
    Inventario_PT inventario = new Inventario_PT();
    List<Protocolo> protocolos = protocolo_dao.obtenerProtocolos();
    List<Catalogo_PT> productos = catalogo_pt_dao.obtenerCatalogos_PT();
    request.setAttribute("protocolos", protocolos);
    request.setAttribute("productos", productos);
    request.setAttribute("inventario_pt", inventario);
    request.setAttribute("accion", "agregar_inventario");

    redireccionar(request, response, redireccion);
  }

  protected void getAgregar_despacho(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Inventario_PT/Agregar_despacho.jsp";
    Despacho despacho = new Despacho();
    List<Inventario_PT> lotes = dao.obtenerInventario_PTs();
    request.setAttribute("lotes", lotes);
    request.setAttribute("despacho", despacho);
    request.setAttribute("accion", "agregar_despacho");

    redireccionar(request, response, redireccion);
  }

  protected void getAgregar_reservacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Inventario_PT/Agregar_reservacion.jsp";
    Reservacion reservacion = new Reservacion();
    List<Inventario_PT> lotes = dao.obtenerInventario_PTs();
    request.setAttribute("lotes", lotes);
    request.setAttribute("reservacion", reservacion);
    request.setAttribute("accion", "agregar_reservacion");

    redireccionar(request, response, redireccion);
  }

  protected void getAgregar_salida(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Inventario_PT/Agregar_salida.jsp";
    Salida_Ext salida = new Salida_Ext();
    List<Inventario_PT> lotes = dao.obtenerInventario_PTs();
    request.setAttribute("tipos", tipos_salidas);
    request.setAttribute("lotes", lotes);
    request.setAttribute("salida", salida);
    request.setAttribute("accion", "agregar_salida");

    redireccionar(request, response, redireccion);
  }
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Editar">

  protected void getEditar_inventario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Inventario_PT/Editar_inventario.jsp";
    int id_inventario_pt = Integer.parseInt(request.getParameter("id_inventario_pt"));
    request.setAttribute("accion", "Editar_inventario");
    try {
      Inventario_PT inventario_pt = dao.obtenerInventario_PT(id_inventario_pt);
      request.setAttribute("inventario_pt", inventario_pt);
      List<Protocolo> protocolos = protocolo_dao.obtenerProtocolos();
      List<Catalogo_PT> productos = catalogo_pt_dao.obtenerCatalogos_PT();
      request.setAttribute("protocolos", protocolos);
      request.setAttribute("productos", productos);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  protected void getEditar_despacho(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Inventario_PT/Editar_despacho.jsp";
    int id_despacho = Integer.parseInt(request.getParameter("id_despacho"));
    request.setAttribute("accion", "Editar_despacho");
    try {
      Despacho despacho = despacho_dao.obtenerDespacho(id_despacho);
      request.setAttribute("despacho", despacho);
      List<Inventario_PT> lotes = dao.obtenerInventario_PTs();
      request.setAttribute("lotes", lotes);
      List<Despachos_inventario> despachos_inventarios = despachos_inventario_dao.obtenerDespachos_inventarios(id_despacho);
      request.setAttribute("despachos_inventarios", despachos_inventarios);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }
  
  protected void getFirmar_despacho(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    boolean resultado = false;
    String redireccion = "Inventario_PT/Index.jsp";
    int id_despacho = Integer.parseInt(request.getParameter("id_despacho"));
    int id_usuario = getIdUsuario(request);
    String tipo = request.getParameter("tipo");
    try {
      if (tipo.equals("c")) {
        despacho_dao.aprobar_Coordinador(id_usuario, id_despacho);
      } else {
        despacho_dao.aprobar_Regente(id_usuario, id_despacho);
      }
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_despacho, Bitacora.ACCION_APROBAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_DESPACHOS, request.getRemoteAddr());

      redireccion = "Inventario_PT/index.jsp";
      request = request_index(request);
      request.setAttribute("des_tab", "active");
      request.setAttribute("mensaje", helper.mensajeDeExito("Despacho firmado correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }

    redireccionar(request, response, redireccion);
  }
    protected void getEditar_reservacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Inventario_PT/Editar_reservacion.jsp";
    int id_reservacion = Integer.parseInt(request.getParameter("id_reservacion"));
    request.setAttribute("accion", "Editar_reservacion");
    try {
      Reservacion reservacion = reservacion_dao.obtenerReservacion(id_reservacion);
      request.setAttribute("reservacion", reservacion);
      List<Inventario_PT> lotes = dao.obtenerInventario_PTs();
      request.setAttribute("lotes", lotes);
      List<Reservaciones_inventario> reservaciones_inventarios = reservaciones_inventario_dao.obtenerReservaciones_inventarios(id_reservacion);
      request.setAttribute("reservaciones_inventarios", reservaciones_inventarios);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }
    protected void getEditar_salida(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Inventario_PT/Editar_salida.jsp";
    int id_salida = Integer.parseInt(request.getParameter("id_salida"));
    request.setAttribute("accion", "Editar_salida");
    try {
      Salida_Ext salida = salida_ext_dao.obtenerSalida_Ext(id_salida);
      request.setAttribute("salida", salida);
      List<Inventario_PT> lotes = dao.obtenerInventario_PTs();
      request.setAttribute("lotes", lotes);
      List<Salidas_inventario> salidas_inventarios = salidas_inventario_dao.obtenerSalidas_inventarios(id_salida);
      request.setAttribute("salidas_inventarios", salidas_inventarios);
      request.setAttribute("tipos", tipos_salidas);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Ver">

  protected void getVer_despacho(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Inventario_PT/Ver_despacho.jsp";
    int id_despacho = Integer.parseInt(request.getParameter("id_despacho"));
        try {
            Despacho despacho = despacho_dao.obtenerDespacho(id_despacho);
            request.setAttribute("despacho", despacho);
            List<Despachos_inventario> despachos_inventarios = despachos_inventario_dao.obtenerDespachos_inventarios(id_despacho);
            request.setAttribute("despachos_inventarios", despachos_inventarios);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
    redireccionar(request, response, redireccion);
  }
  protected void getVer_salida(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Inventario_PT/Ver_salida.jsp";
    int id_salida = Integer.parseInt(request.getParameter("id_salida"));
        try {
            Salida_Ext salida = salida_ext_dao.obtenerSalida_Ext(id_salida);
            request.setAttribute("salida", salida);
            List<Salidas_inventario> salidas_inventarios = salidas_inventario_dao.obtenerSalidas_inventarios(id_salida);
            request.setAttribute("salidas_inventarios", salidas_inventarios);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
    redireccionar(request, response, redireccion);
  }
  protected void getVer_reservacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Inventario_PT/Ver_reservacion.jsp";
    int id_reservacion = Integer.parseInt(request.getParameter("id_reservacion"));
        try {
            Reservacion reservacion = reservacion_dao.obtenerReservacion(id_reservacion);
            request.setAttribute("reservacion", reservacion);
            List<Reservaciones_inventario> reservaciones_inventarios = reservaciones_inventario_dao.obtenerReservaciones_inventarios(id_reservacion);
            request.setAttribute("reservaciones_inventarios", reservaciones_inventarios);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
    redireccionar(request, response, redireccion);
  }
  protected void getVer_inventario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Inventario_PT/Ver_inventario.jsp";
    int id_inventario_pt = Integer.parseInt(request.getParameter("id_inventario_pt"));
        try {
            Inventario_PT inventario = dao.obtenerInventario_PT(id_inventario_pt);
            request.setAttribute("inventario", inventario);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
    redireccionar(request, response, redireccion);
  }


  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Post Agregar">
  protected void postAgregar_inventario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    boolean resultado = false;
    String redireccion = "Inventario_PT/Agregar_inventario.jsp";
        try {
            Inventario_PT pie = construirInventario(request);

            dao.insertarInventario_PT(pie);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(pie.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_INVENTARIO_PT, request.getRemoteAddr());

            redireccion = "Inventario_PT/index.jsp";
            request = request_index(request);
            request.setAttribute("inv_tab", "active");
            request.setAttribute("mensaje", helper.mensajeDeExito("Entrada de inventario de producto terminado agregada correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
    redireccionar(request, response, redireccion);
  }

  protected void postAgregar_despacho(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    boolean resultado = false;
    String redireccion = "Inventario_PT/Agregar_despacho.jsp";
    try {
      Despacho despacho = construirDespacho(request);

      int id_despacho = despacho_dao.insertarDespacho(despacho);
      despacho.setId_despacho(id_despacho);
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(despacho.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_DESPACHOS, request.getRemoteAddr());

      ArrayList<int[]> lotes = construirLotes(request);
      despachos_inventario_dao.insertarDespachos_inventario(lotes, id_despacho);

      redireccion = "Inventario_PT/index.jsp";
      request = request_index(request);
      request.setAttribute("des_tab", "active");
      request.setAttribute("mensaje", helper.mensajeDeExito("Despacho agregado correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    } catch (NumberFormatException ex) {
      redireccion = "Inventario_PT/index.jsp";
      request = request_index(request);
      request.setAttribute("des_tab", "active");
      request.setAttribute("mensaje", helper.mensajeDeAdvertencia("Despacho agregado sin Lotes de Producto"));
    }

    redireccionar(request, response, redireccion);
  }
    protected void postAgregar_reservacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    boolean resultado = false;
    String redireccion = "Inventario_PT/Agregar_reservacion.jsp";
    try {
      Reservacion reservacion = construirReservacion(request);

      int id_reservacion = reservacion_dao.insertarReservacion(reservacion);
      reservacion.setId_reservacion(id_reservacion);
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(reservacion.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESERVACIONES, request.getRemoteAddr());

      ArrayList<int[]> lotes = construirLotes(request);
      reservaciones_inventario_dao.insertarReservaciones_inventario(lotes, id_reservacion);

      redireccion = "Inventario_PT/index.jsp";
      request = request_index(request);
      request.setAttribute("res_tab", "active");
      request.setAttribute("mensaje", helper.mensajeDeExito("Reservación agregada correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    } catch (NumberFormatException ex) {
      redireccion = "Inventario_PT/index.jsp";
      request = request_index(request);
      request.setAttribute("res_tab", "active");
      request.setAttribute("mensaje", helper.mensajeDeAdvertencia("Reservación agregada sin Lotes de Producto"));
    }

    redireccionar(request, response, redireccion);
  }
  protected void postAgregar_salida(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    boolean resultado = false;
    String redireccion = "Inventario_PT/Agregar_salida.jsp";
    try {
      Salida_Ext salida = construirSalida(request);

      int id_salida = salida_ext_dao.insertarSalida_Ext(salida);
      salida.setId_salida(id_salida);
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(salida.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SALIDAS_EXT, request.getRemoteAddr());

      ArrayList<int[]> lotes = construirLotes(request);
      salidas_inventario_dao.insertarSalidas_inventario(lotes, id_salida);

      redireccion = "Inventario_PT/index.jsp";
      request = request_index(request);
      request.setAttribute("sal_tab", "active");
      request.setAttribute("mensaje", helper.mensajeDeExito("Salida Extraordinaria agregada correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    } catch (NumberFormatException ex) {
      redireccion = "Inventario_PT/index.jsp";
      request = request_index(request);
      request.setAttribute("sal_tab", "active");
      request.setAttribute("mensaje", helper.mensajeDeAdvertencia("Salida Extraordinaria agregada sin Lotes de Producto"));
    }

    redireccionar(request, response, redireccion);
  }
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Post Editar">

  protected void postEditar_inventario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    boolean resultado = false;
    String redireccion = "Inventario_PT/Editar_inventario.jsp";
        try {
            Inventario_PT pie = construirInventario(request);

            dao.editarInventario_PT(pie);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(pie.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_INVENTARIO_PT, request.getRemoteAddr());

            redireccion = "Inventario_PT/index.jsp";
            request = request_index(request);
            request.setAttribute("inv_tab", "active");
            request.setAttribute("mensaje", helper.mensajeDeExito("Entrada de inventario de producto terminado editada correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }


    redireccionar(request, response, redireccion);
  }

  protected void postEditar_despacho(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    boolean resultado = false;
    String redireccion = "Inventario_PT/Editar_despacho.jsp";
    try {
      Despacho despacho = construirDespacho(request);
      ArrayList<int[]> lotes = construirLotes(request);
      despacho_dao.editarDespacho(despacho);
      despacho_dao.reset_total(despacho.getId_despacho());
      despachos_inventario_dao.eliminarDespachos_inventario(despacho.getId_despacho());
      despachos_inventario_dao.insertarDespachos_inventario(lotes, despacho.getId_despacho());

      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(despacho.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_DESPACHOS, request.getRemoteAddr());

      redireccion = "Inventario_PT/index.jsp";
      request = request_index(request);
      request.setAttribute("des_tab", "active");
      request.setAttribute("mensaje", helper.mensajeDeExito("Despacho editado correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    } catch (NumberFormatException ex) {
      redireccion = "Inventario_PT/index.jsp";
      request = request_index(request);
      request.setAttribute("des_tab", "active");
      request.setAttribute("mensaje", helper.mensajeDeError("Error al editar despacho: Debe seleccionar uno o varios Lotes de Producto"));
    }

    redireccionar(request, response, redireccion);
  }
  protected void postEditar_salida(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    boolean resultado = false;
    String redireccion = "Inventario_PT/Editar_salida.jsp";
    try {
      Salida_Ext salida = construirSalida(request);
      ArrayList<int[]> lotes = construirLotes(request);
      salida_ext_dao.editarSalida_Ext(salida);
      salida_ext_dao.reset_total(salida.getId_salida());
      salidas_inventario_dao.eliminarSalidas_inventario(salida.getId_salida());
      salidas_inventario_dao.insertarSalidas_inventario(lotes, salida.getId_salida());

      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(salida.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SALIDAS_EXT, request.getRemoteAddr());

      redireccion = "Inventario_PT/index.jsp";
      request = request_index(request);
      request.setAttribute("sal_tab", "active");
      request.setAttribute("mensaje", helper.mensajeDeExito("Salida Extraordinaria editada correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    } catch (NumberFormatException ex) {
      redireccion = "Inventario_PT/index.jsp";
      request = request_index(request);
      request.setAttribute("sal_tab", "active");
      request.setAttribute("mensaje", helper.mensajeDeError("Error al editar salida: Debe seleccionar uno o varios Lotes de Producto"));
    }

    redireccionar(request, response, redireccion);
  }
    protected void postEditar_reservacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    boolean resultado = false;
    String redireccion = "Inventario_PT/Editar_reservacion.jsp";
    try {
      Reservacion reservacion = construirReservacion(request);
      ArrayList<int[]> lotes = construirLotes(request);
      reservacion_dao.editarReservacion(reservacion);
      reservacion_dao.reset_total(reservacion.getId_reservacion());
      reservaciones_inventario_dao.eliminarReservaciones_inventario(reservacion.getId_reservacion());
      reservaciones_inventario_dao.insertarReservaciones_inventario(lotes, reservacion.getId_reservacion());

      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(reservacion.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESERVACIONES, request.getRemoteAddr());

      redireccion = "Inventario_PT/index.jsp";
      request = request_index(request);
      request.setAttribute("res_tab", "active");
      request.setAttribute("mensaje", helper.mensajeDeExito("Reservación editada correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    } catch (NumberFormatException ex) {
      redireccion = "Inventario_PT/index.jsp";
      request = request_index(request);
      request.setAttribute("res_tab", "active");
      request.setAttribute("mensaje", helper.mensajeDeError("Error al editar reservación: Debe seleccionar uno o varios Lotes de Producto"));
    }

    redireccionar(request, response, redireccion);
  }


  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Post Eliminar">
  protected void postEliminar_inventario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    int id_inventario = Integer.parseInt(request.getParameter("id_eliminar"));
    String redireccion = "Inventario_PT/index.jsp";
    try {
      dao.eliminarInventario_PT(id_inventario);

      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_inventario, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_INVENTARIO_PT, request.getRemoteAddr());

      request.setAttribute("mensaje", helper.mensajeDeExito("Inventario eliminado correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
      redireccionar(request, response, redireccion);
    }
    request = request_index(request);
    request.setAttribute("inv_tab", "active");

    redireccionar(request, response, redireccion);
  }

  protected void postEliminar_despacho(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    int id_despacho = Integer.parseInt(request.getParameter("id_eliminar"));
    String redireccion = "Inventario_PT/index.jsp";
    try {
      //Despacho despacho = despacho_dao.obtenerDespacho(id_despacho);
      despacho_dao.eliminarDespacho(id_despacho);

      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_despacho, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_DESPACHOS, request.getRemoteAddr());

      request.setAttribute("mensaje", helper.mensajeDeExito("Despacho eliminado correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
      redireccionar(request, response, redireccion);
    }
    request = request_index(request);
    request.setAttribute("des_tab", "active");

    redireccionar(request, response, redireccion);
  }
  protected void postEliminar_salida(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    int id_salida = Integer.parseInt(request.getParameter("id_eliminar"));
    String redireccion = "Inventario_PT/index.jsp";
    try {
      //Despacho despacho = despacho_dao.obtenerDespacho(id_despacho);
      salida_ext_dao.eliminarSalida_Ext(id_salida);

      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_salida, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SALIDAS_EXT, request.getRemoteAddr());

      request.setAttribute("mensaje", helper.mensajeDeExito("Salida Extraordinaria eliminada correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
      redireccionar(request, response, redireccion);
    }
    request = request_index(request);
    request.setAttribute("sal_tab", "active");

    redireccionar(request, response, redireccion);
  }
  protected void postEliminar_reservacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    int id_reservacion = Integer.parseInt(request.getParameter("id_eliminar"));
    String redireccion = "Inventario_PT/index.jsp";
    try {
      //Despacho despacho = despacho_dao.obtenerDespacho(id_despacho);
      reservacion_dao.eliminarReservacion(id_reservacion);

      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_reservacion, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESERVACIONES, request.getRemoteAddr());

      request.setAttribute("mensaje", helper.mensajeDeExito("Reservación eliminada correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
      redireccionar(request, response, redireccion);
    }
    request = request_index(request);
    request.setAttribute("res_tab", "active");

    redireccionar(request, response, redireccion);
  }
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  private Inventario_PT construirInventario(HttpServletRequest request) throws SIGIPROException {
    Inventario_PT inventario = new Inventario_PT();

        int id_catalogo = Integer.parseInt(request.getParameter("id_catalogo_pt"));
        int id_protocolo = Integer.parseInt(request.getParameter("id_protocolo"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        String lote = request.getParameter("lote");
        String fecha_vencimiento = request.getParameter("fecha_vencimiento");
        Protocolo protocolo = new Protocolo();
        Catalogo_PT producto = new Catalogo_PT();
        protocolo.setId_protocolo(id_protocolo);
        producto.setId_catalogo_pt(id_catalogo);
        inventario.setLote(lote);
        inventario.setCantidad(cantidad);
        inventario.setProducto(producto);
        inventario.setProtocolo(protocolo);

        try {
            HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
            inventario.setFecha_vencimiento(helper_fechas.formatearFecha(fecha_vencimiento));
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
    return inventario;
  }

  private Despacho construirDespacho(HttpServletRequest request) throws SIGIPROException {
    Despacho despacho = new Despacho();

    int id_despacho = Integer.parseInt(request.getParameter("id_despacho"));
    despacho.setId_despacho(id_despacho);
    despacho.setDestino(request.getParameter("destino"));
    String fecha = request.getParameter("fecha");

    try {
      HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
      despacho.setFecha(helper_fechas.formatearFecha(fecha));
    } catch (ParseException ex) {
      ex.printStackTrace();
    }
    return despacho;
  }
  private Reservacion construirReservacion(HttpServletRequest request) throws SIGIPROException {
    Reservacion reservacion = new Reservacion();

    int id_reservacion = Integer.parseInt(request.getParameter("id_reservacion"));
    reservacion.setId_reservacion(id_reservacion);
    reservacion.setObservaciones(request.getParameter("observaciones"));
    String fecha = request.getParameter("hasta");

    try {
      HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
      reservacion.setHasta(helper_fechas.formatearFecha(fecha));
    } catch (ParseException ex) {
      ex.printStackTrace();
    }
    return reservacion;
  }
  private Salida_Ext construirSalida(HttpServletRequest request) throws SIGIPROException {
    Salida_Ext salida = new Salida_Ext();

    int id_salida = Integer.parseInt(request.getParameter("id_salida"));
    salida.setId_salida(id_salida);
    salida.setTipo(request.getParameter("tipo"));
    salida.setObservaciones(request.getParameter("observaciones"));
    String fecha = request.getParameter("fecha");

    try {
      HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
      salida.setFecha(helper_fechas.formatearFecha(fecha));
    } catch (ParseException ex) {
      ex.printStackTrace();
    }
    return salida;
  }
  private ArrayList<int[]> construirLotes(HttpServletRequest request) throws NumberFormatException {
    ArrayList<int[]> resultado = new ArrayList<>();
    String lotes = request.getParameter("rolesUsuario");
    String[] split_lotes_1 = lotes.split("#r#");
    for (int i = 0; i < split_lotes_1.length; i++) {
      int[] sub_lista;
      sub_lista = new int[2];
      String[] split_lote = split_lotes_1[i].split("#c#");
      sub_lista[0] = Integer.parseInt(split_lote[0]);
      sub_lista[1] = Integer.parseInt(split_lote[1]);
      resultado.add(sub_lista);

    }
    return resultado;
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
