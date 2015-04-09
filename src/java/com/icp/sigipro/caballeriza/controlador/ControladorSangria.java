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
import com.icp.sigipro.serpentario.modelos.Extraccion;
import com.icp.sigipro.utilidades.HelperFechas;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
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
    private final HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();

    protected final Class clase = ControladorSangria.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
            add("extraccion");
            add("editarextraccion");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregar");
            add("editar");
            add("extraccion");
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
        int id_sangria = Integer.parseInt(request.getParameter("id_sangria"));
        try {
            Sangria sangria = dao.obtenerSangria(id_sangria);
            request.setAttribute("sangria", sangria);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        redireccionar(request, response, redireccion);
    }

    protected void getExtraccion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(60, listaPermisos);
        String redireccion = "Sangria/Extraccion.jsp";

        int id_sangria = Integer.parseInt(request.getParameter("id_sangria"));
        int dia = Integer.parseInt(request.getParameter("dia"));

        try {
            Sangria sangria = dao.obtenerSangria(id_sangria);
            request.setAttribute("helper_fechas", helper_fechas);
            request.setAttribute("sangria", sangria);
            request.setAttribute("dia", dia);
            request.setAttribute("accion", "Extraccion");
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
            redireccion = "Sangria/index.jsp";
        }

        redireccionar(request, response, redireccion);
    }

    protected void getEditarextraccion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(60, listaPermisos);
        String redireccion = "Sangria/Extraccion.jsp";

        int id_sangria = Integer.parseInt(request.getParameter("id_sangria"));
        int dia = Integer.parseInt(request.getParameter("dia"));

        try {
            Sangria sangria = dao.obtenerSangria(id_sangria);

            Method get_fecha = Sangria.class.getDeclaredMethod("getFecha_dia" + dia + "AsString", (Class<?>[]) null);

            request.setAttribute("sangria", sangria);
            request.setAttribute("editar", true);
            request.setAttribute("fecha_sangria", get_fecha.invoke(sangria, (Object[]) null));
            request.setAttribute("dia", dia);
            request.setAttribute("accion", "Extraccion");
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
            redireccion = "Sangria/index.jsp";
        }
        catch (Exception ex_inesperada) {
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Inténtelo nuevamente y de persistir, favor notificar al administrador del sistema."));
            redireccion = "Sangria/index.jsp";
        }
        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(60, listaPermisos);
        String redireccion = "Sangria/Editar.jsp";

        int id_sangria = Integer.parseInt(request.getParameter("id_sangria"));

        Sangria sangria = dao.obtenerSangriaConCaballosDePrueba(id_sangria);

        request.setAttribute("sangria", sangria);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);
    }

    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        boolean resultado = false;
        String redireccion = "Sangria/Agregar.jsp";
        Sangria sangria = construirObjeto(request);

        try {
            dao.insertarSangria(sangria);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(sangria.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SANGRIA_PRUEBA, request.getRemoteAddr());

            request.setAttribute("mensaje", helper.mensajeDeExito("Sangría agregada correctamente."));
            redireccion = "Sangria/index.jsp";

            request.setAttribute("lista_sangrias", dao.obtenerSangrias());
            redireccionar(request, response, redireccion);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("sangria", sangria);
            redireccionar(request, response, redireccion);
        }
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Sangria/index.jsp";

        try {
            Sangria sangria = construirObjeto(request);
            sangria.setId_sangria(Integer.parseInt(request.getParameter("id_sangria")));

            dao.editarSangria(sangria);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(sangria.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_EVENTO_CLINICO, request.getRemoteAddr());

            request.setAttribute("mensaje", helper.mensajeDeExito("Sangría editada correctamente."));
            List<Sangria> lista = dao.obtenerSangrias();
            request.setAttribute("lista_sangrias", lista);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }

        redireccionar(request, response, redireccion);
    }

    protected void postExtraccion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        int dia = Integer.parseInt(request.getParameter("dia"));
        Sangria sangria = new Sangria();
        sangria.setId_sangria(Integer.parseInt(request.getParameter("id_sangria")));
        String redireccion = "Sangria/Agregar.jsp";

        String[] ids_caballos = request.getParameterValues("caballos");
        String fecha = request.getParameter("fecha_extraccion");

        try {
            Method set_fecha = Sangria.class.getDeclaredMethod("setFecha_dia" + dia, Date.class);
            set_fecha.invoke(sangria, helper_fechas.formatearFecha(fecha));

            for (String id_caballo : ids_caballos) {

                SangriaCaballo sangria_caballo = new SangriaCaballo();

                Caballo caballo = new Caballo();
                caballo.setId_caballo(Integer.parseInt(id_caballo));

                sangria_caballo.setCaballo(caballo);

                String sangre_str = request.getParameter("sangre_" + id_caballo);
                String plasma_str = request.getParameter("plasma_" + id_caballo);
                String lal_str = request.getParameter("lal_" + id_caballo);

                float sangre = (sangre_str.isEmpty()) ? 0.0f : Float.parseFloat(sangre_str);
                float plasma = (plasma_str.isEmpty()) ? 0.0f : Float.parseFloat(plasma_str);
                float lal = (lal_str.isEmpty()) ? 0.0f : Float.parseFloat(lal_str);

                sangria_caballo.setPlasma(dia, plasma);
                sangria_caballo.setSangre(dia, sangre);
                sangria_caballo.setLal(dia, lal);

                sangria.agregarSangriaCaballo(sangria_caballo);
            }
        }
        catch (Exception ex) {

        }

        try {
            dao.registrarExtraccion(sangria, dia);

            request.setAttribute("mensaje", helper.mensajeDeExito("Extracción registrada correctamente."));
            request.setAttribute("lista_sangrias", dao.obtenerSangrias());
            redireccion = "Sangria/index.jsp";
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Extracción no se registró correctamente."));
        }
        redireccionar(request, response, redireccion);
    }

    private Sangria construirObjeto(HttpServletRequest request)
    {
        Sangria sangria = new Sangria();

        sangria.setResponsable(request.getParameter("responsable"));

        String numero_informe_calidad = request.getParameter("num_inf_cc");
        String potencia = request.getParameter("potencia");
        String volumen_plasma_total = request.getParameter("volumen_plasma");

        if (!numero_informe_calidad.isEmpty()) {
            sangria.setNum_inf_cc(Integer.parseInt(numero_informe_calidad));
        }
        if (!potencia.isEmpty()) {
            sangria.setPotencia(Float.parseFloat(potencia));
        }
        if (!volumen_plasma_total.isEmpty()) {
            sangria.setVolumen_plasma_total(Float.parseFloat(volumen_plasma_total));
        }

        SangriaPrueba sangria_prueba = new SangriaPrueba();
        String sang_prueba = request.getParameter("sangria_prueba");
        sangria_prueba.setId_sangria_prueba(Integer.parseInt(sang_prueba));
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
