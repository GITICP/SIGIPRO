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
public class Liofilizacion {
    private int id_extraccion;
    private Usuario usuario_inicio;
    private Date fecha_inicio;
    private int peso_recuperado;
    private Usuario usuario_fin;
    private Date fecha_fin;
    
    private Extraccion extraccion;

    public Liofilizacion() {
    }

    public Liofilizacion(int id_extraccion, Usuario usuario_inicio, Date fecha_inicio, int peso_recuperado, Usuario usuario_fin, Date fecha_fin, Extraccion extraccion) {
        this.id_extraccion = id_extraccion;
        this.usuario_inicio = usuario_inicio;
        this.fecha_inicio = fecha_inicio;
        this.peso_recuperado = peso_recuperado;
        this.usuario_fin = usuario_fin;
        this.fecha_fin = fecha_fin;
        this.extraccion = extraccion;
    }

    public int getId_extraccion() {
        return id_extraccion;
    }

    public void setId_extraccion(int id_extraccion) {
        this.id_extraccion = id_extraccion;
    }

    public Usuario getUsuario_inicio() {
        return usuario_inicio;
    }

    public void setUsuario_inicio(Usuario usuario_inicio) {
        this.usuario_inicio = usuario_inicio;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public int getPeso_recuperado() {
        return peso_recuperado;
    }

    public void setPeso_recuperado(int peso_recuperado) {
        this.peso_recuperado = peso_recuperado;
    }

    public Usuario getUsuario_fin() {
        return usuario_fin;
    }

    public void setUsuario_fin(Usuario usuario_fin) {
        this.usuario_fin = usuario_fin;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
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
            JSON.put("id_usuario_inicio",this.usuario_inicio.getId_usuario());
            JSON.put("id_usuario_fin",this.usuario_fin.getId_usuario());
            
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
