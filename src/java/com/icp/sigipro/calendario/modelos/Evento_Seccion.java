/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.calendario.modelos;
import java.lang.reflect.Field;
import org.json.JSONObject;
/**
 *
 * @author Amed
 */
public class Evento_Seccion {
   private Integer id_evento;
  private Integer id_seccion;

  public Integer getId_evento() {
    return id_evento;
  }

  public void setId_evento(Integer id_evento) {
    this.id_evento = id_evento;
  }

  public Integer getId_seccion() {
    return id_seccion;
  }

  public void setId_seccion(Integer id_seccion) {
    this.id_seccion = id_seccion;
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
}
