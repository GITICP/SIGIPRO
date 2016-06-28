/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.modelos;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Boga
 */
public abstract class Parametro {
    
    protected int numero;
    protected String tipo;
    
    public abstract void agregarAConsulta(PreparedStatement consulta) throws SQLException;
    
    public void agregarAInsert(PreparedStatement consulta) throws SQLException {
        consulta.setInt(2, numero);
        consulta.setString(3, this.tipo);
        consulta.setNull(4, java.sql.Types.VARCHAR);
    };
    
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