/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.configuracion.dao.CorreoDAO;
import com.icp.sigipro.configuracion.modelos.Correo;
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
import java.util.Properties;
import java.util.Set;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
            add("correo");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getArchivo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        int[] permisos = {701, 1};
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
        int[] permisos = {701, 1};
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
        int[] permisos = {701, 1,702,703,704,705,706};
        validarPermisos(permisos, listaPermisos);

        List<Reunion_produccion> reuniones = dao.obtenerReuniones_produccion();
        request.setAttribute("listaReuniones", reuniones);
        String redireccion = "ReunionProduccion/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int[] permisos = {701, 702,703,704,705,706,1};
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
        int[] permisos = {701, 1};
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
    
    protected void postCorreo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "ReunionProduccion/Ver.jsp";
        int[] permisos = {701, 1};
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        int id_reunion = Integer.parseInt(request.getParameter("id_reunion")); 
        try {
            //Obtener: asunto, lista de correos, texto del cuerpo y cargar el archivo
            Reunion_produccion c = dao.obtenerReunion_produccion(id_reunion);
            List<Participantes_reunion> d = pDAO.obtenerParticipantes(id_reunion);
            
            CorreoDAO correodao = new CorreoDAO();
            Correo configcorreo = correodao.obtenerCorreo();
            
            // Recipient's email ID needs to be mentioned.
            String to = "";
            for (Participantes_reunion p: d){
                to = to.concat(p.getUsuario().getCorreo());
                to = to.concat(",");
            }
            to = to.substring(0, to.length()-1); //quitar comma final

            // Sender's email ID needs to be mentioned
            //String from = configcorreo.getCorreo();

            List<String> lista = correodao.obtenerListaCorreo();
            final String host = lista.get(0);//change accordingly
            final String puerto = lista.get(1);//change accordingly
            final String starttls = lista.get(2);//change accordingly
            final String nombreEmisor = lista.get(3);//change accordingly
            final String username = lista.get(4);//change accordingly
            final String password = lista.get(5);//change accordingly

            // Assuming you are sending email through relay.jangosmtp.net
            //String host = nombreEmisor;

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", starttls);
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", puerto);

            // Get the Session object.
            Session session = Session.getInstance(props,
               new javax.mail.Authenticator() {
                  protected PasswordAuthentication getPasswordAuthentication() {
                     return new PasswordAuthentication(username, password);
                  }
               });

            try {
               // Create a default MimeMessage object.
               Message message = new MimeMessage(session);

               // Set From: header field of the header.
               message.setFrom(new InternetAddress(username,nombreEmisor));

               // Set To: header field of the header.
               message.setRecipients(Message.RecipientType.TO,
                  InternetAddress.parse(to));

               // Set Subject: header field. Obtener del request.
               message.setSubject(request.getParameter("asunto")); 

               // Create the message part
               BodyPart messageBodyPart = new MimeBodyPart();

               // Now set the actual message. Obtener del request.
               messageBodyPart.setText(request.getParameter("cuerpo"));

               // Create a multipar message
               Multipart multipart = new MimeMultipart();

               // Set text message part
               multipart.addBodyPart(messageBodyPart);

               // Part two is attachment
               messageBodyPart = new MimeBodyPart();
               String filename = c.getMinuta();
               DataSource source = new FileDataSource(filename);
               messageBodyPart.setDataHandler(new DataHandler(source));
               messageBodyPart.setFileName("Minuta-" + c.getId_reunion() + "." + this.getFileExtension(filename));
               multipart.addBodyPart(messageBodyPart);

               // Send the complete message parts
               message.setContent(multipart);

               // Send message
               Transport.send(message);

               //System.out.println("Sent message successfully....");
               
               resultado = true;

            } catch (MessagingException e) {
               //System.out.println("Error en al enviar el mensaje: "+e.getMessage());
               request.setAttribute("mensaje", e.getMessage());
            }
        } catch (Exception ex) {
            //System.out.println("SIGIPRO Exception");
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Correo enviado con la minuta a los participantes exitosamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al enviar el correo"));
        }
        Reunion_produccion c = dao.obtenerReunion_produccion(id_reunion);
        List<Participantes_reunion> d = pDAO.obtenerParticipantes(id_reunion);
        request.setAttribute("participantes", d);
        request.setAttribute("reunion", c);
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
        int[] permisos = {701, 1};
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
