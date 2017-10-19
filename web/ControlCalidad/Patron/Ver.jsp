<%-- 
    Document   : Ver
    Created on : Jun 29, 2015, 4:48:27 PM
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
                            <a href="/SIGIPRO/ControlCalidad/Patron?">Materiales de Referencia</a>
                        </li>
                        <li class="active"> Material de Referencia ${patron.getNumero_lote()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Material de Referencia ${patron.getNumero_lote()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 573)}">
                                    <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar este patrón" data-href="/SIGIPRO/ControlCalidad/Patron?accion=eliminar&id_patron=${patron.getId_patron()}">Eliminar</a>
                                </c:if>
                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 572)}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/ControlCalidad/Patron?accion=editar&id_patron=${patron.getId_patron()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Número de Lote/Identificador: </strong></td> <td>${patron.getNumero_lote()} </td></tr>
                                <tr><td> <strong>Tipo: </strong></td> <td>${patron.getTipo().getNombre()} (${patron.getTipo().getTipo()}) </td></tr>
                                <tr><td> <strong>Fecha de Vencimiento: </strong></td> <td>${patron.getFecha_vencimientoAsString()} </td></tr>
                                <tr><td> <strong>Fecha de Ingreso: </strong></td> <td>${patron.getFecha_ingresoAsString()} </td></tr>
                                <tr><td> <strong>Fecha de Inicio de Uso: </strong></td> <td>${patron.getFecha_inicio_usoAsString()} </td></tr>
                                <tr><td> <strong>Lugar de Almacenamiento: </strong></td> <td>${patron.getLugar_almacenamiento()} </td></tr>
                                <tr><td> <strong>Condición de Almacenamiento: </strong></td> <td>${patron.getCondicion_almacenamiento()} </td></tr>
                                <tr><td> <strong>Observaciones: </strong></td> <td>${patron.getObservaciones()} </td></tr>
                                <tr><td> <strong>Certificado: </strong></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${patron.getCertificado() != ''}">
                                                <a href="/SIGIPRO/ControlCalidad/Patron?accion=certificado&id_patron=${patron.getId_patron()}">Descargar Certificado</a>
                                            </c:when>
                                            <c:otherwise>
                                                Sin certificado.
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </table>
                            <br>
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

