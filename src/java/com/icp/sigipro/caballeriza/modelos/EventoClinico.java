/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import java.sql.Date;
import org.json.JSONObject;

/**
 *
 * @author Walter
 */
public class EventoClinico {
    private int id_evento;
    private Date fecha;
    private String descripcion;
    private Usuario responsable;

    public EventoClinico() {
    }

    public EventoClinico(int id_evento, Date fecha, String descripcion, Usuario responsable) {
        this.id_evento = id_evento;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.responsable = responsable;
    }
    

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
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
            JSON.put("id_usuario",this.responsable.getID());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
    
}
