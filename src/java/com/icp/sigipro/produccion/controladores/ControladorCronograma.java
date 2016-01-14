/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.produccion.dao.CronogramaDAO;
import com.icp.sigipro.produccion.dao.Semanas_cronogramaDAO;
import com.icp.sigipro.produccion.modelos.Cronograma;
import com.icp.sigipro.produccion.modelos.Semanas_cronograma;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorCronograma", urlPatterns = {"/Produccion/Cronograma"})
public class ControladorCronograma extends SIGIPROServlet {

    private final int[] permisos = {601, 1, 1};
    private final CronogramaDAO dao = new CronogramaDAO();
    private final Semanas_cronogramaDAO sDAO = new Semanas_cronogramaDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();

    protected final Class clase = ControladorCronograma.class;
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
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(601, listaPermisos);
       
        String redireccion = "Cronograma/Agregar.jsp";
        Cronograma ds = new Cronograma();
        request.setAttribute("cronograma", ds);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        List<Cronograma> cronogramas = new CronogramaDAO().obtenerCronogramas();
        request.setAttribute("listaCronogramas", cronogramas);
        String redireccion = "Cronograma/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Cronograma/Ver.jsp";
        int id_cronograma = Integer.parseInt(request.getParameter("id_cronograma"));
        
        try {
            Cronograma c = dao.obtenerCronograma(id_cronograma);
            request.setAttribute("cronograma", c);

        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Cronograma/Editar.jsp";
        int id_cronograma = Integer.parseInt(request.getParameter("id_cronograma"));
        Cronograma ds = dao.obtenerCronograma(id_cronograma);
        request.setAttribute("cronograma", ds);
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }


    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        boolean resultado = false;
        String redireccion = "Cronograma/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        try {
            Cronograma cronograma_nuevo = construirObjeto(request);
            
            int id_cronograma_nuevo = dao.insertarCronograma(cronograma_nuevo);
            if (id_cronograma_nuevo != 0) resultado = true;
            //Crear semanas automáticamente. crearSemanas_cronograma(startdate)
            sDAO.crearSemanas_cronograma(cronograma_nuevo.getValido_desde(),id_cronograma_nuevo);
            //Semanas Bitácora****
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            for (Semanas_cronograma semana : sDAO.obtenerSemanas_cronograma(id_cronograma_nuevo)){
                bitacora.setBitacora(semana.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SEMANAS_CRONOGRAMA, request.getRemoteAddr());
            }
            bitacora.setBitacora(cronograma_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CRONOGRAMA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Cronograma/index.jsp";
            List<Cronograma> cronogramas = new CronogramaDAO().obtenerCronogramas();
            request.setAttribute("listaCronogramas", cronogramas);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Cronograma/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        try {
            Cronograma cronograma_nuevo = construirObjeto(request);
            
            resultado = dao.editarCronograma(cronograma_nuevo);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(cronograma_nuevo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CRONOGRAMA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Cronograma/index.jsp";
            List<Cronograma> cronogramas = new CronogramaDAO().obtenerCronogramas();
            request.setAttribute("listaCronogramas", cronogramas);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Cronograma/index.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        try {
            Cronograma cronograma_a_eliminar = dao.obtenerCronograma(Integer.parseInt(request.getParameter("id_cronograma")));
            
            resultado = dao.eliminarCronograma(cronograma_a_eliminar.getId_cronograma());
            BitacoraDAO bitacora = new BitacoraDAO();
            //Borrar semanas automáticamente
            for (Semanas_cronograma semana : sDAO.obtenerSemanas_cronograma(cronograma_a_eliminar.getId_cronograma())){
                bitacora.setBitacora(semana.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SEMANAS_CRONOGRAMA, request.getRemoteAddr());
            }
            sDAO.eliminarSemanas_del_Cronograma(cronograma_a_eliminar.getId_cronograma());
            //Semanas Bitácora****
            //Funcion que genera la bitacora
            bitacora.setBitacora(cronograma_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CRONOGRAMA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Cronograma/index.jsp";
            List<Cronograma> cronogramas = new CronogramaDAO().obtenerCronogramas();
            request.setAttribute("listaCronogramas", cronogramas);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos del Modelo">
    private Cronograma construirObjeto(HttpServletRequest request) throws SIGIPROException, ParseException {
        Cronograma cronograma = new Cronograma();
        
        cronograma.setId_cronograma(Integer.parseInt(request.getParameter("id_cronograma")));
        cronograma.setNombre(request.getParameter("nombre"));
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date result = df.parse(request.getParameter("valido_desde"));
        java.sql.Date valido_desdeSQL = new java.sql.Date(result.getTime());
        cronograma.setValido_desde(valido_desdeSQL);
        
        cronograma.setObservaciones(request.getParameter("observaciones"));

        return cronograma;
    }
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
