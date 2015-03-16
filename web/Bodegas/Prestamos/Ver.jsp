<%-- 
    Document   : Ver
    Created on : Feb 19, 2015, 6:29:37 PM
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
              <a href="/SIGIPRO/Bodegas/Prestamos?">Préstamos</a>
            </li>
            <li class="active"> ${prestamo.getSolicitud().getId_solicitud()} </li>
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
              <h3><i class="fa fa-barcode"></i> Préstamo  ${prestamo.getSolicitud().getId_solicitud()} </h3>
              <div class="btn-group widget-header-toolbar">
                <c:if test="${prestamo.getSolicitud().getEstado() == 'Pendiente' || prestamo.getSolicitud().getEstado() == 'Pendiente Prestamo'}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Bodegas/Prestamos?accion=editar&id_solicitud=${prestamo.getSolicitud().getId_solicitud()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar la Prestamo" data-href="/SIGIPRO/Bodegas/Prestamos?accion=eliminar&id_solicitud=${prestamo.getSolicitud().getId_solicitud()}">Eliminar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Usuario Solicitante:</strong></td> <td>${prestamo.getSolicitud().getUsuario().getNombreCompleto()} (${prestamo.getSolicitud().getUsuario().getNombreSeccion()})</td></tr>
                <tr><td> <strong>Producto:</strong> <td>${prestamo.getSolicitud().getInventario().getProducto().getNombre()} (${prestamo.getSolicitud().getInventario().getProducto().getCodigo_icp()}) </td></tr>
                <tr><td> <strong>Cantidad:</strong> <td>${prestamo.getSolicitud().getCantidad()} </td></tr>
                <tr><td> <strong>Fecha Solicitud:</strong> <td>${prestamo.getSolicitud().getFecha_solicitud()} </td></tr>
                <tr><td> <strong>Estado:</strong> <td>${prestamo.getSolicitud().getEstado()} </td></tr>
                <tr><td> <strong>Sección que Presta:</strong> <td>${prestamo.getSeccion().getNombre_seccion()} </td></tr>
                <tr><td> <strong>Usuario que aprobó el Préstamo:</strong></td> <td>${prestamo.getUsuario().getNombreCompleto()} </td></tr>
                <tr><td> <strong>Observaciones:</strong></td> <td>${prestamo.getSolicitud().getObservaciones()} </td></tr>
              </table>
              <br>
            </div>
            <!-- END WIDGET TICKET TABLE -->
          </div>
          <!-- /main-content -->
        </div>
        <!-- /main -->
      </div>

    </jsp:attribute>

  </t:plantilla_general>

