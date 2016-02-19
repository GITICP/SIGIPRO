/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Accion;
import com.icp.sigipro.ventas.modelos.Accion_tratamiento;
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
public class Accion_tratamientoDAO extends DAO {

    public Accion_tratamientoDAO() {
    }
    
    private AccionDAO adao = new AccionDAO();

    public List<Accion> obtenerAccionesDeTratamiento(int id_tratamiento) throws SIGIPROException {

        List<Accion> resultado = new ArrayList<Accion>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.accion_tratamiento where id_tratamiento=?; ");
            consulta.setInt(1, id_tratamiento);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Accion accion = new Accion();
                
                accion = adao.obtenerAccion(rs.getInt("id_accion"));
                resultado.add(accion);

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

    public Accion obtenerAccion(int id_accion, int id_tratamiento) throws SIGIPROException {

        Accion resultado = new Accion();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.accion_tratamiento where id_accion = ? and id_tratamiento = ?; ");
            consulta.setInt(1, id_accion);
            consulta.setInt(2, id_tratamiento);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado = adao.obtenerAccion(rs.getInt("id_accion"));
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
    
    public boolean insertarAccionTratamiento(Accion_tratamiento p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.accion_tratamiento (id_tratamiento, id_accion)"
                    + " VALUES (?,?) RETURNING id_tratamiento");

            consulta.setInt(1, p.getId_tratamiento());
            consulta.setInt(2, p.getId_accion());

            ResultSet rs = consulta.executeQuery();
            while(rs.next()){
                resultado = true;
            }
            
            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");
        }
        return resultado;
    }

    public boolean eliminarAccion(int id_accion, int id_tratamiento) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.accion_tratamiento "
                    + " WHERE id_accion=? and id_tratamiento=?; "
            );

            consulta.setInt(1, id_accion);
            consulta.setInt(2, id_tratamiento);

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

        public List<Accion> parsearAcciones(String acciones_tratamiento, int id_tratamiento) {
        List<Accion> resultado = null;
        try {
            resultado = new ArrayList<Accion>();
            List<String> accionsParcial = new LinkedList<String>(Arrays.asList(acciones_tratamiento.split("#r#")));
            accionsParcial.remove("");
            for (String i : accionsParcial) {
                
                int id_accion = Integer.parseInt(i);
                
                Accion p = adao.obtenerAccion(id_accion);
                
                resultado.add(p);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            resultado = null;
        }
        return resultado;
    }
    public boolean esAccionTratamiento (int id_accion, int id_tratamiento) throws SIGIPROException{
        boolean resultado = false;
        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.accion_tratamiento WHERE id_accion=? and id_tratamiento=?; ");
            consulta.setInt(1, id_accion);
            consulta.setInt(2, id_tratamiento);
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
    
    public void asegurarAcciones_Tratamiento(List<Accion> p_i, int id_tratamiento) throws SIGIPROException {
        List<Accion> accionesDelTratamiento = this.obtenerAccionesDeTratamiento(id_tratamiento);
        boolean esta = false;
        for (Accion p : accionesDelTratamiento){
            for (Accion i : p_i){
                //System.out.println("AccionEntrante: id_accion = "+i.getId_accion());
                if (i.getId_accion() == p.getId_accion()){
                    //System.out.println("Accion correcta: id_accion = "+i.getId_accion());
                    esta = true;
                }
            }
            if (esta == false){
                //System.out.println("Accion a eliminar: id_accion = "+p.getId_accion()+", id_tratamiento = "+id_tratamiento);
                this.eliminarAccion(p.getId_accion(), id_tratamiento);
            }
            esta = false;
        }
    }

    public boolean eliminarAcciones_Tratamiento(int id_tratamiento) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.accion_tratamiento "
                    + " WHERE id_tratamiento=?; "
            );

            consulta.setInt(1, id_tratamiento);

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
