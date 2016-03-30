/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;

import com.icp.sigipro.ventas.dao.Reunion_produccionDAO;
import com.icp.sigipro.ventas.modelos.Reunion_produccion;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.ventas.dao.Participantes_reunionDAO;
import com.icp.sigipro.ventas.modelos.Participantes_reunion;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorReunion_produccion", urlPatterns = {"/Ventas/ReunionProduccion"})
public class ControladorReunion_produccion extends SIGIPROServlet {

    private final int[] permisos = {701, 702, 1};
    private final Reunion_produccionDAO dao = new Reunion_produccionDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();
    private final Participantes_reunionDAO pDAO = new Participantes_reunionDAO();
    private String listaProductos = "";

    protected final Class clase = ControladorReunion_produccion.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("archivo");
            add("editar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregareditar");
            add("eliminar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getArchivo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        validarPermisosMultiple(permisos, request);

        int id_reunion = Integer.parseInt(request.getParameter("id_reunion"));
        Reunion_produccion reunion = dao.obtenerReunion_produccion(id_reunion);

        int documento = Integer.parseInt(request.getParameter("documento"));
        String filename = "";
        switch(documento){
            case 1:
                filename = reunion.getMinuta();
                break;
            case 2:
                filename = reunion.getMinuta2();
                break;
        }
        File file = new File(filename);

        if (file.exists()) {
            ServletContext ctx = getServletContext();
            InputStream fis = new FileInputStream(file);
            String mimeType = ctx.getMimeType(file.getAbsolutePath());

            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
            response.setContentLength((int) file.length());
            String nombre = "minuta-" + reunion.getId_reunion() + "." + this.getFileExtension(filename);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre + "\"");

            ServletOutputStream os = response.getOutputStream();
            byte[] bufferData = new byte[1024];
            int read = 0;
            while ((read = fis.read(bufferData)) != -1) {
                os.write(bufferData, 0, read);
            }
            os.flush();
            os.close();
            fis.close();

        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("El archivo no fue encontrado. Actualice la reunión."));
            this.getVer(request, response);
        }

    }
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
       
        String redireccion = "ReunionProduccion/Agregar.jsp";
        Reunion_produccion ds = new Reunion_produccion();
        
        request.setAttribute("usuarios",dao_us.obtenerUsuarios());
        request.setAttribute("reunion", ds);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        List<Reunion_produccion> reuniones = dao.obtenerReuniones_produccion();
        request.setAttribute("listaReuniones", reuniones);
        String redireccion = "ReunionProduccion/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "ReunionProduccion/Ver.jsp";
        int id_reunion = Integer.parseInt(request.getParameter("id_reunion"));
        try {
            Reunion_produccion c = dao.obtenerReunion_produccion(id_reunion);
            List<Participantes_reunion> d = pDAO.obtenerParticipantes(id_reunion);
            request.setAttribute("participantes", d);
            request.setAttribute("reunion", c);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "ReunionProduccion/Editar.jsp";
        int id_reunion = Integer.parseInt(request.getParameter("id_reunion"));
        Reunion_produccion ds = dao.obtenerReunion_produccion(id_reunion);
        List<Participantes_reunion> d = pDAO.obtenerParticipantes(id_reunion);
        request.setAttribute("usuarios",dao_us.obtenerUsuarios());
        request.setAttribute("participantes", d);
        request.setAttribute("reunion", ds);
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregareditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        int resultado = 0;
        try {
            //Se crea el Path en la carpeta del Proyecto
            String fullPath = helper_archivos.obtenerDireccionArchivos();
            String ubicacion = new File(fullPath).getPath() + File.separatorChar + "Documentos" + File.separatorChar + "Reuniones de Produccion" + File.separatorChar + "Minutas";
            //-------------------------------------------
            System.out.println(ubicacion);
            //Crea los directorios si no estan creados aun
            this.crearDirectorio(ubicacion);
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            Reunion_produccion tr = construirObjeto(items, request, ubicacion);

            if (tr.getId_reunion() == 0) { //agregar
                resultado = dao.insertarReunion_produccion(tr);
                
                //System.out.println("listaProductos = "+listaProductos);
                if (listaProductos != null && !(listaProductos.isEmpty()) ) {
                    List<Participantes_reunion> p_i = pDAO.parsearParticipantes(listaProductos, resultado);
                    for (Participantes_reunion i : p_i) {
                        pDAO.insertarParticipantes_reunion(i);
                    }
                }
                if (resultado != 0) {
                    request.setAttribute("mensaje", helper.mensajeDeExito("Reunión de Producción agregada correctamente"));
                    //Funcion que genera la bitacora
                    bitacora.setBitacora(tr.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_REUNION_PRODUCCION, request.getRemoteAddr());
                    //*----------------------------*
                    this.getIndex(request, response);
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Reunión de Producción no pudo ser agregada. Inténtelo de nuevo."));
                    this.getAgregar(request, response);
                }
            } else { //editar
                String archivoViejo = "";
                if (tr.getMinuta().equals("")) {
                    archivoViejo = dao.obtenerReunion_produccion(tr.getId_reunion()).getMinuta();
                }
                //System.out.println("archivoViejo = "+archivoViejo);
                //System.out.println("tr.getMinuta() = "+tr.getMinuta());
                boolean resultado2 = false;
                if (!archivoViejo.equals("")) {
                        tr.setMinuta(archivoViejo);
                        //File archivo = new File(archivoViejo);
                        //archivo.delete();
                    }
                resultado2 = dao.editarReunion_produccion(tr);
                //System.out.println("listaProductos = "+listaProductos);
            if (listaProductos != null && !(listaProductos.isEmpty()) ) {
                List<Participantes_reunion> p_i = pDAO.parsearParticipantes(listaProductos, tr.getId_reunion());
                for (Participantes_reunion i : p_i) {
                    if (!pDAO.esParticipanteReunion(i.getReunion().getId_reunion(), i.getUsuario().getId_usuario())){
                        pDAO.insertarParticipantes_reunion(i);
                        //System.out.println("Producto ingresado.");
                        //e = true;
                    }
                    else{
                        //System.out.println("Producto editado.");
                    }
                }
                pDAO.asegurarReunions_Usuario(p_i, tr.getId_reunion());
            }
            else{
                pDAO.eliminarReunions_Usuario(tr.getId_reunion());
            }
                if (resultado2) {
                    
                    //Funcion que genera la bitacora
                    bitacora.setBitacora(tr.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_REUNION_PRODUCCION, request.getRemoteAddr());
                    //*----------------------------*
                    request.setAttribute("mensaje", helper.mensajeDeExito("Reunión de Producción editada correctamente"));
                    this.getIndex(request, response);
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Reunión de Producción no pudo ser editada. Inténtelo de nuevo."));
                    request.setAttribute("id_reunion", tr.getId_reunion());
                    this.getEditar(request, response);
                }
            }
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }

    }   
    
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "ReunionProduccion/index.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String id_reunion = request.getParameter("id_reunion"); 
        try {
            Reunion_produccion reunion_a_eliminar = dao.obtenerReunion_produccion(Integer.parseInt(id_reunion));
            
            resultado = dao.eliminarReunion_produccion(reunion_a_eliminar.getId_reunion());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(reunion_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_REUNION_PRODUCCION, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "ReunionProduccion/index.jsp";
            List<Reunion_produccion> reuniones = dao.obtenerReuniones_produccion();
            request.setAttribute("listaReuniones", reuniones);
            request.setAttribute("mensaje", helper.mensajeDeExito("Reunión de Producción eliminada correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Método del Modelo">
    private Reunion_produccion construirObjeto(List<FileItem> items, HttpServletRequest request, String ubicacion) throws SIGIPROException, ParseException {
        Reunion_produccion tr = new Reunion_produccion();
        int contador_documento = 1;
        for (FileItem item : items) {
            if (item.isFormField()) {
                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
                String fieldName = item.getFieldName();
                String fieldValue;
                try {
                    fieldValue = item.getString("UTF-8").trim();
                } catch (UnsupportedEncodingException ex) {
                    fieldValue = item.getString();
                }
                switch (fieldName) {
                    case "listaProductos":
                        listaProductos = fieldValue;
                        break;
                    case "id_reunion":
                        int id_reunion = Integer.parseInt(fieldValue);
                        tr.setId_reunion(id_reunion);
                        break;
                    case "fecha":
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        java.util.Date result = df.parse(fieldValue);
                        java.sql.Date fechaSQL = new java.sql.Date(result.getTime());
                        tr.setFecha(fechaSQL);
                        break;
                    case "observaciones":
                        tr.setObservaciones(fieldValue);
                        break;
                }
            } else {
                try {
                    if (item.getSize() != 0) {
                        this.crearDirectorio(ubicacion);
                        //Creacion del nombre
                        Date dNow = new Date();
                        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmm");
                        String fecha = ft.format(dNow);
                        String extension = this.getFileExtension(item.getName());
                        String nombre = tr.getId_reunion()+ "-" + fecha + "." + extension;
                        //---------------------
                        File archivo = new File(ubicacion, nombre);
                        item.write(archivo);
                        switch(contador_documento){
                            case 1:
                                tr.setMinuta(archivo.getAbsolutePath());
                                contador_documento += 1;
                                break;
                            case 2:
                                tr.setMinuta2(archivo.getAbsolutePath());
                                contador_documento += 1;
                                break;
                        }
                    } else {
                        switch(contador_documento){
                            case 1:
                                tr.setMinuta("");
                                contador_documento += 1;
                                break;
                            case 2:
                                tr.setMinuta2("");
                                contador_documento += 1;
                                break;
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
        return tr;
    }

    private boolean crearDirectorio(String path) {
        boolean resultado = false;
        File directorio = new File(path);
        if (!directorio.exists()) {
            System.out.println("Creando directorio: " + path);
            resultado = false;
            try {
                directorio.mkdirs();
                resultado = true;
            } catch (SecurityException se) {
                se.printStackTrace();
            }
            if (resultado) {
                System.out.println("Directorio Creado");
            }
        } else {
            resultado = true;
        }
        return resultado;
    }

    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
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

  // </editor-fold>
}
