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
public class Reservaciones_inventario {
  private int id_rxi;
  private int id_reservacion;
  private int id_inventario_pt;
  private int cantidad;
  
  private Reservacion reservacion;
  private Inventario_PT inventario;

  public int getId_rxi() {
    return id_rxi;
  }

  public void setId_rxi(int id_rxi) {
    this.id_rxi = id_rxi;
  }

  public int getId_reservacion() {
    return id_reservacion;
  }

  public void setId_reservacion(int id_reservacion) {
    this.id_reservacion = id_reservacion;
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

  public Reservacion getReservacion() {
    return reservacion;
  }

  public void setReservacion(Reservacion reservacion) {
    this.reservacion = reservacion;
  }

  public Inventario_PT getInventario() {
    return inventario;
  }

  public void setInventario(Inventario_PT inventario) {
    this.inventario = inventario;
  }
  
  
}
