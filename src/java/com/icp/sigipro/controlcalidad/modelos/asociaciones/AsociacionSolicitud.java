/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos.asociaciones;

import com.icp.sigipro.controlcalidad.dao.SolicitudDAO;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
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
public abstract class AsociacionSolicitud {
    
    protected String tipo;
    protected String tabla;
    protected SolicitudCC solicitud;
    private final SolicitudDAO solicitud_dao = new SolicitudDAO();
    
    public abstract void asociar(HttpServletRequest request);
    public abstract void asociar(ResultSet rs) throws SQLException;
    public abstract void asociarResultado(Resultado resultado, HttpServletRequest request);
    public abstract void prepararEditarSolicitud(HttpServletRequest request) throws SIGIPROException;
    public abstract void prepararGenerarInforme(HttpServletRequest request) throws SIGIPROException;
    public abstract void prepararEditarInforme(HttpServletRequest request) throws SIGIPROException;
    public abstract List<PreparedStatement> insertarSQLSolicitud(Connection conexion) throws SQLException;
    public abstract List<PreparedStatement> insertarSQLInforme(Connection conexion) throws SQLException;
    public abstract List<PreparedStatement> editarSQLInforme(Connection conexion) throws SQLException;
    public abstract List<PreparedStatement> resetear(Connection conexion) throws SQLException;

    public SolicitudCC getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudCC solicitud) {
        this.solicitud = solicitud;
    }
    
    protected List<PreparedStatement> resetearSolicitudAnterior(Connection conexion) throws SQLException {
        
        SolicitudCC solicitud_anterior = solicitud_dao.obtenerAsociacion(solicitud);
        return solicitud_anterior.resetear(conexion);
        
    }
    
}
