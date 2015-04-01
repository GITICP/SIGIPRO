/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;
import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bioterio.modelos.Cara;
import com.icp.sigipro.bioterio.modelos.Cepa;
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
public class CaraDAO {
  private Connection conexion;
  public CaraDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }
  public boolean insertarCara(Cara p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.caras (numero_cara, id_cepa, macho_as, hembra_as,fecha_apareamiento_i,fecha_apareamiento_f,"
              + "fecha_eliminacionmacho_i,fecha_eliminacionmacho_f,fecha_eliminacionhembra_i,fecha_eliminacionhembra_f,fecha_seleccionnuevos_i,fecha_seleccionnuevos_f,"
              + "fecha_reposicionciclo_i,fecha_reposicionciclo_f,fecha_vigencia) "
              + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id_cara");
      

      consulta.setInt(1, p.getNumero_cara());
      consulta.setInt(2,p.getCepa().getId_cepa());
      consulta.setString(3, p.getMacho_as());
      consulta.setString(4, p.getHembra_as());
      consulta.setDate(5, p.getFecha_apareamiento_i());
      consulta.setDate(6, p.getFecha_apareamiento_f());
      consulta.setDate(7, p.getFecha_eliminacionmacho_i());
      consulta.setDate(8, p.getFecha_eliminacionmacho_f());
      consulta.setDate(9, p.getFecha_eliminacionhembra_i());
      consulta.setDate(10, p.getFecha_eliminacionhembra_f());
      consulta.setDate(11, p.getFecha_seleccionnuevos_i());
      consulta.setDate(12, p.getFecha_seleccionnuevos_f());
      consulta.setDate(13, p.getFecha_reposicionciclo_i());
      consulta.setDate(14, p.getFecha_reposicionciclo_f());
      consulta.setDate(15, p.getFecha_vigencia());

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

  public boolean editarCara(Cara p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bioterio.caras "
              + " SET  numero_cara=?, id_cepa=?, macho_as=?, hembra_as=?,fecha_apareamiento_i=?,fecha_apareamiento_f=?,"
              + "fecha_eliminacionmacho_i=?,fecha_eliminacionmacho_f=?,fecha_eliminacionhembra_i=?,fecha_eliminacionhembra_f=?,fecha_seleccionnuevos_i=?,fecha_seleccionnuevos_f=?,"
              + "fecha_reposicionciclo_i=?,fecha_reposicionciclo_f=?,fecha_vigencia=? "
              + " WHERE id_cara=?; "
      );

      consulta.setInt(1, p.getNumero_cara());
      consulta.setInt(2,p.getCepa().getId_cepa());
      consulta.setString(3, p.getMacho_as());
      consulta.setString(4, p.getHembra_as());
      consulta.setDate(5, p.getFecha_apareamiento_i());
      consulta.setDate(6, p.getFecha_apareamiento_f());
      consulta.setDate(7, p.getFecha_eliminacionmacho_i());
      consulta.setDate(8, p.getFecha_eliminacionmacho_f());
      consulta.setDate(9, p.getFecha_eliminacionhembra_i());
      consulta.setDate(10, p.getFecha_eliminacionhembra_f());
      consulta.setDate(11, p.getFecha_seleccionnuevos_i());
      consulta.setDate(12, p.getFecha_seleccionnuevos_f());
      consulta.setDate(13, p.getFecha_reposicionciclo_i());
      consulta.setDate(14, p.getFecha_reposicionciclo_f());
      consulta.setDate(15, p.getFecha_vigencia());
      consulta.setInt(16, p.getId_cara());
      
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

  public boolean eliminarCara(int id_cara) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bioterio.caras "
              + " WHERE id_cara=?; "
      );

      consulta.setInt(1, id_cara);

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

  public Cara obtenerCara(int id) throws SIGIPROException {

    Cara cepa = new Cara();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.caras ca INNER JOIN bioterio.cepas ce ON ca.id_cepa = ce.id_cepa where id_cara = ?");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        cepa.setId_cara(rs.getInt("id_cara"));
        cepa.setNumero_cara(rs.getInt("numero_cara"));
        cepa.setMacho_as(rs.getString("macho_as"));
        cepa.setHembra_as(rs.getString("hembra_as"));
        cepa.setFecha_apareamiento_i(rs.getDate("fecha_apareamiento_i"));
        cepa.setFecha_apareamiento_f(rs.getDate("fecha_apareamiento_f"));
        cepa.setFecha_eliminacionmacho_i(rs.getDate("fecha_eliminacionmacho_i"));
        cepa.setFecha_eliminacionmacho_f(rs.getDate("fecha_eliminacionmacho_f"));
        cepa.setFecha_eliminacionhembra_i(rs.getDate("fecha_eliminacionhembra_i"));
        cepa.setFecha_eliminacionhembra_f(rs.getDate("fecha_eliminacionhembra_f"));
        cepa.setFecha_seleccionnuevos_i(rs.getDate("fecha_seleccionnuevos_i"));
        cepa.setFecha_seleccionnuevos_f(rs.getDate("fecha_seleccionnuevos_f"));
        cepa.setFecha_reposicionciclo_i(rs.getDate("fecha_reposicionciclo_i"));
        cepa.setFecha_reposicionciclo_f(rs.getDate("fecha_reposicionciclo_f"));
        cepa.setFecha_vigencia(rs.getDate("fecha_vigencia"));
        
        Cepa cep = new Cepa();
        cep.setId_cepa(rs.getInt("id_cepa"));
        cep.setNombre(rs.getString("nombre"));
        
        cepa.setCepa(cep);
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return cepa;
  }

  public List<Cara> obtenerCaras() throws SIGIPROException {

    List<Cara> resultado = new ArrayList<Cara>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.caras ca INNER JOIN bioterio.cepas ce ON ca.id_cepa = ce.id_cepa");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Cara cepa = new Cara();
        cepa.setId_cara(rs.getInt("id_cara"));
        cepa.setNumero_cara(rs.getInt("numero_cara"));
        cepa.setMacho_as(rs.getString("macho_as"));
        cepa.setHembra_as(rs.getString("hembra_as"));
        cepa.setFecha_apareamiento_i(rs.getDate("fecha_apareamiento_i"));
        cepa.setFecha_apareamiento_f(rs.getDate("fecha_apareamiento_f"));
        cepa.setFecha_eliminacionmacho_i(rs.getDate("fecha_eliminacionmacho_i"));
        cepa.setFecha_eliminacionmacho_f(rs.getDate("fecha_eliminacionmacho_f"));
        cepa.setFecha_eliminacionhembra_i(rs.getDate("fecha_eliminacionhembra_i"));
        cepa.setFecha_eliminacionhembra_f(rs.getDate("fecha_eliminacionhembra_f"));
        cepa.setFecha_seleccionnuevos_i(rs.getDate("fecha_seleccionnuevos_i"));
        cepa.setFecha_seleccionnuevos_f(rs.getDate("fecha_seleccionnuevos_f"));
        cepa.setFecha_reposicionciclo_i(rs.getDate("fecha_reposicionciclo_i"));
        cepa.setFecha_reposicionciclo_f(rs.getDate("fecha_reposicionciclo_f"));
        cepa.setFecha_vigencia(rs.getDate("fecha_vigencia"));
        
        Cepa cep = new Cepa();
        cep.setId_cepa(rs.getInt("id_cepa"));
        cep.setNombre(rs.getString("nombre"));
        
        cepa.setCepa(cep);
        
        resultado.add(cepa);
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
