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

        <form id="form-eliminar-factura" method="post" action="Factura">
            <input name="accion" value="Eliminar" hidden> 
            <input name="id_factura" value="${factura.getId_factura()}" hidden>
        </form>
    
        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Ventas</li>
                        <li> 
                            <a href="/SIGIPRO/Ventas/Factura?">Facturas</a>
                        </li>
                        <li class="active"> Factura ${factura.getId_factura()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Factura ${factura.getId_factura()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:set var="contienePermisoEditarYBorrar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                  <c:if test="${permiso == 1 || permiso == 702 || permiso == 701}">
                                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                                  </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEditarYBorrar}">
                                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ventas/Factura?accion=editar&id_factura=${factura.getId_factura()}">Editar</a>
                                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar esta factura" data-form-id="form-eliminar-factura">Eliminar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>ID: </strong></td> <td>${factura.getId_factura()} </td></tr>
                                <tr><td> <strong>Cliente: </strong> <td>${factura.getCliente().getNombre()} </td></tr>
                                <tr><td> <strong>ID Orden de Compra: </strong> 
                                    <c:choose>
                                            <c:when test="${factura.getOrden().getId_orden() == 0}">
                                                <td></td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>${factura.getOrden().getId_orden()}</td>
                                            </c:otherwise>
                                        </c:choose>
                                </tr>
                                <tr><td> <strong>Fecha: </strong> <td>${factura.getFecha_S()} </td></tr>
                                <tr><td> <strong>Monto: </strong> <td>${factura.getMonto()} ${factura.getMoneda()} </td></tr>
                                <tr><td> <strong>Moneda: </strong> <td>${factura.getMoneda()} </td></tr>
                                <tr><td> <strong>Fecha de Vencimiento: </strong> <td>${factura.getFecha_vencimiento_S()} </td></tr>
                                <tr><td> <strong>Documento 1: </strong> 
                                    <td>
                                        <c:choose>
                                            <c:when test="${factura.getDocumento_1() == ''}">
                                                Sin documento asociado.
                                            </c:when>
                                            <c:otherwise>
                                                <a href="/SIGIPRO/Ventas/Factura?accion=archivo&id_factura=${factura.getId_factura()}&documento=1">Descargar Documento</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr><td> <strong>Documento 2: </strong> 
                                    <td>
                                        <c:choose>
                                            <c:when test="${factura.getDocumento_2() == ''}">
                                                Sin documento asociado.
                                            </c:when>
                                            <c:otherwise>
                                                <a href="/SIGIPRO/Ventas/Factura?accion=archivo&id_factura=${factura.getId_factura()}&documento=2">Descargar Documento</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr><td> <strong>Documento 3: </strong> 
                                    <td>
                                        <c:choose>
                                            <c:when test="${factura.getDocumento_3() == ''}">
                                                Sin documento asociado.
                                            </c:when>
                                            <c:otherwise>
                                                <a href="/SIGIPRO/Ventas/Factura?accion=archivo&id_factura=${factura.getId_factura()}&documento=3">Descargar Documento</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr><td> <strong>Documento 4: </strong> 
                                    <td>
                                        <c:choose>
                                            <c:when test="${factura.getDocumento_4() == ''}">
                                                Sin documento asociado.
                                            </c:when>
                                            <c:otherwise>
                                                <a href="/SIGIPRO/Ventas/Factura?accion=archivo&id_factura=${factura.getId_factura()}&documento=4">Descargar Documento</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr><td> <strong>Tipo: </strong> <td>${factura.getTipo()}</td></tr>
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


