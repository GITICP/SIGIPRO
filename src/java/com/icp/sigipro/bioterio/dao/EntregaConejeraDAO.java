/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bioterio.modelos.Cepa;
import com.icp.sigipro.bioterio.modelos.EntregaConejera;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class EntregaConejeraDAO
{

    private Connection conexion;

    public EntregaConejeraDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }

    public boolean insertarEntregaConejera(EntregaConejera p) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bioterio.entregas_solicitudes_conejera (id_solicitud, fecha_entrega, numero_animales, peso, "
                                                                        + "sexo,usuario_recipiente)"
                                                                        + " VALUES (?,?,?,?,?,?) RETURNING id_entrega");

            consulta.setInt(1, p.getSolicitud().getId_solicitud());
            consulta.setDate(2, p.getFecha_entrega());
            consulta.setInt(3, p.getNumero_animales());
            consulta.setString(4, p.getPeso());
            consulta.setString(5, p.getSexo());
            consulta.setInt(6, p.getUsuario_recipiente().getID());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
                revisarCompletitudEntrega(p.getSolicitud().getId_solicitud());
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");
        }
        return resultado;
    }

    public void revisarCompletitudEntrega(int id_solicitud) throws SIGIPROException
    {
        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT SUM(numero_animales) as suma FROM bioterio.entregas_solicitudes_conejera where id_solicitud = ?");
            consulta.setInt(1, id_solicitud);
            PreparedStatement consulta2 = getConexion().prepareStatement("SELECT numero_animales FROM bioterio.solicitudes_conejera where id_solicitud = ?");
            consulta2.setInt(1, id_solicitud);

            ResultSet rs = consulta.executeQuery();
            ResultSet rs2 = consulta2.executeQuery();
            if (rs.next() && rs2.next()) {
                if (rs.getInt("suma") >= rs2.getInt("numero_animales")) {
                    PreparedStatement consulta3 = getConexion().prepareStatement(
                            " UPDATE bioterio.solicitudes_conejera "
                            + " SET estado='Entregada'"
                            + " WHERE id_solicitud=?; "
                    );
                    consulta3.setInt(1, id_solicitud);
                    consulta3.executeUpdate();
                    consulta3.close();
                }
            }
            rs.close();
            rs2.close();
            consulta.close();
            consulta2.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            throw new SIGIPROException("Se produjo un error al revisar la completitud de la entrega");
        }
    }

    public boolean eliminarEntregaConejera(int id_solicitud) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM bioterio.entregas_solicitudes_conejera "
                    + " WHERE id_solicitud=?; "
            );

            consulta.setInt(1, id_solicitud);

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            throw new SIGIPROException("Se produjo un error al procesar la eliminación");
        }
        return resultado;
    }

    public EntregaConejera obtenerEntregaConejera(int id) throws SIGIPROException
    {

        EntregaConejera entrega_conejera = new EntregaConejera();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bioterio.entregas_solicitudes_conejera where id_entrega = ?");

            consulta.setInt(1, id);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                UsuarioDAO usr = new UsuarioDAO();
                SolicitudConejeraDAO sdao = new SolicitudConejeraDAO();
                entrega_conejera.setId_entrega(rs.getInt("id_entrega"));
                entrega_conejera.setSolicitud(sdao.obtenerSolicitudConejera(rs.getInt("id_solicitud")));
                entrega_conejera.setFecha_entrega(rs.getDate("fecha_entrega"));
                entrega_conejera.setNumero_animales(rs.getInt("numero_animales"));
                entrega_conejera.setPeso(rs.getString("peso"));
                entrega_conejera.setSexo(rs.getString("sexo"));
                entrega_conejera.setUsuario_recipiente(usr.obtenerUsuario(rs.getInt("usuario_recipiente")));
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return entrega_conejera;
    }

    public List<EntregaConejera> obtenerEntregasConejera(int id_solicitud) throws SIGIPROException
    {

        List<EntregaConejera> resultado = new ArrayList<EntregaConejera>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM bioterio.entregas_solicitudes_conejera e "
                                                      + "INNER JOIN seguridad.usuarios u ON e.usuario_recipiente = u.id_usuario "
                                                      + " WHERE e.id_solicitud=?");
            consulta.setInt(1, id_solicitud);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                EntregaConejera entrega_conejera = new EntregaConejera();
                Usuario usr;
                SolicitudConejeraDAO sdao = new SolicitudConejeraDAO();
                entrega_conejera.setId_entrega(rs.getInt("id_entrega"));
                entrega_conejera.setSolicitud(sdao.obtenerSolicitudConejera(rs.getInt("id_solicitud")));
                entrega_conejera.setFecha_entrega(rs.getDate("fecha_entrega"));
                entrega_conejera.setNumero_animales(rs.getInt("numero_animales"));
                entrega_conejera.setPeso(rs.getString("peso"));
                entrega_conejera.setSexo(rs.getString("sexo"));
                usr = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre_usuario"), rs.getString("correo"), rs.getString("nombre_completo"), rs.getString("cedula"), rs.getInt("id_seccion"), rs.getInt("id_puesto"), rs.getDate("fecha_activacion"), rs.getDate("fecha_desactivacion"), rs.getBoolean("estado"));
                entrega_conejera.setUsuario_recipiente(usr);
                resultado.add(entrega_conejera);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }

    private Connection getConexion()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        if (conexion == null) {
            conexion = s.conectar();
        }
        else {
            try {
                if (conexion.isClosed()) {
                    conexion = s.conectar();
                }
            }
            catch (Exception ex) {
                conexion = null;
            }
        }
        return conexion;
    }

    private void cerrarConexion()
    {
        if (conexion != null) {
            try {
                if (conexion.isClosed()) {
                    conexion.close();
                }
            }
            catch (Exception ex) {
                conexion = null;
            }
        }
    }
}
