<%-- 
    Document   : Ver
    Created on : Mar 28, 2015, 11:26:24 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Produccion" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <form id="form-eliminar-veneno_produccion" method="post" action="Veneno_Produccion">
        <input name="accion" value="Eliminar" hidden> 
        <input name="id_veneno" value="${veneno.getId_veneno()}" hidden>
    </form>
    
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Producción</li>
            <li> 
              <a href="/SIGIPRO/Produccion/Veneno_Produccion?">Venenos de Producción</a>
            </li>
            <li class="active">Veneno de Producción de ${veneno.getVeneno()}</li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-flask"></i> Veneno de Producción de ${veneno.getVeneno()}  </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditarYBorrar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 605}">
                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditarYBorrar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Veneno_Produccion?accion=editar&id_veneno=${veneno.getId_veneno()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar este veneno de producción" data-form-id="form-eliminar-veneno_produccion">Eliminar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Veneno:</strong></td> <td>${veneno.getVeneno()} </td></tr>
                <tr><td> <strong>Fecha de Ingreso:</strong> <td>${veneno.getFecha_ingreso_S()} </td></tr>
                <tr><td> <strong>Cantidad:</strong> <td>${veneno.getCantidad()} </td></tr>
                <tr><td> <strong>Observaciones:</strong> <td>${veneno.getObservaciones()} </td></tr>
              </table>
              <br>
              
              <div class="widget widget-table">
                <div class="widget-header">
                  <h3><i class="fa fa-check"></i> Veneno del Serpentario Asociado</h3>
                </div>
                <div class="widget-content">
                  <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable">
                    <thead>
                      <tr>
                        <th>Especie del Veneno</th>
                      </tr>
                    </thead>
                    <tbody>
                      
                        <tr id="${veneno.getVeneno_serpentario().getId_veneno()}">
                          <td>${veneno.getVeneno_serpentario().getEspecie().getEspecie()}</td>
                        </tr>
                      
                    </tbody>
                  </table>
                </div>
              </div>
              
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
