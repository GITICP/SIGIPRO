<%-- 
    Document   : index
    Created on : Jun 29, 2015, 4:39:43 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Control de Calidad" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Control de Calidad</li>
            <li> 
              <a href="/SIGIPRO/ControlCalidad/TipoPatronControl?">Tipos de Patrones y Controles</a>
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
              <h3><i class="fa fa-gears"></i> Tipos de Patrones y Controles </h3>
              <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 580)}">
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/ControlCalidad/TipoPatronControl?accion=agregar">Agregar Tipo</a>
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
                    <th>Tipo</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaTipos}" var="tipo">

                    <tr id ="${tipo.getId_tipo_patroncontrol()}">
                      <td>
                        <a href="/SIGIPRO/ControlCalidad/TipoPatronControl?accion=ver&id_tipo_patroncontrol=${tipo.getId_tipo_patroncontrol()}">
                          <div style="height:100%;width:100%">
                            ${tipo.getNombre()}
                          </div>
                        </a>
                      </td>
                      <td>
                          ${tipo.getTipo()}
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