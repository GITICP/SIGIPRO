<%-- 
    Document   : Ver
    Created on : Jan 27, 2015, 2:08:46 PM
    Author     : Amed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.json.JSONObject"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Bitácora" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bitácora</li>
            <li> 
              <a href="/SIGIPRO/Bitacora/Bitacora?">Bitácora</a>
            </li>
            <li class="active"> ${bitacora.getFecha_accion_parse()}</li>
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
              <h3><i class="fa fa-barcode"></i> ${bitacora.getFecha_accion_parse()} </h3>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Fecha de Acción:</strong></td> <td>${bitacora.getFecha_accion_parse()} </td></tr>
                <tr><td> <strong>Nombre de Usuario:</strong> <td>${bitacora.getNombre_usuario()} </td></tr>
                <tr><td> <strong>IP:</strong> <td>${bitacora.getIp()} </td></tr>
                <tr><td> <strong>Tabla:</strong> <td>${bitacora.getTabla()} </td></tr>
                <tr><td> <strong>Accion:</strong> <td>${bitacora.getAccion()} </td></tr>
                <td style="line-height:40px;" colspan=3>&nbsp;</td>
                <table style='width:500px' class="table table-sorting table-striped table-hover datatable">
                    <div style='width:500px' class="widget-header">
                        <h3><i class="fa fa-check"></i> Estado después de la Acción - ${bitacora.getAccion()}</h3>
                    </div>                    
                    <c:forEach items="${llaves}" var="key">
                        <tr><td> <strong>${key}</strong></td> <td>${bitacora.getEstado_parse().get(key)} </td></tr>
                    </c:forEach>
                </table>
              </table>
            </div>
          </div>
          <!-- Esta parte es la de los interno del catalogo externo -->

          <!-- END WIDGET TICKET TABLE -->
        </div>
        <!-- /main-content -->
      </div>

      <!-- /main -->
    </div>

  </jsp:attribute>

</t:plantilla_general>

