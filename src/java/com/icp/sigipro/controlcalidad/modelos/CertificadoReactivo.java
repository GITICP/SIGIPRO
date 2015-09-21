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
public class CertificadoReactivo {
    private int id_certificado_reactivo;
    private Date fecha_certificado;
    private String path;

    public CertificadoReactivo() {
    }

    public int getId_certificado_reactivo() {
        return id_certificado_reactivo;
    }

    public void setId_certificado_reactivo(int id_certificado_reactivo) {
        this.id_certificado_reactivo = id_certificado_reactivo;
    }

    public Date getFecha_certificado() {
        return fecha_certificado;
    }

    public void setFecha_certificado(Date fecha_certificado) {
        this.fecha_certificado = fecha_certificado;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
            }
                    
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
    
}
