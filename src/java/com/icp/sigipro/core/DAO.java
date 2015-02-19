/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core;

import com.icp.sigipro.basededatos.SingletonBD;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public abstract class DAO<T extends IModelo>
{

  protected final Class<T> tipo;
  protected String nombreModulo;
  protected String nombreTabla;
  protected Connection conexion;

  public DAO(Class<T> claseT, String nomModulo, String nomTabla)
  {
    nombreTabla = nomTabla;
    nombreModulo = nomModulo;
    tipo = claseT;
  }

  protected Connection getConexion()
  {
    SingletonBD s = SingletonBD.getSingletonBD();
    if (conexion == null) {
      long startTime = System.currentTimeMillis();

      conexion = s.conectar();

      long endTime = System.currentTimeMillis();
      long duration = (endTime - startTime);
      System.out.println("Conectar: " + duration);
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
  
  public void cerrarConexion()
  {
    if (conexion != null){
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

  public abstract T buscar(Long id);

  public abstract List<T> buscarPor(String[] campos, Object valor);

  public List<T> obtenerTodo() throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException
  {
    String codigoConsulta = "SELECT * FROM " + nombreModulo + "." + nombreTabla ;
    PreparedStatement consulta = getConexion().prepareStatement(codigoConsulta);
    return construirLista(ejecutarConsulta(consulta));
  }

  public int insertar(T param) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, SQLException
  {
    String accionTabla = "INSERT INTO " + this.nombreModulo + "." + this.nombreTabla;
    String columnas = "(";
    String valores = "VALUES (";

    List<Object> lista = new ArrayList<Object>();

    List<Tupla<Field, Method>> camposYGetters = param.getMetodos("get");
    for (Tupla<Field, Method> tupla : camposYGetters) {
      if (tupla.x.getType().getName().startsWith("com.icp.sigipro")) {
        IModelo o = (IModelo) tupla.y.invoke(param);
        List<Tupla<Field, Method>> metodosAsociacion = o.getMetodos("get");
        for (Tupla<Field, Method> tuplaAsociacion : metodosAsociacion) {
          if (tuplaAsociacion.x.getName().startsWith("id")) {
            Object resultadoGet = tuplaAsociacion.y.invoke(o);
            columnas += tuplaAsociacion.x.getName() + ",";
            valores += "?,";
            lista.add(resultadoGet);
          }
        }
      } else {
        if (!tupla.x.getName().toLowerCase().startsWith("id")) {
          Object resultadoGet = tupla.y.invoke(param);
          columnas += tupla.x.getName() + ",";
          valores += "?,";
          lista.add(resultadoGet);
        }
      }
    }

    String columnasFinal = columnas.substring(0, columnas.length() - 1);
    String valoresFinal = valores.substring(0, valores.length() - 1);
    String consulta = accionTabla + columnasFinal + ")" + valoresFinal + ");";

    PreparedStatement objetoConsulta = construirConsulta(consulta, lista);

    return ejecutarConsultaSinResultado(objetoConsulta);
  }

  public abstract boolean actualizar(T param);

  public abstract boolean eliminar(T param);

  protected List<T> construirLista(ResultSet resultadoConsulta) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException
  {
    List<T> resultado = new ArrayList<T>();
    T pivote = tipo.newInstance();

    try {
      List<Tupla<Field, Method>> camposYSetters = pivote.getMetodos("set");
      while (resultadoConsulta.next()) {
        T t = tipo.newInstance();
        for (Tupla<Field, Method> tupla : camposYSetters) {
          if (tupla.x.getType().getName().startsWith("com.icp.sigipro")) {
            IModelo o = (IModelo) tupla.x.getType().newInstance();
            List<Tupla<Field, Method>> metodosAsociacion = o.getMetodos("set");
            for (Tupla<Field, Method> tuplaAsociacion : metodosAsociacion) {
              if (!tuplaAsociacion.x.getType().getName().startsWith("com.icp.sigipro")) {
                tuplaAsociacion.y.invoke(o, resultadoConsulta.getObject(tuplaAsociacion.x.getName()));
              }
            }
            tupla.y.invoke(t, o);
          }
          else {
            Object objeto = resultadoConsulta.getObject(tupla.x.getName());
            tupla.y.invoke(t, objeto);
          }
        }
        resultado.add(t);
      }
      resultadoConsulta.close();
    }
    catch (NoSuchMethodException ex) {
      ex.printStackTrace();
    }
    return resultado;
  }

  protected PreparedStatement construirConsulta(String consulta, Object... parametros) throws SQLException
  {
    if (parametros.length == consulta.length() - consulta.replace("?", "").length()) {

      PreparedStatement consultaPreparada = getConexion().prepareStatement(consulta);
      int indexConsulta = 1;

      for (Object param : parametros) {
        consultaPreparada.setObject(indexConsulta++, param);
      }
      return consultaPreparada;
    }
    else {
      throw new UnsupportedOperationException();
    }
  }

  protected PreparedStatement construirConsulta(String consulta, List<Object> parametros) throws SQLException
  {
    if (parametros.size() == consulta.length() - consulta.replace("?", "").length()) {

      PreparedStatement consultaPreparada = getConexion().prepareStatement(consulta);
      int indexConsulta = 1;

      for (Object param : parametros) {
        consultaPreparada.setObject(indexConsulta++, param);
      }
      return consultaPreparada;
    }
    else {
      throw new UnsupportedOperationException();
    }
  }

  protected ResultSet ejecutarConsulta(PreparedStatement consulta) throws SQLException
  {
    long startTime = System.currentTimeMillis();
    ResultSet rs = consulta.executeQuery();
    long endTime = System.currentTimeMillis();
    long duration = (endTime - startTime);
    System.out.println("Ejecutar: " + duration);

    return rs;
  }

  protected int ejecutarConsultaSinResultado(PreparedStatement consulta) throws SQLException
  {
    long startTime = System.currentTimeMillis();

    long endTime = System.currentTimeMillis();
    long duration = (endTime - startTime);
    System.out.println("Ejecutar: " + duration);
    
    return consulta.executeUpdate();
  }
}
