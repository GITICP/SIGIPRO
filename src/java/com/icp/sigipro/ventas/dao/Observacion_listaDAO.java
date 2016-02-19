/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Observacion;
import com.icp.sigipro.ventas.modelos.Observacion_lista;
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
public class Observacion_listaDAO extends DAO {

    public Observacion_listaDAO() {
    }
    
    private ObservacionDAO adao = new ObservacionDAO();

    public List<Observacion> obtenerObservacionesDeTratamiento(int id_lista) throws SIGIPROException {

        List<Observacion> resultado = new ArrayList<Observacion>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.observacion_lista where id_lista=?; ");
            consulta.setInt(1, id_lista);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Observacion observacion = new Observacion();
                
                observacion = adao.obtenerObservacion(rs.getInt("id_observacion"));
                resultado.add(observacion);

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

    public Observacion obtenerObservacion(int id_observacion, int id_lista) throws SIGIPROException {

        Observacion resultado = new Observacion();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.observacion_lista where id_observacion = ? and id_lista = ?; ");
            consulta.setInt(1, id_observacion);
            consulta.setInt(2, id_lista);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado = adao.obtenerObservacion(rs.getInt("id_observacion"));
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
    
    public boolean insertarObservacionTratamiento(Observacion_lista p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.observacion_lista (id_lista, id_observacion)"
                    + " VALUES (?,?) RETURNING id_lista");

            consulta.setInt(1, p.getId_lista());
            consulta.setInt(2, p.getId_observacion());

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

    public boolean eliminarObservacion(int id_observacion, int id_lista) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.observacion_lista "
                    + " WHERE id_observacion=? and id_lista=?; "
            );

            consulta.setInt(1, id_observacion);
            consulta.setInt(2, id_lista);

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

    public List<Observacion> parsearObservaciones(String observaciones_lista, int id_lista) {
        List<Observacion> resultado = null;
        try {
            resultado = new ArrayList<Observacion>();
            List<String> observacionsParcial = new LinkedList<String>(Arrays.asList(observaciones_lista.split("#r#")));
            observacionsParcial.remove("");
            for (String i : observacionsParcial) {
                
                try{
                    List<String> observacion = new LinkedList<String>(Arrays.asList(i.split("#c#")));
                    int id_observacion = Integer.parseInt(observacion.get(0));
                
                    Observacion p = adao.obtenerObservacion(id_observacion);

                    resultado.add(p);
                }
                catch(Exception e){
                    Observacion o = new Observacion();
                    List<String> observacion = new LinkedList<String>(Arrays.asList(i.split("#c#")));
                    o.setObservacion(observacion.get(1));
                    int id_observacion = adao.insertarObservacion(o);
                    o.setId_observacion(id_observacion);
                    resultado.add(o);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            resultado = null;
        }
        return resultado;
    }
    
    public boolean esObservacionTratamiento (int id_observacion, int id_lista) throws SIGIPROException{
        boolean resultado = false;
        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.observacion_lista WHERE id_observacion=? and id_lista=?; ");
            consulta.setInt(1, id_observacion);
            consulta.setInt(2, id_lista);
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
    
    public void asegurarObservaciones_Tratamiento(List<Observacion> p_i, int id_lista) throws SIGIPROException {
        List<Observacion> observacionesDelTratamiento = this.obtenerObservacionesDeTratamiento(id_lista);
        boolean esta = false;
        for (Observacion p : observacionesDelTratamiento){
            for (Observacion i : p_i){
                //System.out.println("ObservacionEntrante: id_observacion = "+i.getId_observacion());
                if (i.getId_observacion() == p.getId_observacion()){
                    //System.out.println("Observacion correcta: id_observacion = "+i.getId_observacion());
                    esta = true;
                }
            }
            if (esta == false){
                //System.out.println("Observacion a eliminar: id_observacion = "+p.getId_observacion()+", id_lista = "+id_lista);
                this.eliminarObservacion(p.getId_observacion(), id_lista);
            }
            esta = false;
        }
    }

    public boolean eliminarObservaciones_Tratamiento(int id_lista) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.observacion_lista "
                    + " WHERE id_lista=?; "
            );

            consulta.setInt(1, id_lista);

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
