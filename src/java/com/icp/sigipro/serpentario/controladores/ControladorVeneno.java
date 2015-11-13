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
import com.icp.sigipro.serpentario.dao.LoteDAO;
import com.icp.sigipro.serpentario.dao.VenenoDAO;
import com.icp.sigipro.serpentario.modelos.Especie;
import com.icp.sigipro.serpentario.modelos.Lote;
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
@WebServlet(name = "ControladorVeneno", urlPatterns = {"/Serpentario/Veneno"})
public class ControladorVeneno extends SIGIPROServlet {

    //340 ver Serpentario, 341 ver Externo, 342 Editar de serpentario
    private final int[] permisos = {1, 340, 341, 342};
    //-----------------
    private VenenoDAO dao = new VenenoDAO();
    private LoteDAO lotedao = new LoteDAO();
    private EspecieDAO especiedao = new EspecieDAO();

    protected final Class clase = ControladorVeneno.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("verexterno");
            add("editar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("editar");
        }
    };

  // <editor-fold defaultstate="collapsed" desc="Métodos Get">
  
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Veneno/index.jsp";
        List<Veneno> venenos = dao.obtenerVenenos();
        request.setAttribute("listaVenenos", venenos);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(340, listaPermisos);
        String redireccion = "Veneno/Ver.jsp";
        int id_veneno = Integer.parseInt(request.getParameter("id_veneno"));
        try {
            Veneno v = dao.obtenerVeneno(id_veneno);
            request.setAttribute("veneno", v);
            List<Lote> lotes = lotedao.obtenerLotes(v.getEspecie());
            float cantidad = 0;
            for (Lote l : lotes){
                cantidad += l.getCantidad_actual();
            }
            v.setCantidad(cantidad);
            request.setAttribute("lotes", lotes);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    protected void getVerexterno(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(341, listaPermisos);
        String redireccion = "Veneno/VerExterno.jsp";
        int id_veneno = Integer.parseInt(request.getParameter("id_veneno"));
        try {
            Veneno v = dao.obtenerVeneno(id_veneno);
            request.setAttribute("veneno", v);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(342, listaPermisos);
        String redireccion = "Veneno/Editar.jsp";
        int id_veneno = Integer.parseInt(request.getParameter("id_veneno"));
        Veneno veneno = dao.obtenerVeneno(id_veneno);
        request.setAttribute("veneno", veneno);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">  
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Veneno/Editar.jsp";
        Veneno v = construirObjeto(request);
        
        resultado = dao.editarVeneno(v);
        
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        request.setAttribute("mensaje", helper.mensajeDeExito("Veneno editado correctamente"));
        if (resultado){
            redireccion = "Veneno/index.jsp";
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(v.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_VENENO,request.getRemoteAddr());
            //*----------------------------*
        }
        request.setAttribute("listaVenenos", dao.obtenerVenenos());
        redireccionar(request, response, redireccion);
    }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
    private Veneno construirObjeto(HttpServletRequest request) {
        Veneno v = new Veneno();
        v.setId_veneno(Integer.parseInt(request.getParameter("id_veneno")));
        v.setEspecie(especiedao.obtenerEspecie(Integer.parseInt(request.getParameter("especie"))));

        if (request.getParameter("restriccion") != null){
            v.setRestriccion(true);
            v.setCantidad_minima(Float.parseFloat(request.getParameter("cantidad_minima")));
        }else{
            v.setRestriccion(false);
            v.setCantidad_minima(0);
        }

        return v;
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
