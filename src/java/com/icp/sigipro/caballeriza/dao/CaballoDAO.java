/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.caballeriza.modelos.Caballo;
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
public class CaballoDAO {
    private Connection conexion;
    
    
    public CaballoDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    } 
    
    public boolean insertarCaballo(Caballo c){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO caballeriza.caballos (nombre, numero_microchip,fecha_nacimiento,fecha_ingreso,sexo,color,otras_sennas,estado,id_grupo_de_caballo) " +
                                                             " VALUES (?,?,?,?,?,?,?,?,?) RETURNING id_caballo");
            consulta.setString(1, c.getNombre());
            consulta.setInt(2, c.getNumero_microchip());
            consulta.setDate(3, c.getFecha_nacimiento());
            consulta.setDate(4,c.getFecha_ingreso());
            consulta.setString(5,c.getSexo());
            consulta.setString(6,c.getColor());
            consulta.setString(7, c.getOtras_sennas());
            consulta.setString(8, c.getEstado());
            consulta.setInt(9, c.getGrupo_de_caballos().getId_grupo_caballo());
            
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                c.setId_caballo(resultadoConsulta.getInt("id_caballo"));
            }
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    public boolean editarCaballo(Caballo c){
        boolean resultado = false;
    
        try{
            //IMPLEMENTAR EL INSERT DE EVENTOS CADA VEZ QUE CAMBIA UN DATO
            //Caballo caballo = this.obtenerCaballo(c.getId_caballo());
            //------------------------------------------------------------
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE caballeriza.caballos " +
                  " SET otras_sennas=?,  estado=?, id_grupo_de_caballo=?" +
                  " WHERE id_caballo=?; "
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
    public boolean eliminarCaballoDeGrupos(int id_caballo){
        boolean resultado = false;
    
        try{
            //IMPLEMENTAR EL INSERT DE EVENTOS CADA VEZ QUE CAMBIA UN DATO
            //Caballo caballo = this.obtenerCaballo(c.getId_caballo());
            //------------------------------------------------------------
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE caballeriza.caballos " +
                  " SET id_grupo_de_caballo=?," +
                  " WHERE id_caballo=?; "
            );
            consulta.setInt(1,0);
            consulta.setInt(2,id_caballo);

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
    public List<Caballo> obtenerCaballosGrupo(int id_grupo_de_caballo){
        List<Caballo> resultado = new ArrayList<Caballo>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.caballos where id_grupo_de_caballo = ?");
            consulta.setInt(1, id_grupo_de_caballo);
            ResultSet rs = consulta.executeQuery();
            GrupoDeCaballosDAO dao = new GrupoDeCaballosDAO();
            while(rs.next()){
                Caballo caballo = new Caballo();
                caballo.setId_caballo(rs.getInt("id_caballo"));
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getInt("numero_microchip"));
                caballo.setGrupo_de_caballos(dao.obtenerGrupoDeCaballos(rs.getInt("id_grupo_de_caballo")));
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
    public List<Caballo> obtenerCaballosRestantes(){
        List<Caballo> resultado = new ArrayList<Caballo>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.caballos where id_grupo_de_caballo = ?");
            consulta.setInt(1, 0);
            ResultSet rs = consulta.executeQuery();
            GrupoDeCaballosDAO dao = new GrupoDeCaballosDAO();
            while(rs.next()){
                Caballo caballo = new Caballo();
                caballo.setId_caballo(rs.getInt("id_caballo"));
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getInt("numero_microchip"));
                caballo.setGrupo_de_caballos(dao.obtenerGrupoDeCaballos(rs.getInt("id_grupo_de_caballo")));
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
    public Caballo obtenerCaballo(int id_caballo){
        Caballo caballo = new Caballo();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM caballeriza.caballos where id_caballo = ?");
            consulta.setInt(1, id_caballo);
            ResultSet rs = consulta.executeQuery();
            GrupoDeCaballosDAO dao = new GrupoDeCaballosDAO();
            if(rs.next()){
                caballo.setId_caballo(rs.getInt("id_caballo"));
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getInt("numero_microchip"));
                caballo.setGrupo_de_caballos(dao.obtenerGrupoDeCaballos(rs.getInt("id_grupo_de_caballo")));
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
    public List<Caballo> obtenerCaballos(){
        List<Caballo> resultado = new ArrayList<Caballo>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.caballos ");
            ResultSet rs = consulta.executeQuery();
            GrupoDeCaballosDAO dao = new GrupoDeCaballosDAO();
            while(rs.next()){
                Caballo caballo = new Caballo();
                caballo.setId_caballo(rs.getInt("id_caballo"));
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getInt("numero_microchip"));
                caballo.setGrupo_de_caballos(dao.obtenerGrupoDeCaballos(rs.getInt("id_grupo_de_caballo")));
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
