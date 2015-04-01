/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bodegas.modelos.Prestamo;
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
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.solicitudes ( id_usuario, id_inventario, cantidad, fecha_solicitud, estado) "
              + " VALUES (?,?,?,?,?) RETURNING id_solicitud");
      

      consulta.setInt(1, p.getId_usuario());
      consulta.setInt(2, p.getId_inventario());
      consulta.setInt(3, p.getCantidad());
      consulta.setDate(4, p.getFecha_solicitudAsDate());
      consulta.setString(5, p.getEstado());
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
  
  public boolean insertarSolicitud_Prestamo(Solicitud p, Prestamo pr) {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.solicitudes ( id_usuario, id_inventario, cantidad, fecha_solicitud, estado) "
              + " VALUES (?,?,?,?,?) RETURNING id_solicitud");
      

      consulta.setInt(1, p.getId_usuario());
      consulta.setInt(2, p.getId_inventario());
      consulta.setInt(3, p.getCantidad());
      consulta.setDate(4, p.getFecha_solicitudAsDate());
      consulta.setString(5, p.getEstado());
      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        PrestamoDAO prDAO = new PrestamoDAO();
        int id_solicitud = resultadoConsulta.getInt("id_solicitud");
        pr.setId_solicitud(id_solicitud);
        boolean resultado_prestamo = prDAO.insertarPrestamo(pr);
        if (resultado_prestamo)
        {resultado = true;}
        
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
              " UPDATE bodega.solicitudes"
              + " zSET cantidad=?, estado=?, fecha_entrega=?, id_usuario_recibo=?, observaciones=?"
              + " WHERE id_solicitud=? AND cantidad <= (Select cantidad from bodega.inventarios where id_inventario =? ) "
      );

      consulta.setInt(1, p.getCantidad());
      consulta.setString(2, p.getEstado());
      consulta.setDate(3, p.getFecha_entregaAsDate());
      if (p.getId_usuario_recibo() == 0)
      {consulta.setNull(4, java.sql.Types.INTEGER );}
      else
      {consulta.setInt(4, p.getId_usuario_recibo());}
      consulta.setString(5, p.getObservaciones());
      consulta.setInt(6, p.getId_solicitud());
      consulta.setInt(7, p.getId_inventario());


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
        solicitud.setId_inventario(rs.getInt("id_inventario"));
        solicitud.setId_usuario(rs.getInt("id_usuario"));
        solicitud.setCantidad(rs.getInt("cantidad"));
        solicitud.setFecha_solicitud(rs.getDate("fecha_solicitud"));
        solicitud.setEstado(rs.getString("estado"));
        solicitud.setFecha_entrega(rs.getDate("fecha_entrega"));
        solicitud.setId_usuario_recibo(rs.getInt("id_usuario_recibo"));
        solicitud.setObservaciones(rs.getString("observaciones"));
        try {
            UsuarioDAO usr = new UsuarioDAO();
            InventarioDAO pr = new InventarioDAO();
            solicitud.setUsuario(usr.obtenerUsuario(rs.getInt("id_usuario")));
            solicitud.setInventario(pr.obtenerInventario(rs.getInt("id_inventario")));
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
        consulta = getConexion().prepareStatement(" SELECT * FROM bodega.solicitudes Where estado != 'Pendiente Prestamo' ORDER BY fecha_solicitud DESC");
      } else {
        consulta = getConexion().prepareStatement(" SELECT * FROM bodega.solicitudes s inner join seguridad.usuarios u on s.id_usuario = u.id_usuario Where u.id_seccion = ? AND s.estado != 'Pendiente Prestamo' ORDER BY s.id_solicitud DESC");
        consulta.setInt(1, id_usuario);
      }

      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Solicitud solicitud = new Solicitud();
        solicitud.setId_solicitud(rs.getInt("id_solicitud"));
        solicitud.setId_inventario(rs.getInt("id_inventario"));
        solicitud.setId_usuario(rs.getInt("id_usuario"));
        solicitud.setCantidad(rs.getInt("cantidad"));
        solicitud.setFecha_solicitud(rs.getDate("fecha_solicitud"));
        solicitud.setEstado(rs.getString("estado"));
        solicitud.setFecha_entrega(rs.getDate("fecha_entrega"));
        solicitud.setId_usuario_recibo(rs.getInt("id_usuario_recibo"));
        solicitud.setObservaciones(rs.getString("observaciones"));
        try {
          UsuarioDAO usr = new UsuarioDAO();
          InventarioDAO pr = new InventarioDAO();
          solicitud.setUsuario(usr.obtenerUsuario(rs.getInt("id_usuario")));
          solicitud.setInventario(pr.obtenerInventario(rs.getInt("id_inventario")));
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
