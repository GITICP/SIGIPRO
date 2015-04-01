/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.modelos;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

/**
 *
 * @author Walter
 */
public class Inoculo {
    private int id_inoculo;
    private String mnn;
    private String baa;
    private String bap;
    private String cdd;
    private String lms;
    private String tetox;
    private String otro;
    private String encargado_preparacion;
    private String encargado_inyeccion;
    private Date fecha;

    public Inoculo() {
    }

    public Inoculo(int id_inoculo, String mnn, String baa, String bap, String cdd, String lms, String tetox, String otro, String encargado_preparacion, String encargado_inyeccion, Date fecha) {
        this.id_inoculo = id_inoculo;
        this.mnn = mnn;
        this.baa = baa;
        this.bap = bap;
        this.cdd = cdd;
        this.lms = lms;
        this.tetox = tetox;
        this.otro = otro;
        this.encargado_preparacion = encargado_preparacion;
        this.encargado_inyeccion = encargado_inyeccion;
        this.fecha = fecha;
    }



    public int getId_inoculo() {
        return id_inoculo;
    }

    public void setId_inoculo(int id_inoculo) {
        this.id_inoculo = id_inoculo;
    }

    public String getMnn() {
        return mnn;
    }

    public void setMnn(String mnn) {
        this.mnn = mnn;
    }

    public String getBaa() {
        return baa;
    }

    public void setBaa(String baa) {
        this.baa = baa;
    }

    public String getBap() {
        return bap;
    }

    public void setBap(String bap) {
        this.bap = bap;
    }

    public String getCdd() {
        return cdd;
    }

    public void setCdd(String cdd) {
        this.cdd = cdd;
    }

    public String getLms() {
        return lms;
    }

    public void setLms(String lms) {
        this.lms = lms;
    }

    public String getTetox() {
        return tetox;
    }

    public void setTetox(String tetox) {
        this.tetox = tetox;
    }

    public String getOtro() {
        return otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }

    public String getEncargado_preparacion() {
        return encargado_preparacion;
    }

    public void setEncargado_preparacion(String encargado_preparacion) {
        this.encargado_preparacion = encargado_preparacion;
    }

    public String getEncargado_inyeccion() {
        return encargado_inyeccion;
    }

    public void setEncargado_inyeccion(String encargado_inyeccion) {
        this.encargado_inyeccion = encargado_inyeccion;
    }

    public Date getFecha() {
        return fecha;
    }
    
    public String getFechaAsString() {
        return formatearFecha(fecha);
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
    
    private String formatearFecha(Date fecha) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }    
}
