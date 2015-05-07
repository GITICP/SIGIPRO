/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;
import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bioterio.modelos.Cruce;
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
public class CruceDAO {
  private Connection conexion;
  public CruceDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }
  public boolean insertarCruce(Cruce p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.cruces (id_macho, id_coneja, fecha_cruce, observaciones, fecha_parto, cantidad_paridos)"  
              + " VALUES (?,?,?,?,?,?) RETURNING id_cruce");
      
      consulta.setInt(1, p.getMacho().getId_macho());
      consulta.setInt(2, p.getConeja().getId_coneja());
      consulta.setDate(3, p.getFecha_cruce());
      consulta.setString(4, p.getObservaciones());
      consulta.setDate(5, p.getFecha_parto());
      consulta.setInt(6, p.getCantidad_paridos());
      
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

  public boolean editarCruce(Cruce p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bioterio.cruces "
              + " SET  fecha_cruce=?, id_macho=?, observaciones=?, fecha_parto=?, cantidad_paridos=?"
              + " WHERE id_cruce=?; "
      );

      consulta.setDate(1, p.getFecha_cruce());
      consulta.setInt(2, p.getMacho().getId_macho());
      consulta.setString(3, p.getObservaciones());
      consulta.setDate(4, p.getFecha_parto());
      consulta.setInt(5, p.getCantidad_paridos());
      consulta.setInt(6, p.getId_cruce());
      
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

  public boolean eliminarCruce(int id_cruce) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bioterio.cruces "
              + " WHERE id_cruce=?; "
      );

      consulta.setInt(1, id_cruce);

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

  public Cruce obtenerCruce(int id) throws SIGIPROException {

    Cruce cruce = new Cruce();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.cruces where id_cruce = ?");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();
      
      if (rs.next()) {
        MachoDAO m = new MachoDAO();
        ConejaDAO c = new ConejaDAO();
        cruce.setId_cruce(rs.getInt("id_cruce"));
        cruce.setFecha_cruce(rs.getDate("fecha_cruce"));
        cruce.setMacho(m.obtenerMacho(rs.getInt("id_macho")));
        cruce.setConeja(c.obtenerConeja(rs.getInt("id_coneja"),true));
        cruce.setFecha_parto(rs.getDate("fecha_parto"));
        cruce.setObservaciones(rs.getString("observaciones"));
        cruce.setCantidad_paridos(rs.getInt("cantidad_paridos"));
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return cruce;
  }

  public List<Cruce> obtenerCruces() throws SIGIPROException {

    List<Cruce> resultado = new ArrayList<Cruce>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.cruces");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Cruce cruce = new Cruce();
        MachoDAO m = new MachoDAO();
//        ConejaDAO c = new ConejaDAO();
        cruce.setId_cruce(rs.getInt("id_cruce"));
        cruce.setFecha_cruce(rs.getDate("fecha_cruce"));
        cruce.setMacho(m.obtenerMacho(rs.getInt("id_macho")));
//        cruce.setConeja(c.obtenerConeja(rs.getInt("numero_machos")));
        cruce.setFecha_parto(rs.getDate("fecha_parto"));
        cruce.setObservaciones(rs.getString("observaciones"));
        cruce.setCantidad_paridos(rs.getInt("cantidad_paridos"));
        resultado.add(cruce);
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return resultado;
  }
   public List<Cruce> obtenerCruces(int id_coneja) throws SIGIPROException {

    List<Cruce> resultado = new ArrayList<Cruce>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.cruces where id_coneja=?");
      consulta.setInt(1, id_coneja);
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Cruce cruce = new Cruce();
        MachoDAO m = new MachoDAO();
//        ConejaDAO c = new ConejaDAO();
        cruce.setId_cruce(rs.getInt("id_cruce"));
        cruce.setFecha_cruce(rs.getDate("fecha_cruce"));
        cruce.setMacho(m.obtenerMacho(rs.getInt("id_macho")));
//      cruce.setConeja(c.obtenerConeja(rs.getInt("numero_machos")));
        cruce.setFecha_parto(rs.getDate("fecha_parto"));
        cruce.setObservaciones(rs.getString("observaciones"));
        cruce.setCantidad_paridos(rs.getInt("cantidad_paridos"));
        resultado.add(cruce);
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
