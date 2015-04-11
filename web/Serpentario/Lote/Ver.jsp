<%-- 
    Document   : Ver
    Created on : Mar 28, 2015, 3:48:39 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Serpentario" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Serpentario</li>
            <li> 
              <a href="/SIGIPRO/Serpentario/Lote?">Lotes de Veneno</a>
            </li>
            <li class="active">Lote ${lote.getNumero_lote()} </li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-tint"></i> Lote ${lote.getNumero_lote()} </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 331}">
                    <c:set var="contienePermisoEditar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Serpentario/Lote?accion=editar&id_lote=${lote.getId_lote()}">Editar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Número de Lote:</strong></td> <td>${lote.getNumero_lote()} </td></tr>
                <tr><td> <strong>Nombre de la Especie:</strong> <td>${lote.getEspecie().getGenero_especie()} </td></tr>
                <tr><td> <strong>Cantidad Actual:</strong> <td>${lote.getCantidad_actual()} Miligramos </td></tr>
                <tr><td> <strong>Cantidad Original:</strong> <td>${lote.getCantidad_original()} Miligramos</td></tr>
              </table>
              <br>
              <div class="col-md-12">
                  <div class="widget widget-table">
                    <div class="widget-header">
                      <h3><i class="fa fa-map-marker"></i> Extracciones Asociadas</h3>
                    </div>
                    <div class="widget-content">
                      <table id="datatable-column-filter-ubicaciones-formulario" class="table table-sorting table-striped table-hover datatable">
                        <thead>
                          <tr>
                            <th>Extracción</th>
                          </tr>
                        </thead>
                        <tbody>
                          <c:forEach items="${extracciones}" var="extraccion">
                            <tr>
                              <td><a href="/SIGIPRO/Serpentario/Extraccion?accion=ver&id_extraccion=${extraccion.getId_extraccion()}">${extraccion.getNumero_extraccion()}</a></td>
                            </tr>
                          </c:forEach>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
            </div>
          </div>
          <!-- END WIDGET TICKET TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>

  </jsp:attribute>

</t:plantilla_general>
