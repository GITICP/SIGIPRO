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
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.tipos_reactivos (nombre,descripcion,machote,certificable) "
                                                                        + " VALUES (?,?,?,?) RETURNING id_tipo_reactivo");
            consulta.setString(1, tiporeactivo.getNombre());
            consulta.setString(2, tiporeactivo.getDescripcion());
            consulta.setString(3, tiporeactivo.getMachote());
            consulta.setBoolean(4, tiporeactivo.isCertificable());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                tiporeactivo.setId_tipo_reactivo(rs.getInt("id_tipo_reactivo"));
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
    
    public boolean editarTipoReactivo(TipoReactivo tiporeactivo)
    {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            if (tiporeactivo.getMachote().equals("")){
                consulta = getConexion().prepareStatement(" UPDATE control_calidad.tipos_reactivos "
                        + "SET nombre=?, descripcion=?, certificable=? "
                        + "WHERE id_tipo_reactivo = ?; ");
                consulta.setString(1, tiporeactivo.getNombre());
                consulta.setString(2, tiporeactivo.getDescripcion());
                consulta.setBoolean(3, tiporeactivo.isCertificable());
                consulta.setInt(4, tiporeactivo.getId_tipo_reactivo());
            }else{
                consulta = getConexion().prepareStatement(" UPDATE control_calidad.tipos_reactivos "
                        + "SET nombre=?, descripcion=?, machote=?, certificable=? "
                        + "WHERE id_tipo_reactivo = ?; ");
                consulta.setString(1, tiporeactivo.getNombre());
                consulta.setString(2, tiporeactivo.getDescripcion());
                consulta.setString(3, tiporeactivo.getMachote());
                consulta.setBoolean(4, tiporeactivo.isCertificable());
                consulta.setInt(5, tiporeactivo.getId_tipo_reactivo());
            }
            if (consulta.executeUpdate()==1) {
                resultado = true;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public List<TipoReactivo> obtenerTipoReactivos()
    {
        List<TipoReactivo> resultado = new ArrayList<TipoReactivo>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT id_tipo_reactivo, nombre FROM control_calidad.tipos_reactivos; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                TipoReactivo tiporeactivo = new TipoReactivo();
                tiporeactivo.setId_tipo_reactivo(rs.getInt("id_tipo_reactivo"));
                tiporeactivo.setNombre(rs.getString("nombre"));
                resultado.add(tiporeactivo);
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
    
    public TipoReactivo obtenerTipoReactivo(int id_tipo_reactivo)
    {
        TipoReactivo resultado = new TipoReactivo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT * FROM control_calidad.tipos_reactivos WHERE id_tipo_reactivo=?; ");
            consulta.setInt(1, id_tipo_reactivo);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_tipo_reactivo(rs.getInt("id_tipo_reactivo"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setDescripcion(rs.getString("descripcion"));
                resultado.setMachote(rs.getString("machote"));
                resultado.setCertificable(rs.getBoolean("certificable"));
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
    
    public boolean eliminarTipoReactivo(int id_tipo_reactivo)
    {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.tipos_reactivos WHERE id_tipo_reactivo=?; ");
            consulta.setInt(1, id_tipo_reactivo);
            if (consulta.executeUpdate() == 1) {
                resultado=true;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
}
