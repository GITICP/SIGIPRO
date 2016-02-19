/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Historial;
import com.icp.sigipro.ventas.modelos.Historial_lista;
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
public class Historial_listaDAO extends DAO {

    public Historial_listaDAO() {
    }
    
    private HistorialDAO adao = new HistorialDAO();

    public List<Historial> obtenerHistorialesDeTratamiento(int id_lista) throws SIGIPROException {

        List<Historial> resultado = new ArrayList<Historial>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.historial_lista where id_lista=?; ");
            consulta.setInt(1, id_lista);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Historial historial = new Historial();
                
                historial = adao.obtenerHistorial(rs.getInt("id_historial"));
                resultado.add(historial);

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

    public Historial obtenerHistorial(int id_historial, int id_lista) throws SIGIPROException {

        Historial resultado = new Historial();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.historial_lista where id_historial = ? and id_lista = ?; ");
            consulta.setInt(1, id_historial);
            consulta.setInt(2, id_lista);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado = adao.obtenerHistorial(rs.getInt("id_historial"));
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
    
    public boolean insertarHistorialTratamiento(Historial_lista p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.historial_lista (id_lista, id_historial)"
                    + " VALUES (?,?) RETURNING id_lista");

            consulta.setInt(1, p.getId_lista());
            consulta.setInt(2, p.getId_historial());

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

    public boolean eliminarHistorial(int id_historial, int id_lista) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.historial_lista "
                    + " WHERE id_historial=? and id_lista=?; "
            );

            consulta.setInt(1, id_historial);
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

    public List<Historial> parsearHistoriales(String historiales_lista, int id_lista) {
        List<Historial> resultado = null;
        try {
            resultado = new ArrayList<Historial>();
            List<String> historialsParcial = new LinkedList<String>(Arrays.asList(historiales_lista.split("#r#")));
            historialsParcial.remove("");
            for (String i : historialsParcial) {
                
                try{
                    List<String> historial = new LinkedList<String>(Arrays.asList(i.split("#c#")));
                    int id_historial = Integer.parseInt(historial.get(0));
                
                    Historial p = adao.obtenerHistorial(id_historial);

                    resultado.add(p);
                }
                catch(Exception e){
                    Historial o = new Historial();
                    List<String> historial = new LinkedList<String>(Arrays.asList(i.split("#c#")));
                    o.setHistorial(historial.get(1));
                    int id_historial = adao.insertarHistorial(o);
                    o.setId_historial(id_historial);
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
    
    public boolean esHistorialTratamiento (int id_historial, int id_lista) throws SIGIPROException{
        boolean resultado = false;
        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.historial_lista WHERE id_historial=? and id_lista=?; ");
            consulta.setInt(1, id_historial);
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
    
    public void asegurarHistoriales_Tratamiento(List<Historial> p_i, int id_lista) throws SIGIPROException {
        List<Historial> historialesDelTratamiento = this.obtenerHistorialesDeTratamiento(id_lista);
        boolean esta = false;
        for (Historial p : historialesDelTratamiento){
            for (Historial i : p_i){
                //System.out.println("HistorialEntrante: id_historial = "+i.getId_historial());
                if (i.getId_historial() == p.getId_historial()){
                    //System.out.println("Historial correcta: id_historial = "+i.getId_historial());
                    esta = true;
                }
            }
            if (esta == false){
                //System.out.println("Historial a eliminar: id_historial = "+p.getId_historial()+", id_lista = "+id_lista);
                this.eliminarHistorial(p.getId_historial(), id_lista);
            }
            esta = false;
        }
    }

    public boolean eliminarHistoriales_Tratamiento(int id_lista) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.historial_lista "
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
