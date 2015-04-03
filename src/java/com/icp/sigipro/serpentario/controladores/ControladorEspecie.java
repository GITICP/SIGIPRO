/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.serpentario.dao.EspecieDAO;
import com.icp.sigipro.serpentario.dao.VenenoDAO;
import com.icp.sigipro.serpentario.modelos.Especie;
import com.icp.sigipro.serpentario.modelos.Veneno;
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
    private final int[] permisos = {1, 300, 301, 302};
    //-----------------
    private EspecieDAO dao = new EspecieDAO();
    private VenenoDAO venenodao = new VenenoDAO();

    protected final Class clase = ControladorEspecie.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("agregar");
            add("eliminar");
            add("editar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregar");
            add("editar");
        }
    };

  // <editor-fold defaultstate="collapsed" desc="Métodos Get">
  
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(300, listaPermisos);

        String redireccion = "Especie/Agregar.jsp";
        Especie e = new Especie();
        request.setAttribute("especie", e);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Especie/index.jsp";
        List<Especie> especies = dao.obtenerEspecies();
        request.setAttribute("listaEspecies", especies);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Especie/Ver.jsp";
        int id_especie = Integer.parseInt(request.getParameter("id_especie"));
        try {
            Especie e = dao.obtenerEspecie(id_especie);
            request.setAttribute("especie", e);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(302, listaPermisos);
        String redireccion = "Especie/Editar.jsp";
        int id_especie = Integer.parseInt(request.getParameter("id_especie"));
        Especie especie = dao.obtenerEspecie(id_especie);
        request.setAttribute("especie", especie);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(301, listaPermisos);
        int id_especie = Integer.parseInt(request.getParameter("id_especie"));
        try{
            dao.eliminarEspecie(id_especie);
            String redireccion = "Especie/index.jsp";
            
            //Funcion que genera la bitacora 
            BitacoraDAO bitacora = new BitacoraDAO(); 
            bitacora.setBitacora(id_especie,Bitacora.ACCION_ELIMINAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_ESPECIE,request.getRemoteAddr()); 
            //----------------------------
            
            List<Especie> especies = dao.obtenerEspecies();
            request.setAttribute("listaEspecies", especies);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Especie/Agregar.jsp";
        Especie e = construirObjeto(request);
        resultado = dao.insertarEspecie(e);
        
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Especie de Serpiente agregada correctamente"));        
            redireccion = "Especie/index.jsp";
            Veneno veneno = new Veneno();
            int id_veneno = venenodao.insertarVeneno(e);
            veneno.setEspecie(e);
            veneno.setId_veneno(id_veneno);
            veneno.setRestriccion(false);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(e.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_ESPECIE,request.getRemoteAddr());
            bitacora.setBitacora(veneno.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_VENENO,request.getRemoteAddr());
            //*----------------------------*
        }
        request.setAttribute("listaEspecies", dao.obtenerEspecies());
        redireccionar(request, response, redireccion);
    }
    
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Especie/Editar.jsp";
        Especie e = construirObjeto(request);
        e.setId_especie(Integer.parseInt(request.getParameter("id_especie")));
        resultado = dao.editarEspecie(e);
        
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado){
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(e.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_ESPECIE,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Especie de Serpiente editada correctamente"));
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
