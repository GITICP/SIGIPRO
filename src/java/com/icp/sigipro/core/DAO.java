/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core;

import com.icp.sigipro.basededatos.SingletonBD;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    public void cerrarConexion()
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

    public List<T> obtenerTodo() throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        String codigoConsulta = "SELECT * FROM " + nombreModulo + "." + nombreTabla;
        PreparedStatement consulta = getConexion().prepareStatement(codigoConsulta);
        return construirLista(ejecutarConsulta(consulta));
    }

    public int insertar(T param) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, SQLException
    {
        PreparedStatement objetoConsulta = construirInsertar(param);
        return ejecutarConsultaSinResultado(objetoConsulta);
    }

    public PreparedStatement construirInsertar(T param) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, SQLException
    {
        String accionTabla = "INSERT INTO " + this.nombreModulo + "." + this.nombreTabla;
        String columnas = "(";
        String valores = "VALUES (";
        String returning = " RETURNING ";

        List<Object> lista = new ArrayList<Object>();

        List<PropiedadModelo> camposYGetters = param.getMetodos("get");
        for (PropiedadModelo tupla : camposYGetters) {
            if (tupla.getCampo().getType().getName().startsWith("com.icp.sigipro")) {
                IModelo o = (IModelo) tupla.getMetodo().invoke(param);
                List<PropiedadModelo> metodosAsociacion = o.getMetodos("get");
                for (PropiedadModelo tuplaAsociacion : metodosAsociacion) {
                    if (tuplaAsociacion.getCampo().getName().startsWith("id")) {
                        Object resultadoGet = tuplaAsociacion.getMetodo().invoke(o);
                        columnas += tuplaAsociacion.getCampo().getName() + ",";
                        valores += "?,";
                        lista.add(resultadoGet);
                    }
                }
            }
            else {
                if (!tupla.getCampo().getName().toLowerCase().startsWith("id")) {
                    Object resultadoGet = tupla.getMetodo().invoke(param);
                    columnas += tupla.getCampo().getName() + ",";
                    valores += "?,";
                    lista.add(resultadoGet);
                }
                else {
                    returning += tupla.getCampo().getName();
                }
            }
        }

        String columnasFinal = columnas.substring(0, columnas.length() - 1);
        String valoresFinal = valores.substring(0, valores.length() - 1);
        String consulta = accionTabla + columnasFinal + ")" + valoresFinal + ")" + returning + ";";

        return construirConsulta(consulta, lista);
    }

    protected List<T> construirLista(ResultSet resultadoConsulta) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        List<T> resultado = new ArrayList<T>();
        T pivote = tipo.newInstance();

        List<PropiedadModelo> camposYSetters = pivote.getMetodos("set");
        while (resultadoConsulta.next()) {
            T t = construirObjeto(camposYSetters, resultadoConsulta);
            resultado.add(t);
        }
        resultadoConsulta.close();
        return resultado;
    }

    protected T construirObjeto(List<PropiedadModelo> propiedades, ResultSet resultadoConsulta) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        T t = tipo.newInstance();
        for (PropiedadModelo tupla : propiedades) {
            if (tupla.getCampo().getType().getName().startsWith("com.icp.sigipro")) {
                IModelo o = (IModelo) tupla.getCampo().getType().newInstance();
                List<PropiedadModelo> metodosAsociacion = o.getMetodos("set");
                for (PropiedadModelo tuplaAsociacion : metodosAsociacion) {
                    if (!tuplaAsociacion.getCampo().getType().getName().startsWith("com.icp.sigipro")) {
                        tuplaAsociacion.getMetodo().invoke(o, resultadoConsulta.getObject(tuplaAsociacion.getCampo().getName()));
                    }
                }
                tupla.getMetodo().invoke(t, o);
            }
            else {
                Object objeto = resultadoConsulta.getObject(tupla.getCampo().getName());
                tupla.getMetodo().invoke(t, objeto);
            }
        }
        return t;
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
        ResultSet rs = consulta.executeQuery();
        return rs;
    }

    protected int ejecutarConsultaSinResultado(PreparedStatement consulta) throws SQLException
    {
        return consulta.executeUpdate();
    }
}
