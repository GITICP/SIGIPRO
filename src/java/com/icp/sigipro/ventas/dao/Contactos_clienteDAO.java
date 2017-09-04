/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Contactos_cliente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class Contactos_clienteDAO extends DAO {

    public Contactos_clienteDAO() {
    }

    public List<Contactos_cliente> obtenerContactos_Del_Cliente(int id_cliente) throws SIGIPROException {

        List<Contactos_cliente> resultado = new ArrayList<Contactos_cliente>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.contactos_cliente WHERE id_cliente=?; ");
            consulta.setInt(1, id_cliente);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Contactos_cliente contactos_cliente = new Contactos_cliente();
                ClienteDAO cDAO = new ClienteDAO();
                
                contactos_cliente.setId_contacto(rs.getInt("id_contacto"));
                contactos_cliente.setCliente(cDAO.obtenerCliente(id_cliente));
                contactos_cliente.setNombre(rs.getString("nombre"));
                contactos_cliente.setTelefono(rs.getString("telefono"));
                contactos_cliente.setTelefono2(rs.getString("telefono2"));
                contactos_cliente.setCorreo_electronico(rs.getString("correo_electronico"));
                contactos_cliente.setCorreo_electronico2(rs.getString("correo_electronico2"));
                resultado.add(contactos_cliente);
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

    public Contactos_cliente obtenerContactos_cliente(int id_contactos_cliente) throws SIGIPROException {

        Contactos_cliente resultado = new Contactos_cliente();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.contactos_cliente where id_contacto = ?; ");
            consulta.setInt(1, id_contactos_cliente);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                ClienteDAO cDAO = new ClienteDAO();
                resultado.setId_contacto(rs.getInt("id_contacto"));
                resultado.setCliente(cDAO.obtenerCliente(rs.getInt("id_cliente")));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setTelefono(rs.getString("telefono"));
                resultado.setTelefono2(rs.getString("telefono2"));
                resultado.setCorreo_electronico(rs.getString("correo_electronico"));
                resultado.setCorreo_electronico2(rs.getString("correo_electronico2"));
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
    
    public boolean insertarContactos_cliente(Contactos_cliente p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.contactos_cliente (id_cliente, nombre, telefono, telefono2, "
                    + "correo_electronico, correo_electronico2)"
                    + " VALUES (?,?,?,?,?,?) RETURNING id_contacto");

            consulta.setInt(1,p.getCliente().getId_cliente());
            consulta.setString(2,p.getNombre());
            consulta.setString(3,p.getTelefono());
            consulta.setString(4,p.getTelefono2());
            consulta.setString(5,p.getCorreo_electronico());
            consulta.setString(6,p.getCorreo_electronico2());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
           if (ex.getMessage().contains("llave"))
              { 
                throw new SIGIPROException("El nombre del contacto debe ser único");}
            else {
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");}
        }
        return resultado;
    }

    public boolean editarContactos_cliente(Contactos_cliente p) throws SIGIPROException {

        boolean resultado = false;
        
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.contactos_cliente"
                    + " SET nombre=?, telefono=?, telefono2=?, correo_electronico=?, correo_electronico2=?"
                    + " WHERE id_contacto=?; "
            );
            
            consulta.setString(1,p.getNombre());
            consulta.setString(2,p.getTelefono());
            consulta.setString(3,p.getTelefono2());
            consulta.setString(4,p.getCorreo_electronico());
            consulta.setString(5,p.getCorreo_electronico2());
            consulta.setInt(6,p.getId_contacto());
            
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (ex.getMessage().contains("llave"))
              { 
                throw new SIGIPROException("El nombre del contacto debe ser único");}
            else {
            throw new SIGIPROException("Se produjo un error al procesar la edición");}
        }
        return resultado;
    }

    public boolean eliminarContactos_cliente(int id_contactos_cliente) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.contactos_cliente "
                    + " WHERE id_contacto=?; "
            );

            consulta.setInt(1, id_contactos_cliente);

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
