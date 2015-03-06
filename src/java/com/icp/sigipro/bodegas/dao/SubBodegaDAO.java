/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.bodegas.modelos.SubBodega;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Boga
 */
public class SubBodegaDAO extends DAO<SubBodega>
{

  public SubBodegaDAO()
  {
    super(SubBodega.class, "bodega", "sub_bodegas");
  }

  @Override
  public int insertar(SubBodega param) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, SQLException
  {
    PreparedStatement objetoConsulta = getConexion().prepareStatement(" INSERT INTO " + this.nombreModulo + "." + this.nombreTabla
                                                                      + " (id_seccion, id_usuario, nombre) VALUES (?,?,?);");

    objetoConsulta.setInt(1, param.getSeccion().getId_seccion());
    objetoConsulta.setInt(2, param.getUsuario().getId_usuario());
    objetoConsulta.setString(3, param.getNombre());

    return ejecutarConsultaSinResultado(objetoConsulta);
  }

  @Override
  public SubBodega buscar(int id) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
  {

    String codigoConsulta = "SELECT sb.nombre, u.id_usuario, u.nombre_usuario, u.nombre_completo, s.id_seccion, s.nombre_seccion FROM " + nombreModulo + "." + nombreTabla + " sb INNER JOIN seguridad.secciones s on s.id_seccion = sb.id_seccion INNER JOIN seguridad.usuarios u on u.id_usuario = sb.id_usuario WHERE id_sub_bodega = ?";

    PreparedStatement consulta = getConexion().prepareStatement(codigoConsulta);
    consulta.setInt(1, id);
    ResultSet resultado = ejecutarConsulta(consulta);

    SubBodega s = new SubBodega();

    if (resultado.next()) {
      s.setId_sub_bodega(id);
      s.setNombre(resultado.getString("nombre"));

      Seccion seccion = new Seccion();
      seccion.setId_seccion(resultado.getInt("id_seccion"));
      seccion.setNombre_seccion(resultado.getString("nombre_seccion"));

      Usuario usuario = new Usuario();
      usuario.setId_usuario(resultado.getInt("id_usuario"));
      usuario.setNombreCompleto(resultado.getString("nombre_completo"));
      usuario.setNombreUsuario(resultado.getString("nombre_usuario"));

      s.setSeccion(seccion);
      s.setUsuario(usuario);
    }

    return s;
  }

  @Override
  public List<SubBodega> buscarPor(String[] campos, Object valor)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean actualizar(SubBodega param)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean eliminar(SubBodega param)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  public boolean insertar(SubBodega param, String[] idsIngresos, String[] idsEgresos) throws SIGIPROException
  {

    boolean resultado = false;
    try {
      PreparedStatement consultaInsertar = getConexion().prepareStatement(" INSERT INTO " + this.nombreModulo + "." + this.nombreTabla
                                                                        + " (id_seccion, id_usuario, nombre) VALUES (?,?,?) RETURNING id_sub_bodega");

      consultaInsertar.setInt(1, param.getSeccion().getId_seccion());
      consultaInsertar.setInt(2, param.getUsuario().getId_usuario());
      consultaInsertar.setString(3, param.getNombre());
      
      ResultSet idSubBodega = consultaInsertar.executeQuery();
      
      if (idSubBodega.next()) {
        param.setId_sub_bodega(idSubBodega.getInt("id_sub_bodega"));
      } else {
        throw new SIGIPROException("Error al insertar sub bodega");
      }
      
      PreparedStatement consultaEliminarIngresos = getConexion().prepareStatement("DELETE FROM bodega.usuarios_sub_bodegas_ingresos WHERE id_sub_bodega = ?");
      PreparedStatement consultaEliminarEgresos = getConexion().prepareStatement("DELETE FROM bodega.usuarios_sub_bodegas_egresos WHERE id_sub_bodega = ?");
      
      consultaEliminarIngresos.setInt(1, param.getId_sub_bodega());
      consultaEliminarEgresos.setInt(1, param.getId_sub_bodega());
      
      consultaEliminarIngresos.executeUpdate();
      consultaEliminarEgresos.executeUpdate();
      
      PreparedStatement insertarIngresos = getConexion().prepareStatement("INSERT INTO bodega.usuarios_sub_bodegas_ingresos (id_sub_bodega, id_usuario) VALUES (?,?)");
      PreparedStatement insertarEgresos = getConexion().prepareStatement("INSERT INTO bodega.usuarios_sub_bodegas_egresos (id_sub_bodega, id_usuario) VALUES (?,?)");
      
      for (String idIngreso : idsIngresos) {
        insertarIngresos.setInt(1, param.getId_sub_bodega());
        insertarIngresos.setInt(2, Integer.parseInt(idIngreso));
        insertarIngresos.addBatch();
      }
      
      for (String idEgreso : idsEgresos) {
        insertarEgresos.setInt(1, param.getId_sub_bodega());
        insertarEgresos.setInt(2, Integer.parseInt(idEgreso));
        insertarEgresos.addBatch();
      }
      
      insertarIngresos.executeBatch();
      insertarEgresos.executeBatch();
      
    } catch (SQLException ex) {
      throw new SIGIPROException("Error al insertar sub bodega");
    }
    
    return true;
  }

  public String[] parsearAsociacion(String pivote, String asociacionesCodificadas)
  {
    String[] idsTemp = asociacionesCodificadas.split(pivote);
    return Arrays.copyOfRange(idsTemp, 1, idsTemp.length);
  }
}
