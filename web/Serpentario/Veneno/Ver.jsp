<%-- 
    Document   : Ver
    Created on : Mar 28, 2015, 11:26:24 PM
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
              <a href="/SIGIPRO/Serpentario/Veneno?">Venenos de Serpiente</a>
            </li>
            <li class="active">Veneno de ${veneno.getEspecie().getGenero_especie()} </li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-flask"></i> Veneno de ${veneno.getEspecie().getGenero_especie()}  </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 342}">
                    <c:set var="contienePermisoEditar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Serpentario/Veneno?accion=editar&id_veneno=${veneno.getId_veneno()}">Editar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Especie:</strong></td> <td>${veneno.getEspecie().getGenero_especie()} </td></tr>
                <tr><td> <strong>Cantidad:</strong> <td>${veneno.getCantidad()} Miligramos </td></tr>
                <c:choose>
                    <c:when test="${veneno.isRestriccion()}">
                        <tr><td> <strong>¿Es restringido?:</strong> <td>Si</td></tr>
                        <tr><td> <strong>Cantidad Máxima:</strong> <td>${veneno.getCantidad_maxima()} Miligramos</td></tr>
                    </c:when>
                    <c:otherwise>
                        <tr><td> <strong>¿Es restringido?:</strong> <td>No</td></tr>
                    </c:otherwise>
                </c:choose>
              </table>
              <br>
              <div class="col-md-12">
                  <div class="widget widget-table">
                    <div class="widget-header">
                      <h3><i class="fa fa-map-marker"></i> Lotes Asociados</h3>
                    </div>
                    <div class="widget-content">
                      <table id="datatable-column-filter-ubicaciones-formulario" class="table table-sorting table-striped table-hover datatable">
                        <thead>
                          <tr>
                            <th>Lotes</th>
                            <th>Cantidad Total</th>
                          </tr>
                        </thead>
                        <tbody>
                          <c:forEach items="${lotes}" var="lote">
                            <tr>
                              <td><a href="/SIGIPRO/Serpentario/Lote?accion=ver&id_lote=${lote.getId_lote()}">Lote ${lote.getNumero_lote()}</a></td>
                              <td>${lote.getCantidad_actual()} gramos</td>
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
