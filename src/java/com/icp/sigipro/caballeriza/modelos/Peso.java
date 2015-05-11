/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.modelos;

import com.icp.sigipro.utilidades.HelperFechas;
import java.lang.reflect.Field;
import java.sql.Date;
import org.json.JSONObject;

/**
 *
 * @author Walter
 */
public class Peso
{
    private int id_peso;
    private int id_caballo;
    private float peso;
    private Date fecha;

    public Peso()
    {
    }

    public int getId_peso()
    {
        return id_peso;
    }

    public void setId_peso(int id_peso)
    {
        this.id_peso = id_peso;
    }

    public int getId_caballo()
    {
        return id_caballo;
    }

    public void setId_caballo(int id_caballo)
    {
        this.id_caballo = id_caballo;
    }

    public float getPeso()
    {
        return peso;
    }

    public void setPeso(float peso)
    {
        this.peso = peso;
    }

    public Date getFecha()
    {
        return fecha;
    }
    
    public String getFechaAsString()
    {
        HelperFechas helper = HelperFechas.getSingletonHelperFechas();
        String resultado = "";
        if(fecha != null) resultado = helper.formatearFecha(fecha);
        return resultado;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    //Parsea a JSON la clase de forma automatica y estandarizada para todas las clases
    public String parseJSON()
    {
        Class _class = this.getClass();
        JSONObject JSON = new JSONObject();
        try {
            Field properties[] = _class.getDeclaredFields();
            for (int i = 0; i < properties.length; i++) {
                Field field = properties[i];
                if (i != 0) {
                    JSON.put(field.getName(), field.get(this));
                }
                else {
                    JSON.put("id_objeto", field.get(this));
                }
            }
        }
        catch (Exception e) {

        }
        return JSON.toString();
    }
}
