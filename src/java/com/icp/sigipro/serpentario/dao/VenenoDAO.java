/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.serpentario.modelos.Especie;
import com.icp.sigipro.serpentario.modelos.Veneno;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class VenenoDAO extends DAO {

    public VenenoDAO() {
    }

    public int insertarVeneno(Especie e) {
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO serpentario.venenos (id_especie, restriccion) "
                    + " VALUES (?,false) RETURNING id_veneno");
            consulta.setInt(1, e.getId_especie());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = rs.getInt("id_veneno");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean editarVeneno(Veneno v) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(
                    " UPDATE serpentario.venenos "
                    + " SET restriccion=?, cantidad_minima=? "
                    + " WHERE id_veneno=?; "
            );

            consulta.setBoolean(1, v.isRestriccion());
            consulta.setFloat(2, v.getCantidad_minima());
            consulta.setInt(3, v.getId_veneno());

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public Veneno obtenerVeneno(int id_veneno) {
        Veneno veneno = new Veneno();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT especie.*,veneno.*, sum(peso_recuperado) as cantidad "
                    + "FROM serpentario.especies as especie  "
                    + "LEFT OUTER JOIN serpentario.extraccion as extraccion ON especie.id_especie=extraccion.id_especie "
                    + "LEFT OUTER JOIN serpentario.liofilizacion as liofilizacion ON liofilizacion.id_extraccion=extraccion.id_extraccion "
                    + "LEFT OUTER JOIN serpentario.venenos as veneno ON veneno.id_especie = especie.id_especie "
                    + "WHERE veneno.id_veneno = ?"
                    + "GROUP BY especie.id_especie, veneno.id_veneno;");
            consulta.setInt(1, id_veneno);
            rs = consulta.executeQuery();
            if (rs.next()) {
                veneno.setId_veneno(rs.getInt("id_veneno"));
                veneno.setCantidad_minima(rs.getFloat("cantidad_minima"));
                Especie especie = new Especie();
                especie.setId_especie(rs.getInt("id_especie"));
                especie.setEspecie(rs.getString("especie"));
                especie.setGenero(rs.getString("genero"));
                veneno.setEspecie(especie);
                veneno.setRestriccion(rs.getBoolean("restriccion"));
                float cantidad_original = rs.getFloat("cantidad");
                float cantidad_entregada = this.obtenerCantidadEntregada(veneno.getEspecie().getId_especie());
                float cantidad = (float) (cantidad_original - (cantidad_entregada * 0.001));
                veneno.setCantidad(cantidad);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return veneno;
    }
    
    public Veneno obtenerVenenoLotes(int id_veneno) {
        Veneno veneno = new Veneno();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement("SELECT veneno.id_veneno, especie.id_especie, especie.especie, especie.genero "
                    + "FROM serpentario.venenos as veneno LEFT OUTER JOIN serpentario.especies as especie ON especie.id_especie = veneno.id_especie "
                    + "where id_veneno = ?");
            consulta.setInt(1, id_veneno);
            rs = consulta.executeQuery();
            EspecieDAO especiedao = new EspecieDAO();
            if (rs.next()) {
                veneno.setId_veneno(rs.getInt("id_veneno"));
                Especie especie = new Especie();
                especie.setEspecie(rs.getString("especie"));
                especie.setGenero(rs.getString("genero"));
                especie.setId_especie(rs.getInt("id_especie"));
                veneno.setEspecie(especie);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return veneno;
    }
//Necesita arreglo
    public List<Veneno> obtenerVenenos() {
        List<Veneno> resultado = new ArrayList<Veneno>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT especie.id_especie,especie.especie, especie.genero, veneno.*, sum(DISTINCT peso_recuperado) as cantidad, sum(entrega.cantidad) as cantidad_entregada "
                    + "FROM serpentario.especies as especie  "
                    + "LEFT OUTER JOIN serpentario.extraccion as extraccion ON especie.id_especie=extraccion.id_especie "
                    + "LEFT OUTER JOIN serpentario.liofilizacion as liofilizacion ON liofilizacion.id_extraccion=extraccion.id_extraccion "
                    + "LEFT OUTER JOIN serpentario.venenos as veneno ON veneno.id_especie = especie.id_especie "
                    + "LEFT OUTER JOIN serpentario.lote as lote ON lote.id_lote = extraccion.id_lote "
                    + "LEFT OUTER JOIN serpentario.lotes_entregas_solicitud as entrega ON lote.id_lote = entrega.id_lote "
                    + "GROUP BY especie.id_especie, veneno.id_veneno;");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Veneno veneno = new Veneno();
                veneno.setId_veneno(rs.getInt("id_veneno"));
                veneno.setCantidad_minima(rs.getFloat("cantidad_minima"));
                Especie especie = new Especie();
                especie.setId_especie(rs.getInt("id_especie"));
                especie.setEspecie(rs.getString("especie"));
                especie.setGenero(rs.getString("genero"));
                veneno.setEspecie(especie);
                veneno.setRestriccion(rs.getBoolean("restriccion"));
                float cantidad_entregada = rs.getFloat("cantidad_entregada");
                float cantidad_original = rs.getFloat("cantidad");
                float cantidad = (float) (cantidad_original - (cantidad_entregada * 0.001));
                veneno.setCantidad(cantidad);
                resultado.add(veneno);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public List<Veneno> obtenerVenenosSimple() {
        List<Veneno> resultado = new ArrayList<Veneno>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT veneno.id_veneno, especie.id_especie, especie.especie, especie.genero "
                    + "FROM serpentario.venenos as veneno LEFT OUTER JOIN serpentario.especies as especie ON veneno.id_especie = especie.id_especie; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Veneno veneno = new Veneno();
                veneno.setId_veneno(rs.getInt("id_veneno"));
                Especie especie = new Especie();
                especie.setId_especie(rs.getInt("id_especie"));
                especie.setEspecie(rs.getString("especie"));
                especie.setGenero(rs.getString("genero"));
                veneno.setEspecie(especie);
                resultado.add(veneno);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public float obtenerCantidadEntregada(int id_especie) {
        float resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement("SELECT lote.id_especie, sum(entrega.cantidad) as cantidad_entregada "
                    + "FROM serpentario.lote as lote LEFT OUTER JOIN serpentario.lotes_entregas_solicitud as entrega ON lote.id_lote = entrega.id_lote "
                    + "WHERE lote.id_especie=? "
                    + "GROUP BY lote.id_especie");

            consulta.setInt(1, id_especie);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = rs.getFloat("cantidad_entregada");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;
    }
}
