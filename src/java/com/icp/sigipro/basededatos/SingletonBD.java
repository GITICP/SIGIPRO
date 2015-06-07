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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Boga
 */
public class SingletonBD
{

    private static SingletonBD theSingleton = null;

    protected SingletonBD()
    {
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
        Connection conexion = null;

        try {
            Class.forName("org.postgresql.Driver");
            conexion
            = DriverManager.getConnection(
                            "jdbc:postgresql://localhost/sigipro", "postgres", "Solaris2014"
                    );
        }
        catch (ClassNotFoundException ex) {
            System.out.println("Clase no encontrada");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conexion;
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
