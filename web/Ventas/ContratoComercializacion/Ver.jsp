<%-- 
    Document   : Ver
    Created on : Mar 28, 2015, 11:26:24 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ventas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <form id="form-eliminar-contrato" method="post" action="ContratoComercializacion">
        <input name="accion" value="Eliminar" hidden> 
        <input name="id_contrato" value="${contrato.getId_contrato()}" hidden>
    </form>
    
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/ContratoComercializacion?">Contratos de Comercialización</a>
            </li>
            <li class="active">Contrato ${contrato.getNombre()}</li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-file-text-o"></i> Contrato ${contrato.getNombre()}  </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditarYBorrar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 702 || permiso == 701}">
                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditarYBorrar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ventas/ContratoComercializacion?accion=editar&id_contrato=${contrato.getId_contrato()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar este contrato" data-form-id="form-eliminar-contrato">Eliminar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Nombre: </strong></td> <center> <td> ${contrato.getNombre()} </td> </center> </tr>
                <tr><td> <strong>Fecha: </strong>  </td> <center> <td> ${contrato.getFecha_S()}   </td> </center> </tr>
                <tr><td> <strong>Observaciones: </strong>  </td> <center> <td> ${contrato.getObservaciones()}   </td> </center> </tr>
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
