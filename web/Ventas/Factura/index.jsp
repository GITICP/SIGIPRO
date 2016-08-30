<%-- 
    Document   : index
    Created on : Jun 29, 2015, 5:02:21 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ventas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/Factura?">Facturas</a>
            </li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-gears"></i> Facturas </h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-warning btn-sm boton-accion " href="/SIGIPRO/Ventas/Factura?accion=actualizarestados">Actualizar Estados</a>
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Ventas/Factura?accion=agregar">Agregar Factura</a>
                </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>ID</th>
                    <th>Número de Factura</th>
                    <th>Proyecto</th>
                    <th>Cliente</th>
                    <th>Orden de Compra</th>
                    <th>Fecha</th>
                    <th>Monto</th>
                    <th>Moneda</th>
                    <th>Fecha de Vencimiento</th>
                    <th>Tipo</th>
                    <th>Estado</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaFacturas}" var="factura">

                    <tr id ="${factura.getId_factura()}">
                      <td>
                        <a href="/SIGIPRO/Ventas/Factura?accion=ver&id_factura=${factura.getId_factura()}">
                          <div style="height:100%;width:100%">
                            ${factura.getId_factura()}
                          </div>
                        </a>
                      </td>
                      <td>${factura.getNumero()}</td>
                      <td>${factura.getProyecto()}</td>
                      <td>${factura.getCliente().getNombre()}</td>
                      <c:choose>
                            <c:when test="${factura.getOrden().getId_orden() == 0}">
                                <td></td>
                            </c:when>
                            <c:otherwise>
                                <td>${factura.getOrden().getId_orden()}</td>
                            </c:otherwise>
                        </c:choose>
                      <td>${factura.getFecha_S()}</td>
                      <c:choose>
                            <c:when test="${factura.getMoneda() == 'Colones'}">
                              <td>&#8353;${factura.getMonto()}</td>
                            </c:when>
                            <c:when test="${factura.getMoneda() == 'Dólares'}">
                              <td>$${factura.getMonto()}</td>
                            </c:when>
                            <c:when test="${factura.getMoneda() == 'Euros'}">
                              <td>&euro;${factura.getMonto()}</td>
                            </c:when>
                            <c:otherwise>
                                <td>${factura.getMoneda()}</td>
                            </c:otherwise>
                        </c:choose>
                      <td>${factura.getMoneda()}</td>
                      <td>${factura.getFecha_vencimiento_S()}</td>
                      <td>${factura.getTipo()}</td>
                      <td>${factura.getEstado()}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
          <!-- END COLUMN FILTER DATA TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>

    </jsp:attribute>

  </t:plantilla_general>
