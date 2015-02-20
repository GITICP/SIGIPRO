<%-- 
    Document   : Ver
    Created on : Jan 27, 2015, 2:08:46 PM
    Author     : Amed
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
              <a href="/SIGIPRO/Bodegas/CatalogoExterno?">Catálogo Externo</a>
            </li>
            <li class="active"> ${producto.getProducto()} (${producto.getCodigo_Externo()})</li>
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
              <h3><i class="fa fa-barcode"></i> ${producto.getProducto()} </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEliminar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 20}">
                    <c:set var="contienePermisoEliminar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEliminar}">
                  <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="el Producto del Catálogo Externo" data-href="/SIGIPRO/Bodegas/CatalogoExterno?accion=eliminar&id_producto=${producto.getId_producto_ext()}">Eliminar</a>
                </c:if>

                <c:set var="contienePermisoEditar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 19}">
                    <c:set var="contienePermisoEditar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Bodegas/CatalogoExterno?accion=editar&id_producto=${producto.getId_producto_ext()}">Editar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Nombre del Producto:</strong></td> <td>${producto.getProducto()} </td></tr>
                <tr><td> <strong>Código Externo:</strong> <td>${producto.getCodigo_Externo()} </td></tr>
                <tr><td> <strong>Marca:</strong> <td>${producto.getMarca()} </td></tr>
                <tr><td> <strong>Proveedor:</strong> <td>${producto.getNombreProveedor()} </td></tr>
              </table>
              <br>
              <div class="widget widget-table">
                <div class="widget-header">
                  <h3><i class="fa fa-check"></i> Productos del Catálogo Interno Asociados</h3>
                </div>
                <div class="widget-content">
                  <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable">
                    <thead>
                      <tr>
                        <th>Nombre y Codigo del Producto</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${productos_internos}" var="interno">
                        <tr id="${interno.getId_producto()}">
                          <td>${interno.getNombre()} (${interno.getCodigo_icp()})</td>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
          <!-- Esta parte es la de los interno del catalogo externo -->

          <!-- END WIDGET TICKET TABLE -->
        </div>
        <!-- /main-content -->
      </div>

      <!-- /main -->
    </div>

  </jsp:attribute>

</t:plantilla_general>

