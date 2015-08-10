<%-- 
    Document   : index
    Created on : Apr 25, 2015, 3:19:45 PM
    Author     : Amed
--%>

<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Notificaciones" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Notificaciones</li>
            <li> 
              <a href="/SIGIPRO/Calendario/Notificaciones?">Notificaciones</a>
            </li>
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
              <h3><i class="fa fa-bell"></i> Notificaciones de Eventos</h3>
                <div class="btn-group widget-header-toolbar">
                </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Nombre de Evento</th>
                    <th>Inicio </th>
                    <th>Fin</th>
                    <th>Seleccionar Notificación</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${eventos}" var="evento">
                    <tr>
                      <td>
                        ${evento.getTitle()}
                      </td>
                      <td>${evento.getStart_date()}</td>
                      <td>
                        ${evento.getEnd_date()}  </td>
                      <td>
                        
													<input type="checkbox">
												</td>
                    </tr>

                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
          <!-- END COLUMN FILTER DATA TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->

    </jsp:attribute>
    <jsp:attribute name="scripts">
      <script></script>
    </jsp:attribute>
  </t:plantilla_general>

