<%-- 
    Document   : Ver
    Created on : Jun 29, 2015, 4:48:27 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Producción" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Producción</li>
                        <li> 
                            <a href="/SIGIPRO/Produccion/Lote?">Lotes de Producción</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/Produccion/Lote?accion=ver&id_lote=${respuesta.getLote().getId_lote()}">${respuesta.getLote().getNombre()}</a></li>
                        <li> <a href="/SIGIPRO/Produccion/Paso?accion=ver&id_paso=${respuesta.getPaso().getId_paso()}">${respuesta.getPaso().getNombre()}</a> </li>
                        <li class="active"> Historial - Respuesta Version ${respuesta.getVersion()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-flask"></i> ${respuesta.getLote().getNombre()} - ${respuesta.getPaso().getNombre()} - Versión ${respuesta.getVersion()}  </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Lote:</strong></td> <td>${respuesta.getLote().getNombre()} </td></tr>
                                <tr><td> <strong>Paso:</strong></td> <td>${respuesta.getPaso().getNombre()} </td></tr>
                                <tr><td> <strong>Usuario realizar:</strong></td> <td>${respuesta.getUsuario_realizar().getNombre_completo()} </td></tr>
                                <c:if test="${respuesta.getUsuario_revisar().getId_usuario()!=0}">
                                    <tr><td> <strong>Usuario revisar:</strong></td> <td>${respuesta.getUsuario_revisar().getNombre_completo()} </td></tr>
                                </c:if>
                                <c:if test="${respuesta.getUsuario_verificar().getId_usuario()!=0}">
                                    <tr><td> <strong>Usuario verificar:</strong></td> <td>${respuesta.getUsuario_verificar().getNombre_completo()} </td></tr>
                                </c:if>
                            </table>
                            <br>
                            <div class="col-md-12">
                                ${cuerpo_datos}
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
