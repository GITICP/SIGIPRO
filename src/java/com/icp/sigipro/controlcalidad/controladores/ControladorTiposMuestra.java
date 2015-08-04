/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.AnalisisDAO;
import com.icp.sigipro.controlcalidad.dao.TipoMuestraDAO;
import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.controlcalidad.modelos.TipoMuestra;
import com.icp.sigipro.core.SIGIPROException;
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
 * @author Boga
 */
@WebServlet(name = "ControladorTiposMuestra", urlPatterns = {"/ControlCalidad/TiposMuestra"})
public class ControladorTiposMuestra extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {1, 314};
    //-----------------
    private final TipoMuestraDAO dao = new TipoMuestraDAO();
    private final AnalisisDAO analisisdao = new AnalisisDAO();

    protected final Class clase = ControladorTiposMuestra.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("agregar");
            add("ver");
            add("editar");
            add("eliminar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
            add("editar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "TiposMuestra/index.jsp";

        obtenerListadoCompleto(request, "No se pudo obtener el listado completo. Refresque la página y de persistir el problema notifique al administrador del sistema.", true);
        redireccionar(request, response, redireccion);
    }

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "TiposMuestra/Agregar.jsp";
        List<Analisis> analisis = analisisdao.obtenerAnalisis();

        request.setAttribute("accion", "Agregar");
        request.setAttribute("tipo_muestra", new TipoMuestra());
        request.setAttribute("listaAnalisis", analisis);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        TipoMuestra tipo_muestra = new TipoMuestra();
        String redireccion = "TiposMuestra/Ver.jsp";

        int id_tipo_muestra = obtenerId(request);

        try {
            tipo_muestra = dao.obtenerTipoDeMuestra(id_tipo_muestra);
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", sig_ex.getMessage());
        }

        request.setAttribute("tipo_muestra", tipo_muestra);
        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        TipoMuestra tipo_muestra = new TipoMuestra();
        String redireccion = "TiposMuestra/Editar.jsp";

        List<Analisis> analisis = analisisdao.obtenerAnalisis();

        int id_tipo_muestra = obtenerId(request);

        try {
            tipo_muestra = dao.obtenerTipoDeMuestra(id_tipo_muestra);
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", sig_ex.getMessage());
        }
        request.setAttribute("listaAnalisis", analisis);
        request.setAttribute("accion", "Editar");
        request.setAttribute("tipo_muestra", tipo_muestra);
        redireccionar(request, response, redireccion);
    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        int id_tipo_muestra = Integer.parseInt(request.getParameter("id_tipos_muestra"));
        boolean resultado = false;
        try {
            resultado = dao.eliminarTipoMuestra(id_tipo_muestra);
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_tipo_muestra, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_TIPOMUESTRA, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Muestra eliminado correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Muestra no pudo ser eliminado ya que tiene muestras asociados."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Muestra no pudo ser eliminado ya que tiene muestras asociados."));
            this.getIndex(request, response);
        }

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redireccion = "TiposMuestra/Agregar.jsp";
        TipoMuestra tipo_muestra = construirObjeto(request);

        try {
            dao.insertarTipoDeMuestra(tipo_muestra);
            dao.insertarTipoMuestraAnalisis(tipo_muestra);
            redireccion = "TiposMuestra/index.jsp";
            request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Muestra agregado con éxito."));
            obtenerListadoCompleto(request, "Tipo de Muestra agregado con éxito, pero no se pudo obtener el listado completo. Refresque la página.", false);
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("accion", "Agregar");
            request.setAttribute("tipo_muestra", tipo_muestra);
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }

        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = false;
        String redireccion = "TiposMuestra/Agregar.jsp";
        TipoMuestra tipo_muestra = construirObjeto(request);
        tipo_muestra.setId_tipo_muestra(obtenerId(request));

        try {
            resultado = dao.editarTipoDeMuestra(tipo_muestra);
            if (resultado) {
                dao.eliminarTipoMuestrasAnalisis(tipo_muestra.getId_tipo_muestra());
                dao.insertarTipoMuestraAnalisis(tipo_muestra);
                redireccion = "TiposMuestra/index.jsp";
                request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Muestra editado con éxito."));
                obtenerListadoCompleto(request, "Tipo de Muestra editado con éxito, pero no se pudo obtener el listado completo. Refresque la página.", false);
            }
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("accion", "Editar");
            request.setAttribute("tipo_muestra", tipo_muestra);
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }

        redireccionar(request, response, redireccion);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private TipoMuestra construirObjeto(HttpServletRequest request) {
        TipoMuestra tipo_muestra = new TipoMuestra();

        tipo_muestra.setNombre(request.getParameter("nombre"));
        tipo_muestra.setDescripcion(request.getParameter("descripcion"));
        tipo_muestra.setDias_descarte(Integer.parseInt(request.getParameter("dias_descarte")));
        tipo_muestra.setTipos_muestras_analisis(new ArrayList<Analisis>());
        String[] analisis = request.getParameterValues("analisis");

        for (String i : analisis) {
            Analisis a = new Analisis();
            a.setId_analisis(Integer.parseInt(i));
            tipo_muestra.getTipos_muestras_analisis().add(a);
        }

        return tipo_muestra;
    }

    private void obtenerListadoCompleto(HttpServletRequest request, String mensaje_error, boolean tipo_mensaje) {
        try {
            List<TipoMuestra> tipos_muestra = dao.obtenerTiposDeMuestra();
            request.setAttribute("tipos_muestra", tipos_muestra);
        } catch (SIGIPROException sig_ex) {
            String mensaje = (tipo_mensaje) ? helper.mensajeDeError(mensaje_error) : helper.mensajeDeAdvertencia(mensaje_error);
            request.setAttribute("mensaje", mensaje);
        }
    }

    private int obtenerId(HttpServletRequest request) {
        int id_tipo_muestra;
        try {
            id_tipo_muestra = Integer.parseInt(request.getParameter("id_tipo_muestra"));
        } catch (Exception p_ex) {
            id_tipo_muestra = -1;
        }
        return id_tipo_muestra;
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
