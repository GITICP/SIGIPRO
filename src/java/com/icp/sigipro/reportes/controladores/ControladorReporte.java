/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.controladores;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.icp.sigipro.configuracion.dao.SeccionDAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.reportes.dao.ReporteDAO;
import com.icp.sigipro.reportes.modelos.BuilderParametro;
import com.icp.sigipro.reportes.modelos.ObjetoAjaxReporte;
import com.icp.sigipro.reportes.modelos.Parametro;
import com.icp.sigipro.reportes.modelos.Reporte;
import com.icp.sigipro.seguridad.modelos.Seccion;
import com.icp.sigipro.utilidades.ExcelWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Boga
 */
@WebServlet(name = "ControladorReporte", urlPatterns = {"/Reportes/Reportes"})
public class ControladorReporte extends SIGIPROServlet {

    private final int[] permisos = {1501, 0, 0};
    private final ReporteDAO dao = new ReporteDAO();
    private final SeccionDAO seccion_dao = new SeccionDAO();

    protected final Class clase = ControladorReporte.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("configurarreporte");
            add("ajaxobjetos");
            add("ajaxdatos");
            add("ajaxexcel");
            add("ver");
            add("permisos");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("ajaxagregar");
            add("permisos");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        List<Integer> permisos_usuario = getPermisosUsuario(request);
        
        String redireccion = "Reportes/index.jsp";
        try {
            List<Reporte> reportes = dao.obtenerReportes( esAdminOTienePermiso(permisos_usuario), this.getIdUsuario(request) );
            request.setAttribute("reportes", reportes);
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }
        redireccionar(request, response, redireccion);

    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        
        String redireccion = "Reportes/Ver.jsp";

        Reporte reporte = new Reporte();
        int id_reporte = Integer.parseInt(request.getParameter("id_reporte"));
        
        try {
            this.validarAcceso(this.getIdUsuario(request), id_reporte, getPermisosUsuario(request));
        } catch(SIGIPROException sig_ex) {
            throw new SIGIPROException(sig_ex.getMessage(), "/index.jsp");
        }

        try {
            reporte = dao.obtenerReporte(id_reporte);
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }

        request.setAttribute("reporte", reporte);

        redireccionar(request, response, redireccion);

    }

    protected void getPermisos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermisosMultiple(permisos, request);
        String redireccion = "Reportes/FormularioPermisos.jsp";

        Reporte reporte;
        int id_reporte = Integer.parseInt(request.getParameter("id_reporte"));
        List<Integer> usuarios = new ArrayList<>();

        try {
            List<Seccion> secciones = seccion_dao.obtenerSeccionesConUsuarios();
            reporte = dao.obtenerReporte(id_reporte);
            usuarios = dao.obtenerIdsUsuariosConPermiso(id_reporte);
            request.setAttribute("secciones", secciones);
            request.setAttribute("reporte", reporte);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("accion", "permisos");
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }

        redireccionar(request, response, redireccion);

    }

    protected void getConfigurarreporte(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String redireccion = "Reportes/Agregar.jsp";

        Reporte r = new Reporte();
        List<com.icp.sigipro.configuracion.modelos.Seccion> secciones = seccion_dao.obtenerSecciones();

        request.setAttribute("reporte", r);
        request.setAttribute("secciones", secciones);
        request.setAttribute("accion", "Agregar");  

        redireccionar(request, response, redireccion);

    }

    protected void getAjaxobjetos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        String tabla = request.getParameter("tabla");
        String resultado = "";

        try {
            List<ObjetoAjaxReporte> lista = dao.obtenerObjetos(tabla);
            Gson gson = new Gson();
            resultado = gson.toJson(lista);
        } catch (SIGIPROException sig_ex) {

        }

        PrintWriter out = response.getWriter();
        out.print(resultado);
        out.flush();

    }

    protected void getAjaxdatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));

        Reporte reporte = new Reporte();

        try {
            int id_reporte = Integer.parseInt(request.getParameter("id_reporte"));
            reporte = dao.obtenerReporte(id_reporte);
            obtenerValoresParametros(reporte, request);
            boolean para_grafico = Boolean.parseBoolean(request.getParameter("para-grafico"));
            dao.obtenerDatos(reporte, writer, para_grafico);
        } catch (SIGIPROException sig_ex) {

        }

        writer.close();
        response.getOutputStream().flush();
    }
    
    protected void getAjaxexcel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");       

        Reporte reporte;

        try {
            int id_reporte = Integer.parseInt(request.getParameter("id_reporte"));
            reporte = dao.obtenerReporte(id_reporte);
            response.setHeader("Content-Disposition", "attachment; filename=Reporte de " + reporte.getNombre() + " " + helper_fechas.getFecha_hoyAsString().replaceAll("/", "-") + ".xlsx");
            obtenerValoresParametros(reporte, request);
            ExcelWriter w = dao.obtenerDatosExcel(reporte);
            w.getArchivo().write(response.getOutputStream());
            w.cerrarArchivo();
        } catch (SIGIPROException sig_ex) {

        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAjaxagregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermiso(0, request);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));

        Reporte reporte = new Reporte();

        reporte.setConsulta(request.getParameter("codigo"));
        reporte.setNombre(request.getParameter("nombre"));
        reporte.setDescripcion(request.getParameter("descripcion"));
        reporte.setUrl_js(request.getParameter("url_js"));

        Seccion s = new Seccion();
        s.setId_seccion(Integer.parseInt(request.getParameter("id_seccion")));
        reporte.setSeccion(s);

        construirParametros(request, reporte, true);

        try {
            dao.probarEInsertarReporte(reporte, writer);
        } catch (SIGIPROException sig_ex) {

        }

        writer.close();
        response.getOutputStream().flush();
    }

    protected void postPermisos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermiso(1501, request);
        String redireccion = "Reportes/index.jsp";

        Reporte reporte = new Reporte();
        reporte.setId_reporte(Integer.parseInt(request.getParameter("id_reporte")));

        String[] ids = request.getParameterValues("usuarios");

        try {
            dao.insertarPermisos(reporte, ids);
            List<Reporte> reportes = dao.obtenerReportes(false, 0);
            request.setAttribute("reportes", reportes);
            request.setAttribute("mensaje", helper.mensajeDeExito("Permisos actualizados correctamente"));
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }

        redireccionar(request, response, redireccion);

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private void construirParametros(HttpServletRequest request, Reporte reporte, boolean primera_vez) {
        Map<String, String[]> mapa = request.getParameterMap();
        int contador = 1;
        BuilderParametro builder_param = new BuilderParametro();

        while (contador != mapa.size()) {
            Parametro p = builder_param.crearParametro(request, contador);
            if (p != null) {
                reporte.agregarParametro(p, primera_vez); 
            } else {
                break;
            }
            contador++;
        }
    }

    private void obtenerValoresParametros(Reporte reporte, HttpServletRequest request) {

        for (Parametro p : reporte.getParametros()) {
            p.setValorRequest(request);
        }

    }
    
    protected void validarAcceso(int id_usuario, int id_reporte, List<Integer> listaPermisos) throws AuthenticationException, SIGIPROException {
        if ( esAdminOTienePermiso(listaPermisos) ) {
            dao.validarAcceso(id_usuario, id_reporte);
        }
    }
    
    private boolean esAdminOTienePermiso(List<Integer> listaPermisos) {
        return !(listaPermisos.contains(1) || listaPermisos.contains(1501) || listaPermisos.contains(9999));
    }

    //</editor-fold>
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
