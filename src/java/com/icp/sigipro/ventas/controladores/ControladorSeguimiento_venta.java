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

import com.icp.sigipro.ventas.dao.Seguimiento_ventaDAO;
import com.icp.sigipro.ventas.modelos.Seguimiento_venta;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.ventas.dao.ClienteDAO;
import com.icp.sigipro.ventas.dao.FacturaDAO;
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
@WebServlet(name = "ControladorSeguimiento_venta", urlPatterns = {"/Ventas/SeguimientoVenta"})
public class ControladorSeguimiento_venta extends SIGIPROServlet {

    private final Seguimiento_ventaDAO dao = new Seguimiento_ventaDAO();
    private final ClienteDAO cdao = new ClienteDAO();
    private final FacturaDAO fdao = new FacturaDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();

    protected final Class clase = ControladorSeguimiento_venta.class;
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
        int[] permisos = {701, 1};
        validarPermisosMultiple(permisos, request);

        int id_seguimiento = Integer.parseInt(request.getParameter("id_seguimiento"));
        Seguimiento_venta seguimiento = dao.obtenerSeguimiento_venta(id_seguimiento);

        String filename = seguimiento.getDocumento_1();
                
        
        File file = new File(filename);

        if (file.exists()) {
            ServletContext ctx = getServletContext();
            InputStream fis = new FileInputStream(file);
            String mimeType = ctx.getMimeType(file.getAbsolutePath());

            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
            response.setContentLength((int) file.length());
            String nombre = "documento-seguimiento-de-venta-" + seguimiento.getId_seguimiento() + "." + this.getFileExtension(filename);
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
    
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
       
        String redireccion = "SeguimientoVenta/Agregar.jsp";
        Seguimiento_venta ds = new Seguimiento_venta();
        
        List<String> tipos = new ArrayList<String>();
        tipos.add("Llamada");
        tipos.add("Correo");
        tipos.add("Contacto");
        tipos.add("Encuesta de Satisfacción");
        tipos.add("Otro");
        
        request.setAttribute("tipos", tipos);
        request.setAttribute("seguimiento", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("facturas", fdao.obtenerFacturas());
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1,702,703,704,705,706};
        validarPermisos(permisos, listaPermisos);

        List<Seguimiento_venta> seguimientoes = dao.obtenerSeguimientos_venta();
        request.setAttribute("listaSeguimientos", seguimientoes);
        String redireccion = "SeguimientoVenta/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1,702,703,704,705,706};
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "SeguimientoVenta/Ver.jsp";
        int id_seguimiento = Integer.parseInt(request.getParameter("id_seguimiento"));
        try {
            Seguimiento_venta c = dao.obtenerSeguimiento_venta(id_seguimiento);
            request.setAttribute("seguimiento", c);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "SeguimientoVenta/Editar.jsp";
        int id_seguimiento = Integer.parseInt(request.getParameter("id_seguimiento"));
        Seguimiento_venta ds = dao.obtenerSeguimiento_venta(id_seguimiento);
        
        List<String> tipos = new ArrayList<String>();
        tipos.add("Llamada");
        tipos.add("Correo");
        tipos.add("Contacto");
        tipos.add("Encuesta de Satisfacción");
        tipos.add("Otro");
        
        request.setAttribute("tipos", tipos);
        request.setAttribute("seguimiento", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("facturas", fdao.obtenerFacturas());
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregareditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
        int resultado = 0;
        try {
            //Se crea el Path en la carpeta del Proyecto
            String fullPath = helper_archivos.obtenerDireccionArchivos();
            String ubicacion = new File(fullPath).getPath() + File.separatorChar + "Documentos" + File.separatorChar + "Seguimientos de Venta" + File.separatorChar + "Documentos";
            //-------------------------------------------
            System.out.println(ubicacion);
            //Crea los directorios si no estan creados aun
            this.crearDirectorio(ubicacion);
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            Seguimiento_venta tr = construirObjeto(items, request, ubicacion);

            if (tr.getId_seguimiento() == 0) { //agregar
                resultado = dao.insertarSeguimiento_venta(tr);
                if (resultado != 0) {
                    request.setAttribute("mensaje", helper.mensajeDeExito("Seguimiento de Venta agregado correctamente"));
                    //Funcion que genera la bitacora
                    bitacora.setBitacora(tr.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SEGUIMIENTO_VENTA, request.getRemoteAddr());
                    //*----------------------------*
                    this.getIndex(request, response);
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Seguimiento de Venta no pudo ser agregado. Inténtelo de nuevo."));
                    this.getAgregar(request, response);
                }
            } else { //editar
                String archivoViejo1 = "";
                if (tr.getDocumento_1().equals("")) {
                    archivoViejo1 = dao.obtenerSeguimiento_venta(tr.getId_seguimiento()).getDocumento_1();
                }
                //System.out.println("archivoViejo = "+archivoViejo);
                //System.out.println("tr.getMinuta() = "+tr.getMinuta());
                boolean resultado2 = false;
                if (!archivoViejo1.equals("")) {
                        tr.setDocumento_1(archivoViejo1);
                        //File archivo = new File(archivoViejo);
                        //archivo.delete();
                    }
                resultado2 = dao.editarSeguimiento_venta(tr);
                if (resultado2) {
                    
                    //Funcion que genera la bitacora
                    bitacora.setBitacora(tr.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SEGUIMIENTO_VENTA, request.getRemoteAddr());
                    //*----------------------------*
                    request.setAttribute("mensaje", helper.mensajeDeExito("Seguimiento de Venta editado correctamente"));
                    this.getIndex(request, response);
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Seguimiento de Venta no pudo ser editado. Inténtelo de nuevo."));
                    request.setAttribute("id_seguimiento", tr.getId_seguimiento());
                    this.getEditar(request, response);
                }
            }
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }

    }   
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "SeguimientoVenta/index.jsp";
        int[] permisos = {701, 1};
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String id_seguimiento = request.getParameter("id_seguimiento"); 
        try {
            Seguimiento_venta seguimiento_a_eliminar = dao.obtenerSeguimiento_venta(Integer.parseInt(id_seguimiento));
            
            resultado = dao.eliminarSeguimiento_venta(seguimiento_a_eliminar.getId_seguimiento());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(seguimiento_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SEGUIMIENTO_VENTA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "SeguimientoVenta/index.jsp";
            List<Seguimiento_venta> seguimientoes = dao.obtenerSeguimientos_venta();
            request.setAttribute("listaSeguimientos", seguimientoes);
            request.setAttribute("mensaje", helper.mensajeDeExito("Seguimiento de Venta eliminado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Método del Modelo">
    private Seguimiento_venta construirObjeto(List<FileItem> items, HttpServletRequest request, String ubicacion) throws SIGIPROException, ParseException {
        Seguimiento_venta tr = new Seguimiento_venta();
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
                    case "id_seguimiento":
                        int id_seguimiento = Integer.parseInt(fieldValue);
                        tr.setId_seguimiento(id_seguimiento);
                        break;
                    case "id_factura":
                        //System.out.println("nueva factura: "+fieldValue);
                        tr.setFactura(fdao.obtenerFactura(Integer.parseInt(fieldValue)));
                        break;
                    case "observaciones":
                        tr.setObservaciones(fieldValue);
                        break;
                    case "tipo":
                        tr.setTipo(fieldValue);
                        break;
                    case "id_cliente":
                        tr.setCliente(cdao.obtenerCliente(Integer.parseInt(fieldValue)));
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
                        String nombre = tr.getId_seguimiento()+ "-" + fecha + "." + extension;
                        //---------------------
                        File archivo = new File(ubicacion, nombre);
                        item.write(archivo);
                        tr.setDocumento_1(archivo.getAbsolutePath());
                                
                    } else {
                        tr.setDocumento_1("");
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
