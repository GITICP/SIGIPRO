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
    
    protected final transient String SANGRIA = "sangria";
    protected final transient String SANGRIA_PRUEBA = "sangria_prueba";
    protected transient AsociacionSolicitud asociacion;
    
    public AsociacionSolicitud getTipoAsociacion() {
        return asociacion;
    } 
    
    public abstract void setTipoAsociacion(String objeto);
    public abstract int getId();
    public abstract boolean tieneTipoAsociacion();
    
}
