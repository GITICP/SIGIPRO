<%-- 
    Document   : index
    Created on : Dec 6, 2014, 10:16:57 PM
    Author     : Amed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.basededatos.SingletonBD"%>
<%@page import="com.icp.sigipro.clases.PermisoRol"%>
<%@page import="com.icp.sigipro.clases.Permiso"%>
<%@page import="java.util.List"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<% 
            
    SingletonBD baseDatos = SingletonBD.getSingletonBD();
    
    Cookie[] cookies = request.getCookies();
    String p_idrol = null;

    if (cookies != null) 
    {
        for (Cookie cookie : cookies) 
        {
            if (cookie.getName().equals("idRol")) 
            {
                p_idrol = cookie.getValue();
                break;
            }
        }
    }
    
    if(p_idrol != null)
    {
        String decodificado = java.net.URLDecoder.decode(p_idrol, "UTF-8");
        
        String[] partes = decodificado.split(";");
        p_idrol = partes[0];
        String nombreRol = partes[1];
        
        List<PermisoRol> permisosrol = baseDatos.obtenerPermisosRol(p_idrol);
        request.setAttribute("idRol", p_idrol);
        request.setAttribute("nombreRol", nombreRol);

        if(permisosrol!=null)
        {
            request.setAttribute("listaPermisosRol", permisosrol);
        }

        List<Permiso> permisos = baseDatos.obtenerPermisosRestantes(p_idrol);

        if(permisos!=null)
        {
            request.setAttribute("listaPermisos", permisos);
        }
    }
    else
    {
        response.sendRedirect(request.getContextPath() + "/Seguridad/Roles.jsp");
    }
    
    
    

%>

<t:plantilla_general title="PermisosRol" direccion_contexto="/SIGIPRO">
    
    <jsp:attribute name="contenido">

        <jsp:include page="../plantillas/barraFuncionalidad.jsp" />
        
        <!-- content-wrapper -->
        <div class="col-md-10 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Seguridad</li>
                        <li class="active">PermisosRol</li>
                    </ul>
                </div>
                <div class="col-md-8 ">
                    <div class="top-content">

                    </div>
                </div>
            </div>

            <!-- main -->
            <div class="content">
                <div class="main-content">
                    
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-group"></i> Permisos del Rol: ${nombreRol}</h3>
                            <div class="btn-group widget-header-toolbar">
                                    <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#modalEliminarPermisoRol" style="margin-left:5px;margin-right:5px;">Eliminar</button>
                                    <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modalAgregarPermisoRol" style="margin-left:5px;margin-right:5px;">Agregar</button>

                               
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table id="datatable-column-filter-permisosrol" class="table table-sorting table-striped table-hover datatable">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Selección</th>
                                        <th>Nombre del Permiso</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                    <c:forEach items="${listaPermisosRol}" var="permiso">
                                    
                                        <tr>
                                            <td>
                                                <input type="radio" name="controlPermiso" value="${permiso.getIDPermiso()}">
                                            </td>
                                            <td>${permiso.getNombrePermiso()}</td>
                                        </tr>
                                        
                                    </c:forEach>
                                    
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- END COLUMN FILTER DATA TABLE -->
                </div>
                <!-- END WIDGET TICKET TABLE -->
            </div>
            <!-- /main-content -->
        </div>
        <!-- /main -->

        <div class="widget-content">
            <div class="modal fade" id="modalAgregarPermisoRol" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Agregar Permiso</h4>
                        </div>
                        <div class="modal-body">

                       <form class="form-horizontal" role="form" action="InsertarPermisoRol" method="post">
                            ${mensajeError}
                            <input type="text" value="${idRol}"  name="rol"  hidden="true">
                            <label for="nombreUsuario" class="control-label">*Rol</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <select name="idpermiso" required
                                               oninvalid="setCustomValidity('Este campo es requerido ')"
                                               oninput="setCustomValidity('')">
                                            <c:forEach items="${listaPermisos}" var="permiso">
                                                <option value=${permiso.getID()}>${permiso.getNombrePermiso()}</option>
                                            </c:forEach>
                                        </select>

                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Agregar Rol</button>
                                </div>
                            </div>
                        </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                            
        <div class="widget-content">
            <div class="modal fade" id="modalEliminarPermisoRol" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Confirmación</h4>
                        </div>
                        <div class="modal-body">

                       <form class="form-horizontal" role="form" action="EliminarPermisoRol" method="post">
                            ${mensajeError}
                            <h5>¿Está seguro que desea desasignar el permiso a este rol? </h5>
                            <input hidden="false" id="controlIDPermiso" name="controlIDPermiso">
                            <input type="text" value="${idRol}"  name="rol"  hidden="true">
                            <div class="form-group">
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Confirmar</button>
                                </div>
                            </div>
                        </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>        
        
    </jsp:attribute>

</t:plantilla_general>
