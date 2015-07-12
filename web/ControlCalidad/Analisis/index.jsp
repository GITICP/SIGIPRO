<%-- 
    Document   : index
    Created on : Jul 2, 2015, 8:43:50 PM
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
              <a href="/SIGIPRO/ControlCalidad/Analisis?">Análisis</a>
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
              <h3><i class="fa fa-gears"></i> Análisis </h3>
              <c:set var="contienePermiso" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 540}">
                  <c:set var="contienePermiso" value="true" />
                </c:if>
              </c:forEach>
              <c:if test="${contienePermiso}">
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/ControlCalidad/Analisis?accion=agregar">Agregar Análisis</a>
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
                    <th>Análisis Pendientes</th>
                    <th>Acción</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaAnalisis}" var="analisis">

                    <tr id ="${analisis.getId_analisis()}">
                      <td>
                        <a href="/SIGIPRO/ControlCalidad/Analisis?accion=ver&id_analisis=${analisis.getId_analisis()}">
                          <div style="height:100%;width:100%">
                            ${analisis.getNombre()}
                          </div>
                        </a>
                      </td>
                      <td>${analisis.getCantidad_pendiente()}</td>
                      <td><a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/ControlCalidad/Analisis?accion=agregar">Realizar</a></td>
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
