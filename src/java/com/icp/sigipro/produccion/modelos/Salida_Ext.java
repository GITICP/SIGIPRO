/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;
import java.lang.reflect.Field;
import org.json.JSONObject;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 *
 * @author Amed
 */
public class Salida_Ext {
  private int id_salida;
  private Date fecha;
  private String tipo;
  private String observaciones;
  private int total;
  
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
  
   public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }
  
  public int getId_salida() {
    return id_salida;
  }

  public void setId_salida(int id_salida) {
    this.id_salida = id_salida;
  }

  public Date getFecha() {
    return fecha;
  }
    public String getFecha_S(){
    if (this.fecha != null)
    {return formatearFecha(fecha);}
    else
    {return "";}}
  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }
  
  
}
