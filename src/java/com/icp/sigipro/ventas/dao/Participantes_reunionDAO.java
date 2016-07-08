/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Participantes_reunion;
import com.icp.sigipro.ventas.modelos.Reunion_produccion;
import com.icp.sigipro.ventas.dao.Reunion_produccionDAO;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class Participantes_reunionDAO extends DAO {

    public Participantes_reunionDAO() {
    }
    
    private Reunion_produccionDAO rDAO = new Reunion_produccionDAO();
    private UsuarioDAO uDAO = new UsuarioDAO();
    
    public boolean esParticipanteReunion (int id_reunion, int id_usuario) throws SIGIPROException{
        boolean resultado = false;
        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.participantes_reunion WHERE id_reunion=? and id_usuario=?; ");
            consulta.setInt(1, id_reunion);
            consulta.setInt(2, id_usuario);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado = true;
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }
    
    public List<Participantes_reunion> obtenerParticipantes(int id_reunion) throws SIGIPROException {

        List<Participantes_reunion> resultado = new ArrayList<Participantes_reunion>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.participantes_reunion WHERE id_reunion=?; ");
            consulta.setInt(1, id_reunion);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Participantes_reunion participantes_reunion = new Participantes_reunion();
                
                participantes_reunion.setReunion(rDAO.obtenerReunion_produccion(rs.getInt("id_reunion")));
                participantes_reunion.setUsuario(uDAO.obtenerUsuario(rs.getInt("id_usuario")));
                
                resultado.add(participantes_reunion);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }

    public Participantes_reunion obtenerParticipante_reunion(int id_reunion, int id_usuario) throws SIGIPROException {

        Participantes_reunion resultado = new Participantes_reunion();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.participantes_reunion where id_reunion = ? and id_usuario = ?; ");
            consulta.setInt(1, id_reunion);
            consulta.setInt(2, id_usuario);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setReunion(rDAO.obtenerReunion_produccion(rs.getInt("id_reunion")));
                resultado.setUsuario(uDAO.obtenerUsuario(rs.getInt("id_usuario")));
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }
    
    public int insertarParticipantes_reunion(Participantes_reunion p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.participantes_reunion (id_reunion, id_usuario)"
                    + " VALUES (?,?) RETURNING id_reunion");

            consulta.setInt(1,p.getReunion().getId_reunion());
            consulta.setInt(2,p.getUsuario().getId_usuario());
            
            //System.out.println("CONSULTA A EJECUTAR: "+consulta.toString());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_reunion");
            }
            resultadoConsulta.close();
            
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");
        }
        return resultado;
    }

    public boolean eliminarParticipantes_reunion(int id_reunion, int id_usuario) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.participantes_reunion "
                    + " WHERE id_reunion=? and id_usuario=?; "
            );

            consulta.setInt(1, id_reunion);
            consulta.setInt(2, id_usuario);

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la eliminación");
        }
        return resultado;
    }

    public List<Participantes_reunion> parsearParticipantes(String participantes, int id_reunion) {
        //System.out.println("participantes = "+participantes);
        List<Participantes_reunion> resultado = null;
        try {
            resultado = new ArrayList<Participantes_reunion>();
            List<String> reunionsParcial = new LinkedList<String>(Arrays.asList(participantes.split("#r#")));
            reunionsParcial.remove("");
            for (String i : reunionsParcial) {
                String[] rol = i.split("#c#");
                
                int id_usuario = Integer.parseInt(rol[0]);
                
                Participantes_reunion p = new Participantes_reunion();
                p.setReunion(rDAO.obtenerReunion_produccion(id_reunion));
                p.setUsuario(uDAO.obtenerUsuario(id_usuario));
                
                resultado.add(p);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            resultado = null;
        }
        return resultado;
    }

    public void asegurarReunions_Usuario(List<Participantes_reunion> p_i, int id_reunion) throws SIGIPROException {
        List<Participantes_reunion> participantesDeLaReunion = this.obtenerParticipantes(id_reunion);
        boolean esta = false;
        for (Participantes_reunion p : participantesDeLaReunion){
            for (Participantes_reunion i : p_i){
                if ((i.getReunion().getId_reunion() == p.getReunion().getId_reunion()) && (i.getUsuario().getId_usuario() == p.getUsuario().getId_usuario())){
                    esta = true;
                }
            }
            if (esta == false){
                //System.out.println("Participantes_reunion a eliminar: id_reunion = "+p.getReunion().getId_reunion()+", id_usuario = "+p.getUsuario().getId_usuario());
                this.eliminarParticipantes_reunion(p.getReunion().getId_reunion(), p.getUsuario().getId_usuario());
            }
            esta = false;
        }
    }

    public boolean eliminarReunions_Usuario(int id_reunion) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.participantes_reunion "
                    + " WHERE id_reunion=?; "
            );

            consulta.setInt(1, id_reunion);

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la eliminación");
        }
        return resultado;
    }

}
