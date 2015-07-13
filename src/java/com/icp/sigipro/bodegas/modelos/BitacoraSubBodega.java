/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelperFechas;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Boga
 */
public class BitacoraSubBodega
{
    public static final String INGRESAR = "Ingresar";
    public static final String EGRESAR = "Consumir";
    public static final String MOVER = "Movimiento";
    public static final String ENTREGAR = "Entregar";
    
    private static final HelperFechas helper = HelperFechas.getSingletonHelperFechas();
    
    private int id_bitacora_sub_bodega;
    private ProductoInterno producto;
    private SubBodega sub_bodega;
    private SubBodega sub_bodega_destino;
    private String accion;
    private Usuario usuario;
    private int cantidad;
    private Timestamp fecha_accion;
    private String observaciones;
    private Date fecha;
    
    public BitacoraSubBodega(){}

    public int getId_bitacora_sub_bodega()
    {
        return id_bitacora_sub_bodega;
    }

    public void setId_bitacora_sub_bodega(int id_bitacora_sub_bodega)
    {
        this.id_bitacora_sub_bodega = id_bitacora_sub_bodega;
    }

    public ProductoInterno getProducto()
    {
        return producto;
    }

    public void setProducto(ProductoInterno producto)
    {
        this.producto = producto;
    }

    public SubBodega getSub_bodega()
    {
        return sub_bodega;
    }

    public void setSub_bodega(SubBodega sub_bodega)
    {
        this.sub_bodega = sub_bodega;
    }

    public SubBodega getSub_bodega_destino()
    {
        return sub_bodega_destino;
    }

    public void setSub_bodega_destino(SubBodega sub_bodega_destino)
    {
        this.sub_bodega_destino = sub_bodega_destino;
    }

    public String getAccion()
    {
        return accion;
    }

    public void setAccion(String accion)
    {
        this.accion = accion;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public int getCantidad()
    {
        return cantidad;
    }

    public void setCantidad(int cantidad)
    {
        this.cantidad = cantidad;
    }

    public Timestamp getFecha_accion()
    {
        return fecha_accion;
    }

    public void setFecha_accion(Timestamp fecha_accion)
    {
        this.fecha_accion = fecha_accion;
    }

    public String getObservaciones()
    {
        return observaciones;
    }

    public void setObservaciones(String observaciones)
    {
        if (observaciones == null) {
            this.observaciones =  "Sin observaciones.";
        } else {
            if (observaciones.isEmpty()) {
                this.observaciones =  "Sin observaciones.";
            } else {
                this.observaciones = observaciones;
            }
        }
    }

    public Date getFecha()
    {
        return fecha;
    }
    
    public String getFechaAsString() {
        return helper.formatearFecha(fecha);
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }
}
