/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;
import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bioterio.modelos.Macho;
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
public class MachoDAO {
  private Connection conexion;
  public MachoDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }
  public boolean insertarMacho(Macho p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.machos (identificacion, fecha_ingreso, descripcion, fecha_retiro)"  
              + " VALUES (?,?,?,?) RETURNING id_macho");
      
      consulta.setDate(2, p.getFecha_ingreso());
      consulta.setString(3, p.getDescripcion());
      consulta.setDate(4, p.getFecha_retiro());
      consulta.setString(1, p.getIdentificacion());
      
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

  public boolean editarMacho(Macho p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bioterio.machos "
              + " SET  identificacion=?, fecha_ingreso=?, descripcion=?, fecha_retiro=?"
              + " WHERE id_macho=?; "
      );
      consulta.setString(1, p.getIdentificacion());
      consulta.setDate(2, p.getFecha_ingreso());
      consulta.setString(3, p.getDescripcion());
      consulta.setDate(4, p.getFecha_retiro());
      consulta.setInt(5, p.getId_macho());
      
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

  public boolean eliminarMacho(int id_macho) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bioterio.machos "
              + " WHERE id_macho=?; "
      );

      consulta.setInt(1, id_macho);

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

  public Macho obtenerMacho(int id) throws SIGIPROException {

    Macho macho = new Macho();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.machos where id_macho = ?");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        macho.setId_macho(rs.getInt("id_macho"));
        macho.setIdentificacion(rs.getString("identificacion"));
        macho.setFecha_ingreso(rs.getDate("fecha_ingreso"));
        macho.setDescripcion(rs.getString("descripcion"));
        macho.setFecha_retiro(rs.getDate("fecha_retiro"));
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return macho;
  }

  public List<Macho> obtenerMachos() throws SIGIPROException {

    List<Macho> resultado = new ArrayList<Macho>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.machos");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Macho macho = new Macho();
        macho.setId_macho(rs.getInt("id_macho"));
        macho.setIdentificacion(rs.getString("identificacion"));
        macho.setFecha_ingreso(rs.getDate("fecha_ingreso"));
        macho.setDescripcion(rs.getString("descripcion"));
        macho.setFecha_retiro(rs.getDate("fecha_retiro"));  
        resultado.add(macho);
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
