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
                            <a href="/SIGIPRO/Produccion/Paso?">Pasos de Protocolo</a>
                        </li>
                        <li class="active"> ${paso.getNombre()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-flask"></i> ${paso.getNombre()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 650)}">
                                    <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar el paso de protocolo" data-href="/SIGIPRO/Produccion/Paso?accion=eliminar&id_paso=${paso.getId_paso()}">Eliminar</a>
                                </c:if>

                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 650)}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Paso?accion=editar&id_paso=${paso.getId_paso()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Nombre:</strong></td> <td>${paso.getNombre()} </td></tr>
                            </table>
                            <br>
                            ${cuerpo_datos}
                            
                            
                            <div class="col-sm-12">
                                <div class="widget widget-table">
                                    <div class="widget-header">
                                        <h3><i class="fa fa-flask"></i> Historial del paso de protocolo </h3>
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
                                                <c:forEach items="${paso.getHistorial()}" var="historial">

                                                    <tr id ="${historial.getId_historial()}">
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${historial.getVersion()!= paso.getVersion()}">
                                                                    <a href="/SIGIPRO/Produccion/Paso?accion=verhistorial&id_historial=${historial.getId_historial()}&id_paso=${paso.getId_paso()}">
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
                                                                <c:when test="${historial.getVersion()!= paso.getVersion()}">
                                                                    <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 651)}">
                                                                        <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Paso?accion=activar&id_historial=${historial.getId_historial()}&id_paso=${paso.getId_paso()}">Activar</a>
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
