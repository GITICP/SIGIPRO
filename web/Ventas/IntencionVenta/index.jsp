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
              <a href="/SIGIPRO/Ventas/IntencionVenta?">Solicitudes o Intenciones de Venta</a>
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
                <h3><i class="fa fa-list-alt"></i> Solicitudes o Intenciones de Venta </h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Ventas/IntencionVenta?accion=agregar">Agregar una Solicitud o Intención de Venta</a>
                </div>  
            </div>
            ${mensaje}
            <div class="widget-content">
                <table id="tabla_intenciones" class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                      <th id="IDcolumn">ID</th>
                    <th>Cliente</th>
                    <th>Observaciones</th>
                    <th>Estado</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaIntenciones}" var="intencion">

                    <tr id ="${intencion.getId_intencion()}">
                      <td>
                        <a href="/SIGIPRO/Ventas/IntencionVenta?accion=ver&id_intencion=${intencion.getId_intencion()}">
                        <div style="height:100%;width:100%">
                            ${intencion.getId_intencion()}
                        </div>
                        </a>
                      </td>
                      <c:choose>
                          <c:when test= "${intencion.getCliente() != null}">
                              <td>
                                <a href="/SIGIPRO/Ventas/IntencionVenta?accion=ver&id_intencion=${intencion.getId_intencion()}">
                                    <div style="height:100%;width:100%">
                                        ${intencion.getCliente().getNombre()}
                                    </div>
                                </a>
                              </td>
                          </c:when>
                          <c:otherwise>
                              <td>${intencion.getNombre_cliente()}</td>
                          </c:otherwise>
                        </c:choose>
                      <td>${intencion.getObservaciones()}</td>
                      <td>${intencion.getEstado()}</td>
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
    <script src="${direccion_contexto}/SIGIPRO/recursos/js/sigipro/Intencion_venta.js"></script>
