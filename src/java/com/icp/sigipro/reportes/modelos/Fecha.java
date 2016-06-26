/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.modelos;

import java.sql.Date;
import java.sql.PreparedStatement;

/**
 *
 * @author Boga
 */
public class Fecha extends Parametro {
    
    Date valor;
    
    public Fecha(){}
    
    public Fecha(Date valor) {
        this.tipo = "fecha";
        this.valor = valor;
    }

    @Override
    public void agregarAConsulta(PreparedStatement consulta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValor(String valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
