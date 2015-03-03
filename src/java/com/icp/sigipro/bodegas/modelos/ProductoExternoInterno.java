/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

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
