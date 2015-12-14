/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.produccion.modelos.Catalogo_PT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    //Cada protocolo muestra la info del mismo, una tabla de versiones y una tabla de pasos del protocolo
    public Catalogo_PT obtenerCatalogo_PT(int id_protocolo) {
        Catalogo_PT resultado = new Catalogo_PT();
        return resultado;
    }
    public List<Catalogo_PT> obtenerCatalogos_PT()
    {
        List<Catalogo_PT> resultado = new ArrayList<Catalogo_PT>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT id_catalogo_pt, nombre, descripcion FROM produccion.catalogo_pt;");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Catalogo_PT catalogo_pt = new Catalogo_PT();
                catalogo_pt.setId_catalogo_pt(rs.getInt("id_catalogo_pt"));
                catalogo_pt.setNombre(rs.getString("nombre"));
                catalogo_pt.setDescripcion(rs.getString("descripcion"));
                resultado.add(catalogo_pt);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
}
