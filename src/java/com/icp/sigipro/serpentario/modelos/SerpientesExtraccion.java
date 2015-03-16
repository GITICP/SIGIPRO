/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class SerpientesExtraccion {
    private Serpiente serpiente;
    private Extraccion extraccion;

    public SerpientesExtraccion() {
    }

    public SerpientesExtraccion(Serpiente serpiente, Extraccion extraccion) {
        this.serpiente = serpiente;
        this.extraccion = extraccion;
    }

    public Serpiente getSerpiente() {
        return serpiente;
    }

    public void setSerpiente(Serpiente serpiente) {
        this.serpiente = serpiente;
    }

    public Extraccion getExtraccion() {
        return extraccion;
    }

    public void setExtraccion(Extraccion extraccion) {
        this.extraccion = extraccion;
    }
    
    //Parsea a JSON la clase de forma automatica y estandarizada para todas las clases
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
            JSON.put("id_serpiente",this.serpiente.getId_serpiente());
            JSON.put("id_extraccion",this.extraccion.getId_extraccion());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
