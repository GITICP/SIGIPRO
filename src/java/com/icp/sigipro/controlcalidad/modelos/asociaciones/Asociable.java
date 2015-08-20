/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos.asociaciones;

/**
 *
 * @author Boga
 */
public abstract class Asociable {
    
    protected final String SANGRIA = "sangria";
    
    public abstract void setTipoAsociacion(String objeto);
    public abstract int getId();
    public abstract boolean tieneTipoAsociacion();
    
}
