/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.CertificadoReactivo;
import com.icp.sigipro.controlcalidad.modelos.Equipo;
import com.icp.sigipro.controlcalidad.modelos.Reactivo;
import com.icp.sigipro.controlcalidad.modelos.TipoReactivo;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class ReactivoDAO extends DAO {

    public boolean insertarCertificado(CertificadoReactivo certificado, int id_reactivo) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.certificados_reactivos (id_reactivo,fecha_certificado,path) "
                    + " VALUES (?,?,?) RETURNING id_certificado_reactivo");
            consulta.setInt(1, id_reactivo);
            consulta.setDate(2, certificado.getFecha_certificado());
            consulta.setString(3, certificado.getPath());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                certificado.setId_certificado_reactivo(rs.getInt("id_certificado_reactivo"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean insertarReactivo(Reactivo reactivo) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.reactivos (nombre,id_tipo_reactivo,preparacion) "
                    + " VALUES (?,?,?) RETURNING id_reactivo");
            consulta.setString(1, reactivo.getNombre());
            consulta.setInt(2, reactivo.getTipo_reactivo().getId_tipo_reactivo());
            consulta.setString(3, reactivo.getPreparacion());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                reactivo.setId_reactivo(rs.getInt("id_reactivo"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean editarReactivo(Reactivo reactivo) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            if (reactivo.getPreparacion().equals("")) {
                consulta = getConexion().prepareStatement(" UPDATE control_calidad.reactivos "
                        + "SET nombre=? "
                        + "WHERE id_reactivo = ?; ");
                consulta.setString(1, reactivo.getNombre());
                consulta.setInt(2, reactivo.getId_reactivo());
            } else {
                consulta = getConexion().prepareStatement(" UPDATE control_calidad.reactivos "
                        + "SET nombre=?, preparacion=? "
                        + "WHERE id_reactivo = ?; ");
                consulta.setString(1, reactivo.getNombre());
                consulta.setString(2, reactivo.getPreparacion());
                consulta.setInt(3, reactivo.getId_reactivo());
            }
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

    public CertificadoReactivo obtenerCertificado(int id_certificado_reactivo) {
        CertificadoReactivo resultado = new CertificadoReactivo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT path FROM control_calidad.certificados_reactivos WHERE id_certificado_reactivo=? ");
            consulta.setInt(1, id_certificado_reactivo);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_certificado_reactivo(id_certificado_reactivo);
                resultado.setPath(rs.getString("path"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public List<Reactivo> obtenerReactivos() {
        PreparedStatement consulta = null;
        ResultSet rs = null;
        List<Reactivo> resultado = new ArrayList<Reactivo>();
        try {
            consulta = getConexion().prepareStatement(" SELECT reactivo.id_reactivo, reactivo.nombre, tipo.nombre as nombre_tipo "
                    + "FROM control_calidad.reactivos as reactivo INNER JOIN control_calidad.tipos_reactivos as tipo ON reactivo.id_tipo_reactivo = tipo.id_tipo_reactivo; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Reactivo reactivo = new Reactivo();
                TipoReactivo tipo = new TipoReactivo();
                tipo.setNombre(rs.getString("nombre_tipo"));
                reactivo.setTipo_reactivo(tipo);
                reactivo.setId_reactivo(rs.getInt("id_reactivo"));
                reactivo.setNombre(rs.getString("nombre"));
                resultado.add(reactivo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public Reactivo obtenerReactivo(int id_reactivo) {
        PreparedStatement consulta = null;
        ResultSet rs = null;
        Reactivo resultado = new Reactivo();
        try {
            consulta = getConexion().prepareStatement(" SELECT reactivo.id_reactivo, reactivo.nombre, reactivo.preparacion, tipo.id_tipo_reactivo, tipo.nombre as nombre_tipo, cert.id_certificado_reactivo, cert.fecha_certificado, cert.path "
                    + "FROM control_calidad.reactivos as reactivo INNER JOIN control_calidad.tipos_reactivos as tipo ON reactivo.id_tipo_reactivo = tipo.id_tipo_reactivo "
                    + "LEFT OUTER JOIN control_calidad.certificados_reactivos as cert ON cert.id_reactivo = reactivo.id_reactivo "
                    + "WHERE reactivo.id_reactivo = ?; ");
            consulta.setInt(1, id_reactivo);
            rs = consulta.executeQuery();
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
                if (rs.getInt("id_certificado_reactivo") != 0) {
                    CertificadoReactivo certificado = new CertificadoReactivo();
                    certificado.setFecha_certificado(rs.getDate("fecha_certificado"));
                    certificado.setId_certificado_reactivo(rs.getInt("id_certificado_reactivo"));
                    certificado.setPath(rs.getString("path"));
                    certificados.add(certificado);
                }
            }
            resultado.setCertificados(certificados);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean eliminarReactivo(int id_reactivo) {
        PreparedStatement consulta = null;
        boolean resultado = false;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.reactivos WHERE id_reactivo=?; ");
            consulta.setInt(1, id_reactivo);
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean eliminarCertificado(int id_certificado) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.certificados_reactivos WHERE id_certificado_reactivo=?; ");
            consulta.setInt(1, id_certificado);
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public List<Reactivo> obtenerReactivosTipo(int[] ids_tipos) throws SIGIPROException
    {
        List<Reactivo> resultado = new ArrayList<Reactivo>();
        
        PreparedStatement consulta = null;
        ResultSet rs = null;
        
        try {
            String ids_parseados = this.pasar_ids_a_parentesis(ids_tipos);
            
            consulta = getConexion().prepareStatement(
                    " SELECT id_reactivo, nombre, preparacion FROM control_calidad.reactivos WHERE id_tipo_reactivo in " + ids_parseados + "; "
            );
            
            rs = consulta.executeQuery();
            
            while(rs.next()) {
                Reactivo reactivo = new Reactivo();
                
                reactivo.setNombre(rs.getString("nombre"));
                reactivo.setId_reactivo(rs.getInt("id_reactivo"));
                reactivo.setPreparacion(rs.getString("preparacion"));
                
                resultado.add(reactivo);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener el listado de reactivos.");
        }
        finally {
            cerrarSilencioso(consulta);
            cerrarSilencioso(rs);
            cerrarConexion();
        }
        
        return resultado;
    }

}
