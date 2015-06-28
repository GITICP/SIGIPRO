/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.caballeriza.modelos.TipoEvento;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walter
 */
public class TipoEventoDAO extends DAO
{

    public TipoEventoDAO()
    {
    }

    public boolean insertarTipoEvento(TipoEvento te) throws SIGIPROException
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO caballeriza.tipos_eventos (nombre, descripcion) "
                                                                        + " VALUES (?,?) RETURNING id_tipo_evento");
            consulta.setString(1, te.getNombre());
            consulta.setString(2, te.getDescripcion());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
                te.setId_tipo_evento(resultadoConsulta.getInt("id_tipo_evento"));
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("El tipo de evento no pudo ser agregado correctamente.");
        }
        return resultado;
    }

    public boolean eliminarTipoEvento(int id_tipo_evento) throws SIGIPROException
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM caballeriza.tipos_eventos "
                    + " WHERE id_tipo_evento=?; "
            );
            consulta.setInt(1, id_tipo_evento);
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("El tipo de evento no pudo ser eliminado debido a que uno o más eventos se encuentran asociadas a este.");
        }
        return resultado;
    }

    public boolean editarTipoEvento(TipoEvento te) throws SIGIPROException
    {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE caballeriza.tipos_eventos "
                    + " SET nombre=?, descripcion=? "
                    + " WHERE id_tipo_evento=?; "
            );

            consulta.setString(1, te.getNombre());
            consulta.setString(2, te.getDescripcion());
            consulta.setInt(3, te.getId_tipo_evento());

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("El tipo de evento no pudo ser actualizado correctamente.");
        }
        return resultado;

    }

    public TipoEvento obtenerTipoEvento(int id_tipo_evento) throws SIGIPROException
    {
        TipoEvento tipoevento = new TipoEvento();
        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM caballeriza.tipos_eventos where id_tipo_evento = ?");
            consulta.setInt(1, id_tipo_evento);
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                tipoevento.setId_tipo_evento(rs.getInt("id_tipo_evento"));
                tipoevento.setNombre(rs.getString("nombre"));
                tipoevento.setDescripcion(rs.getString("descripcion"));
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("No se pudo obtener el tipo de evento correctamente.");
        }
        return tipoevento;
    }

    public List<TipoEvento> obtenerTiposEventos() throws SIGIPROException
    {
        List<TipoEvento> resultado = new ArrayList<TipoEvento>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement("select * from caballeriza.tipos_eventos where id_tipo_evento != 0");
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                TipoEvento tipo = new TipoEvento();
                tipo.setId_tipo_evento(rs.getInt("id_tipo_evento"));
                tipo.setNombre(rs.getString("nombre"));
                tipo.setDescripcion(rs.getString("descripcion"));
                resultado.add(tipo);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("No se pudo obtener los tipos de eventos correctamente.");
        }
        return resultado;
    }
}
