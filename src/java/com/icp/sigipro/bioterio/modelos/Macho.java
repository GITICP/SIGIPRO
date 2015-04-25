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
public class Macho {
  private int id_macho;
  private String identificacion;
  private Date fecha_ingreso;
  private String descripcion;
  private Date fecha_retiro;
  private String id_padre;
  private String id_madre;

  public String getId_padre() {
    return id_padre;
  }

  public void setId_padre(String id_padre) {
    this.id_padre = id_padre;
  }

  public String getId_madre() {
    return id_madre;
  }

  public void setId_madre(String id_madre) {
    this.id_madre = id_madre;
  }

  public int getId_macho() {
    return id_macho;
  }

  public void setId_macho(int id_macho) {
    this.id_macho = id_macho;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  public Date getFecha_ingreso() {
    return fecha_ingreso;
  }

  public void setFecha_ingreso(Date fecha_ingreso) {
    this.fecha_ingreso = fecha_ingreso;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
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
  
  public String getFecha_ingreso_S() {
    if (this.fecha_ingreso != null) {
      return formatearFecha(fecha_ingreso);
    } else {
      return "";
    }
  } public String getFecha_retiro_S() {
    if (this.fecha_retiro != null) {
      return formatearFecha(fecha_retiro);
    } else {
      return "";
    }
  }
}

