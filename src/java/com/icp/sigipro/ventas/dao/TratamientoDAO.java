/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Tratamiento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class TratamientoDAO extends DAO {

    public TratamientoDAO() {
    }
    
    private ClienteDAO cdao = new ClienteDAO();

    public List<Tratamiento> obtenerTratamientos() throws SIGIPROException {

        List<Tratamiento> resultado = new ArrayList<Tratamiento>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.tratamiento; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                
                tratamiento.setId_tratamiento(rs.getInt("id_tratamiento"));
                tratamiento.setCliente(cdao.obtenerCliente(rs.getInt("id_cliente")));
                tratamiento.setFecha(rs.getDate("fecha"));
                tratamiento.setObservaciones(rs.getString("observaciones"));
                tratamiento.setEstado(rs.getString("estado"));
                resultado.add(tratamiento);

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

    public Tratamiento obtenerTratamiento(int id_tratamiento) throws SIGIPROException {

        Tratamiento resultado = new Tratamiento();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.tratamiento where id_tratamiento = ?; ");
            consulta.setInt(1, id_tratamiento);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_tratamiento(rs.getInt("id_tratamiento"));
                resultado.setCliente(cdao.obtenerCliente(rs.getInt("id_cliente")));
                resultado.setFecha(rs.getDate("fecha"));
                resultado.setObservaciones(rs.getString("observaciones"));
                resultado.setEstado(rs.getString("estado"));
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
    
    public int insertarTratamiento(Tratamiento p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.tratamiento (id_cliente, fecha, observaciones, estado)"
                    + " VALUES (?,?,?,?) RETURNING id_tratamiento");

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha());
            consulta.setString(3, p.getObservaciones());
            consulta.setString(4, p.getEstado());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_tratamiento");
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

    public boolean editarTratamiento(Tratamiento p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.tratamiento"
                    + " SET id_cliente=?, fecha=?, observaciones=?, estado=?"
                    + " WHERE id_tratamiento=?; "
            );

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha());
            consulta.setString(3, p.getObservaciones());
            consulta.setString(4, p.getEstado());
            consulta.setInt(5, p.getId_tratamiento());
            
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

    public boolean eliminarTratamiento(int id_tratamiento) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.tratamiento "
                    + " WHERE id_tratamiento=?; "
            );

            consulta.setInt(1, id_tratamiento);

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
