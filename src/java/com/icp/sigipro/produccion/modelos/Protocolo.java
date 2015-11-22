/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author Amed
 */
public class Protocolo {
  private int id_protocolo;
  private String nombre;
  private String descripcion;
  private Formula_Maestra formula_maestra;
  private Catalogo_PT producto;
  private String aprobacion_calidad;
  private String aprobacion_direccion;
  private String version_p;
  
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
            JSON.put("id_producto",this.producto.getId_catalogo_pt());
            JSON.put("id_formula_m",this.formula_maestra.getId_formula_m());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }

  public int getId_protocolo() {
    return id_protocolo;
  }

  public void setId_protocolo(int id_protocolo) {
    this.id_protocolo = id_protocolo;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Formula_Maestra getFormula_maestra() {
    return formula_maestra;
  }

  public void setFormula_maestra(Formula_Maestra formula_maestra) {
    this.formula_maestra = formula_maestra;
  }

  public Catalogo_PT getProducto() {
    return producto;
  }

  public void setProducto(Catalogo_PT producto) {
    this.producto = producto;
  }

  public String getAprobacion_calidad() {
    return aprobacion_calidad;
  }

  public void setAprobacion_calidad(String aprobacion_calidad) {
    this.aprobacion_calidad = aprobacion_calidad;
  }

  public String getAprobacion_direccion() {
    return aprobacion_direccion;
  }

  public void setAprobacion_direccion(String aprobacion_direccion) {
    this.aprobacion_direccion = aprobacion_direccion;
  }

  public String getVersion_p() {
    return version_p;
  }

  public void setVersion_p(String version_p) {
    this.version_p = version_p;
  }
 
}