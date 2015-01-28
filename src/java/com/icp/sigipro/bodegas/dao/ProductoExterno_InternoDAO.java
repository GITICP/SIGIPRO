/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bodegas.modelos.ProductoExternoInterno;
import com.icp.sigipro.seguridad.modelos.PermisoRol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class ProductoExterno_InternoDAO {
    private Connection conexion;
    
    public ProductoExterno_InternoDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }
    public boolean insertarProductoExterno_Interno(ProductoExternoInterno p) {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.catalogos_internos_externos (id_producto_ext, id_producto) "
              + " VALUES (?,?)");

      consulta.setInt(2, p.getId_producto());
      consulta.setInt(1, p.getId_producto_ext());
      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
      }
      consulta.close();
      conexion.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return resultado;
  }

  public boolean eliminarProductoExterno_Interno(ProductoExternoInterno p) {
    boolean resultado = false;
    try {
      PreparedStatement consulta = getConexion().prepareStatement("DELETE FROM bodega.catalogos_internos_externos s "
              + "WHERE  s.id_producto = ? AND s.id_producto_ext = ? "
      );
      consulta.setInt(1, p.getId_producto());
      consulta.setInt(2, p.getId_producto_ext());
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
  
  public boolean eliminarProductoExterno_Interno_Existentes(int idext) {
    boolean resultado = false;
    try {
      PreparedStatement consulta = getConexion().prepareStatement("DELETE FROM bodega.catalogos_internos_externos s "
              + "WHERE  s.id_producto_ext = ? "
      );
      consulta.setInt(1, idext);
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

  public List<ProductoExternoInterno> parsearProductosExternos_Internos(String internos, int idExterno) {
    List<ProductoExternoInterno> resultado = null;
    try {
      resultado = new ArrayList<ProductoExternoInterno>();
      List<String> internosParcial = new LinkedList<String>(Arrays.asList(internos.split("#r#")));
      internosParcial.remove("");
      for (String i : internosParcial) {
        String[] interno = i.split("#c#");
        resultado.add(new ProductoExternoInterno(idExterno, Integer.parseInt(interno[0])));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      resultado = null;
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
