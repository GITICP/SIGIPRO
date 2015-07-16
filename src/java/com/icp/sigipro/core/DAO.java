/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.utilidades.HelperFechas;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Boga
 */
public abstract class DAO
{
    private Connection conexion;
    private final SingletonBD singleton_bd = SingletonBD.getSingletonBD();
    protected HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();

    protected Connection getConexion()
    {
        try {
            if (conexion == null) {
                conexion = singleton_bd.conectar();
            } else {
                if (conexion.isClosed()) {
                    conexion = singleton_bd.conectar();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            conexion = null;
        }
        return conexion;
    }

    protected void cerrarConexion()
    {
        cerrarSilencioso(conexion);
    }

    protected void cerrarSilencioso(Connection c)
    {
        SingletonBD.cerrarSilencioso(c);
    }

    protected void cerrarSilencioso(ResultSet rs)
    {
        SingletonBD.cerrarSilencioso(rs);
    }

    protected void cerrarSilencioso(Statement s)
    {
        SingletonBD.cerrarSilencioso(s);
    }
    
    protected String pasar_ids_a_parentesis(String[] ids)
    {
        String resultado = "(";
        for (String s : ids) {
            resultado = resultado + s;
            resultado = resultado + ",";
        }
        resultado = resultado.substring(0, resultado.length() - 1);
        resultado = resultado + ")";
        return resultado;
    }
    
    protected String pasar_ids_a_parentesis(int[] ids)
    {
        String[] ids_strings = new String[ids.length];
        
        for (int i = 0; i < ids.length; i++) {
            ids_strings[i] =  String.valueOf(ids[i]);
        }
        
        return this.pasar_ids_a_parentesis(ids_strings);
    }
}
