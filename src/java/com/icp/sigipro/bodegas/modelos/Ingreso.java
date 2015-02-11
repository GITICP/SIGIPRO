/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

import com.icp.sigipro.seguridad.modelos.Seccion;
import java.sql.Date;

/**
 *
 * @author Boga
 */
public class Ingreso
{
  int id_ingreso;
  ProductoInterno producto;
  Seccion seccion;
  Date fecha_ingreso;
  Date fecha_registro;
  Date fecha_vencimiento;
  int cantidad;
  String estado;
  int precio;

  public int getId_ingreso()
  {
    return id_ingreso;
  }

  public void setId_ingreso(int id_ingreso)
  {
    this.id_ingreso = id_ingreso;
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

  public Date getFecha_ingreso()
  {
    return fecha_ingreso;
  }

  public void setFecha_ingreso(Date fecha_ingreso)
  {
    this.fecha_ingreso = fecha_ingreso;
  }

  public Date getFecha_registro()
  {
    return fecha_registro;
  }

  public void setFecha_registro(Date fecha_registro)
  {
    this.fecha_registro = fecha_registro;
  }

  public Date getFecha_vencimiento()
  {
    return fecha_vencimiento;
  }

  public void setFecha_vencimiento(Date fecha_vencimiento)
  {
    this.fecha_vencimiento = fecha_vencimiento;
  }

  public int getCantidad()
  {
    return cantidad;
  }

  public void setCantidad(int cantidad)
  {
    this.cantidad = cantidad;
  }

  public String getEstado()
  {
    return estado;
  }

  public void setEstado(String estado)
  {
    this.estado = estado;
  }

  public int getPrecio()
  {
    return precio;
  }

  public void setPrecio(int precio)
  {
    this.precio = precio;
  }
}
