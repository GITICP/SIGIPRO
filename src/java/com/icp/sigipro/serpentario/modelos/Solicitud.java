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
public class Solicitud {
    private int id_solicitud;
    private Date fecha_solicitud;
    private Especie especie;
    private float cantidad;
    private Usuario usuario;
    private String proyecto;
    private String estado;
    private String observaciones;

    public Solicitud() {
    }

    public Solicitud(int id_solicitud, Date fecha_solicitud, Especie especie, float cantidad, Usuario usuario, String proyecto, String estado) {
        this.id_solicitud = id_solicitud;
        this.fecha_solicitud = fecha_solicitud;
        this.especie = especie;
        this.cantidad = cantidad;
        this.usuario = usuario;
        this.proyecto = proyecto;
        this.estado = estado;
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFecha_solicitud() {
        return fecha_solicitud;
    }

    public void setFecha_solicitud(Date fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
            JSON.put("id_especie",this.especie.getId_especie());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
