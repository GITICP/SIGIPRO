/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.produccion.modelos.Inventario_PT;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.produccion.modelos.Catalogo_PT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Amed
 */
public class Inventario_PTDAO extends DAO {
  
  public Inventario_PTDAO() {
  }

  public boolean insertarInventario_PT(Inventario_PT p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.inventario_pt (lote, cantidad, fecha_vencimiento, id_catalogo_pt, cantidad_disponible, reservado)"
              + " VALUES (?,?,?,?,?,?) RETURNING id_inventario_pt");

      consulta.setString(1, p.getLote());
      consulta.setInt(2, p.getCantidad());
      consulta.setDate(3, p.getFecha_vencimiento());
      consulta.setInt(4, p.getProducto().getId_catalogo_pt());
      consulta.setInt(5, p.getCantidad());
      consulta.setInt(6, 0);
      
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

  public boolean editarInventario_PT(Inventario_PT p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE produccion.inventario_pt "
              + " SET  lote=?, cantidad=?, fecha_vencimiento=?, id_catalogo_pt=?, cantidad_disponible=?"
              + " WHERE id_inventario_pt=?; "
      );

     consulta.setString(1, p.getLote());
     consulta.setInt(2, p.getCantidad());
     consulta.setDate(3, p.getFecha_vencimiento());
     consulta.setInt(4, p.getProducto().getId_catalogo_pt());
     consulta.setInt(5, p.getCantidad());
     consulta.setInt(6, p.getId_inventario_pt());
     
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

  public boolean eliminarInventario_PT(int id_inventario_pt) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM produccion.inventario_pt "
              + " WHERE id_inventario_pt=?; "
      );

      consulta.setInt(1, id_inventario_pt);

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

  public Inventario_PT obtenerInventario_PT(int id) throws SIGIPROException {

    Inventario_PT inventario_pt = new Inventario_PT();

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              "SELECT pt.id_inventario_pt, pt.lote, pt.fecha_vencimiento, pt.cantidad, pt.reservado, pt.cantidad_disponible, "
              + "c.id_catalogo_pt, c.nombre "
              + "FROM produccion.inventario_pt pt, produccion.catalogo_pt c "
              + "where pt.id_inventario_pt = ? AND pt.id_catalogo_pt = c.id_catalogo_pt ");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        inventario_pt.setId_inventario_pt(rs.getInt("id_inventario_pt"));
        inventario_pt.setLote(rs.getString("lote"));
        inventario_pt.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
        inventario_pt.setCantidad(rs.getInt("cantidad"));
        inventario_pt.setReservado(rs.getInt("reservado"));
        inventario_pt.setCantidad_disponible(rs.getInt("cantidad_disponible"));
        Catalogo_PT c = new Catalogo_PT();
        c.setId_catalogo_pt(rs.getInt("id_catalogo_pt"));
        c.setNombre(rs.getString("nombre"));
        inventario_pt.setProducto(c);
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return inventario_pt;
  }

  public List<Inventario_PT> obtenerInventario_PTs() throws SIGIPROException {

    List<Inventario_PT> resultado = new ArrayList<Inventario_PT>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT pt.id_inventario_pt, pt.lote, pt.reservado, pt.fecha_vencimiento, pt.cantidad, pt.cantidad_disponible, "
              + " c.id_catalogo_pt, c.nombre "
              + "FROM produccion.inventario_pt pt  "
              + "INNER JOIN produccion.catalogo_pt c on c.id_catalogo_pt = pt.id_catalogo_pt "
              + "WHERE NOT (pt.cantidad_disponible = 0 AND pt.id_inventario_pt NOT IN (SELECT id_inventario_pt FROM produccion.reservaciones_inventario))");
 
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Inventario_PT inventario_pt = new Inventario_PT();
        inventario_pt.setId_inventario_pt(rs.getInt("id_inventario_pt"));
        inventario_pt.setLote(rs.getString("lote"));
        inventario_pt.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
        inventario_pt.setCantidad(rs.getInt("cantidad"));
        inventario_pt.setReservado(rs.getInt("reservado"));
        inventario_pt.setCantidad_disponible(rs.getInt("cantidad_disponible"));
        Catalogo_PT c = new Catalogo_PT();
        c.setId_catalogo_pt(rs.getInt("id_catalogo_pt"));
        c.setNombre(rs.getString("nombre"));
        inventario_pt.setProducto(c);
        resultado.add(inventario_pt);
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
  public List<Inventario_PT> obtenerInventario_PTs_H() throws SIGIPROException {

    List<Inventario_PT> resultado = new ArrayList<Inventario_PT>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT pt.id_inventario_pt, pt.lote, pt.reservado, pt.fecha_vencimiento, pt.cantidad, pt.cantidad_disponible, "
              + "c.id_catalogo_pt, c.nombre "
              + "FROM produccion.inventario_pt pt "
              + "INNER JOIN produccion.catalogo_pt c on c.id_catalogo_pt = pt.id_catalogo_pt "
              + "WHERE pt.cantidad_disponible = 0 AND pt.id_inventario_pt NOT IN (SELECT id_inventario_pt FROM produccion.reservaciones_inventario)");
 
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Inventario_PT inventario_pt = new Inventario_PT();
        inventario_pt.setId_inventario_pt(rs.getInt("id_inventario_pt"));
        inventario_pt.setLote(rs.getString("lote"));
        inventario_pt.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
        inventario_pt.setCantidad(rs.getInt("cantidad"));
        inventario_pt.setReservado(rs.getInt("reservado"));
        inventario_pt.setCantidad_disponible(rs.getInt("cantidad_disponible"));
        Catalogo_PT c = new Catalogo_PT();
        c.setId_catalogo_pt(rs.getInt("id_catalogo_pt"));
        c.setNombre(rs.getString("nombre"));
        inventario_pt.setProducto(c);
        resultado.add(inventario_pt);
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
  public boolean restar_cantidad_disponible(int id_inventario, int a_restar) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE produccion.inventario_pt "
              + " SET  cantidad_disponible=cantidad_disponible - ?"
              + " WHERE id_inventario_pt=?; "
      );

     consulta.setInt(1, a_restar);
     consulta.setInt(2, id_inventario);
     
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
