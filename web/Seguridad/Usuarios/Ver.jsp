<%-- 
    Document   : Usuario
    Created on : Dec 14, 2014, 11:44:52 AM
    Author     : Boga
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.seguridad.modelos.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Seguridad" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-10 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Seguridad</li>
                        <li>Usuarios</li>
                        <li class="active"> ${usuario.getNombreUsuario()} </li>
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
                            <h3><i class="fa fa-group"></i> ${usuario.getNombreUsuario()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <a class="btn btn-danger btn-sm"  style="margin-left:5px;margin-right:5px;color:#fff;" data-toggle="modal" data-target="#modalDesactivarUsuario">Desactivar</a>                                    
                                <a class="btn btn-warning btn-sm" style="margin-left:5px;margin-right:5px;color:#fff;" href="Editar?id=${usuario.getID()}">Editar</a>
                                <a class="btn btn-primary btn-sm" style="margin-left:5px;margin-right:5px;color:#fff;" href="Agregar">Agregar</a>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <p>Nombre de Usuario: ${usuario.getNombreUsuario()}</p>
                            <p>Nombre Completo: ${usuario.getNombreCompleto()}</p>
                            <p>Correo Electrónico: ${usuario.getCorreo()}</p>
                            <p>Cedula: ${usuario.getCedula()}</p>
                            <p>Departamento: ${usuario.getDepartamento()}</p>
                            <p>Puesto: ${usuario.getPuesto()}</p>
                            <p>Fecha de Activación: ${usuario.getFechaActivacion()}</p>
                            <p>Fecha de Desactivacion:  ${usuario.getFechaDesactivacion()}</p>
                            <p>Estado: ${usuario.getActivo()}</p>

                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-group"></i> Roles ${usuario.getNombreUsuario()} </h3>
                                    <div class="btn-group widget-header-toolbar">
                                        <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#modalAgregarRolUsuario" style="margin-left:5px;margin-right:5px;">Agregar</button>
                                    </div>
                                </div>
                                <div class="widget-content">
                                    <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
                                        <thead>
                                            <tr>
                                                <th>Nombre Rol</th>
                                                <th>Fecha Activación</th>
                                                <th>Fecha Desactivación</th>
                                                <th>Editar/Eliminar</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${rolesUsuario}" var="rolUsuario">
                                                <tr id="${rolUsuario.getIDRol()}">
                                                    <td>${rolUsuario.getNombreRol()}</td>
                                                    <td>${rolUsuario.getFechaActivacion()}</td>
                                                    <td>${rolUsuario.getFechaDesactivacion()}</td>
                                                    <td>
                                                        <button class="btn btn-primary btn-sm" onclick="editarRolUsuario(${rolUsuario.getIDRol()})"   style="margin-left:5px;margin-right:5px;">Editar</button>
                                                        <button class="btn btn-primary btn-sm" onclick="eliminarRolUsuario(${rolUsuario.getIDRol()})" style="margin-left:5px;margin-right:5px;">Eliminar</button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- END WIDGET TICKET TABLE -->
                </div>
                <!-- /main-content -->
            </div>
            <!-- /main -->
        </div>

        <t:modal idModal="modalAgregarRolUsuario" titulo="Agregar Rol">

            <jsp:attribute name="form">

                <form class="form-horizontal" role="form" action="InsertarRolUsuario" method="post">
                    <input type="text" value="${usuario.getID()}"  name="usuario"  hidden="true">
                    <label for="nombreUsuario" class="control-label">*Rol</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <select name="idrol" required
                                        oninvalid="setCustomValidity('Este campo es requerido ')"
                                        oninput="setCustomValidity('')">
                                    <c:forEach items="${rolesRestantes}" var="rol">
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
                                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fechaActivacion" data-date-format="dd/mm/yyyy" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       onchange="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
                    <label for="fechaDesactivacion" class="control-label">*Fecha de Desactivación</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker2" class="form-control sigiproDatePicker" name="fechaDesactivacion" data-date-format="dd/mm/yyyy" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       onchange="setCustomValidity('')">
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

            </jsp:attribute>

        </t:modal>

        <t:modal idModal="modalEditarRolUsuario" titulo="Editar Rol">

            <jsp:attribute name="form">

                <form class="form-horizontal" role="form" action="EditarRolUsuario" method="post">
                    <input type="text" value="${usuario.getID()}"  name="idUsuarioEditar"  hidden="true">
                    <input type="text" id="idRolUsuarioEditar"     name="idRolEditar"      hidden="true">
                    <input type="text" name="rol"  hidden="true">
                    <label for="nombreUsuario" class="control-label">*Rol</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <select name="idrol" required
                                        oninvalid="setCustomValidity('Este campo es requerido')"
                                        oninput="setCustomValidity('')">
                                    <c:forEach items="${rolesRestantes}" var="rol">
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
                                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker3" class="form-control sigiproDatePicker" name="editarFechaActivacion" data-date-format="dd/mm/yyyy" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       onchange="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
                    <label for="fechaDesactivacion" class="control-label">*Fecha de Desactivación</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker4" class="form-control sigiproDatePicker" name="editarFechaDesactivacion" data-date-format="dd/mm/yyyy" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       onchange="setCustomValidity('')">
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

            </jsp:attribute>

        </t:modal>

        <t:modal idModal="modalEliminarRolUsuario" titulo="Confirmacion">

            <jsp:attribute name="form">
                <form class="form-horizontal" role="form" action="EliminarRolUsuario" method="post">
                    <h5>¿Está seguro que desea desasignar el rol a este usuario? </h5>
                    <input hidden="false" id="idRolUsuarioEliminar" name="controlIDRol">
                    <input type="text" value="${usuario.getID()}"  name="usuario"  hidden="true">
                    <div class="form-group">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar </button>
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Confirmar </button>
                        </div>
                    </div>
                </form>
            </jsp:attribute>

        </t:modal>
                                
        <t:modal idModal="modalDesactivarUsuario" titulo="Confirmacion">

            <jsp:attribute name="form">
                <form class="form-horizontal" role="form" action="Desactivar" method="post">
                    <h5>¿Está seguro que desea desactivar este usuario? </h5>
                    <input type="text" value="${usuario.getID()}"  name="usuario"  hidden="true">
                    <div class="form-group">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar </button>
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Confirmar </button>
                        </div>
                    </div>
                </form>
            </jsp:attribute>

        </t:modal>

    </jsp:attribute>

</t:plantilla_general>