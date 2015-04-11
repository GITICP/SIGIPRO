/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bodegas.modelos.UbicacionBodega;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.serpentario.modelos.Especie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class EspecieDAO {
    private Connection conexion;
    
    
    public EspecieDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    } 
    
    public boolean insertarEspecie(Especie e){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.especies (genero, especie) " +
                                                             " VALUES (?,?) RETURNING id_especie");
            consulta.setString(1, e.getGenero());
            consulta.setString(2, e.getEspecie());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                e.setId_especie(resultadoConsulta.getInt("id_especie"));
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
    
    public boolean eliminarEspecie(int id_especie) throws SIGIPROException{
        boolean resultado = false;
        try{
            PreparedStatement consultaVeneno = getConexion().prepareStatement(
                    " DELETE FROM serpentario.venenos " +
                    " WHERE id_especie=?; "
            );
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM serpentario.especies " +
                    " WHERE id_especie=?; "
            );
            consultaVeneno.setInt(1, id_especie);
            consulta.setInt(1, id_especie);
            if ( consultaVeneno.executeUpdate() == 1){
                if(consulta.executeUpdate() == 1){
                    resultado = true;
                }
                
            }
            consulta.close();
            conexion.close();
        }
        catch(SQLException ex){
            throw new SIGIPROException("Ubicación no pudo ser eliminada debido a que una o más serpientes se encuentran asociadas a esta.");
        }
        return resultado;
    }  
    
    public boolean editarEspecie(Especie e){
        boolean resultado = false;
    
        try{
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE serpentario.especies " +
                  " SET genero=?, especie=? " +
                  " WHERE id_especie=?; "

          );

            consulta.setString(1, e.getGenero());
            consulta.setString(2, e.getEspecie());
            consulta.setInt(3, e.getId_especie());

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
  
    public Especie obtenerEspecie(int id_especie){
        Especie especie = new Especie();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM serpentario.especies where id_especie = ?");
            consulta.setInt(1, id_especie);
            ResultSet rs = consulta.executeQuery();
            if(rs.next()){
                especie.setId_especie(rs.getInt("id_especie"));
                especie.setGenero(rs.getString("genero"));
                especie.setEspecie(rs.getString("especie"));
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return especie;
    }
  
    public List<Especie> obtenerEspecies(){
        List<Especie> resultado = new ArrayList<Especie>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.especies ");
            ResultSet rs = consulta.executeQuery();
            while(rs.next()){
                Especie especie = new Especie();
                especie.setId_especie(rs.getInt("id_especie"));
                especie.setGenero(rs.getString("genero"));
                especie.setEspecie(rs.getString("especie"));
                resultado.add(especie);
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
