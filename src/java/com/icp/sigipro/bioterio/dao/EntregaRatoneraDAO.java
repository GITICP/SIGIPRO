/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bioterio.modelos.Cepa;
import com.icp.sigipro.bioterio.modelos.EntregaRatonera;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class EntregaRatoneraDAO {
  private Connection conexion;
  public EntregaRatoneraDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }
  public boolean insertarEntregaRatonera(EntregaRatonera p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.entregas_solicitudes_ratonera (id_solicitud, fecha_entrega, numero_animales, peso, "
              + "numero_cajas,sexo,id_cepa,usuario_recipiente)"  
              + " VALUES (?,?,?,?,?,?,?,?) RETURNING id_entrega");
      
      consulta.setInt(1, p.getSolicitud().getId_solicitud());
      consulta.setDate(2, p.getFecha_entrega());
      consulta.setInt(3, p.getNumero_animales());
      consulta.setString(4, p.getPeso());
      consulta.setInt(5, p.getNumero_cajas());
      consulta.setString(6, p.getSexo());
      consulta.setInt(7, p.getCepa().getId_cepa());
      consulta.setInt(8, p.getUsuario_recipiente().getID());

      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
        revisarCompletitudEntrega(p.getSolicitud().getId_solicitud());
      }
      resultadoConsulta.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar el ingreso");
    }
    return resultado;
  }
  public void revisarCompletitudEntrega(int id_solicitud) throws SIGIPROException{
      try{
      PreparedStatement consulta = getConexion().prepareStatement("SELECT SUM(numero_animales) as suma FROM bioterio.entregas_solicitudes_ratonera where id_solicitud = ?");
      consulta.setInt(1, id_solicitud);
      PreparedStatement consulta2 = getConexion().prepareStatement("SELECT numero_animales FROM bioterio.solicitudes_ratonera where id_solicitud = ?");
      consulta2.setInt(1, id_solicitud);

      ResultSet rs = consulta.executeQuery();
      ResultSet rs2 = consulta2.executeQuery();
      if (rs.next() && rs2.next()) {
        if (rs.getInt("suma") >= rs2.getInt("numero_animales")) {
          PreparedStatement consulta3 = getConexion().prepareStatement(
                  " UPDATE bioterio.solicitudes_ratonera "
                  + " SET estado='Entregada'"
                  + " WHERE id_solicitud=?; "
          );
          consulta3.setInt(1, id_solicitud);
          consulta3.executeUpdate();
          consulta3.close();
        }
      }
      rs.close();
      rs2.close();
      consulta.close();
      consulta2.close();
      cerrarConexion();
    }
      catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al revisar la completitud de la entrega");
    }
  }
 

  public boolean eliminarEntregaRatonera(int id_solicitud) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bioterio.entregas_solicitudes_ratonera "
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

  public EntregaRatonera obtenerEntregaRatonera(int id) throws SIGIPROException {

    EntregaRatonera entrega_ratonera = new EntregaRatonera();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.entregas_solicitudes_ratonera where id_entrega = ?");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        CepaDAO cep = new CepaDAO();
        UsuarioDAO usr = new UsuarioDAO();
        SolicitudRatoneraDAO sdao = new SolicitudRatoneraDAO();
        entrega_ratonera.setId_entrega(rs.getInt("id_entrega"));
        entrega_ratonera.setSolicitud(sdao.obtenerSolicitudRatonera(rs.getInt("id_solicitud")));
        entrega_ratonera.setFecha_entrega(rs.getDate("fecha_entrega"));
        entrega_ratonera.setNumero_animales(rs.getInt("numero_animales"));
        entrega_ratonera.setPeso(rs.getString("peso"));
        entrega_ratonera.setNumero_cajas(rs.getInt("numero_cajas"));
        entrega_ratonera.setSexo(rs.getString("sexo"));
        entrega_ratonera.setCepa(cep.obtenerCepa(rs.getInt("id_cepa")));
        entrega_ratonera.setUsuario_recipiente(usr.obtenerUsuario(rs.getInt("usuario_recipiente")));
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return entrega_ratonera;
  }

  public List<EntregaRatonera> obtenerEntregasRatonera(int id_solicitud) throws SIGIPROException {

    List<EntregaRatonera> resultado = new ArrayList<EntregaRatonera>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.entregas_solicitudes_ratonera e " 
              + "INNER JOIN seguridad.usuarios u ON e.usuario_recipiente = u.id_usuario " 
              + "INNER JOIN bioterio.cepas c ON e.id_cepa = c.id_cepa WHERE e.id_solicitud=?");
      consulta.setInt(1, id_solicitud);
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        EntregaRatonera entrega_ratonera = new EntregaRatonera();
        Cepa cep;
        Usuario usr;
        SolicitudRatoneraDAO sdao = new SolicitudRatoneraDAO();
        entrega_ratonera.setId_entrega(rs.getInt("id_entrega"));
        entrega_ratonera.setSolicitud(sdao.obtenerSolicitudRatonera(rs.getInt("id_solicitud")));
        entrega_ratonera.setFecha_entrega(rs.getDate("fecha_entrega"));
        entrega_ratonera.setNumero_animales(rs.getInt("numero_animales"));
        entrega_ratonera.setPeso(rs.getString("peso"));
        entrega_ratonera.setNumero_cajas(rs.getInt("numero_cajas"));
        entrega_ratonera.setSexo(rs.getString("sexo"));
        cep = new Cepa(rs.getInt("id_cepa"),rs.getString("nombre"));
        usr = new Usuario(rs.getInt("id_usuario"),rs.getString("nombre_usuario"),rs.getString("correo"),rs.getString("nombre_completo"),rs.getString("cedula"),rs.getInt("id_seccion"),rs.getInt("id_puesto"),rs.getDate("fecha_activacion"),rs.getDate("fecha_desactivacion"),rs.getBoolean("estado"));
        entrega_ratonera.setCepa(cep);
        entrega_ratonera.setUsuario_recipiente(usr);
        resultado.add(entrega_ratonera);
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
