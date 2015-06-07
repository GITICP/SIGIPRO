/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author Walter
 */
public class EventoClinicoCaballo {
    private EventoClinico id_evento;
    private Caballo id_caballo;

    public EventoClinicoCaballo() {
    }

    public EventoClinicoCaballo(EventoClinico id_evento, Caballo id_caballo) {
        this.id_evento = id_evento;
        this.id_caballo = id_caballo;
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
            JSON.put("id_evento",this.id_evento.getId_evento());
            JSON.put("id_caballo",this.id_caballo.getId_caballo());

        }catch (Exception e){
            
        }
        return JSON.toString();
    }    

    public EventoClinico getId_evento() {
        return id_evento;
    }

    public void setId_evento(EventoClinico id_evento) {
        this.id_evento = id_evento;
    }

    public Caballo getId_caballo() {
        return id_caballo;
    }

    public void setId_caballo(Caballo id_caballo) {
        this.id_caballo = id_caballo;
    }
    
}
