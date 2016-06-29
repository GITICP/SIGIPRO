/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.modelos;

import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.reportes.dao.ReporteDAO;
import com.icp.sigipro.utilidades.HelperVarios;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class ObjetoMultiple extends Parametro {

    String tipo_objeto;
    List<Integer> ids_objetos;

    public ObjetoMultiple() {
        this.tipo = "objeto_multiple";
    }

    public ObjetoMultiple(String tipo_objeto, List<Integer> ids_objetos) {
        this.tipo = "objeto_multiple";
        this.tipo_objeto = tipo_objeto;
        this.ids_objetos = ids_objetos;
    }

    public String getTipo_objeto() {
        return tipo_objeto;
    }

    public void setTipo_objeto(String tipo_objeto) {
        this.tipo_objeto = tipo_objeto;
    }

    public List<Integer> getIds_objetos() {
        return ids_objetos;
    }

    public void setIds_objetos(List<Integer> ids_objetos) {
        this.ids_objetos = ids_objetos;
    }

    @Override
    public void agregarAConsulta(PreparedStatement consulta) throws SQLException {

    }

    @Override
    public void agregarAInsert(PreparedStatement consulta) throws SQLException {
        consulta.setInt(2, numero);
        consulta.setString(3, this.tipo);
        consulta.setString(4, this.tipo_objeto);
        consulta.setString(5, this.nombre);
    }

    ;

    @Override
    public void setValor(String valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setValores(String[] valores) {
        if (ids_objetos == null) {
            ids_objetos = new ArrayList<>();
        }
        for (String s : valores) {
            ids_objetos.add(Integer.parseInt(s));
        }
    }

    public String getIdsString() {
        HelperVarios hv = HelperVarios.getSingletonHelperVarios();
        return hv.ids_string(ids_objetos);
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
