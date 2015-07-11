/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.AnalisisGrupoSolicitud;
import com.icp.sigipro.controlcalidad.modelos.Grupo;
import com.icp.sigipro.controlcalidad.modelos.Muestra;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            System.out.println(consulta);
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

    public boolean insertarGrupo(Grupo g) {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.grupos (id_solicitud) "
                    + "VALUES (?) RETURNING id_grupo; ");
            consulta.setInt(1, g.getSolicitud().getId_solicitud());
            System.out.println(consulta);
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                g.setId_grupo(rs.getInt("id_grupo"));
                System.out.println(g.getId_grupo());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean insertarMuestra(Muestra m) {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.muestras (identificador, id_tipo_muestra, fecha_descarte_estimada) "
                    + "VALUES (?,?,?) RETURNING id_muestra; ");
            consulta.setString(1, m.getIdentificador());
            consulta.setInt(2, m.getTipo_muestra().getId_tipo_muestra());
            consulta.setDate(3, m.getFecha_descarte_estimada());
            System.out.println(consulta);
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                m.setId_muestra(rs.getInt("id_muestra"));
                System.out.println(m.getId_muestra());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean insertarMuestrasGrupo(Grupo g) {
        boolean resultado = false;
        try {
            this.insertarGrupo(g);
            
            PreparedStatement consultaBatch = getConexion().prepareStatement(" INSERT INTO control_calidad.grupos_muestras (id_grupo, id_muestra) "
                    + "VALUES (?,?)");

            for (Muestra muestra : g.getGrupos_muestras()) {
                this.insertarMuestra(muestra);
                consultaBatch.setInt(1, g.getId_grupo());
                consultaBatch.setInt(2, muestra.getId_muestra());
                System.out.println(consultaBatch);
                consultaBatch.addBatch();
            }
            consultaBatch.executeBatch();
            resultado=true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean insertarAnalisisGrupoSolicitud(AnalisisGrupoSolicitud ags) {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.analisis_grupo_solicitud (id_grupo, id_analisis) "
                    + "VALUES (?,?) RETURNING id_analisis_grupo_solicitud");
            consulta.setInt(1, ags.getGrupo().getId_grupo());
            consulta.setInt(2, ags.getAnalisis().getId_analisis());
            System.out.println(consulta);
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                ags.setId_analisis_grupo_solicitud(rs.getInt("id_analisis_grupo_solicitud"));
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
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT s.id_solicitud, s.numero_solicitud, s.fecha_solicitud, s.id_usuario_solicitante, u.nombre_completo, s.estado "
                    + "FROM control_calidad.solicitudes as s INNER JOIN seguridad.usuarios as u ON u.id_usuario = s.id_usuario_solicitante ; ");
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                SolicitudCC solicitud = new SolicitudCC();
                solicitud.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud.setNumero_solicitud(rs.getString("numero_solicitud"));
                solicitud.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario_solicitante"));
                usuario.setNombre_completo(rs.getString("nombre_completo"));
                solicitud.setUsuario_solicitante(usuario);
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
            while (rs.next()) {

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
