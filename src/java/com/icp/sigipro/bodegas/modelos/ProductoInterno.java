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
  private boolean cuarentena;
  private Reactivo reactivo;
 
  public ProductoInterno(){}

  public int getId_producto()
  {
    return id_producto;
  }

  public void setId_producto(int id_producto)
  {
    this.id_producto = id_producto;
  }

  public String getNombre()
  {
    return nombre;
  }

  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }

  public String getCodigo_icp()
  {
    return codigo_icp;
  }

  public void setCodigo_icp(String codigo_icp)
  {
    this.codigo_icp = codigo_icp;
  }

  public int getStock_minimo()
  {
    return stock_minimo;
  }

  public void setStock_minimo(int stock_minimo)
  {
    this.stock_minimo = stock_minimo;
  }

  public int getStock_maximo()
  {
    return stock_maximo;
  }

  public void setStock_maximo(int stock_maximo)
  {
    this.stock_maximo = stock_maximo;
  }

  public String getUbicacion()
  {
    return ubicacion;
  }

  public void setUbicacion(String ubicacion)
  {
    this.ubicacion = ubicacion;
  }

  public String getPresentacion()
  {
    return presentacion;
  }

  public void setPresentacion(String presentacion)
  {
    this.presentacion = presentacion;
  }

  public String getDescripcion()
  {
    return descripcion;
  }

  public void setDescripcion(String descripcion)
  {
    this.descripcion = descripcion;
  }

  public boolean isCuarentena()
  {
    return cuarentena;
  }

  public void setCuarentena(boolean cuarentena)
  {
    this.cuarentena = cuarentena;
  }
  
  public Reactivo getReactivo()
  {
    return reactivo;
  }

  public void setReactivo(Reactivo reactivo)
  {
    this.reactivo = reactivo;
  }
  
  
  
}
