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
import java.sql.SQLException;
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
        PreparedStatement consultaBatch = null;
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
                consulta = getConexion().prepareStatement(" SELECT p.id_paso, h.id_historial, h.nombre, pxp.id_pxp, pxp.requiere_ap, pxp.posicion "
                        + "FROM produccion.protocolo as pro "
                        + "LEFT JOIN produccion.paso_protocolo as pxp ON (pro.id_protocolo = pxp.id_protocolo AND pro.version = pxp.version) "
                        + "LEFT JOIN produccion.paso as p ON pxp.id_paso = p.id_paso "
                        + "LEFT JOIN produccion.historial_paso as h ON (h.id_paso = p.id_paso AND h.version = p.version) "
                        + "WHERE pro.id_protocolo = ? "
                        + "ORDER BY pxp.posicion; ");
                consulta.setInt(1, lote.getProtocolo().getId_protocolo());
                System.out.println(consulta);
                rs = consulta.executeQuery();
                boolean aprobacion = true;
                while (rs.next()) {
                    consultaBatch = getConexion().prepareStatement(" INSERT INTO produccion.respuesta_pxp (id_lote,id_pxp,version,estado, esultimo) "
                            + " VALUES (?,?,0,?,?); ");

                    int id_pxp = rs.getInt("id_pxp");
                    consultaBatch.setInt(1, lote.getId_lote());
                    consultaBatch.setInt(2, id_pxp);
                    Paso p = new Paso();
                    p.setRequiere_ap(rs.getBoolean("requiere_ap"));
                    if (aprobacion) {
                        consultaBatch.setInt(3, 3);
                        consultaBatch.setBoolean(4, false);
                        if (p.isRequiere_ap()) {
                            //Habilitado
                            aprobacion = false;
                            //Habilitado requiere aprobaciones
                            consultaBatch.setInt(3, 4);
                            consultaBatch.setBoolean(4, false);
                        }
                    } else if (p.isRequiere_ap()) {
                        //Deshabilitado  requiere aprobacion
                        consultaBatch.setInt(3, 2);
                        consultaBatch.setBoolean(4, false);
                    } else {
                        //Deshabilitado
                        consultaBatch.setInt(3, 1);
                        consultaBatch.setBoolean(4, false);
                    }
                    if (rs.isLast()) {
                        consultaBatch.setBoolean(4, true);
                    }
                    System.out.println(consultaBatch);
                    consultaBatch.executeUpdate();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarSilencioso(consultaBatch);
            cerrarConexion();
        }
        return resultado;
    }

    public int obtenerVersion(int id_historial) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean pasoActualAprobacion(int id_lote, int posicion) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT pxp.requiere_ap "
                    + "FROM produccion.lote as l "
                    + "LEFT JOIN produccion.protocolo as p "
                    + "ON (l.id_protocolo = p.id_protocolo) "
                    + "LEFT JOIN produccion.paso_protocolo as pxp "
                    + "ON (p.id_protocolo = pxp.id_protocolo and p.version = pxp.version) "
                    + "WHERE l.id_lote = ? and pxp.posicion = ?; ");
            consulta.setInt(1, id_lote);
            consulta.setInt(2, posicion);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = rs.getBoolean("requiere_ap");
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
//ESTA ES DISTINTA QUE REPETIR, HAY QUE HACER OTRA PARA REPETIR

    public boolean insertarRespuesta(Respuesta_pxp respuesta) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_respuesta_pxp (id_respuesta, respuesta, version,id_usuario_realizar, fecha) "
                    + " VALUES (?,?,1,?,?) RETURNING id_historial");
            SQLXML xmlVal = getConexion().createSQLXML();
            xmlVal.setString(respuesta.getRespuestaString());
            consulta.setInt(1, respuesta.getId_respuesta());
            consulta.setSQLXML(2, xmlVal);
            consulta.setInt(3, respuesta.getUsuario_realizar().getId_usuario());
            consulta.setTimestamp(4, respuesta.getFecha());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                respuesta.setId_historial(rs.getInt("id_historial"));
                consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_pxp "
                        + "SET version = 1 "
                        + "WHERE id_respuesta = ?; ");
                consulta.setInt(1, respuesta.getId_respuesta());
                if (consulta.executeUpdate() == 1) {
                    //Se pasa a un estado de incompleto
                    consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_pxp "
                            + "SET estado = 5 "
                            + "WHERE id_respuesta = ?; ");
                    consulta.setInt(1, respuesta.getId_respuesta());
                    if (consulta.executeUpdate() == 1) {
                        resultado = true;
                    }
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
            try {
                if (getConexion() != null) {
                    getConexion().rollback();
                    getConexion().setAutoCommit(true);

                }
            } catch (SQLException se2) {
                se2.printStackTrace();
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

    //Cambiar la habilitacion de pasos, por si hay pasos finales, entonces darles otro tratamiento, ademas de ponerle el numero debido
    public boolean habilitarPasos(List<Respuesta_pxp> respuestas, Respuesta_pxp respuesta) throws SQLException {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_pxp "
                    + "SET estado = ? "
                    + "WHERE id_respuesta = ?; ");

            boolean pasoactual = false;
            for (Respuesta_pxp r : respuestas) {
                //Se busca hasta llegar al paso actual
                if (r.getId_respuesta() == respuesta.getId_respuesta()) {
                    pasoactual = true;
                } else if (r.getPaso().isRequiere_ap()) {
                    if (pasoactual) {
                        //Habilitado requiere aprobaciones
                        consulta.setInt(1, 4);
                        consulta.setInt(2, r.getId_respuesta());
                        consulta.executeUpdate();
                        break;
                    }
                } else if (pasoactual) {
                    //Habilitado
                    consulta.setInt(1, 3);
                    consulta.setInt(2, r.getId_respuesta());
                    consulta.executeUpdate();
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

    public boolean editarRespuesta(Respuesta_pxp respuesta, int version) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            //Inserta la respuesta general
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_respuesta_pxp (id_respuesta, respuesta,id_usuario_realizar, version, fecha) "
                    + " VALUES (?,?,?,?,?) RETURNING id_historial");
            consulta.setInt(1, respuesta.getId_respuesta());
            SQLXML xmlVal = getConexion().createSQLXML();
            xmlVal.setString(respuesta.getRespuestaString());
            consulta.setSQLXML(2, xmlVal);
            consulta.setInt(3, respuesta.getUsuario_realizar().getId_usuario());
            consulta.setInt(4, version);
            consulta.setTimestamp(5, respuesta.getFecha());
            rs = consulta.executeQuery();
            if (rs.next()) {
                respuesta.setId_historial(rs.getInt("id_historial"));
                resultado = true;
                //Inserta la version 1 de la respuesta
                consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_pxp "
                        + "SET version = ? "
                        + "WHERE id_respuesta = ?; ");
                consulta.setInt(1, version);
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
            consulta = getConexion().prepareStatement(" SELECT r.id_respuesta, r.version, r.esultimo, r.id_lote, r.estado, l.nombre as nombrelote,l.aprobacion, r.id_pxp, pxp.posicion, pxp.requiere_ap, hp.nombre as nombrepaso,hp.estructura, hr.respuesta, hr.fecha, pro.id_protocolo, hpro.nombre as nombreprotocolo, "
                    + "hr.id_usuario_realizar, ur.nombre_completo as nombre_completo_realizar, hr.id_usuario_revisar, ure.nombre_completo as nombre_completo_revisar, hr.id_usuario_verificar, uv.nombre_completo as nombre_completo_verificar "
                    + "FROM produccion.respuesta_pxp as r "
                    + "LEFT JOIN produccion.lote as l ON (r.id_lote = l.id_lote) "
                    + "LEFT JOIN produccion.paso_protocolo as pxp ON (pxp.id_pxp = r.id_pxp) "
                    + "LEFT JOIN produccion.paso as p ON (p.id_paso = pxp.id_paso) "
                    + "LEFT JOIN produccion.historial_paso as hp ON (hp.id_paso = p.id_paso and hp.version = p.version) "
                    + "LEFT JOIN produccion.protocolo as pro ON (pro.id_protocolo = pxp.id_protocolo) "
                    + "LEFT JOIN produccion.historial_protocolo as hpro ON (hpro.id_protocolo = pro.id_protocolo and hpro.version = pro.version) "
                    + "LEFT JOIN produccion.historial_respuesta_pxp as hr ON (hr.id_respuesta = r.id_respuesta AND hr.version = r.version) "
                    + "LEFT JOIN seguridad.usuarios as ur ON (ur.id_usuario = hr.id_usuario_realizar) "
                    + "LEFT JOIN seguridad.usuarios as ure ON (ure.id_usuario = hr.id_usuario_revisar) "
                    + "LEFT JOIN seguridad.usuarios as uv ON (uv.id_usuario = hr.id_usuario_verificar) "
                    + "WHERE r.id_respuesta=?; ");
            consulta.setInt(1, id_respuesta);
            System.out.println(consulta);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_respuesta(rs.getInt("id_respuesta"));
                resultado.setVersion(rs.getInt("version"));
                resultado.setUltimo(rs.getBoolean("esultimo"));
                Lote lote = new Lote();
                lote.setId_lote(rs.getInt("id_lote"));
                lote.setNombre(rs.getString("nombrelote"));
                lote.setAprobacion(rs.getBoolean("aprobacion"));
                resultado.setLote(lote);
                resultado.setRespuesta(rs.getSQLXML("respuesta"));
                resultado.setEstado(rs.getInt("estado"));
                resultado.setFecha(rs.getTimestamp("fecha"));
                Paso paso = new Paso();
                paso.setId_pxp(rs.getInt("id_pxp"));
                paso.setNombre(rs.getString("nombrepaso"));
                paso.setPosicion(rs.getInt("posicion"));
                paso.setEstructura(rs.getSQLXML("estructura"));
                paso.setRequiere_ap(rs.getBoolean("requiere_ap"));
                resultado.setPaso(paso);
                Protocolo protocolo = new Protocolo();
                protocolo.setId_protocolo(rs.getInt("id_protocolo"));
                protocolo.setNombre(rs.getString("nombreprotocolo"));
                resultado.getLote().setProtocolo(protocolo);
                try {
                    Usuario usuario_realizar = new Usuario();
                    usuario_realizar.setId_usuario(rs.getInt("id_usuario_realizar"));
                    usuario_realizar.setNombre_completo(rs.getString("nombre_completo_realizar"));
                    resultado.setUsuario_realizar(usuario_realizar);
                } catch (Exception e) {

                }
                try {
                    Usuario usuario_revisar = new Usuario();
                    usuario_revisar.setId_usuario(rs.getInt("id_usuario_revisar"));
                    usuario_revisar.setNombre_completo(rs.getString("nombre_completo_revisar"));
                    resultado.setUsuario_revisar(usuario_revisar);
                } catch (Exception e) {

                }
                try {
                    Usuario usuario_verificar = new Usuario();
                    usuario_verificar.setId_usuario(rs.getInt("id_usuario_verificar"));
                    usuario_verificar.setNombre_completo(rs.getString("nombre_completo_verificar"));
                    resultado.setUsuario_verificar(usuario_verificar);
                } catch (Exception e) {

                }
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

    public List<Respuesta_pxp> obtenerRespuestas(Lote lote) {
        List<Respuesta_pxp> resultado = new ArrayList();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement("SELECT r.id_respuesta, r.estado, pxp.id_pxp, pxp.id_paso, pxp.posicion, pxp.requiere_ap, pxp.version as versionpxp "
                    + "FROM produccion.respuesta_pxp as r "
                    + "LEFT JOIN produccion.paso_protocolo as pxp ON (pxp.id_pxp = r.id_pxp) "
                    + "WHERE r.id_lote=? "
                    + "ORDER BY pxp.posicion;");
            consulta.setInt(1, lote.getId_lote());
            System.out.println(consulta);
            rs = consulta.executeQuery();
            while (rs.next()) {
                Respuesta_pxp respuesta = new Respuesta_pxp();
                Paso paso = new Paso();
                paso.setId_paso(rs.getInt("id_paso"));
                paso.setPosicion(rs.getInt("posicion"));
                paso.setRequiere_ap(rs.getBoolean("requiere_ap"));
                paso.setVersion(rs.getInt("versionpxp"));
                paso.setId_pxp(rs.getInt("id_pxp"));
                respuesta.setPaso(paso);
                respuesta.setId_respuesta(rs.getInt("id_respuesta"));
                respuesta.setEstado(rs.getInt("estado"));
                resultado.add(respuesta);
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
            consulta = getConexion().prepareStatement(" SELECT hr.id_respuesta, hr.version, r.id_lote, l.nombre as nombrelote,l.aprobacion, r.id_pxp, pxp.posicion, hp.nombre as nombrepaso,hp.estructura, hr.respuesta, "
                    + "hr.id_usuario_realizar, ur.nombre_completo as nombre_completo_realizar, hr.id_usuario_revisar, ure.nombre_completo as nombre_completo_revisar, hr.id_usuario_verificar, uv.nombre_completo as nombre_completo_verificar "
                    + "FROM produccion.historial_respuesta_pxp as hr "
                    + "LEFT JOIN produccion.respuesta_pxp as r ON (hr.id_respuesta = r.id_respuesta) "
                    + "LEFT JOIN produccion.lote as l ON (r.id_lote = l.id_lote) "
                    + "LEFT JOIN produccion.paso_protocolo as pxp ON (pxp.id_pxp = r.id_pxp) "
                    + "LEFT JOIN produccion.paso as p ON (p.id_paso = pxp.id_paso) "
                    + "LEFT JOIN produccion.historial_paso as hp ON (hp.id_paso = p.id_paso and hp.version = p.version) "
                    + "LEFT JOIN seguridad.usuarios as ur ON (ur.id_usuario = hr.id_usuario_realizar) "
                    + "LEFT JOIN seguridad.usuarios as ure ON (ure.id_usuario = hr.id_usuario_revisar) "
                    + "LEFT JOIN seguridad.usuarios as uv ON (uv.id_usuario = hr.id_usuario_verificar) "
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
                try {
                    Usuario usuario_realizar = new Usuario();
                    usuario_realizar.setId_usuario(rs.getInt("id_usuario_realizar"));
                    usuario_realizar.setNombre_completo(rs.getString("nombre_completo_realizar"));
                    resultado.setUsuario_realizar(usuario_realizar);
                } catch (Exception e) {

                }
                try {
                    Usuario usuario_revisar = new Usuario();
                    usuario_revisar.setId_usuario(rs.getInt("id_usuario_revisar"));
                    usuario_revisar.setNombre_completo(rs.getString("nombre_completo_revisar"));
                    resultado.setUsuario_revisar(usuario_revisar);
                } catch (Exception e) {

                }
                try {
                    Usuario usuario_verificar = new Usuario();
                    usuario_verificar.setId_usuario(rs.getInt("id_usuario_verificar"));
                    usuario_verificar.setNombre_completo(rs.getString("nombre_completo_verificar"));
                    resultado.setUsuario_verificar(usuario_verificar);
                } catch (Exception e) {

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

    public boolean revisarPaso(int id_respuesta, int id_usuario, int version) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_pxp "
                    + "SET estado=6 "
                    + "WHERE id_respuesta= ?; ");
            consulta.setInt(1, id_respuesta);
            if (consulta.executeUpdate() == 1) {
                resultado = true;
                consulta = getConexion().prepareStatement(" UPDATE produccion.historial_respuesta_pxp "
                        + "SET id_usuario_revisar = ? "
                        + "WHERE id_respuesta= ? and version=?; ");
                consulta.setInt(1, id_usuario);
                consulta.setInt(2, id_respuesta);
                consulta.setInt(3, version);
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
    
    public boolean cerrarPaso(int id_respuesta) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_pxp "
                    + "SET estado=8 "
                    + "WHERE id_respuesta= ?; ");
            consulta.setInt(1, id_respuesta);
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
    
    public boolean devolverPaso(int id_respuesta) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_pxp "
                    + "SET estado=5 "
                    + "WHERE id_respuesta= ?; ");
            consulta.setInt(1, id_respuesta);
            if (consulta.executeUpdate() == 1) {
                resultado = true;
                
                consulta = getConexion().prepareStatement(" UPDATE produccion.historial_respuesta_pxp "
                    + "SET id_usuario_revisar=null, id_usuario_verificar=null "
                    + "WHERE id_respuesta= ?; ");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
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
                    + "SET version=?, estado=5 "
                    + "WHERE id_respuesta= ?; ");
            consulta.setInt(1, version);
            consulta.setInt(2, id_respuesta);
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

    //Revisar que la posicion actual sea la posicion del paso
    public boolean verificarPaso(Respuesta_pxp respuesta, int id_usuario, int version) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.lote "
                    + "SET posicion_actual = ?, estado = ? "
                    + "WHERE id_lote=?; ");
            //Si el paso es el ultimo
            if (respuesta.isUltimo()) {
                consulta.setInt(1, respuesta.getPaso().getPosicion());
                consulta.setBoolean(2, true);
            } else {
                consulta.setInt(1, respuesta.getPaso().getPosicion() + 1);
                consulta.setBoolean(2, false);
            }
            consulta.setInt(3, respuesta.getLote().getId_lote());
            if (consulta.executeUpdate() == 1) {
                resultado = true;
                consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_pxp "
                        + "SET estado=7 "
                        + "WHERE id_respuesta = ?; ");
                consulta.setInt(1, respuesta.getId_respuesta());
                if (consulta.executeUpdate() == 1) {
                    resultado = true;
                    consulta = getConexion().prepareStatement(" UPDATE produccion.historial_respuesta_pxp "
                            + "SET id_usuario_verificar = ? "
                            + "WHERE id_respuesta= ? and version=?; ");
                    consulta.setInt(1, id_usuario);
                    consulta.setInt(2, respuesta.getId_respuesta());
                    consulta.setInt(3, version);
                    if (consulta.executeUpdate() == 1) {
                        resultado = true;
                    }
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
    
    public int obtenerEstado(int id_respuesta) {
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT r.estado  "
                    + "FROM produccion.respuesta_pxp as r "
                    + "WHERE r.id_respuesta = ?; ");
            consulta.setInt(1, id_respuesta);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = rs.getInt("estado");
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
    
    public int obtenerIdLote(int id_respuesta) {
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT r.id_lote  "
                    + "FROM produccion.respuesta_pxp as r "
                    + "WHERE r.id_respuesta = ?; ");
            consulta.setInt(1, id_respuesta);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = rs.getInt("id_lote");
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
            consulta = getConexion().prepareStatement(" SELECT l.id_lote, l.nombre as nombrelote, l.id_protocolo, h.nombre as nombreprotocolo , l.estado "
                    + "FROM produccion.lote as l "
                    + "LEFT JOIN produccion.protocolo as pro ON l.id_protocolo = pro.id_protocolo "
                    + "LEFT JOIN produccion.historial_protocolo as h ON (h.id_protocolo = pro.id_protocolo and h.version = pro.version) "
                    + "WHERE (l.estado = false or l.aprobacion = true) ; ");
            System.out.println(consulta);
            rs = consulta.executeQuery();
            while (rs.next()) {
                Lote lote = new Lote();
                lote.setId_lote(rs.getInt("id_lote"));
                lote.setNombre(rs.getString("nombrelote"));
                lote.setEstado(rs.getBoolean("estado"));
                Protocolo protocolo = new Protocolo();
                protocolo.setId_protocolo(rs.getInt("id_protocolo"));
                protocolo.setNombre(rs.getString("nombreprotocolo"));
                lote.setProtocolo(protocolo);
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
            consulta = getConexion().prepareStatement(" SELECT l.id_lote, l.nombre as nombrelote, l.id_protocolo, l.aprobacion, h.nombre as nombreprotocolo , l.estado, l.posicion_actual, p.id_paso, hp.nombre as nombrepaso, r.id_respuesta, "
                    + "l.fecha_vencimiento, l.id_usuario_distribucion, ul.nombre_completo "
                    + "FROM produccion.lote as l "
                    + "LEFT JOIN produccion.protocolo as pro ON l.id_protocolo = pro.id_protocolo "
                    + "LEFT JOIN produccion.historial_protocolo as h ON (h.id_protocolo = pro.id_protocolo and h.version = pro.version) "
                    + "LEFT JOIN produccion.paso_protocolo as pp ON (pp.id_protocolo = pro.id_protocolo and pro.version = pp.version and pp.posicion = l.posicion_actual) "
                    + "LEFT JOIN produccion.paso as p ON (pp.id_paso = p.id_paso) "
                    + "LEFT JOIN produccion.historial_paso as hp ON (hp.id_paso = p.id_paso and hp.version = p.version) "
                    + "LEFT JOIN produccion.respuesta_pxp as r ON (r.id_pxp = pp.id_pxp and r.id_lote = l.id_lote) "
                    + "LEFT JOIN seguridad.usuarios as ul ON (l.id_usuario_distribucion = ul.id_usuario) "
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
                try {
                    Usuario usuario_distribucion = new Usuario();
                    usuario_distribucion.setId_usuario(rs.getInt("id_usuario_distribucion"));
                    usuario_distribucion.setNombre_completo(rs.getString("nombre_completo"));
                    lote.setUsuario_distribucion(usuario_distribucion);
                } catch (Exception e) {

                }
                try {
                    lote.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                } catch (Exception e) {

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

    public List<Lote> obtenerLotesSimple() {
        List<Lote> resultado = new ArrayList<Lote>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT l.id_lote, l.nombre as nombrelote, l.id_protocolo, h.nombre as nombreprotocolo "
                    + "FROM produccion.lote as l "
                    + "LEFT JOIN produccion.protocolo as pro ON l.id_protocolo = pro.id_protocolo "
                    + "LEFT JOIN produccion.historial_protocolo as h ON (h.id_protocolo = pro.id_protocolo and h.version = pro.version) ");
            System.out.println(consulta);
            rs = consulta.executeQuery();
            while (rs.next()) {
                Lote lote = new Lote();
                lote.setId_lote(rs.getInt("id_lote"));
                lote.setNombre(rs.getString("nombrelote"));
                lote.setNombreProtocolo(rs.getString("nombreprotocolo"));
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

    public List<Lote> obtenerLotesEstado() {
        List<Lote> resultado = new ArrayList<Lote>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT l.id_lote, l.nombre as nombrelote,l.estado, l.id_protocolo, h.nombre as nombreprotocolo "
                    + "FROM produccion.lote as l "
                    + "LEFT JOIN produccion.protocolo as pro ON l.id_protocolo = pro.id_protocolo "
                    + "LEFT JOIN produccion.historial_protocolo as h ON (h.id_protocolo = pro.id_protocolo and h.version = pro.version) "
                    + "WHERE estado=false; ");
            System.out.println(consulta);
            rs = consulta.executeQuery();
            while (rs.next()) {
                Lote lote = new Lote();
                lote.setId_lote(rs.getInt("id_lote"));
                lote.setNombre(rs.getString("nombrelote"));
                Protocolo protocolo = new Protocolo();
                protocolo.setId_protocolo(rs.getInt("id_protocolo"));
                protocolo.setNombre(rs.getString("nombreprotocolo"));
                lote.setProtocolo(protocolo);
                lote.setEstado(rs.getBoolean("estado"));
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

    public List<Respuesta_pxp> obtenerRespuestasEstado(Lote lote) {
        List<Respuesta_pxp> resultado = new ArrayList();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement("SELECT r.id_respuesta, r.estado, pxp.id_pxp, pxp.id_paso, pxp.posicion, pxp.requiere_ap, pxp.version as versionpxp, hp.nombre "
                    + "FROM produccion.respuesta_pxp as r "
                    + "LEFT JOIN produccion.paso_protocolo as pxp ON (pxp.id_pxp = r.id_pxp) "
                    + "LEFT JOIN produccion.paso as p ON (pxp.id_paso = p.id_paso) "
                    + "LEFT JOIN produccion.historial_paso as hp on (hp.id_paso = p.id_paso and hp.version=p.version) "
                    + "WHERE r.id_lote=? "
                    + "ORDER BY pxp.posicion;");
            consulta.setInt(1, lote.getId_lote());
            System.out.println(consulta);
            rs = consulta.executeQuery();
            while (rs.next()) {
                Respuesta_pxp respuesta = new Respuesta_pxp();
                Paso paso = new Paso();
                paso.setId_paso(rs.getInt("id_paso"));
                paso.setPosicion(rs.getInt("posicion"));
                paso.setRequiere_ap(rs.getBoolean("requiere_ap"));
                paso.setVersion(rs.getInt("versionpxp"));
                paso.setId_pxp(rs.getInt("id_pxp"));
                paso.setNombre(rs.getString("nombre"));
                respuesta.setPaso(paso);
                respuesta.setId_respuesta(rs.getInt("id_respuesta"));
                respuesta.setEstado(rs.getInt("estado"));
                resultado.add(respuesta);
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

    public List<Lote> obtenerUltimosLotes() {
        List<Lote> resultado = new ArrayList<Lote>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT l.id_lote, l.nombre as nombrelote "
                    + "FROM produccion.lote as l "
                    + "ORDER BY l.id_lote DESC "
                    + "LIMIT 3; ");
            System.out.println(consulta);
            rs = consulta.executeQuery();
            while (rs.next()) {
                Lote lote = new Lote();
                lote.setId_lote(rs.getInt("id_lote"));
                lote.setNombre(rs.getString("nombrelote"));
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
            consulta = getConexion().prepareStatement(" SELECT l.id_lote, l.nombre as nombrelote,l.aprobacion, l.id_protocolo, h.nombre as nombreprotocolo,pro.version , l.estado, l.posicion_actual, p.id_paso, pp.id_pxp, hp.estructura, hp.nombre as nombrepaso, r.id_respuesta, "
                    + "l.fecha_vencimiento, l.id_usuario_distribucion, ul.nombre_completo "
                    + "FROM produccion.lote as l "
                    + "LEFT JOIN produccion.protocolo as pro ON l.id_protocolo = pro.id_protocolo "
                    + "LEFT JOIN produccion.historial_protocolo as h ON (h.id_protocolo = pro.id_protocolo and h.version = pro.version) "
                    + "LEFT JOIN produccion.paso_protocolo as pp ON (pp.id_protocolo = pro.id_protocolo and pro.version = pp.version and pp.posicion = l.posicion_actual) "
                    + "LEFT JOIN produccion.paso as p ON (pp.id_paso = p.id_paso) "
                    + "LEFT JOIN produccion.historial_paso as hp ON (hp.id_paso = p.id_paso and hp.version = p.version) "
                    + "LEFT JOIN produccion.respuesta_pxp as r ON (r.id_pxp = pp.id_pxp and r.id_lote = l.id_lote) "
                    + "LEFT JOIN seguridad.usuarios as ul ON (l.id_usuario_distribucion = ul.id_usuario) "
                    + "WHERE l.id_lote = ?; ");
            System.out.println(consulta);
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
                try {
                    Usuario usuario_distribucion = new Usuario();
                    usuario_distribucion.setId_usuario(rs.getInt("id_usuario_distribucion"));
                    usuario_distribucion.setNombre_completo(rs.getString("nombre_completo"));
                    resultado.setUsuario_distribucion(usuario_distribucion);
                } catch (Exception e) {

                }
                try {
                    resultado.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                } catch (Exception e) {

                }
                consulta = getConexion().prepareStatement("SELECT pxp.id_pxp, pxp.id_paso, pxp.posicion, pxp.requiere_ap, pxp.version as versionpxp, h.nombre, r.id_respuesta, hr.id_usuario_verificar, hr.id_usuario_revisar,hr.fecha, r.estado as estador, r.esultimo, uv.nombre_completo as nombre_verificar, ure.nombre_completo as nombre_revisar, hr.id_usuario_realizar, ur.nombre_completo as nombre_realizar, hr.version as versionr "
                        + "FROM produccion.paso_protocolo as pxp "
                        + "LEFT JOIN produccion.protocolo as pro ON (pro.id_protocolo = pxp.id_protocolo and pxp.version = pro.version) "
                        + "LEFT JOIN produccion.paso as p ON pxp.id_paso = p.id_paso "
                        + "LEFT JOIN produccion.historial_paso as h ON (h.id_paso = p.id_paso and h.version = p.version) "
                        + "LEFT JOIN produccion.lote as l ON (l.id_protocolo = pro.id_protocolo) "
                        + "LEFT JOIN produccion.respuesta_pxp as r ON (r.id_pxp = pxp.id_pxp and r.id_lote = l.id_lote) "
                        + "LEFT JOIN produccion.historial_respuesta_pxp as hr ON (r.id_respuesta = hr.id_respuesta AND r.version = hr.version) "
                        + "LEFT JOIN seguridad.usuarios as ur ON (ur.id_usuario = hr.id_usuario_realizar) "
                        + "LEFT JOIN seguridad.usuarios as uv ON (uv.id_usuario = hr.id_usuario_verificar) "
                        + "LEFT JOIN seguridad.usuarios as ure ON (ure.id_usuario = hr.id_usuario_revisar) "
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
                        respuesta.setEstado(rs.getInt("estador"));
                        respuesta.setUltimo(rs.getBoolean("esultimo"));
                        respuesta.setFecha(rs.getTimestamp("fecha"));
                        try {
                            Usuario usuario_verificar = new Usuario();
                            usuario_verificar.setId_usuario(rs.getInt("id_usuario_verificar"));
                            usuario_verificar.setNombre_completo(rs.getString("nombre_verificar"));
                            respuesta.setUsuario_verificar(usuario_verificar);
                        } catch (Exception e) {

                        }
                        try {
                            Usuario usuario_revisar = new Usuario();
                            usuario_revisar.setId_usuario(rs.getInt("id_usuario_revisar"));
                            usuario_revisar.setNombre_completo(rs.getString("nombre_revisar"));
                            respuesta.setUsuario_revisar(usuario_revisar);
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

    public boolean distribuirLote(int id_lote, int id_usuario) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.lote "
                    + "SET id_usuario_distribucion=? "
                    + "WHERE id_lote= ?; ");
            consulta.setInt(1, id_usuario);
            consulta.setInt(2, id_lote);
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

    public boolean vencimientoLote(Lote lote) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.lote "
                    + "SET fecha_vencimiento=? "
                    + "WHERE id_lote= ?; ");
            consulta.setDate(1, lote.getFecha_vencimiento());
            consulta.setInt(2, lote.getId_lote());
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

    public int obtenerUltimaVersionRespuesta(int id_respuesta) {
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT version FROM produccion.historial_respuesta_pxp WHERE id_respuesta=? ORDER BY version DESC LIMIT 1; ");
            consulta.setInt(1, id_respuesta);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = rs.getInt("version");
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
