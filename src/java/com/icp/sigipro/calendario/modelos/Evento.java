/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.calendario.modelos;
import java.lang.reflect.Field;
import java.sql.Timestamp;
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


