/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;
import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bioterio.modelos.Caja;
import com.icp.sigipro.bioterio.modelos.Coneja;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class ConejaDAO {
  private Connection conexion;
  public ConejaDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }
  public boolean insertarConeja(Coneja p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.conejas (id_caja, fecha_nacimiento, id_padre, id_madre, fecha_retiro, bool_activa)"  
              + " VALUES (?,?,?,?,?,?) RETURNING id_coneja");
      
      consulta.setDate(2, p.getFecha_nacimiento());
      consulta.setInt(1, p.getCaja().getId_caja());
      consulta.setString(3, p.getId_padre());
      consulta.setString(4, p.getId_madre());
      consulta.setDate(5, p.getFecha_retiro());
      consulta.setBoolean(6, true);
     

      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
      }
      resultadoConsulta.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar el ingreso");
    }
    return resultado;
  }

  public boolean editarConeja(Coneja p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bioterio.conejas "
              + " SET  fecha_nacimiento=?, id_padre=?, id_madre=?, fecha_retiro=?"
              + " WHERE id_coneja=?; "
      );

      consulta.setDate(1, p.getFecha_nacimiento());
      consulta.setString(2, p.getId_padre());
      consulta.setString(3, p.getId_madre());
       consulta.setDate(4, p.getFecha_retiro());
      consulta.setInt(5, p.getId_coneja());
      
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

  public boolean eliminarConeja(int id_coneja) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bioterio.conejas "
              + " WHERE id_coneja=?; "
      );

      consulta.setInt(1, id_coneja);

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

  public Coneja obtenerConeja(int id_caja) throws SIGIPROException {

    Coneja coneja = new Coneja();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.conejas where id_caja = ? AND bool_activa=true");

      consulta.setInt(1, id_caja);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        CajaDAO caja = new CajaDAO();
        coneja.setCaja(caja.obtenerCaja(rs.getInt("id_caja")));
        coneja.setId_coneja(rs.getInt("id_coneja"));
        coneja.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
        coneja.setId_padre(rs.getString("id_padre"));
        coneja.setId_madre(rs.getString("id_madre"));
        coneja.setFecha_retiro(rs.getDate("fecha_retiro"));
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return coneja;
  }
    public Coneja obtenerConeja(int id_coneja, boolean flag) throws SIGIPROException {

    Coneja coneja = new Coneja();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.conejas where id_coneja = ? ");

      consulta.setInt(1, id_coneja);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        CajaDAO caja = new CajaDAO();
        coneja.setCaja(caja.obtenerCaja(rs.getInt("id_caja")));
        coneja.setId_coneja(rs.getInt("id_coneja"));
        coneja.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
        coneja.setId_padre(rs.getString("id_padre"));
        coneja.setId_madre(rs.getString("id_madre"));
        coneja.setFecha_retiro(rs.getDate("fecha_retiro"));
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return coneja;
  }
  public List<Coneja> obtenerConejas() throws SIGIPROException {

    List<Coneja> resultado = new ArrayList<Coneja>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.conejas");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Coneja coneja = new Coneja();
        coneja.setId_coneja(rs.getInt("id_coneja"));
        coneja.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
        coneja.setId_padre(rs.getString("id_padre"));
        coneja.setId_madre(rs.getString("id_madre"));
        coneja.setFecha_retiro(rs.getDate("fecha_retiro"));
        resultado.add(coneja);
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
