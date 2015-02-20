
<%-- 
    Document   : index
    Created on : Jan 27, 2015, 2:08:13 PM
    Author     : Amed
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
              <a href="/SIGIPRO/Bodegas/Solicitudes?">Solicitudes</a>
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
              <h3><i class="fa fa-barcode"></i> Solicitudes de Bodega </h3>
                <div class="btn-group widget-header-toolbar">
                  <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/Solicitudes?accion=agregar">Crear Nueva Solicitud</a>
                </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>ID de Solicitud</th>
                    <th>Usuario Solicitante</th>
                    <th>Producto</th>
                    <th>Cantidad</th>
                    <th>Fecha de Solicitud</th>
                    <th>Estado</th>
                    <th>Fecha de Entrega</th>
                    <th>Usuario que Recibe</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaSolicitudes}" var="solicitud">

                    <tr id ="${solicitud.getId_solicitud()}">
                      <td>
                        <a href="/SIGIPRO/Bodegas/Solicitudes?accion=ver&id_solicitud=${solicitud.getId_solicitud()}">
                          <div style="height:100%;width:100%">
                            ${solicitud.getId_solicitud()}
                          </div>
                        </a>
                      </td>
                      <td>${solicitud.getUsuario().getNombreCompleto()}</td>
                      <td>${solicitud.getProducto().getNombre()} (${solicitud.producto.getCodigo_icp()})</td>
                      <td>${solicitud.getCantidad()}</td>
                      <td>${solicitud.getFecha_solicitud()}</td>
                      <td>${solicitud.getEstado()}</td>
                      <td>${solicitud.getFecha_entrega()}</td>
                      <td>${solicitud.getUsuarioReceptor().getNombreCompleto() }</td>
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
