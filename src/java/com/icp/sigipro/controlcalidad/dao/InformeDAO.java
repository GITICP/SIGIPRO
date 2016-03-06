/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.Informe;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        boolean resultado_solicitud;

        PreparedStatement consulta_informe = null;
        ResultSet rs_informe = null;
        PreparedStatement consulta_resultados = null;
        PreparedStatement update_solicitud = null;

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
            
            String campo_id_resultado = (!informe.getSolicitud().getTipoAsociacionString().equals("sangria_prueba")) ? "id_resultado" : "id_resultado_sp";

            consulta_resultados = getConexion().prepareStatement(
                    " INSERT INTO control_calidad.resultados_informes(id_informe, " + campo_id_resultado + ") VALUES (?,?);"
            );

            for (Resultado r : informe.getResultados()) {
                consulta_resultados.setInt(1, informe.getId_informe());
                consulta_resultados.setInt(2, r.getId_resultado());
                consulta_resultados.addBatch();
            }

            int[] contadores = consulta_resultados.executeBatch();

            boolean iteracion_completa = true;
            for (int i : contadores) {
                if (i != 1) {
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
            } else {
                update_solicitud.setString(1, "Resultado Parcial");
            }

            update_solicitud.setTimestamp(2, informe.getSolicitud().getFecha_cierre());
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
            cerrarSilencioso(consulta_resultados);
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

            consulta_resultados = getConexion().prepareStatement(
                    " INSERT INTO control_calidad.resultados_informes(id_informe, id_resultado) VALUES (?,?);"
            );

            for (Resultado r : informe.getResultados()) {
                consulta_resultados.setInt(1, informe.getId_informe());
                consulta_resultados.setInt(2, r.getId_resultado());
                consulta_resultados.addBatch();
            }

            int[] contadores = consulta_resultados.executeBatch();

            boolean iteracion_completa = true;
            for (int i : contadores) {
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
                        " UPDATE control_calidad.solicitudes SET estado = ?, fecha_cierre=? WHERE id_solicitud = ? "
                );

                update_solicitud.setString(1, "Completada");
                update_solicitud.setTimestamp(2, informe.getSolicitud().getFecha_cierre());
                update_solicitud.setInt(3, informe.getSolicitud().getId_solicitud());

                resultado_solicitud = update_solicitud.executeUpdate() == 1;
            } else {
                resultado_solicitud = true;
            }

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

            cerrarSilencioso(consulta_update_informe);
            cerrarSilencioso(consulta_eliminacion_resultados);
            cerrarSilencioso(consulta_resultados);
            cerrarSilencioso(update_solicitud);
            cerrarConexion();
        }

        return informe;
    }
}
