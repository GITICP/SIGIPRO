<%-- 
    Document   : Ver
    Created on : Jan 11, 2015, 11:57:19 AM
    Author     : Walter
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
              <a href="/SIGIPRO/Bodegas/CatalogoInterno?">Catálogo Interno</a>
            </li>
            <li class="active"> ${producto.getCodigo_icp()} </li>
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
              <h3><i class="fa fa-barcode"></i> ${producto.getNombre()} </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEliminar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 12}">
                    <c:set var="contienePermisoEliminar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEliminar}">
                  <a class="btn btn-danger btn-sm"  style="margin-left:5px;margin-right:5px;color:#fff;"  href="/SIGIPRO/Bodegas/CatalogoInterno?accion=eliminar&id_producto=${producto.getId_producto()}">Eliminar</a>
                </c:if>

                <c:set var="contienePermisoEditar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 12}">
                    <c:set var="contienePermisoEditar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditar}">
                  <a class="btn btn-warning btn-sm" style="margin-left:5px;margin-right:5px;color:#fff;" href="/SIGIPRO/Bodegas/CatalogoInterno?accion=editar&id_producto=${producto.getId_producto()}">Editar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Nombre del Producto:</strong></td> <td>${producto.getNombre()} </td></tr>
                <tr><td> <strong>Código ICP:</strong> <td>${producto.getCodigo_icp()} </td></tr>
                <tr><td> <strong>Stock Mínimo:</strong> <td>${producto.getStock_minimo()} </td></tr>
                <tr><td> <strong>Stock Máximo:</strong> <td>${producto.getStock_maximo()} </td></tr>
                <tr><td> <strong>Ubicación:</strong> <td>${producto.getUbicacion()} </td></tr>
                <tr><td> <strong>Presentación:</strong> <td>${producto.getPresentacion()} </td></tr>
                <tr><td> <strong>Descripción:</strong> <td>${producto.getDescripcion()} </td></tr>
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
