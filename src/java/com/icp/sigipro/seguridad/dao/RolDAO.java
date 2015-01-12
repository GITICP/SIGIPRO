/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.seguridad.modelos.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amed
 */
public class RolDAO
{
    public boolean insertarRol(String nombre, String descripcion)
    {
        boolean resultado = false;
        
        try
        {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("INSERT INTO SEGURIDAD.roles "
                        + " ( nombre, descripcion) "
                        + " VALUES "
                        + " (?,? )");
                consulta.setString(1, nombre);
                consulta.setString(2, descripcion);
                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1)
                {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        }
        catch(SQLException ex){System.out.println(ex); }
        
        return resultado;
    }
    
    public boolean editarRol(int idrol, String nombre, String descripcion)
    {
        boolean resultado = false;
        
        try
        {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("UPDATE SEGURIDAD.roles "
                        + " SET nombre = ?, descripcion = ? "
                        + " WHERE id_rol = ? ");
                
                consulta.setString(1, nombre);
                consulta.setString(2, descripcion);
                consulta.setInt(3, idrol);

                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1)
                {
                    resultado = true;
                }                
                consulta.close();
                PreparedStatement eliminarRolesUsuario = conexion.prepareStatement("Delete from seguridad.roles_usuarios where id_rol = ?");
                eliminarRolesUsuario.setInt(1, idrol);
                int result = eliminarRolesUsuario.executeUpdate();
                eliminarRolesUsuario.close();
                conexion.close();
            }
        }
        catch(SQLException ex) {System.out.println(ex); }

        return resultado;
    }
    public boolean EliminarRol(String p_idrol)
    {
        boolean resultado = false;
        
        try
        {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("DELETE FROM SEGURIDAD.roles s " +
                                                                        "WHERE  s.id_rol = ? "
                        );
                consulta.setInt(1, Integer.parseInt(p_idrol) );
                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1)
                {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        }
        catch(SQLException ex){System.out.println(ex); }

        
        return resultado;
    }
    


    
 public List<Rol> obtenerRoles()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        List<Rol> resultado = null;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT r.id_rol, r.nombre, r.descripcion "
                                                     + "FROM seguridad.roles r");
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarRoles(resultadoConsulta);
                resultadoConsulta.close();
                conexion.close();
            }
            catch(SQLException ex)
            {
                resultado = null;
            }
        }
        return resultado;
    }
  @SuppressWarnings("Convert2Diamond")
    private List<Rol> llenarRoles(ResultSet resultadoConsulta) throws SQLException
    {
        List<Rol> resultado = new ArrayList<Rol>();
        
        while(resultadoConsulta.next())
        {
            String nombreRol = resultadoConsulta.getString("nombre");
            int idRol = resultadoConsulta.getInt("id_rol");
            String descripcionrol = resultadoConsulta.getString("descripcion");
            
            resultado.add(new Rol(idRol, nombreRol, descripcionrol));
        }
        return resultado;
    }
    public String getNombreRol(String p_idrol) throws SQLException
    {
    String resultado = "Error";
    try
        {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("SELECT s.nombre FROM SEGURIDAD.roles s WHERE s.id_rol = ?"
                        );
                consulta.setInt(1, Integer.parseInt(p_idrol) );
                ResultSet resultadoConsulta =  consulta.executeQuery();
                if(resultadoConsulta.next()){
                  resultado = resultadoConsulta.getString("nombre");
                }
                else {resultado = "Error";}
                consulta.close();
                conexion.close();
                
            }
            
        }
        catch(SQLException ex){System.out.println(ex); }
    return resultado;
    }
    public Rol obtenerRol(int p_id) throws SQLException {
    Rol resultado = null;
    try {
      SingletonBD s = SingletonBD.getSingletonBD();
      Connection conexion = s.conectar();

      PreparedStatement consulta = conexion.prepareStatement(" Select nombre, descripcion"
              + " From seguridad.roles"
              + " Where id_rol = ? ");

      consulta.setInt(1, p_id);

      ResultSet resultadoConsulta = consulta.executeQuery();

      if (resultadoConsulta.next()) {

        String nombre = resultadoConsulta.getString("nombre");
        String descripcion = resultadoConsulta.getString("descripcion");
       

        resultado = new Rol(p_id, nombre, descripcion);
      }
    } catch (SQLException ex) {

    }

    return resultado;
    }
    
    public List<RolUsuario> obtenerUsuariosRol(String p_idrol) {
    SingletonBD s = SingletonBD.getSingletonBD();
    Connection conexion = s.conectar();
    List<RolUsuario> resultado = null;

    if (conexion != null) {
      try {
        PreparedStatement consulta;
        consulta = conexion.prepareStatement("SELECT r.nombre, ru.id_rol, ru.id_usuario, ru.fecha_activacion, ru.fecha_desactivacion "
                + "FROM seguridad.roles r inner join seguridad.roles_usuarios ru  on r.id_rol = ru.id_rol AND ru.id_rol = ? ");
        consulta.setInt(1, Integer.parseInt(p_idrol));
        ResultSet resultadoConsulta = consulta.executeQuery();
        resultado = llenarUsuariosRol(resultadoConsulta);
        resultadoConsulta.close();
        conexion.close();
      } catch (SQLException ex) {
        resultado = null;
      }
    }
    return resultado;
  }
     private List<RolUsuario> llenarUsuariosRol(ResultSet resultadoConsulta) throws SQLException {
    List<RolUsuario> resultado = new ArrayList<RolUsuario>();
    SingletonBD s = SingletonBD.getSingletonBD();
    Connection conexion = s.conectar();

    while (resultadoConsulta.next()) {
      int idUsuario = resultadoConsulta.getInt("id_usuario");
      String nombreRol = resultadoConsulta.getString("nombre");
      int idRol = resultadoConsulta.getInt("id_rol");
      Date fechaActivacion = resultadoConsulta.getDate("fecha_activacion");
      Date fechaDesactivacion = resultadoConsulta.getDate("fecha_desactivacion");

      RolUsuario ru = new RolUsuario(idRol, idUsuario, fechaActivacion, fechaDesactivacion, nombreRol);
      
      try {
        PreparedStatement consulta;
        consulta = conexion.prepareStatement("SELECT u.nombre_usuario "
                + "FROM seguridad.usuarios u inner join seguridad.roles_usuarios ru  on u.id_usuario = ru.id_usuario AND ru.id_rol = ? AND ru.id_usuario =? ");
        consulta.setInt(1, idRol);
        consulta.setInt(2, idUsuario);
        ResultSet ResConsulta;
        ResConsulta = consulta.executeQuery();
        if (ResConsulta.next())
        {
          String nombreu = ResConsulta.getString("nombre_usuario");
          ru.setNombreUsuario(nombreu);
        }
        ResConsulta.close();
      } catch (SQLException ex) {
        System.out.println(ex);
        resultado = null;
      }

      resultado.add(ru);
    }
    conexion.close();
    return resultado;
  }
     
  public int obtenerIDRol(String nombre) throws SQLException {
    int resultado =0;
    try {
      SingletonBD s = SingletonBD.getSingletonBD();
      Connection conexion = s.conectar();

      PreparedStatement consulta = conexion.prepareStatement(" Select id_rol"
              + " From seguridad.roles"
              + " Where nombre = ? ");

      consulta.setString(1, nombre);

      ResultSet resultadoConsulta = consulta.executeQuery();

      if (resultadoConsulta.next()) {
        resultado = resultadoConsulta.getInt("id_rol");
      }
    } catch (SQLException ex) {

    }

    return resultado;
    }

}
