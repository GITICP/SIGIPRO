/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

/**
 *
 * @author Josue
 */
public class Semanas_cronograma {
    private int id_semana;
    private Cronograma cronograma;
    private Date fecha;
    private String sangria;
    private String plasma_proyectado;
    private String plasma_real;
    private String antivenenos_lote;
    private String antivenenos_proyectada;
    private String antivenenos_bruta;
    private String antivenenos_neta;
    private String entregas_cantidad;
    private String entregar_destino;
    private String entregas_lote;
    private String observaciones;

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
            JSON.put("id_cronograma",this.cronograma.getId_cronograma());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
    
    public int getId_semana() {
        return id_semana;
    }

    public void setId_semana(int id_semana) {
        this.id_semana = id_semana;
    }

    public Cronograma getCronograma() {
        return cronograma;
    }

    public void setCronograma(Cronograma cronograma) {
        this.cronograma = cronograma;
    }

    public Date getFecha() {
        return fecha;
    }

    private String formatearFecha(Date fecha1)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha1);
    }  
    
    public String getFecha_S(){
        if (this.fecha != null)
            {return formatearFecha(this.fecha);}
        else
            {return "";}
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSangria() {
        return sangria;
    }

    public void setSangria(String sangria) {
        this.sangria = sangria;
    }

    public String getPlasma_proyectado() {
        return plasma_proyectado;
    }

    public void setPlasma_proyectado(String plasma_proyectado) {
        this.plasma_proyectado = plasma_proyectado;
    }

    public String getPlasma_real() {
        return plasma_real;
    }

    public void setPlasma_real(String plasma_real) {
        this.plasma_real = plasma_real;
    }

    public String getAntivenenos_lote() {
        return antivenenos_lote;
    }

    public void setAntivenenos_lote(String antivenenos_lote) {
        this.antivenenos_lote = antivenenos_lote;
    }

    public String getAntivenenos_proyectada() {
        return antivenenos_proyectada;
    }

    public void setAntivenenos_proyectada(String antivenenos_proyectada) {
        this.antivenenos_proyectada = antivenenos_proyectada;
    }

    public String getAntivenenos_bruta() {
        return antivenenos_bruta;
    }

    public void setAntivenenos_bruta(String antivenenos_bruta) {
        this.antivenenos_bruta = antivenenos_bruta;
    }

    public String getAntivenenos_neta() {
        return antivenenos_neta;
    }

    public void setAntivenenos_neta(String antivenenos_neta) {
        this.antivenenos_neta = antivenenos_neta;
    }

    public String getEntregas_cantidad() {
        return entregas_cantidad;
    }

    public void setEntregas_cantidad(String entregas_cantidad) {
        this.entregas_cantidad = entregas_cantidad;
    }

    public String getEntregar_destino() {
        return entregar_destino;
    }

    public void setEntregar_destino(String entregar_destino) {
        this.entregar_destino = entregar_destino;
    }

    public String getEntregas_lote() {
        return entregas_lote;
    }

    public void setEntregas_lote(String entregas_lote) {
        this.entregas_lote = entregas_lote;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    
}
