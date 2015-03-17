/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.serpentario.modelos.Evento;
import com.icp.sigipro.serpentario.modelos.Extraccion;
import com.icp.sigipro.serpentario.modelos.Serpiente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class EventoDAO {
     private Connection conexion;
    
    
    public EventoDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    } 
    
    public boolean insertarEvento(Evento e){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.eventos (id_serpiente, id_usuario, fecha_evento,evento,observaciones) " +
                                                             " VALUES (?,?,?,?,?) RETURNING id_evento");
            consulta.setInt(1, e.getSerpiente().getId_serpiente());
            consulta.setInt(2, e.getUsuario().getId_usuario());
            consulta.setDate(3, e.getFecha_evento());
            consulta.setString(4, e.getEvento());
            consulta.setString(5, e.getObservaciones());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                e.setId_evento(resultadoConsulta.getInt("id_evento"));
            }
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public boolean insertarExtraccion(Evento e){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.eventos (id_serpiente, id_usuario, fecha_evento,evento,observaciones,id_extraccion) " +
                                                             " VALUES (?,?,?,?,?) RETURNING id_evento");
            consulta.setInt(1, e.getSerpiente().getId_serpiente());
            consulta.setInt(2, e.getUsuario().getId_usuario());
            consulta.setDate(3, e.getFecha_evento());
            consulta.setString(4, e.getEvento());
            consulta.setString(5, e.getObservaciones());
            consulta.setInt(6, e.getExtraccion().getId_extraccion());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                e.setId_evento(resultadoConsulta.getInt("id_evento"));
            }
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public List<Evento> obtenerEventos(int id_serpiente){
        List<Evento> resultado = new ArrayList<Evento>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.evento WHERE id_serpiente=?;");
            consulta.setInt(1, id_serpiente);
            ResultSet rs = consulta.executeQuery();
            SerpienteDAO serpienteDao = new SerpienteDAO();
            UsuarioDAO usuarioDao = new UsuarioDAO();
            while(rs.next()){
                Evento e = new Evento();
                e.setId_evento(rs.getInt("id_evento"));
                Serpiente s = serpienteDao.obtenerSerpiente(rs.getInt("id_serpiente"));
                e.setSerpiente(s);
                Usuario u = usuarioDao.obtenerUsuario(rs.getInt("id_usuario"));
                e.setUsuario(u);
                e.setFecha_evento(rs.getDate("fecha_evento"));
                e.setEvento(rs.getString("evento"));
                e.setObservaciones(rs.getString("observaciones"));
                try{
                    //Tratar de agarrar la extraccion desde el DAO
                    Extraccion extraccion = new Extraccion();
                }catch (Exception ex){
                    
                }
                resultado.add(e);
            }      
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
    private Connection getConexion(){
        try{     
            if ( conexion.isClosed() ){
                SingletonBD s = SingletonBD.getSingletonBD();
                conexion = s.conectar();
            }
        }
        catch(Exception ex)
        {
            conexion = null;
        }
        return conexion;
    }
}
