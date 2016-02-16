/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Factura;
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
public class FacturaDAO extends DAO {
    
    public ClienteDAO cDAO = new ClienteDAO();
    public Orden_compraDAO oDAO = new Orden_compraDAO();
    
    public FacturaDAO() {
    }
    
    public List<Factura> obtenerFacturas() throws SIGIPROException {

        List<Factura> resultado = new ArrayList<Factura>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.factura; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Factura factura = new Factura();
                
                factura.setId_factura(rs.getInt("id_factura"));
                factura.setCliente(cDAO.obtenerCliente(rs.getInt("id_cliente")));
                factura.setOrden(oDAO.obtenerOrden_compra(rs.getInt("id_orden")));
                factura.setFecha(rs.getDate("fecha"));
                factura.setMonto(rs.getInt("monto"));
                factura.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                factura.setDocumento(rs.getString("documento"));
                factura.setTipo(rs.getString("tipo"));
                resultado.add(factura);

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

    public Factura obtenerFactura(int id_factura) throws SIGIPROException {

        Factura resultado = new Factura();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.factura where id_factura = ?; ");
            consulta.setInt(1, id_factura);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_factura(rs.getInt("id_factura"));
                resultado.setCliente(cDAO.obtenerCliente(rs.getInt("id_cliente")));
                resultado.setOrden(oDAO.obtenerOrden_compra(rs.getInt("id_orden")));
                resultado.setFecha(rs.getDate("fecha"));
                resultado.setMonto(rs.getInt("monto"));
                resultado.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                resultado.setDocumento(rs.getString("documento"));
                resultado.setTipo(rs.getString("tipo"));
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
    
    public int insertarFactura(Factura p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.factura (id_cliente, id_orden, fecha, monto, fecha_vencimiento, documento, tipo)"
                    + " VALUES (?,?,?,?,?,?,?) RETURNING id_factura");

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setInt(2, p.getOrden().getId_orden());
            consulta.setDate(3, p.getFecha());
            consulta.setInt(4, p.getMonto());
            consulta.setDate(5, p.getFecha_vencimiento());
            consulta.setString(6, p.getDocumento());
            consulta.setString(7, p.getTipo());
            
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_factura");
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
    
    public int insertarFacturaOrden0(Factura p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.factura (id_cliente, fecha, monto, fecha_vencimiento, documento, tipo)"
                    + " VALUES (?,?,?,?,?,?) RETURNING id_factura");

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha());
            consulta.setInt(3, p.getMonto());
            consulta.setDate(4, p.getFecha_vencimiento());
            consulta.setString(5, p.getDocumento());
            consulta.setString(6, p.getTipo());
            
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_factura");
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

    public boolean editarFactura(Factura p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.factura"
                    + " SET id_cliente=?, id_orden=?, fecha=?, monto=?, fecha_vencimiento=?, documento=?, tipo=?"
                    + " WHERE id_factura=?; "
            );

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setInt(2, p.getOrden().getId_orden());
            consulta.setDate(3, p.getFecha());
            consulta.setInt(4, p.getMonto());
            consulta.setDate(5, p.getFecha_vencimiento());
            consulta.setString(6, p.getDocumento());
            consulta.setString(7, p.getTipo());
            consulta.setInt(8, p.getId_factura());
            
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
    
    public boolean editarFacturaOrden0(Factura p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.factura"
                    + " SET id_cliente=?, fecha=?, monto=?, fecha_vencimiento=?, documento=?, tipo=?"
                    + " WHERE id_factura=?; "
            );

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha());
            consulta.setInt(3, p.getMonto());
            consulta.setDate(4, p.getFecha_vencimiento());
            consulta.setString(5, p.getDocumento());
            consulta.setString(6, p.getTipo());
            consulta.setInt(7, p.getId_factura());
            
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

    public boolean eliminarFactura(int id_factura) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.factura "
                    + " WHERE id_factura=?; "
            );

            consulta.setInt(1, id_factura);

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
