package com.icp.sigipro.bitacora.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author ld.conejo
 */

public class BitacoraDAO {
    
    private Connection conexion;
  
    public BitacoraDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    } 
    
    public BitacoraDAO(Connection conexion){
        this.conexion = conexion;
    }
    
    private Connection getConexion(){
        try{     

            if ( conexion.isClosed() ){
                SingletonBD s = SingletonBD.getSingletonBD();
                conexion = s.conectar();
            }
        }
        catch(Exception ex)
        {
            conexion = null;
        }

        return conexion;
      }
    
    public boolean insertarBitacora(Bitacora b){
        boolean resultado = false;
        
        try {
            //Inserta el Estado en un campo de tipo JSON para poder manipular el objeto de forma dinamica desde la BD
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bitacora.bitacora (fecha_accion,nombre_usuario,ip,accion,tabla,id_objeto,estado) "
                    + " VALUES (?,?,?,?,?,"+b.getId_objeto()+",'"+b.getEstado()+"') RETURNING id_bitacora");

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            consulta.setString(1, dateFormat.format(b.getFecha_accion()));
            consulta.setString(2, b.getNombre_usuario());
            consulta.setString(3, b.getIp());
            consulta.setString(4, b.getAccion());
            consulta.setString(5, b.getTabla());
            
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
                b.setId_bitacora(resultadoConsulta.getInt("id_bitacora"));
            }
            consulta.close();
            conexion.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    
}
