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
public class Despachos_inventario {
  private int id_dxi;
  private int id_despacho;
  private int id_inventario_pt;
  private int cantidad;
  
  private Despacho despacho;
  private Inventario_PT inventario;

  public int getId_dxi() {
    return id_dxi;
  }

  public void setId_dxi(int id_dxi) {
    this.id_dxi = id_dxi;
  }

  public int getId_despacho() {
    return id_despacho;
  }

  public void setId_despacho(int id_despacho) {
    this.id_despacho = id_despacho;
  }

  public int getId_inventario_pt() {
    return id_inventario_pt;
  }

  public void setId_inventario_pt(int id_inventario_pt) {
    this.id_inventario_pt = id_inventario_pt;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public Despacho getDespacho() {
    return despacho;
  }

  public void setDespacho(Despacho despacho) {
    this.despacho = despacho;
  }

  public Inventario_PT getInventario() {
    return inventario;
  }

  public void setInventario(Inventario_PT inventario) {
    this.inventario = inventario;
  }
  
  
}
