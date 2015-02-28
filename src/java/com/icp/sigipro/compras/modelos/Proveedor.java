/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.compras.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author Walter
 */
public class Proveedor {
   
    int id_proveedor;
    String nombre_proveedor;
    String telefono1;
    String telefono2;
    String telefono3;
    String correo;

    public Proveedor() {
    }
    
    public Proveedor(int id_proveedor, String nombre_proveedor, String telefono1, String telefono2, String telefono3, String correo) {
        this.id_proveedor = id_proveedor;
        this.nombre_proveedor = nombre_proveedor;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.telefono3 = telefono3;
        this.correo = correo;
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
    
    public int getId_proveedor() {
        return id_proveedor;
    }

    public String getNombre_proveedor() {
        return nombre_proveedor;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public String getTelefono3() {
        return telefono3;
    }

    public String getCorreo() {
        return correo;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public void setNombre_proveedor(String nombre_proveedor) {
        this.nombre_proveedor = nombre_proveedor;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public void setTelefono3(String telefono3) {
        this.telefono3 = telefono3;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
