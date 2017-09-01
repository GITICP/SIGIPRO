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
              <a href="/SIGIPRO/Ventas/ContratoComercializacion?">Contratos de Comercialización</a>
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
              <h3><i class="fa fa-file-text-o"></i> Contratos de Comercialización </h3>
              <div class="btn-group widget-header-toolbar">
                <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Ventas/ContratoComercializacion?accion=agregar">Agregar un Contrato de Comercialización</a>
              </div>  
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter sortable-desc">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th class="columna-escondida">Id</th>
                    <th>Nombre</th>
                    <th>Cliente</th>
                    <th>Fecha Inicial</th>
                    <th>Fecha de Renovación</th>
                    <th>Firmado</th>
                    <th>Observaciones</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaContratos}" var="contrato">

                    <tr id ="${contrato.getId_contrato()}">
                      <td class="columna-escondida">${contrato.getId_contrato()}</td>
                      <td>
                        <a href="/SIGIPRO/Ventas/ContratoComercializacion?accion=ver&id_contrato=${contrato.getId_contrato()}">
                          <div style="height:100%;width:100%">
                            ${contrato.getNombre()}
                          </div>
                        </a>
                      </td>
                      <td>
                        <a href="/SIGIPRO/Ventas/Clientes?accion=ver&id_cliente=${contrato.getCliente().getId_cliente()}">
                          <div style="height:100%;width:100%">
                            ${contrato.getCliente().getNombre()}
                          </div>
                        </a>
                      </td>
                      <td>${contrato.getFechaInicial_S()}</td>
                      <td>${contrato.getFechaRenovacion_S()}</td>
                      <td>
                        <c:choose>
                          <c:when test= "${(contrato.isFirmado())}">
                            Si
                          </c:when>
                          <c:otherwise>
                            No
                          </c:otherwise>
                        </c:choose>
                      </td>
                      <td>${contrato.getObservaciones()}</td>
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
