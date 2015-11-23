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
}
