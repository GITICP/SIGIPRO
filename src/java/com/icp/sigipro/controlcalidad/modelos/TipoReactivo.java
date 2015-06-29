/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class TipoReactivo {
    private int id_tipo_reactivo;
    private String nombre;
    private String descripcion;
    private String machote;

    public TipoReactivo() {
    }

    public String getMachote() {
        return machote;
    }

    public void setMachote(String machote) {
        this.machote = machote;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_tipo_reactivo() {
        return id_tipo_reactivo;
    }

    public void setId_tipo_reactivo(int id_tipo_reactivo) {
        this.id_tipo_reactivo = id_tipo_reactivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
