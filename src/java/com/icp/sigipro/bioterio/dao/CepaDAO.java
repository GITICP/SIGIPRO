/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;

import com.icp.sigipro.bioterio.modelos.Cepa;
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
public class CepaDAO extends DAO
{
    public CepaDAO() {  }

    public boolean insertarCepa(Cepa p) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.cepas (nombre, descripcion)"
                                                                        + " VALUES (?,?) RETURNING id_cepa");

            consulta.setString(1, p.getNombre());
            consulta.setString(2, p.getDescripcion());

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
            String mensaje = ex.getMessage();
            if (mensaje.contains("llave duplicada")) {
                throw new SIGIPROException("Error: El nombre de la cepa ya existe");
            }
            else {
                throw new SIGIPROException("Se produjo un error al procesar el ingreso");
            }
        }
        return resultado;
    }

    public boolean editarCepa(Cepa p) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE bioterio.cepas "
                    + " SET  nombre=?, descripcion=?"
                    + " WHERE id_cepa=?; "
            );

            consulta.setString(1, p.getNombre());
            consulta.setString(2, p.getDescripcion());
            consulta.setInt(3, p.getId_cepa());

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            String mensaje = ex.getMessage();
            if (mensaje.contains("llave duplicada")) {
                throw new SIGIPROException("Error: El nombre de la cepa ya existe");
            }
            else {
                throw new SIGIPROException("Se produjo un error al procesar la edición");
            }
        }
        return resultado;
    }

    public boolean eliminarCepa(int id_cepa) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM bioterio.cepas "
                    + " WHERE id_cepa=?; "
            );

            consulta.setInt(1, id_cepa);

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

    public Cepa obtenerCepa(int id) throws SIGIPROException
    {

        Cepa cepa = new Cepa();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.cepas where id_cepa = ?");

            consulta.setInt(1, id);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                cepa.setId_cepa(rs.getInt("id_cepa"));
                cepa.setNombre(rs.getString("nombre"));
                cepa.setDescripcion(rs.getString("descripcion"));
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return cepa;
    }

    public List<Cepa> obtenerCepas() throws SIGIPROException
    {

        List<Cepa> resultado = new ArrayList<Cepa>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.cepas");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Cepa cepa = new Cepa();
                cepa.setId_cepa(rs.getInt("id_cepa"));
                cepa.setNombre(rs.getString("nombre"));
                cepa.setDescripcion(rs.getString("descripcion"));

                resultado.add(cepa);
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
