/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Accion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class AccionDAO extends DAO {

    public AccionDAO() {
    }
    

    public List<Accion> obtenerAcciones() throws SIGIPROException {

        List<Accion> resultado = new ArrayList<Accion>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.accion; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Accion accion = new Accion();
                
                accion.setId_accion(rs.getInt("id_accion"));
                accion.setAccion(rs.getString("accion"));
                resultado.add(accion);

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

    public Accion obtenerAccion(int id_accion) throws SIGIPROException {

        Accion resultado = new Accion();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.accion where id_accion = ?; ");
            consulta.setInt(1, id_accion);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_accion(rs.getInt("id_accion"));
                resultado.setAccion(rs.getString("accion"));
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
    
    public int insertarAccion(Accion p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.accion (accion)"
                    + " VALUES (?) RETURNING id_accion");

            consulta.setString(1, p.getAccion());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_accion");
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

    public boolean editarAccion(Accion p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.accion"
                    + " SET accion=?"
                    + " WHERE id_accion=?; "
            );

            consulta.setString(1, p.getAccion());
            consulta.setInt(2, p.getId_accion());
            
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

    public boolean eliminarAccion(int id_accion) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.accion "
                    + " WHERE id_accion=?; "
            );

            consulta.setInt(1, id_accion);

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
