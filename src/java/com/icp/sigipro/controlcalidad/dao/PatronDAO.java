/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.Patron;
import com.icp.sigipro.controlcalidad.modelos.TipoPatronControl;
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
                    + " (numero_lote, id_tipo_patroncontrol, fecha_ingreso, fecha_vencimiento, fecha_inicio_uso, certificado, "
                            + "lugar_almacenamiento, condicion_almacenamiento, observaciones) "
                    + " VALUES (?,?,?,?,?,?,?,?,?) RETURNING id_patron;"
            );

            insert_patron.setString(1, patron.getNumero_lote());
            insert_patron.setInt(2, patron.getTipo().getId_tipo_patroncontrol());
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
                    + "     numero_lote = ?, id_tipo_patroncontrol = ?, fecha_ingreso = ?, fecha_vencimiento = ?, fecha_inicio_uso = ?, "
                    + "     lugar_almacenamiento = ?, condicion_almacenamiento = ?, observaciones = ? ";

            if (tiene_certificado) {
                consulta += ", certificado = ?";
            }

            consulta += " WHERE id_patron = ?;";

            update_patron = getConexion().prepareStatement(consulta);

            update_patron.setString(1, patron.getNumero_lote());
            update_patron.setInt(2, patron.getTipo().getId_tipo_patroncontrol());
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
                    " SELECT p.id_patron, p.numero_lote, p.fecha_vencimiento, p.lugar_almacenamiento, tpc.id_tipo_patroncontrol, tpc.nombre, tpc.tipo "
                    + " FROM control_calidad.patrones p "
                    + "     INNER JOIN control_calidad.tipos_patronescontroles tpc ON tpc.id_tipo_patroncontrol = p.id_tipo_patroncontrol "
            );

            rs = consulta.executeQuery();

            while (rs.next()) {
                Patron p = new Patron();

                p.setId_patron(rs.getInt("id_patron"));
                p.setNumero_lote(rs.getString("numero_lote"));
                
                TipoPatronControl tpc = new TipoPatronControl();
                tpc.setTipo(rs.getString("tipo"));
                tpc.setId_tipo_patroncontrol(rs.getInt("id_tipo_patroncontrol"));
                tpc.setNombre(rs.getString("nombre"));
                
                p.setTipo(tpc);
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
                    + "     p.fecha_vencimiento, "
                    + "     p.fecha_ingreso, "
                    + "     p.lugar_almacenamiento,"
                    + "     p.fecha_inicio_uso,"
                    + "     p.observaciones,"
                    + "     p.condicion_almacenamiento,"
                    + "     p.certificado,"
                    + "     tpc.id_tipo_patroncontrol,"
                    + "     tpc.nombre,"
                    + "     tpc.tipo "
                    + " FROM control_calidad.patrones p "
                    + "     INNER JOIN  control_calidad.tipos_patronescontroles tpc ON p.id_tipo_patroncontrol = tpc.id_tipo_patroncontrol "
                    + " WHERE p.id_patron = ?;"
            );

            consulta.setInt(1, id_patron);
            rs = consulta.executeQuery();

            if (rs.next()) {
                resultado.setId_patron(rs.getInt("id_patron"));
                resultado.setNumero_lote(rs.getString("numero_lote"));
                
                TipoPatronControl tpc = new TipoPatronControl();
                tpc.setId_tipo_patroncontrol(rs.getInt("id_tipo_patroncontrol"));
                tpc.setNombre(rs.getString("nombre"));
                tpc.setTipo(rs.getString("tipo"));
                
                resultado.setTipo(tpc);
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
            if (sql_ex.getSQLState().equals("23503")) {
                throw new SIGIPROException("No se puede eliminar el patrón ya que se encuentra asociado a uno más resultados.");
            } else {
                sql_ex.printStackTrace();
                throw new SIGIPROException("Error de comunicación con la base de datos. Notifique al administrador del sistema.");
            }
        } finally {
            try {
                if (resultado) {
                    getConexion().commit();
                } else {
                    getConexion().rollback();
                }
            } catch (SQLException | NullPointerException sql_ex) {
                sql_ex.printStackTrace();
                throw new SIGIPROException("Error de comunicación con la base de datos. Notifique al administrador del sistema.");
            }

            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;
    }
    
    public List<List<Patron>> obtenerPatronesRealizarAnalisis() throws SIGIPROException {
        
        List<Patron> lista_patrones = new ArrayList<>();
        List<Patron> lista_controles = new ArrayList<>();
        
        List<List<Patron>> resultado = new ArrayList<>();
        
        resultado.add(lista_patrones);
        resultado.add(lista_controles);

        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {
            consulta = getConexion().prepareStatement(
                    " SELECT p.id_patron, p.numero_lote, tpc.nombre, tpc.id_tipo_patroncontrol, tpc.tipo "
                    + " FROM control_calidad.patrones p "
                    + "     INNER JOIN control_calidad.tipos_patronescontroles tpc ON tpc.id_tipo_patroncontrol = p.id_tipo_patroncontrol ;"
            );

            rs = consulta.executeQuery();

            while (rs.next()) {
                Patron p = new Patron();

                p.setId_patron(rs.getInt("id_patron"));
                p.setNumero_lote(rs.getString("numero_lote"));
                
                TipoPatronControl tpc = new TipoPatronControl();
                tpc.setId_tipo_patroncontrol(rs.getInt("id_tipo_patroncontrol"));
                tpc.setNombre(rs.getString("nombre"));
                tpc.setTipo(rs.getString("tipo"));
                p.setTipo(tpc);

                if (p.getTipo().getTipo().equalsIgnoreCase("Control")) {
                    lista_controles.add(p);
                } else {
                    lista_patrones.add(p);
                }
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

}
