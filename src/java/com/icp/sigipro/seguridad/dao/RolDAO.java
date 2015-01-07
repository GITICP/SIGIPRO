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
    

}
