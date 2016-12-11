/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Encuesta_satisfaccion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class Encuesta_satisfaccionDAO extends DAO {

    private ClienteDAO cdao = new ClienteDAO();
    public Encuesta_satisfaccionDAO() {
    }

    public List<Encuesta_satisfaccion> obtenerEncuestas_satisfaccion() throws SIGIPROException {

        List<Encuesta_satisfaccion> resultado = new ArrayList<Encuesta_satisfaccion>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.encuesta_satisfaccion; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Encuesta_satisfaccion encuesta = new Encuesta_satisfaccion();
                
                encuesta.setId_encuesta(rs.getInt("id_encuesta"));
                encuesta.setCliente(cdao.obtenerCliente(Integer.parseInt(rs.getString("id_cliente"))));
                encuesta.setFecha(rs.getDate("fecha"));
                encuesta.setObservaciones(rs.getString("observaciones"));
                encuesta.setDocumento(rs.getString("documento"));
                encuesta.setURL(rs.getString("url"));
                resultado.add(encuesta);

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

    public Encuesta_satisfaccion obtenerEncuesta_satisfaccion(int id_encuesta) throws SIGIPROException {

        Encuesta_satisfaccion resultado = new Encuesta_satisfaccion();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.encuesta_satisfaccion where id_encuesta = ?; ");
            consulta.setInt(1, id_encuesta);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_encuesta(rs.getInt("id_encuesta"));
                resultado.setCliente(cdao.obtenerCliente(Integer.parseInt(rs.getString("id_cliente"))));
                resultado.setFecha(rs.getDate("fecha"));
                resultado.setObservaciones(rs.getString("observaciones"));
                resultado.setDocumento(rs.getString("documento"));
                resultado.setURL(rs.getString("url"));
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
    
    public int insertarEncuesta_satisfaccion(Encuesta_satisfaccion p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.encuesta_satisfaccion (id_cliente, fecha, observaciones, documento,url)"
                    + " VALUES (?,?,?,?,?) RETURNING id_encuesta");

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha());
            consulta.setString(3, p.getObservaciones());
            consulta.setString(4, p.getDocumento());
            consulta.setString(5, p.getURL());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_encuesta");
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

    public boolean editarEncuesta_satisfaccion(Encuesta_satisfaccion p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.encuesta_satisfaccion"
                    + " SET observaciones=?, fecha=?, id_cliente=?, documento=?, url=?"
                    + " WHERE id_encuesta=?; "
            );

            consulta.setString(1, p.getObservaciones());
            consulta.setDate(2, p.getFecha());
            consulta.setInt(3, p.getCliente().getId_cliente());
            consulta.setString(4, p.getDocumento());
            consulta.setString(5, p.getURL());
            consulta.setInt(6, p.getId_encuesta());
            
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

    public boolean eliminarEncuesta_satisfaccion(int id_encuesta) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.encuesta_satisfaccion "
                    + " WHERE id_encuesta=?; "
            );

            consulta.setInt(1, id_encuesta);

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
