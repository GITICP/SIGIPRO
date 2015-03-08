<%-- 
    Document   : Ver
    Created on : Jan 11, 2015, 11:57:19 AM
    Author     : Walter
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Activos Fijos" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Activos Fijos</li>
            <li> 
              <a href="/SIGIPRO/ActivosFijos/Activos?">Activos Fijos</a>
            </li>
            <li class="active"> ${activofijo.getNombreFormato()} </li>
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
              <h3><i class="fa fa-barcode"></i> ${activofijo.getNombreFormato()} </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEliminar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 33}">
                    <c:set var="contienePermisoEliminar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEliminar}">
                  <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar el activo fijo" data-href="/SIGIPRO/ActivosFijos/Activos?accion=eliminar&id_activo_fijo=${activofijo.getId_activo_fijo()}">Eliminar</a>
                </c:if>

                <c:set var="contienePermisoEditar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 32}">
                    <c:set var="contienePermisoEditar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/ActivosFijos/Activos?accion=editar&id_activo_fijo=${activofijo.getId_activo_fijo()}">Editar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
                <table>
                    <tr><td> <strong>Placa del Activo:</strong></td> <td>${activofijo.getPlaca()} </td></tr>
                    <tr><td> <strong>Equipo:</strong> <td>${activofijo.getEquipo()} </td></tr>
                    <tr><td> <strong>Marca:</strong> <td>${activofijo.getMarca()} </td></tr>
                    <tr><td> <strong>Fecha de Movimiento:</strong> <td>${activofijo.getFecha_movimiento()} </td></tr>
                    <tr><td> <strong>Sección:</strong> <td>${activofijo.getNombre_seccion()} </td></tr>
                    <tr><td> <strong>Ubicación:</strong> <td>${activofijo.getNombre_ubicacion()} </td></tr>
                    <tr><td> <strong>Fecha de Registro:</strong> <td>${activofijo.getFecha_registro()} </td></tr>
                    <tr><td> <strong>Estado:</strong> <td>${activofijo.getEstado()} </td></tr>
                    <tr><td> <strong>Responsable:</strong> <td>${activofijo.getResponsable()} </td></tr>
                    <tr><td> <strong>Número de Serie:</strong> <td>${activofijo.getSerie()} </td></tr>
                </table>
                <br>
            </div>
          </div>
          <!-- END WIDGET TICKET TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>

  </jsp:attribute>

</t:plantilla_general>
