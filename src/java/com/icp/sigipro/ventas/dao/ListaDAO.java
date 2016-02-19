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
                
                lista.setId_lista(rs.getInt("id_lista"));
                lista.setCliente(cdao.obtenerCliente(rs.getInt("id_cliente")));
                lista.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                lista.setPrioridad(rs.getInt("prioridad"));
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
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.lista where id_lista = ?; ");
            consulta.setInt(1, id_lista);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_lista(rs.getInt("id_lista"));
                resultado.setCliente(cdao.obtenerCliente(rs.getInt("id_cliente")));
                resultado.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                resultado.setPrioridad(rs.getInt("prioridad"));
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
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.lista (id_cliente, fecha_ingreso, prioridad)"
                    + " VALUES (?,?,?) RETURNING id_lista");

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha_ingreso());
            consulta.setInt(3, p.getPrioridad());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_lista");
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

    public boolean editarLista(Lista p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.lista"
                    + " SET id_cliente=?, fecha_ingreso=?, prioridad=?"
                    + " WHERE id_lista=?; "
            );

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha_ingreso());
            consulta.setInt(3, p.getPrioridad());
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
