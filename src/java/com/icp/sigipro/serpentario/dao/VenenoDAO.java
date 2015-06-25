/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.serpentario.modelos.Especie;
import com.icp.sigipro.serpentario.modelos.Veneno;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class VenenoDAO extends DAO{    
    
    public VenenoDAO()
    {
    } 
    
    public int insertarVeneno(Especie e){
        int resultado =0;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.venenos (id_especie, restriccion) " +
                                                             " VALUES (?,false) RETURNING id_veneno");
            consulta.setInt(1, e.getId_especie());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = resultadoConsulta.getInt("id_veneno");
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    } 
    
    public boolean editarVeneno(Veneno v){
        boolean resultado = false;
    
        try{
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE serpentario.venenos " +
                  " SET restriccion=?, cantidad_minima=? " +
                  " WHERE id_veneno=?; "

          );

            consulta.setBoolean(1, v.isRestriccion());
            consulta.setFloat(2, v.getCantidad_minima());
            consulta.setInt(3, v.getId_veneno());

            if ( consulta.executeUpdate() == 1){
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public Veneno obtenerVeneno(int id_veneno){
        Veneno veneno = new Veneno();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM serpentario.venenos where id_veneno = ?");
            consulta.setInt(1, id_veneno);
            ResultSet rs = consulta.executeQuery();
            EspecieDAO especiedao = new EspecieDAO();
            if(rs.next()){
                veneno.setId_veneno(rs.getInt("id_veneno"));
                veneno.setCantidad_minima(rs.getFloat("cantidad_minima"));
                veneno.setEspecie(especiedao.obtenerEspecie(rs.getInt("id_especie")));
                veneno.setCantidad(obtenerCantidad(veneno));
                veneno.setRestriccion(rs.getBoolean("restriccion"));
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return veneno;
    }
  
    public List<Veneno> obtenerVenenos(){
        List<Veneno> resultado = new ArrayList<Veneno>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.venenos ");
            ResultSet rs = consulta.executeQuery();
            EspecieDAO especiedao = new EspecieDAO();
            while(rs.next()){
                Veneno veneno = new Veneno();
                veneno.setId_veneno(rs.getInt("id_veneno"));
                veneno.setCantidad_minima(rs.getFloat("cantidad_minima"));
                veneno.setEspecie(especiedao.obtenerEspecie(rs.getInt("id_especie")));
                veneno.setRestriccion(rs.getBoolean("restriccion"));
                veneno.setCantidad(obtenerCantidad(veneno));
                resultado.add(veneno);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    

    public float obtenerCantidad(Veneno v){
        float respuesta = 0;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT extraccion.id_especie, sum(peso_recuperado) as cantidad "
                    + "FROM serpentario.extraccion as extraccion INNER JOIN serpentario.especies as especie ON especie.id_especie=extraccion.id_especie "
                    + "INNER JOIN serpentario.liofilizacion as liofilizacion ON liofilizacion.id_extraccion=extraccion.id_extraccion "
                    + "WHERE extraccion.id_especie = ? "
                    + "GROUP BY extraccion.id_especie;");
            consulta.setInt(1, v.getEspecie().getId_especie());
            ResultSet rs = consulta.executeQuery();
            float cantidad_entregada = this.obtenerCantidadEntregada(v.getEspecie().getId_especie());
            while (rs.next()){
                float cantidad_original = rs.getFloat("cantidad");
                respuesta = (float) (cantidad_original - (cantidad_entregada*0.001));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return respuesta;
    }
    
    public float obtenerCantidadEntregada(int id_especie){
         float resultado = 0;
         try{
             PreparedStatement consulta = getConexion().prepareStatement("SELECT lote.id_especie, sum(entrega.cantidad) as cantidad_entregada " +
                                    "FROM serpentario.lote as lote LEFT OUTER JOIN serpentario.lotes_entregas_solicitud as entrega ON lote.id_lote = entrega.id_lote " +
                                    "WHERE lote.id_especie=? " +
                                    "GROUP BY lote.id_especie");
             
             consulta.setInt(1, id_especie);
             ResultSet resultadoConsulta = consulta.executeQuery();
             if (resultadoConsulta.next()){
                 resultado = resultadoConsulta.getFloat("cantidad_entregada");
             }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
         }catch (Exception e){
             e.printStackTrace();
         }
         
         return resultado;
     }
}
