/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.Informe;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelperFechas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class InformeDAO extends DAO {
    
    public InformeDAO() {
    }
    public Informe ingresarInforme(Informe informe, boolean cerrar) throws SIGIPROException {

        boolean resultado = false;
        boolean resultado_informe = false;
        boolean resultado_resultados = false;
        boolean resultado_solicitud = false;

        PreparedStatement consulta_informe = null;
        ResultSet rs_informe = null;
        PreparedStatement update_solicitud = null;
        PreparedStatement consulta_ags_resultados = null;
        ResultSet rs_ags = null;
        
        List<PreparedStatement> insert_resultados = null;

        try {
            getConexion().setAutoCommit(false);

            consulta_informe = getConexion().prepareStatement(
                    " INSERT INTO control_calidad.informes(id_solicitud, fecha, realizado_por) VALUES (?,?,?) RETURNING id_informe; "
            );

            consulta_informe.setInt(1, informe.getSolicitud().getId_solicitud());
            consulta_informe.setDate(2, helper_fechas.getFecha_hoy());
            consulta_informe.setInt(3, informe.getUsuario().getId_usuario());

            rs_informe = consulta_informe.executeQuery();

            if (rs_informe.next()) {
                informe.setId_informe(rs_informe.getInt("id_informe"));
                resultado_informe = true;
            } else {
                throw new SQLException("Informe no se ingresó correctamente.");
            }
            
            String ids_resultados = "";
            
            for(Resultado r : informe.getResultados()) {
                ids_resultados += r.getId_resultado() + ",";
            }
            
            ids_resultados = ids_resultados.substring(0, ids_resultados.length() - 1);
            
            String str_consulta_ags_resultados = 
                      " SELECT * "
                    + " FROM control_calidad.analisis_grupo_solicitud ags "
                    + "         inner join control_calidad.grupos g on ags.id_grupo = g.id_grupo "
                    + "         inner join ( "
                    + "                 select id_resultado, id_analisis_grupo_solicitud as id_ags "
                    + "                 from control_calidad.resultados "
                    + "                 UNION "
                    + "                 select id_resultado_analisis_sp as id_resultado, id_ags "
                    + "                 from control_calidad.resultados_analisis_sangrias_prueba "
                    + "                 ) as resultados "
                    + "                 on ags.id_analisis_grupo_solicitud = resultados.id_ags "
                    + " WHERE id_solicitud = ? AND id_resultado IN (" + ids_resultados + ");";

            consulta_ags_resultados = getConexion().prepareStatement(
                    str_consulta_ags_resultados
            );

            consulta_ags_resultados.setInt(1, informe.getSolicitud().getId_solicitud());

            rs_ags = consulta_ags_resultados.executeQuery();

            insert_resultados = new ArrayList<>();

            while (rs_ags.next()) {
                String campo = "id_resultado";
                String campo_id_cambio_fecha = "id_resultado";
                String tabla = "resultados";
                if (rs_ags.getInt("id_analisis") == Integer.MAX_VALUE) {
                    campo = "id_resultado_sp";
                    campo_id_cambio_fecha = "id_resultado_analisis_sp";
                    tabla = "resultados_analisis_sangrias_prueba";
                }
                
                PreparedStatement consulta_resultado = getConexion().prepareStatement(
                        " INSERT INTO control_calidad.resultados_informes(id_informe, " + campo + ") VALUES (?,?);"
                );
                
                int id_resultado = rs_ags.getInt("id_resultado");
                consulta_resultado.setInt(1, informe.getId_informe());
                consulta_resultado.setInt(2, id_resultado);
                insert_resultados.add(consulta_resultado);
                
                PreparedStatement consulta_resultado_fecha = getConexion().prepareStatement(
                        " UPDATE control_calidad." + tabla + " res SET fecha_reportado = "
                                + " CASE WHEN res.fecha_reportado is null THEN current_timestamp ELSE res.fecha_reportado END "
                                + " WHERE " + campo_id_cambio_fecha + " = ?; "
                );
                
                consulta_resultado_fecha.setInt(1, id_resultado);
                insert_resultados.add(consulta_resultado_fecha);
                
            }
            
            boolean iteracion_completa = true;
            for (PreparedStatement ps_resultado : insert_resultados) {
                if (ps_resultado.executeUpdate() != 1) {
                    iteracion_completa = false;
                }
            }
            
            if (iteracion_completa) {
                resultado_resultados = true;
            }

            List<PreparedStatement> consultas_asociacion = informe.getSolicitud().obtenerConsultasInsertarAsociacion(getConexion());
            List<PreparedStatement> consultas_asociacion_informe = informe.getSolicitud().obtenerConsultasInsertarAsociacionInforme(getConexion());

            consultas_asociacion.addAll(consultas_asociacion_informe);

            for (PreparedStatement ps : consultas_asociacion) {
                ps.executeBatch();
            }

            update_solicitud = getConexion().prepareStatement(
                    " UPDATE control_calidad.solicitudes SET estado = ?, fecha_cierre=? WHERE id_solicitud = ? "
            );

            if (cerrar) {
                update_solicitud.setString(1, "Completada");
                update_solicitud.setTimestamp(2, helper_fechas.getFecha_hoy_timestamp());
            } else {
                update_solicitud.setString(1, "Resultado Parcial");
                update_solicitud.setNull(2, java.sql.Types.DATE);
            }

            update_solicitud.setInt(3, informe.getSolicitud().getId_solicitud());

            resultado_solicitud = update_solicitud.executeUpdate() == 1;

            resultado = resultado_resultados && resultado_informe && resultado_solicitud;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Ha ocurrido un error al registrar el informe. Inténtelo nuevamente.");
        } finally {
            try {
                if (resultado) {
                    getConexion().commit();
                } else {
                    getConexion().rollback();
                }
            } catch (SQLException sql_ex) {
                throw new SIGIPROException("Error de comunicación con la base datos. Notifique al administrador del sistema.");
            }

            cerrarSilencioso(rs_informe);
            cerrarSilencioso(consulta_informe);
            cerrarSilencioso(update_solicitud);
            cerrarConexion();
        }

        return informe;
    }
    public Informe editarInforme(Informe informe, boolean cerrar) throws SIGIPROException {

        boolean resultado = false;
        boolean resultado_informe = false;
        boolean resultado_resultados = false;
        boolean resultado_solicitud = false;

        PreparedStatement consulta_update_informe = null;
        int rs_informe;
        PreparedStatement consulta_eliminacion_resultados = null;
        PreparedStatement consulta_resultados = null;
        PreparedStatement consulta_resultados_fecha = null;
        PreparedStatement update_solicitud = null;

        try {
            getConexion().setAutoCommit(false);

            consulta_update_informe = getConexion().prepareStatement(
                    " UPDATE control_calidad.informes SET fecha = ?, realizado_por = ? WHERE id_informe = ?; "
            );

            consulta_update_informe.setDate(1, helper_fechas.getFecha_hoy());
            consulta_update_informe.setInt(2, informe.getUsuario().getId_usuario());
            consulta_update_informe.setInt(3, informe.getId_informe());

            rs_informe = consulta_update_informe.executeUpdate();

            if (rs_informe == 1) {
                resultado_informe = true;
            } else {
                throw new SQLException("Informe no se ingresó correctamente.");
            }

            consulta_eliminacion_resultados = getConexion().prepareStatement(
                    " DELETE FROM control_calidad.resultados_informes WHERE id_informe = ?"
            );

            consulta_eliminacion_resultados.setInt(1, informe.getId_informe());
            consulta_eliminacion_resultados.executeUpdate();

            String campo_id_resultado = (!informe.getSolicitud().getTipoAsociacionString().equals("sangria_prueba")) ? "id_resultado" : "id_resultado_sp";
            String campo_id_resultado_fecha = (!informe.getSolicitud().getTipoAsociacionString().equals("sangria_prueba")) ? "id_resultado" : "id_resultado_analisis_sp";
            String tabla = (!informe.getSolicitud().getTipoAsociacionString().equals("sangria_prueba")) ? "resultados" : "resultados_analisis_sangrias_prueba";

            consulta_resultados = getConexion().prepareStatement(
                    " INSERT INTO control_calidad.resultados_informes(id_informe, " + campo_id_resultado + ") VALUES (?,?);"
            );

            for (Resultado r : informe.getResultados()) {
                consulta_resultados.setInt(1, informe.getId_informe());
                consulta_resultados.setInt(2, r.getId_resultado());
                consulta_resultados.addBatch();
            }
            
            consulta_resultados_fecha = getConexion().prepareStatement(
                    " UPDATE control_calidad." + tabla + " res SET fecha_reportado = "
                            + " CASE WHEN res.fecha_reportado is null THEN current_timestamp ELSE res.fecha_reportado END "
                            + " WHERE " + campo_id_resultado_fecha + " = ? "
            );
            
            for (Resultado r : informe.getResultados()) {
                consulta_resultados_fecha.setInt(1, r.getId_resultado());
                consulta_resultados_fecha.addBatch();
            }

            int[] contadores_resultados = consulta_resultados.executeBatch();
            int[] contadores_resultados_fecha = consulta_resultados_fecha.executeBatch();
            
            int[] contadores_final = new int[contadores_resultados.length + contadores_resultados_fecha.length];
            System.arraycopy(contadores_resultados, 0, contadores_final, 0, contadores_resultados.length);
            System.arraycopy(contadores_resultados_fecha, 0, contadores_final, contadores_resultados.length, contadores_resultados_fecha.length);

            boolean iteracion_completa = true;
            for (int i : contadores_final) {
                if (i != 1) {
                    iteracion_completa = false;
                }
            }

            if (iteracion_completa) {
                resultado_resultados = true;
            }

            List<PreparedStatement> consultas_asociacion = informe.getSolicitud().obtenerConsultasEditarInforme(getConexion());

            for (PreparedStatement ps : consultas_asociacion) {
                ps.executeBatch();
            }

            if (cerrar) {
                update_solicitud = getConexion().prepareStatement(
                        " UPDATE control_calidad.solicitudes SET estado = ?, fecha_cierre = ? WHERE id_solicitud = ? "
                ); 

                update_solicitud.setString(1, "Completada");
                update_solicitud.setTimestamp(2, helper_fechas.getFecha_hoy_timestamp());
                update_solicitud.setInt(3, informe.getSolicitud().getId_solicitud());

                resultado_solicitud = update_solicitud.executeUpdate() == 1;
            } else {
                resultado_solicitud = true;
            }

            resultado = resultado_resultados && resultado_informe && resultado_solicitud;

        } catch (SQLException ex) {
            ex.printStackTrace();
            ex.getNextException().printStackTrace();
            throw new SIGIPROException("Ha ocurrido un error al registrar el informe. Inténtelo nuevamente.");
        } finally {
            try {
                if (resultado) {
                    getConexion().commit();
                } else {
                    getConexion().rollback();
                }
            } catch (SQLException sql_ex) {
                throw new SIGIPROException("Error de comunicación con la base datos. Notifique al administrador del sistema.");
            }

            cerrarSilencioso(consulta_update_informe);
            cerrarSilencioso(consulta_eliminacion_resultados);
            cerrarSilencioso(consulta_resultados);
            cerrarSilencioso(update_solicitud);
            cerrarConexion();
        }

        return informe;
    }
    public void notificacion_informe_parcial(int id_solicitud) throws SIGIPROException {
    SolicitudDAO sdao = new SolicitudDAO();
    SolicitudCC s = sdao.obtenerSolicitud(id_solicitud);
    Usuario solicitante = s.getUsuario_solicitante();
    if (solicitante.getNombre_seccion().equalsIgnoreCase("Producción")) {
      try {
        PreparedStatement consulta = getConexion().prepareStatement("SELECT control_calidad.crear_notificacion_informe_parcial_todo();");
        ResultSet rs = consulta.executeQuery();
        rs.close();
        consulta.close();
        cerrarConexion();
      } catch (Exception ex) {
        ex.printStackTrace();
        throw new SIGIPROException("Se produjo un error al crear una notificación");
      }
    } else {
       try {
        PreparedStatement consulta = getConexion().prepareStatement("SELECT control_calidad.crear_notificacion_informe_parcial(?);");
        consulta.setInt(1, solicitante.getID());
        ResultSet rs = consulta.executeQuery();
        rs.close();
        consulta.close();
        cerrarConexion();
      } catch (Exception ex) {
        ex.printStackTrace();
        throw new SIGIPROException("Se produjo un error al crear una notificación");
      }
    }
  }
    public void notificacion_informe_final(int id_solicitud) throws SIGIPROException {
    SolicitudDAO sdao = new SolicitudDAO();
    SolicitudCC s = sdao.obtenerSolicitud(id_solicitud);
    Usuario solicitante = s.getUsuario_solicitante();
    if (solicitante.getNombre_seccion().equalsIgnoreCase("Producción")) {
      try {
        PreparedStatement consulta = getConexion().prepareStatement("SELECT control_calidad.crear_notificacion_informe_final_todo();");
        ResultSet rs = consulta.executeQuery();
        rs.close();
        consulta.close();
        cerrarConexion();
      } catch (Exception ex) {
        ex.printStackTrace();
        throw new SIGIPROException("Se produjo un error al crear una notificación");
      }
    } else {
       try {
        PreparedStatement consulta = getConexion().prepareStatement("SELECT control_calidad.crear_notificacion_informe_final(?);");
        consulta.setInt(1, solicitante.getID());
        ResultSet rs = consulta.executeQuery();
        rs.close();
        consulta.close();
        cerrarConexion();
      } catch (Exception ex) {
        ex.printStackTrace();
        throw new SIGIPROException("Se produjo un error al crear una notificación");
      }
    }
    }
}
