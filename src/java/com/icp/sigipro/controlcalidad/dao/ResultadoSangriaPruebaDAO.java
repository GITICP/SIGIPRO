/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.ResultadoSangriaPrueba;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Boga
 */
public class ResultadoSangriaPruebaDAO extends DAO {
    
    public ResultadoSangriaPrueba insertarResultadoSangriaPrueba(ResultadoSangriaPrueba resultado_sp) throws SIGIPROException {
        
        PreparedStatement consulta = null;
        ResultSet rs = null;
        
        try {
            consulta = getConexion().prepareStatement(
                    " INSERT INTO control_calidad.resultados_analisis_sangrias_prueba "
                  + " (id_ags,wbc,rbc,hematocrito,hemoglobina,repeticion,fecha) VALUES (?,?,?,?,?,?,?) RETURNING id_resultado_analisis_sp;" 
            );
            
            consulta.setInt(1, resultado_sp.getAgs().getId_analisis_grupo_solicitud());
            consulta.setString(2, resultado_sp.getWbc());
            consulta.setString(3, resultado_sp.getRbc());
            consulta.setFloat(4, resultado_sp.getHematocrito());
            consulta.setFloat(5, resultado_sp.getHemoglobina());
            consulta.setInt(6, 0);
            consulta.setDate(7, resultado_sp.getFecha());
            
            rs = consulta.executeQuery();
            
            if (rs.next()) {
                resultado_sp.setId_resultado_sangria_prueba(rs.getInt("id_resultado_analisis_sp"));
            } else {
                throw new SIGIPROException("Error de comunicación con la base de datos. Notifique al administrador del sistema.");
            }
            
        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        
        return resultado_sp;
        
    }
    
}
