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
import com.icp.sigipro.serpentario.modelos.CatalogoTejido;
import com.icp.sigipro.serpentario.modelos.ColeccionHumeda;
import com.icp.sigipro.serpentario.modelos.Especie;
import com.icp.sigipro.serpentario.modelos.Evento;
import com.icp.sigipro.serpentario.modelos.HelperSerpiente;
import com.icp.sigipro.serpentario.modelos.Serpiente;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * @author ld.conejo
 */
@WebServlet(name = "ControladorSerpiente", urlPatterns = {"/Serpentario/Serpiente"})
public class ControladorSerpiente extends SIGIPROServlet {

    //Agregar, editar, eventos, decesos
    private final int[] permisos = {1, 310, 311, 312,313,314};
    private SerpienteDAO dao = new SerpienteDAO();
    private EventoDAO eventodao = new EventoDAO();
    private BitacoraDAO bitacora = new BitacoraDAO();
    
    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();


    protected final Class clase = ControladorSerpiente.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("verdeceso");
            add("editardeceso");
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
            add("catalogotejido");
            add("editardeceso");
            add("agregarimagen");
            
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
        validarPermiso(310, listaPermisos);

        String redireccion = "Serpiente/Agregar.jsp";
        Serpiente s = new Serpiente();
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

    protected void getVerdeceso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Serpiente/VerDeceso.jsp";
        String deceso = request.getParameter("deceso");
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente"));
        Serpiente serpiente = dao.obtenerSerpiente(id_serpiente);
        request.setAttribute("serpiente", serpiente);
        request.setAttribute("accion", "verdeceso");
        if (deceso.equals("catalogotejido")){
            request.setAttribute("deceso", "catalogotejido");
            CatalogoTejido ct = dao.obtenerCatalogoTejido(serpiente);
            request.setAttribute("catalogotejido", ct);
        }else{
            request.setAttribute("deceso", "coleccionhumeda");
            ColeccionHumeda ch = dao.obtenerColeccionHumeda(serpiente);
            request.setAttribute("coleccionhumeda", ch);
        }
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
            //Imagen de la Serpiente
            request.setAttribute("imagenSerpiente", this.obtenerImagen(s));
            //-----------------------
            List<Evento> validarSerpiente = eventodao.validarSerpiente(id_serpiente);
            List<Evento> eventos = eventodao.obtenerEventos(id_serpiente);
            request.setAttribute("listaEventos",eventos);
            request.setAttribute("listaTipoEventos",tipo_Eventos);
            boolean postDeceso = true;
            request.setAttribute("postDeceso",postDeceso);
            for (Evento i : validarSerpiente){
                if (i.getEvento().equals("Pase a Coleccion Viva")){
                    request.setAttribute("coleccionViva",i);
                }
                if (i.getEvento().equals("Deceso")){
                    request.setAttribute("deceso",i);
                }
                if (i.getEvento().equals("Colección Húmeda")){
                    postDeceso = false;
                    request.setAttribute("postDeceso",postDeceso);
                    request.setAttribute("coleccionhumeda", i);
                    request.setAttribute("catalogotejido", null);
                }if (i.getEvento().equals("Catálogo Tejido")){
                    postDeceso = false;
                    request.setAttribute("postDeceso",postDeceso);
                    request.setAttribute("coleccionhumeda", null);
                    request.setAttribute("catalogotejido", i);
                }
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
        validarPermiso(311, listaPermisos);
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
            validarPermiso(312, listaPermisos);
            Serpiente s = dao.obtenerSerpiente(id_serpiente);
            Evento e = this.setEvento(s, "Pase a Coleccion Viva", request);
            //----Agregar el Evento al Sistema
            boolean resultado = eventodao.insertarEvento(e);

            if (resultado){
                request.setAttribute("mensaje", helper.mensajeDeExito("Serpiente pasada a Colección Viva con éxito."));
                 //Funcion que genera la bitacora
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
      
    protected void getEditardeceso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(314, listaPermisos);
        String redireccion = "Serpiente/EditarDeceso.jsp";
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente"));
        String deceso = request.getParameter("deceso");
        Serpiente serpiente = dao.obtenerSerpiente(id_serpiente);
        request.setAttribute("serpiente", serpiente);
        request.setAttribute("accion", "editardeceso");
        if (deceso.equals("catalogotejido")){
            request.setAttribute("deceso", "catalogotejido");
            CatalogoTejido ct = dao.obtenerCatalogoTejido(serpiente);
            request.setAttribute("catalogotejido", ct);
        }else{
            request.setAttribute("deceso", "coleccionhumeda");
            ColeccionHumeda ch = dao.obtenerColeccionHumeda(serpiente);
            request.setAttribute("coleccionhumeda", ch);
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getDeceso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(313, listaPermisos);
        EventoDAO eventodao = new EventoDAO();
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente"));
        Evento deceso = new Evento();
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        try {
            deceso = eventodao.validarDeceso(id_serpiente);
        } catch (SIGIPROException ex) {
        }
        if (deceso.getId_evento() == 0){      
            String redireccion = "Serpiente/index.jsp";
            Serpiente serpiente = dao.obtenerSerpiente(id_serpiente);
            Evento e = this.setEvento(serpiente, "Deceso", request);
            //----Agregar el Evento al Sistema
            boolean resultado = eventodao.insertarEvento(e);
            if (resultado){
                request.setAttribute("mensaje", helper.mensajeDeExito("Serpiente registrada como Deceso con éxito."));
                 //Funcion que genera la bitacora
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
        String redireccion = "Serpiente/index.jsp";
        Serpiente s = construirObjeto(request);
        
        UsuarioDAO usuariodao = new UsuarioDAO();
        
        Usuario usuario = usuariodao.obtenerUsuario((String)request.getSession().getAttribute("usuario"));
        s.setRecibida(usuario);
        resultado = dao.insertarSerpiente(s);
        
        if (resultado){
            //Funcion que genera la bitacora
            bitacora.setBitacora(s.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_SERPIENTE,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Serpiente agregada correctamente"));
            redireccion = "Serpiente/index.jsp";
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("Serpiente no pudo ser agregada por problemas con el Número de Ingreso. Este debe ser único."));
        }
        request.setAttribute("listaSerpientes", dao.obtenerSerpientes());
        redireccionar(request, response, redireccion);
    }
    
    protected void postAgregarimagen(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String redireccion = "Serpiente/index.jsp";
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            int id_serpiente = 0;
            ByteArrayInputStream bais = null;
            long size = 0;
            for (FileItem item : items) {
                if (item.isFormField()) {
                    // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
                    String fieldName = item.getFieldName();
                    if (fieldName.equals("id_serpiente_imagen")){
                        String fieldValue = item.getString();
                        id_serpiente = Integer.parseInt(fieldValue);
                    }     
                } else {
                    // Process form file field (input type="file").
                    byte[] data = item.get();
                    bais = new ByteArrayInputStream(data);
                    size = item.getSize();
                }
            }
            boolean resultado = dao.insertarImagen(bais, id_serpiente,size);

            if(resultado){
                request.setAttribute("mensaje", helper.mensajeDeExito("Imagen a Serpiente "+id_serpiente+" agregada correctamente"));
            }else{
                request.setAttribute("mensaje", helper.mensajeDeError("No pudo ser agregada la imagen."));
            }
            request.setAttribute("listaSerpientes", dao.obtenerSerpientes());
            redireccionar(request, response, redireccion);

        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }

    // ...
    }
    
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Serpiente/Editar.jsp";
        Serpiente s = construirObjeto(request);
        s.setId_serpiente(Integer.parseInt(request.getParameter("id_serpiente")));
        
        String usuarioString = request.getParameter("recibida");
        UsuarioDAO usuariodao = new UsuarioDAO();
        Usuario usuario = usuariodao.obtenerUsuario(usuarioString);
        s.setRecibida(usuario);
                
        List<HelperSerpiente> cambios = dao.editarSerpiente(s);
        resultado=true;    
            for (HelperSerpiente j : cambios){
                Evento e = new Evento();
                java.sql.Date fecha_evento = new java.sql.Date(new Date().getTime());
                e.setSerpiente(s);
                e.setFecha_evento(fecha_evento);
                Usuario usuario_evento = usuariodao.obtenerUsuario(request.getSession().getAttribute("usuario").toString());
                e.setUsuario(usuario_evento);
                if (j.getCampo_cambiado().equals("sexo")){
                    e.setEvento("Sexo");
                    e.setValor_cambiado(j.getValor_cambiado());
                    eventodao.insertarCambio(e);
                }if (j.getCampo_cambiado().equals("talla_cabeza")){
                    e.setEvento("Talla CabezaCloaca");
                    e.setValor_cambiado(j.getValor_cambiado());
                    eventodao.insertarCambio(e);
                }if (j.getCampo_cambiado().equals("talla_cola")){
                    e.setEvento("Talla Cola");
                    e.setValor_cambiado(j.getValor_cambiado());
                    eventodao.insertarCambio(e);
                }if (j.getCampo_cambiado().equals("peso")){
                    e.setEvento("Peso");
                    e.setValor_cambiado(j.getValor_cambiado());
                    eventodao.insertarCambio(e);
                }
            }
       
        if (resultado){
             //Funcion que genera la bitacora        
            bitacora.setBitacora(s.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_SERPIENTE,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Especie de Serpiente editada correctamente"));
            redireccion = "Serpiente/index.jsp";
        }
        request.setAttribute("listaSerpientes", dao.obtenerSerpientes());
        redireccionar(request, response, redireccion);
    }
    
    protected void postEditardeceso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        boolean resultado = false;
        String redireccion = "Serpiente/EditarDeceso.jsp";
        String deceso = request.getParameter("deceso");
        int id_serpiente=0;
        if (deceso.equals("catalogotejido")){
            CatalogoTejido ct = this.construirCT(request);
            int id_catalogo_tejido = Integer.parseInt(request.getParameter("id_ct"));
            ct.setId_catalogo_tejido(id_catalogo_tejido);
            id_serpiente = ct.getSerpiente().getId_serpiente();
            resultado=dao.editarCatalogoTejido(ct);
            if (resultado){
                //Funcion que genera la bitacora
                bitacora.setBitacora(ct.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_CATALOGOTEJIDO,request.getRemoteAddr());
                //*----------------------------*
            }
        }else{
            ColeccionHumeda ch = this.construirCH(request);
            int id_coleccion_humeda = Integer.parseInt(request.getParameter("id_ch"));
            ch.setId_coleccion_humeda(id_coleccion_humeda);
            id_serpiente = ch.getSerpiente().getId_serpiente();
            resultado=dao.editarColeccionHumeda(ch);
            if (resultado){
                //Funcion que genera la bitacora
                bitacora.setBitacora(ch.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_COLECCIONHUMEDA,request.getRemoteAddr());
                //*----------------------------*
            }
        }
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Evento de serpiente "+id_serpiente+" editado correctamente."));
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
        
        Serpiente serpiente = dao.obtenerSerpiente(id_serpiente);

        Evento evento = this.setEvento(serpiente, request);
                
        resultado = eventodao.insertarEvento(evento);
        
        if (resultado){
            //Funcion que genera la bitacora
            bitacora.setBitacora(evento.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EVENTO,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Evento agregado correctamente"));
        }
        this.getVer(request, response);
    }
    
    protected void postCatalogotejido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Serpiente/index.jsp";
        CatalogoTejido ct = construirCT(request);
        
        UsuarioDAO usuariodao = new UsuarioDAO();
        
        Usuario usuario = usuariodao.obtenerUsuario((String)request.getSession().getAttribute("usuario"));
        ct.setUsuario(usuario);
        
        resultado = dao.insertarSerpiente(ct);
       
        if (resultado){
             //Funcion que genera la bitacora
            bitacora.setBitacora(ct.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_CATALOGOTEJIDO,request.getRemoteAddr());
            //*----------------------------*
            Evento e = this.setEvento(ct.getSerpiente(), "Catálogo Tejido", request);
            eventodao.insertarEvento(e);
            bitacora.setBitacora(e.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EVENTO,request.getRemoteAddr());
            request.setAttribute("mensaje", helper.mensajeDeExito("Serpiente "+ct.getSerpiente().getNumero_serpiente()+" agregada a Catálogo de Tejidos correctamente."));
            redireccion = "Serpiente/index.jsp";
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("Serpiente no pudo ser agregada a Catálogo de Tejidos por problemas con el Número de Ingreso. Este debe ser único."));
        }
        request.setAttribute("listaSerpientes", dao.obtenerSerpientes());
        redireccionar(request, response, redireccion);    }
    
    protected void postColeccionhumeda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Serpiente/index.jsp";
        ColeccionHumeda ch = construirCH(request);
        
        UsuarioDAO usuariodao = new UsuarioDAO();
        
        Usuario usuario = usuariodao.obtenerUsuario((String)request.getSession().getAttribute("usuario"));
        ch.setUsuario(usuario);
        
        resultado = dao.insertarSerpiente(ch);
       
        if (resultado){
             //Funcion que genera la bitacora
            bitacora.setBitacora(ch.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_COLECCIONHUMEDA,request.getRemoteAddr());
            //*----------------------------*
            Evento e = this.setEvento(ch.getSerpiente(), "Colección Húmeda", request);
            eventodao.insertarEvento(e);
            bitacora.setBitacora(e.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EVENTO,request.getRemoteAddr());
            request.setAttribute("mensaje", helper.mensajeDeExito("Serpiente "+ch.getSerpiente().getNumero_serpiente()+" agregada a Colección Húmeda correctamente."));
            redireccion = "Serpiente/index.jsp";
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("Serpiente no pudo ser agregada a Colección Húmeda por problemas con el Número de Ingreso. Este debe ser único."));
        }
        request.setAttribute("listaSerpientes", dao.obtenerSerpientes());
        redireccionar(request, response, redireccion);
    }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
    private Serpiente construirObjeto(HttpServletRequest request) {
        Serpiente s = new Serpiente();
        
        if (!request.getParameter("accion").equals("Editar")){
            s.setNumero_serpiente(Integer.parseInt(request.getParameter("numero_serpiente")));
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
        }
        s.setSexo(request.getParameter("sexo"));
        s.setTalla_cabeza(Float.parseFloat(request.getParameter("talla_cabeza")));
        s.setTalla_cola(Float.parseFloat(request.getParameter("talla_cola")));
        s.setPeso(Float.parseFloat(request.getParameter("peso")));

        return s;
    }
    
    private ColeccionHumeda construirCH(HttpServletRequest request){
        ColeccionHumeda ch = new ColeccionHumeda();
        ch.setNumero_coleccion_humeda(Integer.parseInt(request.getParameter("numero_coleccion_humeda")));
        ch.setObservaciones(request.getParameter("observacionesCH"));
        ch.setProposito(request.getParameter("proposito"));
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente_coleccion_humeda"));
        ch.setSerpiente(dao.obtenerSerpiente(id_serpiente));
        
        return ch;
    }
    
    private CatalogoTejido construirCT(HttpServletRequest request){
        CatalogoTejido ct = new CatalogoTejido();
        ct.setNumero_catalogo_tejido(Integer.parseInt(request.getParameter("numero_catalogo_tejido")));
        ct.setObservaciones(request.getParameter("observacionesCT"));
        ct.setNumero_caja(request.getParameter("numero_caja"));
        ct.setEstado(request.getParameter("estado"));
        ct.setPosicion(request.getParameter("posicion"));
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente_catalogo_tejido"));
        ct.setSerpiente(dao.obtenerSerpiente(id_serpiente));
        
        return ct;
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
    
    private String obtenerImagen(Serpiente s){
        if (s.getImagen() != null){
            return "data:image/jpeg;base64," + Base64.encodeBytes(s.getImagen());
        }else{
            return "";
        }

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
