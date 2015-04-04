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
public class SangriaPrueba {
    private int id_sangria_prueba;
    private String muestra;
    private int num_solicitud;
    private int num_informe;
    private Date fecha_recepcion_muestra;
    private Date fecha_informe;
    private String responsable;
    private Inoculo inoculo;

    public SangriaPrueba() {
    }

    public SangriaPrueba(int id_sangria_prueba, String muestra, int num_solicitud, int num_informe, Date fecha_recepcion_muestra, Date fecha_informe, String responsable, Inoculo inoculo) {
        this.id_sangria_prueba = id_sangria_prueba;
        this.muestra = muestra;
        this.num_solicitud = num_solicitud;
        this.num_informe = num_informe;
        this.fecha_recepcion_muestra = fecha_recepcion_muestra;
        this.fecha_informe = fecha_informe;
        this.responsable = responsable;
        this.inoculo = inoculo;
    }

    public int getId_sangria_prueba() {
        return id_sangria_prueba;
    }

    public void setId_sangria_prueba(int id_sangria_prueba) {
        this.id_sangria_prueba = id_sangria_prueba;
    }

    public String getMuestra() {
        return muestra;
    }

    public void setMuestra(String muestra) {
        this.muestra = muestra;
    }

    public int getNum_solicitud() {
        return num_solicitud;
    }

    public void setNum_solicitud(int num_solicitud) {
        this.num_solicitud = num_solicitud;
    }

    public int getNum_informe() {
        return num_informe;
    }

    public void setNum_informe(int num_informe) {
        this.num_informe = num_informe;
    }

    public Date getFecha_recepcion_muestra() {
        return fecha_recepcion_muestra;
    }
    public String getFecha_recepcion_muestraAsString() {
        return formatearFecha(fecha_recepcion_muestra);
    }
    public void setFecha_recepcion_muestra(Date fecha_recepcion_muestra) {
        this.fecha_recepcion_muestra = fecha_recepcion_muestra;
    }

    public Date getFecha_informe() {
        return fecha_informe;
    }
    public String getFecha_informeAsString() {
        return formatearFecha(fecha_informe);
    }
    public void setFecha_informe(Date fecha_informe) {
        this.fecha_informe = fecha_informe;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Inoculo getInoculo() {
        return inoculo;
    }

    public void setInoculo(Inoculo inoculo) {
        this.inoculo = inoculo;
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
            JSON.put("id_inoculo",this.inoculo.getId_inoculo());

        }catch (Exception e){
            
        }
        return JSON.toString();
    }
    
    private String formatearFecha(Date fecha) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }    
}
