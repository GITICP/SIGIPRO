package com.icp.sigipro.bitacora.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.postgresql.util.PGobject;

/**
 *
 * @author ld.conejo
 */
public class BitacoraDAO
{

    private Connection conexion;

    public BitacoraDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }

    public BitacoraDAO(Connection conexion)
    {
        this.conexion = conexion;
    }

    private Connection getConexion()
    {
        try {

            if (conexion.isClosed()) {
                SingletonBD s = SingletonBD.getSingletonBD();
                conexion = s.conectar();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            conexion = null;
        }

        return conexion;
    }

    public void setBitacora(String JSON, String accion, Object nombre_usuario, String tabla, String ip)
    {
        try {
            Bitacora bitacora = new Bitacora(nombre_usuario.toString(), ip, accion, tabla, JSON);
            this.insertarBitacora(bitacora);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBitacora(int id, String accion, Object nombre_usuario, String tabla, String ip)
    {
        JSONObject JSON = new JSONObject();
        try {
            JSON.put("id_objeto", id);
        }
        catch (Exception e) {

        }
        Bitacora bitacora = new Bitacora(nombre_usuario.toString(), ip, accion, tabla, JSON.toString());
        this.insertarBitacora(bitacora);
    }

    public boolean insertarBitacora(Bitacora b)
    {
        boolean resultado = false;

        try {
            //Inserta el Estado en un campo de tipo JSON para poder manipular el objeto de forma dinamica desde la BD
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bitacora.bitacora (fecha_accion,nombre_usuario,ip,accion,tabla,estado) "
                                                                        + " VALUES (?,?,?,?,?,?) RETURNING id_bitacora");

            PGobject json = new PGobject();
            json.setType("json");
            json.setValue(b.getEstado());

            consulta.setTimestamp(1, b.getFecha_accion());
            consulta.setString(2, b.getNombre_usuario());
            consulta.setString(3, b.getIp());
            consulta.setString(4, b.getAccion());
            consulta.setString(5, b.getTabla());
            consulta.setObject(6, json);

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
                b.setId_bitacora(resultadoConsulta.getInt("id_bitacora"));
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public List<Bitacora> obtenerBitacoras()
    {

        List<Bitacora> resultado = new ArrayList<Bitacora>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT id_bitacora, fecha_accion, nombre_usuario,ip,tabla, accion,estado FROM bitacora.bitacora ORDER BY fecha_accion DESC;");

            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Bitacora bitacora = new Bitacora();
                bitacora.setId_bitacora(rs.getInt("id_bitacora"));
                bitacora.setAccion(rs.getString("accion"));
                bitacora.setFecha_accion(rs.getTimestamp("fecha_accion"));
                bitacora.setNombre_usuario(rs.getString("nombre_usuario"));
                bitacora.setIp(rs.getString("ip"));
                bitacora.setTabla(rs.getString("tabla"));
                bitacora.setEstado(rs.getString("estado"));

                resultado.add(bitacora);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public Bitacora obtenerBitacora(int id_bitacora)
    {

        Bitacora bitacora = new Bitacora();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM bitacora.bitacora Where id_bitacora = ? ");
            consulta.setInt(1, id_bitacora);
            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                bitacora.setId_bitacora(rs.getInt("id_bitacora"));
                bitacora.setAccion(rs.getString("accion"));
                bitacora.setFecha_accion(rs.getTimestamp("fecha_accion"));
                bitacora.setNombre_usuario(rs.getString("nombre_usuario"));
                bitacora.setIp(rs.getString("ip"));
                bitacora.setTabla(rs.getString("tabla"));
                bitacora.setEstado(rs.getString("estado"));
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return bitacora;
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
                ex.printStackTrace();
                conexion = null;
            }
        }
    }

}
