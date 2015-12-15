/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.produccion.modelos.Categoria_AA;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class Categoria_AADAO extends DAO {
    
    public boolean insertarCategoria_AA(Categoria_AA categoria)
    {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.categoria_aa (nombre,descripcion) "
                                                                        + " VALUES (?,?) RETURNING id_categoria_aa");
            consulta.setString(1, categoria.getNombre());
            consulta.setString(2, categoria.getDescripcion());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                categoria.setId_categoria_aa(rs.getInt("id_categoria_aa"));
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public boolean editarCategoria_AA(Categoria_AA categoria)
    {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.categoria_aa "
                    + "SET nombre=?, descripcion=? "
                    + "WHERE id_categoria_aa = ?; ");
            consulta.setString(1, categoria.getNombre());
            consulta.setString(2, categoria.getDescripcion());
            consulta.setInt(3, categoria.getId_categoria_aa());
            if (consulta.executeUpdate()==1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public List<Categoria_AA> obtenerCategorias_AA()
    {
        List<Categoria_AA> resultado = new ArrayList<Categoria_AA>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT id_categoria_aa, nombre, descripcion FROM produccion.categoria_aa; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Categoria_AA categoria = new Categoria_AA();
                categoria.setId_categoria_aa(rs.getInt("id_categoria_aa"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setDescripcion(rs.getString("descripcion"));
                resultado.add(categoria);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public Categoria_AA obtenerCategoria_AA(int id_categoria_aa)
    {
        Categoria_AA resultado = new Categoria_AA();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.categoria_aa WHERE id_categoria_aa=?; ");
            consulta.setInt(1, id_categoria_aa);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_categoria_aa(rs.getInt("id_categoria_aa"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setDescripcion(rs.getString("descripcion"));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public boolean eliminarCategoria_AA(int id_categoria_aa) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM produccion.categoria_aa WHERE id_categoria_aa=?; ");
            consulta.setInt(1, id_categoria_aa);
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
    
}