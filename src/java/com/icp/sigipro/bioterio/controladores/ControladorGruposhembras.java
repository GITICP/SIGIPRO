/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.controladores;

import com.icp.sigipro.bioterio.dao.GrupohembrasDAO;
import com.icp.sigipro.bioterio.modelos.Grupohembras;
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
@WebServlet(name = "ControladorGruposhembras", urlPatterns = {"/Conejera/Gruposhembras"})
public class ControladorGruposhembras extends SIGIPROServlet  {
 private final int[] permisos = {251, 1, 1};
  private GrupohembrasDAO dao = new GrupohembrasDAO();
  private HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

  protected final Class clase = ControladorGruposhembras.class;
  protected final List<String> accionesGet = new ArrayList<String>()
  {
    {
      add("index");
      add("agregar");
      add("editar");
      add("eliminar");
    }
  };
  protected final List<String> accionesPost = new ArrayList<String>()
  {
    {
      add("agregar");
      add("editar");
    }
  };
  
   // <editor-fold defaultstate="collapsed" desc="Métodos Get">
  
  protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermiso(251, listaPermisos);
    String redireccion = "Gruposhembras/Agregar.jsp";

    Grupohembras ds = new Grupohembras();
    request.setAttribute("grupo", ds);
    request.setAttribute("accion", "Agregar");

    redireccionar(request, response, redireccion);
  }

  protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Gruposhembras/index.jsp";
    List<Grupohembras> gruposhembras;
    try {
      gruposhembras = dao.obtenerGruposhembras();
      request.setAttribute("listaGruposhembras", gruposhembras);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Gruposhembras/Editar.jsp";
    int id_grupo = Integer.parseInt(request.getParameter("id_grupo"));
    request.setAttribute("accion", "Editar");
    try {
      Grupohembras s = dao.obtenerGrupohembras(id_grupo);
      request.setAttribute("grupo", s);
    } catch (SIGIPROException ex) {
       request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    int id_grupo = Integer.parseInt(request.getParameter("id_grupo"));
    try {
      dao.eliminarGrupohembras(id_grupo);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_grupo, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------* 
      request.setAttribute("mensaje", helper.mensajeDeExito("Grupo de hembras eliminado correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    try {
      List<Grupohembras> gruposhembras;
      gruposhembras = dao.obtenerGruposhembras();
      request.setAttribute("listaGruposhembras", gruposhembras);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    String redireccion = "Gruposhembras/index.jsp";
    redireccionar(request, response, redireccion);
  }
  
  // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
  protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    boolean resultado = false;
    String redireccion = "Gruposhembras/Agregar.jsp";
    try {
      Grupohembras grupohembras = construirObjeto(request);
      resultado = dao.insertarGrupohembras(grupohembras);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(grupohembras.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------*
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()) );
    }
    if (resultado){
        redireccion = "Gruposhembras/index.jsp";
        List<Grupohembras> gruposhembras;
      try {
        gruposhembras = dao.obtenerGruposhembras();
        request.setAttribute("listaGruposhembras", gruposhembras);
        request.setAttribute("mensaje", helper.mensajeDeExito("Grupo de hembras agregado con éxito"));
      } catch (SIGIPROException ex) {
        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
      }
    }
    else {
    }
    redireccionar(request, response, redireccion);
  }
  
  protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    boolean resultado = false;
    String redireccion = "Gruposhembras/Editar.jsp";
    try {
      Grupohembras grupohembras = construirObjeto(request);
      resultado = dao.editarGrupohembras(grupohembras);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(grupohembras.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------*
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", ex.getMessage() );
    }
    if (resultado){
        redireccion = "Gruposhembras/index.jsp";
        List<Grupohembras> gruposhembras;
      try {
        gruposhembras = dao.obtenerGruposhembras();
        request.setAttribute("listaGruposhembras", gruposhembras);
        request.setAttribute("mensaje", helper.mensajeDeExito("Grupo de hembras editado con éxito"));

      } catch (SIGIPROException ex) {
        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
      }
    }
    else {
      request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
    }
    redireccionar(request, response, redireccion);
  }
  
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
   private Grupohembras construirObjeto(HttpServletRequest request) throws SIGIPROException {
    Grupohembras grupohembras = new Grupohembras();
    
    grupohembras.setId_grupo(Integer.parseInt(request.getParameter("id_grupo")));
    grupohembras.setIdentificador(request.getParameter("identificador"));
    grupohembras.setCantidad_espacios(Integer.parseInt(request.getParameter("cantidad_espacios")));
    
    return grupohembras;
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
