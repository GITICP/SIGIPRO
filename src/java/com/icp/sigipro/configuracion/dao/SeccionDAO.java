/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.configuracion.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.configuracion.modelos.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Walter
 */
public class SeccionDAO
{

    public boolean insertarSeccion(Seccion seccion)
    {
        boolean resultado = false;

        try {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();

            if (conexion != null) {
                PreparedStatement consulta = conexion.prepareStatement("INSERT INTO SEGURIDAD.secciones "
                                                                       + " ( nombre_seccion, descripcion) "
                                                                       + " VALUES "
                                                                       + " (?,? ) RETURNING id_seccion");
                consulta.setString(1, seccion.getNombre_seccion());
                consulta.setString(2, seccion.getDescripcion());
                ResultSet resultadoConsulta = consulta.executeQuery();
                if (resultadoConsulta.next()) {
                    resultado = true;
                    seccion.setId_seccion(resultadoConsulta.getInt("id_seccion"));

                }
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return resultado;
    }

    public boolean editarSeccion(int idseccion, String nombre, String descripcion)
    {
        boolean resultado = false;

        try {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();

            if (conexion != null) {
                PreparedStatement consulta = conexion.prepareStatement("UPDATE SEGURIDAD.secciones "
                                                                       + " SET nombre_seccion = ?, descripcion = ? "
                                                                       + " WHERE id_seccion = ? ");

                consulta.setString(1, nombre);
                consulta.setString(2, descripcion);
                consulta.setInt(3, idseccion);

                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1) {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return resultado;
    }

    public boolean EliminarSeccion(String p_idseccion)
    {
        boolean resultado = false;

        try {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();

            if (conexion != null) {
                PreparedStatement consulta = conexion.prepareStatement("DELETE FROM SEGURIDAD.secciones s "
                                                                       + "WHERE  s.id_seccion = ? "
                );
                consulta.setInt(1, Integer.parseInt(p_idseccion));
                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1) {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return resultado;
    }

    public boolean validarNombreSeccion(String nombre, int id_seccion)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        boolean resultado = false;

        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT nombre_seccion FROM seguridad.secciones WHERE nombre_seccion =? and id_seccion <> ? ");
                consulta.setString(1, nombre);
                consulta.setInt(2, id_seccion);

                ResultSet resultadoConsulta = consulta.executeQuery();
                if (!resultadoConsulta.next()) {
                    resultado = true;
                }

                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }

    public List<Seccion> obtenerSecciones()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        List<Seccion> resultado = null;

        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT s.id_seccion, s.nombre_seccion, s.descripcion "
                                                     + "FROM seguridad.secciones s");
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarSecciones(resultadoConsulta);
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                resultado = null;
            }
        }
        return resultado;
    }

    public Seccion obtenerSeccion(int id)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        Seccion resultado = new Seccion();

        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT s.nombre_seccion, s.descripcion "
                                                     + "FROM seguridad.secciones s WHERE s.id_seccion = ?");
                consulta.setInt(1, id);
                ResultSet resultadoConsulta = consulta.executeQuery();
                if (resultadoConsulta.next()) {
                    resultado.setNombre_seccion(resultadoConsulta.getString("nombre_seccion"));
                    resultado.setId_seccion(id);
                    resultado.setDescripcion(resultadoConsulta.getString("descripcion"));
                }
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                resultado = null;
            }
        }
        return resultado;
    }

    @SuppressWarnings("Convert2Diamond")
    private List<Seccion> llenarSecciones(ResultSet resultadoConsulta) throws SQLException
    {
        List<Seccion> resultado = new ArrayList<Seccion>();

        while (resultadoConsulta.next()) {
            String nombreSeccion = resultadoConsulta.getString("nombre_seccion");
            int idSeccion = resultadoConsulta.getInt("id_seccion");
            String descripcion = resultadoConsulta.getString("descripcion");

            resultado.add(new Seccion(idSeccion, nombreSeccion, descripcion));
        }
        return resultado;
    }

}
