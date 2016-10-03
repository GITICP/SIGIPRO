<%-- 
    Document   : Agregar
    Created on : Dec 14, 2014, 1:43:27 PM
    Author     : Boga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Reportes" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-8 ">
                    <ul class="breadcrumb">
                        <li>Reportes</li>
                        <li>
                            <a href="/SIGIPRO/Reportes/Reportes?">Reportes</a>
                        </li>
                        <li class="active"> Ver Reporte</li>
                    </ul>
                </div>
                <div class="col-md-4 ">
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
                            <h3><i class="fa fa-table"></i> Ver Reporte de ${reporte.getNombre()}</h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">

                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-th-list"></i> Parámetros</h3>
                                    <div class="btn-group widget-header-toolbar">
                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 1501)}">
                                            <a class="btn btn-warning btn-sm boton-accion" href="Reportes?accion=permisos&id_reporte=${reporte.getId_reporte()}">Modificar Permisos</a>
                                        </c:if>
                                        <a class="btn btn-primary btn-sm boton-accion" onclick="history.back();">Volver</a>
                                    </div>

                                </div>
                                <div class="widget-content">
                                    <div id="fila-parametros" class="row">
                                        <input type="hidden" name="id_reporte" value="${reporte.getId_reporte()}">
                                        <c:forEach items="${reporte.getParametros()}" var="parametro">
                                            <t:parametro parametro="${parametro}" />
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>

                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-table"></i> Resultados</h3>
                                    <div class="btn-group widget-header-toolbar">
                                        <a id="exportar-excel" class="btn btn-primary btn-sm boton-accion" disabled>Exportar a Excel</a>
                                        <a id="actualizar-datos" class="btn btn-primary btn-sm boton-accion">Actualizar Datos</a>
                                    </div>
                                </div>
                                <div id="panel-contenido-resultados" class="widget-content"></div>
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
    <jsp:attribute name="scripts">
        <c:choose>
            <c:when test="${!reporte.tieneJS()}">
                <script src="/SIGIPRO/recursos/js/sigipro/Reportes/reporte-default.js"></script>
            </c:when>
            <c:otherwise>
                <script src="/Reportes-SIGIPRO/${reporte.getUrl_js()}.js"></script>
            </c:otherwise>
        </c:choose>

    </jsp:attribute>

</t:plantilla_general>
