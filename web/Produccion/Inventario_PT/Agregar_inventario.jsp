<%-- 
    Document   : Agregar
    Created on : Feb 19, 2015, 7:59:26 PM
    Author     : Amed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Inventario" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">



    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Produccion</li>
            <li> 
              <a href="/SIGIPRO/Produccion/Inventario_PT?">Inventario de Producto Terminado</a>
            </li>
            <li class="active"> Nueva Entrada de Inventario </li>

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
              <h3><i class="fa fa-list-alt"></i> Agregar  Entrada de Inventario </h3>
            </div>
            ${mensaje}
            <div class="widget-content">

              <jsp:include page="Formulario_inventario.jsp"></jsp:include>

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
      <script src="/SIGIPRO/recursos/js/sigipro/Produccion/Inventario/Formulario.js"></script>
    </jsp:attribute>
</t:plantilla_general>

