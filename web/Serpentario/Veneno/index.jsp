<%-- 
    Document   : index
    Created on : Mar 16, 2015, 6:11:43 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
              <a href="/SIGIPRO/Serpentario/Veneno?">Cat�logo de Venenos</a>
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
                <h3><i class="fa fa-flask"></i> Cat�logo de Venenos </h3>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Especie</th>
                    <th>Cantidad Total (G)</th>
                    <th>Restricci�n</th>
                    <th>Cantidad M�xima (mg)</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaVenenos}" var="veneno">

                    <tr id ="${veneno.getId_veneno()}">
                      <td>
                        <c:set var="contienePermisoVer" value="false" />
                        <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                          <c:if test="${permiso == 1 || permiso == 340}">
                            <c:set var="contienePermisoVer" value="true" />
                          </c:if>
                        </c:forEach>
                          <c:choose>
                              <c:when test="${contienePermisoVer}">
                                <a href="/SIGIPRO/Serpentario/Veneno?accion=ver&id_veneno=${veneno.getId_veneno()}">
                                <div style="height:100%;width:100%">
                                    ${veneno.getEspecie().getGenero_especie()}
                                </div>
                                </a>  
                              </c:when>
                              <c:otherwise>
                                <a href="/SIGIPRO/Serpentario/Veneno?accion=verexterno&id_veneno=${veneno.getId_veneno()}">
                                <div style="height:100%;width:100%">
                                    ${veneno.getEspecie().getGenero_especie()}
                                </div>
                                </a>  
                              </c:otherwise>
                          </c:choose>
                      </td>
                      <td>${veneno.getCantidad()}</td>
                      <c:choose>
                          <c:when test="${veneno.isRestriccion()}">
                              <td>Si</td>
                              <td>${veneno.getCantidad_maxima()}</td>
                          </c:when>
                          <c:otherwise>
                              <td>No</td>
                              <td>0.0</td>
                          </c:otherwise>
                      </c:choose>
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

    </jsp:attribute>

  </t:plantilla_general>
