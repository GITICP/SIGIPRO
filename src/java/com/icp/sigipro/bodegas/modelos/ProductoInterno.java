/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

/**
 *
 * @author Boga
 */
public class ProductoInterno
{
  private int id_producto;
  private String nombre;
  private String codigo_icp;
  private int stock_minimo;
  private int stock_maximo;
  private String ubicacion;
  private String presentacion;
  private String descripcion;
  
  public ProductoInterno(){}
  
  public ProductoInterno(int id_producto_interno, String p_nombre, String p_codigo_icp, int p_stock_minimo, int p_stock_maximo, String p_ubicacion, String p_presentacion, String p_descripcion)
  {
    this.nombre = p_nombre;
    this.codigo_icp = p_codigo_icp;
    this.stock_minimo = p_stock_minimo;
    this.stock_maximo = p_stock_maximo;
    this.ubicacion = p_ubicacion;
    this.presentacion = p_presentacion;
    this.descripcion = p_descripcion;
    
  }

  public int getId_producto()
  {
    return id_producto;
  }

  public String getNombre()
  {
    return nombre;
  }

  public String getCodigo_icp()
  {
    return codigo_icp;
  }

  public int getStock_minimo()
  {
    return stock_minimo;
  }

  public int getStock_maximo()
  {
    return stock_maximo;
  }

  public String getUbicacion()
  {
    return ubicacion;
  }

  public String getPresentacion()
  {
    return presentacion;
  }

  public String getDescripcion()
  {
    return descripcion;
  }
  
  public void setId_producto(int id_producto)
  {
    this.id_producto = id_producto;
  }

  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public void setCodigo_icp(String codigo_icp)
  {
    this.codigo_icp = codigo_icp;
  }

  public void setStock_minimo(int stock_minimo)
  {
    this.stock_minimo = stock_minimo;
  }

  public void setStock_maximo(int stock_maximo)
  {
    this.stock_maximo = stock_maximo;
  }

  public void setUbicacion(String ubicacion)
  {
    this.ubicacion = ubicacion;
  }

  public void setPresentacion(String presentacion)
  {
    this.presentacion = presentacion;
  }

  public void setDescripcion(String descripcion)
  {
    this.descripcion = descripcion;
  }
}
