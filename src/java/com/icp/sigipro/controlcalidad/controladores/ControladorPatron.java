/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.PatronDAO;
import com.icp.sigipro.controlcalidad.modelos.Patron;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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
@WebServlet(name = "ControladorPatron", urlPatterns = {"/ControlCalidad/Patron"})
public class ControladorPatron extends SIGIPROServlet {

    //Por definir
    private final int[] permisos = {571, 572, 573, 574};
    //-----------------
    private final PatronDAO dao = new PatronDAO();

    protected final Class clase = ControladorPatron.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("eliminar");
            add("editar");
            add("certificado");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
            add("editar");
        }
    };

    protected String ubicacion;

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getCertificado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermisosMultiple(permisos, request);

        int id_patron = Integer.parseInt(request.getParameter("id_patron"));
        String equipo = request.getParameter("nombre");

        try {
            Patron p = dao.obtenerPatron(id_patron);

            String filename = p.getCertificado();
            File file = new File(filename);

            if (file.exists()) {
                ServletContext ctx = getServletContext();
                InputStream fis = new FileInputStream(file);
                String mimeType = ctx.getMimeType(file.getAbsolutePath());

                response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
                response.setContentLength((int) file.length());
                String nombre = "certificado-" + p.getNumero_lote() + "." + this.getFileExtension(filename);
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
                request.setAttribute("mensaje", helper.mensajeDeError("El archivo que está intentando descargar fue eliminado o se movió de localización."));
                this.getVer(request, response);
            }
        } catch (SIGIPROException sig_ex) {
            this.getVer(request, response);
        }

    }

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermiso(571, request);

        String redireccion = "Patron/Agregar.jsp";
        Patron p = new Patron();
        request.setAttribute("patron", p);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Patron/index.jsp";
        try {
            List<Patron> patrones = dao.obtenerPatrones();
            request.setAttribute("lista_patrones", patrones);
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }

        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Patron/Ver.jsp";
        int id_patron = Integer.parseInt(request.getParameter("id_patron"));
        try {
            Patron p = dao.obtenerPatron(id_patron);
            request.setAttribute("patron", p);
            redireccionar(request, response, redireccion);
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(572, request);
        String redireccion = "Patron/Editar.jsp";
        request.setAttribute("accion", "Editar");
        int id_patron = Integer.parseInt(request.getParameter("id_patron"));
        try {
            Patron patron = dao.obtenerPatron(id_patron);
            request.setAttribute("patron", patron);
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }

        redireccionar(request, response, redireccion);

    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(573, request);
        int id_patron = Integer.parseInt(request.getParameter("id_patron"));
        boolean resultado;
        try {
            resultado = dao.eliminarPatron(id_patron);
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_patron, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PATRON, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Patrón eliminado correctamente."));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("El patrón que está intentando eliminar no existe."));
            }
            this.getIndex(request, response);
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
            this.getIndex(request, response);
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        validarPermiso(571, request);
        
        Patron patron = construirObjeto(request);

        try {
            this.crearDirectorio();
            dao.insertarPatron(patron);
            bitacora.setBitacora(patron.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PATRON, request.getRemoteAddr());
            request.setAttribute("mensaje", helper.mensajeDeExito("Patrón agregado correctamente."));

        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }

        this.getIndex(request, response);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        validarPermiso(572, request);
        
        Patron patron = construirObjeto(request);

        try {
            this.crearDirectorio();
            patron.setId_patron(Integer.parseInt(obtenerParametro("id_patron")));
            dao.editarPatron(patron);
            bitacora.setBitacora(patron.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PATRON, request.getRemoteAddr());
            request.setAttribute("mensaje", helper.mensajeDeExito("Patrón editado correctamente."));

        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }

        this.getIndex(request, response);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private Patron construirObjeto(HttpServletRequest request) {
        Patron p = new Patron();

        p.setNumero_lote(this.obtenerParametro("num_lote"));
        p.setTipo(this.obtenerParametro("tipo"));

        try {
            p.setFecha_ingreso(helper_fechas.formatearFecha(this.obtenerParametro("fecha_ingreso")));
            p.setFecha_vencimiento(helper_fechas.formatearFecha(this.obtenerParametro("fecha_vencimiento")));
            p.setFecha_inicio_uso(helper_fechas.formatearFecha(this.obtenerParametro("fecha_inicio_uso")));
        } catch (ParseException p_ex) {
            p_ex.printStackTrace();
        }

        p.setLugar_almacenamiento(this.obtenerParametro("lugar_almacenamiento"));
        p.setCondicion_almacenamiento(this.obtenerParametro("condicion_almacenamiento"));
        p.setObservaciones(this.obtenerParametro("observaciones"));

        FileItem archivo = this.obtenerParametroFileItem("certificado");

        try {
            if (archivo.getSize() != 0) {
                //Creación del nombre
                String extension = this.getFileExtension(archivo.getName());
                String fecha = helper_fechas.getFecha_hoy_formateada("yyyyMMdd HHmm");
                String nombre = "certificado-patron - " + p.getNumero_lote() + " - " + fecha + "." + extension;
                //---------------------
                File archivo_final = new File(ubicacion, nombre);
                archivo.write(archivo_final);
                p.setCertificado(archivo_final.getAbsolutePath());
            } else {
                p.setCertificado("");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return p;
    }

    private void crearUbicacion() {
        try {
            //Se crea el Path en la carpeta del Proyecto
            String fullPath = helper_archivos.obtenerDireccionArchivos();
            ubicacion = new File(fullPath).getPath() + File.separatorChar + "Documentos" + File.separatorChar + "Patrones" + File.separatorChar + "Certificados";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean crearDirectorio() {
        boolean resultado = false;
        File directorio = new File(ubicacion);
        if (!directorio.exists()) {
            System.out.println("Creando directorio: " + ubicacion);
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
            if (ServletFileUpload.isMultipartContent(request)) {
                this.obtenerParametros(request);
                accion = this.obtenerParametro("accion");
            }
        }
        if (lista_acciones.contains(accion.toLowerCase())) {
            String nombreMetodo = accionHTTP + Character.toUpperCase(accion.charAt(0)) + accion.substring(1);
            Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class
            );
            metodo.invoke(this, request, response);
        } else {
            Method metodo = clase.getDeclaredMethod(accionHTTP + "Index", HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        }
    }

    private void obtenerParametros(HttpServletRequest request) {
        try {
            String fullPath = helper_archivos.obtenerDireccionArchivos();
            this.ubicacion = new File(fullPath).getPath() + File.separatorChar + "Documentos" + File.separatorChar + "Patron";
            //-------------------------------------------
            //Crea los directorios si no estan creados aun
            this.crearDirectorio();
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            parametros = upload.parseRequest(request);
        } catch (FileUploadException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  // </editor-fold>
}
