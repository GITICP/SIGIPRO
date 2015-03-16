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
public class Centrifugado {
    private int id_extraccion;
    private int volumen_recuperado;
    private Usuario usuario;
    private Date fecha_volumen_recuperado;

    public Centrifugado() {
    }

    public Centrifugado(int id_extraccion, int volumen_recuperado, Usuario usuario, Date fecha_volumen_recuperado) {
        this.id_extraccion = id_extraccion;
        this.volumen_recuperado = volumen_recuperado;
        this.usuario = usuario;
        this.fecha_volumen_recuperado = fecha_volumen_recuperado;
    }

    public int getId_extraccion() {
        return id_extraccion;
    }

    public void setId_extraccion(int id_extraccion) {
        this.id_extraccion = id_extraccion;
    }

    public int getVolumen_recuperado() {
        return volumen_recuperado;
    }

    public void setVolumen_recuperado(int volumen_recuperado) {
        this.volumen_recuperado = volumen_recuperado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha_volumen_recuperado() {
        return fecha_volumen_recuperado;
    }

    public void setFecha_volumen_recuperado(Date fecha_volumen_recuperado) {
        this.fecha_volumen_recuperado = fecha_volumen_recuperado;
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
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
