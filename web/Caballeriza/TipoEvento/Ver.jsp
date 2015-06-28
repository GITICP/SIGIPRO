<%-- 
    Document   : Ver
    Created on : Jan 11, 2015, 11:57:19 AM
    Author     : Walter
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Caballeriza" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Caballeriza</li>
                        <li> 
                            <a href="/SIGIPRO/Caballeriza/TipoEvento?">Tipos de Eventos</a>
                        </li>
                        <li class="active"> Tipo de Evento ${tipoevento.getNombre()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-book"></i> Tipo de Evento ${tipoevento.getNombre()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:set var="contienePermisoEliminar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                    <c:if test="${permiso == 1 || permiso == 47}">
                                        <c:set var="contienePermisoEliminar" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEliminar}">
                                    <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar el tipo" data-href="/SIGIPRO/Caballeriza/TipoEvento?accion=eliminar&id_tipo_evento=${tipoevento.getId_tipo_evento()}">Eliminar</a>
                                </c:if>

                                <c:set var="contienePermisoEditar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                    <c:if test="${permiso == 1 || permiso == 48}">
                                        <c:set var="contienePermisoEditar" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEditar}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Caballeriza/TipoEvento?accion=editar&id_tipo_evento=${tipoevento.getId_tipo_evento()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table>
                                <tr><td> <strong>Nombre del tipo</strong></td> <td>${tipoevento.getNombre()} </td></tr>
                                <tr><td> <strong>Descripción:</strong> <td>${tipoevento.getDescripcion()} </td></tr>
                            </table>
                            <br>
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-check"></i> Eventos del Tipo Asociados</h3>
                                </div>
                                <div class="widget-content">
                                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-desc-filter" data-columna-filtro="1">
                                        <thead>
                                            <tr>
                                                <th>Identificador</th>
                                                <th>Fecha</th>
                                                <th>Descripción</th>
                                                <th>Observaciones</th>
                                                <th>Responsable</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${eventos}" var="evento">
                                                <tr id="${evento.getId_evento()}">
                                                    <td>${evento.getId_evento()}</td>
                                                    <td>${evento.getFechaAsString()}</td>
                                                    <td>${evento.getDescripcion()}</td>
                                                    <td>${evento.getObservaciones()}</td>
                                                    <td>${evento.getResponsable().getNombre_completo()}</td>
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

    </jsp:attribute>

</t:plantilla_general>
