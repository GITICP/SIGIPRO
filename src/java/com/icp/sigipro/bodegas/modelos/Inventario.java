/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

import com.icp.sigipro.core.IModelo;
import com.icp.sigipro.seguridad.modelos.Seccion;

/**
 *
 * @author Boga
 */
public class Inventario extends IModelo
{
  private int id_inventario;
  private ProductoInterno producto;
  private Seccion seccion;
  private int stock_actual;

  public int getId_inventario()
  {
    return id_inventario;
  }

  public void setId_inventario(int id_inventario)
  {
    this.id_inventario = id_inventario;
  }

  public ProductoInterno getProducto()
  {
    return producto;
  }

  public void setProducto(ProductoInterno producto)
  {
    this.producto = producto;
  }

  public Seccion getSeccion()
  {
    return seccion;
  }

  public void setSeccion(Seccion seccion)
  {
    this.seccion = seccion;
  }

  public int getStock_actual()
  {
    return stock_actual;
  }

  public void setStock_actual(int stock_actual)
  {
    this.stock_actual = stock_actual;
  } 
}
