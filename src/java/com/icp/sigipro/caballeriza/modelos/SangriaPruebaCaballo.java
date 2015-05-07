/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.modelos;

/**
 *
 * @author Boga
 */
public class SangriaPruebaCaballo
{
    private Caballo caballo;
    private SangriaPrueba sangria_prueba;
    private float hematrocito;
    private float hemoglobina;
    
    public SangriaPruebaCaballo() {
        
    }

    public Caballo getCaballo()
    {
        return caballo;
    }

    public void setCaballo(Caballo caballo)
    {
        this.caballo = caballo;
    }

    public SangriaPrueba getSangria_prueba()
    {
        return sangria_prueba;
    }

    public void setSangria_prueba(SangriaPrueba sangria_prueba)
    {
        this.sangria_prueba = sangria_prueba;
    }

    public float getHematrocito()
    {
        return hematrocito;
    }

    public void setHematrocito(float hematrocito)
    {
        this.hematrocito = hematrocito;
    }

    public float getHemoglobina()
    {
        return hemoglobina;
    }

    public void setHemoglobina(float hemoglobina)
    {
        this.hemoglobina = hemoglobina;
    }
    
    
}
