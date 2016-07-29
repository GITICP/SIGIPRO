/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.bioterio.dao.*;
import com.icp.sigipro.bioterio.modelos.Cepa;
import com.icp.sigipro.bioterio.modelos.EntregaRatonera;
import com.icp.sigipro.bioterio.modelos.SolicitudRatonera;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.produccion.modelos.Historial_Consumo;
import com.icp.sigipro.produccion.modelos.Veneno_Produccion;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.serpentario.dao.LoteDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Amed
 */
public class Veneno_ProduccionDAO extends DAO {

    public Veneno_ProduccionDAO() {
    }

    public List<Veneno_Produccion> obtenerVenenos_Produccion() throws SIGIPROException {

        List<Veneno_Produccion> resultado = new ArrayList<Veneno_Produccion>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.veneno_produccion; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Veneno_Produccion veneno_produccion = new Veneno_Produccion();
                LoteDAO vDAO = new LoteDAO();
                
                veneno_produccion.setId_veneno(rs.getInt("id_veneno"));
                veneno_produccion.setVeneno(rs.getString("veneno"));
                veneno_produccion.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                veneno_produccion.setCantidad(rs.getInt("cantidad"));
                veneno_produccion.setObservaciones(rs.getString("observaciones"));
                veneno_produccion.setVeneno_serpentario(vDAO.obtenerLote(rs.getInt("id_veneno_serpentario")));
                resultado.add(veneno_produccion);

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

    public Veneno_Produccion obtenerVeneno_Produccion(int id_veneno) throws SIGIPROException {

        Veneno_Produccion resultado = new Veneno_Produccion();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.veneno_produccion where id_veneno = ?; ");
            consulta.setInt(1, id_veneno);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                LoteDAO vDAO = new LoteDAO();
                
                resultado.setId_veneno(rs.getInt("id_veneno"));
                resultado.setVeneno(rs.getString("veneno"));
                resultado.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                resultado.setCantidad(rs.getInt("cantidad"));
                resultado.setObservaciones(rs.getString("observaciones"));
                resultado.setVeneno_serpentario(vDAO.obtenerLote(rs.getInt("id_veneno_serpentario")));
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

    public boolean insertarVeneno_Produccion(Veneno_Produccion p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.veneno_produccion (veneno, fecha_ingreso, cantidad, "
                    + "observaciones, id_veneno_serpentario)"
                    + " VALUES (?,?,?,?,?) RETURNING id_veneno");

            consulta.setString(1, p.getVeneno());
            consulta.setDate(2, p.getFecha_ingreso());
            consulta.setInt(3, p.getCantidad());
            consulta.setString(4, p.getObservaciones());
            consulta.setInt(5, p.getVeneno_serpentario().getId_lote());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
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

    public boolean editarVeneno_Produccion(Veneno_Produccion p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE produccion.veneno_produccion"
                    + " SET veneno=?, fecha_ingreso=?, cantidad=?, observaciones=?, id_veneno_serpentario=?"
                    + " WHERE id_veneno=?; "
            );

            consulta.setString(1, p.getVeneno());
            consulta.setDate(2, p.getFecha_ingreso());
            consulta.setInt(3, p.getCantidad());
            consulta.setString(4, p.getObservaciones());
            consulta.setInt(5, p.getVeneno_serpentario().getId_lote());
            consulta.setInt(6, p.getId_veneno());
            
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

    public boolean eliminarVeneno_Produccion(int id_veneno) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM produccion.veneno_produccion "
                    + " WHERE id_veneno=?; "
            );

            consulta.setInt(1, id_veneno);

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
