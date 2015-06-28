/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.TipoReactivo;
import com.icp.sigipro.core.DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class TipoReactivoDAO extends DAO {
    
    public boolean insertarTipoReactivo(TipoReactivo tiporeactivo)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.tipos_reactivos (nombre,descripcion) "
                                                                        + " VALUES (?,?) RETURNING id_tipo_reactivo");
            consulta.setString(1, tiporeactivo.getNombre());
            consulta.setString(2, tiporeactivo.getDescripcion());
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                tiporeactivo.setId_tipo_reactivo(rs.getInt("id_tipo_reactivo"));
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public boolean editarTipoReactivo(TipoReactivo tiporeactivo)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" UPDATE control_calidad.tipos_reactivos "
                    + "SET nombre=?, descripcion=? "
                    + "WHERE id_tipo_reactivo = ?; ");
            consulta.setString(1, tiporeactivo.getNombre());
            consulta.setString(2, tiporeactivo.getDescripcion());
            consulta.setInt(3, tiporeactivo.getId_tipo_reactivo());
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
    
    public List<TipoReactivo> obtenerTipoReactivos()
    {
        List<TipoReactivo> resultado = new ArrayList<TipoReactivo>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_tipo_reactivo, nombre FROM control_calidad.tipos_reactivos; ");
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                TipoReactivo tiporeactivo = new TipoReactivo();
                tiporeactivo.setId_tipo_reactivo(rs.getInt("id_tipo_reactivo"));
                tiporeactivo.setNombre(rs.getString("nombre"));
                resultado.add(tiporeactivo);
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public TipoReactivo obtenerTipoReactivo(int id_tipo_reactivo)
    {
        TipoReactivo resultado = new TipoReactivo();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM control_calidad.tipos_reactivos WHERE id_tipo_reactivo=?; ");
            consulta.setInt(1, id_tipo_reactivo);
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_tipo_reactivo(rs.getInt("id_tipo_reactivo"));
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
    
    public boolean eliminarTipoReactivo(int id_tipo_reactivo)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.tipos_reactivos WHERE id_tipo_reactivo=?; ");
            consulta.setInt(1, id_tipo_reactivo);
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
