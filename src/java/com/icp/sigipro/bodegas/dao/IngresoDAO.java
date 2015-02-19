/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.bodegas.modelos.Ingreso;
import com.icp.sigipro.core.DAO;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Boga
 */
public class IngresoDAO extends DAO<Ingreso>
{
  public IngresoDAO(){
    super(Ingreso.class, "bodega", "ingresos");
  }
  
  @Override
  public Ingreso buscar(Long id)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<Ingreso> buscarPor(String[] campos, Object valor)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean actualizar(Ingreso param)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean eliminar(Ingreso param)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
  @Override
  public List<Ingreso> obtenerTodo() throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException
  {
    String codigoConsulta = "SELECT * FROM " + nombreModulo + "." + nombreTabla + " i "
                          + " INNER JOIN bodega.catalogo_interno ci on ci.id_producto = " + "i.id_producto "
                          + " INNER JOIN seguridad.secciones s on s.id_seccion = i.id_seccion "
                         + " WHERE i.estado <> 'En Cuarentena'";
    PreparedStatement consulta = getConexion().prepareStatement(codigoConsulta);
    return construirLista(ejecutarConsulta(consulta));
  }
  
  public List<Ingreso> obtenerCuarentena() throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException
  {
    String codigoConsulta = "SELECT * FROM " + nombreModulo + "." + nombreTabla + " i "
                          + " INNER JOIN bodega.catalogo_interno ci on ci.id_producto = " + "i.id_producto "
                          + " INNER JOIN seguridad.secciones s on s.id_seccion = i.id_seccion "
                          + " WHERE i.estado = 'En Cuarentena'";
    PreparedStatement consulta = getConexion().prepareStatement(codigoConsulta);
    return construirLista(ejecutarConsulta(consulta));
  }
}

