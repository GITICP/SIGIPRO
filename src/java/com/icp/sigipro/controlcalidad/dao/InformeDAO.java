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
public class InformeDAO extends DAO

{

    public InformeDAO() {
    }

    public Informe ingresarInforme(Informe informe) throws SIGIPROException {

        boolean resultado = false;
        boolean resultado_informe = false;
        boolean resultado_resultados = false;

        PreparedStatement consulta_informe = null;
        ResultSet rs_informe = null;
        PreparedStatement consulta_resultados = null;

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
            
            List<PreparedStatement> consultas_asociacion = informe.obtenerConsultasAsociacion(getConexion());
            
            for (PreparedStatement ps : consultas_asociacion) {
                ps.executeBatch();
            }

            resultado = resultado_resultados && resultado_informe;

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
            cerrarConexion();
        }

        return informe;
    }
}
