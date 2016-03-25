/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.produccion.modelos.Categoria_AA;
import com.icp.sigipro.produccion.modelos.Actividad_Apoyo;
import com.icp.sigipro.produccion.modelos.Respuesta_AA;
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
public class Actividad_ApoyoDAO extends DAO {

    public boolean insertarActividad_Apoyo(Actividad_Apoyo actividad) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            getConexion().setAutoCommit(false);
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.actividad_apoyo (version, aprobacion_calidad, aprobacion_direccion, aprobacion_regente, aprobacion_coordinador, requiere_ap) "
                    + " VALUES (1,false, false, false,false,?) RETURNING id_actividad");
            consulta.setBoolean(1, actividad.isRequiere_ap());
            rs = consulta.executeQuery();
            if (rs.next()) {
                actividad.setId_actividad(rs.getInt("id_actividad"));
                consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_actividad_apoyo (id_actividad,version,estructura,nombre,id_categoria_aa) "
                        + " VALUES (?,1,?,?,?) RETURNING id_historial");
                SQLXML xmlVal = getConexion().createSQLXML();
                xmlVal.setString(actividad.getEstructuraString());
                consulta.setInt(1, actividad.getId_actividad());
                consulta.setSQLXML(2, xmlVal);
                consulta.setString(3, actividad.getNombre());
                consulta.setInt(4, actividad.getCategoria().getId_categoria_aa());
                rs = consulta.executeQuery();
                if (rs.next()) {
                    resultado = true;
                    actividad.setId_historial(rs.getInt("id_historial"));
                    getConexion().commit();
                    getConexion().setAutoCommit(true);
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

    public boolean editarActividad_Apoyo(Actividad_Apoyo actividad) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_actividad_apoyo (id_actividad,version,estructura,nombre,id_categoria_aa) "
                    + " VALUES (?,?,?,?,?) RETURNING id_historial");
            SQLXML xmlVal = getConexion().createSQLXML();
            xmlVal.setString(actividad.getEstructuraString());
            consulta.setInt(1, actividad.getId_actividad());
            consulta.setInt(2, actividad.getVersion() + 1);
            consulta.setSQLXML(3, xmlVal);
            consulta.setString(4, actividad.getNombre());
            consulta.setInt(5, actividad.getCategoria().getId_categoria_aa());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                actividad.setId_historial(rs.getInt("id_historial"));
                consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                        + "SET version = ?, aprobacion_calidad = false, aprobacion_regente = false, aprobacion_coordinador = false, aprobacion_direccion=false, requiere_ap = ?  "
                        + "WHERE id_actividad = ?; ");
                consulta.setInt(1, actividad.getVersion() + 1);
                consulta.setBoolean(2, actividad.isRequiere_ap());
                consulta.setInt(3, actividad.getId_actividad());
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

    public List<Actividad_Apoyo> obtenerActividades_Apoyo() {
        List<Actividad_Apoyo> resultado = new ArrayList<Actividad_Apoyo>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT aa.*, haa.nombre as nombreaa, aa.version, c.id_categoria_aa, c.nombre as nombrec "
                    + "FROM produccion.actividad_apoyo as aa "
                    + "LEFT JOIN produccion.historial_actividad_apoyo as haa ON (haa.id_actividad = aa.id_actividad AND haa.version = aa.version) "
                    + "LEFT JOIN produccion.categoria_aa as c ON (haa.id_categoria_aa = c.id_categoria_aa); ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Actividad_Apoyo actividad = new Actividad_Apoyo();
                actividad.setId_actividad(rs.getInt("id_actividad"));
                actividad.setAprobacion_calidad(rs.getBoolean("aprobacion_calidad"));
                actividad.setAprobacion_coordinador(rs.getBoolean("aprobacion_coordinador"));
                actividad.setAprobacion_direccion(rs.getBoolean("aprobacion_direccion"));
                actividad.setAprobacion_regente(rs.getBoolean("aprobacion_regente"));
                actividad.setRequiere_ap(rs.getBoolean("requiere_ap"));
                actividad.setNombre(rs.getString("nombreaa"));
                actividad.setVersion(rs.getInt("version"));
                Categoria_AA categoria = new Categoria_AA();
                categoria.setId_categoria_aa(rs.getInt("id_categoria_aa"));
                categoria.setNombre(rs.getString("nombrec"));
                actividad.setCategoria(categoria);
                resultado.add(actividad);
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

    public List<Respuesta_AA> obtenerRespuestas(Actividad_Apoyo actividad) {
        List<Respuesta_AA> resultado = new ArrayList<>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT raa.id_respuesta, raa.estado, raa.version as versionr,hraa.id_usuario_realizar,hraa.respuesta, u.nombre_completo, hraa.fecha, hraa.nombre "
                    + "FROM produccion.respuesta_aa as raa "
                    + "LEFT JOIN produccion.historial_respuesta_aa as hraa ON (hraa.id_respuesta = raa.id_respuesta and hraa.version = raa.version) "
                    + "LEFT JOIN seguridad.usuarios as u ON (u.id_usuario = hraa.id_usuario_realizar) "
                    + "WHERE raa.id_actividad = ?");
            System.out.println(consulta);
            consulta.setInt(1, actividad.getId_actividad());
            rs = consulta.executeQuery();
            while (rs.next()) {
                Respuesta_AA respuesta = new Respuesta_AA();
                respuesta.setId_respuesta(rs.getInt("id_respuesta"));
                respuesta.setRespuesta(rs.getSQLXML("respuesta"));
                respuesta.setVersion(rs.getInt("versionr"));
                respuesta.setEstado(rs.getInt("estado"));
                respuesta.setNombre(rs.getString("nombre"));
                respuesta.setFecha(rs.getTimestamp("fecha"));
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario_realizar"));
                usuario.setNombre_completo(rs.getString("nombre_completo"));
                respuesta.setUsuario_realizar(usuario);
                respuesta.setActividad(actividad);
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

    public List<Respuesta_AA> obtenerRespuestasAjax(int id_actividad) {
        List<Respuesta_AA> resultado = new ArrayList<>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT raa.id_respuesta, hraa.nombre, hraa.fecha, raa.estado "
                    + "FROM produccion.respuesta_aa as raa "
                    + "LEFT JOIN produccion.historial_respuesta_aa as hraa ON (hraa.id_respuesta = raa.id_respuesta and hraa.version = raa.version) "
                    + "LEFT JOIN seguridad.usuarios as u ON (u.id_usuario = hraa.id_usuario_realizar) "
                    + "WHERE raa.id_actividad = ? and raa.estado=4");
            System.out.println(consulta);
            consulta.setInt(1, id_actividad);
            rs = consulta.executeQuery();
            while (rs.next()) {
                Respuesta_AA respuesta = new Respuesta_AA();
                respuesta.setId_respuesta(rs.getInt("id_respuesta"));
                respuesta.setNombre(rs.getString("nombre"));
                respuesta.setFecha(rs.getTimestamp("fecha"));
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

    public List<Actividad_Apoyo> obtenerActividades_Apoyo(int id_categoria_aa) {
        List<Actividad_Apoyo> resultado = new ArrayList<Actividad_Apoyo>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT aa.*, haa.nombre as nombreaa, aa.version, c.id_categoria_aa, c.nombre as nombrec "
                    + "FROM produccion.actividad_apoyo as aa "
                    + "LEFT JOIN produccion.historial_actividad_apoyo as haa ON (haa.id_actividad = aa.id_actividad AND haa.version = aa.version) "
                    + "LEFT JOIN produccion.categoria_aa as c ON (haa.id_categoria_aa = c.id_categoria_aa)"
                    + "WHERE haa.id_categoria_aa = ?; ");
            consulta.setInt(1, id_categoria_aa);
            rs = consulta.executeQuery();
            while (rs.next()) {
                Actividad_Apoyo actividad = new Actividad_Apoyo();
                actividad.setId_actividad(rs.getInt("id_actividad"));
                actividad.setAprobacion_calidad(rs.getBoolean("aprobacion_calidad"));
                actividad.setAprobacion_coordinador(rs.getBoolean("aprobacion_coordinador"));
                actividad.setAprobacion_direccion(rs.getBoolean("aprobacion_direccion"));
                actividad.setAprobacion_regente(rs.getBoolean("aprobacion_regente"));
                actividad.setRequiere_ap(rs.getBoolean("requiere_ap"));
                actividad.setNombre(rs.getString("nombreaa"));
                actividad.setVersion(rs.getInt("version"));
                Categoria_AA categoria = new Categoria_AA();
                categoria.setId_categoria_aa(rs.getInt("id_categoria_aa"));
                categoria.setNombre(rs.getString("nombrec"));
                actividad.setCategoria(categoria);
                resultado.add(actividad);
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

    public Actividad_Apoyo obtenerActividad_Apoyo(int id_actividad) {
        Actividad_Apoyo resultado = new Actividad_Apoyo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT aa.*, haa.nombre, haa.estructura, c.id_categoria_aa, c.nombre as nombrec   "
                    + "FROM produccion.actividad_apoyo as aa "
                    + "LEFT JOIN produccion.historial_actividad_apoyo as haa ON (haa.id_actividad = aa.id_actividad AND haa.version = aa.version) "
                    + "LEFT JOIN produccion.categoria_aa as c ON (haa.id_categoria_aa = c.id_categoria_aa) "
                    + "WHERE aa.id_actividad = ?; ");
            consulta.setInt(1, id_actividad);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_actividad(rs.getInt("id_actividad"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setVersion(rs.getInt("version"));
                resultado.setEstructura(rs.getSQLXML("estructura"));
                resultado.setAprobacion_calidad(rs.getBoolean("aprobacion_calidad"));
                resultado.setAprobacion_coordinador(rs.getBoolean("aprobacion_coordinador"));
                resultado.setAprobacion_direccion(rs.getBoolean("aprobacion_direccion"));
                resultado.setAprobacion_regente(rs.getBoolean("aprobacion_regente"));
                resultado.setRequiere_ap(rs.getBoolean("requiere_ap"));
                resultado.setObservaciones(rs.getString("observaciones"));
                Categoria_AA categoria = new Categoria_AA();
                categoria.setId_categoria_aa(rs.getInt("id_categoria_aa"));
                categoria.setNombre(rs.getString("nombrec"));
                resultado.setCategoria(categoria);
                consulta = getConexion().prepareStatement(" SELECT h.id_historial, h.version "
                        + "FROM produccion.historial_actividad_apoyo as h "
                        + "WHERE h.id_actividad = ?; ");
                consulta.setInt(1, id_actividad);
                rs = consulta.executeQuery();
                resultado.setHistorial(new ArrayList<Actividad_Apoyo>());
                while (rs.next()) {
                    Actividad_Apoyo aa = new Actividad_Apoyo();
                    aa.setId_historial(rs.getInt("id_historial"));
                    aa.setVersion(rs.getInt("version"));
                    resultado.getHistorial().add(aa);
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

    public Actividad_Apoyo obtenerHistorial(int id_historial) {
        Actividad_Apoyo resultado = new Actividad_Apoyo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT h.id_actividad, h.nombre, h.version, h.estructura, c.id_categoria_aa, c.nombre as nombrec    "
                    + "FROM produccion.historial_actividad_apoyo as h "
                    + "LEFT JOIN produccion.categoria_aa as c ON (h.id_categoria_aa = c.id_categoria_aa) "
                    + "WHERE h.id_historial = ?; ");
            consulta.setInt(1, id_historial);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_actividad(rs.getInt("id_actividad"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setVersion(rs.getInt("version"));
                resultado.setEstructura(rs.getSQLXML("estructura"));
                Categoria_AA categoria = new Categoria_AA();
                categoria.setId_categoria_aa(rs.getInt("id_categoria_aa"));
                categoria.setNombre(rs.getString("nombrec"));
                resultado.setCategoria(categoria);

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

    public int obtenerVersion(int id_historial) {
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT version FROM produccion.historial_actividad_apoyo WHERE id_historial=?; ");
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

    public boolean activarVersion(int version, int id_actividad) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                    + "SET version=? "
                    + "WHERE id_actividad= ?; ");
            consulta.setInt(1, version);
            consulta.setInt(2, id_actividad);
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

    public boolean eliminarActividad_Apoyo(int id_actividad) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM produccion.actividad_apoyo WHERE id_actividad=?; ");
            consulta.setInt(1, id_actividad);
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

    public Actividad_Apoyo obtenerAprobaciones(int id_actividad) {
        Actividad_Apoyo resultado = new Actividad_Apoyo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.actividad_apoyo WHERE id_actividad=?; ");
            consulta.setInt(1, id_actividad);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_actividad(rs.getInt("id_actividad"));
                resultado.setAprobacion_calidad(rs.getBoolean("aprobacion_calidad"));
                resultado.setAprobacion_coordinador(rs.getBoolean("aprobacion_coordinador"));
                resultado.setAprobacion_direccion(rs.getBoolean("aprobacion_direccion"));
                resultado.setAprobacion_regente(rs.getBoolean("aprobacion_regente"));
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

    public boolean rechazarActividad_Apoyo(int id_actividad, String observaciones) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                    + "SET observaciones=?, aprobacion_calidad = false, aprobacion_regente = false, aprobacion_coordinador = false, aprobacion_direccion=false "
                    + " WHERE id_actividad=?; ");
            consulta.setString(1, observaciones);
            consulta.setInt(2, id_actividad);
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

    public boolean aprobarActividad_Apoyo(int id_actividad, int actor) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            switch (actor) {
                case (1):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                            + "SET aprobacion_calidad = true "
                            + "WHERE id_actividad=?; ");
                    break;
                case (2):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                            + "SET aprobacion_regente=true "
                            + "WHERE id_actividad=?; ");
                    break;
                case (3):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                            + "SET aprobacion_coordinador= true "
                            + "WHERE id_actividad=?; ");
                    break;
                case (4):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                            + "SET aprobacion_direccion = true, observaciones='' "
                            + "WHERE id_actividad=?; ");
                    break;
            }
            consulta.setInt(1, id_actividad);
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

    public int obtenerVersionRespuesta(int id_historial) {
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT version FROM produccion.historial_respuesta_aa WHERE id_historial=?; ");
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

    public boolean insertarRespuesta(Respuesta_AA respuesta) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            getConexion().setAutoCommit(false);
            //Inserta la respuesta general
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.respuesta_aa (id_actividad, version,estado) "
                    + " VALUES (?,1,?) RETURNING id_respuesta");
            consulta.setInt(1, respuesta.getActividad().getId_actividad());
            if (respuesta.getActividad().isRequiere_ap()) {
                //Realizado, requiere revision
                consulta.setInt(2, 2);
            } else {
                //Realizado, requiere aprobacion
                consulta.setInt(2, 3);
            }
            rs = consulta.executeQuery();
            if (rs.next()) {
                respuesta.setId_respuesta(rs.getInt("id_respuesta"));
                //Inserta la version 1 de la respuesta
                consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_respuesta_aa (id_respuesta, respuesta, version,id_usuario_realizar,nombre,fecha) "
                        + " VALUES (?,?,1,?,?,?) RETURNING id_historial");
                SQLXML xmlVal = getConexion().createSQLXML();
                xmlVal.setString(respuesta.getRespuestaString());
                consulta.setInt(1, respuesta.getId_respuesta());
                consulta.setSQLXML(2, xmlVal);
                consulta.setInt(3, respuesta.getUsuario_realizar().getId_usuario());
                consulta.setString(4, respuesta.getNombre());
                consulta.setTimestamp(5, respuesta.getFecha());
                rs = consulta.executeQuery();
                if (rs.next()) {
                    resultado = true;
                    respuesta.setId_historial(rs.getInt("id_historial"));
                    getConexion().commit();
                    getConexion().setAutoCommit(true);

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

    public boolean repetirRespuesta(Respuesta_AA respuesta) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            //Inserta la respuesta general
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_respuesta_aa (id_respuesta, respuesta,id_usuario_realizar, version, nombre, fecha) "
                    + " VALUES (?,?,?,?,?,?) RETURNING id_historial");
            consulta.setInt(1, respuesta.getId_respuesta());
            SQLXML xmlVal = getConexion().createSQLXML();
            xmlVal.setString(respuesta.getRespuestaString());
            consulta.setSQLXML(2, xmlVal);
            consulta.setInt(3, respuesta.getUsuario_realizar().getId_usuario());
            consulta.setInt(4, respuesta.getVersion() + 1);
            consulta.setString(5, respuesta.getNombre());
            consulta.setTimestamp(6, respuesta.getFecha());
            rs = consulta.executeQuery();
            if (rs.next()) {
                respuesta.setId_historial(rs.getInt("id_historial"));
                resultado = true;
                //Inserta la version 1 de la respuesta
                consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_aa "
                        + "SET version = ?, estado=? "
                        + "WHERE id_respuesta = ?; ");
                consulta.setInt(1, respuesta.getVersion() + 1);
                if (respuesta.getActividad().isRequiere_ap()) {
                    //Terminado
                    consulta.setInt(2, 4);
                } else {
                    //Realizado, requiere revision
                    consulta.setInt(2, 2);
                }
                consulta.setInt(3, respuesta.getId_respuesta());
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

    public Respuesta_AA obtenerRespuesta(int id_respuesta) {
        Respuesta_AA resultado = new Respuesta_AA();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT r.id_respuesta, r.version, r.estado, r.id_actividad, hr.nombre,hr.respuesta, hr.id_usuario_realizar, u.nombre_completo as nombre_completo_realizar, hr.id_usuario_revisar, ur.nombre_completo as nombre_completo_revisar, hr.id_usuario_aprobar, ua.nombre_completo as nombre_completo_aprobar, hr.fecha "
                    + "FROM produccion.respuesta_aa as r "
                    + "LEFT JOIN produccion.historial_respuesta_aa as hr ON (hr.id_respuesta = r.id_respuesta AND hr.version = r.version) "
                    + "LEFT JOIN seguridad.usuarios as u ON (hr.id_usuario_realizar = u.id_usuario) "
                    + "LEFT JOIN seguridad.usuarios as ur ON (hr.id_usuario_revisar = ur.id_usuario) "
                    + "LEFT JOIN seguridad.usuarios as ua ON (hr.id_usuario_aprobar = ua.id_usuario) "
                    + "WHERE r.id_respuesta=?; ");
            consulta.setInt(1, id_respuesta);
            System.out.println(consulta);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_respuesta(rs.getInt("id_respuesta"));
                resultado.setVersion(rs.getInt("version"));
                resultado.setFecha(rs.getTimestamp("fecha"));
                resultado.setEstado(rs.getInt("estado"));
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario_realizar"));
                usuario.setNombre_completo(rs.getString("nombre_completo_realizar"));
                resultado.setUsuario_realizar(usuario);
                resultado.setNombre(rs.getString("nombre"));
                resultado.setRespuesta(rs.getSQLXML("respuesta"));
                Actividad_Apoyo actividad = new Actividad_Apoyo();
                actividad.setId_actividad(rs.getInt("id_actividad"));
                resultado.setActividad(actividad);
                try{
                    Usuario usuario_revisar = new Usuario();
                    usuario_revisar.setId_usuario(rs.getInt("id_usuario_revisar"));
                    usuario_revisar.setNombre_completo(rs.getString("nombre_completo_revisar"));
                    resultado.setUsuario_revisar(usuario_revisar);
                }catch(Exception e){
                    
                }
                try{
                    Usuario usuario_aprobar = new Usuario();
                    usuario_aprobar.setId_usuario(rs.getInt("id_usuario_aprobar"));
                    usuario_aprobar.setNombre_completo(rs.getString("nombre_completo_aprobar"));
                    resultado.setUsuario_aprobar(usuario_aprobar);
                }catch(Exception e){
                    
                }

                consulta = getConexion().prepareStatement(" SELECT h.id_historial, h.version "
                        + "FROM produccion.historial_respuesta_aa as h "
                        + "WHERE h.id_respuesta = ?; ");
                consulta.setInt(1, resultado.getId_respuesta());

                rs = consulta.executeQuery();
                resultado.setHistorial(new ArrayList<Respuesta_AA>());
                while (rs.next()) {
                    Respuesta_AA r = new Respuesta_AA();
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

    public Respuesta_AA obtenerHistorialRespuesta(int id_historial) {
        Respuesta_AA resultado = new Respuesta_AA();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT r.id_respuesta, r.version, r.estado, r.id_actividad, hr.nombre,hr.respuesta, hr.id_usuario_realizar, u.nombre_completo, hr.fecha "
                    + "FROM produccion.historial_respuesta_aa as hr  "
                    + "LEFT JOIN produccion.respuesta_aa as r ON (hr.id_respuesta = r.id_respuesta) "
                    + "LEFT JOIN seguridad.usuarios as u ON (hr.id_usuario_realizar = u.id_usuario) "
                    + "WHERE hr.id_historial=?; ");
            consulta.setInt(1, id_historial);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_historial(id_historial);
                resultado.setId_respuesta(rs.getInt("id_respuesta"));
                resultado.setVersion(rs.getInt("version"));
                resultado.setEstado(rs.getInt("estado"));
                resultado.setFecha(rs.getTimestamp("fecha"));
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario_realizar"));
                usuario.setNombre_completo(rs.getString("nombre_completo"));
                resultado.setUsuario_realizar(usuario);
                resultado.setNombre(rs.getString("nombre"));
                resultado.setRespuesta(rs.getSQLXML("respuesta"));
                Actividad_Apoyo actividad = new Actividad_Apoyo();
                actividad.setId_actividad(rs.getInt("id_actividad"));
                resultado.setActividad(actividad);

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

    public boolean activarVersionRespuesta(int version, int id_respuesta) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_aa "
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

    public boolean revisarRespuesta(Respuesta_AA respuesta) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_aa "
                    + "SET estado=? "
                    + "WHERE id_respuesta= ?; ");
            consulta.setInt(1, 3);
            consulta.setInt(2, respuesta.getId_respuesta());
            if (consulta.executeUpdate() == 1) {
                resultado = true;
                consulta = getConexion().prepareStatement(" UPDATE produccion.historial_respuesta_aa "
                        + "SET id_usuario_revisar=? "
                        + "WHERE id_respuesta= ? and version=?; ");
                consulta.setInt(1, respuesta.getUsuario_revisar().getId_usuario());
                consulta.setInt(2, respuesta.getId_respuesta());
                consulta.setInt(3, respuesta.getVersion());
                if (consulta.executeUpdate() == 1) {
                    resultado = true;
                } else {
                    resultado = false;
                }
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

    public boolean aprobarRespuesta(Respuesta_AA respuesta) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_aa "
                    + "SET estado=? "
                    + "WHERE id_respuesta= ?; ");
            consulta.setInt(1, 4);
            consulta.setInt(2, respuesta.getId_respuesta());
            if (consulta.executeUpdate() == 1) {
                resultado = true;
                consulta = getConexion().prepareStatement(" UPDATE produccion.historial_respuesta_aa "
                        + "SET id_usuario_aprobar=? "
                        + "WHERE id_respuesta= ? and version=?; ");
                consulta.setInt(1, respuesta.getUsuario_aprobar().getId_usuario());
                consulta.setInt(2, respuesta.getId_respuesta());
                consulta.setInt(3, respuesta.getVersion());
                if (consulta.executeUpdate() == 1) {
                    resultado = true;
                } else {
                    resultado = false;
                }
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
}
