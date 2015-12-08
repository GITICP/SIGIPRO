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
 * @author Amed FALTA EL XML
 */
public class Paso {

    private int id_paso;
    private String nombre;
    private SQLXML estructura;
    private String estructuraString;
    private boolean requiere_ap;
    private int version;
    private int id_historial;
    private int posicion;
    private int contador;
    private List<Paso> historial;

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public List<Paso> getHistorial() {
        return historial;
    }

    public void setHistorial(List<Paso> historial) {
        this.historial = historial;
    }

    
    
    public String getEstructuraString() {
        return estructuraString;
    }

    public void setEstructuraString(String estructuraString) {
        this.estructuraString = estructuraString;
    }

    public Paso() {
    }

    public int getId_paso() {
        return id_paso;
    }

    public void setId_paso(int id_paso) {
        this.id_paso = id_paso;
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

    public boolean isRequiere_ap() {
        return requiere_ap;
    }

    public void setRequiere_ap(boolean requiere_ap) {
        this.requiere_ap = requiere_ap;
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

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
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
