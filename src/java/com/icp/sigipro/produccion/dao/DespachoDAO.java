/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.produccion.modelos.Despacho;
import com.icp.sigipro.produccion.dao.Inventario_PTDAO;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.produccion.modelos.Catalogo_PT;
import com.icp.sigipro.produccion.modelos.Protocolo;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
 *
 * @author Amed
 */
public class DespachoDAO extends DAO {
  
  public DespachoDAO() {
  }

  public int insertarDespacho(Despacho p) throws SIGIPROException {

    int resultado = 0;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.despacho (fecha, destino, estado_coordinador, estado_regente, total)"
              + " VALUES (?,?,?,?,?) RETURNING id_despacho");

      consulta.setDate(1, p.getFecha());
      consulta.setString(2, p.getDestino());
      consulta.setBoolean(3, false);
      consulta.setBoolean(4, false);
      consulta.setInt(5,0);
      
      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = resultadoConsulta.getInt("id_despacho");
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

  public boolean editarDespacho(Despacho p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE produccion.despacho "
              + " SET  destino=?, fecha=?"
              + " WHERE id_despacho=?; "
      );

     consulta.setString(1, p.getDestino());
     consulta.setDate(2, p.getFecha());
     consulta.setInt(3, p.getId_despacho());

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

  public boolean eliminarDespacho(int id_despacho) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM produccion.despacho "
              + " WHERE id_despacho=?; "
      );

      consulta.setInt(1, id_despacho);

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

  public Despacho obtenerDespacho(int id) throws SIGIPROException {

    Despacho despacho = new Despacho();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT d.id_despacho, d.fecha, d.destino, d.estado_coordinador, d.estado_regente, "
              + " d.fecha_coordinador, d.fecha_regente, d.total,"
              + "u.id_usuario AS id_coordinador, u.nombre_usuario AS coordinador, "
              + "s.id_usuario AS id_regente, s.nombre_usuario AS regente "
              + "FROM produccion.despacho d "
              + "LEFT JOIN seguridad.usuarios u ON d.id_coordinador = u.id_usuario "
              + "LEFT JOIN seguridad.usuarios s ON d.id_regente = s.id_usuario "
              + "where d.id_despacho = ?");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        despacho.setId_despacho(rs.getInt("id_despacho"));
        despacho.setTotal(rs.getInt("total"));
        despacho.setFecha(rs.getDate("fecha"));
        despacho.setDestino(rs.getString("destino"));
        despacho.setEstado_regente(rs.getBoolean("estado_regente"));
        despacho.setEstado_coordinador(rs.getBoolean("estado_coordinador"));
        despacho.setFecha_regente(rs.getDate("fecha_regente"));
        despacho.setFecha_coordinador(rs.getDate("fecha_coordinador"));
        Usuario c = new Usuario();
        Usuario r = new Usuario();
        c.setId_usuario(rs.getInt("id_coordinador"));
        c.setNombre_usuario(rs.getString("coordinador"));
        r.setId_usuario(rs.getInt("id_regente"));
        r.setNombre_usuario(rs.getString("regente"));
        despacho.setCoordinador(c);
        despacho.setRegente(r);
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return despacho;
  }

  public List<Despacho> obtenerDespachos() throws SIGIPROException {

    List<Despacho> resultado = new ArrayList<Despacho>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement("SELECT d.id_despacho, d.fecha, d.destino, d.estado_coordinador, d.estado_regente, "
              + " d.fecha_coordinador, d.fecha_regente, d.total, "
              + "u.id_usuario AS id_coordinador, u.nombre_usuario AS coordinador, "
              + "s.id_usuario AS id_regente, s.nombre_usuario AS regente "
              + "FROM produccion.despacho d "
              + "LEFT JOIN seguridad.usuarios u ON d.id_coordinador = u.id_usuario "
              + "LEFT JOIN seguridad.usuarios s ON d.id_regente = s.id_usuario "
              );
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Despacho despacho = new Despacho();
        despacho.setId_despacho(rs.getInt("id_despacho"));
        despacho.setTotal(rs.getInt("total"));
        despacho.setFecha(rs.getDate("fecha"));
        despacho.setDestino(rs.getString("destino"));
        despacho.setEstado_regente(rs.getBoolean("estado_regente"));
        despacho.setEstado_coordinador(rs.getBoolean("estado_coordinador"));
        despacho.setFecha_regente(rs.getDate("fecha_regente"));
        despacho.setFecha_coordinador(rs.getDate("fecha_coordinador"));
        Usuario c = new Usuario();
        Usuario r = new Usuario();
        c.setId_usuario(rs.getInt("id_coordinador"));
        c.setNombre_usuario(rs.getString("coordinador"));
        r.setId_usuario(rs.getInt("id_regente"));
        r.setNombre_usuario(rs.getString("regente"));
        despacho.setCoordinador(c);
        despacho.setRegente(r);
        resultado.add(despacho);
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
    public boolean update_total(int id_despacho, int cantidad) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE produccion.despacho "
              + " SET  total= total + ?"
              + " WHERE id_despacho=?; "
      );

     consulta.setInt(1, cantidad);
     consulta.setInt(2, id_despacho);

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
      public boolean reset_total(int id_despacho) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE produccion.despacho "
              + " SET  total=0 "
              + " WHERE id_despacho=?; "
      );

     consulta.setInt(1, id_despacho);

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
    public boolean aprobar_Coordinador(int id_usuario, int id_despacho) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE produccion.despacho "
              + " SET   fecha_coordinador=?, id_coordinador=?, estado_coordinador=true"
              + " WHERE id_despacho=?; "
      );
     Date fechahoy = new java.sql.Date(Calendar.getInstance().getTime().getTime());
     DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
     df.format(fechahoy);
     consulta.setDate(1, fechahoy);
     consulta.setInt(2, id_usuario);
     consulta.setInt(3, id_despacho);
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
   public boolean aprobar_Regente(int id_usuario, int id_despacho) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE produccion.despacho "
              + " SET   fecha_regente=?, id_regente=?, estado_coordinador=true"
              + " WHERE id_despacho=?; "
      );
     Date fechahoy = new java.sql.Date(Calendar.getInstance().getTime().getTime());
     DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
     df.format(fechahoy);
     consulta.setDate(1, fechahoy);
     consulta.setInt(2, id_usuario);
     consulta.setInt(3, id_despacho);
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
