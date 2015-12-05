/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.produccion.modelos.Protocolo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class ProtocoloDAO extends DAO {

    public boolean insertarProtocolo(Protocolo protocolo) {
        boolean resultado = false;
        
        return resultado;
    }

    public boolean editarProtocolo(Protocolo protocolo) {
        boolean resultado = false;
        
        return resultado;
    }

    public List<Protocolo> obtenerProtocolos() {
        List<Protocolo> resultado = new ArrayList<Protocolo>();
        return resultado;
    }

    //Cada protocolo muestra la info del mismo, una tabla de versiones y una tabla de pasos del protocolo
    public Protocolo obtenerProtocolo(int id_protocolo) {
        Protocolo resultado = new Protocolo();
        return resultado;
    }

}