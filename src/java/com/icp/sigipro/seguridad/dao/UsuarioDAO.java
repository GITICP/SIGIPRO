 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.seguridad.modelos.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class UsuarioDAO {

  @SuppressWarnings("Convert2Diamond")
  private List<Usuario> llenarUsuarios(ResultSet resultadoConsulta) throws SQLException {
    List<Usuario> resultado = new ArrayList<Usuario>();

    while (resultadoConsulta.next()) {
      int idUsuario = resultadoConsulta.getInt("id_usuario");
      String nombreUsuario = resultadoConsulta.getString("nombre_usuario");
      String correo = resultadoConsulta.getString("correo");
      String nombreCompleto = resultadoConsulta.getString("nombre_completo");
      String cedula = resultadoConsulta.getString("cedula");
      String departamento = resultadoConsulta.getString("departamento");
      String puesto = resultadoConsulta.getString("puesto");
      Date fechaActivacion = resultadoConsulta.getDate("fecha_activacion");
      Date fechaDesactivacion = resultadoConsulta.getDate("fecha_desactivacion");
      boolean activo = resultadoConsulta.getBoolean("estado");

      resultado.add(new Usuario(idUsuario, nombreUsuario, correo, nombreCompleto,
              cedula, departamento, puesto, fechaActivacion, fechaDesactivacion, activo));
    }
    return resultado;
  }

  public int validarInicioSesion(String usuario, String contrasenna) {
    SingletonBD s = SingletonBD.getSingletonBD();
    Connection conexion = s.conectar();
    int resultado = -1;

    if (conexion != null) {
      try {
        PreparedStatement consulta = conexion.prepareStatement("SELECT id_usuario "
                + "FROM seguridad.usuarios us "
                + "WHERE us.nombre_usuario = ? and us.contrasena = ? "
                + "AND us.estado = true ");
        consulta.setString(1, usuario);
        String hash = md5(contrasenna);
        consulta.setString(2, hash);
        ResultSet resultadoConsulta = consulta.executeQuery();
        resultadoConsulta.next();
        resultado = resultadoConsulta.getInt("id_usuario");
        resultadoConsulta.close();
        consulta.close();
        conexion.close();
      } catch (SQLException ex) {
        System.out.println(ex);
      }
    }
    return resultado;
  }

  public Usuario obtenerUsuario(int idUsuario) {
    Usuario resultado = null;
    try {
      SingletonBD s = SingletonBD.getSingletonBD();
      Connection conexion = s.conectar();

      PreparedStatement consulta = conexion.prepareStatement(" Select nombre_usuario, correo, nombre_completo, cedula, departamento, "
              + " puesto, fecha_activacion, fecha_desactivacion, estado "
              + " From seguridad.usuarios"
              + " Where id_usuario = ? ");

      consulta.setInt(1, idUsuario);

      ResultSet resultadoConsulta = consulta.executeQuery();

      if (resultadoConsulta.next()) {
        resultado = new Usuario();

        String nombreUsuario = resultadoConsulta.getString("nombre_usuario");
        String correo = resultadoConsulta.getString("correo");
        String nombreCompleto = resultadoConsulta.getString("nombre_completo");
        String cedula = resultadoConsulta.getString("cedula");
        String departamento = resultadoConsulta.getString("departamento");
        String puesto = resultadoConsulta.getString("puesto");
        Date fechaActivacion = resultadoConsulta.getDate("fecha_activacion");
        Date fechaDesactivacion;
        fechaDesactivacion = resultadoConsulta.getDate("fecha_desactivacion");
        boolean activo = resultadoConsulta.getBoolean("estado");

        resultado.setIdUsuario(idUsuario);
        resultado.setNombreUsuario(nombreUsuario);
        resultado.setCorreo(correo);
        resultado.setNombreCompleto(nombreCompleto);
        resultado.setCedula(cedula);
        resultado.setDepartamento(departamento);
        resultado.setPuesto(puesto);
        resultado.setFechaActivacion(fechaActivacion);
        resultado.setFechaDesactivacion(fechaDesactivacion);
        resultado.setActivo(activo);
      }
    } catch (SQLException ex) {

    }

    return resultado;
  }
  
   public int obtenerIDUsuario(String nombre) {
    int resultado = 0;
    try {
      SingletonBD s = SingletonBD.getSingletonBD();
      Connection conexion = s.conectar();

      PreparedStatement consulta = conexion.prepareStatement(" Select id_usuario "
              + " From seguridad.usuarios"
              + " Where nombre_usuario = ? ");

      consulta.setString(1, nombre);

      ResultSet resultadoConsulta = consulta.executeQuery();

      if (resultadoConsulta.next()) {
        
        resultado = resultadoConsulta.getInt("id_usuario");
      }
    } catch (SQLException ex) {

    }

    return resultado;
  }

  public boolean insertarUsuario(String nombreUsuario, String nombreCompleto, String correoElectronico,
          String cedula, String departamento, String puesto, String fechaActivacion, String fechaDesactivacion) {
    boolean resultado = false;

    try {
      SingletonBD s = SingletonBD.getSingletonBD();
      Connection conexion = s.conectar();

      if (conexion != null) {
        PreparedStatement consulta = conexion.prepareStatement("INSERT INTO SEGURIDAD.usuarios "
                + " (nombre_usuario, contrasena,  nombre_completo, correo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) "
                + " VALUES "
                + " (?,?,?,?,?,?,?,?,?,? )");
        consulta.setString(1, nombreUsuario);
        consulta.setString(2, md5("sigipro"));
        consulta.setString(3, nombreCompleto);
        consulta.setString(4, correoElectronico);
        consulta.setString(5, cedula);
        consulta.setString(6, departamento);
        consulta.setString(7, puesto);

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fActivacion = formatoFecha.parse(fechaActivacion);
        java.util.Date fDesactivacion = formatoFecha.parse(fechaDesactivacion);
        java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
        java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());

        consulta.setDate(8, fActivacionSQL);
        consulta.setDate(9, fDesactivacionSQL);

        consulta.setBoolean(10, compararFechas(fActivacionSQL, fDesactivacionSQL));

        int resultadoConsulta = consulta.executeUpdate();
        if (resultadoConsulta == 1) {
          resultado = true;
        }
        consulta.close();
        conexion.close();
      }
    } catch (SQLException ex) {
      String hola = "hola";
      hola.toCharArray();
    } catch (ParseException ex) {
      String hola = "hola";
      hola.toCharArray();
    }

    return resultado;
  }

  public boolean editarUsuario(int idUsuario, String nombreCompleto, String correoElectronico,
          String cedula, String departamento, String puesto, String fechaActivacion,
          String fechaDesactivacion, List<RolUsuario> p_roles) 
  {  
    boolean resultado = false;
    
    SingletonBD s = SingletonBD.getSingletonBD();
    Connection conexion = s.conectar();
    
    try 
    {
      if (conexion != null) {
        conexion.setAutoCommit(false);

        PreparedStatement consulta = conexion.prepareStatement("UPDATE SEGURIDAD.usuarios "
                + " SET correo = ?, nombre_completo = ?, cedula = ?, departamento = ?, puesto = ?, fecha_activacion = ?, fecha_desactivacion= ?, estado = ?"
                + " WHERE id_usuario = ? ");

        consulta.setString(1, correoElectronico);
        consulta.setString(2, nombreCompleto);
        consulta.setString(3, cedula);
        consulta.setString(4, departamento);
        consulta.setString(5, puesto);

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fActivacion = formatoFecha.parse(fechaActivacion);
        java.util.Date fDesactivacion = formatoFecha.parse(fechaDesactivacion);
        java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
        java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());

        consulta.setDate(6, fActivacionSQL);
        consulta.setDate(7, fDesactivacionSQL);

        consulta.setBoolean(8, compararFechas(fActivacionSQL, fDesactivacionSQL));

        consulta.setInt(9, idUsuario);

        List<PreparedStatement> operaciones = new ArrayList<PreparedStatement>();
        PreparedStatement eliminarRolesUsuario = conexion.prepareStatement("Delete from seguridad.roles_usuarios where id_usuario = ?");
        eliminarRolesUsuario.setInt(1, idUsuario);

        operaciones.add(consulta);
        operaciones.add(eliminarRolesUsuario);

        String insert = " INSERT INTO seguridad.roles_usuarios (id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (?,?,?,?)";

        for (RolUsuario i : p_roles) 
        {
          PreparedStatement upsertTemp = conexion.prepareStatement(insert);
          upsertTemp.setInt(1, i.getIDUsuario());
          upsertTemp.setInt(2, i.getIDRol());
          upsertTemp.setDate(3, i.getFechaActivacionSQL());
          upsertTemp.setDate(4, i.getFechaDesactivacionSQL());
          operaciones.add(upsertTemp);
        }

        for (PreparedStatement operacion : operaciones) 
        {
          int resultadoOperacion = operacion.executeUpdate();
          operacion.close();
        }
        conexion.commit();
        conexion.close();
        resultado = true;
      }
    }
    catch (SQLException ex) 
    {
      ex.printStackTrace();
      try
      {
        resultado = false;
        conexion.rollback();
      }
      catch(SQLException sqlEx)
      {
        sqlEx.printStackTrace();
      }      
    } 
    catch (ParseException ex) 
    {
      resultado = false;
      ex.printStackTrace();
    }
    return resultado;
  }

  public boolean desactivarUsuario(int idUsuario) {
    boolean resultado = false;

    try {
      SingletonBD s = SingletonBD.getSingletonBD();
      Connection conexion = s.conectar();

      PreparedStatement consulta = conexion.prepareStatement("UPDATE SEGURIDAD.usuarios "
              + " SET estado = false "
              + " WHERE id_usuario = ? ");

      consulta.setInt(1, idUsuario);

      int resultadoConsulta = consulta.executeUpdate();
      if (resultadoConsulta == 1) 
      {
        resultado = true;
      }
      consulta.close();
      conexion.close();
    } 
    catch (SQLException ex) 
    {
      ex.printStackTrace();
    }
    return resultado;
  }

  public List<Usuario> obtenerUsuarios() {
    SingletonBD s = SingletonBD.getSingletonBD();
    Connection conexion = s.conectar();

    List<Usuario> resultado = null;

    if (conexion != null) {
      try {
        PreparedStatement consulta;
        consulta = conexion.prepareStatement("SELECT us.id_usuario, us.nombre_usuario, us.correo, us.nombre_completo, us.cedula, "
                + "us.departamento, us.puesto, us.fecha_activacion, us.fecha_desactivacion, us.estado "
                + "FROM seguridad.usuarios us");
        ResultSet resultadoConsulta = consulta.executeQuery();
        resultado = llenarUsuarios(resultadoConsulta);
        resultadoConsulta.close();
        conexion.close();
      } catch (SQLException ex) {
        resultado = null;
      }
    }
    return resultado;
  }

  private boolean compararFechas(Date fechaActivacion, Date fechaDesactivacion) {
    java.util.Calendar calendario = java.util.Calendar.getInstance();
    java.util.Date hoy = calendario.getTime();
    Date hoySQL = new Date(hoy.getTime());
    boolean resultado
            = ((fechaActivacion.before(hoySQL)
            && fechaDesactivacion.after(hoySQL))
            || fechaActivacion.equals(fechaDesactivacion));
    return resultado;
  }

  private String md5(String texto) {
    String resultado = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.reset();
      md.update(texto.getBytes());
      byte[] digest = md.digest();
      BigInteger bigInt = new BigInteger(1, digest);
      resultado = bigInt.toString(16);

      while (resultado.length() < 32) {
        resultado = "0" + resultado;
      }
    } catch (NoSuchAlgorithmException ex) {
      //Imprimir error
    }

    return resultado;
  }

  public List<RolUsuario> obtenerRolesUsuario(String p_IdUsuario) {
    SingletonBD s = SingletonBD.getSingletonBD();
    Connection conexion = s.conectar();
    List<RolUsuario> resultado = null;

    if (conexion != null) {
      try {
        PreparedStatement consulta;
        consulta = conexion.prepareStatement("SELECT r.nombre, ru.id_rol, ru.id_usuario, ru.fecha_activacion, ru.fecha_desactivacion "
                + "FROM seguridad.roles r inner join seguridad.roles_usuarios ru  on r.id_rol = ru.id_rol AND ru.id_usuario = ? ");
        consulta.setInt(1, Integer.parseInt(p_IdUsuario));
        ResultSet resultadoConsulta = consulta.executeQuery();
        resultado = llenarRolesUsuario(resultadoConsulta);
        resultadoConsulta.close();
        conexion.close();
      } catch (SQLException ex) {
        resultado = null;
      }
    }
    return resultado;
  }

  public List<Rol> obtenerRolesRestantes(String p_IdUsuario) {
    SingletonBD s = SingletonBD.getSingletonBD();
    Connection conexion = s.conectar();
    List<Rol> resultado = null;

    if (conexion != null) {
      try {
        PreparedStatement consulta;
        consulta = conexion.prepareStatement("SELECT r.id_rol, r.nombre, r.descripcion "
                + "FROM seguridad.roles r "
                + "WHERE r.id_rol NOT IN (SELECT ru.id_rol FROM seguridad.roles_usuarios ru WHERE ru.id_usuario = ?)");
        consulta.setInt(1, Integer.parseInt(p_IdUsuario));
        ResultSet resultadoConsulta = consulta.executeQuery();
        resultado = llenarRoles(resultadoConsulta);
        resultadoConsulta.close();
        conexion.close();
      } catch (SQLException ex) {
        resultado = null;
      }
    }
    return resultado;
  }

  @SuppressWarnings("Convert2Diamond")
  private List<RolUsuario> llenarRolesUsuario(ResultSet resultadoConsulta) throws SQLException {
    List<RolUsuario> resultado = new ArrayList<RolUsuario>();

    while (resultadoConsulta.next()) {
      int idUsuario = resultadoConsulta.getInt("id_usuario");
      String nombreRol = resultadoConsulta.getString("nombre");
      int idRol = resultadoConsulta.getInt("id_rol");
      Date fechaActivacion = resultadoConsulta.getDate("fecha_activacion");
      Date fechaDesactivacion = resultadoConsulta.getDate("fecha_desactivacion");

      resultado.add(new RolUsuario(idRol, idUsuario, fechaActivacion, fechaDesactivacion, nombreRol));
    }
    return resultado;
  }

  @SuppressWarnings("Convert2Diamond")
  private List<Rol> llenarRoles(ResultSet resultadoConsulta) throws SQLException {
    List<Rol> resultado = new ArrayList<Rol>();

    while (resultadoConsulta.next()) {
      String nombreRol = resultadoConsulta.getString("nombre");
      int idRol = resultadoConsulta.getInt("id_rol");
      String descripcionrol = resultadoConsulta.getString("descripcion");

      resultado.add(new Rol(idRol, nombreRol, descripcionrol));
    }
    return resultado;
  }
}
