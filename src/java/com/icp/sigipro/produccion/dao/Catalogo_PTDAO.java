/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.produccion.modelos.Catalogo_PT;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class Catalogo_PTDAO extends DAO{
  public boolean insertarCatalogo_PT(Catalogo_PT protocolo) {
        boolean resultado = false;
        
        return resultado;
    }

    public boolean editarCatalogo_PT(Catalogo_PT protocolo) {
        boolean resultado = false;
        
        return resultado;
    }

    public List<Catalogo_PT> obtenerCatalogo_PTs() {
        List<Catalogo_PT> resultado = new ArrayList<Catalogo_PT>();
        return resultado;
    }

    //Cada protocolo muestra la info del mismo, una tabla de versiones y una tabla de pasos del protocolo
    public Catalogo_PT obtenerCatalogo_PT(int id_protocolo) {
        Catalogo_PT resultado = new Catalogo_PT();
        return resultado;
    }
}
