<%-- 
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
    Author     : Boga
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.basededatos.SingletonBD"%>
<%@page import="com.icp.sigipro.clases.Rol"%>
<%@page import="java.util.List"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<% 
            
    SingletonBD baseDatos = SingletonBD.getSingletonBD();
    List<Rol> roles = baseDatos.obtenerRoles();
    
    if(roles!=null)
    {
        request.setAttribute("listaRoles", roles);
    }
    

%>
<t:plantilla_general title="Seguridad" direccion_contexto="/SIGIPRO">
    
    <jsp:attribute name="contenido">

        <jsp:include page="../plantillas/barraFuncionalidad.jsp" />
        
        <!-- content-wrapper -->
        <div class="col-md-10 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Seguridad</li>
                        <li class="active">Roles</li>
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
                            <h3><i class="fa fa-legal"></i> Roles</h3>
                            <div class="btn-group widget-header-toolbar">
                                <button class="btn btn-primary btn-sm" onclick="agregarPermisos()" style="margin-left:5px;margin-right:5px;">Agregar Permisos</button>
                                <%--<button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal" style="margin-left:5px;margin-right:5px;">Agregar Permisos</button>--%>
                                <button class="btn btn-danger btn-sm" onclick="eliminarRol()" style="margin-left:5px;margin-right:5px;">Eliminar</button>
                                <button class="btn btn-warning btn-sm" onclick="editarRol()" style="margin-left:5px;margin-right:5px;" onclick="EditarRolJS()">Editar</button>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#ModalAgregarRol" style="margin-left:5px;margin-right:5px;">Agregar Rol</button>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Selección</th>
                                        <th>Nombre</th>
                                        <th>Descripción</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaRoles}" var="rol">
                                    
                                        <tr id ="${rol.getID()}">
                                            <td>
                                                <input type="radio" name="controlRol" value="${rol.getID()}">
                                            </td>
                                            <td>${rol.getNombreRol()}</td>
                                            <td>${rol.getDescripcion()}</td>
                                        </tr>
                                        
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- END COLUMN FILTER DATA TABLE -->
                </div>
                <!-- /main-content -->
            </div>
            <!-- /main -->

        <div class="widget-content">
            <div class="modal fade" id="ModalAgregarRol" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Agregar Rol</h4>
                        </div>
                        <div class="modal-body">

                        <form class="form-horizontal" role="form" action="InsertarRol" method="post">
                            ${mensajeError}
                            <label for="nombreRol" class="control-label">Nombre del Rol</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <input type="text" maxlength="45" placeholder="Nombre del Rol" class="form-control" name="nombreRol" required
                                               oninvalid="setCustomValidity('Este campo es requerido ')"
                                               oninput="setCustomValidity('')" > 
                                    </div>
                                </div>
                            </div>
                            <label for="descripcionRol" class="control-label">Descripción</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <input type="text" maxlength="500" placeholder="Drescripción del Rol" class="form-control" name="descripcionRol" required
                                               oninvalid="setCustomValidity('Este campo es requerido ')"
                                               oninput="setCustomValidity('')" > 
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
            <div class="modal fade" id="ModalEditarRol" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Editar Rol</h4>
                        </div>
                        <div class="modal-body">

                        <form class="form-horizontal" role="form" action="EditarRol" method="post">
                            ${mensajeErrorEditar}
                            <input id="editarIdRol" hidden="true" name="editarIdRol">
                            <label for="editarNombre" class="control-label">Nombre del Rol</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <input id="editarNombre" type="text" maxlength="45" placeholder="Nombre del Rol" class="form-control" name="editarNombre" required
                                               oninvalid="setCustomValidity('Este campo es requerido ')"
                                               oninput="setCustomValidity('')" > 
                                    </div>
                                </div>
                            </div>
                            <label for="editarDescripcion" class="control-label">Descripción</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <input id="editarDescripcion" type="text" maxlength="500" placeholder="Drescripción del Rol" class="form-control" name="editarDescripcion" required
                                               oninvalid="setCustomValidity('Este campo es requerido ')"
                                               oninput="setCustomValidity('')" > 
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Editar Rol</button>
                                </div>
                            </div>
                        </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="widget-content">
            <div class="modal fade" id="ModalEliminarRol" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Confirmación</h4>
                        </div>
                        <div class="modal-body">
                       <form class="form-horizontal" role="form" action="EliminarRol" method="post">
                            <h5 class="title">¿Está seguro que desea eliminar el Rol?</h5>
                            <br><br>
                            <input hidden="false" id="controlIDRol" name="controlIDRol">
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
                            
        <div class="widget-content">
            <div class="modal fade" id="modalError" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Error</h4>
                        </div>
                        <div class="modal-body">
                            <h5>Debe seleccionar un rol.</h5>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cerrar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
    </jsp:attribute>

</t:plantilla_general>
