/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import java.lang.reflect.Field;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class Reactivo {
    private int id_reactivo;
    private String nombre;
    private TipoReactivo tipo_reactivo;
    private String preparacion;
    
    private List<CertificadoReactivo> certificados;

    public Reactivo() {
    }

    public int getId_reactivo() {
        return id_reactivo;
    }

    public void setId_reactivo(int id_reactivo) {
        this.id_reactivo = id_reactivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoReactivo getTipo_reactivo() {
        return tipo_reactivo;
    }

    public void setTipo_reactivo(TipoReactivo tipo_reactivo) {
        this.tipo_reactivo = tipo_reactivo;
    }

    public String getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }

    public List<CertificadoReactivo> getCertificados() {
        return certificados;
    }

    public void setCertificados(List<CertificadoReactivo> certificados) {
        this.certificados = certificados;
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
            }JSON.put("id_tipo_reactivo", this.getTipo_reactivo().getId_tipo_reactivo());
                    
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
    
    
}
