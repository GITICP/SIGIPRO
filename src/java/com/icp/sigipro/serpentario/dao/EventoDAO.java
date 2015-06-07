/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.core.SIGIPROException;
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
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.eventos (id_serpiente, id_usuario, fecha_evento,id_categoria,observaciones) " +
                                                             " VALUES (?,?,?,?,?) RETURNING id_evento");
            consulta.setInt(1, e.getSerpiente().getId_serpiente());
            consulta.setInt(2, e.getUsuario().getId_usuario());
            consulta.setDate(3, e.getFecha_evento());
            consulta.setInt(4, e.getId_categoria());
            consulta.setString(5, e.getObservaciones());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                e.setId_evento(resultadoConsulta.getInt("id_evento"));
            }
            resultadoConsulta.close();            
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public boolean insertarCambio(Evento e){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.eventos (id_serpiente, id_usuario, fecha_evento,id_categoria,valor_cambiado) " +
                                                             " VALUES (?,?,?,?,?) RETURNING id_evento");
            consulta.setInt(1, e.getSerpiente().getId_serpiente());
            consulta.setInt(2, e.getUsuario().getId_usuario());
            consulta.setDate(3, e.getFecha_evento());
            consulta.setInt(4, e.getId_categoria());
            consulta.setString(5, e.getValor_cambiado());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                e.setId_evento(resultadoConsulta.getInt("id_evento"));
            }
            resultadoConsulta.close();
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
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.eventos (id_serpiente, id_usuario, fecha_evento,id_categoria,observaciones,id_extraccion) "
                    + "SELECT ?,?,?,?,?,? WHERE NOT EXISTS (SELECT 1 FROM SERPENTARIO.EVENTOS WHERE ID_SERPIENTE=? AND ID_EXTRACCION=?) "
                    + "RETURNING id_evento");
            consulta.setInt(1, e.getSerpiente().getId_serpiente());
            consulta.setInt(2, e.getUsuario().getId_usuario());
            consulta.setDate(3, e.getFecha_evento());
            consulta.setInt(4, e.getId_categoria());
            consulta.setString(5, e.getObservaciones());
            consulta.setInt(6, e.getExtraccion().getId_extraccion());
            consulta.setInt(7, e.getSerpiente().getId_serpiente());
            consulta.setInt(8, e.getExtraccion().getId_extraccion());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                e.setId_evento(resultadoConsulta.getInt("id_evento"));
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public boolean validarPasoCV(int id_serpiente){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_categoria FROM serpentario.serpientes as serpiente INNER JOIN serpentario.eventos as evento ON serpiente.id_serpiente=evento.id_serpiente "
                    + "WHERE serpiente.id_serpiente=? and evento.id_categoria=5;");
            consulta.setInt(1, id_serpiente);
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public boolean reversarPasoCV(int id_serpiente){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("DELETE FROM SERPENTARIO.EVENTOS WHERE ID_SERPIENTE=? AND ID_CATEGORIA=5; ");
            consulta.setInt(1, id_serpiente);
            if (consulta.executeUpdate() == 1 ){
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
        public boolean reversarDeceso(int id_serpiente){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("DELETE FROM SERPENTARIO.EVENTOS WHERE ID_SERPIENTE=? AND ID_CATEGORIA=6; ");
            consulta.setInt(1, id_serpiente);
            if (consulta.executeUpdate() == 1 ){
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public boolean validarDeceso(int id_serpiente){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_categoria FROM serpentario.serpientes as serpiente INNER JOIN serpentario.eventos as evento ON serpiente.id_serpiente=evento.id_serpiente "
                    + "WHERE serpiente.id_serpiente=? and evento.id_categoria=6;");
            
            consulta.setInt(1, id_serpiente);
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
            }
            resultadoConsulta.close();
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
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.eventos as eventos INNER JOIN serpentario.categorias as categorias ON eventos.id_categoria = categorias.id_categoria "
                    + "WHERE eventos.id_serpiente=?;");
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
                e.setEvento(rs.getString("nombre_categoria"));
                e.setId_categoria(rs.getInt("id_categoria"));
                e.setFecha_evento(rs.getDate("fecha_evento"));
                e.setObservaciones(rs.getString("observaciones"));
                e.setValor_cambiado(rs.getString("valor_cambiado"));
                try{
                    //Tratar de agarrar la extraccion desde el DAO
                    Extraccion extraccion = new Extraccion();
                    ExtraccionDAO extracciondao = new ExtraccionDAO();
                    extraccion = extracciondao.obtenerExtraccion(rs.getInt("id_extraccion"));
                    e.setExtraccion(extraccion);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                resultado.add(e);
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public Evento obtenerEvento(int id_evento){
        Evento e = new Evento();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.eventos as eventos INNER JOIN serpentario.categorias as categorias ON eventos.id_categoria = categorias.id_categoria "
                    + "WHERE eventos.id_evento=?;");
            consulta.setInt(1, id_evento);
            ResultSet rs = consulta.executeQuery();
            SerpienteDAO serpienteDao = new SerpienteDAO();
            UsuarioDAO usuarioDao = new UsuarioDAO();
            if ( rs.next() ){
                e.setId_evento(rs.getInt("id_evento"));
                Serpiente s = serpienteDao.obtenerSerpiente(rs.getInt("id_serpiente"));
                e.setSerpiente(s);
                Usuario u = usuarioDao.obtenerUsuario(rs.getInt("id_usuario"));
                e.setUsuario(u);
                e.setFecha_evento(rs.getDate("fecha_evento"));
                e.setEvento(rs.getString("nombre_categoria"));
                e.setId_categoria(rs.getInt("id_categoria"));
                e.setObservaciones(rs.getString("observaciones"));
                e.setValor_cambiado(rs.getString("valor_cambiado"));
                try{
                    //Tratar de agarrar la extraccion desde el DAO
                    Extraccion extraccion = new Extraccion();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return e;
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
            ex.printStackTrace();
            conexion = null;
        }
        return conexion;
    }
}
