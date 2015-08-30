/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.controlcalidad.modelos.AnalisisGrupoSolicitud;
import com.icp.sigipro.controlcalidad.modelos.ControlSolicitud;
import com.icp.sigipro.controlcalidad.modelos.Grupo;
import com.icp.sigipro.controlcalidad.modelos.Informe;
import com.icp.sigipro.controlcalidad.modelos.Muestra;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.controlcalidad.modelos.TipoMuestra;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class SolicitudDAO extends DAO {

    public boolean entregarSolicitud(SolicitudCC solicitud) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.solicitudes (numero_solicitud,id_usuario_solicitante,fecha_solicitud,estado) "
                    + " VALUES (?,?,?,?) RETURNING id_solicitud");
            consulta.setString(1, solicitud.getNumero_solicitud());
            consulta.setInt(2, solicitud.getUsuario_solicitante().getId_usuario());
            consulta.setDate(3, solicitud.getFecha_solicitud());
            consulta.setString(4, solicitud.getEstado());
            rs = consulta.executeQuery();
            if (rs.next()) {
                solicitud.setId_solicitud(rs.getInt("id_solicitud"));
            }
            
            List<PreparedStatement> consultas_asociacion = solicitud.obtenerConsultasInsertarAsociacion(getConexion());
            
            for(PreparedStatement ps : consultas_asociacion) {
                ps.executeBatch();
                cerrarSilencioso(ps);
            }
            
            resultado = true;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;
    }

    public int obtenerProximoId() {
        int nextval = 0;
        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT MAX(ID_SOLICITUD) AS LAST_VALUE FROM control_calidad.solicitudes;");
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                int currval = resultadoConsulta.getInt("last_value");
                nextval = currval + 1;
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nextval;
    }

    public boolean insertarGrupo(Grupo g) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.grupos (id_solicitud) "
                    + "VALUES (?) RETURNING id_grupo; ");
            consulta.setInt(1, g.getSolicitud().getId_solicitud());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                g.setId_grupo(rs.getInt("id_grupo"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean insertarMuestra(Muestra m) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.muestras (identificador, id_tipo_muestra, fecha_descarte_estimada) "
                    + "VALUES (?,?,?) RETURNING id_muestra; ");
            consulta.setString(1, m.getIdentificador());
            consulta.setInt(2, m.getTipo_muestra().getId_tipo_muestra());
            consulta.setDate(3, m.getFecha_descarte_estimada());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                m.setId_muestra(rs.getInt("id_muestra"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean insertarMuestrasGrupo(Grupo g) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            this.insertarGrupo(g);

            PreparedStatement consultaBatch = getConexion().prepareStatement(" INSERT INTO control_calidad.grupos_muestras (id_grupo, id_muestra) "
                    + "VALUES (?,?)");

            for (Muestra muestra : g.getGrupos_muestras()) {
                this.insertarMuestra(muestra);
                consultaBatch.setInt(1, g.getId_grupo());
                consultaBatch.setInt(2, muestra.getId_muestra());
                consultaBatch.addBatch();
            }
            consultaBatch.executeBatch();
            resultado = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean insertarAnalisisGrupoSolicitud(AnalisisGrupoSolicitud ags) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.analisis_grupo_solicitud (id_grupo, id_analisis) "
                    + "VALUES (?,?) RETURNING id_analisis_grupo_solicitud");
            consulta.setInt(1, ags.getGrupo().getId_grupo());
            consulta.setInt(2, ags.getAnalisis().getId_analisis());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                ags.setId_analisis_grupo_solicitud(rs.getInt("id_analisis_grupo_solicitud"));
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

    //PENDIENTE
    public boolean editarSolicitud(SolicitudCC solicitud) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            
            List<PreparedStatement> consultas_asociacion = solicitud.obtenerConsultasInsertarAsociacion(getConexion());
            
            for(PreparedStatement ps : consultas_asociacion) {
                ps.executeBatch();
                cerrarSilencioso(ps);
            }
            
            resultado = true;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean recibirSolicitud(SolicitudCC solicitud) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE control_calidad.solicitudes "
                    + "SET id_usuario_recibido=?, fecha_recibido=?, estado='Recibido' "
                    + "WHERE id_solicitud = ?; ");
            consulta.setInt(1, solicitud.getUsuario_recibido().getId_usuario());
            consulta.setDate(2, solicitud.getFecha_recibido());
            consulta.setInt(3, solicitud.getId_solicitud());
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

    public List<SolicitudCC> obtenerTodasSolicitudes() {
        List<SolicitudCC> resultado = new ArrayList<SolicitudCC>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT s.id_solicitud, s.numero_solicitud, s.fecha_solicitud, s.id_usuario_solicitante, u.nombre_completo, s.estado "
                    + "FROM control_calidad.solicitudes as s INNER JOIN seguridad.usuarios as u ON u.id_usuario = s.id_usuario_solicitante "
                    + "WHERE s.estado ='Solicitado' or s.estado='Recibido'; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                SolicitudCC solicitud = new SolicitudCC();
                solicitud.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud.setNumero_solicitud(rs.getString("numero_solicitud"));
                solicitud.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario_solicitante"));
                usuario.setNombre_completo(rs.getString("nombre_completo"));
                solicitud.setUsuario_solicitante(usuario);
                solicitud.setEstado(rs.getString("estado"));
                resultado.add(solicitud);
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
    
    public List<SolicitudCC> obtenerSeccionSolicitudes(int id_usuario) {
        List<SolicitudCC> resultado = new ArrayList<SolicitudCC>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement("SELECT s.id_solicitud, s.numero_solicitud, s.fecha_solicitud, s.id_usuario_solicitante, u.nombre_completo, u.id_seccion, s.estado, u2.nombre_completo, u2.id_seccion "
                    + "FROM control_calidad.solicitudes as s "
                    + "INNER JOIN seguridad.usuarios as u ON u.id_usuario = s.id_usuario_solicitante "
                    + "INNER JOIN seguridad.usuarios as u2 ON u2.id_usuario = ? "
                    + "WHERE s.estado ='Solicitado' or s.estado='Recibido' "
                    + "AND u.id_seccion=u2.id_seccion");
            consulta.setInt(1, id_usuario);
            rs = consulta.executeQuery();
            while (rs.next()) {
                SolicitudCC solicitud = new SolicitudCC();
                solicitud.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud.setNumero_solicitud(rs.getString("numero_solicitud"));
                solicitud.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario_solicitante"));
                usuario.setNombre_completo(rs.getString("nombre_completo"));
                solicitud.setUsuario_solicitante(usuario);
                solicitud.setEstado(rs.getString("estado"));
                resultado.add(solicitud);
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
    
    public List<SolicitudCC> obtenerSeccionSolicitudesHistorial(int id_usuario) {
        List<SolicitudCC> resultado = new ArrayList<SolicitudCC>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement("SELECT s.id_solicitud, s.numero_solicitud, s.fecha_solicitud, s.id_usuario_solicitante, u.nombre_completo, u.id_seccion, s.estado, u2.nombre_completo, u2.id_seccion "
                    + "FROM control_calidad.solicitudes as s "
                    + "INNER JOIN seguridad.usuarios as u ON u.id_usuario = s.id_usuario_solicitante "
                    + "INNER JOIN seguridad.usuarios as u2 ON u2.id_usuario = ? "
                    + "WHERE s.estado ='Anulada' or s.estado='Completada' "
                    + "AND u.id_seccion=u2.id_seccion");
            consulta.setInt(1, id_usuario);
            rs = consulta.executeQuery();
            while (rs.next()) {
                SolicitudCC solicitud = new SolicitudCC();
                solicitud.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud.setNumero_solicitud(rs.getString("numero_solicitud"));
                solicitud.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario_solicitante"));
                usuario.setNombre_completo(rs.getString("nombre_completo"));
                solicitud.setUsuario_solicitante(usuario);
                solicitud.setEstado(rs.getString("estado"));
                resultado.add(solicitud);
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
    
    public List<SolicitudCC> obtenerSolicitudesHistorial() {
        List<SolicitudCC> resultado = new ArrayList<SolicitudCC>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT s.id_solicitud, s.numero_solicitud, s.fecha_solicitud, s.id_usuario_solicitante, u.nombre_completo, s.estado "
                    + "FROM control_calidad.solicitudes as s INNER JOIN seguridad.usuarios as u ON u.id_usuario = s.id_usuario_solicitante "
                    + "WHERE s.estado ='Anulada' or s.estado='Completada'; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                SolicitudCC solicitud = new SolicitudCC();
                solicitud.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud.setNumero_solicitud(rs.getString("numero_solicitud"));
                solicitud.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario_solicitante"));
                usuario.setNombre_completo(rs.getString("nombre_completo"));
                solicitud.setUsuario_solicitante(usuario);
                solicitud.setEstado(rs.getString("estado"));
                resultado.add(solicitud);
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

    public List<Muestra> obtenerMuestras() {
        List<Muestra> resultado = new ArrayList<Muestra>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT m.id_muestra, m.identificador, m.fecha_descarte_estimada, tm.id_tipo_muestra,tm.nombre "
                    + "FROM control_calidad.muestras AS m LEFT OUTER JOIN control_calidad.tipos_muestras AS tm ON tm.id_tipo_muestra = m.id_tipo_muestra "
                    + "ORDER BY fecha_descarte_estimada ; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                TipoMuestra tm = new TipoMuestra();
                tm.setId_tipo_muestra(rs.getInt("id_tipo_muestra"));
                tm.setNombre(rs.getString("nombre"));
                Muestra m = new Muestra();
                m.setFecha_descarte_estimada(rs.getDate("fecha_descarte_estimada"));
                m.setId_muestra(rs.getInt("id_muestra"));
                m.setIdentificador(rs.getString("identificador"));
                m.setTipo_muestra(tm);
                resultado.add(m);
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

    public SolicitudCC obtenerSolicitud(int id_solicitud) {
        SolicitudCC resultado = new SolicitudCC();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {

            consulta = getConexion().prepareStatement(
                    " SELECT solicitud.id_solicitud, "
                    + "        solicitud.numero_solicitud, "
                    + "        solicitud.id_usuario_solicitante, "
                    + "        solicitud.id_usuario_recibido, "
                    + "        solicitud.fecha_solicitud, "
                    + "        solicitud.fecha_recibido, "
                    + "        solicitud.estado, "
                    + "        solicitud.observaciones, "
                    + "        solicitud.tipo_referencia, "
                    + "        solicitud.tabla_referencia, "
                    + "        solicitud.id_referenciado, "
                    + "        solicitud.informacion_referencia_adicional, "
                    + "        i.id_informe, "
                    + "        i.realizado_por,"
                    + "        i.fecha as fecha_informe, "
                    + "        u.nombre_completo as nombre_usuario_informe "
                    + " FROM control_calidad.solicitudes as solicitud "
                    + "     LEFT JOIN control_calidad.informes i ON i.id_solicitud = solicitud.id_solicitud "
                    + "     LEFT JOIN seguridad.usuarios u ON u.id_usuario = i.realizado_por "
                    + " WHERE solicitud.id_solicitud=?; "
            );

            consulta.setInt(1, id_solicitud);
            rs = consulta.executeQuery();
            UsuarioDAO usuariodao = new UsuarioDAO();

            if (rs.next()) {
                resultado.setId_solicitud(rs.getInt("id_solicitud"));
                resultado.setEstado(rs.getString("estado"));
                Usuario usuario_solicitante = usuariodao.obtenerUsuario(rs.getInt("id_usuario_solicitante"));
                Usuario usuario_recibido = usuariodao.obtenerUsuario(rs.getInt("id_usuario_recibido"));
                resultado.setUsuario_recibido(usuario_recibido);
                resultado.setUsuario_solicitante(usuario_solicitante);
                resultado.setFecha_recibido(rs.getDate("fecha_recibido"));
                resultado.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                resultado.setNumero_solicitud(rs.getString("numero_solicitud"));
                resultado.setObservaciones(rs.getString("observaciones"));
                int id_informe = rs.getInt("id_informe");
                if (id_informe != 0) {
                    Informe i = new Informe();
                    i.setId_informe(id_informe);
                    i.setFecha(rs.getDate("fecha_informe"));
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("realizado_por"));
                    u.setNombreCompleto(rs.getString("nombre_usuario_informe"));
                    i.setUsuario(u);
                    resultado.setInforme(i);
                }
                String tipo = rs.getString("tipo_referencia");
                if (tipo != null) {
                    resultado.setTipoAsociacion(tipo);
                    resultado.asociar(rs);
                }
            }

            consulta = getConexion().prepareStatement(
                    " SELECT ags.id_analisis_grupo_solicitud, a.id_analisis, a.nombre as nombreanalisis, g.id_grupo, m.id_muestra, m.identificador, tm.nombre as nombretipo, tm.id_tipo_muestra "
                    + " FROM control_calidad.analisis_grupo_solicitud as ags "
                    + "   LEFT OUTER JOIN control_calidad.grupos as g ON g.id_grupo = ags.id_grupo "
                    + "   LEFT OUTER JOIN control_calidad.grupos_muestras as gm ON gm.id_grupo = g.id_grupo "
                    + "   LEFT OUTER JOIN control_calidad.muestras as m ON m.id_muestra = gm.id_muestra "
                    + "   LEFT OUTER JOIN control_calidad.tipos_muestras as tm ON tm.id_tipo_muestra = m.id_tipo_muestra "
                    + "   LEFT OUTER JOIN control_calidad.analisis as a ON a.id_analisis = ags.id_analisis "
                    + " WHERE g.id_solicitud = ?"
                    + " ORDER BY ags.id_analisis_grupo_solicitud;");

            consulta.setInt(1, id_solicitud);

            rs = consulta.executeQuery();

            List<AnalisisGrupoSolicitud> lista_grupos_analisis_solicitud = new ArrayList<AnalisisGrupoSolicitud>();
            ControlSolicitud cs = new ControlSolicitud();

            AnalisisGrupoSolicitud ags = new AnalisisGrupoSolicitud();
            int id_ags;

            while (rs.next()) {
                //Pregunta si el ags es nuevo o si es el mismo del while pasado
                id_ags = rs.getInt("id_analisis_grupo_solicitud");

                if (id_ags != ags.getId_analisis_grupo_solicitud()) {
                    ags = new AnalisisGrupoSolicitud();
                    ags.setId_analisis_grupo_solicitud(id_ags);

                    Analisis a = new Analisis();
                    a.setId_analisis(rs.getInt("id_analisis"));
                    a.setNombre(rs.getString("nombreanalisis"));

                    Grupo g = new Grupo();
                    g.setId_grupo(rs.getInt("id_grupo"));
                    g.setSolicitud(resultado);

                    ags.setGrupo(g);
                    ags.setAnalisis(a);

                    lista_grupos_analisis_solicitud.add(ags);
                }

                Muestra m = new Muestra();
                m.setId_muestra(rs.getInt("id_muestra"));
                m.setIdentificador(rs.getString("identificador"));

                TipoMuestra tm = new TipoMuestra();
                tm.setNombre(rs.getString("nombretipo"));
                tm.setId_tipo_muestra(rs.getInt("id_tipo_muestra"));

                m.setTipo_muestra(tm);

                ags.getGrupo().agregarMuestra(m);
                ags.setId_analisis_grupo_solicitud(id_ags);

                cs.agregarCombinacion(ags.getAnalisis(), tm, m);
            }

            resultado.setAnalisis_solicitud(lista_grupos_analisis_solicitud);
            resultado.setControl_solicitud(cs);

            if (resultado.getInforme() != null) {
                String ids = this.pasarIdsAGSAParentesis(lista_grupos_analisis_solicitud);
                consulta = getConexion().prepareStatement(
                          " SELECT ags.id_analisis_grupo_solicitud, r.id_resultado, r.resultado, r.repeticion, "
                        + " CASE WHEN r.id_resultado IN (  "
                        + " 				SELECT r.id_resultado "
                        + " 				FROM control_calidad.resultados_informes ri  "
                        + " 				    INNER JOIN control_calidad.resultados r ON r.id_resultado = ri.id_resultado  "
                        + " 				WHERE ri.id_informe = ?  "
                        + " 				)  "
                        + "      THEN true "
                        + "      ELSE false "
                        + " END AS en_informe "
                        + " FROM control_calidad.analisis_grupo_solicitud ags "
                        + "     LEFT JOIN control_calidad.resultados r ON r.id_analisis_grupo_solicitud = ags.id_analisis_grupo_solicitud"
                        + " WHERE ags.id_analisis_grupo_solicitud IN " + ids
                        + " ORDER BY ags.id_analisis_grupo_solicitud; "
                );
                
                consulta.setInt(1, resultado.getInforme().getId_informe());
            } else {
                consulta = getConexion().prepareStatement(
                          " SELECT ags.id_analisis_grupo_solicitud, r.id_resultado, r.resultado, r.repeticion "
                        + " FROM control_calidad.analisis_grupo_solicitud ags "
                        + "     LEFT JOIN control_calidad.resultados r ON r.id_analisis_grupo_solicitud = ags.id_analisis_grupo_solicitud"
                        + " WHERE ags.id_analisis_grupo_solicitud IN " + this.pasarIdsAGSAParentesis(lista_grupos_analisis_solicitud)
                        + " ORDER BY ags.id_analisis_grupo_solicitud; "
                );
            }

            rs = consulta.executeQuery();

            while (rs.next()) {
                int id_resultado = rs.getInt("id_resultado");
                if (id_resultado != 0) {
                    Resultado r = new Resultado();
                    r.setId_resultado(rs.getInt("id_resultado"));
                    r.setResultado(rs.getString("resultado"));
                    r.setRepeticion(rs.getInt("repeticion"));
                    AnalisisGrupoSolicitud ags_iter = new AnalisisGrupoSolicitud();
                    ags_iter.setId_analisis_grupo_solicitud(rs.getInt("id_analisis_grupo_solicitud"));
                    r.setAgs(ags_iter);
                    resultado.agregarResultadoAnalisisGrupoSolicitud(r);
                    if (resultado.getInforme() != null) {
                        if (rs.getBoolean("en_informe")) {
                            resultado.getInforme().agregarResultado(r);
                        }
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
    
    public SolicitudCC obtenerAsociacion(SolicitudCC solicitud) throws SQLException {
        
        PreparedStatement consulta = null;
        ResultSet rs = null;
        
        try {
            
            consulta = getConexion().prepareStatement(
                      " SELECT s.tipo_referencia, s.tabla_referencia, s.id_referenciado, s.informacion_referencia_adicional "
                    + " FROM control_calidad.solicitudes s "
                    + " WHERE id_solicitud = ? "
            );
            
            consulta.setInt(1, solicitud.getId_solicitud());
            
            rs = consulta.executeQuery();
            
            if (rs.next()) {
                String tipo = rs.getString("tipo_referencia");
                if (tipo != null) {
                    solicitud.setTipoAsociacion(tipo);
                    solicitud.asociar(rs);
                }
            }
            
        } catch (SQLException sql_ex) {
            
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
        }
        
        return solicitud;
    }

    public List<List<String>> obtenerSolicitudEditar(int id_solicitud) {
        List<List<String>> resultado = new ArrayList<List<String>>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement("SELECT tm.id_tipo_muestra, string_agg(CAST (m.identificador as text), ', ') AS identificador, string_agg(DISTINCT CAST ( m.fecha_descarte_estimada as text), ', ') as fecha_descarte, string_agg(DISTINCT CAST ( a.id_analisis as text), ', ') as id_analisis, string_agg(CAST (g.id_grupo as text), ', ') AS id_grupo "
                    + "FROM control_calidad.analisis_grupo_solicitud as ags "
                    + "LEFT OUTER JOIN control_calidad.grupos as g ON g.id_grupo = ags.id_grupo "
                    + "LEFT OUTER JOIN control_calidad.grupos_muestras as gm ON gm.id_grupo = g.id_grupo "
                    + "LEFT OUTER JOIN control_calidad.muestras as m ON m.id_muestra = gm.id_muestra "
                    + "LEFT OUTER JOIN control_calidad.tipos_muestras as tm ON tm.id_tipo_muestra = m.id_tipo_muestra "
                    + "LEFT OUTER JOIN control_calidad.analisis as a ON a.id_analisis = ags.id_analisis "
                    + "WHERE g.id_solicitud = ? "
                    + "GROUP BY tm.id_tipo_muestra; ");
            consulta.setInt(1, id_solicitud);
            rs = consulta.executeQuery();
            int contador = 1;
            while (rs.next()) {
                List<String> filaSolicitud = new ArrayList<String>();
                filaSolicitud.add(contador + "");
                filaSolicitud.add(rs.getString("id_tipo_muestra"));
                filaSolicitud.add(rs.getString("identificador"));
                Muestra m = new Muestra();
                m.setFecha_descarte_estimada(rs.getDate("fecha_descarte"));
                if (m.getFecha_descarte_estimada() != null) {
                    filaSolicitud.add(m.getFecha_descarte_estimadaAsString());
                } else {
                    filaSolicitud.add("");
                }
                filaSolicitud.add(rs.getString("id_analisis"));
                filaSolicitud.add(rs.getString("id_grupo"));

                resultado.add(filaSolicitud);
                contador++;
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

    public boolean anularSolicitud(int id_solicitud, String observaciones) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE control_calidad.solicitudes "
                    + "SET estado='Anulada', observaciones=? "
                    + "WHERE id_solicitud = ?; ");
            consulta.setString(1, observaciones);
            consulta.setInt(2, id_solicitud);
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

    public boolean eliminarGrupo(String[] grupos) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.muestras "
                    + "WHERE id_muestra in (SELECT m.id_muestra FROM control_calidad.muestras as m INNER JOIN control_calidad.grupos_muestras as gm ON gm.id_muestra = m.id_muestra WHERE gm.id_grupo = ?); ");
            for (String i : grupos) {
                consulta.setInt(1, Integer.parseInt(i));
                consulta.addBatch();
            }
            consulta.executeBatch();

            consulta = getConexion().prepareStatement(" DELETE FROM control_calidad.grupos "
                    + "WHERE id_grupo = ?; ");
            for (String i : grupos) {
                consulta.setInt(1, Integer.parseInt(i));
                consulta.addBatch();
            }
            int[] result = consulta.executeBatch();
            if (result.length > 0) {
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

    public boolean insertarGrupoConAnalisis(Grupo grupo) throws SIGIPROException {

        boolean resultado = false;

        PreparedStatement consulta_grupo = null;
        ResultSet rs_grupo = null;

        PreparedStatement consulta_muestras_grupo = null;
        PreparedStatement consulta_ags = null;

        try {
            getConexion().setAutoCommit(false);

            consulta_grupo = getConexion().prepareStatement(
                    " INSERT INTO control_calidad.grupos (id_solicitud) VALUES (?) RETURNING id_grupo"
            );

            consulta_grupo.setInt(1, grupo.getSolicitud().getId_solicitud());

            System.out.println(consulta_grupo);
            rs_grupo = consulta_grupo.executeQuery();

            if (rs_grupo.next()) {
                grupo.setId_grupo(rs_grupo.getInt("id_grupo"));
            }

            consulta_muestras_grupo = getConexion().prepareStatement(
                    " INSERT INTO control_calidad.grupos_muestras (id_muestra, id_grupo) VALUES (?,?);"
            );

            for (Muestra m : grupo.getGrupos_muestras()) {
                consulta_muestras_grupo.setInt(1, m.getId_muestra());
                consulta_muestras_grupo.setInt(2, grupo.getId_grupo());
                System.out.println(consulta_muestras_grupo);
                consulta_muestras_grupo.addBatch();
            }

            consulta_muestras_grupo.executeBatch();

            consulta_ags = getConexion().prepareStatement(
                    " INSERT INTO control_calidad.analisis_grupo_solicitud (id_analisis, id_grupo) VALUES (?, ?); "
            );

            for (Analisis a : grupo.getAnalisis()) {
                consulta_ags.setInt(1, a.getId_analisis());
                consulta_ags.setInt(2, grupo.getId_grupo());
                consulta_ags.addBatch();
            }

            consulta_ags.executeBatch();

            resultado = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al ingresar el grupo. Inténtelo nuevamente.");
        } finally {
            try {
                if (resultado) {
                    getConexion().commit();
                } else {
                    getConexion().rollback();
                }
            } catch (SQLException sql_ex) {
                sql_ex.printStackTrace();
                throw new SIGIPROException("Error de comunicación con la base de datos. Notifique al administrador del sistema.");
            }
            cerrarSilencioso(rs_grupo);
            cerrarSilencioso(consulta_grupo);
            cerrarSilencioso(consulta_muestras_grupo);
            cerrarSilencioso(consulta_ags);
            cerrarConexion();
        }

        return resultado;
    }

    private String pasarIdsAGSAParentesis(List<AnalisisGrupoSolicitud> lista_ags) {

        String resultado = "(";

        for (AnalisisGrupoSolicitud ags : lista_ags) {
            resultado = resultado + String.valueOf(ags.getId_analisis_grupo_solicitud()) + ",";
        }

        return resultado.substring(0, resultado.length() - 1) + ")";
    }
}
