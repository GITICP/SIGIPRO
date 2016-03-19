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

        <form id="form-eliminar-seguimiento" method="post" action="SeguimientoVenta">
            <input name="accion" value="Eliminar" hidden> 
            <input name="id_seguimiento" value="${seguimiento.getId_seguimiento()}" hidden>
        </form>
        
    
        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Ventas</li>
                        <li> 
                            <a href="/SIGIPRO/Ventas/SeguimientoVenta?">Seguimientos de Venta</a>
                        </li>
                        <li class="active"> Seguimiento de Venta ${seguimiento.getId_seguimiento()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Seguimiento de Venta ${seguimiento.getId_seguimiento()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:set var="contienePermisoEditarYBorrar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                  <c:if test="${permiso == 1 || permiso == 702 || permiso == 701}">
                                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                                  </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEditarYBorrar}">
                                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ventas/SeguimientoVenta?accion=editar&id_seguimiento=${seguimiento.getId_seguimiento()}">Editar</a>
                                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar esta seguimiento" data-form-id="form-eliminar-seguimiento">Eliminar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>ID: </strong></td> <td>${seguimiento.getId_seguimiento()} </td></tr>
                                <tr><td> <strong>Cliente: </strong> <td>${seguimiento.getCliente().getNombre()} </td></tr>
                                <tr><td> <strong>Factura: </strong> <td>ID: ${seguimiento.getFactura().getId_factura()} Cliente: ${seguimiento.getFactura().getCliente().getNombre()} </td></tr>
                                <tr><td> <strong>Observaciones: </strong> <td>${seguimiento.getObservaciones()} </td></tr>
                                <tr><td> <strong>Tipo: </strong> <td>${seguimiento.getTipo()} </td></tr>
                                <tr><td> <strong>Documento 1: </strong> 
                                    <td>
                                        <c:choose>
                                            <c:when test="${seguimiento.getDocumento_1() == ''}">
                                                Sin documento asociado.
                                            </c:when>
                                            <c:otherwise>
                                                <a href="/SIGIPRO/Ventas/SeguimientoVenta?accion=archivo&id_seguimiento=${seguimiento.getId_seguimiento()}">Descargar Documento</a>
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


