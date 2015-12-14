/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;

/**
 *
 * @author Amed
 */
public class Catalogo_PT {
  private int id_catalogo_pt;
  private String nombre;
  private String descripción;

  public int getId_catalogo_pt() {
    return id_catalogo_pt;
  }

  public void setId_catalogo_pt(int id_catalogo_pt) {
    this.id_catalogo_pt = id_catalogo_pt;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripción() {
    return descripción;
  }

  public void setDescripción(String descripción) {
    this.descripción = descripción;
  }
 
}
