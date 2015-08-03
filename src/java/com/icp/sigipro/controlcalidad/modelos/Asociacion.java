/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Boga
 */
public abstract class Asociacion {
    
    protected String objeto;
    protected String tabla;
    protected Informe informe;
    
    protected abstract void asociar(Resultado resultado, HttpServletRequest request);
    protected abstract List<PreparedStatement> asociarSQL(Connection conexion) throws SQLException;

    public Informe getInforme() {
        return informe;
    }

    public void setInforme(Informe informe) {
        this.informe = informe;
    }
    
}
