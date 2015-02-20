/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bodegas.modelos.Solicitud;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class SolicitudDAO {

  private Connection conexion;

  public SolicitudDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }

  public boolean insertarSolicitud(Solicitud p) {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.solicitudes (id_solicitud, id_usuario, id_producto, cantidad, fecha_solicitud, estado) "
              + " VALUES (?,?,?,?,?,?) RETURNING id_solicitud");
      

      consulta.setInt(1, p.getId_solicitud());
      consulta.setInt(2, p.getId_usuario());
      consulta.setInt(3, p.getId_producto());
      consulta.setInt(4, p.getCantidad());
      consulta.setDate(5, p.getFecha_solicitudAsDate());
      consulta.setString(6, p.getEstado());
      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
      }
      consulta.close();
      conexion.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return resultado;
  }

  public boolean editarSolicitud(Solicitud p) {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bodega.solicitudes "
              + " SET cantidad=?, estado=?, fecha_entrega=?, id_usuario_recibo=?"
              + " WHERE id_solicitud=?; "
      );

      consulta.setInt(1, p.getCantidad());
      consulta.setString(2, p.getEstado());
      consulta.setDate(3, p.getFecha_entregaAsDate());
      consulta.setInt(4, p.getId_usuario_recibo());
      consulta.setInt(5, p.getId_solicitud());

      if (consulta.executeUpdate() == 1) {
        resultado = true;
      }
      consulta.close();
      conexion.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return resultado;
  }

  public boolean eliminarSolicitud(int id_solicitud) {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bodega.solicitudes "
              + " WHERE id_solicitud=?; "
      );

      consulta.setInt(1, id_solicitud);

      if (consulta.executeUpdate() == 1) {
        resultado = true;
      }
      consulta.close();
      conexion.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return resultado;
  }

  public Solicitud obtenerSolicitud(int id) {

    Solicitud solicitud = new Solicitud();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bodega.solicitudes where id_solicitud = ?");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        solicitud.setId_solicitud(rs.getInt("id_solicitud"));
        solicitud.setId_producto(rs.getInt("id_producto"));
        solicitud.setId_usuario(rs.getInt("id_usuario"));
        solicitud.setCantidad(rs.getInt("cantidad"));
        solicitud.setFecha_solicitud(rs.getDate("fecha_solicitud"));
        solicitud.setEstado(rs.getString("estado"));
        solicitud.setFecha_entrega(rs.getDate("fecha_entrega"));
        solicitud.setId_usuario_recibo(rs.getInt("id_usuario_recibo"));
        try {
            UsuarioDAO usr = new UsuarioDAO();
            ProductoInternoDAO pr = new ProductoInternoDAO();
            solicitud.setUsuario(usr.obtenerUsuario(rs.getInt("id_usuario")));
            solicitud.setProducto(pr.obtenerProductoInterno(rs.getInt("id_usuario")));
            solicitud.setUsuarioReceptor(usr.obtenerUsuario(rs.getInt("id_usuario_recibo")));
          
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return solicitud;
  }

  public List<Solicitud> obtenerSolicitudes(int id_usuario) {

    List<Solicitud> resultado = new ArrayList<Solicitud>();

    try {
      PreparedStatement consulta;
      if (id_usuario == 0) {
        consulta = getConexion().prepareStatement(" SELECT * FROM bodega.solicitudes ");
      } else {
        consulta = getConexion().prepareStatement(" SELECT * FROM bodega.solicitudes Where id_usuario = ? ");
        consulta.setInt(1, id_usuario);
      }

      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Solicitud solicitud = new Solicitud();
        solicitud.setId_solicitud(rs.getInt("id_solicitud"));
        solicitud.setId_producto(rs.getInt("id_producto"));
        solicitud.setId_usuario(rs.getInt("id_usuario"));
        solicitud.setCantidad(rs.getInt("cantidad"));
        solicitud.setFecha_solicitud(rs.getDate("fecha_solicitud"));
        solicitud.setEstado(rs.getString("estado"));
        solicitud.setFecha_entrega(rs.getDate("fecha_entrega"));
        solicitud.setId_usuario_recibo(rs.getInt("id_usuario_recibo"));
        try {
          UsuarioDAO usr = new UsuarioDAO();
          ProductoInternoDAO pr = new ProductoInternoDAO();
          solicitud.setUsuario(usr.obtenerUsuario(rs.getInt("id_usuario")));
          solicitud.setProducto(pr.obtenerProductoInterno(rs.getInt("id_usuario")));
          solicitud.setUsuarioReceptor(usr.obtenerUsuario(rs.getInt("id_usuario_recibo")));
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        resultado.add(solicitud);
      }

      consulta.close();
      conexion.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return resultado;
  }

  private Connection getConexion() {
    try {

      if (conexion.isClosed()) {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
      }
    } catch (Exception ex) {
      conexion = null;
    }

    return conexion;
  }
}
