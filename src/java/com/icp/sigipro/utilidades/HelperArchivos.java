/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.utilidades;

import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.dao.DireccionArchivosDAO;
import com.icp.sigipro.seguridad.modelos.DireccionArchivos;
import java.io.File;

/**
 *
 * @author Boga
 */
public class HelperArchivos {

    private static HelperArchivos theSingleton;
    private final DireccionArchivosDAO archivos_dao = new DireccionArchivosDAO();

    private HelperArchivos() {
    }

    public static HelperArchivos getSingletonHelperArchivos() {
        if (theSingleton == null) {
            theSingleton = new HelperArchivos();
        }
        return theSingleton;
    }

    public String obtenerDireccionArchivos() {
        try {
            DireccionArchivos direccion = archivos_dao.obtenerDireccion();
            return direccion.getDireccion();
        } catch (SIGIPROException sig_ex) {
            return File.separatorChar + "SIGIPRO_DEFAULT";
        }
    }

}
