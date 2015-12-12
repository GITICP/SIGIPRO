/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.bioterio.dao.*;
import com.icp.sigipro.bioterio.modelos.Cepa;
import com.icp.sigipro.bioterio.modelos.EntregaRatonera;
import com.icp.sigipro.bioterio.modelos.SolicitudRatonera;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.produccion.modelos.Veneno_Produccion;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.serpentario.dao.VenenoDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class Veneno_ProduccionDAO extends DAO {

    public Veneno_ProduccionDAO() {
    }

    public List<Veneno_Produccion> obtenerVenenos_Produccion() throws SIGIPROException {

        List<Veneno_Produccion> resultado = new ArrayList<Veneno_Produccion>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.veneno_produccion; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Veneno_Produccion veneno_produccion = new Veneno_Produccion();
                VenenoDAO vDAO = new VenenoDAO();
                
                veneno_produccion.setId_veneno(rs.getInt("id_veneno"));
                veneno_produccion.setVeneno(rs.getString("veneno"));
                veneno_produccion.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                veneno_produccion.setCantidad(rs.getInt("cantidad"));
                veneno_produccion.setObservaciones(rs.getString("observaciones"));
                veneno_produccion.setVeneno_serpentario(vDAO.obtenerVeneno(rs.getInt("id_veneno_serpentario")));
                resultado.add(veneno_produccion);

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

    public Veneno_Produccion obtenerVeneno_Produccion(int id_veneno) throws SIGIPROException {

        Veneno_Produccion resultado = new Veneno_Produccion();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.veneno_produccion where id_veneno = ?; ");
            consulta.setInt(1, id_veneno);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                VenenoDAO vDAO = new VenenoDAO();
                
                resultado.setId_veneno(rs.getInt("id_veneno"));
                resultado.setVeneno(rs.getString("veneno"));
                resultado.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                resultado.setCantidad(rs.getInt("cantidad"));
                resultado.setObservaciones(rs.getString("observaciones"));
                resultado.setVeneno_serpentario(vDAO.obtenerVeneno(rs.getInt("id_veneno_serpentario")));
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
    
    public boolean insertarVeneno_Produccion(Veneno_Produccion p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.veneno_produccion (veneno, fecha_ingreso, cantidad, "
                    + "observaciones, id_veneno_serpentario)"
                    + " VALUES (?,?,?,?,?) RETURNING id_veneno");

            consulta.setString(1, p.getVeneno());
            consulta.setDate(2, p.getFecha_ingreso());
            consulta.setInt(3, p.getCantidad());
            consulta.setString(4, p.getObservaciones());
            consulta.setInt(5, p.getVeneno_serpentario().getId_veneno());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
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

    public boolean editarVeneno_Produccion(Veneno_Produccion p) throws SIGIPROException {

        boolean resultado = false;

        System.out.println("veneno="+p.getVeneno()+", fecha_ingreso="+p.getFecha_ingreso()+", cantidad="+p.getCantidad()+
                ", observaciones="+p.getObservaciones()+", id_veneno_serpentario="+p.getVeneno_serpentario().getId_veneno()+". Id_veneno="+p.getId_veneno());
        
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE produccion.veneno_produccion"
                    + " SET veneno=?, fecha_ingreso=?, cantidad=?, observaciones=?, id_veneno_serpentario=?"
                    + " WHERE id_veneno=?; "
            );

            consulta.setString(1, p.getVeneno());
            consulta.setDate(2, p.getFecha_ingreso());
            consulta.setInt(3, p.getCantidad());
            consulta.setString(4, p.getObservaciones());
            consulta.setInt(5, p.getVeneno_serpentario().getId_veneno());
            consulta.setInt(6, p.getId_veneno());
            
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la edición");
        }
        System.out.println("Resultado del editar: "+resultado);
        return resultado;
    }

    public boolean eliminarVeneno_Produccion(int id_veneno) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM produccion.veneno_produccion "
                    + " WHERE id_veneno=?; "
            );

            consulta.setInt(1, id_veneno);

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

    /*public SolicitudRatonera obtenerSolicitudRatonera(int id) throws SIGIPROException {

        SolicitudRatonera solicitud_ratonera = new SolicitudRatonera();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.solicitudes_ratonera where id_solicitud = ?");

            consulta.setInt(1, id);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                CepaDAO cep = new CepaDAO();
                UsuarioDAO usr = new UsuarioDAO();
                solicitud_ratonera.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud_ratonera.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                solicitud_ratonera.setNumero_animales(rs.getInt("numero_animales"));
                solicitud_ratonera.setPeso_requerido(rs.getString("peso_requerido"));
                solicitud_ratonera.setNumero_cajas(rs.getInt("numero_cajas"));
                solicitud_ratonera.setSexo(rs.getString("sexo"));
                solicitud_ratonera.setCepa(cep.obtenerCepa(rs.getInt("id_cepa")));
                solicitud_ratonera.setUsuario_solicitante(usr.obtenerUsuario(rs.getInt("usuario_solicitante")));
                solicitud_ratonera.setUsuario_utiliza(usr.obtenerUsuario(rs.getInt("usuario_utiliza")));
                solicitud_ratonera.setObservaciones(rs.getString("observaciones"));
                solicitud_ratonera.setObservaciones_rechazo(rs.getString("observaciones_rechazo"));
                solicitud_ratonera.setEstado(rs.getString("estado"));
                solicitud_ratonera.setFecha_necesita(rs.getDate("fecha_necesita"));

            }
            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return solicitud_ratonera;
    }


    public List<SolicitudRatonera> obtenerSolicitudesRatoneraAdm() throws SIGIPROException {

        List<SolicitudRatonera> resultado = new ArrayList<SolicitudRatonera>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.solicitudes_ratonera s "
                    + "INNER JOIN seguridad.usuarios u ON s.usuario_solicitante = u.id_usuario "
                    + "INNER JOIN bioterio.cepas c ON s.id_cepa = c.id_cepa "
                    + "WHERE s.estado NOT IN ('Rechazada', 'Cerrada', 'Cerrada (Entrega Parcial)', 'Entregada', 'Cerrada (Anulada)') ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                SolicitudRatonera solicitud_ratonera = new SolicitudRatonera();
                Cepa cep;
                Usuario usr;

                solicitud_ratonera.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud_ratonera.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                solicitud_ratonera.setNumero_animales(rs.getInt("numero_animales"));
                solicitud_ratonera.setPeso_requerido(rs.getString("peso_requerido"));
                solicitud_ratonera.setNumero_cajas(rs.getInt("numero_cajas"));
                solicitud_ratonera.setSexo(rs.getString("sexo"));
                solicitud_ratonera.setObservaciones(rs.getString("observaciones"));
                solicitud_ratonera.setObservaciones_rechazo(rs.getString("observaciones_rechazo"));
                solicitud_ratonera.setEstado(rs.getString("estado"));
                cep = new Cepa(rs.getInt("id_cepa"), rs.getString("nombre"));
                usr = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre_usuario"), rs.getString("correo"), rs.getString("nombre_completo"), rs.getString("cedula"), rs.getInt("id_seccion"), rs.getInt("id_puesto"), rs.getDate("fecha_activacion"), rs.getDate("fecha_desactivacion"), rs.getBoolean("estado"));
                UsuarioDAO usr2 = new UsuarioDAO();
                solicitud_ratonera.setUsuario_utiliza(usr2.obtenerUsuario(rs.getInt("usuario_utiliza")));
                solicitud_ratonera.setCepa(cep);
                solicitud_ratonera.setUsuario_solicitante(usr);
                solicitud_ratonera.setFecha_necesita(rs.getDate("fecha_necesita"));
                resultado.add(solicitud_ratonera);
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

    public List<SolicitudRatonera> obtenerSolicitudesRatoneraAdmCompletadas() throws SIGIPROException {
        
        List<SolicitudRatonera> resultado = new ArrayList<SolicitudRatonera>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(
                      "  WITH consulta AS ( "
                    + "    SELECT s.*, c.*, u.*, sr.id_entrega, sr.fecha_entrega, ROW_NUMBER() OVER(PARTITION BY s.id_solicitud ORDER BY sr.fecha_entrega DESC) AS num_fila "
                    + "    FROM bioterio.solicitudes_ratonera s "
                    + "      INNER JOIN seguridad.usuarios u ON s.usuario_solicitante = u.id_usuario "
                    + "      INNER JOIN bioterio.cepas c ON s.id_cepa = c.id_cepa "
                    + "      LEFT JOIN bioterio.entregas_solicitudes_ratonera sr ON sr.id_solicitud = s.id_solicitud "
                    + "    WHERE s.estado IN ('Rechazada', 'Cerrada', 'Cerrada (Entrega Parcial)', 'Entregada', 'Cerrada (Anulada)')"
                    + ") SELECT consulta.* FROM consulta WHERE consulta.num_fila = 1 "
            );
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                SolicitudRatonera solicitud_ratonera = new SolicitudRatonera();
                Cepa cep;
                Usuario usr;

                solicitud_ratonera.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud_ratonera.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                solicitud_ratonera.setNumero_animales(rs.getInt("numero_animales"));
                solicitud_ratonera.setPeso_requerido(rs.getString("peso_requerido"));
                solicitud_ratonera.setNumero_cajas(rs.getInt("numero_cajas"));
                solicitud_ratonera.setSexo(rs.getString("sexo"));
                solicitud_ratonera.setObservaciones(rs.getString("observaciones"));
                solicitud_ratonera.setObservaciones_rechazo(rs.getString("observaciones_rechazo"));
                solicitud_ratonera.setEstado(rs.getString("estado"));
                cep = new Cepa(rs.getInt("id_cepa"), rs.getString("nombre"));
                usr = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre_usuario"), rs.getString("correo"), rs.getString("nombre_completo"), rs.getString("cedula"), rs.getInt("id_seccion"), rs.getInt("id_puesto"), rs.getDate("fecha_activacion"), rs.getDate("fecha_desactivacion"), rs.getBoolean("estado"));
                UsuarioDAO usr2 = new UsuarioDAO();
                solicitud_ratonera.setUsuario_utiliza(usr2.obtenerUsuario(rs.getInt("usuario_utiliza")));
                solicitud_ratonera.setCepa(cep);
                solicitud_ratonera.setUsuario_solicitante(usr);
                solicitud_ratonera.setFecha_necesita(rs.getDate("fecha_necesita"));
                resultado.add(solicitud_ratonera);
                
                if (rs.getInt("id_entrega") != 0) {
                    EntregaRatonera e = new EntregaRatonera();
                    e.setId_entrega(rs.getInt("id_entrega"));
                    e.setFecha_entrega(rs.getDate("fecha_entrega"));
                    solicitud_ratonera.setEntrega(e);
                }
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

    public List<SolicitudRatonera> obtenerSolicitudesRatoneraCompletadas(int id_seccion) throws SIGIPROException {
        List<SolicitudRatonera> resultado = new ArrayList<SolicitudRatonera>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(
                      " WITH consulta AS ( "
                    + "   SELECT *, ROW_NUMBER() OVER(PARTITION BY s.id_solicitud ORDER BY sr.fecha_entrega DESC) AS num_fila "
                    + "   FROM bioterio.solicitudes_ratonera s "
                    + "     INNER JOIN seguridad.usuarios u ON s.usuario_solicitante = u.id_usuario "
                    + "     INNER JOIN bioterio.cepas c ON s.id_cepa = c.id_cepa "
                    + "     LEFT JOIN bioterio.entregas_solicitudes_ratonera sr ON sr.id_solicitud = s.id_solicitud "
                    + "     LEFT JOIN seguridad.usuarios u2 ON s.usuario_utiliza = u2.id_usuario "
                    + "   WHERE u.id_seccion = ? AND s.estado IN ('Rechazada', 'Cerrada', 'Cerrada (Entrega Parcial)', 'Entregada', 'Cerrada (Anulada)') "
                    + " ) SELECT consulta.* FROM consulta WHERE consulta.num_fila = 1"
            );
            consulta.setInt(1, id_seccion);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                SolicitudRatonera solicitud_ratonera = new SolicitudRatonera();
                Cepa cep;
                Usuario usr;

                solicitud_ratonera.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud_ratonera.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                solicitud_ratonera.setNumero_animales(rs.getInt("numero_animales"));
                solicitud_ratonera.setPeso_requerido(rs.getString("peso_requerido"));
                solicitud_ratonera.setNumero_cajas(rs.getInt("numero_cajas"));
                solicitud_ratonera.setSexo(rs.getString("sexo"));
                solicitud_ratonera.setObservaciones(rs.getString("observaciones"));
                solicitud_ratonera.setObservaciones_rechazo(rs.getString("observaciones_rechazo"));
                solicitud_ratonera.setEstado(rs.getString("estado"));
                cep = new Cepa(rs.getInt("id_cepa"), rs.getString("nombre"));
                usr = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre_usuario"), rs.getString("correo"), rs.getString("nombre_completo"), rs.getString("cedula"), rs.getInt("id_seccion"), rs.getInt("id_puesto"), rs.getDate("fecha_activacion"), rs.getDate("fecha_desactivacion"), rs.getBoolean("estado"));
                solicitud_ratonera.setCepa(cep);
                solicitud_ratonera.setUsuario_solicitante(usr);
                UsuarioDAO usr2 = new UsuarioDAO();
                solicitud_ratonera.setUsuario_utiliza(usr2.obtenerUsuario(rs.getInt("usuario_utiliza")));
                solicitud_ratonera.setFecha_necesita(rs.getDate("fecha_necesita"));
                resultado.add(solicitud_ratonera);
                
                if (rs.getInt("id_entrega") != 0) {
                    EntregaRatonera e = new EntregaRatonera();
                    e.setId_entrega(rs.getInt("id_entrega"));
                    e.setFecha_entrega(rs.getDate("fecha_entrega"));
                    solicitud_ratonera.setEntrega(e);
                }

            }
            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }*/
}
