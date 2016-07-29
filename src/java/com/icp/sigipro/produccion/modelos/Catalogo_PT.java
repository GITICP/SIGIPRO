/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author Amed
 */
public class Catalogo_PT {
  private int id_catalogo_pt;
  private String nombre;
  private String descripcion;
  private int vida_util;

  public int getVida_util() {
    return vida_util;
  }

  public void setVida_util(int vida_util) {
    this.vida_util = vida_util;
  }
  
  public int getId_catalogo_pt() {
    return id_catalogo_pt;
  }

  public void setId_catalogo_pt(int id_catalogo_pt) {
    this.id_catalogo_pt = id_catalogo_pt;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
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
}
