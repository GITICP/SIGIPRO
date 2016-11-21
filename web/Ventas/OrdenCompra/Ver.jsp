<%-- 
    Document   : Ver
    Created on : Mar 28, 2015, 11:26:24 PM
    Author     : jespinozac95
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ventas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <form id="form-eliminar-orden" method="post" action="OrdenCompra">
        <input name="accion" value="Eliminar" hidden> 
        <input name="id_orden" value="${orden.getId_orden()}" hidden>
    </form>
    
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/OrdenCompra?">Órdenes de Compra</a>
            </li>
            <li class="active">Orden de Compra ${orden.getId_orden()}</li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-list-alt"></i> Orden de Compra ${orden.getId_orden()}  </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditarYBorrar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 702 || permiso == 701}">
                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditarYBorrar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ventas/OrdenCompra?accion=editar&id_orden=${orden.getId_orden()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar esta orden" data-form-id="form-eliminar-orden">Eliminar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Consecutivo: </strong></td> <center> <td> ${orden.getId_orden()} </td> </center> </tr>
                <c:choose>
                    <c:when test= "${orden.getCotizacion() == null}">
                        <tr><td> <strong>ID Intención: </strong>  </td> <center> 
                            <td> 
                                <a href="/SIGIPRO/Ventas/IntencionVenta?accion=ver&id_intencion=${orden.getIntencion().getId_intencion()}">
                                <div style="height:100%;width:100%">
                                  ${orden.getIntencion().getId_intencion()}
                                </div>
                                </a>
                              </td> 
                        </center> </tr>
                    </c:when>
                    <c:otherwise>
                        <tr><td> <strong>ID Cotización: </strong>  </td> <center> 
                            <td>
                                <a href="/SIGIPRO/Ventas/Cotizacion?accion=ver&id_cotizacion=${orden.getCotizacion().getId_cotizacion()}">
                                <div style="height:100%;width:100%">
                                    ${orden.getCotizacion().getIdentificador()}
                                </div>
                                </a>
                              </td>
                        </center> </tr>
                    </c:otherwise>
                </c:choose>
                <tr><td> <strong>Información de Rotulación: </strong>  </td> <center> <td> ${orden.getRotulacion()}   </td> </center> </tr>
                <tr><td> <strong>Estado: </strong>  </td> <center> <td> ${orden.getEstado()}   </td> </center> </tr>
                <tr><td> <strong>Documento: </strong> 
                    <td>
                        <c:choose>
                            <c:when test="${(orden.getDocumento() == null)||(orden.getDocumento() == '')}">
                                Sin documento asociado.
                            </c:when>
                            <c:otherwise>
                                <a href="/SIGIPRO/Ventas/OrdenCompra?accion=archivo&id_orden=${orden.getId_orden()}">Descargar Documento</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
              </table>
              <br>
              
            </div>     
          <!-- END WIDGET TICKET TABLE -->
          <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> Productos de la Solicitud / Intención de Venta Asociada</h3>
                    <div class="btn-group widget-header-toolbar">
                      
                    </div>
                  </div>
                  <div class="widget-content">
                    <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>Nombre del Producto</th>
                          <th>Cantidad</th>
                          <th>Lote</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${productos_orden}" var="producto">
                          <tr id="${producto.getProducto().getId_producto()}">
                            <td>${producto.getProducto().getNombre()}</td>
                            <td>${producto.getCantidad()}</td>
                            <td>${producto.getProducto().getLote()}</td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
  </jsp:attribute>
      

</t:plantilla_general>
