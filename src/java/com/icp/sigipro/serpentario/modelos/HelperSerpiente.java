/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.modelos;

/**
 *
 * @author ld.conejo
 */
public class HelperSerpiente {
    private String campo_cambiado;
    private String valor_cambiado;

    public HelperSerpiente(String campo_cambiado, String valor_cambiado) {
        this.campo_cambiado = campo_cambiado;
        this.valor_cambiado = valor_cambiado;
    }

    public String getCampo_cambiado() {
        return campo_cambiado;
    }

    public void setCampo_cambiado(String campo_cambiado) {
        this.campo_cambiado = campo_cambiado;
    }

    public String getValor_cambiado() {
        return valor_cambiado;
    }

    public void setValor_cambiado(String valor_cambiado) {
        this.valor_cambiado = valor_cambiado;
    }
    
    
}
