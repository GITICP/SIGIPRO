/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

import java.sql.Date;

/**
 *
 * @author Boga
 */
public class InventarioSubBodega
{

    SubBodega sub_bodega;
    ProductoInterno producto;
    int cantidad;
    Date fecha_vencimiento;

    public InventarioSubBodega()
    {
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

}
