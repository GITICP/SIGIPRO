/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core;

/**
 *
 * @author Boga
 */
public class SIGIPROException extends Exception
{

    String redireccion;

    public SIGIPROException()
    {
    }

    public SIGIPROException(String mensaje)
    {
        super(mensaje);
    }

    public SIGIPROException(String mensaje, String redireccion_error)
    {
        super(mensaje);
        redireccion = redireccion_error;
    }

    public String getRedireccion()
    {
        return redireccion;
    }

}
