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

import com.icp.sigipro.ventas.dao.FacturaDAO;
import com.icp.sigipro.ventas.modelos.Factura;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.ventas.dao.ClienteDAO;
import com.icp.sigipro.ventas.dao.Orden_compraDAO;
import com.icp.sigipro.ventas.modelos.Orden_compra;
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
@WebServlet(name = "ControladorFactura", urlPatterns = {"/Ventas/Factura"})
public class ControladorFactura extends SIGIPROServlet {

    private final int[] permisos = {701, 702, 1};
    private final FacturaDAO dao = new FacturaDAO();
    private final Orden_compraDAO odao = new Orden_compraDAO();
    private final ClienteDAO cdao = new ClienteDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();

    protected final Class clase = ControladorFactura.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
            add("archivo");
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

        int id_factura = Integer.parseInt(request.getParameter("id_factura"));
        Factura factura = dao.obtenerFactura(id_factura);

        int documento = Integer.parseInt(request.getParameter("documento"));
        String filename = "";
        switch(documento){
            case 1:
                filename = factura.getDocumento_1();
                break;
            case 2:
                filename = factura.getDocumento_2();
                break;
            case 3:
                filename = factura.getDocumento_3();
                break;
            case 4:
                filename = factura.getDocumento_4();
                break;
        }
        File file = new File(filename);

        if (file.exists()) {
            ServletContext ctx = getServletContext();
            InputStream fis = new FileInputStream(file);
            String mimeType = ctx.getMimeType(file.getAbsolutePath());

            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
            response.setContentLength((int) file.length());
            String nombre = "documento-" + factura.getId_factura() + "." + this.getFileExtension(filename);
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
            request.setAttribute("mensaje", helper.mensajeDeError("El archivo no fue encontrado. Actualice la factura."));
            this.getVer(request, response);
        }

    }
    
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
       
        String redireccion = "Factura/Agregar.jsp";
        Factura ds = new Factura();
        
        List<String> tipos = new ArrayList<String>();
        tipos.add("FUNDEVI");
        tipos.add("UCR");
        
        List<Orden_compra> ordenes = odao.obtenerOrdenes_compra();
        
        request.setAttribute("factura", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("ordenes", ordenes);
        request.setAttribute("tipos", tipos);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        List<Factura> facturas = dao.obtenerFacturas();
        request.setAttribute("listaFacturas", facturas);
        String redireccion = "Factura/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Factura/Ver.jsp";
        int id_factura = Integer.parseInt(request.getParameter("id_factura"));
        try {
            Factura c = dao.obtenerFactura(id_factura);
            request.setAttribute("factura", c);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Factura/Editar.jsp";
        int id_factura = Integer.parseInt(request.getParameter("id_factura"));
        Factura ds = dao.obtenerFactura(id_factura);
        
        List<String> tipos = new ArrayList<String>();
        tipos.add("FUNDEVI");
        tipos.add("UCR");
        
        List<Orden_compra> ordenes = odao.obtenerOrdenes_compra();
        
        request.setAttribute("factura", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("ordenes", ordenes);
        request.setAttribute("tipos", tipos);
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
            String ubicacion = new File(fullPath).getPath() + File.separatorChar + "Documentos" + File.separatorChar + "Factura" + File.separatorChar + "Documentos";
            //-------------------------------------------
            System.out.println(ubicacion);
            //Crea los directorios si no estan creados aun
            this.crearDirectorio(ubicacion);
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            Factura tr = construirObjeto(items, request, ubicacion);

            if (tr.getId_factura() == 0) {
                if (tr.getOrden().getId_orden() != 0){
                    resultado = dao.insertarFactura(tr);
                }
                else{
                    resultado = dao.insertarFacturaOrden0(tr);
                }
                if (resultado != 0) {
                    request.setAttribute("mensaje", helper.mensajeDeExito("Factura agregada correctamente"));
                    //Funcion que genera la bitacora
                    bitacora.setBitacora(tr.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_FACTURA, request.getRemoteAddr());
                    //*----------------------------*
                    this.getIndex(request, response);
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Factura no pudo ser agregada. Inténtelo de nuevo."));
                    this.getAgregar(request, response);
                }
            } else {
                String archivoViejo1 = "";
                String archivoViejo2 = "";
                String archivoViejo3 = "";
                String archivoViejo4 = "";
                if (tr.getDocumento_1().equals("")) {
                    archivoViejo1 = dao.obtenerFactura(tr.getId_factura()).getDocumento_1();
                }
                if (tr.getDocumento_2().equals("")) {
                    archivoViejo2 = dao.obtenerFactura(tr.getId_factura()).getDocumento_2();
                }
                if (tr.getDocumento_3().equals("")) {
                    archivoViejo3 = dao.obtenerFactura(tr.getId_factura()).getDocumento_3();
                }
                if (tr.getDocumento_4().equals("")) {
                    archivoViejo4 = dao.obtenerFactura(tr.getId_factura()).getDocumento_4();
                }
                //System.out.println("archivoViejo = "+archivoViejo);
                //System.out.println("tr.getDocumento() = "+tr.getDocumento());
                boolean resultado2 = false;
                if (!archivoViejo1.equals("")) {
                        tr.setDocumento_1(archivoViejo1);
                        //File archivo = new File(archivoViejo);
                        //archivo.delete();
                    }
                if (!archivoViejo2.equals("")) {
                        tr.setDocumento_2(archivoViejo2);
                        //File archivo = new File(archivoViejo);
                        //archivo.delete();
                    }
                if (!archivoViejo3.equals("")) {
                        tr.setDocumento_3(archivoViejo3);
                        //File archivo = new File(archivoViejo);
                        //archivo.delete();
                    }
                if (!archivoViejo4.equals("")) {
                        tr.setDocumento_4(archivoViejo4);
                        //File archivo = new File(archivoViejo);
                        //archivo.delete();
                    }
                if (tr.getOrden() == null || tr.getOrden().getId_orden() != 0){
                    resultado2 = dao.editarFacturaOrden0(tr);
                }
                else{
                    resultado2 = dao.editarFacturaOrden0(tr);
                }
                if (resultado2) {
                    
                    //Funcion que genera la bitacora
                    bitacora.setBitacora(tr.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_FACTURA, request.getRemoteAddr());
                    //*----------------------------*
                    request.setAttribute("mensaje", helper.mensajeDeExito("Factura editada correctamente"));
                    this.getIndex(request, response);
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Factura no pudo ser editada. Inténtelo de nuevo."));
                    request.setAttribute("id_factura", tr.getId_factura());
                    this.getEditar(request, response);
                }
            }
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }

    }   
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        boolean resultado2 = false;
        String redireccion = "Factura/index.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String id_factura = request.getParameter("id_factura"); 
        try {
            Factura factura_a_eliminar = dao.obtenerFactura(Integer.parseInt(id_factura));
            
            resultado = dao.eliminarFactura(factura_a_eliminar.getId_factura());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(factura_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_INTENCION_VENTA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Factura/index.jsp";
            List<Factura> facturas = dao.obtenerFacturas();
            request.setAttribute("listaFacturas", facturas);
            request.setAttribute("mensaje", helper.mensajeDeExito("Factura eliminada correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Método del Modelo">
    private Factura construirObjeto(List<FileItem> items, HttpServletRequest request, String ubicacion) throws SIGIPROException, ParseException {
        Factura tr = new Factura();
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
                    case "id_factura":
                        int id_factura = Integer.parseInt(fieldValue);
                        tr.setId_factura(id_factura);
                        break;
                    case "id_cliente":
                        int id_cliente = Integer.parseInt(fieldValue);
                        tr.setCliente(cdao.obtenerCliente(id_cliente));
                        break;
                    case "id_orden":
                        if (!fieldValue.equals("")){
                            int id_orden = Integer.parseInt(fieldValue);
                            tr.setOrden(odao.obtenerOrden_compra(id_orden));
                        }
                        else{
                            Orden_compra o = new Orden_compra();
                            o.setId_orden(0);
                            tr.setOrden(o);
                        }
                        break;
                    case "fecha":
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        java.util.Date result = df.parse(fieldValue);
                        java.sql.Date fechaSQL = new java.sql.Date(result.getTime());
                        tr.setFecha(fechaSQL);
                        break;
                    case "monto":
                        int monto = Integer.parseInt(fieldValue);
                        tr.setMonto(monto);
                        break;
                    case "fecha_vencimiento":
                        DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
                        java.util.Date result1 = df1.parse(fieldValue);
                        java.sql.Date fecha_vencimientoSQL = new java.sql.Date(result1.getTime());
                        tr.setFecha_vencimiento(fecha_vencimientoSQL);
                        break;
                    case "tipo":
                        tr.setTipo(fieldValue);
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
                        String nombre = tr.getId_factura()+ "-" + fecha + "." + extension;
                        //---------------------
                        File archivo = new File(ubicacion, nombre);
                        item.write(archivo);
                        switch(contador_documento){
                            case 1:
                                tr.setDocumento_1(archivo.getAbsolutePath());
                                contador_documento += 1;
                                break;
                            case 2:
                                tr.setDocumento_2(archivo.getAbsolutePath());
                                contador_documento += 1;
                                break;
                            case 3:
                                tr.setDocumento_3(archivo.getAbsolutePath());
                                contador_documento += 1;
                                break;
                            case 4:
                                tr.setDocumento_4(archivo.getAbsolutePath());
                                contador_documento += 1;
                                break;
                        }
                    } else {
                        switch(contador_documento){
                            case 1:
                                tr.setDocumento_1("");
                                contador_documento += 1;
                                break;
                            case 2:
                                tr.setDocumento_2("");
                                contador_documento += 1;
                                break;
                            case 3:
                                tr.setDocumento_3("");
                                contador_documento += 1;
                                break;
                            case 4:
                                tr.setDocumento_4("");
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
