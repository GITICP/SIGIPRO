package com.icp.sigipro.bitacora.modelo;

import java.sql.Date;
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
    //Almacena el estado anterior del sistema, se almacenará en forma de JSON
    private String estado;
    
    //Variables de Tablas
    
    public static final String TABLA_ACTIVOFIJO = "ACTIVO FIJO";
    public static final String TABLA_CATALOGOEXTERNO = "CATALOGO EXTERNO";
    public static final String TABLA_CATALOGOINTERNO = "CATALOGO INTERNO";
    public static final String TABLA_UBICACION = "UBICACION";
    public static final String TABLA_UBICACIONBODEGA = "UBICACION BODEGA";
    public static final String TABLA_PROVEEDOR = "PROVEEDOR";
    public static final String TABLA_CORREO = "CORREO";
    public static final String TABLA_SECCION = "SECCION";
    public static final String TABLA_PERMISO = "PERMISO";
    public static final String TABLA_PERMISOROL = "PERMISO ROL";
    public static final String TABLA_PUESTO = "PUESTO";
    public static final String TABLA_ROL = "ROL";
    public static final String TABLA_ROLUSUARIO = "ROL USUARIO";
    public static final String TABLA_USUARIO = "USUARIO";
    
    
    //Variables de Accion
    public static final String ACCION_AGREGAR = "AGREGAR";
    public static final String ACCION_EDITAR = "EDITAR";
    public static final String ACCION_ELIMINAR = "ELIMINAR";
    public static final String ACCION_LOGIN = "LOG IN";
    public static final String ACCION_LOGOUT = "LOG OUT";
    
    
    public Bitacora(){
   
    }
    
    public Bitacora(int id_bitacora, Date fecha_accion, String nombre_usuario, String ip, String accion, String tabla, String estado){
        this.id_bitacora = id_bitacora;
        this.fecha_accion = fecha_accion;
        this.nombre_usuario = nombre_usuario;
        this.ip = ip;
        this.accion = accion;
        this.tabla = tabla;
        this.estado = estado;
        
    }
}
