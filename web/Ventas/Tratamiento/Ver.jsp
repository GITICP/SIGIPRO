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

    <form id="form-eliminar-tratamiento" method="post" action="Tratamiento">
        <input name="accion" value="Eliminar" hidden> 
        <input name="id_tratamiento" value="${tratamiento.getId_tratamiento()}" hidden>
    </form>
    
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/Tratamiento?">Tratamientos</a>
            </li>
            <li class="active">Tratamiento ${tratamiento.getId_tratamiento()}</li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-file-text-o"></i> Tratamiento ${tratamiento.getId_tratamiento()}  </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditarYBorrar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 702 || permiso == 701}">
                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditarYBorrar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ventas/Tratamiento?accion=editar&id_tratamiento=${tratamiento.getId_tratamiento()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar este tratamiento" data-form-id="form-eliminar-tratamiento">Eliminar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>ID:  </strong></td> <center> <td> ${tratamiento.getId_tratamiento()} </td> </center> </tr>
                <tr><td> <strong>Cliente:  </strong>  </td> <center> <td> ${tratamiento.getCliente().getNombre()}   </td> </center> </tr>
                <tr><td> <strong>Fecha: </strong>  </td> <center> <td> ${tratamiento.getFecha_S()}   </td> </center> </tr>
                <tr><td> <strong>Observaciones: </strong>  </td> <center> <td> ${tratamiento.getObservaciones()}   </td> </center> </tr>
                <tr><td> <strong>Estado: </strong>  </td> <center> 
                <c:choose>
                        <c:when test="${tratamiento.getEstado().equals('Idóneo')}">
                           <td><font color="green">${tratamiento.getEstado()}</font></td>
                        </c:when>
                        <c:when test="${tratamiento.getEstado().equals('Normal')}">
                            <td><font color="blue">${tratamiento.getEstado()}</font></td>
                        </c:when>
                        <c:otherwise>
                            <td><font color="red">${tratamiento.getEstado()}</font></td>
                        </c:otherwise>
                    </c:choose>
                </center> </tr>
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
