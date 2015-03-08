<%-- 
    Document   : Ver
    Created on : Mar 08, 2015, 10:43:19 AM
    Author     : Boga
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
              <a href="/SIGIPRO/Bodegas/SubBodegas?">SubBodegas</a>
            </li>
            <li class="active"> ${sub_bodega.getNombre()} </li>
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
              <h3><i class="fa fa-th-large"></i> ${sub_bodega.getNombre()} </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 12}">
                    <c:set var="contienePermisoEditar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Bodegas/SubBodegas?accion=editar&id_sub_bodega=${sub_bodega.getId_sub_bodega()}">Editar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Nombre de la Sub-bodega:</strong></td> <td>${sub_bodega.getNombre()} </td></tr>
                <tr><td> <strong>Sección a la que pertenece:</strong> <td>${sub_bodega.getSeccion().getNombre_seccion()} </td></tr>
                <tr><td> <strong>Usuario encargado:</strong> <td>${sub_bodega.getUsuario().getNombre_completo()} </td></tr>
              </table>
              <br>
              <div class="row">
                <div class="col-md-6">
                  <div class="widget widget-table">
                    <div class="widget-header">
                      <h3><i class="fa fa-map-marker"></i> Ubicaciones ${producto.getNombre()}</h3>
                    </div>
                    <div class="widget-content">
                      <table id="datatable-column-filter-ubicaciones-formulario" class="table table-sorting table-striped table-hover datatable">
                        <thead>
                          <tr>
                            <th>Ubicación</th>
                          </tr>
                        </thead>
                        <tbody>
                          <c:forEach items="${ubicacionesProducto}" var="ubicacion">
                            <tr>
                              <td><a href="/SIGIPRO/Bodegas/UbicacionesBodega?accion=ver&id_ubicacion=${ubicacion.getId_ubicacion()}">${ubicacion.getNombre()}</a></td>
                            </tr>
                          </c:forEach>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="widget widget-table">
                    <div class="widget-header">
                      <h3><i class="fa fa-truck"></i> Productos Externos ${producto.getNombre()}</h3>
                    </div>
                    <div class="widget-content">
                      <table id="datatable-column-filter-productos-externos" class="table table-sorting table-striped table-hover datatable">
                        <thead>
                          <tr>
                            <th>Producto Externo</th>
                            <th>Código Producto</th>
                          </tr>
                        </thead>
                        <tbody>
                          <c:forEach items="${productosExternos}" var="producto">
                            <tr>
                              <td><a href="/SIGIPRO/Bodegas/CatalogoExterno?accion=ver&id_producto=${producto.getId_producto_ext()}">${producto.getProducto()}</a></td>
                              <td>(${producto.getCodigo_Externo()})</td>
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
