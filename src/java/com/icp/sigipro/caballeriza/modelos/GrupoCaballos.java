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
public class GrupoCaballos {

    private GrupoDeCaballos grupo_de_caballos;
    private Caballo caballo;

    public GrupoCaballos() {
    }

    public GrupoCaballos(GrupoDeCaballos grupo_de_caballos, Caballo caballo) {
        this.grupo_de_caballos = grupo_de_caballos;
        this.caballo = caballo;
    }

    public GrupoDeCaballos getGrupo_de_caballos() {
        return grupo_de_caballos;
    }

    public void setGrupo_de_caballos(GrupoDeCaballos grupo_de_caballos) {
        this.grupo_de_caballos = grupo_de_caballos;
    }

    public Caballo getCaballo() {
        return caballo;
    }

    public void setCaballo(Caballo caballo) {
        this.caballo = caballo;
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
            JSON.put("id_grupo_caballo",this.grupo_de_caballos.getId_grupo_caballo());
            JSON.put("id_caballo",this.caballo.getId_caballo());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
