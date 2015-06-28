/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.Equipo;
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
public class EquipoDAO extends DAO{
    
    public boolean insertarEquipo(Equipo equipo)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.equipos (nombre,id_tipo_equipo,descripcion) "
                                                                        + " VALUES (?,?,?) RETURNING id_equipo");
            consulta.setString(1, equipo.getNombre());
            consulta.setInt(2, equipo.getTipo_equipo().getId_tipo_equipo());
            consulta.setString(3,equipo.getDescripcion());
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                equipo.setId_equipo(rs.getInt("id_equipo"));
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public boolean editarEquipo(Equipo equipo)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" UPDATE control_calidad.equipos "
                    + "SET nombre=?, id_tipo_equipo=?, descripcion=? "
                    + "WHERE id_equipo = ?; ");
            consulta.setString(1, equipo.getNombre());
            consulta.setInt(2, equipo.getTipo_equipo().getId_tipo_equipo());
            consulta.setString(3, equipo.getDescripcion());
            consulta.setInt(4, equipo.getId_equipo());
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
    
    public List<Equipo> obtenerEquipos()
    {
        List<Equipo> resultado = new ArrayList<Equipo>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT equipo.id_equipo, equipo.nombre, tipo.nombre as nombre_tipo "
                    + "FROM control_calidad.equipos as equipo INNER JOIN control_calidad.tipos_equipos as tipo ON equipo.id_tipo_equipo = tipo.id_tipo_equipo; ");
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                Equipo equipo = new Equipo();
                TipoEquipo tipo = new TipoEquipo();
                tipo.setNombre(rs.getString("nombre_tipo"));
                equipo.setTipo_equipo(tipo);
                equipo.setId_equipo(rs.getInt("id_equipo"));
                equipo.setNombre(rs.getString("nombre"));
                resultado.add(equipo);
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public Equipo obtenerEquipo(int id_equipo)
    {
        Equipo resultado = new Equipo();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT equipo.id_equipo, equipo.nombre, equipo.descripcion, tipo.nombre as nombre_tipo "
                    + "FROM control_calidad.equipos as equipo INNER JOIN control_calidad.tipos_equipos as tipo ON equipo.id_tipo_equipo = tipo.id_tipo_equipo "
                    + "WHERE equipo.id_equipo = ?; ");
            consulta.setInt(1, id_equipo);
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                TipoEquipo tipo = new TipoEquipo();
                tipo.setNombre(rs.getString("nombre_tipo"));
                resultado.setTipo_equipo(tipo);
                resultado.setDescripcion(rs.getString("descripcion"));
                resultado.setId_equipo(rs.getInt("id_equipo"));
                resultado.setNombre(rs.getString("nombre"));
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public boolean eliminarEquipo(int id_equipo)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.equipos WHERE id_equipo=?; ");
            consulta.setInt(1, id_equipo);
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
