/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.Patron;
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
public class PatronDAO extends DAO {

    public boolean insertarPatron(Patron patron) throws SIGIPROException {

        boolean resultado = false;

        PreparedStatement insert_patron = null;
        ResultSet rs = null;

        try {
            insert_patron = getConexion().prepareStatement(
                    " INSERT INTO control_calidad.patrones "
                    + " (numero_lote, tipo, fecha_ingreso, fecha_vencimiento, fecha_inicio_uso, certificado, lugar_almacenamiento, condicion_almacenamiento, observaciones) "
                    + " VALUES (?,?,?,?,?,?,?,?,?) RETURNING id_patron;"
            );

            insert_patron.setString(1, patron.getNumero_lote());
            insert_patron.setString(2, patron.getTipo());
            insert_patron.setDate(3, patron.getFecha_ingreso());
            insert_patron.setDate(4, patron.getFecha_vencimiento());
            insert_patron.setDate(5, patron.getFecha_inicio_uso());
            insert_patron.setString(6, patron.getCertificado());
            insert_patron.setString(7, patron.getLugar_almacenamiento());
            insert_patron.setString(8, patron.getCondicion_almacenamiento());
            insert_patron.setString(9, patron.getObservaciones());

            rs = insert_patron.executeQuery();

            if (rs.next()) {
                resultado = true;
                patron.setId_patron(rs.getInt("id_patron"));
            }

        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
            throw new SIGIPROException("Error al insertar el patrón. Inténtelo nuevamente y de persistir notificar al administrador del sistema.");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(insert_patron);
            cerrarConexion();
        }

        return resultado;

    }

    public boolean editarPatron(Patron patron) throws SIGIPROException {

        boolean resultado = false;

        PreparedStatement update_patron = null;

        try {
            getConexion().setAutoCommit(false);
            boolean tiene_certificado = patron.tieneCertificado();

            String consulta
                    = " UPDATE control_calidad.patrones SET "
                    + "     numero_lote = ?, tipo = ?, fecha_ingreso = ?, fecha_vencimiento = ?, fecha_inicio_uso = ?, "
                    + "     lugar_almacenamiento = ?, condicion_almacenamiento = ?, observaciones = ? ";

            if (tiene_certificado) {
                consulta += ", certificado = ?";
            }

            consulta += " WHERE id_patron = ?;";

            update_patron = getConexion().prepareStatement(consulta);

            update_patron.setString(1, patron.getNumero_lote());
            update_patron.setString(2, patron.getTipo());
            update_patron.setDate(3, patron.getFecha_ingreso());
            update_patron.setDate(4, patron.getFecha_vencimiento());
            update_patron.setDate(5, patron.getFecha_inicio_uso());
            update_patron.setString(6, patron.getLugar_almacenamiento());
            update_patron.setString(7, patron.getCondicion_almacenamiento());
            update_patron.setString(8, patron.getObservaciones());

            if (tiene_certificado) {
                update_patron.setString(9, patron.getCertificado());
                update_patron.setInt(10, patron.getId_patron());
            } else {
                update_patron.setInt(9, patron.getId_patron());
            }

            int resultado_update = update_patron.executeUpdate();

            if (resultado_update == 1) {
                resultado = true;
            } else {
                resultado = false;
                throw new SIGIPROException("Error al editar el patrón. Inténtelo nuevamente y de persistir notificar al administrador del sistema.");
            }

        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
            throw new SIGIPROException("Error al insertar el patrón. Inténtelo nuevamente y de persistir notificar al administrador del sistema.");
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

            cerrarSilencioso(update_patron);
            cerrarConexion();
        }

        return resultado;

    }

    public List<Patron> obtenerPatrones() throws SIGIPROException {

        List<Patron> resultado = new ArrayList<Patron>();

        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {
            consulta = getConexion().prepareStatement(
                    " SELECT p.id_patron, p.numero_lote, p.tipo, p.fecha_vencimiento, p.lugar_almacenamiento "
                    + " FROM control_calidad.patrones p "
            );

            rs = consulta.executeQuery();

            while (rs.next()) {
                Patron p = new Patron();

                p.setId_patron(rs.getInt("id_patron"));
                p.setNumero_lote(rs.getString("numero_lote"));
                p.setTipo(rs.getString("tipo"));
                p.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                p.setLugar_almacenamiento(rs.getString("lugar_almacenamiento"));

                resultado.add(p);
            }

        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
            throw new SIGIPROException("Error de comunicación con la base de datos. Notifique al admiinstrador del sistema.");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;
    }

    public Patron obtenerPatron(int id_patron) throws SIGIPROException {

        Patron resultado = new Patron();

        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {
            consulta = getConexion().prepareStatement(
                    " SELECT p.id_patron, "
                    + "     p.numero_lote, "
                    + "     p.tipo, "
                    + "     p.fecha_vencimiento, "
                    + "     p.fecha_ingreso, "
                    + "     p.lugar_almacenamiento,"
                    + "     p.fecha_inicio_uso,"
                    + "     p.observaciones,"
                    + "     p.condicion_almacenamiento,"
                    + "     p.certificado"
                    + " FROM control_calidad.patrones p "
                    + " WHERE p.id_patron = ?;"
            );

            consulta.setInt(1, id_patron);
            rs = consulta.executeQuery();

            if (rs.next()) {
                resultado.setId_patron(rs.getInt("id_patron"));
                resultado.setNumero_lote(rs.getString("numero_lote"));
                resultado.setTipo(rs.getString("tipo"));
                resultado.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                resultado.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                resultado.setLugar_almacenamiento(rs.getString("lugar_almacenamiento"));
                resultado.setFecha_inicio_uso(rs.getDate("fecha_inicio_uso"));
                resultado.setObservaciones(rs.getString("observaciones"));
                resultado.setCondicion_almacenamiento(rs.getString("condicion_almacenamiento"));
                resultado.setCertificado(rs.getString("certificado"));
            } else {
                throw new SIGIPROException("El patrón que está intentando buscar no existe.");
            }

        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
            throw new SIGIPROException("Error de comunicación con la base de datos. Notifique al admiinstrador del sistema.");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;

    }

    public boolean eliminarPatron(int id_patron) throws SIGIPROException {

        boolean resultado = false;

        PreparedStatement consulta = null;

        try {
            
            getConexion().setAutoCommit(false);

            consulta = getConexion().prepareStatement(
                    " DELETE FROM control_calidad.patrones WHERE id_patron = ?;"
            );

            consulta.setInt(1, id_patron);

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }

        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
            String a = "prueba";
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

            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;
    }

}
