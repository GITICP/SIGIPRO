package com.icp.sigipro.reportes.modelos;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Boga
 */
public class Numero extends Parametro {
    
    int valor;
    
    public Numero() {
        this.tipo = "numero";
    }
    
    public Numero(int valor) {
        this.tipo = "numero";
        this.valor = valor;
    }

    @Override
    public void agregarAConsulta(PreparedStatement consulta) throws SQLException {
        consulta.setInt(numero, valor);
    }

    @Override
    public void setValor(String valor) {
        this.valor = Integer.parseInt(valor);
    }
    
}
