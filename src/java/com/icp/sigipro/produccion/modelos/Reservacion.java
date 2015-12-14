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
public class Reservacion {
  private int id_reservacion;
  private Date hasta;
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
  public int getId_reservacion() {
    return id_reservacion;
  }

  public void setId_reservacion(int id_reservacion) {
    this.id_reservacion = id_reservacion;
  }

  public Date getHasta() {
    return hasta;
  }
    public String getHasta_S(){
    if (this.hasta != null)
    {return formatearFecha(hasta);}
    else
    {return "";}}
    
  public void setHasta(Date hasta) {
    this.hasta = hasta;
  }


  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }
  
  
}
