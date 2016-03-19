/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Seguimiento_venta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class Seguimiento_ventaDAO extends DAO {

    private ClienteDAO cdao = new ClienteDAO();
    private FacturaDAO fdao = new FacturaDAO();
    public Seguimiento_ventaDAO() {
    }

    public List<Seguimiento_venta> obtenerSeguimientos_venta() throws SIGIPROException {

        List<Seguimiento_venta> resultado = new ArrayList<Seguimiento_venta>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.seguimiento_venta; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Seguimiento_venta seguimiento = new Seguimiento_venta();
                
                seguimiento.setId_seguimiento(rs.getInt("id_seguimiento"));
                seguimiento.setCliente(cdao.obtenerCliente(Integer.parseInt(rs.getString("id_cliente"))));
                seguimiento.setFactura(fdao.obtenerFactura(Integer.parseInt(rs.getString("id_factura"))));
                seguimiento.setObservaciones(rs.getString("observaciones"));
                seguimiento.setTipo(rs.getString("tipo"));
                seguimiento.setDocumento_1(rs.getString("documento"));
                resultado.add(seguimiento);

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

    public Seguimiento_venta obtenerSeguimiento_venta(int id_seguimiento) throws SIGIPROException {

        Seguimiento_venta resultado = new Seguimiento_venta();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.seguimiento_venta where id_seguimiento = ?; ");
            consulta.setInt(1, id_seguimiento);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_seguimiento(rs.getInt("id_seguimiento"));
                resultado.setCliente(cdao.obtenerCliente(Integer.parseInt(rs.getString("id_cliente"))));
                resultado.setFactura(fdao.obtenerFactura(Integer.parseInt(rs.getString("id_factura"))));
                resultado.setObservaciones(rs.getString("observaciones"));
                resultado.setTipo(rs.getString("tipo"));
                resultado.setDocumento_1(rs.getString("documento"));
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
    
    public int insertarSeguimiento_venta(Seguimiento_venta p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.seguimiento_venta (id_cliente, id_factura, observaciones, documento, tipo)"
                    + " VALUES (?,?,?,?,?) RETURNING id_seguimiento");

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setInt(2, p.getFactura().getId_factura());
            consulta.setString(3, p.getObservaciones());
            consulta.setString(4, p.getDocumento_1());
            consulta.setString(5, p.getTipo());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_seguimiento");
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

    public boolean editarSeguimiento_venta(Seguimiento_venta p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.seguimiento_venta"
                    + " SET observaciones=?, id_factura=?, id_cliente=?, documento=?, tipo=?"
                    + " WHERE id_seguimiento=?; "
            );

            consulta.setString(1, p.getObservaciones());
            consulta.setInt(2, p.getFactura().getId_factura());
            consulta.setInt(3, p.getCliente().getId_cliente());
            consulta.setString(4, p.getDocumento_1());
            consulta.setString(5, p.getTipo());
            consulta.setInt(6, p.getId_seguimiento());
            
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

    public boolean eliminarSeguimiento_venta(int id_seguimiento) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.seguimiento_venta "
                    + " WHERE id_seguimiento=?; "
            );

            consulta.setInt(1, id_seguimiento);

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
