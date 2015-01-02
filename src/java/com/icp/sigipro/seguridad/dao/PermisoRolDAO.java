/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.seguridad.modelos.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Amed
 */
public class PermisoRolDAO 
{
         public boolean insertarPermisoRol(String idrol, String idpermiso)
    {
        boolean resultado = false;
        
        try
        {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("INSERT INTO SEGURIDAD.permisosrol "
                        + " (idrol, idpermiso) "
                        + " VALUES "
                        + " (?,? )");
                consulta.setInt(1, Integer.parseInt(idrol));
                consulta.setInt(2, Integer.parseInt(idpermiso));
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
    public boolean EliminarPermisoRol(String p_idrol, String p_idpermiso)
    {
        boolean resultado = false;
        
        try
        {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("DELETE FROM seguridad.permisosrol s " +
                                                                        "WHERE  s.idrol = ? AND s.idpermiso = ? "
                        );
                consulta.setInt(1, Integer.parseInt(p_idrol) );
                consulta.setInt(2, Integer.parseInt(p_idpermiso));
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
}
