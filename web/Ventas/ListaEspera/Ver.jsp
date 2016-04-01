<%-- 
    Document   : Ver
    Created on : Mar 28, 2015, 11:26:24 PM
    Author     : jespinozac95
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ventas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <form id="form-eliminar-lista" method="post" action="ListaEspera">
        <input name="accion" value="Eliminar" hidden> 
        <input name="id_lista" value="${lista.getId_lista()}" hidden>
    </form>
    
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/ListaEspera?">Listas de Espera</a>
            </li>
            <li class="active">Enlistado ${lista.getId_lista()}</li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-file-text-o"></i> Enlistado ${lista.getId_lista()}  </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditarYBorrar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 702 || permiso == 701}">
                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditarYBorrar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ventas/ListaEspera?accion=editar&id_lista=${lista.getId_lista()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar este lista" data-form-id="form-eliminar-lista">Eliminar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Cliente:  </strong>  </td> <center> <td> ${lista.getCliente().getNombre()}   </td> </center> </tr>
                <tr><td> <strong>Fecha de Solicitud: </strong>  </td> <center> <td> ${lista.getFecha_solicitud_S()}   </td> </center> </tr>
                <tr><td> <strong>Fecha de Atención / Despacho: </strong>  </td> <center> <td> ${lista.getFecha_atencion_S()}   </td> </center> </tr>
              </table>
              <br>
              
            </div>
          <!-- END WIDGET TICKET TABLE -->
          </div>
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>

  </jsp:attribute>

</t:plantilla_general>
