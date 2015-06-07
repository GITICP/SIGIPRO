/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.controladores;
import com.icp.sigipro.bioterio.dao.SalidaDAO;
import com.icp.sigipro.bioterio.modelos.Salida;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelperFechas;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorSalidas", urlPatterns = {"/Bioterio/Salidas"})
public class ControladorSalidas extends SIGIPROServlet {


    private int[] permisos = {258, 208, 0};
    private int permiso_por_buscar;
    private final SalidaDAO dao = new SalidaDAO();
    private final HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
    private final String contexto = "/Bioterio/";
    private boolean especie_consultada; //true ratones

    protected final Class clase = ControladorSalidas.class;
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
            add("eliminar");
        }
    };
    protected final List<String> razones = new ArrayList<String>()
    {
        {
            add("Muerte");
            add("Donación");
            add("Venta");
            add("Sacrificado");
            add("Otra");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(permiso_por_buscar, listaPermisos);
        String redireccion = contexto + "Salidas/index.jsp";

        try {
            List<Salida> salida = dao.obtenerSalidas(especie_consultada);
            request.setAttribute("lista_salida", salida);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }

        redireccionar(request, response, redireccion);
    }

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(permiso_por_buscar, listaPermisos);
        String redireccion = contexto + "Salidas/Agregar.jsp"; 
        request.setAttribute("razones", razones);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = contexto + "Salidas/Editar.jsp";
        int id_salida = Integer.parseInt(request.getParameter("id_salida"));
        request.setAttribute("accion", "Editar");
        request.setAttribute("razones", razones);
        try {
            Salida salida = dao.obtenerSalida(id_salida);
            request.setAttribute("salida", salida);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = contexto + "Salidas/Ver.jsp";
        int id_salida = Integer.parseInt(request.getParameter("id_salida"));
        try {
            Salida salida = dao.obtenerSalida(id_salida);
            request.setAttribute("salida", salida);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = contexto + "Salidas/Agregar.jsp";
        try {
            Salida salida = construirObjeto(request);
            dao.insertarSalida(salida);
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(salida.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

            redireccion = contexto + "Salidas/index.jsp";
            request.setAttribute("mensaje", helper.mensajeDeExito("Salida Extraordinaria agregada correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("razones", razones);
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }

        try {
            List<Salida> salida = dao.obtenerSalidas(especie_consultada);
            request.setAttribute("lista_salida", salida);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("razones", razones);
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = contexto + "Salidas/Editar.jsp";
        try {
            Salida salida = construirObjeto(request);
            dao.editarSalida(salida);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(salida.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

            redireccion = contexto + "Salidas/index.jsp";
            request.setAttribute("mensaje", helper.mensajeDeExito("Salida Extraordinaria editada correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("razones", razones);
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }

        try {
            List<Salida> salida = dao.obtenerSalidas(especie_consultada);
            request.setAttribute("razones", razones);
            request.setAttribute("lista_salida", salida);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(permiso_por_buscar, listaPermisos);
        int id_salida = Integer.parseInt(request.getParameter("id_salida"));
        //boolean especie = Boolean.parseBoolean(request.getParameter("especie"));  
        String redireccion = contexto + "Salidas/index.jsp";
        try {
            dao.eliminarSalida(id_salida);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_salida, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

            request.setAttribute("mensaje", helper.mensajeDeExito("Salida Extraordinaria eliminada correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }

        try {
            List<Salida> lista_salida = dao.obtenerSalidas(especie_consultada);
            request.setAttribute("lista_salida", lista_salida);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private Salida construirObjeto(HttpServletRequest request) throws SIGIPROException
    {
        Salida salida = new Salida();
        HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
        salida.setEspecie(especie_consultada);
        String id_salida = request.getParameter("id_salida");
        if (!id_salida.isEmpty()) {
            salida.setId_salida(Integer.parseInt(id_salida));
        }
        else {
            salida.setFecha(helper_fechas.getFecha_hoy());
        }
        salida.setRazon(request.getParameter("razon"));
        salida.setObservaciones(request.getParameter("observaciones"));
        salida.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
        return salida;
    }

  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        List<String> lista_acciones;
        especie_consultada = Boolean.parseBoolean(request.getParameter("especie"));
        permisos = (especie_consultada) ? new int[]{208,0,0} : new int[]{258,0,0};
        permiso_por_buscar = (especie_consultada) ? 208 : 258;
        request.setAttribute("especie", especie_consultada);
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
    protected void validarPermiso(int permiso, List<Integer> permisosUsuario) throws AuthenticationException, NullPointerException
    {
        try {
            if (!(permisosUsuario.contains(permiso) || permisosUsuario.contains(1))) {
                throw new AuthenticationException("Usuario no tiene permisos para acceder a la acción.");
            }
        }
        catch (NullPointerException e) {
            throw new AuthenticationException("Expiró la sesión.");
        }
    }

    @Override
    protected int getPermiso()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  // </editor-fold>
   
}
