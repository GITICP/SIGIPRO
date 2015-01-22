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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class RolUsuarioDAO {

  public boolean insertarRolUsuario(int idusuario, int idrol, String fechaActivacion, String fechaDesactivacion) {
    boolean resultado = false;

    try {
      SingletonBD s = SingletonBD.getSingletonBD();
      Connection conexion = s.conectar();

      if (conexion != null) {
        PreparedStatement consulta = conexion.prepareStatement("INSERT INTO SEGURIDAD.roles_usuarios "
                + " (id_usuario, id_rol, fecha_activacion, fecha_desactivacion) "
                + " VALUES "
                + " (?,?,?,? )");
        consulta.setInt(1, idusuario);
        consulta.setInt(2, idrol);

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fActivacion = formatoFecha.parse(fechaActivacion);
        java.util.Date fDesactivacion = formatoFecha.parse(fechaDesactivacion);
        java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
        java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());

        consulta.setDate(3, fActivacionSQL);
        consulta.setDate(4, fDesactivacionSQL);

        int resultadoConsulta = consulta.executeUpdate();
        if (resultadoConsulta == 1) {
          resultado = true;
        }
        consulta.close();
        conexion.close();
      }
    } catch (SQLException ex) {
      System.out.println(ex);
    } catch (ParseException ex) {
      System.out.println(ex);
    }

    return resultado;
  }

  public boolean EliminarRolUsuario(String p_idusuario, String p_idrol) {
    boolean resultado = false;

    try {
      SingletonBD s = SingletonBD.getSingletonBD();
      Connection conexion = s.conectar();

      if (conexion != null) {
        PreparedStatement consulta = conexion.prepareStatement("DELETE FROM seguridad.rolesusuario s "
                + "WHERE  s.idrol = ? AND s.idusuario = ? "
        );
        consulta.setInt(1, Integer.parseInt(p_idrol));
        consulta.setInt(2, Integer.parseInt(p_idusuario));
        int resultadoConsulta = consulta.executeUpdate();
        if (resultadoConsulta == 1) {
          resultado = true;
        }
        consulta.close();
        conexion.close();
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return resultado;
  }

  public boolean EditarRolUsuario(String p_idusuario, String p_idrol, String fechaActivacion, String fechaDesactivacion) {
    boolean resultado = false;

    try {
      SingletonBD s = SingletonBD.getSingletonBD();
      Connection conexion = s.conectar();

      if (conexion != null) {
        PreparedStatement consulta = conexion.prepareStatement("Update seguridad.rolesusuario Set fechaactivacion = ?, fechadesactivacion = ? "
                + "WHERE  idrol = ? AND idusuario = ? "
        );

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fActivacion = formatoFecha.parse(fechaActivacion);
        java.util.Date fDesactivacion = formatoFecha.parse(fechaDesactivacion);
        java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
        java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());

        consulta.setDate(1, fActivacionSQL);
        consulta.setDate(2, fDesactivacionSQL);
        consulta.setInt(3, Integer.parseInt(p_idrol));
        consulta.setInt(4, Integer.parseInt(p_idusuario));
        int resultadoConsulta = consulta.executeUpdate();
        if (resultadoConsulta == 1) {
          resultado = true;
        }
        consulta.close();
        conexion.close();
      }
    } catch (SQLException ex) {
      System.out.println(ex);
    } catch (ParseException ex) {
      System.out.println(ex);
    }

    return resultado;
  }

  public List<RolUsuario> parsearRoles(String roles, int idUsuario) {
    List<RolUsuario> resultado = null;
    try {
      resultado = new ArrayList<RolUsuario>();
      List<String> rolesParcial = new LinkedList<String>(Arrays.asList(roles.split("#r#")));
      rolesParcial.remove("");
      for (String i : rolesParcial) {
        String[] rol = i.split("#c#");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fActivacion = formatoFecha.parse(rol[1]);
        java.util.Date fDesactivacion = formatoFecha.parse(rol[2]);
        java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
        java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());
        resultado.add(new RolUsuario(Integer.parseInt(rol[0]), idUsuario, fActivacionSQL, fDesactivacionSQL));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      resultado = null;
    }
    return resultado;
  }
  public List<RolUsuario> parsearUsuarios(String roles, int idRol) {
    List<RolUsuario> resultado = null;
    try {
      resultado = new ArrayList<RolUsuario>();
      List<String> rolesParcial = new LinkedList<String>(Arrays.asList(roles.split("#r#")));
      rolesParcial.remove("");
      for (String i : rolesParcial) {
        String[] rol = i.split("#c#");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fActivacion = formatoFecha.parse(rol[1]);
        java.util.Date fDesactivacion = formatoFecha.parse(rol[2]);
        java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
        java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());
        resultado.add(new RolUsuario(idRol, Integer.parseInt(rol[0]), fActivacionSQL, fDesactivacionSQL));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      resultado = null;
    }
    return resultado;
  }

}
