<%-- 
    Document   : Ver
    Created on : Jan 11, 2015, 11:57:19 AM
    Author     : Walter
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
              <a href="/SIGIPRO/Bodegas/CatalogoInterno?">Catálogo Interno</a>
            </li>
            <li class="active"> ${producto.getCodigo_icp()} </li>
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
              <h3><i class="fa fa-barcode"></i> ${producto.getNombre()} </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 12}">
                    <c:set var="contienePermisoEditar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Bodegas/CatalogoInterno?accion=editar&id_producto=${producto.getId_producto()}">Editar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Nombre del Producto:</strong></td> <td>${producto.getNombre()} </td></tr>
                <tr><td> <strong>Código ICP:</strong> <td>${producto.getCodigo_icp()} </td></tr>
                <tr><td> <strong>Stock Mínimo:</strong> <td>${producto.getStock_minimo()} </td></tr>
                <tr><td> <strong>Stock Máximo:</strong> <td>${producto.getStock_maximo()} </td></tr>
                <tr><td> <strong>Presentación:</strong> <td>${producto.getPresentacion()} </td></tr>
                <tr><td> <strong>Descripción:</strong> <td>${producto.getDescripcion()} </td></tr>
              </table>
              <br>
              <c:if test="${producto.getReactivo() != null}" >
                <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-flask"></i> Información Reactivo</h3>
                  </div>
                  <div class="widget-content">
                    <table>
                      <tr><td> <strong>Número Cas:</strong></td> <td>${producto.getReactivo().getNumero_cas()} </td></tr>
                      <tr><td> <strong>Fórmula Química</strong> <td>${producto.getReactivo().getFormula_quimica()} </td></tr>
                      <tr><td> <strong>Familia</strong> <td>${producto.getReactivo().getFamilia()} </td></tr>
                      <tr><td> <strong>Cantidad Botella Bodega:</strong> <td>${producto.getReactivo().getCantidad_botella_bodega()} </td></tr>
                      <tr><td> <strong>Cantidad Botella Laboratorio:</strong> <td>${producto.getReactivo().getCantidad_botella_lab()} </td></tr>
                      <tr><td> <strong>Volumen Bodega:</strong> <td>${producto.getReactivo().getVolumen_bodega()} </td></tr>
                      <tr><td> <strong>Volumen Laboratorio:</strong> <td>${producto.getReactivo().getVolumen_lab()} </td></tr>
                    </table>
                  </div>
                </div>
              </c:if>
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
