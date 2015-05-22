package com.icp.sigipro.serpentario.modelos;


import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.serpentario.modelos.Veneno;
import java.lang.reflect.Field;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ld.conejo
 */
public class Restriccion {
    private int id_restriccion;
    private Usuario usuario;
    private Veneno veneno;
    private float cantidad_anual;
    private float cantidad_consumida;

    public Restriccion() {
    }

    public Restriccion(int id_restriccion, Usuario usuario, Veneno veneno, float cantidad_anual) {
        this.id_restriccion = id_restriccion;
        this.usuario = usuario;
        this.veneno = veneno;
        this.cantidad_anual = cantidad_anual;
    }

    public float getCantidad_consumida() {
        return cantidad_consumida;
    }

    public void setCantidad_consumida(float cantidad_consumida) {
        this.cantidad_consumida = cantidad_consumida;
    }

    public int getId_restriccion() {
        return id_restriccion;
    }

    public void setId_restriccion(int id_restriccion) {
        this.id_restriccion = id_restriccion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Veneno getVeneno() {
        return veneno;
    }

    public void setVeneno(Veneno veneno) {
        this.veneno = veneno;
    }

    public float getCantidad_anual() {
        return cantidad_anual;
    }

    public void setCantidad_anual(float cantidad_anual) {
        this.cantidad_anual = cantidad_anual;
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
            JSON.put("id_usuario",this.usuario.getId_usuario());
            JSON.put("id_veneno",this.veneno.getId_veneno());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
    
    
    
}
