/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core;

import com.icp.sigipro.bodegas.modelos.ActivoFijo;
import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public abstract class JSON {
        //Parsea a JSON la clase de forma automatica y estandarizada para todas las clases
    public static String parseJSON(Class _this,Object o){
        Class _class = _this;
        JSONObject JSON = new JSONObject();
        ActivoFijo a = (ActivoFijo)o;
        try{
            Field properties[] = _class.getDeclaredFields();
            System.out.println(properties.length);
            for (int i = 0; i < properties.length; i++) {
                Field field = properties[i];
                System.out.println(field.get(a));
                if (i != 0){
                    JSON.put(field.getName(), field.get(a));
                }else{
                    JSON.put("id_objeto", field.get(a));
                }
            }          
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
    
    
}
