/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.configuracion.modelos;

import com.icp.sigipro.core.IModelo;

/**
 *
 * @author Boga
 */
public class Seccion extends IModelo
{

  int id_seccion;
  String nombre_seccion;
  String descripcion;

  public Seccion()
  {
  }

  public Seccion(int p_id_seccion, String p_nombre_seccion, String p_descripcion)
  {
    id_seccion = p_id_seccion;
    nombre_seccion = p_nombre_seccion;
    descripcion = p_descripcion;
  }

  public int getId_seccion()
  {
    return id_seccion;
  }

  public void setId_seccion(int id_seccion)
  {
    this.id_seccion = id_seccion;
  }

  public void setNombre_seccion(String nombre_seccion)
  {
    this.nombre_seccion = nombre_seccion;
  }

  public void setDescripcion(String descripcion)
  {
    this.descripcion = descripcion;
  }

  public int getID()
  {
    return id_seccion;
  }

  public String getNombre_seccion()
  {
    return nombre_seccion;
  }

  public String getDescripcion()
  {
    return descripcion;
  }

}
