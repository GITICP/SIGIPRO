/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.controlador;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.caballeriza.dao.CaballoDAO;
import com.icp.sigipro.caballeriza.dao.GrupoDeCaballosDAO;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.EventoClinico;
import com.icp.sigipro.caballeriza.modelos.GrupoDeCaballos;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
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
 * @author Walter
 */
@WebServlet(name = "ControladorCaballo", urlPatterns = {"/Caballeriza/Caballo"})
public class ControladorCaballo extends SIGIPROServlet {

    //Falta implementar
    //private final int[] permisos = {1, 43, 44, 45};
    //-----------------
    private CaballoDAO dao = new CaballoDAO();

    protected final Class clase = ControladorCaballo.class;
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

        String redireccion = "Caballo/Agregar.jsp";
        Caballo c = new Caballo();
        GrupoDeCaballosDAO grupodecaballosdao = new GrupoDeCaballosDAO();
        List<GrupoDeCaballos> listagrupos = grupodecaballosdao.obtenerGruposDeCaballos();
        request.setAttribute("helper", HelpersHTML.getSingletonHelpersHTML());
        request.setAttribute("caballo", c);
        request.setAttribute("listagrupos",listagrupos);
        request.setAttribute("accion", "Agregar");
        request.setAttribute("sexos", Caballo.SEXOS);
        request.setAttribute("estados",Caballo.ESTADOS);
        redireccionar(request, response, redireccion);
    }
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //List<Integer> listaPermisos = getPermisosUsuario(request);
        //validarPermisos(permisos, listaPermisos);
        String redireccion = "Caballo/index.jsp";
        List<Caballo> caballos = dao.obtenerCaballos();
        request.setAttribute("listaCaballos", caballos);
        redireccionar(request, response, redireccion);
    }
        protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //List<Integer> listaPermisos = getPermisosUsuario(request);
        //validarPermisos(permisos, listaPermisos);
        String redireccion = "Caballo/Ver.jsp";
        int id_caballo = Integer.parseInt(request.getParameter("id_caballo"));
        try {
            Caballo g = dao.obtenerCaballo(id_caballo);
            List<EventoClinico> listaeventos = dao.ObtenerEventosCaballo(id_caballo);
            
            request.setAttribute("grupo", g.getGrupo_de_caballos());
            request.setAttribute("nombregrupo", g.getGrupo_de_caballos().getNombre());
            request.setAttribute("caballo", g);
            request.setAttribute("listaEventos", listaeventos);

            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        //List<Integer> listaPermisos = getPermisosUsuario(request);
        //validarPermiso(42, listaPermisos);
        String redireccion = "Caballo/Editar.jsp";
        int id_caballo = Integer.parseInt(request.getParameter("id_caballo"));
        Caballo caballo = dao.obtenerCaballo(id_caballo);
        GrupoDeCaballosDAO grupodao = new GrupoDeCaballosDAO();
        List<GrupoDeCaballos> listagrupos = grupodao.obtenerGruposDeCaballos();
        List<EventoClinico> listaeventosrestantes = dao.ObtenerEventosCaballoRestantes(id_caballo);
        List<EventoClinico> listaeventos = dao.ObtenerEventosCaballo(id_caballo);
        request.setAttribute("listagrupos",listagrupos);      
        request.setAttribute("caballo", caballo);
        request.setAttribute("accion", "Editar");
        request.setAttribute("sexos", Caballo.SEXOS);
        request.setAttribute("estados",Caballo.ESTADOS);
        request.setAttribute("listaEventosRestantes", listaeventosrestantes);
        request.setAttribute("listaEventos", listaeventos);
        redireccionar(request, response, redireccion);

    }
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = false;
        String redireccion = "Caballo/Agregar.jsp";
        Caballo c = construirObjeto(request);

       // System.out.println(request.getParameter("imagen2").getBytes());

        resultado = dao.insertarCaballo(c);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        //bitacora.setBitacora(c.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CABALLO, request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Caballo agregado correctamente"));
            redireccion = "Caballo/index.jsp";
        }
        request.setAttribute("listaCaballos", dao.obtenerCaballos());
        redireccionar(request, response, redireccion);
    }
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Caballo/Editar.jsp";
        Caballo c = construirObjeto(request);
        c.setId_caballo(Integer.parseInt(request.getParameter("id_caballo")));
        resultado = dao.editarCaballo(c);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        //bitacora.setBitacora(c.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_CABALLO,request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Caballo editado correctamente"));
            redireccion = "Caballo/index.jsp";
        }
        request.setAttribute("listaCaballos", dao.obtenerCaballos());
        redireccionar(request, response, redireccion);
    }    

    private Caballo construirObjeto(HttpServletRequest request) {
        Caballo c = new Caballo();
        
        GrupoDeCaballosDAO grupodecaballosdao = new GrupoDeCaballosDAO();
        String grupo=request.getParameter("grupodecaballo");
        
        GrupoDeCaballos grupodecaballo;
        if(grupo == ""){
            grupodecaballo = null;
        }
        else{
            grupodecaballo = grupodecaballosdao.obtenerGrupoDeCaballos(Integer.parseInt(request.getParameter("grupodecaballo")));
        }
        c.setGrupo_de_caballos(grupodecaballo);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fecha_ingreso;
        java.sql.Date fecha_ingresoSQL;
        java.util.Date fecha_nacimiento;
        java.sql.Date fecha_nacimientoSQL;
        try {
          String fechaI2 =  request.getParameter("fecha_ingreso2");
          String fechaI =  request.getParameter("fecha_ingreso");
          String fechaN =  request.getParameter("fecha_nacimiento");
          fecha_ingreso = formatoFecha.parse(request.getParameter("fecha_ingreso"));
          fecha_ingresoSQL = new java.sql.Date(fecha_ingreso.getTime());
          fecha_nacimiento = formatoFecha.parse(request.getParameter("fecha_nacimiento"));
          fecha_nacimientoSQL = new java.sql.Date(fecha_nacimiento.getTime());
          c.setFecha_ingreso(fecha_ingresoSQL);
          c.setFecha_nacimiento(fecha_nacimientoSQL);
        } catch (ParseException ex) {
            
        }
        c.setNombre(request.getParameter("nombre"));
        c.setNumero_microchip(Integer.parseInt(request.getParameter("numero_microchip")));
        c.setSexo(request.getParameter("sexo"));
        c.setColor(request.getParameter("color"));
        c.setOtras_sennas(request.getParameter("otras_sennas"));
        c.setEstado(request.getParameter("estado"));
        //No se si sirve   
        try{
            String imagen = request.getParameter("fotografia");
            Blob blob = c.getFotografia();
            blob.setBytes(1,imagen.getBytes());
            c.setFotografia(blob);
        }catch (Exception e){
            
        }
        //-------------
        return c;
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
