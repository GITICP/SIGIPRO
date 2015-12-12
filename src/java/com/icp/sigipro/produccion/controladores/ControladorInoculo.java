/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.controladores;

import com.icp.sigipro.produccion.modelos.Inoculo;
import com.icp.sigipro.produccion.dao.InoculoDAO;
import com.icp.sigipro.produccion.dao.Veneno_ProduccionDAO;
import com.icp.sigipro.produccion.modelos.Veneno_Produccion;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorInoculo", urlPatterns = {"/Produccion/Inoculo"})
public class ControladorInoculo extends SIGIPROServlet {


    private final int[] permisos = {604, 1, 1};
    private final InoculoDAO dao = new InoculoDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();
    
    protected final Class clase = ControladorInoculo.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
            add("editar");
            add("eliminar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(604, listaPermisos);
        
        HttpSession sesion = request.getSession();
        String nombre_usr = (String) sesion.getAttribute("usuario");
        int id_usuario = dao_us.obtenerIDUsuario(nombre_usr);
        Usuario us = dao_us.obtenerUsuario(id_usuario);
        List<Usuario> usuarios = dao_us.obtenerUsuarios(us);

        List<Veneno_Produccion> venenos = new Veneno_ProduccionDAO().obtenerVenenos_Produccion();
        
        request.setAttribute("usuarios", usuarios);
        request.setAttribute("venenos", venenos);
        
        String redireccion = "Inoculo/Agregar.jsp";
        Inoculo ds = new Inoculo();
        request.setAttribute("inoculo", ds);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Inoculo/index.jsp";
        List<Inoculo> inoculos;
        try {
            inoculos = dao.obtenerInoculos();
            request.setAttribute("listaInoculos", inoculos);
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<String> lista_acciones;
        System.out.println("Accion HTTP = "+accionHTTP);
        System.out.println("Accion = "+accion);
        if (accionHTTP.equals("get")) {
            lista_acciones = accionesGet;
        } else {
            lista_acciones = accionesPost;
        }
        if (lista_acciones.contains(accion.toLowerCase())) {
            String nombreMetodo = accionHTTP + Character.toUpperCase(accion.charAt(0)) + accion.substring(1);
            Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class);
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
