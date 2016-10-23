/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Pago;
import java.sql.Array;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class PagoDAO extends DAO {
    
    public FacturaDAO fDAO = new FacturaDAO();
    
    public PagoDAO() {
    }
    
    public List<Pago> obtenerPagos() throws SIGIPROException {

        List<Pago> resultado = new ArrayList<Pago>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.pago; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Pago pago = new Pago();
                
                pago.setId_pago(rs.getInt("id_pago"));
                pago.setFactura(fDAO.obtenerFactura(rs.getInt("id_factura")));
                pago.setCodigo(rs.getInt("codigo"));
                pago.setMonto(rs.getFloat("monto"));
                pago.setNota(rs.getString("nota"));
                pago.setFecha(rs.getString("fecha"));
                pago.setConsecutive(rs.getString("consecutive"));
                pago.setMoneda(rs.getString("moneda"));
                pago.setCodigo_remision(rs.getInt("codigo_remision"));
                pago.setConsecutive_remision(rs.getString("consecutive_remision"));
                pago.setFecha_remision(rs.getString("fecha_remision"));
                resultado.add(pago);

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

    public Pago obtenerPago(int id_pago) throws SIGIPROException {

        Pago resultado = new Pago();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.pago where id_pago = ?; ");
            consulta.setInt(1, id_pago);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_pago(rs.getInt("id_pago"));
                resultado.setFactura(fDAO.obtenerFactura(rs.getInt("id_factura")));
                resultado.setCodigo(rs.getInt("codigo"));
                resultado.setMonto(rs.getFloat("monto"));
                resultado.setNota(rs.getString("nota"));
                resultado.setFecha(rs.getString("fecha"));
                resultado.setConsecutive(rs.getString("consecutive"));
                resultado.setMoneda(rs.getString("moneda"));
                resultado.setCodigo_remision(rs.getInt("codigo_remision"));
                resultado.setConsecutive_remision(rs.getString("consecutive_remision"));
                resultado.setFecha_remision(rs.getString("fecha_remision"));
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
    
    public boolean existePago(int codigo) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.pago where codigo = ?; ");
            consulta.setInt(1, codigo);
            ResultSet rs = consulta.executeQuery();

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_pago");
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        if (resultado == 0){
            return false;
        }
        else{
            return true;
        }
    }
    
    public int insertarPago(Pago p) throws SIGIPROException {
        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.pago (id_factura,codigo,monto,nota,fecha,consecutive,moneda,codigo_remision,consecutive_remision,fecha_remision)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?) RETURNING id_pago");

            consulta.setInt(1, p.getFactura().getId_factura());
            consulta.setInt(2, p.getCodigo());
            consulta.setFloat(3, p.getMonto());
            consulta.setString(4, p.getNota());
            consulta.setString(5, p.getFecha());
            consulta.setString(6, p.getConsecutive());
            consulta.setString(7, p.getMoneda());
            consulta.setInt(8, p.getCodigo_remision());
            consulta.setString(9, p.getConsecutive_remision());
            consulta.setString(10, p.getFecha_remision());
            
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_pago");
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

    public boolean actualizarPago(Pago p) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.pago"
                    + " SET id_factura=?,monto=?,nota=?,fecha=?,consecutive=?,moneda=?,codigo_remision=?,consecutive_remision=?,fecha_remision=?"
                    + " WHERE codigo=?; ");

            consulta.setInt(1, p.getFactura().getId_factura());
            consulta.setFloat(2, p.getMonto());
            consulta.setString(3, p.getNota());
            consulta.setString(4, p.getFecha());
            consulta.setString(5, p.getConsecutive());
            consulta.setString(6, p.getMoneda());
            consulta.setInt(7, p.getCodigo_remision());
            consulta.setString(8, p.getConsecutive_remision());
            consulta.setString(9, p.getFecha_remision());
            consulta.setInt(10, p.getCodigo());
            
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");
        }
        return resultado;
    }

    public List<Pago> obtenerPagos(int id_factura) throws SIGIPROException {

        List<Pago> resultado = new ArrayList<Pago>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.pago where id_factura=?; ");
            consulta.setInt(1, id_factura);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Pago pago = new Pago();
                
                pago.setId_pago(rs.getInt("id_pago"));
                pago.setFactura(fDAO.obtenerFactura(rs.getInt("id_factura")));
                pago.setCodigo(rs.getInt("codigo"));
                pago.setMonto(rs.getFloat("monto"));
                pago.setNota(rs.getString("nota"));
                pago.setFecha(rs.getString("fecha"));
                pago.setConsecutive(rs.getString("consecutive"));
                pago.setMoneda(rs.getString("moneda"));
                pago.setCodigo_remision(rs.getInt("codigo_remision"));
                pago.setConsecutive_remision(rs.getString("consecutive_remision"));
                pago.setFecha_remision(rs.getString("fecha_remision"));
                resultado.add(pago);

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

}
