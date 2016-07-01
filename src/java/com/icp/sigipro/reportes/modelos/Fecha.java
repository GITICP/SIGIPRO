/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.modelos;

import com.icp.sigipro.utilidades.HelperFechas;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Boga
 */
public class Fecha extends Parametro {
    
    Date valor;
    HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
    
    public Fecha() {
        this.tipo = "fecha";
    }
    
    public Fecha(Date valor) {
        this.tipo = "fecha";
        this.valor = valor;
    }

    @Override
    public void agregarAConsulta(PreparedStatement consulta) throws SQLException {
        consulta.setDate(numero, valor);
    }

    @Override
    public void setValor(String valor) {
        try{
            this.valor = helper_fechas.formatearFecha(valor);
        } catch (ParseException p) {
            
        }
    }
    
    @Override
    public void setValorRequest(HttpServletRequest request) {
        String valor_request = request.getParameter("valor_param_" + this.numero);
        this.setValor(valor_request);
    }
    
}
