/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;

import com.icp.sigipro.bioterio.modelos.ConejoProduccion;
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
public class ConejoProduccionDAO extends DAO
{

    public ConejoProduccionDAO()
    {
    }

    public boolean insertarConejoProduccion(ConejoProduccion p) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.conejos_produccion (identificador, cantidad, detalle_procedencia, mortalidad)"
                                                                        + " VALUES (?,?,?,?) RETURNING id_produccion");

            consulta.setString(1, p.getIdentificador());
            consulta.setInt(2, p.getCantidad());
            consulta.setString(3, p.getDetalle_procedencia());
            consulta.setInt(4, -1);

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

    public boolean editarConejoProduccion(ConejoProduccion p) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE bioterio.conejos_produccion "
                    + " SET  identificador=?, cantidad=?, detalle_procedencia=?, mortalidad=?"
                    + " WHERE id_produccion=?; "
            );
            consulta.setString(1, p.getIdentificador());
            consulta.setInt(2, p.getCantidad());
            consulta.setString(3, p.getDetalle_procedencia());
            consulta.setInt(4, p.getMortalidad());
            consulta.setInt(5, p.getId_produccion());

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

    public boolean eliminarConejoProduccion(int id_produccion) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM bioterio.conejos_produccion "
                    + " WHERE id_produccion=?; "
            );

            consulta.setInt(1, id_produccion);

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

    public ConejoProduccion obtenerConejoProduccion(int id) throws SIGIPROException
    {

        ConejoProduccion produccion = new ConejoProduccion();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.conejos_produccion where id_produccion = ?");

            consulta.setInt(1, id);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                produccion.setId_produccion(rs.getInt("id_produccion"));
                produccion.setIdentificador(rs.getString("identificador"));
                produccion.setCantidad(rs.getInt("cantidad"));
                produccion.setMortalidad(rs.getInt("mortalidad"));
                produccion.setDetalle_procedencia(rs.getString("detalle_procedencia"));
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return produccion;
    }

    public List<ConejoProduccion> obtenerConejosProduccion() throws SIGIPROException
    {

        List<ConejoProduccion> resultado = new ArrayList<ConejoProduccion>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.conejos_produccion");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                ConejoProduccion produccion = new ConejoProduccion();
                produccion.setId_produccion(rs.getInt("id_produccion"));
                produccion.setIdentificador(rs.getString("identificador"));
                produccion.setCantidad(rs.getInt("cantidad"));
                produccion.setMortalidad(rs.getInt("mortalidad"));
                produccion.setDetalle_procedencia(rs.getString("detalle_procedencia"));
                resultado.add(produccion);
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
