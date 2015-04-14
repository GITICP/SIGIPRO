/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.basededatos.SingletonBD;
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
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.lote (id_especie,numero_lote) " +
                                                             " VALUES (?,?) RETURNING id_lote");
            consulta.setInt(1, l.getEspecie().getId_especie());
            consulta.setString(2, l.getNumero_lote());
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
    /* No se usa por ahora
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
                    List<Lote> lotes = this.obtenerLotes();
                    if (lotes == null){
                        nextval = currval;
                    }else{
                        nextval = currval + 1;
                    }
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
    */
     public float obtenerCantidadSolicitada(int id_lote){
         float resultado = 0;
         try{
             PreparedStatement consulta = getConexion().prepareStatement("SELECT lote.id_lote, sum(entrega.cantidad) as cantidad_actual " +
                                "FROM serpentario.lote as lote LEFT OUTER JOIN serpentario.lotes_entregas_solicitud as entrega ON lote.id_lote = entrega.id_lote " +
                                "WHERE lote.id_lote=? " +
                                "GROUP BY lote.id_lote; ");
             
             consulta.setInt(1, id_lote);
             ResultSet resultadoConsulta = consulta.executeQuery();
             if (resultadoConsulta.next()){
                 resultado = resultadoConsulta.getFloat("cantidad_actual");
             }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
         }catch (Exception e){
             
         }
         
         return resultado;
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
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT lote.id_lote,lote.numero_lote, lote.id_especie, sum(peso_recuperado) as cantidad_original "
                    + "FROM (serpentario.lote as lote LEFT OUTER JOIN serpentario.extraccion as extraccion ON lote.id_lote = extraccion.id_lote) "
                    + "LEFT OUTER JOIN serpentario.liofilizacion as liofilizacion ON extraccion.id_extraccion = liofilizacion.id_extraccion "
                    + "GROUP BY lote.id_lote;");
            ResultSet rs = consulta.executeQuery();
            EspecieDAO especiedao = new EspecieDAO();
            while(rs.next()){
                Lote lote = new Lote();
                int id_lote = rs.getInt("id_lote");
                lote.setId_lote(id_lote);
                lote.setNumero_lote(rs.getString("numero_lote"));
                lote.setEspecie(especiedao.obtenerEspecie(rs.getInt("id_especie")));
                try{
                    float cantidad_original = rs.getFloat("cantidad_original");
                    lote.setCantidad_original(cantidad_original);
                    float cantidad_entregada = this.obtenerCantidadSolicitada(id_lote);
                    float cantidad_actual = cantidad_original - cantidad_entregada;
                    lote.setCantidad_actual(cantidad_actual);
                }catch (Exception e){
                    lote.setCantidad_original(0);
                    lote.setCantidad_actual(0);
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
    
    public List<Lote> obtenerLotes(Especie e){
        List<Lote> resultado = new ArrayList<Lote>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT lote.id_lote,lote.numero_lote, lote.id_especie, sum(peso_recuperado) as cantidad_original "
                    + "FROM (serpentario.lote as lote LEFT OUTER JOIN serpentario.extraccion as extraccion ON lote.id_lote = extraccion.id_lote) "
                    + "LEFT OUTER JOIN serpentario.liofilizacion as liofilizacion ON extraccion.id_extraccion = liofilizacion.id_extraccion "
                    + "WHERE lote.id_especie = ? "
                    + "GROUP BY lote.id_lote;");
            consulta.setInt(1, e.getId_especie());
            ResultSet rs = consulta.executeQuery();
            while(rs.next()){
                Lote lote = new Lote();
                int id_lote = rs.getInt("id_lote");
                lote.setId_lote(id_lote);          
                lote.setNumero_lote(rs.getString("numero_lote"));
                lote.setEspecie(e);
                float cantidad_original = rs.getFloat("cantidad_original");
                lote.setCantidad_original(cantidad_original);
                float cantidad_entregada = this.obtenerCantidadSolicitada(id_lote);
                float cantidad_actual = cantidad_original - cantidad_entregada;
                if (cantidad_actual != 0.0){
                    lote.setCantidad_actual(cantidad_actual);
                    resultado.add(lote);
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
    
    public Lote obtenerLote(int id_lote){
        Lote resultado = new Lote();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT lote.id_lote,lote.numero_lote, lote.id_especie, sum(peso_recuperado) as cantidad_original "
                    + "FROM (serpentario.lote as lote LEFT OUTER JOIN serpentario.extraccion as extraccion ON lote.id_lote = extraccion.id_lote) "
                    + "LEFT OUTER JOIN serpentario.liofilizacion as liofilizacion ON extraccion.id_extraccion = liofilizacion.id_extraccion "
                    + "WHERE lote.id_lote = ? "
                    + "GROUP BY lote.id_lote;");
            consulta.setInt(1, id_lote);
            ResultSet rs = consulta.executeQuery();
            EspecieDAO especiedao = new EspecieDAO();
            while(rs.next()){
                Lote lote = new Lote();
                lote.setId_lote(id_lote);
                lote.setNumero_lote(rs.getString("numero_lote"));
                lote.setEspecie(especiedao.obtenerEspecie(rs.getInt("id_especie")));
                try{
                    float cantidad_original = rs.getFloat("cantidad_original");
                    lote.setCantidad_original(cantidad_original);
                    float cantidad_entregada = this.obtenerCantidadSolicitada(id_lote);
                    float cantidad_actual = cantidad_original - cantidad_entregada;
                    lote.setCantidad_actual(cantidad_actual);
                }catch (Exception e){
                    System.out.println(e.getStackTrace());
                    lote.setCantidad_original(0);
                    lote.setCantidad_actual(0);
                }
                resultado = lote;
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
