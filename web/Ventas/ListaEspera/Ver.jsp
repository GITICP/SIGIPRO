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
                <tr><td> <strong>Cliente: </strong></td><center> 
                        <c:choose>
                          <c:when test= "${lista.getCliente() != null}">
                              <td><a href="/SIGIPRO/Ventas/Clientes?accion=ver&id_cliente=${lista.getCliente().getId_cliente()}">
                                <div style="height:100%;width:100%">
                                    ${lista.getCliente().getNombre()}
                                </div>
                                </a>
                              </td>
                          </c:when>
                          <c:otherwise>
                              <td>${lista.getNombre_cliente()}</td>
                          </c:otherwise>
                        </c:choose>
                    </center> </tr>
                <c:choose>
                  <c:when test= "${lista.getCliente() == null}">
                      <tr><td> <strong>Teléfono: </strong>  </td> <center> <td> ${lista.getTelefono()}   </td> </center> </tr>
                      <tr><td> <strong>Correo electrónico: </strong>  </td> <center> <td> ${lista.getCorreo()}   </td> </center> </tr>
                  </c:when>
                </c:choose>    
                <tr><td> <strong>Fecha de Solicitud: </strong>  </td> <center> <td> ${lista.getFecha_solicitud_S()}   </td> </center> </tr>
                <tr><td> <strong>Fecha de Atención / Despacho: </strong>  </td> <center> <td> ${lista.getFecha_atencion_S()}   </td> </center> </tr>
                <tr><td> <strong>Total de Días: </strong>  </td> <center> <td> ${lista.getDias()}   </td> </center> </tr>
                <tr><td> <strong>Observaciones: </strong>  </td> <center> <td> ${lista.getObservaciones()}   </td> </center> </tr>
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
            <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
            <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.js"></script>
            

  </jsp:attribute>

</t:plantilla_general>
