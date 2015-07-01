/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.CertificadoReactivo;
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

    public boolean insertarCertificado(CertificadoReactivo certificado, int id_reactivo) {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.certificados_reactivos (id_reactivo,fecha_certificado,path) "
                    + " VALUES (?,?,?) RETURNING id_certificado_reactivo");
            consulta.setInt(1, id_reactivo);
            consulta.setDate(2, certificado.getFecha_certificado());
            consulta.setString(3, certificado.getPath());
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                certificado.setId_certificado_reactivo(rs.getInt("id_certificado_reactivo"));
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean insertarReactivo(Reactivo reactivo) {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.reactivos (nombre,id_tipo_reactivo,preparacion) "
                    + " VALUES (?,?,?) RETURNING id_reactivo");
            consulta.setString(1, reactivo.getNombre());
            consulta.setInt(2, reactivo.getTipo_reactivo().getId_tipo_reactivo());
            consulta.setString(3, reactivo.getPreparacion());
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                reactivo.setId_reactivo(rs.getInt("id_reactivo"));
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean editarReactivo(Reactivo reactivo) {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" UPDATE control_calidad.reactivos "
                    + "SET nombre=?, id_tipo_reactivo=?, preparacion=? "
                    + "WHERE id_reactivo = ?; ");
            consulta.setString(1, reactivo.getNombre());
            consulta.setInt(2, reactivo.getTipo_reactivo().getId_tipo_reactivo());
            consulta.setString(3, reactivo.getPreparacion());
            consulta.setInt(4, reactivo.getId_reactivo());
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

    public CertificadoReactivo obtenerCertificado(int id_certificado_reactivo) {
        CertificadoReactivo resultado = new CertificadoReactivo();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT path FROM control_calidad.certificados_reactivos WHERE id_certificado_reactivo=? ");
            consulta.setInt(1, id_certificado_reactivo);
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_certificado_reactivo(id_certificado_reactivo);
                resultado.setPath(rs.getString("path"));
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public List<Reactivo> obtenerReactivos() {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public Reactivo obtenerReactivo(int id_reactivo) {
        Reactivo resultado = new Reactivo();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT reactivo.id_reactivo, reactivo.nombre, reactivo.preparacion, tipo.id_tipo_reactivo, tipo.nombre as nombre_tipo, cert.id_certificado_reactivo, cert.fecha_certificado, cert.path "
                    + "FROM control_calidad.reactivos as reactivo INNER JOIN control_calidad.tipos_reactivos as tipo ON reactivo.id_tipo_reactivo = tipo.id_tipo_reactivo "
                    + "INNER JOIN control_calidad.certificados_reactivos as cert ON cert.id_reactivo = reactivo.id_reactivo "
                    + "WHERE reactivo.id_reactivo = ?; ");
            consulta.setInt(1, id_reactivo);
            ResultSet rs = consulta.executeQuery();
            List<CertificadoReactivo> certificados = new ArrayList<CertificadoReactivo>();
            while (rs.next()) {
                if (resultado.getId_reactivo() == 0) {
                    TipoReactivo tipo = new TipoReactivo();
                    tipo.setNombre(rs.getString("nombre_tipo"));
                    tipo.setId_tipo_reactivo(rs.getInt("id_tipo_reactivo"));
                    resultado.setTipo_reactivo(tipo);
                    resultado.setPreparacion(rs.getString("preparacion"));
                    resultado.setId_reactivo(rs.getInt("id_reactivo"));
                    resultado.setNombre(rs.getString("nombre"));
                }
                CertificadoReactivo certificado = new CertificadoReactivo();
                certificado.setFecha_certificado(rs.getDate("fecha_certificado"));
                certificado.setId_certificado_reactivo(rs.getInt("id_certificado_reactivo"));
                certificado.setPath(rs.getString("path"));
                certificados.add(certificado);
            }
            resultado.setCertificados(certificados);
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarReactivo(int id_reactivo) {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.reactivos WHERE id_reactivo=?; ");
            consulta.setInt(1, id_reactivo);
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
