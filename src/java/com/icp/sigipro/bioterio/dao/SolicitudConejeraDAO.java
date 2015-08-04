/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;

import com.icp.sigipro.bioterio.modelos.SolicitudConejera;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class SolicitudConejeraDAO extends DAO
{

    public SolicitudConejeraDAO()
    {  }

    public boolean insertarSolicitudConejera(SolicitudConejera p) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.solicitudes_conejera (fecha_solicitud, numero_animales, peso_requerido, "
                                                                        + "sexo,usuario_solicitante,observaciones,observaciones_rechazo,estado, fecha_necesita, usuario_utiliza)"
                                                                        + " VALUES (?,?,?,?,?,?,?,?,?,?) RETURNING id_solicitud");

            consulta.setDate(1, p.getFecha_solicitud());
            consulta.setInt(2, p.getNumero_animales());
            consulta.setString(3, p.getPeso_requerido());
            consulta.setString(4, p.getSexo());
            consulta.setInt(5, p.getUsuario_solicitante().getID());
            consulta.setString(6, p.getObservaciones());
            consulta.setString(7, p.getObservaciones_rechazo());
            consulta.setString(8, p.getEstado());
            consulta.setDate(9, p.getFecha_necesita());
            consulta.setInt(10, p.getUsuario_utiliza().getID());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");
        }
        return resultado;
    }

    public boolean editarSolicitudConejera(SolicitudConejera p) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE bioterio.solicitudes_conejera "
                    + " SET   fecha_solicitud=?, numero_animales=?, peso_requerido=?, "
                    + " sexo=?,usuario_solicitante=?,observaciones=?,observaciones_rechazo=?,estado=?, fecha_necesita=?, usuario_utiliza=?"
                    + " WHERE id_solicitud=?; "
            );

            consulta.setDate(1, p.getFecha_solicitud());
            consulta.setInt(2, p.getNumero_animales());
            consulta.setString(3, p.getPeso_requerido());
            consulta.setString(4, p.getSexo());
            consulta.setInt(5, p.getUsuario_solicitante().getID());
            consulta.setString(6, p.getObservaciones());
            consulta.setString(7, p.getObservaciones_rechazo());
            consulta.setString(8, p.getEstado());
            consulta.setDate(9, p.getFecha_necesita());
            if (p.getUsuario_utiliza() != null) {
                consulta.setInt(10, p.getUsuario_utiliza().getID());
            } else {
                consulta.setNull(10, java.sql.Types.INTEGER);
            }
            
            consulta.setInt(11, p.getId_solicitud());

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la edición");
        }
        return resultado;
    }

    public boolean eliminarSolicitudConejera(int id_solicitud) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM bioterio.solicitudes_conejera "
                    + " WHERE id_solicitud=?; "
            );

            consulta.setInt(1, id_solicitud);

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la eliminación");
        }
        return resultado;
    }

    public SolicitudConejera obtenerSolicitudConejera(int id) throws SIGIPROException
    {

        SolicitudConejera solicitud_conejera = new SolicitudConejera();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.solicitudes_conejera where id_solicitud = ?");

            consulta.setInt(1, id);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                UsuarioDAO usr = new UsuarioDAO();
                solicitud_conejera.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud_conejera.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                solicitud_conejera.setNumero_animales(rs.getInt("numero_animales"));
                solicitud_conejera.setPeso_requerido(rs.getString("peso_requerido"));
                solicitud_conejera.setSexo(rs.getString("sexo"));
                solicitud_conejera.setUsuario_solicitante(usr.obtenerUsuario(rs.getInt("usuario_solicitante")));
                solicitud_conejera.setUsuario_utiliza(usr.obtenerUsuario(rs.getInt("usuario_utiliza")));
                solicitud_conejera.setObservaciones(rs.getString("observaciones"));
                solicitud_conejera.setObservaciones_rechazo(rs.getString("observaciones_rechazo"));
                solicitud_conejera.setEstado(rs.getString("estado"));
                solicitud_conejera.setFecha_necesita(rs.getDate("fecha_necesita"));
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return solicitud_conejera;
    }

    public List<SolicitudConejera> obtenerSolicitudesConejera(int id_usuario) throws SIGIPROException
    {

        List<SolicitudConejera> resultado = new ArrayList<SolicitudConejera>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.solicitudes_conejera s "
                                                      + "INNER JOIN seguridad.usuarios u ON s.usuario_solicitante = u.id_usuario "
                                                      + "WHERE s.usuario_solicitante=? ");
            consulta.setInt(1, id_usuario);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                SolicitudConejera solicitud_conejera = new SolicitudConejera();
                Usuario usr;
                solicitud_conejera.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud_conejera.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                solicitud_conejera.setNumero_animales(rs.getInt("numero_animales"));
                solicitud_conejera.setPeso_requerido(rs.getString("peso_requerido"));
                solicitud_conejera.setSexo(rs.getString("sexo"));
                solicitud_conejera.setObservaciones(rs.getString("observaciones"));
                solicitud_conejera.setObservaciones_rechazo(rs.getString("observaciones_rechazo"));
                solicitud_conejera.setEstado(rs.getString("estado"));
                solicitud_conejera.setFecha_necesita(rs.getDate("fecha_necesita"));
                usr = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre_usuario"), rs.getString("correo"), rs.getString("nombre_completo"), rs.getString("cedula"), rs.getInt("id_seccion"), rs.getInt("id_puesto"), rs.getDate("fecha_activacion"), rs.getDate("fecha_desactivacion"), rs.getBoolean("estado"));
                UsuarioDAO usr2 = new UsuarioDAO();
                solicitud_conejera.setUsuario_utiliza(usr2.obtenerUsuario(rs.getInt("usuario_utiliza")));
                solicitud_conejera.setUsuario_solicitante(usr);
                resultado.add(solicitud_conejera);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }

    public List<SolicitudConejera> obtenerSolicitudesConejeraAdm() throws SIGIPROException
    {

        List<SolicitudConejera> resultado = new ArrayList<SolicitudConejera>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.solicitudes_conejera s "
                                                      + "INNER JOIN seguridad.usuarios u ON s.usuario_solicitante = u.id_usuario ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                SolicitudConejera solicitud_conejera = new SolicitudConejera();
                Usuario usr;
                solicitud_conejera.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud_conejera.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                solicitud_conejera.setNumero_animales(rs.getInt("numero_animales"));
                solicitud_conejera.setPeso_requerido(rs.getString("peso_requerido"));
                solicitud_conejera.setSexo(rs.getString("sexo"));
                solicitud_conejera.setObservaciones(rs.getString("observaciones"));
                solicitud_conejera.setObservaciones_rechazo(rs.getString("observaciones_rechazo"));
                solicitud_conejera.setEstado(rs.getString("estado"));
                solicitud_conejera.setFecha_necesita(rs.getDate("fecha_necesita"));
                usr = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre_usuario"), rs.getString("correo"), rs.getString("nombre_completo"), rs.getString("cedula"), rs.getInt("id_seccion"), rs.getInt("id_puesto"), rs.getDate("fecha_activacion"), rs.getDate("fecha_desactivacion"), rs.getBoolean("estado"));
                UsuarioDAO usr2 = new UsuarioDAO();
                solicitud_conejera.setUsuario_utiliza(usr2.obtenerUsuario(rs.getInt("usuario_utiliza")));
                solicitud_conejera.setUsuario_solicitante(usr);
                resultado.add(solicitud_conejera);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }
}
