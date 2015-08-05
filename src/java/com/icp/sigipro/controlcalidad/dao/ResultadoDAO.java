/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.controlcalidad.modelos.AnalisisGrupoSolicitud;
import com.icp.sigipro.controlcalidad.modelos.Equipo;
import com.icp.sigipro.controlcalidad.modelos.Grupo;
import com.icp.sigipro.controlcalidad.modelos.Muestra;
import com.icp.sigipro.controlcalidad.modelos.Reactivo;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.controlcalidad.modelos.TipoEquipo;
import com.icp.sigipro.controlcalidad.modelos.TipoMuestra;
import com.icp.sigipro.controlcalidad.modelos.TipoReactivo;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class ResultadoDAO extends DAO {

    public ResultadoDAO() {
    }
    
    public Resultado insertarResultado(Resultado resultado) throws SIGIPROException {
        boolean commit = false;
        PreparedStatement insert_resultado = null;
        ResultSet rs_insert_resultado = null;
        PreparedStatement insert_reactivos = null;
        PreparedStatement insert_equipos = null;
        
        PreparedStatement consulta_solicitud = null;
        ResultSet rs_solicitud = null;
        
        try {
            
            getConexion().setAutoCommit(false);
            
            insert_resultado = getConexion().prepareStatement(
                    " INSERT INTO control_calidad.resultados (path, datos, id_usuario, fecha, id_analisis_grupo_solicitud, resultado) "
                    + " VALUES (?,?,?,?,?,?) RETURNING id_resultado; "
            );
            
            SQLXML datos = getConexion().createSQLXML();
            datos.setString(resultado.getDatos_string());
            
            insert_resultado.setString(1, resultado.getPath());
            insert_resultado.setSQLXML(2, datos);
            insert_resultado.setInt(3, resultado.getUsuario().getId_usuario());
            insert_resultado.setDate(4, helper_fechas.getFecha_hoy());
            insert_resultado.setInt(5, resultado.getAgs().getId_analisis_grupo_solicitud());
            insert_resultado.setString(6, resultado.getResultado());
            
            rs_insert_resultado = insert_resultado.executeQuery();
            
            if (rs_insert_resultado.next()) {
                resultado.setId_resultado(rs_insert_resultado.getInt("id_resultado"));
            }
            
            if (resultado.tieneReactivos()) {
                insert_reactivos = getConexion().prepareStatement(
                        " INSERT INTO control_calidad.reactivos_resultado (id_resultado, id_reactivo) VALUES (?,?);"
                );
                
                for (Reactivo r : resultado.getReactivos_resultado()) {
                    insert_reactivos.setInt(1, resultado.getId_resultado());
                    insert_reactivos.setInt(2, r.getId_reactivo());
                    insert_reactivos.addBatch();
                }
                
                insert_reactivos.executeBatch();
            }
            
            if (resultado.tieneEquipos()) {
                insert_equipos = getConexion().prepareStatement(
                        " INSERT INTO control_calidad.equipos_resultado (id_resultado, id_equipo) VALUES (?,?); "
                );
                
                for (Equipo e : resultado.getEquipos_resultado()) {
                    insert_equipos.setInt(1, resultado.getId_resultado());
                    insert_equipos.setInt(2, e.getId_equipo());
                    insert_equipos.addBatch();
                }
                
                insert_equipos.executeBatch();
            }
            
            consulta_solicitud = getConexion().prepareStatement(
                    " SELECT g.id_solicitud from control_calidad.analisis_grupo_solicitud ags "
                    + " INNER JOIN control_calidad.grupos g ON ags.id_grupo = g.id_grupo "
                    + " WHERE ags.id_analisis_grupo_solicitud = ?; "
            );
            
            consulta_solicitud.setInt(1, resultado.getAgs().getId_analisis_grupo_solicitud());
            
            rs_solicitud = consulta_solicitud.executeQuery();
            
            if (rs_solicitud.next()) {
                Grupo g = new Grupo();
                SolicitudCC s = new SolicitudCC();
                s.setId_solicitud(rs_solicitud.getInt("id_solicitud"));
                g.setSolicitud(s);
                resultado.getAgs().setGrupo(g);
                resultado.getAgs().getGrupo().setSolicitud(s);
            }
            
            commit = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al ingresar el resultado. Inténtelo nuevamente.");
        } finally {
            try {
                if (commit) {
                    getConexion().commit();
                } else {
                    getConexion().rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new SIGIPROException("Error de comunicación con la base de datos. Inténtelo nuevamente o notifique al administrador del sistema.");
            }
            cerrarSilencioso(rs_insert_resultado);
            cerrarSilencioso(insert_resultado);
            cerrarSilencioso(insert_reactivos);
            cerrarSilencioso(insert_equipos);
            cerrarConexion();
        }
        
        return resultado;
    }
    
    public Resultado obtenerResultado(int id_resultado) throws SIGIPROException {
        Resultado resultado = null;
        
        PreparedStatement consulta = null;
        ResultSet rs = null;
        PreparedStatement consulta_reactivos = null;
        ResultSet rs_reactivos = null;
        PreparedStatement consulta_equipos = null;
        ResultSet rs_equipos = null;
        
        try {
            consulta = getConexion().prepareStatement(
                    " SELECT r.*, ags.id_analisis, a.nombre, s.id_solicitud, s.numero_solicitud "
                    + " FROM control_calidad.resultados r "
                    + "   INNER JOIN control_calidad.analisis_grupo_solicitud ags ON ags.id_analisis_grupo_solicitud = r.id_analisis_grupo_solicitud "
                    + "INNER JOIN control_calidad.analisis as a ON a.id_analisis = ags.id_analisis "
                            + "LEFT JOIN control_calidad.grupos as g ON g.id_grupo = ags.id_grupo "
                            + "LEFT JOIN control_calidad.solicitudes as s ON s.id_solicitud = g.id_solicitud "
                    + " WHERE r.id_resultado = ? "
            );
            
            consulta.setInt(1, id_resultado);
            
            rs = consulta.executeQuery();
            
            if (rs.next()) {
                resultado = new Resultado();
                
                resultado.setId_resultado(rs.getInt("id_resultado"));
                resultado.setDatos(rs.getSQLXML("datos"));
                resultado.setPath(rs.getString("path"));
                resultado.setResultado(rs.getString("resultado"));
                
                AnalisisGrupoSolicitud ags = new AnalisisGrupoSolicitud();
                ags.setId_analisis_grupo_solicitud(rs.getInt("id_analisis_grupo_solicitud"));
                Analisis a = new Analisis();
                a.setId_analisis(rs.getInt("id_analisis"));
                a.setNombre(rs.getString("nombre"));
                ags.setAnalisis(a);
                SolicitudCC s = new SolicitudCC();
                s.setId_solicitud(rs.getInt("id_solicitud"));
                s.setNumero_solicitud(rs.getString("numero_solicitud"));
                Grupo g = new Grupo();
                g.setSolicitud(s);
                ags.setGrupo(g);
                
                resultado.setAgs(ags);
                
            } else {
                throw new SIGIPROException("El resultado que está intentando buscar no existe.");
            }
            
            consulta_reactivos = getConexion().prepareStatement(
                    " SELECT r.id_reactivo, r.nombre, tr.id_tipo_reactivo, tr.nombre as nombre_tipo "
                    + " FROM control_calidad.reactivos_resultado rr "
                    + "      INNER JOIN control_calidad.reactivos r ON rr.id_reactivo = r.id_reactivo "
                    + "      INNER JOIN control_calidad.tipos_reactivos tr ON tr.id_tipo_reactivo = r.id_tipo_reactivo "
                    + "      WHERE rr.id_resultado = ?; "
            );
            
            consulta_reactivos.setInt(1, id_resultado);
            rs_reactivos = consulta_reactivos.executeQuery();
            
            List<Reactivo> reactivos_resultado = new ArrayList<Reactivo>();            
            
            if (rs_reactivos.next()) {
                do {
                    
                    Reactivo r = new Reactivo();
                    
                    r.setId_reactivo(rs_reactivos.getInt("id_reactivo"));
                    r.setNombre(rs_reactivos.getString("nombre"));
                    
                    TipoReactivo tr = new TipoReactivo();
                    
                    tr.setId_tipo_reactivo(rs_reactivos.getInt("id_tipo_reactivo"));
                    tr.setNombre(rs_reactivos.getString("nombre_tipo"));
                    
                    r.setTipo_reactivo(tr);
                    
                    reactivos_resultado.add(r);
                    
                } while (rs_reactivos.next());
            }
            
            resultado.setReactivos_resultado(reactivos_resultado);
            
            consulta_equipos = getConexion().prepareStatement(
                    " SELECT e.id_equipo, e.nombre, te.id_tipo_equipo, te.nombre as nombre_tipo "
                    + " FROM control_calidad.equipos_resultado er "
                    + "   INNER JOIN control_calidad.equipos e ON er.id_equipo = e.id_equipo "
                    + "   INNER JOIN control_calidad.tipos_equipos te ON te.id_tipo_equipo = e.id_tipo_equipo "
                    + "   WHERE er.id_resultado = ?; "
            );
            
            consulta_equipos.setInt(1, id_resultado);
            rs_equipos = consulta_equipos.executeQuery();
            
            List<Equipo> equipos_resultado = new ArrayList<Equipo>();
            
            if (rs_equipos.next()) {
                
                do {
                    
                    Equipo e = new Equipo();
                    
                    e.setId_equipo(rs_equipos.getInt("id_equipo"));
                    e.setNombre(rs_equipos.getString("nombre"));
                    
                    TipoEquipo te = new TipoEquipo();
                    
                    te.setId_tipo_equipo(rs_equipos.getInt("id_tipo_equipo"));
                    te.setNombre(rs_equipos.getString("nombre_tipo"));
                    
                    e.setTipo_equipo(te);
                    
                    equipos_resultado.add(e);
                    
                } while (rs_equipos.next());
            }
            
            resultado.setEquipos_resultado(equipos_resultado);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener el resultado. Notifique al administrador del sistema.");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarSilencioso(rs_equipos);
            cerrarSilencioso(consulta_equipos);
            cerrarSilencioso(rs_reactivos);
            cerrarSilencioso(consulta_reactivos);
            cerrarConexion();
        }
        
        return resultado;
    }
    
    public AnalisisGrupoSolicitud obtenerAGS(int id_ags) throws SIGIPROException {
        
        AnalisisGrupoSolicitud ags_resultado = new AnalisisGrupoSolicitud();
        List<Resultado> lista_resultados = new ArrayList<Resultado>();
        
        PreparedStatement consulta_grupo = null;
        ResultSet rs_grupo = null;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        
        try {
            consulta = getConexion().prepareStatement(
                    " SELECT r.id_resultado, r.fecha, r.repeticion, r.resultado, u.id_usuario, u.nombre_completo "
                    + " FROM control_calidad.resultados r "
                    + "     INNER JOIN seguridad.usuarios u ON u.id_usuario = r.id_usuario "
                    + " WHERE r.id_analisis_grupo_solicitud = ? "
                    + " ORDER BY repeticion ASC"
            );
            
            consulta.setInt(1, id_ags);
            
            rs = consulta.executeQuery();
            
            while (rs.next()) {
                Resultado r = new Resultado();
                
                r.setId_resultado(rs.getInt("id_resultado"));
                r.setFecha(rs.getDate("fecha"));
                r.setRepeticion(rs.getInt("repeticion"));
                r.setResultado(rs.getString("resultado"));
                
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombreCompleto(rs.getString("nombre_completo"));
                
                r.setUsuario(u);
                
                lista_resultados.add(r);
            }
            
            consulta_grupo = getConexion().prepareStatement(
                    " SELECT m.identificador, tm.nombre "
                  + " FROM control_calidad.analisis_grupo_solicitud ags "
                  + "   INNER JOIN control_calidad.grupos g ON ags.id_grupo = g.id_grupo "
                  + "   INNER JOIN control_calidad.grupos_muestras gm ON g.id_grupo = gm.id_grupo "
                  + "   INNER JOIN control_calidad.muestras m ON gm.id_muestra = m.id_muestra "
                  + "   INNER JOIN control_calidad.tipos_muestras tm ON m.id_tipo_muestra = tm.id_tipo_muestra "
                  + " WHERE ags.id_analisis_grupo_solicitud = ?; "
            );
            
            consulta_grupo.setInt(1, id_ags);
            
            rs_grupo = consulta_grupo.executeQuery();
            
            ags_resultado.setResultados(lista_resultados);
            Grupo g = new Grupo();
            List<Muestra> muestras = new ArrayList<Muestra>();
            
            while(rs_grupo.next()) {
                TipoMuestra tm = new TipoMuestra();
                tm.setNombre(rs_grupo.getString("nombre"));
                Muestra m = new Muestra();
                m.setIdentificador(rs_grupo.getString("identificador"));
                m.setTipo_muestra(tm);
                muestras.add(m);
            }
            
            g.setGrupos_muestras(muestras);
            ags_resultado.setGrupo(g);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener el listado de resultados. Inténtelo nuevamente.");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarSilencioso(rs_grupo);
            cerrarSilencioso(consulta_grupo);
            cerrarConexion();
        }
        
        return ags_resultado;
    }
    
    public Resultado editarResultado(Resultado resultado) throws SIGIPROException {
        boolean commit = false;
        PreparedStatement update_resultado = null;
        PreparedStatement delete_reactivos = null;
        PreparedStatement delete_equipos = null;
        PreparedStatement insert_reactivos = null;
        PreparedStatement insert_equipos = null;
        
        PreparedStatement consulta_solicitud = null;
        ResultSet rs_solicitud = null;
        
        try {
            
            getConexion().setAutoCommit(false);
            
            update_resultado = getConexion().prepareStatement(
                    " UPDATE control_calidad.resultados SET PATH = ?, datos = ?, id_usuario = ?, fecha = ?, id_analisis_grupo_solicitud = ?, resultado = ? "
                    + " WHERE id_resultado = ?; "
            );
            
            SQLXML datos = getConexion().createSQLXML();
            datos.setString(resultado.getDatos_string());
            
            update_resultado.setString(1, resultado.getPath());
            update_resultado.setSQLXML(2, datos);
            update_resultado.setInt(3, resultado.getUsuario().getId_usuario());
            update_resultado.setDate(4, helper_fechas.getFecha_hoy());
            update_resultado.setInt(5, resultado.getAgs().getId_analisis_grupo_solicitud());
            update_resultado.setString(6, resultado.getResultado());
            update_resultado.setInt(7, resultado.getId_resultado());
            
            update_resultado.executeUpdate();
            
            delete_reactivos = getConexion().prepareStatement(
                    " DELETE FROM control_calidad.reactivos_resultado where id_resultado = ?;"
            );
            delete_reactivos.setInt(1, resultado.getId_resultado());
            delete_reactivos.execute();
            
            if (resultado.tieneReactivos()) {
                insert_reactivos = getConexion().prepareStatement(
                        " INSERT INTO control_calidad.reactivos_resultado (id_resultado, id_reactivo) VALUES (?,?);"
                );
                
                for (Reactivo r : resultado.getReactivos_resultado()) {
                    insert_reactivos.setInt(1, resultado.getId_resultado());
                    insert_reactivos.setInt(2, r.getId_reactivo());
                    insert_reactivos.addBatch();
                }
                
                insert_reactivos.executeBatch();
            }
            
            delete_equipos = getConexion().prepareStatement(
                    " DELETE FROM control_calidad.equipos_resultado where id_resultado = ?;"
            );
            delete_equipos.setInt(1, resultado.getId_resultado());
            delete_equipos.execute();
            
            if (resultado.tieneEquipos()) {
                insert_equipos = getConexion().prepareStatement(
                        " INSERT INTO control_calidad.equipos_resultado (id_resultado, id_equipo) VALUES (?,?); "
                );
                
                for (Equipo e : resultado.getEquipos_resultado()) {
                    insert_equipos.setInt(1, resultado.getId_resultado());
                    insert_equipos.setInt(2, e.getId_equipo());
                    insert_equipos.addBatch();
                }
                
                insert_equipos.executeBatch();
            }
            
            consulta_solicitud = getConexion().prepareStatement(
                    " SELECT g.id_solicitud from control_calidad.analisis_grupo_solicitud ags "
                    + " INNER JOIN control_calidad.grupos g ON ags.id_grupo = g.id_grupo "
                    + " WHERE ags.id_analisis_grupo_solicitud = ?; "
            );
            
            consulta_solicitud.setInt(1, resultado.getAgs().getId_analisis_grupo_solicitud());
            
            rs_solicitud = consulta_solicitud.executeQuery();
            
            if (rs_solicitud.next()) {
                Grupo g = new Grupo();
                SolicitudCC s = new SolicitudCC();
                s.setId_solicitud(rs_solicitud.getInt("id_solicitud"));
                g.setSolicitud(s);
                resultado.getAgs().setGrupo(g);
                resultado.getAgs().getGrupo().setSolicitud(s);
            }
            
            commit = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al ingresar el resultado. Inténtelo nuevamente.");
        } finally {
            try {
                if (commit) {
                    getConexion().commit();
                } else {
                    getConexion().rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new SIGIPROException("Error de comunicación con la base de datos. Inténtelo nuevamente o notifique al administrador del sistema.");
            }
            cerrarSilencioso(update_resultado);
            cerrarSilencioso(insert_reactivos);
            cerrarSilencioso(insert_equipos);
            cerrarSilencioso(rs_solicitud);
            cerrarSilencioso(consulta_solicitud);
            cerrarConexion();
        }
        
        return resultado;
    }
    
}
