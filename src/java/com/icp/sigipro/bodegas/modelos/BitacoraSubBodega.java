/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;

/**
 *
 * @author Boga
 */
public class BitacoraSubBodega
{
    public static final String INGRESAR = "Ingresar";
    public static final String EGRESAR = "Consumir";
    public static final String MOVER = "Movimiento";
    
    private int id_bitacora_sub_bodega;
    private ProductoInterno producto;
    private SubBodega sub_bodega;
    private String accion;
    private Usuario usuario;
    private int cantidad;
    
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
}
