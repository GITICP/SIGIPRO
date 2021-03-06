<%-- 
    Document   : Agregar
    Created on : Dec 14, 2014, 1:43:27 PM
    Author     : Boga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="SubBodegas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Bodegas</li>
            <li> 
              <a href="/SIGIPRO/Bodegas/SubBodegas?">Sub Bodegas</a>
            </li>
            <li class="active"> Registrar Ingreso de Producto a Sub Bodega </li>
          </ul>
        </div>
      </div>

      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-th-large"></i> Registrar Ingreso de Producto a Sub Bodega </h3>
            </div>
            ${mensaje}
            <div class="widget-content">

              <jsp:include page="FormularioIngreso.jsp"></jsp:include>

              </div>
            </div>
            <!-- END WIDGET TICKET TABLE -->
          </div>
          <!-- /main-content -->
        </div>
        <!-- /main -->
      </div>

  </jsp:attribute>
    
  <jsp:attribute name="scripts">
    <script src="/SIGIPRO/recursos/js/sigipro/sub-bodegas.js"></script>
  </jsp:attribute>

</t:plantilla_general>