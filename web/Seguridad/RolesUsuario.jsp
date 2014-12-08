<%-- 
    Document   : index
    Created on : Dec 6, 2014, 10:16:57 PM
    Author     : Amed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.basededatos.SingletonBD"%>
<%@page import="com.icp.sigipro.clases.RolUsuario"%>
<%@page import="com.icp.sigipro.clases.Rol"%>
<%@page import="java.util.List"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<% 
            
    SingletonBD baseDatos = SingletonBD.getSingletonBD();
    
    Cookie[] cookies = request.getCookies();
    String p_idusuario = null;

    if (cookies != null) 
    {
        for (Cookie cookie : cookies) 
        {
            if (cookie.getName().equals("idUsuario")) 
            {
                p_idusuario = cookie.getValue();
                break;
            }
        }
    }
    
    if(p_idusuario != null)
    {
        String decodificado = java.net.URLDecoder.decode(p_idusuario, "UTF-8");
        
        String[] partes = decodificado.split(";");
        p_idusuario = partes[0];
        String nombreUsuario = partes[1];
        
        List<RolUsuario> rolesusuario = baseDatos.obtenerRolesUsuario(p_idusuario);
        request.setAttribute("usuario", p_idusuario);
        request.setAttribute("nombreUsuario", nombreUsuario);

        if(rolesusuario!=null)
        {
            request.setAttribute("listaRolesUsuario", rolesusuario);
        }

        List<Rol> roles = baseDatos.obtenerRolesRestantes(p_idusuario);

        if(roles!=null)
        {
            request.setAttribute("listaRoles", roles);
        }
    }
    else
    {
        response.sendRedirect(request.getContextPath() + "/Seguridad/Usuarios.jsp");
    }
    
    
    

%>

<t:plantilla_general title="RolesUsuario" direccion_contexto="/SIGIPRO">
    
    <jsp:attribute name="contenido">

        <jsp:include page="../plantillas/barraFuncionalidad.jsp" />
        
        <!-- content-wrapper -->
        <div class="col-md-10 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Seguridad</li>
                        <li class="active">RolesUsuario</li>
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
                            <h3><i class="fa fa-group"></i> Roles de ${nombreUsuario}</h3>
                            <div class="btn-group widget-header-toolbar">
                                    <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#modalEliminarRolUsuario" style="margin-left:5px;margin-right:5px;">Eliminar</button>
                                    <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modalAgregarRolUsuario" style="margin-left:5px;margin-right:5px;">Agregar</button>

                               
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table id="datatable-column-filter-rolesusuario" class="table table-sorting table-striped table-hover datatable">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Selección</th>
                                        <th>Nombre del Rol</th>
                                        <th>Fecha Activacion</th>
                                        <th>Fecha Desactivacion</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                    <c:forEach items="${listaRolesUsuario}" var="rol">
                                    
                                        <tr>
                                            <td>
                                                <input type="radio" name="control" value="${rol.getIDRol()}">
                                            </td>
                                            <td>${rol.getNombreRol()}</td>
                                            <td>${rol.getFechaActivacion()}</td>
                                            <td>${rol.getFechaDesactivacion()}</td>
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
            <div class="modal fade" id="modalAgregarRolUsuario" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Agregar Rol</h4>
                        </div>
                        <div class="modal-body">

                       <form class="form-horizontal" role="form" action="InsertarRolUsuario" method="post">
                            ${mensajeError}
                            <input type="text" value="${usuario}"  name="usuario"  hidden="true">
                            <label for="nombreUsuario" class="control-label">*Rol</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <select name="idrol" required
                                               oninvalid="setCustomValidity('Este campo es requerido ')"
                                               oninput="setCustomValidity('')">
                                            <c:forEach items="${listaRoles}" var="rol">
                                                <option value=${rol.getID()}>${rol.getNombreRol()}</option>
                                            </c:forEach>
                                        </select>

                                    </div>
                                </div>
                            </div>
                            <label for="fechaActivacion" class="control-label">*Fecha de Activación</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control" name="fechaActivacion" data-date-format="dd/mm/yyyy" required
                                               oninvalid="setCustomValidity('Este campo es requerido ')"
                                               oninput="setCustomValidity('')">
                                    </div>
                                </div>
                            </div>
                            <label for="fechaDesactivacion" class="control-label">*Fecha de Desactivación</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker2" class="form-control" name="fechaDesactivacion" data-date-format="dd/mm/yyyy" required
                                               oninvalid="setCustomValidity('Este campo es requerido ')"
                                               oninput="setCustomValidity('')">
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
            <div class="modal fade" id="modalEliminarRolUsuario" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Confirmacion</h4>
                        </div>
                        <div class="modal-body">

                       <form class="form-horizontal" role="form" action="EliminarRolUsuario" method="post">
                            ${mensajeError}
                            <h5>¿Está seguro que desea desasignar el rol a este usuario? </h5>
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