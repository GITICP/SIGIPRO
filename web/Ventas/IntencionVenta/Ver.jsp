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

    <form id="form-eliminar-intencion" method="post" action="IntencionVenta">
        <input name="accion" value="Eliminar" hidden> 
        <input name="id_intencion" value="${intencion.getId_intencion()}" hidden>
    </form>
    
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/IntencionVenta?">Solicitudes o Intenciones de Venta</a>
            </li>
            <li class="active">Solicitud o Intención de Venta ${intencion.getId_intencion()}</li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-list-alt"></i> Solicitud o Intención de Venta ${intencion.getId_intencion()}  </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditarYBorrar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 702 || permiso == 701}">
                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditarYBorrar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ventas/IntencionVenta?accion=editar&id_intencion=${intencion.getId_intencion()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar esta intencion" data-form-id="form-eliminar-intencion">Eliminar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>ID: </strong></td> <center> <td> ${intencion.getId_intencion()} </td> </center> </tr>
                <tr><td> <strong>Cliente: </strong></td><center> 
                        <c:choose>
                          <c:when test= "${intencion.getCliente() != null}">
                              <td>${intencion.getCliente().getNombre()}</td>
                          </c:when>
                          <c:otherwise>
                              <td>${intencion.getNombre_cliente()}</td>
                          </c:otherwise>
                        </c:choose>
                    </center> </tr>
                <c:choose>
                  <c:when test= "${intencion.getCliente() == null}">
                      <tr><td> <strong>Teléfono: </strong>  </td> <center> <td> ${intencion.getTelefono()}   </td> </center> </tr>
                      <tr><td> <strong>Correo electrónico: </strong>  </td> <center> <td> ${intencion.getCorreo()}   </td> </center> </tr>
                  </c:when>
                </c:choose>    
                <tr><td> <strong>Estado: </strong>  </td> <center> <td> ${intencion.getEstado()}   </td> </center> </tr>
                <tr><td> <strong>Observaciones: </strong>  </td> <center> <td> ${intencion.getObservaciones()}   </td> </center> </tr>
              </table>
              <br>
              
            </div>     
          <!-- END WIDGET TICKET TABLE -->
          <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> Productos Asociados</h3>
                    <div class="btn-group widget-header-toolbar">
                      
                    </div>
                  </div>
                  <div class="widget-content">
                    <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>Producto</th>
                          <th>Cantidad</th>
                          <th>Posible Fecha de Despacho</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${productos_intencion}" var="producto">
                          <tr id="${producto.getProducto().getId_producto()}">
                            <td>${producto.getProducto().getNombre()}</td>
                            <td>${producto.getCantidad()}</td>
                            <td>${producto.getFecha_S()}</td>
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
