/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.produccion.modelos.Lote;
import com.icp.sigipro.produccion.modelos.Paso;
import com.icp.sigipro.produccion.modelos.Protocolo;
import com.icp.sigipro.produccion.modelos.Respuesta_pxp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLXML;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class LoteDAO extends DAO {

    public boolean insertarLote(Lote lote) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.lote (nombre,id_protocolo,estado,posicion_actual) "
                    + " VALUES (?,?,false,1) RETURNING id_lote");
            consulta.setString(1, lote.getNombre());
            consulta.setInt(2, lote.getProtocolo().getId_protocolo());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                lote.setId_lote(rs.getInt("id_lote"));
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean insertarRespuesta(Respuesta_pxp respuesta) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.respuesta_pxp (id_lote, id_pxp, version) "
                    + " VALUES (?,?,1) RETURNING id_respuesta");
            consulta.setInt(1, respuesta.getLote().getId_lote());
            consulta.setInt(2, respuesta.getPaso().getId_pxp());
            rs = consulta.executeQuery();
            if (rs.next()) {
                respuesta.setId_respuesta(rs.getInt("id_respuesta"));
                consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_respuesta_pxp (id_respuesta, respuesta, version) "
                        + " VALUES (?,?,1) RETURNING id_historial_respuesta_pxp");
                SQLXML xmlVal = getConexion().createSQLXML();
                xmlVal.setString(respuesta.getRespuestaString());
                consulta.setInt(1, respuesta.getId_respuesta());
                consulta.setSQLXML(2, xmlVal);
                rs = consulta.executeQuery();
                if (rs.next()) {
                    resultado = true;
                    respuesta.setId_historial(rs.getInt("id_historial_respuesta_pxp"));
                    int cantidad_pasos = respuesta.getLote().getProtocolo().getPasos().size();
                    int paso_siguiente = respuesta.getLote().getPosicion_actual() + 1;
                    System.out.println(cantidad_pasos);
                    System.out.println(paso_siguiente);
                    if (cantidad_pasos >= paso_siguiente) {
                        consulta = getConexion().prepareStatement(" UPDATE produccion.lote "
                                + "SET posicion_actual = ? "
                                + "WHERE id_lote = ?; ");
                        consulta.setInt(1, paso_siguiente);
                        consulta.setInt(2, respuesta.getLote().getId_lote());
                        if (consulta.executeUpdate() == 1) {
                            resultado = true;
                        }
                    } else {
                        consulta = getConexion().prepareStatement(" UPDATE produccion.lote "
                                + "SET estado = true "
                                + "WHERE id_lote = ?; ");
                        consulta.setInt(1, respuesta.getLote().getId_lote());
                        if (consulta.executeUpdate() == 1) {
                            resultado = true;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public List<Lote> obtenerLotes() {
        List<Lote> resultado = new ArrayList<Lote>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT l.id_lote, l.nombre as nombrelote, l.id_protocolo, h.nombre as nombreprotocolo , l.estado, l.posicion_actual, p.id_paso, hp.nombre as nombrepaso "
                    + "FROM produccion.lote as l "
                    + "LEFT JOIN produccion.protocolo as pro ON l.id_protocolo = pro.id_protocolo "
                    + "LEFT JOIN produccion.historial_protocolo as h ON (h.id_protocolo = pro.id_protocolo and h.version = pro.version) "
                    + "LEFT JOIN produccion.paso_protocolo as pp ON (pp.id_protocolo = pro.id_protocolo and pro.version = pp.version and pp.posicion = l.posicion_actual) "
                    + "LEFT JOIN produccion.paso as p ON (pp.id_paso = p.id_paso) "
                    + "LEFT JOIN produccion.historial_paso as hp ON (hp.id_paso = p.id_paso and hp.version = p.version) "
                    + "WHERE l.estado = false; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Lote lote = new Lote();
                lote.setId_lote(rs.getInt("id_lote"));
                lote.setNombre(rs.getString("nombrelote"));
                lote.setEstado(rs.getBoolean("estado"));
                lote.setPosicion_actual(rs.getInt("posicion_actual"));
                Protocolo protocolo = new Protocolo();
                protocolo.setId_protocolo(rs.getInt("id_protocolo"));
                protocolo.setNombre(rs.getString("nombreprotocolo"));
                lote.setProtocolo(protocolo);
                Paso paso = new Paso();
                paso.setId_paso(rs.getInt("id_paso"));
                paso.setNombre(rs.getString("nombrepaso"));
                lote.setPaso_actual(paso);
                resultado.add(lote);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public List<Lote> obtenerLotes(int id_protocolo) {
        List<Lote> resultado = new ArrayList<Lote>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement("  SELECT l.id_lote, l.nombre as nombrelote, l.id_protocolo, h.nombre as nombreprotocolo , l.estado, l.posicion_actual, p.id_paso, hp.nombre as nombrepaso "
                    + "FROM produccion.lote as l "
                    + "LEFT JOIN produccion.protocolo as pro ON l.id_protocolo = pro.id_protocolo "
                    + "LEFT JOIN produccion.historial_protocolo as h ON (h.id_protocolo = pro.id_protocolo and h.version = pro.version) "
                    + "LEFT JOIN produccion.paso_protocolo as pp ON (pp.id_protocolo = pro.id_protocolo and pro.version = pp.version and pp.posicion = l.posicion_actual) "
                    + "LEFT JOIN produccion.paso as p ON (pp.id_paso = p.id_paso) "
                    + "LEFT JOIN produccion.historial_paso as hp ON (hp.id_paso = p.id_paso and hp.version = p.version) "
                    + "WHERE l.id_protocolo = ?; ");
            consulta.setInt(1, id_protocolo);
            rs = consulta.executeQuery();
            while (rs.next()) {
                Lote lote = new Lote();
                lote.setId_lote(rs.getInt("id_lote"));
                lote.setNombre(rs.getString("nombrelote"));
                lote.setEstado(rs.getBoolean("estado"));
                lote.setPosicion_actual(rs.getInt("posicion_actual"));
                Protocolo protocolo = new Protocolo();
                protocolo.setId_protocolo(rs.getInt("id_protocolo"));
                protocolo.setNombre(rs.getString("nombreprotocolo"));
                lote.setProtocolo(protocolo);
                Paso paso = new Paso();
                paso.setId_paso(rs.getInt("id_paso"));
                paso.setNombre(rs.getString("nombrepaso"));
                lote.setPaso_actual(paso);
                resultado.add(lote);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public Lote obtenerLote(int id_lote) {
        Lote resultado = new Lote();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement("  SELECT l.id_lote, l.nombre as nombrelote, l.id_protocolo, h.nombre as nombreprotocolo,pro.version , l.estado, l.posicion_actual, p.id_paso, pp.id_pxp, hp.estructura, hp.nombre as nombrepaso "
                    + "FROM produccion.lote as l "
                    + "LEFT JOIN produccion.protocolo as pro ON l.id_protocolo = pro.id_protocolo "
                    + "LEFT JOIN produccion.historial_protocolo as h ON (h.id_protocolo = pro.id_protocolo and h.version = pro.version) "
                    + "LEFT JOIN produccion.paso_protocolo as pp ON (pp.id_protocolo = pro.id_protocolo and pro.version = pp.version and pp.posicion = l.posicion_actual) "
                    + "LEFT JOIN produccion.paso as p ON (pp.id_paso = p.id_paso) "
                    + "LEFT JOIN produccion.historial_paso as hp ON (hp.id_paso = p.id_paso and hp.version = p.version) "
                    + "WHERE l.id_lote = ?; ");
            consulta.setInt(1, id_lote);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_lote(rs.getInt("id_lote"));
                resultado.setNombre(rs.getString("nombrelote"));
                resultado.setEstado(rs.getBoolean("estado"));
                resultado.setPosicion_actual(rs.getInt("posicion_actual"));
                Protocolo protocolo = new Protocolo();
                protocolo.setId_protocolo(rs.getInt("id_protocolo"));
                protocolo.setNombre(rs.getString("nombreprotocolo"));
                protocolo.setVersion(rs.getInt("version"));
                Paso p = new Paso();
                p.setId_paso(rs.getInt("id_paso"));
                p.setNombre(rs.getString("nombrepaso"));
                p.setId_pxp(rs.getInt("id_pxp"));
                p.setEstructura(rs.getSQLXML("estructura"));
                resultado.setPaso_actual(p);
                consulta = getConexion().prepareStatement("SELECT pxp.id_pxp, pxp.id_paso, pxp.posicion, pxp.requiere_ap, pxp.version, h.nombre "
                        + "FROM produccion.paso_protocolo as pxp "
                        + "LEFT JOIN produccion.protocolo as pro ON (pro.id_protocolo = pxp.id_protocolo and pxp.version = pro.version) "
                        + "LEFT JOIN produccion.paso as p ON pxp.id_paso = p.id_paso "
                        + "LEFT JOIN produccion.historial_paso as h ON (h.id_paso = p.id_paso and h.version = p.version) "
                        + "WHERE pxp.id_protocolo = ?;");
                consulta.setInt(1, protocolo.getId_protocolo());
                rs = consulta.executeQuery();
                List<Paso> pasos = new ArrayList<Paso>();
                while (rs.next()) {
                    Paso paso = new Paso();
                    paso.setId_paso(rs.getInt("id_paso"));
                    paso.setPosicion(rs.getInt("posicion"));
                    paso.setNombre(rs.getString("nombre"));
                    paso.setRequiere_ap(rs.getBoolean("requiere_ap"));
                    paso.setVersion(rs.getInt("version"));
                    paso.setId_pxp(rs.getInt("id_pxp"));
                    pasos.add(paso);
                }
                protocolo.setPasos(pasos);
                resultado.setProtocolo(protocolo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean eliminarLote(int id_lote) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM produccion.lote WHERE id_lote=?; ");
            consulta.setInt(1, id_lote);
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public Paso obtenerPasoActual(int id_lote) {
        Paso resultado = new Paso();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT pxp.id_paso, h.nombre, h.estructura  "
                    + "FROM produccion.paso_protocolo as pxp "
                    + "LEFT JOIN produccion.paso as p ON pxp.id_paso = p.id_paso "
                    + "LEFT JOIN produccion.historial_paso as h ON (h.id_paso = p.id_paso and h.version = p.version) "
                    + "LEFT JOIN produccion.lote as l ON l.id_protocolo = pxp.id_protocolo "
                    + "WHERE pxp.posicion=l.posicion_actual and l.id_lote = ?; ");
            consulta.setInt(1, id_lote);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setEstructura(rs.getSQLXML("estructura"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setId_paso(rs.getInt("id_paso"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
}
