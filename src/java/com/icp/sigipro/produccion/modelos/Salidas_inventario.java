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
public class Salidas_inventario {
  private int id_sxi;
  private int id_salida;
  private int id_inventario_pt;
  private int cantidad;
  
  private Salida_Ext salida;
  private Inventario_PT inventario;

  public int getId_sxi() {
    return id_sxi;
  }

  public void setId_sxi(int id_sxi) {
    this.id_sxi = id_sxi;
  }

  public int getId_salida() {
    return id_salida;
  }

  public void setId_salida(int id_salida) {
    this.id_salida = id_salida;
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

  public Salida_Ext getSalida() {
    return salida;
  }

  public void setSalida(Salida_Ext salida) {
    this.salida = salida;
  }

  public Inventario_PT getInventario() {
    return inventario;
  }

  public void setInventario(Inventario_PT inventario) {
    this.inventario = inventario;
  }
  
  
}
