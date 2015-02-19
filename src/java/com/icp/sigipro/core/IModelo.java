/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Boga
 */
public abstract class IModelo 
{
  public List<Tupla<Field, Method>> getMetodos(String tipo) throws NoSuchMethodException
  {
    List<Tupla<Field, Method>> resultado = new ArrayList<Tupla<Field, Method>>();

    List<Field> campos = getCampos();

    for (Field campo : campos) {
      Class c = campo.getType().getSuperclass();
      if (c == null) {
        resultado.add(getTupla(campo, tipo, this.getClass()));
      }
      else {
        resultado.add(getTupla(campo, tipo, this.getClass()));
      }
    }
    return resultado;
  }
  
  private List<Method> getMetodos()
  {
    return Arrays.asList(this.getClass().getDeclaredMethods());
  }

  private List<Field> getCampos()
  {
    return Arrays.asList(this.getClass().getDeclaredFields());
  }

  private Tupla<Field, Method> getTupla(Field campo, String tipo, Class clase) throws NoSuchMethodException
  {
    String nombreCampo = campo.getName();
    String primeraLetra = nombreCampo.substring(0, 1);
    String nombreCampoFinal = nombreCampo.replaceFirst(primeraLetra, primeraLetra.toUpperCase());
    Method m;
    if (tipo.equals("get")) {
      if (campo.getType().equals(boolean.class)) {
        m = clase.getMethod("is" + nombreCampoFinal, (Class<?>[]) null);
      }
      else {
        m = clase.getMethod(tipo + nombreCampoFinal, (Class<?>[]) null);
      }
    }
    else {
      m = clase.getMethod(tipo + nombreCampoFinal, campo.getType());
    }
    return new Tupla<Field, Method>(campo, m);
  }
}
