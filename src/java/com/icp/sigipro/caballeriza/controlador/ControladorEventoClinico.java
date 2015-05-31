/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.controlador;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.caballeriza.dao.EventoClinicoDAO;
import com.icp.sigipro.caballeriza.dao.GrupoDeCaballosDAO;
import com.icp.sigipro.caballeriza.dao.TipoEventoDAO;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.EventoClinico;
import com.icp.sigipro.caballeriza.modelos.GrupoDeCaballos;
import com.icp.sigipro.caballeriza.modelos.TipoEvento;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.serpentario.modelos.Especie;
import com.icp.sigipro.serpentario.modelos.Serpiente;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Walter
 */
@WebServlet(name = "ControladorEventoClinico", urlPatterns = {"/Caballeriza/EventoClinico"})
public class ControladorEventoClinico extends SIGIPROServlet {

    private final int[] permisos = {1, 55, 56};
    private EventoClinicoDAO dao = new EventoClinicoDAO();

    protected final Class clase = ControladorEventoClinico.class;
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
            add("agregareditar");
        }
    };

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(55, listaPermisos);

        String redireccion = "EventoClinico/Agregar.jsp";
        EventoClinico c = new EventoClinico();
        TipoEventoDAO tipoeventodao = new TipoEventoDAO();
        GrupoDeCaballosDAO gdcDAO = new GrupoDeCaballosDAO();
        UsuarioDAO usr_dao = new UsuarioDAO();
        List<GrupoDeCaballos> grupos_caballos = gdcDAO.obtenerGruposDeCaballosConCaballos();
        List<TipoEvento> listatipos = tipoeventodao.obtenerTiposEventos();
        List<Usuario> lista_usuarios = usr_dao.obtenerUsuariosSeccion(6, 1);
        request.setAttribute("helper", HelpersHTML.getSingletonHelpersHTML());
        request.setAttribute("evento", c);
        request.setAttribute("grupos_caballos", grupos_caballos);
        request.setAttribute("imagenEvento", c.getImagen_ver());
        request.setAttribute("usuarios_cab_prod", lista_usuarios);
        request.setAttribute("listatipos", listatipos);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "EventoClinico/index.jsp";
        List<EventoClinico> eventosclinicos = dao.obtenerEventosClinicos();
        request.setAttribute("listaEventosClinicos", eventosclinicos);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "EventoClinico/Ver.jsp";
        int id_evento = Integer.parseInt(request.getParameter("id_evento"));
        try {
            EventoClinico g = dao.obtenerEventoClinico(id_evento);
            List<Caballo> listacaballos = dao.obtenerCaballosEvento(id_evento);
            request.setAttribute("imagenEvento", g.getImagen_ver());
            request.setAttribute("caballos", listacaballos);
            request.setAttribute("eventoclinico", g);
            redireccionar(request, response, redireccion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(56, listaPermisos);
        String redireccion = "EventoClinico/Editar.jsp";
        int id_evento = Integer.parseInt(request.getParameter("id_evento"));
        EventoClinico eventoclinico = dao.obtenerEventoClinicoConCaballos(id_evento);
        GrupoDeCaballosDAO gdcDAO = new GrupoDeCaballosDAO();
        List<GrupoDeCaballos> grupos_caballos = gdcDAO.obtenerGruposDeCaballosConCaballos();
        TipoEventoDAO tipodao = new TipoEventoDAO();
        List<TipoEvento> listatipos = tipodao.obtenerTiposEventos();
        UsuarioDAO usr_dao = new UsuarioDAO();
        List<Usuario> lista_usuarios = usr_dao.obtenerUsuariosSeccion(6, 1);
        request.setAttribute("usuarios_cab_prod", lista_usuarios);
        request.setAttribute("listatipos", listatipos);
        request.setAttribute("imagenEvento", eventoclinico.getImagen_ver());
        request.setAttribute("evento", eventoclinico);
        request.setAttribute("grupos_caballos", grupos_caballos);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);
    }

    protected void postAgregareditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redireccion = "Serpiente/index.jsp";
        boolean resultado = false;
        try {
            EventoClinico ec = new EventoClinico();
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            ec = construirObjeto(items);

            if (ec.isAccion()) {
                resultado = dao.insertarEventoClinico(ec, ec.getId_caballos());
                if (resultado) {
                    //Funcion que genera la bitacora
                    BitacoraDAO bitacora = new BitacoraDAO();
                    bitacora.setBitacora(ec.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_EVENTO_CLINICO, request.getRemoteAddr());
                    //*----------------------------*
                    if (ec.getImagen() != null) {
                        ByteArrayInputStream bais = new ByteArrayInputStream(ec.getImagen());
                        dao.insertarImagen(bais, ec.getId_evento(), ec.getImagen_tamano());
                    }
                    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
                    request.setAttribute("mensaje", helper.mensajeDeExito("Evento Clínico agregado correctamente"));
                    redireccion = "EventoClinico/index.jsp";
                } else {
                    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
                    request.setAttribute("mensaje", helper.mensajeDeError("Evento Clínico no pudo ser agregado correctamente."));
                    redireccion = "EventoClinico/index.jsp";
                }
                request.setAttribute("listaEventosClinicos", dao.obtenerEventosClinicos());
                redireccionar(request, response, redireccion);

            } else {
                resultado = dao.editarEventoClinico(ec, ec.getId_caballos());
                if (resultado) {
                    //Funcion que genera la bitacora
                    BitacoraDAO bitacora = new BitacoraDAO();
                    bitacora.setBitacora(ec.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_EVENTO_CLINICO, request.getRemoteAddr());
                    //*----------------------------*
                    if (ec.getImagen() != null) {
                        ByteArrayInputStream bais = new ByteArrayInputStream(ec.getImagen());
                        dao.insertarImagen(bais, ec.getId_evento(), ec.getImagen_tamano());
                    }
                    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
                    request.setAttribute("mensaje", helper.mensajeDeExito("Evento Clínico editado correctamente"));
                    redireccion = "EventoClinico/index.jsp";
                } else {
                    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
                    request.setAttribute("mensaje", helper.mensajeDeError("Evento Clínico no pudo ser editado correctamente."));
                    redireccion = "EventoClinico/index.jsp";
                }
                request.setAttribute("listaEventosClinicos", dao.obtenerEventosClinicos());
                redireccionar(request, response, redireccion);

            }

        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        } catch (SIGIPROException ex) {
            ex.printStackTrace();
        }

    }

    private EventoClinico construirObjeto(List<FileItem> items) throws SIGIPROException {
        EventoClinico ec = new EventoClinico();
        TipoEventoDAO tipoeventodao = new TipoEventoDAO();
        ec.setId_caballos(new ArrayList<String>());
        for (FileItem item : items) {
            System.out.println(item.getFieldName());
            if (item.isFormField()) {
                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
                String fieldName = item.getFieldName();
                System.out.println(item.getString());

                String fieldValue;
                try {
                    fieldValue = item.getString("UTF-8").trim();
                } catch (UnsupportedEncodingException ex) {
                    fieldValue = item.getString();
                }
                switch (fieldName) {
                    case "id_evento":
                        int id_evento = Integer.parseInt(fieldValue);
                        ec.setId_evento(id_evento);
                        break;
                    case "descripcion":
                        String descripcion = fieldValue;
                        ec.setDescripcion(descripcion);
                        break;
                    case "observaciones":
                        String observaciones = fieldValue;
                        ec.setObservaciones(observaciones);
                        break;
                    case "accion":
                        if (fieldValue.equals("Agregar")) {
                            ec.setAccion(true);
                        } else {
                            ec.setAccion(false);
                        }
                        break;
                    case "fecha":
                        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                        java.util.Date fecha;
                        java.sql.Date fechaSQL;
                        try {
                            fecha = formatoFecha.parse(fieldValue);
                            fechaSQL = new java.sql.Date(fecha.getTime());
                            ec.setFecha(fechaSQL);
                        } catch (ParseException ex) {

                        }
                        break;
                    case "tipoevento":
                        String[] tiposeleccionado;
                        tiposeleccionado = fieldValue.split(",");
                        ec.setTipo_evento(tipoeventodao.obtenerTipoEvento(Integer.parseInt(tiposeleccionado[0])));
                        break;
                    case "responsable":
                        Usuario responsable = new Usuario();
                        responsable.setId_usuario(Integer.parseInt(fieldValue));
                        ec.setResponsable(responsable);
                        break;
                    case "caballos":
                        List<String> id_caballos = ec.getId_caballos();
                        id_caballos.add(fieldValue);
                        ec.setId_caballos(id_caballos);
                }
            } else {
                // Process form file field (input type="file").
                byte[] data = item.get();
                long size = item.getSize();
                if (size == 0) {
                    ec.setImagen(null);
                    ec.setImagen_tamano(0);
                } else {
                    ec.setImagen(data);
                    ec.setImagen_tamano(size);
                }
            }
        }
        return ec;
    }

    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<String> lista_acciones;
        if (accionHTTP.equals("get")) {
            lista_acciones = accionesGet;
        } else {
            lista_acciones = accionesPost;
        }
        if (lista_acciones.contains(accion.toLowerCase())) {
            String nombreMetodo = accionHTTP + Character.toUpperCase(accion.charAt(0)) + accion.substring(1);
            Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        } else {
            Method metodo = clase.getDeclaredMethod(accionHTTP + "Index", HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        }
    }

    @Override
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
