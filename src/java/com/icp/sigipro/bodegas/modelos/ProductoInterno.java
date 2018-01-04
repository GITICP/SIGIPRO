/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

import com.icp.sigipro.core.IModelo;
import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author Boga
 */
public class ProductoInterno extends IModelo
{
  private int id_producto;
  private String nombre;
  private String codigo_icp;
  private int stock_minimo;
  private int stock_maximo;
  private String presentacion;
  private String descripcion;
  private boolean cuarentena;
  private boolean perecedero;
  private Reactivo reactivo;
  private String numero_lote;
 
  public ProductoInterno(){}

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

    public String getNumero_lote() {
        return numero_lote;
    }

    public void setNumero_lote(String numero_lote) {
        this.numero_lote = numero_lote;
    }

  
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
      if (nombre == null){
          return nombre;
      } else {
          return nombre.replaceAll("\"", "");
      }
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

  public boolean isPerecedero()
  {
    return perecedero;
  }

  public void setPerecedero(boolean perecedero)
  {
    this.perecedero = perecedero;
  }
  
  public Reactivo getReactivo()
  {
    return reactivo;
  }

  public void setReactivo(Reactivo reactivo)
  {
    this.reactivo = reactivo;
  }
  
  public String getCuarentena(){
    if (isCuarentena()){
      return "Sí";
    } else {
      return "No";
    }
  }
  
  public String getPerecedero(){
    if (isPerecedero()){
      return "Sí";
    } else {
      return "No";
    }
  }
  
  public String isReactivo(){
    if (getReactivo() == null){
      return "No";
    } else {
      return "Sí";
    }
  }
}
