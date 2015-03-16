<%-- 
    Document   : Ver
    Created on : Jan 11, 2015, 11:57:19 AM
    Author     : Walter
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
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Serpentario</li>
            <li> 
              <a href="/SIGIPRO/Serpentario/Especie?">Especies de Serpiente</a>
            </li>
            <li class="active"> ${especie.getGenero_especie()} </li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-barcode"></i> ${especie.getGenero_especie()} </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEliminar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 41}">
                    <c:set var="contienePermisoEliminar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEliminar}">
                  <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar la especie" data-href="/SIGIPRO/Serpentario/Especie?accion=eliminar&id_especie=${especie.getId_especie()}">Eliminar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Nombre del Género:</strong></td> <td>${especie.getGenero()} </td></tr>
                <tr><td> <strong>Nombe de la Especie:</strong> <td>${especie.getEspecie()} </td></tr>
              </table>
              <br>
            </div>
          </div>
          <!-- END WIDGET TICKET TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>

  </jsp:attribute>

</t:plantilla_general>
