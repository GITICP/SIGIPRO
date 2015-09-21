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
              <a href="/SIGIPRO/ControlCalidad/TipoEquipo?">Tipos de Equipos</a>
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
              <h3><i class="fa fa-gears"></i> Tipos de Equipos </h3>
              <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 500)}">
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/ControlCalidad/TipoEquipo?accion=agregar">Agregar Tipo</a>
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
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaTipos}" var="tipo">

                    <tr id ="${tipo.getId_tipo_equipo()}">
                      <td>
                        <a href="/SIGIPRO/ControlCalidad/TipoEquipo?accion=ver&id_tipo_equipo=${tipo.getId_tipo_equipo()}">
                          <div style="height:100%;width:100%">
                            ${tipo.getNombre()}
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

  </t:plantilla_general>