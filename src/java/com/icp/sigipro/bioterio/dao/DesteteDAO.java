/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;
import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bioterio.modelos.Destete;
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
public class DesteteDAO {
  private Connection conexion;
  public DesteteDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }
  public boolean insertarDestete(Destete p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.destetes (fecha_destete, numero_hembras, numero_machos)"  
              + " VALUES (?,?,?) RETURNING id_destete");
      
      consulta.setDate(1, p.getFecha_destete());
      consulta.setInt(2, p.getNumero_hembras());
      consulta.setInt(3, p.getNumero_machos());
      
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

  public boolean editarDestete(Destete p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bioterio.destetes "
              + " SET  fecha_destete=?, numero_hembras=?, numero_machos=?"
              + " WHERE id_destete=?; "
      );

      consulta.setDate(1, p.getFecha_destete());
      consulta.setInt(2, p.getNumero_hembras());
      consulta.setInt(3, p.getNumero_machos());
      consulta.setInt(4, p.getId_destete());
      
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

  public boolean eliminarDestete(int id_destete) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bioterio.destetes "
              + " WHERE id_destete=?; "
      );

      consulta.setInt(1, id_destete);

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

  public Destete obtenerDestete(int id) throws SIGIPROException {

    Destete destete = new Destete();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.destetes where id_destete = ?");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        destete.setId_destete(rs.getInt("id_destete"));
        destete.setFecha_destete(rs.getDate("fecha_destete"));
        destete.setNumero_hembras(rs.getInt("numero_hembras"));
        destete.setNumero_machos(rs.getInt("numero_machos"));
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return destete;
  }

  public List<Destete> obtenerDestetes() throws SIGIPROException {

    List<Destete> resultado = new ArrayList<Destete>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.destetes");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Destete destete = new Destete();
        destete.setId_destete(rs.getInt("id_destete"));
        destete.setFecha_destete(rs.getDate("fecha_destete"));
        destete.setNumero_hembras(rs.getInt("numero_hembras"));
        destete.setNumero_machos(rs.getInt("numero_machos"));  
        resultado.add(destete);
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
