/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bodegas.modelos.ProductoInterno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class ProductoInternoDAO
{
  
  private Connection conexion;
  
  public ProductoInternoDAO()
  {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }
  
  public boolean insertarProductoInterno(ProductoInterno p){
    
    boolean resultado = false;
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.catalogo_interno (nombre, codigo_icp, stock_minimo, stock_maximo, ubicacion, presentacion, descripcion) " +
                                                             " VALUES (?,?,?,?,?,?,?) ");
      
      consulta.setString(1, p.getNombre());
      consulta.setString(2, p.getCodigo_icp());
      consulta.setInt(3, p.getStock_minimo());
      consulta.setInt(4, p.getStock_maximo());
      consulta.setString(5, p.getUbicacion());
      consulta.setString(6, p.getPresentacion());
      consulta.setString(7, p.getDescripcion());
      
      if ( consulta.executeUpdate() == 1){
        resultado = true;
      }
      consulta.close();
      conexion.close();
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    return resultado;
  }
  
  public boolean editarProductoInterno(ProductoInterno p){
    
    boolean resultado = false;
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bodega.catalogo_interno " +
              " SET nombre=?, codigo_icp=?, stock_minimo=?, stock_maximo=?, " + 
              " ubicacion=?, presentacion=?, descripcion=? " +
              " WHERE id_producto=?; "

      );
      
      consulta.setString(1, p.getNombre());
      consulta.setString(2, p.getCodigo_icp());
      consulta.setInt(3, p.getStock_minimo());
      consulta.setInt(4, p.getStock_maximo());
      consulta.setString(5, p.getUbicacion());
      consulta.setString(6, p.getPresentacion());
      consulta.setString(7, p.getDescripcion());
      consulta.setInt(8, p.getId_producto());
      
      if ( consulta.executeUpdate() == 1){
        resultado = true;
      }
      consulta.close();
      conexion.close();
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    return resultado;
  }
  
  public boolean eliminarProductoInterno(int id_producto){
    
    boolean resultado = false;
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bodega.catalogo_interno " +
              " WHERE id_producto=?; "

      );
      
      consulta.setInt(1, id_producto);
      
      if ( consulta.executeUpdate() == 1){
        resultado = true;
      }
      consulta.close();
      conexion.close();
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    return resultado;
  }
  
  public ProductoInterno obtenerProductoInterno(int id_producto){
    
    ProductoInterno producto = new ProductoInterno();
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bodega.catalogo_interno where id_producto = ?");
      
      consulta.setInt(1, id_producto);
      
      ResultSet rs = consulta.executeQuery();
      
      if(rs.next()){
        producto.setId_producto(rs.getInt("id_producto"));
        producto.setNombre(rs.getString("nombre"));
        producto.setCodigo_icp(rs.getString("codigo_icp"));
        producto.setStock_minimo(rs.getInt("stock_minimo"));
        producto.setStock_maximo(rs.getInt("stock_maximo"));
        producto.setUbicacion(rs.getString("ubicacion"));
        producto.setPresentacion(rs.getString("presentacion"));
        producto.setDescripcion(rs.getString("descripcion"));
      }      
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    return producto;
  }
  
  public List<ProductoInterno> obtenerProductos(){
    
    List<ProductoInterno> resultado = new ArrayList<ProductoInterno>();
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM bodega.catalogo_interno ");
      ResultSet rs = consulta.executeQuery();
      
      while(rs.next()){
        ProductoInterno producto = new ProductoInterno();
        producto.setId_producto(rs.getInt("id_producto"));
        producto.setNombre(rs.getString("nombre"));
        producto.setCodigo_icp(rs.getString("codigo_icp"));
        producto.setStock_minimo(rs.getInt("stock_minimo"));
        producto.setStock_maximo(rs.getInt("stock_maximo"));
        producto.setUbicacion(rs.getString("ubicacion"));
        producto.setPresentacion(rs.getString("presentacion"));
        producto.setDescripcion(rs.getString("descripcion"));
        
        resultado.add(producto);
      }      
      
      consulta.close();
      conexion.close();
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    return resultado;
  }
  
  private Connection getConexion(){
    try{     

      if ( conexion.isClosed() ){
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
      }
    }
    catch(Exception ex)
    {
      conexion = null;
  }

    return conexion;
  }
  
}
