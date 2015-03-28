/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.serpentario.modelos.Especie;
import com.icp.sigipro.serpentario.modelos.Extraccion;
import com.icp.sigipro.serpentario.modelos.Lote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class LoteDAO {
    private Connection conexion;
    
    
    public LoteDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    } 

    public boolean insertarLote(Lote l){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.lote (id_especie) " +
                                                             " VALUES (?) RETURNING id_lote");
            consulta.setInt(1, l.getEspecie().getId_especie());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                l.setId_lote(resultadoConsulta.getInt("id_lote"));
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
    
     public int obtenerProximoId(){
        boolean resultado = false;
        int nextval = 0;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT last_value FROM serpentario.lote_id_lote_seq;");
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()){
                resultado=true;
                int currval = resultadoConsulta.getInt("last_value");
                if (currval==1){
                    nextval = currval;
                }else{
                    nextval = currval + 1;
                }
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
        }catch (Exception e){
            
        }
        return nextval;
    }
    
    public boolean insertarExtracciones(Lote l, String[] extracciones){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("UPDATE serpentario.extraccion "
                    + "SET id_lote=? "
                    + "WHERE id_extraccion=?");
            consulta.setInt(1, l.getId_lote());
            for (String i:extracciones){
                consulta.setInt(2,Integer.parseInt(i));
                System.out.println(consulta);
                resultado = consulta.executeUpdate() == 1;
            }
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public List<Extraccion> obtenerExtracciones(Especie especie){
        List<Extraccion> resultado = new ArrayList<Extraccion>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_extraccion, numero_extraccion FROM serpentario.extraccion WHERE id_lote is null and id_especie=?; ");
            consulta.setInt(1,especie.getId_especie());
            ResultSet rs = consulta.executeQuery();
            ExtraccionDAO extracciondao = new ExtraccionDAO();
            while(rs.next()){
                Extraccion extraccion = new Extraccion();
                extraccion.setId_extraccion(rs.getInt("id_extraccion"));
                extraccion.setNumero_extraccion(rs.getString("numero_extraccion"));
                if (extracciondao.validarIsLiofilizacionFin(extraccion)){
                    resultado.add(extraccion);
                }
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
    
    public List<Extraccion> obtenerExtracciones(Lote lote){
        List<Extraccion> resultado = new ArrayList<Extraccion>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_extraccion, numero_extraccion FROM serpentario.extraccion WHERE id_lote=?; ");
            consulta.setInt(1,lote.getId_lote());
            ResultSet rs = consulta.executeQuery();
            while(rs.next()){
                Extraccion extraccion = new Extraccion();
                extraccion.setId_extraccion(rs.getInt("id_extraccion"));
                extraccion.setNumero_extraccion(rs.getString("numero_extraccion"));
                resultado.add(extraccion);
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
    
    public List<Lote> obtenerLotes(){
        List<Lote> resultado = new ArrayList<Lote>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT lote.id_lote, lote.id_especie, sum(peso_recuperado) as cantidad_total "
                    + "FROM (serpentario.lote as lote LEFT OUTER JOIN serpentario.extraccion as extraccion ON lote.id_lote = extraccion.id_lote) "
                    + "LEFT OUTER JOIN serpentario.liofilizacion as liofilizacion ON extraccion.id_extraccion = liofilizacion.id_extraccion "
                    + "GROUP BY lote.id_lote;");
            ResultSet rs = consulta.executeQuery();
            EspecieDAO especiedao = new EspecieDAO();
            while(rs.next()){
                Lote lote = new Lote();
                lote.setId_lote(rs.getInt("id_lote"));
                lote.setEspecie(especiedao.obtenerEspecie(rs.getInt("id_especie")));
                try{
                    lote.setCantidad_total(rs.getFloat("cantidad_total"));
                }catch (Exception e){
                    lote.setCantidad_total(0);
                }
                resultado.add(lote);
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
    
    public Lote obtenerLote(int id_lote){
        Lote resultado = new Lote();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * "
                    + "FROM serpentario.lote WHERE id_lote = ?; ");
            consulta.setInt(1, id_lote);
            ResultSet rs = consulta.executeQuery();
            EspecieDAO especiedao = new EspecieDAO();
            while(rs.next()){
                resultado.setId_lote(rs.getInt("id_lote"));
                resultado.setEspecie(especiedao.obtenerEspecie(rs.getInt("id_especie")));
            }
            resultado.setExtracciones(obtenerExtracciones(resultado));
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
