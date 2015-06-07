/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.modelos;
import java.lang.reflect.Field;
import org.json.JSONObject;
/**
 *
 * @author Amed
 */
public class Grupohembras {
  private int id_grupo;
  private String identificador;
  private int cantidad_espacios;
          
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

  public int getId_grupo() {
    return id_grupo;
  }

  public void setId_grupo(int id_grupo) {
    this.id_grupo = id_grupo;
  }

  public String getIdentificador() {
    return identificador;
  }

  public void setIdentificador(String identificador) {
    this.identificador = identificador;
  }

  public int getCantidad_espacios() {
    return cantidad_espacios;
  }

  public void setCantidad_espacios(int cantidad_espacios) {
    this.cantidad_espacios = cantidad_espacios;
  }
  
}
