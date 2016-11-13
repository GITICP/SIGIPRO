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
              <a href="/SIGIPRO/Ventas/OrdenCompra?">Órdenes de Compra</a>
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
                <h3><i class="fa fa-list-alt"></i> Órdenes de Compra </h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Ventas/OrdenCompra?accion=agregar">Agregar una Orden de Compra</a>
                </div>  
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Consecutivo</th>
                    <th>Cotización</th>
                    <th>Intención</th>
                    <th>Información de Rotulación</th>
                    <th>Estado</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaOrdenes}" var="orden">

                    <tr id ="${orden.getId_orden()}">
                      <td>
                        <a href="/SIGIPRO/Ventas/OrdenCompra?accion=ver&id_orden=${orden.getId_orden()}">
                        <div style="height:100%;width:100%">
                            ${orden.getId_orden()}
                        </div>
                        </a>
                      </td>
                      <c:choose>
                          <c:when test= "${orden.getCotizacion().getId_cotizacion() == 0}">
                              <td></td>
                          </c:when>
                          <c:otherwise>
                              <td>${orden.getCotizacion().getIdentificador()}</td>
                          </c:otherwise>
                      </c:choose>
                      <c:choose>
                          <c:when test= "${orden.getIntencion().getId_intencion() == 0}">
                              <td></td>
                          </c:when>
                          <c:otherwise>
                              <td>${orden.getIntencion().getId_intencion()}</td>
                          </c:otherwise>
                      </c:choose>
                      <td>${orden.getRotulacion()}</td>
                      <td>${orden.getEstado()}</td>
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
