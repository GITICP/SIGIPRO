/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.controladores;

import com.icp.sigipro.bioterio.dao.CajaDAO;
import com.icp.sigipro.bioterio.dao.ConejaDAO;
import com.icp.sigipro.bioterio.dao.CruceDAO;
import com.icp.sigipro.bioterio.dao.GrupohembrasDAO;
import com.icp.sigipro.bioterio.modelos.Caja;
import com.icp.sigipro.bioterio.modelos.Coneja;
import com.icp.sigipro.bioterio.modelos.Cruce;
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
@WebServlet(name = "ControladorCajas", urlPatterns = {"/Conejera/Cajas"})
public class ControladorCajas extends SIGIPROServlet {

  private final int[] permisos = {251, 1, 1};
  private final CajaDAO dao = new CajaDAO();
  private final ConejaDAO coneja_dao = new ConejaDAO();
  private final CruceDAO cruce_dao = new CruceDAO();
  private final GrupohembrasDAO grupoDAO = new GrupohembrasDAO();
  private final HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

  protected final Class clase = ControladorCajas.class;
  protected final List<String> accionesGet = new ArrayList<String>()
  {
    {
      add("index");
      add("ver");
    }
  };
  protected final List<String> accionesPost = new ArrayList<String>()
  {
    {
//      add("agregar");
//      add("editar");
    }
  };
  
   // <editor-fold defaultstate="collapsed" desc="Métodos Get">


  protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Cajas/index.jsp";
    int id_grupo = Integer.parseInt(request.getParameter("id_grupo"));
    List<Caja> cajas;
    try {
      cajas = dao.obtenerCajas(id_grupo);
      request.setAttribute("listaCajas", cajas);
      Grupohembras grupo = grupoDAO.obtenerGrupohembras(id_grupo);
      request.setAttribute("grupo", grupo);
    } catch (SIGIPROException ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    redireccionar(request, response, redireccion);
  }

  protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "Cajas/Ver.jsp";
    int id_caja = Integer.parseInt(request.getParameter("id_caja"));
    try {
      Caja s = dao.obtenerCaja(id_caja);
      request.setAttribute("caja", s);
      Coneja co = coneja_dao.obtenerConeja(id_caja);
      request.setAttribute("coneja", co);
      List<Cruce> cruces = cruce_dao.obtenerCruces(co.getId_coneja());
      request.setAttribute("cruces", cruces);
    }
    catch (Exception ex) {
      request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
    }
    try{
      if (request.getParameter("mensaje") != null){
      request.setAttribute("mensaje", helper.mensajeDeExito(request.getParameter("mensaje")));}
    }
    catch(Exception ex){}
    redireccionar(request, response, redireccion);
  }

  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
   private Caja construirObjeto(HttpServletRequest request) throws SIGIPROException {
    Caja caja = new Caja();
    
//    caja.setId_caja(Integer.parseInt(request.getParameter("id_caja")));
//    caja.setNumero(Integer.parseInt(request.getParameter("numero")));
//    
    return caja;
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
