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
              <a href="/SIGIPRO/Bodegas/Solicitudes?">Solicitudes</a>
            </li>
            <li class="active"> ${solicitud.getId_solicitud()} </li>
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
              <h3><i class="fa fa-barcode"></i> Solicitud  ${solicitud.getId_solicitud()} </h3>
              <div class="btn-group widget-header-toolbar">
                <c:if test="${solicitud.getEstado() == 'Pendiente'}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Bodegas/Solicitudes?accion=editar&id_solicitud=${solicitud.getId_solicitud()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar la Solicitud" data-href="/SIGIPRO/Bodegas/Solicitudes?accion=eliminar&id_solicitud=${solicitud.getId_solicitud()}">Eliminar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Usuario Solicitante:</strong></td> <td>${solicitud.getUsuario().getNombreCompleto()} </td></tr>
                <tr><td> <strong>Producto:</strong> <td>${solicitud.getProducto().getNombre()} (${solicitud.producto.getCodigo_icp()}) </td></tr>
                <tr><td> <strong>Cantidad:</strong> <td>${solicitud.getCantidad()} </td></tr>
                <tr><td> <strong>Fecha Solicitud:</strong> <td>${solicitud.getFecha_solicitud()} </td></tr>
                <tr><td> <strong>Estado:</strong> <td>${solicitud.getEstado()} </td></tr>
                <tr><td> <strong>Fecha Entrega:</strong> <td>${solicitud.getFecha_entrega()} </td></tr>
                <tr><td> <strong>Usuario que Recibe:</strong></td> <td>${solicitud.getUsuarioReceptor().getNombreCompleto()} </td></tr>
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

