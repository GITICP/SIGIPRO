/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.clases;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class BarraFuncionalidad 
{
    
    String Modulo = null;
    List<String[]> Funcionalidades = new ArrayList<String[]>();
    
    @SuppressWarnings("Convert2Diamond")
    public BarraFuncionalidad(String modulo)
    {
        Modulo = modulo;
    }
    public BarraFuncionalidad() 
    {  
        
    }
    
    public void agregarModulo(String modulo)
    {
        Modulo = modulo;
    }
    
    public void agregarFuncionalidad(String[] funcionalidad)
    {
        Funcionalidades.add(funcionalidad);
    }
    
    public List<String[]> getFuncionalidades()
    {
        return Funcionalidades;
    }
    
    public String getModulo()
    {
        return Modulo;
    }
}
