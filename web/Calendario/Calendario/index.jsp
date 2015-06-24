<%-- 
    Document   : Index
    Created on : Jun 24, 2015, 10:49:21 AM
    Author     : Amed
--%>

<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Calendario" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
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
              <h3><i class="fa fa-list-alt"></i>Calendario</h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="">Agregar Evento</a>
                </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <div id='calendar'></div>
              
            </div>
          </div>
          <!-- END COLUMN FILTER DATA TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
      </div>

    </jsp:attribute>
    <jsp:attribute name="scripts">
      <link rel='stylesheet' href="/SIGIPRO/recursos/css/fullcalendar.css" />
      <script src="/SIGIPRO/recursos/js/fullcalendar/moment.min.js"></script>
      <script src="/SIGIPRO/recursos/js/fullcalendar/fullcalendar.js"></script>
      <script src="/SIGIPRO/recursos/js/sigipro/calendario.js"></script>
    </jsp:attribute>
  </t:plantilla_general>
