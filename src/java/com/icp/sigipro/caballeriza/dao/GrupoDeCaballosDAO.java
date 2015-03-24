/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.caballeriza.modelos.GrupoDeCaballos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walter
 */
public class GrupoDeCaballosDAO {
    private Connection conexion;
    
    
    public GrupoDeCaballosDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    } 
    
    public boolean insertarGrupoDeCaballos(GrupoDeCaballos g){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO caballeriza.grupos_de_caballos (nombre, descripcion) " +
                                                             " VALUES (?,?) RETURNING id_grupo_de_caballo");
            consulta.setString(1, g.getNombre());
            consulta.setString(2, g.getDescripcion());
            
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                g.setId_grupo_caballo(resultadoConsulta.getInt("id_grupo_caballo"));
            }
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    public List<GrupoDeCaballos> obtenerGruposDeCaballos(){
        List<GrupoDeCaballos> resultado = new ArrayList<GrupoDeCaballos>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("select * from caballeriza.grupos_de_caballos");
            ResultSet rs = consulta.executeQuery();
            while(rs.next()){
                GrupoDeCaballos grupo = new GrupoDeCaballos();
                grupo.setId_grupo_caballo(rs.getInt("id_grupo_de_caballo"));
                grupo.setNombre(rs.getString("nombre"));
                grupo.setDescripcion(rs.getString("descripcion"));
                resultado.add(grupo);
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
