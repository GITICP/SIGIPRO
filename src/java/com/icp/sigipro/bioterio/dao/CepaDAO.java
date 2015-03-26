/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;
import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bioterio.modelos.Cepa;
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
public class CepaDAO {
  private Connection conexion;
  public CepaDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }
  public boolean insertarCepa(Cepa p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.catalogo_cepas (nombre, macho_as, hembra_as,fecha_apareamiento_i,fecha_apareamiento_f,"
              + "fecha_eliminacionmacho_i,fecha_eliminacionmacho_f,fecha_eliminacionhembra_i,fecha_eliminacionhembra_f,fecha_seleccionnuevos_i,fecha_seleccionnuevos_f,"
              + "fecha_reposicionciclo_i,fecha_reposicionciclo_f,fecha_vigencia) "
              + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id_cepa");
      

      consulta.setString(1, p.getNombre());
      consulta.setString(2, p.getMacho_as());
      consulta.setString(3, p.getHembra_as());
      consulta.setDate(4, p.getFecha_apareamiento_i());
      consulta.setDate(5, p.getFecha_apareamiento_f());
      consulta.setDate(6, p.getFecha_eliminacionmacho_i());
      consulta.setDate(7, p.getFecha_eliminacionmacho_f());
      consulta.setDate(8, p.getFecha_eliminacionhembra_i());
      consulta.setDate(9, p.getFecha_eliminacionhembra_f());
      consulta.setDate(10, p.getFecha_seleccionnuevos_i());
      consulta.setDate(11, p.getFecha_seleccionnuevos_f());
      consulta.setDate(12, p.getFecha_reposicionciclo_i());
      consulta.setDate(13, p.getFecha_reposicionciclo_f());
      consulta.setDate(14, p.getFecha_vigencia());

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

  public boolean editarCepa(Cepa p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bioterio.catalogo_cepas "
              + " SET  nombre=?, macho_as=?, hembra_as=?,fecha_apareamiento_i=?,fecha_apareamiento_f=?,"
              + "fecha_eliminacionmacho_i=?,fecha_eliminacionmacho_f=?,fecha_eliminacionhembra_i=?,fecha_eliminacionhembra_f=?,fecha_seleccionnuevos_i=?,fecha_seleccionnuevos_f=?,"
              + "fecha_reposicionciclo_i=?,fecha_reposicionciclo_f=?,fecha_vigencia=? "
              + " WHERE id_cepa=?; "
      );

      consulta.setString(1, p.getNombre());
      consulta.setString(2, p.getMacho_as());
      consulta.setString(3, p.getHembra_as());
      consulta.setDate(4, p.getFecha_apareamiento_i());
      consulta.setDate(5, p.getFecha_apareamiento_f());
      consulta.setDate(6, p.getFecha_eliminacionmacho_i());
      consulta.setDate(7, p.getFecha_eliminacionmacho_f());
      consulta.setDate(8, p.getFecha_eliminacionhembra_i());
      consulta.setDate(9, p.getFecha_eliminacionhembra_f());
      consulta.setDate(10, p.getFecha_seleccionnuevos_i());
      consulta.setDate(11, p.getFecha_seleccionnuevos_f());
      consulta.setDate(12, p.getFecha_reposicionciclo_i());
      consulta.setDate(13, p.getFecha_reposicionciclo_f());
      consulta.setDate(14, p.getFecha_vigencia());

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

  public boolean eliminarCepa(int id_cepa) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bioterio.catalogo_cepas "
              + " WHERE id_cepa=?; "
      );

      consulta.setInt(1, id_cepa);

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

  public Cepa obtenerCepa(int id) throws SIGIPROException {

    Cepa cepa = new Cepa();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.catalogo_cepas where id_cepa = ?");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        cepa.setId_cepa(rs.getInt("id_cepa"));
        cepa.setNombre(rs.getString("nombre"));
        cepa.setMacho_as(rs.getString("macho_as"));
        cepa.setHembra_as(rs.getString("hembra_as"));
        cepa.setFecha_apareamiento_i(rs.getDate("fecha_apareamiento_i"));
        cepa.setFecha_apareamiento_f(rs.getDate("fecha_apareamiento_f"));
        cepa.setFecha_eliminacionmacho_i(rs.getDate("fecha_eliminacionmacho_i"));
        cepa.setFecha_eliminacionmacho_f(rs.getDate("fecha_eliminacionmacho_f"));
        cepa.setFecha_eliminacionhembra_i(rs.getDate("fecha_eliminacionhembra_i"));
        cepa.setFecha_eliminacionhembra_f(rs.getDate("fecha_eliminacionhembra_f"));
        cepa.setFecha_seleccionnuevos_i(rs.getDate("fecha_seleccionnuevos_i"));
        cepa.setFecha_seleccionnuevos_f(rs.getDate("fecha_seleccionnuevos_f"));
        cepa.setFecha_reposicionciclo_i(rs.getDate("fecha_reposicionciclo_i"));
        cepa.setFecha_reposicionciclo_f(rs.getDate("fecha_reposicionciclo_f"));
        cepa.setFecha_vigencia(rs.getDate("fecha_vigencia"));
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return cepa;
  }

  public List<Cepa> obtenerCepas() throws SIGIPROException {

    List<Cepa> resultado = new ArrayList<Cepa>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.catalogo_cepas");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Cepa cepa = new Cepa();
        cepa.setId_cepa(rs.getInt("id_cepa"));
        cepa.setNombre(rs.getString("nombre"));
        cepa.setMacho_as(rs.getString("macho_as"));
        cepa.setHembra_as(rs.getString("hembra_as"));
        cepa.setFecha_apareamiento_i(rs.getDate("fecha_apareamiento_i"));
        cepa.setFecha_apareamiento_f(rs.getDate("fecha_apareamiento_f"));
        cepa.setFecha_eliminacionmacho_i(rs.getDate("fecha_eliminacionmacho_i"));
        cepa.setFecha_eliminacionmacho_f(rs.getDate("fecha_eliminacionmacho_f"));
        cepa.setFecha_eliminacionhembra_i(rs.getDate("fecha_eliminacionhembra_i"));
        cepa.setFecha_eliminacionhembra_f(rs.getDate("fecha_eliminacionhembra_f"));
        cepa.setFecha_seleccionnuevos_i(rs.getDate("fecha_seleccionnuevos_i"));
        cepa.setFecha_seleccionnuevos_f(rs.getDate("fecha_seleccionnuevos_f"));
        cepa.setFecha_reposicionciclo_i(rs.getDate("fecha_reposicionciclo_i"));
        cepa.setFecha_reposicionciclo_f(rs.getDate("fecha_reposicionciclo_f"));
        cepa.setFecha_vigencia(rs.getDate("fecha_vigencia"));     
        resultado.add(cepa);
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
