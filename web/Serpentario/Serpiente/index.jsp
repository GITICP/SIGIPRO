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
              <a href="/SIGIPRO/Serpentario/Serpiente?">Serpientes</a>
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
              <h3><i class="fa fa-bug"></i> Serpientes </h3>

              <c:set var="contienePermiso" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 310}">
                  <c:set var="contienePermiso" value="true" />
                </c:if>
              </c:forEach>
              <c:if test="${contienePermiso}">
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Serpentario/Serpiente?accion=agregar">Agregar Serpiente</a>
                </div>
              </c:if>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>N�mero de Ingreso</th>
                    <th>Especie</th>
                    <th>Fecha de Ingreso</th>
                    <th>Localidad de Origen</th>
                    <th>Colecci�n Viva</th>
                    <th>Estado</th>

                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaSerpientes}" var="serpiente">

                    <tr id ="${serpiente.getId_serpiente()}">
                      <td>
                        <a href="/SIGIPRO/Serpentario/Serpiente?accion=ver&id_serpiente=${serpiente.getId_serpiente()}">
                          <div style="height:100%;width:100%">
                            ${serpiente.getNumero_serpiente()}
                          </div>
                        </a>
                      </td>
                      <td>${serpiente.getEspecie().getGenero_especie()}</td>
                      <td>${serpiente.getFecha_ingresoAsString()}</td>
                      <td>${serpiente.getLocalidad_origen()}</td>
                      <c:choose>
                          <c:when test="${serpiente.isColeccionviva()}">
                              <td>Si</td>
                          </c:when>
                          <c:otherwise>
                              <td>No</td>
                          </c:otherwise>
                      </c:choose>
                      <c:choose>
                          <c:when test="${serpiente.isEstado()}">
                              <td>Vivo</td>
                          </c:when>
                          <c:otherwise>
                              <td>Muerto</td>
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

