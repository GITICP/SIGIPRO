/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.caballeriza.modelos.EventoClinico;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walter
 */
public class EventoClinicoDAO {
    private Connection conexion;
    
    
    public EventoClinicoDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    } 
    
    public boolean insertarEventoClinico(EventoClinico c){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO caballeriza.eventos_clinicos (fecha, descripcion,responsable,id_tipo_evento) " +
                                                             " VALUES (?,?,?,?,?,?,?,?,?) RETURNING id_evento");
            consulta.setDate(1, c.getFecha());
            consulta.setString(2, c.getDescripcion());
            consulta.setInt(3, c.getResponsable().getID());
            consulta.setInt(4,c.getTipo_evento().getId_tipo_evento());
            
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                c.setId_evento(resultadoConsulta.getInt("id_evento"));
            }
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    public boolean editarEventoClinico(EventoClinico c){
        boolean resultado = false;
    
        try{
            //IMPLEMENTAR EL INSERT DE EVENTOS CADA VEZ QUE CAMBIA UN DATO
            //EventoClinico caballo = this.obtenerEventoClinico(c.getId_caballo());
            //------------------------------------------------------------
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE caballeriza.caballos " +
                  " SET otras_sennas=?,  estado=?, id_grupo_de_caballo=?" +
                  " WHERE id_evento=?; "
            );
//            int gC=c.getGrupo_de_caballos().getId_grupo_caballo();
//            String sennas=c.getOtras_sennas();
//            Blob foto=c.getFotografia();
//            String ESTADO = c.getEstado();
//            int id=c.getId_caballo();
            consulta.setString(1, c.getOtras_sennas());
            //consulta.setBlob(2, c.getFotografia());
            consulta.setString(2, c.getEstado());
            consulta.setInt(3,c.getGrupo_de_caballos().getId_grupo_caballo());
            consulta.setInt(4,c.getId_caballo());
            
            int xxx= consulta.executeUpdate();

            if ( consulta.executeUpdate() == 1){
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
    public boolean eliminarEventoClinico(int id_evento){
        boolean resultado = false;
    
        try{
            //IMPLEMENTAR EL INSERT DE EVENTOS CADA VEZ QUE CAMBIA UN DATO
            //EventoClinico caballo = this.obtenerEventoClinico(c.getId_caballo());
            //------------------------------------------------------------
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE caballeriza.caballos " +
                  " SET id_grupo_de_caballo=?," +
                  " WHERE id_evento=?; "
            );
            consulta.setInt(1,0);
            consulta.setInt(2,id_evento);

            if ( consulta.executeUpdate() == 1){
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
    public List<EventoClinico> obtenerEventosTipo(int id_grupo_de_caballo){
        List<EventoClinico> resultado = new ArrayList<EventoClinico>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.caballos where id_grupo_de_caballo = ?");
            consulta.setInt(1, id_grupo_de_caballo);
            ResultSet rs = consulta.executeQuery();
            TipoEventoDAO dao = new TipoEventoDAO();
            while(rs.next()){
                EventoClinico caballo = new EventoClinico();
                caballo.setId_caballo(rs.getInt("id_evento"));
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getInt("numero_microchip"));
                caballo.setGrupo_de_caballos(dao.obtenerTipoEvento(rs.getInt("id_grupo_de_caballo")));
                caballo.setFecha_ingreso(rs.getDate("fecha_nacimiento"));
                caballo.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                caballo.setSexo(rs.getString("sexo"));
                caballo.setColor(rs.getString("color"));
                caballo.setOtras_sennas(rs.getString("otras_sennas"));
                caballo.setEstado(rs.getString("estado"));
                caballo.setFotografia(rs.getBlob("fotografia"));
                resultado.add(caballo);
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
    public List<EventoClinico> obtenerEventosClinicosRestantes(){
        List<EventoClinico> resultado = new ArrayList<EventoClinico>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.caballos where id_grupo_de_caballo = ?");
            consulta.setInt(1, 0);
            ResultSet rs = consulta.executeQuery();
            TipoEventoDAO dao = new TipoEventoDAO();
            while(rs.next()){
                EventoClinico caballo = new EventoClinico();
                caballo.setId_caballo(rs.getInt("id_evento"));
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getInt("numero_microchip"));
                caballo.setGrupo_de_caballos(dao.obtenerTipoEvento(rs.getInt("id_grupo_de_caballo")));
                caballo.setFecha_ingreso(rs.getDate("fecha_nacimiento"));
                caballo.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                caballo.setSexo(rs.getString("sexo"));
                caballo.setColor(rs.getString("color"));
                caballo.setOtras_sennas(rs.getString("otras_sennas"));
                caballo.setEstado(rs.getString("estado"));
                caballo.setFotografia(rs.getBlob("fotografia"));
                resultado.add(caballo);
            }      
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }        
    public EventoClinico obtenerEventoClinico(int id_evento){
        EventoClinico caballo = new EventoClinico();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM caballeriza.caballos where id_evento = ?");
            consulta.setInt(1, id_evento);
            ResultSet rs = consulta.executeQuery();
            TipoEventoDAO dao = new TipoEventoDAO();
            if(rs.next()){
                caballo.setId_caballo(rs.getInt("id_evento"));
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getInt("numero_microchip"));
                caballo.setGrupo_de_caballos(dao.obtenerTipoEvento(rs.getInt("id_grupo_de_caballo")));
                caballo.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
                caballo.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                caballo.setSexo(rs.getString("sexo"));
                caballo.setColor(rs.getString("color"));
                caballo.setOtras_sennas(rs.getString("otras_sennas"));
                caballo.setEstado(rs.getString("estado"));
                caballo.setFotografia(rs.getBlob("fotografia"));
            }      
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return caballo;
    }     
    public List<EventoClinico> obtenerEventosClinicos(){
        List<EventoClinico> resultado = new ArrayList<EventoClinico>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.caballos ");
            ResultSet rs = consulta.executeQuery();
            TipoEventoDAO dao = new TipoEventoDAO();
            while(rs.next()){
                EventoClinico caballo = new EventoClinico();
                caballo.setId_caballo(rs.getInt("id_evento"));
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getInt("numero_microchip"));
                caballo.setGrupo_de_caballos(dao.obtenerTipoEvento(rs.getInt("id_grupo_de_caballo")));
                caballo.setFecha_ingreso(rs.getDate("fecha_nacimiento"));
                caballo.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                caballo.setSexo(rs.getString("sexo"));
                caballo.setColor(rs.getString("color"));
                caballo.setOtras_sennas(rs.getString("otras_sennas"));
                caballo.setEstado(rs.getString("estado"));
                caballo.setFotografia(rs.getBlob("fotografia"));
                resultado.add(caballo);
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
