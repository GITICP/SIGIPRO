/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.controladores;

import com.icp.sigipro.bioterio.dao.CaraDAO;
import com.icp.sigipro.bioterio.dao.CepaDAO;
import com.icp.sigipro.bioterio.modelos.Cara;
import com.icp.sigipro.bioterio.modelos.Cepa;
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
@WebServlet(name = "ControladorCaras", urlPatterns = {"/Ratonera/Caras"})
public class ControladorCaras extends SIGIPROServlet {

  private final int[] permisos = {201, 1, 1};
  private CaraDAO dao = new CaraDAO();
  private CepaDAO cepa_dao = new CepaDAO();
  private HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

  protected final Class clase = ControladorCaras.class;
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
    validarPermiso(201, listaPermisos);
    String redireccion = "Caras/Agregar.jsp";

    Cara ds = new Cara();
    try {
      List<Cepa> cepas;
      cepas = cepa_dao.obtenerCepas();
      request.setAttribute("cepas", cepas);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError("No se pudo cargar las cepas. " + ex.getMessage()));
    }
    request.setAttribute("cara", ds);
    request.setAttribute("accion", "Agregar");

    redireccionar(request, response, redireccion);
  }

  protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Caras/index.jsp";
    List<Cara> caras;
    try {
      caras = dao.obtenerCaras();
      request.setAttribute("listaCaras", caras);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Caras/Ver.jsp";
    int id_cara = Integer.parseInt(request.getParameter("id_cara"));
    try {
      Cara s = dao.obtenerCara(id_cara);
      request.setAttribute("cara", s);
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
    String redireccion = "Caras/Editar.jsp";
    int id_cara = Integer.parseInt(request.getParameter("id_cara"));
    request.setAttribute("accion", "Editar");
    try {
      Cara s = dao.obtenerCara(id_cara);
      request.setAttribute("cara", s);
    } catch (SIGIPROException ex) {
       request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    try {
      List<Cepa> cepas;
      cepas = cepa_dao.obtenerCepas();
      request.setAttribute("cepas", cepas);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError("No se pudo cargar las cepas. " + ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    int id_cara = Integer.parseInt(request.getParameter("id_cara"));
    try {
      dao.eliminarCara(id_cara);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(id_cara, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------* 
      request.setAttribute("mensaje", helper.mensajeDeExito("Cara eliminada correctamente."));
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    try {
      List<Cara> caras;
      caras = dao.obtenerCaras();
      request.setAttribute("listaCaras", caras);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    String redireccion = "Caras/index.jsp";
    redireccionar(request, response, redireccion);
  }
  
  // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
  protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    boolean resultado = false;
    String redireccion = "Caras/Agregar.jsp";
    try {
      Cara cara = construirObjeto(request);
      resultado = dao.insertarCara(cara);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(cara.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------*
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", ex.getMessage() );
    }
    if (resultado){
        redireccion = "Caras/index.jsp";
        List<Cara> caras;
      try {
        caras = dao.obtenerCaras();
        request.setAttribute("listaCaras", caras);
        request.setAttribute("mensaje", helper.mensajeDeExito("Cara agregada con éxito"));
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
    String redireccion = "Caras/Editar.jsp";
    try {
      Cara cara = construirObjeto(request);
      resultado = dao.editarCara(cara);
      //Funcion que genera la bitacora
      BitacoraDAO bitacora = new BitacoraDAO();
      bitacora.setBitacora(cara.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
      //*----------------------------*
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", ex.getMessage() );
    }
    if (resultado){
        redireccion = "Caras/index.jsp";
        List<Cara> caras;
      try {
        caras = dao.obtenerCaras();
        request.setAttribute("listaCaras", caras);
        request.setAttribute("mensaje", helper.mensajeDeExito("Cara editada con éxito"));

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
   private Cara construirObjeto(HttpServletRequest request) throws SIGIPROException {
    Cara cara = new Cara();
    String fecha_apai = request.getParameter("fecha_apai");
    String fecha_apaf = request.getParameter("fecha_apaf");
    String fecha_elimi = request.getParameter("fecha_elimi");
    String fecha_elimf = request.getParameter("fecha_elimf");
    String fecha_elihi = request.getParameter("fecha_elihi");
    String fecha_elihf = request.getParameter("fecha_elihf");
    String fecha_selni = request.getParameter("fecha_selni");
    String fecha_selnf = request.getParameter("fecha_selnf");
    String fecha_repoi = request.getParameter("fecha_repoi");
    String fecha_repof = request.getParameter("fecha_repof");
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    java.util.Date Dfecha_apai;
    java.sql.Date fecha_apaiSQL;
    java.util.Date Dfecha_apaf;
    java.sql.Date fecha_apafSQL;
    java.util.Date Dfecha_elimi;
    java.sql.Date fecha_elimiSQL;
    java.util.Date Dfecha_elimf;
    java.sql.Date fecha_elimfSQL;
    java.util.Date Dfecha_elihi;
    java.sql.Date fecha_elihiSQL;
    java.util.Date Dfecha_elihf;
    java.sql.Date fecha_elihfSQL;
    java.util.Date Dfecha_selni;
    java.sql.Date fecha_selniSQL;
    java.util.Date Dfecha_selnf;
    java.sql.Date fecha_selnfSQL;
    java.util.Date Dfecha_repoi;
    java.sql.Date fecha_repoiSQL;
    java.util.Date Dfecha_repof;
    java.sql.Date fecha_repofSQL;


    try {
      Dfecha_apai = formatoFecha.parse(fecha_apai);
      Dfecha_apaf = formatoFecha.parse(fecha_apaf);
      Dfecha_elimi = formatoFecha.parse(fecha_elimi);
      Dfecha_elimf = formatoFecha.parse(fecha_elimf);
      Dfecha_elihi = formatoFecha.parse(fecha_elihi);
      Dfecha_elihf = formatoFecha.parse(fecha_elihf);
      Dfecha_selni = formatoFecha.parse(fecha_selni);
      Dfecha_selnf = formatoFecha.parse(fecha_selnf);
      Dfecha_repoi = formatoFecha.parse(fecha_repoi);
      Dfecha_repof = formatoFecha.parse(fecha_repof);

      
      fecha_apaiSQL = new java.sql.Date(Dfecha_apai.getTime());
      fecha_apafSQL = new java.sql.Date(Dfecha_apaf.getTime());
      fecha_elimiSQL = new java.sql.Date(Dfecha_elimi.getTime());
      fecha_elimfSQL = new java.sql.Date(Dfecha_elimf.getTime());
      fecha_elihiSQL = new java.sql.Date(Dfecha_elihi.getTime());
      fecha_elihfSQL = new java.sql.Date(Dfecha_elihf.getTime());
      fecha_selniSQL = new java.sql.Date(Dfecha_selni.getTime());
      fecha_selnfSQL = new java.sql.Date(Dfecha_selnf.getTime());
      fecha_repoiSQL = new java.sql.Date(Dfecha_repoi.getTime());
      fecha_repofSQL = new java.sql.Date(Dfecha_repof.getTime());

      
      cara.setFecha_apareamiento_i(fecha_apaiSQL);
      cara.setFecha_apareamiento_f(fecha_apafSQL);
      cara.setFecha_eliminacionmacho_i(fecha_elimiSQL);
      cara.setFecha_eliminacionmacho_f(fecha_elimfSQL);
      cara.setFecha_eliminacionhembra_i(fecha_elihiSQL);
      cara.setFecha_eliminacionhembra_f(fecha_elihfSQL);
      cara.setFecha_seleccionnuevos_i(fecha_selniSQL);
      cara.setFecha_seleccionnuevos_f(fecha_selnfSQL);
      cara.setFecha_reposicionciclo_i(fecha_repoiSQL);
      cara.setFecha_reposicionciclo_f(fecha_repofSQL);

      
    } catch (ParseException ex) {
      Logger.getLogger(ControladorCaras.class.getName()).log(Level.SEVERE, null, ex);
    }
    cara.setId_cara(Integer.parseInt(request.getParameter("id_cara")));
    cara.setNumero_cara(Integer.parseInt(request.getParameter("numero_cara")));
    cara.setMacho_as(request.getParameter("macho_as"));
    cara.setHembra_as(request.getParameter("hembra_as"));
    Integer id_cepa = Integer.parseInt(request.getParameter("id_cepa"));
    cara.setCepa(cepa_dao.obtenerCepa(id_cepa));
    
    return cara;
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
