/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class Equipo {
    private int id_equipo;
    private String nombre;
    private String descripcion;
    private TipoEquipo tipo_equipo;
    
    private List<CertificadoEquipo> certificados;

    public Equipo() {
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = "Sin descripcion";
        if (descripcion != null) {
            if (!descripcion.isEmpty()) {
                this.descripcion = descripcion;
            }
        }
    }

    public TipoEquipo getTipo_equipo() {
        return tipo_equipo;
    }

    public void setTipo_equipo(TipoEquipo tipo_equipo) {
        this.tipo_equipo = tipo_equipo;
    }

    public List<CertificadoEquipo> getCertificados() {
        return certificados;
    }

    public void setCertificados(List<CertificadoEquipo> certificados) {
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
            }
            JSON.put("id_tipo_equipo", this.getTipo_equipo().getId_tipo_equipo());
                    
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
    
    private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }
}
