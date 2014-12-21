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
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 *
 * @author Amed
 */
public class RolUsuarioDAO
{
    public boolean insertarRolUsuario(String idusuario, String idrol, String fechaActivacion, String fechaDesactivacion)
    {
        boolean resultado = false;
        
        try
        {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("INSERT INTO SEGURIDAD.rolesusuario "
                        + " (idusuario, idrol, fechaactivacion, fechadesactivacion) "
                        + " VALUES "
                        + " (?,?,?,? )");
                consulta.setInt(1, Integer.parseInt(idusuario));
                consulta.setInt(2, Integer.parseInt(idrol));

                
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date fActivacion = formatoFecha.parse(fechaActivacion);
                java.util.Date fDesactivacion = formatoFecha.parse(fechaDesactivacion);
                java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
                java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());
                
                consulta.setDate(3, fActivacionSQL);
                consulta.setDate(4, fDesactivacionSQL);
                
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
        catch(ParseException ex){System.out.println(ex); }

        
        return resultado;
    }
    public boolean EliminarRolUsuario(String p_idusuario, String p_idrol)
    {
        boolean resultado = false;
        
        try
        {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("DELETE FROM seguridad.rolesusuario s " +
                                                                        "WHERE  s.idrol = ? AND s.idusuario = ? "
                        );
                consulta.setInt(1, Integer.parseInt(p_idrol) );
                consulta.setInt(2, Integer.parseInt(p_idusuario));
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
    public boolean EditarRolUsuario(String p_idusuario, String p_idrol, String fechaActivacion, String fechaDesactivacion)
    {
        boolean resultado = false;
        
        try
        {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("Update seguridad.rolesusuario Set fechaactivacion = ?, fechadesactivacion = ? " +
                                                                        "WHERE  idrol = ? AND idusuario = ? "
                        );
                
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date fActivacion = formatoFecha.parse(fechaActivacion);
                java.util.Date fDesactivacion = formatoFecha.parse(fechaDesactivacion);
                java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
                java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());
                
                consulta.setDate(1, fActivacionSQL);
                consulta.setDate(2, fDesactivacionSQL);
                consulta.setInt(3, Integer.parseInt(p_idrol) );
                consulta.setInt(4, Integer.parseInt(p_idusuario));
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
        catch(ParseException ex){System.out.println(ex); }

        
        return resultado;
    }
}
