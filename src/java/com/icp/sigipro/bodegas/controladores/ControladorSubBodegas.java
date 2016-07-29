/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.controladores;

import com.google.gson.Gson;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.bodegas.dao.ProductoInternoDAO;
import com.icp.sigipro.bodegas.dao.SubBodegaDAO;
import com.icp.sigipro.bodegas.modelos.BitacoraSubBodega;
import com.icp.sigipro.bodegas.modelos.InventarioSubBodega;
import com.icp.sigipro.bodegas.modelos.PermisoSubBodegas;
import com.icp.sigipro.bodegas.modelos.ProductoInterno;
import com.icp.sigipro.bodegas.modelos.SubBodega;
import com.icp.sigipro.configuracion.dao.SeccionDAO;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelperFechas;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Boga
 */
@WebServlet(name = "ControladorSubBodegas", urlPatterns = {"/Bodegas/SubBodegas"})
public class ControladorSubBodegas extends SIGIPROServlet {

    private final int[] permisos = {70, 0, 0};
    private SubBodegaDAO dao = new SubBodegaDAO();
    private final SeccionDAO daoSecciones = new SeccionDAO();
    private final UsuarioDAO daoUsuarios = new UsuarioDAO();
    private final HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();

    private String redireccion;

    protected final Class clase = ControladorSubBodegas.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
            add("ingresar");
            add("consumir");
            add("mover");
            add("historial");
            add("subbodegasajax");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
            add("editar");
            add("eliminar");
            add("ingresar");
            add("consumir");
            add("mover");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);

        validarPermiso(70, listaPermisos);
        redireccion = "SubBodegas/Agregar.jsp";

        SubBodega sb = new SubBodega();

        List<Seccion> secciones = daoSecciones.obtenerSecciones();
        List<Usuario> usuarios = daoUsuarios.obtenerUsuarios();

        request.setAttribute("sub_bodega", sb);
        request.setAttribute("secciones", secciones);
        request.setAttribute("usuarios", usuarios);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIngresar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));
        redireccion = "SubBodegas/Ingresar.jsp";

        try {
            validarAcceso(SubBodegaDAO.INGRESAR, getIdUsuario(request), id_sub_bodega, listaPermisos);
        } catch (SIGIPROException ex) {
            throw new SIGIPROException(ex.getMessage(), "/index.jsp");
        }
        SubBodega sb;

        try {
            sb = dao.buscarSubBodega(id_sub_bodega);
            ProductoInternoDAO productosDAO = new ProductoInternoDAO();
            List<ProductoInterno> productos = productosDAO.obtenerProductosYCuarentena();

            request.setAttribute("sub_bodega", sb);
            request.setAttribute("productos", productos);
            request.setAttribute("accion", "Ingresar");
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega a la que desea ingresar artículos. Inténtelo nuevamente."));
            try {
                obtenerSubBodegas(request);
            } catch (SIGIPROException sig_ex) {
                request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega a la que desea ingresar artículos ni se pudo obtener el listado completo. Notifique al administrador del sistema."));
            }
            redireccion = "SubBodegas/index.jsp";
        }
        redireccionar(request, response, redireccion);
    }

    protected void getConsumir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        redireccion = "SubBodegas/Consumir.jsp";

        int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));

        try {
            validarAcceso(SubBodegaDAO.EGRESAR, getIdUsuario(request), id_sub_bodega, listaPermisos);
        } catch (SIGIPROException ex) {
            throw new SIGIPROException(ex.getMessage(), "/index.jsp");
        }

        SubBodega sb;

        try {
            sb = dao.buscarSubBodegaEInventarios(id_sub_bodega);

            request.setAttribute("sub_bodega", sb);
            request.setAttribute("inventarios", sb.getInventarios());
            request.setAttribute("accion", "Consumir");
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega de la que desea consumir artículos. Inténtelo nuevamente."));
            try {
                obtenerSubBodegas(request);
            } catch (SIGIPROException sig_ex) {
                sig_ex.printStackTrace();
                request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega de la que desea consumir artículos ni se pudo obtener el listado completo. Notifique al administrador del sistema."));
            }
            redireccion = "SubBodegas/index.jsp";
        }
        redireccionar(request, response, redireccion);
    }

    protected void getMover(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        redireccion = "SubBodegas/Mover.jsp";

        int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));

        try {
            PermisoSubBodegas permisos_sub_bodega = obtenerPermisosVer(request, id_sub_bodega);

            if (permisos_sub_bodega.isEncargado()) {
                SubBodega sb;
                try {
                    sb = dao.buscarSubBodegaEInventarios(id_sub_bodega);
                    List<SubBodega> sbs = dao.obtenerSubBodegas();
                    SubBodega temp = null;

                    for (SubBodega sub : sbs) {
                        if (sub.getId_sub_bodega() == id_sub_bodega) {
                            temp = sub;
                            break;
                        }
                    }
                    sbs.remove(temp);

                    request.setAttribute("sub_bodega", sb);
                    request.setAttribute("sub_bodegas", sbs);
                    request.setAttribute("inventarios", sb.getInventarios());
                    request.setAttribute("accion", "Mover");
                } catch (SIGIPROException ex) {
                    request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega de la que desea mover artículos. Inténtelo nuevamente."));
                    try {
                        obtenerSubBodegas(request);
                    } catch (SIGIPROException sig_ex) {
                        sig_ex.printStackTrace();
                        request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega de la que desea mover artículos. Notifique al administrador del sistema."));
                    }
                    redireccion = "SubBodegas/index.jsp";
                }
            } else {
                redireccion = "/index.jsp";
            }
        } catch (SIGIPROException ex) {
            throw new SIGIPROException(ex.getMessage(), "/index.jsp");
        }

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> lista_permisos = getPermisosUsuario(request);
        int id_usuario = getIdUsuario(request);

        try {
            validarPermisosSubBodega(id_usuario, lista_permisos);

            obtenerSubBodegas(request);
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeAdvertencia("No se pudo obtener el listado completo. Refresque la página."));
            String mensaje_log = "Error de instanciación. Mensaje: " + ex.getMessage();
            Logger.getGlobal().log(Level.SEVERE, mensaje_log);
        }

        redireccion = "SubBodegas/index.jsp";
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        redireccion = "SubBodegas/Ver.jsp";
        int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));

        PermisoSubBodegas permisos_sub_bodega = obtenerPermisosVer(request, id_sub_bodega);

        obtenerSubBodega(request, permisos_sub_bodega, id_sub_bodega);

        String agrupar = request.getParameter("agrupar");

        if (agrupar != null) {
            if (agrupar.equals("true")) {
                Object obj_sb = request.getAttribute("sub_bodega");
                if (obj_sb != null) {
                    SubBodega sb = (SubBodega) obj_sb;
                    sb.agruparPorProducto();
                    request.setAttribute("inventarios", sb.getInventarios());
                    request.setAttribute("agrupacion_por_producto", true);
                }
            }
        }

        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        redireccion = "SubBodegas/Editar.jsp";
        int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));

        SubBodega sb;
        try {
            validarAcceso(SubBodegaDAO.ENCARGADO, getIdUsuario(request), id_sub_bodega, listaPermisos);

            sb = dao.buscarSubBodega(id_sub_bodega);

            List<Usuario> usuarios_ingresos;
            List<Usuario> usuarios_egresos;
            List<Usuario> usuarios_ver;
            List<Usuario> usuarios;

            usuarios_ingresos = dao.usuariosPermisos(SubBodegaDAO.INGRESAR, id_sub_bodega);
            usuarios_egresos = dao.usuariosPermisos(SubBodegaDAO.EGRESAR, id_sub_bodega);
            usuarios_ver = dao.usuariosPermisos(SubBodegaDAO.VER, id_sub_bodega);
            usuarios = daoUsuarios.obtenerUsuarios();
            List<Seccion> secciones = daoSecciones.obtenerSecciones();

            request.setAttribute("usuarios", usuarios);
            request.setAttribute("secciones", secciones);
            request.setAttribute("usuarios_ingresos", usuarios_ingresos);
            request.setAttribute("usuarios_egresos", usuarios_egresos);
            request.setAttribute("usuarios_ver", usuarios_ver);
            request.setAttribute("sub_bodega", sb);
            request.setAttribute("accion", "Editar");
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega que está buscando. Inténtelo nuevamente."));
            try {
                obtenerSubBodegas(request);
            } catch (SIGIPROException sig_ex) {
                request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega que está buscando ni se pudo obtener el listado completo. Notifique al administrador del sistema."));
            }
            redireccion = "SubBodegas/index.jsp";
        }

        redireccionar(request, response, redireccion);
    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(13, listaPermisos);
        int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));
        // dao.eliminarProductoInterno(id_producto);
        redireccion = "SubBodegas/index.jsp";
        // Obtener todo
        // Setear
        // request.setAttribute("listaProductos", productos);
    }

    protected void getSubbodegasajax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        String resultado = "";

        int id_sub_bodega = Integer.parseInt(request.getParameter("id_subbodega"));

        try {
            SubBodega subbodega = dao.obtenerSubbodegaAjax(id_sub_bodega);
            List<InventarioSubBodega> inventario = subbodega.getInventarios();
            List<ProductoInterno> productos = new ArrayList<>();
            for (InventarioSubBodega i : inventario) {
                if (i.getCantidad() > 0) {
                    productos.add(i.getProducto());
                }
            }
            Gson gson = new Gson();
            System.out.println(gson.toJson(productos));

            resultado = gson.toJson(productos);

        } catch (SIGIPROException sig_ex) {
            // Enviar error al AJAX
        }

        out.print(resultado);

        out.flush();
    }

    protected void getHistorial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        redireccion = "SubBodegas/VerHistorial.jsp";
        int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));
        request.setAttribute("id_sub_bodega", id_sub_bodega);

        PermisoSubBodegas permisos_sub_bodega = obtenerPermisosVer(request, id_sub_bodega);

        try {
            SubBodega sb = dao.obtenerHistorial(id_sub_bodega);
            request.setAttribute("sub_bodega", sb);
            request.setAttribute("valor_movimiento", BitacoraSubBodega.MOVER);
            request.setAttribute("permisos_usuario", permisos_sub_bodega);
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError("No se pudo obtener el historial. Inténtelo nuevamente."));
        }

        redireccionar(request, response, redireccion);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        redireccion = "SubBodegas/Agregar.jsp";

        SubBodega sb = construirObjeto(request);

        try {
            dao = new SubBodegaDAO();
            String[] ids_ingresos = dao.parsearAsociacion("#i#", request.getParameter("ids-ingresos"));
            String[] ids_egresos = dao.parsearAsociacion("#e#", request.getParameter("ids-egresos"));
            String[] ids_ver = dao.parsearAsociacion("#v#", request.getParameter("ids-ver"));
            if (dao.insertar(sb, ids_ingresos, ids_egresos, ids_ver)) {
                try {
                    BitacoraDAO bitacora_dao = new BitacoraDAO();
                    bitacora_dao.setBitacora(sb.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SUBBODEGAS, request.getRemoteAddr());

                    request.setAttribute("mensaje", helper.mensajeDeExito("SubBodega agregada correctamente."));
                    obtenerSubBodegas(request);
                    redireccion = "SubBodegas/index.jsp";
                } catch (SIGIPROException ex) {
                    request.setAttribute("mensaje", helper.mensajeDeAdvertencia("SubBodega se insertó correctamente, pero no se pudo obtener el listado completo. Refresque la página."));
                    String mensaje_log = "Error de instanciación. Mensaje: " + ex.getMessage();
                    Logger.getGlobal().log(Level.SEVERE, mensaje_log);
                    redireccion = "SubBodegas/index.jsp";
                }
            }
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeAdvertencia("SubBodega no insertó correctamente vuélvalo a intentarlo."));
            request.setAttribute("", ex);
        }

        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id_sub_bodega");

        SubBodega sb = construirObjeto(request);
        sb.setId_sub_bodega(Integer.parseInt(id));

        String[] ids_ingresos = dao.parsearAsociacion("#i#", request.getParameter("ids-ingresos"));
        String[] ids_egresos = dao.parsearAsociacion("#e#", request.getParameter("ids-egresos"));
        String[] ids_ver = dao.parsearAsociacion("#v#", request.getParameter("ids-ver"));

        try {
            redireccion = "SubBodegas/index.jsp";
            if (dao.editarSubBodega(sb, ids_ingresos, ids_egresos, ids_ver)) {
                try {
                    request.setAttribute("mensaje", helper.mensajeDeExito("SubBodega editada correctamente."));
                    obtenerSubBodegas(request);
                } catch (SIGIPROException ex) {
                    request.setAttribute("mensaje", helper.mensajeDeAdvertencia("SubBodega se editó correctamente, pero no se pudo obtener el listado completo. Refresque la página."));
                    String mensaje_log = "Error de instanciación. Mensaje: " + ex.getMessage();
                    Logger.getGlobal().log(Level.SEVERE, mensaje_log);
                }
            }
        } catch (SIGIPROException ex) {
            List<Usuario> usuarios_ingresos;
            List<Usuario> usuarios_egresos;
            List<Usuario> usuarios_ver;
            List<Usuario> usuarios;

            redireccion = "SubBodegas/Editar.jsp";

            try {
                usuarios_ingresos = dao.usuariosPermisos(SubBodegaDAO.INGRESAR, sb.getId_sub_bodega());
                usuarios_egresos = dao.usuariosPermisos(SubBodegaDAO.EGRESAR, sb.getId_sub_bodega());
                usuarios_ver = dao.usuariosPermisos(SubBodegaDAO.VER, sb.getId_sub_bodega());
                usuarios = daoUsuarios.obtenerUsuarios();

                request.setAttribute("usuarios", usuarios);
                request.setAttribute("usuarios_ingresos", usuarios_ingresos);
                request.setAttribute("usuarios_egresos", usuarios_egresos);
                request.setAttribute("usuarios_ver", usuarios_ver);
                request.setAttribute("sub_bodega", sb);
                request.setAttribute("accion", "Editar");
            } catch (SIGIPROException sig_ex) {
                request.setAttribute("mensaje", helper.mensajeDeError("Error al accesar a la base de datos. Contacte al administrador del sistema."));
            }
        }

        redireccionar(request, response, redireccion);

    }

    protected void postIngresar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));

        redireccion = "SubBodegas/Ingresar.jsp";

        SubBodega sub_bodega;

        try {
            InventarioSubBodega inventario_sub_bodega = new InventarioSubBodega();
            BitacoraSubBodega bitacora = new BitacoraSubBodega();

            sub_bodega = dao.buscarSubBodega(id_sub_bodega);
            inventario_sub_bodega.setSub_bodega(sub_bodega);

            ProductoInterno producto = new ProductoInterno();
            producto.setId_producto(Integer.parseInt(request.getParameter("producto")));
            inventario_sub_bodega.setProducto(producto);

            inventario_sub_bodega.setNumero_lote(request.getParameter("numero_lote"));

            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            inventario_sub_bodega.setCantidad(cantidad);

            bitacora.setAccion(BitacoraSubBodega.INGRESAR);
            bitacora.setSub_bodega(sub_bodega);
            bitacora.setProducto(producto);
            bitacora.setCantidad(cantidad);
            bitacora.setObservaciones(request.getParameter("observaciones"));

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(this.getIdUsuario(request));

            bitacora.setUsuario(usuario);

            String fecha = request.getParameter("fecha");
            if (fecha != null) {
                try {
                    bitacora.setFecha(helper_fechas.formatearFecha(fecha));
                } catch (ParseException ex) {
                    // mostrar mensaje de error con la fecha.
                }
            }

            String fecha_vencimiento = request.getParameter("fecha_vencimiento");
            if (fecha_vencimiento != null) {
                try {
                    inventario_sub_bodega.setFecha_vencimiento(helper_fechas.formatearFecha(fecha_vencimiento));
                } catch (ParseException ex) {
                    // mostrar mensaje de error con la fecha.
                }
            }

            try {
                if (dao.registrarIngreso(inventario_sub_bodega, bitacora)) {
                    request.setAttribute("mensaje", helper.mensajeDeExito("Ingreso de artículos registrado en la sub bodega correctamente."));
                    try {
                        redireccion = "SubBodegas/Ver.jsp";
                        PermisoSubBodegas permisos_sub_bodega = obtenerPermisosVer(request, id_sub_bodega);
                        obtenerSubBodega(request, permisos_sub_bodega, id_sub_bodega);
                    } catch (SIGIPROException ex) {
                        request.setAttribute("mensaje", helper.mensajeDeError("Ingreso de artículos registrado en la sub bodega correctamente, pero hubo un error al obtener la sub bodega. Refresque la página."));
                    }
                    redireccion = "SubBodegas/Ver.jsp";
                } else {
                    throw new SIGIPROException();
                }
            } catch (SIGIPROException sig_ex) {
                request.setAttribute("mensaje", helper.mensajeDeError("Error al registrar artículo en la sub bodega. Inténtelo nuevamente."));

                ProductoInternoDAO productosDAO = new ProductoInternoDAO();
                List<ProductoInterno> productos = productosDAO.obtenerProductosYCuarentena();

                request.setAttribute("sub_bodega", sub_bodega);
                request.setAttribute("productos", productos);
                request.setAttribute("accion", "Ingresar");
            }
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega a la que desea ingresar artículos. Inténtelo nuevamente."));
            try {
                request.setAttribute("listaSubBodegas", dao.obtenerSubBodegas());
            } catch (SIGIPROException sig_ex) {
                request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega a la que desea ingresar artículos ni se pudo obtener el listado completo. Notifique al administrador del sistema."));
            }
            redireccion = "SubBodegas/index.jsp";
        }

        redireccionar(request, response, redireccion);
    }

    protected void postConsumir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        int inventario = Integer.parseInt(request.getParameter("id-inventario-sub-bodega"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        int id_sub_bodega = Integer.parseInt(request.getParameter("id-sub-bodega"));

        BitacoraSubBodega bitacora = new BitacoraSubBodega();
        Usuario u = new Usuario();
        SubBodega s = new SubBodega();
        u.setId_usuario(getIdUsuario(request));
        s.setId_sub_bodega(id_sub_bodega);
        bitacora.setUsuario(u);
        bitacora.setCantidad(cantidad);
        bitacora.setSub_bodega(s);
        bitacora.setAccion(BitacoraSubBodega.EGRESAR);
        bitacora.setObservaciones(request.getParameter("observaciones"));

        String fecha = request.getParameter("fecha");
        if (fecha != null) {
            try {
                bitacora.setFecha(helper_fechas.formatearFecha(fecha));
            } catch (ParseException ex) {
                // mostrar mensaje de error con la fecha.
            }
        }

        redireccion = "SubBodegas/index.jsp";

        try {
            dao.consumirArticulo(inventario, cantidad, bitacora);
            request.setAttribute("mensaje", helper.mensajeDeExito("Artículos descontados correctamente."));
            try {
                redireccion = "SubBodegas/Ver.jsp";
                PermisoSubBodegas permisos_sub_bodega = obtenerPermisosVer(request, id_sub_bodega);
                obtenerSubBodega(request, permisos_sub_bodega, id_sub_bodega);
            } catch (SIGIPROException sig_ex) {
                request.setAttribute("mensaje", helper.mensajeDeError("Artículos consumidos correctamente de la sub bodega, pero no se pudo obtener el listado completo."));
            }
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
            try {
                request.setAttribute("listaSubBodegas", dao.obtenerSubBodegas());
            } catch (SIGIPROException sig_ex) {
                request.setAttribute("mensaje", helper.mensajeDeError("No se pudo consumir artículos de la sub bodega ni se pudo obtener el listado completo. Notifique al administrador del sistema."));
            }
        }
        redireccionar(request, response, redireccion);
    }

    protected void postMover(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        int inventario = Integer.parseInt(request.getParameter("id-inventario-sub-bodega"));
        int sub_bodega_destino = Integer.parseInt(request.getParameter("id-sub-bodega-destino"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        int id_sub_bodega = Integer.parseInt(request.getParameter("id-sub-bodega"));

        BitacoraSubBodega bitacora = new BitacoraSubBodega();

        Usuario u = new Usuario();
        SubBodega s = new SubBodega();
        SubBodega s_destino = new SubBodega();
        u.setId_usuario(getIdUsuario(request));
        s.setId_sub_bodega(id_sub_bodega);
        s_destino.setId_sub_bodega(sub_bodega_destino);
        bitacora.setUsuario(u);
        bitacora.setCantidad(cantidad);
        bitacora.setSub_bodega(s);
        bitacora.setSub_bodega_destino(s_destino);
        bitacora.setAccion(BitacoraSubBodega.MOVER);
        bitacora.setObservaciones(request.getParameter("observaciones"));

        String fecha = request.getParameter("fecha");
        if (fecha != null) {
            try {
                bitacora.setFecha(helper_fechas.formatearFecha(fecha));
            } catch (ParseException ex) {
                // mostrar mensaje de error con la fecha.
            }
        }

        redireccion = "SubBodegas/index.jsp";

        try {
            dao.moverArticulo(inventario, cantidad, sub_bodega_destino, bitacora);
            request.setAttribute("mensaje", helper.mensajeDeExito("Artículos trasladados correctamente."));
            try {
                redireccion = "SubBodegas/Ver.jsp";
                PermisoSubBodegas permisos_sub_bodega = obtenerPermisosVer(request, id_sub_bodega);
                obtenerSubBodega(request, permisos_sub_bodega, id_sub_bodega);
            } catch (SIGIPROException sig_ex) {
                request.setAttribute("mensaje", helper.mensajeDeError("Artículos trasladados correctamente, pero no se pudo obtener el listado completo."));
            }
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
            try {
                obtenerSubBodegas(request);
            } catch (SIGIPROException sig_ex) {
                request.setAttribute("mensaje", helper.mensajeDeError("No se pudo trasladar los artículos de la sub bodega ni se pudo obtener el listado completo. Notifique al administrador del sistema."));
            }
        }
        redireccionar(request, response, redireccion);
    }

    private void obtenerSubBodegas(HttpServletRequest request) throws AuthenticationException, SIGIPROException {
        List<Integer> lista_permisos = getPermisosUsuario(request);
        int id_usuario = getIdUsuario(request);
        if (lista_permisos.contains(1) || lista_permisos.contains(70)) {
            request.setAttribute("listaSubBodegas", dao.obtenerSubBodegas());
        } else {
            request.setAttribute("listaSubBodegas", dao.obtenerSubBodegas(id_usuario));
        }
    }

    private PermisoSubBodegas obtenerPermisosVer(HttpServletRequest request, int id_sub_bodega) throws AuthenticationException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        PermisoSubBodegas permisos_sub_bodega = new PermisoSubBodegas();

        if (!listaPermisos.contains(1)) {
            try {
                permisos_sub_bodega = dao.obtenerPermisos(getIdUsuario(request), id_sub_bodega);
            } catch (SIGIPROException ex) {
                throw new SIGIPROException(ex.getMessage(), "/index.jsp");
            }
        } else {
            permisos_sub_bodega.setConsumir(true);
            permisos_sub_bodega.setEncargado(true);
            permisos_sub_bodega.setIngresar(true);
            permisos_sub_bodega.setVer(true);
        }

        return permisos_sub_bodega;
    }

    private void obtenerSubBodega(HttpServletRequest request, PermisoSubBodegas permisos_sub_bodega, int id_sub_bodega) throws SIGIPROException, AuthenticationException {
        SubBodega sb = null;
        try {
            sb = dao.buscarSubBodegaEInventarios(id_sub_bodega);

            request.setAttribute("sub_bodega", sb);

            List<Usuario> usuarios_ingresos = dao.usuariosPermisos(SubBodegaDAO.INGRESAR, sb.getId_sub_bodega());
            List<Usuario> usuarios_egresos = dao.usuariosPermisos(SubBodegaDAO.EGRESAR, sb.getId_sub_bodega());
            List<Usuario> usuarios_ver = dao.usuariosPermisos(SubBodegaDAO.VER, sb.getId_sub_bodega());

            request.setAttribute("permisos_usuario", permisos_sub_bodega);
            request.setAttribute("usuarios_ingresos", usuarios_ingresos);
            request.setAttribute("usuarios_egresos", usuarios_egresos);
            request.setAttribute("usuarios_ver", usuarios_ver);
            request.setAttribute("inventarios", sb.getInventarios());
            request.setAttribute("sub_bodega", sb);
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega que está buscando. Inténtelo nuevamente."));
            try {
                obtenerSubBodegas(request);
            } catch (SIGIPROException sig_ex) {
                request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega que está buscando ni se pudo obtener el listado completo. Notifique al administrador del sistema."));
            }
            redireccion = "SubBodegas/index.jsp";
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private SubBodega construirObjeto(HttpServletRequest request) {
        SubBodega sb = new SubBodega();

        sb.setNombre(request.getParameter("nombre"));

        Seccion seccion = new Seccion();
        Usuario usuario = new Usuario();

        seccion.setId_seccion(Integer.parseInt(request.getParameter("seccion")));
        usuario.setId_usuario(Integer.parseInt(request.getParameter("usuario")));

        sb.setSeccion(seccion);
        sb.setUsuario(usuario);

        return sb;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<String> listaAcciones;
        request.setAttribute("helper_fechas", helper_fechas);
        if (accionHTTP.equals("get")) {
            listaAcciones = accionesGet;
        } else {
            listaAcciones = accionesPost;
        }
        if (listaAcciones.contains(accion.toLowerCase())) {
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

    protected void validarAcceso(String tabla, int id_usuario, int id_sub_bodega, List<Integer> listaPermisos) throws AuthenticationException, SIGIPROException {
        if (!listaPermisos.contains(1)) {
            dao.validarAcceso(tabla, id_usuario, id_sub_bodega);
        }
    }

    protected void validarPermisosSubBodega(int id_usuario, List<Integer> permisosUsuario) throws AuthenticationException, SIGIPROException {
        try {
            boolean acceso_sub_bodega = true;
            try {
                dao.validarAcceso(id_usuario);
            } catch (AuthenticationException auth) {
                acceso_sub_bodega = false;
            }
            boolean tiene_permisos = permisosUsuario.contains(permisos[0]) || permisosUsuario.contains(1);

            if (!(acceso_sub_bodega || tiene_permisos)) {
                throw new AuthenticationException("Usuario no tiene permisos para acceder a la acción.");
            }
        } catch (NullPointerException e) {
            throw new AuthenticationException("Expiró la sesión.");
        }
    }

    // </editor-fold>
}
