/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.produccion.modelos.Catalogo_PT;
import com.icp.sigipro.produccion.modelos.Despachos_inventario;
import com.icp.sigipro.produccion.modelos.Inventario_PT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class Despachos_inventarioDAO extends DAO {
  
  public boolean insertarDespachos_inventario(int id_d, int id_i, int cantidad) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.despachos_inventario (id_despacho, id_inventario_pt, cantidad)"
              + " VALUES (?,?,?) RETURNING id_despacho");

      consulta.setInt(1, id_d);
      consulta.setInt(2, id_i);
      consulta.setInt(3, cantidad);
    
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

  public boolean eliminarDespachos_inventario(int id_despacho) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM produccion.despachos_inventario "
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
  
  public List<Despachos_inventario> obtenerDespachos_inventarios(int id_despacho) throws SIGIPROException {

    List<Despachos_inventario> resultado = new ArrayList<Despachos_inventario>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement("SELECT d.id_dxi, d.id_despacho, d.id_inventario_pt, d.cantidad, "
              + "i.lote, c.nombre "
              + "FROM produccion.despachos_inventario d "
              + "INNER JOIN produccion.inventario_pt i ON d.id_inventario_pt = i.id_inventario_pt "
              + "INNER JOIN produccion.catalogo_pt c ON c.id_catalogo_pt = i.id_catalogo_pt "
              + "WHERE d.id_despacho = ?"
              );
      consulta.setInt(1, id_despacho);
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Despachos_inventario despacho = new Despachos_inventario();
        despacho.setId_despacho(rs.getInt("id_despacho"));
        despacho.setId_inventario_pt(rs.getInt("id_inventario_pt"));
        despacho.setId_dxi(rs.getInt("id_dxi"));
        despacho.setCantidad(rs.getInt("cantidad"));
        Inventario_PT i = new Inventario_PT();
        Catalogo_PT c = new Catalogo_PT();
        c.setNombre(rs.getString("nombre"));
        i.setLote(rs.getString("lote"));
        i.setProducto(c);
        despacho.setInventario(i);
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
}
