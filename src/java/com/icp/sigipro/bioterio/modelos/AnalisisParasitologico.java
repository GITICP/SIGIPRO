/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

/**
 *
 * @author Amed
 */
public class AnalisisParasitologico {

  private int id_analisis;
  private Date fecha;
  private String numero_informe;
  private boolean especie;
  private String resultados;
  private String tratamiento_dosis;
  private String recetado_por;
  private Date fecha_tratamiento;
  private Usuario responsable;

  public int getId_analisis() {
    return id_analisis;
  }

  public void setId_analisis(int id_analisis) {
    this.id_analisis = id_analisis;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public String getNumero_informe() {
    return numero_informe;
  }

  public void setNumero_informe(String numero_informe) {
    this.numero_informe = numero_informe;
  }

  public boolean isEspecie() {
    return especie;
  }
  
  public String getEspecie() {
      if(especie) {
          return "Ratones";
      } else {
          return "Conejos";
      }
  }

  public void setEspecie(boolean especie) {
    this.especie = especie;
  }

  public String getResultados() {
    return resultados;
  }

  public void setResultados(String resultados) {
    this.resultados = resultados;
  }

  public String getTratamiento_dosis() {
    return tratamiento_dosis;
  }

  public void setTratamiento_dosis(String tratamiento_dosis) {
    this.tratamiento_dosis = tratamiento_dosis;
  }

  public String getRecetado_por() {
    return recetado_por;
  }

  public void setRecetado_por(String recetado_por) {
    this.recetado_por = recetado_por;
  }

  public Date getFecha_tratamiento() {
    return fecha_tratamiento;
  }

  public void setFecha_tratamiento(Date fecha_tratamiento) {
    this.fecha_tratamiento = fecha_tratamiento;
  }

  public Usuario getResponsable() {
    return responsable;
  }

  public void setResponsable(Usuario responsable) {
    this.responsable = responsable;
  }

  public String getFecha_S() {
    if (this.fecha != null) {
      return formatearFecha(fecha);
    } else {
      return "";
    }
  }

  public String getFecha_tratamiento_S() {
    if (this.fecha_tratamiento != null) {
      return formatearFecha(fecha_tratamiento);
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
      JSON.put("id_usuario", this.responsable.getID());
    } catch (Exception e) {

    }
    return JSON.toString();
  }

  private String formatearFecha(Date fecha) {
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    return df.format(fecha);
  }
}
