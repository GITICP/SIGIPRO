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
              <a href="/SIGIPRO/Ratonera/Cepas?">Cepas</a>
            </li>
            <li class="active"> ${cepa.getNombre()} </li>
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
              <h3><i class="fa fa-barcode"></i> Cepa  ${cepa.getNombre()} </h3>
              <div class="btn-group widget-header-toolbar">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ratonera/Cepas?accion=editar&id_cepa=${cepa.getId_cepa()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar el Cepa" data-href="/SIGIPRO/Ratonera/Cepas?accion=eliminar&id_cepa=${cepa.getId_cepa()}">Eliminar</a>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Nombre de la Cepa:</strong> <td>${cepa.getNombre()} </td></tr>
                <tr><td> <strong>Descripción:</strong> <td>${cepa.getDescripcion()} </td></tr>
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

