<%-- 
    Document   : Ver
    Created on : Jun 29, 2015, 5:02:50 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ventas" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <form id="form-eliminar-reunion" method="post" action="ReunionProduccion">
            <input name="accion" value="Eliminar" hidden> 
            <input name="id_reunion" value="${reunion.getId_reunion()}" hidden>
        </form>
    
        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Ventas</li>
                        <li> 
                            <a href="/SIGIPRO/Ventas/ReunionProduccion?">Reuniones de Producción</a>
                        </li>
                        <li class="active"> Reunión de Producción ${reunion.getId_reunion()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Reunión de Producción ${reunion.getId_reunion()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:set var="contienePermisoEditarYBorrar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                  <c:if test="${permiso == 1 || permiso == 702 || permiso == 701}">
                                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                                  </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEditarYBorrar}">
                                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ventas/ReunionProduccion?accion=editar&id_reunion=${reunion.getId_reunion()}">Editar</a>
                                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar esta reunion" data-form-id="form-eliminar-reunion">Eliminar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>ID: </strong></td> <td>${reunion.getId_reunion()} </td></tr>
                                <tr><td> <strong>Fecha: </strong> <td>${reunion.getFecha_S()} </td></tr>
                                <tr><td> <strong>Observaciones: </strong> <td>${reunion.getObservaciones()} </td></tr>
                                <tr><td> <strong>Minuta: </strong> 
                                    <td>
                                        <c:choose>
                                            <c:when test="${reunion.getMinuta() == ''}">
                                                Sin minuta asociada.
                                            </c:when>
                                            <c:otherwise>
                                                <a href="/SIGIPRO/Ventas/ReunionProduccion?accion=archivo&id_reunion=${reunion.getId_reunion()}">Descargar Minuta</a>
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


