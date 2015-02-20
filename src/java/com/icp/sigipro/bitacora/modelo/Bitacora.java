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
    
    public String TABLA_ACTIVOFIJO = "ACTIVO FIJO";
    public String TABLA_CATALOGOEXTERNO = "CATALOGO EXTERNO";
    public String TABLA_CATALOGOINTERNO = "CATALOGO INTERNO";
    public String TABLA_UBICACION = "UBICACION";
    public String TABLA_UBICACIONBODEGA = "UBICACION BODEGA";
    public String TABLA_PROVEEDOR = "PROVEEDOR";
    public String TABLA_CORREO = "CORREO";
    public String TABLA_SECCION = "SECCION";
    public String TABLA_PERMISO = "PERMISO";
    public String TABLA_PERMISOROL = "PERMISO ROL";
    public String TABLA_PUESTO = "PUESTO";
    public String TABLA_ROL = "ROL";
    public String TABLA_ROLUSUARIO = "ROL USUARIO";
    public String TABLA_USUARIO = "USUARIO";
    
    
    //Variables de Accion
    public String ACCION_AGREGAR = "AGREGAR";
    public String ACCION_EDITAR = "EDITAR";
    public String ACCION_ELIMINAR = "ELIMINAR";
    public String ACCION_VER = "VER"; //????
    public String ACCION_LOGIN = "LOG IN";
    public String ACCION_LOGOUT = "LOG OUT";
    
    
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
