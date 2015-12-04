<%-- 
    Document   : Ver_reservacion
    Created on : Dec 2, 2015, 4:47:39 PM
    Author     : Amed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Reservaciones de Producto Terminado" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />
    <form id="form-eliminar" class="form-horizontal" autocomplete="off" method="post" action="Inventario_PT">
      <input hidden="true" id="id_eliminar" name="id_eliminar" value="">
      <input hidden="true" id="accion" name="accion" value="">
    </form>

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Producción</li>
            <li> 
              <a href="/SIGIPRO/Produccion/Inventario_PT?">Reservaciones de Producto Terminado</a>
            </li>
            <li class="active"> Reservaciones de Producto Terminado </li>
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
              <h3><i class="fa fa-list-alt"></i> Reservación Número: ${reservacion.getId_reservacion()}</h3>
              <div class="btn-group widget-header-toolbar">
                <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Inventario_PT?accion=editar_reservacion&id_reservacion=${reservacion.getId_reservacion()}">Editar</a>
                <a class="btn btn-danger btn-sm boton-accion" onclick="Eliminar(${reservacion.getId_reservacion()}, 'eliminar esta reservación', 'reservación')">Eliminar</a>                      
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="tabla-ver">
                <tr><td> <strong>Fecha Límite de la Reservación:</strong> <td>${reservacion.getHasta_S()} </td></tr>
                <tr><td> <strong>Observaciones: </strong> <td>${reservacion.getObservaciones()} </td></tr>
                <tr><td> <strong>Total:</strong> <td>${reservacion.getTotal()} </td></tr>   
              </table>
            </div>
          </div>
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-check"></i> Lotes de Producto y Cantidades de esta Reservacion</h3>
              <div class="btn-group widget-header-toolbar">
              </div>
            </div>
            <div class="widget-content">
              <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
                <thead>
                  <tr>
                    <th>Lote</th>
                    <th>Cantidad</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${reservaciones_inventarios}" var="inventario">
                    <tr id="${inventario.getId_inventario_pt()}">
                      <td>${inventario.getInventario().getLote()} (${inventario.getInventario().getProducto().getNombre()})</td>
                      <td>${inventario.getCantidad()}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <!-- /main-content -->
    </div>
    <!-- /main -->

</jsp:attribute>
<jsp:attribute name="scripts">
  <script src="/SIGIPRO/recursos/js/sigipro/Produccion/Inventario/Eliminar.js"></script>
</jsp:attribute>
</t:plantilla_general>
