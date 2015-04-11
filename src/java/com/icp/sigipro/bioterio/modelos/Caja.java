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
public class Caja {

  private int id_caja;
  private int numero;
          
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

  public int getId_caja() {
    return id_caja;
  }

  public int getNumero() {
    return numero;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }

  public void setId_caja(int id_caja) {
    this.id_caja = id_caja;
  }
  
}
