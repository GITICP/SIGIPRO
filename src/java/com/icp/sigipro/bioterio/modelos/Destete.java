/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.modelos;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONObject;
/**
 *
 * @author Amed
 */
public class Destete {
  private int id_destete;
  private Date fecha_destete;
  private int numero_hembras;
  private int numero_machos;
  
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
  private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }  
  public String getFecha_destete_S(){
    if (this.fecha_destete != null)
    {return formatearFecha(fecha_destete);}
    else
    {return "Sin fecha";}}

  public int getId_destete() {
    return id_destete;
  }

  public void setId_destete(int id_destete) {
    this.id_destete = id_destete;
  }

  public Date getFecha_destete() {
    return fecha_destete;
  }

  public void setFecha_destete(Date fecha_destete) {
    this.fecha_destete = fecha_destete;
  }

  public int getNumero_hembras() {
    return numero_hembras;
  }

  public void setNumero_hembras(int numero_hembras) {
    this.numero_hembras = numero_hembras;
  }

  public int getNumero_machos() {
    return numero_machos;
  }

  public void setNumero_machos(int numero_machos) {
    this.numero_machos = numero_machos;
  }
  
  
}

