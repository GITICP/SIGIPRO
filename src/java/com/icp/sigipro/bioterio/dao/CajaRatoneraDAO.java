/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bioterio.modelos.CajaRatonera;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class CajaRatoneraDAO {
  private Connection conexion;
  public CajaRatoneraDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }
  public boolean insertarCajaRatonera(CajaRatonera p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.cajas_ratonera (numero_caja, id_cepa)"  
              + " VALUES (?,?) RETURNING id_caja_ratonera");
      
      consulta.setInt(1, p.getNumero_caja());
      consulta.setInt(2, p.getCepa().getId_cepa());

      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
      }
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar el ingreso");
    }
    return resultado;
  }

  public boolean editarCajaRatonera(CajaRatonera p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bioterio.cajas_ratonera "
              + " SET  numero_caja=?, id_cepa=?"
              + " WHERE id_caja_ratonera=?; "
      );

      consulta.setInt(1, p.getNumero_caja());
      consulta.setInt(2, p.getCepa().getId_cepa());

      if (consulta.executeUpdate() == 1) {
        resultado = true;
      }
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la edición");
    }
    return resultado;
  }

  public boolean eliminarCajaRatonera(int id_caja_ratonera) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bioterio.cajas_ratonera "
              + " WHERE id_caja_ratonera=?; "
      );

      consulta.setInt(1, id_caja_ratonera);

      if (consulta.executeUpdate() == 1) {
        resultado = true;
      }
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la eliminación");
    }
    return resultado;
  }

  public CajaRatonera obtenerCajaRatonera(int id) throws SIGIPROException {

    CajaRatonera caja_ratonera = new CajaRatonera();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.cajas_ratonera where id_caja_ratonera = ?");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        caja_ratonera.setId_caja_ratonera(rs.getInt("id_caja_ratonera"));
        caja_ratonera.setNumero_caja(rs.getInt("numero_caja"));
        CepaDAO cDao = new CepaDAO();
        caja_ratonera.setCepa(cDao.obtenerCepa(rs.getInt("numero_hembras")));
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return caja_ratonera;
  }

  public List<CajaRatonera> obtenerCajasRatonera() throws SIGIPROException {

    List<CajaRatonera> resultado = new ArrayList<CajaRatonera>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.cajas_ratonera");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        CajaRatonera caja_ratonera = new CajaRatonera();
        caja_ratonera.setId_caja_ratonera(rs.getInt("id_caja_ratonera"));
        caja_ratonera.setNumero_caja(rs.getInt("numero_caja"));
        CepaDAO cDao = new CepaDAO();
        caja_ratonera.setCepa(cDao.obtenerCepa(rs.getInt("numero_hembras")));
        resultado.add(caja_ratonera);
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return resultado;
  }
  private Connection getConexion()
  {
    SingletonBD s = SingletonBD.getSingletonBD();
    if (conexion == null) {
      conexion = s.conectar();
    }
    else {
      try {
        if (conexion.isClosed()) {
          conexion = s.conectar();
        }
      }
      catch (Exception ex) {
        conexion = null;
      }
    }
    return conexion;
  }

  private void cerrarConexion()
  {
    if (conexion != null) {
      try {
        if (conexion.isClosed()) {
          conexion.close();
        }
      }
      catch (Exception ex) {
        conexion = null;
      }
    }
  }
}
