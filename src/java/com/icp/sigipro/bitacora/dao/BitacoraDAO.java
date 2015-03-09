package com.icp.sigipro.bitacora.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.bodegas.dao.ProductoInternoDAO;
import com.icp.sigipro.bodegas.modelos.Inventario;
import com.icp.sigipro.configuracion.dao.SeccionDAO;
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
    
    public void setBitacora(String JSON, String accion, Object nombre_usuario, String tabla,String ip){
        Bitacora bitacora = new Bitacora(nombre_usuario.toString(),ip,accion,tabla,JSON);      
        this.insertarBitacora(bitacora);
    }
    
    public void setBitacora(int id, String accion, Object nombre_usuario, String tabla,String ip){
        JSONObject JSON = new JSONObject();
        try{
            JSON.put("id_objeto",id);
        }catch (Exception e){
            
        }
        Bitacora bitacora = new Bitacora(nombre_usuario.toString(),ip,accion,tabla,JSON.toString());      
        this.insertarBitacora(bitacora);
    }
    
    public boolean insertarBitacora(Bitacora b){
        boolean resultado = false;
        
        try {
            //Inserta el Estado en un campo de tipo JSON para poder manipular el objeto de forma dinamica desde la BD
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bitacora.bitacora (fecha_accion,nombre_usuario,ip,accion,tabla,estado) "
                    + " VALUES (?,?,?,?,?,?) RETURNING id_bitacora");
            
            PGobject json = new PGobject();
            json.setType("json");
            json.setValue(b.getEstado());

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            consulta.setString(1, dateFormat.format(b.getFecha_accion()));
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
            consulta.close();
            conexion.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
       public List<Inventario> obtenerInventarios() {

    List<Inventario> resultado = new ArrayList<Inventario>();

    try {
        PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bodega.inventarios");

        ResultSet rs = consulta.executeQuery();

        while (rs.next()) {
            Inventario inventario = new Inventario();
            System.out.println(rs.getInt("id_inventario"));
            inventario.setId_inventario(rs.getInt("id_inventario"));
            inventario.setId_producto(rs.getInt("id_producto"));
            inventario.setId_seccion(rs.getInt("id_seccion"));
            inventario.setStock_actual(rs.getInt("stock_actual"));
            try {
                ProductoInternoDAO pr = new ProductoInternoDAO();
                SeccionDAO sc = new SeccionDAO();
                inventario.setProducto(pr.obtenerProductoInterno(rs.getInt("id_producto")));
                inventario.setSeccion(sc.obtenerSeccion(rs.getInt("id_seccion")));
            }catch (Exception ex) {
                ex.printStackTrace();
            }
            resultado.add(inventario);
        }
        consulta.close();
        conexion.close();
    }catch (Exception ex) {
        ex.printStackTrace();
    }
    return resultado;
  }
   
   public List<Bitacora> obtenerBitacoras() {

    List<Bitacora> resultado = new ArrayList<Bitacora>();

    try {
        PreparedStatement consulta = getConexion().prepareStatement("SELECT id_bitacora, fecha_accion, nombre_usuario,ip,tabla, accion,estado FROM bitacora.bitacora, tabla ORDER BY fecha_accion DESC where ");

        ResultSet rs = consulta.executeQuery();

        while (rs.next()) {
            Inventario inventario = new Inventario();
            System.out.println(rs.getInt("id_inventario"));
            inventario.setId_inventario(rs.getInt("id_inventario"));
            inventario.setId_producto(rs.getInt("id_producto"));
            inventario.setId_seccion(rs.getInt("id_seccion"));
            inventario.setStock_actual(rs.getInt("stock_actual"));
            try {
                ProductoInternoDAO pr = new ProductoInternoDAO();
                SeccionDAO sc = new SeccionDAO();
                inventario.setProducto(pr.obtenerProductoInterno(rs.getInt("id_producto")));
                inventario.setSeccion(sc.obtenerSeccion(rs.getInt("id_seccion")));
            }catch (Exception ex) {
                ex.printStackTrace();
            }
            //resultado.add(inventario);
        }
        consulta.close();
        conexion.close();
    }catch (Exception ex) {
        ex.printStackTrace();
    }
    return resultado;
  }

  public Inventario obtenerInventario(int id_inventario) {

    Inventario inventario = new Inventario();

    try {
      PreparedStatement consulta;
      consulta = getConexion().prepareStatement(" SELECT * FROM bodega.inventarios Where id_inventario = ? ");
      consulta.setInt(1, id_inventario);
      ResultSet rs = consulta.executeQuery();

      if (rs.next()) {
        inventario.setId_inventario(rs.getInt("id_inventario"));
        inventario.setId_producto(rs.getInt("id_producto"));
        inventario.setId_seccion(rs.getInt("id_seccion"));
        inventario.setStock_actual(rs.getInt("stock_actual"));
        try {
          ProductoInternoDAO pr = new ProductoInternoDAO();
          SeccionDAO sc = new SeccionDAO();
          inventario.setProducto(pr.obtenerProductoInterno(rs.getInt("id_producto")));
          inventario.setSeccion(sc.obtenerSeccion(rs.getInt("id_seccion")));
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }

      consulta.close();
      conexion.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return inventario;
  }   
   
}
