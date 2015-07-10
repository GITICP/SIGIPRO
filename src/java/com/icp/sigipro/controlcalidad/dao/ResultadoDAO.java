/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.Equipo;
import com.icp.sigipro.controlcalidad.modelos.Reactivo;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.utilidades.HelperFechas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;

/**
 *
 * @author Boga
 */
public class ResultadoDAO extends DAO
{

    HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();

    public ResultadoDAO()
    {
    }

    public Resultado insertarResultado(Resultado resultado) throws SIGIPROException
    {
        boolean commit = false;
        PreparedStatement insert_resultado = null;
        ResultSet rs_insert_resultado = null;
        PreparedStatement insert_reactivos = null;
        PreparedStatement insert_equipos = null;

        try {

            getConexion().setAutoCommit(false);

            insert_resultado = getConexion().prepareStatement(
                    " INSERT INTO control_calidad.resultados (path, datos, id_usuario, fecha, id_analisis_grupo_solicitud) "
                    + " VALUES (?,?,?,?,?) RETURNING id_resultado; "
            );

            SQLXML datos = getConexion().createSQLXML();
            datos.setString(resultado.getDatos_string());

            insert_resultado.setString(1, resultado.getPath());
            insert_resultado.setSQLXML(2, datos);
            insert_resultado.setInt(3, 1);
            insert_resultado.setDate(4, helper_fechas.getFecha_hoy());
            insert_resultado.setInt(5, 1);

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
            
            commit = true;

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("<Mensaje>");
        }
        finally {
            try {
                if (commit) {
                    getConexion().commit();
                }
                else {
                    getConexion().rollback();
                }
            }
            catch (SQLException ex) {
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

}
