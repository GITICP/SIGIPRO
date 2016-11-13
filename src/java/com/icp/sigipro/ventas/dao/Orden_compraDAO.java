/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Orden_compra;
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
public class Orden_compraDAO extends DAO {
    
    public Producto_ventaDAO pDAO = new Producto_ventaDAO();
    public CotizacionDAO cotDAO = new CotizacionDAO();
    public Intencion_ventaDAO iDAO = new Intencion_ventaDAO();
    
    public Orden_compraDAO() {
    }
    
    public List<Orden_compra> obtenerOrdenes_compra() throws SIGIPROException {

        List<Orden_compra> resultado = new ArrayList<Orden_compra>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.orden_compra; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Orden_compra orden = new Orden_compra();
                
                orden.setId_orden(rs.getInt("id_orden"));
                orden.setDocumento(rs.getString("documento"));
                orden.setCotizacion(cotDAO.obtenerCotizacion(rs.getInt("id_cotizacion")));
                orden.setIntencion(iDAO.obtenerIntencion_venta((rs.getInt("id_intencion"))));
                orden.setRotulacion(rs.getString("rotulacion"));
                orden.setEstado(rs.getString("estado"));
                resultado.add(orden);

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

    public Orden_compra obtenerOrden_compra(int id_orden) throws SIGIPROException {

        Orden_compra resultado = new Orden_compra();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.orden_compra where id_orden = ?; ");
            consulta.setInt(1, id_orden);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_orden(rs.getInt("id_orden"));
                resultado.setDocumento(rs.getString("documento"));
                resultado.setCotizacion(cotDAO.obtenerCotizacion(rs.getInt("id_cotizacion")));
                resultado.setIntencion(iDAO.obtenerIntencion_venta((rs.getInt("id_intencion"))));
                resultado.setRotulacion(rs.getString("rotulacion"));
                resultado.setEstado(rs.getString("estado"));
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

    public int CantidadDEOrdenesConIntencion(int id_intencion) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.orden_compra where id_intencion = ?; ");
            consulta.setInt(1, id_intencion);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado += 1;
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
    
    public boolean eliminarOrden_compra(int id_orden) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.orden_compra "
                    + " WHERE id_orden=?; "
            );

            consulta.setInt(1, id_orden);

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

    public int insertarOrden_compraCotizacion0(Orden_compra p) throws SIGIPROException {
        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.orden_compra (documento, id_intencion, rotulacion, estado)"
                    + " VALUES (?,?,?,?) RETURNING id_orden");

            consulta.setString(1, p.getDocumento());
            consulta.setInt(2, p.getIntencion().getId_intencion());
            consulta.setString(3, p.getRotulacion());
            consulta.setString(4, p.getEstado());
            
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_orden");
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

    public int insertarOrden_compraIntencion0(Orden_compra p) throws SIGIPROException {
        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.orden_compra (documento, id_cotizacion, rotulacion, estado)"
                    + " VALUES (?,?,?,?) RETURNING id_orden");

            consulta.setString(1, p.getDocumento());
            consulta.setInt(2, p.getCotizacion().getId_cotizacion());
            consulta.setString(3, p.getRotulacion());
            consulta.setString(4, p.getEstado());
            
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_orden");
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

    public boolean editarOrden_compraIntencion0(Orden_compra p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.orden_compra"
                    + " SET documento=?, id_cotizacion=?, rotulacion=?, estado=?"
                    + " WHERE id_orden=?; "
            );

            consulta.setString(1, p.getDocumento());
            consulta.setInt(2, p.getCotizacion().getId_cotizacion());
            consulta.setString(3, p.getRotulacion());
            consulta.setString(4, p.getEstado());
            consulta.setInt(5, p.getId_orden());
            
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

    public boolean editarOrden_compraCotizacion0(Orden_compra p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.orden_compra"
                    + " SET documento=?, id_intencion=?, rotulacion=?, estado=?"
                    + " WHERE id_orden=?; "
            );

            consulta.setString(1, p.getDocumento());
            consulta.setInt(2, p.getIntencion().getId_intencion());
            consulta.setString(3, p.getRotulacion());
            consulta.setString(4, p.getEstado());
            consulta.setInt(5, p.getId_orden());
            
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

}
