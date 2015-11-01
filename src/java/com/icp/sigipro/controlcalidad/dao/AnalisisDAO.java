/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.controlcalidad.modelos.AnalisisGrupoSolicitud;
import com.icp.sigipro.controlcalidad.modelos.Grupo;
import com.icp.sigipro.controlcalidad.modelos.Muestra;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
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
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.analisis (nombre,estructura,machote,estado) "
                    + " VALUES (?,?,?,?) RETURNING id_analisis");

            SQLXML xmlVal = getConexion().createSQLXML();
            xmlVal.setString(analisis.getEstructuraString());
            consulta.setString(1, analisis.getNombre());
            consulta.setSQLXML(2, xmlVal);
            consulta.setString(3, analisis.getMachote());
            consulta.setString(4, Analisis.PENDIENTE);
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
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean editarAnalisis(Analisis analisis) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            if (analisis.getMachote().equals("")) {
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
                    + "SET estado=? "
                    + "WHERE id_analisis = ?; ");
            consulta.setString(1, Analisis.APROBADO);
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
    
    public boolean retirarAnalisis(int id_analisis) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {

            consulta = getConexion().prepareStatement(" UPDATE control_calidad.analisis "
                    + "SET estado=? "
                    + "WHERE id_analisis = ?; ");
            consulta.setString(1, Analisis.RETIRADO);
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
            consulta = getConexion().prepareStatement("SELECT analisis.id_analisis, analisis.nombre,analisis.estado, count(ags.id_analisis) as cantidad "
                    + "FROM control_calidad.analisis as analisis "
                    + "LEFT OUTER JOIN control_calidad.analisis_grupo_solicitud as ags ON analisis.id_analisis = ags.id_analisis "
                    + "LEFT OUTER JOIN control_calidad.grupos as grupo ON grupo.id_grupo = ags.id_grupo "
                    + "LEFT OUTER JOIN control_calidad.solicitudes as solicitud ON grupo.id_solicitud = solicitud.id_solicitud "
                    + "WHERE ags.id_analisis_grupo_solicitud not in (SELECT id_analisis_grupo_solicitud FROM control_calidad.resultados)  "
                    + "AND solicitud.id_solicitud not in (SELECT id_solicitud FROM control_calidad.informes) "
                    + "GROUP BY analisis.id_analisis "
                    + "UNION "
                    + "SELECT analisis.id_analisis, analisis.nombre,analisis.estado, 0 as cantidad "
                    + "FROM CONTROL_CALIDAD.ANALISIS AS ANALISIS "
                    + "LEFT OUTER JOIN control_calidad.analisis_grupo_solicitud as ags ON analisis.id_analisis = ags.id_analisis "
                    + "LEFT OUTER JOIN control_calidad.grupos as grupo ON grupo.id_grupo = ags.id_grupo "
                    + "LEFT OUTER JOIN control_calidad.solicitudes as solicitud ON grupo.id_solicitud = solicitud.id_solicitud "
                    + "WHERE analisis.id_analisis not in " 
                    + "(SELECT ags.id_analisis FROM control_calidad.analisis as a "
                    + "INNER JOIN control_calidad.analisis_grupo_solicitud as ags ON a.id_analisis = ags.id_analisis "
                    + "LEFT OUTER JOIN control_calidad.grupos as grupo ON grupo.id_grupo = ags.id_grupo "
                    + "LEFT OUTER JOIN control_calidad.solicitudes as solicitud ON grupo.id_solicitud = solicitud.id_solicitud "
                    + "WHERE ags.id_analisis_grupo_solicitud not in (SELECT id_analisis_grupo_solicitud FROM control_calidad.resultados) "
                    + "AND solicitud.id_solicitud not in (SELECT id_solicitud FROM control_calidad.informes) ) "
                    + "GROUP BY analisis.id_analisis;");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Analisis analisis = new Analisis();
                analisis.setId_analisis(rs.getInt("id_analisis"));
                analisis.setNombre(rs.getString("nombre"));
                analisis.setCantidad_pendiente(rs.getInt("cantidad"));
                analisis.setEstado(rs.getString("estado"));
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
            consulta = getConexion().prepareStatement(" SELECT analisis.id_analisis, analisis.nombre, analisis.estructura, analisis.machote, analisis.estado "
                    + "FROM control_calidad.analisis as analisis "
                    + "WHERE analisis.id_analisis = ?; ");
            consulta.setInt(1, id_analisis);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_analisis(rs.getInt("id_analisis"));
                resultado.setMachote(rs.getString("machote"));
                resultado.setEstructura(rs.getSQLXML("estructura"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setEstado(rs.getString("estado"));
            }
            resultado.setTipos_equipos_analisis(this.obtenerTipoEquipos(id_analisis));
            resultado.setTipos_reactivos_analisis(this.obtenerTipoReactivos(id_analisis));
            resultado.setTipos_muestras_analisis(this.obtenerTipoMuestras(id_analisis));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public List<AnalisisGrupoSolicitud> obtenerSolicitudesAnalisis(int id_analisis) {
        PreparedStatement consulta = null;
        ResultSet rs = null;
        List<AnalisisGrupoSolicitud> resultado = new ArrayList<AnalisisGrupoSolicitud>();

        try {
            consulta = getConexion().prepareStatement(
                    "SELECT ags.id_analisis_grupo_solicitud, g.id_solicitud, s.numero_solicitud, s.estado, a.id_analisis, a.nombre as nombreanalisis, g.id_grupo, m.id_muestra, m.identificador, tm.nombre as nombretipo, tm.id_tipo_muestra "
                    + "FROM control_calidad.analisis_grupo_solicitud as ags "
                    + "LEFT OUTER JOIN control_calidad.grupos as g ON g.id_grupo = ags.id_grupo "
                    + "LEFT OUTER JOIN control_calidad.grupos_muestras as gm ON gm.id_grupo = g.id_grupo "
                    + "LEFT OUTER JOIN control_calidad.muestras as m ON m.id_muestra = gm.id_muestra "
                    + "LEFT OUTER JOIN control_calidad.tipos_muestras as tm ON tm.id_tipo_muestra = m.id_tipo_muestra "
                    + "LEFT OUTER JOIN control_calidad.analisis as a ON a.id_analisis = ags.id_analisis "
                    + "LEFT OUTER JOIN control_calidad.solicitudes as s ON s.id_solicitud = g.id_solicitud "
                    + "WHERE ags.id_analisis = ? "
                    + "AND s.id_solicitud not in (SELECT id_solicitud FROM control_calidad.informes) "
                    + "ORDER BY ags.id_analisis_grupo_solicitud;");

            consulta.setInt(1, id_analisis);

            System.out.println(consulta);

            AnalisisGrupoSolicitud ags = new AnalisisGrupoSolicitud();
            Grupo g = new Grupo();
            Muestra m = new Muestra();

            rs = consulta.executeQuery();
            if (rs.next()) {
                Analisis a = new Analisis();
                a.setId_analisis(rs.getInt("id_analisis"));
                a.setNombre(rs.getString("nombreanalisis"));
                do {
                    if (g.getId_grupo() == 0 || g.getId_grupo() != rs.getInt("id_grupo")) {
                        if (g.getId_grupo() != 0) {
                            ags.setGrupo(g);
                        }
                        SolicitudCC s = new SolicitudCC();
                        s.setId_solicitud(rs.getInt("id_solicitud"));
                        s.setNumero_solicitud(rs.getString("numero_solicitud"));
                        s.setEstado(rs.getString("estado"));
                        g = new Grupo();
                        g.setId_grupo(rs.getInt("id_grupo"));
                        g.setSolicitud(s);
                        g.setGrupos_muestras(new ArrayList<Muestra>());
                    }
                    if (ags.getId_analisis_grupo_solicitud() == 0 || ags.getId_analisis_grupo_solicitud() != rs.getInt("id_analisis_grupo_solicitud")) {
                        if (ags.getId_analisis_grupo_solicitud() != 0) {
                            resultado.add(ags);
                        }
                        ags = new AnalisisGrupoSolicitud();
                        ags.setAnalisis(a);
                        ags.setId_analisis_grupo_solicitud(rs.getInt("id_analisis_grupo_solicitud"));
                        ags.setResultados(new ArrayList<Resultado>());
                    }

                    TipoMuestra tm = new TipoMuestra();
                    tm.setId_tipo_muestra(rs.getInt("id_tipo_muestra"));
                    tm.setNombre(rs.getString("nombretipo"));
                    m = new Muestra();
                    m.setId_muestra(rs.getInt("id_muestra"));
                    m.setIdentificador(rs.getString("identificador"));
                    m.setTipo_muestra(tm);

                    g.getGrupos_muestras().add(m);

                } while (rs.next());
            }
            ags.setGrupo(g);
            resultado.add(ags);

            consulta = getConexion().prepareStatement("SELECT ags.id_analisis_grupo_solicitud, r.id_resultado "
                    + "FROM control_calidad.analisis_grupo_solicitud ags "
                    + "INNER JOIN control_calidad.resultados r ON r.id_analisis_grupo_solicitud = ags.id_analisis_grupo_solicitud "
                    + "WHERE ags.id_analisis = ? "
                    + "ORDER BY ags.id_analisis_grupo_solicitud;");
            consulta.setInt(1, id_analisis);

            rs = consulta.executeQuery();

            while (rs.next()) {
                Resultado r = new Resultado();
                r.setId_resultado(rs.getInt("id_resultado"));
                for (AnalisisGrupoSolicitud a : resultado) {
                    if (a.getId_analisis_grupo_solicitud() == rs.getInt("id_analisis_grupo_solicitud")) {
                        a.agregarResultado(r);
                    }
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

    public List<TipoMuestra> obtenerTipoMuestras(int id_analisis) {
        PreparedStatement consulta = null;
        ResultSet rs = null;
        List<TipoMuestra> resultado = new ArrayList<TipoMuestra>();
        try {
            consulta = getConexion().prepareStatement(" SELECT tipo.id_tipo_muestra, tipo.nombre "
                    + "FROM control_calidad.tipos_muestras as tipo INNER JOIN control_calidad.tipos_muestras_analisis as tiposanalisis ON tiposanalisis.id_tipo_muestra = tipo.id_tipo_muestra  "
                    + "WHERE tiposanalisis.id_analisis = ?; ");
            consulta.setInt(1, id_analisis);
            rs = consulta.executeQuery();
            while (rs.next()) {
                TipoMuestra tipomuestra = new TipoMuestra();
                tipomuestra.setId_tipo_muestra(rs.getInt("id_tipo_muestra"));
                tipomuestra.setNombre(rs.getString("nombre"));
                resultado.add(tipomuestra);
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
