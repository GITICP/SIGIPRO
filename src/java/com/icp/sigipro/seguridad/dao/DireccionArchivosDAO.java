/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.modelos.DireccionArchivos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Boga
 */
public class DireccionArchivosDAO extends DAO {

    public DireccionArchivos obtenerDireccion() throws SIGIPROException {

        DireccionArchivos resultado = new DireccionArchivos();

        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {
            consulta = getConexion().prepareStatement(
                    " SELECT * FROM configuracion.direccion_archivos WHERE id_direccion = 1 "
            );

            rs = consulta.executeQuery();

            if (rs.next()) {
                resultado.setId_direccion(rs.getInt("id_direccion"));
                resultado.setDireccion(rs.getString("direccion"));
            } else {
                throw new SIGIPROException("Las configuraciones de las direcciones de los archivos fueron eliminadas. Notifique al administrador del sistema.");
            }

        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
            throw new SIGIPROException("Error de comunicación con la base de datos. Notifique al administrador del sistema.");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;

    }

    public boolean editarDireccion(DireccionArchivos direccion) throws SIGIPROException {

        boolean resultado = false;

        PreparedStatement consulta = null;

        try {
            consulta = getConexion().prepareStatement(
                    " UPDATE configuracion.direccion_archivos SET direccion = ? WHERE id_direccion = 1 "
            );
            
            consulta.setString(1, direccion.getDireccion());
            consulta.setInt(1, direccion.getId_direccion());

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            } else {
                throw new SIGIPROException("Las configuraciones de las direcciones de los archivos fueron eliminadas. Notifique al administrador del sistema.");
            }

        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
            throw new SIGIPROException("Error de comunicación con la base de datos. Notifique al administrador del sistema.");
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;

    }

}
