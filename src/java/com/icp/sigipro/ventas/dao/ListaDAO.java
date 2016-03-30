/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Lista;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class ListaDAO extends DAO {

    public ListaDAO() {
    }
    
    private ClienteDAO cdao = new ClienteDAO();

    public List<Lista> obtenerListas() throws SIGIPROException {

        List<Lista> resultado = new ArrayList<Lista>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.lista; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Lista lista = new Lista();
                
                lista.setId_lista(rs.getInt("id_enlistado"));
                lista.setCliente(cdao.obtenerCliente(rs.getInt("id_cliente")));
                lista.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                lista.setFecha_atencion(rs.getDate("fecha_atencion"));
                resultado.add(lista);

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

    public Lista obtenerLista(int id_lista) throws SIGIPROException {

        Lista resultado = new Lista();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.lista where id_enlistado = ?; ");
            consulta.setInt(1, id_lista);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_lista(rs.getInt("id_enlistado"));
                resultado.setCliente(cdao.obtenerCliente(rs.getInt("id_cliente")));
                resultado.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                resultado.setFecha_atencion(rs.getDate("fecha_atencion"));
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
    
    public int insertarLista(Lista p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.lista (id_cliente, fecha_solicitud, fecha_atencion)"
                    + " VALUES (?,?,?) RETURNING id_enlistado");

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha_solicitud());
            consulta.setDate(3, p.getFecha_atencion());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_enlistado");
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el solicitud");
        }
        return resultado;
    }

    public boolean editarLista(Lista p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.lista"
                    + " SET id_cliente=?, fecha_solicitud=?, fecha_atencion=?"
                    + " WHERE id_enlistado=?; "
            );

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha_solicitud());
            consulta.setDate(3, p.getFecha_atencion());
            consulta.setInt(4, p.getId_lista());
            
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la edición");
        }
        return resultado;
    }

    public boolean eliminarLista(int id_lista) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.lista "
                    + " WHERE id_enlistado=?; "
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

    public int insertarListaFechaA0(Lista p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.lista (id_cliente, fecha_solicitud)"
                    + " VALUES (?,?) RETURNING id_enlistado");

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha_solicitud());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_enlistado");
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el solicitud");
        }
        return resultado;
        
    }

    public boolean editarListaFechaA0(Lista p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.lista"
                    + " SET id_cliente=?, fecha_solicitud=?"
                    + " WHERE id_enlistado=?; "
            );

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha_solicitud());
            consulta.setInt(3, p.getId_lista());
            
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la edición");
        }
        return resultado;

    }

}
