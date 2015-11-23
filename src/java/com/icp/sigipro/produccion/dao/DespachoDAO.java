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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Amed
 */
public class DespachoDAO extends DAO {
  
  public DespachoDAO() {
  }

  public boolean insertarDespacho(Despacho p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.despacho (fecha, destino, estado_coordinador, estado_regente)"
              + " VALUES (?,?,?,?) RETURNING id_despacho");

      consulta.setDate(1, p.getFecha());
      consulta.setString(2, p.getDestino());
      consulta.setBoolean(3, false);
      consulta.setBoolean(4, false);
      
      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
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
     consulta.setDate(3, p.getFecha());

     Inventario_PTDAO inv = new Inventario_PTDAO();
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
      PreparedStatement consulta = getConexion().prepareStatement("SELECT pt.id_despacho, pt.lote, pt.fecha_vencimiento, pt.cantidad, pt.cantidad_disponible, p.id_protocolo, "
              + "p.nombre AS nombre_protocolo, c.id_catalogo_pt, c.nombre "
              + "FROM produccion.despacho pt, produccion.catalogo_pt c, produccion.protocolo p "
              + "where pt.id_despacho = ? AND pt.id_protocolo = p.id_protocolo AND pt.id_catalogo_pt = c.id_catalogo_pt");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        despacho.setId_despacho(rs.getInt("id_despacho"));
        despacho.setLote(rs.getString("lote"));
        despacho.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
        despacho.setCantidad(rs.getInt("cantidad"));
        despacho.setCantidad_disponible(rs.getInt("cantidad_disponible"));
        Protocolo p = new Protocolo();
        p.setId_protocolo(rs.getInt("id_protocolo"));
        p.setNombre(rs.getString("nombre_protocolo"));
        Catalogo_PT c = new Catalogo_PT();
        c.setId_catalogo_pt(rs.getInt("id_catalogo_pt"));
        c.setNombre(rs.getString("nombre"));
        despacho.setProtocolo(p);
        despacho.setProducto(c);
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
      consulta = getConexion().prepareStatement(" SELECT pt.id_despacho, pt.lote, pt.fecha_vencimiento, pt.cantidad, pt.cantidad_disponible, p.id_protocolo, "
              + "p.nombre AS nombre_protocolo, c.id_catalogo_pt, c.nombre FROM produccion.despacho pt Inner Join produccion.protocolo p ON p.id_protocolo = pt.id_protocolo "
              + "INNER JOIN produccion.catalogo_pt c on c.id_catalogo_pt = p.id_catalogo_pt ");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Despacho despacho = new Despacho();
        despacho.setId_despacho(rs.getInt("id_despacho"));
        despacho.setLote(rs.getString("lote"));
        despacho.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
        despacho.setCantidad(rs.getInt("cantidad"));
        despacho.setCantidad_disponible(rs.getInt("cantidad_disponible"));
        Protocolo p = new Protocolo();
        p.setId_protocolo(rs.getInt("id_protocolo"));
        p.setNombre(rs.getString("nombre_protocolo"));
        Catalogo_PT c = new Catalogo_PT();
        c.setId_catalogo_pt(rs.getInt("id_catalogo_pt"));
        c.setNombre(rs.getString("nombre"));
        despacho.setProtocolo(p);
        despacho.setProducto(c);
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
    public boolean update_total(Despacho p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE produccion.despacho "
              + " SET  lote=?, cantidad=?, fecha_vencimiento=?, id_protocolo=?, id_catalogo_pt=?, cantidad_disponible=?"
              + " WHERE id_despacho=?; "
      );

     consulta.setString(1, p.getLote());
     consulta.setInt(2, p.getCantidad());
     consulta.setDate(3, p.getFecha_vencimiento());
     consulta.setInt(4, p.getProtocolo().getId_protocolo());
     consulta.setInt(5, p.getProducto().getId_catalogo_pt());
     consulta.setInt(6, p.getCantidad());
     consulta.setInt(7, p.getId_despacho());
     Inventario_PTDAO inv = new Inventario_PTDAO();
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
    public boolean aprobar(String tipo, int id_usuario) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE produccion.despacho "
              + " SET  lote=?, cantidad=?, fecha_vencimiento=?, id_protocolo=?, id_catalogo_pt=?, cantidad_disponible=?"
              + " WHERE id_despacho=?; "
      );

     consulta.setString(1, p.getLote());
     consulta.setInt(2, p.getCantidad());
     consulta.setDate(3, p.getFecha_vencimiento());
     consulta.setInt(4, p.getProtocolo().getId_protocolo());
     consulta.setInt(5, p.getProducto().getId_catalogo_pt());
     consulta.setInt(6, p.getCantidad());
     consulta.setInt(7, p.getId_despacho());
     Inventario_PTDAO inv = new Inventario_PTDAO();
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
