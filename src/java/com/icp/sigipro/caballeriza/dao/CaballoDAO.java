/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.EventoClinico;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            if (c.getGrupo_de_caballos()== null){
                consulta.setNull(9, java.sql.Types.INTEGER);
            }
            else{
               consulta.setInt(9, c.getGrupo_de_caballos().getId_grupo_caballo()); 
            }
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
            if (c.getGrupo_de_caballos()== null){
                consulta.setNull(3, java.sql.Types.INTEGER);
            }
            else{
               consulta.setInt(3, c.getGrupo_de_caballos().getId_grupo_caballo()); 
            }            
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
            rs.close();
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
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_caballo, nombre, numero_microchip FROM caballeriza.caballos where id_grupo_de_caballo is null AND estado = ?;");
            
            consulta.setString(1, Caballo.VIVO);
            
            ResultSet rs = consulta.executeQuery();
            GrupoDeCaballosDAO dao = new GrupoDeCaballosDAO();
            
            while(rs.next()){
                Caballo caballo = new Caballo();
                caballo.setId_caballo(rs.getInt("id_caballo"));
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getInt("numero_microchip"));
                
                resultado.add(caballo);
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

    public List<EventoClinico> ObtenerEventosCaballo(int id_caballo) throws SIGIPROException {
        List<EventoClinico> resultado = new ArrayList<EventoClinico>();
        TipoEventoDAO dao = new TipoEventoDAO();
        UsuarioDAO daoUsr = new UsuarioDAO();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("select ec.id_evento,fecha,id_tipo_evento,responsable,descripcion from caballeriza.eventos_clinicos ec left outer join caballeriza.eventos_clinicos_caballos ecc on ec.id_evento = ecc.id_evento where id_caballo=?; ");
            consulta.setInt(1, id_caballo);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                EventoClinico evento = new EventoClinico();
                evento.setId_evento(rs.getInt("id_evento"));
                evento.setFecha(rs.getDate("fecha"));
                evento.setTipo_evento(dao.obtenerTipoEvento(rs.getInt("id_tipo_evento")));
                evento.setResponsable(daoUsr.obtenerUsuario(rs.getInt("responsable")));                
                evento.setDescripcion(rs.getString("descripcion"));
                resultado.add(evento);
            }          
            rs.close();
            consulta.close();
            conexion.close();
            
        } catch (SQLException ex) {
            throw new SIGIPROException("Error de comunicación con la base de datos.");
        }
        return resultado;         
    }
    public List<EventoClinico> ObtenerEventosCaballoRestantes(int id_caballo) throws SIGIPROException {
        List<EventoClinico> resultado = new ArrayList<EventoClinico>();
        TipoEventoDAO dao = new TipoEventoDAO();
        UsuarioDAO daousr = new UsuarioDAO();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT  ec.id_evento, ec.id_tipo_evento, ec.fecha, ec.descripcion, ec.responsable FROM caballeriza.eventos_clinicos ec LEFT OUTER JOIN caballeriza.tipos_eventos tp ON ec.id_tipo_evento = tp.id_tipo_evento WHERE id_evento NOT IN (SELECT id_evento FROM caballeriza.eventos_clinicos_caballos ecc where id_caballo =?) order by ec.id_tipo_evento;");
            consulta.setInt(1, id_caballo);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                EventoClinico evento = new EventoClinico();
                evento.setId_evento(rs.getInt("id_evento"));
                evento.setFecha(rs.getDate("fecha"));              
                evento.setTipo_evento(dao.obtenerTipoEvento(rs.getInt("id_tipo_evento")));
                evento.setResponsable(daousr.obtenerUsuario(rs.getInt("responsable")));
                evento.setDescripcion(rs.getString("descripcion"));
                resultado.add(evento);
            }          
            rs.close();
            consulta.close();
            conexion.close();
            
        } catch (SQLException ex) {
            throw new SIGIPROException("Error de comunicación con la base de datos.");
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
