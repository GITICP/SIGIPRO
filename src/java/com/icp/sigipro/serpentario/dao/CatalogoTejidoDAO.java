/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.serpentario.modelos.CatalogoTejido;
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
public class CatalogoTejidoDAO {
    private Connection conexion;
    
    
    public CatalogoTejidoDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    } 
    
    public boolean insertarSerpiente(CatalogoTejido ct){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.catalogo_tejido (id_serpiente, numero_caja,posicion,observaciones,estado,id_usuario,numero_catalogo_tejido) " +
                                                             " VALUES (?,?,?,?,?,?,?) RETURNING id_catalogo_tejido");
            consulta.setInt(1, ct.getSerpiente().getId_serpiente());
            consulta.setString(2, ct.getNumero_caja());
            consulta.setString(3, ct.getPosicion());
            consulta.setString(4, ct.getObservaciones());
            consulta.setString(5, ct.getEstado());
            consulta.setInt(6,ct.getUsuario().getId_usuario());
            consulta.setInt(7,ct.getNumero_catalogo_tejido());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                ct.setId_catalogo_tejido(resultadoConsulta.getInt("id_catalogo_tejido"));
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return resultado;
    }
    
    public CatalogoTejido obtenerCatalogoTejido(Serpiente serpiente){
        CatalogoTejido ct = new CatalogoTejido();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM serpentario.catalogo_tejido where id_serpiente = ?");
            consulta.setInt(1, serpiente.getId_serpiente());
            ResultSet rs = consulta.executeQuery();
            UsuarioDAO usuariodao = new UsuarioDAO();
            if(rs.next()){
                ct.setId_catalogo_tejido(rs.getInt("id_catalogo_tejido"));
                ct.setNumero_catalogo_tejido(rs.getInt("numero_catalogo_tejido"));
                ct.setSerpiente(serpiente);
                ct.setNumero_caja(rs.getString("numero_caja"));
                ct.setObservaciones(rs.getString("observaciones"));
                ct.setEstado(rs.getString("estado"));
                ct.setPosicion(rs.getString("posicion"));
                ct.setUsuario(usuariodao.obtenerUsuario(rs.getInt("id_usuario")));
            }else{
                ct = null;
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return ct;
    }
    
    public List<CatalogoTejido> obtenerCatalogosTejidos(){
        List<CatalogoTejido> cts = new ArrayList<CatalogoTejido>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM serpentario.catalogo_tejido;");
            ResultSet rs = consulta.executeQuery();
            UsuarioDAO usuariodao = new UsuarioDAO();
            SerpienteDAO serpientedao = new SerpienteDAO();
            while(rs.next()){
                CatalogoTejido ct = new CatalogoTejido();
                ct.setId_catalogo_tejido(rs.getInt("id_catalogo_tejido"));
                ct.setNumero_catalogo_tejido(rs.getInt("numero_catalogo_tejido"));
                Serpiente serpiente = serpientedao.obtenerSerpiente(rs.getInt("id_serpiente"));
                ct.setSerpiente(serpiente);
                ct.setNumero_caja(rs.getString("numero_caja"));
                ct.setObservaciones(rs.getString("observaciones"));
                ct.setEstado(rs.getString("estado"));
                ct.setPosicion(rs.getString("posicion"));
                ct.setUsuario(usuariodao.obtenerUsuario(rs.getInt("id_usuario")));
                cts.add(ct);
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return cts;
    }

    public boolean editarCatalogoTejido(CatalogoTejido ct){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE serpentario.catalogo_tejido " +
                  " SET numero_caja=?, posicion=?, observaciones=?,estado=? " +
                  " WHERE id_catalogo_tejido=?; ");

            consulta.setString(1, ct.getNumero_caja());
            consulta.setString(2, ct.getPosicion());
            consulta.setString(3, ct.getObservaciones());
            consulta.setString(4, ct.getEstado());
            consulta.setInt(5, ct.getId_catalogo_tejido());

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
