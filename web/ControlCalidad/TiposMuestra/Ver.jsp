<%-- 
    Document   : Ver
    Created on : Jan 11, 2015, 11:57:19 AM
    Author     : Walter
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
                            <a href="/SIGIPRO/ControlCalidad/TiposMuestra?">Tipos de Muestra</a>
                        </li>
                        <li class="active"> ${tipo_muestra.getNombre()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-flask"></i> ${tipo_muestra.getNombre()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 563)}">
                                    <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar el tipo de muestra" data-href="/SIGIPRO/ControlCalidad/TiposMuestra?accion=eliminar&id_tipos_muestra=${tipo_muestra.getId_tipo_muestra()}">Eliminar</a>
                                </c:if>

                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 562)}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/ControlCalidad/TiposMuestra?accion=editar&id_tipo_muestra=${tipo_muestra.getId_tipo_muestra()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table>
                                <tr><td> <strong>Nombre:</strong></td> <td>${tipo_muestra.getNombre()} </td></tr>
                                <tr><td> <strong>Descripción:</strong> <td>${tipo_muestra.getDescripcion()} </td></tr>
                            </table>
                            <br>
                        </div>
                        <div class="col-md-12">
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-gears"></i>Análisis Asociadas</h3>
                                </div>
                                <div class="widget-content">
                                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                                        <thead>
                                            <tr>
                                                <th>Nombre</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${tipo_muestra.getTipos_muestras_analisis()}" var="analisis">
                                                <tr>
                                                    <td>${analisis.getNombre()}</td>
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
