/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;
import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bioterio.modelos.Caja;
import com.icp.sigipro.bioterio.modelos.Grupohembras;
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
public class GrupohembrasDAO {
  private Connection conexion;
  public GrupohembrasDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }
  public boolean insertarGrupohembras(Grupohembras p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.gruposhembras (identificador, cantidad_espacios)"  
              + " VALUES (?,?) RETURNING id_grupo");
      
      consulta.setString(1, p.getIdentificador());
      consulta.setInt(2, p.getCantidad_espacios());
      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        int id_g = resultadoConsulta.getInt("id_grupo");
        crearCajas(id_g);
        resultado = true;
      }
      resultadoConsulta.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar el ingreso");
    }
    return resultado;
  }

  public boolean editarGrupohembras(Grupohembras p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bioterio.gruposhembras "
              + " SET   identificador=?, cantidad_espacios=?"
              + " WHERE id_grupo=?; "
      );

      consulta.setString(1, p.getIdentificador());
      consulta.setInt(2, p.getCantidad_espacios());
      consulta.setInt(3, p.getId_grupo());
      
      if (consulta.executeUpdate() == 1) {
        editarCajas(p);
        resultado = true;
      }
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la edición");
    }
    return resultado;
  }
  

  public boolean eliminarGrupohembras(int id_grupohembras) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bioterio.gruposhembras "
              + " WHERE id_grupo=?; "
      );

      consulta.setInt(1, id_grupohembras);

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

  public Grupohembras obtenerGrupohembras(int id) throws SIGIPROException {

    Grupohembras grupohembras = new Grupohembras();

    try {
      PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.gruposhembras  where id_grupo = ?");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        grupohembras.setId_grupo(rs.getInt("id_grupo"));
        grupohembras.setIdentificador(rs.getString("identificador"));
        grupohembras.setCantidad_espacios(rs.getInt("cantidad_espacios"));
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return grupohembras;
  }

  public List<Grupohembras> obtenerGruposhembras() throws SIGIPROException {

    List<Grupohembras> resultado = new ArrayList<Grupohembras>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.gruposhembras");
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Grupohembras grupohembras = new Grupohembras();
        grupohembras.setId_grupo(rs.getInt("id_grupo"));
        grupohembras.setIdentificador(rs.getString("identificador"));
        grupohembras.setCantidad_espacios(rs.getInt("cantidad_espacios"));
        resultado.add(grupohembras);
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
  private void crearCajas(int id_grupo) throws SIGIPROException {
    Grupohembras grupo = obtenerGrupohembras(id_grupo);
    CajaDAO cajadao = new CajaDAO();
    for(int i=1; i<(grupo.getCantidad_espacios()+1); i++){
             Caja caja = new Caja();
             caja.setNumero(i);
             caja.setGrupo(grupo);
             cajadao.insertarCaja(caja);
         }
  }
  private void editarCajas(Grupohembras p) throws SIGIPROException{
    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bioterio.cajas "
              + " WHERE id_grupo=?; "
      );

      consulta.setInt(1, p.getId_grupo());

      if (consulta.executeUpdate() == 1) {
        crearCajas(p.getId_grupo());
      }
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
      throw new SIGIPROException("Se produjo un error al procesar la eliminación de cajas para el grupo");
    }
  }
}
