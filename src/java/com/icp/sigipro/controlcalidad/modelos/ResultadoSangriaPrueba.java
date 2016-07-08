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
    private float wbc;
    private float rbc;
    private float hematocrito;
    private float hemoglobina;
    private float mcv;
    private float mch;
    private float mchc;
    private float plt;
    private float lym;
    private float otros;
    private float linfocitos;
    private float num_otros;
    
    private String observaciones;
    
    
    public ResultadoSangriaPrueba(){}

    public int getId_resultado_sangria_prueba() {
        return id_resultado_sangria_prueba;
    }

    public void setId_resultado_sangria_prueba(int id_resultado_sp) {
        this.id_resultado_sangria_prueba = id_resultado_sp;
    }

    public float getWbc() {
        return wbc;
    }

    public void setWbc(float wbc) {
        this.wbc = wbc;
    }

    public float getRbc() {
        return rbc;
    }

    public void setRbc(float rbc) {
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

    public float getMcv() {
        return mcv;
    }

    public void setMcv(float mcv) {
        this.mcv = mcv;
    }

    public float getMch() {
        return mch;
    }

    public void setMch(float mch) {
        this.mch = mch;
    }

    public float getMchc() {
        return mchc;
    }

    public void setMchc(float mchc) {
        this.mchc = mchc;
    }

    public float getPlt() {
        return plt;
    }

    public void setPlt(float plt) {
        this.plt = plt;
    }

    public float getLym() {
        return lym;
    }

    public void setLym(float lym) {
        this.lym = lym;
    }

    public float getOtros() {
        return otros;
    }

    public void setOtros(float otros) {
        this.otros = otros;
    }

    public float getLinfocitos() {
        return linfocitos;
    }

    public void setLinfocitos(float linfocitos) {
        this.linfocitos = linfocitos;
    }

    public float getNum_otros() {
        return num_otros;
    }

    public void setNum_otros(float num_otros) {
        this.num_otros = num_otros;
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
