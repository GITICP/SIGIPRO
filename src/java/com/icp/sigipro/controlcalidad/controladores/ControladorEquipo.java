/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.EquipoDAO;
import com.icp.sigipro.controlcalidad.dao.TipoEquipoDAO;
import com.icp.sigipro.controlcalidad.modelos.CertificadoEquipo;
import com.icp.sigipro.controlcalidad.modelos.Equipo;
import com.icp.sigipro.controlcalidad.modelos.TipoEquipo;
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
@WebServlet(name = "ControladorEquipo", urlPatterns = {"/ControlCalidad/Equipo"})
public class ControladorEquipo extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {520, 521, 522, 523, 524};
    //-----------------
    private final EquipoDAO dao = new EquipoDAO();
    private final TipoEquipoDAO tipoequipodao = new TipoEquipoDAO();

    protected final Class clase = ControladorEquipo.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("eliminar");
            add("editar");
            add("certificado");
            add("eliminarcertificado");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregareditar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getCertificado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        validarPermisosMultiple(permisos, request);

        int id_certificado_equipo = Integer.parseInt(request.getParameter("id_certificado_equipo"));
        String equipo = request.getParameter("nombre");
        CertificadoEquipo certificado = dao.obtenerCertificado(id_certificado_equipo);

        String filename = certificado.getPath();
        File file = new File(filename);

        if (file.exists()) {
            ServletContext ctx = getServletContext();
            InputStream fis = new FileInputStream(file);
            String mimeType = ctx.getMimeType(file.getAbsolutePath());

            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
            response.setContentLength((int) file.length());
            String nombre = "certificado-" + equipo + "." + this.getFileExtension(filename);
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
        
        validarPermiso(520, request);

        String redireccion = "Equipo/Agregar.jsp";
        Equipo e = new Equipo();
        List<TipoEquipo> tipoequipos = tipoequipodao.obtenerTipoEquipos();
        request.setAttribute("equipo", e);
        request.setAttribute("tipoequipos", tipoequipos);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Equipo/index.jsp";
        List<Equipo> equipos = dao.obtenerEquipos();
        request.setAttribute("listaEquipos", equipos);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Equipo/Ver.jsp";
        int id_equipo = Integer.parseInt(request.getParameter("id_equipo"));
        try {
            Equipo e = dao.obtenerEquipo(id_equipo);
            request.setAttribute("equipo", e);
            request.setAttribute("certificados", e.getCertificados());
            redireccionar(request, response, redireccion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(522, request);
        String redireccion = "Equipo/Editar.jsp";
        int id_equipo = Integer.parseInt(request.getParameter("id_equipo"));
        Equipo equipo = dao.obtenerEquipo(id_equipo);
        List<TipoEquipo> tipoequipos = tipoequipodao.obtenerTipoEquipos();
        request.setAttribute("tipoequipos", tipoequipos);
        request.setAttribute("equipo", equipo);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(523, request);
        int id_equipo = Integer.parseInt(request.getParameter("id_equipo"));
        boolean resultado = false;
        try {
            resultado = dao.eliminarEquipo(id_equipo);
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_equipo, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_EQUIPO, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Equipo eliminado correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Equipo no pudo ser eliminado ya que tiene certificados asociados."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Equipo no pudo ser eliminado ya que tiene certificados asociados."));
            this.getIndex(request, response);
        }

    }

    protected void getEliminarcertificado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(521, request);
        int id_certificado_equipo = Integer.parseInt(request.getParameter("id_certificado_equipo"));
        String certificado = dao.obtenerCertificado(id_certificado_equipo).getPath();
        boolean resultado = false;
        try {
            resultado = dao.eliminarCertificado(id_certificado_equipo);
            if (resultado) {
                File archivo = new File(certificado);
                archivo.delete();
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_certificado_equipo, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CERTIFICADOEQUIPO, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Certificado de Equipo eliminado correctamente"));

            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Certificado de Equipo no pudo ser eliminado."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Certificado de Equipo no pudo ser eliminado."));
            this.getIndex(request, response);
        }

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregareditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = false;
        try {
            //Se crea el Path en la carpeta del Proyecto
            String fullPath = helper_archivos.obtenerDireccionArchivos();
            String ubicacion = new File(fullPath).getPath() + File.separatorChar + "Documentos" + File.separatorChar + "Equipo" + File.separatorChar + "Certificados";
            //-------------------------------------------
            //Crea los directorios si no estan creados aun
            this.crearDirectorio(ubicacion);
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            Equipo e = construirObjeto(items, request, ubicacion);

            if (e.getId_equipo() == 0) {
                resultado = dao.insertarEquipo(e);
                if (resultado) {
                    request.setAttribute("mensaje", helper.mensajeDeExito("Equipo agregado correctamente"));
                    //Funcion que genera la bitacora
                    bitacora.setBitacora(e.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_EQUIPO, request.getRemoteAddr());
                    //*----------------------------*
                    for (CertificadoEquipo cert : e.getCertificados()) {
                        resultado = dao.insertarCertificado(cert, e.getId_equipo());
                        if (resultado) {
                            //Funcion que genera la bitacora
                            bitacora.setBitacora(cert.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CERTIFICADOEQUIPO, request.getRemoteAddr());
                            //*----------------------------*
                        }
                    }
                    this.getIndex(request, response);
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Equipo no pudo ser agregado. Inténtelo de nuevo."));
                    this.getAgregar(request, response);
                }
            } else if (e.getCertificados().isEmpty()) {
                resultado = dao.editarEquipo(e);
                if (resultado) {
                    //Funcion que genera la bitacora
                    bitacora.setBitacora(e.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_EQUIPO, request.getRemoteAddr());
                    //*----------------------------*
                    request.setAttribute("mensaje", helper.mensajeDeExito("Equipo editado correctamente"));
                    this.getIndex(request, response);
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Equipo no pudo ser editado. Inténtelo de nuevo."));
                    request.setAttribute("id_equipo", e.getId_equipo());
                    this.getEditar(request, response);
                }
            } else {
                for (CertificadoEquipo cert : e.getCertificados()) {
                    resultado = dao.insertarCertificado(cert, e.getId_equipo());
                    if (resultado) {
                        request.setAttribute("mensaje", helper.mensajeDeExito("Certificado de Equipo agregado correctamente"));
                        //Funcion que genera la bitacora
                        bitacora.setBitacora(cert.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CERTIFICADOEQUIPO, request.getRemoteAddr());
                        //*----------------------------*
                        this.getIndex(request, response);
                    }
                }
            }
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private Equipo construirObjeto(List<FileItem> items, HttpServletRequest request, String ubicacion) {
        Equipo e = new Equipo();
        List<CertificadoEquipo> certificados = new ArrayList<CertificadoEquipo>();
        e.setCertificados(certificados);
        CertificadoEquipo cert = new CertificadoEquipo();
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
                    case "id_equipo_certificado":
                        e.setId_equipo(Integer.parseInt(fieldValue));
                        break;
                    case "nombre":
                        e.setNombre(fieldValue);
                        break;
                    case "descripcion":
                        e.setDescripcion(fieldValue);
                        break;
                    case "id_equipo":
                        int id_equipo = Integer.parseInt(fieldValue);
                        e.setId_equipo(id_equipo);
                        break;
                    case "tipo_equipo":
                        int id_tipo_equipo = Integer.parseInt(fieldValue);
                        TipoEquipo tipo_equipo = new TipoEquipo();
                        tipo_equipo.setId_tipo_equipo(id_tipo_equipo);
                        e.setTipo_equipo(tipo_equipo);
                        break;
                }
            } else {
                try {
                    if (item.getSize() != 0) {
                        java.sql.Date date = new java.sql.Date(new Date().getTime());
                        cert.setFecha_certificado(date);
                        this.crearDirectorio(ubicacion);
                        //Creacion del nombre
                        Date dNow = new Date();
                        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmm");
                        String fecha = ft.format(dNow);
                        String extension = this.getFileExtension(item.getName());
                        String nombre = e.getNombre() + "-" + fecha + "." + extension;
                        //---------------------
                        File archivo = new File(ubicacion, nombre);
                        item.write(archivo);
                        cert.setPath(archivo.getAbsolutePath());
                        e.getCertificados().add(cert);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
        return e;
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
