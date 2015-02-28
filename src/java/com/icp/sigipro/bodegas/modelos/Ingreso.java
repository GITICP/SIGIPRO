/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

import com.icp.sigipro.core.IModelo;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.utilidades.HelperFechas;
import java.lang.reflect.Field;
import java.sql.Date;
import org.json.JSONObject;

/**
 *
 * @author Boga
 */
public class Ingreso extends IModelo
{
  public static final String CUARENTENA = "En Cuarentena";
  public static final String DISPONIBLE = "Disponible";
  public static final String NO_DISPONIBLE = "No Disponible";
  public static final String RECHAZADO = "Rechazado";
  
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

      //Parsea a JSON la clase de forma automatica y estandarizada para todas las clases
    public String parseJSON(){
        Class _class = this.getClass();
        JSONObject JSON = new JSONObject();
        try{
            Field properties[] = _class.getDeclaredFields();
            for (int i = 0; i < properties.length; i++) {
                Field field = properties[i];
                if (i != 0){
                    JSON.put(field.getName(), field.get(this));
                }else{
                    JSON.put("id_objeto", field.get(this));
                }
            }          
        }catch (Exception e){
            
        }
        return JSON.toString();
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
  
  public String getFecha_vencimientoAsString(){
    if(fecha_vencimiento != null){
      return formatearFecha(fecha_vencimiento);
    } else {
      return "Producto no perecedero.";
    }
  }
  
  public String getFecha_ingresoAsString(){
    return formatearFecha(fecha_ingreso);
  }
  
  public String getFecha_registroAsString(){
    return formatearFecha(fecha_registro);
  }
  
  private String formatearFecha(Date fecha){
    HelperFechas h = HelperFechas.getSingletonHelperFechas();
    return h.formatearFecha(fecha);
  }
}
