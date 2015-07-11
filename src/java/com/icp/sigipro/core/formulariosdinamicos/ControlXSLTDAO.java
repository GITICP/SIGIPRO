/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core.formulariosdinamicos;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Boga
 */
public class ControlXSLTDAO extends DAO
{
    
    public ControlXSLTDAO(){}
    
    public ControlXSLT obtenerControlXSLTFormulario() throws SIGIPROException {
        return obtenerControlXSLT(1);
    }
    
    public ControlXSLT obtenerControlXSLTResultado() throws SIGIPROException {
        return obtenerControlXSLT(2);
    }
    
    public ControlXSLT obtenerControlXSLTResultadoReducido() throws SIGIPROException {
        return obtenerControlXSLT(3);
    }
    
    public ControlXSLT obtenerControlXSLTVerFormulario() throws SIGIPROException {
        return obtenerControlXSLT(4);
    }
    
    public ControlXSLT obtenerControlXSLT(int id_control) throws SIGIPROException{
        
        ControlXSLT resultado = new ControlXSLT();
        
        PreparedStatement consulta = null;
        ResultSet rs = null;
        
        try {
            consulta = getConexion().prepareStatement(
                    " SELECT * FROM control_xslt.control_xslt where id_control_xslt = ?; "
            );
            
            consulta.setInt(1, id_control);
            
            rs = consulta.executeQuery();
            
            if(rs.next()){
                resultado.setId(rs.getInt("id_control_xslt"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setEstructura(rs.getSQLXML("estructura"));
            } else {
                throw new SQLException();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Ocurrió un error inesperado. Notifique al administrador del sistema.");
        }
        finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        
        return resultado;
    }
    
}
