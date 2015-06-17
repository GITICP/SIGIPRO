/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.bodegas.modelos.Inventario;
import com.icp.sigipro.bodegas.modelos.ProductoInterno;
import com.icp.sigipro.configuracion.dao.SeccionDAO;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.DAOEspecial;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class InventarioDAO extends DAOEspecial<Inventario> {

  public InventarioDAO() {
    super(Inventario.class,"bodega","inventarios");
  }

  public boolean restarInventario(int id_inventario, int cant) {
    boolean resultado = false;
    try {

      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bodega.inventarios "
              + " SET stock_actual= stock_actual + ?"
              + " WHERE id_inventario  = ?; "
      );
      
      consulta.setInt(1, cant);
      consulta.setInt(2, id_inventario);
      int resultadoConsulta = consulta.executeUpdate();
      if (resultadoConsulta == 1) {
        resultado = true;
      }
      consulta.close();
      cerrarConexion();

    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return resultado;
  }

  public List<Inventario> obtenerInventarios(int id_seccion, int flag_prestamos) {

    List<Inventario> resultado = new ArrayList<Inventario>();

    try {
      PreparedStatement consulta;
      if (flag_prestamos==0)
        { consulta = getConexion().prepareStatement(" SELECT i.stock_actual as stock_actual, ci.codigo_icp as codigo_icp, ci.presentacion as presentacion, i.id_inventario as id_inventario, i.id_producto as id_producto, i.id_seccion as id_seccion, ci.nombre as nombre_producto, s.nombre_seccion as nombre_seccion "
                                                  + " FROM bodega.inventarios i "
                                                  + "           INNER JOIN bodega.catalogo_interno ci on i.id_producto = ci.id_producto "
                                                  + "           INNER JOIN seguridad.secciones s on i.id_seccion = s.id_seccion "
                                                  + " Where i.id_seccion != ? AND i.id_seccion != 0 ");
          }
      else
        {consulta = getConexion().prepareStatement(" SELECT i.stock_actual as stock_actual, ci.codigo_icp as codigo_icp, ci.presentacion as presentacion, i.id_inventario as id_inventario, i.id_producto as id_producto, i.id_seccion as id_seccion, ci.nombre as nombre_producto, s.nombre_seccion as nombre_seccion "
                                                  + " FROM bodega.inventarios i "
                                                  + "           INNER JOIN bodega.catalogo_interno ci on i.id_producto = ci.id_producto "
                                                  + "           INNER JOIN seguridad.secciones s on i.id_seccion = s.id_seccion "
                                                  + " Where i.id_seccion = ? or i.id_seccion = 0;");
         }
      consulta.setInt(1, id_seccion);
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Inventario inventario = new Inventario();
        inventario.setId_inventario(rs.getInt("id_inventario"));
        inventario.setId_producto(rs.getInt("id_producto"));
        inventario.setId_seccion(rs.getInt("id_seccion"));
        inventario.setStock_actual(rs.getInt("stock_actual"));
        ProductoInterno p = new ProductoInterno();
        p.setId_producto(rs.getInt("id_producto"));
        p.setNombre(rs.getString("nombre_producto"));
        p.setPresentacion(rs.getString("presentacion"));
        p.setCodigo_icp(rs.getString("codigo_icp"));
        Seccion s = new Seccion();
        s.setId_seccion(rs.getInt("id_seccion"));
        s.setNombre_seccion(rs.getString("nombre_seccion"));
        inventario.setProducto(p);
        inventario.setSeccion(s);
        resultado.add(inventario);
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return resultado;
  }
  
   public List<Inventario> obtenerInventarios() {

    List<Inventario> resultado = new ArrayList<Inventario>();

    try {
        PreparedStatement consulta = getConexion().prepareStatement(" SELECT i.stock_actual as stock_actual, i.id_inventario as id_inventario, i.id_producto as id_producto, i.id_seccion as id_seccion, ci.nombre as nombre_producto, s.nombre_seccion as nombre_seccion "
                                                                  + " FROM bodega.inventarios i "
                                                                  + "           INNER JOIN bodega.catalogo_interno ci on i.id_producto = ci.id_producto "
                                                                  + "           INNER JOIN seguridad.secciones s on i.id_seccion = s.id_seccion;");

        ResultSet rs = consulta.executeQuery();

        while (rs.next()) {
            Inventario inventario = new Inventario();
            inventario.setId_inventario(rs.getInt("id_inventario"));
            inventario.setId_producto(rs.getInt("id_producto"));
            inventario.setId_seccion(rs.getInt("id_seccion"));
            inventario.setStock_actual(rs.getInt("stock_actual"));
            ProductoInterno p = new ProductoInterno();
            p.setId_producto(rs.getInt("id_producto"));
            p.setNombre(rs.getString("nombre_producto"));
            Seccion s = new Seccion();
            s.setId_seccion(rs.getInt("id_seccion"));
            s.setNombre_seccion(rs.getString("nombre_seccion"));
            inventario.setProducto(p);
            inventario.setSeccion(s);
            
            resultado.add(inventario);
        }
        rs.close();
        consulta.close();
        cerrarConexion();
    }catch (Exception ex) {
        ex.printStackTrace();
    }
    return resultado;
  }
  
  
  public Inventario obtenerInventario(int id_inventario) {

    Inventario inventario = new Inventario();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bodega.inventarios Where id_inventario = ? ");
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
          ProductoInterno p = pr.obtenerProductoInterno(rs.getInt("id_producto"));
          Seccion s = sc.obtenerSeccion(rs.getInt("id_seccion"));
          inventario.setProducto(p);
          inventario.setSeccion(s);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return inventario;
  }
}
