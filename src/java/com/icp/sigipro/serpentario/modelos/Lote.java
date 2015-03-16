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
public class Lote {
    private int id_lote;
    private Veneno veneno;

    public Lote() {
    }

    public Lote(int id_lote, Veneno veneno) {
        this.id_lote = id_lote;
        this.veneno = veneno;
    }

    public int getId_lote() {
        return id_lote;
    }

    public void setId_lote(int id_lote) {
        this.id_lote = id_lote;
    }

    public Veneno getVeneno() {
        return veneno;
    }

    public void setVeneno(Veneno veneno) {
        this.veneno = veneno;
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
            JSON.put("id_veneno",this.veneno.getId_veneno());

        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
