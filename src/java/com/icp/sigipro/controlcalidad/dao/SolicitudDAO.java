/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.Solicitud;
import com.icp.sigipro.core.DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class SolicitudDAO extends DAO {

    public boolean insertarSolicitud(Solicitud solicitud) {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.solicitudes (nombre,descripcion) "
                    + " VALUES (?,?) RETURNING id_solicitud");
            consulta.setString(1, solicitud.getNombre());
            consulta.setString(2, solicitud.getDescripcion());
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                solicitud.setId_solicitud(rs.getInt("id_solicitud"));
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean editarSolicitud(Solicitud solicitud) {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" UPDATE control_calidad.solicitudes "
                    + "SET nombre=?, descripcion=? "
                    + "WHERE id_solicitud = ?; ");
            consulta.setString(1, solicitud.getNombre());
            consulta.setString(2, solicitud.getDescripcion());
            consulta.setInt(3, solicitud.getId_solicitud());
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public List<Solicitud> obtenerSolicituds() {
        List<Solicitud> resultado = new ArrayList<Solicitud>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_solicitud, nombre FROM control_calidad.solicitudes; ");
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                Solicitud solicitud = new Solicitud();
                solicitud.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud.setNombre(rs.getString("nombre"));
                resultado.add(solicitud);
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public Solicitud obtenerSolicitud(int id_solicitud) {
        Solicitud resultado = new Solicitud();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM control_calidad.solicitudes WHERE id_solicitud=?; ");
            consulta.setInt(1, id_solicitud);
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_solicitud(rs.getInt("id_solicitud"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setDescripcion(rs.getString("descripcion"));
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarSolicitud(int id_solicitud) {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.solicitudes WHERE id_solicitud=?; ");
            consulta.setInt(1, id_solicitud);
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

}
