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

    <form id="form-eliminar-inoculo" method="post" action="Inoculo">
        <input name="accion" value="Eliminar" hidden> 
        <input name="id_inoculo" value="${inoculo.getId_inoculo()}" hidden>
    </form>
    
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Producción</li>
            <li> 
              <a href="/SIGIPRO/Produccion/Inoculo?">Catálogo de Inóculos</a>
            </li>
            <li class="active">Inóculo de ${inoculo.getIdentificador()} </li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-flask"></i> Inóculo de ${inoculo.getIdentificador()}  </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditarYBorrar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 604}">
                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditarYBorrar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Inoculo?accion=editar&id_inoculo=${inoculo.getId_inoculo()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar este inóculo" data-form-id="form-eliminar-inoculo">Eliminar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Identificador:</strong></td> <td>${inoculo.getIdentificador()} </td></tr>
                <tr><td> <strong>Fecha de Preparación:</strong> <td>${inoculo.getFecha_preparacion_S()} </td></tr>
                <tr><td> <strong>Encargado de Preparación:</strong> <td>${inoculo.getEncargado_preparacion().getNombreCompleto()} </td></tr>
              </table>
              <br>
              
              <div class="widget widget-table">
                <div class="widget-header">
                  <h3><i class="fa fa-check"></i> Venenos Asociados</h3>
                </div>
                <div class="widget-content">
                  <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                    <thead>
                      <tr>
                        <th>Nombre del Veneno</th>
                        <th>Peso (mg)</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${venenos}" var="veneno">
                            <tr>
                                <td>
                                <a href="/SIGIPRO/Produccion/Veneno_Produccion?accion=ver&id_veneno=${veneno.getVeneno().getId_veneno()}">
                                <div style="height:100%;width:100%">
                                        ${veneno.getVeneno().getVeneno()}
                                </div>
                                </a>
                                </td>
                                    <td>${veneno.getCantidad()}</td>
                            </tr>
                        </c:forEach>
                      
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
