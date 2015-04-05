/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.controlador;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.caballeriza.dao.SangriaDAO;
import com.icp.sigipro.caballeriza.dao.SangriaPruebaDAO;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.Sangria;
import com.icp.sigipro.caballeriza.modelos.SangriaCaballo;
import com.icp.sigipro.caballeriza.modelos.SangriaPrueba;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelperFechas;
import com.icp.sigipro.utilidades.HelpersHTML;
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
 * @author Walter
 */
@WebServlet(name = "ControladorSangria", urlPatterns = {"/Caballeriza/Sangria"})
public class ControladorSangria extends SIGIPROServlet
{

    private final int[] permisos = {1, 59, 60};
    private final SangriaDAO dao = new SangriaDAO();
    private final HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
    
    protected final Class clase = ControladorSangria.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregar");
            add("editar");

        }
    };

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(59, listaPermisos);
        String redireccion = "Sangria/Agregar.jsp";
        
        SangriaPruebaDAO sangria_pruebas_dao = new SangriaPruebaDAO();
        List<SangriaPrueba> sangrias_prueba = sangria_pruebas_dao.obtenerSangriasPruebasLimitadoConCaballos();
        
        request.setAttribute("sangrias_prueba", sangrias_prueba);
        request.setAttribute("helper", helper);
        request.setAttribute("accion", "Agregar");
        
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Sangria/index.jsp";
        
        List<Sangria> sangrias = dao.obtenerSangrias();
        
        request.setAttribute("lista_sangrias", sangrias);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Sangria/Ver.jsp";
        try {
            
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(60, listaPermisos);
        String redireccion = "Sangria/Editar.jsp";
        
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);
    }

    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        boolean resultado = false;
        String redireccion = "SangriaPrueba/Agregar.jsp";
        Sangria sangria = construirObjeto(request);

        try {
            dao.insertarSangria(sangria);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(sangria.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SANGRIA_PRUEBA, request.getRemoteAddr());

            request.setAttribute("mensaje", helper.mensajeDeExito("Sangría agregada correctamente."));
            redireccion = "Sangria/index.jsp";
            
            request.setAttribute("lista_sangrias", dao.obtenerSangrias());
            redireccionar(request, response, redireccion);
        } catch(SIGIPROException sig_ex) {
            request.setAttribute("lista_sangrias", dao.obtenerSangrias());
            redireccionar(request, response, redireccion);
        }
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        boolean resultado = false;
        String redireccion = "EventoClinico/Editar.jsp";

        
        BitacoraDAO bitacora = new BitacoraDAO();
        //bitacora.setBitacora(c.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EVENTO_CLINICO,request.getRemoteAddr());
              
        redireccionar(request, response, redireccion);
    }

    private Sangria construirObjeto(HttpServletRequest request) throws SIGIPROException
    {
        Sangria sangria = new Sangria();
        
        sangria.setResponsable(request.getParameter("responsable"));
        
        String numero_informe_calidad = request.getParameter("num_inf_cc");
        String potencia = request.getParameter("potencia");
        if (!numero_informe_calidad.isEmpty()) {
            sangria.setNum_inf_cc(Integer.parseInt(numero_informe_calidad));
        }
        if(!potencia.isEmpty()) {
            sangria.setPotencia(Float.parseFloat(potencia));
        }
        
        SangriaPrueba sangria_prueba = new SangriaPrueba();
        int id_prueba = Integer.parseInt(request.getParameter("sangria_prueba"));
        sangria_prueba.setId_sangria_prueba(Integer.parseInt(request.getParameter("sangria_prueba")));
        sangria.setSangria_prueba(sangria_prueba);
        
        String[] ids_caballos = request.getParameterValues("caballos");
        
        for (String id_caballo : ids_caballos) {
            
            SangriaCaballo sangria_caballo = new SangriaCaballo();
            
            Caballo c = new Caballo();
            c.setId_caballo(Integer.parseInt(id_caballo));
            sangria_caballo.setCaballo(c);
            
            sangria.agregarSangriaCaballo(sangria_caballo);
            
        }
        
        return sangria;
    }

    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        List<String> lista_acciones;
        if (accionHTTP.equals("get")) {
            lista_acciones = accionesGet;
        }
        else {
            lista_acciones = accionesPost;
        }
        if (lista_acciones.contains(accion.toLowerCase())) {
            String nombreMetodo = accionHTTP + Character.toUpperCase(accion.charAt(0)) + accion.substring(1);
            Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        }
        else {
            Method metodo = clase.getDeclaredMethod(accionHTTP + "Index", HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        }
    }

    @Override
    protected int getPermiso()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
