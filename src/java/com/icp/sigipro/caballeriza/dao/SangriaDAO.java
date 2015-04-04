/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.caballeriza.modelos.Sangria;
import com.icp.sigipro.core.SIGIPROException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walter
 */
public class SangriaDAO
{

    private Connection conexion;

    public SangriaDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }

    public List<Sangria> obtenerSangrias() throws SIGIPROException
    {
        List<Sangria> resultado = new ArrayList<Sangria>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.sangrias");
            ResultSet rs = consulta.executeQuery();
            SangriaPruebaDAO dao = new SangriaPruebaDAO();
            while (rs.next()) {
                Sangria sangria = new Sangria();
                sangria.setId_sangria(rs.getInt("id_sangria"));
                sangria.setSangria_prueba(dao.obtenerSangriaPrueba(rs.getInt("id_sangria_prueba")));
                sangria.setFecha_dia1(rs.getDate("fecha_dia1"));
                sangria.setFecha_dia2(rs.getDate("fecha_dia2"));
                sangria.setFecha_dia3(rs.getDate("fecha_dia3"));
                sangria.setHematrocito_promedio(rs.getBigDecimal("hematrocito_promedio"));
                sangria.setNum_inf_cc(rs.getInt("num_inf_cc"));
                sangria.setResponsable(rs.getString("responsable"));
                sangria.setCantidad_de_caballos(rs.getInt("cantidad_de_caballos"));
                sangria.setSangre_total(rs.getBigDecimal("sangre_total"));
                sangria.setPeso_plasma_total(rs.getBigDecimal("peso_plasma_total"));
                sangria.setVolumen_plasma_total(rs.getBigDecimal("volumen_plasma_total"));
                sangria.setPlasma_por_caballo(rs.getBigDecimal("plasma_por_caballo"));
                sangria.setPotencia(rs.getBigDecimal("potencia"));
                resultado.add(sangria);
            }
            consulta.close();
            conexion.close();
            rs.close();
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Las Sangrias no pueden ser accedidas.");
        }
        return resultado;
    }    
    private Connection getConexion()
    {
        try {
            if (conexion.isClosed()) {
                SingletonBD s = SingletonBD.getSingletonBD();
                conexion = s.conectar();
            }
        }
        catch (Exception ex) {
            conexion = null;
        }
        return conexion;
    }

    private void cerrarConexion()
    {
        if (conexion != null) {
            try {
                if (conexion.isClosed()) {
                    conexion.close();
                }
            }
            catch (Exception ex) {
                conexion = null;
            }
        }
    }





}
