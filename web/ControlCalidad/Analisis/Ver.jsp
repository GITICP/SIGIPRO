<%-- 
    Document   : Ver
    Created on : Jun 10, 2015, 5:39:03 PM
    Author     : Boga
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Control de Calidad" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Control de Calidad</li>
                        <li> 
                            <a href="/SIGIPRO/ControlCalidad/Analisis?">Análisis</a>
                        </li>
                        <li class="active"> Análisis ${analisis.getNombre()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> ${analisis.getNombre()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 543)}">
                                    <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar el equipo" data-href="/SIGIPRO/ControlCalidad/Analisis?accion=eliminar&id_analisis=${analisis.getId_analisis()}">Eliminar</a>
                                </c:if>

                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 542)}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/ControlCalidad/Analisis?accion=editar&id_analisis=${analisis.getId_analisis()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <div class="form-group">
                                <strong>Machote de Preparación: </strong> 
                                <c:choose>
                                    <c:when test="${analisis.getMachote() != ''}">
                                        <a href="/SIGIPRO/ControlCalidad/Analisis?accion=archivo&id_analisis=${analisis.getId_analisis()}">Descargar Machote</a>
                                    </c:when>
                                    <c:otherwise>
                                        No hay machote.
                                    </c:otherwise>
                                    
                                </c:choose>
                                <br>
                            </div>
                            ${cuerpo_datos}

                            <div class="row">
                                <div class="col-md-4">
                                    <div class="widget widget-table">
                                        <div class="widget-header">
                                            <h3><i class="fa fa-flask"></i>Tipos de Reactivos Utilizados</h3>
                                        </div>
                                        <div class="widget-content">
                                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                                                <thead>
                                                    <tr>
                                                        <th>Nombre</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${analisis.getTipos_reactivos_analisis()}" var="tipo_reactivo">
                                                        <tr>
                                                            <td>${tipo_reactivo.getNombre()}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-4">
                                    <div class="widget widget-table">
                                        <div class="widget-header">
                                            <h3><i class="fa fa-gears"></i>Tipos de Equipos Utilizados</h3>
                                        </div>
                                        <div class="widget-content">
                                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                                                <thead>
                                                    <tr>
                                                        <th>Nombre</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${analisis.getTipos_equipos_analisis()}" var="tipo_equipo">
                                                        <tr>
                                                            <td>${tipo_equipo.getNombre()}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="widget widget-table">
                                        <div class="widget-header">
                                            <h3><i class="fa fa-gears"></i>Tipos de Muestras Asociadas</h3>
                                        </div>
                                        <div class="widget-content">
                                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                                                <thead>
                                                    <tr>
                                                        <th>Nombre</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${analisis.getTipos_muestras_analisis()}" var="tipo_muestra">
                                                        <tr>
                                                            <td>${tipo_muestra.getNombre()}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
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
