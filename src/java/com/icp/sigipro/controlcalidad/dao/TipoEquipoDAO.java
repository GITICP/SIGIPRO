/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.TipoEquipo;
import com.icp.sigipro.core.DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class TipoEquipoDAO extends DAO {
    
    public boolean insertarTipoEquipo(TipoEquipo tipoequipo)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.tipos_equipos (nombre,descripcion) "
                                                                        + " VALUES (?,?) RETURNING id_tipo_equipo");
            consulta.setString(1, tipoequipo.getNombre());
            consulta.setString(2, tipoequipo.getDescripcion());
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                tipoequipo.setId_tipo_equipo(rs.getInt("id_tipo_equipo"));
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public boolean editarTipoEquipo(TipoEquipo tipoequipo)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" UPDATE control_calidad.tipos_equipos "
                    + "SET nombre=?, descripcion=? "
                    + "WHERE id_tipo_equipo = ?; ");
            consulta.setString(1, tipoequipo.getNombre());
            consulta.setString(2, tipoequipo.getDescripcion());
            consulta.setInt(3, tipoequipo.getId_tipo_equipo());
            if (consulta.executeUpdate()==1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public List<TipoEquipo> obtenerTipoEquipos()
    {
        List<TipoEquipo> resultado = new ArrayList<TipoEquipo>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_tipo_equipo, nombre FROM control_calidad.tipos_equipos; ");
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                TipoEquipo tipoequipo = new TipoEquipo();
                tipoequipo.setId_tipo_equipo(rs.getInt("id_tipo_equipo"));
                tipoequipo.setNombre(rs.getString("nombre"));
                resultado.add(tipoequipo);
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public TipoEquipo obtenerTipoEquipo(int id_tipo_equipo)
    {
        TipoEquipo resultado = new TipoEquipo();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM control_calidad.tipos_equipos WHERE id_tipo_equipo=?; ");
            consulta.setInt(1, id_tipo_equipo);
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_tipo_equipo(rs.getInt("id_tipo_equipo"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setDescripcion(rs.getString("descripcion"));
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public boolean eliminarTipoEquipo(int id_tipo_equipo)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.tipos_equipos WHERE id_tipo_equipo=?; ");
            consulta.setInt(1, id_tipo_equipo);
            if (consulta.executeUpdate() == 1) {
                resultado=true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
}
