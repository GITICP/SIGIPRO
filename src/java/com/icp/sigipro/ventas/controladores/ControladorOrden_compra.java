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

import com.icp.sigipro.ventas.dao.CotizacionDAO;
import com.icp.sigipro.ventas.modelos.Cotizacion;
import com.icp.sigipro.ventas.dao.Orden_compraDAO;
import com.icp.sigipro.ventas.modelos.Orden_compra;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.ventas.dao.ClienteDAO;
import com.icp.sigipro.ventas.dao.Intencion_ventaDAO;
import com.icp.sigipro.ventas.dao.Producto_OrdenDAO;
import com.icp.sigipro.ventas.dao.Producto_ventaDAO;
import com.icp.sigipro.ventas.modelos.Intencion_venta;
import com.icp.sigipro.ventas.modelos.Producto_Orden;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.security.sasl.AuthenticationException;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "ControladorOrden_compra", urlPatterns = {"/Ventas/OrdenCompra"})
public class ControladorOrden_compra extends SIGIPROServlet {

    private final Orden_compraDAO dao = new Orden_compraDAO();
    private final Producto_ventaDAO pdao = new Producto_ventaDAO();
    private final Intencion_ventaDAO idao = new Intencion_ventaDAO();
    private final CotizacionDAO cotdao = new CotizacionDAO();
    private final Producto_OrdenDAO podao = new Producto_OrdenDAO();
    private final ClienteDAO cdao = new ClienteDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();

    protected final Class clase = ControladorOrden_compra.class;
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
            add("agregar");
            add("editar");
            add("eliminar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
       
        String redireccion = "OrdenCompra/Agregar.jsp";
        Orden_compra ds = new Orden_compra();
            
        List<String> estados = new ArrayList<String>();
        estados.add("En proceso");
        estados.add("Entregada");
        estados.add("Cancelada");
        
        //List<Producto_venta> productos = pdao.obtenerProductos_venta();
        List<Orden_compra> ordenes = dao.obtenerOrdenes_compra();
        
        request.setAttribute("consecutivo", ordenes.get(ordenes.size()-1).getId_orden()+1);
        request.setAttribute("orden", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        request.setAttribute("cotizaciones", cotdao.obtenerCotizaciones());
        request.setAttribute("intenciones", idao.obtenerIntenciones_venta());
        
        //request.setAttribute("productos", productos);
        request.setAttribute("estados", estados);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getArchivo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        int[] permisos = {701,704,1};
        validarPermisosMultiple(permisos, request);

        int id_orden = Integer.parseInt(request.getParameter("id_orden"));
        Orden_compra orden = dao.obtenerOrden_compra(id_orden);

        String filename = "";
        filename = orden.getDocumento();
                
        File file = new File(filename);

        if (file.exists()) {
            ServletContext ctx = getServletContext();
            InputStream fis = new FileInputStream(file);
            String mimeType = ctx.getMimeType(file.getAbsolutePath());

            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
            response.setContentLength((int) file.length());
            String nombre = "documento-" + orden.getId_orden()+ "." + this.getFileExtension(filename);
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
    
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701,702,703,704,705,706, 1};
        validarPermisos(permisos, listaPermisos);

        List<Orden_compra> ordenes = dao.obtenerOrdenes_compra();
        request.setAttribute("listaOrdenes", ordenes);
        String redireccion = "OrdenCompra/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1,702,703,704,705,706};
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "OrdenCompra/Ver.jsp";
        int id_orden = Integer.parseInt(request.getParameter("id_orden"));
        try {
            Orden_compra c = dao.obtenerOrden_compra(id_orden);
            List<Producto_Orden> d = podao.obtenerProductosOrden(id_orden);
            request.setAttribute("productos_orden", d);
            request.setAttribute("orden", c);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1};
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "OrdenCompra/Editar.jsp";
        int id_orden = Integer.parseInt(request.getParameter("id_orden"));
        Orden_compra ds = dao.obtenerOrden_compra(id_orden);
        List<Producto_Orden> d = podao.obtenerProductosOrden(id_orden);
        
        List<String> estados = new ArrayList<String>();
        estados.add("En proceso");
        estados.add("Entregada");
        estados.add("Cancelada");
        
        int contador = 1;
        String listadoProductos = "";
        for (Producto_Orden po: d){
            listadoProductos += "#r#"+contador;
            listadoProductos += "#c#"+po.getProducto().getNombre();
            listadoProductos += "#c#"+po.getCantidad();
            listadoProductos += "#c#"+po.getFecha_S();
            d.get(contador-1).setContador(contador);
            contador ++;
        }
        
        request.setAttribute("listadoProductos", listadoProductos);
        request.setAttribute("productos_orden", d);
        request.setAttribute("orden", ds);
        request.setAttribute("clientes", cdao.obtenerClientes());
        //request.setAttribute("productos", pdao.obtenerProductos_venta());
        request.setAttribute("cotizaciones", cotdao.obtenerCotizaciones());
        request.setAttribute("intenciones", idao.obtenerIntenciones_venta());
        request.setAttribute("estados", estados);
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregareditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException, FileUploadException {
        int resultado = 0;
        boolean resultado2 = false;
        int[] permisos = {701, 1};
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "OrdenCompra/index.jsp";
        String redireccionEditar = "OrdenCompra/Editar.jsp";
        String redireccionAgregar = "OrdenCompra/Agregar.jsp";
        
        String accion = "";
        
        try {
            //Se crea el Path en la carpeta del Proyecto
            String fullPath = helper_archivos.obtenerDireccionArchivos();
            String ubicacion = new File(fullPath).getPath() + File.separatorChar + "Documentos" + File.separatorChar + "OrdenCompra" + File.separatorChar + "Documentos";
            //-------------------------------------------
            System.out.println(ubicacion);
            //Crea los directorios si no estan creados aun
            this.crearDirectorio(ubicacion);
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            Orden_compra orden_nuevo = construirObjeto(items, request, ubicacion);
            
            if(orden_nuevo.getId_orden()==0){
                accion = "Agregar";
            
                if((orden_nuevo.getCotizacion() == null)||(orden_nuevo.getCotizacion().getId_cotizacion() == 0)){
                    resultado = dao.insertarOrden_compraCotizacion0(orden_nuevo);
                }
                else{
                    resultado = dao.insertarOrden_compraIntencion0(orden_nuevo);
                }

                if (orden_nuevo.getListaProductos() != null && !(orden_nuevo.getListaProductos().isEmpty()) ) {
                    List<Producto_Orden> p_i = podao.parsearProductos(orden_nuevo.getListaProductos(), resultado);
                    for (Producto_Orden i : p_i) {
                        podao.insertarProducto_Orden(i);
                    }
                }

                //Funcion que genera la bitacora
                BitacoraDAO bitacora = new BitacoraDAO();
                bitacora.setBitacora(orden_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ORDEN_COMPRA, request.getRemoteAddr());
                //*----------------------------*
            }else{
                accion = "Editar";
                String archivoViejo = "";

                if (orden_nuevo.getDocumento().equals("")) {
                    archivoViejo = dao.obtenerOrden_compra(orden_nuevo.getId_orden()).getDocumento();
                }
                if (!archivoViejo.equals("")) {
                    orden_nuevo.setDocumento(archivoViejo);
                }

                if((orden_nuevo.getCotizacion() == null)||(orden_nuevo.getCotizacion().getId_cotizacion() == 0)){
                    resultado2 = dao.editarOrden_compraCotizacion0(orden_nuevo);
                }
                else{
                    resultado2 = dao.editarOrden_compraIntencion0(orden_nuevo);
                }

                int id_orden = orden_nuevo.getId_orden();

                if (orden_nuevo.getListaProductos() != null && !(orden_nuevo.getListaProductos().isEmpty()) ) {
                    List<Producto_Orden> p_i = podao.parsearProductos(orden_nuevo.getListaProductos(), id_orden);
                    for (Producto_Orden i : p_i) {
                        if (!podao.esProductoOrden(i.getProducto().getId_producto(), id_orden)){
                            podao.insertarProducto_Orden(i);
                        }
                        else{
                            podao.editarProducto_Orden(i);
                        }
                    }
                    podao.asegurarProductos_Orden(p_i, id_orden);
                }
                else{
                    podao.eliminarProductos_Orden(id_orden);
                }
                //Funcion que genera la bitacora
                BitacoraDAO bitacora = new BitacoraDAO();
                bitacora.setBitacora(orden_nuevo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ORDEN_COMPRA, request.getRemoteAddr());
                //*----------------------------*
            }
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (accion.equals("Agregar")){
            if (resultado != 0){
                List<Orden_compra> ordenes = dao.obtenerOrdenes_compra();
                request.setAttribute("listaOrdenes", ordenes);
                request.setAttribute("mensaje", helper.mensajeDeExito("Orden de Compra agregada correctamente"));
            } else {
                redireccion = redireccionAgregar;
                request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
            }
        }
        else{
            if (resultado2) {
                List<Orden_compra> ordenes = dao.obtenerOrdenes_compra();
                request.setAttribute("listaOrdenes", ordenes);
                request.setAttribute("mensaje", helper.mensajeDeExito("Orden de Compra editada correctamente"));
            } else {
                redireccion = redireccionEditar;
                request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
            }   
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        boolean resultado2 = false;
        String redireccion = "OrdenCompra/index.jsp";
        int[] permisos = {701, 1};
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String id_orden = request.getParameter("id_orden"); 
        try {
            Orden_compra orden_a_eliminar = dao.obtenerOrden_compra(Integer.parseInt(id_orden));
            
            resultado = dao.eliminarOrden_compra(orden_a_eliminar.getId_orden());
            //resultado2 = podao.eliminarProductos_Orden(orden_a_eliminar.getId_orden());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(orden_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ORDEN_COMPRA, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "OrdenCompra/index.jsp";
            List<Orden_compra> ordenes = dao.obtenerOrdenes_compra();
            request.setAttribute("listaOrdenes", ordenes);
            request.setAttribute("mensaje", helper.mensajeDeExito("Orden de Compra eliminada correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Método del Modelo">
    private Orden_compra construirObjeto(List<FileItem> items, HttpServletRequest request, String ubicacion) throws SIGIPROException, ParseException {
        Orden_compra orden = new Orden_compra();
        
        for (FileItem item : items) {
            if (!(item.isFormField())) {
                try {
                    if (item.getSize() != 0) {
                        this.crearDirectorio(ubicacion);
                        //Creacion del nombre
                        Date dNow = new Date();
                        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmm");
                        String fecha = ft.format(dNow);
                        String extension = this.getFileExtension(item.getName());
                        String nombre = dao.obtenerOrdenes_compra().size() + "-" + fecha + "." + extension;
                        //---------------------
                        File archivo = new File(ubicacion, nombre);
                        item.write(archivo);
                        
                        orden.setDocumento(archivo.getAbsolutePath());
                    } else {
                        orden.setDocumento("");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else{
                String fieldName = item.getFieldName();
                String fieldValue;
                try {
                    fieldValue = item.getString("UTF-8").trim();
                } catch (UnsupportedEncodingException ex) {
                    fieldValue = item.getString();
                }
                //System.out.println("Field Name = "+fieldName+", Value = "+fieldValue);
                switch (fieldName) {
                    case "id_orden":
                        orden.setId_orden(Integer.parseInt(fieldValue));
                        break;
                    case "listaProductos":
                        orden.setListaProductos(fieldValue);
                        break;
                    case "rotulacion":
                        orden.setRotulacion(fieldValue);
                        break;
                    case "estado":
                        orden.setEstado(fieldValue);
                        break;
                    case "id_cotizacion":
                        String cotizacion = fieldValue;
                        if(cotizacion != null && !cotizacion.equals("")){
                            orden.setCotizacion(cotdao.obtenerCotizacion(Integer.parseInt(cotizacion)));
                        }
                        else{
                            Cotizacion c = new Cotizacion();
                            c.setId_cotizacion(0);
                            orden.setCotizacion(c);
                        }
                        break;
                    case "id_intencion":
                        String intencion = fieldValue;
                        if(intencion != null && !intencion.equals("")){
                            orden.setIntencion(idao.obtenerIntencion_venta(Integer.parseInt(intencion)));
                        }
                        else{
                            Intencion_venta iv = new Intencion_venta();
                            iv.setId_intencion(0);
                            orden.setIntencion(iv);
                        }
                        break;
                }
            }
        }
        
        return orden;
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
            if (accionHTTP.equals("post")){
                String nombreMetodo = accionHTTP + Character.toUpperCase(accion.charAt(0)) + accion.substring(1);
                Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class);
                metodo.invoke(this, request, response);
            }
            else{
                Method metodo = clase.getDeclaredMethod(accionHTTP + "Index", HttpServletRequest.class, HttpServletResponse.class);
                metodo.invoke(this, request, response);
            }
        }
    }

    @Override
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  // </editor-fold>
}
