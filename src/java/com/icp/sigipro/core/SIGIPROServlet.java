/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core;

import java.util.List;
import javax.security.sasl.AuthenticationException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author Boga
 */
@WebServlet(name = "SIGIPROServlet", urlPatterns = {"/SIGIPROServlet"})
public abstract class SIGIPROServlet extends HttpServlet
{
    protected void validarPermiso(int permiso, List<Integer> permisosUsuario) throws AuthenticationException, NullPointerException
  {
    try {
      if (!(permisosUsuario.contains(permiso) || permisosUsuario.contains(1))) {
        throw new AuthenticationException("Usuario no tiene permisos para acceder a la acción.");
      }
    } catch (NullPointerException e) {
      throw new AuthenticationException("Expiró la sesión.");
    }
  }

  protected void validarPermisos(int[] permisos, List<Integer> permisosUsuario) throws AuthenticationException
  {
    try {
      if (!(permisosUsuario.contains(permisos[0]) || permisosUsuario.contains(permisos[1]) || permisosUsuario.contains(permisos[2]) || permisosUsuario.contains(1))) {
        throw new AuthenticationException("Usuario no tiene permisos para acceder a la acción.");
      }
    } catch (NullPointerException e) {
      throw new AuthenticationException("Expiró la sesión.");
    }
  }
  
  protected boolean validarPermiso(List<Integer> permisosUsuario) throws AuthenticationException
  {
    try {
      return permisosUsuario.contains(getPermiso()) || permisosUsuario.contains(1); 
    } catch (NullPointerException e) {
      throw new AuthenticationException("Expiró la sesión.");
    }
  }
  protected boolean verificarPermiso(Integer p, List<Integer> permisosUsuario) throws AuthenticationException
  {
    try {
      return permisosUsuario.contains(p) || permisosUsuario.contains(1); 
    } catch (NullPointerException e) {
      throw new AuthenticationException("Expiró la sesión.");
    }
  }
  
  protected abstract int getPermiso();
}
