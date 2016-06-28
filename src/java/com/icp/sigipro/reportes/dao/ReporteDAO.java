/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.dao;

import com.google.gson.stream.JsonWriter;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.reportes.modelos.ObjetoAjaxReporte;
import com.icp.sigipro.reportes.modelos.Parametro;
import com.icp.sigipro.reportes.modelos.Reporte;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Boga
 */
public class ReporteDAO extends DAO {

    private final Map<String, String> tablas_consultas = new HashMap<String, String>() {
        {
            put("cat_interno", " SELECT id_producto as VAL, nombre || '(' || codigo_icp || ')' AS TEXTO FROM bodega.catalogo_interno ");
            put("secciones", " SELECT id_seccion as VAL, nombre_seccion AS TEXTO FROM seguridad.secciones");
        }
    };

    public List<Reporte> obtenerReportes() throws SIGIPROException {
        List<Reporte> resultado = new ArrayList<>();

        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {
            consulta = getConexion().prepareStatement("SELECT * FROM reportes.reportes");

            rs = consulta.executeQuery();

            while (rs.next()) {
                Reporte r = new Reporte();

                r.setId_reporte(rs.getInt("id_reporte"));
                r.setNombre(rs.getString("nombre"));
                r.setDescripcion(rs.getString("descripcion"));

                resultado.add(r);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener la información de la base de datos.");
        } finally {
            cerrarSilencioso(consulta);
            cerrarSilencioso(rs);
            cerrarConexion();
        }

        return resultado;
    }

    public List<ObjetoAjaxReporte> obtenerObjetos(String tabla) throws SIGIPROException {
        List<ObjetoAjaxReporte> resultado = new ArrayList<>();

        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {
            consulta = getConexion().prepareStatement(
                    tablas_consultas.get(tabla)
            );

            rs = consulta.executeQuery();

            while (rs.next()) {
                ObjetoAjaxReporte r = new ObjetoAjaxReporte();
                r.setVal(rs.getInt("VAL"));
                r.setTexto(rs.getString("TEXTO"));
                resultado.add(r);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener los datos. Notifique al administrador del sistema");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;
    }

    public void probarEInsertarReporte(Reporte r, JsonWriter w) throws SIGIPROException {

        PreparedStatement consulta = null;
        PreparedStatement insert_reporte = null;
        PreparedStatement insert_parametros = null;
        ResultSet rs = null;
        ResultSet rs_id_reporte = null;
        boolean resultado = false;

        try {
            String string_consulta = r.getStringConsulta();
            consulta = getConexion().prepareStatement(string_consulta);
            r.prepararConsulta(consulta);

            w.beginObject();
            w.name("data");
            w.beginArray();

            rs = consulta.executeQuery();

            construirJsonDesdeResultSet(rs, w);

            getConexion().setAutoCommit(false);

            insert_reporte = getConexion().prepareStatement(
                    "INSERT INTO reportes.reportes(nombre, consulta, descripcion) VALUES (?,?,?) RETURNING id_reporte;"
            );

            insert_reporte.setString(1, r.getNombre());
            insert_reporte.setString(2, r.getConsulta());
            insert_reporte.setString(3, r.getDescripcion());

            rs_id_reporte = insert_reporte.executeQuery();

            if (rs_id_reporte.next()) {
                r.setId_reporte(rs_id_reporte.getInt(1));
            } else {
                throw new SIGIPROException("Reporte no se pudo insertar.");
            }

            insert_parametros = getConexion().prepareStatement(
                    "INSERT INTO reportes.parametros (id_reporte, num_parametro, tipo_parametro, info_adicional) VALUES (?,?,?,?);"
            );

            for (Parametro p : r.getParametros()) {
                insert_parametros.setInt(1, r.getId_reporte());
                p.agregarAInsert(insert_parametros);
                insert_parametros.addBatch();
            }

            insert_parametros.executeBatch();

            resultado = true;

            w.endArray();
            w.name("message");
            w.value("Éxito");
            w.endObject();
        } catch (IOException io) {
            resultado = false;
            io.printStackTrace();
            throw new SIGIPROException("Error insesperado. Contact al administrador del sistema.");
        } catch (SQLException ex) {
            resultado = false;
            ex.printStackTrace();
            try {
                w.endArray();
                w.name("message");
                w.value(ex.getLocalizedMessage());
                w.endObject();
            } catch (IOException io) {
                io.printStackTrace();
            }
            throw new SIGIPROException("Error al obtener la información de la base de datos.");
        } finally {
            try {
                if (resultado) {
                    getConexion().commit();
                } else {
                    getConexion().rollback();
                }
            } catch(SQLException sql_ex) {
                throw new SIGIPROException("Error de conexión con la base de datos.");
            }
            cerrarSilencioso(consulta);
            cerrarSilencioso(insert_parametros);
            cerrarSilencioso(insert_reporte);
            cerrarSilencioso(rs_id_reporte);
            cerrarSilencioso(rs);
            cerrarConexion();
        }

    }

    private void construirJsonDesdeResultSet(ResultSet rs, JsonWriter w) throws SQLException, IOException, SIGIPROException {
        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()) {
            w.beginObject();

            for (int cont_col = 1; cont_col <= rsmd.getColumnCount(); cont_col++) {
                String nombre_col = rsmd.getColumnLabel(cont_col);
                w.name(nombre_col);
                int tipo_columna = rsmd.getColumnType(cont_col);

                switch (tipo_columna) {
                    case 4:
                        w.value(rs.getInt(cont_col));
                        break;
                    case 12:
                        w.value(rs.getString(cont_col));
                        break;
                    case 92:
                        String fecha = helper_fechas.formatearFecha(rs.getDate(cont_col));
                        w.value(fecha);
                        break;
                    case -5:
                        w.value(rs.getInt(cont_col));
                        break;
                    default:
                        throw new SIGIPROException("Tipo de dato no soportado.");
                }
            }

            w.endObject();
        }
    }

}
