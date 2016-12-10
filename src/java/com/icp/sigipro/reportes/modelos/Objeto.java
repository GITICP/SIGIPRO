/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.modelos;

import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Boga
 */
public abstract class Objeto extends Parametro {
    
    String tipo_objeto;    

    public String getTipo_objeto() {
        return tipo_objeto;
    }

    public void setTipo_objeto(String tipo_objeto) {
        this.tipo_objeto = tipo_objeto;
    }
    
}
