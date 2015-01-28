/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bodegas.modelos.ProductoExterno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class ProductoExternoDAO {

  private Connection conexion;

  public ProductoExternoDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }

  public boolean insertarProductoExterno(ProductoExterno p) {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.catalogo_externo (producto, codigo_externo, marca, id_proveedor) "
              + " VALUES (?,?,?,?) RETURNING id_producto_ext");

      consulta.setString(1, p.getProducto());
      consulta.setString(2, p.getCodigo_Externo());
      consulta.setInt(4, p.getId_Proveedor());
      consulta.setString(3, p.getMarca());
      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
        p.setId_producto_ext(resultadoConsulta.getInt("id_producto_ext"));
      }
      consulta.close();
      conexion.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return resultado;
  }

  public boolean editarProductoExterno(ProductoExterno p) {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bodega.catalogo_externo "
              + " SET producto=?, codigo_externo=?, marca=?, "
              + " id_proveedor=? "
              + " WHERE id_producto_ext=?; "
      );

      consulta.setString(1, p.getProducto());
      consulta.setString(2, p.getCodigo_Externo());
      consulta.setInt(4, p.getId_Proveedor());
      consulta.setString(3, p.getMarca());
      consulta.setInt(5,p.getId_producto_ext());

      if (consulta.executeUpdate() == 1) {
        resultado = true;
      }
      consulta.close();
      conexion.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return resultado;
  }

  public boolean eliminarProductoExterno(int id_producto_ext) {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bodega.catalogo_externo "
              + " WHERE id_producto_ext=?; "
      );

      consulta.setInt(1, id_producto_ext);

      if (consulta.executeUpdate() == 1) {
        resultado = true;
      }
      consulta.close();
      conexion.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return resultado;
  }

  public ProductoExterno obtenerProductoExterno(int id_producto_ext) {

    ProductoExterno producto = new ProductoExterno();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bodega.catalogo_externo where id_producto_ext = ?");

      consulta.setInt(1, id_producto_ext);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        producto.setId_producto_ext(rs.getInt("id_producto_ext"));
        producto.setProducto(rs.getString("producto"));
        producto.setCodigo_Externo(rs.getString("codigo_externo"));
        producto.setId_Proveedor(rs.getInt("id_proveedor"));
        producto.setMarca(rs.getString("marca"));
        try {
          PreparedStatement consulta2 = getConexion().prepareStatement("SELECT nombre_proveedor FROM compras.proveedores where id_proveedor = ?");
          consulta2.setInt(1, producto.getId_Proveedor());
          ResultSet rs2 = consulta2.executeQuery();
          if (rs2.next()) {
            producto.setNombreProveedor(rs2.getString("nombre_proveedor"));
          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return producto;
  }

  public List<ProductoExterno> obtenerProductos() {

    List<ProductoExterno> resultado = new ArrayList<ProductoExterno>();

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM bodega.catalogo_externo ");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        ProductoExterno producto = new ProductoExterno();
        producto.setId_producto_ext(rs.getInt("id_producto_ext"));
        producto.setProducto(rs.getString("producto"));
        producto.setCodigo_Externo(rs.getString("codigo_externo"));
        producto.setId_Proveedor(rs.getInt("id_proveedor"));
        producto.setMarca(rs.getString("marca"));
        try {
          PreparedStatement consulta2 = getConexion().prepareStatement("SELECT nombre_proveedor FROM compras.proveedores where id_proveedor = ?");
          consulta2.setInt(1, producto.getId_Proveedor());
          ResultSet rs2 = consulta2.executeQuery();
          if (rs2.next()) {
            producto.setNombreProveedor(rs2.getString("nombre_proveedor"));
          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        resultado.add(producto);
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
