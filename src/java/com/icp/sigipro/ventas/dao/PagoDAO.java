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
                pago.setPago(rs.getInt("pago"));
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
                resultado.setPago(rs.getInt("pago"));
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
    
    public int insertarPago(Pago p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.pago (id_factura, pago)"
                    + " VALUES (?,?) RETURNING id_pago");

            consulta.setInt(1, p.getFactura().getId_factura());
            consulta.setInt(2, p.getPago());
            
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
    public boolean editarPago(Pago p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.pago"
                    + " SET id_factura=?, pago=?"
                    + " WHERE id_pago=?; "
            );

            consulta.setInt(1, p.getFactura().getId_factura());
            consulta.setInt(2, p.getPago());
            consulta.setInt(3, p.getId_pago());
            
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

    public boolean eliminarPago(int id_pago) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.pago "
                    + " WHERE id_pago=?; "
            );

            consulta.setInt(1, id_pago);

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
