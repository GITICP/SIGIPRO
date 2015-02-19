/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.bodegas.modelos.Inventario;
import com.icp.sigipro.core.DAO;
import java.util.List;

/**
 *
 * @author Boga
 */
public class InventarioDAO extends DAO<Inventario>
{
  
  InventarioDAO(){
    super(Inventario.class, "bodega", "inventaios");
  }
  
  @Override
  public Inventario buscar(Long id)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<Inventario> buscarPor(String[] campos, Object valor)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public int insertar(Inventario param)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean actualizar(Inventario param)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean eliminar(Inventario param)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
