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
                            <a href="/SIGIPRO/ControlCalidad/TipoPatronControl?">Tipos de Materiales de Referencia</a>
                        </li>
                        <li class="active"> ${tipopatroncontrol.getNombre()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> ${tipopatroncontrol.getNombre()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 582)}">
                                    <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar el tipo de patrón o control" data-href="/SIGIPRO/ControlCalidad/TipoPatronControl?accion=eliminar&id_tipo_patroncontrol=${tipopatroncontrol.getId_tipo_patroncontrol()}">Eliminar</a>
                                </c:if>
                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 581)}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/ControlCalidad/TipoPatronControl?accion=editar&id_tipo_patroncontrol=${tipopatroncontrol.getId_tipo_patroncontrol()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Nombre del Tipo: </strong></td> <td>${tipopatroncontrol.getNombre()} </td></tr>
                                <tr><td> <strong>Descripción: </strong> <td>${tipopatroncontrol.getDescripcion()} </td></tr>
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

