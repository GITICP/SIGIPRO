/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author Amed
 */
public class Rol {

  int idRol;
  String nombreRol;
  String descripcionRol;

  public Rol(int p_idrol,
          String p_nombrerol,
          String p_descripcionrol) {
    idRol = p_idrol;
    nombreRol = p_nombrerol;
    descripcionRol = p_descripcionrol;
  }

  public Rol(){};
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
  
  
  public int getID() {
    return idRol;
  }

  public void setId_rol(int id_rol){
      this.idRol=id_rol;
  }
  public String getNombreRol() {
    return nombreRol;
  }
  
  public void setNombre_rol(String nombre_rol){
      this.nombreRol = nombre_rol;
  }

  public String getDescripcion() {
    return descripcionRol;
  }
  
  public void setDescripcion(String descripcion){
      this.descripcionRol = descripcion;
  }
  

}
