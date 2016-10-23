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

    <form id="form-eliminar-Pago" method="post" action="Pago">
        <input name="accion" value="Eliminar" hidden> 
        <input name="id_pago" value="${pago.getId_pago()}" hidden>
    </form>
    
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/Pago?">Pagos</a>
            </li>
            <li class="active">Pago ${pago.getId_pago()}</li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-file-text-o"></i> Pago ${pago.getId_pago()}  </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditarYBorrar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 702 || permiso == 701}">
                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditarYBorrar}">
                  </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>ID: </strong></td> <center> <td> ${pago.getId_pago()} </td> </center> </tr>
                <tr><td> <strong>Número de Factura: </strong>  </td> <center> <td>
                          <a href="/SIGIPRO/Ventas/Factura?accion=ver&id_factura=${pago.getFactura().getId_factura()}">
                        <div style="height:100%;width:100%">
                            ${pago.getFactura().getNumero()}
                        </div>
                      </td>
                      </center> </tr>
                <tr><td> <strong>Código: </strong>  </td> <center> <td> ${pago.getCodigo()}  </td> </center> </tr>
                <tr><td> <strong>Monto: </strong>  </td> <center> <td> ${pago.getMonto()}  </td> </center> </tr>
                <tr><td> <strong>Nota: </strong>  </td> <center> <td> ${pago.getNota()}  </td> </center> </tr>
                <tr><td> <strong>Fecha: </strong>  </td> <center> <td> ${pago.getFecha()}  </td> </center> </tr>
                <tr><td> <strong>Consecutivo: </strong>  </td> <center> <td> ${pago.getConsecutive()}  </td> </center> </tr>
                <tr><td> <strong>Moneda: </strong>  </td> <center> <td> ${pago.getMoneda()}  </td> </center> </tr>
                <tr><td> <strong>Código de Remisión: </strong>  </td> <center> <td> ${pago.getCodigo_remision()}  </td> </center> </tr>
                <tr><td> <strong>Consecutivo de Remisión: </strong>  </td> <center> <td> ${pago.getConsecutive_remision()}  </td> </center> </tr>
                <tr><td> <strong>Fecha de Remisión: </strong>  </td> <center> <td> ${pago.getFecha_remision()}  </td> </center> </tr>
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
