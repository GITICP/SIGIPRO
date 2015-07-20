/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.controlcalidad.dao.AnalisisDAO;
import com.icp.sigipro.controlcalidad.dao.ResultadoDAO;
import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.core.formulariosdinamicos.ControlXSLT;
import com.icp.sigipro.core.formulariosdinamicos.ControlXSLTDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author ld.conejo, boga
 */
@WebServlet(name = "ControladorResultado", urlPatterns = {"/ControlCalidad/Resultado"})
public class ControladorResultado extends SIGIPROServlet
{

    //Falta implementar
    private final int[] permisos = {1, 541};
    //-----------------
    private final AnalisisDAO dao = new AnalisisDAO();
    private final ControlXSLTDAO controlxsltdao = new ControlXSLTDAO();
    private final ResultadoDAO resultadodao = new ResultadoDAO();

    protected final Class clase = ControladorResultado.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("ver");
            add("vermultiple");
            add("verprueba");
            add("archivo");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregareditar");
            add("realizar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getArchivo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        int id_analisis = Integer.parseInt(request.getParameter("id_analisis"));
        Analisis analisis = dao.obtenerAnalisis(id_analisis);

        String filename = analisis.getMachote();
        File file = new File(filename);

        if (file.exists()) {
            ServletContext ctx = getServletContext();
            InputStream fis = new FileInputStream(file);
            String mimeType = ctx.getMimeType(file.getAbsolutePath());

            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
            response.setContentLength((int) file.length());
            String nombre = "machote-" + analisis + "." + this.getFileExtension(filename);
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
    
    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(541, listaPermisos);
        String redireccion = "Resultado/Ver.jsp";
        
        int id_resultado = Integer.parseInt(request.getParameter("id_resultado"));
        
        ControlXSLT xslt;
        Resultado resultado;
        
        try {
            xslt = controlxsltdao.obtenerControlXSLTResultado();
            resultado = resultadodao.obtenerResultado(id_resultado);

            TransformerFactory tff = TransformerFactory.newInstance();
            InputStream streamXSLT = xslt.getEstructura().getBinaryStream();
            InputStream streamXML = resultado.getDatos().getBinaryStream();
            Transformer transformador = tff.newTransformer(new StreamSource(streamXSLT));
            StreamSource stream_source = new StreamSource(streamXML);
            StreamResult stream_result = new StreamResult(new StringWriter());
            transformador.transform(stream_source, stream_result);

            String formulario = stream_result.getWriter().toString();

            request.setAttribute("resultado", resultado);
            request.setAttribute("cuerpo_datos", formulario);
        } catch (TransformerException | SIGIPROException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
        }

        redireccionar(request, response, redireccion);
    }
    
    protected void getVerprueba(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(541, listaPermisos);
        String redireccion = "Resultado/Ver.jsp";
        
        int id_resultado = Integer.parseInt(request.getParameter("id_resultado"));
        
        ControlXSLT xslt;
        Resultado resultado;
        
        try {
            xslt = controlxsltdao.obtenerControlXSLTResultadoReducido();
            resultado = resultadodao.obtenerResultado(id_resultado);

            TransformerFactory tff = TransformerFactory.newInstance();
            InputStream streamXSLT = xslt.getEstructura().getBinaryStream();
            InputStream streamXML = resultado.getDatos().getBinaryStream();
            Transformer transformador = tff.newTransformer(new StreamSource(streamXSLT));
            StreamSource stream_source = new StreamSource(streamXML);
            StreamResult stream_result = new StreamResult(new StringWriter());
            transformador.transform(stream_source, stream_result);

            String formulario = stream_result.getWriter().toString();

            request.setAttribute("resultado", formulario);
            request.setAttribute("cuerpo_datos", formulario);
        } catch (TransformerException | SIGIPROException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
        }

        redireccionar(request, response, redireccion);
    }
    
    protected void getVermultiple(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(540, listaPermisos);
        String redireccion = "Resultado/VerMultiple.jsp";
        
        int id_ags = Integer.parseInt(request.getParameter("id_ags"));
        String id_solicitud = request.getParameter("id_solicitud");
        String numero_solicitud = request.getParameter("numero_solicitud");
        request.setAttribute("id_solicitud", id_solicitud);
        request.setAttribute("numero_solicitud", numero_solicitud);
        
        try {
            List<Resultado> resultados = resultadodao.obtenerResultadosAGS(id_ags);
            
            request.setAttribute("resultados", resultados);
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", sig_ex.getMessage());
        }
        
        redireccionar(request, response, redireccion);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">


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
            Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class
            );
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
