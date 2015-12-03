/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.produccion.modelos.Salida_Ext;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Amed
 */
public class Salida_ExtDAO extends DAO {
  
  public Salida_ExtDAO() {
  }

  public int insertarSalida_Ext(Salida_Ext p) throws SIGIPROException {

    int resultado = 0;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.salida_ext (fecha, tipo, observaciones, total)"
              + " VALUES (?,?,?,?) RETURNING id_salida");

      consulta.setDate(1, p.getFecha());
      consulta.setString(2, p.getTipo());
      consulta.setString(3, p.getObservaciones());
      consulta.setInt(4,0);
      
      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = resultadoConsulta.getInt("id_salida");
      }
      resultadoConsulta.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      ex.printStackTrace();
      String mensaje = ex.getMessage();
        throw new SIGIPROException("Se produjo un error al procesar el ingreso");
    }
    return resultado;
  }

  public boolean editarSalida_Ext(Salida_Ext p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE produccion.salida_ext "
              + " SET  fecha=?, tipo=?, observaciones=?"
              + " WHERE id_salida=?; "
      );

     consulta.setString(3, p.getObservaciones());
     consulta.setString(2, p.getTipo());
     consulta.setDate(1, p.getFecha());
     consulta.setInt(4, p.getId_salida());

      if (consulta.executeUpdate() == 1) {
        resultado = true;
      }
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      ex.printStackTrace();
      String mensaje = ex.getMessage();
      throw new SIGIPROException("Se produjo un error al procesar la edición");
      
    }
    return resultado;
  }

  public boolean eliminarSalida_Ext(int id_salida) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM produccion.salida_ext "
              + " WHERE id_salida=?; "
      );

      consulta.setInt(1, id_salida);

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

  public Salida_Ext obtenerSalida_Ext(int id) throws SIGIPROException {

    Salida_Ext salida_ext = new Salida_Ext();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT d.id_salida, d.tipo, d.fecha, d.observaciones, d.total "
              + "FROM produccion.salida_ext d "
              + "where d.id_salida = ?");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        salida_ext.setId_salida(rs.getInt("id_salida"));
        salida_ext.setTotal(rs.getInt("total"));
        salida_ext.setFecha(rs.getDate("fecha"));
        salida_ext.setObservaciones(rs.getString("observaciones"));
        salida_ext.setTipo(rs.getString("tipo"));
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return salida_ext;
  }

  public List<Salida_Ext> obtenerSalida_Exts() throws SIGIPROException {

    List<Salida_Ext> resultado = new ArrayList<Salida_Ext>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement("SELECT d.id_salida, d.fecha, d.tipo, d.observaciones, d.total "
              + "FROM produccion.salida_ext d "
              );
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Salida_Ext salida_ext = new Salida_Ext();
        salida_ext.setId_salida(rs.getInt("id_salida"));
        salida_ext.setTotal(rs.getInt("total"));
        salida_ext.setFecha(rs.getDate("fecha"));
        salida_ext.setObservaciones(rs.getString("observaciones"));
        salida_ext.setTipo(rs.getString("tipo"));
        resultado.add(salida_ext);
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
 public boolean reset_total(int id_salida) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE produccion.salida_ext "
              + " SET  total=0 "
              + " WHERE id_salida=?; "
      );

     consulta.setInt(1, id_salida);

      if (consulta.executeUpdate() == 1) {
        resultado = true;
      }
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      ex.printStackTrace();
      String mensaje = ex.getMessage();
      throw new SIGIPROException("Se produjo un error al procesar la edición");
      
    }
    return resultado;
  }
}
