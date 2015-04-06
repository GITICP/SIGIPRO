/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.controladores;


import com.icp.sigipro.bioterio.dao.CepaDAO;
import com.icp.sigipro.bioterio.modelos.Cepa;
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
@WebServlet(name = "ControladorCepas", urlPatterns = {"/Ratonera/Cepas"})
public class ControladorCepas extends  SIGIPROServlet {

  private final int[] permisos = {202, 1, 1};
  private CepaDAO dao = new CepaDAO();
  private HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

  protected final Class clase = ControladorCepas.class;
  protected final List<String> accionesGet = new ArrayList<String>()
  {
    {
      add("index");
      add("ver");
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
    validarPermiso(202, listaPermisos);
    String redireccion = "Cepas/Agregar.jsp";

    Cepa ds = new Cepa();
    request.setAttribute("cepa", ds);
    request.setAttribute("accion", "Agregar");

    redireccionar(request, response, redireccion);
  }

  protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Cepas/index.jsp";
    List<Cepa> cepas;
    try {
      cepas = dao.obtenerCepas();
      request.setAttribute("listaCepas", cepas);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Cepas/Ver.jsp";
    int id_cepa = Integer.parseInt(request.getParameter("id_cepa"));
    try {
      Cepa s = dao.obtenerCepa(id_cepa);
      request.setAttribute("cepa", s);
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
    String redireccion = "Cepas/Editar.jsp";
    int id_cepa = Integer.parseInt(request.getParameter("id_cepa"));
    request.setAttribute("accion", "Editar");
    try {
      Cepa s = dao.obtenerCepa(id_cepa);
      request.setAttribute("cepa", s);
    } catch (SIGIPROException ex) {
       request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    int id_cepa = Integer.parseInt(request.getParameter("id_cepa"));
    try {
      dao.eliminarCepa(id_cepa);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_cepa, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------* 
      request.setAttribute("mensaje", helper.mensajeDeExito("Cepa eliminada correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    try {
      List<Cepa> cepas;
      cepas = dao.obtenerCepas();
      request.setAttribute("listaCepas", cepas);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    String redireccion = "Cepas/index.jsp";
    redireccionar(request, response, redireccion);
  }
  
  // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
  protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    boolean resultado = false;
    String redireccion = "Cepas/Agregar.jsp";
    try {
      Cepa cepa = construirObjeto(request);
      resultado = dao.insertarCepa(cepa);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(cepa.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------*
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", ex.getMessage() );
    }
    if (resultado){
        redireccion = "Cepas/index.jsp";
        List<Cepa> cepas;
      try {
        cepas = dao.obtenerCepas();
        request.setAttribute("listaCepas", cepas);
        request.setAttribute("mensaje", helper.mensajeDeExito("Cepa agregada con éxito"));
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
    boolean resultado = false;
    String redireccion = "Cepas/Editar.jsp";
    try {
      Cepa cepa = construirObjeto(request);
      resultado = dao.editarCepa(cepa);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(cepa.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------*
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", ex.getMessage() );
    }
    if (resultado){
        redireccion = "Cepas/index.jsp";
        List<Cepa> cepas;
      try {
        cepas = dao.obtenerCepas();
        request.setAttribute("listaCepas", cepas);
        request.setAttribute("mensaje", helper.mensajeDeExito("Cepa editada con éxito"));

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
   private Cepa construirObjeto(HttpServletRequest request) throws SIGIPROException {
    Cepa cepa = new Cepa();
    
    cepa.setId_cepa(Integer.parseInt(request.getParameter("id_cepa")));
    cepa.setNombre(request.getParameter("nombre"));
    
    return cepa;
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
