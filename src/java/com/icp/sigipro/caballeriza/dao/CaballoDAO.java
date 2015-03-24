/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
            consulta.setBoolean(8, c.isEstado());
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
