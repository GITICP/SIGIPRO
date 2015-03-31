/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.caballeriza.modelos.EventoClinico;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walter
 */
public class EventoClinicoDAO {

    private Connection conexion;

    public EventoClinicoDAO() {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }

    public boolean insertarEventoClinico(EventoClinico c) throws SIGIPROException {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO caballeriza.eventos_clinicos (fecha, descripcion,responsable,id_tipo_evento) "
                    + " VALUES (?,?,?,?,?,?,?,?,?) RETURNING id_evento");
            consulta.setDate(1, c.getFecha());
            consulta.setString(2, c.getDescripcion());
            consulta.setInt(3, c.getResponsable().getID());
            consulta.setInt(4, c.getTipo_evento().getId_tipo_evento());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
                c.setId_evento(resultadoConsulta.getInt("id_evento"));
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
        } catch (SQLException ex) {
            throw new SIGIPROException("MODIFICAR MENSAJE.");
        }
        return resultado;
    }

    public boolean editarEventoClinico(EventoClinico c) throws SIGIPROException {
        boolean resultado = false;

        try {
            //IMPLEMENTAR EL INSERT DE EVENTOS CADA VEZ QUE CAMBIA UN DATO
            //EventoClinico caballo = this.obtenerEventoClinico(c.getId_caballo());
            //------------------------------------------------------------
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE caballeriza.eventos_clinicos "
                    + " SET descripcion=?"
                    + " WHERE id_evento=?; "
            );
//            int gC=c.getGrupo_de_caballos().getId_grupo_caballo();
//            String sennas=c.getOtras_sennas();
//            Blob foto=c.getFotografia();
//            String ESTADO = c.getEstado();
//            int id=c.getId_caballo();
            consulta.setString(1, c.getDescripcion());
            consulta.setInt(2, c.getId_evento());

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            conexion.close();
        } catch (SQLException ex) {
            throw new SIGIPROException("El evento clinico no puede ser editado.");
        }
        return resultado;

    }

    public EventoClinico obtenerEventoClinico(int id_evento) throws SIGIPROException {
        EventoClinico evento = new EventoClinico();
        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM caballeriza.eventos_clinicos"
                    + " where id_evento = ?");
            consulta.setInt(1, id_evento);
            ResultSet rs = consulta.executeQuery();
            TipoEventoDAO dao = new TipoEventoDAO();
            UsuarioDAO daoU = new UsuarioDAO();
            if (rs.next()) {
                evento.setId_evento(rs.getInt("id_evento"));
                evento.setFecha(rs.getDate("fecha"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setResponsable(daoU.obtenerUsuario(rs.getInt("responsable")));
                evento.setTipo_evento(dao.obtenerTipoEvento(rs.getInt("id_tipo_evento")));
            }
            consulta.close();
            conexion.close();
            rs.close();
        } catch (SQLException ex) {
            throw new SIGIPROException("El evento clinico no puede ser obtenido.");
        }
        return evento;
    }

    public List<EventoClinico> obtenerEventosClinicos() throws SIGIPROException {
        List<EventoClinico> resultado = new ArrayList<EventoClinico>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.eventos_clinicos ");
            ResultSet rs = consulta.executeQuery();
            TipoEventoDAO dao = new TipoEventoDAO();
            UsuarioDAO daoU = new UsuarioDAO();
            while (rs.next()) {
                EventoClinico evento = new EventoClinico();
                evento.setId_evento(rs.getInt("id_evento"));
                evento.setFecha(rs.getDate("fecha"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setResponsable(daoU.obtenerUsuario(rs.getInt("responsable")));
                evento.setTipo_evento(dao.obtenerTipoEvento(rs.getInt("id_tipo_evento")));
                resultado.add(evento);
            }
            consulta.close();
            conexion.close();
            rs.close();
        } catch (SQLException ex) {
            throw new SIGIPROException("Los eventos clinicos no pueden ser accedidos.");
        }
        return resultado;
    }

    public List<EventoClinico> obtenerEventosClinicosSinTipo() throws SIGIPROException {
        List<EventoClinico> resultado = new ArrayList<EventoClinico>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_evento, descripcion FROM caballeriza.eventos_clinicos WHERE id_tipo_evento is null; ");

            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                EventoClinico evento_clinico = new EventoClinico();
                evento_clinico.setId_evento(rs.getInt("id_evento_clinico"));
                // CAMBIAR POR NOMBRE 
                evento_clinico.setDescripcion(rs.getString("descripcion"));

                resultado.add(evento_clinico);
            }          
            rs.close();
            consulta.close();
            cerrarConexion();
            
        } catch (SQLException ex) {
            throw new SIGIPROException("Error de comunicación con la base de datos.");
        }
        return resultado;
    }

    private Connection getConexion() {
        try {
            if (conexion.isClosed()) {
                SingletonBD s = SingletonBD.getSingletonBD();
                conexion = s.conectar();
            }
        } catch (Exception ex) {
            conexion = null;
        }
        return conexion;
    }

    private void cerrarConexion() {
        if (conexion != null) {
            try {
                if (conexion.isClosed()) {
                    conexion.close();
                }
            } catch (Exception ex) {
                conexion = null;
            }
        }
    }
}
