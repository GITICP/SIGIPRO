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
                consulta = conexion.prepareStatement("SELECT p.nombrepermiso, pr.idrol, pr.idpermiso "
                                                     + "FROM seguridad.permisosrol pr, seguridad.permisos p  Where  pr.idpermiso = p.idpermiso AND pr.idrol = ? ");
                consulta.setInt(1, Integer.parseInt(p_IdRol));
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarPermisosRol(resultadoConsulta);
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
    private List<PermisoRol> llenarPermisosRol(ResultSet resultadoConsulta) throws SQLException
    {
        List<PermisoRol> resultado = new ArrayList<PermisoRol>();
        
        while(resultadoConsulta.next())
        {
            int idPermiso= resultadoConsulta.getInt("idpermiso");
            String nombrePermiso = resultadoConsulta.getString("nombrepermiso");
            int idRol = resultadoConsulta.getInt("idrol");
            
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
                consulta = conexion.prepareStatement(  "SELECT p.idpermiso, p.nombrepermiso, p.descripcionpermiso "
                                                     + "FROM seguridad.permisos p "
                                                     + "WHERE p.idpermiso NOT IN (SELECT ru.idpermiso FROM seguridad.permisosrol ru WHERE ru.idrol = ?)");
                consulta.setInt(1, Integer.parseInt(p_idrol));
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarPermisos(resultadoConsulta);
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
    private List<Permiso> llenarPermisos(ResultSet resultadoConsulta) throws SQLException
    {
        List<Permiso> resultado = new ArrayList<Permiso>();
        
        while(resultadoConsulta.next())
        {
            String nombreRol = resultadoConsulta.getString("nombrepermiso");
            int idRol = resultadoConsulta.getInt("idpermiso");
            String descripcionrol = resultadoConsulta.getString("descripcionpermiso");
            
            resultado.add(new Permiso(idRol, nombreRol, descripcionrol));
        }
        return resultado;
    }
}
