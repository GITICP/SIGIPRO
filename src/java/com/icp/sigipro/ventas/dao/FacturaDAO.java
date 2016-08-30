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
                int orden = rs.getInt("id_orden");
                if (orden!=0){
                    factura.setOrden(oDAO.obtenerOrden_compra(orden));
                }
                factura.setFecha(rs.getDate("fecha"));
                factura.setMonto(rs.getInt("monto"));
                factura.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                factura.setDocumento_1(rs.getString("documento1"));
                factura.setDocumento_2(rs.getString("documento2"));
                factura.setDocumento_3(rs.getString("documento3"));
                factura.setDocumento_4(rs.getString("documento4"));
                factura.setTipo(rs.getString("tipo"));
                factura.setMoneda(rs.getString("moneda"));

                factura.setEstado(rs.getString("estado"));
                factura.setProyecto(rs.getInt("proyecto"));
                factura.setNumero(rs.getInt("numero_factura"));
                factura.setPlazo(rs.getInt("plazo"));
                
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
                int orden = rs.getInt("id_orden");
                if (orden!=0){
                    resultado.setOrden(oDAO.obtenerOrden_compra(orden));
                }
                resultado.setFecha(rs.getDate("fecha"));
                resultado.setMonto(rs.getInt("monto"));
                resultado.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                resultado.setDocumento_1(rs.getString("documento1"));
                resultado.setDocumento_2(rs.getString("documento2"));
                resultado.setDocumento_3(rs.getString("documento3"));
                resultado.setDocumento_4(rs.getString("documento4"));
                resultado.setTipo(rs.getString("tipo"));
                resultado.setMoneda(rs.getString("moneda"));
                
                resultado.setEstado(rs.getString("estado"));
                resultado.setProyecto(rs.getInt("proyecto"));
                resultado.setNumero(rs.getInt("numero_factura"));
                resultado.setPlazo(rs.getInt("plazo"));
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
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.factura (id_cliente, id_orden, fecha, monto, fecha_vencimiento, documento1, documento2, documento3, documento4, tipo, moneda,"
                    + "estado,plazo,numero_factura,proyecto,detalle,nombre_factura)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id_factura");

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setInt(2, p.getOrden().getId_orden());
            consulta.setDate(3, p.getFecha());
            consulta.setInt(4, p.getMonto());
            consulta.setDate(5, p.getFecha_vencimiento());
            consulta.setString(6, p.getDocumento_1());
            consulta.setString(7, p.getDocumento_2());
            consulta.setString(8, p.getDocumento_3());
            consulta.setString(9, p.getDocumento_4());
            consulta.setString(10, p.getTipo());
            consulta.setString(11, p.getMoneda());
            
            consulta.setString(12, p.getEstado());
            consulta.setInt(13, p.getPlazo());
            consulta.setInt(14, p.getNumero());
            consulta.setInt(15, p.getProyecto());
            consulta.setString(16, p.getDetalle());
            consulta.setString(17, p.getNombre());
            
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
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.factura (id_cliente, fecha, monto, fecha_vencimiento, documento1, documento2, documento3, documento4, tipo, moneda,estado,plazo,numero_factura,proyecto,detalle,nombre_factura)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id_factura");

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha());
            consulta.setInt(3, p.getMonto());
            consulta.setDate(4, p.getFecha_vencimiento());
            consulta.setString(5, p.getDocumento_1());
            consulta.setString(6, p.getDocumento_2());
            consulta.setString(7, p.getDocumento_3());
            consulta.setString(8, p.getDocumento_4());
            consulta.setString(9, p.getTipo());
            consulta.setString(10, p.getMoneda());
            
            consulta.setString(11, p.getEstado());
            consulta.setInt(12, p.getPlazo());
            consulta.setInt(13, p.getNumero());
            consulta.setInt(14, p.getProyecto());
            consulta.setString(15, p.getDetalle());
            consulta.setString(16, p.getNombre());
            
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
    
    public int insertarFacturaUCR(Factura p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.factura (id_cliente, id_orden, fecha, monto, fecha_vencimiento, documento1, documento2, documento3, documento4, tipo, moneda)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?) RETURNING id_factura");

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setInt(2, p.getOrden().getId_orden());
            consulta.setDate(3, p.getFecha());
            consulta.setInt(4, p.getMonto());
            consulta.setDate(5, p.getFecha_vencimiento());
            consulta.setString(6, p.getDocumento_1());
            consulta.setString(7, p.getDocumento_2());
            consulta.setString(8, p.getDocumento_3());
            consulta.setString(9, p.getDocumento_4());
            consulta.setString(10, p.getTipo());
            consulta.setString(11, p.getMoneda());
            
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
    
    public int insertarFacturaOrden0UCR(Factura p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.factura (id_cliente, fecha, monto, fecha_vencimiento, documento1, documento2, documento3, documento4, tipo, moneda)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?) RETURNING id_factura");

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha());
            consulta.setInt(3, p.getMonto());
            consulta.setDate(4, p.getFecha_vencimiento());
            consulta.setString(5, p.getDocumento_1());
            consulta.setString(6, p.getDocumento_2());
            consulta.setString(7, p.getDocumento_3());
            consulta.setString(8, p.getDocumento_4());
            consulta.setString(9, p.getTipo());
            consulta.setString(10, p.getMoneda());
            
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

    public boolean actualizarEstado(Factura p, String estado) throws SIGIPROException{
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.factura"
                    + " SET estado=?"
                    + " WHERE id_factura=?; "
            );

            consulta.setInt(2, p.getId_factura());
            consulta.setString(1, estado);
            
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado = false;
            throw new SIGIPROException("Se produjo un error al procesar la edición");
        }
        return resultado;
    }
    
    public boolean editarFactura(Factura p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.factura"
                    + " SET id_cliente=?, id_orden=?, fecha=?, monto=?, fecha_vencimiento=?, documento1=?, documento2=?, documento3=?, documento4=?, tipo=?, moneda=?,"
                            + "estado=?,plazo=?,numero_factura=?,proyecto=?,detalle=?,nombre_factura=?"
                    + " WHERE id_factura=?; "
            );

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setInt(2, p.getOrden().getId_orden());
            consulta.setDate(3, p.getFecha());
            consulta.setInt(4, p.getMonto());
            consulta.setDate(5, p.getFecha_vencimiento());
            consulta.setString(6, p.getDocumento_1());
            consulta.setString(7, p.getDocumento_2());
            consulta.setString(8, p.getDocumento_3());
            consulta.setString(9, p.getDocumento_4());
            consulta.setString(10, p.getTipo());
            consulta.setString(11, p.getMoneda());
            
            consulta.setString(12, p.getEstado());
            consulta.setInt(13, p.getPlazo());
            consulta.setInt(14, p.getNumero());
            consulta.setInt(15, p.getProyecto());
            consulta.setString(16, p.getDetalle());
            consulta.setString(17, p.getNombre());
            
            consulta.setInt(18, p.getId_factura());
            
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
                    + " SET id_cliente=?, fecha=?, monto=?, fecha_vencimiento=?, documento1=?, documento2=?, documento3=?, documento4=?, tipo=?, moneda=?,"
                            + "estado=?,plazo=?,numero_factura=?,proyecto=?,detalle=?,nombre_factura=?"
                    + " WHERE id_factura=?; "
            );

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha());
            consulta.setInt(3, p.getMonto());
            consulta.setDate(4, p.getFecha_vencimiento());
            consulta.setString(5, p.getDocumento_1());
            consulta.setString(6, p.getDocumento_2());
            consulta.setString(7, p.getDocumento_3());
            consulta.setString(8, p.getDocumento_4());
            consulta.setString(9, p.getTipo());
            consulta.setString(10, p.getMoneda());
            
            consulta.setString(11, p.getEstado());
            consulta.setInt(12, p.getPlazo());
            consulta.setInt(13, p.getNumero());
            consulta.setInt(14, p.getProyecto());
            consulta.setString(15, p.getDetalle());
            consulta.setString(16, p.getNombre());
            
            consulta.setInt(17, p.getId_factura());
            
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

    public boolean editarFacturaUCR(Factura p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.factura"
                    + " SET id_cliente=?, id_orden=?, fecha=?, monto=?, fecha_vencimiento=?, documento1=?, documento2=?, documento3=?, documento4=?, tipo=?, moneda=?"
                    + " WHERE id_factura=?; "
            );

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setInt(2, p.getOrden().getId_orden());
            consulta.setDate(3, p.getFecha());
            consulta.setInt(4, p.getMonto());
            consulta.setDate(5, p.getFecha_vencimiento());
            consulta.setString(6, p.getDocumento_1());
            consulta.setString(7, p.getDocumento_2());
            consulta.setString(8, p.getDocumento_3());
            consulta.setString(9, p.getDocumento_4());
            consulta.setString(10, p.getTipo());
            consulta.setString(11, p.getMoneda());
            consulta.setInt(12, p.getId_factura());
            
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
    
    public boolean editarFacturaOrden0UCR(Factura p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.factura"
                    + " SET id_cliente=?, fecha=?, monto=?, fecha_vencimiento=?, documento1=?, documento2=?, documento3=?, documento4=?, tipo=?, moneda=?"
                    + " WHERE id_factura=?; "
            );

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha());
            consulta.setInt(3, p.getMonto());
            consulta.setDate(4, p.getFecha_vencimiento());
            consulta.setString(5, p.getDocumento_1());
            consulta.setString(6, p.getDocumento_2());
            consulta.setString(7, p.getDocumento_3());
            consulta.setString(8, p.getDocumento_4());
            consulta.setString(9, p.getTipo());
            consulta.setString(10, p.getMoneda());
            consulta.setInt(11, p.getId_factura());
            
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

    public boolean agregarNumeroFactura(Factura f) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.factura "
                        + "SET numero_factura=?"
                    + " WHERE id_factura=?; "
            );

            consulta.setInt(1, f.getNumero());
            consulta.setInt(2, f.getId_factura());

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
