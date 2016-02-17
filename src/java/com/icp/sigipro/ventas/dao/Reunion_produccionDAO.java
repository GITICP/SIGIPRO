/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Reunion_produccion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class Reunion_produccionDAO extends DAO {

    public Reunion_produccionDAO() {
    }

    public List<Reunion_produccion> obtenerReuniones_produccion() throws SIGIPROException {

        List<Reunion_produccion> resultado = new ArrayList<Reunion_produccion>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.reunion_produccion; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Reunion_produccion reunion = new Reunion_produccion();
                
                reunion.setId_reunion(rs.getInt("id_reunion"));
                reunion.setFecha(rs.getDate("fecha"));
                reunion.setObservaciones(rs.getString("observaciones"));
                reunion.setMinuta(rs.getString("minuta"));
                resultado.add(reunion);

            }
            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }

    public Reunion_produccion obtenerReunion_produccion(int id_reunion) throws SIGIPROException {

        Reunion_produccion resultado = new Reunion_produccion();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.reunion_produccion where id_reunion = ?; ");
            consulta.setInt(1, id_reunion);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_reunion(rs.getInt("id_reunion"));
                resultado.setFecha(rs.getDate("fecha"));
                resultado.setObservaciones(rs.getString("observaciones"));
                resultado.setMinuta(rs.getString("minuta"));
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }
    
    public int insertarReunion_produccion(Reunion_produccion p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.reunion_produccion (fecha, observaciones, minuta)"
                    + " VALUES (?,?,?) RETURNING id_reunion");

            consulta.setDate(1, p.getFecha());
            consulta.setString(2, p.getObservaciones());
            consulta.setString(3, p.getMinuta());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_reunion");
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");
        }
        return resultado;
    }

    public boolean editarReunion_produccion(Reunion_produccion p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.reunion_produccion"
                    + " SET observaciones=?, fecha=?, minuta=?"
                    + " WHERE id_reunion=?; "
            );

            consulta.setString(1, p.getObservaciones());
            consulta.setDate(2, p.getFecha());
            consulta.setString(3, p.getMinuta());
            consulta.setInt(4, p.getId_reunion());
            
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la edición");
        }
        return resultado;
    }

    public boolean eliminarReunion_produccion(int id_reunion) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.reunion_produccion "
                    + " WHERE id_reunion=?; "
            );

            consulta.setInt(1, id_reunion);

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la eliminación");
        }
        return resultado;
    }

}
