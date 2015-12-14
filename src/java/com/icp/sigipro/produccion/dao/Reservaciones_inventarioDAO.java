/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.produccion.modelos.Catalogo_PT;
import com.icp.sigipro.produccion.modelos.Reservaciones_inventario;
import com.icp.sigipro.produccion.modelos.Inventario_PT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class Reservaciones_inventarioDAO extends DAO {
  
  public boolean insertarReservaciones_inventario(ArrayList<int[]> lotes, int id_d) throws SIGIPROException {

    boolean resultado = false;
    
    for (int i=0; i<lotes.size(); i++){
    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.reservaciones_inventario (id_reservacion, id_inventario_pt, cantidad)"
              + " VALUES (?,?,?) RETURNING id_reservacion");

      consulta.setInt(1, id_d);
      consulta.setInt(2, lotes.get(i)[0]);
      consulta.setInt(3, lotes.get(i)[1]);
    
      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
      }
      else{
        resultado = false;
      }
      resultadoConsulta.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      ex.printStackTrace();
      String mensaje = ex.getMessage();
        throw new SIGIPROException("Se produjo un error al procesar el ingreso");
    }
    }
    
    return resultado;
  }

  public boolean eliminarReservaciones_inventario(int id_reservacion) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM produccion.reservaciones_inventario "
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
  
  public List<Reservaciones_inventario> obtenerReservaciones_inventarios(int id_reservacion) throws SIGIPROException {

    List<Reservaciones_inventario> resultado = new ArrayList<Reservaciones_inventario>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement("SELECT d.id_rxi, d.id_reservacion, d.id_inventario_pt, d.cantidad, "
              + "i.lote, c.nombre "
              + "FROM produccion.reservaciones_inventario d "
              + "INNER JOIN produccion.inventario_pt i ON d.id_inventario_pt = i.id_inventario_pt "
              + "INNER JOIN produccion.catalogo_pt c ON c.id_catalogo_pt = i.id_catalogo_pt "
              + "WHERE d.id_reservacion = ?"
              );
      consulta.setInt(1, id_reservacion);
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Reservaciones_inventario reservacion = new Reservaciones_inventario();
        reservacion.setId_reservacion(rs.getInt("id_reservacion"));
        reservacion.setId_inventario_pt(rs.getInt("id_inventario_pt"));
        reservacion.setId_rxi(rs.getInt("id_rxi"));
        reservacion.setCantidad(rs.getInt("cantidad"));
        Inventario_PT i = new Inventario_PT();
        Catalogo_PT c = new Catalogo_PT();
        c.setNombre(rs.getString("nombre"));
        i.setLote(rs.getString("lote"));
        i.setProducto(c);
        reservacion.setInventario(i);
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
}
