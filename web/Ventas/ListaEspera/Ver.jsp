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

    <form id="form-eliminar-lista" method="post" action="ListaEspera">
        <input name="accion" value="Eliminar" hidden> 
        <input name="id_lista" value="${lista.getId_lista()}" hidden>
    </form>
    
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/ListaEspera?">Listas de Espera</a>
            </li>
            <li class="active">Lista ${lista.getId_lista()}</li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-file-text-o"></i> Lista ${lista.getId_lista()}  </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditarYBorrar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 702 || permiso == 701}">
                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditarYBorrar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ventas/ListaEspera?accion=editar&id_lista=${lista.getId_lista()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar este lista" data-form-id="form-eliminar-lista">Eliminar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>ID:  </strong></td> <center> <td> ${lista.getId_lista()} </td> </center> </tr>
                <tr><td> <strong>Cliente:  </strong>  </td> <center> <td> ${lista.getCliente().getNombre()}   </td> </center> </tr>
                <tr><td> <strong>Fecha de Ingreso: </strong>  </td> <center> <td> ${lista.getFecha_ingreso_S()}   </td> </center> </tr>
                <tr><td> <strong>Prioridad: </strong>  </td> <center> <td> ${lista.getPrioridad()}   </td> </center> </tr>
              </table>
              <br>
              
            </div>
          <!-- END WIDGET TICKET TABLE -->
          <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> Productos asociados</h3>
                  </div>
                  <div class="widget-content">
                    <table id="datatable-column-filter-acciones" class="table table-sorting table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>ID</th>
                          <th>Producto</th>
                          <th>Cantidad</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${productos_lista}" var="producto">
                          <tr id="${producto.getProducto().getId_producto()}">
                            <td>${producto.getProducto().getId_producto()}</td>
                            <td>${producto.getProducto().getNombre()}</td>
                            <td>${producto.getCantidad()}</td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
          <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> Historiales asociados</h3>
                  </div>
                  <div class="widget-content">
                    <table id="datatable-column-filter-acciones" class="table table-sorting table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>ID</th>
                          <th>Historial</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${historiales_lista}" var="historial">
                          <tr id="${historial.getId_historial()}">
                            <td>${historial.getId_historial()}</td>
                            <td>${historial.getHistorial()}</td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
          <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> Observaciones asociadas</h3>
                  </div>
                  <div class="widget-content">
                    <table id="datatable-column-filter-acciones" class="table table-sorting table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>ID</th>
                          <th>Observación</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${observaciones_lista}" var="observacion">
                          <tr id="${observacion.getId_observacion()}">
                            <td>${observacion.getId_observacion()}</td>
                            <td>${observacion.getObservacion()}</td>
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
    </div>

  </jsp:attribute>

</t:plantilla_general>
