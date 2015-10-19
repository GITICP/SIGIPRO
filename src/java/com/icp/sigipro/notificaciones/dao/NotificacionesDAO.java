/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.notificaciones.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.notificaciones.modelos.Notificacion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class NotificacionesDAO extends DAO {
    
    public NotificacionesDAO(){    }

    public void marcarNotificacionesLeidas(String nombre_usr) throws SIGIPROException{
        try {
            UsuarioDAO usrDAO = new UsuarioDAO();
            int id_usuario = usrDAO.obtenerIDUsuario(nombre_usr);
            
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" UPDATE calendario.notificaciones SET leida = true WHERE id_usuario = " + id_usuario);
            consulta.executeUpdate();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
    }
    
    public int contarNotificacionesNoLeidas(String nombre_usr) throws SIGIPROException{
        int resultado = 0;
        try {
            UsuarioDAO usrDAO = new UsuarioDAO();
            int id_usuario = usrDAO.obtenerIDUsuario(nombre_usr);
            
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM calendario.notificaciones WHERE id_usuario = " + id_usuario + "and leida = false");
            ResultSet rs = consulta.executeQuery();
            
            while (rs.next()) {
                resultado ++;
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }
    
    public List<Notificacion> obtenerNotificaciones(String nombre_usr) throws SIGIPROException
    {
        List<Notificacion> resultado = new ArrayList<Notificacion>();
        if (nombre_usr.equals("")){
            return resultado;
        }
        try {
            UsuarioDAO usrDAO = new UsuarioDAO();
            int id_usuario = usrDAO.obtenerIDUsuario(nombre_usr);
            
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM calendario.notificaciones WHERE id_usuario = " + id_usuario + "order by time_stamp desc limit 10");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Notificacion notificacion = new Notificacion();                
                notificacion.setTs(rs.getTimestamp("time_stamp"));
                notificacion.setLeida(rs.getBoolean("leida"));
                notificacion.setRedirect(rs.getString("redirect"));
                notificacion.setId(rs.getInt("id_notificacion"));
                
                //Los siguientes atributos se obtienen de calendario.tipo_notificaciones
                int id_tipo_notificacion = rs.getInt("id_tipo_notificacion");
                PreparedStatement consulta2;
                consulta2 = getConexion().prepareStatement(" SELECT * FROM calendario.tipo_notificaciones WHERE id_tipo_notificacion = " + id_tipo_notificacion); 
                ResultSet rs2 = consulta2.executeQuery();
                rs2.next();
                notificacion.setDescripcion(rs2.getString("descripcion"));
                notificacion.setIcono(rs2.getString("icono"));

                resultado.add(notificacion);
                
                rs2.close();
                consulta2.close();
                //cerrarConexion();
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }
}