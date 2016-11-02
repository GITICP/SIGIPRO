/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.calendario.modelos;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONObject;
/**
 *
 * @author Amed
 */
public class Evento {
  private Integer id;
  private String title;
  private Timestamp start;
  private Timestamp end;
  private String description;
  private Boolean allDay;

  private String formatearFecha(Date fecha) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(fecha);
    }
  public String getStart_S() {
        if (this.start != null) {
            Date fecha = new Date(this.start.getTime());
            return formatearFecha(fecha);
        } else {
            return "";
        }
    }
  public String getEnd_S() {
        if (this.end != null) {
            Date fecha = new Date(this.end.getTime());
            return formatearFecha(fecha);
        } else {
            return "";
        }
    }
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Timestamp getStart_date() {
    return start;
  }
  public String getHora() {
    if (this.allDay){return "Todo el día";}
    else{
    int horas = start.getHours();
    int minutos = start.getMinutes();
    String minuto;
    if (minutos==0){
      minuto = "00";
    }
    else
    {minuto = String.valueOf(minutos);}
    String hora = String.valueOf(horas);

    return  hora+":"+minuto;}
  }
  public String getHoraFin() {
    if (this.allDay){return "Todo el día";}
    else{
    int horas = end.getHours();
    int minutos = end.getMinutes();
    String minuto;
    if (minutos==0){
      minuto = "00";
    }
    else
    {minuto = String.valueOf(minutos);}
    String hora = String.valueOf(horas);

    return  hora+":"+minuto;}
  }
  public void setStart_date(Timestamp start_date) {
    this.start = start_date;
  }

  public Timestamp getEnd_date() {
    return end;
  }

  public void setEnd_date(Timestamp end_date) {
    this.end = end_date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getAllDay() {
    return allDay;
  }

  public void setAllDay(Boolean allDay) {
    this.allDay = allDay;
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


