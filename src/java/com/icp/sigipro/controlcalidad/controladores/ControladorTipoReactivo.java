/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.TipoReactivoDAO;
import com.icp.sigipro.controlcalidad.modelos.TipoReactivo;
import com.icp.sigipro.core.SIGIPROServlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
 * @author ld.conejo
 */
@WebServlet(name = "ControladorTipoReactivo", urlPatterns = {"/ControlCalidad/TipoReactivo"})
public class ControladorTipoReactivo extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {510, 511, 512, 513};
    //-----------------
    private final TipoReactivoDAO dao = new TipoReactivoDAO();

    protected final Class clase = ControladorTipoReactivo.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("eliminar");
            add("editar");
            add("archivo");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregareditar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getArchivo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);

        int id_tipo_reactivo = Integer.parseInt(request.getParameter("id_tipo_reactivo"));
        TipoReactivo tiporeactivo = dao.obtenerTipoReactivo(id_tipo_reactivo);

        String filename = tiporeactivo.getMachote();
        File file = new File(filename);

        if (file.exists()) {
            ServletContext ctx = getServletContext();
            InputStream fis = new FileInputStream(file);
            String mimeType = ctx.getMimeType(file.getAbsolutePath());

            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
            response.setContentLength((int) file.length());
            String nombre = "machote-" + tiporeactivo.getNombre() + "." + this.getFileExtension(filename);
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
            request.setAttribute("mensaje", helper.mensajeDeError("El archivo no fue encontrado. Actualice el tipo de reactivo."));
            this.getVer(request, response);
        }

    }

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(510, request);

        String redireccion = "TipoReactivo/Agregar.jsp";
        TipoReactivo tr = new TipoReactivo();
        request.setAttribute("tiporeactivo", tr);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "TipoReactivo/index.jsp";
        List<TipoReactivo> tiporeactivos = dao.obtenerTipoReactivos();
        request.setAttribute("listaTipos", tiporeactivos);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermisosMultiple(permisos, request);
        String redireccion = "TipoReactivo/Ver.jsp";
        int id_tipo_reactivo = Integer.parseInt(request.getParameter("id_tipo_reactivo"));
        try {
            TipoReactivo tr = dao.obtenerTipoReactivo(id_tipo_reactivo);
            request.setAttribute("tiporeactivo", tr);
            redireccionar(request, response, redireccion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(511, request);
        String redireccion = "TipoReactivo/Editar.jsp";
        int id_tipo_reactivo = Integer.parseInt(request.getParameter("id_tipo_reactivo"));
        TipoReactivo tiporeactivo = dao.obtenerTipoReactivo(id_tipo_reactivo);
        request.setAttribute("tiporeactivo", tiporeactivo);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(512, request);
        int id_tipo_reactivo = Integer.parseInt(request.getParameter("id_tipo_reactivo"));
        TipoReactivo tiporeactivo = dao.obtenerTipoReactivo(id_tipo_reactivo);
        boolean resultado = false;
        try {
            resultado = dao.eliminarTipoReactivo(id_tipo_reactivo);
            if (resultado) {
                File machote = new File(tiporeactivo.getMachote());
                if (machote.delete()) {
                    //Funcion que genera la bitacora 
                    bitacora.setBitacora(id_tipo_reactivo, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_TIPOREACTIVO, request.getRemoteAddr());
                    //----------------------------
                    request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Reactivo eliminado correctamente"));
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Reactivo eliminado correctamente."));
                }
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Reactivo no pudo ser eliminado ya que tiene reactivos asociados."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Reactivo no pudo ser eliminado ya que tiene reactivos asociados."));
            this.getIndex(request, response);
        }

    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregareditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        boolean resultado = false;
        try {
            //Se crea el Path en la carpeta del Proyecto
            String fullPath = helper_archivos.obtenerDireccionArchivos();
            String ubicacion = new File(fullPath).getPath() + File.separatorChar + "Documentos" + File.separatorChar + "TipoReactivo" + File.separatorChar + "Machotes";
            //-------------------------------------------
            System.out.println(ubicacion);
            //Crea los directorios si no estan creados aun
            this.crearDirectorio(ubicacion);
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            TipoReactivo tr = construirObjeto(items, request, ubicacion);

            if (tr.getId_tipo_reactivo() == 0) {
                resultado = dao.insertarTipoReactivo(tr);
                if (resultado) {
                    request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Reactivo agregado correctamente"));
                    //Funcion que genera la bitacora
                    bitacora.setBitacora(tr.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_TIPOREACTIVO, request.getRemoteAddr());
                    //*----------------------------*
                    this.getIndex(request, response);
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Reactivo no pudo ser agregado. Inténtelo de nuevo."));
                    this.getAgregar(request, response);
                }
            } else {
                String archivoViejo = "";
                if (!tr.getMachote().equals("")) {
                    archivoViejo = dao.obtenerTipoReactivo(tr.getId_tipo_reactivo()).getMachote();
                }
                resultado = dao.editarTipoReactivo(tr);
                if (resultado) {
                    if (!archivoViejo.equals("")) {
                        File archivo = new File(archivoViejo);
                        archivo.delete();
                    }
                    //Funcion que genera la bitacora
                    bitacora.setBitacora(tr.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_TIPOREACTIVO, request.getRemoteAddr());
                    //*----------------------------*
                    request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Reactivo editado correctamente"));
                    this.getIndex(request, response);
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Reactivo no pudo ser editado. Inténtelo de nuevo."));
                    request.setAttribute("id_tipo_reactivo", tr.getId_tipo_reactivo());
                    this.getEditar(request, response);
                }
            }
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }

    }

  // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private TipoReactivo construirObjeto(List<FileItem> items, HttpServletRequest request, String ubicacion) {
        TipoReactivo tr = new TipoReactivo();
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
                    case "nombre":
                        tr.setNombre(fieldValue);
                        break;
                    case "descripcion":
                        tr.setDescripcion(fieldValue);
                        break;
                    case "id_tipo_reactivo":
                        int id_tipo_reactivo = Integer.parseInt(fieldValue);
                        tr.setId_tipo_reactivo(id_tipo_reactivo);
                        break;
                    case "certificable":
                        boolean certificable = Boolean.parseBoolean(fieldValue);
                        tr.setCertificable(certificable);
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
                        String nombre = tr.getNombre() + "-" + fecha + "." + extension;
                        //---------------------
                        File archivo = new File(ubicacion, nombre);
                        item.write(archivo);
                        tr.setMachote(archivo.getAbsolutePath());
                    } else {
                        tr.setMachote("");
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
