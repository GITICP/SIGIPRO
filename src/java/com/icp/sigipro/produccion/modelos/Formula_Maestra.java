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
public class Formula_Maestra {
  private int id_formula_m;
  private String nombre;
  private String descripcion;

  public int getId_formula_m() {
    return id_formula_m;
  }

  public void setId_formula_m(int id_formula_m) {
    this.id_formula_m = id_formula_m;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  
}
