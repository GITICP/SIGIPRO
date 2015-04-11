/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.serpentario.modelos.ColeccionHumeda;
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
public class ColeccionHumedaDAO {
    private Connection conexion;
    
    
    public ColeccionHumedaDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    } 
    
    
    
    public boolean insertarSerpiente(ColeccionHumeda ch){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.coleccion_humeda (id_serpiente, proposito,observaciones,id_usuario,numero_coleccion_humeda) " +
                                                             " VALUES (?,?,?,?,?) RETURNING id_coleccion_humeda");
            consulta.setInt(1, ch.getSerpiente().getId_serpiente());
            consulta.setString(2, ch.getProposito());
            consulta.setString(3, ch.getObservaciones());
            consulta.setInt(4,ch.getUsuario().getId_usuario());
            consulta.setInt(5,ch.getNumero_coleccion_humeda());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                ch.setId_coleccion_humeda(resultadoConsulta.getInt("id_coleccion_humeda"));
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            return false;
        }
        return resultado;
    }

    public boolean editarColeccionHumeda(ColeccionHumeda ch){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE serpentario.coleccion_humeda " +
                  " SET proposito=?, observaciones=? " +
                  " WHERE id_coleccion_humeda=?; ");

            consulta.setString(2, ch.getObservaciones());
            consulta.setString(1, ch.getProposito());
            consulta.setInt(3, ch.getId_coleccion_humeda());

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
    
    public ColeccionHumeda obtenerColeccionHumeda(Serpiente serpiente){
        ColeccionHumeda ch = new ColeccionHumeda();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM serpentario.coleccion_humeda where id_serpiente = ?");
            consulta.setInt(1, serpiente.getId_serpiente());
            ResultSet rs = consulta.executeQuery();
            UsuarioDAO usuariodao = new UsuarioDAO();
            if(rs.next()){
                ch.setId_coleccion_humeda(rs.getInt("id_coleccion_humeda"));
                ch.setNumero_coleccion_humeda(rs.getInt("numero_coleccion_humeda"));
                ch.setSerpiente(serpiente);
                ch.setProposito(rs.getString("proposito"));
                ch.setObservaciones(rs.getString("observaciones"));
                ch.setUsuario(usuariodao.obtenerUsuario(rs.getInt("id_usuario")));
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return ch;
    }
    
    public List<ColeccionHumeda> obtenerColeccionesHumedas(){
        List<ColeccionHumeda> chs = new ArrayList<ColeccionHumeda>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM serpentario.coleccion_humeda;");
            ResultSet rs = consulta.executeQuery();
            UsuarioDAO usuariodao = new UsuarioDAO();
            SerpienteDAO serpientedao = new SerpienteDAO();
            System.out.println(consulta);
            while(rs.next()){
                ColeccionHumeda ch = new ColeccionHumeda();
                ch.setId_coleccion_humeda(rs.getInt("id_coleccion_humeda"));
                ch.setNumero_coleccion_humeda(rs.getInt("numero_coleccion_humeda"));
                Serpiente serpiente = serpientedao.obtenerSerpiente(rs.getInt("id_serpiente"));
                ch.setSerpiente(serpiente);
                ch.setProposito(rs.getString("proposito"));
                ch.setObservaciones(rs.getString("observaciones"));
                ch.setUsuario(usuariodao.obtenerUsuario(rs.getInt("id_usuario")));
                chs.add(ch);
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return chs;
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
