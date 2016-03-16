/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

/**
 *
 * @author Boga
 */
public class ResultadoSangriaPrueba extends Resultado {

    private int id_resultado_sangria_prueba;
    private String wbc;
    private String rbc;
    private float hematocrito;
    private float hemoglobina;
    private String observaciones;
    
    public ResultadoSangriaPrueba(){}

    public int getId_resultado_sangria_prueba() {
        return id_resultado_sangria_prueba;
    }

    public void setId_resultado_sangria_prueba(int id_resultado_sp) {
        this.id_resultado_sangria_prueba = id_resultado_sp;
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

    public String getObservaciones() {
        String resultado = "Sin observaciones.";
        if(observaciones != null){
            if (!observaciones.isEmpty()){
                resultado = observaciones;
            }
        }
        return resultado;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    @Override
    public String getResultado() {
        return "Hto.: " + hematocrito + " // Hb.: " + hemoglobina;
    }
    
    @Override
    public String getTipo() {
        return "SangriaPrueba";
    }
    
    @Override
    public void setId_resultado(int id_resultado) {
        this.id_resultado_sangria_prueba = id_resultado;
    }
    
    @Override 
    public int getId_resultado(){
        return this.id_resultado_sangria_prueba;
    }
    
}
