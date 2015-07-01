/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.ReactivoDAO;
import com.icp.sigipro.controlcalidad.dao.TipoReactivoDAO;
import com.icp.sigipro.controlcalidad.modelos.CertificadoReactivo;
import com.icp.sigipro.controlcalidad.modelos.Reactivo;
import com.icp.sigipro.controlcalidad.modelos.TipoReactivo;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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
@WebServlet(name = "ControladorReactivo", urlPatterns = {"/ControlCalidad/Reactivo"})
public class ControladorReactivo extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {1, 530};
    //-----------------
    private ReactivoDAO dao = new ReactivoDAO();
    private TipoReactivoDAO tiporeactivodao = new TipoReactivoDAO();

    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
    BitacoraDAO bitacora = new BitacoraDAO();

    protected final Class clase = ControladorReactivo.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("eliminar");
            add("editar");
            add("certificado");
            add("preparacion");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregareditar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getCertificado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        int id_certificado_reactivo = Integer.parseInt(request.getParameter("id_certificado_reactivo"));
        String reactivo = request.getParameter("nombre");
        CertificadoReactivo certificado = dao.obtenerCertificado(id_certificado_reactivo);

        String filename = certificado.getPath();
        File file = new File(filename);

        if (file.exists()) {
            ServletContext ctx = getServletContext();
            InputStream fis = new FileInputStream(file);
            String mimeType = ctx.getMimeType(file.getAbsolutePath());

            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
            response.setContentLength((int) file.length());
            String nombre = "certificado-" + reactivo + "." + this.getFileExtension(filename);
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

        }

    }

    protected void getPreparacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        int id_reactivo = Integer.parseInt(request.getParameter("id_reactivo"));
        Reactivo reactivo = dao.obtenerReactivo(id_reactivo);

        String filename = reactivo.getPreparacion();
        File file = new File(filename);

        if (file.exists()) {
            ServletContext ctx = getServletContext();
            InputStream fis = new FileInputStream(file);
            String mimeType = ctx.getMimeType(file.getAbsolutePath());

            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
            response.setContentLength((int) file.length());
            String nombre = "preparacion-" + reactivo.getNombre() + "." + this.getFileExtension(filename);
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

        }

    }

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(530, listaPermisos);

        String redireccion = "Reactivo/Agregar.jsp";
        Reactivo r = new Reactivo();
        List<TipoReactivo> tiporeactivos = tiporeactivodao.obtenerTipoReactivos();
        request.setAttribute("reactivo", r);
        request.setAttribute("tiporeactivos", tiporeactivos);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Reactivo/index.jsp";
        List<Reactivo> reactivos = dao.obtenerReactivos();
        request.setAttribute("listaReactivos", reactivos);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Reactivo/Ver.jsp";
        int id_reactivo = Integer.parseInt(request.getParameter("id_reactivo"));
        try {
            Reactivo r = dao.obtenerReactivo(id_reactivo);
            request.setAttribute("reactivo", r);
            request.setAttribute("certificados", r.getCertificados());
            redireccionar(request, response, redireccion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(530, listaPermisos);
        String redireccion = "Reactivo/Editar.jsp";
        int id_reactivo = Integer.parseInt(request.getParameter("id_reactivo"));
        Reactivo reactivo = dao.obtenerReactivo(id_reactivo);
        List<TipoReactivo> tiporeactivos = tiporeactivodao.obtenerTipoReactivos();
        request.setAttribute("tiporeactivos", tiporeactivos);
        request.setAttribute("reactivo", reactivo);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(530, listaPermisos);
        int id_reactivo = Integer.parseInt(request.getParameter("id_reactivo"));
        boolean resultado = false;
        try {
            resultado = dao.eliminarReactivo(id_reactivo);
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_reactivo, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_REACTIVO, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Reactivo eliminado correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Reactivo no pudo ser eliminado ya que tiene certificados asociados."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Reactivo no pudo ser eliminado ya que tiene certificados asociados."));
            this.getIndex(request, response);
        }

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregareditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = false;
        try {
            //Se crea el Path en la carpeta del Proyecto
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            String pathArr[] = fullPath.split("/WEB-INF/classes/");
            fullPath = pathArr[0];
            String ubicacion = new File(fullPath).getPath() + File.separatorChar + "Documentos" + File.separatorChar + "Reactivo";
            //-------------------------------------------
            //Crea los directorios si no estan creados aun
            this.crearDirectorio(ubicacion);
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            Reactivo r = construirObjeto(items, request, ubicacion);

            if (r.getId_reactivo() == 0) {
                resultado = dao.insertarReactivo(r);
                if (resultado) {
                    request.setAttribute("mensaje", helper.mensajeDeExito("Reactivo agregado correctamente"));
                    //Funcion que genera la bitacora
                    bitacora.setBitacora(r.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_REACTIVO, request.getRemoteAddr());
                    //*----------------------------*
                    for (CertificadoReactivo cert : r.getCertificados()) {
                        resultado = dao.insertarCertificado(cert, r.getId_reactivo());
                        if (resultado) {
                            //Funcion que genera la bitacora
                            bitacora.setBitacora(cert.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CERTIFICADOREACTIVO, request.getRemoteAddr());
                            //*----------------------------*
                        }
                    }
                    this.getIndex(request, response);
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Reactivo no pudo ser agregado. Inténtelo de nuevo."));
                    this.getAgregar(request, response);
                }
            } else if (r.getCertificados().isEmpty()) {
                resultado = dao.editarReactivo(r);
                if (resultado) {
                    //Funcion que genera la bitacora
                    bitacora.setBitacora(r.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_REACTIVO, request.getRemoteAddr());
                    //*----------------------------*
                    request.setAttribute("mensaje", helper.mensajeDeExito("Reactivo editado correctamente"));
                    this.getIndex(request, response);
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Reactivo no pudo ser editado. Inténtelo de nuevo."));
                    request.setAttribute("id_reactivo", r.getId_reactivo());
                    this.getEditar(request, response);
                }
            } else {
                for (CertificadoReactivo cert : r.getCertificados()) {
                    resultado = dao.insertarCertificado(cert, r.getId_reactivo());
                    if (resultado) {
                        request.setAttribute("mensaje", helper.mensajeDeExito("Certificado de Reactivo agregado correctamente"));
                        //Funcion que genera la bitacora
                        bitacora.setBitacora(cert.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CERTIFICADOREACTIVO, request.getRemoteAddr());
                        //*----------------------------*
                        this.getIndex(request, response);
                    }
                }
            }
        } catch (FileUploadException r) {
            throw new ServletException("Cannot parse multipart request.", r);
        }

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private Reactivo construirObjeto(List<FileItem> items, HttpServletRequest request, String ubicacion) {
        Reactivo r = new Reactivo();
        List<CertificadoReactivo> certificados = new ArrayList<CertificadoReactivo>();
        r.setCertificados(certificados);
        CertificadoReactivo cert = new CertificadoReactivo();
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
                    case "id_reactivo_certificado":
                        r.setId_reactivo(Integer.parseInt(fieldValue));
                        break;
                    case "nombre":
                        r.setNombre(fieldValue);
                        break;
                    case "id_reactivo":
                        int id_reactivo = Integer.parseInt(fieldValue);
                        r.setId_reactivo(id_reactivo);
                        break;
                    case "tipo_reactivo":
                        int id_tipo_reactivo = Integer.parseInt(fieldValue);
                        TipoReactivo tipo_reactivo = new TipoReactivo();
                        tipo_reactivo.setId_tipo_reactivo(id_tipo_reactivo);
                        r.setTipo_reactivo(tipo_reactivo);
                        break;
                }
            } else {
                try {
                    if (item.getSize() != 0) {
                        Date dNow = new Date();
                        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmm");
                        String fecha = ft.format(dNow);
                        String extension = this.getFileExtension(item.getName());
                        String nombre = r.getNombre() + "-" + fecha + "." + extension;
                        switch (item.getFieldName()) {
                            case "preparacion":
                                String ubicacionPrep = ubicacion + File.separatorChar + "Preparacion";
                                this.crearDirectorio(ubicacionPrep);
                                File archivoPrep = new File(ubicacionPrep, nombre);
                                item.write(archivoPrep);
                                r.setPreparacion(archivoPrep.getAbsolutePath());
                                break;
                            case "certificado":
                                String ubicacionCert = ubicacion + File.separatorChar + "Certificados";
                                this.crearDirectorio(ubicacionCert);
                                java.sql.Date date = new java.sql.Date(new Date().getTime());
                                cert.setFecha_certificado(date);
                                File archivoCert = new File(ubicacionCert, nombre);
                                item.write(archivoCert);
                                cert.setPath(archivoCert.getAbsolutePath());
                                r.getCertificados().add(cert);
                        }
                    }else if (item.getFieldName().equals("preparacion")){
                        r.setPreparacion("");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
        return r;
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
