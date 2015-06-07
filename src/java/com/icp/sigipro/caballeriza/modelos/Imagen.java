/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;
import org.postgresql.util.Base64;

/**
 *
 * @author ld.conejo
 */
public class Imagen {
    private int id_imagen;
    private int id_caballo;
    private long imagen_tamano;
    private byte[] imagen;

    public Imagen() {
    }

    public int getId_imagen() {
        return id_imagen;
    }

    public void setId_imagen(int id_imagen) {
        this.id_imagen = id_imagen;
    }

    public int getId_caballo() {
        return id_caballo;
    }

    public void setId_caballo(int id_caballo) {
        this.id_caballo = id_caballo;
    }

    public long getImagen_tamano() {
        return imagen_tamano;
    }

    public void setImagen_tamano(long imagen_tamano) {
        this.imagen_tamano = imagen_tamano;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
    
    public String getImagen_ver(){
        return "data:image/jpeg;base64," + Base64.encodeBytes(this.getImagen());
    }
    
    //Parsea a JSON la clase de forma automatica y estandarizada para todas las clases
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
            }
        } catch (Exception e) {

        }
        return JSON.toString();
    }

}
