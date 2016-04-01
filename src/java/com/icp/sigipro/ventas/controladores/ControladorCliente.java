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

import com.icp.sigipro.ventas.dao.ClienteDAO;
import com.icp.sigipro.ventas.modelos.Cliente;
import com.icp.sigipro.ventas.modelos.Contactos_cliente;
import com.icp.sigipro.ventas.dao.Contactos_clienteDAO;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorClientes", urlPatterns = {"/Ventas/Clientes"})
public class ControladorCliente extends SIGIPROServlet {

    private final int[] permisos = {701, 702, 1};
    private final ClienteDAO dao = new ClienteDAO();
    private final Contactos_clienteDAO ccdao = new Contactos_clienteDAO();
    private final UsuarioDAO dao_us = new UsuarioDAO();

    protected final Class clase = ControladorCliente.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
            
            add("ver_contacto");
            add("editar_contacto");
            add("agregar_contacto");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
            add("editar");
            add("eliminar");
            
            add("agregar_contacto");
            add("editar_contacto");
            add("eliminar_contacto");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
       
        String redireccion = "Clientes/Agregar.jsp";
        Cliente ds = new Cliente();
        List<String> countries = new ArrayList<String>();

        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
          String name = locale.getDisplayCountry();

          if (!"".equals(name)) {
            countries.add(name);
          }
        }

        Set<String> hs = new HashSet<>();
        hs.addAll(countries);
        countries.clear();
        countries.addAll(hs);
        Collections.sort(countries);
    
        request.setAttribute("cliente", ds);
        request.setAttribute("paises", countries);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        List<Cliente> clientes = dao.obtenerClientes();
        request.setAttribute("listaClientes", clientes);
        String redireccion = "Clientes/index.jsp";
        
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Clientes/Ver.jsp";
        int id_cliente = Integer.parseInt(request.getParameter("id_cliente"));
        try {
            Cliente c = dao.obtenerCliente(id_cliente);
            List<Contactos_cliente> listaContactos = ccdao.obtenerContactos_Del_Cliente(id_cliente);
            request.setAttribute("cliente", c);
            request.setAttribute("listaContactos", listaContactos);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Clientes/Editar.jsp";
        int id_cliente = Integer.parseInt(request.getParameter("id_cliente"));
        Cliente ds = dao.obtenerCliente(id_cliente);
        
        List<String> countries = new ArrayList<String>();

        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
          String name = locale.getDisplayCountry();

          if (!"".equals(name)) {
            countries.add(name);
          }
        }

        Set<String> hs = new HashSet<>();
        hs.addAll(countries);
        countries.clear();
        countries.addAll(hs);
        Collections.sort(countries);
    
        request.setAttribute("cliente", ds);
        request.setAttribute("paises", countries);
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Get Contactos">
    protected void getAgregar_contacto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
       
        String redireccion = "Clientes/AgregarContacto.jsp";
        Contactos_cliente ds = new Contactos_cliente();
        int id_cliente = Integer.parseInt(request.getParameter("id_cliente"));
        request.setAttribute("contacto", ds);
        request.setAttribute("id_cliente", id_cliente);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }
    
    protected void getVer_contacto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Clientes/VerContacto.jsp";
        int id_contacto = Integer.parseInt(request.getParameter("id_contacto"));
        try {
            Contactos_cliente c = ccdao.obtenerContactos_cliente(id_contacto);
            request.setAttribute("contacto", c);
        } catch (Exception ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditar_contacto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        String redireccion = "Clientes/EditarContacto.jsp";
        int id_contacto = Integer.parseInt(request.getParameter("id_contacto"));
        Contactos_cliente ds = ccdao.obtenerContactos_cliente(id_contacto);
        request.setAttribute("contacto", ds);
        request.setAttribute("accion", "Editar");
        
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        int resultado = 0;
        boolean resultado2 = false;
        String redireccion = "Clientes/Agregar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        try {
            Cliente cliente_nuevo = construirObjeto(request);
            resultado = dao.insertarCliente(cliente_nuevo);
            
            Contactos_cliente contacto_nuevo = construirObjetoContacto(request, resultado);
            
            resultado2 = ccdao.insertarContactos_cliente(contacto_nuevo);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(cliente_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CLIENTE, request.getRemoteAddr());
            bitacora.setBitacora(contacto_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CONTACTOS_CLIENTE, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if ((resultado != 0) && (resultado2)) {
            redireccion = "Clientes/index.jsp";
            List<Cliente> clientes = dao.obtenerClientes();
            request.setAttribute("listaClientes", clientes);
            request.setAttribute("mensaje", helper.mensajeDeExito("Cliente agregado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Clientes/Editar.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        try {
            Cliente cliente_nuevo = construirObjeto(request);
            
            resultado = dao.editarCliente(cliente_nuevo);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(cliente_nuevo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CLIENTE, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Clientes/index.jsp";
            List<Cliente> clientes = dao.obtenerClientes();
            request.setAttribute("listaClientes", clientes);
            request.setAttribute("mensaje", helper.mensajeDeExito("Cliente editado correctamente"));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Clientes/index.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String id_cliente = request.getParameter("id_cliente"); 
        if(dao.relacionesConOtrasTablas(Integer.parseInt(id_cliente)) == 0){
            try {
                Cliente cliente_a_eliminar = dao.obtenerCliente(Integer.parseInt(id_cliente));

                resultado = dao.eliminarCliente(cliente_a_eliminar.getId_cliente());
                //Funcion que genera la bitacora
                BitacoraDAO bitacora = new BitacoraDAO();
                bitacora.setBitacora(cliente_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CLIENTE, request.getRemoteAddr());
                //*----------------------------*
            } catch (SIGIPROException ex) {
                request.setAttribute("mensaje", ex.getMessage());
            }
            if (resultado) {
                redireccion = "Clientes/index.jsp";
                List<Cliente> clientes = dao.obtenerClientes();
                request.setAttribute("listaClientes", clientes);
                request.setAttribute("mensaje", helper.mensajeDeExito("Cliente eliminado correctamente"));
            } else {
                redireccion = "Clientes/index.jsp";
                request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
            }
        }
        else{
            redireccion = "Clientes/index.jsp";
            List<Cliente> clientes = dao.obtenerClientes();
            request.setAttribute("listaClientes", clientes);
            request.setAttribute("mensaje", helper.mensajeDeError("No se puede borrar el cliente debido a que tiene dependencias con otras secciones"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Métodos Post Contactos">
    protected void postAgregar_contacto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException, ParseException {
        boolean resultado = false;
        String redireccion = "Clientes/AgregarContacto.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        try {
            Contactos_cliente contacto_nuevo = construirObjetoContacto(request, Integer.parseInt(request.getParameter("id_cliente")));
            
            resultado = ccdao.insertarContactos_cliente(contacto_nuevo);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(contacto_nuevo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CONTACTOS_CLIENTE, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Clientes/index.jsp";
            List<Cliente> clientes = dao.obtenerClientes();
            request.setAttribute("listaClientes", clientes);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar_contacto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Clientes/EditarContacto.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        Contactos_cliente contacto_a_editar = ccdao.obtenerContactos_cliente(Integer.parseInt(request.getParameter("id_contacto")));
        int id_cliente = contacto_a_editar.getCliente().getId_cliente();
        
        try {
            Contactos_cliente contacto_nuevo = construirObjetoContacto(request, id_cliente);
            
            resultado = ccdao.editarContactos_cliente(contacto_nuevo);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(contacto_nuevo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CONTACTOS_CLIENTE, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Clientes/index.jsp";
            List<Cliente> clientes = dao.obtenerClientes();
            request.setAttribute("listaClientes", clientes);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEliminar_contacto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "Clientes/index.jsp";
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        
        try {
            Contactos_cliente contacto_a_eliminar = ccdao.obtenerContactos_cliente(Integer.parseInt(request.getParameter("id_contacto")));
            
            resultado = ccdao.eliminarContactos_cliente(contacto_a_eliminar.getId_contacto());
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(contacto_a_eliminar.parseJSON(), Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CONTACTOS_CLIENTE, request.getRemoteAddr());
            //*----------------------------*
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        if (resultado) {
            redireccion = "Clientes/index.jsp";
            List<Cliente> clientes = dao.obtenerClientes();
            request.setAttribute("listaClientes", clientes);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
        }
        redireccionar(request, response, redireccion);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos de los Modelos">
    private Cliente construirObjeto(HttpServletRequest request) throws SIGIPROException, ParseException {
        Cliente cliente = new Cliente();
        cliente.setId_cliente(Integer.parseInt(request.getParameter("id_cliente")));
        cliente.setNombre(request.getParameter("nombre"));
        cliente.setTipo(request.getParameter("tipo"));
        cliente.setPais(request.getParameter("pais"));
        return cliente;
    }
    
    private Contactos_cliente construirObjetoContacto(HttpServletRequest request, int id_cliente) throws SIGIPROException, ParseException {
        Contactos_cliente contacto = new Contactos_cliente();
        //contacto.setId_contacto(Integer.parseInt(request.getParameter("id_contacto")));
        contacto.setNombre(request.getParameter("nombreContacto"));
        contacto.setTelefono(request.getParameter("telefono"));
        contacto.setTelefono2(request.getParameter("telefono2"));
        contacto.setCorreo_electronico(request.getParameter("correo_electronico"));
        contacto.setCorreo_electronico2(request.getParameter("correo_electronico2"));
        contacto.setCliente(dao.obtenerCliente(id_cliente));
        
        System.out.println("Datos del nuevo contacto: Nombre="+contacto.getNombre() +", Telefono="+ contacto.getTelefono() + ", idCliente="+contacto.getCliente().getId_cliente());
        return contacto;
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
