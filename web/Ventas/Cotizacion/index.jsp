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
              <a href="/SIGIPRO/Ventas/Cotizacion?">Cotizaciones</a>
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
                <h3><i class="fa fa-list-alt"></i> Cotizaciones </h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Ventas/Cotizacion?accion=agregar">Agregar una Cotización</a>
                </div>  
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>ID</th>
                    <th>Identificador</th>
                    <th>Intención</th>
                    <th>Moneda</th>
                    <th>Total</th>
                    <th>Flete</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaCotizaciones}" var="cotizacion">

                    <tr id ="${cotizacion.getId_cotizacion()}">
                      <td>
                        <a href="/SIGIPRO/Ventas/Cotizacion?accion=ver&id_cotizacion=${cotizacion.getId_cotizacion()}">
                        <div style="height:100%;width:100%">
                            ${cotizacion.getId_cotizacion()}
                        </div>
                        </a>
                      </td>
                      <td>${cotizacion.getIdentificador()}</td>
                      <td>${cotizacion.getIntencion().getId_intencion()}</td>
                      <td>${cotizacion.getMoneda()}</td>
                      <c:choose>
                            <c:when test="${cotizacion.getMoneda() == 'Colones'}">
                              <td align="right">&#8353;${String.format("%,.2f", cotizacion.getTotal().doubleValue())}</td>
                            </c:when>
                            <c:when test="${cotizacion.getMoneda() == 'Dólares'}">
                              <td align="right">$${String.format("%,.2f", cotizacion.getTotal().doubleValue())}</td>
                            </c:when>
                            <c:when test="${cotizacion.getMoneda() == 'Euros'}">
                              <td align="right">&euro;${String.format("%,.2f", cotizacion.getTotal().doubleValue())}</td>
                            </c:when>
                            <c:otherwise>
                                <td align="right">${String.format("%,.2f", cotizacion.getTotal().doubleValue())}</td>
                            </c:otherwise>
                        </c:choose>
                      <c:choose>
                            <c:when test="${cotizacion.getMoneda() == 'Colones'}">
                              <td align="right">&#8353;${String.format("%,.2f", cotizacion.getFlete().doubleValue())}</td>
                            </c:when>
                            <c:when test="${cotizacion.getMoneda() == 'Dólares'}">
                              <td align="right">$${String.format("%,.2f", cotizacion.getFlete().doubleValue())}</td>
                            </c:when>
                            <c:when test="${cotizacion.getMoneda() == 'Euros'}">
                              <td align="right">&euro;${String.format("%,.2f", cotizacion.getFlete().doubleValue())}</td>
                            </c:when>
                            <c:otherwise>
                                <td align="right">${String.format("%,.2f", cotizacion.getFlete().doubleValue())}</td>
                            </c:otherwise>
                        </c:choose>
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
