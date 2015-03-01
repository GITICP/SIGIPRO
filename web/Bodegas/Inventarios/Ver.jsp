<%-- 
    Document   : Ver
    Created on : Jan 11, 2015, 11:57:19 AM
    Author     : Conejo
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
              <a href="/SIGIPRO/Bodegas/Inventarios?">Inventarios</a>
            </li>
            <li class="active"> ${inventario.getProducto().getNombre()} </li>
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
              <h3><i class="fa fa-barcode"></i> ${inventario.getProducto().getNombre()} </h3>
              <div class="btn-group widget-header-toolbar">

              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
                <table>
                    <tr><td> <strong>Nombre del Producto:</strong></td> <td>${inventario.getProducto().getNombre()} </td></tr>
                    <tr><td> <strong>Sección:</strong> <td>${inventario.getSeccion().getNombre_seccion()} </td></tr>
                    <tr><td> <strong>Stock Actual:</strong> <td>${inventario.getStock_actual()} </td></tr>
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
