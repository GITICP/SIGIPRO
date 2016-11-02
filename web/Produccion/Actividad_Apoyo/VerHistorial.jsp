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
                        <li> 
                            <a href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=ver&id_actividad=${actividad.getId_actividad()}">Ver ${actividad.getNombre()}</a>
                        </li>
                        <li class="active">Historial de ${actividad.getNombre()} - Versión ${actividad.getVersion()}</li>
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
                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 671)}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=activar&id_historial=${actividad.getId_historial()}&id_actividad=${actividad.getId_actividad()}">Activar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Nombre:</strong></td> <td>${actividad.getNombre()} </td></tr>
                                <tr><td> <strong>Versión:</strong></td> <td>${actividad.getVersion()} </td></tr>
                                <tr><td> <strong>Categoría de Actividad de Apoyo:</strong></td> <td>${actividad.getCategoria().getNombre()} </td></tr>
                                <tr><td> <strong>Requiere aprobación de Coordinación:</strong> <td>
                                        <c:choose>
                                            <c:when test="${actividad.isRequiere_coordinacion()}">
                                                Sí
                                            </c:when>
                                            <c:otherwise>
                                                No
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr><td> <strong>Requiere aprobación de Regencia</strong> <td>
                                        <c:choose>
                                            <c:when test="${actividad.isRequiere_regencia()}">
                                                Sí
                                            </c:when>
                                            <c:otherwise>
                                                No
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </table>
                            <br>
                            ${cuerpo_datos}

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
