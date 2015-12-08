/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.Muestra;
import com.icp.sigipro.controlcalidad.modelos.TipoMuestra;
import com.icp.sigipro.core.DAO;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class MuestraDAO extends DAO {

    public List<Muestra> obtenerMuestras() {
        List<Muestra> resultado = new ArrayList<Muestra>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT m.id_muestra, m.identificador, m.fecha_descarte_estimada, m.fecha_descarte_real, tm.id_tipo_muestra,tm.nombre "
                    + "FROM control_calidad.muestras AS m LEFT OUTER JOIN control_calidad.tipos_muestras AS tm ON tm.id_tipo_muestra = m.id_tipo_muestra "
                    + "ORDER BY fecha_descarte_estimada ; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                TipoMuestra tm = new TipoMuestra();
                tm.setId_tipo_muestra(rs.getInt("id_tipo_muestra"));
                tm.setNombre(rs.getString("nombre"));
                Muestra m = new Muestra();
                m.setFecha_descarte_estimada(rs.getDate("fecha_descarte_estimada"));
                m.setFecha_descarte_real(rs.getDate("fecha_descarte_real"));
                m.setId_muestra(rs.getInt("id_muestra"));
                m.setIdentificador(rs.getString("identificador"));
                m.setTipo_muestra(tm);
                resultado.add(m);
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

    public boolean descartarMuestra(String[] id_muestras, Date fecha_descarte) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE control_calidad.muestras "
                    + "SET fecha_descarte_real=? "
                    + "WHERE id_muestra = ?; ");
            consulta.setDate(1, fecha_descarte);
            for (String id_muestra : id_muestras) {
                if (!"".equals(id_muestra)) {
                    consulta.setInt(2, Integer.parseInt(id_muestra));
                    consulta.addBatch();
                }
            }
            consulta.executeBatch();
            resultado = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
}
