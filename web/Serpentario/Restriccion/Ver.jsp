<%-- 
    Document   : Ver
    Created on : May 21, 2015, 10:10:44 PM
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
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Serpentario</li>
            <li> 
              <a href="/SIGIPRO/Serpentario/Restriccion?">Restricción de Solicitudes</a>
            </li>
            <li class="active"> Restricción ${restriccion.getUsuario().getNombre_completo()} / ${restriccion.getVeneno().getEspecie().getGenero_especie()} </li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-ban"></i> Restricción ${restriccion.getUsuario().getNombre_completo()} / ${restriccion.getVeneno().getEspecie().getGenero_especie()}</h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEliminar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 362}">
                    <c:set var="contienePermisoEliminar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEliminar}">
                  <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar la restricción" data-href="/SIGIPRO/Serpentario/Restriccion?accion=eliminar&id_restriccion=${restriccion.getId_restriccion()}">Eliminar</a>
                </c:if>
                  
                <c:set var="contienePermisoEditar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 361}">
                    <c:set var="contienePermisoEditar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Serpentario/Restriccion?accion=editar&id_restriccion=${restriccion.getId_restriccion()}">Editar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Nombre del Usuario</strong></td> <td>${restriccion.getUsuario().getNombre_completo()} </td></tr>
                <tr><td> <strong>Nombre de la Especie:</strong> <td>${restriccion.getVeneno().getEspecie().getGenero_especie()} </td></tr>
                <tr><td> <strong>Cantidad Máxima Año Fiscal:</strong> <td>${restriccion.getCantidad_anual()} </td></tr>
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
