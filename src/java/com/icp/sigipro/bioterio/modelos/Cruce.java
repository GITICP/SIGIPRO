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
public class Cruce {

  private int id_cruce;
  private Macho macho;
  private Coneja coneja;
  private Date fecha_cruce;
  private String observaciones;
  private int cantidad_paridos;
  private Date fecha_parto;
  private Date fecha_estimada_parto;

  public Date getFecha_estimada_parto() {
    return fecha_estimada_parto;
  }

  public void setFecha_estimada_parto(Date fecha_estimada_parto) {
    this.fecha_estimada_parto = fecha_estimada_parto;
  }

  
  public int getId_cruce() {
    return id_cruce;
  }

  public void setId_cruce(int id_cruce) {
    this.id_cruce = id_cruce;
  }

  public Macho getMacho() {
    return macho;
  }

  public void setMacho(Macho macho) {
    this.macho = macho;
  }

  public Coneja getConeja() {
    return coneja;
  }

  public void setConeja(Coneja coneja) {
    this.coneja = coneja;
  }

  public Date getFecha_cruce() {
    return fecha_cruce;
  }

  public void setFecha_cruce(Date fecha_cruce) {
    this.fecha_cruce = fecha_cruce;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public int getCantidad_paridos() {
    return cantidad_paridos;
  }

  public void setCantidad_paridos(int cantidad_paridos) {
    this.cantidad_paridos = cantidad_paridos;
  }

  public Date getFecha_parto() {
    return fecha_parto;
  }

  public void setFecha_parto(Date fecha_parto) {
    this.fecha_parto = fecha_parto;
  }

  public String getFecha_cruce_S() {
    if (this.fecha_cruce != null) {
      return formatearFecha(fecha_cruce);
    } else {
      return "";
    }
  }
    public String getFecha_parto_S() {
    if (this.fecha_parto != null) {
      return formatearFecha(fecha_parto);
    } else {
      return "";
    }
  }
    public String getFecha_estimada_parto_S() {
    if (this.fecha_estimada_parto != null) {
      return formatearFecha(fecha_estimada_parto);
    } else {
      return "";
    }
  }
  //Parsea a JSON la clase de forma automatica y estandarizada para todas las clases
    public String parseJSON() {
    Class _class = this.getClass();
    JSONObject JSON = new JSONObject();
    try {
      Field properties[] = _class.getDeclaredFields();
      for (int i = 0; i < properties.length; i++) {
        Field field = properties[i];
        if (i != 0) {
          JSON.put(field.getName(), field.get(this));
        } else {
          JSON.put("id_objeto", field.get(this));
        }
      }
      JSON.put("id_coneja", this.coneja.getId_coneja());
      JSON.put("id_macho", this.macho.getId_macho());
    } catch (Exception e) {

    }
    return JSON.toString();
  }

  private String formatearFecha(Date fecha) {
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    return df.format(fecha);
  }

}
