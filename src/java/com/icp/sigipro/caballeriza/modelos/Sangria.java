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
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Walter
 */
public class Sangria {
    private int id_sangria;
    private SangriaPrueba sangria_prueba;
    private Date fecha_dia1;
    private Date fecha_dia2;
    private Date fecha_dia3;
    private float hematrocito_promedio;
    private int num_inf_cc;
    private String responsable;
    private int cantidad_de_caballos;
    private float sangre_total;
    private float peso_plasma_total;
    private float volumen_plasma_total;
    private float plasma_por_caballo;
    private float potencia;
    List<SangriaCaballo> sangrias_caballos;

    public Sangria() {
    }

    public Sangria(int id_sangria, SangriaPrueba sangria_prueba, Date fecha_dia1, Date fecha_dia2, Date fecha_dia3, float hematrocito_promedio, int num_inf_cc, String responsable, int cantidad_de_caballos, float sangre_total, float peso_plasma_total, float volumen_plasma_total, float plasma_por_caballo, float potencia) {
        this.id_sangria = id_sangria;
        this.sangria_prueba = sangria_prueba;
        this.fecha_dia1 = fecha_dia1;
        this.fecha_dia2 = fecha_dia2;
        this.fecha_dia3 = fecha_dia3;
        this.hematrocito_promedio = hematrocito_promedio;
        this.num_inf_cc = num_inf_cc;
        this.responsable = responsable;
        this.cantidad_de_caballos = cantidad_de_caballos;
        this.sangre_total = sangre_total;
        this.peso_plasma_total = peso_plasma_total;
        this.volumen_plasma_total = volumen_plasma_total;
        this.plasma_por_caballo = plasma_por_caballo;
        this.potencia = potencia;
    }

    public int getId_sangria() {
        return id_sangria;
    }

    public void setId_sangria(int id_sangria) {
        this.id_sangria = id_sangria;
    }

    public SangriaPrueba getSangria_prueba() {
        return sangria_prueba;
    }

    public void setSangria_prueba(SangriaPrueba sangria_prueba) {
        this.sangria_prueba = sangria_prueba;
    }

    public Date getFecha_dia1() {
        return fecha_dia1;
    }
    
    public String getFecha_dia1AsString() {
        return formatearFecha(fecha_dia1);
    }

    public void setFecha_dia1(Date fecha_dia1) {
        this.fecha_dia1 = fecha_dia1;
    }

    public Date getFecha_dia2() {
        return fecha_dia2;
    }
    
    public String getFecha_dia2AsString() {
        return formatearFecha(fecha_dia2);
    }
    
    public void setFecha_dia2(Date fecha_dia2) {
        this.fecha_dia2 = fecha_dia2;
    }
    
    public Date getFecha_dia3() {
        return fecha_dia3;
    }
    
    public String getFecha_dia3AsString() {
        return formatearFecha(fecha_dia3);
    }
    
    public void setFecha_dia3(Date fecha_dia3) {
        this.fecha_dia3 = fecha_dia3;
    }
    

    public float getHematrocito_promedio() {
        return hematrocito_promedio;
    }

    public void setHematrocito_promedio(float hematrocito_promedio) {
        this.hematrocito_promedio = hematrocito_promedio;
    }

    public int getNum_inf_cc() {
        return num_inf_cc;
    }

    public void setNum_inf_cc(int num_inf_cc) {
        this.num_inf_cc = num_inf_cc;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public int getCantidad_de_caballos() {
        return cantidad_de_caballos;
    }

    public void setCantidad_de_caballos(int cantidad_de_caballos) {
        this.cantidad_de_caballos = cantidad_de_caballos;
    }

    public float getSangre_total() {
        return sangre_total;
    }

    public void setSangre_total(float sangre_total) {
        this.sangre_total = sangre_total;
    }

    public float getPeso_plasma_total() {
        return peso_plasma_total;
    }

    public void setPeso_plasma_total(float peso_plasma_total) {
        this.peso_plasma_total = peso_plasma_total;
    }

    public float getVolumen_plasma_total() {
        return volumen_plasma_total;
    }

    public void setVolumen_plasma_total(float volumen_plasma_total) {
        this.volumen_plasma_total = volumen_plasma_total;
    }

    public float getPlasma_por_caballo() {
        return plasma_por_caballo;
    }

    public void setPlasma_por_caballo(float plasma_por_caballo) {
        this.plasma_por_caballo = plasma_por_caballo;
    }

    public float getPotencia() {
        return potencia;
    }

    public void setPotencia(float potencia) {
        this.potencia = potencia;
    }

    public List<SangriaCaballo> getSangrias_caballos()
    {
        return sangrias_caballos;
    }

    public void setSangrias_caballos(List<SangriaCaballo> sangrias_caballos)
    {
        this.sangrias_caballos = sangrias_caballos;
    }
    
    public void agregarSangriaCaballo(SangriaCaballo sangria_caballo) {
        if(sangrias_caballos == null) {
            sangrias_caballos = new ArrayList<SangriaCaballo>();
        }
        sangrias_caballos.add(sangria_caballo);
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
            JSON.put("id_sangria_prueba",this.sangria_prueba.getId_sangria_prueba());

        }catch (Exception e){
            
        }
        return JSON.toString();
    }
    
    private String formatearFecha(Date fecha) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }    
}
