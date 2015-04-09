/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.controlador;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.caballeriza.dao.CaballoDAO;
import com.icp.sigipro.caballeriza.dao.GrupoDeCaballosDAO;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.EventoClinico;
import com.icp.sigipro.caballeriza.modelos.GrupoDeCaballos;
import com.icp.sigipro.caballeriza.modelos.Inoculo;
import com.icp.sigipro.caballeriza.modelos.SangriaCaballo;
import com.icp.sigipro.caballeriza.modelos.SangriaPruebaCaballo;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.serpentario.modelos.Serpiente;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.ByteArrayInputStream;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.postgresql.util.Base64;

/**
 *
 * @author Walter
 */
@WebServlet(name = "ControladorCaballo", urlPatterns = {"/Caballeriza/Caballo"})
public class ControladorCaballo extends SIGIPROServlet {

    private final int[] permisos = {1, 49, 50};
    //-----------------
    private CaballoDAO dao = new CaballoDAO();

    protected final Class clase = ControladorCaballo.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");           
            add("editar");
            add("evento");
            add("inoculo");
            add("sangriap");
            add("sangria");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
            add("editar");
            add("agregarimagen");

        }
    };

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(49, listaPermisos);

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
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Caballo/index.jsp";
        List<Caballo> caballos = dao.obtenerCaballos();
        request.setAttribute("listaCaballos", caballos);
        redireccionar(request, response, redireccion);
    }
        protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Caballo/Ver.jsp";
        int id_caballo = Integer.parseInt(request.getParameter("id_caballo"));
        try {
            Caballo g = dao.obtenerCaballo(id_caballo);
            //Imagen del Caballo
            request.setAttribute("imagenCaballo", this.obtenerImagen(g));
            //-----------------------
            request.setAttribute("grupo", g.getGrupo_de_caballos());
            request.setAttribute("nombregrupo", g.getGrupo_de_caballos().getNombre());
            request.setAttribute("caballo", g);
            
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
        protected void getEvento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Caballo/VerEC.jsp";
        int id_caballo = Integer.parseInt(request.getParameter("id_caballo"));
        try {
            Caballo g = dao.obtenerCaballo(id_caballo);
            List<EventoClinico> listaeventos = dao.ObtenerEventosCaballo(id_caballo);
            request.setAttribute("caballo", g);
            request.setAttribute("listaEventos", listaeventos);

            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
        protected void getInoculo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Caballo/VerI.jsp";
        int id_caballo = Integer.parseInt(request.getParameter("id_caballo"));
        try {
            Caballo g = dao.obtenerCaballo(id_caballo);
            List<Inoculo> listainoculos= dao.ObtenerInoculosCaballo(id_caballo);
            request.setAttribute("caballo", g);
            request.setAttribute("listaInoculos", listainoculos);

            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
        protected void getSangriap(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Caballo/VerSp.jsp";
        int id_caballo = Integer.parseInt(request.getParameter("id_caballo"));
        try {
            Caballo g = dao.obtenerCaballo(id_caballo);
            List<SangriaPruebaCaballo> listasangriaspruebas = dao.ObtenerSangriasPruebaCaballo(id_caballo);
            request.setAttribute("caballo", g);
            request.setAttribute("listaSangriasPruebas", listasangriaspruebas);

            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
        protected void getSangria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Caballo/VerS.jsp";
        int id_caballo = Integer.parseInt(request.getParameter("id_caballo"));
        try {
            Caballo g = dao.obtenerCaballo(id_caballo);
            List<SangriaCaballo> listasangrias = dao.ObtenerSangriasCaballo(id_caballo);
            request.setAttribute("caballo", g);
            request.setAttribute("listaSangrias", listasangrias);

            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }        
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(50, listaPermisos);
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
        bitacora.setBitacora(c.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CABALLO, request.getRemoteAddr());
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
        bitacora.setBitacora(c.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_CABALLO,request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Caballo editado correctamente"));
            redireccion = "Caballo/index.jsp";
        }
        request.setAttribute("listaCaballos", dao.obtenerCaballos());
        redireccionar(request, response, redireccion);
    }    

    protected void postAgregarimagen(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String redireccion = "Caballo/index.jsp";
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            int id_caballo = 0;
            ByteArrayInputStream bais = null;
            long size = 0;
            for (FileItem item : items) {
                if (item.isFormField()) {
                    // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
                    String fieldName = item.getFieldName();
                    if (fieldName.equals("id_caballo_imagen")){
                        String fieldValue = item.getString();
                        id_caballo = Integer.parseInt(fieldValue);
                    }     
                } else {
                    // Process form file field (input type="file").
                    byte[] data = item.get();
                    bais = new ByteArrayInputStream(data);
                    size = item.getSize();
                }
            }
            boolean resultado = dao.insertarImagen(bais, id_caballo,size);
            HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
            if(resultado){
                request.setAttribute("mensaje", helper.mensajeDeExito("Imagen a Caballo "+id_caballo+" agregada correctamente"));
            }else{
                request.setAttribute("mensaje", helper.mensajeDeError("No pudo ser agregada la imagen."));
            }
            List<Caballo> caballos = dao.obtenerCaballos();
            request.setAttribute("listaCaballos", caballos);
            redireccionar(request, response, redireccion);

        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }

    // ...
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
        return c;
    } 
    
    private String obtenerImagen(Caballo c){
        if (c.getFotografia() != null){
            return "data:image/jpeg;base64," + Base64.encodeBytes(c.getFotografia());
        }else{
            return "";
        }

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
