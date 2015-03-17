package com.icp.sigipro.bitacora.modelo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class Bitacora {
    
    private int id_bitacora;
    private Timestamp fecha_accion;
    private String nombre_usuario;
    private String ip;
    private String accion;
    private String tabla;
    //Almacena el estado despues de la accion, se almacenará en forma de JSON
    private String estado;
    
    //Variables de Tablas
    
    public static final String TABLA_ACTIVOFIJO = "BODEGA.ACTIVOS_FIJOS";
    public static final String TABLA_CATALOGOEXTERNO = "BODEGA.CATALOGO_EXTERNO";
    public static final String TABLA_CATALOGOEXTERNOINTERNO = "BODEGA.CATALOGOS_INTERNOS_EXTERNOS";
    public static final String TABLA_CATALOGOINTERNO = "BODEGA.CATALOGO_INTERNO";
    public static final String TABLA_REACTIVO = "BODEGA.REACTIVOS";
    public static final String TABLA_UBICACION = "BODEGA.UBICACIONES";
    public static final String TABLA_UBICACIONBODEGA = "BODEGA.UBICACIONES_BODEGA";
    public static final String TABLA_UBICACIONCATALOGOINTERNO = "BODEGA.UBICACIONES_CATALOGO_INTERNO";
    public static final String TABLA_PROVEEDOR = "COMPRAS.PROVEEDORES";
    public static final String TABLA_CORREO = "CONFIGURACION.CORREO";
    public static final String TABLA_INGRESO = "BODEGA.INGRESOS";
    public static final String TABLA_SUBBODEGAS = "BODEGA.SUB_BODEGAS";
    public static final String TABLA_PRESTAMO = "BODEGA.SOLICITUDES_PRESTAMOS";
    public static final String TABLA_SOLICITUD = "BODEGA.SOLICITUDES";
    public static final String TABLA_INVENTARIO = "BODEGA.INVENTARIOS";
    public static final String TABLA_SECCION = "SEGURIDAD.SECCIONES";
    public static final String TABLA_PERMISO = "SEGURIDAD.PERMISOS";
    public static final String TABLA_PERMISOROL = "SEGURIDAD.PERMISO_ROLES";
    public static final String TABLA_PUESTO = "SEGURIDAD.PUESTOS";
    public static final String TABLA_ROL = "SEGURIDAD.ROLES";
    public static final String TABLA_ROLUSUARIO = "SEGURIDAD.ROLES_USUARIOS";
    public static final String TABLA_USUARIO = "SEGURIDAD.USUARIOS";
    public static final String TABLA_ESPECIE = "SERPENTARIO.ESPECIES";
    public static final String TABLA_SERPIENTE = "SERPENTARIO.SERPIENTES";
    
    
    //Variables de Accion
    public static final String ACCION_AGREGAR = "AGREGAR";
    public static final String ACCION_EDITAR = "EDITAR";
    public static final String ACCION_ELIMINAR = "ELIMINAR";
    public static final String ACCION_LOGIN = "LOG IN";
    public static final String ACCION_LOGOUT = "LOG OUT";
    public static final String ACCION_APROBAR = "APROBAR";
    public static final String ACCION_RECHAZAR = "RECHAZAR";
    public static final String ACCION_ENTREGAR = "ENTREGAR";
    public static final String ACCION_REPONER = "REPONER";

   
    
    
    public Bitacora(){
   
    }
    
    public Bitacora(int id_bitacora, Timestamp fecha_accion, String nombre_usuario, String ip, String accion, String tabla,String estado){
        this.id_bitacora = id_bitacora;
        this.fecha_accion = fecha_accion;
        this.nombre_usuario = nombre_usuario;
        this.ip = ip;
        this.accion = accion;
        this.tabla = tabla;
        this.estado = estado;
        
    }
    public Bitacora(String nombre_usuario, String ip, String accion, String tabla, String estado){
        Timestamp date = new Timestamp(new Date().getTime());
        this.fecha_accion = date;
        this.nombre_usuario = nombre_usuario;
        this.ip = ip;
        this.accion = accion;
        this.tabla = tabla;
        this.estado = estado;
        
    }
    
    public int getId_bitacora(){
        return this.id_bitacora;
    }
    
    public JSONObject getEstado_parse(){
        try{
            return new JSONObject(this.estado);
        }catch(Exception e){
            return null;
        }
        
    }
    
    public String getFecha_accion_parse(){
          String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.fecha_accion); 
          return date;
    }
    
    public Timestamp getFecha_accion(){
        return this.fecha_accion;
    }
    
    public String getAccion(){
        return this.accion;
    }
    
    public String getEstado(){
        return this.estado;
    }
    
    public String getIp(){
        return this.ip;
    }
    
    public String getNombre_usuario(){
        return this.nombre_usuario;
    }
    
    public int getId_objeto(){
        int id_objeto = 0;
        try{
            JSONObject json = new JSONObject(this.estado);
            id_objeto = json.getInt("id_objeto");
        }catch (Exception e){
            
        }
        return id_objeto;
    }
    
    public String getTabla(){
        return this.tabla;
    }
    
    public void setId_bitacora(int id_bitacora){
        this.id_bitacora = id_bitacora;
    }
    
    public void setFecha_accion(Timestamp fecha_accion){
        this.fecha_accion = fecha_accion;
    }
    
    public void setAccion(String accion){
        this.accion = accion;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    public void setIp(String ip){
        this.ip = ip;
    }
    
    public void setNombre_usuario(String nombre_usuario){
        this.nombre_usuario = nombre_usuario;
    }
    
    public void setTabla(String tabla){
        this.tabla = tabla;
    }
        

}
