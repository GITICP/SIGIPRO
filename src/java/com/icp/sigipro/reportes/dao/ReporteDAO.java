/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.dao;

import com.google.gson.stream.JsonWriter;
import com.icp.sigipro.seguridad.modelos.Seccion;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.reportes.modelos.BuilderParametro;
import com.icp.sigipro.reportes.modelos.ObjetoAjaxReporte;
import com.icp.sigipro.reportes.modelos.Parametro;
import com.icp.sigipro.reportes.modelos.Reporte;
import com.icp.sigipro.utilidades.ExcelWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.security.sasl.AuthenticationException;

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

    public List<Reporte> obtenerReportes(boolean por_usuario, int id_usuario) throws SIGIPROException, AuthenticationException {
        List<Reporte> resultado = new ArrayList<>();

        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {

            String s_consulta = " SELECT r.id_reporte, r.nombre, r.descripcion, s.id_seccion, s.nombre_seccion "
                    + " FROM reportes.reportes r "
                    + " INNER JOIN seguridad.secciones s ON s.id_seccion = r.id_seccion ";

            if (por_usuario) {
                s_consulta += " WHERE r.id_reporte IN (SELECT id_reporte FROM reportes.permisos_reportes WHERE id_usuario = ?); ";
            }

            consulta = getConexion().prepareStatement(s_consulta);

            if (por_usuario) {
                consulta.setInt(1, id_usuario);
            }

            rs = consulta.executeQuery();

            int contador = 0;

            while (rs.next()) {
                contador++;

                Reporte r = new Reporte();

                r.setId_reporte(rs.getInt("id_reporte"));
                r.setNombre(rs.getString("nombre"));
                r.setDescripcion(rs.getString("descripcion"));

                Seccion s = new Seccion();
                s.setId_seccion(rs.getInt("id_seccion"));
                s.setNombre_seccion(rs.getString("nombre_seccion"));

                r.setSeccion(s);

                resultado.add(r);
            }

            if (contador == 0) {
                throw new AuthenticationException();
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

    public Reporte obtenerReporte(int id_reporte) throws SIGIPROException {
        Reporte resultado = new Reporte();
        resultado.setId_reporte(id_reporte);

        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {
            consulta = getConexion().prepareStatement(
                    " SELECT r.nombre, r.descripcion, r.consulta, r.url_js, r.id_seccion, s.nombre_seccion, p.num_parametro, p.tipo_parametro, p.info_adicional, p.nombre AS nombre_param"
                    + " FROM reportes.reportes r "
                    + "   INNER JOIN seguridad.secciones s ON s.id_seccion = r.id_seccion "
                    + "   LEFT JOIN reportes.parametros p ON p.id_reporte = r.id_reporte "
                    + " WHERE r.id_reporte = ?; "
            );

            consulta.setInt(1, id_reporte);

            rs = consulta.executeQuery();

            if (rs.next()) {
                BuilderParametro builder = new BuilderParametro();

                resultado.setNombre(rs.getString("nombre"));
                resultado.setDescripcion(rs.getString("descripcion"));
                resultado.setConsulta(rs.getString("consulta"));
                resultado.setUrl_js(rs.getString("url_js"));

                Seccion s = new Seccion();
                s.setId_seccion(rs.getInt("id_seccion"));
                s.setNombre_seccion(rs.getString("nombre_seccion"));

                do {
                    Parametro p = builder.crearParametro(rs);
                    if(p != null) {
                        resultado.agregarParametro(p, false);
                    }
                } while (rs.next());
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

            rs = consulta.executeQuery();

            w.beginObject();

            construirJsonDesdeResultSet(rs, w, false);

            getConexion().setAutoCommit(false);

            insert_reporte = getConexion().prepareStatement(
                    "INSERT INTO reportes.reportes(nombre, consulta, descripcion, url_js, id_seccion) VALUES (?,?,?,?,?) RETURNING id_reporte;"
            );

            insert_reporte.setString(1, r.getNombre());
            insert_reporte.setString(2, r.getConsulta());
            insert_reporte.setString(3, r.getDescripcion());
            insert_reporte.setString(4, r.getUrl_js());
            insert_reporte.setInt(5, r.getSeccion().getId_seccion());

            rs_id_reporte = insert_reporte.executeQuery();

            if (rs_id_reporte.next()) {
                r.setId_reporte(rs_id_reporte.getInt(1));
            } else {
                throw new SIGIPROException("Reporte no se pudo insertar.");
            }

            insert_parametros = getConexion().prepareStatement(
                    "INSERT INTO reportes.parametros (id_reporte, num_parametro, tipo_parametro, info_adicional, nombre, repeticiones) VALUES (?,?,?,?,?,?);"
            );

            for (Parametro p : r.getParametros()) {
                insert_parametros.setInt(1, r.getId_reporte());
                p.agregarAInsert(insert_parametros);
                insert_parametros.addBatch();
            }

            insert_parametros.executeBatch();

            resultado = true;

            w.name("message");
            w.value("Exito");
            w.endObject();
        } catch (IOException io) {
            resultado = false;
            io.printStackTrace();
            throw new SIGIPROException("Error insesperado. Contacte al administrador del sistema.");
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (resultado) {
                    getConexion().commit();
                } else {
                    getConexion().rollback();
                }
            } catch (SQLException sql_ex) {
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

    public void obtenerDatos(Reporte r, JsonWriter w, boolean para_grafico) throws SIGIPROException {

        PreparedStatement consulta_datos = null;
        ResultSet rs_datos = null;

        try {
            String string_consulta = r.getStringConsulta();
            consulta_datos = getConexion().prepareStatement(string_consulta);
            r.prepararConsulta(consulta_datos);

            rs_datos = consulta_datos.executeQuery();

            w.beginObject();

            String mensaje = construirJsonDesdeResultSet(rs_datos, w, para_grafico);

            w.name("message");
            w.value(mensaje);
            w.endObject();

        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
            try {
                w.endArray();
                w.name("message");
                w.value("Error al obtener la información de los datos. Verifique que los parámetros estén correctos y de persistir el problema, contacte al administrador del sistema.");
                w.endObject();
            } catch (IOException io) {
                io.printStackTrace();
            }
            throw new SIGIPROException("Error al obtener la información de los datos. Verifique que los parámetros estén correctos y de persistir el problema, contacte al administrador del sistema.");
        } catch (IOException io) {
            io.printStackTrace();
            throw new SIGIPROException("Error insesperado. Contacte al administrador del sistema.");
        } finally {
            cerrarSilencioso(rs_datos);
            cerrarSilencioso(consulta_datos);
            cerrarConexion();
        }

    }

    private String construirJsonDesdeResultSet(ResultSet rs, JsonWriter w, boolean para_grafico) throws SQLException, IOException, SIGIPROException {
        ResultSetMetaData rsmd = rs.getMetaData();
        String resultado = "";

        if (rs.next()) {

            w.name("columnas");
            w.beginArray();

            if (para_grafico) {
                crearArrayColumnasParaGrafico(rsmd, w);
            } else {
                crearArrayColumnasParaTabla(rsmd, w);
            }

            w.endArray();

            w.name("data");
            w.beginArray();

            do {
                w.beginArray();

                for (int cont_col = 1; cont_col <= rsmd.getColumnCount(); cont_col++) {

                    int tipo_columna = rsmd.getColumnType(cont_col);

                    switch (tipo_columna) {
                        case 4:
                            w.value(rs.getInt(cont_col));
                            break;
                        case 12:
                            w.value(rs.getString(cont_col));
                            break;
                        case 91:
                            w.value(rs.getString(cont_col));
                            break;
                        case 92:
                            String fecha = helper_fechas.formatearFecha(rs.getDate(cont_col));
                            w.value(fecha);
                            break;
                        case -5:
                            w.value(rs.getInt(cont_col));
                            break;
                        case -7:
                            w.value(rs.getBoolean(cont_col));
                            break;
                        default:
                            throw new SIGIPROException("Tipo de dato no soportado.");
                    }
                }

                w.endArray();

            } while (rs.next());

            w.endArray();

            resultado = "Exito";

        } else {
            resultado = "Su consulta no produjo ningún resultado. Intente modificar los valores de los parámetros.";
        }

        return resultado;

    }
    
    public ExcelWriter obtenerDatosExcel(Reporte r) throws SIGIPROException {

        PreparedStatement consulta_datos = null;
        ResultSet rs_datos = null;
        ExcelWriter w = null;

        try {
            String string_consulta = r.getStringConsulta();
            consulta_datos = getConexion().prepareStatement(string_consulta);
            r.prepararConsulta(consulta_datos);

            rs_datos = consulta_datos.executeQuery();

            w = construirExcelDesdeResultSet(rs_datos);

        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
            throw new SIGIPROException("Error al obtener la información de los datos. Verifique que los parámetros estén correctos y de persistir el problema, contacte al administrador del sistema.");
        } finally {
            cerrarSilencioso(rs_datos);
            cerrarSilencioso(consulta_datos);
            cerrarConexion();
        }
        
        return w;

    }
    
    private ExcelWriter construirExcelDesdeResultSet(ResultSet rs) throws SQLException, SIGIPROException {
        ResultSetMetaData rsmd = rs.getMetaData();
        ExcelWriter w = new ExcelWriter();

        if (rs.next()) {
            
            crearArrayColumnasParaExcel(rsmd, w);

            do {
                
                w.comenzarFila();

                for (int cont_col = 1; cont_col <= rsmd.getColumnCount(); cont_col++) {

                    int tipo_columna = rsmd.getColumnType(cont_col);

                    switch (tipo_columna) {
                        case 4:
                            w.agregarEntero(rs.getInt(cont_col));
                            break;
                        case 12:
                            w.agregarString(rs.getString(cont_col));
                            break;
                        case 92:
                            w.agregarFecha(rs.getDate(cont_col));
                            break;
                        case -5:
                            w.agregarEntero(rs.getInt(cont_col));
                            break;
                        default:
                            throw new SIGIPROException("Tipo de dato no soportado.");
                    }
                }
                
                w.terminarFila();

            } while (rs.next());

        } else {
            w.comenzarFila();
            w.agregarString("Su consulta no produjo ningún resultado. Intente modificar los valores de los parámetros.");
            w.terminarFila();
        }
        
        return w;

    }

    public void insertarPermisos(Reporte reporte, String[] ids) throws SIGIPROException {

        PreparedStatement insert_permisos = null;
        PreparedStatement delete_permisos = null;
        boolean resultado_transaccion = false;

        try {
            getConexion().setAutoCommit(false);

            delete_permisos = getConexion().prepareStatement(
                    " DELETE FROM reportes.permisos_reportes WHERE id_reporte = ? "
            );

            delete_permisos.setInt(1, reporte.getId_reporte());

            delete_permisos.execute();

            insert_permisos = getConexion().prepareStatement(
                    " INSERT INTO reportes.permisos_reportes(id_reporte, id_usuario) VALUES (?,?);"
            );

            for (String id_s : ids) {
                insert_permisos.setInt(1, reporte.getId_reporte());
                insert_permisos.setInt(2, Integer.parseInt(id_s));
                insert_permisos.addBatch();
            }

            insert_permisos.executeBatch();

            resultado_transaccion = true;

        } catch (SQLException sql_ex) {

            resultado_transaccion = false;
            sql_ex.printStackTrace();
            throw new SIGIPROException("Error de comunicación con la base de datos. Comunique al administrador del sistema.");
        } finally {
            try {
                if (resultado_transaccion) {
                    getConexion().commit();
                } else {
                    getConexion().rollback();
                }
            } catch (SQLException sql_ex) {
                throw new SIGIPROException("Error de comunicación con la base de datos. Comunique al administrador del sistema.");
            }

            cerrarSilencioso(insert_permisos);
            cerrarSilencioso(delete_permisos);
            cerrarConexion();
        }

    }

    public List<Integer> obtenerIdsUsuariosConPermiso(int id_reporte) throws SIGIPROException {

        List<Integer> resultado = new ArrayList<>();

        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {

            consulta = getConexion().prepareStatement(" SELECT id_usuario FROM reportes.permisos_reportes where id_reporte = ? ");

            consulta.setInt(1, id_reporte);

            rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.add(rs.getInt("id_usuario"));
            }

        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
            throw new SIGIPROException("Error de comunicación con la base de datos. Notifique al administrador del sistema.");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
        }

        return resultado;
    }

    public boolean validarAcceso(int id_usuario, int id_reporte) throws AuthenticationException, SIGIPROException {
        boolean resultado = false;

        PreparedStatement consulta = null;
        ResultSet resultado_consulta = null;

        try {
            consulta = getConexion().prepareStatement(
                    " SELECT 1 "
                    + " FROM reportes.permisos_reportes "
                    + " WHERE id_usuario = ? AND id_reporte = ?; "
            );

            consulta.setInt(1, id_usuario);
            consulta.setInt(2, id_reporte);

            resultado_consulta = consulta.executeQuery();

            if (resultado_consulta.next()) {
                resultado = true;
            } else {
                throw new AuthenticationException();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al comunicarse con la base de datos. Notifique al administrador del sistema.");
        } finally {
            cerrarSilencioso(resultado_consulta);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    private void crearArrayColumnasParaTabla(ResultSetMetaData rsmd, JsonWriter w) throws IOException, SQLException {
        for (int cont_col = 1; cont_col <= rsmd.getColumnCount(); cont_col++) {
            w.beginObject();
            w.name("title");
            String nombre_col = rsmd.getColumnLabel(cont_col);
            w.value(nombre_col);
            w.endObject();
        }
    }

    private void crearArrayColumnasParaGrafico(ResultSetMetaData rsmd, JsonWriter w) throws IOException, SQLException {
        for (int cont_col = 1; cont_col <= rsmd.getColumnCount(); cont_col++) {
            String nombre_col = rsmd.getColumnLabel(cont_col);
            w.value(nombre_col);
        }
    }
    
    private void crearArrayColumnasParaExcel(ResultSetMetaData rsmd, ExcelWriter exc_writer) throws SQLException {
        
        exc_writer.comenzarFila();
        
        for (int cont_col = 1; cont_col <= rsmd.getColumnCount(); cont_col++) {    
            String nombre_col = rsmd.getColumnLabel(cont_col);
            exc_writer.agregarString(nombre_col);
        }
        
        exc_writer.terminarFila();
    }

}
