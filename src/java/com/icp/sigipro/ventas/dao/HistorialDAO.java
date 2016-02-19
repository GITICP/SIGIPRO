/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Historial;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class HistorialDAO extends DAO {

    public HistorialDAO() {
    }
    

    public List<Historial> obtenerHistoriales() throws SIGIPROException {

        List<Historial> resultado = new ArrayList<Historial>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.historial; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Historial historial = new Historial();
                
                historial.setId_historial(rs.getInt("id_historial"));
                historial.setHistorial(rs.getString("historial"));
                resultado.add(historial);

            }
            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }

    public Historial obtenerHistorial(int id_historial) throws SIGIPROException {

        Historial resultado = new Historial();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.historial where id_historial = ?; ");
            consulta.setInt(1, id_historial);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_historial(rs.getInt("id_historial"));
                resultado.setHistorial(rs.getString("historial"));
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }
    
    public int insertarHistorial(Historial p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.historial (historial)"
                    + " VALUES (?) RETURNING id_historial");

            consulta.setString(1, p.getHistorial());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_historial");
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");
        }
        return resultado;
    }

    public boolean editarHistorial(Historial p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.historial"
                    + " SET historial=?"
                    + " WHERE id_historial=?; "
            );

            consulta.setString(1, p.getHistorial());
            consulta.setInt(2, p.getId_historial());
            
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la edición");
        }
        return resultado;
    }

    public boolean eliminarHistorial(int id_historial) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.historial "
                    + " WHERE id_historial=?; "
            );

            consulta.setInt(1, id_historial);

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la eliminación");
        }
        return resultado;
    }

}
