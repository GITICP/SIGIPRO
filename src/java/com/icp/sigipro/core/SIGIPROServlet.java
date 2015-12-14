/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bodegas.controladores.ControladorSubBodegas;
import com.icp.sigipro.utilidades.HelperArchivos;
import com.icp.sigipro.utilidades.HelperFechas;
import com.icp.sigipro.utilidades.HelperPermisos;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.sasl.AuthenticationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Boga
 */
@WebServlet(name = "SIGIPROServlet", urlPatterns = {"/SIGIPROServlet"})
public abstract class SIGIPROServlet extends HttpServlet {

    protected final HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
    protected final BitacoraDAO bitacora = new BitacoraDAO();
    protected final HelperPermisos helper_permisos = HelperPermisos.getSingletonHelperPermisos();
    protected final HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
    protected final HelperArchivos helper_archivos = HelperArchivos.getSingletonHelperArchivos();
    protected List<FileItem> parametros;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesarSolicitud(request, response, "get");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        procesarSolicitud(request, response, "post");
    }

    protected void procesarSolicitud(HttpServletRequest request, HttpServletResponse response, String accionHTTP)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        request.setAttribute("helper_permisos", helper_permisos);
        request.setAttribute("helper_fechas", helper_fechas);
        if (accion == null) {
            accion = "index";
        }
        if (ServletFileUpload.isMultipartContent(request)) {
            accion = "agregareditar";
        }
        try {
            ejecutarAccion(request, response, accion, accionHTTP);
        } catch (InvocationTargetException ex) {
            try {
                throw ex.getCause();
            }
            catch (AuthenticationException auth) {
                RequestDispatcher vista = request.getRequestDispatcher("/Inicio");
                vista.forward(request, response);
            } catch (SIGIPROException sigipro) {
                request.setAttribute("mensaje", helper.mensajeDeError(sigipro.getMessage()));
                RequestDispatcher vista = request.getRequestDispatcher(sigipro.getRedireccion());
                vista.forward(request, response);
            } catch (Throwable ex1) {
                ex1.printStackTrace();
                Logger.getLogger(ControladorSubBodegas.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (NoSuchMethodException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    protected abstract int getPermiso();

    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected void validarPermiso(int permiso, List<Integer> permisosUsuario) throws AuthenticationException, NullPointerException {
        try {
            if (!(permisosUsuario.contains(permiso) || permisosUsuario.contains(1))) {
                throw new AuthenticationException("Usuario no tiene permisos para acceder a la acción.");
            }
        } catch (NullPointerException e) {
            throw new AuthenticationException("Expiró la sesión.");
        }
    }

    protected void validarPermiso(int permiso, HttpServletRequest request) throws AuthenticationException, NullPointerException {
        try {
            List<Integer> permisosUsuario = this.getPermisosUsuario(request);
            if (!(permisosUsuario.contains(permiso) || permisosUsuario.contains(1))) {
                throw new AuthenticationException("Usuario no tiene permisos para acceder a la acción.");
            }
        } catch (NullPointerException e) {
            throw new AuthenticationException("Expiró la sesión.");
        }
    }

    protected void validarPermisos(int[] permisos, List<Integer> permisosUsuario) throws AuthenticationException {
        try {
            if (!(permisosUsuario.contains(permisos[0]) || permisosUsuario.contains(permisos[1]) || permisosUsuario.contains(permisos[2]) || permisosUsuario.contains(1))) {
                throw new AuthenticationException("Usuario no tiene permisos para acceder a la acción.");
            }
        } catch (NullPointerException e) {
            throw new AuthenticationException("Expiró la sesión.");
        }
    }

    protected void validarPermisosMultiple(int[] permisos, HttpServletRequest request) throws AuthenticationException {

        List<Integer> lista_permisos = this.getPermisosUsuario(request);
        boolean incluido = false;

        try {
            if (!lista_permisos.contains(1)) {
                for (int i = 0; i < permisos.length; i++) {
                    if (lista_permisos.contains(permisos[i])) {
                        incluido = true;
                        break;
                    }
                }
            } else {
                incluido = true;
            }

            if (!incluido) {
                throw new AuthenticationException("Usuario no tiene permisos para acceder a la acción.");
            }
        } catch (NullPointerException e) {
            throw new AuthenticationException("Expiró la sesión.");
        }

    }

    protected boolean validarPermiso(List<Integer> permisosUsuario) throws AuthenticationException {
        try {
            return permisosUsuario.contains(getPermiso()) || permisosUsuario.contains(1);
        } catch (NullPointerException e) {
            throw new AuthenticationException("Expiró la sesión.");
        }
    }

    protected boolean verificarPermiso(Integer p, List<Integer> permisosUsuario) throws AuthenticationException {
        try {
            return permisosUsuario.contains(p) || permisosUsuario.contains(1);
        } catch (NullPointerException e) {
            throw new AuthenticationException("Expiró la sesión.");
        }
    }

    protected void redireccionar(HttpServletRequest request, HttpServletResponse response, String redireccion) throws ServletException, IOException {
        RequestDispatcher vista = request.getRequestDispatcher(redireccion);
        vista.forward(request, response);
    }

    protected List<Integer> getPermisosUsuario(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        return (List<Integer>) sesion.getAttribute("listaPermisos");
    }

    protected String obtenerParametro(String nombre_parametro) {
        FileItem resultado = null;
        for (FileItem file_item : parametros) {
            if (file_item.getFieldName().equals(nombre_parametro)) {
                resultado = file_item;
                break;
            }
        }
        String valor;
        if (resultado != null) {
            try {
                valor = resultado.getString("UTF-8").trim();
            } catch (UnsupportedEncodingException ex) {
                valor = resultado.getString();
            }
            parametros.remove(resultado);
        } else {
            valor = "";
        }
        return valor;
    }

    protected FileItem obtenerParametroFileItem(String nombre_parametro) {
        FileItem resultado = null;
        for (FileItem file_item : parametros) {
            if (file_item.getFieldName().equals(nombre_parametro)) {
                resultado = file_item;
                break;
            }
        }
        if (resultado != null) {
            parametros.remove(resultado);
        }
        return resultado;
    }

    protected String[] obtenerParametros(String nombre_parametro) {
        List<FileItem> lista_items = new ArrayList<FileItem>();
        List<String> resultado = new ArrayList<String>();
        for (FileItem file_item : parametros) {
            if (file_item.getFieldName().equals(nombre_parametro)) {
                lista_items.add(file_item);
                try {
                    resultado.add(file_item.getString("UTF-8").trim());
                } catch (UnsupportedEncodingException ex) {
                    resultado.add(file_item.getString());
                }
            }
        }

        if (!lista_items.isEmpty()) {
            for (FileItem item : lista_items) {
                parametros.remove(item);
            }
        }
        return resultado.toArray(new String[]{});
    }

    protected int getIdUsuario(HttpServletRequest request) throws AuthenticationException {
        try {
            HttpSession sesion = request.getSession();
            return (int) sesion.getAttribute("idusuario");
        } catch (NullPointerException ex) {
            throw new AuthenticationException("Expiró la sesión");
        }

    }
}
