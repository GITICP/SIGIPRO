<%-- 
    Document   : Agregar
    Created on : Dec 14, 2014, 1:43:27 PM
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Bioterio" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12">
          <ul class="breadcrumb">
            <li>Bioterio</li>
            <li> 
              <a href="/SIGIPRO/Conejera/AnalisisParasitologico?especie=${especie}">Análisis Parasitológico</a>
            </li>
            <li class="active"> Agregar Análisis Parasitológico</li>
          </ul>
        </div>
      </div>

      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-barcode"></i> Agregar Nuevo Análisis Parasitológico</h3>
            </div>
            ${mensaje}
            <div class="widget-content">

              <jsp:include page="Formulario.jsp"></jsp:include>

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
