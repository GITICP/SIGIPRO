<%-- 
    Document   : Ver
    Created on : Jun 29, 2015, 4:48:27 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Producción" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Producción</li>
                        <li>
                            <a href="/SIGIPRO/Produccion/Actividad_Apoyo">Categorías de Actividades de Apoyo</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=indexactividades&id_categoria_aa=${respuesta.getActividad().getCategoria().getId_categoria_aa()}">Actividades de Apoyo</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=veractividad&id_actividad=${respuesta.getActividad().getId_actividad()}&version=${respuesta.getVersion_usada()}">Actividades de Apoyo Realizadas</a>
                        </li>
                        <li class="active"> Respuesta - ${respuesta.getNombre()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-flask"></i> Respuesta de  ${respuesta.getActividad().getCategoria().getNombre()} - ${respuesta.getActividad().getNombre()} - ${respuesta.getNombre()} </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Nombre:</strong></td> <td>${respuesta.getNombre()} </td></tr>
                                <tr><td> <strong>Fecha:</strong></td> <td>${respuesta.getFechaAsString()} </td></tr>
                                <tr><td> <strong>Versión de Actividad de Apoyo:</strong></td> <td><a href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=verhistorial&id_historial=${respuesta.getActividad().getId_historial()}&id_actividad=${respuesta.getActividad().getId_actividad()}">Version ${respuesta.getVersion_usada()}</a></td>
                                <tr><td> <strong>Usuario realizar:</strong></td> <td>${respuesta.getUsuario_realizar().getNombre_completo()} </td></tr>
                                <c:if test="${respuesta.getUsuario_cerrar().getId_usuario()!=0}">
                                    <tr><td> <strong>Usuario cerrar:</strong></td> <td>${respuesta.getUsuario_cerrar().getNombre_completo()} </td></tr>
                                </c:if>
                                <c:if test="${respuesta.getUsuario_aprobar_coordinacion().getId_usuario()!=0}">
                                    <tr><td> <strong>Usuario aprobar Coordinación:</strong></td> <td>${respuesta.getUsuario_aprobar_coordinacion().getNombre_completo()} </td></tr>
                                </c:if>
                                <c:if test="${respuesta.getUsuario_aprobar_regencia().getId_usuario()!=0}">
                                    <tr><td> <strong>Usuario aprobar Regencia:</strong></td> <td>${respuesta.getUsuario_aprobar_regencia().getNombre_completo()} </td></tr>
                                </c:if>
                                <c:if test="${respuesta.getObservaciones()!=null}">
                                    <tr><td> <strong>Observaciones de rechazo:</strong></td> <td>${respuesta.getObservaciones()} </td></tr>
                                </c:if>
                            </table>
                            <br>
                            <div class="col-md-12">
                                ${cuerpo_datos}
                            </div>


                            <div class="col-sm-12">
                                <div class="widget widget-table">
                                    <div class="widget-header">
                                        <h3><i class="fa fa-flask"></i> Historial del Respuestas </h3>
                                    </div>
                                    <div class="widget-content">
                                        <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                            <!-- Columnas -->
                                            <thead> 
                                                <tr>
                                                    <th>Version</th>
                                                    <th>Estado</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${respuesta.getHistorial()}" var="historial">

                                                    <tr id ="${historial.getId_historial()}">
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${historial.getVersion()!= respuesta.getVersion()}">
                                                                    <a href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=verhistorialrespuesta&id_historial=${historial.getId_historial()}&id_respuesta=${respuesta.getId_respuesta()}">
                                                                        <div style="height:100%;width:100%">
                                                                            Version ${historial.getVersion()}
                                                                        </div>
                                                                    </a>                                                                
                                                                </c:when>
                                                                <c:otherwise>
                                                                    Version ${historial.getVersion()}
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${historial.getVersion()!= respuesta.getVersion()}">
                                                                    <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 676)}">
                                                                        <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=activarrespuesta&id_historial=${historial.getId_historial()}&id_respuesta=${respuesta.getId_respuesta()}">Activar</a>
                                                                    </c:if>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <a class="btn btn-warning btn-sm boton-accion" disabled>Activo</a>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
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
