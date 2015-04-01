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
              <a href="/SIGIPRO/Ratonera/Caras?">Caras</a>
            </li>
            <li class="active"> Número ${cara.getNumero_cara()} </li>
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
              <h3><i class="fa fa-barcode"></i> Cara Número ${cara.getNumero_cara()} </h3>
              <div class="btn-group widget-header-toolbar">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ratonera/Caras?accion=editar&id_cara=${cara.getId_cara()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar la Cara" data-href="/SIGIPRO/Ratonera/Caras?accion=eliminar&id_cara=${cara.getId_cara()}">Eliminar</a>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="tabla-ver">
                <tr><td> <strong>Numero de Cara:</strong> <td>${cara.getNumero_cara()} </td></tr>
                <tr><td> <strong>Cepa:</strong> <td>${cara.getCepa().getNombre()} </td></tr>
                <tr><td> <strong>Ascendencia Macho:</strong> <td>${cara.getMacho_as()} </td></tr>
                <tr><td> <strong>Ascendencia Hembra:</strong> <td>${cara.getHembra_as()} </td></tr>
                <tr><td> <strong>Fechas de Apareamiento:</strong> <td>  ${cara.getFecha_apareamiento_i_S()} - ${cara.getFecha_apareamiento_f_S()}</td></tr>
                <tr><td> <strong>Fechas de Eliminación Macho:</strong> <td>  ${cara.getFecha_eliminacionmacho_i_S()} - ${cara.getFecha_eliminacionmacho_f_S()}</td></tr>
                <tr><td> <strong>Fechas de Eliminación Hembra:</strong> <td>  ${cara.getFecha_eliminacionhembra_i_S()} - ${cara.getFecha_eliminacionhembra_f_S()}</td></tr>
                <tr><td> <strong>Fechas de Selección Machos y Hembras:</strong> <td>  ${cara.getFecha_seleccionnuevos_i_S()} - ${cara.getFecha_seleccionnuevos_f_S()}</td></tr>
                <tr><td> <strong>Fechas de Reposición Nuevo Ciclo:</strong> <td>  ${cara.getFecha_reposicionciclo_i_S()} - ${cara.getFecha_reposicionciclo_f_S()}</td></tr>
                <tr><td> <strong>Vigente Hasta:</strong> <td> ${cara.getFecha_vigencia_S()}</td></tr>
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

