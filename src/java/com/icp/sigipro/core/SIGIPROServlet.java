/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core;

import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author Boga
 */
@WebServlet(name = "SIGIPROServlet", urlPatterns = {"/SIGIPROServlet"})
public abstract class SIGIPROServlet extends HttpServlet
{
  protected boolean validarPermiso(List<Integer> permisosUsuario)
  {
    return permisosUsuario.contains(getPermiso()) || permisosUsuario.contains(1); 
  }
  
  protected abstract int getPermiso();
}
