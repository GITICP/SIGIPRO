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
                            <a href="/SIGIPRO/Caballeriza/EventoClinico?">Eventos Clínicos</a>
                        </li>
                        <li class="active"> ${eventoclinico.getId_evento()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-book"></i> ${eventoclinico.getId_evento()} </h3>
                            <div class="btn-group widget-header-toolbar">

                                <c:set var="contienePermisoEditar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                    <c:if test="${permiso == 1 || permiso == 56}">
                                        <c:set var="contienePermisoEditar" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEditar}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Caballeriza/EventoClinico?accion=editar&id_evento=${eventoclinico.getId_evento()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table>
                                <tr><td> <strong>Identificador:</strong></td> <td>${eventoclinico.getId_evento()} </td></tr>
                                <tr><td> <strong>Tipo del Evento:</strong></td> <td>${eventoclinico.getTipo_evento().getNombre()} </td></tr>
                                <tr><td> <strong>Descripción:</strong> <td>${eventoclinico.getDescripcion()} </td></tr>
                                <tr><td> <strong>Fecha:</strong> <td>${eventoclinico.getFechaAsString()} </td></tr>
                                <tr><td> <strong>Reespondable:</strong> <td>
                                        <c:set var="val" value=""/>
                                        <c:choose> 
                                            <c:when test="${eventoclinico.getResponsable().getNombreUsuario() == null}">
                                                No Tiene Usuario Responsable
                                            </c:when>
                                            <c:otherwise>
                                                ${eventoclinico.getResponsable().getNombreUsuario()}
                                            </c:otherwise>
                                        </c:choose>
                                    </td></tr>                                         
                            </table>
                            <br>
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-check"></i> Caballos del Evento Clínico </h3>
                                </div>
                                <div class="widget-content">
                                    <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable">
                                        <thead>
                                            <tr>
                                                <th>Nombre y Número de Microchip</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${caballos}" var="caballo">
                                                <tr id="${caballo.getId_caballo()}">
                                                    <td>${caballo.getNombre()} (${caballo.getNumero_microchip()})</td>
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
