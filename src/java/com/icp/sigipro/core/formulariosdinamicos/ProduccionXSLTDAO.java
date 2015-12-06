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
 * @author ld.conejo
 */
public class ProduccionXSLTDAO extends DAO {
    
    
    public ProduccionXSLTDAO(){}
    
    public ProduccionXSLT obtenerProduccionXSLTFormulario() throws SIGIPROException {
        return obtenerProduccionXSLT(1);
    }
    
    public ProduccionXSLT obtenerProduccionXSLTVerFormulario() throws SIGIPROException {
        return obtenerProduccionXSLT(4);
    }
    
    public ProduccionXSLT obtenerProduccionXSLT(int id_produccion) throws SIGIPROException{
        
        ProduccionXSLT resultado = new ProduccionXSLT();
        
        PreparedStatement consulta = null;
        ResultSet rs = null;
        
        try {
            consulta = getConexion().prepareStatement(
                    " SELECT * FROM produccion_xslt.produccion_xslt where id_produccion_xslt = ?; "
            );
            
            consulta.setInt(1, id_produccion);
            
            rs = consulta.executeQuery();
            
            if(rs.next()){
                resultado.setId(rs.getInt("id_produccion_xslt"));
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
