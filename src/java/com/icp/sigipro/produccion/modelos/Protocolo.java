/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;

import java.lang.reflect.Field;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Amed
 */
public class Protocolo {
  private int id_protocolo;
  private int id_historial;
  private String nombre;
  private String descripcion;
  private Formula_Maestra formula_maestra;
  private Catalogo_PT producto;
  private boolean aprobacion_calidad;
  private boolean aprobacion_direccion;
  private boolean aprobacion_regente;
  private boolean aprobacion_coordinador;
  private boolean aprobacion_gestion;
  private String observaciones;
  private int version;
  private List<Paso> pasos;
  private List<Protocolo> historial;

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<Protocolo> getHistorial() {
        return historial;
    }

    public void setHistorial(List<Protocolo> historial) {
        this.historial = historial;
    }

    public boolean isAprobacion_gestion() {
        return aprobacion_gestion;
    }

    public void setAprobacion_gestion(boolean aprobacion_gestion) {
        this.aprobacion_gestion = aprobacion_gestion;
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
            JSON.put("id_producto",this.producto.getId_catalogo_pt());
            JSON.put("id_formula_m",this.formula_maestra.getId_formula_maestra());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }

    public List<Paso> getPasos() {
        return pasos;
    }

    public void setPasos(List<Paso> pasos) {
        this.pasos = pasos;
    }
    
 
 
    public int getId_historial() {
        return id_historial;
    }

    public void setId_historial(int id_historial) {
        this.id_historial = id_historial;
    }

    public boolean getAprobacion_regente() {
        return aprobacion_regente;
    }

    public void setAprobacion_regente(boolean aprobacion_regente) {
        this.aprobacion_regente = aprobacion_regente;
    }

    public boolean getAprobacion_coordinador() {
        return aprobacion_coordinador;
    }

    public void setAprobacion_coordinador(boolean aprobacion_coordinador) {
        this.aprobacion_coordinador = aprobacion_coordinador;
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

  public boolean getAprobacion_calidad() {
    return aprobacion_calidad;
  }

  public void setAprobacion_calidad(boolean aprobacion_calidad) {
    this.aprobacion_calidad = aprobacion_calidad;
  }

  public boolean getAprobacion_direccion() {
    return aprobacion_direccion;
  }

  public void setAprobacion_direccion(boolean aprobacion_direccion) {
    this.aprobacion_direccion = aprobacion_direccion;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }
 
}