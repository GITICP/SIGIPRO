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
public class PermisoRol {
     
    int idRol;
    int idPermiso;
    String nombrePermiso;


    public PermisoRol (int p_idrol, int p_idpermiso, String p_nombrepermiso)
    {
        idRol = p_idrol;
        idPermiso = p_idpermiso;
        nombrePermiso = p_nombrepermiso;
    }
    public PermisoRol (int p_idrol, int p_idpermiso)
    {
        idRol = p_idrol;
        idPermiso = p_idpermiso;
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
    
    public int getIDRol()                 {return idRol;}
    public int getIDPermiso()             {return idPermiso;}
    public String getNombrePermiso() {return nombrePermiso;}

}
