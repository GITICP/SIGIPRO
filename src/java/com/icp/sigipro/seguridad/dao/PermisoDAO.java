/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.dao;


import com.icp.sigipro.seguridad.modelos.PermisoRol;
import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.seguridad.modelos.Permiso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class PermisoDAO 
{
    private String nombreTabla;
    
    public List<PermisoRol> obtenerPermisosRol(String p_IdRol)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        List<PermisoRol> resultado = null;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT p.nombre, pr.id_rol, pr.id_permiso "
                                                     + "FROM seguridad.permisos_roles pr, seguridad.permisos p  Where  pr.id_permiso = p.id_permiso AND pr.id_rol = ? ");
                consulta.setInt(1, Integer.parseInt(p_IdRol));
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarPermisosRol(resultadoConsulta);
                resultadoConsulta.close();
                conexion.close();
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
                resultado = null;
            }
        }
        return resultado;
    }
    
    @SuppressWarnings("Convert2Diamond")
    private List<PermisoRol> llenarPermisosRol(ResultSet resultadoConsulta) throws SQLException
    {
        List<PermisoRol> resultado = new ArrayList<PermisoRol>();
        
        while(resultadoConsulta.next())
        {
            int idPermiso= resultadoConsulta.getInt("id_permiso");
            String nombrePermiso = resultadoConsulta.getString("nombre");
            int idRol = resultadoConsulta.getInt("id_rol");
            
            resultado.add(new PermisoRol(idRol, idPermiso, nombrePermiso));
        }
        return resultado;
    }
    
 
public List<Permiso> obtenerPermisosRestantes(String p_idrol)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        List<Permiso> resultado = null;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement(  "SELECT p.id_permiso, p.nombre, p.descripcion "
                                                     + "FROM seguridad.permisos p "
                                                     + "WHERE p.id_permiso NOT IN (SELECT ru.id_permiso FROM seguridad.permisos_roles ru WHERE ru.id_rol = ?)");
                consulta.setInt(1, Integer.parseInt(p_idrol));
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarPermisos(resultadoConsulta);
                resultadoConsulta.close();
                conexion.close();
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
                resultado = null;
            }
        }
        return resultado;
    }

 @SuppressWarnings("Convert2Diamond")
    private List<Permiso> llenarPermisos(ResultSet resultadoConsulta) throws SQLException
    {
        List<Permiso> resultado = new ArrayList<Permiso>();
        
        while(resultadoConsulta.next())
        {
            String nombreRol = resultadoConsulta.getString("nombre");
            int idRol = resultadoConsulta.getInt("id_permiso");
            String descripcion = resultadoConsulta.getString("descripcion");
            
            resultado.add(new Permiso(idRol, nombreRol, descripcion));
        }
        return resultado;
    }
}
