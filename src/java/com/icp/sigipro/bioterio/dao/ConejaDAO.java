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
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.conejas (id_caja, fecha_nacimiento, id_padre, id_madre, fecha_retiro, bool_activa, fecha_ingreso, fecha_cambio, fecha_seleccion)"  
              + " VALUES (?,?,?,?,?,?,?,?,?) RETURNING id_coneja");
      
      consulta.setDate(2, p.getFecha_nacimiento());
      consulta.setInt(1, p.getCaja().getId_caja());
      consulta.setString(3, p.getId_padre());
      consulta.setString(4, p.getId_madre());
      consulta.setDate(5, p.getFecha_retiro());
      consulta.setBoolean(6, true);
      consulta.setDate(7, p.getFecha_ingreso());
      consulta.setDate(8, p.getFecha_cambio());
      consulta.setDate(9, p.getFecha_seleccion());
     

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
              + " SET  fecha_nacimiento=?, id_padre=?, id_madre=?, fecha_retiro=?, fecha_ingreso=?, fecha_cambio=?, fecha_seleccion=?"
              + " WHERE id_coneja=?; "
      );

      consulta.setDate(1, p.getFecha_nacimiento());
      consulta.setString(2, p.getId_padre());
      consulta.setString(3, p.getId_madre());
      consulta.setDate(4, p.getFecha_retiro());
      consulta.setDate(5, p.getFecha_ingreso());
      consulta.setDate(6, p.getFecha_cambio());
      consulta.setDate(7, p.getFecha_seleccion());
      consulta.setInt(8, p.getId_coneja());
      
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
  public boolean eliminarConeja(Coneja p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bioterio.conejas "
              + " SET  bool_activa=?, fecha_cambio=?, observaciones=?"
              + " WHERE id_coneja=?; "
      );
      consulta.setBoolean(1, false);
      consulta.setDate(2, p.getFecha_cambio());
      consulta.setString(3, p.getObservaciones());
      consulta.setInt(4, p.getId_coneja());
      
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
        coneja.setFecha_ingreso(rs.getDate("fecha_ingreso"));
        coneja.setFecha_cambio(rs.getDate("fecha_cambio"));
        coneja.setFecha_seleccion(rs.getDate("fecha_seleccion"));
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
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.conejas where id_coneja = ? AND bool_activa=true");

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
        coneja.setFecha_ingreso(rs.getDate("fecha_ingreso"));
        coneja.setFecha_cambio(rs.getDate("fecha_cambio"));
        coneja.setFecha_seleccion(rs.getDate("fecha_seleccion"));
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return coneja;
  }
  public List<Coneja> obtenerConejas(int id_grupo) throws SIGIPROException {

    List<Coneja> resultado = new ArrayList<Coneja>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.conejas c INNER JOIN bioterio.cajas p ON c.id_caja = p.id_caja WHERE p.id_grupo=? AND c.bool_activa=true");
      consulta.setInt(1, id_grupo);
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Coneja coneja = new Coneja();
        coneja.setId_coneja(rs.getInt("id_coneja"));
        coneja.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
        coneja.setId_padre(rs.getString("id_padre"));
        coneja.setId_madre(rs.getString("id_madre"));
        coneja.setFecha_retiro(rs.getDate("fecha_retiro"));
        coneja.setFecha_ingreso(rs.getDate("fecha_ingreso"));
        coneja.setFecha_cambio(rs.getDate("fecha_cambio"));
        coneja.setFecha_seleccion(rs.getDate("fecha_seleccion"));
        Caja caja = new Caja();
        caja.setId_caja(rs.getInt("id_caja"));
        caja.setNumero(rs.getInt("numero"));
        coneja.setCaja(caja);
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
