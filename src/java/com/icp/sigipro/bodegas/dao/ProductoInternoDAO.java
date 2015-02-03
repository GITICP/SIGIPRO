/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bodegas.modelos.ProductoInterno;
import com.icp.sigipro.bodegas.modelos.Reactivo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
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
  
  public boolean insertarProductoInterno(ProductoInterno p, String ubicaciones, String productosExternos){
    
    boolean resultado = false;
    
    try{
      getConexion().setAutoCommit(false);
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.catalogo_interno (nombre, codigo_icp, stock_minimo, stock_maximo, ubicacion, presentacion, descripcion) " +
                                                             " VALUES (?,?,?,?,?,?,?) RETURNING id_producto");
      
      consulta.setString(1, p.getNombre());
      consulta.setString(2, p.getCodigo_icp());
      consulta.setInt(3, p.getStock_minimo());
      consulta.setInt(4, p.getStock_maximo());
      consulta.setString(5, p.getUbicacion());
      consulta.setString(6, p.getPresentacion());
      consulta.setString(7, p.getDescripcion());
      ResultSet resultadoConsulta = consulta.executeQuery();
      if ( resultadoConsulta.next() ){
        resultado = true;
        p.setId_producto(resultadoConsulta.getInt("id_producto"));
      }
      
      Reactivo reactivo = p.getReactivo();
      
      if(reactivo != null){
        PreparedStatement consultaReactivo = getConexion().prepareStatement(
                " INSERT INTO bodega.reactivos (id_producto, numero_cas, formula_quimica, familia, cantidad_botella_bodega, cantidad_botella_lab, volumen_bodega, volumen_lab) " +
                " VALUES (?,?,?,?,?,?,?,?)");
        
        consultaReactivo.setInt(1, p.getId_producto());
        consultaReactivo.setString(2, reactivo.getNumero_cas());
        consultaReactivo.setString(3, reactivo.getFormula_quimica());
        consultaReactivo.setString(4, reactivo.getFamilia());
        consultaReactivo.setInt(5, reactivo.getCantidad_botella_bodega());
        consultaReactivo.setInt(6, reactivo.getCantidad_botella_lab());
        consultaReactivo.setString(7, reactivo.getVolumen_bodega());
        consultaReactivo.setString(8, reactivo.getVolumen_lab());
        
        consultaReactivo.executeUpdate(); 
      }
      
      if (ubicaciones != null){
        String[] idsTemp = ubicaciones.split("#u#");
        String[] ids = Arrays.copyOfRange(idsTemp, 1, idsTemp.length);
        
        PreparedStatement consultaUbicaciones = getConexion().prepareStatement(
                " INSERT INTO bodega.ubicaciones_catalogo_interno(id_ubicacion, id_producto) " +
                " VALUES (?, ?)");
        
        consultaUbicaciones.setInt(2, p.getId_producto());
        
        for (String id : ids){
          consultaUbicaciones.setInt(1, Integer.parseInt(id));
          consultaUbicaciones.executeUpdate();
        }
      }
      
      if (productosExternos != null){
        String[] idsTemp = productosExternos.split("#p#");
        String[] ids = Arrays.copyOfRange(idsTemp, 1, idsTemp.length);
        
        PreparedStatement consultaUbicaciones = getConexion().prepareStatement(
                " INSERT INTO bodega.catalogos_internos_externos(id_producto_ext, id_producto) " +
                " VALUES (?, ?)");
        
        consultaUbicaciones.setInt(2, p.getId_producto());
        
        for (String id : ids){
          consultaUbicaciones.setInt(1, Integer.parseInt(id));
          consultaUbicaciones.executeUpdate();
        }
      }
      
      getConexion().commit();
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

  public List<ProductoInterno> obtenerProductosInternosRestantes(int p_IdExterno) {
    List<ProductoInterno> resultado = new ArrayList<ProductoInterno>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement("SELECT * "
              + "FROM bodega.catalogo_interno c "
              + "WHERE c.id_producto NOT IN (SELECT ru.id_producto FROM bodega.catalogos_internos_externos ru WHERE ru.id_producto_ext = ?)");
      consulta.setInt(1, p_IdExterno);
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
    } catch (Exception ex) {
      resultado = null;
    }

    return resultado;
  }

  public List<ProductoInterno> obtenerProductosInternos_Externo(int p_IdExterno) {
    List<ProductoInterno> resultado = new ArrayList<ProductoInterno>();

   try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement("SELECT * "
              + "FROM bodega.catalogo_interno c "
              + "WHERE c.id_producto IN (SELECT ru.id_producto FROM bodega.catalogos_internos_externos ru WHERE ru.id_producto_ext = ?)");
      consulta.setInt(1, p_IdExterno);
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
    } catch (Exception ex) {
      resultado = null;
    }

    return resultado;
  }

}
