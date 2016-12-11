/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Tipo_Seguimiento;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 *
 * @author Josue
 */
public class Tipo_SeguimientoDAO extends DAO {
    public Tipo_SeguimientoDAO() {
    }
    
    private Seguimiento_ventaDAO iDAO = new Seguimiento_ventaDAO();
    
    public boolean esTipoSeguimiento (int id_tipo, int id_seguimiento, String tipo, Date fecha) throws SIGIPROException{
        boolean resultado = false;
        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.tipo_seguimiento WHERE id_tipo=? and id_seguimiento=? and tipo=? and fecha=? ; ");
            consulta.setInt(1, id_tipo);
            consulta.setInt(2, id_seguimiento);
            consulta.setString(3, tipo);
            consulta.setDate(4, fecha);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado = true;
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
    
    public List<Tipo_Seguimiento> obtenerTiposSeguimiento(int id_seguimiento) throws SIGIPROException {

        List<Tipo_Seguimiento> resultado = new ArrayList<Tipo_Seguimiento>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.tipo_seguimiento WHERE id_seguimiento=?; ");
            consulta.setInt(1, id_seguimiento);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Tipo_Seguimiento tipo = new Tipo_Seguimiento();
                
                tipo.setId_seguimiento(iDAO.obtenerSeguimiento_venta(id_seguimiento));
                tipo.setId_tipo(rs.getInt("id_tipo"));
                tipo.setFecha(rs.getDate("fecha"));
                tipo.setObservaciones(rs.getString("observaciones"));
                tipo.setTipo(rs.getString("tipo"));
                
                resultado.add(tipo);
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

    public Tipo_Seguimiento obtenerTipo(int id_tipo) throws SIGIPROException {

        Tipo_Seguimiento resultado = new Tipo_Seguimiento();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.tipo_seguimiento where id_tipo = ?; ");
            consulta.setInt(1, id_tipo);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_seguimiento(iDAO.obtenerSeguimiento_venta(rs.getInt("id_seguimiento")));
                resultado.setId_tipo(id_tipo);
                resultado.setTipo(rs.getString("tipo"));
                resultado.setObservaciones(rs.getString("observaciones"));
                resultado.setFecha(rs.getDate("fecha"));
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
    
    public int insertarTipo_Seguimiento(Tipo_Seguimiento p) throws SIGIPROException {
        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.tipo_seguimiento (id_seguimiento, tipo, observaciones, fecha)"
                    + " VALUES (?,?,?,?) RETURNING id_tipo");

            consulta.setInt(1,p.getId_seguimiento().getId_seguimiento());
            consulta.setString(2,p.getTipo());
            consulta.setString(3,p.getObservaciones());
            consulta.setDate(4,p.getFecha());
            
            //System.out.println("CONSULTA A EJECUTAR: "+consulta.toString());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_tipo");
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

    public boolean editarTipo_Seguimiento(Tipo_Seguimiento p) throws SIGIPROException {

        boolean resultado = false;
        
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.tipo_seguimiento"
                    + " SET tipo=?, fecha=?, observaciones=?"
                    + " WHERE id_tipo=?; "
            );
            
            consulta.setString(1,p.getTipo());
            consulta.setDate(2,p.getFecha());
            consulta.setString(3,p.getObservaciones());
            consulta.setInt(4,p.getId_tipo());
            
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

    public boolean eliminarTipo_Seguimiento(int id_tipo) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.tio_seguimiento"
                    + " WHERE id_tipo=?; "
            );

            consulta.setInt(1, id_tipo);

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

    public List<Tipo_Seguimiento> parsearProductos(String productos_intencion, int id_seguimiento) {
        List<Tipo_Seguimiento> resultado = null;
        try {
            resultado = new ArrayList<Tipo_Seguimiento>();
            List<String> productosParcial = new LinkedList<String>(Arrays.asList(productos_intencion.split("#r#")));
            productosParcial.remove("");
            for (String i : productosParcial) {
                String[] rol = i.split("#c#");
                
                int id_tipo = Integer.parseInt(rol[0]);
                String tipo = rol[1];
                String observaciones = rol[3];
                
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date fecha = formatoFecha.parse(rol[2]);
                java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
                
                Tipo_Seguimiento p = new Tipo_Seguimiento();
                p.setFecha(fechaSQL);
                p.setId_seguimiento(iDAO.obtenerSeguimiento_venta(id_seguimiento));
                p.setId_tipo(id_tipo);
                p.setTipo(tipo);
                p.setObservaciones(observaciones);
                
                resultado.add(p);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            resultado = null;
        }
        return resultado;
    }

    public boolean eliminarTipos_Seguimiento(int id_seguimiento) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.tipo_seguimiento "
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
