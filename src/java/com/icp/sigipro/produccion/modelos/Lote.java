/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;

import java.lang.reflect.Field;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class Lote {
    int id_lote;
    Protocolo protocolo;
    String nombre;
    boolean estado;
    int posicion_actual;
    Paso paso_actual;
    
    List <Respuesta_pxp> respuestas;

    public Lote() {
    }

    public Paso getPaso_actual() {
        return paso_actual;
    }

    public void setPaso_actual(Paso paso_actual) {
        this.paso_actual = paso_actual;
    }
    
    

    public List<Respuesta_pxp> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta_pxp> respuestas) {
        this.respuestas = respuestas;
    }

    public int getId_lote() {
        return id_lote;
    }

    public void setId_lote(int id_lote) {
        this.id_lote = id_lote;
    }

    public Protocolo getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(Protocolo protocolo) {
        this.protocolo = protocolo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getPosicion_actual() {
        return posicion_actual;
    }

    public void setPosicion_actual(int posicion_actual) {
        this.posicion_actual = posicion_actual;
    }
    
    public String parseJSON() {
        Class _class = this.getClass();
        JSONObject JSON = new JSONObject();
        try {
            Field properties[] = _class.getDeclaredFields();
            for (int i = 0; i < properties.length; i++) {
                Field field = properties[i];
                if (i != 0) {
                    JSON.put(field.getName(), field.get(this));
                } else {
                    JSON.put("id_objeto", field.get(this));
                }
            }JSON.put("id_protocolo", protocolo.getId_protocolo());

        } catch (Exception e) {

        }
        return JSON.toString();
    }
    
}
