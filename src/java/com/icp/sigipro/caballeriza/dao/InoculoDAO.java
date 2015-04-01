/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.Inoculo;
import com.icp.sigipro.core.SIGIPROException;
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
public class InoculoDAO {

    private Connection conexion;

    public InoculoDAO() {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }

    public boolean insertarInoculo(Inoculo i) throws SIGIPROException {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO caballeriza.inoculos (mnn, baa, bap, cdd, lms, tetox, otro, encargado_preparacion, encargado_inyeccion, fecha) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?) RETURNING id_inoculo");
            consulta.setString(1,i.getMnn());
            consulta.setString(2,i.getBaa());
            consulta.setString(3,i.getBap());
            consulta.setString(4,i.getCdd());
            consulta.setString(5,i.getLms());
            consulta.setString(6,i.getTetox());
            consulta.setString(7,i.getOtro());
            consulta.setString(8,i.getEncargado_preparacion());
            consulta.setString(9,i.getEncargado_inyeccion());
            consulta.setDate(10, i.getFecha());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
                i.setId_inoculo(resultadoConsulta.getInt("id_inoculo"));
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
        } catch (SQLException ex) {
            throw new SIGIPROException("No se pudo registrar el Inóculo.");
        }
        return resultado;
    }

    public boolean editarInoculo(Inoculo i) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE caballeriza.inoculos "
                    + " SET mnn=?, baa=?, bap=?, cdd=?, lms=?, tetox=?, otro=?, encargado_preparacion=?, encargado_inyeccion=?, fecha=? "
                    + " WHERE id_inoculo=?; "
            );
            consulta.setString(1,i.getMnn());
            consulta.setString(2,i.getBaa());
            consulta.setString(3,i.getBap());
            consulta.setString(4,i.getCdd());
            consulta.setString(5,i.getLms());
            consulta.setString(6,i.getTetox());
            consulta.setString(7,i.getOtro());
            consulta.setString(8,i.getEncargado_preparacion());
            consulta.setString(9,i.getEncargado_inyeccion());
            consulta.setDate(10, i.getFecha());
            consulta.setInt(11,i.getId_inoculo());
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            conexion.close();
        } catch (SQLException ex) {
            throw new SIGIPROException("El Inóculo no puede ser editado.");
        }
        return resultado;

    }

    public Inoculo obtenerInoculo(int id_inoculo) throws SIGIPROException {
        Inoculo inoculo = new Inoculo();
        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM caballeriza.inoculos"
                    + " where id_inoculo = ?");
            consulta.setInt(1, id_inoculo);
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                inoculo.setId_inoculo(rs.getInt("id_inoculo"));
                inoculo.setMnn(rs.getString("mnn"));
                inoculo.setBaa(rs.getString("baa"));
                inoculo.setBap(rs.getString("bap"));
                inoculo.setCdd(rs.getString("cdd"));
                inoculo.setLms(rs.getString("lms"));
                inoculo.setTetox(rs.getString("tetox"));
                inoculo.setOtro(rs.getString("otro"));
                inoculo.setEncargado_preparacion(rs.getString("encargado_preparacion"));
                inoculo.setEncargado_inyeccion(rs.getString("encargado_inyeccion"));
                inoculo.setFecha(rs.getDate("fecha"));
            }
            consulta.close();
            conexion.close();
            rs.close();
        } catch (SQLException ex) {
            throw new SIGIPROException("El Inóculo no puede ser obtenido.");
        }
        return inoculo;
    }

    public List<Inoculo> obtenerInoculos() throws SIGIPROException {
        List<Inoculo> resultado = new ArrayList<Inoculo>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.inoculos");
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                Inoculo inoculo = new Inoculo();
                inoculo.setId_inoculo(rs.getInt("id_inoculo"));
                inoculo.setMnn(rs.getString("mnn"));
                inoculo.setBaa(rs.getString("baa"));
                inoculo.setBap(rs.getString("bap"));
                inoculo.setCdd(rs.getString("cdd"));
                inoculo.setLms(rs.getString("lms"));
                inoculo.setTetox(rs.getString("tetox"));
                inoculo.setOtro(rs.getString("otro"));
                inoculo.setEncargado_preparacion(rs.getString("encargado_preparacion"));
                inoculo.setEncargado_inyeccion(rs.getString("encargado_inyeccion"));
                inoculo.setFecha(rs.getDate("fecha"));
                resultado.add(inoculo);
            }
            consulta.close();
            conexion.close();
            rs.close();
        } catch (SQLException ex) {
            throw new SIGIPROException("Los Inóculos no pueden ser accedidos.");
        }
        return resultado;
    }


    public List<Caballo> obtenerCaballosInoculo(int id_inoculo) throws SIGIPROException {
        List<Caballo> resultado = new ArrayList<Caballo>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" select nombre, numero_microchip from caballeriza.caballos c left outer join caballeriza.inoculos_caballos ecc on c.id_caballo = ecc.id_caballo where id_inoculo=?; ");
            consulta.setInt(1, id_inoculo);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Caballo caballo = new Caballo();
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getInt("numero_microchip"));
                resultado.add(caballo);
            }          
            rs.close();
            consulta.close();
            cerrarConexion();
            
        } catch (SQLException ex) {
            throw new SIGIPROException("Error de comunicación con la base de datos.");
        }
        return resultado;        
    }    
    private Connection getConexion() {
        try {
            if (conexion.isClosed()) {
                SingletonBD s = SingletonBD.getSingletonBD();
                conexion = s.conectar();
            }
        } catch (Exception ex) {
            conexion = null;
        }
        return conexion;
    }

    private void cerrarConexion() {
        if (conexion != null) {
            try {
                if (conexion.isClosed()) {
                    conexion.close();
                }
            } catch (Exception ex) {
                conexion = null;
            }
        }
    }

}
