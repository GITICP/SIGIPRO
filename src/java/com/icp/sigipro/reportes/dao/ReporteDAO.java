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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Boga
 */
public class ReporteDAO extends DAO {
    
    private final Map<String, String> tablas_consultas = new HashMap<String, String>() {
        {
            put("cat_interno", " SELECT id_producto as VAL, nombre || '(' || codigo_icp || ')' AS TEXTO FROM bodega.catalogo_interno ");
            put("secciones", " SELECT id_seccion as VAL, nombre_seccion AS TEXTO FROM seguridad.secciones");
        }
    };
        
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
                tablas_consultas.get(tabla)
            );
            
            rs = consulta.executeQuery();
            
            while(rs.next()) {
                ObjetoAjaxReporte r = new ObjetoAjaxReporte();
                r.setVal(rs.getInt("VAL"));
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
