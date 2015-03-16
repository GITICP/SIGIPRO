/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class ColeccionHumeda {
    private int id_coleccion_humeda;
    private Serpiente serpiente;
    private String proposito;
    private String observaciones;
    private Usuario usuario;

    public ColeccionHumeda() {
    }

    public ColeccionHumeda(int id_coleccion_humeda, Serpiente serpiente, String proposito, String observaciones, Usuario usuario) {
        this.id_coleccion_humeda = id_coleccion_humeda;
        this.serpiente = serpiente;
        this.proposito = proposito;
        this.observaciones = observaciones;
        this.usuario = usuario;
    }

    public int getId_coleccion_humeda() {
        return id_coleccion_humeda;
    }

    public void setId_coleccion_humeda(int id_coleccion_humeda) {
        this.id_coleccion_humeda = id_coleccion_humeda;
    }

    public Serpiente getSerpiente() {
        return serpiente;
    }

    public void setSerpiente(Serpiente serpiente) {
        this.serpiente = serpiente;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
