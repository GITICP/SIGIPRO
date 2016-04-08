/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core;

import com.icp.sigipro.utilidades.HelperFechas;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Boga
 */
public abstract class IModelo 
{
    
  protected HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
    
  public List<PropiedadModelo> getMetodos(String tipo) throws NoSuchMethodException
  {
    List<PropiedadModelo> resultado = new ArrayList<>();

    List<Field> campos = getCampos();

    for (Field campo : campos) {
      Class c = campo.getType().getSuperclass();
      boolean a = Modifier.isStatic(campo.getModifiers());
      if (!Modifier.isStatic(campo.getModifiers())){
        if (c == null) {
          resultado.add(getPropiedad(campo, tipo, this.getClass()));
        }
        else {
          resultado.add(getPropiedad(campo, tipo, this.getClass()));
        }
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

  private PropiedadModelo getPropiedad(Field campo, String tipo, Class clase) throws NoSuchMethodException
  {
    String nombreCampo = campo.getName();
    String primeraLetra = nombreCampo.substring(0, 1);
    String nombreCampoFinal = nombreCampo.replaceFirst(primeraLetra, primeraLetra.toUpperCase());
    Method metodo;
    if (tipo.equals("get")) {
      if (campo.getType().equals(boolean.class)) {
        metodo = clase.getMethod("is" + nombreCampoFinal, (Class<?>[]) null);
      }
      else {
        metodo = clase.getMethod(tipo + nombreCampoFinal, (Class<?>[]) null);
      }
    }
    else {
      metodo = clase.getMethod(tipo + nombreCampoFinal, campo.getType());
    }
    return new PropiedadModelo(campo, metodo);
  }
}
