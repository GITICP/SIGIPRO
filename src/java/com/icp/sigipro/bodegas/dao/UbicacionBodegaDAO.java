package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bodegas.modelos.UbicacionBodega;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walter
 */
public class UbicacionBodegaDAO 
{
  private Connection conexion;
  
  public UbicacionBodegaDAO()
  {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  } 
  
  public boolean insertarUbicacion(UbicacionBodega u){
    
    boolean resultado = false;
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.ubicaciones_bodega (nombre, descripcion) " +
                                                             " VALUES (?,?) RETURNING id_ubicacion");
      
      consulta.setString(1, u.getNombre());
      consulta.setString(2, u.getDescripcion());
      ResultSet resultadoConsulta = consulta.executeQuery();
      if ( resultadoConsulta.next() ){
        resultado = true;
        u.setId_ubicacion(resultadoConsulta.getInt("id_ubicacion"));
      }
      consulta.close();
      conexion.close();
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    return resultado;
  }
  
  public boolean editarUbicacion(UbicacionBodega u){
    
    boolean resultado = false;
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bodega.ubicaciones_bodega " +
              " SET nombre=?, descripcion=? " +
              " WHERE id_ubicacion=?; "

      );
      
      consulta.setString(1, u.getNombre());
      consulta.setString(2, u.getDescripcion());
      consulta.setInt(3, u.getId_ubicacion());
      
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
  
  public boolean eliminarUbicacion(int id_ubicacion) throws SIGIPROException{
    
    boolean resultado = false;
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bodega.ubicaciones_bodega " +
              " WHERE id_ubicacion=?; "

      );
      
      consulta.setInt(1, id_ubicacion);
      
      if ( consulta.executeUpdate() == 1){
        resultado = true;
      }
      consulta.close();
      conexion.close();
    }
    catch(SQLException ex){
      throw new SIGIPROException("Ubicación no pudo ser eliminada debido a que uno o más productos se encuentran asociados a esta.");
    }
    return resultado;
  }  
  
  public UbicacionBodega obtenerUbicacion(int id_ubicacion){
    
    UbicacionBodega ubicacion = new UbicacionBodega();
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bodega.ubicaciones_bodega where id_ubicacion = ?");
      
      consulta.setInt(1, id_ubicacion);
      
      ResultSet rs = consulta.executeQuery();
      
      if(rs.next()){
        ubicacion.setId_ubicacion(rs.getInt("id_ubicacion"));
        ubicacion.setNombre(rs.getString("nombre"));
        ubicacion.setDescripcion(rs.getString("descripcion"));
      }      
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    return ubicacion;
  }
  
  public List<UbicacionBodega> obtenerUbicaciones(){
    
    List<UbicacionBodega> resultado = new ArrayList<UbicacionBodega>();
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM bodega.ubicaciones_bodega ");
      ResultSet rs = consulta.executeQuery();
      
      while(rs.next()){
        UbicacionBodega ubicacion = new UbicacionBodega();
        ubicacion.setId_ubicacion(rs.getInt("id_ubicacion"));
        ubicacion.setNombre(rs.getString("nombre"));
        ubicacion.setDescripcion(rs.getString("descripcion"));
        
        resultado.add(ubicacion);
      }      
      
      consulta.close();
      conexion.close();
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    return resultado;
  }
  
  public List<UbicacionBodega> obtenerUbicacionesLimitado(){
    
    List<UbicacionBodega> resultado = new ArrayList<UbicacionBodega>();
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_ubicacion, nombre FROM bodega.ubicaciones_bodega ");
      ResultSet rs = consulta.executeQuery();
      
      while(rs.next()){
        UbicacionBodega ubicacion = new UbicacionBodega();
        ubicacion.setId_ubicacion(rs.getInt("id_ubicacion"));
        ubicacion.setNombre(rs.getString("nombre"));
        
        resultado.add(ubicacion);
      }      
      
      consulta.close();
      conexion.close();
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    return resultado;
  } 
  
  public List<UbicacionBodega> obtenerUbicaciones(int p_Id_Interno){
    
    List<UbicacionBodega> resultado = new ArrayList<UbicacionBodega>();
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_ubicacion, nombre FROM bodega.ubicaciones_bodega "+
                                                                  " WHERE id_ubicacion in ( SELECT uci.id_ubicacion FROM bodega.ubicaciones_catalogo_interno uci "+
                                                                  " WHERE uci.id_producto = ?) ");
      
      consulta.setInt(1, p_Id_Interno);
      
      ResultSet rs = consulta.executeQuery();
      
      while(rs.next()){
        UbicacionBodega ubicacion = new UbicacionBodega();
        ubicacion.setId_ubicacion(rs.getInt("id_ubicacion"));
        ubicacion.setNombre(rs.getString("nombre"));
        
        resultado.add(ubicacion);
      }      
      
      consulta.close();
      conexion.close();
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    return resultado;
  } 
  
  public List<UbicacionBodega> obtenerUbicacionesRestantes(int p_Id_Interno){
    
    List<UbicacionBodega> resultado = new ArrayList<UbicacionBodega>();
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_ubicacion, nombre FROM bodega.ubicaciones_bodega "+
                                                                  " WHERE id_ubicacion not in ( SELECT uci.id_ubicacion FROM bodega.ubicaciones_catalogo_interno uci "+
                                                                  " WHERE uci.id_producto = ?) ");
      
      consulta.setInt(1, p_Id_Interno);
      
      ResultSet rs = consulta.executeQuery();
      
      while(rs.next()){
        UbicacionBodega ubicacion = new UbicacionBodega();
        ubicacion.setId_ubicacion(rs.getInt("id_ubicacion"));
        ubicacion.setNombre(rs.getString("nombre"));
        
        resultado.add(ubicacion);
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
