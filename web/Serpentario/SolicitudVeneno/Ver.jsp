<%-- 
    Document   : Ver
    Created on : Apr 2, 2015, 1:27:19 AM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Serpentario" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Serpentario</li>
            <li> 
              <a href="/SIGIPRO/Serpentario/SolicitudVeneno?">Solicitudes de Veneno</a>
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
                <c:set var="contienePermisoEditar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 351}">
                    <c:set var="contienePermisoEditar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditar}">
                    <c:if test="${solicitud.getEstado() == 'Solicitado'}">
                        <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Serpentario/SolicitudVeneno?accion=editar&id_solicitud=${solicitud.getId_solicitud()}">Editar</a>
                    </c:if>                
                </c:if>
                
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Usuario Solicitante:</strong></td> <td>${solicitud.getUsuario().getNombreCompleto()} </td></tr>
                <tr><td> <strong>Especie</strong> <td>${solicitud.getEspecie().getGenero_especie()}</td></tr>
                <tr><td> <strong>Cantidad:</strong> <td>${solicitud.getCantidad()}</td></tr>
                <tr><td> <strong>Fecha Solicitud:</strong> <td>${solicitud.getFecha_solicitudAsString()} </td></tr>
                <tr><td> <strong>Estado:</strong> <td>${solicitud.getEstado()} </td></tr>
                <tr><td> <strong>Proyecto:</strong> <td>${solicitud.getProyecto()} </td></tr>
                <br>
                <c:if test="${solicitud.getEstado().equals('Entregado')}">
                    <tr><td> <strong>Fecha Entrega:</strong> <td>${entrega.getFecha_entregaAsString()} </td></tr>
                    <tr><td> <strong>Usuario que Entrega:</strong></td> <td>${entrega.getUsuario_entrega().getNombreCompleto()} </td></tr>
                    <tr><td> <strong>Usuario que Recibe:</strong></td> <td>${entrega.getUsuario_recibo().getNombreCompleto()} </td></tr>
                    <tr><td> <strong>Cantidad Entregada:</strong></td> <td>${entrega.getCantidad_entregada()} </td></tr>
                </c:if>
                <tr><td> <strong>Observaciones:</strong></td> <td>${solicitud.getObservaciones()} </td></tr>
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

