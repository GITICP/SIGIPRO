/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.serpentario.modelos.Centrifugado;
import com.icp.sigipro.serpentario.modelos.Extraccion;
import com.icp.sigipro.serpentario.modelos.Liofilizacion;
import com.icp.sigipro.serpentario.modelos.Serpiente;
import com.icp.sigipro.serpentario.modelos.SerpientesExtraccion;
import com.icp.sigipro.serpentario.modelos.UsuariosExtraccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class ExtraccionDAO {
    private Connection conexion;
    
    
    public ExtraccionDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    } 
    
    
    public boolean insertarExtraccion(Extraccion e){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.extraccion (numero_extraccion, id_especie,ingreso_cv,fecha_extraccion) " +
                                                             " VALUES (?,?,?,?) RETURNING id_extraccion");
            consulta.setString(1, e.getNumero_extraccion());
            consulta.setInt(2, e.getEspecie().getId_especie());
            consulta.setBoolean(3, e.isIngreso_cv());
            consulta.setDate(4, e.getFecha_extraccion());
            
            ResultSet resultadoConsulta = consulta.executeQuery();
            if ( resultadoConsulta.next() ){
                resultado = true;
                e.setId_extraccion(resultadoConsulta.getInt("id_extraccion"));
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
    
    public boolean insertarCentrifugado(Centrifugado c){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.centrifugado (id_extraccion, volumen_recuperado,id_usuario,fecha_volumen_recuperado) " +
                                                             " VALUES (?,?,?,?);");
            consulta.setInt(1, c.getId_extraccion());
            consulta.setFloat(2, c.getVolumen_recuperado());
            consulta.setInt(3, c.getUsuario().getId_usuario());
            consulta.setDate(4, c.getFecha_volumen_recuperado());
            
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
    
        public boolean insertarLiofilizacionInicio(Liofilizacion l){
            boolean resultado = false;
            try{
                PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.liofilizacion (id_extraccion, id_usuario_inicio,fecha_inicio) " +
                                                                 " VALUES (?,?,?);");
                consulta.setInt(1, l.getId_extraccion());
                consulta.setInt(2, l.getUsuario_inicio().getId_usuario());
                consulta.setDate(3, l.getFecha_inicio());

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
    
        public boolean insertarLiofilizacionFin(Liofilizacion l){
            boolean resultado = false;
            try{
                PreparedStatement consulta = getConexion().prepareStatement(" UPDATE serpentario.liofilizacion "
                        + "SET peso_recuperado=?, id_usuario_fin=?, fecha_fin=?"
                        + "WHERE id_extraccion=?");
                consulta.setFloat(1, l.getPeso_recuperado());
                consulta.setInt(2, l.getUsuario_fin().getId_usuario());
                consulta.setDate(3, l.getFecha_fin());
                consulta.setInt(4, l.getId_extraccion());
                                
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
    
    public boolean editarExtraccion(Extraccion e){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement("UPDATE serpentario.extraccion "
                    + "SET volumen_extraido=?, id_usuario_registro = ?, fecha_registro = ?"
                    + "WHERE id_extraccion=?");

            consulta.setFloat(1,e.getVolumen_extraido());
            consulta.setInt(2,e.getUsuario_registro().getId_usuario());
            consulta.setDate(3,e.getFecha_registro());
            consulta.setInt(4,e.getId_extraccion());
           
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
    
    public boolean insertarUsuariosExtraccion(List<UsuariosExtraccion> usuariosextraccion){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.usuarios_extraccion (id_extraccion,id_usuario) " +
                                                             " VALUES (?,?);");
            
            if (usuariosextraccion != null){
                
                for (UsuariosExtraccion usuarioextraccion : usuariosextraccion){
                    consulta.setInt(1, usuarioextraccion.getExtraccion().getId_extraccion());
                    consulta.setInt(2, usuarioextraccion.getUsuario().getId_usuario());
                    consulta.executeUpdate();
                }
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
    
    public boolean insertarSerpientesExtraccion(List<SerpientesExtraccion> serpientesextraccion){
        boolean resultado = false;
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO serpentario.serpientes_extraccion (id_extraccion,id_serpiente) " +
                                                             " VALUES (?,?);");
            
            if (serpientesextraccion != null){
                
                for (SerpientesExtraccion serpienteextraccion : serpientesextraccion){
                    consulta.setInt(1, serpienteextraccion.getExtraccion().getId_extraccion());
                    consulta.setInt(2, serpienteextraccion.getSerpiente().getId_serpiente());
                    consulta.executeUpdate();
                }
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
    
      public List<SerpientesExtraccion> parsearSerpientesExtraccion(String serpientes, int id_extraccion) {
            List<SerpientesExtraccion> resultado = null;
            try {
              resultado = new ArrayList<SerpientesExtraccion>();
              List<String> serpientesParcial = new LinkedList<String>(Arrays.asList(serpientes.split("#r#")));
              serpientesParcial.remove("");
              
              SerpienteDAO serpientedao = new SerpienteDAO();
              
              for (String i : serpientesParcial) {
                String[] serpiente = i.split("#c#");
                Extraccion e = this.obtenerExtraccion(id_extraccion);
                Serpiente s = serpientedao.obtenerSerpiente(Integer.parseInt(serpiente[0]));
                resultado.add(new SerpientesExtraccion(s,e));
              }
            } catch (Exception ex) {
              ex.printStackTrace();
              resultado = null;
            }
            return resultado;
          }
    
    public List<Serpiente> obtenerSerpientesExtraccion(int id_extraccion){
        List<Serpiente> resultado = new ArrayList<Serpiente>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT serpientes.id_serpiente FROM serpentario.serpientes_extraccion as extraccion INNER JOIN serpentario.serpientes as serpientes ON extraccion.id_serpiente = serpientes.id_serpiente WHERE id_extraccion=?;");
            
            consulta.setInt(1, id_extraccion);
            ResultSet rs = consulta.executeQuery();
            SerpienteDAO serpientedao = new SerpienteDAO();
            while(rs.next()){
                int id_serpiente = rs.getInt("id_serpiente");
                Serpiente serpiente = serpientedao.obtenerSerpiente(id_serpiente);
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
    
    public List<Usuario> obtenerUsuariosExtraccion(int id_extraccion){
        List<Usuario> resultado = new ArrayList<Usuario>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement("SELECT usuarios.id_usuario FROM serpentario.usuarios_extraccion as extraccion INNER JOIN seguridad.usuarios as usuarios ON extraccion.id_usuario = usuarios.id_usuario WHERE id_extraccion=?;");
            
            consulta.setInt(1, id_extraccion);
            ResultSet rs = consulta.executeQuery();
            UsuarioDAO usuariodao = new UsuarioDAO();
            while(rs.next()){
                int id_usuario = rs.getInt("id_usuario");
                Usuario usuario = usuariodao.obtenerUsuario(id_usuario);
                resultado.add(usuario);
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
    
    public List<Extraccion> obtenerExtracciones(){
        List<Extraccion> resultado = new ArrayList<Extraccion>();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.extraccion ");
            ResultSet rs = consulta.executeQuery();
            EspecieDAO especiedao = new EspecieDAO();
            UsuarioDAO usuariodao = new UsuarioDAO();
            while(rs.next()){
                Extraccion extraccion = new Extraccion();
                extraccion.setId_extraccion(rs.getInt("id_extraccion"));
                extraccion.setFecha_extraccion(rs.getDate("fecha_extraccion"));
                extraccion.setFecha_registro(rs.getDate("fecha_registro"));
                extraccion.setIngreso_cv(rs.getBoolean("ingreso_cv"));
                extraccion.setNumero_extraccion(rs.getString("numero_extraccion"));
                extraccion.setVolumen_extraido(rs.getFloat("volumen_extraido"));
                extraccion.setEspecie(especiedao.obtenerEspecie(rs.getInt("id_especie")));
                extraccion.setUsuario_registro(usuariodao.obtenerUsuario(rs.getInt("id_usuario_registro")));
                validarExtraccion(extraccion);
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
    
    public void validarExtraccion(Extraccion e){
        if (validarIsSerpiente(e)){
            if (validarIsRegistro(e)){
                if (validarIsCentrifugado(e)){
                    if (validarIsLiofilizacionInicio(e)){
                        if (validarIsLiofilizacionFin(e)){
                            
                        }
                    }
                }
            }
        }
    }
    
    public boolean validarIsSerpiente(Extraccion e){
        try{
            List<Serpiente> serpientes = this.obtenerSerpientesExtraccion(e.getId_extraccion());
            if (!serpientes.isEmpty()){
                e.setIsSerpiente(true);
                return true;
            }else{
                e.setIsSerpiente(false);
                return false;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean validarIsRegistro(Extraccion e){
            if (e.getVolumen_extraido() != 0.0){
                e.setIsRegistro(true);
                return true;
            }else{
                e.setIsRegistro(false);
                return false;
            }
    }
    
    public boolean validarIsCentrifugado(Extraccion e){
        try{
            Centrifugado centrifugado = this.obtenerCentrifugado(e.getId_extraccion());
            if (centrifugado.getVolumen_recuperado() !=0.0){
                e.setIsCentrifugado(true);
                return true;
            }else{
                e.setIsCentrifugado(false);
                return false;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean validarIsLiofilizacionInicio(Extraccion e){
        try{
            Liofilizacion liofilizacion = this.obtenerLiofilizacion(e.getId_extraccion());
            if (liofilizacion.getFecha_inicio()!=null){
                e.setIsLiofilizacionInicio(true);
                return true;
            }else{
                e.setIsLiofilizacionInicio(false);
                return false;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean validarIsLiofilizacionFin(Extraccion e){
        try{
            Liofilizacion liofilizacion = this.obtenerLiofilizacion(e.getId_extraccion());
            if (liofilizacion.getFecha_finAsString() !=""){
                e.setIsLiofilizacionFin(true);
                return true;
            }else{
                e.setIsLiofilizacionFin(false);
                return false;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    public Extraccion obtenerExtraccion(int id_extraccion){
        Extraccion extraccion = new Extraccion();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.extraccion WHERE id_extraccion=?; ");
            consulta.setInt(1, id_extraccion);
            ResultSet rs = consulta.executeQuery();
            EspecieDAO especiedao = new EspecieDAO();
            UsuarioDAO usuariodao = new UsuarioDAO();
            while(rs.next()){
                extraccion.setId_extraccion(rs.getInt("id_extraccion"));
                extraccion.setFecha_extraccion(rs.getDate("fecha_extraccion"));
                extraccion.setFecha_registro(rs.getDate("fecha_registro"));
                extraccion.setIngreso_cv(rs.getBoolean("ingreso_cv"));
                extraccion.setNumero_extraccion(rs.getString("numero_extraccion"));
                extraccion.setVolumen_extraido(rs.getFloat("volumen_extraido"));
                extraccion.setEspecie(especiedao.obtenerEspecie(rs.getInt("id_especie")));
                extraccion.setUsuario_registro(usuariodao.obtenerUsuario(rs.getInt("id_usuario_registro")));
                validarExtraccion(extraccion);
            }      
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return extraccion;
    }
    
    public Centrifugado obtenerCentrifugado(int id_extraccion){
        Centrifugado centrifugado = new Centrifugado();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.centrifugado WHERE id_extraccion=?; ");
            consulta.setInt(1, id_extraccion);
            ResultSet rs = consulta.executeQuery();
            UsuarioDAO usuariodao = new UsuarioDAO();
            while(rs.next()){
                centrifugado.setId_extraccion(rs.getInt("id_extraccion"));
                centrifugado.setFecha_volumen_recuperado(rs.getDate("fecha_volumen_recuperado"));
                centrifugado.setVolumen_recuperado(rs.getFloat("volumen_recuperado"));
                centrifugado.setUsuario(usuariodao.obtenerUsuario(rs.getInt("id_usuario")));
            }      
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return centrifugado;
    }
    
    public Liofilizacion obtenerLiofilizacion(int id_extraccion){
        Liofilizacion liofilizacion = new Liofilizacion();
        try{
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM serpentario.liofilizacion WHERE id_extraccion=?; ");
            consulta.setInt(1, id_extraccion);
            ResultSet rs = consulta.executeQuery();
            UsuarioDAO usuariodao = new UsuarioDAO();
            while(rs.next()){
                liofilizacion.setId_extraccion(rs.getInt("id_extraccion"));
                liofilizacion.setFecha_inicio(rs.getDate("fecha_inicio"));
                liofilizacion.setFecha_fin(rs.getDate("fecha_fin"));
                liofilizacion.setPeso_recuperado(rs.getFloat("peso_recuperado"));
                liofilizacion.setUsuario_inicio(usuariodao.obtenerUsuario(rs.getInt("id_usuario_inicio")));
                liofilizacion.setUsuario_fin(usuariodao.obtenerUsuario(rs.getInt("id_usuario_fin")));
            } 
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return liofilizacion;
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
