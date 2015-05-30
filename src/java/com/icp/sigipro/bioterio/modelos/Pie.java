/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.modelos;
import java.lang.reflect.Field;
import org.json.JSONObject;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 *
 * @author Amed
 */
public class Pie {
  private int id_pie;
  private String codigo;
  private String fuente;
  private Date fecha_ingreso;
  private Date fecha_retiro;

  public int getId_pie() {
    return id_pie;
  }

  public void setId_pie(int id_pie) {
    this.id_pie = id_pie;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getFuente() {
    return fuente;
  }

  public void setFuente(String fuente) {
    this.fuente = fuente;
  }

  public Date getFecha_ingreso() {
    return fecha_ingreso;
  }

  public void setFecha_ingreso(Date fecha_ingreso) {
    this.fecha_ingreso = fecha_ingreso;
  }

  public Date getFecha_retiro() {
    return fecha_retiro;
  }

  public void setFecha_retiro(Date fecha_retiro) {
    this.fecha_retiro = fecha_retiro;
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
        }catch (Exception e){
            
        }
        return JSON.toString();
    } 
  private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }  
  public String getFecha_ingreso_S(){
    if (this.fecha_ingreso != null)
    {return formatearFecha(fecha_ingreso);}
    else
    {return "";}}
  public String getFecha_retiro_S(){
    if (this.fecha_retiro != null)
    {return formatearFecha(fecha_retiro);}
    else
    {return "";}}

}
