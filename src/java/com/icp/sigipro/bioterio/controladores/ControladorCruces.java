/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.controladores;

import com.icp.sigipro.bioterio.dao.CruceDAO;
import com.icp.sigipro.bioterio.dao.ConejaDAO;
import com.icp.sigipro.bioterio.dao.CruceDAO;
import com.icp.sigipro.bioterio.dao.MachoDAO;
import com.icp.sigipro.bioterio.modelos.Cruce;
import com.icp.sigipro.bioterio.modelos.Coneja;
import com.icp.sigipro.bioterio.modelos.Macho;
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
@WebServlet(name = "ControladorCruces", urlPatterns = {"/Conejera/Cruces"})
public class ControladorCruces extends SIGIPROServlet {

  private final int[] permisos = {251, 1, 1};
  private final CruceDAO dao = new CruceDAO();
  private final ConejaDAO coneja_dao = new ConejaDAO();
  private final MachoDAO macho_dao = new MachoDAO();
  private final HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

  protected final Class clase = ControladorCruces.class;
  protected final List<String> accionesGet = new ArrayList<String>()
  {
    {
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
    validarPermiso(251, listaPermisos);
    String redireccion = "Cruces/Agregar.jsp";
    int id_coneja = Integer.parseInt(request.getParameter("id_coneja"));
    Coneja coneja =null;
    try {
      coneja = coneja_dao.obtenerConeja(id_coneja, true);
      List<Macho> machos = macho_dao.obtenerMachos();
      request.setAttribute("machos", machos);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    Cruce ds = new Cruce();
    request.setAttribute("cruce", ds);
    request.setAttribute("coneja", coneja);
    request.setAttribute("accion", "Agregar");

    redireccionar(request, response, redireccion);
  }

  protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Cruces/Ver.jsp";
    int id_cruce = Integer.parseInt(request.getParameter("id_cruce"));
    try {
      Cruce s = dao.obtenerCruce(id_cruce);
      request.setAttribute("cruce", s);
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
    String redireccion = "Cruces/Editar.jsp";
    int id_cruce = Integer.parseInt(request.getParameter("id_cruce"));
    request.setAttribute("accion", "Editar");
    try {
      List<Macho> machos = macho_dao.obtenerMachos();
      request.setAttribute("machos", machos);
      Cruce s = dao.obtenerCruce(id_cruce);
      request.setAttribute("cruce", s);
    } catch (SIGIPROException ex) {
       request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    int id_cruce = Integer.parseInt(request.getParameter("id_cruce"));
    Cruce cruce = null;
    try {
      cruce = dao.obtenerCruce(id_cruce);
    } catch (SIGIPROException ex) {
      Logger.getLogger(ControladorCruces.class.getName()).log(Level.SEVERE, null, ex);
    }
    int id_caja = cruce.getConeja().getCaja().getId_caja();
    try {
      dao.eliminarCruce(id_cruce);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_cruce, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------* 
      request.setAttribute("mensaje", helper.mensajeDeExito("Cruce eliminada correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
   
    String redireccion = "Cajas?accion=ver&mensaje=Cruce eliminado correctamente&id_caja="+id_caja;
    response.sendRedirect(redireccion); 
  }
  
  // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
  protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    boolean resultado = false;
    String redireccion = "Cruces/Agregar.jsp";
    int id_caja =0;
    try {
      Cruce cruce = construirObjeto(request);
      id_caja = cruce.getConeja().getCaja().getId_caja();
      resultado = dao.insertarCruce(cruce);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(cruce.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------*
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", ex.getMessage() );
    }
     if (resultado){
        
        redireccion = "Cajas?accion=ver&mensaje=Cruce agregado correctamente&id_caja="+id_caja;
    }
    else {
      request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
    }
    response.sendRedirect(redireccion);
  }
  
  protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    boolean resultado = false;
    String redireccion = "Cruces/Editar.jsp";
    int id_caja =0;
    try {
      Cruce cruce = construirObjeto(request);
      id_caja = cruce.getConeja().getCaja().getId_caja();
      resultado = dao.editarCruce(cruce);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(cruce.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------*
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", ex.getMessage() );
    }
     if (resultado){
        
        redireccion = "Cajas?accion=ver&mensaje=Cruce editado correctamente&id_caja="+id_caja;
    }
    else {
      request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
    }
    response.sendRedirect(redireccion);
  }
  
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
   private Cruce construirObjeto(HttpServletRequest request) throws SIGIPROException {
    Cruce cruce = new Cruce();
    cruce.setId_cruce(Integer.parseInt(request.getParameter("id_cruce")));
    cruce.setMacho(macho_dao.obtenerMacho(Integer.parseInt(request.getParameter("id_macho"))));
    cruce.setConeja(coneja_dao.obtenerConeja(Integer.parseInt(request.getParameter("id_coneja")),true));
    cruce.setObservaciones(request.getParameter("observaciones"));
    cruce.setCantidad_paridos(Integer.parseInt(request.getParameter("cantidad_paridos")));
    String fecha_nac = request.getParameter("fecha_cruce");
    String fecha_ret = request.getParameter("fecha_parto");
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    java.util.Date fecha_nacimiento;
    java.sql.Date fecha_nacimientoSQL;
    java.util.Date fecha_retiro;
    java.sql.Date fecha_retiroSQL;
    try {
      fecha_nacimiento = formatoFecha.parse(fecha_nac);
      fecha_nacimientoSQL = new java.sql.Date(fecha_nacimiento.getTime());
      cruce.setFecha_cruce(fecha_nacimientoSQL);
    } catch (ParseException ex) {
      Logger.getLogger(ControladorCruces.class.getName()).log(Level.SEVERE, null, ex);
    }
     try {
      fecha_retiro = formatoFecha.parse(fecha_ret);
      fecha_retiroSQL = new java.sql.Date(fecha_retiro.getTime());
      cruce.setFecha_parto(fecha_retiroSQL);
    } catch (ParseException ex) {
      cruce.setFecha_parto(null);
      Logger.getLogger(ControladorCruces.class.getName()).log(Level.SEVERE, null, ex);
    }
    return cruce;
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
