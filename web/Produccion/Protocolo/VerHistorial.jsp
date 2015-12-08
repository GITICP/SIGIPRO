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
                        <li><a href="/SIGIPRO/Produccion/Protocolo?accion=ver&id_protocolo=${protocolo.getId_protocolo()}">Historial de ${protocolo.getNombre()}</a> </li>
                        <li class="active">Versión ${protocolo.getVersion()}</li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-flask"></i> Historial de ${protocolo.getNombre()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 640)}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Protocolo?accion=activar&id_historial=${protocolo.getId_historial()}&id_protocolo=${protocolo.getId_protocolo()}">Activar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Nombre:</strong></td> <td>${protocolo.getNombre()} </td></tr>
                                <tr><td> <strong>Descripción:</strong> <td>${protocolo.getDescripcion()} </td></tr>
                                <tr><td> <strong>Versión:</strong> <td>${protocolo.getVersion()} </td></tr>
                                <tr><td> <strong>Fórmula Maestra:</strong> <td>${protocolo.getFormula_maestra().getNombre()} </td></tr>
                                <tr><td> <strong>Producto Terminado:</strong> <td>${protocolo.getProducto().getNombre()} </td></tr>
                                <tr><td><strong>Estado:</strong><td>Inactivo</td></tr>
                            </table>
                            <br>
                            <div class="col-sm-12">
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
