/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.controlcalidad.modelos.TipoMuestra;
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
public class TipoMuestraDAO extends DAO {

    public TipoMuestraDAO() {
    }

    public TipoMuestra insertarTipoDeMuestra(TipoMuestra m) throws SIGIPROException {
        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {
            consulta = getConexion().prepareStatement(
                    " INSERT INTO control_calidad.tipos_muestras (nombre, descripcion) "
                    + " VALUES (?,?) RETURNING id_tipo_muestra; "
            );

            consulta.setString(1, m.getNombre());
            consulta.setString(2, m.getDescripcion());

            rs = consulta.executeQuery();

            if (rs.next()) {
                m.setId_tipo_muestra(rs.getInt("id_tipo_muestra"));
            } else {
                throw new SQLException("TipoMuestra > insertarTipoMuestra: No hubo respuesta al insertar.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("No se pudo insertar el tipo de muestra. Inténtelo nuevamente y de persistir el problema notifique al adminstrador del sistema.");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return m;
    }

    public List<TipoMuestra> obtenerTiposDeMuestra() throws SIGIPROException {
        List<TipoMuestra> resultado = new ArrayList<TipoMuestra>();
        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {
            consulta = getConexion().prepareStatement(
                    " SELECT * FROM control_calidad.tipos_muestras; "
            );

            rs = consulta.executeQuery();

            while (rs.next()) {
                TipoMuestra m = new TipoMuestra();

                m.setId_tipo_muestra(rs.getInt("id_tipo_muestra"));
                m.setNombre(rs.getString("nombre"));
                m.setDescripcion(rs.getString("descripcion"));

                resultado.add(m);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener listado completo.");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;
    }

    public List<TipoMuestra> obtenerTiposDeMuestraSolicitud() throws SIGIPROException {
        List<TipoMuestra> resultado = new ArrayList<TipoMuestra>();
        PreparedStatement consulta = null;
        ResultSet res = null;

        try {
            consulta = getConexion().prepareStatement(
                    " SELECT * FROM control_calidad.tipos_muestras; "
            );

            res = consulta.executeQuery();

            while (res.next()) {
                TipoMuestra m = new TipoMuestra();

                m.setId_tipo_muestra(res.getInt("id_tipo_muestra"));
                m.setNombre(res.getString("nombre"));
                m.setDescripcion(res.getString("descripcion"));
                resultado.add(m);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener listado completo.");
        } finally {
            cerrarSilencioso(res);
            cerrarSilencioso(consulta);
            cerrarConexion();
            for (TipoMuestra m : resultado) {
                List<Analisis> analisis = obtenerTipoMuestraAnalisis(m.getId_tipo_muestra());
                m.setTipos_muestras_analisis(analisis);

            }
        }

        return resultado;
    }

    public List<Analisis> obtenerTipoMuestraAnalisis(int id_tipo_muestra) throws SIGIPROException {
        List<Analisis> resultado = new ArrayList<Analisis>();
        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {
            consulta = getConexion().prepareStatement(
                    "SELECT a.id_analisis, a.nombre as nombreanalisis, true "
                    + "FROM control_calidad.tipos_muestras as tm "
                    + "LEFT OUTER JOIN control_calidad.tipos_muestras_analisis as tma ON tma.id_tipo_muestra = tm.id_tipo_muestra "
                    + "INNER JOIN control_calidad.analisis as a ON a.id_analisis = tma.id_analisis "
                    + "WHERE tm.id_tipo_muestra = ? "
                    + "UNION ALL "
                    + "SELECT a.id_analisis, a.nombre as nombreanalisis, false "
                    + "FROM control_calidad.analisis as a "
                    + "WHERE a.id_analisis not in "
                    + "(SELECT an.id_analisis FROM control_calidad.analisis as an "
                    + "LEFT OUTER JOIN control_calidad.tipos_muestras_analisis as tma ON tma.id_analisis = an.id_analisis "
                    + "WHERE tma.id_tipo_muestra = ?)"
            );

            consulta.setInt(1, id_tipo_muestra);
            consulta.setInt(2, id_tipo_muestra);

            rs = consulta.executeQuery();

            while (rs.next()) {
                Analisis analisis = new Analisis();
                analisis.setId_analisis(rs.getInt("id_analisis"));
                analisis.setNombre(rs.getString("nombreanalisis"));
                analisis.setIsTipoMuestra(rs.getBoolean("bool"));
                resultado.add(analisis);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener listado completo.");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;

    }

    public boolean editarTipoDeMuestra(TipoMuestra m) throws SIGIPROException {
        boolean resultado = false;

        PreparedStatement consulta = null;

        try {
            consulta = getConexion().prepareStatement(
                    " UPDATE control_calidad.tipos_muestras"
                    + " SET nombre = ?, descripcion = ?"
                    + " WHERE id_tipo_muestra = ?;"
            );

            consulta.setString(1, m.getNombre());
            consulta.setString(2, m.getDescripcion());
            consulta.setInt(3, m.getId_tipo_muestra());

            resultado = consulta.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new SIGIPROException("No se pudo editar el tipo de muestra. Inténtelo nuevamente y de persistir el problema notifique al adminstrador del sistema.");
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;
    }

    public TipoMuestra obtenerTipoDeMuestra(int id_tipo_muestra) throws SIGIPROException {
        TipoMuestra resultado = new TipoMuestra();
        List<Analisis> tipos_muestras_analisis = new ArrayList<Analisis>();
        resultado.setTipos_muestras_analisis(tipos_muestras_analisis);
        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {
            consulta = getConexion().prepareStatement(
                    " SELECT tm.id_tipo_muestra, tm.nombre, tm.descripcion, a.id_analisis, a.nombre as nombreanalisis "
                    + "FROM control_calidad.tipos_muestras as tm LEFT OUTER JOIN control_calidad.tipos_muestras_analisis as tma ON tma.id_tipo_muestra = tm.id_tipo_muestra "
                    + "INNER JOIN control_calidad.analisis as analisis ON analisis.id_analisis = tma.id_analisis "
                    + "WHERE id_tipo_muestra = ?"
            );

            consulta.setInt(1, id_tipo_muestra);

            rs = consulta.executeQuery();

            while (rs.next()) {
                if (resultado.getId_tipo_muestra() == 0) {
                    resultado.setId_tipo_muestra(rs.getInt("id_tipo_muestra"));
                    resultado.setNombre(rs.getString("nombre"));
                    resultado.setDescripcion(rs.getString("descripcion"));
                }
                Analisis analisis = new Analisis();
                analisis.setId_analisis(rs.getInt("id_analisis"));
                analisis.setNombre(rs.getString("nombreanalisis"));
                resultado.getTipos_muestras_analisis().add(analisis);
            }

        } catch (SQLException ex) {
            throw new SIGIPROException("No se pudo obtener el tipo de muestra. Inténtelo nuevamente y de persistir el problema notifique al adminstrador del sistema.");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;
    }
}
