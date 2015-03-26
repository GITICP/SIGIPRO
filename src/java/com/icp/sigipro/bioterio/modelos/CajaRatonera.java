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
public class CajaRatonera {
  private int id_caja_ratonera;
  private int numero_caja;
  private Cepa cepa;
  
  
  public int getId_caja_ratonera() {
    return id_caja_ratonera;
  }

  public void setId_caja_ratonera(int id_caja_ratonera) {
    this.id_caja_ratonera = id_caja_ratonera;
  }

  public int getNumero_caja() {
    return numero_caja;
  }

  public void setNumero_caja(int numero_caja) {
    this.numero_caja = numero_caja;
  }

  public Cepa getCepa() {
    return cepa;
  }

  public void setCepa(Cepa cepa) {
    this.cepa = cepa;
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
            JSON.put("id_cepa",this.cepa.getId_cepa());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
