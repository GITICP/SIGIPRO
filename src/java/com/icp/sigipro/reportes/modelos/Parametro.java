/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.modelos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Boga
 */
public abstract class Parametro {
    
    protected int numero;
    protected String tipo;
    protected String nombre;
    
    public abstract void agregarAConsulta(PreparedStatement consulta) throws SQLException;
    
    public void agregarAInsert(PreparedStatement consulta) throws SQLException {
        consulta.setInt(2, this.numero);
        consulta.setString(3, this.tipo);
        consulta.setNull(4, java.sql.Types.VARCHAR);
        consulta.setString(5, this.nombre);
    };
    
    public abstract void setValor(String valor);
    public abstract void setValorRequest(HttpServletRequest request);

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}