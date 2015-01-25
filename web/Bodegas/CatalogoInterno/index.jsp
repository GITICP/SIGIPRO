<%-- 
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
    Author     : Walter
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Bodegas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bodegas</li>
            <li> 
              <a href="/SIGIPRO/Bodegas/CatalogoInterno?">Catálogo Interno</a>
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
              <h3><i class="fa fa-barcode"></i> Catálogo Interno </h3>

              <c:set var="contienePermiso" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 11}">
                  <c:set var="contienePermiso" value="true" />
                </c:if>
              </c:forEach>
              <c:if test="${contienePermiso}">
                <div class="btn-group widget-header-toolbar">
                  <a class="btn btn-primary btn-sm" style="margin-left:5px;margin-right:5px;color: white;" href="/SIGIPRO/Bodegas/CatalogoInterno?accion=agregar">Agregar Producto</a>
                </div>
              </c:if>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Código ICP</th>
                    <th>Nombre</th>
                    <th>Stock Mínimo</th>
                    <th>Stock Máximo</th>
                    <th>Ubicación</th>
                    <th>Presentación</th>
                    <th>Descripción</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaProductos}" var="producto">

                    <tr id ="${producto.getId_producto()}">
                      <td>
                        <a href="/SIGIPRO/Bodegas/CatalogoInterno?accion=ver&id_producto=${producto.getId_producto()}">
                          <div style="height:100%;width:100%">
                            ${producto.getCodigo_icp()}
                          </div>
                        </a>
                      </td>
                      <td>${producto.getNombre()}</td>
                      <td>${producto.getStock_minimo()}</td>
                      <td>${producto.getStock_maximo()}</td>
                      <td>${producto.getUbicacion()}</td>
                      <td>${producto.getPresentacion()}</td>
                      <td>${producto.getDescripcion()}</td>
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