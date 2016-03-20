/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos.asociaciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Boga
 */
public abstract class AsociacionInforme {
    
    public abstract List<PreparedStatement> insertarSQL(Connection conexion) throws SQLException;
    public abstract List<PreparedStatement> editarSQL(Connection conexion) throws SQLException;
    
}
