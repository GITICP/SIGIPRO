/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.bodegas.modelos.Inventario;
import com.icp.sigipro.configuracion.dao.SeccionDAO;
import com.icp.sigipro.core.DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class InventarioDAO extends DAO<Inventario>{

  public InventarioDAO() {
    super(Inventario.class, "bodega", "inventarios");
  }

  public boolean restarInventario(int id, int cant) {
    boolean resultado = false;
    try {
      PreparedStatement consultaActual = getConexion().prepareStatement("SELECT sotck_actual FROM bodega.inventarios where id_inventario = ?");
      consultaActual.setInt(1, id);
      ResultSet rs = consultaActual.executeQuery();
      int StockActual = 0;
      if (rs.next()) {
        StockActual = rs.getInt("stock_actual");
      }
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bodega.inventarios "
              + " SET stock_actual= ?"
              + " WHERE id_inventario=?; "
      );
      
      consulta.setInt(1, (StockActual - cant));
      consulta.setInt(2, id);
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

  public Inventario obtenerInventario(int id_inventario) {

    Inventario inventario = new Inventario();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bodega.inventarios where id_inventario = ?");

      consulta.setInt(1, id_inventario);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        inventario.setId_inventario(rs.getInt("id_inventario"));
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
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return inventario;
  }

  public List<Inventario> obtenerInventarios() {

    List<Inventario> resultado = new ArrayList<Inventario>();

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM bodega.inventarios ");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Inventario inventario = new Inventario();
        inventario.setId_inventario(rs.getInt("id_inventario"));
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

  @Override
  public Inventario buscar(int id)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<Inventario> buscarPor(String[] campos, Object valor)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean actualizar(Inventario param)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean eliminar(Inventario param)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}