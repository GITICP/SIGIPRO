/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Cliente;;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class ClienteDAO extends DAO {

    public ClienteDAO() {
    }

    public boolean existeCliente(String nombre, String cedula) throws SIGIPROException {
        boolean resultado = false;
        List<Cliente> clientes = this.obtenerClientes();
        int contador = 0;
        while ((resultado != false) || (contador < clientes.size())){
            Cliente c = clientes.get(contador);
            if (c.getCedula().equalsIgnoreCase(cedula) || c.getNombre().equalsIgnoreCase(nombre)){
                resultado = true;
            }
            contador ++;
        }
        return resultado;
    }
    
    public List<Cliente> obtenerClientes() throws SIGIPROException {

        List<Cliente> resultado = new ArrayList<Cliente>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.cliente; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                
                cliente.setId_cliente(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setPais(rs.getString("pais"));
                cliente.setTipo(rs.getString("tipo"));
                cliente.setPersona(rs.getString("persona"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setCedula(rs.getString("cedula"));
                resultado.add(cliente);

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

    public Cliente obtenerCliente(int id_cliente) throws SIGIPROException {

        Cliente resultado = new Cliente();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.cliente where id_cliente = ?; ");
            consulta.setInt(1, id_cliente);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_cliente(rs.getInt("id_cliente"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setPais(rs.getString("pais"));
                resultado.setTipo(rs.getString("tipo"));
                resultado.setPersona(rs.getString("persona"));
                resultado.setDireccion(rs.getString("direccion"));
                resultado.setCedula(rs.getString("cedula"));
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
    
    public int insertarCliente(Cliente p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.cliente (nombre, pais, tipo, direccion, cedula, persona)"
                    + " VALUES (?,?,?,?,?,?) RETURNING id_cliente");

            consulta.setString(1, p.getNombre());
            consulta.setString(2, p.getPais());
            consulta.setString(3, p.getTipo());
            consulta.setString(4, p.getDireccion());
            consulta.setString(5, p.getCedula());
            consulta.setString(6, p.getPersona());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_cliente");
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

    public boolean editarCliente(Cliente p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.cliente"
                    + " SET nombre=?, pais=?, tipo=?, direccion=?, cedula=?, persona=?"
                    + " WHERE id_cliente=?; "
            );

            consulta.setString(1, p.getNombre());
            consulta.setString(2, p.getPais());
            consulta.setString(3, p.getTipo());
            consulta.setString(4, p.getDireccion());
            consulta.setString(5, p.getCedula());
            consulta.setString(6, p.getPersona());
            consulta.setInt(7, p.getId_cliente());
            
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

    public boolean eliminarCliente(int id_cliente) throws SIGIPROException {

        Contactos_clienteDAO cDAO = new Contactos_clienteDAO();
        Intencion_ventaDAO iDAO = new Intencion_ventaDAO();
        boolean resultado = false;
        if (!cDAO.obtenerContactos_Del_Cliente(id_cliente).isEmpty() || iDAO.CantidadIntencionesConCliente(id_cliente) > 0){
            throw new SIGIPROException("Cliente relacionado con otros elementos.");
        }
        else{
            try {
                PreparedStatement consulta = getConexion().prepareStatement(
                        " DELETE FROM ventas.cliente "
                        + " WHERE id_cliente=?; "
                );

                consulta.setInt(1, id_cliente);

                if (consulta.executeUpdate() == 1) {
                    resultado = true;
                }
                consulta.close();
                cerrarConexion();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new SIGIPROException("Se produjo un error al procesar la eliminación");
            }
        }
        return resultado;
    }

    public int relacionesConOtrasTablas(int id_cliente) throws SIGIPROException{
        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" select " +
                "(select count(*) from ventas.intencion_venta where id_cliente =?)" +
                "+" +
                "(select count(*) from ventas.cotizacion where id_cliente =?)" +
                "+" +
                "(select count(*) from ventas.orden_compra where id_cliente =?)" +
                "+" +
                "(select count(*) from ventas.factura where id_cliente =?)" +
                "+" +
                "(select count(*) from ventas.seguimiento_venta where id_cliente =?)" +
                "+" +
                "(select count(*) from ventas.tratamiento where id_cliente =?)" +
                "+" +
                "(select count(*) from ventas.encuesta_satisfaccion where id_cliente =?)" +
                "+" +
                "(select count(*) from ventas.lista where id_cliente =?)" +
                "as count;");

            consulta.setInt(1, id_cliente);
            consulta.setInt(2, id_cliente);
            consulta.setInt(3, id_cliente);
            consulta.setInt(4, id_cliente);
            consulta.setInt(5, id_cliente);
            consulta.setInt(6, id_cliente);
            consulta.setInt(7, id_cliente);
            consulta.setInt(8, id_cliente);

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("count");
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
}
