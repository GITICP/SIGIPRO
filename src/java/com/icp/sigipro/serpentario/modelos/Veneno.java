/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class Veneno {
    private int id_veneno;
    private boolean restriccion;
    private float cantidad;
    private float cantidad_maxima;
    
    private Especie especie;

    public Veneno() {
    }

    public Veneno(int id_veneno, boolean restriccion, float cantidad_maxima, Especie especie) {
        this.id_veneno = id_veneno;
        this.restriccion = restriccion;
        this.cantidad_maxima = cantidad_maxima;
        this.especie = especie;
    }

    public int getId_veneno() {
        return id_veneno;
    }

    public void setId_veneno(int id_veneno) {
        this.id_veneno = id_veneno;
    }

    public float getCantidad() {
        return cantidad;
    }
    
    public float getCantidadAsMiligramos(){
        return cantidad*1000;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isRestriccion() {
        return restriccion;
    }

    public void setRestriccion(boolean restriccion) {
        this.restriccion = restriccion;
    }

    public float getCantidad_maxima() {
        return cantidad_maxima;
    }

    public void setCantidad_maxima(float cantidad_maxima) {
        this.cantidad_maxima = cantidad_maxima;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
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
