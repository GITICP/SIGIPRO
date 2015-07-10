/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class SolicitudDAO extends DAO {

    public boolean entregarSolicitud(SolicitudCC solicitud) {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.solicitudes (numero_solicitud,id_usuario_solicitante,fecha_solicitud,estado) "
                    + " VALUES (?,?,?,?) RETURNING id_solicitud");
            consulta.setString(1, solicitud.getNumero_solicitud());
            consulta.setInt(2, solicitud.getUsuario_solicitante().getId_usuario());
            consulta.setDate(3, solicitud.getFecha_solicitud());
            consulta.setString(4, solicitud.getEstado());
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
    //PENDIENTE
    public boolean editarSolicitud(SolicitudCC solicitud) {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" UPDATE control_calidad.solicitudes "
                    + "SET id_usuario_recibido=?, fecha_recibido=?, estado=? "
                    + "WHERE id_solicitud = ?; ");
            consulta.setInt(1, solicitud.getUsuario_recibido().getId_usuario());
            consulta.setDate(2, solicitud.getFecha_recibido());
            consulta.setString(3, solicitud.getEstado());
            consulta.setInt(4, solicitud.getId_solicitud());
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

    public boolean recibirSolicitud(SolicitudCC solicitud) {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" UPDATE control_calidad.solicitudes "
                    + "SET id_usuario_recibido=?, fecha_recibido=?, estado='Pendiente' "
                    + "WHERE id_solicitud = ?; ");
            consulta.setInt(1, solicitud.getUsuario_recibido().getId_usuario());
            consulta.setDate(2, solicitud.getFecha_recibido());
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

    public List<SolicitudCC> obtenerSolicitudes() {
        List<SolicitudCC> resultado = new ArrayList<SolicitudCC>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_solicitud, numero_solicitud, estado FROM control_calidad.solicitudes; ");
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                SolicitudCC solicitud = new SolicitudCC();
                solicitud.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud.setNumero_solicitud(rs.getString("numero_solicitud"));
                solicitud.setEstado(rs.getString("estado"));
                resultado.add(solicitud);
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public SolicitudCC obtenerSolicitud(int id_solicitud) {
        SolicitudCC resultado = new SolicitudCC();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT solicitud.id_solicitud, solicitud.numero_solicitud, solicitud.id_usuario_solicitante, solicitud.id_usuario_recibido, solicitud.fecha_solicitud, solicitud.fecha_recibido, solicitud.estado "
                    + "FROM control_calidad.solicitudes as solicitud  "
                    + "WHERE id_solicitud=?; ");
            consulta.setInt(1, id_solicitud);
            ResultSet rs = consulta.executeQuery();
            UsuarioDAO usuariodao = new UsuarioDAO();
            if (rs.next()) {
                resultado.setId_solicitud(rs.getInt("id_solicitud"));
                resultado.setEstado("estado");
                Usuario usuario_solicitante = usuariodao.obtenerUsuario(rs.getInt("id_usuario_solicitante"));
                Usuario usuario_recibido = usuariodao.obtenerUsuario(rs.getInt("id_usuario_recibido"));
                resultado.setUsuario_recibido(usuario_recibido);
                resultado.setUsuario_solicitante(usuario_solicitante);
                resultado.setFecha_recibido(rs.getDate("fecha_recibido"));
                resultado.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                resultado.setNumero_solicitud(rs.getString("numero_solicitud"));
            }
            consulta = getConexion().prepareStatement("SELECT grupo.id_grupo, muestra.id_muestra, muestra.identificador,tm.nombre as nombretipo, analisis.id_analisis, analisis.nombre as nombreanalisis "
                    + "FROM control_calidad.solicitudes as solicitud "
                    + "LEFT OUTER JOIN control_calidad.grupos as grupo ON grupo.id_solicitud = solicitud.id_solicitud "
                    + "LEFT OUTER JOIN control_calidad.grupos_muestras as gm ON gm.id_grupo = grupo.id_grupo "
                    + "LEFT OUTER JOIN control_calidad.muestras as muestra ON muestra.id_muestra = gm.id_muestra "
                    + "LEFT OUTER JOIN control_calidad.analisis_grupo_solicitud as ags ON ags.id_grupo = grupo.id_grupo "
                    + "LEFT OUTER JOIN control_calidad.analisis as analisis ON analisis.id_analisis = ags.id_analisis "
                    + "LEFT OUTER JOIN control_calidad.tipos_muestras as tm ON tm.id_tipo_muestra = muestra.id_tipo_muestra "
                    + "WHERE solicitud.id_solicitud = ?");
            consulta.setInt(1, id_solicitud);
            rs = consulta.executeQuery();
            while(rs.next()){
                
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean anularSolicitud(int id_solicitud) {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" UPDATE control_calidad.solicitudes "
                    + "SET estado='Anulada' "
                    + "WHERE id_solicitud = ?; ");
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
