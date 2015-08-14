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
import com.icp.sigipro.caballeriza.dao.SangriaDAO;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.GrupoDeCaballos;
import com.icp.sigipro.caballeriza.modelos.Sangria;
import com.icp.sigipro.caballeriza.modelos.SangriaAJAX;
import com.icp.sigipro.caballeriza.modelos.SangriaCaballo;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelperFechas;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
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

    private final int[] permisos = {1, 61, 62};
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
            add("sangriasajax");
            add("caballossangriaajax");
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
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Get">

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(61, listaPermisos);
        String redireccion = "Sangria/Agregar.jsp";

        GrupoDeCaballosDAO gruposdao= new GrupoDeCaballosDAO();
        UsuarioDAO usr_dao = new UsuarioDAO();
        List<GrupoDeCaballos> lista_grupos = gruposdao.obtenerGruposDeCaballosConCaballos();
        List<Usuario> lista_usuarios = usr_dao.obtenerUsuariosSeccion(6);
        request.setAttribute("usuarios_cab", lista_usuarios);
        request.setAttribute("lista_grupos", lista_grupos);
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
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void getExtraccion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(61, listaPermisos);
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
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
            redireccion = "Sangria/index.jsp";
        }

        redireccionar(request, response, redireccion);
    }

    protected void getEditarextraccion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(61, listaPermisos);
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
        validarPermiso(62, listaPermisos);
        String redireccion = "Sangria/Editar.jsp";

        int id_sangria = Integer.parseInt(request.getParameter("id_sangria"));

        Sangria sangria = dao.obtenerSangriaConCaballosDeGrupo(id_sangria);
        UsuarioDAO usr_dao = new UsuarioDAO();
        List<Usuario> lista_usuarios = usr_dao.obtenerUsuariosSeccion(6);
        request.setAttribute("usuarios_cab", lista_usuarios);
        request.setAttribute("sangria", sangria);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);
    }
    
    protected void getSangriasajax (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("application/json");
        
        PrintWriter out = response.getWriter();
        String resultado = "";
        
        try {
            List<Sangria> sangrias = dao.obtenerSangriasLALPendiente();
            
            List<SangriaAJAX> sangrias_ajax = new ArrayList<SangriaAJAX>();
            
            for (Sangria s : sangrias) {
                SangriaAJAX s_ajax = new SangriaAJAX(s);
                sangrias_ajax.add(s_ajax);
            }
            
            Gson gson = new Gson();
            resultado = gson.toJson(sangrias_ajax);
            
        } catch(SIGIPROException sig_ex) {
            // Enviar error al AJAX
        }
        
        out.print(resultado);
        
        out.flush();
    }
    
    protected void getCaballossangriaajax (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("application/json");
        
        int id_sangria = Integer.parseInt(request.getParameter("id_sangria"));
        int dia = Integer.parseInt(request.getParameter("dia"));
        
        PrintWriter out = response.getWriter();
        String resultado = "";
        
        try {
            List<Caballo> caballos = dao.obtenerCaballosSangriaDia(id_sangria, dia);
            
            Gson gson = new Gson();
            resultado = gson.toJson(caballos);
            
        } catch(SIGIPROException sig_ex) {
            // Enviar error al AJAX
        }
        
        out.print(resultado);
        
        out.flush();
        
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        String redireccion = "Sangria/Agregar.jsp";
        Sangria sangria = construirObjeto(request);

        try {
            dao.insertarSangria(sangria);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(sangria.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SANGRIA, request.getRemoteAddr());

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
        String redireccion = "Sangria/index.jsp";

        try {
            Sangria sangria = construirObjeto(request);
            sangria.setId_sangria(Integer.parseInt(request.getParameter("id_sangria")));

            dao.editarSangria(sangria);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(sangria.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SANGRIA, request.getRemoteAddr());

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
        String redireccion; // Esta línea se cambió

        String[] ids_caballos = request.getParameterValues("caballos");
        String[] ids_caballos_false = request.getParameterValues("caballos_false"); // Se agregó esta línea
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
                String observaciones = request.getParameter("observaciones_" + id_caballo); // Esta línea se cambió

                float sangre = (sangre_str.isEmpty()) ? 0.0f : Float.parseFloat(sangre_str);
                float plasma = (plasma_str.isEmpty()) ? 0.0f : Float.parseFloat(plasma_str);
                // Aquí se borró algo

                sangria_caballo.setPlasma(dia, plasma);
                sangria_caballo.setSangre(dia, sangre);
                sangria_caballo.setObservaciones(dia, observaciones); // Esta línea se cambió

                
                sangria.agregarSangriaCaballo(sangria_caballo);
                
            }
            
            // Se agregó todo este bloque.
            for (String id_caballo : ids_caballos_false) {

                SangriaCaballo sangria_caballo = new SangriaCaballo();

                Caballo caballo = new Caballo();
                caballo.setId_caballo(Integer.parseInt(id_caballo));

                sangria_caballo.setCaballo(caballo);

                String observaciones = request.getParameter("observaciones_" + id_caballo);

                sangria_caballo.setObservaciones(dia, observaciones);
                
                sangria.agregarSangriaCaballoSinParticipacion(sangria_caballo);
                
            }
        }
        catch (Exception ex) {

        }

        try {
            dao.registrarExtraccion(sangria, dia);
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(sangria.parseJSON(), Bitacora.ACCION_REGISTRAR_EXTRACCION, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SANGRIA, request.getRemoteAddr());

            request.setAttribute("mensaje", helper.mensajeDeExito("Extracción registrada correctamente."));

            // Código nuevo
            boolean volver = Boolean.parseBoolean(request.getParameter("volver"));
            
            if (volver) {
                this.getEditarextraccion(request, response);
            } else {
                request.setAttribute("lista_sangrias", dao.obtenerSangrias());
                redireccion = "Sangria/index.jsp";
                redireccionar(request, response, redireccion);
            }
            // Código nuevo
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError("Extracción no se registró correctamente."));
        }
        // Aquí se borró algo
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos modelo">
    
    private Sangria construirObjeto(HttpServletRequest request)
    {
        Sangria sangria = new Sangria();

        Usuario u = new Usuario();
        u.setId_usuario(Integer.parseInt(request.getParameter("responsable")));
        sangria.setResponsable(u);

        GrupoDeCaballos g = new GrupoDeCaballos();
        g.setId_grupo_caballo(Integer.parseInt(request.getParameter("grupo")));
        sangria.setGrupo(g);
        
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

        String[] ids_caballos = request.getParameterValues("caballos");

        if (ids_caballos != null) {
            for (String id_caballo : ids_caballos) {
                SangriaCaballo sangria_caballo = new SangriaCaballo();

                Caballo c = new Caballo();
                c.setId_caballo(Integer.parseInt(id_caballo));
                sangria_caballo.setCaballo(c);

                sangria.agregarSangriaCaballo(sangria_caballo);
            }
        }

        return sangria;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
    
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
    
    // </editor-fold>

}
