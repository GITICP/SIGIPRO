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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
                int id_cliente = rs.getInt("id_cliente");
                if (id_cliente != 0){
                    lista.setCliente(cdao.obtenerCliente(id_cliente));
                }
                else{
                    lista.setNombre_cliente(rs.getString("nombre_cliente"));
                    lista.setCorreo(rs.getString("correo_electronico"));
                    lista.setTelefono(rs.getString("telefono"));
                }
                lista.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                lista.setFecha_atencion(rs.getDate("fecha_atencion"));
                lista.setObservaciones(rs.getString("observaciones"));
                lista.setDescripcion(rs.getString("descripcion"));
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

    public List<Lista> obtenerListasPorCliente(int id_cliente) throws SIGIPROException {

        List<Lista> resultado = new ArrayList<Lista>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.lista WHERE id_cliente = ?; ");
            consulta.setInt(1, id_cliente);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Lista lista = new Lista();
                
                lista.setId_lista(rs.getInt("id_enlistado"));
                lista.setCliente(cdao.obtenerCliente(rs.getInt("id_cliente")));
                lista.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                lista.setFecha_atencion(rs.getDate("fecha_atencion"));
                lista.setObservaciones(rs.getString("observaciones"));
                lista.setDescripcion(rs.getString("descripcion"));
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

    public List<Lista> obtenerListasPorNombreCliente(String nombre_cliente) throws SIGIPROException {

        List<Lista> resultado = new ArrayList<Lista>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.lista WHERE nombre_cliente = ?; ");
            consulta.setString(1, nombre_cliente);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Lista lista = new Lista();
                
                lista.setId_lista(rs.getInt("id_enlistado"));
                lista.setNombre_cliente(nombre_cliente);
                lista.setTelefono(rs.getString("telefono"));
                lista.setCorreo(rs.getString("correo_electronico"));
                lista.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                lista.setFecha_atencion(rs.getDate("fecha_atencion"));
                lista.setObservaciones(rs.getString("observaciones"));
                lista.setDescripcion(rs.getString("descripcion"));
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
                int id_cliente = rs.getInt("id_cliente");
                if (id_cliente != 0){
                    resultado.setCliente(cdao.obtenerCliente(id_cliente));
                }
                else{
                    resultado.setNombre_cliente(rs.getString("nombre_cliente"));
                    resultado.setCorreo(rs.getString("correo_electronico"));
                    resultado.setTelefono(rs.getString("telefono"));
                }
                resultado.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                resultado.setFecha_atencion(rs.getDate("fecha_atencion"));
                resultado.setObservaciones(rs.getString("observaciones"));
                resultado.setDescripcion(rs.getString("descripcion"));
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
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.lista (id_cliente, fecha_solicitud, fecha_atencion, observaciones, descripcion)"
                    + " VALUES (?,?,?,?,?) RETURNING id_enlistado");

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha_solicitud());
            consulta.setDate(3, p.getFecha_atencion());
            consulta.setString(4, p.getObservaciones());
            consulta.setString(5, p.getDescripcion());

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
    
    public int insertarListaClienteUnknown(Lista p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.lista (nombre_cliente, fecha_solicitud, fecha_atencion, telefono, correo_electronico, observaciones, descripcion)"
                    + " VALUES (?,?,?,?,?,?,?) RETURNING id_enlistado");

            consulta.setString(1, p.getNombre_cliente());
            consulta.setDate(2, p.getFecha_solicitud());
            consulta.setDate(3, p.getFecha_atencion());
            consulta.setString(4, p.getTelefono());
            consulta.setString(5, p.getCorreo());
            consulta.setString(6, p.getObservaciones());
            consulta.setString(7, p.getDescripcion());

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
    
    public int insertarListaClienteUnknownFechaA0(Lista p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.lista (nombre_cliente, fecha_solicitud, telefono, correo_electronico, observaciones, descripcion)"
                    + " VALUES (?,?,?,?,?,?) RETURNING id_enlistado");

            consulta.setString(1, p.getNombre_cliente());
            consulta.setDate(2, p.getFecha_solicitud());
            consulta.setString(3, p.getTelefono());
            consulta.setString(4, p.getCorreo());
            consulta.setString(5, p.getObservaciones());
            consulta.setString(6, p.getDescripcion());

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
                    + " SET id_cliente=?, fecha_solicitud=?, fecha_atencion=?, observaciones=?, descripcion=?"
                    + " WHERE id_enlistado=?; "
            );

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha_solicitud());
            consulta.setDate(3, p.getFecha_atencion());
            consulta.setString(4, p.getObservaciones());
            consulta.setString(5, p.getDescripcion());
            consulta.setInt(6, p.getId_lista());
            
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
    
    public boolean editarListaClienteUnknown(Lista p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.lista"
                    + " SET nombre_cliente=?, fecha_solicitud=?, fecha_atencion=?, telefono=?, correo_electronico=?, observaciones=?, descripcion=?, id_cliente=?"
                    + " WHERE id_enlistado=?; "
            );

            consulta.setString(1, p.getNombre_cliente());
            consulta.setDate(2, p.getFecha_solicitud());
            consulta.setDate(3, p.getFecha_atencion());
            consulta.setString(4, p.getTelefono());
            consulta.setString(5, p.getCorreo());
            consulta.setString(6, p.getObservaciones());
            consulta.setString(7, p.getDescripcion());
            consulta.setNull(8, 0);
            consulta.setInt(9, p.getId_lista());
            
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
    
    public boolean editarListaClienteUnknownFechaA0(Lista p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.lista"
                    + " SET nombre_cliente=?, fecha_solicitud=?, telefono=?, correo_electronico=?, observaciones=?, descripcion=?, id_cliente=?"
                    + " WHERE id_enlistado=?; "
            );

            consulta.setString(1, p.getNombre_cliente());
            consulta.setDate(2, p.getFecha_solicitud());
            consulta.setString(3, p.getTelefono());
            consulta.setString(4, p.getCorreo());
            consulta.setString(5, p.getObservaciones());
            consulta.setString(6, p.getDescripcion());
            consulta.setNull(7, 0);
            consulta.setInt(8, p.getId_lista());
            
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

    public boolean marcarFechaAtencion(int id_lista) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.lista "
                    + " SET fecha_atencion=?"
                    + " WHERE id_enlistado=?; "
            );

            String df = "dd/MM/yyyy";
            String dateInString =new SimpleDateFormat(df).format(new Date());
            //java.util.Calendar cal = java.util.Calendar.getInstance();
            //java.util.Date utilDate = cal.getTime();
            //java.util.Date result = df.parse(utilDate.toString());
            //java.sql.Date fecha_solicitudSQL = new java.sql.Date(result.getTime());
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = format.parse(dateInString);
            java.sql.Date fecha_solicitudSQL = new java.sql.Date(utilDate.getTime());
            consulta.setDate(1, fecha_solicitudSQL);
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

    public int insertarListaFechaA0(Lista p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.lista (id_cliente, fecha_solicitud, observaciones, descripcion)"
                    + " VALUES (?,?,?,?) RETURNING id_enlistado");

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha_solicitud());
            consulta.setString(3, p.getObservaciones());
            consulta.setString(4, p.getDescripcion());

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
                    + " SET id_cliente=?, fecha_solicitud=?, observaciones=?, descripcion=?"
                    + " WHERE id_enlistado=?; "
            );

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setDate(2, p.getFecha_solicitud());
            consulta.setString(3, p.getObservaciones());
            consulta.setString(4, p.getDescripcion());
            consulta.setInt(5, p.getId_lista());
            
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

    public boolean clienteEnLista(int id_cliente) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.lista where id_cliente = ?; ");
            consulta.setInt(1, id_cliente);
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
    
    public boolean clienteNombreEnLista(String nombre_cliente) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.lista where nombre_cliente = ?; ");
            consulta.setString(1, nombre_cliente);
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

}
