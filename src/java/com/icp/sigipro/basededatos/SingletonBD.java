/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.basededatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.mchange.v2.c3p0.*;
import java.beans.PropertyVetoException;

public class SingletonBD
{

    private static SingletonBD theSingleton = null;
    private ComboPooledDataSource cpds;

    protected SingletonBD()
    {
        try {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass("org.postgresql.Driver");
            cpds.setJdbcUrl("jdbc:postgresql://localhost/sigipro");
            cpds.setUser("postgres");
            cpds.setPassword("Solaris2014");

            cpds.setMinPoolSize(10);
            cpds.setAcquireIncrement(5);
            cpds.setMaxStatements(180);
            cpds.setMaxPoolSize(50);
        } catch (PropertyVetoException pvx) {
            pvx.printStackTrace();
        }
    }

    public static SingletonBD getSingletonBD()
    {
        if (theSingleton == null) {
            theSingleton = new SingletonBD();
        }
        return theSingleton;
    }

    public Connection conectar() throws SQLException
    {        
        return cpds.getConnection();
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
