/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author Amed
 */
public class ConejoProduccion {

  private int id_produccion;
  private String identificador;
  private int cantidad;
  private String detalle_procedencia;

  public int getId_produccion() {
    return id_produccion;
  }

  public void setId_produccion(int id_produccion) {
    this.id_produccion = id_produccion;
  }

  public String getIdentificador() {
    return identificador;
  }

  public void setIdentificador(String identificador) {
    this.identificador = identificador;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public String getDetalle_procedencia() {
    return detalle_procedencia;
  }

  public void setDetalle_procedencia(String detalle_procedencia) {
    this.detalle_procedencia = detalle_procedencia;
  }

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
    } catch (Exception e) {

    }
    return JSON.toString();
  }
}
