<%-- 
    Document   : Ver
    Created on : Feb 19, 2015, 6:29:37 PM
    Author     : Amed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ratonera" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bioterio - Ratonera</li>
            <li> 
              <a href="/SIGIPRO/Ratonera/Destetes?">Destetes</a>
            </li>
            <li class="active"> ${destete.getFecha_destete()} </li>
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
              <h3><i class="sigipro-mouse-1"></i> Destete  ${destete.getFecha_destete()} </h3>
              <div class="btn-group widget-header-toolbar">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ratonera/Destetes?accion=editar&id_destete=${destete.getId_destete()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar el Destete" data-href="/SIGIPRO/Ratonera/Destetes?accion=eliminar&id_destete=${destete.getId_destete()}">Eliminar</a>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Fecha Destete:</strong> <td>${destete.getFecha_destete_S()} </td></tr>
                <tr><td> <strong>Numero de Hembras:</strong> <td>${destete.getNumero_hembras()} </td></tr>
                <tr><td> <strong>Numero de Machos:</strong> <td>${destete.getNumero_machos()} </td></tr>
                <tr><td> <strong>Cepa:</strong> <td>${destete.getCepa().getNombre()} </td></tr>
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

