/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.reportes.modelos.ObjetoAjaxReporte;
import com.icp.sigipro.reportes.modelos.Reporte;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class ReporteDAO extends DAO {
    
    public List<Reporte> obtenerReportes() throws SIGIPROException {
        List<Reporte> resultado = new ArrayList<>();
        
        PreparedStatement consulta = null;
        ResultSet rs = null;
        
        try {            
            consulta = getConexion().prepareStatement("SELECT * FROM reportes.reportes");           
            
            rs = consulta.executeQuery();
            
            while(rs.next()) {
                Reporte r = new Reporte();
                
                r.setId_reporte(rs.getInt("id_reporte"));
                r.setNombre(rs.getString("nombre"));
                r.setDescripcion(rs.getString("descripcion"));
                
                resultado.add(r);
            }
            
        } catch(SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener la información de la base de datos.");
        } finally {
            cerrarSilencioso(consulta);
            cerrarSilencioso(rs);
            cerrarConexion();
        }
        
        return resultado;
    }

    public List<ObjetoAjaxReporte> obtenerObjetos(String tabla) throws SIGIPROException {
        List<ObjetoAjaxReporte> resultado = new ArrayList<>();
        
        PreparedStatement consulta = null;
        ResultSet rs = null;
        
        try {
            consulta = getConexion().prepareStatement(
            
                    " SELECT id_producto as ID, nombre || '(' || codigo_icp || ')' AS TEXTO"
                  + " FROM bodega.catalogo_interno "
                    
            );
            
            rs = consulta.executeQuery();
            
            while(rs.next()) {
                ObjetoAjaxReporte r = new ObjetoAjaxReporte();
                r.setId(rs.getInt("ID"));
                r.setTexto(rs.getString("TEXTO"));
                resultado.add(r);
            }
            
            
        } catch(SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener los datos. Notifique al administrador del sistema");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        
        return resultado;
    }
    
}
