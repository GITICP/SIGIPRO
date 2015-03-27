/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core;

import com.icp.sigipro.bodegas.controladores.ControladorSubBodegas;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.sasl.AuthenticationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Boga
 */
@WebServlet(name = "SIGIPROServlet", urlPatterns = {"/SIGIPROServlet"})
public abstract class SIGIPROServlet extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    procesarSolicitud(request, response, "get");
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    request.setCharacterEncoding("UTF-8");
    procesarSolicitud(request, response, "post");
  }
  
  protected void procesarSolicitud(HttpServletRequest request, HttpServletResponse response, String accionHTTP)
          throws ServletException, IOException
  {
    String accion = request.getParameter("accion");
    System.out.println(accion);
    if (accion == null) {
        accion = "index";
    }
    try {
      ejecutarAccion(request, response, accion, accionHTTP);
    }
    catch (InvocationTargetException ex) {
      try {
        throw ex.getCause();
      }
      catch (AuthenticationException auth) {
        RequestDispatcher vista = request.getRequestDispatcher("/index.jsp");
        vista.forward(request, response);
      }
      catch (SIGIPROException sigipro) {
        // Obtener la redirección de alguna manera 
      }
      catch (Throwable ex1) {
        Logger.getLogger(ControladorSubBodegas.class.getName()).log(Level.SEVERE, null, ex1);
      }
    }
    catch (NoSuchMethodException | IllegalAccessException ex) {
      ex.printStackTrace();
    }
  }
  
  protected abstract int getPermiso();
  
  protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException 
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  protected void validarPermiso(int permiso, List<Integer> permisosUsuario) throws AuthenticationException, NullPointerException
  {
    try {
      if (!(permisosUsuario.contains(permiso) || permisosUsuario.contains(1))) {
        throw new AuthenticationException("Usuario no tiene permisos para acceder a la acción.");
      }
    }
    catch (NullPointerException e) {
      throw new AuthenticationException("Expiró la sesión.");
    }
  }

  protected void validarPermisos(int[] permisos, List<Integer> permisosUsuario) throws AuthenticationException
  {
    try {
      if (!(permisosUsuario.contains(permisos[0]) || permisosUsuario.contains(permisos[1]) || permisosUsuario.contains(permisos[2]) || permisosUsuario.contains(1))) {
        throw new AuthenticationException("Usuario no tiene permisos para acceder a la acción.");
      }
    }
    catch (NullPointerException e) {
      throw new AuthenticationException("Expiró la sesión.");
    }
  }

  protected boolean validarPermiso(List<Integer> permisosUsuario) throws AuthenticationException
  {
    try {
      return permisosUsuario.contains(getPermiso()) || permisosUsuario.contains(1);
    }
    catch (NullPointerException e) {
      throw new AuthenticationException("Expiró la sesión.");
    }
  }

  protected boolean verificarPermiso(Integer p, List<Integer> permisosUsuario) throws AuthenticationException
  {
    try {
      return permisosUsuario.contains(p) || permisosUsuario.contains(1);
    }
    catch (NullPointerException e) {
      throw new AuthenticationException("Expiró la sesión.");
    }
  }

  protected void redireccionar(HttpServletRequest request, HttpServletResponse response, String redireccion) throws ServletException, IOException
  {
    RequestDispatcher vista = request.getRequestDispatcher(redireccion);
    vista.forward(request, response);
  }

  protected List<Integer> getPermisosUsuario(HttpServletRequest request)
  {
    HttpSession sesion = request.getSession();
    return (List<Integer>) sesion.getAttribute("listaPermisos");
  }
}
