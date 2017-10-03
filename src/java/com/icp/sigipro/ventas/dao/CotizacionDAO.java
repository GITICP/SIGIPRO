/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Cotizacion;
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
public class CotizacionDAO extends DAO {
    
    public ClienteDAO cDAO = new ClienteDAO();
    public Intencion_ventaDAO iDAO = new Intencion_ventaDAO();
    public Producto_ventaDAO pDAO = new Producto_ventaDAO();
    
    public CotizacionDAO() {
    }
    
    public List<Cotizacion> obtenerCotizaciones() throws SIGIPROException {

        List<Cotizacion> resultado = new ArrayList<Cotizacion>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.cotizacion; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Cotizacion cotizacion = new Cotizacion();
                
                cotizacion.setId_cotizacion(rs.getInt("id_cotizacion"));
                cotizacion.setIntencion(iDAO.obtenerIntencion_venta(rs.getInt("id_intencion")));
                cotizacion.setMoneda(rs.getString("moneda"));
                cotizacion.setTotal(rs.getInt("total"));
                cotizacion.setFlete(rs.getInt("flete"));
                cotizacion.setIdentificador(rs.getString("identificador"));
                resultado.add(cotizacion);

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
    
    public int CantidadDeCotizacionesConIntencion(int id_intencion) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.cotizacion WHERE id_intencion=?; ");
            consulta.setInt(1, id_intencion);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado +=1;

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

    public Cotizacion obtenerCotizacion(int id_cotizacion) throws SIGIPROException {

        Cotizacion resultado = new Cotizacion();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.cotizacion where id_cotizacion = ?; ");
            consulta.setInt(1, id_cotizacion);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_cotizacion(rs.getInt("id_cotizacion"));
                resultado.setIntencion(iDAO.obtenerIntencion_venta(rs.getInt("id_intencion")));
                resultado.setMoneda(rs.getString("moneda"));
                resultado.setIdentificador(rs.getString("identificador"));
                resultado.setTotal(rs.getInt("total"));
                resultado.setFlete(rs.getInt("flete"));
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
    
    public int insertarCotizacion(Cotizacion p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.cotizacion (id_intencion, identificador, total, flete, moneda)"
                    + " VALUES (?,?,?,?,?) RETURNING id_cotizacion");

            consulta.setInt(1, p.getIntencion().getId_intencion());
            consulta.setString(2, p.getIdentificador());
            consulta.setInt(3, p.getTotal());
            consulta.setInt(4, p.getFlete());
            consulta.setString(5, p.getMoneda());
            
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_cotizacion");
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (ex.getMessage().contains("llave"))
              { 
                throw new SIGIPROException("El identificador de la cotización debe ser único");}
            else {
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");}
        }
        return resultado;
    }

    public boolean editarCotizacion(Cotizacion p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.cotizacion"
                    + " SET identificador=?, id_intencion=?, total=?, flete=?, moneda=?"
                    + " WHERE id_cotizacion=?; "
            );

            consulta.setString(1, p.getIdentificador());
            consulta.setInt(2, p.getIntencion().getId_intencion());
            consulta.setInt(3, p.getTotal());
            consulta.setInt(4, p.getFlete());
            consulta.setString(5, p.getMoneda());
            consulta.setInt(6, p.getId_cotizacion());
            
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (ex.getMessage().contains("llave"))
              { 
                throw new SIGIPROException("El identificador de la cotización debe ser único");}
            else {
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");}
        }
        return resultado;
    }

    public boolean eliminarCotizacion(int id_cotizacion) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.cotizacion "
                    + " WHERE id_cotizacion=?; "
            );

            consulta.setInt(1, id_cotizacion);

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
