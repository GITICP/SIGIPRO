/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

import com.icp.sigipro.utilidades.HelperFechas;
import java.sql.Date;

/**
 *
 * @author Boga
 */
public class InventarioSubBodega
{
    int id_inventario_sub_bodega;
    SubBodega sub_bodega;
    ProductoInterno producto;
    int cantidad;
    Date fecha_vencimiento;

    public InventarioSubBodega()
    {
    }

    public int getId_inventario_sub_bodega()
    {
        return id_inventario_sub_bodega;
    }

    public void setId_inventario_sub_bodega(int id_inventario_sub_bodega)
    {
        this.id_inventario_sub_bodega = id_inventario_sub_bodega;
    }

    public SubBodega getSub_bodega()
    {
        return sub_bodega;
    }

    public void setSub_bodega(SubBodega sub_bodega)
    {
        this.sub_bodega = sub_bodega;
    }

    public ProductoInterno getProducto()
    {
        return producto;
    }

    public void setProducto(ProductoInterno producto)
    {
        this.producto = producto;
    }

    public int getCantidad()
    {
        return cantidad;
    }

    public void setCantidad(int cantidad)
    {
        this.cantidad = cantidad;
    }

    public Date getFecha_vencimiento()
    {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento)
    {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public String getFecha_vencimientoAsString()
    {
        if(fecha_vencimiento != null){
            return formatearFecha(fecha_vencimiento);
        } else {
            return "";
        }        
    }

    private String formatearFecha(Date fecha)
    {
        HelperFechas h = HelperFechas.getSingletonHelperFechas();
        return h.formatearFecha(fecha);
    }
}
