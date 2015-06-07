/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;
import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bioterio.modelos.AnalisisParasitologico;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class AnalisisParasitologicoDAO {
  private Connection conexion;
  public AnalisisParasitologicoDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
    conexion = s.conectar();
  }
  public boolean insertarAnalisisParasitologico(AnalisisParasitologico p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.analisis_parasitologicos (fecha, numero_informe, especie, resultados,"
              + " tratamiento_dosis, recetado_por, fecha_tratamiento, responsable)"  
              + " VALUES (?,?,?,?,?,?,?,?) RETURNING id_analisis");
      
      consulta.setDate(1, p.getFecha());
      consulta.setString(2, p.getNumero_informe());
      consulta.setBoolean(3, p.isEspecie());
      consulta.setString(4, p.getResultados());
      consulta.setString(5, p.getTratamiento_dosis());
      consulta.setString(6, p.getRecetado_por());
      consulta.setDate(7, p.getFecha_tratamiento());
      consulta.setInt(8, p.getResponsable().getID());
      
      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
      }
      resultadoConsulta.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
        ex.printStackTrace();
      throw new SIGIPROException("Se produjo un error al procesar el ingreso");
    }
    return resultado;
  }

  public boolean editarAnalisisParasitologico(AnalisisParasitologico p) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE bioterio.analisis_parasitologicos "
              + " SET numero_informe=?, especie=?, resultados=?, tratamiento_dosis=?, recetado_por=?, fecha_tratamiento=?, responsable=?"
              + " WHERE id_analisis=?; "
      );

      consulta.setString(1, p.getNumero_informe());
      consulta.setBoolean(2, p.isEspecie());
      consulta.setString(3, p.getResultados());
      consulta.setString(4, p.getTratamiento_dosis());
      consulta.setString(5, p.getRecetado_por());
      consulta.setDate(6, p.getFecha_tratamiento());
      consulta.setInt(7, p.getResponsable().getID());
      consulta.setInt(8, p.getId_analisis());
      
      if (consulta.executeUpdate() == 1) {
        resultado = true;
      }
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
        ex.printStackTrace();
      throw new SIGIPROException("Se produjo un error al procesar la edición");
    }
    return resultado;
  }

  public boolean eliminarAnalisisParasitologico(int id_analisis) throws SIGIPROException {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " DELETE FROM bioterio.analisis_parasitologicos "
              + " WHERE id_analisis=?; "
      );

      consulta.setInt(1, id_analisis);

      if (consulta.executeUpdate() == 1) {
        resultado = true;
      }
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
        ex.printStackTrace();
      throw new SIGIPROException("Se produjo un error al procesar la eliminación");
    }
    return resultado;
  }

  public AnalisisParasitologico obtenerAnalisisParasitologico(int id) throws SIGIPROException {

    AnalisisParasitologico analisis_parasitologico = new AnalisisParasitologico();

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
                                                " SELECT ap.* "
                                              + " FROM bioterio.analisis_parasitologicos ap "
                                              + " WHERE  id_analisis = ?;");

      consulta.setInt(1, id);

      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        UsuarioDAO usr = new UsuarioDAO();
        analisis_parasitologico.setId_analisis(rs.getInt("id_analisis"));
        analisis_parasitologico.setFecha(rs.getDate("fecha"));
        analisis_parasitologico.setNumero_informe(rs.getString("numero_informe"));
        analisis_parasitologico.setEspecie(rs.getBoolean("especie"));
        analisis_parasitologico.setResultados(rs.getString("resultados"));
        analisis_parasitologico.setTratamiento_dosis(rs.getString("tratamiento_dosis"));
        analisis_parasitologico.setFecha_tratamiento(rs.getDate("fecha_tratamiento"));
        analisis_parasitologico.setResponsable(usr.obtenerUsuario(rs.getInt("responsable")));
        analisis_parasitologico.setRecetado_por(rs.getString("recetado_por"));
        
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
        ex.printStackTrace();
      throw new SIGIPROException("Se produjo un error al procesar la solicitud");
    }
    return analisis_parasitologico;
  }

  public List<AnalisisParasitologico> obtenerAnalisisParasitologicos(boolean especie) throws SIGIPROException {

    List<AnalisisParasitologico> resultado = new ArrayList<AnalisisParasitologico>();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * "
                                              + " FROM bioterio.analisis_parasitologicos ap INNER JOIN seguridad.usuarios u ON ap.responsable = u.id_usuario"
                                              + " WHERE ap.especie = ?; ");
      consulta.setBoolean(1, especie);
      
      ResultSet rs = consulta.executeQuery();

      while (rs.next()) {
        Usuario usr;
        AnalisisParasitologico analisis_parasitologico = new AnalisisParasitologico();
        analisis_parasitologico.setId_analisis(rs.getInt("id_analisis"));
        analisis_parasitologico.setFecha(rs.getDate("fecha"));
        analisis_parasitologico.setNumero_informe(rs.getString("numero_informe"));
        analisis_parasitologico.setEspecie(rs.getBoolean("especie"));
        analisis_parasitologico.setResultados(rs.getString("resultados"));
        analisis_parasitologico.setTratamiento_dosis(rs.getString("tratamiento_dosis"));
        usr = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre_usuario"), rs.getString("correo"), rs.getString("nombre_completo"), rs.getString("cedula"), rs.getInt("id_seccion"), rs.getInt("id_puesto"), rs.getDate("fecha_activacion"), rs.getDate("fecha_desactivacion"), rs.getBoolean("estado"));
        analisis_parasitologico.setRecetado_por(rs.getString("recetado_por"));        
        analisis_parasitologico.setResponsable(usr);
        analisis_parasitologico.setFecha_tratamiento(rs.getDate("fecha_tratamiento"));
        resultado.add(analisis_parasitologico);
      }
      rs.close();
      consulta.close();
      cerrarConexion();
    } catch (Exception ex) {
        ex.printStackTrace();
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
          ex.printStackTrace();
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
          ex.printStackTrace();
        conexion = null;
      }
    }
  }
}
