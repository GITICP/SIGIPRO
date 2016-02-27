/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import java.sql.Date;

/**
 *
 * @author Boga
 */
public class ResultadoSangriaPrueba {
    private int id_resultado_sangria_prueba;
    private AnalisisGrupoSolicitud ags;
    private String wbc;
    private String rbc;
    private float hematocrito;
    private float hemoglobina;
    private int repeticion;
    private Date fecha;
    
    public ResultadoSangriaPrueba(){}

    public int getId_resultado_sangria_prueba() {
        return id_resultado_sangria_prueba;
    }

    public void setId_resultado_sangria_prueba(int id_resultado_sangria_prueba) {
        this.id_resultado_sangria_prueba = id_resultado_sangria_prueba;
    }

    public String getWbc() {
        return wbc;
    }

    public void setWbc(String wbc) {
        this.wbc = wbc;
    }

    public String getRbc() {
        return rbc;
    }

    public void setRbc(String rbc) {
        this.rbc = rbc;
    }

    public float getHematocrito() {
        return hematocrito;
    }

    public void setHematocrito(float hematocrito) {
        this.hematocrito = hematocrito;
    }

    public float getHemoglobina() {
        return hemoglobina;
    }

    public void setHemoglobina(float hemoglobina) {
        this.hemoglobina = hemoglobina;
    }

    public AnalisisGrupoSolicitud getAgs() {
        return ags;
    }

    public void setAgs(AnalisisGrupoSolicitud ags) {
        this.ags = ags;
    }

    public int getRepeticion() {
        return repeticion;
    }

    public void setRepeticion(int repeticion) {
        this.repeticion = repeticion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
}
