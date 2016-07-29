/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.controladores;

import com.icp.sigipro.produccion.controladores.ControladorCatalogo_PT;
import com.icp.sigipro.produccion.dao.Catalogo_PTDAO;
import com.icp.sigipro.produccion.modelos.Catalogo_PT;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
@WebServlet(name = "ControladorCatalogo_PT", urlPatterns = {"/Produccion/Catalogo_PT"})
public class ControladorCatalogo_PT extends SIGIPROServlet {

  private final int[] permisos = {606, 1, 1};
  private final Catalogo_PTDAO dao = new Catalogo_PTDAO();
  private final HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

  protected final Class clase = ControladorCatalogo_PT.class;
  protected final List<String> accionesGet = new ArrayList<String>() {
    {
      add("index");
      add("ver");
      add("agregar");
      add("editar");
    }
  };
  protected final List<String> accionesPost = new ArrayList<String>() {
    {
      add("agregar");
      add("editar");
      add("eliminar");
    }
  };

  // <editor-fold defaultstate="collapsed" desc="Métodos Get">
  protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermiso(606, listaPermisos);
    String redireccion = "Catalogo_PT/index.jsp";
    List<Catalogo_PT> productos = dao.obtenerCatalogos_PT();
    request.setAttribute("productos", productos);
    redireccionar(request, response, redireccion);
  }

  protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermiso(606, listaPermisos);
    String redireccion = "Catalogo_PT/Agregar.jsp";
    Catalogo_PT catalogo_pt = new Catalogo_PT();
    request.setAttribute("catalogo_pt", catalogo_pt);
    request.setAttribute("accion", "Agregar");

    redireccionar(request, response, redireccion);
  }

  protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Catalogo_PT/Editar.jsp";
    int id_catalogo_pt = Integer.parseInt(request.getParameter("id_catalogo_pt"));
    request.setAttribute("accion", "Editar");
    try {
      Catalogo_PT catalogo_pt = dao.obtenerCatalogo_PT(id_catalogo_pt);
      request.setAttribute("catalogo_pt", catalogo_pt);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Catalogo_PT/Ver.jsp";
    int id_catalogo_pt = Integer.parseInt(request.getParameter("id_catalogo_pt"));
    try {
      Catalogo_PT catalogo_pt = dao.obtenerCatalogo_PT(id_catalogo_pt);
      request.setAttribute("catalogo_pt", catalogo_pt);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    boolean resultado = false;
    String redireccion = "Catalogo_PT/Agregar.jsp";
    try {
      Catalogo_PT catalogo_pt = construirObjeto(request);

      dao.insertarCatalogo_PT(catalogo_pt);

      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(catalogo_pt.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

      redireccion = "Catalogo_PT/index.jsp";
      request.setAttribute("mensaje", helper.mensajeDeExito("Catalogo_PT agregado correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }

    List<Catalogo_PT> productos = dao.obtenerCatalogos_PT();
    request.setAttribute("productos", productos);

    redireccionar(request, response, redireccion);
  }

  protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    boolean resultado = false;
    String redireccion = "Catalogo_PT/Editar.jsp";
    try {
      Catalogo_PT catalogo_pt = construirObjeto(request);
      dao.editarCatalogo_PT(catalogo_pt);

      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(catalogo_pt.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

      redireccion = "Catalogo_PT/index.jsp";
      request.setAttribute("mensaje", helper.mensajeDeExito("Catalogo_PT editado correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }

    List<Catalogo_PT> productos = dao.obtenerCatalogos_PT();
    request.setAttribute("productos", productos);

    redireccionar(request, response, redireccion);
  }

  protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    int id_catalogo_pt = Integer.parseInt(request.getParameter("id_eliminar"));
    String redireccion = "Catalogo_PT/index.jsp";
    Catalogo_PT catalogo_pt;
    try {
      catalogo_pt = dao.obtenerCatalogo_PT(id_catalogo_pt);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    try {
      dao.eliminarCatalogo_PT(id_catalogo_pt);

      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_catalogo_pt, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

      request.setAttribute("mensaje", helper.mensajeDeExito("Catalogo_PT eliminado correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }

    List<Catalogo_PT> productos = dao.obtenerCatalogos_PT();
    request.setAttribute("productos", productos);

    redireccionar(request, response, redireccion);
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  private Catalogo_PT construirObjeto(HttpServletRequest request) throws SIGIPROException {
    Catalogo_PT catalogo_pt = new Catalogo_PT();

    int id_catalogo_pt = Integer.parseInt(request.getParameter("id_catalogo_pt"));
    catalogo_pt.setId_catalogo_pt(id_catalogo_pt);
    catalogo_pt.setNombre(request.getParameter("nombre"));
    catalogo_pt.setDescripcion(request.getParameter("descripcion"));
    try {
    catalogo_pt.setVida_util(Integer.parseInt(request.getParameter("vida_util")));
    }
    catch (NumberFormatException e){
    }
    return catalogo_pt;
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
