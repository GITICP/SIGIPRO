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
public class ControlSolicitud
{

    List<AnalisisTipoMuestra> analisis_tipo_muestras;

    public ControlSolicitud() {
        analisis_tipo_muestras = new ArrayList<AnalisisTipoMuestra>();
    }

    public void agregarCombinacion(Analisis a, TipoMuestra tm, Muestra m) {

        boolean existe = false;

        for (AnalisisTipoMuestra a_tm : analisis_tipo_muestras) {
            if (a_tm.getTipo_muestra().getId_tipo_muestra() == tm.getId_tipo_muestra()) {
                a_tm.agregarAnalisis(a);
                a_tm.agregarMuestra(m);
                existe = true;
                break;
            }
        }

        if (!existe) {
            analisis_tipo_muestras.add(new AnalisisTipoMuestra(a, tm, m));
        }
    }

    public List<AnalisisTipoMuestra> getAnalisis_tipo_muestras() {
        return analisis_tipo_muestras;
    }
    
    public String getTiposMuestrasHTML() {
        String resultado = "";
        for (AnalisisTipoMuestra a_tm : analisis_tipo_muestras) {
            resultado += "<br><span>" + a_tm.getTipo_muestra().getNombre() + "</span>";
        }
        return resultado.substring(4);
    }

}
