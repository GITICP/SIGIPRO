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
              <a href="/SIGIPRO/Ventas/SeguimientoVenta?">Seguimientos de Venta</a>
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
              <h3><i class="fa fa-gears"></i> Seguimientos de Venta </h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Ventas/SeguimientoVenta?accion=agregar">Agregar Seguimiento de Venta</a>
                </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter sortable-desc">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>ID</th>
                    <th>Cliente</th>
                    <th>Factura</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaSeguimientos}" var="seguimiento">

                    <tr id ="${seguimiento.getId_seguimiento()}">
                      <td>
                        <a href="/SIGIPRO/Ventas/SeguimientoVenta?accion=ver&id_seguimiento=${seguimiento.getId_seguimiento()}">
                          <div style="height:100%;width:100%">
                            ${seguimiento.getId_seguimiento()}
                          </div>
                        </a>
                      </td>
                      <td>
                          <a href="/SIGIPRO/Ventas/Clientes?accion=ver&id_cliente=${seguimiento.getCliente().getId_cliente()}">
                            <div style="height:100%;width:100%">
                                  ${seguimiento.getCliente().getNombre()}
                            </div>
                            </a>
                      </td>
                      <td>
                          <a href="/SIGIPRO/Ventas/Factura?accion=ver&id_factura=${seguimiento.getFactura().getId_factura()}">
                          <div style="height:100%;width:100%">
                              ID: ${seguimiento.getFactura().getId_factura()} Cliente: ${seguimiento.getFactura().getCliente().getNombre()}
                          </div>
                        </a>
                      </td>
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
  <jsp:attribute name="scripts">
    <script src="/SIGIPRO/recursos/js/sigipro/sortTables.js"></script>
  </jsp:attribute>
  </t:plantilla_general>
