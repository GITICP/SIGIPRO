<%-- 
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
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
              <a href="/SIGIPRO/Serpentario/Especie?">Especies de Serpiente</a>
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
              <h3><i class="fa fa-bug"></i> Especies de Serpiente </h3>

              <c:set var="contienePermiso" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 300}">
                  <c:set var="contienePermiso" value="true" />
                </c:if>
              </c:forEach>
              <c:if test="${contienePermiso}">
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Serpentario/Especie?accion=agregar">Agregar Especie</a>
                </div>
              </c:if>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table id="datatable-column-filter-ubicaciones" class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>G�nero</th>
                    <th>Especie</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaEspecies}" var="especie">

                    <tr id ="${especie.getId_especie()}">
                      <td>
                        <a href="/SIGIPRO/Serpentario/Especie?accion=ver&id_especie=${especie.getId_especie()}">
                          <div style="height:100%;width:100%">
                            ${especie.getGenero()}
                          </div>
                        </a>
                      </td>
                      <td>${especie.getEspecie()}</td>
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
