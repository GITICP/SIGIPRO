/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.serpentario.modelos.Restriccion;
import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.serpentario.modelos.Especie;
import com.icp.sigipro.serpentario.modelos.Veneno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class RestriccionDAO extends DAO{
    private String inicio = "-10-01";
    private String fin = "-09-30";
    
    public RestriccionDAO()
    {
    }
    
    public boolean insertarRestriccion(Restriccion r){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.restriccion (id_usuario, id_veneno, cantidad_anual) " +
                                                             " VALUES (?,?,?) RETURNING id_restriccion");
            consulta.setInt(1, r.getUsuario().getId_usuario());
            consulta.setInt(2, r.getVeneno().getId_veneno());
            consulta.setFloat(3, r.getCantidad_anual());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                r.setId_restriccion(resultadoConsulta.getInt("id_restriccion"));
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
    
    public boolean editarRestriccion(Restriccion r){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("UPDATE serpentario.restriccion "
                    + "SET cantidad_anual = ? "
                    + "WHERE id_restriccion=?");
            consulta.setFloat(1, r.getCantidad_anual());
            consulta.setInt(2, r.getId_restriccion());
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
    
        public boolean eliminarRestriccion(int id_restriccion){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("DELETE FROM serpentario.restriccion "
                    + "WHERE id_restriccion=?");
            consulta.setInt(1, id_restriccion);
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
    
    public List<Usuario> obtenerUsuarios(Restriccion r){
        List<Usuario> usuarios = new ArrayList<Usuario>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT u.id_usuario, u.nombre_usuario, u.nombre_completo "
                    + "FROM seguridad.usuarios as U "
                    + "WHERE id_usuario not in (SELECT id_usuario FROM serpentario.restriccion WHERE id_veneno = ?);");
            consulta.setInt(1, r.getVeneno().getId_veneno());
            ResultSet rs = consulta.executeQuery();
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setNombre_completo(rs.getString("nombre_completo"));
                usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                usuarios.add(usuario);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return usuarios;
    }    
        
    public List<Restriccion> obtenerRestricciones(){
        List<Restriccion> restricciones = new ArrayList<Restriccion>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT r.id_restriccion,r.id_veneno, r.id_usuario,  v.id_especie, e.genero, e.especie, u.nombre_completo, u.nombre_usuario,r.cantidad_anual "
                    + "FROM serpentario.restriccion as r "
                    + "INNER JOIN serpentario.venenos as v "
                    + "ON v.id_veneno = r.id_veneno "
                    + "INNER JOIN seguridad.usuarios as u "
                    + "ON u.id_usuario = r.id_usuario "
                    + "INNER JOIN serpentario.especies as e "
                    + "ON e.id_especie = v.id_especie;");
            ResultSet rs = consulta.executeQuery();
            while(rs.next()){
                Restriccion restriccion = new Restriccion();
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setNombre_completo(rs.getString("nombre_completo"));
                usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                restriccion.setUsuario(usuario);
                
                Veneno veneno = new Veneno();
                veneno.setId_veneno(rs.getInt("id_veneno"));
                Especie especie = new Especie();
                especie.setId_especie(rs.getInt("id_especie"));
                especie.setEspecie(rs.getString("especie"));
                especie.setGenero(rs.getString("genero"));
                veneno.setEspecie(especie);
                restriccion.setVeneno(veneno);
                
                restriccion.setCantidad_anual(rs.getFloat("cantidad_anual"));
                restriccion.setId_restriccion(rs.getInt("id_restriccion"));
                restriccion.setCantidad_consumida(obtenerCantidad(usuario.getId_usuario(),especie.getId_especie()));
                restricciones.add(restriccion);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return restricciones;
    }

    public Restriccion obtenerRestriccion(int id_restriccion){
        Restriccion restriccion = new Restriccion();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT r.id_restriccion,r.id_veneno, r.id_usuario,  v.id_especie, e.genero, e.especie, u.nombre_completo, u.nombre_usuario,r.cantidad_anual "
                    + "FROM serpentario.restriccion as r "
                    + "INNER JOIN serpentario.venenos as v "
                    + "ON v.id_veneno = r.id_veneno "
                    + "INNER JOIN seguridad.usuarios as u "
                    + "ON u.id_usuario = r.id_usuario "
                    + "INNER JOIN serpentario.especies as e "
                    + "ON e.id_especie = v.id_especie "
                    + "WHERE r.id_restriccion=?;");
            
            consulta.setInt(1, id_restriccion);
            ResultSet rs = consulta.executeQuery();
            if(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setNombre_completo(rs.getString("nombre_completo"));
                usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                restriccion.setUsuario(usuario);
                
                Veneno veneno = new Veneno();
                veneno.setId_veneno(rs.getInt("id_veneno"));
                Especie especie = new Especie();
                especie.setId_especie(rs.getInt("id_especie"));
                especie.setEspecie(rs.getString("especie"));
                especie.setGenero(rs.getString("genero"));
                veneno.setEspecie(especie);
                restriccion.setVeneno(veneno);
                
                restriccion.setCantidad_anual(rs.getFloat("cantidad_anual"));
                restriccion.setId_restriccion(rs.getInt("id_restriccion"));
                restriccion.setCantidad_consumida(obtenerCantidad(usuario.getId_usuario(),especie.getId_especie()));
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return restriccion;
    }
    
    public Restriccion obtenerRestriccion(int id_veneno,int id_usuario){
        Restriccion restriccion = new Restriccion();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT r.id_restriccion,r.id_veneno, r.id_usuario,  v.id_especie, e.genero, e.especie, u.nombre_completo, u.nombre_usuario,r.cantidad_anual "
                    + "FROM serpentario.restriccion as r "
                    + "INNER JOIN serpentario.venenos as v "
                    + "ON v.id_veneno = r.id_veneno "
                    + "INNER JOIN seguridad.usuarios as u "
                    + "ON u.id_usuario = r.id_usuario "
                    + "INNER JOIN serpentario.especies as e "
                    + "ON e.id_especie = v.id_especie "
                    + "WHERE r.id_veneno=? and r.id_usuario=?;");
            
            consulta.setInt(1, id_veneno);
            consulta.setInt(2, id_usuario);
            ResultSet rs = consulta.executeQuery();
            if(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setNombre_completo(rs.getString("nombre_completo"));
                usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                restriccion.setUsuario(usuario);
                
                Veneno veneno = new Veneno();
                veneno.setId_veneno(rs.getInt("id_veneno"));
                Especie especie = new Especie();
                especie.setId_especie(rs.getInt("id_especie"));
                especie.setEspecie(rs.getString("especie"));
                especie.setGenero(rs.getString("genero"));
                veneno.setEspecie(especie);
                restriccion.setVeneno(veneno);
                
                restriccion.setCantidad_anual(rs.getFloat("cantidad_anual"));
                restriccion.setId_restriccion(rs.getInt("id_restriccion"));
                restriccion.setCantidad_consumida(obtenerCantidad(usuario.getId_usuario(),especie.getId_especie()));
            }else{
                restriccion.setCantidad_consumida(this.obtenerCantidad(id_usuario, id_veneno));
                restriccion.setCantidad_anual(0);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return restriccion;
    }
    
    public float obtenerCantidad(int id_usuario,int id_especie){
        float cantidad = 0;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT r.id_usuario, v.id_especie, sum(es.cantidad_entregada) as cantidad_entregada "
                    + "FROM serpentario.restriccion as r "
                    + "INNER JOIN serpentario.entregas_solicitud as es ON r.id_usuario = es.id_usuario_recibo "
                    + "INNER JOIN serpentario.solicitudes as s ON es.id_solicitud = s.id_solicitud "
                    + "INNER JOIN serpentario.venenos as v on v.id_veneno = r.id_veneno "
                    + "WHERE ? < es.fecha_entrega and es.fecha_entrega < ? and r.id_usuario=? and v.id_especie =? "
                    + "GROUP BY r.id_usuario,v.id_especie;");
            int anoactual = Calendar.getInstance().get(Calendar.YEAR);
            int anoanterior = anoactual-1;
            String fecha_inicio = anoanterior + inicio;
            String fecha_fin = anoactual + fin;
            
            DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            
            java.util.Date inicio;
            java.sql.Date inicioSQL = null;
            java.util.Date fin;
            java.sql.Date finSQL = null;
            try {
                inicio = formatoFecha.parse(fecha_inicio);
                inicioSQL = new java.sql.Date(inicio.getTime());
                fin = formatoFecha.parse(fecha_fin);
                finSQL = new java.sql.Date(fin.getTime());
            }catch (Exception ex){
                
            }
            consulta.setDate(1, inicioSQL);
            consulta.setDate(2, finSQL);
            consulta.setInt(3, id_usuario);
            consulta.setInt(4, id_especie);
            ResultSet rs = consulta.executeQuery();
            while(rs.next()){
                cantidad = rs.getFloat("cantidad_entregada");
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return cantidad;
    }
}
