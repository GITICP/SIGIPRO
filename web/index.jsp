<%-- 
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
    Author     : Boga
--%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Inicio" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-xs-2"></div>
        <div class="col-xs-8">${mensaje}</div>
        <div class="col-xs-2"></div>
      </div>
      <div class="row">
        <div class="col-xs-1"></div>
        <div class="col-xs-10">
          <h1>Inicio</h1>
        </div>
        <div class="col-xs-1"></div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- Acá va contenido importante, notificaciones, etc. -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>
    <!-- /content-wrapper -->

  </jsp:attribute>

</t:plantilla_general>