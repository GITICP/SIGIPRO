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
        String repeticionesStr = this.repeticionesAString();
        consulta.setString(6, repeticionesStr);
    }

    ;
    
    public abstract void setValor(String valor);

    public void setValorRequest(HttpServletRequest request) {
        String valor_request = request.getParameter("valor_param_" + this.numero);
        this.setValor(valor_request);
    }

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
        if (!repeticiones.isEmpty()) {
            List<Integer> listaNueva = new ArrayList<>();
            for (int r : repeticiones) {
                listaNueva.add(r - cantidad);
            }
            repeticiones = listaNueva;
        }
    }

    public String repeticionesAString() {

        if (repeticiones.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int r : repeticiones) {
            sb.append(r);
            sb.append(",");
        }
        return sb.substring(0, sb.length() - 1);

    }

    public void setRepeticiones(String repeticiones) {
        if (repeticiones != null) {
            if (!repeticiones.isEmpty()) {
                String[] valores_array = repeticiones.split(",");
                for (String s : valores_array) {
                    agregarRepeticion(Integer.parseInt(s));
                }
            }
        }
    }
}
