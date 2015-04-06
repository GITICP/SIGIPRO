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
              <a href="/SIGIPRO/Ratonera/SolicitudesRatonera?">Entrega Ratonera</a>
            </li>
            <li class="active"> Entrega ${entrega.getFecha_entrega_S()} </li>
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
              <h3><i class="fa fa-barcode"></i> Entrega del ${entrega.getFecha_entrega_S()}  de la solicitud número ${entrega.getSolicitud().getId_solicitud()}</h3>
              <div class="btn-group widget-header-toolbar">
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="tabla-ver">
                <tr><td> <strong>Numero de Solicitud:</strong> <td>${entrega.getSolicitud().getId_solicitud()} </td></tr>
                <tr><td> <strong>Fecha Entrega:</strong> <td>${entrega.getFecha_entrega()} </td></tr>
                <tr><td> <strong>Numero de Animales:</strong> <td>${entrega.getNumero_animales()} </td></tr>
                <tr><td> <strong>Peso:</strong> <td>${entrega.getPeso()} </td></tr>
                <tr><td> <strong>Numero de Cajas:</strong> <td>  ${entrega.getNumero_cajas()}</td></tr>
                <tr><td> <strong>Sexo:</strong> <td>  ${entrega.getSexo()}</td></tr>
                <tr><td> <strong>Cepa:</strong> <td>  ${entrega.getCepa().getNombre()}</td></tr>
                <tr><td> <strong>Usuario Recipiente:</strong> <td>  ${entrega.getUsuario_recipiente().getNombreCompleto()}</td></tr>
            </div>
            <!-- END WIDGET TICKET TABLE -->
          </div>
          <!-- /main-content -->
        </div>
        <!-- /main -->
      </div>
   </div>
              

    </jsp:attribute>

  </t:plantilla_general>

