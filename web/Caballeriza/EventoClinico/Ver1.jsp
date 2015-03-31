<%-- 
    Document   : Ver
    Created on : 25-mar-2015, 18:22:58
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
                            <h3><i class="fa fa-barcode"></i> ${eventoclinico.getId_evento()} </h3>
                            <div class="btn-group widget-header-toolbar">

                                <!--
                                -->
                                <c:set var="contienePermisoEditar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                    <c:if test="${permiso == 1 || permiso == 44}">
                                        <c:set var="contienePermisoEditar" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEditar}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Caballeriza/EventoClinico?accion=editar&id_evento=${eventoclinico.getId_evento()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <th>Identificador</th>
                        <div class="widget-content">
                            <table>
                                <tr><td> <strong>Identificador:</strong> <td>${eventoclinico.getId_evento()} </td></tr>
                                <tr><td> <strong>Fecha:</strong> <td>${eventoclinico.getFechaAsString()} </td></tr>
                                <tr><td> <strong>Responsable:</strong> <td>${eventoclinico.getRespondable().getNombreUsuario()} </td></tr>                
                                <tr><td> <strong>Descripción</strong> <td>${eventoclinico.getDescripcion()} </td></tr>
                                <tr><td> <strong>Tipo de Evento:</strong> <td>${eventoclinico.getTipo_evento().getNombre()} </td></tr>
                            </table>
                        </div>
                        <br>

                    </div>
                    <!-- END WIDGET TICKET TABLE -->
                </div>
                <!-- /main-content -->
            </div>
            <!-- /main -->
        </div>

    </jsp:attribute>

</t:plantilla_general>