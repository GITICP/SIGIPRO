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
        
        <form id="form-eliminar-cronograma" method="post" action="Cronograma">
            <input name="accion" value="Eliminar" hidden> 
            <input id="id_cronograma" name="id_cronograma" value="${cronograma.getId_cronograma()}" hidden>
        </form>

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Producción</li>
                        <li> 
                            <a href="/SIGIPRO/Produccion/Cronograma?">Catálogo de Cronogramas de Producción</a>
                        </li>
                        <li class="active"> ${cronograma.getNombre()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-list-alt"></i> ${cronograma.getNombre()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:set var="contienePermisoEditarYBorrar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                  <c:if test="${permiso == 1 || permiso == 601}">
                                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                                  </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEditarYBorrar}">
                                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Cronograma?accion=editar&id_cronograma=${cronograma.getId_cronograma()}">Editar</a>
                                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar este cronograma" data-form-id="form-eliminar-cronograma">Eliminar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <center><p><strong>${cronograma.getNombre()}</strong></p></center>
                            <center><p>Válido desde: <i>${cronograma.getValido_desde_S()}</i></p></center>
                            <center><p>Observaciones: ${cronograma.getObservaciones()}</p></center>
                        </div>
                        <div id="table-content"></div>
                    </div>

                    <!-- END WIDGET TICKET TABLE -->
                </div>
                <!-- /main-content -->
            </div>
            <!-- /main -->
        </div>

    </jsp:attribute>

    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/plugins/editablegrid/editablegrid.js"></script>
        <script src="/SIGIPRO/recursos/js/plugins/editablegrid/editablegrid_renderers.js" ></script>
	<script src="/SIGIPRO/recursos/js/plugins/editablegrid/editablegrid_editors.js" ></script>
	<script src="/SIGIPRO/recursos/js/plugins/editablegrid/editablegrid_validators.js" ></script>
	<script src="/SIGIPRO/recursos/js/plugins/editablegrid/editablegrid_utils.js" ></script>
	<script src="/SIGIPRO/recursos/js/plugins/editablegrid/editablegrid_charts.js" ></script>
        <script src="/SIGIPRO/recursos/js/sigipro/Produccion/cronograma.js"></script>
        <script src="/SIGIPRO/recursos/js/plugins/moment/moment-with-spanish.js"></script>
        <link rel="stylesheet" type="text/css" href="/SIGIPRO/recursos/css/editablegrid-cronograma.css" media="screen"/>
    </jsp:attribute>
        
</t:plantilla_general>
