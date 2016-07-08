/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.basededatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.mchange.v2.c3p0.*;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.sql.Statement;

public class SingletonBD
{

    private static SingletonBD theSingleton = null;
    private ComboPooledDataSource cpds;

    protected SingletonBD()
    {
        try {
            cpds = new ComboPooledDataSource();

            // Configuración de Base de Datos
            cpds.setDriverClass("org.postgresql.Driver");
            cpds.setJdbcUrl("jdbc:postgresql://localhost/sigipro");
            cpds.setUser("postgres");
            cpds.setPassword("Solaris2014");

            // Configuración de máximos y mínimos de conexiones
            cpds.setMinPoolSize(10);
            cpds.setAcquireIncrement(5);
            cpds.setMaxStatements(180);
            cpds.setMaxPoolSize(80);

            // Configuración de tiempos de vida
            cpds.setMaxIdleTime(30);
            cpds.setMaxIdleTimeExcessConnections(60);
            cpds.setCheckoutTimeout(5000);
            
            // Configuración de errores
            cpds.setUnreturnedConnectionTimeout(120);
            cpds.setDebugUnreturnedConnectionStackTraces(true);
        }
        catch (PropertyVetoException pvx) {
            pvx.printStackTrace();
        }
    }

    public void eliminarPool()
    {
        cpds.hardReset();
    }

    public static SingletonBD getSingletonBD()
    {
        if (theSingleton == null) {
            theSingleton = new SingletonBD();
        }
        return theSingleton;
    }

    public Connection conectar()
    {
        try {
            return cpds.getConnection();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void cerrarSilencioso(Statement s)
    {
        if (s != null) {
            try {
                s.close();
            }
            catch (SQLException sql_ex) {
                sql_ex.printStackTrace();
            }
        }
    }

    public static void cerrarSilencioso(ResultSet rs)
    {
        if (rs != null) {
            try {
                rs.close();
            }
            catch (SQLException sql_ex) {
                sql_ex.printStackTrace();
            }
        }
    }

    public static void cerrarSilencioso(Connection c)
    {
        if (c != null) {
            try {
                c.close();
            }
            catch (SQLException sql_ex) {
                sql_ex.printStackTrace();
            }
        }
    }

    private Date parsearFecha(java.util.Date fecha)
    {
        return new Date(fecha.getTime());
    }

    public Date parsearFecha(String fecha, String formato) throws ParseException
    {
        SimpleDateFormat formatoFecha = new SimpleDateFormat(formato);
        return parsearFecha(formatoFecha.parse(fecha));
    }

    public Date parsearFecha(String fecha) throws ParseException
    {
        return parsearFecha(fecha, "dd/MM/yyyy");
    }
}
