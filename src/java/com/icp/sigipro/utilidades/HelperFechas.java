/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.utilidades;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Boga
 */
public class HelperFechas
{

    private static HelperFechas theSingleton = null;

    private HelperFechas()
    {
    }

    public static HelperFechas getSingletonHelperFechas()
    {
        if (theSingleton == null) {
            theSingleton = new HelperFechas();
        }
        return theSingleton;
    }

    public String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }

    public Date formatearFecha(String fecha) throws ParseException
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        java.util.Date fecha_date = df.parse(fecha);
        Date fecha_sql = new Date(fecha_date.getTime());
        
        return fecha_sql;
    }
    
    public Date getFecha_hoy() {
        java.util.Date hoy = new java.util.Date();
        Date hoysql = new Date(hoy.getTime());
        return hoysql;
    }

}
