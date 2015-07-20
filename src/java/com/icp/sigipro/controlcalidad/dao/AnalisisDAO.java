/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.controlcalidad.modelos.TipoEquipo;
import com.icp.sigipro.controlcalidad.modelos.TipoMuestra;
import com.icp.sigipro.controlcalidad.modelos.TipoReactivo;
import com.icp.sigipro.core.DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class AnalisisDAO extends DAO {

    public boolean insertarAnalisis(Analisis analisis) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.analisis (nombre,estructura,machote,aprobado) "
                    + " VALUES (?,?,?,?) RETURNING id_analisis");

            SQLXML xmlVal = getConexion().createSQLXML();
            xmlVal.setString(analisis.getEstructuraString());
            consulta.setString(1, analisis.getNombre());
            consulta.setSQLXML(2, xmlVal);
            consulta.setString(3, analisis.getMachote());
            consulta.setBoolean(4, analisis.isAprobado());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                analisis.setId_analisis(rs.getInt("id_analisis"));
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

    public boolean insertarTipoReactivo(List<TipoReactivo> tiporeactivos, int id_analisis) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.tipos_reactivos_analisis (id_analisis,id_tipo_reactivo) "
                    + " VALUES (?,?);");

            for (TipoReactivo tipo : tiporeactivos) {
                consulta.setInt(1, id_analisis);
                consulta.setInt(2, tipo.getId_tipo_reactivo());
                consulta.addBatch();
            }
            consulta.executeBatch();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;
    }

    public boolean eliminarTiposEquiposAnalisis(int id_analisis) {
        PreparedStatement consulta = null;
        boolean resultado = false;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.tipos_equipos_analisis WHERE id_analisis=?; ");
            consulta.setInt(1, id_analisis);
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

    public boolean eliminarTiposReactivosAnalisis(int id_analisis) {
        PreparedStatement consulta = null;
        boolean resultado = false;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.tipos_reactivos_analisis WHERE id_analisis=?; ");
            consulta.setInt(1, id_analisis);
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

    public boolean insertarTipoEquipo(List<TipoEquipo> tipoequipos, int id_analisis) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.tipos_equipos_analisis (id_analisis,id_tipo_equipo) "
                    + " VALUES (?,?);");

            for (TipoEquipo tipo : tipoequipos) {
                consulta.setInt(1, id_analisis);
                consulta.setInt(2, tipo.getId_tipo_equipo());
                consulta.addBatch();
            }
            consulta.executeBatch();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public boolean insertarTipoMuestra(List<TipoMuestra> tipos_muestras_analisis, int id_analisis) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.tipos_muestras_analisis (id_analisis,id_tipo_muestra) "
                    + " VALUES (?,?);");

            for (TipoMuestra tipo : tipos_muestras_analisis) {
                consulta.setInt(1, id_analisis);
                consulta.setInt(2, tipo.getId_tipo_muestra());
                consulta.addBatch();
            }
            consulta.executeBatch();

        } catch (SQLException ex) {
            ex.printStackTrace();
            SQLException ex2 = ex.getNextException();
            ex2.printStackTrace();
        }
        finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean editarAnalisis(Analisis analisis) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            if (analisis.getMachote() == null) {
                consulta = getConexion().prepareStatement(" UPDATE control_calidad.analisis "
                        + "SET nombre=?, estructura=? "
                        + "WHERE id_analisis = ?; ");
                SQLXML xmlVal = getConexion().createSQLXML();
                xmlVal.setString(analisis.getEstructuraString());
                consulta.setString(1, analisis.getNombre());
                consulta.setSQLXML(2, xmlVal);
                consulta.setInt(3, analisis.getId_analisis());
            } else {
                consulta = getConexion().prepareStatement(" UPDATE control_calidad.analisis "
                        + "SET nombre=?, estructura=?, machote=? "
                        + "WHERE id_analisis = ?; ");
                SQLXML xmlVal = getConexion().createSQLXML();
                xmlVal.setString(analisis.getEstructuraString());
                consulta.setString(1, analisis.getNombre());
                consulta.setSQLXML(2, xmlVal);
                consulta.setString(3, analisis.getMachote());
                consulta.setInt(4, analisis.getId_analisis());
            }
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

    public boolean aprobarAnalisis(int id_analisis) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {

            consulta = getConexion().prepareStatement(" UPDATE control_calidad.analisis "
                    + "SET aprobado=? "
                    + "WHERE id_analisis = ?; ");
            consulta.setBoolean(1, true);
            consulta.setInt(2, id_analisis);
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

    public List<Analisis> obtenerAnalisis() {
        PreparedStatement consulta = null;
        ResultSet rs = null;
        List<Analisis> resultado = new ArrayList<Analisis>();
        try {
            consulta = getConexion().prepareStatement("SELECT analisis.id_analisis, analisis.nombre,analisis.aprobado, count(ags.id_analisis) as cantidad "
                    + "FROM control_calidad.analisis as analisis "
                    + "LEFT OUTER JOIN control_calidad.analisis_grupo_solicitud as ags ON analisis.id_analisis = ags.id_analisis "
                    + "WHERE ags.id_analisis_grupo_solicitud not in (SELECT id_analisis_grupo_solicitud FROM control_calidad.resultados) "
                    + "GROUP BY analisis.id_analisis "
                    + "UNION "
                    + "SELECT analisis.id_analisiS, analisis.nombre,analisis.aprobado, 0 as cantidad "
                    + "FROM CONTROL_CALIDAD.ANALISIS AS ANALISIS "
                    + "WHERE analisis.id_analisis not in (SELECT ags.id_analisis FROM control_calidad.analisis as a INNER JOIN control_calidad.analisis_grupo_solicitud as ags ON a.id_analisis = ags.id_analisis);");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Analisis analisis = new Analisis();
                analisis.setId_analisis(rs.getInt("id_analisis"));
                analisis.setNombre(rs.getString("nombre"));
                analisis.setCantidad_pendiente(rs.getInt("cantidad"));
                analisis.setAprobado(rs.getBoolean("aprobado"));
                resultado.add(analisis);
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

    public Analisis obtenerAnalisis(int id_analisis) {
        PreparedStatement consulta = null;
        ResultSet rs = null;
        Analisis resultado = new Analisis();
        try {
            consulta = getConexion().prepareStatement(" SELECT analisis.id_analisis, analisis.nombre, analisis.estructura, analisis.machote, analisis.aprobado "
                    + "FROM control_calidad.analisis as analisis "
                    + "WHERE analisis.id_analisis = ?; ");
            consulta.setInt(1, id_analisis);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_analisis(rs.getInt("id_analisis"));
                resultado.setMachote(rs.getString("machote"));
                resultado.setEstructura(rs.getSQLXML("estructura"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setAprobado(rs.getBoolean("aprobado"));
            }
            resultado.setTipos_equipos_analisis(this.obtenerTipoEquipos(id_analisis));
            resultado.setTipos_reactivos_analisis(this.obtenerTipoReactivos(id_analisis));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public List<TipoEquipo> obtenerTipoEquipos(int id_analisis) {
        PreparedStatement consulta = null;
        ResultSet rs = null;
        List<TipoEquipo> resultado = new ArrayList<TipoEquipo>();
        try {
            consulta = getConexion().prepareStatement(" SELECT tipo.id_tipo_equipo, tipo.nombre "
                    + "FROM control_calidad.tipos_equipos as tipo INNER JOIN control_calidad.tipos_equipos_analisis as tiposanalisis ON tiposanalisis.id_tipo_equipo = tipo.id_tipo_equipo  "
                    + "WHERE tiposanalisis.id_analisis = ?; ");
            consulta.setInt(1, id_analisis);
            rs = consulta.executeQuery();
            while (rs.next()) {
                TipoEquipo tipoequipo = new TipoEquipo();
                tipoequipo.setId_tipo_equipo(rs.getInt("id_tipo_equipo"));
                tipoequipo.setNombre(rs.getString("nombre"));
                resultado.add(tipoequipo);
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

    public List<TipoReactivo> obtenerTipoReactivos(int id_analisis) {
        PreparedStatement consulta = null;
        ResultSet rs = null;
        List<TipoReactivo> resultado = new ArrayList<TipoReactivo>();
        try {
            consulta = getConexion().prepareStatement(" SELECT tipo.id_tipo_reactivo, tipo.nombre "
                    + "FROM control_calidad.tipos_reactivos as tipo INNER JOIN control_calidad.tipos_reactivos_analisis as tiposanalisis ON tiposanalisis.id_tipo_reactivo = tipo.id_tipo_reactivo  "
                    + "WHERE tiposanalisis.id_analisis = ?; ");
            consulta.setInt(1, id_analisis);
            rs = consulta.executeQuery();
            while (rs.next()) {
                TipoReactivo tiporeactivo = new TipoReactivo();
                tiporeactivo.setId_tipo_reactivo(rs.getInt("id_tipo_reactivo"));
                tiporeactivo.setNombre(rs.getString("nombre"));
                resultado.add(tiporeactivo);
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

    public boolean eliminarAnalisis(int id_analisis) {
        PreparedStatement consulta = null;
        boolean resultado = false;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.analisis WHERE id_analisis=?; ");
            consulta.setInt(1, id_analisis);
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
