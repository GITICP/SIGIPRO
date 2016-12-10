/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.modelos;

import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Boga
 */
public class Objeto extends Parametro {
    
    String tipo_objeto;
    int id_objeto;
    
    public Objeto() {
        this.tipo = "objeto";
    }
    
    public Objeto(String tipo_objeto, int id_objeto) {
        this.tipo = "objeto";
        this.tipo_objeto = tipo_objeto;
        this.id_objeto = id_objeto;
    }

    @Override
    public void agregarAConsulta(PreparedStatement consulta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValor(String valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValorRequest(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
