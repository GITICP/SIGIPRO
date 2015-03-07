package com.icp.sigipro.activosfijos.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.activosfijos.modelos.Ubicacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walter
 */
public class UbicacionDAO 
{
  private Connection conexion;
  
  public UbicacionDAO()
  {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  } 
  
  public boolean insertarUbicacion(Ubicacion u){
    
    boolean resultado = false;
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.ubicaciones (nombre, descripcion) " +
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
  
  public boolean editarUbicacion(Ubicacion u){
    
    boolean resultado = false;
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bodega.ubicaciones " +
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
  
  public boolean eliminarUbicacion(int id_ubicacion){
    
    boolean resultado = false;
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bodega.ubicaciones " +
              " WHERE id_ubicacion=?; "

      );
      
      consulta.setInt(1, id_ubicacion);
      
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
  
  public Ubicacion obtenerUbicacion(int id_ubicacion){
    
    Ubicacion ubicacion = new Ubicacion();
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bodega.ubicaciones where id_ubicacion = ?");
      
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
  
  public List<Ubicacion> obtenerUbicaciones(){
    
    List<Ubicacion> resultado = new ArrayList<Ubicacion>();
    
    try{
      PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM bodega.ubicaciones ");
      ResultSet rs = consulta.executeQuery();
      
      while(rs.next()){
        Ubicacion ubicacion = new Ubicacion();
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
