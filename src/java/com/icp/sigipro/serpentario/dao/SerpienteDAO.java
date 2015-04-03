/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.serpentario.modelos.CatalogoTejido;
import com.icp.sigipro.serpentario.modelos.ColeccionHumeda;
import com.icp.sigipro.serpentario.modelos.HelperSerpiente;
import com.icp.sigipro.serpentario.modelos.Serpiente;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class SerpienteDAO {
    private Connection conexion;
    
    
    public SerpienteDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    } 
    
    public boolean insertarSerpiente(Serpiente s){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.serpientes (id_especie, fecha_ingreso,localidad_origen,colectada,recibida,sexo,talla_cabeza,talla_cola,peso) " +
                                                             " VALUES (?,?,?,?,?,?,?,?,?) RETURNING id_serpiente");
            consulta.setInt(1, s.getEspecie().getId_especie());
            consulta.setDate(2, s.getFecha_ingreso());
            consulta.setString(3, s.getLocalidad_origen());
            consulta.setString(4,s.getColectada());
            consulta.setString(5,s.getRecibida().getNombre_usuario());
            consulta.setString(6,s.getSexo());
            consulta.setFloat(7, s.getTalla_cabeza());
            consulta.setFloat(8, s.getTalla_cola());
            consulta.setFloat(9, s.getPeso());
            
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                s.setId_serpiente(resultadoConsulta.getInt("id_serpiente"));
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
    
    public boolean insertarSerpiente(ColeccionHumeda ch){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.coleccion_humeda (id_serpiente, proposito,observaciones,id_usuario) " +
                                                             " VALUES (?,?,?,?) RETURNING id_coleccion_humeda");
            consulta.setInt(1, ch.getSerpiente().getId_serpiente());
            consulta.setString(2, ch.getProposito());
            consulta.setString(3, ch.getObservaciones());
            consulta.setInt(4,ch.getUsuario().getId_usuario());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                ch.setId_coleccion_humeda(resultadoConsulta.getInt("id_coleccion_humeda"));
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
    
    public boolean insertarSerpiente(CatalogoTejido ct){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.catalogo_tejido (id_serpiente, numero_caja,posicion,observaciones,estado,id_usuario) " +
                                                             " VALUES (?,?,?,?,?,?) RETURNING id_catalogo_tejido");
            consulta.setInt(1, ct.getSerpiente().getId_serpiente());
            consulta.setString(2, ct.getNumero_caja());
            consulta.setString(3, ct.getPosicion());
            consulta.setString(4, ct.getObservaciones());
            consulta.setString(5, ct.getEstado());
            consulta.setInt(6,ct.getUsuario().getId_usuario());

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
        }
        return resultado;
    }
    
    public boolean insertarImagen(InputStream imagen, int id_serpiente,long size){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(
                  "UPDATE serpentario.serpientes " +
                  "SET imagen=? " +
                  "WHERE id_serpiente=?; ");

            consulta.setBinaryStream(1, imagen,size);
            consulta.setInt(2, id_serpiente);

            System.out.println(consulta);
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
    
    public boolean editarColeccionHumeda(ColeccionHumeda ch){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE serpentario.coleccion_humeda " +
                  " SET observaciones=? " +
                  " WHERE id_coleccion_humeda=?; ");

            consulta.setString(1, ch.getObservaciones());
            consulta.setInt(2, ch.getId_coleccion_humeda());

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
    
    public int obtenerProximoId(){
        boolean resultado = false;
        int nextval = 0;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT last_value FROM serpentario.serpientes_id_serpiente_seq;");
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
    
    public int obtenerProximoIdCH(){
        boolean resultado = false;
        int nextval = 0;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT last_value FROM serpentario.coleccion_humeda_id_coleccion_humeda_seq;");
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()){
                resultado=true;
                int currval = resultadoConsulta.getInt("last_value");
                System.out.println(currval);
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
    
    public int obtenerProximoIdCT(){
        boolean resultado = false;
        int nextval = 0;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT last_value FROM serpentario.catalogo_tejido_id_catalogo_tejido_seq;");
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()){
                resultado=true;
                int currval = resultadoConsulta.getInt("last_value");
                System.out.println(currval);
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
    
    public boolean eliminarSerpiente(int id_serpiente) throws SIGIPROException{
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM serpentario.serpientes " +
                    " WHERE id_serpiente=?; "
            );
            consulta.setInt(1, id_serpiente);
            if ( consulta.executeUpdate() == 1){
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }
        catch(SQLException ex){
            throw new SIGIPROException("Serpiente no pudo ser eliminada debido a que una o más objetos se encuentran asociadas a esta.");
        }
        return resultado;
    }  
    
    public List<HelperSerpiente> editarSerpiente(Serpiente s){
        boolean resultado = false;
        List<HelperSerpiente> lista = new ArrayList<HelperSerpiente>();
        try{
            //IMPLEMENTAR EL INSERT DE EVENTOS CADA VEZ QUE CAMBIA UN DATO
            Serpiente old_s = obtenerSerpiente(s.getId_serpiente());
            //------------------------------------------------------------
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE serpentario.serpientes " +
                  " SET sexo=?, talla_cabeza=?, talla_cola=?,peso=?" +
                  " WHERE id_serpiente=?; "
            );
            
            if (!s.getSexo().equals(old_s.getSexo())){
                HelperSerpiente cambio = new HelperSerpiente("sexo",old_s.getSexo());
                lista.add(cambio);
            }if (s.getTalla_cabeza()!=old_s.getTalla_cabeza()){
                HelperSerpiente cambio = new HelperSerpiente("talla_cabeza",Float.toString(old_s.getTalla_cabeza()));
                lista.add(cambio);
            }if (s.getTalla_cola()!=old_s.getTalla_cola()){
                HelperSerpiente cambio = new HelperSerpiente("talla_cola",Float.toString(old_s.getTalla_cola()));
                lista.add(cambio);
            }if (s.getPeso()!=old_s.getPeso()){
                HelperSerpiente cambio = new HelperSerpiente("peso",Float.toString(old_s.getPeso()));
                lista.add(cambio);
            }
            
            consulta.setString(1,s.getSexo());
            consulta.setFloat(2, s.getTalla_cabeza());
            consulta.setFloat(3, s.getTalla_cola());
            consulta.setFloat(4, s.getPeso());
            //consulta.setBlob(5,s.getImagen());
            consulta.setInt(5, s.getId_serpiente());

            if ( consulta.executeUpdate() == 1){
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return lista;
        
    }
  
    public ColeccionHumeda obtenerColeccionHumeda(Serpiente serpiente){
        ColeccionHumeda ch = new ColeccionHumeda();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM serpentario.coleccion_humeda where id_serpiente = ?");
            consulta.setInt(1, serpiente.getId_serpiente());
            ResultSet rs = consulta.executeQuery();
            UsuarioDAO usuariodao = new UsuarioDAO();
            if(rs.next()){
                ch.setId_coleccion_humeda(rs.getInt("id_coleccion_humeda"));
                ch.setSerpiente(serpiente);
                ch.setProposito(rs.getString("proposito"));
                ch.setObservaciones(rs.getString("observaciones"));
                ch.setUsuario(usuariodao.obtenerUsuario(rs.getInt("id_usuario")));
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return ch;
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
                ct.setSerpiente(serpiente);
                ct.setNumero_caja(rs.getString("numero_caja"));
                ct.setObservaciones(rs.getString("observaciones"));
                ct.setEstado(rs.getString("estado"));
                ct.setPosicion(rs.getString("posicion"));
                ct.setUsuario(usuariodao.obtenerUsuario(rs.getInt("id_usuario")));
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
    
    public Serpiente obtenerSerpiente(int id_serpiente){
        Serpiente serpiente = new Serpiente();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM serpentario.serpientes where id_serpiente = ?");
            consulta.setInt(1, id_serpiente);
            ResultSet rs = consulta.executeQuery();
            EspecieDAO dao = new EspecieDAO();
            if(rs.next()){
                serpiente.setId_serpiente(rs.getInt("id_serpiente"));
                serpiente.setEspecie(dao.obtenerEspecie(rs.getInt("id_especie")));
                serpiente.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                serpiente.setLocalidad_origen(rs.getString("localidad_origen"));
                serpiente.setColectada(rs.getString("colectada"));
                UsuarioDAO usuariodao = new UsuarioDAO();
                serpiente.setRecibida(usuariodao.obtenerUsuario(rs.getString("recibida")));
                serpiente.setSexo(rs.getString("sexo"));
                serpiente.setTalla_cabeza(rs.getFloat("talla_cabeza"));
                serpiente.setTalla_cola(rs.getFloat("talla_cola"));
                serpiente.setPeso(rs.getFloat("peso"));
                byte[] imagen = rs.getBytes("imagen");
                
                serpiente.setImagen(imagen);
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return serpiente;
    }
  
    public List<Serpiente> obtenerSerpientes(){
        List<Serpiente> resultado = new ArrayList<Serpiente>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.serpientes ");
            ResultSet rs = consulta.executeQuery();
            EspecieDAO dao = new EspecieDAO();
            while(rs.next()){
                Serpiente serpiente = new Serpiente();
                serpiente.setId_serpiente(rs.getInt("id_serpiente"));
                serpiente.setEspecie(dao.obtenerEspecie(rs.getInt("id_especie")));
                serpiente.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                serpiente.setLocalidad_origen(rs.getString("localidad_origen"));
                serpiente.setColectada(rs.getString("colectada"));
                UsuarioDAO usuariodao = new UsuarioDAO();
                
                serpiente.setRecibida(usuariodao.obtenerUsuario(rs.getString("recibida")));
                serpiente.setSexo(rs.getString("sexo"));
                serpiente.setTalla_cabeza(rs.getFloat("talla_cabeza"));
                serpiente.setTalla_cola(rs.getFloat("talla_cola"));
                serpiente.setPeso(rs.getFloat("peso"));
                resultado.add(serpiente);
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
    
    public List<Serpiente> obtenerSerpientes(int id_especie){
        List<Serpiente> resultado = new ArrayList<Serpiente>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.serpientes WHERE id_especie=? AND ID_SERPIENTE NOT IN(SELECT ID_SERPIENTE "
                    + "FROM SERPENTARIO.EVENTOS AS evento WHERE evento.evento='Deceso') "
                    + "AND ID_SERPIENTE IN (SELECT ID_SERPIENTE FROM SERPENTARIO.EVENTOS WHERE EVENTO = 'Pase a Coleccion Viva')");
            consulta.setInt(1, id_especie);
            ResultSet rs = consulta.executeQuery();
            EspecieDAO dao = new EspecieDAO();
            while(rs.next()){
                Serpiente serpiente = new Serpiente();
                serpiente.setId_serpiente(rs.getInt("id_serpiente"));
                serpiente.setEspecie(dao.obtenerEspecie(rs.getInt("id_especie")));
                serpiente.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                serpiente.setLocalidad_origen(rs.getString("localidad_origen"));
                serpiente.setColectada(rs.getString("colectada"));
                UsuarioDAO usuariodao = new UsuarioDAO();
                
                serpiente.setRecibida(usuariodao.obtenerUsuario(rs.getString("recibida")));
                serpiente.setSexo(rs.getString("sexo"));
                serpiente.setTalla_cabeza(rs.getFloat("talla_cabeza"));
                serpiente.setTalla_cola(rs.getFloat("talla_cola"));
                serpiente.setPeso(rs.getFloat("peso"));
                resultado.add(serpiente);
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
    
    public List<Serpiente> obtenerSerpientesCuarentena(int id_especie){
        List<Serpiente> resultado = new ArrayList<Serpiente>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.serpientes WHERE id_especie=? AND ID_SERPIENTE NOT IN(SELECT ID_SERPIENTE "
                    + "FROM SERPENTARIO.EVENTOS AS evento WHERE evento.evento='Deceso');");
            consulta.setInt(1, id_especie);
            ResultSet rs = consulta.executeQuery();
            EspecieDAO dao = new EspecieDAO();
            while(rs.next()){
                Serpiente serpiente = new Serpiente();
                serpiente.setId_serpiente(rs.getInt("id_serpiente"));
                serpiente.setEspecie(dao.obtenerEspecie(rs.getInt("id_especie")));
                serpiente.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                serpiente.setLocalidad_origen(rs.getString("localidad_origen"));
                serpiente.setColectada(rs.getString("colectada"));
                UsuarioDAO usuariodao = new UsuarioDAO();
                
                serpiente.setRecibida(usuariodao.obtenerUsuario(rs.getString("recibida")));
                serpiente.setSexo(rs.getString("sexo"));
                serpiente.setTalla_cabeza(rs.getFloat("talla_cabeza"));
                serpiente.setTalla_cola(rs.getFloat("talla_cola"));
                serpiente.setPeso(rs.getFloat("peso"));
                resultado.add(serpiente);
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
