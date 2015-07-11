<%-- 
    Document   : Ver
    Created on : Jul 11, 2015, 12:19:00 PM
    Author     : ld.conejo
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
                            <a href="/SIGIPRO/ControlCalidad/Solicitud?">Solicitudes de Control de Calidad</a>
                        </li>
                        <li class="active"> ${solicitud.getNumero_solicitud()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> ${solicitud.getNumero_solicitud()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:set var="contienePermisoAnular" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                    <c:if test="${permiso == 1 || permiso == 552}">
                                        <c:set var="contienePermisoAnular" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoAnular}">
                                    <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="anular la solicitud" data-href="/SIGIPRO/ControlCalidad/Solicitud?accion=anular&id_solicitud=${solicitud.getId_solicitud()}">Anular</a>
                                </c:if>

                                <c:set var="contienePermisoEditar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                    <c:if test="${permiso == 1 || permiso == 550}">
                                        <c:set var="contienePermisoEditar" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEditar}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/ControlCalidad/Solicitud?accion=editar&id_solicitud=${solicitud.getId_solicitud()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table>
                                <tr><td> <strong>Número de Solicitud: </strong></td> <td>${solicitud.getNumero_solicitud()} </td></tr>
                                <tr><td> <strong>Usuario Solicitante: </strong> <td>${solicitud.getUsuario_solicitante().getNombre_completo()} </td></tr>
                                <tr><td> <strong>Fecha de Solicitud: </strong> <td>${solicitud.getFecha_solicitudAsString()} </td></tr>
                                <c:if test="${solicitud.getUsuario_recibido()!=null}">
                                    <tr><td> <strong>Usuario Receptor: </strong> <td>${solicitud.getUsuario_recibido().getNombre_completo()} </td></tr>
                                    <tr><td> <strong>Fecha de Recepción: </strong> <td>${solicitud.getFecha_recibidoAsString()} </td></tr>
                                </c:if>
                                <tr><td> <strong>Estado: </strong> <td>${solicitud.getEstado()} </td></tr>
                            </table>
                            <br>
                        </div>
                        <div class="col-md-12">
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-calendar"></i> Análisis Solicitados</h3>
                                </div>
                                <div class="widget-content">
                                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                        <!-- Columnas -->
                                        <thead> 
                                            <tr>
                                                <th>Nombre del Análisis</th>
                                                <th>Tipo de Muestras</th>
                                                <th>Muestras</th>
                                                <th>Acción</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${solicitud.getAnalisis_solicitud()}" var="ags">
                                                <tr id='${ags.getId_analisis_grupo_solicitud()}'>
                                                    <td>
                                                        ${ags.getAnalisis().getNombre()}
                                                    </td>
                                                    <td>
                                                        ${ags.getGrupo().getGrupos_muestras().get(0).getTipo_muestra().getNombre()}
                                                    </td>
                                                    <td>
                                                        <c:forEach items="${ags.getGrupo().getGrupos_muestras()}" var="muestra">
                                                            ${muestra.getIdentificador()} <br>
                                                        </c:forEach>
                                                    </td>
                                                    <td>
                                                        <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/ControlCalidad/Solicitud?accion=realizar&id_solicitud=${solicitud.getId_solicitud()}">Realizar</a>

                                                    </td>
                                                </tr>



                                            </c:forEach>


                                        </tbody>
                                    </table>
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

