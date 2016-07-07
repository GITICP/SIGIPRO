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
                            <a href="/SIGIPRO/Produccion/Protocolo?">Protocolo de Producción</a>
                        </li>
                        <li class="active"> ${protocolo.getNombre()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-flask"></i> ${protocolo.getNombre()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 640)}">
                                    <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar la fórmula maestra" data-href="/SIGIPRO/Produccion/Protocolo?accion=eliminar&id_protocolo=${protocolo.getId_protocolo()}">Eliminar</a>
                                </c:if>

                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 640)}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Protocolo?accion=editar&id_protocolo=${protocolo.getId_protocolo()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Nombre:</strong></td> <td>${protocolo.getNombre()} </td></tr>
                                <tr><td> <strong>Descripción:</strong> <td>${protocolo.getDescripcion()} </td></tr>
                                <tr><td> <strong>Version:</strong> <td>${protocolo.getVersion()} </td></tr>
                                <tr><td> <strong>Aprobación de Control de Calidad:</strong> <td>
                                        <c:choose>
                                            <c:when test="${protocolo.getAprobacion_calidad()}">
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
                                            <c:when test="${protocolo.getAprobacion_coordinador()}">
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
                                            <c:when test="${protocolo.getAprobacion_regente()}">
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
                                            <c:when test="${protocolo.getAprobacion_direccion()}">
                                                Aprobado
                                            </c:when>
                                            <c:otherwise>
                                                Pendiente
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr><td> <strong>Aprobación de Gestión de Calidad:</strong> <td>
                                        <c:choose>
                                            <c:when test="${protocolo.getAprobacion_gestion()}">
                                                Aprobado
                                            </c:when>
                                            <c:otherwise>
                                                Pendiente
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <c:if test="${protocolo.getObservaciones()!=''}">
                                    <tr><td> <strong>Observaciones de Rechazo:</strong> <td>${protocolo.getObservaciones()} </td></tr>
                                </c:if>
                                <tr><td> <strong>Fórmula Maestra:</strong> <td>${protocolo.getFormula_maestra().getNombre()} </td></tr>
                                <tr><td> <strong>Producto Terminado:</strong> <td>${protocolo.getProducto().getNombre()} </td></tr>
                            </table>
                            <br>
                            <div class="col-sm-6">
                                <div class="widget widget-table">
                                    <div class="widget-header">
                                        <h3><i class="fa fa-flask"></i> Pasos de Protocolo </h3>
                                    </div>
                                    <div class="widget-content">
                                        <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                            <!-- Columnas -->
                                            <thead> 
                                                <tr>
                                                    <th>Posicion</th>
                                                    <th>Paso de Protocolo</th>
                                                    <th>Aprobación</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${protocolo.getPasos()}" var="paso">

                                                    <tr id ="${paso.getId_paso()}">
                                                        <td>
                                                            ${paso.getPosicion()}
                                                        </td>
                                                        <td>
                                                            <a href="/SIGIPRO/Produccion/Paso?accion=ver&id_paso=${paso.getId_paso()}">
                                                                <div style="height:100%;width:100%">
                                                                    ${paso.getNombre()}
                                                                </div>
                                                            </a>
                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${paso.isRequiere_ap()}">
                                                                    Sí
                                                                </c:when>
                                                                <c:otherwise>
                                                                    No
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
                            <div class="col-sm-6">
                                <div class="widget widget-table">
                                    <div class="widget-header">
                                        <h3><i class="fa fa-flask"></i> Historial del protocolo </h3>
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
                                                <c:forEach items="${protocolo.getHistorial()}" var="historial">

                                                    <tr id ="${historial.getId_historial()}">
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${historial.getVersion()!= protocolo.getVersion()}">
                                                                    <a href="/SIGIPRO/Produccion/Protocolo?accion=verhistorial&id_historial=${historial.getId_historial()}&id_protocolo=${protocolo.getId_protocolo()}">
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
                                                                <c:when test="${historial.getVersion()!= protocolo.getVersion()}">
                                                                    <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 645)}">
                                                                        <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Protocolo?accion=activar&id_historial=${historial.getId_historial()}&id_protocolo=${protocolo.getId_protocolo()}">Activar</a>
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