<%-- 
    Document   : index
    Created on : Jan 7, 2016, 4:39:43 PM
    Author     : jespinozac95
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Producción" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Producción</li>
            <li> 
              <a href="/SIGIPRO/Produccion/Cronograma?">Catálogo de Cronogramas de Producción</a>
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
              <h3><i class="fa fa-list-alt"></i> Catálogo de Cronogramas de Producción </h3>
              <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 601)}">
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Cronograma?accion=agregar">Agregar Cronograma</a>
                </div>
              </c:if>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Nombre</th>
                    <th>Válido desde</th>
                    <th>Observaciones</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaCronogramas}" var="cronograma">

                    <tr id ="${cronograma.getId_cronograma()}">
                      <td>
                        <a href="/SIGIPRO/Produccion/Cronograma?accion=ver&id_cronograma=${cronograma.getId_cronograma()}">
                          <div style="height:100%;width:100%">
                            ${cronograma.getNombre()}
                          </div>
                        </a>
                      </td>
                      <td>
                          ${cronograma.getValido_desde_S()}
                      </td>
                      <td>
                          ${cronograma.getObservaciones()}
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
    
  </t:plantilla_general>
