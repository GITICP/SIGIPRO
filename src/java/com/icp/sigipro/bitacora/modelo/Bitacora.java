package com.icp.sigipro.bitacora.modelo;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author ld.conejo
 */
public class Bitacora {
    
    private int id_bitacora;
    private Date fecha_accion;
    private String nombre_usuario;
    private String ip;
    private String accion;
    private String tabla;
    //Almacena el estado despues de la accion, se almacenará en forma de JSON
    private String estado;
    
    //Variables de Tablas
    
    public static final String TABLA_ACTIVOFIJO = "ACTIVOS_FIJOS";
    public static final String TABLA_CATALOGOEXTERNO = "CATALOGO_EXTERNO";
    public static final String TABLA_CATALOGOEXTERNOINTERNO = "CATALOGOS_INTERNOS_EXTERNOS";
    public static final String TABLA_CATALOGOINTERNO = "CATALOGO_INTERNO";
    public static final String TABLA_REACTIVO = "REACTIVOS";
    public static final String TABLA_UBICACION = "UBICACIONES";
    public static final String TABLA_UBICACIONBODEGA = "UBICACIONES_BODEGA";
    public static final String TABLA_UBICACIONCATALOGOINTERNO = "UBICACIONES_CATALOGO_INTERNO";
    public static final String TABLA_PROVEEDOR = "PROVEEDORES";
    public static final String TABLA_CORREO = "CORREO";
    public static final String TABLA_INGRESO = "INGRESOS";
    public static final String TABLA_PRESTAMO = "SOLICITUDES_PRESTAMOS";
    public static final String TABLA_SOLICITUD = "SOLICITUDES";
    public static final String TABLA_INVENTARIO = "INVENTARIOS";
    public static final String TABLA_SECCION = "SECCIONES";
    public static final String TABLA_PERMISO = "PERMISOS";
    public static final String TABLA_PERMISOROL = "PERMISO_ROLES";
    public static final String TABLA_PUESTO = "PUESTOS";
    public static final String TABLA_ROL = "ROLES";
    public static final String TABLA_ROLUSUARIO = "ROLES_USUARIOS";
    public static final String TABLA_USUARIO = "USUARIOS";
    
    
    //Variables de Accion
    public static final String ACCION_AGREGAR = "AGREGAR";
    public static final String ACCION_EDITAR = "EDITAR";
    public static final String ACCION_ELIMINAR = "ELIMINAR";
    public static final String ACCION_LOGIN = "LOG IN";
    public static final String ACCION_LOGOUT = "LOG OUT";
    
    
    public Bitacora(){
   
    }
    
    public Bitacora(int id_bitacora, Date fecha_accion, String nombre_usuario, String ip, String accion, String tabla,String estado){
        this.id_bitacora = id_bitacora;
        this.fecha_accion = fecha_accion;
        this.nombre_usuario = nombre_usuario;
        this.ip = ip;
        this.accion = accion;
        this.tabla = tabla;
        this.estado = estado;
        
    }
    public Bitacora(String nombre_usuario, String ip, String accion, String tabla, String estado){
        Date date = new Date();
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
    
    public Date getFecha_accion(){
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
    
    public String getTabla(){
        return this.tabla;
    }
    
    public void setId_bitacora(int id_bitacora){
        this.id_bitacora = id_bitacora;
    }
    
    public void setFecha_accion(Date fecha_accion){
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
