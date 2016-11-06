/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author Amed
 */
public class Permiso {
    int idPermiso;
    String nombrePermiso;
    String descripcionPermiso;
    Seccion seccion;

    public Permiso(){}
    public Permiso (int p_idPermiso,
    String p_nombrePermiso,
    String p_descripcionPermiso, Seccion p_seccion)
    {
        idPermiso = p_idPermiso;
        nombrePermiso = p_nombrePermiso;
        descripcionPermiso = p_descripcionPermiso; 
        seccion = p_seccion;
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
    
    public int getID()                 {return idPermiso;}
    public String getNombrePermiso()   {return nombrePermiso;}
    public String getDescripcion()          {return descripcionPermiso;}
    public Seccion getSeccion() {return seccion;}

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getDescripcionPermiso() {
        return descripcionPermiso;
    }

    public void setDescripcionPermiso(String descripcionPermiso) {
        this.descripcionPermiso = descripcionPermiso;
    }

    public void setNombrePermiso(String nombrePermiso) {
        this.nombrePermiso = nombrePermiso;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }
}
