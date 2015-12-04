/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.produccion.modelos.Formula_Maestra;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class Formula_MaestraDAO extends DAO {
    
    public boolean insertarFormula_Maestra(Formula_Maestra formula)
    {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.formula_maestra (nombre,descripcion) "
                                                                        + " VALUES (?,?) RETURNING id_formula_maestra");
            consulta.setString(1, formula.getNombre());
            consulta.setString(2, formula.getDescripcion());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                formula.setId_formula_maestra(rs.getInt("id_formula_maestra"));
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
    
    public boolean editarFormula_Maestra(Formula_Maestra formula)
    {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.formula_maestra "
                    + "SET nombre=?, descripcion=? "
                    + "WHERE id_formula_maestra= ?; ");
            consulta.setString(1, formula.getNombre());
            consulta.setString(2, formula.getDescripcion());
            consulta.setInt(3, formula.getId_formula_maestra());
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
    
    public List<Formula_Maestra> obtenerFormulas_Maestras()
    {
        List<Formula_Maestra> resultado = new ArrayList<Formula_Maestra>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT id_formula_maestra, nombre, descripcion FROM produccion.formula_maestra; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Formula_Maestra formula = new Formula_Maestra();
                formula.setId_formula_maestra(rs.getInt("id_formula_maestra"));
                formula.setNombre(rs.getString("nombre"));
                formula.setDescripcion(rs.getString("descripcion"));
                resultado.add(formula);
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
    
    public Formula_Maestra obtenerFormula_Maestra(int id_formula_maestra)
    {
        Formula_Maestra resultado = new Formula_Maestra();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.formula_maestra WHERE id_formula_maestra=?; ");
            consulta.setInt(1, id_formula_maestra);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_formula_maestra(rs.getInt("id_formula_maestra"));
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
    
    public boolean eliminarFormula_Maestra(int id_formula_maestra) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM produccion.formula_maestra WHERE id_formula_maestra=?; ");
            consulta.setInt(1, id_formula_maestra);
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
