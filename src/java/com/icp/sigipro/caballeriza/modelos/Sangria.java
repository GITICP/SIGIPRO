/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.modelos;

import com.icp.sigipro.controlcalidad.modelos.Informe;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelperVarios;
import java.lang.reflect.Field;
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
public class Sangria {
    private int id_sangria;
    private String observaciones;
    private Date fecha;
    private Date fecha_dia1;
    private Date fecha_dia2;
    private Date fecha_dia3;
    private float hematrocito_promedio;
    private Informe informe_dia1;
    private Informe informe_dia2;
    private Informe informe_dia3;
    private Usuario responsable;
    private int cantidad_de_caballos;
    private float sangre_total;
    private float peso_plasma_total;
    private float volumen_plasma_total;
    private float plasma_por_caballo;
    private float potencia;
    private GrupoDeCaballos grupo;
    List<SangriaCaballo> sangrias_caballos;
    List<SangriaCaballo> sangrias_caballos_sin_participacion;
    
    List<Caballo> caballos; //Este atributo es para ayudar y no se encuentra en la base de datos

    public Sangria() {
    }

    public int getId_sangria() {
        return id_sangria;
    }

    public void setId_sangria(int id_sangria) {
        this.id_sangria = id_sangria;
    }
    
    public String getId_sangria_especial() {
        return formatearFecha(fecha).replaceAll("/", "-") + "-" + grupo.getNombre() + " (id: " + id_sangria + ")";
    }

    public Date getFecha()
    {
        return fecha;
    }
    
    public String getFechaAsString() {
        return formatearFecha(fecha);
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public Date getFecha_dia1() {
        return fecha_dia1;
    }
    
    public String getFecha_dia1AsString() {
        return formatearFecha(fecha_dia1);
    }

    public void setFecha_dia1(Date fecha_dia1) {
        this.fecha_dia1 = fecha_dia1;
    }

    public Date getFecha_dia2() {
        return fecha_dia2;
    }
    
    public String getFecha_dia2AsString() {
        return formatearFecha(fecha_dia2);
    }
    
    public void setFecha_dia2(Date fecha_dia2) {
        this.fecha_dia2 = fecha_dia2;
    }
    
    public Date getFecha_dia3() {
        return fecha_dia3;
    }
    
    public String getFecha_dia3AsString() {
        return formatearFecha(fecha_dia3);
    }
    
    public void setFecha_dia3(Date fecha_dia3) {
        this.fecha_dia3 = fecha_dia3;
    }

    public float getHematrocito_promedio() {
        return hematrocito_promedio;
    }

    public void setHematrocito_promedio(float hematrocito_promedio) {
        this.hematrocito_promedio = hematrocito_promedio;
    }

    public Informe getInforme_dia1() {
        return informe_dia1;
    }

    public void setInforme_dia1(Informe informe_dia1) {
        this.informe_dia1 = informe_dia1;
    }

    public Informe getInforme_dia2() {
        return informe_dia2;
    }

    public void setInforme_dia2(Informe informe_dia2) {
        this.informe_dia2 = informe_dia2;
    }

    public Informe getInforme_dia3() {
        return informe_dia3;
    }

    public void setInforme_dia3(Informe informe_dia3) {
        this.informe_dia3 = informe_dia3;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public int getCantidad_de_caballos() {
        return cantidad_de_caballos;
    }

    public void setCantidad_de_caballos(int cantidad_de_caballos) {
        this.cantidad_de_caballos = cantidad_de_caballos;
    }

    public float getSangre_total() {
        return formatearResultado(sangre_total);// Esta línea se cambió
    }

    public void setSangre_total(float sangre_total) {
        this.sangre_total = sangre_total;
    }

    public float getPeso_plasma_total() {
        return formatearResultado(peso_plasma_total);// Esta línea se cambió
    }

    public void setPeso_plasma_total(float peso_plasma_total) {
        this.peso_plasma_total = peso_plasma_total;
    }

    public float getVolumen_plasma_total() {
        return formatearResultado(volumen_plasma_total);// Esta línea se cambió
    }

    public void setVolumen_plasma_total(float volumen_plasma_total) {
        this.volumen_plasma_total = volumen_plasma_total;
    }

    public float getPlasma_por_caballo() {
        return formatearResultado(plasma_por_caballo);// Esta línea se cambió
    }

    public void setPlasma_por_caballo(float plasma_por_caballo) {
        this.plasma_por_caballo = plasma_por_caballo;
    }

    public float getPotencia() {
        return formatearResultado(potencia); // Esta línea se cambió
    }

    public void setPotencia(float potencia) {
        this.potencia = potencia;
    }

    public List<SangriaCaballo> getSangrias_caballos()
    {
        return sangrias_caballos;
    }
    
    // Se agregó esta función
    public List<SangriaCaballo> getSangrias_caballos_sin_participacion()
    {
        return sangrias_caballos_sin_participacion;
    }

    public void setSangrias_caballos(List<SangriaCaballo> sangrias_caballos)
    {
        this.sangrias_caballos = sangrias_caballos;
    }

    public GrupoDeCaballos getGrupo()
    {
        return grupo;
    }

    public void setGrupo(GrupoDeCaballos grupo)
    {
        this.grupo = grupo;
    }

    public List<Caballo> getCaballos()
    {
        return caballos;
    }

    public void setCaballos(List<Caballo> caballos)
    {
        this.caballos = caballos;
    }
    
    public float getSubtotalSangre(int dia) {
        if (dia == 1) {
            return getSubtotalSangreDia1();
        } else if (dia == 2) {
            return getSubtotalSangreDia2();
        } else if (dia == 3) {
            return getSubtotalSangreDia3();
        } else {
            return 0;
        }
    }
    
    private float getSubtotalSangreDia1() {
        float resultado = 0.0f;
        for (SangriaCaballo sc : sangrias_caballos) {
            resultado += sc.getSangre_dia1();
        }
        return formatearResultado(resultado);
    }
    
    private float getSubtotalSangreDia2() {
        float resultado = 0.0f;
        for (SangriaCaballo sc : sangrias_caballos) {
            resultado += sc.getSangre_dia2();
        }
        return formatearResultado(resultado);
    }
    private float getSubtotalSangreDia3() {
        float resultado = 0.0f;
        for (SangriaCaballo sc : sangrias_caballos) {
            resultado += sc.getSangre_dia3();
        }
        return formatearResultado(resultado);
    }
    
    public float getSubtotalPlasma(int dia) {
        if (dia == 1) {
            return getSubtotalPlasmaDia1();
        } else if (dia == 2) {
            return getSubtotalPlasmaDia2();
        } else if (dia == 3) {
            return getSubtotalPlasmaDia3();
        } else {
            return 0;
        }
    }
    
    private float getSubtotalPlasmaDia1() {
        float resultado = 0.0f;
        for (SangriaCaballo sc : sangrias_caballos) {
            resultado += sc.getPlasma_dia1();
        }
        return formatearResultado(resultado);
    }
    
    private float getSubtotalPlasmaDia2() {
        float resultado = 0.0f;
        for (SangriaCaballo sc : sangrias_caballos) {
            resultado += sc.getPlasma_dia2();
        }
        return formatearResultado(resultado);
    }
    private float getSubtotalPlasmaDia3() {
        float resultado = 0.0f;
        for (SangriaCaballo sc : sangrias_caballos) {
            resultado += sc.getPlasma_dia3();
        }
        return formatearResultado(resultado);
    }
    
    public void agregarSangriaCaballo(SangriaCaballo sangria_caballo) {
        if(sangrias_caballos == null) {
            sangrias_caballos = new ArrayList<SangriaCaballo>();
        }
        sangrias_caballos.add(sangria_caballo);
    }
    
    // Se agregó esta función
    public void agregarSangriaCaballoSinParticipacion(SangriaCaballo sangria_caballo) {
        if(sangrias_caballos_sin_participacion == null) {
            sangrias_caballos_sin_participacion = new ArrayList<SangriaCaballo>();
        }
        sangrias_caballos_sin_participacion.add(sangria_caballo);
    }
    
    private float formatearResultado(float sumatoria) {
        HelperVarios hv = HelperVarios.getSingletonHelperVarios();
        return hv.redondearFloat(sumatoria, 2);
    }
    
    public boolean valididarCaballoEnSangria(Caballo c){
        if (caballos == null) {
            caballos = new ArrayList<Caballo>();
            for (SangriaCaballo sangria_caballo : sangrias_caballos){
                caballos.add(sangria_caballo.getCaballo());
            }
        }
        return caballos.contains(c);
    }

//Parsea a JSON la clase de forma automatica y estandarizada para todas las clases
    public String parseJSON(){
        Class _class = this.getClass();
        JSONObject JSON = new JSONObject();
        try{
            Field properties[] = _class.getDeclaredFields();
            for (int i = 0; i < properties.length; i++) {
                Field field = properties[i];
                if (i != 0){
                    JSON.put(field.getName(), field.get(this));
                }else{
                    JSON.put("id_objeto", field.get(this));
                }
            }
            JSON.put("id_resonsable",this.responsable.getId_usuario());

        }catch (Exception e){
            
        }
        return JSON.toString();
    }
    
    private String formatearFecha(Date fecha) {
        String resultado;
        if (fecha != null){
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            resultado = df.format(fecha);
        } else {
            resultado = "Pendiente";
        }
        return resultado;
    }
    
    public int[] obtener_ids_caballos_sangria() {
        int[] resultado = new int[sangrias_caballos.size()];
        
        for (int i = 0; i < sangrias_caballos.size(); i++) {
            resultado[i] =  sangrias_caballos.get(i).getCaballo().getId_caballo();
        }
        return resultado;
    }
    
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        if (observaciones == null) {
            this.observaciones = "Sin observaciones.";
        } else {
            if (observaciones.isEmpty()) {
                this.observaciones = "Sin observaciones.";
            } else {
                this.observaciones = observaciones;
            }
        }
    }
}
