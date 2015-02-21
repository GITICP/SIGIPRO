/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bodegas.modelos.Inventario;
import com.icp.sigipro.configuracion.dao.SeccionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class InventarioDAO {

  private Connection conexion;

  public InventarioDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }

  public boolean restarInventario(int id_producto, int id_seccion, int cant) {
    boolean resultado = false;
    try {

      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bodega.inventarios "
              + " SET stock_actual= stock_actual + ?"
              + " WHERE id_producto  = ? and id_seccion = ?; "
      );
      
      consulta.setInt(1, cant);
      consulta.setInt(2, id_producto);
      consulta.setInt(3, id_seccion);
      int resultadoConsulta = consulta.executeUpdate();
      if (resultadoConsulta == 1) {
        resultado = true;
      }
      consulta.close();
      conexion.close();

    } catch (SQLException ex) {
      System.out.println(ex);
    }

    return resultado;
  }

  public List<Inventario> obtenerInventarios() {

    List<Inventario> resultado = new ArrayList<Inventario>();

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM bodega.inventarios ");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Inventario inventario = new Inventario();
        inventario.setId_producto(rs.getInt("id_producto"));
        inventario.setId_seccion(rs.getInt("id_seccion"));
        inventario.setStock_actual(rs.getInt("stock_actual"));
        try {
          ProductoInternoDAO pr = new ProductoInternoDAO();
          SeccionDAO sc = new SeccionDAO();
          inventario.setProducto(pr.obtenerProductoInterno(rs.getInt("id_producto")));
          inventario.setSeccion(sc.obtenerSeccion(rs.getInt("id_seccion")));
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        resultado.add(inventario);
      }

      consulta.close();
      conexion.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return resultado;
  }
  
  private Connection getConexion() {
    try {

      if (conexion.isClosed()) {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
      }
    } catch (Exception ex) {
      conexion = null;
    }

    return conexion;
  }
}
