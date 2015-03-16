/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import java.sql.Date;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class Evento {
    private int id_evento;
    private Serpiente serpiente;
    private Usuario usuario;
    private Date fecha_evento;
    private String evento;
    private String observaciones;
    private Extraccion extraccion;

    public Evento() {
    }

    public Evento(int id_evento, Serpiente serpiente, Usuario usuario, Date fecha_evento, String evento, String observaciones, Extraccion extraccion) {
        this.id_evento = id_evento;
        this.serpiente = serpiente;
        this.usuario = usuario;
        this.fecha_evento = fecha_evento;
        this.evento = evento;
        this.observaciones = observaciones;
        this.extraccion = extraccion;
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public Serpiente getSerpiente() {
        return serpiente;
    }

    public void setSerpiente(Serpiente serpiente) {
        this.serpiente = serpiente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha_evento() {
        return fecha_evento;
    }

    public void setFecha_evento(Date fecha_evento) {
        this.fecha_evento = fecha_evento;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
            JSON.put("id_usuario",this.usuario.getId_usuario());
            JSON.put("id_serpiente",this.serpiente.getId_serpiente());
            
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
