/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Observacion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class ObservacionDAO extends DAO {

    public ObservacionDAO() {
    }
    

    public List<Observacion> obtenerObservaciones() throws SIGIPROException {

        List<Observacion> resultado = new ArrayList<Observacion>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.observacion; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Observacion observacion = new Observacion();
                
                observacion.setId_observacion(rs.getInt("id_observacion"));
                observacion.setObservacion(rs.getString("observacion"));
                resultado.add(observacion);

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

    public Observacion obtenerObservacion(int id_observacion) throws SIGIPROException {

        Observacion resultado = new Observacion();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.observacion where id_observacion = ?; ");
            consulta.setInt(1, id_observacion);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_observacion(rs.getInt("id_observacion"));
                resultado.setObservacion(rs.getString("observacion"));
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
    
    public int insertarObservacion(Observacion p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.observacion (observacion)"
                    + " VALUES (?) RETURNING id_observacion");

            consulta.setString(1, p.getObservacion());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_observacion");
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

    public boolean editarObservacion(Observacion p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.observacion"
                    + " SET observacion=?"
                    + " WHERE id_observacion=?; "
            );

            consulta.setString(1, p.getObservacion());
            consulta.setInt(2, p.getId_observacion());
            
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

    public boolean eliminarObservacion(int id_observacion) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.observacion "
                    + " WHERE id_observacion=?; "
            );

            consulta.setInt(1, id_observacion);

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
