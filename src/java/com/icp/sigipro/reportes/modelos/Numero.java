package com.icp.sigipro.reportes.modelos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

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
        for(int r : repeticiones) {
            consulta.setInt(r, valor);
        }
    }

    @Override
    public void setValor(String valor) {
        this.valor = Integer.parseInt(valor);
    }

    @Override
    public void setValorRequest(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
