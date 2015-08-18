/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;

import com.icp.sigipro.bioterio.modelos.Pie;
import com.icp.sigipro.bioterio.modelos.PiexCepa;
import com.icp.sigipro.bioterio.modelos.Cepa;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Amed
 */
public class PiexCepaDAO extends DAO
{

    public PiexCepaDAO()
    {
    }

    public boolean insertarPiexCepa(PiexCepa p) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.piexcepa (id_pie, id_cepa, fecha_inicio, fecha_estimada_retiro)"
                                                                        + " VALUES (?,?,?,?) RETURNING id_pie");

            consulta.setInt(1, p.getPie().getId_pie());
            consulta.setInt(2, p.getCepa().getId_cepa());
            consulta.setDate(3, p.getFecha_inicio());
            consulta.setDate(4, p.getFecha_estimada_retiro());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");
        }
        return resultado;
    }

    public boolean eliminarPiexCepa(int id_cepa) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM bioterio.piexcepa "
                    + " WHERE id_cepa=?; "
            );

            consulta.setInt(1, id_cepa);

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la eliminación");
        }
        return resultado;
    }

    public PiexCepa obtenerPiexCepa(int id) throws SIGIPROException
    {

        PiexCepa piexcepa = new PiexCepa();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM (bioterio.piexcepa pc INNER JOIN bioterio.pie_de_cria p ON pc.id_pie = p.id_pie) INNER JOIN bioterio.cepas c ON pc.id_cepa = c.id_cepa WHERE pc.id_cepa = ?");

            consulta.setInt(1, id);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                Pie pie = new Pie();
                Cepa cepa = new Cepa();
                pie.setCodigo(rs.getString("codigo"));
                pie.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                pie.setId_pie(rs.getInt("id_pie"));
                pie.setFecha_retiro(rs.getDate("fecha_retiro"));
                pie.setFuente(rs.getString("fuente"));
                cepa.setId_cepa(rs.getInt("id_cepa"));
                cepa.setDescripcion(rs.getString("descripcion"));
                cepa.setNombre(rs.getString("nombre"));
                piexcepa.setPie(pie);
                piexcepa.setCepa(cepa);
                piexcepa.setFecha_inicio(rs.getDate("fecha_inicio"));
                piexcepa.setFecha_estimada_retiro(rs.getDate("fecha_estimada_retiro"));
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return piexcepa;
    }
}
