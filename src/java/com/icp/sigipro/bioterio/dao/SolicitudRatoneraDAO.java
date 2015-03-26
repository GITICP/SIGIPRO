/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;
import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bioterio.modelos.SolicitudRatonera;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class SolicitudRatoneraDAO {
  private Connection conexion;
  public SolicitudRatoneraDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }
  public boolean insertarSolicitudRatonera(SolicitudRatonera p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.solicitudes_ratonera (fecha_solicitud, numero_animales, peso_requerido, "
              + "numero_cajas,sexo,id_cepa,usuario_solicitante,observaciones,observaciones_rechazo,estado)"  
              + " VALUES (?,?,?,?,?,?,?,?,?,?) RETURNING id_solicitud");
      
      consulta.setDate(1, p.getFecha_solicitud());
      consulta.setInt(2, p.getNumero_animales());
      consulta.setString(3, p.getPeso_requerido());
      consulta.setInt(4, p.getNumero_cajas());
      consulta.setString(5, p.getSexo());
      consulta.setInt(6, p.getCepa().getId_cepa());
      consulta.setInt(7, p.getUsuario_solicitante().getID());
      consulta.setString(8, p.getObservaciones());
      consulta.setString(9, p.getObservaciones_rechazo());
      consulta.setString(10, p.getEstado());
      
      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
      }
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar el ingreso");
    }
    return resultado;
  }

  public boolean editarSolicitudRatonera(SolicitudRatonera p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bioterio.solicitudes_ratonera "
              + " SET   fecha_solicitud=?, numero_animales=?, peso_requerido=?, "
              + " numero_cajas=?,sexo=?,id_cepa=?,usuario_solicitante=?,observaciones=?,observaciones_rechazo=?,estado=?"
              + " WHERE id_solicitud=?; "
      );

      consulta.setDate(1, p.getFecha_solicitud());
      consulta.setInt(2, p.getNumero_animales());
      consulta.setString(3, p.getPeso_requerido());
      consulta.setInt(4, p.getNumero_cajas());
      consulta.setString(5, p.getSexo());
      consulta.setInt(6, p.getCepa().getId_cepa());
      consulta.setInt(7, p.getUsuario_solicitante().getID());
      consulta.setString(8, p.getObservaciones());
      consulta.setString(9, p.getObservaciones_rechazo());
      consulta.setString(10, p.getEstado());

      if (consulta.executeUpdate() == 1) {
        resultado = true;
      }
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la edición");
    }
    return resultado;
  }

  public boolean eliminarSolicitudRatonera(int id_solicitud) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bioterio.solicitudes_ratonera "
              + " WHERE id_solicitud=?; "
      );

      consulta.setInt(1, id_solicitud);

      if (consulta.executeUpdate() == 1) {
        resultado = true;
      }
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la eliminación");
    }
    return resultado;
  }

  public SolicitudRatonera obtenerSolicitudRatonera(int id) throws SIGIPROException {

    SolicitudRatonera solicitud_ratonera = new SolicitudRatonera();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.solicitudes_ratonera where id_solicitud = ?");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        CepaDAO cep = new CepaDAO();
        UsuarioDAO usr = new UsuarioDAO();
        solicitud_ratonera.setId_solicitud(rs.getInt("id_solicitud"));
        solicitud_ratonera.setFecha_solicitud(rs.getDate("fecha_solicitud"));
        solicitud_ratonera.setNumero_animales(rs.getInt("numero_animales"));
        solicitud_ratonera.setPeso_requerido(rs.getString("peso_requerido"));
        solicitud_ratonera.setNumero_cajas(rs.getInt("numero_cajas"));
        solicitud_ratonera.setSexo(rs.getString("sexo"));
        solicitud_ratonera.setCepa(cep.obtenerCepa(rs.getInt("id_cepa")));
        solicitud_ratonera.setUsuario_solicitante(usr.obtenerUsuario(rs.getInt("usuario_solicitante")));
        solicitud_ratonera.setObservaciones(rs.getString("observaciones"));
        solicitud_ratonera.setObservaciones_rechazo(rs.getString("observaciones_rechazo"));
        solicitud_ratonera.setEstado(rs.getString("estado"));


      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return solicitud_ratonera;
  }

  public List<SolicitudRatonera> obtenerSolicitudesRatonera() throws SIGIPROException {

    List<SolicitudRatonera> resultado = new ArrayList<SolicitudRatonera>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.solicitudes_ratonera");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        SolicitudRatonera solicitud_ratonera = new SolicitudRatonera();
        CepaDAO cep = new CepaDAO();
        UsuarioDAO usr = new UsuarioDAO();
        solicitud_ratonera.setId_solicitud(rs.getInt("id_solicitud"));
        solicitud_ratonera.setFecha_solicitud(rs.getDate("fecha_solicitud"));
        solicitud_ratonera.setNumero_animales(rs.getInt("numero_animales"));
        solicitud_ratonera.setPeso_requerido(rs.getString("peso_requerido"));
        solicitud_ratonera.setNumero_cajas(rs.getInt("numero_cajas"));
        solicitud_ratonera.setSexo(rs.getString("sexo"));
        solicitud_ratonera.setCepa(cep.obtenerCepa(rs.getInt("id_cepa")));
        solicitud_ratonera.setUsuario_solicitante(usr.obtenerUsuario(rs.getInt("usuario_solicitante")));
        solicitud_ratonera.setObservaciones(rs.getString("observaciones"));
        solicitud_ratonera.setObservaciones_rechazo(rs.getString("observaciones_rechazo"));
        solicitud_ratonera.setEstado(rs.getString("estado"));
        resultado.add(solicitud_ratonera);
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return resultado;
  }
  private Connection getConexion()
  {
    SingletonBD s = SingletonBD.getSingletonBD();
    if (conexion == null) {
      conexion = s.conectar();
    }
    else {
      try {
        if (conexion.isClosed()) {
          conexion = s.conectar();
        }
      }
      catch (Exception ex) {
        conexion = null;
      }
    }
    return conexion;
  }

  private void cerrarConexion()
  {
    if (conexion != null) {
      try {
        if (conexion.isClosed()) {
          conexion.close();
        }
      }
      catch (Exception ex) {
        conexion = null;
      }
    }
  }
}
