/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.utilidades;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 *
 * @author Boga
 */
public class HelperVarios {
    
    private static HelperVarios theSingleton = null;
    
    private HelperVarios(){}
    
    public static HelperVarios getSingletonHelperVarios() {
        if (theSingleton == null) {
            theSingleton = new HelperVarios();
        }
        return theSingleton;
    }
    
    public String redondearString(String numero, int decimales) {
        BigDecimal bd = new BigDecimal(numero);
        bd = bd.setScale(decimales, BigDecimal.ROUND_HALF_UP);
        return bd.toPlainString();
    }
    
    public String redondearDouble(Double numero, int decimales) {
        DecimalFormat df = new DecimalFormat(definirFormato(decimales));
        return df.format(numero);
    }
    
    public float redondearFloat(float numero, int decimales) {
        BigDecimal bd = new BigDecimal(Float.toString(numero));
        bd = bd.setScale(decimales, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    
    public float parsearFloat(String valor) {
        float resultado = 0.0f;
        if (valor != null) {
            if (!valor.isEmpty()) {
                resultado = Float.parseFloat(valor);
            }
        }
        return resultado;
    }
    
    private String definirFormato(int decimales) {
        String resultado = "#.";
        int i = 0;
        while(i < decimales) {
            resultado += "#";
            i++;
        }
        return resultado;
    }
    
    public String ids_string(String[] ids)
    {
        String resultado = "(";
        for (String s : ids) {
            resultado = resultado + s;
            resultado = resultado + ",";
        }
        resultado = resultado.substring(0, resultado.length() - 1);
        return resultado;
    }
    
    public String ids_string(int[] ids)
    {
        String resultado = "";
        for (int i : ids) {
            resultado = resultado + i;
            resultado = resultado + ",";
        }
        resultado = resultado.substring(0, resultado.length() - 1);
        return resultado;
    }
    
    public String ids_string(List<Integer> ids)
    {
        String resultado = "";
        for (int i : ids) {
            resultado = resultado + i;
            resultado = resultado + ",";
        }
        resultado = resultado.substring(0, resultado.length() - 1);
        return resultado;
    }
}
