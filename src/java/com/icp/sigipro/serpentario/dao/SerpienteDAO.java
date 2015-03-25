/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.serpentario.modelos.Serpiente;
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
public class SerpienteDAO {
    private Connection conexion;
    
    
    public SerpienteDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    } 
    
    public boolean insertarSerpiente(Serpiente s){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.serpientes (id_especie, fecha_ingreso,localidad_origen,colectada,recibida,sexo,talla_cabeza,talla_cola,peso) " +
                                                             " VALUES (?,?,?,?,?,?,?,?,?) RETURNING id_serpiente");
            consulta.setInt(1, s.getEspecie().getId_especie());
            consulta.setDate(2, s.getFecha_ingreso());
            consulta.setString(3, s.getLocalidad_origen());
            consulta.setString(4,s.getColectada());
            consulta.setString(5,s.getRecibida().getNombre_usuario());
            consulta.setString(6,s.getSexo());
            consulta.setFloat(7, s.getTalla_cabeza());
            consulta.setFloat(8, s.getTalla_cola());
            consulta.setFloat(9, s.getPeso());
            
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                s.setId_serpiente(resultadoConsulta.getInt("id_serpiente"));
            }
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public int obtenerProximoId(){
        boolean resultado = false;
        int nextval = 0;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT last_value FROM serpentario.serpientes_id_serpiente_seq;");
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()){
                resultado=true;
                int currval = resultadoConsulta.getInt("last_value");
                nextval = currval + 1;
            }
            consulta.close();
            conexion.close();
        }catch (Exception e){
            
        }
        return nextval;
    }
    
    public boolean eliminarSerpiente(int id_serpiente) throws SIGIPROException{
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM serpentario.serpientes " +
                    " WHERE id_serpiente=?; "
            );
            consulta.setInt(1, id_serpiente);
            if ( consulta.executeUpdate() == 1){
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }
        catch(SQLException ex){
            throw new SIGIPROException("Serpiente no pudo ser eliminada debido a que una o más objetos se encuentran asociadas a esta.");
        }
        return resultado;
    }  
    
    public boolean editarSerpiente(Serpiente s){
        boolean resultado = false;
    
        try{
            //IMPLEMENTAR EL INSERT DE EVENTOS CADA VEZ QUE CAMBIA UN DATO
            Serpiente serpiente = this.obtenerSerpiente(s.getId_serpiente());
            //------------------------------------------------------------
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE serpentario.serpientes " +
                  " SET sexo=?, talla_cabeza=?, talla_cola=?,peso=?,imagen=? " +
                  " WHERE id_serpiente=?; "
            );
            consulta.setString(1,s.getSexo());
            consulta.setFloat(2, s.getTalla_cabeza());
            consulta.setFloat(3, s.getTalla_cola());
            consulta.setFloat(4, s.getPeso());
            consulta.setBlob(5,s.getImagen());
            consulta.setInt(6, s.getId_serpiente());

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
  
    public Serpiente obtenerSerpiente(int id_serpiente){
        Serpiente serpiente = new Serpiente();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM serpentario.serpientes where id_serpiente = ?");
            consulta.setInt(1, id_serpiente);
            ResultSet rs = consulta.executeQuery();
            EspecieDAO dao = new EspecieDAO();
            if(rs.next()){
                serpiente.setId_serpiente(rs.getInt("id_serpiente"));
                serpiente.setEspecie(dao.obtenerEspecie(rs.getInt("id_especie")));
                serpiente.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                serpiente.setLocalidad_origen(rs.getString("localidad_origen"));
                serpiente.setColectada(rs.getString("colectada"));
                UsuarioDAO usuariodao = new UsuarioDAO();
                
                serpiente.setRecibida(usuariodao.obtenerUsuario(rs.getString("recibida")));
                serpiente.setSexo(rs.getString("sexo"));
                serpiente.setTalla_cabeza(rs.getFloat("talla_cabeza"));
                serpiente.setTalla_cola(rs.getFloat("talla_cola"));
                serpiente.setPeso(rs.getFloat("peso"));
                serpiente.setImagen(rs.getBlob("imagen"));
            }      
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return serpiente;
    }
  
    public List<Serpiente> obtenerSerpientes(){
        List<Serpiente> resultado = new ArrayList<Serpiente>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.serpientes ");
            ResultSet rs = consulta.executeQuery();
            EspecieDAO dao = new EspecieDAO();
            while(rs.next()){
                Serpiente serpiente = new Serpiente();
                serpiente.setId_serpiente(rs.getInt("id_serpiente"));
                serpiente.setEspecie(dao.obtenerEspecie(rs.getInt("id_especie")));
                serpiente.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                serpiente.setLocalidad_origen(rs.getString("localidad_origen"));
                serpiente.setColectada(rs.getString("colectada"));
                UsuarioDAO usuariodao = new UsuarioDAO();
                
                serpiente.setRecibida(usuariodao.obtenerUsuario(rs.getString("recibida")));
                serpiente.setSexo(rs.getString("sexo"));
                serpiente.setTalla_cabeza(rs.getFloat("talla_cabeza"));
                serpiente.setTalla_cola(rs.getFloat("talla_cola"));
                serpiente.setPeso(rs.getFloat("peso"));
                serpiente.setImagen(rs.getBlob("imagen"));
                resultado.add(serpiente);
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
