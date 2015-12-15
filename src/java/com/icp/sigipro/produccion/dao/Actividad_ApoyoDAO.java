/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.produccion.modelos.Actividad_Apoyo;
import com.icp.sigipro.produccion.modelos.Categoria_AA;
import com.icp.sigipro.produccion.modelos.Actividad_Apoyo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLXML;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class Actividad_ApoyoDAO extends DAO {

    public boolean insertarActividad_Apoyo(Actividad_Apoyo actividad) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.actividad_apoyo (version, aprobacion_calidad, aprobacion_direccion, aprobacion_regente, aprobacion_coordinador) "
                    + " VALUES (1,false, false, false,false) RETURNING id_actividad");
            rs = consulta.executeQuery();
            if (rs.next()) {
                actividad.setId_actividad(rs.getInt("id_actividad"));
                consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_actividad_apoyo (id_actividad,version,estructura,nombre,id_categoria_aa) "
                        + " VALUES (?,1,?,?,?) RETURNING id_historial");
                SQLXML xmlVal = getConexion().createSQLXML();
                xmlVal.setString(actividad.getEstructuraString());
                consulta.setInt(1, actividad.getId_actividad());
                consulta.setSQLXML(2, xmlVal);
                consulta.setString(3, actividad.getNombre());
                consulta.setInt(4, actividad.getCategoria().getId_categoria_aa());
                rs = consulta.executeQuery();
                if (rs.next()) {
                    resultado = true;
                    actividad.setId_historial(rs.getInt("id_historial"));
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

    public boolean editarActividad_Apoyo(Actividad_Apoyo actividad) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_actividad_apoyo (id_actividad,version,estructura,nombre) "
                    + " VALUES (?,?,?,?) RETURNING id_historial");
            SQLXML xmlVal = getConexion().createSQLXML();
            xmlVal.setString(actividad.getEstructuraString());
            consulta.setInt(1, actividad.getId_actividad());
            consulta.setInt(2, actividad.getVersion() + 1);
            consulta.setSQLXML(3, xmlVal);
            consulta.setString(4, actividad.getNombre());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                actividad.setId_historial(rs.getInt("id_historial"));
                consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                        + "SET version = ?, aprobacion_calidad = false, aprobacion_regente = false, aprobacion_coordinador = false, aprobacion_direccion=false  "
                        + "WHERE id_actividad = ?; ");
                consulta.setInt(1, actividad.getVersion() + 1);
                consulta.setInt(2, actividad.getId_actividad());
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

    public List<Actividad_Apoyo> obtenerActividades_Apoyo() {
        List<Actividad_Apoyo> resultado = new ArrayList<Actividad_Apoyo>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT aa.*, haa.nombre as nombreaa, aa.version, c.id_categoria_aa, c.nombre as nombrec "
                    + "FROM produccion.actividad_apoyo as aa "
                    + "LEFT JOIN produccion.historial_actividad_apoyo as haa ON (haa.id_actividad = aa.id_actividad AND haa.version = aa.version) "
                    + "LEFT JOIN produccion.categoria_aa ON (haa.id_categoria_aa = c.id_categoria_aa); ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Actividad_Apoyo actividad = new Actividad_Apoyo();
                actividad.setId_actividad(rs.getInt("id_actividad"));
                actividad.setAprobacion_calidad(rs.getBoolean("aprobacion_calidad"));
                actividad.setAprobacion_coordinador(rs.getBoolean("aprobacion_coordinador"));
                actividad.setAprobacion_direccion(rs.getBoolean("aprobacion_direccion"));
                actividad.setAprobacion_regente(rs.getBoolean("aprobacion_regente"));
                actividad.setNombre(rs.getString("nombreaa"));
                actividad.setVersion(rs.getInt("version"));
                Categoria_AA categoria = new Categoria_AA();
                categoria.setId_categoria_aa(rs.getInt("id_categoria_aa"));
                categoria.setNombre(rs.getString("nombrec"));
                actividad.setCategoria(categoria);
                resultado.add(actividad);
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

    public Actividad_Apoyo obtenerActividad_Apoyo(int id_actividad) {
        Actividad_Apoyo resultado = new Actividad_Apoyo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT aa.*, haa.nombre, haa.estructura, c.id_categoria_aa, c.nombre as nombrec   "
                    + "FROM produccion.actividad_apoyo as aa "
                    + "LEFT JOIN produccion.historial_actividad_apoyo as haa ON (haa.id_actividad = aa.id_actividad AND haa.version = aa.version) "
                    + "LEFT JOIN produccion.categoria_aa ON (haa.id_categoria_aa = c.id_categoria_aa) "
                    + "WHERE aa.id_actividad = ?; ");
            consulta.setInt(1, id_actividad);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_actividad(rs.getInt("id_actividad"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setVersion(rs.getInt("version"));
                resultado.setEstructura(rs.getSQLXML("estructura"));
                resultado.setAprobacion_calidad(rs.getBoolean("aprobacion_calidad"));
                resultado.setAprobacion_coordinador(rs.getBoolean("aprobacion_coordinador"));
                resultado.setAprobacion_direccion(rs.getBoolean("aprobacion_direccion"));
                resultado.setAprobacion_regente(rs.getBoolean("aprobacion_regente"));
                Categoria_AA categoria = new Categoria_AA();
                categoria.setId_categoria_aa(rs.getInt("id_categoria_aa"));
                categoria.setNombre(rs.getString("nombrec"));
                resultado.setCategoria(categoria);
                consulta = getConexion().prepareStatement(" SELECT h.id_historial, h.version "
                        + "FROM produccion.historial_actividad_apoyo as h "
                        + "WHERE h.id_actividad = ?; ");
                consulta.setInt(1, id_actividad);
                rs = consulta.executeQuery();
                resultado.setHistorial(new ArrayList<Actividad_Apoyo>());
                while (rs.next()) {
                    Actividad_Apoyo aa = new Actividad_Apoyo();
                    aa.setId_historial(rs.getInt("id_historial"));
                    aa.setVersion(rs.getInt("version"));
                    resultado.getHistorial().add(aa);
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

    public Actividad_Apoyo obtenerHistorial(int id_historial) {
        Actividad_Apoyo resultado = new Actividad_Apoyo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT h.id_actividad, h.nombre, h.version, h.estructura, c.id_categoria_aa, c.nombre as nombrec    "
                    + "FROM produccion.historial_actividad_apoyo as h "
                    + "LEFT JOIN produccion.categoria_aa ON (h.id_categoria_aa = c.id_categoria_aa) "
                    + "WHERE h.id_historial = ?; ");
            consulta.setInt(1, id_historial);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_actividad(rs.getInt("id_actividad"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setVersion(rs.getInt("version"));
                resultado.setEstructura(rs.getSQLXML("estructura"));
                Categoria_AA categoria = new Categoria_AA();
                categoria.setId_categoria_aa(rs.getInt("id_categoria_aa"));
                categoria.setNombre(rs.getString("nombrec"));
                resultado.setCategoria(categoria);

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

    public int obtenerVersion(int id_historial) {
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT version FROM produccion.historial_actividad_apoyo WHERE id_historial=?; ");
            consulta.setInt(1, id_historial);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = rs.getInt("version");
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

    public boolean activarVersion(int version, int id_actividad) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                    + "SET version=? "
                    + "WHERE id_actividad= ?; ");
            consulta.setInt(1, version);
            consulta.setInt(2, id_actividad);
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

    public boolean eliminarActividad_Apoyo(int id_actividad) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM produccion.actividad_apoyo WHERE id_actividad=?; ");
            consulta.setInt(1, id_actividad);
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
    
    public Actividad_Apoyo obtenerAprobaciones(int id_actividad) {
        Actividad_Apoyo resultado = new Actividad_Apoyo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.actividad_apoyo WHERE id_actividad=?; ");
            consulta.setInt(1, id_actividad);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_actividad(rs.getInt("id_actividad"));
                resultado.setAprobacion_calidad(rs.getBoolean("aprobacion_calidad"));
                resultado.setAprobacion_coordinador(rs.getBoolean("aprobacion_coordinador"));
                resultado.setAprobacion_direccion(rs.getBoolean("aprobacion_direccion"));
                resultado.setAprobacion_regente(rs.getBoolean("aprobacion_regente"));
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
    
    public boolean rechazarActividad_Apoyo(int id_actividad, String observaciones) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                    + "SET observaciones=?, aprobacion_calidad = false, aprobacion_regente = false, aprobacion_coordinador = false, aprobacion_direccion=false "
                    + " WHERE id_actividad=?; ");
            consulta.setString(1, observaciones);
            consulta.setInt(2, id_actividad);
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

    public boolean aprobarActividad_Apoyo(int id_actividad, int actor) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            switch (actor) {
                case (1):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                            + "SET aprobacion_calidad = true "
                            + "WHERE id_actividad=?; ");
                    break;
                case (2):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                            + "SET aprobacion_regente=true "
                            + "WHERE id_actividad=?; ");
                    break;
                case (3):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                            + "SET aprobacion_coordinador= true "
                            + "WHERE id_actividad=?; ");
                    break;
                case (4):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                            + "SET aprobacion_direccion = true, observaciones='' "
                            + "WHERE id_actividad=?; ");
                    break;
            }
            consulta.setInt(1, id_actividad);
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
