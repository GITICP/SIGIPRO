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
        <div class="col-xs-1"></div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-header">
          <h2>INICIO</h2>
          <em>Recordatorios</em>
        </div>
        <div class="main-content">
          <div class="col-md-12">
            <!-- WIDGET REMINDER -->
            <div class="widget widget-hide-header widget-reminder">
              <div class="widget-header hide">
                <h3>Recordaorio</h3>
              </div>
              <div class="widget-content">
                <div class="today-reminder">
                  <h4 class="reminder-title">Prueba 1</h4>
                  <p class="reminder-time"><i class="fa fa-clock-o"></i> 9:00 AM</p>
                  <p class="reminder-place"> Bodega 2</p>
                  <em class="reminder-notes"> Arreglar las cosas que hay adentro</em>
                  <i class="fa fa-bell"></i>

                </div>
              </div>
            </div>
            <!-- END WIDGET REMINDER -->
          </div>
          <div class="col-md-12">
            <!-- WIDGET REMINDER -->
            <div class="widget widget-hide-header widget-reminder">
              <div class="widget-header hide">
                <h3>Recordaorio</h3>
              </div>
              <div class="widget-content">
                <div class="today-reminder">
                  <h4 class="reminder-title">Destete</h4>
                  <p class="reminder-time"><i class="fa fa-clock-o"></i> 2:00 PM</p>
                  <p class="reminder-place">Destetes</p>
                  <em class="reminder-notes">Arrebatarle los ratones a las hembras</em>
                  <i class="fa fa-bell"></i>

                </div>
              </div>
            </div>
            <!-- END WIDGET REMINDER -->
          </div>
          <!-- Acá va contenido importante, notificaciones, etc. -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>
    <!-- /content-wrapper -->

  </jsp:attribute>

</t:plantilla_general>