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
import com.icp.sigipro.serpentario.dao.ExtraccionDAO;
import com.icp.sigipro.serpentario.dao.LoteDAO;
import com.icp.sigipro.serpentario.modelos.Especie;
import com.icp.sigipro.serpentario.modelos.Extraccion;
import com.icp.sigipro.serpentario.modelos.Lote;
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
@WebServlet(name = "ControladorLote", urlPatterns = {"/Serpentario/Lote"})
public class ControladorLote extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {1, 330, 331};
    //-----------------
    private LoteDAO dao = new LoteDAO();
    private EspecieDAO especiedao = new EspecieDAO();
    private ExtraccionDAO extracciondao = new ExtraccionDAO();

    protected final Class clase = ControladorLote.class;
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
        }
    };

  // <editor-fold defaultstate="collapsed" desc="Métodos Get">
  
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(330, listaPermisos);

        String redireccion = "Lote/Agregar.jsp";
        Lote l = new Lote();
        List<Especie> especies = especiedao.obtenerEspecies();
        request.setAttribute("lote", l);
        request.setAttribute("especies",especies);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Lote/index.jsp";
        List<Lote> lotes = dao.obtenerLotes();
        request.setAttribute("listaLotes", lotes);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Lote/Ver.jsp";
        int id_lote = Integer.parseInt(request.getParameter("id_lote"));
        try {
            Lote l = dao.obtenerLote(id_lote);
            List<Extraccion> extracciones = dao.obtenerExtracciones(l);
            request.setAttribute("lote", l);
            request.setAttribute("extracciones",extracciones);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    //Agrega mas Extracciones al Lote, pero no elimina
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(331, listaPermisos);
        String redireccion = "Lote/Editar.jsp";
        int id_lote = Integer.parseInt(request.getParameter("id_lote"));
        Lote lote = dao.obtenerLote(id_lote);
        List<Extraccion> extracciones = dao.obtenerExtracciones(lote.getEspecie());
        request.setAttribute("lote", lote);
        request.setAttribute("extracciones", extracciones);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }
    //Agrega mas Extracciones al Lote, pero no elimina
    protected void getEditar(HttpServletRequest request, HttpServletResponse response,int id_lote) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(331, listaPermisos);
        String redireccion = "Lote/Editar.jsp";
        Lote lote = dao.obtenerLote(id_lote);
        List<Extraccion> extracciones = dao.obtenerExtracciones(lote.getEspecie());
        request.setAttribute("lote", lote);
        request.setAttribute("extracciones", extracciones);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Lote/index.jsp";
        Lote l = construirObjeto(request);
        resultado = dao.insertarLote(l);
        
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Lote agregado correctamente"));
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(l.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_LOTE,request.getRemoteAddr());
            //*----------------------------*
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("Lote no pudo ser agregado por problemas con el Número de Lote. Este debe ser único."));
        }
        this.getEditar(request, response, l.getId_lote());
    }
    
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Lote/Editar.jsp";
        Lote l = construirObjeto(request);
        l.setId_lote(Integer.parseInt(request.getParameter("id_lote")));
        
        String[] extracciones = request.getParameterValues("extracciones");
        resultado = dao.insertarExtracciones(l, extracciones);
        
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado){
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            for (String i : extracciones){
                Extraccion e = extracciondao.obtenerExtraccion(Integer.parseInt(i));
                bitacora.setBitacora(e.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EXTRACCION,request.getRemoteAddr());
                //*----------------------------*
            }
            request.setAttribute("mensaje", helper.mensajeDeExito("Lote editado correctamente"));
            redireccion = "Lote/index.jsp";
        }
        request.setAttribute("listaLotes", dao.obtenerLotes());
        redireccionar(request, response, redireccion);
    }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
    private Lote construirObjeto(HttpServletRequest request) {
        Lote l = new Lote();
        l.setNumero_lote(request.getParameter("numero_lote"));
        Especie e = especiedao.obtenerEspecie(Integer.parseInt(request.getParameter("especie")));
        l.setEspecie(e);

        return l;
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
