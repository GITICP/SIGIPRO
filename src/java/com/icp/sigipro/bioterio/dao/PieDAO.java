/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bioterio.modelos.Pie;
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
public class PieDAO {
  private Connection conexion;
  public PieDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }
  public boolean insertarPie(Pie p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.pie_de_cria (codigo, fuente, fecha_ingreso,fecha_retiro)"  
              + " VALUES (?,?,?,?) RETURNING id_pie");
      
      consulta.setString(1, p.getCodigo());
      consulta.setDate(3, p.getFecha_ingreso());
      consulta.setString(2, p.getFuente());
      consulta.setDate(4, p.getFecha_retiro());
      
      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
      }
      resultadoConsulta.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
        ex.printStackTrace();
      throw new SIGIPROException("Se produjo un error al procesar el ingreso");
    }
    return resultado;
  }

  public boolean editarPie(Pie p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bioterio.pie_de_cria "
              + " SET  codigo=?, fuente=?, fecha_ingreso=?, fecha_retiro=?"
              + " WHERE id_pie=?; "
      );
      consulta.setString(1, p.getCodigo());
      consulta.setDate(3, p.getFecha_ingreso());
      consulta.setString(2, p.getFuente());
      consulta.setDate(4, p.getFecha_retiro());
      consulta.setInt(5, p.getId_pie());
      
      if (consulta.executeUpdate() == 1) {
        resultado = true;
      }
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
        ex.printStackTrace();
      throw new SIGIPROException("Se produjo un error al procesar la edición");
    }
    return resultado;
  }

  public boolean eliminarPie(int id_pie) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bioterio.pie_de_cria "
              + " WHERE id_pie=?; "
      );

      consulta.setInt(1, id_pie);

      if (consulta.executeUpdate() == 1) {
        resultado = true;
      }
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
        ex.printStackTrace();
      throw new SIGIPROException("Se produjo un error al procesar la eliminación");
    }
    return resultado;
  }

  public Pie obtenerPie(int id) throws SIGIPROException {

    Pie pie = new Pie();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.pie_de_cria where id_pie = ?");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        pie.setId_pie(rs.getInt("id_pie"));
        pie.setCodigo(rs.getString("codigo"));
        pie.setFecha_ingreso(rs.getDate("fecha_ingreso"));
        pie.setFuente(rs.getString("fuente"));
        pie.setFecha_retiro(rs.getDate("fecha_retiro"));
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
        ex.printStackTrace();
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return pie;
  }

  public List<Pie> obtenerPies() throws SIGIPROException {

    List<Pie> resultado = new ArrayList<Pie>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.pie_de_cria");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Pie pie = new Pie();
        pie.setId_pie(rs.getInt("id_pie"));
        pie.setCodigo(rs.getString("codigo"));
        pie.setFecha_ingreso(rs.getDate("fecha_ingreso"));
        pie.setFuente(rs.getString("fuente"));
        pie.setFecha_retiro(rs.getDate("fecha_retiro"));
        resultado.add(pie);
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
        ex.printStackTrace();
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return resultado;
  }
  public List<Pie> obtenerPiesRestantes() throws SIGIPROException {

    List<Pie> resultado = new ArrayList<Pie>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.pie_de_cria p WHERE p.id_pie NOT IN (SELECT id_pie FROM bioterio.piexcepa)");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Pie pie = new Pie();
        pie.setId_pie(rs.getInt("id_pie"));
        pie.setCodigo(rs.getString("codigo"));
        pie.setFecha_ingreso(rs.getDate("fecha_ingreso"));
        pie.setFuente(rs.getString("fuente"));
        pie.setFecha_retiro(rs.getDate("fecha_retiro"));
        resultado.add(pie);
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
        ex.printStackTrace();
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
          ex.printStackTrace();
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
          ex.printStackTrace();
        conexion = null;
      }
    }
  }
}
