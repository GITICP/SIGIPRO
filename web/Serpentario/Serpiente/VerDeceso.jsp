<%-- 
    Document   : VerDeceso
    Created on : Apr 2, 2015, 4:56:34 PM
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
              <a href="/SIGIPRO/Serpentario/Serpiente?">Serpientes</a>
            </li>
            <li class="active"> ${serpiente.getId_serpiente()} </li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-bug"></i> Ver Catálogo Tejido - Colección Húmeda de Serpiente ${serpiente.getId_serpiente()} </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 314}">
                    <c:set var="contienePermisoEditar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Serpentario/Serpiente?accion=editardeceso&deceso=${deceso}&id_serpiente=${serpiente.getId_serpiente()}">Editar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                  <c:choose>
                      <c:when test="${deceso.equals('coleccionhumeda')}">
                        <tr><td> <strong>Colección Húmeda:</strong></td> <td>${coleccionhumeda.getId_coleccion_humeda()} </td></tr>
                        <tr><td> <strong>Serpiente:</strong> <td>${coleccionhumeda.getSerpiente().getNumero_serpiente()} </td></tr> 
                        <tr><td> <strong>Propósito:</strong> <td>${coleccionhumeda.getProposito()} </td></tr> 
                        <tr><td> <strong>Observaciones:</strong> <td>${coleccionhumeda.getObservaciones()} </td></tr> 
                        <tr><td> <strong>Usuario Responsable:</strong> <td>${coleccionhumeda.getUsuario().getNombreCompleto()} </td></tr> 
                     </c:when>
                     <c:otherwise>
                        <tr><td> <strong>Catálogo de Tejidos:</strong></td> <td>${catalogotejido.getId_catalogo_tejido()} </td></tr>
                        <tr><td> <strong>Serpiente:</strong> <td>${catalogotejido.getSerpiente().getNumero_serpiente()} </td></tr> 
                        <tr><td> <strong>Número de Caja:</strong> <td>${catalogotejido.getNumero_caja()} </td></tr> 
                        <tr><td> <strong>Posición:</strong> <td>${catalogotejido.getPosicion()} </td></tr> 
                        <tr><td> <strong>Observaciones:</strong> <td>${catalogotejido.getObservaciones()} </td></tr> 
                        <tr><td> <strong>Estado:</strong> <td>${catalogotejido.getEstado()} </td></tr> 
                        <tr><td> <strong>Usuario Responsable:</strong> <td>${catalogotejido.getUsuario().getNombreCompleto()} </td></tr> 
                     </c:otherwise>
                  </c:choose>
                
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