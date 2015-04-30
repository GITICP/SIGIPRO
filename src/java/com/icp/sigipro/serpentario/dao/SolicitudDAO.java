/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.serpentario.modelos.EntregasSolicitud;
import com.icp.sigipro.serpentario.modelos.Lote;
import com.icp.sigipro.serpentario.modelos.LotesEntregasSolicitud;
import com.icp.sigipro.serpentario.modelos.Solicitud;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class SolicitudDAO {
    private Connection conexion;
    
    
    public SolicitudDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }     
    
    public boolean insertarSolicitud(Solicitud s){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.solicitudes (fecha_solicitud, id_especie, cantidad, id_usuario,proyecto, estado) " +
                                                             " VALUES (?,?,?,?,?,?) RETURNING id_solicitud");
            consulta.setDate(1, s.getFecha_solicitud());
            consulta.setInt(2, s.getEspecie().getId_especie());
            consulta.setFloat(3, s.getCantidad());
            consulta.setInt(4, s.getUsuario().getId_usuario());
            consulta.setString(5,s.getProyecto());
            consulta.setString(6,s.getEstado());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                s.setId_solicitud(resultadoConsulta.getInt("id_solicitud"));
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
    
    public boolean insertarEntrega(EntregasSolicitud e){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.entregas_solicitud (id_solicitud, id_usuario_entrega, fecha_entrega, cantidad_entregada,id_usuario_recibo) " +
                                                             " VALUES (?,?,?,?,?) RETURNING id_entrega");
            consulta.setInt(1, e.getSolicitud().getId_solicitud());
            consulta.setInt(2, e.getUsuario_entrega().getId_usuario());
            consulta.setDate(3, e.getFecha_entrega());
            consulta.setFloat(4, e.getCantidad_entregada());
            consulta.setInt(5,e.getUsuario_recibo().getId_usuario());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                e.setId_entrega(resultadoConsulta.getInt("id_entrega"));
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
    
    public boolean insertarLotesEntrega(List<LotesEntregasSolicitud> les){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.lotes_entregas_solicitud (id_entrega, id_lote, cantidad) " +
                                                             " VALUES (?,?,?); ");
            for (LotesEntregasSolicitud i : les){
                consulta.setInt(1, i.getEntrega_solicitud().getId_entrega());
                consulta.setInt(2, i.getLote().getId_lote());
                consulta.setFloat(3,i.getCantidad());
                if ( consulta.executeUpdate()==1 ){
                    resultado = true;
                }
            }
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public boolean editarSolicitud(Solicitud s) {

    boolean resultado = false;

    try {
      PreparedStatement consulta = getConexion().prepareStatement(
              " UPDATE serpentario.solicitudes "
              + "SET cantidad=?, estado=?, proyecto=?, observaciones=? "
              + "WHERE id_solicitud=?; "
      );

      consulta.setFloat(1, s.getCantidad());
      consulta.setString(2, s.getEstado());
      consulta.setString(3, s.getProyecto());
      consulta.setString(4, s.getObservaciones());
      consulta.setInt(5, s.getId_solicitud());

      if (consulta.executeUpdate() == 1) {
        resultado = true;
      }
      
      consulta.close();
      conexion.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return resultado;
  }
    public List<Lote> parsearLotes(String lotes) {
        List<Lote> resultado = null;
        try {
          resultado = new ArrayList<Lote>();
          List<String> lotesParcial = new LinkedList<String>(Arrays.asList(lotes.split("#r#")));
          lotesParcial.remove("");

          LoteDAO lotedao = new LoteDAO();

          for (String i : lotesParcial) {
            String[] lote = i.split("#c#");
            Lote l = lotedao.obtenerLote(Integer.parseInt(lote[0]));
            resultado.add(l);
          }
        } catch (Exception ex) {
          ex.printStackTrace();
          resultado = null;
        }
        return resultado;
      }
    
    public boolean eliminarSolicitud(int id_solicitud) throws SIGIPROException{
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM serpentario.solicitudes " +
                    " WHERE id_solicitud=?; "
            );
            consulta.setInt(1, id_solicitud);
            if ( consulta.executeUpdate() == 1){
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }
        catch(SQLException ex){
            throw new SIGIPROException("Solicitud no pudo ser eliminada.");
        }
        return resultado;
    }  
    
 public Solicitud obtenerSolicitud(int id_solicitud){
        Solicitud solicitud = new Solicitud();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM serpentario.solicitudes where id_solicitud = ?");
            consulta.setInt(1, id_solicitud);
            ResultSet rs = consulta.executeQuery();
            EspecieDAO especiedao=new EspecieDAO();
            UsuarioDAO usuariodao = new UsuarioDAO();
            if(rs.next()){
                solicitud.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud.setCantidad(rs.getFloat("cantidad"));
                solicitud.setEspecie(especiedao.obtenerEspecie(rs.getInt("id_especie")));
                solicitud.setUsuario(usuariodao.obtenerUsuario(rs.getInt("id_usuario")));
                solicitud.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                solicitud.setEstado(rs.getString("estado"));
                solicitud.setProyecto(rs.getString("proyecto"));
                solicitud.setObservaciones(rs.getString("observaciones"));
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return solicitud;
    }
 
  public List<Solicitud> obtenerSolicitudes()
  {
        List<Solicitud> solicitudes = new ArrayList<Solicitud>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT solicitud.id_solicitud, solicitud.fecha_solicitud, solicitud.id_especie, solicitud.cantidad, solicitud.id_usuario, solicitud.estado, entrega.fecha_entrega, entrega.cantidad_entregada "
                    + "FROM serpentario.solicitudes AS solicitud LEFT OUTER JOIN serpentario.entregas_solicitud as entrega ON solicitud.id_solicitud=entrega.id_solicitud; ");
            ResultSet rs = consulta.executeQuery();
            EspecieDAO especiedao=new EspecieDAO();
            UsuarioDAO usuariodao = new UsuarioDAO();
            while(rs.next()){
                Solicitud solicitud = new Solicitud();
                solicitud.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud.setCantidad(rs.getFloat("cantidad"));
                solicitud.setEspecie(especiedao.obtenerEspecie(rs.getInt("id_especie")));
                solicitud.setUsuario(usuariodao.obtenerUsuario(rs.getInt("id_usuario")));
                solicitud.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                solicitud.setEstado(rs.getString("estado"));
                
                EntregasSolicitud entrega = new EntregasSolicitud();
                entrega.setCantidad_entregada(rs.getFloat("cantidad_entregada"));
                entrega.setFecha_entrega(rs.getDate("fecha_entrega"));
                
                solicitud.setEntrega(entrega);
                
                solicitudes.add(solicitud);
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return solicitudes;
    }
  
    public EntregasSolicitud obtenerEntrega(Solicitud solicitud){
        EntregasSolicitud entrega = new EntregasSolicitud();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM serpentario.entregas_solicitud where id_solicitud = ?");
            consulta.setInt(1, solicitud.getId_solicitud());
            ResultSet rs = consulta.executeQuery();
            UsuarioDAO usuariodao = new UsuarioDAO();
            if(rs.next()){
                entrega.setId_entrega(rs.getInt("id_entrega"));
                entrega.setSolicitud(solicitud);
                entrega.setFecha_entrega(rs.getDate("fecha_entrega"));
                entrega.setUsuario_entrega(usuariodao.obtenerUsuario(rs.getInt("id_usuario_entrega")));
                entrega.setUsuario_recibo(usuariodao.obtenerUsuario(rs.getInt("id_usuario_recibo")));
                entrega.setCantidad_entregada(rs.getFloat("cantidad_entregada"));
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return entrega;
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
