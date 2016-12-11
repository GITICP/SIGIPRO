/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.modelos;

import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.reportes.dao.ReporteDAO;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Boga
 */
public class ObjetoUnico extends Objeto {
    
    int id_objeto;
    int valor;
    
    public ObjetoUnico() {
        this.tipo = "objeto";
    }
    
    public ObjetoUnico(String tipo_objeto) {
        this.tipo = "objeto";
        this.tipo_objeto = tipo_objeto;
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
    public void agregarAInsert(PreparedStatement consulta) throws SQLException {
        consulta.setInt(2, numero);
        consulta.setString(3, this.tipo);
        consulta.setString(4, this.tipo_objeto);
        consulta.setString(5, this.nombre);
        consulta.setString(6, this.repeticionesAString());
    }
    
    public List<ObjetoAjaxReporte> getListaItems() {
        ReporteDAO dao = new ReporteDAO();
        List<ObjetoAjaxReporte> resultado = new ArrayList<>();
        try {
            resultado = dao.obtenerObjetos(this.tipo_objeto);
        } catch (SIGIPROException sig_ex) {

        }
        return resultado;
    }
    
}
