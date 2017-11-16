/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import com.icp.sigipro.core.IModelo;
import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class TipoPatronControl extends IModelo {
    private int id_tipo_patroncontrol;
    private String nombre;
    private String descripcion;

    public TipoPatronControl() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = "Sin descripción.";
        if (descripcion != null) {
            if (!descripcion.isEmpty()) {
                this.descripcion = descripcion;
            }
        }
    }    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_tipo_patroncontrol() {
        return id_tipo_patroncontrol;
    }

    public void setId_tipo_patroncontrol(int id_tipo_patroncontrol) {
        this.id_tipo_patroncontrol = id_tipo_patroncontrol;
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
