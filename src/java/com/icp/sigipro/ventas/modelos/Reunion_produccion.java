/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.modelos;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

/**
 *
 * @author Josue
 */
public class Reunion_produccion {
    private int id_reunion;
    private Date fecha;
    private String observaciones;
    private String minuta;
    private String minuta2;

    public String getMinuta2() {
        return minuta2;
    }

    public void setMinuta2(String minuta2) {
        this.minuta2 = minuta2;
    }
    
    public int getId_reunion() {
        return id_reunion;
    }

    public void setId_reunion(int id_reunion) {
        this.id_reunion = id_reunion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }  
    
    public String getFecha_S(){
        if (this.fecha != null)
            {return formatearFecha(this.fecha);}
        else
            {return "";}
    }
    
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getMinuta() {
        return minuta;
    }

    public void setMinuta(String minuta) {
        this.minuta = minuta;
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
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
