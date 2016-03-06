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
import com.icp.sigipro.seguridad.modelos.Usuario;
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
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.lote (nombre,id_protocolo,estado,posicion_actual, aprobacion) "
                    + " VALUES (?,?,false,1,false) RETURNING id_lote");
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
    
    public int obtenerVersion(int id_historial)
    {
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT version FROM produccion.historial_respuesta_pxp WHERE id_historial=?; ");
            consulta.setInt(1, id_historial);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = rs.getInt("version");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean pasoActualAprobacion(int id_lote) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT pxp.requiere_ap "
                    + "FROM produccion.lote as l "
                    + "LEFT JOIN produccion.protocolo as p "
                    + "ON (l.id_protocolo = p.id_protocolo) "
                    + "LEFT JOIN produccion.paso_protocolo as pxp "
                    + "ON (p.id_protocolo = pxp.id_protocolo and p.version = pxp.version and l.posicion_actual = pxp.posicion) "
                    + "WHERE l.id_lote = ?; ");
            consulta.setInt(1, id_lote);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = rs.getBoolean("requiere_ap");
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
//ESTA ES DISTINTA QUE REPETIR, HAY QUE HACER OTRA PARA REPETIR

    public boolean insertarRespuesta(Respuesta_pxp respuesta) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            //Inserta la respuesta general
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.respuesta_pxp (id_lote, id_pxp, version) "
                    + " VALUES (?,?,1) RETURNING id_respuesta");
            consulta.setInt(1, respuesta.getLote().getId_lote());
            consulta.setInt(2, respuesta.getPaso().getId_pxp());
            rs = consulta.executeQuery();
            if (rs.next()) {
                respuesta.setId_respuesta(rs.getInt("id_respuesta"));
                //Inserta la version 1 de la respuesta
                consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_respuesta_pxp (id_respuesta, respuesta, version,id_usuario_realizar) "
                        + " VALUES (?,?,1,?) RETURNING id_historial");
                SQLXML xmlVal = getConexion().createSQLXML();
                xmlVal.setString(respuesta.getRespuestaString());
                consulta.setInt(1, respuesta.getId_respuesta());
                consulta.setSQLXML(2, xmlVal);
                consulta.setInt(3, respuesta.getUsuario_realizar().getId_usuario());
                rs = consulta.executeQuery();
                if (rs.next()) {
                    resultado = true;
                    respuesta.setId_historial(rs.getInt("id_historial"));
                    int cantidad_pasos = respuesta.getLote().getRespuestas().size();
                    int paso_siguiente = respuesta.getLote().getPosicion_actual() + 1;
                    System.out.println("Cantidad pasos: " + cantidad_pasos + " Paso siguiente: " + paso_siguiente);
                    boolean aprobacion = this.pasoActualAprobacion(respuesta.getLote().getId_lote());
                    if (aprobacion) {
                        //Si todavia faltan pasos y requiere aprobacion
                        consulta = getConexion().prepareStatement(" UPDATE produccion.lote "
                                + "SET aprobacion = true "
                                + "WHERE id_lote = ?; ");
                        consulta.setInt(1, respuesta.getLote().getId_lote());
                    } else {
                        if (cantidad_pasos < paso_siguiente) {
                            consulta = getConexion().prepareStatement(" UPDATE produccion.lote "
                                    + "SET estado = true "
                                    + "WHERE id_lote = ?; ");
                            consulta.setInt(1, respuesta.getLote().getId_lote());
                        } else {
                            consulta = getConexion().prepareStatement(" UPDATE produccion.lote "
                                    + "SET aprobacion = false, posicion_actual = ? "
                                    + "WHERE id_lote = ?; ");
                            consulta.setInt(1, paso_siguiente);
                            consulta.setInt(2, respuesta.getLote().getId_lote());
                        }
                    }
                    if (consulta.executeUpdate() == 1) {
                        resultado = true;
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

    public boolean repetirRespuesta(Respuesta_pxp respuesta) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            //Inserta la respuesta general
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_respuesta_pxp (id_respuesta, respuesta,id_usuario_realizar, version) "
                    + " VALUES (?,?,?,?) RETURNING id_historial");
            consulta.setInt(1, respuesta.getId_respuesta());
            SQLXML xmlVal = getConexion().createSQLXML();
            xmlVal.setString(respuesta.getRespuestaString());
            consulta.setSQLXML(2, xmlVal);
            consulta.setInt(3, respuesta.getUsuario_realizar().getId_usuario());
            consulta.setInt(4, respuesta.getVersion() + 1);
            rs = consulta.executeQuery();
            if (rs.next()) {
                respuesta.setId_historial(rs.getInt("id_historial"));
                resultado = true;
                //Inserta la version 1 de la respuesta
                consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_pxp "
                        + "SET version = ? "
                        + "WHERE id_respuesta = ?; ");
                consulta.setInt(1, respuesta.getVersion() + 1);
                consulta.setInt(2, respuesta.getId_respuesta());
                if (consulta.executeUpdate() == 1) {
                    resultado = true;
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

    public Respuesta_pxp obtenerRespuesta(int id_respuesta) {
        Respuesta_pxp resultado = new Respuesta_pxp();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT r.id_respuesta, r.version, r.id_lote, l.nombre as nombrelote,l.aprobacion, r.id_pxp, pxp.posicion, hp.nombre as nombrepaso,hp.estructura, hr.respuesta "
                    + "FROM produccion.respuesta_pxp as r "
                    + "LEFT JOIN produccion.lote as l ON (r.id_lote = l.id_lote) "
                    + "LEFT JOIN produccion.paso_protocolo as pxp ON (pxp.id_pxp = r.id_pxp) "
                    + "LEFT JOIN produccion.paso as p ON (p.id_paso = pxp.id_paso) "
                    + "LEFT JOIN produccion.historial_paso as hp ON (hp.id_paso = p.id_paso and hp.version = p.version) "
                    + "LEFT JOIN produccion.historial_respuesta_pxp as hr ON (hr.id_respuesta = r.id_respuesta AND hr.version = r.version) "
                    + "WHERE r.id_respuesta=?; ");
            consulta.setInt(1, id_respuesta);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_respuesta(rs.getInt("id_respuesta"));
                resultado.setVersion(rs.getInt("version"));
                Lote lote = new Lote();
                lote.setId_lote(rs.getInt("id_lote"));
                lote.setNombre(rs.getString("nombrelote"));
                lote.setAprobacion(rs.getBoolean("aprobacion"));
                resultado.setLote(lote);
                resultado.setRespuesta(rs.getSQLXML("respuesta"));
                Paso paso = new Paso();
                paso.setId_pxp(rs.getInt("id_pxp"));
                paso.setNombre(rs.getString("nombrepaso"));
                paso.setPosicion(rs.getInt("posicion"));
                paso.setEstructura(rs.getSQLXML("estructura"));
                resultado.setPaso(paso);

                consulta = getConexion().prepareStatement(" SELECT h.id_historial, h.version "
                        + "FROM produccion.historial_respuesta_pxp as h "
                        + "WHERE h.id_respuesta = ?; ");
                consulta.setInt(1, resultado.getId_respuesta());

                rs = consulta.executeQuery();
                resultado.setHistorial(new ArrayList<Respuesta_pxp>());
                while (rs.next()) {
                    Respuesta_pxp r = new Respuesta_pxp();
                    r.setId_historial(rs.getInt("id_historial"));
                    r.setVersion(rs.getInt("version"));
                    resultado.getHistorial().add(r);
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

    public Respuesta_pxp obtenerHistorial(int id_historial) {
        Respuesta_pxp resultado = new Respuesta_pxp();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT hr.id_respuesta, hr.version, r.id_lote, l.nombre as nombrelote,l.aprobacion, r.id_pxp, pxp.posicion, hp.nombre as nombrepaso,hp.estructura, hr.respuesta "
                    + "FROM produccion.historial_respuesta_pxp as hr "
                    + "LEFT JOIN produccion.respuesta_pxp as r ON (hr.id_respuesta = r.id_respuesta) "
                    + "LEFT JOIN produccion.lote as l ON (r.id_lote = l.id_lote) "
                    + "LEFT JOIN produccion.paso_protocolo as pxp ON (pxp.id_pxp = r.id_pxp) "
                    + "LEFT JOIN produccion.paso as p ON (p.id_paso = pxp.id_paso) "
                    + "LEFT JOIN produccion.historial_paso as hp ON (hp.id_paso = p.id_paso and hp.version = p.version) "
                    + "WHERE hr.id_historial=?");
            consulta.setInt(1, id_historial);
            System.out.println(consulta);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_respuesta(rs.getInt("id_respuesta"));
                resultado.setVersion(rs.getInt("version"));
                Lote lote = new Lote();
                lote.setId_lote(rs.getInt("id_lote"));
                lote.setNombre(rs.getString("nombrelote"));
                lote.setAprobacion(rs.getBoolean("aprobacion"));
                resultado.setLote(lote);
                resultado.setRespuesta(rs.getSQLXML("respuesta"));
                Paso paso = new Paso();
                paso.setId_pxp(rs.getInt("id_pxp"));
                paso.setNombre(rs.getString("nombrepaso"));
                paso.setPosicion(rs.getInt("posicion"));
                paso.setEstructura(rs.getSQLXML("estructura"));
                resultado.setPaso(paso);
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
    
    public boolean activarVersion(int version, int id_respuesta) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_pxp "
                    + "SET version=? "
                    + "WHERE id_respuesta= ?; ");
            consulta.setInt(1, version);
            consulta.setInt(2, id_respuesta);
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean aprobarPasoActual(int id_lote, int id_respuesta, int id_usuario, int posicion_actual) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.lote "
                    + "SET aprobacion = false, posicion_actual = ?, estado = ? "
                    + "WHERE id_lote=?; ");
            int paso_siguiente = posicion_actual + 1;
            System.out.println("Lote: " + id_lote);
            int cantidad_pasos = this.obtenerCantidadPasos(id_lote);
            System.out.println("Cantidad de pasos: " + cantidad_pasos + " Paso siguiente: " + paso_siguiente);
            if (cantidad_pasos < paso_siguiente) {
                consulta.setBoolean(2, true);
            } else {
                consulta.setBoolean(2, false);
            }
            consulta.setInt(1, paso_siguiente);
            consulta.setInt(3, id_lote);
            if (consulta.executeUpdate() == 1) {
                resultado = true;

                consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_pxp "
                        + "SET id_usuario_aprobar = ? "
                        + "WHERE id_respuesta = ?; ");
                consulta.setInt(1, id_usuario);
                consulta.setInt(2, id_respuesta);
                if (consulta.executeUpdate() == 1) {
                    resultado = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;

    }

    public int obtenerCantidadPasos(int id_lote) {
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT count(pxp.id_paso)  "
                    + "FROM produccion.lote as l "
                    + "LEFT JOIN produccion.protocolo as pro ON pro.id_protocolo = l.id_protocolo "
                    + "LEFT JOIN produccion.paso_protocolo as pxp ON (pxp.id_protocolo = pro.id_protocolo and pxp.version = pro.version) "
                    + "WHERE l.id_lote = ?; ");
            consulta.setInt(1, id_lote);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = rs.getInt("count");
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
            consulta = getConexion().prepareStatement(" SELECT l.id_lote, l.nombre as nombrelote, l.id_protocolo, l.aprobacion, h.nombre as nombreprotocolo , l.estado, l.posicion_actual, p.id_paso, hp.nombre as nombrepaso, r.id_respuesta "
                    + "FROM produccion.lote as l "
                    + "LEFT JOIN produccion.protocolo as pro ON l.id_protocolo = pro.id_protocolo "
                    + "LEFT JOIN produccion.historial_protocolo as h ON (h.id_protocolo = pro.id_protocolo and h.version = pro.version) "
                    + "LEFT JOIN produccion.paso_protocolo as pp ON (pp.id_protocolo = pro.id_protocolo and pro.version = pp.version and pp.posicion = l.posicion_actual) "
                    + "LEFT JOIN produccion.paso as p ON (pp.id_paso = p.id_paso) "
                    + "LEFT JOIN produccion.historial_paso as hp ON (hp.id_paso = p.id_paso and hp.version = p.version) "
                    + "LEFT JOIN produccion.respuesta_pxp as r ON (r.id_pxp = pp.id_pxp and r.id_lote = l.id_lote) "
                    + "WHERE (l.estado = false or l.aprobacion = true) ; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Lote lote = new Lote();
                lote.setId_lote(rs.getInt("id_lote"));
                lote.setNombre(rs.getString("nombrelote"));
                lote.setEstado(rs.getBoolean("estado"));
                lote.setAprobacion(rs.getBoolean("aprobacion"));
                lote.setPosicion_actual(rs.getInt("posicion_actual"));
                Protocolo protocolo = new Protocolo();
                protocolo.setId_protocolo(rs.getInt("id_protocolo"));
                protocolo.setNombre(rs.getString("nombreprotocolo"));
                lote.setProtocolo(protocolo);
                Paso paso = new Paso();
                paso.setId_paso(rs.getInt("id_paso"));
                paso.setNombre(rs.getString("nombrepaso"));
                lote.setPaso_actual(paso);
                try {
                    lote.setId_respuesta_actual(rs.getInt("id_respuesta"));
                } catch (Exception e) {
                    System.out.println("No tiene respuesta.");
                }
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

    public List<Lote> obtenerLotesHistorial() {
        List<Lote> resultado = new ArrayList<Lote>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT l.id_lote, l.nombre as nombrelote, l.id_protocolo, l.aprobacion, h.nombre as nombreprotocolo , l.estado, l.posicion_actual, p.id_paso, hp.nombre as nombrepaso, r.id_respuesta "
                    + "FROM produccion.lote as l "
                    + "LEFT JOIN produccion.protocolo as pro ON l.id_protocolo = pro.id_protocolo "
                    + "LEFT JOIN produccion.historial_protocolo as h ON (h.id_protocolo = pro.id_protocolo and h.version = pro.version) "
                    + "LEFT JOIN produccion.paso_protocolo as pp ON (pp.id_protocolo = pro.id_protocolo and pro.version = pp.version and pp.posicion = l.posicion_actual) "
                    + "LEFT JOIN produccion.paso as p ON (pp.id_paso = p.id_paso) "
                    + "LEFT JOIN produccion.historial_paso as hp ON (hp.id_paso = p.id_paso and hp.version = p.version) "
                    + "LEFT JOIN produccion.respuesta_pxp as r ON (r.id_pxp = pp.id_pxp and r.id_lote = l.id_lote) "
                    + "WHERE (l.estado = true and l.aprobacion = false) ; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Lote lote = new Lote();
                lote.setId_lote(rs.getInt("id_lote"));
                lote.setNombre(rs.getString("nombrelote"));
                lote.setEstado(rs.getBoolean("estado"));
                lote.setAprobacion(rs.getBoolean("aprobacion"));
                lote.setPosicion_actual(rs.getInt("posicion_actual"));
                Protocolo protocolo = new Protocolo();
                protocolo.setId_protocolo(rs.getInt("id_protocolo"));
                protocolo.setNombre(rs.getString("nombreprotocolo"));
                lote.setProtocolo(protocolo);
                Paso paso = new Paso();
                paso.setId_paso(rs.getInt("id_paso"));
                paso.setNombre(rs.getString("nombrepaso"));
                lote.setPaso_actual(paso);
                try {
                    lote.setId_respuesta_actual(rs.getInt("id_respuesta"));
                } catch (Exception e) {
                    System.out.println("No tiene respuesta.");
                }
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
            consulta = getConexion().prepareStatement(" SELECT l.id_lote, l.nombre as nombrelote,l.aprobacion, l.id_protocolo, h.nombre as nombreprotocolo,pro.version , l.estado, l.posicion_actual, p.id_paso, pp.id_pxp, hp.estructura, hp.nombre as nombrepaso, r.id_respuesta "
                    + "FROM produccion.lote as l "
                    + "LEFT JOIN produccion.protocolo as pro ON l.id_protocolo = pro.id_protocolo "
                    + "LEFT JOIN produccion.historial_protocolo as h ON (h.id_protocolo = pro.id_protocolo and h.version = pro.version) "
                    + "LEFT JOIN produccion.paso_protocolo as pp ON (pp.id_protocolo = pro.id_protocolo and pro.version = pp.version and pp.posicion = l.posicion_actual) "
                    + "LEFT JOIN produccion.paso as p ON (pp.id_paso = p.id_paso) "
                    + "LEFT JOIN produccion.historial_paso as hp ON (hp.id_paso = p.id_paso and hp.version = p.version) "
                    + "LEFT JOIN produccion.respuesta_pxp as r ON (r.id_pxp = pp.id_pxp and r.id_lote = l.id_lote) "
                    + "WHERE l.id_lote = ?; ");
            consulta.setInt(1, id_lote);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_lote(rs.getInt("id_lote"));
                resultado.setNombre(rs.getString("nombrelote"));
                resultado.setEstado(rs.getBoolean("estado"));
                resultado.setPosicion_actual(rs.getInt("posicion_actual"));
                resultado.setAprobacion(rs.getBoolean("aprobacion"));
                try {
                    resultado.setId_respuesta_actual(rs.getInt("id_respuesta"));
                } catch (Exception e) {

                }
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
                consulta = getConexion().prepareStatement("SELECT pxp.id_pxp, pxp.id_paso, pxp.posicion, pxp.requiere_ap, pxp.version as versionpxp, h.nombre, r.id_respuesta, r.id_usuario_aprobar, ua.nombre_completo as nombre_aprobar, hr.id_usuario_realizar, ur.nombre_completo as nombre_realizar, hr.version as versionr "
                        + "FROM produccion.paso_protocolo as pxp "
                        + "LEFT JOIN produccion.protocolo as pro ON (pro.id_protocolo = pxp.id_protocolo and pxp.version = pro.version) "
                        + "LEFT JOIN produccion.paso as p ON pxp.id_paso = p.id_paso "
                        + "LEFT JOIN produccion.historial_paso as h ON (h.id_paso = p.id_paso and h.version = p.version) "
                        + "LEFT JOIN produccion.lote as l ON (l.id_protocolo = pro.id_protocolo) "
                        + "LEFT JOIN produccion.respuesta_pxp as r ON (r.id_pxp = pxp.id_pxp and r.id_lote = l.id_lote) "
                        + "LEFT JOIN produccion.historial_respuesta_pxp as hr ON (r.id_respuesta = hr.id_respuesta AND r.version = hr.version) "
                        + "LEFT JOIN seguridad.usuarios as ur ON (ur.id_usuario = hr.id_usuario_realizar) "
                        + "LEFT JOIN seguridad.usuarios as ua ON (ua.id_usuario = r.id_usuario_aprobar) "
                        + "WHERE pxp.id_protocolo = ? and l.id_lote=? "
                        + "ORDER BY pxp.posicion;");
                consulta.setInt(1, protocolo.getId_protocolo());
                consulta.setInt(2, id_lote);
                System.out.println(consulta);
                rs = consulta.executeQuery();
                List<Respuesta_pxp> respuestas = new ArrayList<>();
                while (rs.next()) {
                    Respuesta_pxp respuesta = new Respuesta_pxp();
                    Paso paso = new Paso();
                    paso.setId_paso(rs.getInt("id_paso"));
                    paso.setPosicion(rs.getInt("posicion"));
                    paso.setNombre(rs.getString("nombre"));
                    paso.setRequiere_ap(rs.getBoolean("requiere_ap"));
                    paso.setVersion(rs.getInt("versionpxp"));
                    paso.setId_pxp(rs.getInt("id_pxp"));
                    respuesta.setPaso(paso);
                    try {
                        respuesta.setId_respuesta(rs.getInt("id_respuesta"));
                        respuesta.setVersion(rs.getInt("versionr"));
                        try {
                            Usuario usuario_aprobar = new Usuario();
                            usuario_aprobar.setId_usuario(rs.getInt("id_usuario_aprobar"));
                            usuario_aprobar.setNombre_completo(rs.getString("nombre_aprobar"));
                            respuesta.setUsuario_aprobar(usuario_aprobar);
                        } catch (Exception e) {

                        }
                        try {
                            Usuario usuario_realizar = new Usuario();
                            usuario_realizar.setId_usuario(rs.getInt("id_usuario_realizar"));
                            usuario_realizar.setNombre_completo(rs.getString("nombre_realizar"));
                            respuesta.setUsuario_realizar(usuario_realizar);
                        } catch (Exception e) {

                        }

                    } catch (Exception e) {

                    }

                    respuestas.add(respuesta);
                }
                resultado.setRespuestas(respuestas);
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
                    + "RIGHT JOIN produccion.protocolo as pro ON (pxp.id_protocolo = pro.id_protocolo AND pxp.version = pro.version) "
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