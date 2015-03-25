/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    protected final List<String> sexo = new ArrayList<String>()
    {
        {
            add("Macho");
            add("Hembra");
            add("Indefinido");
        }
    };
    
    protected final List<String> tipo_Eventos = new ArrayList<String>()
    {
        {
            add("Defecación");
            add("CambioPiel");
            add("Desparasitación");
            add("Alimentación");

        }
    };

  // <editor-fold defaultstate="collapsed" desc="Métodos Get">
  
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(43, listaPermisos);

        String redireccion = "Serpiente/Agregar.jsp";
        Serpiente s = new Serpiente();
        SerpienteDAO serpientedao = new SerpienteDAO();
        s.setId_serpiente(serpientedao.obtenerProximoId());
        EspecieDAO especiedao = new EspecieDAO();
        List<Especie> especies = especiedao.obtenerEspecies();
        request.setAttribute("helper", HelpersHTML.getSingletonHelpersHTML());
        request.setAttribute("serpiente", s);
        request.setAttribute("especies",especies);
        request.setAttribute("accion", "Agregar");
        request.setAttribute("sexos",sexo);
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
            Evento coleccionviva = eventodao.validarPasoCV(id_serpiente);
            Evento deceso = eventodao.validarDeceso(id_serpiente);
            List<Evento> eventos = eventodao.obtenerEventos(id_serpiente);
            request.setAttribute("listaEventos",eventos);
            request.setAttribute("listaTipoEventos",tipo_Eventos);
            if (coleccionviva.getId_evento() != 0){
                request.setAttribute("coleccionViva",coleccionviva);
            }else{
                 request.setAttribute("coleccionViva",null);               
            }
            if (deceso.getId_evento() != 0){
                request.setAttribute("deceso",deceso);
            }else{
                 request.setAttribute("deceso",null);                               
            }
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
        EspecieDAO especiedao = new EspecieDAO();
        List<Especie> especies = especiedao.obtenerEspecies();
        request.setAttribute("especies",especies);
        request.setAttribute("serpiente", serpiente);
        request.setAttribute("accion", "Editar");
        request.setAttribute("sexos",sexo);
        redireccionar(request, response, redireccion);

    }
    
    protected void getColeccionviva(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        EventoDAO eventodao = new EventoDAO();
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente"));
        Evento pasoCV = new Evento();
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        try {
            pasoCV = eventodao.validarPasoCV(id_serpiente);
        } catch (SIGIPROException ex) {
        }
        if (pasoCV.getId_evento() == 0){
            List<Integer> listaPermisos = getPermisosUsuario(request);
            validarPermiso(45, listaPermisos);
            Serpiente s = dao.obtenerSerpiente(id_serpiente);
            Evento e = this.setEvento(s, "Pase a Coleccion Viva", request);
            //----Agregar el Evento al Sistema
            boolean resultado = eventodao.insertarEvento(e);

            if (resultado){
                request.setAttribute("mensaje", helper.mensajeDeExito("Serpiente pasada a Colección Viva con éxito."));
                 //Funcion que genera la bitacora
                BitacoraDAO bitacora = new BitacoraDAO();
                bitacora.setBitacora(e.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EVENTO,request.getRemoteAddr());
                //*----------------------------*
            }else{
                request.setAttribute("mensaje", helper.mensajeDeError("Error en la Base de Datos. Serpiente no pudo pasarse a Coleccion Viva."));
            }
            this.getVer(request, response);
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("Error en el Sistema. La serpiente ya fue registrada como Colección Viva."));
            this.getIndex(request, response);
        }

    }
      

    protected void getDeceso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        EventoDAO eventodao = new EventoDAO();
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente"));
        Evento deceso = new Evento();
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        try {
            deceso = eventodao.validarDeceso(id_serpiente);
        } catch (SIGIPROException ex) {
        }
        if (deceso.getId_evento() == 0){      
            List<Integer> listaPermisos = getPermisosUsuario(request);
            validarPermiso(45, listaPermisos);
            String redireccion = "Serpiente/index.jsp";
            Serpiente serpiente = dao.obtenerSerpiente(id_serpiente);
            Evento e = this.setEvento(serpiente, "Deceso", request);
            //----Agregar el Evento al Sistema
            boolean resultado = eventodao.insertarEvento(e);
            if (resultado){
                request.setAttribute("mensaje", helper.mensajeDeExito("Serpiente registrada como Deceso con éxito."));
                 //Funcion que genera la bitacora
                BitacoraDAO bitacora = new BitacoraDAO();
                bitacora.setBitacora(e.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EVENTO,request.getRemoteAddr());
                //*----------------------------*
            }else{
                request.setAttribute("mensaje", helper.mensajeDeError("Error en la Base de Datos. Serpiente no pudo ser registrada como Deceso."));
            }
            this.getVer(request, response);
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("Error en el Sistema. La serpiente ya fue registrada como Deceso."));
            this.getIndex(request, response);
        }

    }


    // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Serpiente/Agregar.jsp";
        Serpiente s = construirObjeto(request);
        
        UsuarioDAO usuariodao = new UsuarioDAO();
        
        Usuario usuario = usuariodao.obtenerUsuario((String)request.getSession().getAttribute("usuario"));
        s.setRecibida(usuario);
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
        
        String usuarioString = request.getParameter("recibida");
        UsuarioDAO usuariodao = new UsuarioDAO();
        Usuario usuario = usuariodao.obtenerUsuario(usuarioString);
        s.setRecibida(usuario);
        
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
    
    protected void postEvento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Serpiente/index.jsp";
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente"));
        SerpienteDAO serpientedao = new SerpienteDAO();
        
        Serpiente serpiente = serpientedao.obtenerSerpiente(Integer.parseInt(request.getParameter("id_serpiente")));

        Evento evento = this.setEvento(serpiente, request);
        
        EventoDAO eventodao = new EventoDAO();
        
        resultado = eventodao.insertarEvento(evento);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(evento.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EVENTO,request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Evento agregado correctamente"));
        }
        this.getVer(request, response);
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
        System.out.println(request.getParameter("especie"));
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
        s.setSexo(request.getParameter("sexo"));
        s.setTalla_cabeza(Float.parseFloat(request.getParameter("talla_cabeza")));
        s.setTalla_cola(Float.parseFloat(request.getParameter("talla_cola")));
        s.setPeso(Float.parseFloat(request.getParameter("peso")));
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
        java.sql.Date date = new java.sql.Date(new Date().getTime());
        e.setFecha_evento(date);
        e.setSerpiente(serpiente);
        UsuarioDAO usuariodao = new UsuarioDAO();
        e.setUsuario(usuariodao.obtenerUsuario(request.getSession().getAttribute("usuario").toString()));
        
        return e;
    }
    
    //Para Evento
    private Evento setEvento(Serpiente serpiente,HttpServletRequest request){
        Evento e = new Evento();
        System.out.println(request.getParameter("eventoModal"));
        e.setEvento(request.getParameter("eventoModal"));
        e.setObservaciones(request.getParameter("observacionesModal"));
        java.sql.Date date = new java.sql.Date(new Date().getTime());
        e.setFecha_evento(date);
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
