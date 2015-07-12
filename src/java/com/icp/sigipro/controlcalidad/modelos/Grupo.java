/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class Grupo {
    private int id_grupo;
    private SolicitudCC solicitud;
    
    private List<Muestra> grupos_muestras;
    private List<Analisis> analisis;

    public Grupo() {
    }

    public int getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }

    public SolicitudCC getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudCC solicitud) {
        this.solicitud = solicitud;
    }

    public List<Muestra> getGrupos_muestras() {
        return grupos_muestras;
    }

    public void setGrupos_muestras(List<Muestra> grupos_muestras) {
        this.grupos_muestras = grupos_muestras;
    }

    public List<Analisis> getAnalisis() {
        return analisis;
    }

    public void setAnalisis(List<Analisis> analisis) {
        this.analisis = analisis;
    }
    
    public void agregarMuestra(Muestra m) {
        if (this.grupos_muestras == null) {
            grupos_muestras = new ArrayList<Muestra>();
        }
        grupos_muestras.add(m);
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
            }JSON.put("id_solicitud", this.getSolicitud().getId_solicitud());
                    
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
