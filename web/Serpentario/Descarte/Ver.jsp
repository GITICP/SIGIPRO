<%-- 
    Document   : Ver
    Created on : May 30, 2015, 2:05:39 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Serpentario" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Serpentario</li>
            <li> 
              <a href="/SIGIPRO/Serpentario/Descarte?">Descartes</a>
            </li>
            <li class="active"> ${evento.getId_evento()} </li>
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
              <h3><i class="sigipro-snake-1"></i> Descarte del Evento  ${evento.getId_evento()} </h3>
              <div class="btn-group widget-header-toolbar">
                
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Número de Serpiente:</strong></td> <td>${evento.getSerpiente().getNumero_serpiente()} </td></tr>
                <tr><td> <strong>Usuario del Evento:</strong> <td>${evento.getUsuario().getNombre_completo()}</td></tr>
                <tr><td> <strong>Fecha del Evento:</strong> <td>${evento.getFecha_eventoAsString()}</td></tr>
                <tr><td> <strong>Observaciones:</strong></td> <td>${evento.getObservaciones()} </td></tr>
              </table>
              <br>
            </div>
            <!-- END WIDGET TICKET TABLE -->
          </div>
          <!-- /main-content -->
        </div>
        <!-- /main -->
      </div>

    </jsp:attribute>

  </t:plantilla_general>

