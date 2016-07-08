/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.controlador;

import com.google.gson.Gson;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.caballeriza.dao.GrupoDeCaballosDAO;
import com.icp.sigipro.caballeriza.dao.SangriaPruebaDAO;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.GrupoDeCaballos;
import com.icp.sigipro.caballeriza.modelos.SangriaPrueba;
import com.icp.sigipro.caballeriza.modelos.SangriaPruebaAJAX;
import com.icp.sigipro.caballeriza.modelos.SangriaPruebaCaballo;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Walter
 */
@WebServlet(name = "ControladorSangriaPrueba", urlPatterns = {"/Caballeriza/SangriaPrueba"})
public class ControladorSangriaPrueba extends SIGIPROServlet {

    private final int[] permisos = {1, 59, 60};
    private final SangriaPruebaDAO dao = new SangriaPruebaDAO();

    private final UsuarioDAO usr_dao = new UsuarioDAO();
    private final GrupoDeCaballosDAO gruposdao = new GrupoDeCaballosDAO();

    protected final Class clase = ControladorSangriaPrueba.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("sangrias_pruebas_ajax");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
        }
    };

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(59, listaPermisos);

        String redireccion = "SangriaPrueba/Agregar.jsp";
        SangriaPrueba sp = new SangriaPrueba();
        List<Usuario> lista_usuarios = usr_dao.obtenerUsuariosSeccion(6);
        List<GrupoDeCaballos> lista_grupos = gruposdao.obtenerGruposDeCaballosConCaballos();
        request.setAttribute("lista_grupos", lista_grupos);
        request.setAttribute("usuarios_cab", lista_usuarios);
        request.setAttribute("helper", helper);
        request.setAttribute("sangria_prueba", sp);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "SangriaPrueba/index.jsp";
        List<SangriaPrueba> sangriasprueba = dao.obtenerSangriasPruebas();
        request.setAttribute("listaSangriasPrueba", sangriasprueba);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "SangriaPrueba/Ver.jsp";
        int id_sangria_prueba = Integer.parseInt(request.getParameter("id_sangria_prueba"));
        try {
            SangriaPrueba sp = dao.obtenerSangriaPrueba(id_sangria_prueba);
            request.setAttribute("sangriap", sp);
            redireccionar(request, response, redireccion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    protected void getSangrias_pruebas_ajax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("application/json");
        
        PrintWriter out = response.getWriter();
        String resultado = "";
        
        try {
            List<SangriaPruebaAJAX> sangrias_pruebas_ajax = dao.obtenerSangriasPruebaPendiente();
            
            Gson gson = new Gson();
            resultado = gson.toJson(sangrias_pruebas_ajax);
            
        } catch(SIGIPROException sig_ex) {
            // Enviar error al AJAX
        }
        
        out.print(resultado);
        
        out.flush();
        
    }

    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        String redireccion = "SangriaPrueba/Agregar.jsp";
        SangriaPrueba sp = construirObjeto(request);
        
        String[] ids_caballos = request.getParameterValues("caballos");

        List<SangriaPruebaCaballo> informacion_caballos = new ArrayList<>();

        for (String id_caballo : ids_caballos) {

            SangriaPruebaCaballo informacion_caballo = new SangriaPruebaCaballo();

            Caballo c = new Caballo();
            c.setId_caballo(Integer.parseInt(id_caballo));
            informacion_caballo.setCaballo(c);

            informacion_caballos.add(informacion_caballo);
        }
        
        sp.setLista_sangrias_prueba_caballo(informacion_caballos);

        try {
            dao.insertarSangriaPrueba(sp);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(sp.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SANGRIA_PRUEBA, request.getRemoteAddr());

            request.setAttribute("mensaje", helper.mensajeDeExito("Sangría de prueba agregada correctamente."));
            redireccion = "SangriaPrueba/index.jsp";

            request.setAttribute("listaSangriasPrueba", dao.obtenerSangriasPruebas());
            redireccionar(request, response, redireccion);
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("listaSangriasPrueba", dao.obtenerSangriasPruebas());
            redireccionar(request, response, redireccion);
        }

    }

    private SangriaPrueba construirObjeto(HttpServletRequest request) throws SIGIPROException {
        SangriaPrueba sp = new SangriaPrueba();
        GrupoDeCaballos g = new GrupoDeCaballos();
        int id_grupo;
        
        try {
            Date fecha;
            fecha = helper_fechas.formatearFecha(request.getParameter("fecha_extraccion"));
            sp.setFecha(fecha);
            id_grupo = Integer.parseInt(request.getParameter("grupo"));
            g.setId_grupo_caballo(id_grupo);
            sp.setGrupo(g);
        } catch (ParseException p_ex) {

        }

        Usuario u = new Usuario();
        u.setIdUsuario(Integer.parseInt(request.getParameter("responsable")));
        sp.setUsuario(u);
        return sp;
    }

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
