/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.IModelo;
import com.icp.sigipro.seguridad.modelos.Usuario;

/**
 *
 * @author Boga
 */
public class SubBodega extends IModelo
{
  
  private int id_sub_bodega;
  private Seccion seccion;
  private Usuario usuario;
  private String nombre;

  public SubBodega(){
    
  }
  
  public int getId_sub_bodega()
  {
    return id_sub_bodega;
  }

  public void setId_sub_bodega(int id_sub_bodega)
  {
    this.id_sub_bodega = id_sub_bodega;
  }

  public Seccion getSeccion()
  {
    return seccion;
  }

  public void setSeccion(Seccion seccion)
  {
    this.seccion = seccion;
  }

  public Usuario getUsuario()
  {
    return usuario;
  }

  public void setUsuario(Usuario usuario)
  {
    this.usuario = usuario;
  }

  public String getNombre()
  {
    return nombre;
  }

  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  
  
}
