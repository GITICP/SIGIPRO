/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

import com.icp.sigipro.configuracion.modelos.Seccion;



/**
 *
 * @author Amed
 */
public class Inventario {
  private int id_inventario;
  private int id_producto;
  private int id_seccion;
  private int stock_actual;
  
  public ProductoInterno producto;
  public Seccion seccion;
  
  public Inventario(){};
  public void setId_inventario(int p){this.id_inventario = p;}
  public void setId_producto(int p){this.id_producto = p;}
  public void setId_seccion(int p){this.id_seccion = p;}
  public void setStock_actual(int p){this.stock_actual = p;}
  public int getId_inventario(){return id_inventario;}
  public int getId_producto(){return id_producto;}
  public int getId_seccion(){return id_seccion;}
  public int getStock_actual(){return stock_actual;}
  
}
