<%-- 
    Document   : index
    Created on : Mar 16, 2015, 6:11:43 PM
    Author     : jespinozac95
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
              <a href="/SIGIPRO/Ventas/Pago?">Pagos</a>
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
                <h3><i class="fa fa-file-text-o"></i> Pagos </h3>
                <div class="btn-group widget-header-toolbar">
                </div>  
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>ID</th>
                    <th>Código</th>
                    <th>Número de Factura</th>
                    <th>Monto</th>
                    <th>Nota</th>
                    <th>Fecha</th>
                    <th>Consecutivo</th>
                    <th>Moneda</th>
                    <th>Código de Remisión</th>
                    <th>Consecutivo de Remisión</th>
                    <th>Fecha de Remisión</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaPagos}" var="Pago">

                    <tr id ="${Pago.getId_pago()}">
                      <td>
                        <a href="/SIGIPRO/Ventas/Pago?accion=ver&id_pago=${Pago.getId_pago()}">
                        <div style="height:100%;width:100%">
                            ${Pago.getId_pago()}
                        </div>
                        </a>
                      </td>
                      <td>${Pago.getCodigo()}</td>
                      <td>
                          <a href="/SIGIPRO/Ventas/Factura?accion=ver&id_factura=${Pago.getFactura().getId_factura()}">
                        <div style="height:100%;width:100%">
                            ${Pago.getFactura().getNumero()}
                        </div>
                      </td>
                      <c:choose>
                            <c:when test="${Pago.getMoneda() == 'Colones'}">
                              <td>&#8353;${Pago.getMonto()}</td>
                            </c:when>
                            <c:when test="${Pago.getMoneda() == 'Dólares'}">
                              <td>$${Pago.getMonto()}</td>
                            </c:when>
                            <c:when test="${Pago.getMoneda() == 'Euros'}">
                              <td>&euro;${Pago.getMonto()}</td>
                            </c:when>
                            <c:otherwise>
                                <td>${Pago.getMonto()}</td>
                            </c:otherwise>
                        </c:choose>
                      <td>${Pago.getNota()}</td>
                      <td>${Pago.getFecha()}</td>
                      <td>${Pago.getConsecutive()}</td>
                      <td>${Pago.getMoneda()}</td>
                      <td>${Pago.getCodigo_remision()}</td>
                      <td>${Pago.getConsecutive_remision()}</td>
                      <td>${Pago.getFecha_remision()}</td>
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
