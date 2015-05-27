/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
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
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.serpientes (id_especie, fecha_ingreso,localidad_origen,colectada,recibida,sexo,talla_cabeza,talla_cola,peso,numero_serpiente,coleccionviva,estado) " +
                                                             " VALUES (?,?,?,?,?,?,?,?,?,?,false,true) RETURNING id_serpiente");
            consulta.setInt(1, s.getEspecie().getId_especie());
            consulta.setDate(2, s.getFecha_ingreso());
            consulta.setString(3, s.getLocalidad_origen());
            consulta.setString(4,s.getColectada());
            consulta.setString(5,s.getRecibida().getNombre_usuario());
            consulta.setString(6,s.getSexo());
            consulta.setFloat(7, s.getTalla_cabeza());
            consulta.setFloat(8, s.getTalla_cola());
            consulta.setFloat(9, s.getPeso());
            consulta.setInt(10,s.getNumero_serpiente());
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
            return false;
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
        int nextval = 0;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT MAX(NUMERO_SERPIENTE) AS LAST_VALUE FROM SERPENTARIO.SERPIENTES;");
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()){
                int currval = resultadoConsulta.getInt("last_value");
                nextval = currval + 1;
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return nextval;
    }
    
    public int obtenerProximoIdCH(){
        int nextval = 0;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT MAX(NUMERO_COLECCION_HUMEDA) AS LAST_VALUE FROM SERPENTARIO.COLECCION_HUMEDA;");
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()){
                int currval = resultadoConsulta.getInt("last_value");
                nextval=currval+1;
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return nextval;
    }
    
    public int obtenerProximoIdCT(){
        int nextval = 0;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT MAX(NUMERO_CATALOGO_TEJIDO) AS LAST_VALUE FROM SERPENTARIO.CATALOGO_TEJIDO;");
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()){
                int currval = resultadoConsulta.getInt("last_value");
                nextval=currval+1;
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
        }catch (Exception e){
            e.printStackTrace();
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
            ex.printStackTrace();
            throw new SIGIPROException("Serpiente no pudo ser eliminada debido a que una o más objetos se encuentran asociadas a esta.");
        }
        return resultado;
    }  
    
    public List<HelperSerpiente> editarSerpiente(Serpiente s){
        boolean resultado = false;
        List<HelperSerpiente> lista = new ArrayList<HelperSerpiente>();
        try{
            Serpiente old_s = obtenerSerpiente(s.getId_serpiente());
            //------------------------------------------------------------
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE serpentario.serpientes " +
                  " SET localidad_origen=?, colectada=?,  sexo=?, talla_cabeza=?, talla_cola=?,peso=?" +
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
            
            consulta.setString(1, s.getLocalidad_origen());
            consulta.setString(2, s.getColectada());
            consulta.setString(3,s.getSexo());
            consulta.setFloat(4, s.getTalla_cabeza());
            consulta.setFloat(5, s.getTalla_cola());
            consulta.setFloat(6, s.getPeso());
            consulta.setInt(7, s.getId_serpiente());

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

    public boolean actualizarColeccionViva(int id_serpiente){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE serpentario.serpientes " +
                  " SET coleccionviva=true" +
                  " WHERE id_serpiente=?; "
            );
            consulta.setInt(1, id_serpiente);
            if ( consulta.executeUpdate() == 1){
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
        public boolean reversarColeccionViva(int id_serpiente){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE serpentario.serpientes " +
                  " SET coleccionviva=false" +
                  " WHERE id_serpiente=?; "
            );
            consulta.setInt(1, id_serpiente);
            if ( consulta.executeUpdate() == 1){
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
        
    public boolean reversarEstado(int id_serpiente){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE serpentario.serpientes " +
                  " SET estado=true" +
                  " WHERE id_serpiente=?; "
            );
            consulta.setInt(1, id_serpiente);
            if ( consulta.executeUpdate() == 1){
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public boolean actualizarEstado(int id_serpiente){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(
                  " UPDATE serpentario.serpientes " +
                  " SET estado=false" +
                  " WHERE id_serpiente=?; "
            );
            consulta.setInt(1, id_serpiente);
            if ( consulta.executeUpdate() == 1){
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
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
                serpiente.setNumero_serpiente(rs.getInt("numero_serpiente"));
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
                serpiente.setColeccionviva(rs.getBoolean("coleccionviva"));
                serpiente.setEstado(rs.getBoolean("estado"));
                
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
  
    public boolean validarDescarte(int id_serpiente){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.eventos WHERE id_categoria=14 and id_serpiente=?; ");
            consulta.setInt(1, id_serpiente);
            ResultSet rs = consulta.executeQuery();
            if(rs.next()){
                resultado=true;
            }
            rs.close();
            consulta.close();
            conexion.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return resultado;
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
                serpiente.setNumero_serpiente(rs.getInt("numero_serpiente"));
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
                serpiente.setColeccionviva(rs.getBoolean("coleccionviva"));
                serpiente.setEstado(rs.getBoolean("estado"));
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
    
    public List<Serpiente> obtenerSerpientes(int id_especie, int id_extraccion){
        List<Serpiente> resultado = new ArrayList<Serpiente>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.serpientes WHERE id_especie=? AND ID_SERPIENTE NOT IN(SELECT ID_SERPIENTE "
                    + "FROM SERPENTARIO.EVENTOS AS evento WHERE evento.id_categoria=6) "
                    + "AND ID_SERPIENTE IN (SELECT ID_SERPIENTE FROM SERPENTARIO.EVENTOS AS evento WHERE evento.ID_CATEGORIA = 5) "
                    + "AND ID_SERPIENTE NOT IN (SELECT SE.ID_SERPIENTE FROM SERPENTARIO.SERPIENTES_EXTRACCION AS SE WHERE ID_EXTRACCION=?)");
            consulta.setInt(1, id_especie);
            consulta.setInt(2,id_extraccion);
            ResultSet rs = consulta.executeQuery();
            EspecieDAO dao = new EspecieDAO();
            while(rs.next()){
                Serpiente serpiente = new Serpiente();
                serpiente.setId_serpiente(rs.getInt("id_serpiente"));
                serpiente.setNumero_serpiente(rs.getInt("numero_serpiente"));
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
                serpiente.setColeccionviva(rs.getBoolean("coleccionviva"));
                serpiente.setEstado(rs.getBoolean("estado"));
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
    
    public List<Serpiente> obtenerSerpientesCuarentena(int id_especie,int id_extraccion){
        List<Serpiente> resultado = new ArrayList<Serpiente>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.serpientes WHERE id_especie=? AND ID_SERPIENTE NOT IN(SELECT ID_SERPIENTE "
                    + "FROM SERPENTARIO.EVENTOS AS evento WHERE evento.id_categoria=6) "
                    + "AND ID_SERPIENTE NOT IN (SELECT SE.ID_SERPIENTE FROM SERPENTARIO.SERPIENTES_EXTRACCION AS SE WHERE ID_EXTRACCION=?);");
            consulta.setInt(1, id_especie);
            consulta.setInt(2, id_extraccion);
            ResultSet rs = consulta.executeQuery();
            EspecieDAO dao = new EspecieDAO();
            while(rs.next()){
                Serpiente serpiente = new Serpiente();
                serpiente.setId_serpiente(rs.getInt("id_serpiente"));
                serpiente.setNumero_serpiente(rs.getInt("numero_serpiente"));
                serpiente.setEspecie(dao.obtenerEspecie(rs.getInt("id_especie")));
                serpiente.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                serpiente.setLocalidad_origen(rs.getString("localidad_origen"));
                serpiente.setColectada(rs.getString("colectada"));
                UsuarioDAO usuariodao = new UsuarioDAO();
                
                serpiente.setRecibida(usuariodao.obtenerUsuario(rs.getString("recibida")));
                serpiente.setSexo(rs.getString("sexo"));
                serpiente.setTalla_cabeza(rs.getFloat("talla_cabeza"));
                serpiente.setTalla_cola(rs.getFloat("talla_cola"));
                serpiente.setColeccionviva(rs.getBoolean("coleccionviva"));
                serpiente.setEstado(rs.getBoolean("estado"));
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
            ex.printStackTrace();
            conexion = null;
        }
        return conexion;
    }  
}
