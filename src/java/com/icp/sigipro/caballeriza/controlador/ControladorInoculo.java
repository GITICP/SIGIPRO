/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.controlador;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.caballeriza.dao.GrupoDeCaballosDAO;
import com.icp.sigipro.caballeriza.dao.InoculoDAO;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.GrupoDeCaballos;
import com.icp.sigipro.caballeriza.modelos.Inoculo;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
 * @author Walter
 */
@WebServlet(name = "ControladorInoculo", urlPatterns = {"/Caballeriza/Inoculo"})
public class ControladorInoculo extends SIGIPROServlet {

    private final int[] permisos = {1, 57, 58};
    //-----------------
    private InoculoDAO dao = new InoculoDAO();

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

        }
    };

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(57, listaPermisos);

        String redireccion = "Inoculo/Agregar.jsp";
        GrupoDeCaballosDAO gruposdao= new GrupoDeCaballosDAO();
        List<GrupoDeCaballos> listagrupos = gruposdao.obtenerGruposDeCaballosConCaballos();
        Inoculo i = new Inoculo();
        request.setAttribute("helper", HelpersHTML.getSingletonHelpersHTML());
        List<Integer> listavacia = new ArrayList<Integer>();
        request.setAttribute("inoculo", i);
        request.setAttribute("listacaballos", listavacia);
        request.setAttribute("listagrupos", listagrupos);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Inoculo/index.jsp";
        List<Inoculo> inoculos = dao.obtenerInoculos();
        request.setAttribute("listaInoculos", inoculos);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Inoculo/Ver.jsp";
        int id_inoculo = Integer.parseInt(request.getParameter("id_inoculo"));
        try {
            Inoculo i = dao.obtenerInoculo(id_inoculo);
            List<Caballo> listacaballos = dao.obtenerCaballosInoculo(id_inoculo);
            request.setAttribute("caballos", listacaballos);
            request.setAttribute("inoculo", i);
            redireccionar(request, response, redireccion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(58, listaPermisos);
        String redireccion = "Inoculo/Editar.jsp";
        int id_inoculo = Integer.parseInt(request.getParameter("id_inoculo"));
        Inoculo inoculo = dao.obtenerInoculo(id_inoculo);
        List<Caballo> listacaballos = dao.obtenerCaballosInoculo(id_inoculo);
        String nombregrupo = request.getParameter("inoculogrupo");
        GrupoDeCaballos grupo = dao.obtenerGrupoInoculo(id_inoculo);
        request.setAttribute("grupo", grupo);
        request.setAttribute("listacaballos", listacaballos);
        request.setAttribute("inoculo", inoculo);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Inoculo/Agregar.jsp";
        Inoculo i = construirObjeto(request);
        String[] ids_caballos = request.getParameterValues("inoculocaballo");
        resultado = dao.insertarInoculo(i,ids_caballos);
        
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(i.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_INOCULO, request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Inóculo agregado correctamente"));
            redireccion = "Inoculo/index.jsp";
        }
        request.setAttribute("listaInoculos", dao.obtenerInoculos());
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        boolean resultadoInoculo = false;
        boolean resultadoInoculoCaballoD = false;
        String redireccion = "Inoculo/Editar.jsp";
        Inoculo i = construirObjeto(request);
        i.setId_inoculo(Integer.parseInt(request.getParameter("id_inoculo")));
        String[] ids_caballos = request.getParameterValues("inoculocaballo");
        resultadoInoculo = dao.editarInoculo(i);
        resultadoInoculoCaballoD= dao.actualizarInoculoCaballo(Integer.parseInt(request.getParameter("id_inoculo")),ids_caballos);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(i.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_INOCULO, request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultadoInoculo && resultadoInoculoCaballoD) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Inóculo editado correctamente"));
            redireccion = "Inoculo/index.jsp";
        }
        request.setAttribute("listaInoculos", dao.obtenerInoculos());
        redireccionar(request, response, redireccion);
    }

    private Inoculo construirObjeto(HttpServletRequest request) throws SIGIPROException {
        Inoculo i = new Inoculo();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fecha;
        java.sql.Date fechaSQL;
        try {
            fecha = formatoFecha.parse(request.getParameter("fecha"));
            fechaSQL = new java.sql.Date(fecha.getTime());
            i.setFecha(fechaSQL);
        } catch (ParseException ex) {

        }
        if(request.getParameter("mnn")==""){
            i.setMnn("--");
        }
        else{
            i.setMnn(request.getParameter("mnn"));
        }
        if(request.getParameter("baa")==""){
            i.setBaa("--");
        }
        else{
            i.setBaa(request.getParameter("baa"));
        }
        if(request.getParameter("bap")==""){
            i.setBap("--");
        }
        else{
            i.setBap(request.getParameter("bap"));
        }
        if(request.getParameter("cdd")==""){
            i.setCdd("--");
        }
        else{
            i.setCdd(request.getParameter("cdd"));
        }
        if(request.getParameter("lms")==""){
            i.setLms("--");
        }
        else{
            i.setLms(request.getParameter("lms"));
        }
        if(request.getParameter("tetox")==""){
            i.setTetox("--");
        }
        else{
            i.setTetox(request.getParameter("tetox"));
        }
        if(request.getParameter("otro")==""){
            i.setOtro("--");
        }
        else{
            i.setOtro(request.getParameter("otro"));
        }
        i.setEncargado_preparacion(request.getParameter("encargado_preparacion"));
        i.setEncargado_inyeccion(request.getParameter("encargado_inyeccion"));
        return i;
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
