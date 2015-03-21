/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.configuracion.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.configuracion.modelos.*;
import com.icp.sigipro.utilidades.UtilidadEmail;
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
public class CorreoDAO {

  public List<String> obtenerListaCorreo() {
    List<String> resultado = new ArrayList<String>();

    try {
      SingletonBD s = SingletonBD.getSingletonBD();
      Connection conexion = s.conectar();

      if (conexion != null) {
        PreparedStatement consulta = conexion.prepareStatement("SELECT * "
                + " FROM configuracion.correo");
        ResultSet resultadoConsulta = consulta.executeQuery();
        if (resultadoConsulta.next()) {
          String host;
          host = resultadoConsulta.getString("host");
          resultado.add(host);
          String puerto;
          puerto = resultadoConsulta.getString("puerto");
          resultado.add(puerto);
          String starttls;
          starttls = resultadoConsulta.getString("starttls");
          resultado.add(starttls);
          String nombreemisor;
          nombreemisor = resultadoConsulta.getString("nombre_emisor");
          resultado.add(nombreemisor);
          String correo;
          correo = resultadoConsulta.getString("correo");
          resultado.add(correo);
          String contrasena;
          contrasena = resultadoConsulta.getString("contrasena");
          resultado.add(contrasena);
        }
        resultadoConsulta.close();
        consulta.close();
        conexion.close();
      }
    } catch (SQLException ex) {
      System.out.println(ex);
    }

    return resultado;
  }

  public Correo obtenerCorreo() {
    Correo resultado = null;

    try {
      SingletonBD s = SingletonBD.getSingletonBD();
      Connection conexion = s.conectar();

      if (conexion != null) {
        PreparedStatement consulta = conexion.prepareStatement("SELECT * "
                + " FROM configuracion.correo");
        ResultSet resultadoConsulta = consulta.executeQuery();
        if (resultadoConsulta.next()) {
          Integer id;
          id = resultadoConsulta.getInt("id_correo");
          String host;
          host = resultadoConsulta.getString("host");
          String puerto;
          puerto = resultadoConsulta.getString("puerto");
          String starttls;
          starttls = resultadoConsulta.getString("starttls");
          String nombreemisor;
          nombreemisor = resultadoConsulta.getString("nombre_emisor");
          String correo;
          correo = resultadoConsulta.getString("correo");
          String contrasena;
          contrasena = resultadoConsulta.getString("contrasena");

          resultado = new Correo(id, host, puerto, starttls, nombreemisor, correo, contrasena);
        }
        consulta.close();
        resultadoConsulta.close();
        conexion.close();
      }
    } catch (SQLException ex) {
      System.out.println(ex);
    }

    return resultado;
  }

  public boolean editarConfiguracionCorreo(String host, String puerto, String emisor, String contrasena, String correo, int id) {
    boolean resultado = false;

    SingletonBD s = SingletonBD.getSingletonBD();
    Connection conexion = s.conectar();

    try {
      if (conexion != null) {
        
        List<String> l = new ArrayList<String>();
        
        l.add(host);
        l.add(puerto);
        l.add("true");
        l.add(emisor);
        l.add(correo);
        l.add(contrasena);
        
        UtilidadEmail u = UtilidadEmail.getSingletonUtilidadEmail();
        if ( !u.probarNuevaConfiguracion(l) )
        {
           throw new SQLException();
        }
        
        PreparedStatement consulta = conexion.prepareStatement("UPDATE configuracion.correo "
                + " SET puerto = ?, nombre_emisor = ?, correo = ?, contrasena = ?"
                + " WHERE id_correo = ? ");

        consulta.setString(1, puerto);
        consulta.setString(2, emisor);
        consulta.setString(3, correo);
        consulta.setString(4, contrasena);
        consulta.setInt(5, id);
        consulta.executeUpdate();
        
        consulta.close();
        conexion.close();
        
        resultado = true;
      }
    } 
    catch (SQLException ex) {
      System.out.println(ex);
      resultado = false;
    }
    return resultado;
  }
}
