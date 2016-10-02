/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class AnalisisTipoMuestra {

    private final List<Analisis> analisis;
    private final List<Muestra> muestras;
    private TipoMuestra tipo_muestra;

    public AnalisisTipoMuestra(Analisis a, TipoMuestra tm, Muestra m) {
        analisis = new ArrayList<>();
        analisis.add(a);
        muestras = new ArrayList<>();
        muestras.add(m);
        tipo_muestra = tm;

    }

    public List<Analisis> getAnalisis() {
        return analisis;
    }

    public String getAnalisisAsString() {
        String resultado = "";
        for (Analisis a : this.analisis) {
            resultado = resultado + String.valueOf(a.getId_analisis()) + ",";
        }
        return resultado.substring(0, resultado.length() - 1);
    }

    public void agregarAnalisis(Analisis analisis) {
        if (analisis != null) {
            boolean existe = false;
            for (Analisis a : this.analisis) {
                if (a.getId_analisis() == analisis.getId_analisis()) {
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                this.analisis.add(analisis);
            }
        } else {
            return;
        }
    }

    public void agregarMuestra(Muestra muestra) {
        if (muestra != null) {
            boolean existe = false;
            for (Muestra muestra_iter : this.muestras) {
                if (muestra_iter.getId_muestra() == muestra.getId_muestra()) {
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                this.muestras.add(muestra);
            }
        } else {
            return;
        }

    }

    public TipoMuestra getTipo_muestra() {
        return tipo_muestra;
    }

    public void setTipo_muestra(TipoMuestra tipo_muestra) {
        this.tipo_muestra = tipo_muestra;
    }

    public int getCantidad_muestras() {
        return this.muestras.size();
    }

}
