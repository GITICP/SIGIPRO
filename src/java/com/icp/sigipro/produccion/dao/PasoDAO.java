/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.produccion.modelos.Formula_Maestra;
import com.icp.sigipro.produccion.modelos.Paso;
import com.icp.sigipro.produccion.modelos.Protocolo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLXML;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class PasoDAO extends DAO {

    public boolean insertarPaso(Paso paso) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.paso (version) "
                    + " VALUES (1) RETURNING id_paso");

            rs = consulta.executeQuery();
            if (rs.next()) {
                paso.setId_paso(rs.getInt("id_paso"));
                consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_paso (id_paso,version,estructura,nombre) "
                        + " VALUES (?,1,?,?) RETURNING id_historial");
                SQLXML xmlVal = getConexion().createSQLXML();
                xmlVal.setString(paso.getEstructuraString());
                consulta.setInt(1, paso.getId_paso());
                consulta.setSQLXML(2, xmlVal);
                consulta.setString(3, paso.getNombre());
                rs = consulta.executeQuery();
                if (rs.next()) {
                    resultado = true;
                    paso.setId_historial(rs.getInt("id_historial"));
                }
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

    public boolean editarPaso(Paso paso) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_paso (id_paso,version,estructura,nombre) "
                    + " VALUES (?,?,?,?) RETURNING id_historial");
            SQLXML xmlVal = getConexion().createSQLXML();
            xmlVal.setString(paso.getEstructuraString());
            consulta.setInt(1, paso.getId_paso());
            consulta.setInt(2, paso.getVersion() + 1);
            consulta.setSQLXML(3, xmlVal);
            consulta.setString(4, paso.getNombre());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                paso.setId_historial(rs.getInt("id_historial"));
                consulta = getConexion().prepareStatement(" UPDATE produccion.paso "
                        + "SET version = ? "
                        + "WHERE id_paso = ?; ");
                consulta.setInt(1, paso.getVersion() + 1);
                consulta.setInt(2, paso.getId_paso());
                if (consulta.executeUpdate() == 1) {
                    resultado = true;
                }
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

    public List<Paso> obtenerPasos() {
        List<Paso> resultado = new ArrayList<Paso>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT p.id_paso, h.nombre, p.version FROM produccion.paso as p "
                    + "LEFT JOIN produccion.historial_paso as h ON (h.id_paso = p.id_paso AND h.version = p.version); ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Paso paso = new Paso();
                paso.setId_paso(rs.getInt("id_paso"));
                paso.setNombre(rs.getString("nombre"));
                paso.setVersion(rs.getInt("version"));
                resultado.add(paso);
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

    public Paso obtenerPaso(int id_paso) {
        Paso resultado = new Paso();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT p.id_paso, h.nombre, p.version, h.estructura  FROM produccion.paso as p "
                    + "LEFT JOIN produccion.historial_paso as h ON (h.id_paso = p.id_paso AND h.version = p.version) "
                    + "WHERE p.id_paso = ?; ");
            consulta.setInt(1, id_paso);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_paso(rs.getInt("id_paso"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setVersion(rs.getInt("version"));
                resultado.setEstructura(rs.getSQLXML("estructura"));

                consulta = getConexion().prepareStatement(" SELECT h.id_historial, h.version "
                        + "FROM produccion.historial_paso as h "
                        + "WHERE h.id_paso = ?; ");
                consulta.setInt(1, id_paso);

                rs = consulta.executeQuery();
                resultado.setHistorial(new ArrayList<Paso>());
                while (rs.next()) {
                    Paso p = new Paso();
                    p.setId_historial(rs.getInt("id_historial"));
                    p.setVersion(rs.getInt("version"));
                    resultado.getHistorial().add(p);
                }
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

    public Paso obtenerHistorial(int id_historial) {
        Paso resultado = new Paso();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT h.id_paso, h.nombre, h.version, h.estructura  FROM produccion.historial_paso as h "
                    + "WHERE h.id_historial = ?; ");
            consulta.setInt(1, id_historial);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_paso(rs.getInt("id_paso"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setVersion(rs.getInt("version"));
                resultado.setEstructura(rs.getSQLXML("estructura"));

                
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
    
    public int obtenerVersion(int id_historial)
    {
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT version FROM produccion.historial_paso WHERE id_historial=?; ");
            consulta.setInt(1, id_historial);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = rs.getInt("version");
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
    
    public boolean activarVersion(int version, int id_protocolo) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.paso "
                    + "SET version=? "
                    + "WHERE id_paso= ?; ");
            consulta.setInt(1, version);
            consulta.setInt(2, id_protocolo);
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public boolean eliminarPaso(int id_paso) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM produccion.paso WHERE id_paso=?; ");
            consulta.setInt(1, id_paso);
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
