/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author Walter
 */
public class GrupoDeCaballos {
    private int id_grupo_caballo;
    private String nombre;
    private String descripcion;

    public GrupoDeCaballos() {
    }

    public GrupoDeCaballos(int id_grupo_caballo, String nombre, String descripcion) {
        this.id_grupo_caballo = id_grupo_caballo;
        this.nombre = nombre;
        this.descripcion = descripcion;
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
    public int getId_grupo_caballo() {
        return id_grupo_caballo;
    }

    public void setId_grupo_caballo(int id_grupo_caballo) {
        this.id_grupo_caballo = id_grupo_caballo;
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
        this.descripcion = descripcion;
    }
    
    

    
}
