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
                                <tr><td> <strong>Proyecto: </strong> 
                                    <c:choose>
                                            <c:when test="${factura.getProyecto() == 404}">
                                                <td>0418-00</td>
                                            </c:when>
                                            <c:when test="${factura.getProyecto() == 1965}">
                                                <td>1770-00</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>2541-00</td>
                                            </c:otherwise>
                                        </c:choose>
                                </tr>
                                <tr><td> <strong>Estado: </strong></td> 
                                    <c:choose>
                                        <c:when test="${factura.getEstado().equals('Cancelado')}">
                                           <td><font color="green">${factura.getEstado()}</font></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td><font color="blue">${factura.getEstado()}</font></td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                                <tr><td> <strong>ID Orden de Compra: </strong> 
                                    <c:choose>
                                            <c:when test="${factura.getOrden().getId_orden() == 0}">
                                                <td></td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>
                                                    <a href="/SIGIPRO/Ventas/OrdenCompra?accion=ver&id_orden=${factura.getOrden().getId_orden()}">
                                                    <div style="height:100%;width:100%">
                                                        ${factura.getOrden().getId_orden()}
                                                    </div>
                                                    </a>
                                                  </td>
                                            </c:otherwise>
                                        </c:choose>
                                </tr>
                                <tr><td> <strong>Fecha: </strong> <td>${factura.getFecha_S()} </td></tr>
                                <tr><td> <strong>Monto: </strong> 
                                    <c:choose>
                                        <c:when test="${factura.getMoneda() == 'Colones'}">
                                          <td>&#8353;${String.format("%,.2f", factura.getMonto().doubleValue())}</td>
                                        </c:when>
                                        <c:when test="${factura.getMoneda() == 'Dólares'}">
                                          <td>$${String.format("%,.2f", factura.getMonto().doubleValue())}</td>
                                        </c:when>
                                        <c:when test="${factura.getMoneda() == 'Euros'}">
                                          <td>&euro;${String.format("%,.2f", factura.getMonto().doubleValue())}</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>${String.format("%,.2f", factura.getMonto().doubleValue())}</td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                                <tr><td> <strong>Monto Pendiente: </strong> 
                                    <c:choose>
                                        <c:when test="${factura.getMoneda() == 'Colones'}">
                                          <td>&#8353;${String.format("%,.2f", factura.getMonto_pendiente().doubleValue())}</td>
                                        </c:when>
                                        <c:when test="${factura.getMoneda() == 'Dólares'}">
                                          <td>$${String.format("%,.2f", factura.getMonto_pendiente().doubleValue())}</td>
                                        </c:when>
                                        <c:when test="${factura.getMoneda() == 'Euros'}">
                                          <td>&euro;${String.format("%,.2f", factura.getMonto_pendiente().doubleValue())}</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>${String.format("%,.2f", factura.getMonto_pendiente().doubleValue())}</td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
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
                            
                            <div class="widget widget-table">
                                <div class="widget-header">
                                  <h3><i class="fa fa-usd"></i> Pagos Asociados </h3>
                                  
                                </div>
                                <div class="widget-content">
                                  <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                    <thead>
                                      <tr>
                                        <th>ID</th>
                                        <th>Código</th>
                                        <th>Monto</th>
                                        <th>Nota</th>
                                        <th>Fecha</th>
                                        <th>Consecutivo</th>
                                        <th>Moneda</th>
                                      </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${pagos}" var="pago">
                                          <tr id ="${pago.getId_pago()}">
                                            <td>
                                              <a href="/SIGIPRO/Ventas/Pago?accion=ver&id_pago=${pago.getId_pago()}">
                                              <div style="height:100%;width:100%">
                                                  ${pago.getId_pago()}
                                              </div>
                                              </a>
                                            </td>
                                            <td>${pago.getCodigo()}</td>
                                              <c:choose>
                                                    <c:when test="${pago.getMoneda() == 'Colones'}">
                                                      <td>&#8353;${pago.getMonto()}</td>
                                                    </c:when>
                                                    <c:when test="${pago.getMoneda() == 'Dólares'}">
                                                      <td>$${pago.getMonto()}</td>
                                                    </c:when>
                                                    <c:when test="${pago.getMoneda() == 'Euros'}">
                                                      <td>&euro;${pago.getMonto()}</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>${pago.getMonto()}</td>
                                                    </c:otherwise>
                                                </c:choose>
                                              <td>${pago.getNota()}</td>
                                              <td>${pago.getFecha()}</td>
                                              <td>${pago.getConsecutive()}</td>
                                              <td>${pago.getMoneda()}</td>
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


