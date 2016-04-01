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
                            <a href="/SIGIPRO/Produccion/Actividad_Apoyo">Categorías de Actividades de Apoyo</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=indexactividades&id_categoria_aa=${respuesta.getActividad().getCategoria().getId_categoria_aa()}">Actividades de Apoyo</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=veractividad&id_actividad=${respuesta.getActividad().getId_actividad()}">Actividades de Apoyo Realizadas</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=verrespuesta&id_respuesta=${respuesta.getId_respuesta()}">Respuesta - ${respuesta.getNombre()} </a>
                        </li>
                        <li class="active"> Historial de ${respuesta.getNombre()} - Version ${respuesta.getVersion()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-flask"></i> ${respuesta.getActividad().getCategoria().getNombre()} - ${respuesta.getActividad().getNombre()} - Versión ${respuesta.getVersion()}  </h3>
                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 676)}">
                                <div class="btn-group widget-header-toolbar">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=activarrespuesta&id_historial=${respuesta.getId_historial()}&id_respuesta=${respuesta.getId_respuesta()}">Activar</a>
                                </div>
                            </c:if>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Nombre:</strong></td> <td>${respuesta.getNombre()} </td></tr>
                                <tr><td> <strong>Fecha:</strong></td> <td>${respuesta.getFechaAsString()} </td></tr>
                                <tr><td> <strong>Usuario realizar:</strong></td> <td>${respuesta.getUsuario_realizar().getNombre_completo()} </td></tr>
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
