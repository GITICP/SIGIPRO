<%-- 
    Document   : Ver
    Created on : Feb 19, 2015, 6:29:37 PM
    Author     : Amed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Conejera" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bioterio - Conejera</li>
                         <li> 
                            <a href="/SIGIPRO/Conejera/Gruposhembras?">Grupo ${cruce.getConeja().getCaja().getGrupo().getIdentificador()}</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/Conejera/Cajas?id_grupo=${cruce.getConeja().getCaja().getGrupo().getId_grupo()}">Espacios</a>
                        </li>
                        <li>
                           <a href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${cruce.getConeja().getCaja().getId_caja()}">1</a>
                        </li>
            <li class="active"> Cruce del ${cruce.getFecha_cruce()} de Coneja en Caja #${cruce.getConeja().getCaja().getNumero()} </li>
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
              <h3><i class="fa fa-barcode"></i>  Cruce del ${cruce.getFecha_cruce()} de Coneja en Caja #${cruce.getConeja().getCaja().getNumero()} </h3>
              <div class="btn-group widget-header-toolbar">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Conejera/Cruces?accion=editar&id_cruce=${cruce.getId_cruce()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar el cruce" data-href="/SIGIPRO/Conejera/Cruces?accion=eliminar&id_cruce=${cruce.getId_cruce()}">Eliminar</a>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="tabla-ver">
                <tr><td> <strong>Macho:</strong> <td>${cruce.getMacho().getIdentificacion()} </td></tr>
                <tr><td> <strong>Caja de Coneja:</strong> <td> #${cruce.getConeja().getCaja().getNumero()} </td></tr>
                <tr><td> <strong>Fecha del Cruce:</strong> <td>${cruce.getFecha_cruce_S()} </td></tr>
                <tr><td> <strong>Observaciones:</strong> <td>${cruce.getObservaciones()} </td></tr>
                <tr><td> <strong>Cantidad de Paridos:</strong> <td>  ${cruce.getCantidad_paridos()}</td></tr>
                <tr><td> <strong>Fecha Estimada del Parto</strong> <td>  ${cruce.getFecha_estimada_parto_S()}</td></tr>
                <tr><td> <strong>Fecha del Parto</strong> <td>  ${cruce.getFecha_parto_S()}</td></tr>
                
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

