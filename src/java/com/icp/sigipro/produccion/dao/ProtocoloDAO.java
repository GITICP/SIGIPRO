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
import java.sql.SQLException;
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
            getConexion().setAutoCommit(false);
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.protocolo (version, aprobacion_calidad, aprobacion_direccion, aprobacion_regente, aprobacion_coordinador, aprobacion_gestion) "
                    + " VALUES (1,false, false, false,false,false) RETURNING id_protocolo");
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                protocolo.setId_protocolo(rs.getInt("id_protocolo"));
                consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_protocolo (id_protocolo, version, nombre, descripcion, id_catalogo_pt) "
                        + " VALUES (?,1,?,?,?) RETURNING id_historial");

                consulta.setInt(1, protocolo.getId_protocolo());
                consulta.setString(2, protocolo.getNombre());
                consulta.setString(3, protocolo.getDescripcion());
                //consulta.setInt(4, protocolo.getFormula_maestra().getId_formula_maestra());
                consulta.setInt(4, protocolo.getProducto().getId_catalogo_pt());
                rs = consulta.executeQuery();
                if (rs.next()) {
                    resultado = true;
                    protocolo.setId_historial(rs.getInt("id_historial"));

                    consulta = getConexion().prepareStatement(" INSERT INTO produccion.paso_protocolo (id_protocolo, version, id_paso, posicion, requiere_ap) "
                            + " VALUES (?,1,?,?,?);");

                    consulta.setInt(1, protocolo.getId_protocolo());
                    for (Paso p : protocolo.getPasos()) {
                        consulta.setInt(2, p.getId_paso());
                        consulta.setInt(3, p.getPosicion());
                        consulta.setBoolean(4, p.isRequiere_ap());
                        consulta.addBatch();
                    }
                    consulta.executeBatch();
                    getConexion().setAutoCommit(true);
                    getConexion().commit();
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

    public boolean editarProtocolo(Protocolo protocolo, int version) {
        boolean resultado = false;
        ResultSet rs = null;
        PreparedStatement consulta = null;
        try {
            getConexion().setAutoCommit(false);
            consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_protocolo (id_protocolo, version, nombre, descripcion, id_catalogo_pt) "
                    + " VALUES (?,?,?,?,?) RETURNING id_historial");
            consulta.setInt(1, protocolo.getId_protocolo());
            consulta.setInt(2, version);
            consulta.setString(3, protocolo.getNombre());
            consulta.setString(4, protocolo.getDescripcion());
            //consulta.setInt(5, protocolo.getFormula_maestra().getId_formula_maestra());
            consulta.setInt(5, protocolo.getProducto().getId_catalogo_pt());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                consulta = getConexion().prepareStatement(" UPDATE produccion.protocolo "
                        + "SET version=?, aprobacion_calidad = false, aprobacion_regente = false, aprobacion_coordinador = false, aprobacion_direccion=false, aprobacion_gestion=false  "
                        + "WHERE id_protocolo = ?; ");

                consulta.setInt(1, version);
                consulta.setInt(2, protocolo.getId_protocolo());
                if (consulta.executeUpdate() == 1) {
                    consulta = getConexion().prepareStatement(" INSERT INTO produccion.paso_protocolo (id_protocolo, version, id_paso, posicion, requiere_ap) "
                            + " VALUES (?,?,?,?,?); ");

                    consulta.setInt(1, protocolo.getId_protocolo());
                    for (Paso p : protocolo.getPasos()) {
                        consulta.setInt(2, version);
                        consulta.setInt(3, p.getId_paso());
                        consulta.setInt(4, p.getPosicion());
                        consulta.setBoolean(5, p.isRequiere_ap());
                        consulta.addBatch();
                    }
                    consulta.executeBatch();
                    getConexion().setAutoCommit(true);
                    getConexion().commit();

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

    public boolean activarVersion(int version, int id_protocolo) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE produccion.protocolo "
                    + "SET version=?, aprobacion_calidad = false, aprobacion_regente = false, aprobacion_coordinador = false, aprobacion_direccion=false, aprobacion_gestion=false  "
                    + "WHERE id_protocolo= ?; ");
            consulta.setInt(1, version);
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
                protocolo.setAprobacion_gestion(rs.getBoolean("aprobacion_gestion"));
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

    public Protocolo obtenerProtocolo(int id_protocolo) {
        Protocolo resultado = new Protocolo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT pro.*, h.*, pt.id_catalogo_pt, pt.nombre as nombrept "
                    + "FROM produccion.protocolo as pro "
                    + "LEFT JOIN produccion.historial_protocolo as h ON (pro.id_protocolo = h.id_protocolo AND pro.version = h.version) "
                    + "LEFT JOIN produccion.catalogo_pt as pt ON pt.id_catalogo_pt = h.id_catalogo_pt "
                    //+ "LEFT JOIN produccion.formula_maestra as fm ON fm.id_formula_maestra = h.id_formula_maestra "
                    + "WHERE pro.id_protocolo = ?; ");
            consulta.setInt(1, id_protocolo);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_protocolo(rs.getInt("id_protocolo"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setDescripcion(rs.getString("descripcion"));
                resultado.setAprobacion_calidad(rs.getBoolean("aprobacion_calidad"));
                resultado.setAprobacion_coordinador(rs.getBoolean("aprobacion_coordinador"));
                resultado.setAprobacion_direccion(rs.getBoolean("aprobacion_direccion"));
                resultado.setAprobacion_regente(rs.getBoolean("aprobacion_regente"));
                resultado.setAprobacion_gestion(rs.getBoolean("aprobacion_gestion"));
                resultado.setObservaciones(rs.getString("observaciones"));
                resultado.setVersion(rs.getInt("version"));
                Catalogo_PT pt = new Catalogo_PT();
                pt.setId_catalogo_pt(rs.getInt("id_catalogo_pt"));
                pt.setNombre(rs.getString("nombrept"));
                resultado.setProducto(pt);
                /*Formula_Maestra fm = new Formula_Maestra();
                fm.setId_formula_maestra(rs.getInt("id_formula_maestra"));
                fm.setNombre(rs.getString("nombrefm"));
                resultado.setFormula_maestra(fm);*/

                consulta = getConexion().prepareStatement(" SELECT h.id_historial, h.version "
                        + "FROM produccion.historial_protocolo as h "
                        + "WHERE h.id_protocolo = ?; ");
                consulta.setInt(1, id_protocolo);

                rs = consulta.executeQuery();
                resultado.setHistorial(new ArrayList<Protocolo>());
                while (rs.next()) {
                    Protocolo p = new Protocolo();
                    p.setId_historial(rs.getInt("id_historial"));
                    p.setVersion(rs.getInt("version"));
                    resultado.getHistorial().add(p);
                }

                consulta = getConexion().prepareStatement(" SELECT p.id_paso, h.id_historial, h.nombre, pxp.requiere_ap, pxp.posicion "
                        + "FROM produccion.protocolo as pro "
                        + "LEFT JOIN produccion.paso_protocolo as pxp ON (pro.id_protocolo = pxp.id_protocolo AND pro.version = pxp.version) "
                        + "LEFT JOIN produccion.paso as p ON pxp.id_paso = p.id_paso "
                        + "LEFT JOIN produccion.historial_paso as h ON (h.id_paso = p.id_paso AND h.version = p.version) "
                        + "WHERE pro.id_protocolo = ? "
                        + "ORDER BY pxp.posicion; ");
                consulta.setInt(1, id_protocolo);
                rs = consulta.executeQuery();
                int contador = 1;
                resultado.setPasos(new ArrayList<Paso>());
                while (rs.next()) {
                    Paso p = new Paso();
                    p.setContador(contador);
                    p.setId_paso(rs.getInt("id_paso"));
                    p.setId_historial(rs.getInt("id_historial"));
                    p.setNombre(rs.getString("nombre"));
                    p.setRequiere_ap(rs.getBoolean("requiere_ap"));
                    p.setPosicion(rs.getInt("posicion"));
                    contador++;
                    resultado.getPasos().add(p);
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

    public int obtenerVersion(int id_historial) {
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT version FROM produccion.historial_protocolo WHERE id_historial=?; ");
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

    public Protocolo obtenerHistorial(int id_historial, int id_protocolo) {
        Protocolo resultado = new Protocolo();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT h.*, pt.id_catalogo_pt, pt.nombre as nombrept "
                    + "FROM produccion.historial_protocolo as h "
                    + "LEFT JOIN produccion.catalogo_pt as pt ON pt.id_catalogo_pt = h.id_catalogo_pt "
                    //+ "LEFT JOIN produccion.formula_maestra as fm ON fm.id_formula_maestra = h.id_formula_maestra "
                    + "WHERE h.id_historial = ?; ");
            consulta.setInt(1, id_historial);
            System.out.println(consulta);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado.setId_protocolo(id_protocolo);
                resultado.setId_historial(rs.getInt("id_historial"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setDescripcion(rs.getString("descripcion"));
                resultado.setVersion(rs.getInt("version"));
                Catalogo_PT pt = new Catalogo_PT();
                pt.setId_catalogo_pt(rs.getInt("id_catalogo_pt"));
                pt.setNombre(rs.getString("nombrept"));
                resultado.setProducto(pt);
                /*Formula_Maestra fm = new Formula_Maestra();
                fm.setId_formula_maestra(rs.getInt("id_formula_maestra"));
                fm.setNombre(rs.getString("nombrefm"));
                resultado.setFormula_maestra(fm);*/

                consulta = getConexion().prepareStatement(" SELECT h.id_historial,p.id_paso, h.nombre, pxp.requiere_ap, pxp.posicion "
                        + "FROM produccion.historial_protocolo as hpro "
                        + "LEFT JOIN produccion.paso_protocolo as pxp ON (hpro.id_protocolo = pxp.id_protocolo AND hpro.version = pxp.version) "
                        + "LEFT JOIN produccion.paso as p ON pxp.id_paso = p.id_paso "
                        + "LEFT JOIN produccion.historial_paso as h ON (h.id_paso = p.id_paso AND h.version = p.version) "
                        + "WHERE hpro.id_historial=? "
                        + "ORDER BY pxp.posicion; ");
                consulta.setInt(1, id_historial);
                rs = consulta.executeQuery();
                int contador = 1;
                resultado.setPasos(new ArrayList<Paso>());
                while (rs.next()) {
                    Paso p = new Paso();
                    p.setContador(contador);
                    p.setId_paso(rs.getInt("id_paso"));
                    p.setId_historial(rs.getInt("id_historial"));
                    p.setNombre(rs.getString("nombre"));
                    p.setRequiere_ap(rs.getBoolean("requiere_ap"));
                    p.setPosicion(rs.getInt("posicion"));
                    contador++;
                    resultado.getPasos().add(p);
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
                resultado.setAprobacion_gestion(rs.getBoolean("aprobacion_gestion"));
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
                    + "SET observaciones=?, aprobacion_calidad = false, aprobacion_regente = false, aprobacion_coordinador = false, aprobacion_direccion=false, aprobacion_gestion=false "
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
                case (2):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.protocolo "
                            + "SET aprobacion_regente=true "
                            + "WHERE id_protocolo=?; ");
                    break;
                case (3):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.protocolo "
                            + "SET aprobacion_coordinador= true "
                            + "WHERE id_protocolo=?; ");
                    break;
                case (4):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.protocolo "
                            + "SET aprobacion_direccion = true, observaciones='' "
                            + "WHERE id_protocolo=?; ");
                    break;
                case (5):
                    consulta = getConexion().prepareStatement(" UPDATE produccion.protocolo "
                            + "SET aprobacion_gestion = true, observaciones='' "
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
    
    public int obtenerUltimaVersion(int id_protocolo){
        int resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT version FROM produccion.historial_protocolo WHERE id_protocolo=? ORDER BY version DESC LIMIT 1; ");
            consulta.setInt(1, id_protocolo);
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
