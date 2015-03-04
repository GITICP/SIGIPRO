/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.configuracion.modelos;

import com.icp.sigipro.core.IModelo;
import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author Boga
 */
public class Seccion extends IModelo
{

  int id_seccion;
  String nombre_seccion;
  String descripcion;

  public Seccion()
  {
  }

  public Seccion(int p_id_seccion, String p_nombre_seccion, String p_descripcion)
  {
    id_seccion = p_id_seccion;
    nombre_seccion = p_nombre_seccion;
    descripcion = p_descripcion;
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
    
  
  public int getId_seccion()
  {
    return id_seccion;
  }

  public void setId_seccion(int id_seccion)
  {
    this.id_seccion = id_seccion;
  }

  public void setNombre_seccion(String nombre_seccion)
  {
    this.nombre_seccion = nombre_seccion;
  }

  public void setDescripcion(String descripcion)
  {
    this.descripcion = descripcion;
  }

  public int getID()
  {
    return id_seccion;
  }

  public String getNombre_seccion()
  {
    return nombre_seccion;
  }

  public String getDescripcion()
  {
    return descripcion;
  }

}
