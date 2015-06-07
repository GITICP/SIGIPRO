/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.modelos;
import java.lang.reflect.Field;
import org.json.JSONObject;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 *
 * @author Amed
 */
public class Coneja {
  private int id_coneja;
  private Caja caja;
  private Date fecha_nacimiento;
  private String id_padre;
  private String id_madre;
  private Date fecha_retiro;
  private boolean bool_activa;
  private Date fecha_ingreso;
  private Date fecha_cambio;
  private Date fecha_seleccion;
  private String observaciones;

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

  
  public Date getFecha_ingreso() {
    return fecha_ingreso;
  }

  public void setFecha_ingreso(Date fecha_ingreso) {
    this.fecha_ingreso = fecha_ingreso;
  }

  public Date getFecha_cambio() {
    return fecha_cambio;
  }

  public void setFecha_cambio(Date fecha_cambio) {
    this.fecha_cambio = fecha_cambio;
  }

  public Date getFecha_seleccion() {
    return fecha_seleccion;
  }

  public void setFecha_seleccion(Date fecha_seleccion) {
    this.fecha_seleccion = fecha_seleccion;
  }
  
  
  public boolean isBool_activa() {
    return bool_activa;
  }

  public void setBool_activa(boolean bool_activa) {
    this.bool_activa = bool_activa;
  }

  public int getId_coneja() {
    return id_coneja;
  }

  public void setId_coneja(int id_coneja) {
    this.id_coneja = id_coneja;
  }

  public Caja getCaja() {
    return caja;
  }

  public void setCaja(Caja caja) {
    this.caja = caja;
  }

  public Date getFecha_nacimiento() {
    return fecha_nacimiento;
  }

  public void setFecha_nacimiento(Date fecha_nacimiento) {
    this.fecha_nacimiento = fecha_nacimiento;
  }

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

  public Date getFecha_retiro() {
    return fecha_retiro;
  }

  public void setFecha_retiro(Date fecha_retiro) {
    this.fecha_retiro = fecha_retiro;
  }

   public String getFecha_nacimiento_S() {
    if (this.fecha_nacimiento != null) {
      return formatearFecha(fecha_nacimiento);
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
  public String getFecha_ingreso_S() {
    if (this.fecha_ingreso != null) {
      return formatearFecha(fecha_ingreso);
    } else {
      return "";
    }
  }
  public String getFecha_cambio_S() {
    if (this.fecha_cambio != null) {
      return formatearFecha(fecha_cambio);
    } else {
      return "";
    }
  }
  public String getFecha_seleccion_S() {
    if (this.fecha_seleccion != null) {
      return formatearFecha(fecha_seleccion);
    } else {
      return "";
    }
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
            JSON.put("id_caja",this.caja.getId_caja());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
      private String formatearFecha(Date fecha) {
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    return df.format(fecha);
  }
}
