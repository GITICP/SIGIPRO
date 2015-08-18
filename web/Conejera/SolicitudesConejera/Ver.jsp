<%-- 
    Document   : Ver
    Created on : Feb 19, 2015, 6:29:37 PM
    Author     : Amed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Conejera" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Bioterio - Conejera</li>
                        <li> 
                            <a href="/SIGIPRO/Conejera/SolicitudesConejera?">Solicitudes Conejera</a>
                        </li>
                        <li class="active"> Número ${solicitud.getId_solicitud()} </li>
                    </ul>
                </div>
                <div class="col-md-8 ">
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
                            <h3><i class="fa fa-list-alt"></i> Solicitud Número ${solicitud.getId_solicitud()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:if test="${solicitud.getEstado() == 'Pendiente'}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Conejera/SolicitudesConejera?accion=editar&id_solicitud=${solicitud.getId_solicitud()}">Editar</a>
                                    <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar la Solicitud" data-href="/SIGIPRO/Conejera/SolicitudesConejera?accion=eliminar&id_solicitud=${solicitud.getId_solicitud()}">Eliminar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Numero de Solicitud:</strong> <td>${solicitud.getId_solicitud()} </td></tr>
                                <tr><td> <strong>Fecha Solicitud:</strong> <td>${solicitud.getFecha_solicitud_S()} </td></tr>
                                <tr><td> <strong>Fecha en que se necesita</strong> <td>${solicitud.getFecha_necesita_S()} </td></tr>
                                <tr><td> <strong>Numero de Animales:</strong> <td>${solicitud.getNumero_animales()} </td></tr>
                                <tr><td> <strong>Peso Requerido:</strong> <td>${solicitud.getPeso_requerido()} </td></tr>
                                <tr><td> <strong>Sexo:</strong> <td>  ${solicitud.getSexo()}</td></tr>
                                <tr><td> <strong>Usuario Solicitante:</strong> <td>  ${solicitud.getUsuario_solicitante().getNombreCompleto()}</td></tr>
                                <tr><td> <strong>Observaciones:</strong> <td>  ${solicitud.getObservaciones()}</td></tr>
                                <tr><td> <strong>Observaciones de Rechazo:</strong> <td> ${solicitud.getObservaciones_rechazo()}</td></tr>
                                <tr><td> <strong>Estado:</strong> <td> ${solicitud.getEstado()}</td></tr>
                                <tr><td> <strong>Usuario que utiliza:</strong> <td>  ${solicitud.getUsuario_utiliza().getNombreCompleto()}</td></tr>
                            </table>
                            <br>


                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-check"></i> Entregas de la Solicitud</h3>
                                </div>
                                <div class="widget-content">
                                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-desc-filter">
                                        <thead>
                                            <tr>
                                                <th>Fecha de Entrega</th>
                                                <th>Usuario Recipiente</th>
                                                <th>Número de Animales</th>
                                                <th>Peso</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${entregas}" var="entrega">
                                                <tr id ="${entrega.getId_entrega()}">
                                                    <td>
                                                        <a href="/SIGIPRO/Conejera/SolicitudesConejera?accion=verentrega&id_entrega=${entrega.getId_entrega()}">
                                                            <div style="height:100%;width:100%">
                                                                ${entrega.getFecha_entrega()}
                                                            </div>
                                                        </a>
                                                    </td>
                                                    <td>${entrega.getUsuario_recipiente().getNombreCompleto()}</td>
                                                    <td>${entrega.getNumero_animales()}</td>
                                                    <td>${entrega.getPeso()}</td>
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

