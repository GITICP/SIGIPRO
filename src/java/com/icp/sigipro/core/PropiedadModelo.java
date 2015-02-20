/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author Boga
 */
public class PropiedadModelo {
  
  public final Field campo;
  public final Method metodo;
  
  public PropiedadModelo(Field c, Method m) {
    this.campo = c;
    this.metodo = m;
  }

  public Field getCampo()
  {
    return campo;
  }

  public Method getMetodo()
  {
    return metodo;
  }
  
  
}
