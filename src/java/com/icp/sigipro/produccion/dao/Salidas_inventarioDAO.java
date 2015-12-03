/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.produccion.modelos.Catalogo_PT;
import com.icp.sigipro.produccion.modelos.Salidas_inventario;
import com.icp.sigipro.produccion.modelos.Inventario_PT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class Salidas_inventarioDAO extends DAO {
  
  public boolean insertarSalidas_inventario(ArrayList<int[]> lotes, int id_d) throws SIGIPROException {

    boolean resultado = false;
    
    for (int i=0; i<lotes.size(); i++){
    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.salidas_inventario (id_salida, id_inventario_pt, cantidad)"
              + " VALUES (?,?,?) RETURNING id_salida");

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

  public boolean eliminarSalidas_inventario(int id_salida) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM produccion.salidas_inventario "
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
  
  public List<Salidas_inventario> obtenerSalidas_inventarios(int id_salida) throws SIGIPROException {

    List<Salidas_inventario> resultado = new ArrayList<Salidas_inventario>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement("SELECT d.id_sxi, d.id_salida, d.id_inventario_pt, d.cantidad, "
              + "i.lote, c.nombre "
              + "FROM produccion.salidas_inventario d "
              + "INNER JOIN produccion.inventario_pt i ON d.id_inventario_pt = i.id_inventario_pt "
              + "INNER JOIN produccion.catalogo_pt c ON c.id_catalogo_pt = i.id_catalogo_pt "
              + "WHERE d.id_salida = ?"
              );
      consulta.setInt(1, id_salida);
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Salidas_inventario salida = new Salidas_inventario();
        salida.setId_salida(rs.getInt("id_salida"));
        salida.setId_inventario_pt(rs.getInt("id_inventario_pt"));
        salida.setId_sxi(rs.getInt("id_sxi"));
        salida.setCantidad(rs.getInt("cantidad"));
        Inventario_PT i = new Inventario_PT();
        Catalogo_PT c = new Catalogo_PT();
        c.setNombre(rs.getString("nombre"));
        i.setLote(rs.getString("lote"));
        i.setProducto(c);
        salida.setInventario(i);
        resultado.add(salida);
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
