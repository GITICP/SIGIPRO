/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.modelos;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class ObjetoMultiple extends Parametro {
    
    String tipo_objeto;
    List<Integer> ids_objetos;
    
    public ObjetoMultiple(){}
    
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
    public void agregarAConsulta(PreparedStatement consulta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValor(String valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setValores(String[] valores) {
        if (ids_objetos == null) {
            ids_objetos = new ArrayList<>();
        }
        for(String s : valores) {
            ids_objetos.add(Integer.parseInt(s));
        }
    }
    
}
