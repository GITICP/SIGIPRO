/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.modelos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Boga
 */
public abstract class Parametro {
    
    protected int numero;
    protected String tipo;
    protected String nombre;
    protected List<Integer> repeticiones = new ArrayList<>();

    public abstract void agregarAConsulta(PreparedStatement consulta) throws SQLException;
    
    public void agregarAInsert(PreparedStatement consulta) throws SQLException {
        consulta.setInt(2, this.numero);
        consulta.setString(3, this.tipo);
        consulta.setNull(4, java.sql.Types.VARCHAR);
        consulta.setString(5, this.nombre);
        consulta.setString(6, this.repeticionesAString());
    };
    
    public abstract void setValor(String valor);
    public abstract void setValorRequest(HttpServletRequest request);

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
        if (!repeticiones.isEmpty()) {
            for (int r : repeticiones) {
                r--;
            }
        }
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
    
    public void agregarRepeticion(int repeticion) {
        repeticiones.add(repeticion);
    }
    
    public void actualizarLista(int cantidad) {
        if(!repeticiones.isEmpty()) {
            List<Integer> listaNueva = new ArrayList<>();
            for(int r : repeticiones) {
                listaNueva.add(r - cantidad);
            }
            repeticiones = listaNueva;
        }
    }
    
    public String repeticionesAString() {
        String resultado = "";
        for(int r : repeticiones) {
            resultado += r;
            resultado += ",";
        }
        return resultado.substring(0, resultado.length() - 1);
    }
}