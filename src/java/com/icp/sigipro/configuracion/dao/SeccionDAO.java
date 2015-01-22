/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.configuracion.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.configuracion.modelos.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Walter
 */
public class SeccionDAO
{
    public boolean insertarSeccion(String nombre, String descripcion)
    {
        boolean resultado = false;
        
        try
        {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("INSERT INTO SEGURIDAD.secciones "
                        + " ( nombre_seccion, descripcion) "
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
    
    public boolean editarSeccion(int idseccion, String nombre, String descripcion)
    {
        boolean resultado = false;
        
        try
        {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("UPDATE SEGURIDAD.secciones "
                        + " SET nombre_seccion = ?, descripcion = ? "
                        + " WHERE id_seccion = ? ");
                
                consulta.setString(1, nombre);
                consulta.setString(2, descripcion);
                consulta.setInt(3, idseccion);

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
    public boolean EliminarSeccion(String p_idseccion)
    {
        boolean resultado = false;
        
        try
        {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("DELETE FROM SEGURIDAD.secciones s " +
                                                                        "WHERE  s.id_seccion = ? "
                        );
                consulta.setInt(1, Integer.parseInt(p_idseccion) );
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
    public boolean validarNombreSeccion(String nombre)
    {
    SingletonBD s = SingletonBD.getSingletonBD();
    Connection conexion = s.conectar();
    boolean resultado1 = false;

    if (conexion != null)
    {
      try
      {
        PreparedStatement consulta;
        consulta = conexion.prepareStatement("SELECT nombre_seccion FROM seguridad.secciones WHERE nombre_seccion =? ");
        consulta.setString(1, nombre);
        
        boolean resultadoConsulta = consulta.execute();
        if (resultadoConsulta == true)
      {
        resultado1 = true;
      }
      }
      catch (SQLException ex) {
      ex.printStackTrace();
    }
    
    }
    return resultado1;
    }
    


    
 public List<Seccion> obtenerSecciones()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        List<Seccion> resultado = null;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT s.id_seccion, s.nombre_seccion, s.descripcion "
                                                     + "FROM seguridad.secciones s");
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarSecciones(resultadoConsulta);
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
    private List<Seccion> llenarSecciones(ResultSet resultadoConsulta) throws SQLException
    {
        List<Seccion> resultado = new ArrayList<Seccion>();
        
        while(resultadoConsulta.next())
        {
            String nombreSeccion = resultadoConsulta.getString("nombre_seccion");
            int idSeccion = resultadoConsulta.getInt("id_seccion");
            String descripcion = resultadoConsulta.getString("descripcion");
            
            resultado.add(new Seccion(idSeccion, nombreSeccion, descripcion));
        }
        return resultado;
    }
    
    
}
