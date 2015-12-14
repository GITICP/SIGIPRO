/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.produccion.modelos.Reservacion;
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
public class ReservacionDAO extends DAO {
  
  public ReservacionDAO() {
  }

  public int insertarReservacion(Reservacion p) throws SIGIPROException {

    int resultado = 0;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.reservacion (hasta, observaciones, total)"
              + " VALUES (?,?,?) RETURNING id_reservacion");

      consulta.setDate(1, p.getHasta());
      consulta.setString(2, p.getObservaciones());
      consulta.setInt(3,0);
      
      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = resultadoConsulta.getInt("id_reservacion");
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

  public boolean editarReservacion(Reservacion p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE produccion.reservacion "
              + " SET  hasta=?, observaciones=?"
              + " WHERE id_reservacion=?; "
      );

     consulta.setString(2, p.getObservaciones());
     consulta.setDate(1, p.getHasta());
     consulta.setInt(3, p.getId_reservacion());

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

  public boolean eliminarReservacion(int id_reservacion) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM produccion.reservacion "
              + " WHERE id_reservacion=?; "
      );

      consulta.setInt(1, id_reservacion);

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

  public Reservacion obtenerReservacion(int id) throws SIGIPROException {

    Reservacion reservacion = new Reservacion();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT d.id_reservacion, d.hasta, d.observaciones, d.total "
              + "FROM produccion.reservacion d "
              + "where d.id_reservacion = ?");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        reservacion.setId_reservacion(rs.getInt("id_reservacion"));
        reservacion.setTotal(rs.getInt("total"));
        reservacion.setHasta(rs.getDate("hasta"));
        reservacion.setObservaciones(rs.getString("observaciones"));
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return reservacion;
  }

  public List<Reservacion> obtenerReservaciones() throws SIGIPROException {

    List<Reservacion> resultado = new ArrayList<Reservacion>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement("SELECT d.id_reservacion, d.hasta, d.observaciones, d.total "
              + "FROM produccion.reservacion d "
              );
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Reservacion reservacion = new Reservacion();
        reservacion.setId_reservacion(rs.getInt("id_reservacion"));
        reservacion.setTotal(rs.getInt("total"));
        reservacion.setHasta(rs.getDate("hasta"));
        reservacion.setObservaciones(rs.getString("observaciones"));
        resultado.add(reservacion);
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
 public boolean reset_total(int id_reservacion) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE produccion.reservacion "
              + " SET  total=0 "
              + " WHERE id_reservacion=?; "
      );

     consulta.setInt(1, id_reservacion);

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
