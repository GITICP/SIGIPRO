/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.modelos;

import java.sql.PreparedStatement;

/**
 *
 * @author Boga
 */
public abstract class Parametro {
    
    int numero;
    String tipo;
    
    public abstract void agregarAConsulta(PreparedStatement consulta);
    public abstract void setValor(String valor);

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
            
}