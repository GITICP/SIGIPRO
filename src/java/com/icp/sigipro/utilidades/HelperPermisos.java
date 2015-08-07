/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.utilidades;

import java.util.List;

/**
 *
 * @author Boga
 */
public class HelperPermisos {
    
    private static HelperPermisos theSingleton;
    
    private HelperPermisos(){}
    
    public static HelperPermisos getSingletonHelperPermisos() {
        if (theSingleton == null) {
            theSingleton = new HelperPermisos();
        }
        return theSingleton;
    }
    
    public boolean validarPermiso(List<Integer> lista_permisos, int permiso) {
        
        return lista_permisos.contains(1) || lista_permisos.contains(permiso);
        
    }
    
}
