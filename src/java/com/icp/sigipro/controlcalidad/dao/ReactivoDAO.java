/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.Reactivo;
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
public class ReactivoDAO extends DAO {
    
    public boolean insertarReactivo(Reactivo reactivo)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.reactivos (nombre,id_tipo_reactivo,preparacion) "
                                                                        + " VALUES (?,?,?) RETURNING id_reactivo");
            consulta.setString(1, reactivo.getNombre());
            consulta.setInt(2, reactivo.getTipo_reactivo().getId_tipo_reactivo());
            consulta.setString(3,reactivo.getPreparacion());
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                reactivo.setId_reactivo(rs.getInt("id_reactivo"));
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public boolean editarReactivo(Reactivo reactivo)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" UPDATE control_calidad.reactivos "
                    + "SET nombre=?, id_tipo_reactivo=?, preparacion=? "
                    + "WHERE id_reactivo = ?; ");
            consulta.setString(1, reactivo.getNombre());
            consulta.setInt(2, reactivo.getTipo_reactivo().getId_tipo_reactivo());
            consulta.setString(3, reactivo.getPreparacion());
            consulta.setInt(4, reactivo.getId_reactivo());
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
    
    public List<Reactivo> obtenerReactivos()
    {
        List<Reactivo> resultado = new ArrayList<Reactivo>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT reactivo.id_reactivo, reactivo.nombre, tipo.nombre as nombre_tipo "
                    + "FROM control_calidad.reactivos as reactivo INNER JOIN control_calidad.tipos_reactivos as tipo ON reactivo.id_tipo_reactivo = tipo.id_tipo_reactivo; ");
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                Reactivo reactivo = new Reactivo();
                TipoReactivo tipo = new TipoReactivo();
                tipo.setNombre(rs.getString("nombre_tipo"));
                reactivo.setTipo_reactivo(tipo);
                reactivo.setId_reactivo(rs.getInt("id_reactivo"));
                reactivo.setNombre(rs.getString("nombre"));
                resultado.add(reactivo);
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public Reactivo obtenerReactivo(int id_reactivo)
    {
        Reactivo resultado = new Reactivo();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT reactivo.id_reactivo, reactivo.nombre, reactivo.preparacion, tipo.nombre as nombre_tipo "
                    + "FROM control_calidad.reactivos as reactivo INNER JOIN control_calidad.tipos_reactivos as tipo ON reactivo.id_tipo_reactivo = tipo.id_tipo_reactivo "
                    + "WHERE reactivo.id_reactivo = ?; ");
            consulta.setInt(1, id_reactivo);
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                TipoReactivo tipo = new TipoReactivo();
                tipo.setNombre(rs.getString("nombre_tipo"));
                resultado.setTipo_reactivo(tipo);
                resultado.setPreparacion(rs.getString("preparacion"));
                resultado.setId_reactivo(rs.getInt("id_reactivo"));
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
    
    public boolean eliminarReactivo(int id_reactivo)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.reactivos WHERE id_reactivo=?; ");
            consulta.setInt(1, id_reactivo);
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
