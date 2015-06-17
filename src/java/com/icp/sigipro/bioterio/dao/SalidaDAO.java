/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;

import com.icp.sigipro.bioterio.modelos.Salida;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class SalidaDAO extends DAO
{

    public SalidaDAO()
    {  }

    public boolean insertarSalida(Salida p) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.salidas (especie, fecha, cantidad, razon, observaciones)"
                                                                        + " VALUES (?,?,?,?,?) RETURNING id_salida");

            consulta.setBoolean(1, p.isEspecie());
            consulta.setDate(2, p.getFecha());
            consulta.setInt(3, p.getCantidad());
            consulta.setString(4, p.getRazon());
            consulta.setString(5, p.getObservaciones());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");
        }
        return resultado;
    }

    public boolean editarSalida(Salida p) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE bioterio.salidas "
                    + " SET cantidad=?, razon=?, observaciones=? "
                    + " WHERE id_salida=?;"
            );

            consulta.setInt(1, p.getCantidad());
            consulta.setString(2, p.getRazon());
            consulta.setString(3, p.getObservaciones());
            consulta.setInt(4, p.getId_salida());

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la edición");
        }
        return resultado;
    }

    public boolean eliminarSalida(int id_salida) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM bioterio.salidas "
                    + " WHERE id_salida=?; "
            );

            consulta.setInt(1, id_salida);

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la eliminación");
        }
        return resultado;
    }

    public Salida obtenerSalida(int id) throws SIGIPROException
    {

        Salida salida_parasitologico = new Salida();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT ap.* "
                    + " FROM bioterio.salidas ap "
                    + " WHERE  id_salida = ?;");

            consulta.setInt(1, id);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                salida_parasitologico.setId_salida(rs.getInt("id_salida"));
                salida_parasitologico.setFecha(rs.getDate("fecha"));
                salida_parasitologico.setEspecie(rs.getBoolean("especie"));
                salida_parasitologico.setCantidad(rs.getInt("cantidad"));
                salida_parasitologico.setRazon(rs.getString("razon"));
                salida_parasitologico.setObservaciones(rs.getString("observaciones"));

            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return salida_parasitologico;
    }

    public List<Salida> obtenerSalidas(boolean especie) throws SIGIPROException
    {

        List<Salida> resultado = new ArrayList<Salida>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * "
                                                      + " FROM bioterio.salidas ap "
                                                      + " WHERE ap.especie = ?; ");
            consulta.setBoolean(1, especie);

            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {

                Salida salida_parasitologico = new Salida();
                salida_parasitologico.setId_salida(rs.getInt("id_salida"));
                salida_parasitologico.setFecha(rs.getDate("fecha"));
                salida_parasitologico.setEspecie(rs.getBoolean("especie"));
                salida_parasitologico.setCantidad(rs.getInt("cantidad"));
                salida_parasitologico.setRazon(rs.getString("razon"));
                salida_parasitologico.setObservaciones(rs.getString("observaciones"));
                resultado.add(salida_parasitologico);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }
}
