/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.produccion.modelos.Cronograma;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class CronogramaDAO extends DAO {

    public CronogramaDAO() {
    }

    public List<Cronograma> obtenerCronogramas() throws SIGIPROException {

        List<Cronograma> resultado = new ArrayList<Cronograma>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.cronograma; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Cronograma cronograma = new Cronograma();
                CronogramaDAO cDAO = new CronogramaDAO();
                
                cronograma.setId_cronograma(rs.getInt("id_cronograma"));
                cronograma.setNombre(rs.getString("nombre"));
                cronograma.setObservaciones(rs.getString("observaciones"));
                cronograma.setValido_desde(rs.getDate("valido_desde"));
                resultado.add(cronograma);

            }
            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }

    public Cronograma obtenerCronograma(int id_cronograma) throws SIGIPROException {

        Cronograma resultado = new Cronograma();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.cronograma where id_cronograma = ?; ");
            consulta.setInt(1, id_cronograma);
            ResultSet rs = consulta.executeQuery();
                while (rs.next()) {
                    resultado.setId_cronograma(rs.getInt("id_cronograma"));
                    resultado.setNombre(rs.getString("nombre"));
                    resultado.setObservaciones(rs.getString("observaciones"));
                    resultado.setValido_desde(rs.getDate("valido_desde"));
                }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }
    
    public int insertarCronograma(Cronograma c) throws SIGIPROException {

        int resultado=0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.cronograma (nombre, observaciones, valido_desde)"
                    + " VALUES (?,?,?) RETURNING id_cronograma");

            consulta.setString(1, c.getNombre());
            consulta.setString(2, c.getObservaciones());
            consulta.setDate(3, c.getValido_desde());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_cronograma");
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");
        }
        return resultado;
    }

    public boolean editarCronograma(Cronograma c) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE produccion.cronograma"
                    + " SET nombre=?, observaciones=?, valido_desde=?"
                    + " WHERE id_cronograma=?; "
            );

            consulta.setString(1, c.getNombre());
            consulta.setString(2, c.getObservaciones());
            consulta.setDate(3, c.getValido_desde());
            consulta.setInt(4, c.getId_cronograma());
            
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la edición");
        }
        return resultado;
    }

    public boolean eliminarCronograma(int id_cronograma) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM produccion.cronograma "
                    + " WHERE id_cronograma=?; "
                    //Eliminar también todas las semanas asociadas al cronograma
                    + "DELETE FROM produccion.semanas_cronograma WHERE id_cronograma=?"
            );

            consulta.setInt(1, id_cronograma);
            consulta.setInt(2, id_cronograma);

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la eliminación");
        }
        return resultado;
    }

}
