/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import java.lang.reflect.Field;
import java.sql.SQLXML;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class Resultado {
    private int id_resultado;
    private String path;
    //Debe ser de tipo XML pero por ahora lo dejo asi
    private SQLXML datos;
    
    private List<Reactivo> reactivos_resultado;
    private List<Equipo> equipos_resultado;

    public Resultado() {
    }

    public int getId_resultado() {
        return id_resultado;
    }

    public void setId_resultado(int id_resultado) {
        this.id_resultado = id_resultado;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SQLXML getDatos() {
        return datos;
    }

    public void setDatos(SQLXML datos) {
        this.datos = datos;
    }

    public List<Reactivo> getReactivos_resultado() {
        return reactivos_resultado;
    }

    public void setReactivos_resultado(List<Reactivo> reactivos_resultado) {
        this.reactivos_resultado = reactivos_resultado;
    }

    public List<Equipo> getEquipos_resultado() {
        return equipos_resultado;
    }

    public void setEquipos_resultado(List<Equipo> equipos_resultado) {
        this.equipos_resultado = equipos_resultado;
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
}
