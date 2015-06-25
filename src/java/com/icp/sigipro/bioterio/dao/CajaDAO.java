/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bioterio.modelos.Caja;
import com.icp.sigipro.bioterio.modelos.Grupohembras;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class CajaDAO extends DAO
{
    public CajaDAO() {  }

    public boolean insertarCaja(Caja p) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.cajas (numero, id_grupo)"
                                                                        + " VALUES (?,?) RETURNING id_caja");

            consulta.setInt(1, p.getNumero());
            consulta.setInt(2, p.getGrupo().getId_grupo());

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

//  public boolean editarCaja(Caja p) throws SIGIPROException {
//
//    boolean resultado = false;
//
//    try {
//      PreparedStatement consulta = getConexion().prepareStatement(
//              " UPDATE bioterio.cajas "
//              + " SET   numero=?"
//              + " WHERE id_caja=?; "
//      );
//
//      consulta.setInt(1, p.getNumero());
//      consulta.setInt(2, p.getId_caja());
//      
//      if (consulta.executeUpdate() == 1) {
//        resultado = true;
//      }
//      consulta.close();
//      cerrarConexion();
//    } catch (Exception ex) {
//      throw new SIGIPROException("Se produjo un error al procesar la edición");
//    }
//    return resultado;
//  }
//  
    public boolean eliminarCaja(int id_caja) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM bioterio.cajas "
                    + " WHERE id_caja=?; "
            );

            consulta.setInt(1, id_caja);

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

    public Caja obtenerCaja(int id) throws SIGIPROException
    {

        Caja caja = new Caja();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.cajas c INNER JOIN bioterio.gruposhembras g ON g.id_grupo =c.id_grupo  where c.id_caja = ?");

            consulta.setInt(1, id);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                caja.setId_caja(rs.getInt("id_caja"));
                caja.setNumero(rs.getInt("numero"));
                Grupohembras g = new Grupohembras();
                g.setId_grupo(rs.getInt("id_grupo"));
                g.setIdentificador(rs.getString("identificador"));
                g.setCantidad_espacios(rs.getInt("cantidad_espacios"));
                caja.setGrupo(g);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return caja;
    }

    public List<Caja> obtenerCajas(int id_grupo) throws SIGIPROException
    {

        List<Caja> resultado = new ArrayList<Caja>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement("SELECT * FROM bioterio.cajas c INNER JOIN bioterio.gruposhembras g ON g.id_grupo =c.id_grupo  where c.id_grupo=?");
            consulta.setInt(1, id_grupo);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Caja caja = new Caja();
                caja.setId_caja(rs.getInt("id_caja"));
                caja.setNumero(rs.getInt("numero"));
                Grupohembras g = new Grupohembras();
                g.setId_grupo(rs.getInt("id_grupo"));
                g.setIdentificador(rs.getString("identificador"));
                g.setCantidad_espacios(rs.getInt("cantidad_espacios"));
                caja.setGrupo(g);
                resultado.add(caja);
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
