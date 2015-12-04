/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.produccion.modelos.Catalogo_PT;
import com.icp.sigipro.produccion.modelos.Formula_Maestra;
import com.icp.sigipro.produccion.modelos.Paso;
import com.icp.sigipro.produccion.modelos.Protocolo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class ProtocoloDAO extends DAO {

    public boolean insertarProtocolo(Protocolo protocolo) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.protocolo (version, aprobacion_calidad, aprobacion_direccion, aprobacion_regente, aprobacion_coordinador) "
                    + " VALUES (1,false, false, false,false) RETURNING id_protocolo");
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                protocolo.setId_protocolo(rs.getInt("id_protocolo"));
                consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_protocolo (id_protocolo, version, nombre, descripcion, id_formula_maestra, id_catalogo_pt) "
                        + " VALUES (?,1,?,?,?,?) RETURNING id_historial");

                consulta.setInt(1, protocolo.getId_protocolo());
                consulta.setString(2, protocolo.getNombre());
                consulta.setString(3, protocolo.getDescripcion());
                consulta.setInt(4, protocolo.getFormula_maestra().getId_formula_maestra());
                consulta.setInt(5, protocolo.getProducto().getId_catalogo_pt());
                rs = consulta.executeQuery();
                if (rs.next()) {
                    resultado = true;
                    protocolo.setId_historial(rs.getInt("id_historial"));
                    //Meter los pasos del protocolo

                }

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

    public boolean editarProtocolo(Protocolo protocolo) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_protocolo (id_protocolo, version, nombre, descripcion, id_formula_maestra, id_catalogo_pt) "
                    + " VALUES (?,?,?,?,?,?) RETURNING id_historial");
            consulta.setInt(1, protocolo.getId_protocolo());
            consulta.setInt(2, protocolo.getVersion() + 1);
            consulta.setString(3, protocolo.getNombre());
            consulta.setString(4, protocolo.getDescripcion());
            consulta.setInt(5, protocolo.getFormula_maestra().getId_formula_maestra());
            consulta.setInt(6, protocolo.getProducto().getId_catalogo_pt());
            if (consulta.executeUpdate() == 1) {
                resultado = true;
                consulta = getConexion().prepareStatement(" UPDATE produccion.protocolo "
                        + "SET version=?, aprobacion_calidad = false, aprobacion_regente = false, aprobacion_coordinador = false, aprobacion_direccion=false  "
                        + "WHERE id_protocolo = ?; ");

                consulta.setInt(1, protocolo.getVersion() + 1);
                consulta.setInt(2, protocolo.getId_protocolo());
                if (consulta.executeUpdate() == 1) {
                    //Meter los pasos nuevos

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

    //ActivarProtocolo
    //Aprobaciones
    //Desaprobar
    public List<Protocolo> obtenerProtocolos() {
        List<Protocolo> resultado = new ArrayList<Protocolo>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT pro.*, h.*, pt.id_catalogo_pt, pt.nombre as nombrept "
                    + "FROM produccion.protocolo as pro "
                    + "LEFT JOIN produccion.historial_protocolo as h ON (pro.id_protocolo = h.id_protocolo AND pro.version = h.version)"
                    + "LEFT JOIN produccion.catalogo_pt as pt ON h.id_catalogo_pt = pt.id_catalogo_pt; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Protocolo protocolo = new Protocolo();
                protocolo.setId_protocolo(rs.getInt("id_protocolo"));
                protocolo.setNombre(rs.getString("nombre"));
                protocolo.setDescripcion(rs.getString("descripcion"));
                protocolo.setAprobacion_calidad(rs.getBoolean("aprobacion_calidad"));
                protocolo.setAprobacion_coordinador(rs.getBoolean("aprobacion_coordinador"));
                protocolo.setAprobacion_direccion(rs.getBoolean("aprobacion_direccion"));
                protocolo.setAprobacion_regente(rs.getBoolean("aprobacion_regente"));
                protocolo.setVersion(rs.getInt("version"));
                Catalogo_PT pt = new Catalogo_PT();
                pt.setId_catalogo_pt(rs.getInt("id_catalogo_pt"));
                pt.setNombre(rs.getString("nombrept"));
                protocolo.setProducto(pt);
                resultado.add(protocolo);
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

    //Cada protocolo muestra la info del mismo, una tabla de versiones y una tabla de pasos del protocolo
    public Protocolo obtenerProtocolo(int id_protocolo) {
        Protocolo resultado = new Protocolo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT pro.*, h.*, pt.id_catalogo_pt, pt.nombre as nombrept, fm.id_formula_maestra, fm.nombre as nombrefm "
                    + "FROM produccion.protocolo as pro "
                    + "LEFT JOIN produccion.historial_protocolo as h ON (pro.id_protocolo = h.id_protocolo AND pro.version = h.version) "
                    + "LEFT JOIN produccion.paso_protocolo as pxp ON (pxp.id_protocolo = pro.id_protocolo AND pxp.version = pro.version) "
                    + "LEFT JOIN produccion.catalogo_pt as pt ON pt.id_catalogo_pt = h.id_catalogo_pt "
                    + "LEFT JOIN produccion.formula_maestra as fm ON fm.id_formula_maestra = h.id_formula_maestra "
                    + "WHERE pro.id_protocolo = ?; ");
            consulta.setInt(1, id_protocolo);
            rs = consulta.executeQuery();
            if (rs.next()) {
                Protocolo protocolo = new Protocolo();
                protocolo.setId_protocolo(rs.getInt("id_protocolo"));
                protocolo.setNombre(rs.getString("nombre"));
                protocolo.setDescripcion(rs.getString("descripcion"));
                protocolo.setAprobacion_calidad(rs.getBoolean("aprobacion_calidad"));
                protocolo.setAprobacion_coordinador(rs.getBoolean("aprobacion_coordinador"));
                protocolo.setAprobacion_direccion(rs.getBoolean("aprobacion_direccion"));
                protocolo.setAprobacion_regente(rs.getBoolean("aprobacion_regente"));
                protocolo.setVersion(rs.getInt("version"));
                Catalogo_PT pt = new Catalogo_PT();
                pt.setId_catalogo_pt(rs.getInt("id_catalogo_pt"));
                pt.setNombre(rs.getString("nombrept"));
                protocolo.setProducto(pt);
                Formula_Maestra fm = new Formula_Maestra();
                fm.setId_formula_maestra(rs.getInt("id_formula_maestra"));
                fm.setNombre(rs.getString("nombrefm"));
                protocolo.setFormula_maestra(fm);

                consulta = getConexion().prepareStatement(" SELECT h.id_historial, h.version "
                        + "FROM produccion.historial_protocolo as h "
                        + "WHERE h.id_protocolo = ?; ");
                consulta.setInt(1, id_protocolo);

                rs = consulta.executeQuery();
                protocolo.setHistorial(new ArrayList<Protocolo>());
                while (rs.next()) {
                    Protocolo p = new Protocolo();
                    p.setId_historial(rs.getInt("id_historial"));
                    p.setVersion(rs.getInt("version"));
                    protocolo.getHistorial().add(p);
                }

                consulta = getConexion().prepareStatement(" SELECT h.id_historial, h.nombre, h.requiere_ap, pxp.posicion "
                        + "FROM produccion.protocolo as pro "
                        + "LEFT JOIN produccion.paso_protocolo as pxp ON (pro.id_protocolo = pxp.id_protocolo AND pro.version = pxp.version) "
                        + "LEFT JOIN produccion.paso as p ON pxp.id_paso = p.id_paso "
                        + "LEFT JOIN produccion.historial_paso as h ON (h.id_paso = p.id_paso AND h.version = p.version) "
                        + "WHERE pro.id_protocolo = ? "
                        + "ORDER BY pxp.posicion; ");
                consulta.setInt(1, id_protocolo);
                rs = consulta.executeQuery();
                protocolo.setPasos(new ArrayList<Paso>());
                while (rs.next()) {
                    Paso p = new Paso();
                    p.setId_historial(rs.getInt("id_historial"));
                    p.setNombre(rs.getString("nombre"));
                    p.setRequiere_ap(rs.getBoolean("requiere_ap"));
                    p.setPosicion(rs.getInt("posicion"));
                    protocolo.getPasos().add(p);
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

    public Protocolo obtenerAprobaciones(int id_protocolo) {
        Protocolo resultado = new Protocolo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.protocolo WHERE id_protocolo=?; ");
            consulta.setInt(1, id_protocolo);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_protocolo(rs.getInt("id_protocolo"));
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

    public boolean eliminarProtocolo(int id_protocolo) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" DELETE FROM produccion.protocolo WHERE id_protocolo=?; ");
            consulta.setInt(1, id_protocolo);
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

    public boolean rechazarProtocolo(int id_protocolo, String observaciones) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.protocolo "
                    + "SET observaciones=?, aprobacion_calidad = false, aprobacion_regente = false, aprobacion_coordinador = false, aprobacion_direccion=false "
                    + " WHERE id_protocolo=?; ");
            consulta.setString(1, observaciones);
            consulta.setInt(2, id_protocolo);
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
    
    public boolean aprobarProtocolo(int id_protocolo, int actor) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            switch (actor) {
                case (1):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.protocolo "
                            + "SET aprobacion_calidad = true "
                            + "WHERE id_protocolo=?; ");
                    break;
                case(2):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.protocolo "
                    + "SET aprobacion_regente=true "
                    + "WHERE id_protocolo=?; ");
                    break;
                case(3):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.protocolo "
                    + "SET aprobacion_coordinador= true "
                    + "WHERE id_protocolo=?; ");
                    break;
                case(4):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.protocolo "
                    + "SET aprobacion_direccion = true "
                    + "WHERE id_protocolo=?; ");
                    break;
            }
            consulta.setInt(1, id_protocolo);
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
    
    

}
