/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import java.lang.reflect.Field;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class TipoMuestra {

    private int id_tipo_muestra;
    private String nombre;
    private String descripcion;

    private List<Analisis> tipos_muestras_analisis;

    public TipoMuestra() {
    }

    public List<Analisis> getTipos_muestras_analisis() {
        return tipos_muestras_analisis;
    }

    public void setTipos_muestras_analisis(List<Analisis> tipos_muestras_analisis) {
        this.tipos_muestras_analisis = tipos_muestras_analisis;
    }

    public int getId_tipo_muestra() {
        return id_tipo_muestra;
    }

    public void setId_tipo_muestra(int id_tipo_muestra) {
        this.id_tipo_muestra = id_tipo_muestra;
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
        if (descripcion == null) {
            this.descripcion = "Sin descripción.";
        } else {
            if (descripcion.isEmpty()) {
                this.descripcion = "Sin descripción.";
            } else {
                this.descripcion = descripcion;
            }
        }
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
