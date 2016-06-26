package com.icp.sigipro.reportes.modelos;

import java.sql.PreparedStatement;

/**
 *
 * @author Boga
 */
public class Numero extends Parametro {
    
    int valor;
    
    public Numero(int valor) {
        this.tipo = "numero";
        this.valor = valor;
    }
    
    public Numero(){}

    @Override
    public void agregarAConsulta(PreparedStatement consulta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValor(String valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
