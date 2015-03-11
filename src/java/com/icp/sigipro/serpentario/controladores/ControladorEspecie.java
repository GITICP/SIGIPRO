/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.bodegas.modelos.SubBodega;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.serpentario.dao.EspecieDAO;
import com.icp.sigipro.serpentario.modelos.Especie;
import com.icp.sigipro.utilidades.HelpersHTML;
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
 * @author ld.conejo
 */
@WebServlet(name = "ControladorEspecie", urlPatterns = {"/Serpentario/Especie"})
public class ControladorEspecie extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {1, 1, 1};
    //-----------------
    private EspecieDAO dao = new EspecieDAO();

    protected final Class clase = ControladorEspecie.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("agregar");
            add("eliminar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregar");
        }
    };

  // <editor-fold defaultstate="collapsed" desc="Métodos Get">
  
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        //No esta implementado
        validarPermiso(11, listaPermisos);
        //--------------------
        String redireccion = "Especie/Agregar.jsp";
        Especie e = new Especie();
        request.setAttribute("especie", e);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //No esta implementado
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        //--------------------
        String redireccion = "Especie/index.jsp";
        List<Especie> especies = dao.obtenerEspecies();
        request.setAttribute("listaEspecies", especies);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //No esta implementado
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        //--------------------
        String redireccion = "Especie/Ver.jsp";
        int id_especie = Integer.parseInt(request.getParameter("id_especie"));
        try {
            Especie e = dao.obtenerEspecie(id_especie);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //No esta implementado
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(12, listaPermisos);
        //--------------------
        String redireccion = "Especie/Editar.jsp";
        int id_especie = Integer.parseInt(request.getParameter("id_especie"));
        Especie especie = dao.obtenerEspecie(id_especie);
        // Obtener todo y asociaciones restantes
        // Setear
        request.setAttribute("especie", especie);
        request.setAttribute("accion", "Editar");
    }
    // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        System.out.println("NO SIRVO");
        String redireccion = "Especie/Agregar.jsp";
        Especie e = construirObjeto(request);
        resultado = dao.insertarEspecie(e);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(e.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_ESPECIE,request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        request.setAttribute("mensaje", helper.mensajeDeExito("Especie de Serpiente agregada correctamente"));
        if (resultado){
            redireccion = "Especie/index.jsp";
        }
        request.setAttribute("listaEspecies", dao.obtenerEspecies());
        redireccionar(request, response, redireccion);
    }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
    private Especie construirObjeto(HttpServletRequest request) {
        Especie e = new Especie();
        e.setGenero(request.getParameter("genero"));
        e.setEspecie(request.getParameter("especie"));

        return e;
    }
  
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
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
  protected int getPermiso()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
  // </editor-fold>
}
