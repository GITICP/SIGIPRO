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
                  <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/CatalogoInterno?accion=agregar">Agregar Producto</a>
                </div>
              </c:if>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Código ICP</th>
                    <th>Nombre</th>
                    <th>Presentación</th>
                    <th>Ingresa en Cuarentena</th>
                    <th>Es perecedero</th>
                    <th>Es reactivo</th>
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
                      <td>${producto.getPresentacion()}</td>
                      <td>${producto.getCuarentena()}</td>
                      <td>${producto.getPerecedero()}</td>
                      <td>${producto.isReactivo()}</td>
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
