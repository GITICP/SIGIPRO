/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.controlador;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.caballeriza.dao.CaballoDAO;
import com.icp.sigipro.caballeriza.dao.GrupoDeCaballosDAO;
import com.icp.sigipro.caballeriza.dao.InoculoDAO;
import com.icp.sigipro.caballeriza.dao.SangriaPruebaDAO;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.SangriaPrueba;
import com.icp.sigipro.caballeriza.modelos.GrupoDeCaballos;
import com.icp.sigipro.caballeriza.modelos.Inoculo;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
    private SangriaPruebaDAO dao = new SangriaPruebaDAO();

    protected final Class clase = ControladorSangriaPrueba.class;
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

        }
    };

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(59, listaPermisos);

        String redireccion = "SangriaPrueba/Agregar.jsp";
        SangriaPrueba sp = new SangriaPrueba();
        InoculoDAO inoculodao = new InoculoDAO();
        List<Inoculo> listainoculos = inoculodao.obtenerInoculos();
        CaballoDAO caballodao = new CaballoDAO();
        List<Caballo> listacaballos = caballodao.obtenerCaballos();
        request.setAttribute("helper", HelpersHTML.getSingletonHelpersHTML());
        request.setAttribute("sangriap", sp);
        request.setAttribute("listacaballos", listacaballos);
        request.setAttribute("listainoculos", listainoculos);
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
            List<Caballo> listacaballos = dao.obtenerCaballosSangriaP(id_sangria_prueba);
            request.setAttribute("caballos", listacaballos);
            request.setAttribute("sangriap", sp);
            redireccionar(request, response, redireccion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(60, listaPermisos);
        String redireccion = "EventoClinico/Editar.jsp";
        int id_evento = Integer.parseInt(request.getParameter("id_evento"));
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);
    }

    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "SangriaPrueba/Agregar.jsp";
        SangriaPrueba sp = construirObjeto(request);
        String caballos = request.getParameter("caballos");
        
        //List<SerpientesExtraccion> serpientesextraccion = dao.parsearSerpientesExtraccion(caballos, id_extraccion);
        String[] ids_caballos=caballos.split("#r#");
        int tam=ids_caballos.length;
                //request.getParameterValues("caballos");
        resultado = dao.insertarSangriaPrueba(sp, ids_caballos);
        int id_sangria_prueba = dao.obtenerIdSangriaPrueba(request.getParameter("muestra"));
        this.datosExtrasSangriaPrueba(request, ids_caballos,id_sangria_prueba);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(sp.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SANGRIA_PRUEBA, request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Evento Clínico agregado correctamente"));
            redireccion = "SangriaPrueba/index.jsp";
        }
        request.setAttribute("listaSangriasPrueba", dao.obtenerSangriasPruebas());
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "EventoClinico/Editar.jsp";
        //EventoClinico c = construirObjeto(request);
        //c.setId_evento(Integer.parseInt(request.getParameter("id_evento")));
        //resultado = dao.editarEventoClinico(c);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        //bitacora.setBitacora(c.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EVENTO_CLINICO,request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Evento Clínico editado correctamente"));
            redireccion = "EventoClinico/index.jsp";
        }
        //request.setAttribute("listaEventosClinicos", dao.obtenerEventosClinicos());
        redireccionar(request, response, redireccion);
    }

    private SangriaPrueba construirObjeto(HttpServletRequest request) throws SIGIPROException {
        SangriaPrueba sp = new SangriaPrueba();
        InoculoDAO inoculodao = new InoculoDAO();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fecha_recepcion;
        java.util.Date fecha_informe;
        java.sql.Date fecha_recepcionSQL;
        java.sql.Date fecha_informeSQL;
        try {
            fecha_recepcion = formatoFecha.parse(request.getParameter("fecha_recepcion_muestra"));
            fecha_recepcionSQL = new java.sql.Date(fecha_recepcion.getTime());
            sp.setFecha_recepcion_muestra(fecha_recepcionSQL);
            fecha_informe = formatoFecha.parse(request.getParameter("fecha_informe"));
            fecha_informeSQL = new java.sql.Date(fecha_informe.getTime());
            sp.setFecha_informe(fecha_informeSQL);            
        } catch (ParseException ex) {

        }
        sp.setMuestra(request.getParameter("muestra"));
        sp.setNum_solicitud(Integer.parseInt(request.getParameter("num_solicitud")));
        sp.setNum_informe(Integer.parseInt(request.getParameter("num_informe")));
        sp.setResponsable(request.getParameter("responsable"));
        sp.setInoculo(inoculodao.obtenerInoculo(Integer.parseInt(request.getParameter("inoculo"))));
        return sp;
    }
    private boolean datosExtrasSangriaPrueba(HttpServletRequest request, String[] ids_caballos, int id_sangria_prueba) {
        boolean resultado = true;
        List<String> list = new LinkedList<String>(Arrays.asList(ids_caballos)); 
        list.add(0, ids_caballos[0]); 
        ids_caballos = list.toArray(new String[list.size()]);
        
        for (String id_caballo : ids_caballos) {
            String hematrocito = request.getParameter("hematrocito_" + id_caballo);
            String hemoglobina = request.getParameter("hemoglobina_" + id_caballo);
            System.out.println(id_caballo);
            float hematrocitop=0;
            float hemoglobinap=0;
            if (!hematrocito.equals("") || !hemoglobina.equals("")) {
                if(!hematrocito.equals("")){
                     hematrocitop = Float.parseFloat(hematrocito);
                }
                if(!hemoglobina.equals("")){
                     hemoglobinap = Float.parseFloat(hemoglobina);
                }
            }
            boolean editarSangriaPruebaCaballo = dao.editarSangriaPruebaCaballo(id_sangria_prueba,Integer.parseInt(id_caballo),hematrocitop,hemoglobinap);
        }
        return resultado;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
