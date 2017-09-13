 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.produccion.modelos.Categoria_AA;
import com.icp.sigipro.seguridad.modelos.*;
import com.icp.sigipro.utilidades.UtilidadEmail;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Boga
 */
public class UsuarioDAO extends DAO
{
    
    public UsuarioDAO(){}

    @SuppressWarnings("Convert2Diamond")
    private List<Usuario> llenarUsuarios(ResultSet resultadoConsulta) throws SQLException
    {
        List<Usuario> resultado = new ArrayList<Usuario>();
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();

        while (resultadoConsulta.next()) {
            int idUsuario = resultadoConsulta.getInt("id_usuario");
            String nombreUsuario = resultadoConsulta.getString("nombre_usuario");
            String correo = resultadoConsulta.getString("correo");
            String nombreCompleto = resultadoConsulta.getString("nombre_completo");
            String cedula = resultadoConsulta.getString("cedula");
            int id_seccion = resultadoConsulta.getInt("id_seccion");
            int id_puesto = resultadoConsulta.getInt("id_puesto");
            Date fechaActivacion = resultadoConsulta.getDate("fecha_activacion");
            Date fechaDesactivacion = resultadoConsulta.getDate("fecha_desactivacion");
            boolean activo = resultadoConsulta.getBoolean("estado");

            PreparedStatement consultaSeccion = conexion.prepareStatement(" Select nombre_seccion "
                                                                          + " From seguridad.secciones"
                                                                          + " Where id_seccion = ? ");
            consultaSeccion.setInt(1, id_seccion);
            ResultSet resultadoConsultaSeccion = consultaSeccion.executeQuery();
            resultadoConsultaSeccion.next();
            String seccion = resultadoConsultaSeccion.getString("nombre_seccion");

            PreparedStatement consultaPuesto = conexion.prepareStatement(" Select nombre_puesto "
                                                                         + " From seguridad.puestos"
                                                                         + " Where id_puesto = ? ");
            consultaPuesto.setInt(1, id_puesto);
            ResultSet resultadoconsultaPuesto = consultaPuesto.executeQuery();
            resultadoconsultaPuesto.next();
            String puesto = resultadoconsultaPuesto.getString("nombre_puesto");

            Usuario us = new Usuario(idUsuario, nombreUsuario, correo, nombreCompleto,
                                     cedula, id_seccion, id_puesto, fechaActivacion, fechaDesactivacion, activo);
            us.setNombreSeccion(seccion);
            us.setNombrePuesto(puesto);
            resultado.add(us);

            resultadoConsultaSeccion.close();
            resultadoconsultaPuesto.close();
            consultaSeccion.close();
            consultaPuesto.close();
        }
        conexion.close();
        return resultado;
    }

    public int validarInicioSesion(String usuario, String contrasenna)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        int resultado = -1;
        
        PreparedStatement consultaContrasena = null;
        ResultSet resultadoConsulta = null;
        PreparedStatement consultaEstado = null;
        ResultSet resultadoConsultaEstado = null;
                          

        if (conexion != null) {
            try {
                consultaContrasena = conexion.prepareStatement("SELECT id_usuario, contrasena_caducada "
                                                                                 + "FROM seguridad.usuarios us "
                                                                                 + "WHERE us.nombre_usuario = ? and us.contrasena = ? ");

                consultaContrasena.setString(1, usuario);
                String hash = md5(contrasenna);
                consultaContrasena.setString(2, hash);
                resultadoConsulta = consultaContrasena.executeQuery();
                resultadoConsulta.next();
                resultado = resultadoConsulta.getInt("id_usuario");
                if (resultadoConsulta.getBoolean("contrasena_caducada")) {
                    resultado = 0;
                }
                consultaEstado = conexion.prepareStatement("SELECT id_usuario, contrasena_caducada "
                                                                             + "FROM seguridad.usuarios us "
                                                                             + "WHERE us.nombre_usuario = ? AND us.estado = false ");

                consultaEstado.setString(1, usuario);
                resultadoConsultaEstado = consultaEstado.executeQuery();

                if (resultadoConsultaEstado.next()) {
                    resultado = -2;
                }
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                cerrarSilencioso(resultadoConsultaEstado);
                cerrarSilencioso(consultaEstado);
                cerrarSilencioso(resultadoConsulta);
                cerrarSilencioso(consultaContrasena);
                cerrarSilencioso(conexion);
            }
        }
        return resultado;
    }
    
    public List<Usuario> obtenerUsuariosProduccion(int id_seccion)
    {
        List<Usuario> resultado = new ArrayList<Usuario>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT id_usuario, nombre_completo "
                    + "FROM seguridad.usuarios "
                    + "WHERE id_seccion = ?; ");
            consulta.setInt(1, id_seccion);
            rs = consulta.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setNombre_completo(rs.getString("nombre_completo"));
                resultado.add(usuario);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public Usuario obtenerUsuario(int idUsuario)
    {
        Usuario resultado = null;
        try {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();

            PreparedStatement consulta = conexion.prepareStatement(" Select nombre_usuario, correo, nombre_completo, cedula, id_seccion, "
                                                                   + " id_puesto, fecha_activacion, fecha_desactivacion, estado "
                                                                   + " From seguridad.usuarios"
                                                                   + " Where id_usuario = ? ");

            consulta.setInt(1, idUsuario);

            ResultSet resultadoConsulta = consulta.executeQuery();

            if (resultadoConsulta.next()) {
                resultado = new Usuario();

                String nombreUsuario = resultadoConsulta.getString("nombre_usuario");
                String correo = resultadoConsulta.getString("correo");
                String nombreCompleto = resultadoConsulta.getString("nombre_completo");
                String cedula = resultadoConsulta.getString("cedula");
                Integer id_seccion = resultadoConsulta.getInt("id_seccion");
                Integer id_puesto = resultadoConsulta.getInt("id_puesto");
                Date fechaActivacion = resultadoConsulta.getDate("fecha_activacion");
                Date fechaDesactivacion;
                fechaDesactivacion = resultadoConsulta.getDate("fecha_desactivacion");
                boolean activo = resultadoConsulta.getBoolean("estado");

                PreparedStatement consultaSeccion = conexion.prepareStatement(" Select nombre_seccion "
                                                                              + " From seguridad.secciones"
                                                                              + " Where id_seccion = ? ");
                consultaSeccion.setInt(1, id_seccion);
                ResultSet resultadoConsultaSeccion = consultaSeccion.executeQuery();
                resultadoConsultaSeccion.next();
                String seccion = resultadoConsultaSeccion.getString("nombre_seccion");

                PreparedStatement consultaPuesto = conexion.prepareStatement(" Select nombre_puesto "
                                                                             + " From seguridad.puestos"
                                                                             + " Where id_puesto = ? ");
                consultaPuesto.setInt(1, id_puesto);
                ResultSet resultadoconsultaPuesto = consultaPuesto.executeQuery();
                resultadoconsultaPuesto.next();
                String puesto = resultadoconsultaPuesto.getString("nombre_puesto");

                resultado.setIdUsuario(idUsuario);
                resultado.setNombreUsuario(nombreUsuario);
                resultado.setCorreo(correo);
                resultado.setNombreCompleto(nombreCompleto);
                resultado.setCedula(cedula);
                resultado.setIdSeccion(id_seccion);
                resultado.setIdPuesto(id_puesto);
                resultado.setFechaActivacion(fechaActivacion);
                resultado.setFechaDesactivacion(fechaDesactivacion);
                resultado.setActivo(activo);
                resultado.setNombreSeccion(seccion);
                resultado.setNombrePuesto(puesto);

                resultadoconsultaPuesto.close();
                resultadoConsultaSeccion.close();
                consultaPuesto.close();
                consultaSeccion.close();
            }

            resultadoConsulta.close();
            consulta.close();
            conexion.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return resultado;
    }

    public boolean validarActividad(int p_id)
    {
        boolean resultado = false;
        try {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();

            PreparedStatement consulta = conexion.prepareStatement(" Select estado "
                                                                   + " From seguridad.usuarios"
                                                                   + " Where id_usuario = ? ");

            consulta.setInt(1, p_id);

            ResultSet resultadoConsulta = consulta.executeQuery();

            if (resultadoConsulta.next()) {

                resultado = resultadoConsulta.getBoolean("estado");
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return resultado;
    }

    public Usuario obtenerUsuario(String nombre_usuario)
    {
        Usuario resultado = null;
        try {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();

            PreparedStatement consulta = conexion.prepareStatement(" Select id_usuario,id_seccion,nombre_completo "
                                                                   + " From seguridad.usuarios"
                                                                   + " Where nombre_usuario = ? ");

            consulta.setString(1, nombre_usuario);

            ResultSet resultadoConsulta = consulta.executeQuery();

            if (resultadoConsulta.next()) {
                resultado = new Usuario();
                int id_usuario = resultadoConsulta.getInt("id_usuario");
                int id_seccion = resultadoConsulta.getInt("id_seccion");

                resultado.setId_usuario(id_usuario);
                resultado.setNombre_usuario(nombre_usuario);
                resultado.setId_seccion(id_seccion);
                resultado.setNombre_completo(resultadoConsulta.getString("nombre_completo"));
            }
            
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return resultado;
    }

    public int obtenerIDUsuario(String nombre)
    {
        int resultado = 0;
        try {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();

            PreparedStatement consulta = conexion.prepareStatement(" Select id_usuario "
                                                                   + " From seguridad.usuarios"
                                                                   + " Where nombre_usuario = ? ");

            consulta.setString(1, nombre);

            ResultSet resultadoConsulta = consulta.executeQuery();

            if (resultadoConsulta.next()) {

                resultado = resultadoConsulta.getInt("id_usuario");
            }
            resultadoConsulta.close();
            consulta.close();
            conexion.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return resultado;
    }

    public boolean validarCorreo(String correo, int id_usuario)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        boolean resultado = false;

        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT correo FROM seguridad.usuarios WHERE correo = ? and id_usuario <> ?");
                consulta.setString(1, correo);
                consulta.setInt(2, id_usuario);

                ResultSet resultadoConsulta = consulta.executeQuery();
                if (!resultadoConsulta.next()) {
                    resultado = true;
                }
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        return resultado;
    }

    public boolean validarNombreUsuario(String nombre_usuario, int id_usuario)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        boolean resultado = false;

        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT nombre_usuario FROM seguridad.usuarios WHERE nombre_usuario = ? and id_usuario <> ?");
                consulta.setString(1, nombre_usuario);
                consulta.setInt(2, id_usuario);

                ResultSet resultadoConsulta = consulta.executeQuery();
                if (!resultadoConsulta.next()) {
                    resultado = true;
                }

                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }

    public boolean insertarUsuario(Usuario usuario)
    {
        boolean resultado = false;

        try {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();

            if (conexion != null) {
                PreparedStatement consulta = conexion.prepareStatement("INSERT INTO SEGURIDAD.usuarios "
                                                                       + " (nombre_usuario, contrasena,  nombre_completo, correo, cedula, id_seccion, id_puesto, fecha_activacion, fecha_desactivacion, estado, contrasena_caducada) "
                                                                       + " VALUES "
                                                                       + " (?,?,?,?,?,?,?,?,?,?,true) RETURNING id_usuario");
                if(usuario.getContrasenna() == null) {
                    usuario.setContrasenna(generarContrasena());
                }
                
                consulta.setString(1, usuario.getNombre_usuario());
                consulta.setString(2, md5(usuario.getContrasenna()));
                consulta.setString(3, usuario.getNombre_completo());
                consulta.setString(4, usuario.getCorreo());
                consulta.setString(5, usuario.getCedula());
                consulta.setInt(6, usuario.getId_seccion());
                consulta.setInt(7, usuario.getId_puesto());

                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date fActivacion = formatoFecha.parse(usuario.getFechaActivacion());
                java.util.Date fDesactivacion = formatoFecha.parse(usuario.getFechaDesactivacion());
                java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
                java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());

                consulta.setDate(8, fActivacionSQL);
                consulta.setDate(9, fDesactivacionSQL);

                boolean permanente = usuario.getFechaActivacion().equals(usuario.getFechaDesactivacion());
                consulta.setBoolean(10, compararFechas(fActivacionSQL, fDesactivacionSQL, formatoFecha));

                ResultSet resultadoConsulta = consulta.executeQuery();
                if (resultadoConsulta.next()) {
                    resultado = true;
                    usuario.setId_usuario(resultadoConsulta.getInt("id_usuario"));
                }
                UtilidadEmail u = UtilidadEmail.getSingletonUtilidadEmail();
                u.enviarUsuarioCreado(usuario.getCorreo(), usuario.getNombre_usuario(), usuario.getContrasenna(), usuario.getFechaActivacion(), usuario.getFechaDesactivacion(), permanente);

                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();

        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }

        return resultado;
    }

    public boolean editarUsuario(int idUsuario, String nombreCompleto, String correoElectronico,
                                 String cedula, Integer id_seccion, Integer id_puesto, String fechaActivacion,
                                 String fechaDesactivacion, List<RolUsuario> p_roles, String estado, String contrasenna)
    {
        boolean resultado = false;

        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();

        try {
            if (conexion != null) {
                conexion.setAutoCommit(false);

                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date fActivacion = formatoFecha.parse(fechaActivacion);
                java.util.Date fDesactivacion = formatoFecha.parse(fechaDesactivacion);
                java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
                java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());

                java.util.Calendar calendario = java.util.Calendar.getInstance();
                java.util.Date hoy = calendario.getTime();
                java.util.Date hoyFormateado = formatoFecha.parse(formatoFecha.format(hoy));
                Date hoySQL = new Date(hoyFormateado.getTime());

                PreparedStatement consulta;
                
                String updateContrasenna = "";
                boolean updateContrasennaBool = contrasenna.length() > 0;
                if (updateContrasennaBool) {
                    updateContrasenna = ", contrasena = ? ";
                }
                
                if (fActivacion.before(hoySQL)) {
                    consulta = conexion.prepareStatement("UPDATE SEGURIDAD.usuarios "
                                                         + " SET correo = ?, nombre_completo = ?, cedula = ?, id_seccion = ?, id_puesto = ?, fecha_activacion = ?, fecha_desactivacion= ? "
                                                         + updateContrasenna
                                                         + " WHERE id_usuario = ? ");
                    if (updateContrasennaBool) {
                        consulta.setString(8, md5(contrasenna));
                        consulta.setInt(9, idUsuario);
                    } else {
                        consulta.setInt(8, idUsuario);
                    }
                    
                }

                else {
                    consulta = conexion.prepareStatement("UPDATE SEGURIDAD.usuarios "
                                                         + " SET correo = ?, nombre_completo = ?, cedula = ?, id_seccion = ?, id_puesto = ?, fecha_activacion = ?, fecha_desactivacion= ?, estado = ?, contrasenna = ?"
                                                         + updateContrasenna
                                                         + " WHERE id_usuario = ? ");
                    boolean fechas;
                    if (estado.toLowerCase().equals("false")) {
                        fechas = false;
                    }
                    else {
                        fechas = compararFechas(fActivacionSQL, fDesactivacionSQL, formatoFecha);
                    }
                    
                    if (updateContrasennaBool) {
                        consulta.setBoolean(8, fechas);
                        consulta.setString(9, md5(contrasenna));
                        consulta.setInt(10, idUsuario);
                    } else {
                        consulta.setBoolean(8, fechas);
                        consulta.setInt(9, idUsuario);
                    }
                }

                consulta.setString(1, correoElectronico);
                consulta.setString(2, nombreCompleto);
                consulta.setString(3, cedula);
                consulta.setInt(4, id_seccion);
                consulta.setInt(5, id_puesto);

                consulta.setDate(6, fActivacionSQL);
                consulta.setDate(7, fDesactivacionSQL);

                List<PreparedStatement> operaciones = new ArrayList<PreparedStatement>();
                PreparedStatement eliminarRolesUsuario = conexion.prepareStatement("Delete from seguridad.roles_usuarios where id_usuario = ?");
                eliminarRolesUsuario.setInt(1, idUsuario);

                operaciones.add(consulta);
                operaciones.add(eliminarRolesUsuario);

                String insert = " INSERT INTO seguridad.roles_usuarios (id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (?,?,?,?)";

                for (RolUsuario i : p_roles) {
                    PreparedStatement upsertTemp = conexion.prepareStatement(insert);
                    upsertTemp.setInt(1, i.getIDUsuario());
                    upsertTemp.setInt(2, i.getIDRol());
                    upsertTemp.setDate(3, i.getFechaActivacionSQL());
                    upsertTemp.setDate(4, i.getFechaDesactivacionSQL());
                    operaciones.add(upsertTemp);
                }

                for (PreparedStatement operacion : operaciones) {
                    int resultadoOperacion = operacion.executeUpdate();
                    operacion.close();
                }
                conexion.commit();
                consulta.close();
                conexion.close();
                resultado = true;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            try {
                resultado = false;
                conexion.rollback();
            }
            catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }
        catch (ParseException ex) {
            resultado = false;
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean desactivarUsuario(int idUsuario)
    {
        boolean resultado = false;

        try {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();

            PreparedStatement consulta = conexion.prepareStatement("UPDATE SEGURIDAD.usuarios "
                                                                   + " SET estado = false "
                                                                   + " WHERE id_usuario = ? ");

            consulta.setInt(1, idUsuario);

            int resultadoConsulta = consulta.executeUpdate();
            if (resultadoConsulta == 1) {
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean activarUsuario(int idUsuario)
    {
        boolean resultado = false;

        try {
            SingletonBD s = SingletonBD.getSingletonBD();
            Connection conexion = s.conectar();

            PreparedStatement consulta = conexion.prepareStatement("UPDATE SEGURIDAD.usuarios "
                                                                   + " SET estado = true "
                                                                   + " WHERE id_usuario = ? ");

            consulta.setInt(1, idUsuario);

            int resultadoConsulta = consulta.executeUpdate();
            if (resultadoConsulta == 1) {
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    


    public List<Usuario> obtenerUsuarios()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();

        List<Usuario> resultado = null;

        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT us.id_usuario, us.nombre_usuario, us.correo, us.nombre_completo, us.cedula, "
                                                     + "us.id_seccion, us.id_puesto, us.fecha_activacion, us.fecha_desactivacion, us.estado "
                                                     + "FROM seguridad.usuarios us");
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarUsuarios(resultadoConsulta);
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                resultado = null;
            }
        }
        return resultado;
    }
    
    public List<Usuario> obtenerUsuariosAlfa()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();

        List<Usuario> resultado = null;

        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT us.id_usuario, us.nombre_usuario, us.correo, us.nombre_completo, us.cedula, "
                                                     + "us.id_seccion, us.id_puesto, us.fecha_activacion, us.fecha_desactivacion, us.estado "
                                                     + "FROM seguridad.usuarios us "
                                                     + "ORDER BY us.nombre_completo ");
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarUsuarios(resultadoConsulta);
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                resultado = null;
            }
        }
        return resultado;
    }
    

    
        
        public List<Usuario> obtenerUsuarios(Usuario u)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();

        List<Usuario> resultado = null;

        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT us.id_usuario, us.nombre_usuario, us.correo, us.nombre_completo, us.cedula, "
                                                    + "us.id_seccion, us.id_puesto, us.fecha_activacion, us.fecha_desactivacion, us.estado "
                                                    + "FROM seguridad.usuarios us "
                                                    + "WHERE us.id_seccion = ?; ");
                consulta.setInt(1, u.getId_seccion());
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarUsuarios(resultadoConsulta);
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                resultado = null;
            }
        }
        return resultado;
    }

    private boolean compararFechas(Date fechaActivacion, Date fechaDesactivacion, SimpleDateFormat formato) throws ParseException
    {

        java.util.Calendar calendario = java.util.Calendar.getInstance();
        java.util.Date hoy = calendario.getTime();
        java.util.Date hoyFormateado = formato.parse(formato.format(hoy));
        Date hoySQL = new Date(hoyFormateado.getTime());
        if (fechaActivacion.after(hoySQL)) {
            return false;
        }
        else {
            return fechaActivacion.equals(hoySQL);
        }

    }

    private String md5(String texto)
    {
        String resultado = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(texto.getBytes());
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            resultado = bigInt.toString(16);

            while (resultado.length() < 32) {
                resultado = "0" + resultado;
            }
        }
        catch (NoSuchAlgorithmException ex) {
            //Imprimir error
        }

        return resultado;
    }

    public List<RolUsuario> obtenerRolesUsuario(String p_IdUsuario)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        List<RolUsuario> resultado = null;

        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT r.nombre, ru.id_rol, ru.id_usuario, ru.fecha_activacion, ru.fecha_desactivacion "
                                                     + "FROM seguridad.roles r inner join seguridad.roles_usuarios ru  on r.id_rol = ru.id_rol AND ru.id_usuario = ? ");
                consulta.setInt(1, Integer.parseInt(p_IdUsuario));
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarRolesUsuario(resultadoConsulta);
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                resultado = null;
            }
        }
        return resultado;
    }

    public List<Rol> obtenerRolesRestantes(String p_IdUsuario)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        List<Rol> resultado = null;

        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT r.id_rol, r.nombre, r.descripcion "
                                                     + "FROM seguridad.roles r "
                                                     + "WHERE r.id_rol NOT IN (SELECT ru.id_rol FROM seguridad.roles_usuarios ru WHERE ru.id_usuario = ?)");
                consulta.setInt(1, Integer.parseInt(p_IdUsuario));
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarRoles(resultadoConsulta);
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                resultado = null;
            }
        }
        return resultado;
    }

    @SuppressWarnings("Convert2Diamond")
    private List<RolUsuario> llenarRolesUsuario(ResultSet resultadoConsulta) throws SQLException
    {
        List<RolUsuario> resultado = new ArrayList<RolUsuario>();

        while (resultadoConsulta.next()) {
            int idUsuario = resultadoConsulta.getInt("id_usuario");
            String nombreRol = resultadoConsulta.getString("nombre");
            int idRol = resultadoConsulta.getInt("id_rol");
            Date fechaActivacion = resultadoConsulta.getDate("fecha_activacion");
            Date fechaDesactivacion = resultadoConsulta.getDate("fecha_desactivacion");

            resultado.add(new RolUsuario(idRol, idUsuario, fechaActivacion, fechaDesactivacion, nombreRol));
        }
        return resultado;
    }

    @SuppressWarnings("Convert2Diamond")
    private List<Rol> llenarRoles(ResultSet resultadoConsulta) throws SQLException
    {
        List<Rol> resultado = new ArrayList<Rol>();

        while (resultadoConsulta.next()) {
            String nombreRol = resultadoConsulta.getString("nombre");
            int idRol = resultadoConsulta.getInt("id_rol");
            String descripcionrol = resultadoConsulta.getString("descripcion");

            resultado.add(new Rol(idRol, nombreRol, descripcionrol));
        }
        return resultado;
    }

    public List<Usuario> obtenerUsuariosRestantes(String p_IdRol)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        List<Usuario> resultado = null;

        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT us.id_usuario, us.nombre_usuario, us.correo, us.nombre_completo, us.cedula, "
                                                     + "us.id_seccion, us.id_puesto, us.fecha_activacion, us.fecha_desactivacion, us.estado "
                                                     + "FROM seguridad.usuarios us "
                                                     + "WHERE us.id_usuario NOT IN (SELECT ru.id_usuario FROM seguridad.roles_usuarios ru WHERE ru.id_rol = ?)");
                consulta.setInt(1, Integer.parseInt(p_IdRol));
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarUsuarios(resultadoConsulta);
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                resultado = null;
            }
        }
        return resultado;
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public List<Integer> obtenerPermisos(int p_id_usuario)
    {
        List<Integer> resultado = null;

        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement(" Select id_permiso "
                                                     + " From seguridad.permisos_roles "
                                                     + " Where id_rol in "
                                                     + " ("
                                                     + "   Select id_rol "
                                                     + "   From seguridad.roles_usuarios "
                                                     + "   Where id_usuario = ? "
                                                     + "   And ( "
                                                     + "     (fecha_activacion = fecha_desactivacion and fecha_activacion <= current_date)"
                                                     + "     or "
                                                     + "     (fecha_activacion <= current_date and fecha_desactivacion >= current_date) "
                                                     + "       ) "
                                                     + ")");
                consulta.setInt(1, p_id_usuario);
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarPermisos(resultadoConsulta);
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                resultado = null;
            }
        }
        return resultado;
    }

    public int recuperarContrasena(String correoElectronico)
    {
        int resultado = -1;

        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();

        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("Update seguridad.usuarios set contrasena=?,contrasena_caducada = true where correo = ? RETURNING nombre_usuario");
                String contrasena = generarContrasena();
                consulta.setString(1, md5(contrasena));
                consulta.setString(2, correoElectronico);
                ResultSet resultadoConsulta = consulta.executeQuery();
                if (resultadoConsulta.next()) {
                    String nombre_usuario = resultadoConsulta.getString("nombre_usuario");
                    UtilidadEmail u = UtilidadEmail.getSingletonUtilidadEmail();
                    u.enviarRecuperacionContrasena(correoElectronico, nombre_usuario, contrasena);
                    resultado = 1;
                }
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                resultado = -1;
            }
        }
        return resultado;
    }

    public String generarContrasena()
    {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 6);
    }

    private List<Integer> llenarPermisos(ResultSet resultadoConsulta) throws SQLException
    {
        @SuppressWarnings("Convert2Diamond")
        List<Integer> resultado = new ArrayList<Integer>();
        while (resultadoConsulta.next()) {
            resultado.add(resultadoConsulta.getInt("id_permiso"));
        }
        return resultado;
    }

    public boolean cambiarContrasena(String usuario, String contrasena)
    {
        boolean resultado = false;

        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();

        try {
            PreparedStatement consulta = conexion.prepareStatement("Update seguridad.usuarios set contrasena = ?, contrasena_caducada=false where nombre_usuario = ?");

            consulta.setString(1, md5(contrasena));
            consulta.setString(2, usuario);

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }

            consulta.close();
            conexion.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean AutorizarRecibo(String usuario, String contrasenna, int id_seccion)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        boolean resultado = false;

        if (conexion != null) {
            try {
                PreparedStatement consultaContrasena = conexion.prepareStatement("SELECT id_usuario, contrasena "
                                                                                 + "FROM seguridad.usuarios us "
                                                                                 + "WHERE us.nombre_usuario = ? and us.contrasena = ? and us.id_seccion = ?");

                consultaContrasena.setString(1, usuario);
                String hash = md5(contrasenna);
                consultaContrasena.setString(2, hash);
                consultaContrasena.setInt(3, id_seccion);
                ResultSet resultadoConsulta = consultaContrasena.executeQuery();
                if (resultadoConsulta.next()) {
                    resultado = true;
                }
                resultadoConsulta.close();
                consultaContrasena.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }

    public boolean AutorizarRecibo(String usuario, String contrasenna)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        boolean resultado = false;

        if (conexion != null) {
            try {
                PreparedStatement consultaContrasena = conexion.prepareStatement("SELECT id_usuario, contrasena "
                                                                                 + "FROM seguridad.usuarios us "
                                                                                 + "WHERE us.nombre_usuario = ? and us.contrasena = ?");

                consultaContrasena.setString(1, usuario);
                String hash = md5(contrasenna);
                consultaContrasena.setString(2, hash);
                ResultSet resultadoConsulta = consultaContrasena.executeQuery();
                if (resultadoConsulta.next()) {
                    resultado = true;
                }
                resultadoConsulta.close();
                consultaContrasena.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }

    public List<Usuario> obtenerUsuariosSeccion(int... ids_secciones) throws SIGIPROException
    {
        List<Integer> secciones = new ArrayList<Integer>();
        for (int i : ids_secciones) {
            secciones.add(i);
        }
        String secciones_final = pasar_id_secciones(secciones);
        
        List<Usuario> resultado = new ArrayList<Usuario>();

        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        PreparedStatement consulta = null;
        ResultSet rs = null;

        if (conexion != null) {
            try {
                consulta = conexion.prepareStatement(" SELECT id_usuario, nombre_completo FROM seguridad.usuarios WHERE id_seccion in " + secciones_final + ";");

                rs = consulta.executeQuery();

                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId_usuario(rs.getInt("id_usuario"));
                    u.setNombreCompleto(rs.getString("nombre_completo"));
                    resultado.add(u);
                }
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                throw new SIGIPROException("Error al obtener usuarios. Inténtelo nuevamente.");
            }
            finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (consulta != null) {
                        consulta.close();
                    }
                    conexion.close();
                }
                catch (SQLException sql_ex) {
                    sql_ex.printStackTrace();
                    throw new SIGIPROException("Rrror al comunicarse con la base de datos. Comunique al administrador del sistema.");
                }
            }
        }
        return resultado;
    }

    public boolean autorizarRecibo(String usuario, String contrasenna)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        boolean resultado = false;

        if (conexion != null) {
            try {
                PreparedStatement consultaContrasena = conexion.prepareStatement("SELECT id_usuario, contrasena "
                                                                                 + "FROM seguridad.usuarios us "
                                                                                 + "WHERE us.nombre_usuario = ? and us.contrasena = ?");

                consultaContrasena.setString(1, usuario);
                String hash = md5(contrasenna);
                consultaContrasena.setString(2, hash);
                ResultSet resultadoConsulta = consultaContrasena.executeQuery();
                if (resultadoConsulta.next()) {
                    resultado = true;
                }
                resultadoConsulta.close();
                consultaContrasena.close();
                conexion.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }
    
    private String pasar_id_secciones(List<Integer> ids_secciones)
    {
        String secciones = "(";
        for (int s : ids_secciones) {
            secciones = secciones + s;
            secciones = secciones + ",";
        }
        secciones = secciones.substring(0, secciones.length() - 1);
        secciones = secciones + ")";
        return secciones;
    }

}
