/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core;

import java.util.List;

/**
 *
 * @author Boga
 */
public abstract class DAO
{
    String nombreTabla;
    List<String[]> errores;
    
    public DAO(String nomTabla)
    {
        nombreTabla = nomTabla;
    }
    
    public abstract IModelo buscar(Long id);

    public abstract IModelo buscarPor(String campo, Object valor);
    
    public abstract List<IModelo> obtenerTodo();
    
    public abstract boolean insertar(IModelo param);

    public abstract boolean actualizar(IModelo param);

    public abstract boolean eliminar(IModelo param);



}
