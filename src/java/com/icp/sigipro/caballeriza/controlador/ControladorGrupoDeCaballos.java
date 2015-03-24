/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.controlador;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.caballeriza.dao.GrupoDeCaballosDAO;
import com.icp.sigipro.caballeriza.modelos.GrupoDeCaballos;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Walter
 */
@WebServlet(name = "ControladorGrupoDeCaballos", urlPatterns = {"/ControladorGrupoDeCaballos"})
public class ControladorGrupoDeCaballos extends SIGIPROServlet {

    //Falta implementar
    //private final int[] permisos = {1, 43, 44, 45};
    //-----------------
    private GrupoDeCaballosDAO dao = new GrupoDeCaballosDAO();

    protected final Class clase = ControladorGrupoDeCaballos.class;
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

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //List<Integer> listaPermisos = getPermisosUsuario(request);
        //validarPermiso(43, listaPermisos);

        String redireccion = "GrupoDeCaballos/Agregar.jsp";
        GrupoDeCaballos g = new GrupoDeCaballos();
        request.setAttribute("helper", HelpersHTML.getSingletonHelpersHTML());
        request.setAttribute("grupodecaballos", g);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //List<Integer> listaPermisos = getPermisosUsuario(request);
        //validarPermisos(permisos, listaPermisos);
        String redireccion = "GrupoDeCaballos/index.jsp";
        List<GrupoDeCaballos> grupos = dao.obtenerGruposDeCaballos();
        request.setAttribute("listaGrupos", grupos);
        redireccionar(request, response, redireccion);
    }    

    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = false;
        String redireccion = "GrupoDeCaballos/Agregar.jsp";
        GrupoDeCaballos g = construirObjeto(request);

       // System.out.println(request.getParameter("imagen2").getBytes());

        resultado = dao.insertarGrupoDeCaballos(g);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        //bitacora.setBitacora(c.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CABALLO, request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Grupo de Caballos agregado correctamente"));
            redireccion = "GrupoDeCaballos/index.jsp";
        }
        //request.setAttribute("listaCaballos", dao.obtenerCaballos());
        redireccionar(request, response, redireccion);
    }

    private GrupoDeCaballos construirObjeto(HttpServletRequest request) {
        GrupoDeCaballos g = new GrupoDeCaballos();
        
        g.setNombre(request.getParameter("nombre"));
        g.setDescripcion(request.getParameter("descripcion"));
        return g;
    } 

  @Override
  protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
  {
      List<String> lista_acciones;
      if (accionHTTP.equals("get")){
          lista_acciones = accionesGet; 
      } else {
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
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
