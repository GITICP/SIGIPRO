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
public class PiexCepa {
  private Pie pie;
  private Cepa cepa;
  private Date fecha_inicio;
  private Date fecha_estimada_retiro;

  public Pie getPie() {
    return pie;
  }

  public void setPie(Pie pie) {
    this.pie = pie;
  }

  public Cepa getCepa() {
    return cepa;
  }

  public void setCepa(Cepa cepa) {
    this.cepa = cepa;
  }

  public Date getFecha_inicio() {
    return fecha_inicio;
  }

  public void setFecha_inicio(Date fecha_inicio) {
    this.fecha_inicio = fecha_inicio;
  }

  public Date getFecha_estimada_retiro() {
    return fecha_estimada_retiro;
  }

  public void setFecha_estimada_retiro(Date fecha_estimada_retiro) {
    this.fecha_estimada_retiro = fecha_estimada_retiro;
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
            JSON.put("id_cepa",this.cepa.getId_cepa());
            JSON.put("id_pie",this.pie.getId_pie());
        }catch (Exception e){
            
        }
        return JSON.toString();
    } 
  private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }  
  public String getFecha_inicio_S(){
    if (this.fecha_inicio != null)
    {return formatearFecha(fecha_inicio);}
    else
    {return "";}}
  public String getFecha_estimada_retiro_S(){
    if (this.fecha_estimada_retiro != null)
    {return formatearFecha(fecha_estimada_retiro);}
    else
    {return "";}}

}
