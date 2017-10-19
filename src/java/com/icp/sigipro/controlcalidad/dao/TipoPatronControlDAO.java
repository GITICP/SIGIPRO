/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.TipoPatronControl;
import com.icp.sigipro.core.DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class TipoPatronControlDAO extends DAO {
    
    public boolean insertarTipoPatronControl(TipoPatronControl tipo_patroncontrol)
    {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.tipos_patronescontroles (nombre,descripcion,tipo) "
                                                                        + " VALUES (?,?,?) RETURNING id_tipo_patroncontrol");
            consulta.setString(1, tipo_patroncontrol.getNombre());
            consulta.setString(2, tipo_patroncontrol.getDescripcion());
            consulta.setString(3, tipo_patroncontrol.getTipo());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                tipo_patroncontrol.setId_tipo_patroncontrol(rs.getInt("id_tipo_patroncontrol"));
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public boolean editarTipoPatronControl(TipoPatronControl tipo_patroncontrol)
    {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE control_calidad.tipos_patronescontroles "
                    + "SET nombre=?, descripcion=?, tipo=? "
                    + "WHERE id_tipo_patroncontrol = ?; ");
            consulta.setString(1, tipo_patroncontrol.getNombre());
            consulta.setString(2, tipo_patroncontrol.getDescripcion());
            consulta.setString(3, tipo_patroncontrol.getTipo());
            consulta.setInt(4, tipo_patroncontrol.getId_tipo_patroncontrol());
            if (consulta.executeUpdate()==1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public List<TipoPatronControl> obtenerTiposPatronesControles()
    {
        List<TipoPatronControl> resultado = new ArrayList<>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(
                    " SELECT id_tipo_patroncontrol, nombre, tipo FROM control_calidad.tipos_patronescontroles; "
            );
            rs = consulta.executeQuery();
            while (rs.next()) {
                TipoPatronControl tipo_patroncontrol = new TipoPatronControl();
                tipo_patroncontrol.setId_tipo_patroncontrol(rs.getInt("id_tipo_patroncontrol"));
                tipo_patroncontrol.setNombre(rs.getString("nombre"));
                tipo_patroncontrol.setTipo(rs.getString("tipo"));
                resultado.add(tipo_patroncontrol);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public List<TipoPatronControl> obtenerTiposPatronesControlesOrdenado()
    {
        List<TipoPatronControl> resultado = new ArrayList<>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(
                    " SELECT id_tipo_patroncontrol, nombre, tipo FROM control_calidad.tipos_patronescontroles ORDER BY tipo; "
            );
            rs = consulta.executeQuery();
            while (rs.next()) {
                TipoPatronControl tipo_patroncontrol = new TipoPatronControl();
                tipo_patroncontrol.setId_tipo_patroncontrol(rs.getInt("id_tipo_patroncontrol"));
                tipo_patroncontrol.setNombre(rs.getString("nombre"));
                tipo_patroncontrol.setTipo(rs.getString("tipo"));
                resultado.add(tipo_patroncontrol);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public TipoPatronControl obtenerTipoPatronControl(int id_tipo_patroncontrol)
    {
        TipoPatronControl resultado = new TipoPatronControl();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(
                    " SELECT * FROM control_calidad.tipos_patronescontroles WHERE id_tipo_patroncontrol=?; "
            );
            consulta.setInt(1, id_tipo_patroncontrol);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_tipo_patroncontrol(rs.getInt("id_tipo_patroncontrol"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setDescripcion(rs.getString("descripcion"));
                resultado.setTipo(rs.getString("tipo"));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public boolean eliminarTipoPatronControl(int id_tipo_patroncontrol) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.tipos_patronescontroles WHERE id_tipo_patroncontrol=?; ");
            consulta.setInt(1, id_tipo_patroncontrol);
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
}
