/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Contrato_comercializacion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class Contrato_comercializacionDAO extends DAO {

    public Contrato_comercializacionDAO() {
    }
    
    private ClienteDAO cDAO = new ClienteDAO();

    public List<Contrato_comercializacion> obtenerContratos_comercializacion() throws SIGIPROException {

        List<Contrato_comercializacion> resultado = new ArrayList<Contrato_comercializacion>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.contrato_comercializacion; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Contrato_comercializacion cliente = new Contrato_comercializacion();
                
                cliente.setId_contrato(rs.getInt("id_contrato"));
                cliente.setCliente(cDAO.obtenerCliente(rs.getInt("id_cliente")));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setFechaInicial(rs.getDate("fechaInicial"));
                cliente.setFechaRenovacion(rs.getDate("fechaRenovacion"));
                cliente.setObservaciones(rs.getString("observaciones"));
                cliente.setFirmado(rs.getBoolean("firmado"));
                resultado.add(cliente);

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

    public Contrato_comercializacion obtenerContrato_comercializacion(int id_contrato) throws SIGIPROException {

        Contrato_comercializacion resultado = new Contrato_comercializacion();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.contrato_comercializacion where id_contrato = ?; ");
            consulta.setInt(1, id_contrato);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_contrato(rs.getInt("id_contrato"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setCliente(cDAO.obtenerCliente(rs.getInt("id_cliente")));
                resultado.setFechaInicial(rs.getDate("fechaInicial"));
                resultado.setFechaRenovacion(rs.getDate("fechaRenovacion"));
                resultado.setObservaciones(rs.getString("observaciones"));
                resultado.setFirmado(rs.getBoolean("firmado"));
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
    
    public int insertarContrato_comercializacion(Contrato_comercializacion p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.contrato_comercializacion (nombre, id_cliente, fechaInicial, fechaRenovacion, observaciones, firmado)"
                    + " VALUES (?,?,?,?,?,?) RETURNING id_contrato");

            consulta.setString(1, p.getNombre());
            consulta.setInt(2, p.getCliente().getId_cliente());
            consulta.setDate(3, p.getFechaInicial());
            consulta.setDate(4, p.getFechaRenovacion());
            consulta.setString(5, p.getObservaciones());
            consulta.setBoolean(6, p.isFirmado());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_contrato");
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (ex.getMessage().contains("llave"))
              { 
                throw new SIGIPROException("El nombre del contrato debe ser único");}
            else {
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");}
        }
        return resultado;
    }

    public boolean editarContrato_comercializacion(Contrato_comercializacion p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.contrato_comercializacion"
                    + " SET nombre=?, fechaInicial=?, fechaRenovacion=?, observaciones=?, firmado=?"
                    + " WHERE id_contrato=?; "
            );

            consulta.setString(1, p.getNombre());
            consulta.setDate(2, p.getFechaInicial());
            consulta.setDate(3, p.getFechaRenovacion());
            consulta.setString(4, p.getObservaciones());
            consulta.setBoolean(5, p.isFirmado());
            consulta.setInt(6, p.getId_contrato());
            
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

    public boolean eliminarContrato_comercializacion(int id_contrato) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.contrato_comercializacion "
                    + " WHERE id_contrato=?; "
            );

            consulta.setInt(1, id_contrato);

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
