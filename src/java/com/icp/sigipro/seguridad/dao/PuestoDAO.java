/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.seguridad.modelos.Puesto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walter
 */
public class PuestoDAO {

    public boolean insertarPuesto(Puesto puesto) {
        boolean resultado = false;

        try {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();

            if (conexion != null) {
                PreparedStatement consulta = conexion.prepareStatement("INSERT INTO SEGURIDAD.puestos "
                        + " ( nombre_puesto, descripcion) "
                        + " VALUES "
                        + " (?,? ) RETURNING id_puesto");
                consulta.setString(1, puesto.getNombre_puesto());
                consulta.setString(2, puesto.getDescripcion());
                ResultSet resultadoConsulta = consulta.executeQuery();
                if (resultadoConsulta.next()) {
                    resultado = true;
                    puesto.setId_puesto(resultadoConsulta.getInt("id_puesto"));

                }
                consulta.close();
                conexion.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return resultado;
    }

    public boolean editarPuesto(int idpuesto, String nombre, String descripcion) {
        boolean resultado = false;

        try {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();

            if (conexion != null) {
                PreparedStatement consulta = conexion.prepareStatement("UPDATE SEGURIDAD.puestos "
                        + " SET nombre_puesto = ?, descripcion = ? "
                        + " WHERE id_puesto = ? ");

                consulta.setString(1, nombre);
                consulta.setString(2, descripcion);
                consulta.setInt(3, idpuesto);

                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1) {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return resultado;
    }

    public boolean EliminarPuesto(String p_idpuesto) {
        boolean resultado = false;

        try {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();

            if (conexion != null) {
                PreparedStatement consulta = conexion.prepareStatement("DELETE FROM SEGURIDAD.puestos p "
                        + "WHERE  p.id_puesto = ? "
                );
                consulta.setInt(1, Integer.parseInt(p_idpuesto));
                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1) {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return resultado;
    }

    public boolean validarNombrePuesto(String nombre, int id_puesto) {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        boolean resultado = false;

        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT nombre_puesto FROM seguridad.puestos WHERE nombre_puesto =? and id_puesto <> ? ");
                consulta.setString(1, nombre);
                consulta.setInt(2, id_puesto);

                ResultSet resultadoConsulta = consulta.executeQuery();
                if (!resultadoConsulta.next()) {
                    resultado = true;
                }
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }

    public List<Puesto> obtenerPuestos() {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        List<Puesto> resultado = null;

        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT p.id_puesto, p.nombre_puesto, p.descripcion "
                        + "FROM seguridad.puestos p");
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarPuestos(resultadoConsulta);
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            } catch (SQLException ex) {
                resultado = null;
            }
        }
        return resultado;
    }

    @SuppressWarnings("Convert2Diamond")
    private List<Puesto> llenarPuestos(ResultSet resultadoConsulta) throws SQLException {
        List<Puesto> resultado = new ArrayList<Puesto>();

        while (resultadoConsulta.next()) {
            String nombrePuesto = resultadoConsulta.getString("nombre_puesto");
            int idPuesto = resultadoConsulta.getInt("id_puesto");
            String descripcion = resultadoConsulta.getString("descripcion");

            resultado.add(new Puesto(idPuesto, nombrePuesto, descripcion));
        }
        return resultado;
    }

}
