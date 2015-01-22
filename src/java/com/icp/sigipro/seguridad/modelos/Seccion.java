/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.modelos;

/**
 *
 * @author Amed
 */
public class Seccion {
  int id_seccion;
  String nombre_seccion;
  String descripcion;
  
  public Seccion( Integer pid, String pnombre, String pdesc){
  id_seccion = pid;
  nombre_seccion = pnombre;
  descripcion = pdesc;
  }
  
  public int getID()                 {return id_seccion;}
  public String getNombreSeccion()   {return nombre_seccion;}
  public String getDescripcion()          {return descripcion;}
}
