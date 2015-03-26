/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.controladores;

import com.icp.sigipro.bioterio.dao.DesteteDAO;
import com.icp.sigipro.bioterio.modelos.Destete;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorDestetes", urlPatterns = {"/Ratonera/Destetes"})
public class ControladorDestetes extends SIGIPROServlet {

 
  private final int[] permisos = {204, 1, 1};
  private DesteteDAO dao = new DesteteDAO();
  private HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

  protected final Class clase = ControladorDestetes.class;
  protected final List<String> accionesGet = new ArrayList<String>()
  {
    {
      add("index");
      add("ver");
      add("agregar");
      add("editar");
    }
  };
  protected final List<String> accionesPost = new ArrayList<String>()
  {
    {
      add("agregar");
      add("editar");
      add("eliminar");
    }
  };
  
   // <editor-fold defaultstate="collapsed" desc="Métodos Get">
  
  protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermiso(204, listaPermisos);
    String redireccion = "Destetes/Agregar.jsp";

    Destete ds = new Destete();

    request.setAttribute("destete", ds);
    request.setAttribute("accion", "Agregar");

    redireccionar(request, response, redireccion);
  }

  protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Destetes/index.jsp";
    List<Destete> destetes;
    try {
      destetes = dao.obtenerDestetes();
      request.setAttribute("listaDestetes", destetes);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Destetes/Ver.jsp";
    int id_destete = Integer.parseInt(request.getParameter("id_destete"));
    try {
      Destete s = dao.obtenerDestete(id_destete);
      request.setAttribute("destete", s);
    }
    catch (Exception ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Destetes/Editar.jsp";
    int id_destete = Integer.parseInt(request.getParameter("id_destete"));
    request.setAttribute("accion", "Editar");
    try {
      Destete s = dao.obtenerDestete(id_destete);
      request.setAttribute("destete", s);
    } catch (SIGIPROException ex) {
       request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    int id_destete = Integer.parseInt(request.getParameter("id_destete"));
    try {
      dao.eliminarDestete(id_destete);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_destete, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------* 
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    String redireccion = "Destetes/index.jsp";
    redireccionar(request, response, redireccion);
  }
  
  // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
  protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    boolean resultado = false;
    String redireccion = "Destetes/Agregar.jsp";
    Destete destete = construirObjeto(request);
    try {
      resultado = dao.insertarDestete(destete);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(destete.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------*
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", ex.getMessage() );
    }
    if (resultado){
        redireccion = "Destetees/index.jsp";
        List<Destete> destetes;
      try {
        destetes = dao.obtenerDestetes();
        request.setAttribute("listaDestetes", destetes);
      } catch (SIGIPROException ex) {
        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
      }
    }
    else {
      request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
    }
    redireccionar(request, response, redireccion);
  }
  
  protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String id = request.getParameter("id_destete");
  }
  
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
   private Destete construirObjeto(HttpServletRequest request) {
    Destete destete = new Destete();
    String fch_sol = request.getParameter("fecha_destete");
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    java.util.Date fecha_destete;
    java.sql.Date fecha_desteteSQL;
    try {
      fecha_destete = formatoFecha.parse(fch_sol);
      fecha_desteteSQL = new java.sql.Date(fecha_destete.getTime());
      destete.setFecha_destete(fecha_desteteSQL);
    } catch (ParseException ex) {
      Logger.getLogger(ControladorDestetes.class.getName()).log(Level.SEVERE, null, ex);
    }
    destete.setNumero_hembras(Integer.parseInt(request.getParameter("numero_hembras")));
    destete.setNumero_machos(Integer.parseInt(request.getParameter("numero_machos")));
    return destete;
  }
  
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
  @Override
  protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
  {
      List<String> lista_acciones;
      if (accionHTTP.equals("get")){
          lista_acciones = accionesGet; 
      } else {
          lista_acciones = accionesPost;
      }
    if (lista_acciones.contains(accion.toLowerCase())) {
      String nombreMetodo = accionHTTP + Character.toUpperCase(accion.charAt(0)) + accion.substring(1);
      Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class);
      metodo.invoke(this, request, response);
    }
    else {
      Method metodo = clase.getDeclaredMethod(accionHTTP + "Index", HttpServletRequest.class, HttpServletResponse.class);
      metodo.invoke(this, request, response);
    }
  }

  @Override
  protected int getPermiso()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
  // </editor-fold>
}
