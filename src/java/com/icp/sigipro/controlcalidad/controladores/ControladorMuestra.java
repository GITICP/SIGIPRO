/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.MuestraDAO;
import com.icp.sigipro.controlcalidad.modelos.Muestra;
import com.icp.sigipro.controlcalidad.modelos.TipoEquipo;
import com.icp.sigipro.core.SIGIPROServlet;
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
 * @author ld.conejo
 */
@WebServlet(name = "ControladorMuestra", urlPatterns = {"/ControlCalidad/Muestra"})
public class ControladorMuestra extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {1, 560};
    //-----------------
    private final MuestraDAO dao = new MuestraDAO();

    protected final Class clase = ControladorMuestra.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("descartar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Muestra/index.jsp";
        List<Muestra> muestras = dao.obtenerMuestras();
        request.setAttribute("listaMuestras", muestras);
        request.setAttribute("helper", helper);
        redireccionar(request, response, redireccion);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postDescartar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = false;
        String ids = request.getParameter("id_muestras_descartar");
        System.out.println(ids);
        String[] id_muestras = ids.split(",");

        String fecha_descarte = request.getParameter("fecha_descarte_real");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fecha;
        java.sql.Date fecha_SQL = null;
        try {
            fecha = formatoFecha.parse(fecha_descarte);
            fecha_SQL = new java.sql.Date(fecha.getTime());
        } catch (ParseException ex) {

        }
        System.out.println(fecha_SQL);
        resultado = dao.descartarMuestra(id_muestras, fecha_SQL);
        if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Muestas descartadas correctamente"));
            //Funcion que genera la bitacora
            for (String id_muestra : id_muestras) {
                if (!id_muestra.equals("")) {
                    Muestra m = new Muestra();
                    m.setId_muestra(Integer.parseInt(id_muestra));
                    m.setFecha_descarte_real(fecha_SQL);
                    bitacora.setBitacora(m.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_MUESTRA, request.getRemoteAddr());
                }
            }
            //*----------------------------*
            this.getIndex(request, response);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Muestras no pudieron ser descartadas. Inténtelo de nuevo."));
            this.getIndex(request, response);
        }

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private TipoEquipo construirObjeto(HttpServletRequest request) {
        TipoEquipo te = new TipoEquipo();
        te.setNombre(request.getParameter("nombre"));
        te.setDescripcion(request.getParameter("descripcion"));

        return te;
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
