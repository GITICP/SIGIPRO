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
import com.icp.sigipro.caballeriza.modelos.Peso;
import com.icp.sigipro.caballeriza.modelos.SangriaCaballo;
import com.icp.sigipro.caballeriza.modelos.SangriaPruebaCaballo;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelperFechas;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
public class ControladorCaballo extends SIGIPROServlet
{

    private final int[] permisos = {1, 49, 50};
    private final CaballoDAO dao = new CaballoDAO();

    protected final Class clase = ControladorCaballo.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
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
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregar");
            add("editar");
            add("agregareditar");
            add("agregarpeso");
        }
    };

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(49, listaPermisos);

        String redireccion = "Caballo/Agregar.jsp";
        Caballo c = new Caballo();
        GrupoDeCaballosDAO grupodecaballosdao = new GrupoDeCaballosDAO();
        List<GrupoDeCaballos> listagrupos = grupodecaballosdao.obtenerGruposDeCaballos();
        request.setAttribute("helper", HelpersHTML.getSingletonHelpersHTML());
        request.setAttribute("caballo", c);
        request.setAttribute("imagenCaballo", this.obtenerImagen(c));
        request.setAttribute("listagrupos", listagrupos);
        request.setAttribute("accion", "Agregar");
        request.setAttribute("sexos", Caballo.SEXOS);
        request.setAttribute("estados", Caballo.ESTADOS);
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
            request.setAttribute("imagenCaballo", this.obtenerImagen(g));
            request.setAttribute("grupo", g.getGrupo_de_caballos());
            request.setAttribute("nombregrupo", g.getGrupo_de_caballos().getNombre());
            request.setAttribute("caballo", g);
            request.setAttribute("helper", helper);
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
            List<Inoculo> listainoculos = dao.ObtenerInoculosCaballo(id_caballo);
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
        request.setAttribute("listagrupos", listagrupos);
        request.setAttribute("caballo", caballo);
        request.setAttribute("imagenCaballo", this.obtenerImagen(caballo));
        request.setAttribute("accion", "Editar");
        request.setAttribute("sexos", Caballo.SEXOS);
        request.setAttribute("estados", Caballo.ESTADOS);
        request.setAttribute("listaEventosRestantes", listaeventosrestantes);
        request.setAttribute("listaEventos", listaeventos);
        redireccionar(request, response, redireccion);
    }

    protected void postAgregareditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String redireccion = "Caballo/index.jsp";
        boolean resultado = false;
        try {
            Caballo c = new Caballo();
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            c = construirObjeto(items);
            
            if (c.isAccion()){
                resultado = dao.insertarCaballo(c);

                HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
                if (resultado) {
                    BitacoraDAO bitacora = new BitacoraDAO();
                    bitacora.setBitacora(c.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CABALLO, request.getRemoteAddr());
                    if (c.getFotografia()!=null){
                        ByteArrayInputStream bais = new ByteArrayInputStream(c.getFotografia());
                        dao.insertarImagen(bais, c.getId_caballo(),c.getFotografiaTamano());
                    }
                    request.setAttribute("mensaje", helper.mensajeDeExito("Caballo agregado correctamente"));
                    redireccion = "Caballo/index.jsp";
                }
                else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Caballo no pudo ser agregado. El número de caballo ya se encuentra en uso."));
                    request.setAttribute("accion", "Agregar");
                    request.setAttribute("caballo", c);
                }
                request.setAttribute("listaCaballos", dao.obtenerCaballos());
                redireccionar(request, response, redireccion);
            }else{
                resultado = dao.editarCaballo(c);

                HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
                if (resultado) {
                    //Funcion que genera la bitacora
                    BitacoraDAO bitacora = new BitacoraDAO();
                    bitacora.setBitacora(c.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CABALLO, request.getRemoteAddr());
                    //*----------------------------*
                    if (c.getFotografia()!=null){
                        ByteArrayInputStream bais = new ByteArrayInputStream(c.getFotografia());
                        dao.insertarImagen(bais, c.getId_caballo(),c.getFotografiaTamano());
                        
                    }
                    request.setAttribute("mensaje", helper.mensajeDeExito("Caballo editado correctamente"));
                    redireccion = "Caballo/index.jsp";
                }
                else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Caballo no pudo ser agregado. El número de caballo ya se encuentra en uso."));
                    request.setAttribute("accion", "Editar");
                    request.setAttribute("caballo", c);
                }
                request.setAttribute("listaCaballos", dao.obtenerCaballos());
                redireccionar(request, response, redireccion);
            }
        }catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }

    }

    protected void postAgregarpeso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String redireccion = "Caballo/Ver.jsp";
        HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
        Peso p = new Peso();
        int id_caballo = 0;
        
        try {
            id_caballo = Integer.parseInt(request.getParameter("id_caballo_peso"));
            p.setId_caballo(id_caballo);
            p.setFecha(helper_fechas.formatearFecha(request.getParameter("fecha_pesaje")));
            p.setPeso(Float.parseFloat(request.getParameter("peso")));
        } catch(ParseException p_ex) {
            
        }
        
        try {
            if (dao.agregarPeso(p)) {
                request.setAttribute("mensaje", helper.mensajeDeExito("Peso registrado correctamente."));
            } else {
                throw new SIGIPROException("Error al registrar el peso.");
            }
        } catch(SIGIPROException sig_ex){
            request.setAttribute("mensaje", helper.mensajeDeError(redireccion));
        }
        
        try {
            Caballo g = dao.obtenerCaballo(id_caballo);
            request.setAttribute("imagenCaballo", this.obtenerImagen(g));
            request.setAttribute("grupo", g.getGrupo_de_caballos());
            request.setAttribute("nombregrupo", g.getGrupo_de_caballos().getNombre());
            request.setAttribute("caballo", g);
            request.setAttribute("helper", helper);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Caballo construirObjeto(List<FileItem> items)
    {
        Caballo c = new Caballo();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        for (FileItem item : items){
            if (item.isFormField()){
                
                String fieldName = item.getFieldName();
                String fieldValue = item.getString();
                switch (fieldName){
                    case "id_caballo":
                        c.setId_caballo(Integer.parseInt(fieldValue));
                        break;
                    case "grupodecaballo":
                        GrupoDeCaballosDAO grupodecaballosdao = new GrupoDeCaballosDAO();  
                        String grupo = fieldValue;
                        GrupoDeCaballos grupodecaballo;
                        if (grupo.equals("")) {
                            grupodecaballo = null;
                        }
                        else {
                            grupodecaballo = grupodecaballosdao.obtenerGrupoDeCaballos(Integer.parseInt(grupo));
                        }
                        c.setGrupo_de_caballos(grupodecaballo);
                        break;
                    case "fecha_ingreso":
                        java.util.Date fecha_ingreso;
                        java.sql.Date fecha_ingresoSQL;
                        try{
                            fecha_ingreso = formatoFecha.parse(fieldValue);
                            fecha_ingresoSQL = new java.sql.Date(fecha_ingreso.getTime());
                            c.setFecha_ingreso(fecha_ingresoSQL);
                        }catch (ParseException ex){
                            
                        }
                        break;
                    case "fecha_nacimiento":
                        java.util.Date fecha_nacimiento;
                        java.sql.Date fecha_nacimientoSQL;
                        try{
                            fecha_nacimiento = formatoFecha.parse(fieldValue);
                            fecha_nacimientoSQL = new java.sql.Date(fecha_nacimiento.getTime());
                            c.setFecha_nacimiento(fecha_nacimientoSQL);
                        }catch (ParseException ex){
                            
                        }
                        break;
                    case "numero_caballo":
                        if (fieldValue != null){
                            c.setNumero(Integer.parseInt(fieldValue));
                        }
                        break;
                    case "nombre":
                        c.setNombre(fieldValue);
                        break;
                    case "numero_microchip":
                        c.setNumero_microchip(fieldValue);
                        break;
                    case "sexo":
                        c.setSexo(fieldValue);
                        break;
                    case "color":
                        c.setColor(fieldValue);
                        break;
                    case "otras_sennas":
                        c.setOtras_sennas(fieldValue);
                        break;
                    case "estado":
                        c.setEstado(fieldValue);
                        break;
                    case "accion":
                        if (fieldValue.equals("Agregar"))
                            c.setAccion(true);
                        else
                            c.setAccion(false);
                        break;
                }
            }else{
                // Process form file field (input type="file").
                byte[] data = item.get();
                long size = item.getSize();
                if (size==0){
                    c.setFotografia(null);
                    c.setFotografiaTamano(0);
                }else{
                    c.setFotografia(data);
                    c.setFotografiaTamano(size);
                }
            }   
        }

        return c;
    }

    private String obtenerImagen(Caballo c)
    {
        if (c.getFotografia() != null) {
            return "data:image/jpeg;base64," + Base64.encodeBytes(c.getFotografia());
        }
        else {
            return "";
        }
    }

    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        List<String> lista_acciones;
        if (accionHTTP.equals("get")) {
            lista_acciones = accionesGet;
        }
        else {
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
