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
import com.icp.sigipro.controlcalidad.modelos.Patron;
import com.icp.sigipro.controlcalidad.modelos.Reactivo;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.controlcalidad.modelos.ResultadoSangriaPrueba;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.controlcalidad.modelos.TipoEquipo;
import com.icp.sigipro.controlcalidad.modelos.TipoReactivo;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class ResultadoSangriaPruebaDAO extends DAO {

    public ResultadoSangriaPrueba insertarResultadoSangriaPrueba(ResultadoSangriaPrueba resultado_sp) throws SIGIPROException {

        PreparedStatement consulta = null;
        PreparedStatement consulta_solicitud = null;
        ResultSet rs_solicitud = null;
        ResultSet rs = null;
        
        PreparedStatement insert_reactivos = null;
        PreparedStatement insert_equipos = null;
        PreparedStatement insert_patrones_controles = null;

        try {
            consulta = getConexion().prepareStatement(
                    " INSERT INTO control_calidad.resultados_analisis_sangrias_prueba "
                    + " (id_ags,wbc,rbc,hematocrito,hemoglobina,fecha,id_usuario,observaciones,mcv,mch,mchc,plt,lym,otros,linfocitos,num_otros) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id_resultado_analisis_sp;"
            );

            consulta.setInt(1, resultado_sp.getAgs().getId_analisis_grupo_solicitud());
            consulta.setFloat(2, resultado_sp.getWbc());
            consulta.setFloat(3, resultado_sp.getRbc());
            consulta.setFloat(4, resultado_sp.getHematocrito());
            consulta.setFloat(5, resultado_sp.getHemoglobina());
            consulta.setDate(6, resultado_sp.getFecha());
            consulta.setInt(7, resultado_sp.getUsuario().getId_usuario());
            consulta.setString(8, resultado_sp.getObservaciones());
            consulta.setFloat(9, resultado_sp.getMcv());
            consulta.setFloat(10, resultado_sp.getMch());
            consulta.setFloat(11, resultado_sp.getMchc());
            consulta.setFloat(12, resultado_sp.getPlt());
            consulta.setFloat(13, resultado_sp.getLym());
            consulta.setFloat(14, resultado_sp.getOtros());
            consulta.setFloat(15, resultado_sp.getLinfocitos());
            consulta.setFloat(16, resultado_sp.getNum_otros());

            rs = consulta.executeQuery();

            if (rs.next()) {
                resultado_sp.setId_resultado_sangria_prueba(rs.getInt("id_resultado_analisis_sp"));
            } else {
                throw new SIGIPROException("Error de comunicación con la base de datos. Notifique al administrador del sistema.");
            }

            consulta_solicitud = getConexion().prepareStatement(
                    " SELECT g.id_solicitud from control_calidad.analisis_grupo_solicitud ags "
                    + " INNER JOIN control_calidad.grupos g ON ags.id_grupo = g.id_grupo "
                    + " WHERE ags.id_analisis_grupo_solicitud = ?; "
            );

            consulta_solicitud.setInt(1, resultado_sp.getAgs().getId_analisis_grupo_solicitud());

            rs_solicitud = consulta_solicitud.executeQuery();

            if (rs_solicitud.next()) {
                Grupo g = new Grupo();
                SolicitudCC s = new SolicitudCC();
                s.setId_solicitud(rs_solicitud.getInt("id_solicitud"));
                g.setSolicitud(s);
                resultado_sp.getAgs().setGrupo(g);
                resultado_sp.getAgs().getGrupo().setSolicitud(s);
            }

            if (resultado_sp.tieneReactivos()) {
                insert_reactivos = getConexion().prepareStatement(
                        " INSERT INTO control_calidad.reactivos_resultado_sp (id_resultado_sp, id_reactivo) VALUES (?,?);"
                );

                for (Reactivo r : resultado_sp.getReactivos_resultado()) {
                    insert_reactivos.setInt(1, resultado_sp.getId_resultado());
                    insert_reactivos.setInt(2, r.getId_reactivo());
                    insert_reactivos.addBatch();
                }

                insert_reactivos.executeBatch();
            }

            if (resultado_sp.tieneEquipos()) {
                insert_equipos = getConexion().prepareStatement(
                        " INSERT INTO control_calidad.equipos_resultado_sp (id_resultado_sp, id_equipo) VALUES (?,?); "
                );

                for (Equipo e : resultado_sp.getEquipos_resultado()) {
                    insert_equipos.setInt(1, resultado_sp.getId_resultado());
                    insert_equipos.setInt(2, e.getId_equipo());
                    insert_equipos.addBatch();
                }

                insert_equipos.executeBatch();
            }
            
            if (resultado_sp.tienePatronesOControles()) {

                insert_patrones_controles = getConexion().prepareStatement(
                        " INSERT INTO control_calidad.patrones_resultados_sp (id_resultado_sp, id_patron) VALUES (?,?); "
                );

                for (Patron p : resultado_sp.getPatrones_resultado()) {
                    insert_patrones_controles.setInt(1, resultado_sp.getId_resultado());
                    insert_patrones_controles.setInt(2, p.getId_patron());
                    insert_patrones_controles.addBatch();
                }

                for (Patron p : resultado_sp.getControles_resultado()) {
                    insert_patrones_controles.setInt(1, resultado_sp.getId_resultado());
                    insert_patrones_controles.setInt(2, p.getId_patron());
                    insert_patrones_controles.addBatch();
                }

                insert_patrones_controles.executeBatch();

            }

            consulta_solicitud.setInt(1, resultado_sp.getAgs().getId_analisis_grupo_solicitud());

            rs_solicitud = consulta_solicitud.executeQuery();

            if (rs_solicitud.next()) {
                Grupo g = new Grupo();
                SolicitudCC s = new SolicitudCC();
                s.setId_solicitud(rs_solicitud.getInt("id_solicitud"));
                g.setSolicitud(s);
                resultado_sp.getAgs().setGrupo(g);
                resultado_sp.getAgs().getGrupo().setSolicitud(s);
            }

        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs_solicitud);
            cerrarSilencioso(consulta_solicitud);
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado_sp;

    }

    public Resultado obtenerResultadoSangriaPrueba(int id_resultado) throws SIGIPROException {
        ResultadoSangriaPrueba resultado = null;

        PreparedStatement consulta = null;
        ResultSet rs = null;
        PreparedStatement consulta_reactivos = null;
        ResultSet rs_reactivos = null;
        PreparedStatement consulta_equipos = null;
        ResultSet rs_equipos = null;
        PreparedStatement consulta_patrones_controles = null;
        ResultSet rs_patrones_controles = null;

        try {
            consulta = getConexion().prepareStatement(
                    " SELECT r.*, ags.id_analisis, a.nombre, s.id_solicitud, s.numero_solicitud "
                    + " FROM control_calidad.resultados_analisis_sangrias_prueba r "
                    + "     INNER JOIN control_calidad.analisis_grupo_solicitud ags ON ags.id_analisis_grupo_solicitud = r.id_ags "
                    + "     INNER JOIN control_calidad.analisis as a ON a.id_analisis = ags.id_analisis "
                    + "     LEFT JOIN control_calidad.grupos as g ON g.id_grupo = ags.id_grupo "
                    + "     LEFT JOIN control_calidad.solicitudes as s ON s.id_solicitud = g.id_solicitud "
                    + " WHERE r.id_resultado_analisis_sp = ? "
            );

            consulta.setInt(1, id_resultado);

            rs = consulta.executeQuery();

            if (rs.next()) {
                resultado = new ResultadoSangriaPrueba();

                resultado.setId_resultado(rs.getInt("id_resultado_analisis_sp"));
                resultado.setHematocrito(rs.getFloat("hematocrito"));
                resultado.setHemoglobina(rs.getFloat("hemoglobina"));
                resultado.setRbc(rs.getFloat("rbc"));
                resultado.setWbc(rs.getFloat("wbc"));
                resultado.setMcv(rs.getFloat("mcv"));
                resultado.setMch(rs.getFloat("mch"));
                resultado.setMchc(rs.getFloat("mchc"));
                resultado.setPlt(rs.getFloat("plt"));
                resultado.setLym(rs.getFloat("lym"));
                resultado.setOtros(rs.getFloat("otros"));
                resultado.setLinfocitos(rs.getFloat("linfocitos"));
                resultado.setNum_otros(rs.getFloat("num_otros"));
                resultado.setRepeticion(rs.getInt("repeticion"));
                resultado.setObservaciones(rs.getString("observaciones"));

                AnalisisGrupoSolicitud ags = new AnalisisGrupoSolicitud();
                ags.setId_analisis_grupo_solicitud(rs.getInt("id_ags"));
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
                    + " FROM control_calidad.reactivos_resultado_sp rr "
                    + "      INNER JOIN control_calidad.reactivos r ON rr.id_reactivo = r.id_reactivo "
                    + "      INNER JOIN control_calidad.tipos_reactivos tr ON tr.id_tipo_reactivo = r.id_tipo_reactivo "
                    + "      WHERE rr.id_resultado_sp = ?; "
            );

            consulta_reactivos.setInt(1, id_resultado);
            rs_reactivos = consulta_reactivos.executeQuery();

            List<Reactivo> reactivos_resultado = new ArrayList<>();

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
                    + " FROM control_calidad.equipos_resultado_sp er "
                    + "   INNER JOIN control_calidad.equipos e ON er.id_equipo = e.id_equipo "
                    + "   INNER JOIN control_calidad.tipos_equipos te ON te.id_tipo_equipo = e.id_tipo_equipo "
                    + "   WHERE er.id_resultado_sp = ?; "
            );

            consulta_equipos.setInt(1, id_resultado);
            rs_equipos = consulta_equipos.executeQuery();

            if (rs_equipos.next()) {

                List<Equipo> equipos_resultado = new ArrayList<Equipo>();

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

                resultado.setEquipos_resultado(equipos_resultado);
            }

            consulta_patrones_controles = getConexion().prepareStatement(
                    " SELECT p.id_patron, p.tipo, p.numero_lote "
                    + " FROM control_calidad.patrones_resultados_sp pr "
                    + "     INNER JOIN control_calidad.patrones p ON pr.id_patron = p.id_patron "
                    + " WHERE pr.id_resultado_sp = ?;"
            );

            consulta_patrones_controles.setInt(1, id_resultado);

            rs_patrones_controles = consulta_patrones_controles.executeQuery();

            if (rs_patrones_controles.next()) {
                do {
                    Patron p = new Patron();

                    p.setId_patron(rs_patrones_controles.getInt("id_patron"));
                    p.setNumero_lote(rs_patrones_controles.getString("numero_lote"));
                    p.setTipo(rs_patrones_controles.getString("tipo"));

                    resultado.agregarPatron(p);
                } while (rs_patrones_controles.next());
            }

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
            cerrarSilencioso(rs_patrones_controles);
            cerrarSilencioso(consulta_patrones_controles);
            cerrarConexion();
        }

        return resultado;
    }

    public ResultadoSangriaPrueba editarResultadoSangriaPrueba(ResultadoSangriaPrueba resultado_sp) throws SIGIPROException {

        PreparedStatement consulta = null;
        PreparedStatement consulta_solicitud = null;
        ResultSet rs_solicitud = null;
        PreparedStatement delete_reactivos = null;
        PreparedStatement insert_reactivos = null;
        PreparedStatement delete_equipos = null;
        PreparedStatement insert_equipos = null;
        PreparedStatement delete_patrones_controles = null;
        PreparedStatement insert_patrones_controles = null;

        try {
            consulta = getConexion().prepareStatement(
                    " UPDATE control_calidad.resultados_analisis_sangrias_prueba "
                    + " SET wbc = ?,rbc = ?, hematocrito = ?, hemoglobina = ?, fecha = ?, id_usuario = ?, observaciones = ?, "
                    + " mcv = ?, mch = ?, mchc = ?, plt = ?, lym = ?, otros = ?, linfocitos = ?, num_otros = ? "
                    + " WHERE id_resultado_analisis_sp = ?;"
            );

            consulta.setFloat(1, resultado_sp.getWbc());
            consulta.setFloat(2, resultado_sp.getRbc());
            consulta.setFloat(3, resultado_sp.getHematocrito());
            consulta.setFloat(4, resultado_sp.getHemoglobina());
            consulta.setDate(5, resultado_sp.getFecha());
            consulta.setInt(6, resultado_sp.getUsuario().getId_usuario());
            consulta.setString(7, resultado_sp.getObservaciones());
            consulta.setFloat(8, resultado_sp.getMcv());
            consulta.setFloat(9, resultado_sp.getMch());
            consulta.setFloat(10, resultado_sp.getMchc());
            consulta.setFloat(11, resultado_sp.getPlt());
            consulta.setFloat(12, resultado_sp.getLym());
            consulta.setFloat(13, resultado_sp.getOtros());
            consulta.setFloat(14, resultado_sp.getLinfocitos());
            consulta.setFloat(15, resultado_sp.getNum_otros());
            consulta.setInt(16, resultado_sp.getId_resultado());

            consulta.executeUpdate();

            delete_reactivos = getConexion().prepareStatement(
                    " DELETE FROM control_calidad.reactivos_resultado_sp where id_resultado_sp = ?;"
            );
            delete_reactivos.setInt(1, resultado_sp.getId_resultado());
            delete_reactivos.execute();

            if (resultado_sp.tieneReactivos()) {
                insert_reactivos = getConexion().prepareStatement(
                        " INSERT INTO control_calidad.reactivos_resultado_sp (id_resultado_sp, id_reactivo) VALUES (?,?);"
                );

                for (Reactivo r : resultado_sp.getReactivos_resultado()) {
                    insert_reactivos.setInt(1, resultado_sp.getId_resultado());
                    insert_reactivos.setInt(2, r.getId_reactivo());
                    insert_reactivos.addBatch();
                }

                insert_reactivos.executeBatch();
            }

            delete_equipos = getConexion().prepareStatement(
                    " DELETE FROM control_calidad.equipos_resultado_sp where id_resultado_sp = ?;"
            );
            delete_equipos.setInt(1, resultado_sp.getId_resultado());
            delete_equipos.execute();

            if (resultado_sp.tieneEquipos()) {
                insert_equipos = getConexion().prepareStatement(
                        " INSERT INTO control_calidad.equipos_resultado_sp (id_resultado_sp, id_equipo) VALUES (?,?); "
                );

                for (Equipo e : resultado_sp.getEquipos_resultado()) {
                    insert_equipos.setInt(1, resultado_sp.getId_resultado());
                    insert_equipos.setInt(2, e.getId_equipo());
                    insert_equipos.addBatch();
                }

                insert_equipos.executeBatch();
            }

            delete_patrones_controles = getConexion().prepareStatement(
                    " DELETE FROM control_calidad.patrones_resultados_sp WHERE id_resultado_sp = ?; "
            );

            delete_patrones_controles.setInt(1, resultado_sp.getId_resultado());
            delete_patrones_controles.execute();

            if (resultado_sp.tienePatronesOControles()) {

                insert_patrones_controles = getConexion().prepareStatement(
                        " INSERT INTO control_calidad.patrones_resultados_sp (id_resultado_sp, id_patron) VALUES (?,?); "
                );

                for (Patron p : resultado_sp.getPatrones_resultado()) {
                    insert_patrones_controles.setInt(1, resultado_sp.getId_resultado());
                    insert_patrones_controles.setInt(2, p.getId_patron());
                    insert_patrones_controles.addBatch();
                }

                for (Patron p : resultado_sp.getControles_resultado()) {
                    insert_patrones_controles.setInt(1, resultado_sp.getId_resultado());
                    insert_patrones_controles.setInt(2, p.getId_patron());
                    insert_patrones_controles.addBatch();
                }

                insert_patrones_controles.executeBatch();

            }
            
            consulta_solicitud = getConexion().prepareStatement(
                    " SELECT g.id_solicitud from control_calidad.analisis_grupo_solicitud ags "
                    + " INNER JOIN control_calidad.grupos g ON ags.id_grupo = g.id_grupo "
                    + " WHERE ags.id_analisis_grupo_solicitud = ?; "
            );

            consulta_solicitud.setInt(1, resultado_sp.getAgs().getId_analisis_grupo_solicitud());

            rs_solicitud = consulta_solicitud.executeQuery();

            if (rs_solicitud.next()) {
                Grupo g = new Grupo();
                SolicitudCC s = new SolicitudCC();
                s.setId_solicitud(rs_solicitud.getInt("id_solicitud"));
                g.setSolicitud(s);
                resultado_sp.getAgs().setGrupo(g);
                resultado_sp.getAgs().getGrupo().setSolicitud(s);
            }

        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
            sql_ex.getNextException().printStackTrace(); 
        } finally {
            cerrarSilencioso(delete_reactivos);
            cerrarSilencioso(insert_reactivos);
            cerrarSilencioso(delete_equipos);
            cerrarSilencioso(insert_equipos);
            cerrarSilencioso(delete_patrones_controles);
            cerrarSilencioso(insert_patrones_controles);
            cerrarSilencioso(rs_solicitud);
            cerrarSilencioso(consulta_solicitud);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado_sp;

    }

}
