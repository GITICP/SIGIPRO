<%-- 
    Document   : index
    Created on : Dec 6, 2014, 10:16:57 PM
    Author     : Amed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.basededatos.SingletonBD"%>
<%@page import="com.icp.sigipro.clases.RolUsuario"%>
<%@page import="java.util.List"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<% 
            
    SingletonBD baseDatos = SingletonBD.getSingletonBD();
    
    String p_idusuario = "1";
    List<RolUsuario> roles = baseDatos.obtenerRolesUsuario(p_idusuario);

    if(roles!=null)
    {
        request.setAttribute("listaRolesUsuario", roles);
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
                            <h3><i class="fa fa-group"></i> Roles de Usuario</h3>
                            <div class="btn-group widget-header-toolbar">
                                <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#myModal" style="margin-left:5px;margin-right:5px;">Eliminar</button>
                                <button class="btn btn-warning btn-sm" data-toggle="modal" data-target="#myModal" style="margin-left:5px;margin-right:5px;">Editar</button>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modalUsuarios" style="margin-left:5px;margin-right:5px;">Agregar</button>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table id="datatable-column-filter" class="table table-sorting table-striped table-hover datatable">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Nombre del Rol</th>
                                        <th>Fecha Activacion</th>
                                        <th>Fecha Desactivacion</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                    <c:forEach items="${listaRolesUsuario}" var="rol">
                                    
                                        <tr>
                                            <td>
                                                <input type="radio" name="control" value="${rol.getID()}">
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
            <div class="modal fade" id="modalUsuarios" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Agregar Usuario</h4>
                        </div>
                        <div class="modal-body">

                       <form class="form-horizontal" role="form" action="InsertarRolUsuario" method="post">
                            <p class="title">Agregar Usuario</p>
                            ${mensajeError}
                            <label for="nombreUsuario" class="control-label">*Nombre de Usuario</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <input type="text" maxlength="45" placeholder="Nombre de Usuario" class="form-control" name="nombreUsuario" required
                                               oninvalid="setCustomValidity('Este campo es requerido ')"
                                               oninput="setCustomValidity('')" > 
                                    </div>
                                </div>
                            </div>
                            <label for="nombreCompleto" class="control-label">*Nombre Completo</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <input type="text" maxlength="200" placeholder="Nombre Completo" class="form-control" name="nombreCompleto" required
                                               oninvalid="setCustomValidity('Este campo es requerido ')"
                                               oninput="setCustomValidity('')">
                                    </div>
                                </div>
                            </div>
                            <label for="correoElectronico" class="control-label">*Correo Electrónico</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-at"></i></span>
                                        <input type="email" maxlength="45" placeholder="usuario@icp.ucr.ac.cr" class="form-control" name="correoElectronico" required
                                               oninvalid="setCustomValidity('Este campo es requerido ')"
                                               oninput="setCustomValidity('')">
                                    </div>
                                </div>
                            </div>
                            <label for="cedula" class="control-label">*Cédula</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-at"></i></span>
                                        <input type="text" placeholder="1-0001-4628" pattern="[0-9]{1}-[0-9]{4}-[0-9]{4}" class="form-control" name="cedula" required
                                               oninvalid="setCustomValidity('Este campo es requerido ')"
                                               oninput="setCustomValidity('')">
                                    </div>
                                </div>
                            </div>
                            <label for="departamento" class="control-label">*Departamento</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-at"></i></span>
                                        <input type="text" maxlength="200" placeholder="Producción" class="form-control" name="departamento" required
                                               oninvalid="setCustomValidity('Este campo es requerido ')"
                                               oninput="setCustomValidity('')">
                                    </div>
                                </div>
                            </div>
                            <label for="puesto" class="control-label">*Puesto</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-at"></i></span>
                                        <input type="text" maxlength="200" placeholder="Jefe" class="form-control" name="puesto" required
                                               oninvalid="setCustomValidity('Este campo es requerido ')"
                                               oninput="setCustomValidity('')">
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
                                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Agregar Usuario</button>
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