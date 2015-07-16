/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.controlcalidad.dao.SolicitudDAO;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.core.SIGIPROServlet;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ld.conejo, boga
 */
@WebServlet(name = "ControladorInformes", urlPatterns = {"/ControlCalidad/Informe"})
public class ControladorInformes extends SIGIPROServlet
{

    //Falta implementar
    private final int[] permisos = {1, 540};
    //-----------------
    
    private final SolicitudDAO daosolicitud = new SolicitudDAO();

    protected final Class clase = ControladorInformes.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("eliminar");
            add("editar");
            add("generar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {

        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getGenerar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(550, listaPermisos);
        String redireccion = "Informe/Generar.jsp";
        
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        
        SolicitudCC solicitud = daosolicitud.obtenerSolicitud(id_solicitud);
        
        if (solicitud != null) {
            request.setAttribute("solicitud", solicitud);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Error al obtener la información de la solicitud. Inténtelo nuevamente."));
        }
        
        redireccionar(request, response, redireccion);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<String> lista_acciones;
        if (accionHTTP.equals("get")) {
            lista_acciones = accionesGet;
        } else {
            lista_acciones = accionesPost;
        }
        if (lista_acciones.contains(accion.toLowerCase())) {
            String nombreMetodo = accionHTTP + Character.toUpperCase(accion.charAt(0)) + accion.substring(1);
            Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class
            );
            metodo.invoke(this, request, response);
        } else {
            Method metodo = clase.getDeclaredMethod(accionHTTP + "Index", HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        }
    }

    @Override
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // </editor-fold>
}
