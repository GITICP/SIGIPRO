/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import java.lang.reflect.Field;
import java.sql.Date;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class Muestra {
    private int id_muestra;
    private String identificador;
    private TipoMuestra tipo_muestra;
    private Date fecha_descarte_estimada;
    private Date fecha_descarte_real;

    public Muestra() {
    }

    public int getId_muestra() {
        return id_muestra;
    }

    public void setId_muestra(int id_muestra) {
        this.id_muestra = id_muestra;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public TipoMuestra getTipo_muestra() {
        return tipo_muestra;
    }

    public void setTipo_muestra(TipoMuestra tipo_muestra) {
        this.tipo_muestra = tipo_muestra;
    }

    public Date getFecha_descarte_estimada() {
        return fecha_descarte_estimada;
    }

    public void setFecha_descarte_estimada(Date fecha_descarte_estimada) {
        this.fecha_descarte_estimada = fecha_descarte_estimada;
    }

    public Date getFecha_descarte_real() {
        return fecha_descarte_real;
    }

    public void setFecha_descarte_real(Date fecha_descarte_real) {
        this.fecha_descarte_real = fecha_descarte_real;
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
            }JSON.put("id_tipo_muestra", this.getTipo_muestra().getId_tipo_muestra());
                    
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
    
}
