/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;

import java.lang.reflect.Field;
import java.sql.SQLXML;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Amed
 */
public class Actividad_Apoyo {

    private int id_actividad;
    private String nombre;
    private SQLXML estructura;
    private String estructuraString;
    private Categoria_AA categoria;
    private boolean aprobacion_calidad;
    private boolean aprobacion_direccion;
    private boolean aprobacion_regente;
    private boolean aprobacion_coordinador;
    private boolean aprobacion_gestion;
    private int version;
    private int id_historial;
    private String observaciones;
    private List<Actividad_Apoyo> historial;
    private boolean requiere_ap;
    private boolean estado;
    private boolean requiere_coordinacion;
    private boolean requiere_regencia;

    public Actividad_Apoyo() {
    }

    public boolean isRequiere_coordinacion() {
        return requiere_coordinacion;
    }

    public void setRequiere_coordinacion(boolean requiere_coordinacion) {
        this.requiere_coordinacion = requiere_coordinacion;
    }

    public boolean isRequiere_regencia() {
        return requiere_regencia;
    }

    public void setRequiere_regencia(boolean requiere_regencia) {
        this.requiere_regencia = requiere_regencia;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isRequiere_ap() {
        return requiere_ap;
    }

    public void setRequiere_ap(boolean requiere_ap) {
        this.requiere_ap = requiere_ap;
    }

    public boolean isAprobacion_gestion() {
        return aprobacion_gestion;
    }

    public void setAprobacion_gestion(boolean aprobacion_gestion) {
        this.aprobacion_gestion = aprobacion_gestion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getId_actividad() {
        return id_actividad;
    }

    public void setId_actividad(int id_actividad) {
        this.id_actividad = id_actividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public SQLXML getEstructura() {
        return estructura;
    }

    public void setEstructura(SQLXML estructura) {
        this.estructura = estructura;
    }

    public String getEstructuraString() {
        return estructuraString;
    }

    public void setEstructuraString(String estructuraString) {
        this.estructuraString = estructuraString;
    }

    public Categoria_AA getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria_AA categoria) {
        this.categoria = categoria;
    }

    public boolean isAprobacion_calidad() {
        return aprobacion_calidad;
    }

    public void setAprobacion_calidad(boolean aprobacion_calidad) {
        this.aprobacion_calidad = aprobacion_calidad;
    }

    public boolean isAprobacion_direccion() {
        return aprobacion_direccion;
    }

    public void setAprobacion_direccion(boolean aprobacion_direccion) {
        this.aprobacion_direccion = aprobacion_direccion;
    }

    public boolean isAprobacion_regente() {
        return aprobacion_regente;
    }

    public void setAprobacion_regente(boolean aprobacion_regente) {
        this.aprobacion_regente = aprobacion_regente;
    }

    public boolean isAprobacion_coordinador() {
        return aprobacion_coordinador;
    }

    public void setAprobacion_coordinador(boolean aprobacion_coordinador) {
        this.aprobacion_coordinador = aprobacion_coordinador;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getId_historial() {
        return id_historial;
    }

    public void setId_historial(int id_historial) {
        this.id_historial = id_historial;
    }

    public List<Actividad_Apoyo> getHistorial() {
        return historial;
    }

    public void setHistorial(List<Actividad_Apoyo> historial) {
        this.historial = historial;
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
            }JSON.put("id_categoria_aa", this.getCategoria().getId_categoria_aa());

        } catch (Exception e) {

        }
        return JSON.toString();
    }

}
