/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.IModelo;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Boga
 */
public class SubBodega extends IModelo
{

    private int id_sub_bodega;
    private Seccion seccion;
    private Usuario usuario;
    private String nombre;
    private List<InventarioSubBodega> inventarios;
    private List<BitacoraSubBodega> historial;

    public SubBodega()
    {

    }

    //Parsea a JSON la clase de forma automatica y estandarizada para todas las clases
    public String parseJSON()
    {
        Class _class = this.getClass();
        JSONObject JSON = new JSONObject();
        try {
            Field properties[] = _class.getDeclaredFields();
            for (int i = 0; i < properties.length; i++) {
                Field field = properties[i];
                if (i != 0) {
                    JSON.put(field.getName(), field.get(this));
                }
                else {
                    JSON.put("id_objeto", field.get(this));
                }
            }
        }
        catch (Exception e) {

        }
        return JSON.toString();
    }

    public int getId_sub_bodega()
    {
        return id_sub_bodega;
    }

    public void setId_sub_bodega(int id_sub_bodega)
    {
        this.id_sub_bodega = id_sub_bodega;
    }

    public Seccion getSeccion()
    {
        return seccion;
    }

    public void setSeccion(Seccion seccion)
    {
        this.seccion = seccion;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public List<InventarioSubBodega> getInventarios()
    {
        return inventarios;
    }

    public void setInventarios(List<InventarioSubBodega> inventarios)
    {
        this.inventarios = inventarios;
    }
    
    public List<BitacoraSubBodega> getHistorial()
    {
        return historial;
    }

    public void setHistorial(List<BitacoraSubBodega> historial)
    {
        this.historial = historial;
    }
}
