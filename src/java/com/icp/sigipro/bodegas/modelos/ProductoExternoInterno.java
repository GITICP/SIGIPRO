/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

/**
 *
 * @author Amed
 */
public class ProductoExternoInterno {
  private int id_producto_ext;
  private int id_producto;
  
  public ProductoExternoInterno(int idext, int id){
  this.id_producto_ext = idext;
  this.id_producto = id;}
  
  public ProductoExternoInterno(){};
  
  public int getId_producto()
  {
    return id_producto;
  }
  public void setId_producto(int id_producto)
  {
    this.id_producto = id_producto;
  }
  public int getId_producto_ext()
  {
    return id_producto_ext;
  }
  public void setId_producto_ext(int id_producto)
  {
    this.id_producto_ext = id_producto;
  }
}
