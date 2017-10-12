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
import com.icp.sigipro.produccion.dao.LoteDAO;
import com.icp.sigipro.produccion.modelos.Lote;
import com.icp.sigipro.ventas.dao.ClienteDAO;
import com.icp.sigipro.ventas.dao.ListaDAO;
import com.icp.sigipro.ventas.dao.Orden_compraDAO;
import com.icp.sigipro.ventas.dao.PagoDAO;
import com.icp.sigipro.ventas.dao.Producto_FacturaDAO;
import com.icp.sigipro.ventas.dao.Producto_ventaDAO;
import com.icp.sigipro.ventas.modelos.Lista;
import com.icp.sigipro.ventas.modelos.Orden_compra;
import com.icp.sigipro.ventas.modelos.Pago;
import com.icp.sigipro.ventas.modelos.Producto_Factura;
import com.icp.sigipro.ventas.modelos.Producto_venta;
import com.icp.sigipro.webservices.FacturasPagosWs;
import com.icp.sigipro.webservices.FacturasWs;
import com.icp.sigipro.webservices.PagosWs;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    private final FacturaDAO dao = new FacturaDAO();
    private final PagoDAO pdao = new PagoDAO();
    private final Orden_compraDAO odao = new Orden_compraDAO();
    private final ClienteDAO cdao = new ClienteDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();
    private final ListaDAO ldao = new ListaDAO();
    private final Producto_FacturaDAO pfdao = new Producto_FacturaDAO();
    private final Producto_ventaDAO pvdao = new Producto_ventaDAO();
    private final LoteDAO lodao = new LoteDAO();

    protected final Class clase = ControladorFactura.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
            add("archivo");
            add("actualizarestados");
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
        int[] permisos = {701,704,1};
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
        int[] permisos = {701, 704, 1};
        validarPermisos(permisos, listaPermisos);
       
        String redireccion = "Factura/Agregar.jsp";
        Factura ds = new Factura();
        
        List<String> tipos = new ArrayList<String>();
        tipos.add("FUNDEVI");
        tipos.add("UCR");
        
        List<Orden_compra> ordenes = odao.obtenerOrdenes_compra();
        List<Producto_venta> productos = pvdao.obtenerProductos_venta();
        List<String> monedas = new ArrayList<String>();
        List<Lote> lotes = lodao.obtenerLotes();
        monedas.add("Colones");
        monedas.add("Dólares");
        monedas.add("Euros");
        monedas.add("Otra Moneda");
        
        request.setAttribute("monedas", monedas);
        request.setAttribute("lotes", lotes);
        request.setAttribute("factura", ds);
        request.setAttribute("productos", productos);
        request.setAttribute("clientes", cdao.obtenerClientesContratosFirmados());
        request.setAttribute("ordenes", ordenes);
        request.setAttribute("tipos", tipos);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1,702,703,704,705,706};
        validarPermisos(permisos, listaPermisos);

        /*
        try{
            ActualizarEstadosYPagos();
        }
        catch (SIGIPROException e){
        }
        */
        
        List<Factura> facturas = dao.obtenerFacturas();
        request.setAttribute("listaFacturas", facturas);
        String redireccion = "Factura/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1,702,703,704,705,706};
        validarPermisos(permisos, listaPermisos);
        
        /*
        try{
            ActualizarEstadosYPagos();
        }
        catch (SIGIPROException e){
        }
        */
        
        String redireccion = "Factura/Ver.jsp";
        int id_factura = Integer.parseInt(request.getParameter("id_factura"));
        try {
            Factura c = dao.obtenerFactura(id_factura);
            List<Pago> pagos = pdao.obtenerPagos(id_factura);
            List<Producto_Factura> productos_factura = pfdao.obtenerProductosFactura(id_factura);            
            request.setAttribute("productos_factura", productos_factura);            
            request.setAttribute("factura", c);
            request.setAttribute("pagos", pagos);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void getActualizarestados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 1,702,703,704,705,706};
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Factura/index.jsp";
        boolean resultado = false;
        try {
            List<Factura> facturas = dao.obtenerFacturas();
            String nuevoEstado = "";
            for (Factura f:facturas){
                //actualizar estado por cada factura una por una
                if (f.getNumero()!=0 && f.getProyecto()!=0){
                    try{
                    FacturasPagosWs estadofactura = estadoFactura(f.getNumero(),f.getProyecto());
                    if (estadofactura != null) {
                        //Estado
                        nuevoEstado = estadofactura.getEstadoFactura();
                        resultado = dao.actualizarEstado(f, nuevoEstado);
                        
                        //Pagos
                        float monto_pagos = 0;
                        if (!estadofactura.getListPagos().getPagosWs().isEmpty()){
                            for (PagosWs pago:estadofactura.getListPagos().getPagosWs()){
                                //Verificar si el pago está entonces lo actualiza, sino, agregarlo y sumarlo al monto_pagos
                                if (!pdao.existePago(pago.getCodigo())){
                                    Pago pago_nuevo = new Pago();
                                    
                                    pago_nuevo.setFactura(f);
                                    pago_nuevo.setCodigo(pago.getCodigo());
                                    pago_nuevo.setMonto(pago.getMonto().floatValue());
                                    pago_nuevo.setNota(pago.getNota());
                                    pago_nuevo.setFecha(pago.getFecha());
                                    pago_nuevo.setConsecutive(pago.getConsecutive());
                                    pago_nuevo.setMoneda(pago.getMoneda());
                                    pago_nuevo.setCodigo_remision(pago.getCodigoRemision());
                                    pago_nuevo.setConsecutive_remision(pago.getConsecutiveRemision());
                                    pago_nuevo.setFecha_remision(pago.getFechaRemision());
                                    
                                    pdao.insertarPago(pago_nuevo);
                                    
                                    monto_pagos += pago_nuevo.getMonto();
                                }
                                else{
                                    Pago pago_viejo = new Pago();
                                    
                                    pago_viejo.setFactura(f);
                                    pago_viejo.setCodigo(pago.getCodigo());
                                    pago_viejo.setMonto(pago.getMonto().floatValue());
                                    pago_viejo.setNota(pago.getNota());
                                    pago_viejo.setFecha(pago.getFecha());
                                    pago_viejo.setConsecutive(pago.getConsecutive());
                                    pago_viejo.setMoneda(pago.getMoneda());
                                    pago_viejo.setCodigo_remision(pago.getCodigoRemision());
                                    pago_viejo.setConsecutive_remision(pago.getConsecutiveRemision());
                                    pago_viejo.setFecha_remision(pago.getFechaRemision());
                                    
                                    pdao.actualizarPago(pago_viejo);
                                    
                                    monto_pagos += pago_viejo.getMonto();
                                }
                            }
                            float monto_pendiente = f.getMonto() - monto_pagos;
                            resultado = dao.actualizarMontoPendiente(f, monto_pendiente);
                        }
                    }
                    else{
                        request.setAttribute("mensaje", helper.mensajeDeError("Error del servidor de FUNDEVI"));
                        List<Factura> facturas1 = dao.obtenerFacturas();
                        request.setAttribute("listaFacturas", facturas1);
                        redireccionar(request, response, redireccion);
                        return;
                    }
                    }
                    catch (javax.xml.ws.soap.SOAPFaultException soapFaultException){
                        System.out.println("Error SOAP: "+soapFaultException);
                        request.setAttribute("mensaje", helper.mensajeDeError("Error del servidor de FUNDEVI"));
                        List<Factura> facturas1 = dao.obtenerFacturas();
                        request.setAttribute("listaFacturas", facturas1);
                        redireccionar(request, response, redireccion);
                        return;
                    }
                }
            }
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError("Error del servidor de FUNDEVI"));
            redireccionar(request, response, redireccion);
            return;
        }
        if (resultado) {
            List<Factura> facturas = dao.obtenerFacturas();
            request.setAttribute("listaFacturas", facturas);
            request.setAttribute("mensaje", helper.mensajeDeExito("Actualización de estados exitosa"));
        } else {
            List<Factura> facturas = dao.obtenerFacturas();
            request.setAttribute("listaFacturas", facturas);
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 704, 1};
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Factura/Editar.jsp";
        int id_factura = Integer.parseInt(request.getParameter("id_factura"));
        Factura ds = dao.obtenerFactura(id_factura);
        
        List<String> tipos = new ArrayList<String>();
        tipos.add("FUNDEVI");
        tipos.add("UCR");
        
        List<Orden_compra> ordenes = odao.obtenerOrdenes_compra();
        List<Producto_venta> productos = pvdao.obtenerProductos_venta();
        List<String> monedas = new ArrayList<String>();
        List<Lote> lotes = lodao.obtenerLotes();
        List<Producto_Factura> productos_factura = pfdao.obtenerProductosFactura(id_factura);
        monedas.add("Colones");
        monedas.add("Dólares");
        monedas.add("Euros");
        monedas.add("Otra Moneda");
        
        request.setAttribute("productos_factura", productos_factura);        
        request.setAttribute("productos", productos);
        request.setAttribute("lotes", lotes);
        request.setAttribute("monedas", monedas);
        request.setAttribute("factura", ds);
        request.setAttribute("clientes", cdao.obtenerClientesContratosFirmados());
        request.setAttribute("ordenes", ordenes);
        request.setAttribute("tipos", tipos);
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregareditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException, NoSuchAlgorithmException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 704, 1};
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
            String productos_factura = null;
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
                if (fieldName.equalsIgnoreCase("listaProductos")) {
                  productos_factura = fieldValue;
                  break;
                }
              }
            }
            Factura tr = construirObjeto(items, request, ubicacion);

            if (tr.getId_factura() == 0) {
                if (tr.getOrden() != null && tr.getOrden().getId_orden() != 0){
                    if (tr.getTipo().equals("FUNDEVI")){
                        resultado = dao.insertarFactura(tr);
                    }
                    else{
                        resultado = dao.insertarFacturaUCR(tr);
                    }
                }
                else{
                    if (tr.getTipo().equals("FUNDEVI")){
                        resultado = dao.insertarFacturaOrden0(tr);
                    }
                    else{
                        resultado = dao.insertarFacturaOrden0UCR(tr);
                    }
                }
                if (resultado != 0) {
                    //Eliminar cliente de lista de espera
                    if ((tr.getCliente()==null)||(tr.getCliente().getId_cliente()==0)){
                        if (ldao.clienteNombreEnLista(tr.getNombre())){
                            List<Lista> ClientesASacarDeListaDeEspera = ldao.obtenerListasPorNombreCliente(tr.getNombre());
                            for (Lista l : ClientesASacarDeListaDeEspera){
                                ldao.marcarFechaAtencion(l.getId_lista());
                            }
                        }
                    }
                    else{
                        if (ldao.clienteEnLista(tr.getCliente().getId_cliente())){
                            List<Lista> ClientesASacarDeListaDeEspera = ldao.obtenerListasPorCliente(tr.getCliente().getId_cliente());
                            for (Lista l : ClientesASacarDeListaDeEspera){
                                ldao.marcarFechaAtencion(l.getId_lista());
                            }
                        }
                    }
                    if (productos_factura != null && !(productos_factura.isEmpty())) {
                      List<Producto_Factura> p_i = pfdao.parsearProductos(productos_factura, resultado);
                      for (Producto_Factura i : p_i) {
                          pfdao.insertarProducto_Factura(i);
                        }
                      }
                    //Funcion que genera la bitacora
                    bitacora.setBitacora(tr.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_FACTURA, request.getRemoteAddr());
                    //*----------------------------*
                    String mensaje = "Factura agregada correctamente. ";
                    if (tr.getTipo() != null && tr.getTipo().equals("FUNDEVI")){
                        //int codigoOrden, java.lang.String usuarioEjecuta, java.lang.String nombreFactura, int proyecto, 
                        //int moneda, int plazo, java.math.BigDecimal montoFactura, java.lang.String correoEnviar, 
                        //java.lang.String detalle, java.lang.String llave generarLlave(int codigoOrden, int proyecto, int montoFactura, String fecha)
                        int moneda = 1;
                        if (tr.getMoneda().equals("Dólares"))
                            moneda = 2;
                        //Realiza el detalleServicio en el WebService de Fundevi
                        tr.setNombre(generarNombreFactura(tr));
                        FacturasWs fWS;
                        if (tr.getCorreo_enviar() != null){
                            fWS = detalleServicio(resultado,request.getSession().getAttribute("usuario").toString(),dao_us.obtenerIDUsuario("usuario"), tr.getNombre(),tr.getProyecto(),
                                moneda,tr.getPlazo(),new BigDecimal(0),new BigDecimal(tr.getMonto()),new BigDecimal(tr.getMonto()),tr.getCorreo_enviar(),"",tr.getDetalle(),generarLlave(resultado,tr.getProyecto(),tr.getMonto(),tr.getFecha_S()));
                        }
                        else{
                            fWS = detalleServicio(resultado,request.getSession().getAttribute("usuario").toString(),dao_us.obtenerIDUsuario("usuario"), tr.getNombre(),tr.getProyecto(),
                                moneda,tr.getPlazo(),new BigDecimal(0),new BigDecimal(tr.getMonto()),new BigDecimal(tr.getMonto()),""
                                    ,"",tr.getDetalle(),generarLlave(resultado,tr.getProyecto(),tr.getMonto(),tr.getFecha_S()));
                        }
                        //Obtener los valores de respuesta del WS y asignarlos a la factura
                        if (fWS == null){
                            request.setAttribute("mensaje", helper.mensajeDeAdvertencia(mensaje + "Error al enviar Factura a FUNDEVI."));
                        }
                        else if (fWS.getNumeroFactura()!=-1){
                            tr.setId_factura(resultado);
                            tr.setNumero(fWS.getNumeroFactura());
                            dao.agregarNumeroFactura(tr);
                            mensaje+=fWS.getMensaje();
                            request.setAttribute("mensaje", helper.mensajeDeExito(mensaje));
                        }
                        else{
                            mensaje="Error al enviar la factura a FUNDEVI.";
                            request.setAttribute("mensaje", helper.mensajeDeError("Factura no pudo ser agregada. "+mensaje));
                        }
                    }
                    //En este else, puede venir la parte del WS de OAF UCR
                    else{
                        request.setAttribute("mensaje", helper.mensajeDeExito(mensaje));
                    }
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
                if (tr.getOrden() == null || tr.getOrden().getId_orden() == 0){
                    if (tr.getTipo() != null && tr.getTipo().equals("FUNDEVI")){
                        resultado2 = dao.editarFacturaOrden0(tr);
                    }
                    else{
                        resultado2 = dao.editarFacturaOrden0UCR(tr);
                    }
                }
                else{
                    if (tr.getTipo() != null && tr.getTipo().equals("FUNDEVI")){
                        resultado2 = dao.editarFactura(tr);
                    }
                    else{
                        resultado2 = dao.editarFacturaUCR(tr);
                    }
                }
                if (resultado2) {                   
                    pfdao.eliminarProductos_Factura(tr.getId_factura());
                    if (productos_factura != null && !(productos_factura.isEmpty())) {
                      List<Producto_Factura> p_i = pfdao.parsearProductos(productos_factura, resultado);
                      for (Producto_Factura i : p_i) {
                          pfdao.insertarProducto_Factura(i);
                        }
                      }
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
            request.setAttribute("mensaje", helper.mensajeDeError("Error al procesar su petición. Inténtelo de nuevo."));
            this.getIndex(request, response);
            throw new ServletException("Cannot parse multipart request.", e);
        }
        catch (javax.xml.ws.soap.SOAPFaultException soapFaultException){
            System.out.println("Error SOAP: "+soapFaultException);
            request.setAttribute("mensaje", helper.mensajeDeError("Error al enviar la factura a FUNDEVI."));
            List<Factura> facturas1 = dao.obtenerFacturas();
            request.setAttribute("listaFacturas", facturas1);
            redireccionar(request, response, "Factura/index.jsp");
        }

    }   
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        boolean resultado2 = false;
        String redireccion = "Factura/index.jsp";
        int[] permisos = {701, 704, 1};
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
                    case "moneda":
                        tr.setMoneda(fieldValue);
                        break;
                    case "plazo":
                        tr.setPlazo(Integer.parseInt(fieldValue));
                        break;
                    case "proyecto":
                        tr.setProyecto(Integer.parseInt(fieldValue));
                        break;
                    case "correo_enviar":
                        tr.setCorreo_enviar(fieldValue);
                        break;
                    case "observaciones":
                        tr.setDetalle(fieldValue);
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
    // <editor-fold defaultstate="collapsed" desc="Métodos WebService">
    private static FacturasPagosWs estadoFactura(int numeroFactura, int proyecto) {
        com.icp.sigipro.webservices.MóduloX0020ServiciosX0020ExternosX0020X0028MSEX0029X0020X0020FundaciónX0020UCR service = null;
        com.icp.sigipro.webservices.MóduloX0020ServiciosX0020ExternosX0020X0028MSEX0029X0020X0020FundaciónX0020UCRSoap port = null;
        try{
            service = new com.icp.sigipro.webservices.MóduloX0020ServiciosX0020ExternosX0020X0028MSEX0029X0020X0020FundaciónX0020UCR();
            port = service.getMóduloX0020ServiciosX0020ExternosX0020X0028MSEX0029X0020X0020FundaciónX0020UCRSoap();
        }
        catch(Exception e){
            if (port!=null){
                return port.estadoFactura(numeroFactura, proyecto);
            }
            else{
                return null;
            }
        }
        if (port!=null){
            return port.estadoFactura(numeroFactura, proyecto);
        }
        else{
            return null;
        }
    }

    public String ActualizarEstadosYPagos() throws IOException, SIGIPROException {
        boolean resultado = false;
        try {
            List<Factura> facturas = dao.obtenerFacturas();
            String nuevoEstado = "";
            for (Factura f:facturas){
                //actualizar estado por cada factura una por una
                if (f.getNumero()!=0 && f.getProyecto()!=0){
                    FacturasPagosWs estadofactura = estadoFactura(f.getNumero(),f.getProyecto());
                    if (estadofactura != null) {
                        //Estado
                        nuevoEstado = estadofactura.getEstadoFactura();
                        resultado = dao.actualizarEstado(f, nuevoEstado);
                        
                        //Pagos
                        float monto_pagos = 0;
                        if (!estadofactura.getListPagos().getPagosWs().isEmpty()){
                            for (PagosWs pago:estadofactura.getListPagos().getPagosWs()){
                                //Verificar si el pago está entonces lo actualiza, sino, agregarlo y sumarlo al monto_pagos
                                if (!pdao.existePago(pago.getCodigo())){
                                    Pago pago_nuevo = new Pago();
                                    
                                    pago_nuevo.setFactura(f);
                                    pago_nuevo.setCodigo(pago.getCodigo());
                                    pago_nuevo.setMonto(pago.getMonto().floatValue());
                                    pago_nuevo.setNota(pago.getNota());
                                    pago_nuevo.setFecha(pago.getFecha());
                                    pago_nuevo.setConsecutive(pago.getConsecutive());
                                    pago_nuevo.setMoneda(pago.getMoneda());
                                    pago_nuevo.setCodigo_remision(pago.getCodigoRemision());
                                    pago_nuevo.setConsecutive_remision(pago.getConsecutiveRemision());
                                    pago_nuevo.setFecha_remision(pago.getFechaRemision());
                                    
                                    pdao.insertarPago(pago_nuevo);
                                    
                                    monto_pagos += pago_nuevo.getMonto();
                                }
                                else{
                                    Pago pago_viejo = new Pago();
                                    
                                    pago_viejo.setFactura(f);
                                    pago_viejo.setCodigo(pago.getCodigo());
                                    pago_viejo.setMonto(pago.getMonto().floatValue());
                                    pago_viejo.setNota(pago.getNota());
                                    pago_viejo.setFecha(pago.getFecha());
                                    pago_viejo.setConsecutive(pago.getConsecutive());
                                    pago_viejo.setMoneda(pago.getMoneda());
                                    pago_viejo.setCodigo_remision(pago.getCodigoRemision());
                                    pago_viejo.setConsecutive_remision(pago.getConsecutiveRemision());
                                    pago_viejo.setFecha_remision(pago.getFechaRemision());
                                    
                                    pdao.actualizarPago(pago_viejo);
                                    
                                    monto_pagos += pago_viejo.getMonto();
                                }
                            }
                            float monto_pendiente = f.getMonto() - monto_pagos;
                            resultado = dao.actualizarMontoPendiente(f, monto_pendiente);
                        }
                    }
                    else{
                        return "Error del servidor de FUNDEVI";
                    }
                }
            }
        } catch (SIGIPROException ex) {
            return "Error del servidor de FUNDEVI";
        }
        if (resultado) {
            return "Actualización de estados exitosa";
        } else {
            return "Ocurrió un error al procesar su petición";
        }
    }
        
    private static FacturasWs detalleServicio(int codigoOrden, java.lang.String usuarioEjecuta, int codUsuarioEjecuta, java.lang.String nombreFactura, int proyecto, int moneda, int plazo, BigDecimal porcentajeDescuento, BigDecimal subtotalFactura, BigDecimal totalFactura, java.lang.String correoEnviar, java.lang.String notas, java.lang.String detalle, java.lang.String llave) {
        com.icp.sigipro.webservices.MóduloX0020ServiciosX0020ExternosX0020X0028MSEX0029X0020X0020FundaciónX0020UCR service = null;
        com.icp.sigipro.webservices.MóduloX0020ServiciosX0020ExternosX0020X0028MSEX0029X0020X0020FundaciónX0020UCRSoap port = null;
        try{
            service = new com.icp.sigipro.webservices.MóduloX0020ServiciosX0020ExternosX0020X0028MSEX0029X0020X0020FundaciónX0020UCR();
            port = service.getMóduloX0020ServiciosX0020ExternosX0020X0028MSEX0029X0020X0020FundaciónX0020UCRSoap();
        }
        catch(Exception e){
            if (port!=null){
                return port.detalleServicio(codigoOrden, usuarioEjecuta, codUsuarioEjecuta, nombreFactura, proyecto, moneda, plazo, porcentajeDescuento, subtotalFactura, totalFactura, correoEnviar, notas, detalle, llave);
            }
            else{
                return null;
            }
        }
        if (port!=null){
            return port.detalleServicio(codigoOrden, usuarioEjecuta, codUsuarioEjecuta, nombreFactura, proyecto, moneda, plazo, porcentajeDescuento, subtotalFactura, totalFactura, correoEnviar, notas, detalle, llave);
        }
        else{
            return null;
        }
    }
    
    private static String generarLlave(int codigoOrden, int proyecto, int montoFactura, String fecha) throws NoSuchAlgorithmException{
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        String llaveProyecto = "";
        switch (proyecto){
            case 404:
                llaveProyecto = "JfDUuRpN84";
                break;
            case 1965:
                llaveProyecto = "bcnvZ9nkch";
                break;
            case 2815:
                llaveProyecto = "QWPHUKcwfA";
                break;
        }
        String llave = ""+codigoOrden+""+llaveProyecto+""+proyecto+""+montoFactura+".00"+fecha;
        m.update(llave.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        return bigInt.toString(16);
    }
    
    private static String generarNombreFactura(Factura f){
        String resultado = "";
        resultado+=f.getFecha_S()+" "+f.getCliente().getNombre();
        return resultado;
    }
    
  // </editor-fold>

}
