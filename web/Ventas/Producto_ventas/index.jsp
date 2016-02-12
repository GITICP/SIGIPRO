<%-- 
    Document   : index
    Created on : Mar 16, 2015, 6:11:43 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ventas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/Producto_ventas?">Catálogo de Productos de Venta</a>
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
                <h3><i class="fa fa-list-alt"></i> Catálogo de Productos de Venta </h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Ventas/Producto_ventas?accion=agregar">Agregar un Producto de Venta</a>
                </div>  
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Nombre</th>
                    <th>Descripcion</th>
                    <th>Cantidad en Stock</th>
                    <th>Precio Unitario</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaProductos}" var="producto">

                    <tr id ="${producto.getId_producto()}">
                      <td>
                        <a href="/SIGIPRO/Ventas/Producto_ventas?accion=ver&id_producto=${producto.getId_producto()}">
                        <div style="height:100%;width:100%">
                            ${producto.getNombre()}
                        </div>
                        </a>
                      </td>
                      <td>${producto.getDescripcion()}</td>
                      <td>${producto.getStock()}</td>
                      <td>${producto.getPrecio()}</td>
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
      </div>
    </jsp:attribute>

  </t:plantilla_general>
