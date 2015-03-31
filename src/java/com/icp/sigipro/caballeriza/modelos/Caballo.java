/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.modelos;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Walter
 */
public class Caballo {
    
    public static final String MUERTO = "Muerto";
    public static final String VIVO = "Vivo";
    
    public static final String MACHO = "Macho";
    public static final String HEMBRA = "Hembra";
    
    public static final List<String> ESTADOS = new ArrayList<String>()
    {
        {
            add(MUERTO);
            add(VIVO);
        }
    };
    
    public static final List<String> SEXOS = new ArrayList<String>()
    {
        {
            add(MACHO);
            add(HEMBRA);
        }
    };
    
    
    
    private int id_caballo;
    private String nombre;
    private int numero_microchip;
    private Date fecha_nacimiento;
    private Date fecha_ingreso;
    private String sexo;
    private String color;
    private String otras_sennas;
    private Blob fotografia;
    private String estado;
    private GrupoDeCaballos grupo_de_caballos;

    public Caballo() {
    }

    public Caballo(int id_caballo, String nombre, int numero_microchip, Date fecha_nacimiento, Date fecha_ingreso, String sexo, String color, String otras_sennas, Blob fotografia, String estado, GrupoDeCaballos grupo_de_caballos) {
        this.id_caballo = id_caballo;
        this.nombre = nombre;
        this.numero_microchip = numero_microchip;
        this.fecha_nacimiento = fecha_nacimiento;
        this.fecha_ingreso = fecha_ingreso;
        this.sexo = sexo;
        this.color = color;
        this.otras_sennas = otras_sennas;
        this.fotografia = fotografia;
        this.estado = estado;
        this.grupo_de_caballos = grupo_de_caballos;
    }

    //Parsea a JSON la clase de forma automatica y estandarizada para todas las clases
    public String parseJSON() {
        Class _class = this.getClass();
        JSONObject JSON = new JSONObject();
        try {
            Field properties[] = _class.getDeclaredFields();
            for (int i = 0; i < properties.length; i++) {
                Field field = properties[i];
                if (i != 0) {
                    JSON.put(field.getName(), field.get(this));
                } else {
                    JSON.put("id_objeto", field.get(this));
                }
            }
            JSON.put("id_grupo_caballo", this.grupo_de_caballos.getId_grupo_caballo());
        } catch (Exception e) {

        }
        return JSON.toString();
    }

    public int getId_caballo() {
        return id_caballo;
    }

    public void setId_caballo(int id_caballo) {
        this.id_caballo = id_caballo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero_microchip() {
        return numero_microchip;
    }

    public void setNumero_microchip(int numero_microchip) {
        this.numero_microchip = numero_microchip;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }
    public String getFecha_nacimientoAsString() {
        return formatearFecha(fecha_nacimiento);
    }
    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public String getFecha_ingresoAsString() {
        return formatearFecha(fecha_ingreso);
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOtras_sennas() {
        return otras_sennas;
    }

    public void setOtras_sennas(String otras_sennas) {
        this.otras_sennas = otras_sennas;
    }

    public Blob getFotografia() {
        return fotografia;
    }

    public void setFotografia(Blob fotografia) {
        this.fotografia = fotografia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public GrupoDeCaballos getGrupo_de_caballos() {
        return grupo_de_caballos;
    }

    public void setGrupo_de_caballos(GrupoDeCaballos grupo_de_caballos) {
        this.grupo_de_caballos = grupo_de_caballos;
    }

    private String formatearFecha(Date fecha) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }

}
