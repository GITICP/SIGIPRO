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
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.eventos (id_serpiente, id_usuario, fecha_evento,evento,valor_cambiado) " +
                                                             " VALUES (?,?,?,?,?) RETURNING id_evento");
            consulta.setInt(1, e.getSerpiente().getId_serpiente());
            consulta.setInt(2, e.getUsuario().getId_usuario());
            consulta.setDate(3, e.getFecha_evento());
            consulta.setString(4, e.getEvento());
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
    
    public List<Evento> validarSerpiente(int id_serpiente) throws SIGIPROException{
        List<Evento> resultado = new ArrayList<Evento>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT id_evento "
                    + "FROM serpentario.serpientes AS serpientes "
                    + "INNER JOIN serpentario.eventos AS eventos ON serpientes.id_serpiente=eventos.id_serpiente and (eventos.evento = 'Pase a Coleccion Viva' "
                    + "or eventos.evento='Deceso' or eventos.evento='Catálogo Tejido' or eventos.evento='Colección Húmeda') "
                    + "WHERE serpientes.id_serpiente=?;");
            
            consulta.setInt(1,id_serpiente);
            ResultSet resultadoConsulta = consulta.executeQuery();
            while( resultadoConsulta.next() ){
                Evento evento;
                evento = this.obtenerEvento(resultadoConsulta.getInt("id_evento"));
                resultado.add(evento);
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
            
        }catch (Exception e){
            
        }
        return resultado;
        
        
    }
    
    public Evento validarPasoCV(int id_serpiente) throws SIGIPROException{
        boolean resultado = false;
        Evento evento = new Evento();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT id_evento FROM serpentario.serpientes AS serpientes INNER JOIN serpentario.eventos AS eventos ON serpientes.id_serpiente=eventos.id_serpiente and eventos.evento = 'Pase a Coleccion Viva' WHERE serpientes.id_serpiente=?");
            
            consulta.setInt(1,id_serpiente);
            ResultSet resultadoConsulta = consulta.executeQuery();
            evento = new Evento();
            if ( resultadoConsulta.next() ){
                resultado = true;
                evento = this.obtenerEvento(resultadoConsulta.getInt("id_evento"));
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
            
        }catch (Exception e){
            
        }
        return evento;
        
        
    }
    
    public Evento validarCatalogoTejido(int id_serpiente) throws SIGIPROException{
        boolean resultado = false;
        Evento evento = new Evento();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT id_evento FROM serpentario.serpientes AS serpientes INNER JOIN serpentario.eventos AS eventos ON serpientes.id_serpiente=eventos.id_serpiente and eventos.evento = 'Catálogo Tejido' WHERE serpientes.id_serpiente=?");
            
            consulta.setInt(1,id_serpiente);
            ResultSet resultadoConsulta = consulta.executeQuery();
            evento = new Evento();
            if ( resultadoConsulta.next() ){
                resultado = true;
                evento = this.obtenerEvento(resultadoConsulta.getInt("id_evento"));
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
            
        }catch (Exception e){
            
        }
        return evento;
        
        
    }
    
    public Evento validarColeccionHumeda(int id_serpiente) throws SIGIPROException{
        boolean resultado = false;
        Evento evento = new Evento();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT id_evento FROM serpentario.serpientes AS serpientes INNER JOIN serpentario.eventos AS eventos ON serpientes.id_serpiente=eventos.id_serpiente and eventos.evento = 'Colección Húmeda' WHERE serpientes.id_serpiente=?");
            
            consulta.setInt(1,id_serpiente);
            ResultSet resultadoConsulta = consulta.executeQuery();
            evento = new Evento();
            if ( resultadoConsulta.next() ){
                resultado = true;
                evento = this.obtenerEvento(resultadoConsulta.getInt("id_evento"));
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
            
        }catch (Exception e){
            
        }
        return evento;
        
        
    }
    
    public Evento validarDeceso(int id_serpiente) throws SIGIPROException{
        boolean resultado = false;
        Evento evento = new Evento();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT id_evento FROM serpentario.serpientes AS serpientes INNER JOIN serpentario.eventos AS eventos ON serpientes.id_serpiente=eventos.id_serpiente and eventos.evento = 'Deceso' WHERE serpientes.id_serpiente=?");
            
            consulta.setInt(1,id_serpiente);
            ResultSet resultadoConsulta = consulta.executeQuery();
            evento = new Evento();
            if ( resultadoConsulta.next() ){
                resultado = true;
                evento = this.obtenerEvento(resultadoConsulta.getInt("id_evento"));
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
            
        }catch (Exception e){
            
        }
        return evento;
    }
    
    public boolean insertarExtraccion(Evento e){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.eventos (id_serpiente, id_usuario, fecha_evento,evento,observaciones,id_extraccion) " +
                                                             " VALUES (?,?,?,?,?,?) RETURNING id_evento");
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
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.eventos WHERE id_serpiente=?;");
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
                e.setValor_cambiado(rs.getString("valor_cambiado"));
                try{
                    //Tratar de agarrar la extraccion desde el DAO
                    Extraccion extraccion = new Extraccion();
                    ExtraccionDAO extracciondao = new ExtraccionDAO();
                    extraccion = extracciondao.obtenerExtraccion(rs.getInt("id_extraccion"));
                    e.setExtraccion(extraccion);
                }catch (Exception ex){
                    
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
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.eventos WHERE id_evento=?;");
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
                e.setEvento(rs.getString("evento"));
                e.setObservaciones(rs.getString("observaciones"));
                e.setValor_cambiado(rs.getString("valor_cambiado"));
                try{
                    //Tratar de agarrar la extraccion desde el DAO
                    Extraccion extraccion = new Extraccion();
                }catch (Exception ex){
                    
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
            conexion = null;
        }
        return conexion;
    }
}
