/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author Josue
 */
public class Cotizacion {
    private int id_cotizacion;
    private Intencion_venta intencion;
    private int total;
    private int flete;
    private String moneda;
    private String identificador;

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public int getId_cotizacion() {
        return id_cotizacion;
    }

    public void setId_cotizacion(int id_cotizacion) {
        this.id_cotizacion = id_cotizacion;
    }

    public Intencion_venta getIntencion() {
        return intencion;
    }

    public void setIntencion(Intencion_venta intencion) {
        this.intencion = intencion;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getFlete() {
        return flete;
    }

    public void setFlete(int flete) {
        this.flete = flete;
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
            JSON.put("id_intencion",this.intencion.getId_intencion());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
