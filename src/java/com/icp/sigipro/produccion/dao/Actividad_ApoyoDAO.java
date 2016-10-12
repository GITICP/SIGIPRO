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
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.actividad_apoyo (version, aprobacion_calidad, aprobacion_direccion, aprobacion_regente, aprobacion_coordinador, aprobacion_gestion, requiere_ap, estado) "
                    + " VALUES (1,false, false, false,false,false,?,true) RETURNING id_actividad");
            consulta.setBoolean(1, actividad.isRequiere_ap());
            rs = consulta.executeQuery();
            if (rs.next()) {
                actividad.setId_actividad(rs.getInt("id_actividad"));
                consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_actividad_apoyo (id_actividad,version,estructura,nombre,id_categoria_aa,requiere_coordinacion,requiere_regencia) "
                        + " VALUES (?,1,?,?,?,?,?) RETURNING id_historial");
                SQLXML xmlVal = getConexion().createSQLXML();
                xmlVal.setString(actividad.getEstructuraString());
                consulta.setInt(1, actividad.getId_actividad());
                consulta.setSQLXML(2, xmlVal);
                consulta.setString(3, actividad.getNombre());
                consulta.setInt(4, actividad.getCategoria().getId_categoria_aa());
                consulta.setBoolean(5, actividad.isRequiere_coordinacion());
                consulta.setBoolean(6, actividad.isRequiere_regencia());
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

    public boolean editarActividad_Apoyo(Actividad_Apoyo actividad, int version) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_actividad_apoyo (id_actividad,version,estructura,nombre,id_categoria_aa,requiere_coordinacion,requiere_regencia) "
                    + " VALUES (?,?,?,?,?,?,?) RETURNING id_historial");
            SQLXML xmlVal = getConexion().createSQLXML();
            xmlVal.setString(actividad.getEstructuraString());
            consulta.setInt(1, actividad.getId_actividad());
            consulta.setInt(2, version);
            consulta.setSQLXML(3, xmlVal);
            consulta.setString(4, actividad.getNombre());
            consulta.setInt(5, actividad.getCategoria().getId_categoria_aa());
            consulta.setBoolean(6, actividad.isRequiere_coordinacion());
            consulta.setBoolean(7, actividad.isRequiere_regencia());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                actividad.setId_historial(rs.getInt("id_historial"));
                consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                        + "SET version = ?, aprobacion_calidad = false, aprobacion_regente = false, aprobacion_coordinador = false, aprobacion_direccion=false, aprobacion_gestion=false, requiere_ap = ?,version_anterior=?  "
                        + "WHERE id_actividad = ?; ");
                consulta.setInt(1, version);
                consulta.setBoolean(2, actividad.isRequiere_ap());
                if (actividad.isAprobacion_gestion()) {
                    consulta.setInt(3, actividad.getVersion());
                } else {
                    consulta.setInt(3, 0);
                }
                consulta.setInt(4, actividad.getId_actividad());
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
            consulta = getConexion().prepareStatement(" SELECT aa.*, haa.nombre as nombreaa,haa.requiere_coordinacion,haa.requiere_regencia, aa.version, c.id_categoria_aa, c.nombre as nombrec "
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
                actividad.setAprobacion_gestion(rs.getBoolean("aprobacion_gestion"));
                actividad.setRequiere_ap(rs.getBoolean("requiere_ap"));
                actividad.setEstado(rs.getBoolean("estado"));
                actividad.setNombre(rs.getString("nombreaa"));
                actividad.setVersion(rs.getInt("version"));
                actividad.setRequiere_coordinacion(rs.getBoolean("requiere_coordinacion"));
                actividad.setRequiere_regencia(rs.getBoolean("requiere_regencia"));
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

    public List<Actividad_Apoyo> obtenerActividades_Apoyo_Activas() {
        List<Actividad_Apoyo> resultado = new ArrayList<Actividad_Apoyo>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT aa.*, haa.nombre as nombreaa,haa.requiere_coordinacion,haa.requiere_regencia, aa.version, c.id_categoria_aa, c.nombre as nombrec "
                    + "FROM produccion.actividad_apoyo as aa "
                    + "LEFT JOIN produccion.historial_actividad_apoyo as haa ON (haa.id_actividad = aa.id_actividad AND haa.version = aa.version) "
                    + "LEFT JOIN produccion.categoria_aa as c ON (haa.id_categoria_aa = c.id_categoria_aa) "
                    + "WHERE aa.estado = true; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Actividad_Apoyo actividad = new Actividad_Apoyo();
                actividad.setId_actividad(rs.getInt("id_actividad"));
                actividad.setAprobacion_calidad(rs.getBoolean("aprobacion_calidad"));
                actividad.setAprobacion_coordinador(rs.getBoolean("aprobacion_coordinador"));
                actividad.setAprobacion_direccion(rs.getBoolean("aprobacion_direccion"));
                actividad.setAprobacion_regente(rs.getBoolean("aprobacion_regente"));
                actividad.setAprobacion_gestion(rs.getBoolean("aprobacion_gestion"));
                actividad.setRequiere_ap(rs.getBoolean("requiere_ap"));
                actividad.setEstado(rs.getBoolean("estado"));
                actividad.setNombre(rs.getString("nombreaa"));
                actividad.setVersion(rs.getInt("version"));
                actividad.setRequiere_coordinacion(rs.getBoolean("requiere_coordinacion"));
                actividad.setRequiere_regencia(rs.getBoolean("requiere_regencia"));
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
            consulta = getConexion().prepareStatement(" SELECT raa.id_respuesta,raa.version_usada, raa.estado, raa.version as versionr,raa.aprobacion_coordinacion,raa.aprobacion_regencia,hraa.id_usuario_realizar,hraa.respuesta, u.nombre_completo, hraa.fecha, hraa.nombre "
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
                respuesta.setVersion_usada(rs.getInt("version_usada"));
                respuesta.setNombre(rs.getString("nombre"));
                respuesta.setFecha(rs.getTimestamp("fecha"));
                respuesta.setAprobacion_coordinacion(rs.getBoolean("aprobacion_coordinacion"));
                respuesta.setAprobacion_regencia(rs.getBoolean("aprobacion_regencia"));
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
            consulta = getConexion().prepareStatement(" SELECT aa.*, haa.nombre as nombreaa,haa.requiere_coordinacion,haa.requiere_regencia, aa.version, c.id_categoria_aa, c.nombre as nombrec "
                    + "FROM produccion.actividad_apoyo as aa "
                    + "LEFT JOIN produccion.historial_actividad_apoyo as haa ON (haa.id_actividad = aa.id_actividad AND haa.version = aa.version) "
                    + "LEFT JOIN produccion.categoria_aa as c ON (haa.id_categoria_aa = c.id_categoria_aa)"
                    + "WHERE haa.id_categoria_aa = ? and aa.estado = true; ");
            consulta.setInt(1, id_categoria_aa);
            rs = consulta.executeQuery();
            while (rs.next()) {
                Actividad_Apoyo actividad = new Actividad_Apoyo();
                actividad.setId_actividad(rs.getInt("id_actividad"));
                actividad.setAprobacion_calidad(rs.getBoolean("aprobacion_calidad"));
                actividad.setAprobacion_coordinador(rs.getBoolean("aprobacion_coordinador"));
                actividad.setAprobacion_direccion(rs.getBoolean("aprobacion_direccion"));
                actividad.setAprobacion_regente(rs.getBoolean("aprobacion_regente"));
                actividad.setAprobacion_gestion(rs.getBoolean("aprobacion_gestion"));
                actividad.setRequiere_ap(rs.getBoolean("requiere_ap"));
                actividad.setNombre(rs.getString("nombreaa"));
                actividad.setEstado(rs.getBoolean("estado"));
                actividad.setVersion(rs.getInt("version"));
                actividad.setRequiere_coordinacion(rs.getBoolean("requiere_coordinacion"));
                actividad.setRequiere_regencia(rs.getBoolean("requiere_regencia"));
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

    public Actividad_Apoyo obtenerActividad_Apoyo(int id_actividad, int version) {
        Actividad_Apoyo resultado = new Actividad_Apoyo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT aa.*,haa.id_historial, haa.version as versionh, haa.nombre,haa.requiere_coordinacion,haa.requiere_regencia, haa.estructura, c.id_categoria_aa, c.nombre as nombrec   "
                    + "FROM produccion.historial_actividad_apoyo as haa "
                    + "LEFT JOIN produccion.actividad_apoyo as aa ON (haa.id_actividad = aa.id_actividad) "
                    + "LEFT JOIN produccion.categoria_aa as c ON (haa.id_categoria_aa = c.id_categoria_aa) "
                    + "WHERE haa.id_actividad = ? and haa.version = ?; ");
            consulta.setInt(1, id_actividad);
            consulta.setInt(2, version);
            System.out.println(consulta);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_actividad(rs.getInt("id_actividad"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setVersion(rs.getInt("versionh"));
                resultado.setId_historial(rs.getInt("id_historial"));
                resultado.setEstructura(rs.getSQLXML("estructura"));
                resultado.setAprobacion_calidad(rs.getBoolean("aprobacion_calidad"));
                resultado.setAprobacion_coordinador(rs.getBoolean("aprobacion_coordinador"));
                resultado.setAprobacion_direccion(rs.getBoolean("aprobacion_direccion"));
                resultado.setAprobacion_regente(rs.getBoolean("aprobacion_regente"));
                resultado.setAprobacion_gestion(rs.getBoolean("aprobacion_gestion"));
                resultado.setRequiere_ap(rs.getBoolean("requiere_ap"));
                resultado.setRequiere_coordinacion(rs.getBoolean("requiere_coordinacion"));
                resultado.setRequiere_regencia(rs.getBoolean("requiere_regencia"));
                resultado.setEstado(rs.getBoolean("estado"));
                resultado.setVersion_anterior(rs.getInt("version_anterior"));
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
            consulta = getConexion().prepareStatement(" SELECT h.id_actividad, h.nombre, h.version, h.estructura,h.requiere_coordinacion,h.requiere_regencia, c.id_categoria_aa, c.nombre as nombrec    "
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
                resultado.setRequiere_coordinacion(rs.getBoolean("requiere_coordinacion"));
                resultado.setRequiere_regencia(rs.getBoolean("requiere_regencia"));
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

    public boolean obtenerEstado(int id_actividad) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT aa.estado "
                    + "FROM produccion.actividad_apoyo as aa "
                    + "WHERE aa.id_actividad = ?; ");
            consulta.setInt(1, id_actividad);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = rs.getBoolean("estado");
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

    public int obtenerEstadoRespuesta(int id_respuesta) {
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT raa.estado "
                    + "FROM produccion.respuesta_aa as raa "
                    + "WHERE raa.id_respuesta = ?; ");
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

    public Actividad_Apoyo obtenerIdActividad(int id_respuesta) {
        Actividad_Apoyo resultado = new Actividad_Apoyo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT id_actividad, version_usada FROM produccion.respuesta_aa WHERE id_respuesta=?; ");
            consulta.setInt(1, id_respuesta);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_actividad(rs.getInt("id_actividad"));
                resultado.setVersion(rs.getInt("version_usada"));
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
                    + "SET version=?, aprobacion_calidad = false, aprobacion_regente = false, aprobacion_coordinador = false, aprobacion_direccion=false, aprobacion_gestion=false "
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

    public boolean retirarActividad_Apoyo(int id_actividad) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                    + "SET estado=false "
                    + "WHERE id_actividad= ?; ");
            consulta.setInt(1, id_actividad);
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

    public boolean incluirActividad_Apoyo(int id_actividad) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                    + "SET estado=true "
                    + "WHERE id_actividad= ?; ");
            consulta.setInt(1, id_actividad);
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
                resultado.setAprobacion_gestion(rs.getBoolean("aprobacion_gestion"));
                resultado.setVersion(rs.getInt("version"));
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
                    + "SET observaciones=?, aprobacion_calidad = false, aprobacion_regente = false, aprobacion_coordinador = false, aprobacion_direccion=false, aprobacion_gestion=false "
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
                case (5):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.actividad_apoyo "
                            + "SET aprobacion_gestion = true, observaciones='',version_anterior=0 "
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
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.respuesta_aa (id_actividad, version,estado,aprobacion_coordinacion,aprobacion_regencia,version_usada) "
                    + " VALUES (?,1,?,?,?,?) RETURNING id_respuesta");
            consulta.setInt(1, respuesta.getActividad().getId_actividad());
            //Realizado pero incompleto
            consulta.setInt(2, 2);
            if (respuesta.getActividad().isRequiere_coordinacion()) {
                consulta.setBoolean(3, false);
            } else {
                consulta.setBoolean(3, true);
            }
            if (respuesta.getActividad().isRequiere_regencia()) {
                consulta.setBoolean(4, false);
            } else {
                consulta.setBoolean(4, true);
            }
            consulta.setInt(5, respuesta.getActividad().getVersion());
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

    public boolean repetirRespuesta(Respuesta_AA respuesta, int version) {
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
            consulta.setInt(4, version);
            consulta.setString(5, respuesta.getNombre());
            consulta.setTimestamp(6, respuesta.getFecha());
            rs = consulta.executeQuery();
            if (rs.next()) {
                respuesta.setId_historial(rs.getInt("id_historial"));
                resultado = true;
                //Inserta la version 1 de la respuesta
                consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_aa "
                        + "SET version = ?, estado=?,aprobacion_coordinacion=?,aprobacion_regencia=? "
                        + "WHERE id_respuesta = ?; ");
                consulta.setInt(1, version);
                //Realizado pero incompleto
                consulta.setInt(2, 2);
                if (respuesta.getActividad().isRequiere_coordinacion()) {
                    consulta.setBoolean(3, false);
                } else {
                    consulta.setBoolean(3, true);
                }
                if (respuesta.getActividad().isRequiere_regencia()) {
                    consulta.setBoolean(4, false);
                } else {
                    consulta.setBoolean(4, true);
                }
                consulta.setInt(5, respuesta.getId_respuesta());
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

    public boolean completarRespuesta(Respuesta_AA respuesta, int version) {
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
            consulta.setInt(4, version);
            consulta.setString(5, respuesta.getNombre());
            consulta.setTimestamp(6, respuesta.getFecha());
            rs = consulta.executeQuery();
            if (rs.next()) {
                respuesta.setId_historial(rs.getInt("id_historial"));
                resultado = true;
                //Inserta la version 1 de la respuesta
                consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_aa "
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

    public Respuesta_AA obtenerRespuesta(int id_respuesta) {
        Respuesta_AA resultado = new Respuesta_AA();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT r.id_respuesta,r.observaciones, r.version_usada, r.version, r.estado, r.id_actividad,r.aprobacion_coordinacion,r.aprobacion_regencia, hr.nombre,hr.respuesta, hr.id_usuario_realizar, u.nombre_completo as nombre_completo_realizar, hr.id_usuario_cerrar, ur.nombre_completo as nombre_completo_cerrar, hr.id_usuario_aprobar_coordinacion,hr.id_usuario_aprobar_regencia, uac.nombre_completo as nombre_completo_aprobar_coordinacion,uar.nombre_completo as nombre_completo_aprobar_regencia, hr.fecha "
                    + "FROM produccion.respuesta_aa as r "
                    + "LEFT JOIN produccion.historial_respuesta_aa as hr ON (hr.id_respuesta = r.id_respuesta AND hr.version = r.version) "
                    + "LEFT JOIN seguridad.usuarios as u ON (hr.id_usuario_realizar = u.id_usuario) "
                    + "LEFT JOIN seguridad.usuarios as ur ON (hr.id_usuario_cerrar = ur.id_usuario) "
                    + "LEFT JOIN seguridad.usuarios as uac ON (hr.id_usuario_aprobar_coordinacion = uac.id_usuario) "
                    + "LEFT JOIN seguridad.usuarios as uar ON (hr.id_usuario_aprobar_regencia = uar.id_usuario) "
                    + "WHERE r.id_respuesta=?; ");
            consulta.setInt(1, id_respuesta);
            System.out.println(consulta);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_respuesta(rs.getInt("id_respuesta"));
                resultado.setVersion(rs.getInt("version"));
                resultado.setVersion_usada(rs.getInt("version_usada"));
                resultado.setFecha(rs.getTimestamp("fecha"));
                resultado.setEstado(rs.getInt("estado"));
                resultado.setObservaciones(rs.getString("observaciones"));
                resultado.setAprobacion_coordinacion(rs.getBoolean("aprobacion_coordinacion"));
                resultado.setAprobacion_regencia(rs.getBoolean("aprobacion_regencia"));
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario_realizar"));
                usuario.setNombre_completo(rs.getString("nombre_completo_realizar"));
                resultado.setUsuario_realizar(usuario);
                resultado.setNombre(rs.getString("nombre"));
                resultado.setRespuesta(rs.getSQLXML("respuesta"));
                Actividad_Apoyo actividad = new Actividad_Apoyo();
                actividad.setId_actividad(rs.getInt("id_actividad"));
                actividad.setVersion(rs.getInt("version_usada"));
                resultado.setActividad(actividad);
                try {
                    Usuario usuario_revisar = new Usuario();
                    usuario_revisar.setId_usuario(rs.getInt("id_usuario_cerrar"));
                    usuario_revisar.setNombre_completo(rs.getString("nombre_completo_cerrar"));
                    resultado.setUsuario_cerrar(usuario_revisar);
                } catch (Exception e) {

                }
                try {
                    Usuario usuario_aprobar = new Usuario();
                    usuario_aprobar.setId_usuario(rs.getInt("id_usuario_aprobar_coordinacion"));
                    usuario_aprobar.setNombre_completo(rs.getString("nombre_completo_aprobar_coordinacion"));
                    resultado.setUsuario_aprobar_coordinacion(usuario_aprobar);
                } catch (Exception e) {

                }
                try {
                    Usuario usuario_aprobar = new Usuario();
                    usuario_aprobar.setId_usuario(rs.getInt("id_usuario_aprobar_regencia"));
                    usuario_aprobar.setNombre_completo(rs.getString("nombre_completo_aprobar_regencia"));
                    resultado.setUsuario_aprobar_regencia(usuario_aprobar);
                } catch (Exception e) {

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
            consulta = getConexion().prepareStatement(" SELECT r.id_respuesta, r.version, r.estado, r.id_actividad,r.version_usada hr.nombre,hr.respuesta, hr.id_usuario_realizar, u.nombre_completo, hr.fecha "
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
                resultado.setVersion_usada(rs.getInt("version_usada"));
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

    public boolean activarVersionRespuesta(int version, int id_respuesta, boolean requiere_coordinacion, boolean requiere_regencia) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_aa "
                    + "SET version=?,estado=?,aprobacion_coordinacion=?,aprobacion_regencia=?  "
                    + "WHERE id_respuesta= ?; ");
            consulta.setInt(1, version);
            consulta.setInt(2, 2);
            if (requiere_coordinacion) {
                consulta.setBoolean(3, false);
            } else {
                consulta.setBoolean(3, true);
            }
            if (requiere_regencia) {
                consulta.setBoolean(4, false);
            } else {
                consulta.setBoolean(4, true);
            }
            consulta.setInt(5, id_respuesta);
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

    public boolean cerrarRespuesta(Respuesta_AA respuesta) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_aa "
                    + "SET estado=? "
                    + "WHERE id_respuesta= ?; ");
            if (!respuesta.isAprobacion_coordinacion() || !respuesta.isAprobacion_regencia()) {
                consulta.setInt(1, 3);
            } else {
                consulta.setInt(1, 4);
            }
            consulta.setInt(2, respuesta.getId_respuesta());
            if (consulta.executeUpdate() == 1) {
                resultado = true;
                consulta = getConexion().prepareStatement(" UPDATE produccion.historial_respuesta_aa "
                        + "SET id_usuario_cerrar=? "
                        + "WHERE id_respuesta= ? and version=?; ");
                consulta.setInt(1, respuesta.getUsuario_cerrar().getId_usuario());
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

    //1 - Coordinacion, 2 - Regencia
    public boolean aprobarRespuesta(Respuesta_AA respuesta, int tipo) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            //Aprobacion de coordinacion
            if (tipo == 1) {
                consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_aa "
                        + "SET estado=?, aprobacion_coordinacion=true "
                        + "WHERE id_respuesta= ?; ");
                if (respuesta.isAprobacion_coordinacion() && respuesta.isAprobacion_regencia()) {
                    consulta.setInt(1, 4);
                } else {
                    consulta.setInt(1, 3);
                }
                consulta.setInt(2, respuesta.getId_respuesta());
            } else {
                consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_aa "
                        + "SET estado=?, aprobacion_regencia=true "
                        + "WHERE id_respuesta= ?; ");
                if (respuesta.isAprobacion_coordinacion() && respuesta.isAprobacion_regencia()) {
                    consulta.setInt(1, 4);
                } else {
                    consulta.setInt(1, 3);
                }
                consulta.setInt(2, respuesta.getId_respuesta());
            }
            if (consulta.executeUpdate() == 1) {
                resultado = true;
                if (tipo == 1) {
                    consulta = getConexion().prepareStatement(" UPDATE produccion.historial_respuesta_aa "
                            + "SET id_usuario_aprobar_coordinacion=? "
                            + "WHERE id_respuesta= ? and version=?; ");
                    consulta.setInt(1, respuesta.getUsuario_aprobar_coordinacion().getId_usuario());
                } else {
                    consulta = getConexion().prepareStatement(" UPDATE produccion.historial_respuesta_aa "
                            + "SET id_usuario_aprobar_regencia=? "
                            + "WHERE id_respuesta= ? and version=?; ");
                    consulta.setInt(1, respuesta.getUsuario_aprobar_regencia().getId_usuario());
                }
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

    //1 - Coordinacion, 2 - Regencia
    public boolean rechazarRespuesta(Respuesta_AA respuesta, int tipo, Actividad_Apoyo requisitos) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.respuesta_aa "
                    + "SET estado=?, aprobacion_coordinacion=?, aprobacion_regencia=?, observaciones=? "
                    + "WHERE id_respuesta= ?; ");
            consulta.setInt(1, 2);
            if (requisitos.isRequiere_coordinacion()) {
                consulta.setBoolean(2, false);
            } else {
                consulta.setBoolean(2, true);
            }
            if (requisitos.isRequiere_regencia()) {
                consulta.setBoolean(3, false);
            } else {
                consulta.setBoolean(3, true);
            }
            consulta.setString(4, respuesta.getObservaciones());
            consulta.setInt(5, respuesta.getId_respuesta());
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

    public int obtenerUltimaVersion(int id_actividad) {
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT version FROM produccion.historial_actividad_apoyo WHERE id_actividad=? ORDER BY version DESC LIMIT 1; ");
            consulta.setInt(1, id_actividad);
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

    public int obtenerUltimaVersionRespuesta(int id_respuesta) {
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT version FROM produccion.historial_respuesta_aa WHERE id_respuesta=? ORDER BY version DESC LIMIT 1; ");
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

    public Respuesta_AA obtenerAprobacionesRespuesta(int id_respuesta) {
        Respuesta_AA resultado = new Respuesta_AA();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT aprobacion_coordinacion, aprobacion_regencia FROM produccion.respuesta_aa WHERE id_respuesta=?; ");
            consulta.setInt(1, id_respuesta);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setAprobacion_coordinacion(rs.getBoolean("aprobacion_coordinacion"));
                resultado.setAprobacion_regencia(rs.getBoolean("aprobacion_regencia"));
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

    public int obtenerVersionUsada(int id_respuesta) {
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT version_usada FROM produccion.respuesta_aa WHERE id_respuesta=?; ");
            consulta.setInt(1, id_respuesta);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = rs.getInt("version_usada");
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

    public Actividad_Apoyo obtenerRequerimientosAprobacion(int id_respuesta) {
        Actividad_Apoyo resultado = new Actividad_Apoyo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT haa.requiere_coordinacion, haa.requiere_regencia "
                    + "FROM produccion.respuesta_aa as raa "
                    + "LEFT JOIN produccion.actividad_apoyo as aa ON (raa.id_actividad = aa.id_actividad) "
                    + "LEFT JOIN produccion.historial_actividad_apoyo as haa ON (haa.id_actividad = aa.id_actividad and haa.version = aa.version) "
                    + "WHERE raa.id_respuesta=?; ");
            consulta.setInt(1, id_respuesta);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setRequiere_coordinacion(rs.getBoolean("requiere_coordinacion"));
                resultado.setRequiere_regencia(rs.getBoolean("requiere_regencia"));
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
