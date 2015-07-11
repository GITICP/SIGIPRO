/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.CertificadoEquipo;
import com.icp.sigipro.controlcalidad.modelos.Equipo;
import com.icp.sigipro.controlcalidad.modelos.TipoEquipo;
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
public class EquipoDAO extends DAO {

    public boolean insertarCertificado(CertificadoEquipo certificado, int id_equipo) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.certificados_equipos (id_equipo,fecha_certificado,path) "
                    + " VALUES (?,?,?) RETURNING id_certificado_equipo");
            consulta.setInt(1, id_equipo);
            consulta.setDate(2, certificado.getFecha_certificado());
            consulta.setString(3, certificado.getPath());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                certificado.setId_certificado_equipo(rs.getInt("id_certificado_equipo"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean insertarEquipo(Equipo equipo) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.equipos (nombre,id_tipo_equipo,descripcion) "
                    + " VALUES (?,?,?) RETURNING id_equipo");
            consulta.setString(1, equipo.getNombre());
            consulta.setInt(2, equipo.getTipo_equipo().getId_tipo_equipo());
            consulta.setString(3, equipo.getDescripcion());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                equipo.setId_equipo(rs.getInt("id_equipo"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean editarEquipo(Equipo equipo) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE control_calidad.equipos "
                    + "SET nombre=?, descripcion=? "
                    + "WHERE id_equipo = ?; ");
            consulta.setString(1, equipo.getNombre());
            consulta.setString(2, equipo.getDescripcion());
            consulta.setInt(3, equipo.getId_equipo());
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public CertificadoEquipo obtenerCertificado(int id_certificado_equipo) {
        CertificadoEquipo resultado = new CertificadoEquipo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT path FROM control_calidad.certificados_equipos WHERE id_certificado_equipo=? ");
            consulta.setInt(1, id_certificado_equipo);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_certificado_equipo(id_certificado_equipo);
                resultado.setPath(rs.getString("path"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public List<Equipo> obtenerEquipos() {
        List<Equipo> resultado = new ArrayList<Equipo>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT equipo.id_equipo, equipo.nombre, tipo.nombre as nombre_tipo "
                    + "FROM control_calidad.equipos as equipo INNER JOIN control_calidad.tipos_equipos as tipo ON equipo.id_tipo_equipo = tipo.id_tipo_equipo; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Equipo equipo = new Equipo();
                TipoEquipo tipo = new TipoEquipo();
                tipo.setNombre(rs.getString("nombre_tipo"));
                equipo.setTipo_equipo(tipo);
                equipo.setId_equipo(rs.getInt("id_equipo"));
                equipo.setNombre(rs.getString("nombre"));
                resultado.add(equipo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public List<Equipo> obtenerEquiposTipo(int[] ids_tipos) throws SIGIPROException {
        
        List<Equipo> resultado = new ArrayList<Equipo>();
        
        PreparedStatement consulta = null;
        ResultSet rs = null;
        
        try {
            String ids_parseados = this.pasar_ids_a_parentesis(ids_tipos);
            consulta = getConexion().prepareStatement(
                    " SELECT id_equipo, nombre FROM control_calidad.equipos WHERE id_tipo_equipo in " + ids_parseados + "; "
            );
            
            rs = consulta.executeQuery();
            
            while(rs.next()) {
                Equipo equipo = new Equipo();
                equipo.setNombre(rs.getString("nombre"));
                equipo.setId_equipo(rs.getInt("id_equipo"));
                
                resultado.add(equipo);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener el listado de equipos.");
        }
        finally {
            cerrarSilencioso(consulta);
            cerrarSilencioso(rs);
            cerrarConexion();
        }
        
        return resultado;
    }

    public Equipo obtenerEquipo(int id_equipo) {
        Equipo resultado = new Equipo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT equipo.id_equipo, equipo.nombre, equipo.descripcion, tipo.id_tipo_equipo, tipo.nombre as nombre_tipo, cert.id_certificado_equipo, cert.fecha_certificado, cert.path "
                    + "FROM control_calidad.equipos as equipo INNER JOIN control_calidad.tipos_equipos as tipo ON equipo.id_tipo_equipo = tipo.id_tipo_equipo "
                    + "LEFT OUTER JOIN control_calidad.certificados_equipos as cert ON cert.id_equipo = equipo.id_equipo "
                    + "WHERE equipo.id_equipo = ?; ");
            consulta.setInt(1, id_equipo);
            rs = consulta.executeQuery();
            List<CertificadoEquipo> certificados = new ArrayList<CertificadoEquipo>();
            while (rs.next()) {
                if (resultado.getId_equipo() == 0) {
                    TipoEquipo tipo = new TipoEquipo();
                    tipo.setNombre(rs.getString("nombre_tipo"));
                    tipo.setId_tipo_equipo(rs.getInt("id_tipo_equipo"));
                    resultado.setTipo_equipo(tipo);
                    resultado.setDescripcion(rs.getString("descripcion"));
                    resultado.setId_equipo(rs.getInt("id_equipo"));
                    resultado.setNombre(rs.getString("nombre"));
                }
                if (rs.getInt("id_certificado_equipo") != 0) {
                    CertificadoEquipo certificado = new CertificadoEquipo();
                    certificado.setFecha_certificado(rs.getDate("fecha_certificado"));
                    certificado.setId_certificado_equipo(rs.getInt("id_certificado_equipo"));
                    certificado.setPath(rs.getString("path"));
                    certificados.add(certificado);
                }
            }
            resultado.setCertificados(certificados);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean eliminarEquipo(int id_equipo) {
        PreparedStatement consulta = null;
        boolean resultado = false;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.equipos WHERE id_equipo=?; ");
            consulta.setInt(1, id_equipo);
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean eliminarCertificado(int id_certificado) {
        PreparedStatement consulta = null;
        boolean resultado = false;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.certificados_equipos WHERE id_certificado_equipo=?; ");
            consulta.setInt(1, id_certificado);
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
}
