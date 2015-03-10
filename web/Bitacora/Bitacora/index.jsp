<%-- 
    Document   : index
    Created on : Jan 27, 2015, 2:08:13 PM
    Author     : Conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Bitacora" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bitácora</li>
            <li> 
              <a href="/SIGIPRO/Bitacora/Bitacora?">Bitácora</a>
            </li>
          </ul>
        </div>
        <div class="col-md-8 ">
          <div class="top-content">

          </div>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-barcode"></i> Bitácora </h3>

              <c:set var="contienePermiso" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 21}">
                  <c:set var="contienePermiso" value="true" />
                </c:if>
              </c:forEach>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table id="example" class="table table-sorting table-responsive table-striped table-hover datatable tablaSigipro sigipro-bitacora-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Fecha Acción</th>
                    <th>Usuario</th>
                    <th>IP</th>
                    <th>Tabla</th>
                    <th>Acción</th>
                    <th>ID Objeto</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaBitacoras}" var="bitacora">

                    <tr id ="${bitacora.getId_bitacora()}">
                      <td>
                        <a href="/SIGIPRO/Bitacora/Bitacora?accion=ver&id_bitacora=${bitacora.getId_bitacora()}">
                          <div style="height:100%;width:100%">
                            ${bitacora.getFecha_accion_parse()}
                          </div>
                        </a>
                      </td>
                      <td>${bitacora.getNombre_usuario()}</td>
                      <td>${bitacora.getIp()}</td>
                      <td>${bitacora.getTabla()}</td>
                      <td>${bitacora.getAccion()}</td>
                      <td>${bitacora.getId_objeto()}</td>
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
