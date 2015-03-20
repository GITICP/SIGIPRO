/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.serpentario.dao.EspecieDAO;
import com.icp.sigipro.serpentario.dao.EventoDAO;
import com.icp.sigipro.serpentario.dao.SerpienteDAO;
import com.icp.sigipro.serpentario.modelos.Especie;
import com.icp.sigipro.serpentario.modelos.Evento;
import com.icp.sigipro.serpentario.modelos.Serpiente;
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
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author ld.conejo
 */
@WebServlet(name = "ControladorSerpiente", urlPatterns = {"/Serpentario/Serpiente"})
public class ControladorSerpiente extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {1, 43, 44, 45};
    //-----------------
    private SerpienteDAO dao = new SerpienteDAO();

    protected final Class clase = ControladorSerpiente.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
            add("coleccionviva");
            add("deceso");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregar");
            add("editar");
            add("evento");
            //Cuando esta muerta
            add("coleccionhumeda");
            add("catalogotejidos");
            
        }
    };

  // <editor-fold defaultstate="collapsed" desc="Métodos Get">
  
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(43, listaPermisos);

        String redireccion = "Serpiente/Agregar.jsp";
        Serpiente s = new Serpiente();
        EspecieDAO especiedao = new EspecieDAO();
        List<Especie> especies = especiedao.obtenerEspecies();
        request.setAttribute("helper", HelpersHTML.getSingletonHelpersHTML());
        request.setAttribute("serpiente", s);
        request.setAttribute("especies",especies);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Serpiente/index.jsp";
        List<Serpiente> serpientes = dao.obtenerSerpientes();
        request.setAttribute("listaSerpientes", serpientes);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Serpiente/Ver.jsp";
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente"));
        try {
            Serpiente s = dao.obtenerSerpiente(id_serpiente);
            request.setAttribute("serpiente", s);
            EventoDAO eventodao = new EventoDAO();
            List<Evento> eventos = eventodao.obtenerEventos(id_serpiente);
            request.setAttribute("listaEventos",eventos);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(44, listaPermisos);
        String redireccion = "Serpiente/Editar.jsp";
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente"));
        Serpiente serpiente = dao.obtenerSerpiente(id_serpiente);
        request.setAttribute("serpiente", serpiente);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }
    
    protected void getColeccionviva(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(45, listaPermisos);
        String redireccion = "Serpiente/index.jsp";
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente"));
        Serpiente s = dao.obtenerSerpiente(id_serpiente);
        Evento e = this.setEvento(s, "Pase a Coleccion Viva", request);
        //----Agregar el Evento al Sistema
        EventoDAO eventodao = new EventoDAO();
        boolean resultado = eventodao.insertarEvento(e);
        
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Serpiente pasada a Colección Viva con éxito."));
             //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(e.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EVENTO,request.getRemoteAddr());
            //*----------------------------*
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("Error en la Base de Datos. Serpiente no pudo pasarse a Coleccion Viva."));
        }
        List<Serpiente> serpientes = dao.obtenerSerpientes();
        request.setAttribute("listaSerpientes", serpientes);
        redireccionar(request, response, redireccion);

    }
      

    protected void getDeceso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(45, listaPermisos);
        String redireccion = "Serpiente/index.jsp";
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente"));
        Serpiente serpiente = dao.obtenerSerpiente(id_serpiente);
        Evento e = this.setEvento(serpiente, "Deceso", request);
        //----Agregar el Evento al Sistema
        EventoDAO eventodao = new EventoDAO();
        boolean resultado = eventodao.insertarEvento(e);
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Serpiente registrada como Deceso con éxito."));
             //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(e.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EVENTO,request.getRemoteAddr());
            //*----------------------------*
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("Error en la Base de Datos. Serpiente no pudo ser registrada como Deceso."));
        }
        List<Serpiente> serpientes = dao.obtenerSerpientes();
        request.setAttribute("listaSerpientes", serpientes);
        redireccionar(request, response, redireccion);

    }


    // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Serpiente/Agregar.jsp";
        Serpiente s = construirObjeto(request);
        
        System.out.println(request.getParameter("imagen2").getBytes());
                   
        resultado = dao.insertarSerpiente(s);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(s.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_SERPIENTE,request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Serpiente agregada correctamente"));
            redireccion = "Serpiente/index.jsp";
        }
        request.setAttribute("listaSerpientes", dao.obtenerSerpientes());
        redireccionar(request, response, redireccion);
    }
    
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Serpiente/Editar.jsp";
        Serpiente s = construirObjeto(request);
        s.setId_serpiente(Integer.parseInt(request.getParameter("id_serpiente")));
        resultado = dao.editarSerpiente(s);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(s.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_SERPIENTE,request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Especie de Serpiente editada correctamente"));
            redireccion = "Serpiente/index.jsp";
        }
        request.setAttribute("listaSerpientes", dao.obtenerSerpientes());
        redireccionar(request, response, redireccion);
    }
    
    protected void postCatalogotejidos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //Not implemented yet
    }
    
    protected void postColeccionhumeda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //Not implemented yet
    }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
    private Serpiente construirObjeto(HttpServletRequest request) {
        Serpiente s = new Serpiente();
        
        EspecieDAO especiedao = new EspecieDAO();
        Especie especie = especiedao.obtenerEspecie(Integer.parseInt(request.getParameter("especie")));
        s.setEspecie(especie);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fecha_ingreso;
        java.sql.Date fecha_ingresoSQL;
        try {
          fecha_ingreso = formatoFecha.parse(request.getParameter("fecha_ingreso"));
          fecha_ingresoSQL = new java.sql.Date(fecha_ingreso.getTime());
          s.setFecha_ingreso(fecha_ingresoSQL);
        } catch (ParseException ex) {
            
        }
        s.setLocalidad_origen(request.getParameter("localidad_origen"));
        s.setColectada(request.getParameter("colectada"));
        s.setRecibida(request.getParameter("recibida"));
        s.setSexo(request.getParameter("sexo"));
        s.setTalla_cabeza(Integer.parseInt(request.getParameter("talla_cabeza")));
        s.setTalla_cola(Integer.parseInt(request.getParameter("talla_cola")));
        s.setPeso(Integer.parseInt(request.getParameter("peso")));
        //No se si sirve   
        try{
            String imagen = request.getParameter("imagen");
            Blob blob = s.getImagen();
            blob.setBytes(1,imagen.getBytes());
            s.setImagen(blob);
        }catch (Exception e){
            
        }
        //-------------
        return s;
    }
  
    //Para Coleccion Viva, Deceso
    private Evento setEvento(Serpiente serpiente,String evento,HttpServletRequest request){
        Evento e = new Evento();
        e.setEvento(evento);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fecha_evento;
        java.sql.Date fecha_eventoSQL;
        try {
          fecha_evento = formatoFecha.parse(new java.util.Date().toString());
          fecha_eventoSQL = new java.sql.Date(fecha_evento.getTime());
          e.setFecha_evento(fecha_eventoSQL);
        } catch (ParseException ex) {
            
        }
        e.setSerpiente(serpiente);
        UsuarioDAO usuariodao = new UsuarioDAO();
        e.setUsuario(usuariodao.obtenerUsuario(request.getSession().getAttribute("usuario").toString()));
        
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
