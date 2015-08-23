/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos.asociaciones;

import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Boga
 */
public abstract class Asociacion {
    
    protected String tipo;
    protected String tabla;
    protected Asociable objeto_asociable;
    
    public abstract void asociar(HttpServletRequest request);
    public abstract void asociar(ResultSet rs) throws SQLException;
    public abstract void asociar(Resultado resultado, HttpServletRequest request);
    public abstract void prepararEditarSolicitud(HttpServletRequest request) throws SIGIPROException;
    public abstract void prepararGenerarInforme(HttpServletRequest request) throws SIGIPROException;
    public abstract void prepararEditarInforme(HttpServletRequest request) throws SIGIPROException;
    public abstract List<PreparedStatement> insertarSQL(Connection conexion) throws SQLException;
    public abstract List<PreparedStatement> editarSQL(Connection conexion) throws SQLException;

    public Asociable getInforme() {
        return objeto_asociable;
    }

    public void setAsociable(Asociable asociable) {
        this.objeto_asociable = asociable;
    }
    
}
