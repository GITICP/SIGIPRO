/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.dao;
import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.seguridad.modelos.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SeccionDAO {
  
  public List<Seccion> obtenerSecciones(){
  SingletonBD s = SingletonBD.getSingletonBD();
  Connection conexion = s.conectar();

    List<Seccion> resultado = null;

    if (conexion != null) {
      try {
        PreparedStatement consulta;
        consulta = conexion.prepareStatement("SELECT * "
                + "FROM seguridad.secciones");
        ResultSet resultadoConsulta = consulta.executeQuery();
        resultado = llenarSecciones(resultadoConsulta);
        resultadoConsulta.close();
        consulta.close();
        conexion.close();
      } catch (SQLException ex) {
          ex.printStackTrace();
        resultado = null;
      }
    }
    return resultado;
  }

private List<Seccion> llenarSecciones(ResultSet resultadoConsulta) throws SQLException {
    List<Seccion> resultado = new ArrayList<Seccion>();
    
    while (resultadoConsulta.next()) {
      int id_seccion = resultadoConsulta.getInt("id_seccion");
      String nombre_seccion = resultadoConsulta.getString("nombre_seccion");
      String descripcion = resultadoConsulta.getString("descripcion");


      Seccion sec = new Seccion(id_seccion,nombre_seccion,descripcion);
      
      resultado.add(sec);
    }
    return resultado;
  }


}

