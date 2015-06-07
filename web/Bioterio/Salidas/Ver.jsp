<%-- 
    Document   : Ver
    Created on : Feb 19, 2015, 6:29:37 PM
    Author     : Amed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Conejera" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />
        <form id="form-eliminar-salida" method="post" action="Salidas">
            <input name="accion" value="Eliminar" hidden> 
            <input name="id_salida" value="${salida.getId_salida()}" hidden>
            <input hidden="true" name="especie" value="${salida.isEspecie()}">
        </form>
        
        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Bioterio - Salidas</li>
                        <li> 
                            <a href="/SIGIPRO/Bioterio/Salidas?">Salida Extraordinaria</a>
                        </li>
                        <li class="active"> ${salida.getFecha()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-barcode"></i> Salida Extraordinaria ${salida.getFecha()}</h3>
                            <div class="btn-group widget-header-toolbar">
                                <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Bioterio/Salidas?especie=${salida.isEspecie()}&accion=editar&id_salida=${salida.getId_salida()}">Editar Salida</a>
                                <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar este análisis" data-form-id="form-eliminar-salida">Eliminar Salida</a>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <c:choose>
                                  <c:when test="${salida.isEspecie()}">
                                    <tr><td> <strong>Especie: </strong> <td>Ratones </td></tr>
                                  </c:when>
                                  <c:otherwise>
                                    <tr><td> <strong>Especie: </strong> <td>Conejos </td></tr>
                                  </c:otherwise>
                                </c:choose>
                                <tr><td> <strong>Fecha: </strong> <td>${salida.getFecha()} </td></tr>
                                <tr><td> <strong>Cantidad:</strong> <td>${salida.getCantidad()} </td></tr>
                                <tr><td> <strong>Razón:</strong> <td>${salida.getRazon()} </td></tr>
                                <tr><td> <strong>Observaciones:</strong> <td>${salida.getObservaciones()} </td></tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /main-content -->
        </div>
        <!-- /main -->
    </div>
</div>

</jsp:attribute>
<jsp:attribute name="scripts">
    <script src="/SIGIPRO/recursos/js/sigipro/cajas.js"></script>
</jsp:attribute>
</t:plantilla_general>

