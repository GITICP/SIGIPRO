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
public class PrestamoDAO {
  private Connection conexion;
  public PrestamoDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }

  public boolean insertarPrestamo(Prestamo p) {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.solicitudes_prestamos (id_solicitud, id_seccion_presta) "
              + " VALUES (?,?) RETURNING id_solicitud");
      

      consulta.setInt(1, p.getId_solicitud());
      consulta.setInt(2, p.getId_seccion_presta());
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

  public boolean editarPrestamo(Prestamo p) {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bodega.solicitudes_prestamos "
              + " SET id_seccion_presta=?, id_usuario_aprobo=? "
              + " WHERE id_solicitud=?; "
      );

      consulta.setInt(1, p.getId_seccion_presta());
      consulta.setInt(2, p.getId_usuario_aprobo());
      consulta.setInt(3, p.getId_solicitud());

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

  public boolean eliminarPrestamo(int id_solicitud) {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bodega.solicitudes_prestamos "
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

  public Prestamo obtenerPrestamo(int id) {

    Prestamo solicitud = new Prestamo();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bodega.solicitudes_prestamos where id_solicitud = ?");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        solicitud.setId_solicitud(rs.getInt("id_solicitud"));
        solicitud.setId_seccion_presta(rs.getInt("id_seccion_presta"));
        solicitud.setId_usuario_aprobo(rs.getInt("id_usuario_aprobo"));
       
        try {
            SolicitudDAO sol = new SolicitudDAO();
            solicitud.setSolicitud(sol.obtenerSolicitud(rs.getInt("id_solicitud")));
          
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return solicitud;
  }

  public List<Prestamo> obtenerPrestamos() {

    List<Prestamo> resultado = new ArrayList<Prestamo>();

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM bodega.solicitudes_prestamos ");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Prestamo solicitud = new Prestamo();
        solicitud.setId_solicitud(rs.getInt("id_solicitud"));
        solicitud.setId_seccion_presta(rs.getInt("id_seccion_presta"));
        solicitud.setId_usuario_aprobo(rs.getInt("id_usuario_aprobo"));
       
        try {
          SolicitudDAO sol = new SolicitudDAO();
          solicitud.setSolicitud(sol.obtenerSolicitud(rs.getInt("id_solicitud")));
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
