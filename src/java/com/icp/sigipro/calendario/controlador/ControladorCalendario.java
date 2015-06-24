/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.calendario.controlador;


import com.icp.sigipro.calendario.dao.CalendarioDAO;
import com.icp.sigipro.bioterio.modelos.Cepa;
import com.icp.sigipro.bioterio.modelos.Pie;
import com.icp.sigipro.bioterio.modelos.PiexCepa;
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
@WebServlet(name = "ControladorCalendario", urlPatterns = {"/Calendario"})
public class ControladorCalendario extends  SIGIPROServlet {

  private final int[] permisos = {5000, 1, 1};
  private CalendarioDAO dao = new CalendarioDAO();
  private HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

  protected final Class clase = ControladorCalendario.class;
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
    String redireccion = "Calendario/Agregar.jsp";

    Cepa ds = new Cepa();
    request.setAttribute("cepa", ds);
    request.setAttribute("accion", "Agregar");

    redireccionar(request, response, redireccion);
  }

  protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {

    String redireccion = "Calendario/Calendario/index.jsp";
    redireccionar(request, response, redireccion);
  }

  protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {

    String redireccion = "Calendario/Ver.jsp";
    redireccionar(request, response, redireccion);
  }

  protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {

    String redireccion = "Calendario/Editar.jsp";
    redireccionar(request, response, redireccion);
  }

  protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String redireccion = "Calendario/index.jsp";
    redireccionar(request, response, redireccion);
  }
  
  // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
  protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {

    String redireccion = "Calendario/Agregar.jsp";
   
    redireccionar(request, response, redireccion);
  }
  
  protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    
    String redireccion = "Calendario/Editar.jsp";
   
    redireccionar(request, response, redireccion);
  }
  
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
   private Cepa construirObjeto(HttpServletRequest request) throws SIGIPROException {
    Cepa cepa = new Cepa();
    
    cepa.setId_cepa(Integer.parseInt(request.getParameter("id_cepa")));
    cepa.setNombre(request.getParameter("nombre"));
    cepa.setDescripcion(request.getParameter("descripcion"));
    
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
