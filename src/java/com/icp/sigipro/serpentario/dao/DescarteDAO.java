/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.serpentario.modelos.Evento;
import com.icp.sigipro.serpentario.modelos.Serpiente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class DescarteDAO extends DAO
{

    public DescarteDAO()
    {
    }

    public List<Evento> obtenerDescartes()
    {
        List<Evento> respuesta = new ArrayList<Evento>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT S.NUMERO_SERPIENTE, S.ID_SERPIENTE,E.ID_EVENTO, E.FECHA_EVENTO "
                                                                        + "FROM SERPENTARIO.SERPIENTES AS S INNER JOIN SERPENTARIO.EVENTOS AS E ON S.ID_SERPIENTE = E.ID_SERPIENTE "
                                                                        + "WHERE E.ID_CATEGORIA = 14;");

            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                Evento evento = new Evento();
                Serpiente serpiente = new Serpiente();
                serpiente.setId_serpiente(rs.getInt("id_serpiente"));
                serpiente.setNumero_serpiente(rs.getInt("numero_serpiente"));
                evento.setSerpiente(serpiente);

                evento.setId_evento(rs.getInt("id_evento"));
                evento.setFecha_evento(rs.getDate("fecha_evento"));
                respuesta.add(evento);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return respuesta;
    }
}
