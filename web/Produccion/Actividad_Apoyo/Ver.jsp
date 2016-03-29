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
                            <a href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=indexactividades&id_categoria_aa=${actividad.getCategoria().getId_categoria_aa()}">Actividades de Apoyo</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=veractividad&id_actividad=${actividad.getId_actividad()}">Actividades de Apoyo Realizadas</a>
                        </li>
                        <li class="active"> Ver ${actividad.getNombre()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-flask"></i> ${actividad.getNombre()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 670)}">
                                    <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar el actividad de actividad" data-href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=eliminar&id_actividad=${actividad.getId_actividad()}">Eliminar</a>
                                </c:if>

                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 670)}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=editar&id_actividad=${actividad.getId_actividad()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Nombre:</strong></td> <td>${actividad.getNombre()} </td></tr>
                                <tr><td> <strong>Categoría de Actividad de Apoyo</strong></td> <td>${actividad.getCategoria().getNombre()} </td></tr>
                                <tr><td> <strong>Aprobación de Control de Calidad:</strong> <td>
                                        <c:choose>
                                            <c:when test="${actividad.isAprobacion_calidad()}">
                                                Aprobado
                                            </c:when>
                                            <c:otherwise>
                                                Pendiente
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr><td> <strong>Aprobación de Coordinación:</strong> <td>
                                        <c:choose>
                                            <c:when test="${actividad.isAprobacion_coordinador()}">
                                                Aprobado
                                            </c:when>
                                            <c:otherwise>
                                                Pendiente
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr><td> <strong>Aprobación de Regente Farmacéutico:</strong> <td>
                                        <c:choose>
                                            <c:when test="${actividad.isAprobacion_regente()}">
                                                Aprobado
                                            </c:when>
                                            <c:otherwise>
                                                Pendiente
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr><td> <strong>Aprobación de Director:</strong> <td>
                                        <c:choose>
                                            <c:when test="${actividad.isAprobacion_direccion()}">
                                                Aprobado
                                            </c:when>
                                            <c:otherwise>
                                                Pendiente
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr><td> <strong>Requiere aprobación:</strong> <td>
                                        <c:choose>
                                            <c:when test="${actividad.isRequiere_ap()}">
                                                Sí
                                            </c:when>
                                            <c:otherwise>
                                                No
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <c:if test="${actividad.getObservaciones()!=''}">
                                    <tr><td> <strong>Observaciones de Rechazo:</strong> <td>${actividad.getObservaciones()} </td></tr>
                                </c:if>
                            </table>
                            <br>
                            ${cuerpo_datos}
                            
                            
                            <div class="col-sm-12">
                                <div class="widget widget-table">
                                    <div class="widget-header">
                                        <h3><i class="fa fa-flask"></i> Historial de la Actividad de Apoyo </h3>
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
                                                <c:forEach items="${actividad.getHistorial()}" var="historial">

                                                    <tr id ="${historial.getId_historial()}">
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${historial.getVersion()!= actividad.getVersion()}">
                                                                    <a href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=verhistorial&id_historial=${historial.getId_historial()}&id_actividad=${actividad.getId_actividad()}">
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
                                                                <c:when test="${historial.getVersion()!= actividad.getVersion()}">
                                                                    <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 671)}">
                                                                        <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=activar&id_historial=${historial.getId_historial()}&id_actividad=${actividad.getId_actividad()}">Activar</a>
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
