/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.basededatos;

import com.icp.sigipro.seguridad.modelos.BarraFuncionalidad;
import com.icp.sigipro.seguridad.modelos.Permiso;
import com.icp.sigipro.seguridad.modelos.PermisoRol;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.seguridad.modelos.RolUsuario;
import com.icp.sigipro.seguridad.modelos.Rol;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class SingletonBD 
{    
    private static SingletonBD theSingleton = null;
    
    protected SingletonBD(){  }
    
    public static SingletonBD getSingletonBD()
    {
        if (theSingleton == null)
        {
            theSingleton = new SingletonBD();
        }
        return theSingleton;
    }
    
    private Connection conectar()
    {
        Connection conexion = null;
        
        try
        {
            Class.forName("org.postgresql.Driver");
            conexion = 
            DriverManager.getConnection(
                "jdbc:postgresql://localhost/sigipro","postgres","Solaris2014"
            );
        }
        catch(ClassNotFoundException ex)
        {
            System.out.println("Clase no encontrada");
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return conexion;
        
    }
    
    public int validarInicioSesion(String usuario, String contrasenna)
    {
        Connection conexion = conectar();
        int resultado = -1;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta = conexion.prepareStatement("SELECT idusuario "
                                                                     + "FROM seguridad.usuarios us "
                                                                     + "WHERE us.nombreusuario = ? and us.contrasena = ? "
                                                                     + "AND us.fechaactivacion <= current_date "
                                                                     + "AND (us.fechadesactivacion > current_date or us.fechaactivacion = us.fechadesactivacion) "
                                                                     + "AND us.estado = true ");
                consulta.setString(1, usuario);
                String hash = md5(contrasenna);
                consulta.setString(2, hash);
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultadoConsulta.next();
                resultado = resultadoConsulta.getInt("idusuario"); //Se verifica si hay resultado de la consulta.
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
            catch(SQLException ex)
            {
                ex.printStackTrace(System.out);
            }
        }
        return resultado;
    }
    
    public List<BarraFuncionalidad> obtenerModulos(int usuario)
    {
        Connection conexion = conectar();
        List<BarraFuncionalidad> resultado = null;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("Select idmenu_principal, idpadre, tag, redirect " +
                                                    "From seguridad.entrada_menu_principal " +
                                                    "Where permiso in " +
                                                    "( " +
                                                    "	Select idpermiso " +
                                                    "	From seguridad.permisosrol " +
                                                    "	Where idrol in " +
                                                    "		( " +
                                                    "			Select idrol " +
                                                    "			From seguridad.rolesusuario " +
                                                    "			Where idusuario = ? " +
                                                    "			And ( " +
                                                    "				fechaactivacion = fechadesactivacion " +
                                                    "			      or " +
                                                    "				(fechaactivacion < current_date and fechadesactivacion > current_date) " +
                                                    "			    ) " +
                                                    "		) " +
                                                    ") " +
                                                    "order  by idpadre, redirect desc ");
                consulta.setInt(1, usuario);
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarBarraFuncionalidad(resultadoConsulta);
                resultadoConsulta.close();
                conexion.close();
            }
            catch(SQLException ex)
            {
                resultado = null;
            }
        }
        return resultado;
    }
    
    @SuppressWarnings("Convert2Diamond")
    private List<BarraFuncionalidad> llenarBarraFuncionalidad(ResultSet resultadoConsulta) throws SQLException
    {
        List<BarraFuncionalidad> resultado = new ArrayList<BarraFuncionalidad>();
        BarraFuncionalidad temp = new BarraFuncionalidad();
        String modulo = null;
        
        while(resultadoConsulta.next())
        {
            if (resultadoConsulta.getInt("idmenu_principal") == resultadoConsulta.getInt("idpadre"))
            {
                modulo = resultadoConsulta.getString("tag");
                temp = new BarraFuncionalidad(resultadoConsulta.getString("tag"));
                resultado.add(temp);
            }
            else
            {
                String[] funcionalidad = new String[2];
                funcionalidad[0] = resultadoConsulta.getString("tag");
                funcionalidad[1] = resultadoConsulta.getString("redirect");
                temp.agregarFuncionalidad(funcionalidad);
            }
        }
        return resultado;
    }
    
    
    public Usuario obtenerUsuario(int idUsuario)
    {
        Usuario resultado = null;
        try{
            Connection conexion = conectar();

            PreparedStatement consulta = conexion.prepareStatement(" Select nombreusuario, correo, nombrecompleto, cedula, departamento, " + 
                                                                   " puesto, fechaactivacion, fechadesactivacion, estado " +
                                                                   " From seguridad.usuarios" + 
                                                                   " Where idusuario = ? ");

            consulta.setInt(1, idUsuario);
             
            ResultSet resultadoConsulta = consulta.executeQuery();
            
            if(resultadoConsulta.next())
            {
                resultado = new Usuario();
                
                String nombreUsuario = resultadoConsulta.getString("nombreusuario");
                String correo = resultadoConsulta.getString("correo");
                String nombreCompleto = resultadoConsulta.getString("nombrecompleto");
                String cedula = resultadoConsulta.getString("cedula");
                String departamento = resultadoConsulta.getString("departamento");
                String puesto = resultadoConsulta.getString("puesto");
                Date fechaActivacion = resultadoConsulta.getDate("fechaactivacion");
                Date fechaDesactivacion;
                fechaDesactivacion = resultadoConsulta.getDate("fechadesactivacion");
                boolean activo = resultadoConsulta.getBoolean("estado");

                resultado.setIdUsuario(idUsuario);
                resultado.setNombreUsuario(nombreUsuario);
                resultado.setCorreo(correo);
                resultado.setNombreCompleto(nombreCompleto);
                resultado.setCedula(cedula);
                resultado.setDepartamento(departamento);
                resultado.setPuesto(puesto);
                resultado.setFechaActivacion(fechaActivacion);
                resultado.setFechaDesactivacion(fechaDesactivacion);
                resultado.setActivo(activo);
            }
        }
        catch(SQLException ex)
        {
            
        }
        
        
        return resultado;
    }
    
    public boolean insertarUsuario(String nombreUsuario, String nombreCompleto, String correoElectronico,
                                    String cedula, String departamento, String puesto, String fechaActivacion, String fechaDesactivacion)
    {
        boolean resultado = false;
        
        try
        {
            Connection conexion = conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("INSERT INTO SEGURIDAD.usuarios "
                        + " (nombreusuario, contrasena,  nombrecompleto, correo, cedula, departamento, puesto, fechaactivacion, fechadesactivacion, estado) "
                        + " VALUES "
                        + " (?,?,?,?,?,?,?,?,?,? )");
                consulta.setString(1, nombreUsuario);
                consulta.setString(2, md5("sigipro"));
                consulta.setString(3, nombreCompleto);
                consulta.setString(4, correoElectronico);
                consulta.setString(5, cedula);
                consulta.setString(6, departamento);
                consulta.setString(7, puesto);
                
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date fActivacion = formatoFecha.parse(fechaActivacion);
                java.util.Date fDesactivacion = formatoFecha.parse(fechaDesactivacion);
                java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
                java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());
                
                consulta.setDate(8, fActivacionSQL);
                consulta.setDate(9, fDesactivacionSQL);
                
                consulta.setBoolean(10, compararFechas(fActivacionSQL, fDesactivacionSQL));
                
                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1)
                {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        }
        catch(SQLException ex)
        {
            String hola = "hola";
            hola.toCharArray();
        }
        catch(ParseException ex)
        {
            String hola = "hola";
            hola.toCharArray();
        }
        
        return resultado;
    }
    
    public boolean editarUsuario(int idUsuario, String nombreCompleto, String correoElectronico,
                                    String cedula, String departamento, String puesto, String fechaActivacion, String fechaDesactivacion)
    {
        boolean resultado = false;
        
        try
        {
            Connection conexion = conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("UPDATE SEGURIDAD.usuarios "
                        + " SET correo = ?, nombrecompleto = ?, cedula = ?, departamento = ?, puesto = ?, fechaactivacion = ?, fechadesactivacion= ?, estado = ?"
                        + " WHERE idusuario = ? ");
                
                consulta.setString(1, correoElectronico);
                consulta.setString(2, nombreCompleto);
                consulta.setString(3, cedula);
                consulta.setString(4, departamento);
                consulta.setString(5, puesto);
                
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date fActivacion = formatoFecha.parse(fechaActivacion);
                java.util.Date fDesactivacion = formatoFecha.parse(fechaDesactivacion);
                java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
                java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());
                
                consulta.setDate(6, fActivacionSQL);
                consulta.setDate(7, fDesactivacionSQL);
                
                consulta.setBoolean(8, compararFechas(fActivacionSQL, fDesactivacionSQL));
                
                consulta.setInt(9, idUsuario);
                
                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1)
                {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        }
        catch(SQLException ex)
        {
            String hola = "hola";
            hola.toCharArray();
        }
        catch(ParseException ex)
        {
            String hola = "hola";
            hola.toCharArray();
        }
        
        return resultado;
    }
    
    public boolean desactivarUsuario(int idUsuario)
    {
        boolean resultado = false;
        
        try
        {
            Connection conexion = conectar();
            
            PreparedStatement consulta = conexion.prepareStatement("UPDATE SEGURIDAD.usuarios "
                        + " SET estado = false "
                        + " WHERE idusuario = ? ");
            
            consulta.setInt(1, idUsuario);
            
            int resultadoConsulta = consulta.executeUpdate();
            if (resultadoConsulta == 1)
            {
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }
        catch(SQLException ex)
        {
            String hola = "hola";
            hola.toCharArray();
        }
        return resultado;
    }
    
    public List<Usuario> obtenerUsuarios()
    {
        Connection conexion = conectar();
        List<Usuario> resultado = null;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT us.idusuario, us.nombreusuario, us.correo, us.nombrecompleto, us.cedula, "
                                                          + "us.departamento, us.puesto, us.fechaactivacion, us.fechadesactivacion, us.estado "
                                                   + "FROM seguridad.usuarios us");
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarUsuarios(resultadoConsulta);
                resultadoConsulta.close();
                conexion.close();
            }
            catch(SQLException ex)
            {
                resultado = null;
            }
        }
        return resultado;
    }
    
    private boolean compararFechas(Date fechaActivacion, Date fechaDesactivacion)
    {
        java.util.Calendar calendario = java.util.Calendar.getInstance();
        java.util.Date hoy = calendario.getTime();
        Date hoySQL = new Date(hoy.getTime());
        boolean resultado = 
                ((fechaActivacion.before(hoySQL) 
                && fechaDesactivacion.after(hoySQL) )
                || fechaActivacion.equals(fechaDesactivacion));
        return resultado;
    }
    
    @SuppressWarnings("Convert2Diamond")
    private List<Usuario> llenarUsuarios(ResultSet resultadoConsulta) throws SQLException
    {
        List<Usuario> resultado = new ArrayList<Usuario>();
        
        while(resultadoConsulta.next())
        {
            int idUsuario = resultadoConsulta.getInt("idusuario");
            String nombreUsuario = resultadoConsulta.getString("nombreusuario");
            String correo = resultadoConsulta.getString("correo");
            String nombreCompleto = resultadoConsulta.getString("nombrecompleto");
            String cedula = resultadoConsulta.getString("cedula");
            String departamento = resultadoConsulta.getString("departamento");
            String puesto = resultadoConsulta.getString("puesto");
            Date fechaActivacion = resultadoConsulta.getDate("fechaactivacion");
            Date fechaDesactivacion = resultadoConsulta.getDate("fechadesactivacion");
            boolean activo = resultadoConsulta.getBoolean("estado");
            
            resultado.add(new Usuario(idUsuario, nombreUsuario, correo, nombreCompleto, 
                                        cedula, departamento, puesto, fechaActivacion, fechaDesactivacion, activo));
        }
        return resultado;
    }
    
    private String md5(String texto)
    {
        String resultado = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(texto.getBytes());
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            resultado = bigInt.toString(16);
            
            while(resultado.length() < 32 ){
              resultado = "0"+resultado;
            }
        }
        catch(NoSuchAlgorithmException ex)
        {
            //Imprimir error
        }
        
        return resultado;
    }

public List<RolUsuario> obtenerRolesUsuario(String p_IdUsuario)
    {
        Connection conexion = conectar();
        List<RolUsuario> resultado = null;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT r.nombrerol, ru.idrol, ru.idusuario, ru.fechaactivacion, ru.fechadesactivacion "
                                                     + "FROM seguridad.roles r, seguridad.rolesusuario ru  Where r.idrol = ru.idrol AND ru.idusuario = ? ");
                consulta.setInt(1, Integer.parseInt(p_IdUsuario));
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarRolesUsuario(resultadoConsulta);
                resultadoConsulta.close();
                conexion.close();
            }
            catch(SQLException ex)
            {
                resultado = null;
            }
        }
        return resultado;
    }

 @SuppressWarnings("Convert2Diamond")
    private List<RolUsuario> llenarRolesUsuario(ResultSet resultadoConsulta) throws SQLException
    {
        List<RolUsuario> resultado = new ArrayList<RolUsuario>();
        
        while(resultadoConsulta.next())
        {
            int idUsuario = resultadoConsulta.getInt("idusuario");
            String nombreRol = resultadoConsulta.getString("nombrerol");
            int idRol = resultadoConsulta.getInt("idrol");
            Date fechaActivacion = resultadoConsulta.getDate("fechaactivacion");
            Date fechaDesactivacion = resultadoConsulta.getDate("fechadesactivacion");
            
            resultado.add(new RolUsuario(idRol, idUsuario, fechaActivacion, fechaDesactivacion, nombreRol));
        }
        return resultado;
    }
    
 public List<Rol> obtenerRoles()
    {
        Connection conexion = conectar();
        List<Rol> resultado = null;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT r.idrol, r.nombrerol, r.descripcionrol "
                                                     + "FROM seguridad.roles r");
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarRoles(resultadoConsulta);
                resultadoConsulta.close();
                conexion.close();
            }
            catch(SQLException ex)
            {
                resultado = null;
            }
        }
        return resultado;
    }
 
public List<Rol> obtenerRolesRestantes(String p_IdUsuario)
    {
        Connection conexion = conectar();
        List<Rol> resultado = null;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement(  "SELECT r.idrol, r.nombrerol, r.descripcionrol "
                                                     + "FROM seguridad.roles r "
                                                     + "WHERE r.idrol NOT IN (SELECT ru.idrol FROM seguridad.rolesusuario ru WHERE ru.idusuario = ?)");
                consulta.setInt(1, Integer.parseInt(p_IdUsuario));
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarRoles(resultadoConsulta);
                resultadoConsulta.close();
                conexion.close();
            }
            catch(SQLException ex)
            {
                resultado = null;
            }
        }
        return resultado;
    }

 @SuppressWarnings("Convert2Diamond")
    private List<Rol> llenarRoles(ResultSet resultadoConsulta) throws SQLException
    {
        List<Rol> resultado = new ArrayList<Rol>();
        
        while(resultadoConsulta.next())
        {
            String nombreRol = resultadoConsulta.getString("nombrerol");
            int idRol = resultadoConsulta.getInt("idrol");
            String descripcionrol = resultadoConsulta.getString("descripcionrol");
            
            resultado.add(new Rol(idRol, nombreRol, descripcionrol));
        }
        return resultado;
    }
    
    public boolean insertarRolUsuario(String idusuario, String idrol, String fechaActivacion, String fechaDesactivacion)
    {
        boolean resultado = false;
        
        try
        {
            Connection conexion = conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("INSERT INTO SEGURIDAD.rolesusuario "
                        + " (idusuario, idrol, fechaactivacion, fechadesactivacion) "
                        + " VALUES "
                        + " (?,?,?,? )");
                consulta.setInt(1, Integer.parseInt(idusuario));
                consulta.setInt(2, Integer.parseInt(idrol));

                
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date fActivacion = formatoFecha.parse(fechaActivacion);
                java.util.Date fDesactivacion = formatoFecha.parse(fechaDesactivacion);
                java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
                java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());
                
                consulta.setDate(3, fActivacionSQL);
                consulta.setDate(4, fDesactivacionSQL);
                
                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1)
                {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        }
        catch(SQLException ex){System.out.println(ex); }
        catch(ParseException ex){System.out.println(ex); }

        
        return resultado;
    }
    public boolean EliminarRolUsuario(String p_idusuario, String p_idrol)
    {
        boolean resultado = false;
        
        try
        {
            Connection conexion = conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("DELETE FROM seguridad.rolesusuario s " +
                                                                        "WHERE  s.idrol = ? AND s.idusuario = ? "
                        );
                consulta.setInt(1, Integer.parseInt(p_idrol) );
                consulta.setInt(2, Integer.parseInt(p_idusuario));
                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1)
                {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        }
        catch(SQLException ex){System.out.println(ex); }

        
        return resultado;
    }
    public boolean EditarRolUsuario(String p_idusuario, String p_idrol, String fechaActivacion, String fechaDesactivacion)
    {
        boolean resultado = false;
        
        try
        {
            Connection conexion = conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("Update seguridad.rolesusuario Set fechaactivacion = ?, fechadesactivacion = ? " +
                                                                        "WHERE  idrol = ? AND idusuario = ? "
                        );
                
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date fActivacion = formatoFecha.parse(fechaActivacion);
                java.util.Date fDesactivacion = formatoFecha.parse(fechaDesactivacion);
                java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
                java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());
                
                consulta.setDate(1, fActivacionSQL);
                consulta.setDate(2, fDesactivacionSQL);
                consulta.setInt(3, Integer.parseInt(p_idrol) );
                consulta.setInt(4, Integer.parseInt(p_idusuario));
                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1)
                {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        }
        catch(SQLException ex){System.out.println(ex); }
        catch(ParseException ex){System.out.println(ex); }

        
        return resultado;
    }
    public boolean insertarRol(String nombre, String descripcion)
    {
        boolean resultado = false;
        
        try
        {
            Connection conexion = conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("INSERT INTO SEGURIDAD.roles "
                        + " ( nombrerol, descripcionrol) "
                        + " VALUES "
                        + " (?,? )");
                consulta.setString(1, nombre);
                consulta.setString(2, descripcion);
                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1)
                {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        }
        catch(SQLException ex){System.out.println(ex); }
        
        return resultado;
    }
    
    public boolean editarRol(int idrol, String nombre, String descripcion)
    {
        boolean resultado = false;
        
        try
        {
            Connection conexion = conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("UPDATE SEGURIDAD.roles "
                        + " SET nombrerol = ?, descripcionrol = ? "
                        + " WHERE idrol = ? ");
                
                consulta.setString(1, nombre);
                consulta.setString(2, descripcion);
                consulta.setInt(3, idrol);

                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1)
                {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        }
        catch(SQLException ex) {System.out.println(ex); }

        return resultado;
    }
    public boolean EliminarRol(String p_idrol)
    {
        boolean resultado = false;
        
        try
        {
            Connection conexion = conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("DELETE FROM SEGURIDAD.roles s " +
                                                                        "WHERE  s.idrol = ? "
                        );
                consulta.setInt(1, Integer.parseInt(p_idrol) );
                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1)
                {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        }
        catch(SQLException ex){System.out.println(ex); }

        
        return resultado;
    }
    //---------------------------------------------------------------------------------------------------------
    public List<PermisoRol> obtenerPermisosRol(String p_IdRol)
    {
        Connection conexion = conectar();
        List<PermisoRol> resultado = null;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT p.nombrepermiso, pr.idrol, pr.idpermiso "
                                                     + "FROM seguridad.permisosrol pr, seguridad.permisos p  Where  pr.idpermiso = p.idpermiso AND pr.idrol = ? ");
                consulta.setInt(1, Integer.parseInt(p_IdRol));
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarPermisosRol(resultadoConsulta);
                resultadoConsulta.close();
                conexion.close();
            }
            catch(SQLException ex)
            {
                resultado = null;
            }
        }
        return resultado;
    }

 @SuppressWarnings("Convert2Diamond")
    private List<PermisoRol> llenarPermisosRol(ResultSet resultadoConsulta) throws SQLException
    {
        List<PermisoRol> resultado = new ArrayList<PermisoRol>();
        
        while(resultadoConsulta.next())
        {
            int idPermiso= resultadoConsulta.getInt("idpermiso");
            String nombrePermiso = resultadoConsulta.getString("nombrepermiso");
            int idRol = resultadoConsulta.getInt("idrol");
            
            resultado.add(new PermisoRol(idRol, idPermiso, nombrePermiso));
        }
        return resultado;
    }
    
 
public List<Permiso> obtenerPermisosRestantes(String p_idrol)
    {
        Connection conexion = conectar();
        List<Permiso> resultado = null;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement(  "SELECT p.idpermiso, p.nombrepermiso, p.descripcionpermiso "
                                                     + "FROM seguridad.permisos p "
                                                     + "WHERE p.idpermiso NOT IN (SELECT ru.idpermiso FROM seguridad.permisosrol ru WHERE ru.idrol = ?)");
                consulta.setInt(1, Integer.parseInt(p_idrol));
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarPermisos(resultadoConsulta);
                resultadoConsulta.close();
                conexion.close();
            }
            catch(SQLException ex)
            {
                resultado = null;
            }
        }
        return resultado;
    }

 @SuppressWarnings("Convert2Diamond")
    private List<Permiso> llenarPermisos(ResultSet resultadoConsulta) throws SQLException
    {
        List<Permiso> resultado = new ArrayList<Permiso>();
        
        while(resultadoConsulta.next())
        {
            String nombreRol = resultadoConsulta.getString("nombrepermiso");
            int idRol = resultadoConsulta.getInt("idpermiso");
            String descripcionrol = resultadoConsulta.getString("descripcionpermiso");
            
            resultado.add(new Permiso(idRol, nombreRol, descripcionrol));
        }
        return resultado;
    }
    
    public boolean insertarPermisoRol(String idrol, String idpermiso)
    {
        boolean resultado = false;
        
        try
        {
            Connection conexion = conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("INSERT INTO SEGURIDAD.permisosrol "
                        + " (idrol, idpermiso) "
                        + " VALUES "
                        + " (?,? )");
                consulta.setInt(1, Integer.parseInt(idrol));
                consulta.setInt(2, Integer.parseInt(idpermiso));
                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1)
                {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        }
        catch(SQLException ex){System.out.println(ex); }

        
        return resultado;
    }
    public boolean EliminarPermisoRol(String p_idrol, String p_idpermiso)
    {
        boolean resultado = false;
        
        try
        {
            Connection conexion = conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("DELETE FROM seguridad.permisosrol s " +
                                                                        "WHERE  s.idrol = ? AND s.idpermiso = ? "
                        );
                consulta.setInt(1, Integer.parseInt(p_idrol) );
                consulta.setInt(2, Integer.parseInt(p_idpermiso));
                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1)
                {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        }
        catch(SQLException ex){System.out.println(ex); }

        
        return resultado;
    }
}
