/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.EventoClinico;
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
public class EventoClinicoDAO
{

    private Connection conexion;

    public EventoClinicoDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }

    public boolean insertarEventoClinico(EventoClinico c, String[] ids_caballos) throws SIGIPROException
    {
        boolean resultado_insert_evento = false;
        boolean resultado_asociacion_caballos = false;
        PreparedStatement consulta_caballos = null;
        PreparedStatement consulta = null;
        ResultSet resultadoConsulta = null;
        try {
            getConexion().setAutoCommit(false);

            consulta = getConexion().prepareStatement(" INSERT INTO caballeriza.eventos_clinicos (fecha, descripcion,responsable,id_tipo_evento) "
                                                      + " VALUES (?,?,?,?) RETURNING id_evento");
            consulta.setDate(1, c.getFecha());
            consulta.setString(2, c.getDescripcion());
            consulta.setString(3, c.getResponsable());
            consulta.setInt(4, c.getTipo_evento().getId_tipo_evento());

            resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado_insert_evento = true;
                c.setId_evento(resultadoConsulta.getInt("id_evento"));
            }

            if (ids_caballos != null || ids_caballos.length > 0) {
                consulta_caballos = getConexion().prepareStatement(" INSERT INTO caballeriza.eventos_clinicos_caballos (id_evento, id_caballo) VALUES (?,?);");

                for (String id_caballo : ids_caballos) {
                    consulta_caballos.setInt(1, c.getId_evento());
                    consulta_caballos.setInt(2, Integer.parseInt(id_caballo));
                    consulta_caballos.addBatch();
                }

                int[] asociacion_caballos = consulta_caballos.executeBatch();

                boolean iteracion_completa = true;

                for (int asociacion : asociacion_caballos) {
                    if (asociacion != 1) {
                        iteracion_completa = false;
                        break;
                    }
                }

                if (iteracion_completa) {
                    resultado_asociacion_caballos = true;
                }

                consulta_caballos.close();
            }
            else {
                resultado_asociacion_caballos = true;
            }
        }
        catch (SQLException ex) {
            throw new SIGIPROException("No se pudo registrar el Evento Clinico.");
        }
        finally {
            try {
                if (resultado_insert_evento && resultado_asociacion_caballos) {
                    getConexion().commit();
                }
                else {
                    getConexion().rollback();
                }
                if (resultadoConsulta != null) {
                    resultadoConsulta.close();
                }
                if (consulta != null) {
                    consulta.close();
                }
                if (consulta_caballos != null) {
                    consulta_caballos.close();
                }
                cerrarConexion();
            }
            catch (SQLException sql_ex) {
                throw new SIGIPROException("Error de comunicación con la base de datos.");
            }
        }
        return resultado_insert_evento && resultado_asociacion_caballos;
    }

    public boolean editarEventoClinico(EventoClinico evento, String[] ids_caballos) throws SIGIPROException
    {
        boolean resultado_consulta = false;
        boolean resultado_delete_caballos = false;
        boolean resultado_asociacion_caballos = false;
        PreparedStatement consulta = null;
        PreparedStatement delete_caballos = null;
        PreparedStatement consulta_caballos = null;

        try {
            getConexion().setAutoCommit(false);
            
            consulta = getConexion().prepareStatement(
                    " UPDATE caballeriza.eventos_clinicos "
                    + " SET fecha=?, descripcion=?,responsable=?,id_tipo_evento=?"
                    + " WHERE id_evento=?; "
            );
            consulta.setDate(1, evento.getFecha());
            consulta.setString(2, evento.getDescripcion());
            consulta.setString(3, evento.getResponsable());
            consulta.setInt(4, evento.getTipo_evento().getId_tipo_evento());
            consulta.setInt(5, evento.getId_evento());
            if (consulta.executeUpdate() == 1) {
                resultado_consulta = true;
            }
            
            delete_caballos = getConexion().prepareStatement(
                    " DELETE FROM caballeriza.eventos_clinicos_caballos WHERE id_evento = ?"
            );
            delete_caballos.setInt(1, evento.getId_evento());
            
            if(delete_caballos.executeUpdate() >= 0) { //Pueden no haber caballos por eliminar
                resultado_delete_caballos = true;
            }
            
            if (ids_caballos != null || ids_caballos.length > 0) {
                consulta_caballos = getConexion().prepareStatement(" INSERT INTO caballeriza.eventos_clinicos_caballos (id_evento, id_caballo) VALUES (?,?);");

                for (String id_caballo : ids_caballos) {
                    consulta_caballos.setInt(1, evento.getId_evento());
                    consulta_caballos.setInt(2, Integer.parseInt(id_caballo));
                    consulta_caballos.addBatch();
                }

                int[] asociacion_caballos = consulta_caballos.executeBatch();

                boolean iteracion_completa = true;

                for (int asociacion : asociacion_caballos) {
                    if (asociacion != 1) {
                        iteracion_completa = false;
                        break;
                    }
                }

                if (iteracion_completa) {
                    resultado_asociacion_caballos = true;
                }

                consulta_caballos.close();
            }
            else {
                resultado_asociacion_caballos = true;
            }
        }
        catch (SQLException ex) {
            throw new SIGIPROException("El evento clinico no puede ser editado.");
        }
        finally {
            try {
                if (resultado_consulta && resultado_asociacion_caballos && resultado_delete_caballos) {
                    getConexion().commit();
                }
                else {
                    getConexion().rollback();
                }
                if (consulta != null) {
                    consulta.close();
                }
                if (delete_caballos != null) {
                    delete_caballos.close();
                }
                if (consulta_caballos != null) {
                    consulta_caballos.close();
                }
                cerrarConexion();
            }
            catch (SQLException sql_ex) {
                throw new SIGIPROException("Error de comunicación con la base de datos.");
            }
        }
        return resultado_consulta && resultado_asociacion_caballos && resultado_delete_caballos;
    }

    public EventoClinico obtenerEventoClinico(int id_evento) throws SIGIPROException
    {
        EventoClinico evento = new EventoClinico();
        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM caballeriza.eventos_clinicos"
                                                                        + " where id_evento = ?");
            consulta.setInt(1, id_evento);
            ResultSet rs = consulta.executeQuery();
            TipoEventoDAO dao = new TipoEventoDAO();
            if (rs.next()) {
                evento.setId_evento(rs.getInt("id_evento"));
                evento.setFecha(rs.getDate("fecha"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setResponsable(rs.getString("responsable"));
                evento.setTipo_evento(dao.obtenerTipoEvento(rs.getInt("id_tipo_evento")));
            }
            rs.close();
            consulta.close();
            cerrarConexion();

        }
        catch (SQLException ex) {
            throw new SIGIPROException("El evento clinico no puede ser obtenido.");
        }
        return evento;
    }

    public EventoClinico obtenerEventoClinicoConCaballos(int id_evento) throws SIGIPROException
    {
        EventoClinico evento = new EventoClinico();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    "SELECT evento.*, c.nombre, c.id_caballo\n"
                    + "FROM (SELECT * FROM caballeriza.eventos_clinicos WHERE id_evento = ?) as evento "
                    + "	LEFT JOIN caballeriza.eventos_clinicos_caballos ecc ON ecc.id_evento = evento.id_evento "
                    + "	LEFT JOIN caballeriza.caballos c ON c.id_caballo = ecc.id_caballo");
            consulta.setInt(1, id_evento);
            ResultSet rs = consulta.executeQuery();
            TipoEventoDAO dao = new TipoEventoDAO();
            if (rs.next()) {
                evento.setId_evento(rs.getInt("id_evento"));
                evento.setFecha(rs.getDate("fecha"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setResponsable(rs.getString("responsable"));
                evento.setTipo_evento(dao.obtenerTipoEvento(rs.getInt("id_tipo_evento")));
                int id_caballo = rs.getInt("id_caballo");
                if (id_caballo != 0) {
                    do {
                        Caballo c = new Caballo();
                        c.setId_caballo(rs.getInt("id_caballo"));
                        c.setNombre(rs.getString("nombre"));
                        evento.agregarCaballo(c);
                    }
                    while (rs.next());
                }
            }
            rs.close();
            consulta.close();
            cerrarConexion();

        }
        catch (SQLException ex) {
            throw new SIGIPROException("El evento clinico no puede ser obtenido.");
        }
        return evento;
    }

    public List<EventoClinico> obtenerEventosClinicos() throws SIGIPROException
    {
        List<EventoClinico> resultado = new ArrayList<EventoClinico>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.eventos_clinicos");
            ResultSet rs = consulta.executeQuery();
            TipoEventoDAO dao = new TipoEventoDAO();
            while (rs.next()) {
                EventoClinico evento = new EventoClinico();
                evento.setId_evento(rs.getInt("id_evento"));
                evento.setFecha(rs.getDate("fecha"));
                evento.setDescripcion(rs.getString("descripcion"));
                int tipo = rs.getInt("id_tipo_evento");
                evento.setResponsable(rs.getString("responsable"));
                evento.setTipo_evento(dao.obtenerTipoEvento(rs.getInt("id_tipo_evento")));
                resultado.add(evento);
            }
            consulta.close();
            cerrarConexion();
            rs.close();
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Los eventos clinicos no pueden ser accedidos.");
        }
        return resultado;
    }

    public List<EventoClinico> obtenerEventosClinicosSinTipo() throws SIGIPROException
    {
        List<EventoClinico> resultado = new ArrayList<EventoClinico>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_evento, descripcion FROM caballeriza.eventos_clinicos WHERE id_tipo_evento is null; ");

            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                EventoClinico evento_clinico = new EventoClinico();
                evento_clinico.setId_evento(rs.getInt("id_evento_clinico"));
                evento_clinico.setDescripcion(rs.getString("descripcion"));

                resultado.add(evento_clinico);
            }
            rs.close();
            consulta.close();
            cerrarConexion();

        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error de comunicación con la base de datos.");
        }
        return resultado;
    }

    public List<EventoClinico> obtenerEventosTipo(int id_tipo_evento)
    {
        List<EventoClinico> resultado = new ArrayList<EventoClinico>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.eventos_clinicos where id_tipo_evento = ?");
            consulta.setInt(1, id_tipo_evento);
            ResultSet rs = consulta.executeQuery();
            TipoEventoDAO daoTipo = new TipoEventoDAO();
            while (rs.next()) {
                EventoClinico eventoclinico = new EventoClinico();
                eventoclinico.setId_evento(rs.getInt("id_evento"));
                eventoclinico.setFecha(rs.getDate("fecha"));
                eventoclinico.setDescripcion(rs.getString("descripcion"));
                eventoclinico.setResponsable(rs.getString("responsable"));
                eventoclinico.setTipo_evento(daoTipo.obtenerTipoEvento(rs.getInt("id_tipo_evento")));
                resultado.add(eventoclinico);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public List<Caballo> obtenerCaballosEvento(int id_evento) throws SIGIPROException
    {
        List<Caballo> resultado = new ArrayList<Caballo>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" select nombre, numero_microchip, numero from caballeriza.caballos c left outer join caballeriza.eventos_clinicos_caballos ecc on c.id_caballo = ecc.id_caballo where id_evento=?; ");
            consulta.setInt(1, id_evento);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Caballo caballo = new Caballo();
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getString("numero_microchip"));
                caballo.setNumero(rs.getInt("numero"));
                resultado.add(caballo);
            }
            rs.close();
            consulta.close();
            cerrarConexion();

        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error de comunicación con la base de datos.");
        }
        return resultado;
    }

    private Connection getConexion()
    {
        try {
            if (conexion.isClosed()) {
                SingletonBD s = SingletonBD.getSingletonBD();
                conexion = s.conectar();
            }
        }
        catch (Exception ex) {
            conexion = null;
        }
        return conexion;
    }

    private void cerrarConexion()
    {
        if (conexion != null) {
            try {
                if (conexion.isClosed()) {
                    conexion.close();
                }
            }
            catch (Exception ex) {
                conexion = null;
            }
        }
    }
}
