<%-- 
    Document   : Ver
    Created on : Jun 30, 2015, 8:58:03 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Control de Calidad" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Control de Calidad</li>
                        <li> 
                            <a href="/SIGIPRO/ControlCalidad/Resultado?accion=vermultiple&id_analisis=${analisis.getId_analisis()}&id_ags=${resultado.getAgs().getId_analisis_grupo_solicitud()}&id_solicitud=${solicitud.getId_solicitud()}&numero_solicitud=${solicitud.getNumero_solicitud()}">Resultados</a>
                        </li>
                        <li class="active"> Resultado de Solicitud ${solicitud.getNumero_solicitud()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Ver Resultado de Solicitud ${solicitud.getNumero_solicitud()} - ${analisis.getNombre()}</h3>

                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 547)}">
                                <div class="btn-group widget-header-toolbar">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/ControlCalidad/Resultado?accion=editar&id_resultado=${resultado.getId_resultado()}&id_analisis=${analisis.getId_analisis()}${(analisis.getId_analisis() == 2147483647) ? "&identificadores=" += param.identificadores : "" }">Editar</a>
                                </div>
                            </c:if>

                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            
                            <c:choose>
                                <c:when test="${analisis.getId_analisis() != Integer.MAX_VALUE}">
                                    ${cuerpo_datos}
                                </c:when>
                                <c:otherwise>
                                    <div class="widget-content">
                                        <table class="tabla-ver">
                                            <tr><td> <strong>Glóbulos Rojos (RBC)( x 1000000/µL): </strong></td> <td>${resultado.getRbc()} </td></tr>
                                            <tr><td> <strong>Glóbulos blancos (WBC)( x 1000/µL): </strong></td> <td>${resultado.getWbc()} </td></tr>
                                            <tr><td> <strong>Hematocrito (HCT)%: </strong></td> <td>${resultado.getHematocrito()} </td></tr>
                                            <tr><td> <strong>Hemoglobina (HGB)g/dL: </strong></td> <td>${resultado.getHemoglobina()} </td></tr>
                                            <tr><td> <strong>Volumen corpuscular media (MCV )fL: </strong></td> <td>${resultado.getMcv()} </td></tr>
                                            <tr><td> <strong>Hemoglobina corpuscular Media (MCH) pg: </strong></td> <td>${resultado.getMch()} </td></tr>
                                            <tr><td> <strong>Conc  HGB Corpuscular Media (MCHC) (g/dL): </strong></td> <td>${resultado.getMchc()} </td></tr>
                                            <tr><td> <strong>Otros %: </strong></td> <td>${resultado.getOtros()} </td></tr>
                                            <tr><td> <strong>Número de Linfocitos( x 1000/µL): </strong></td> <td>${resultado.getLinfocitos()} </td></tr>
                                            <tr><td> <strong>Linfocitos LYM%: </strong></td> <td>${resultado.getLym()} </td></tr>
                                            <tr><td> <strong>Número de otros ( x 1000/µL): </strong></td> <td>${resultado.getNum_otros()} </td></tr>
                                            <tr><td> <strong>Plaquetas (PLT)( x 1000/µL): </strong></td> <td>${resultado.getPlt()} </td></tr>
                                            <tr><td> <strong>Repetición: </strong></td> <td>${resultado.getRepeticion()} </td></tr>
                                            <tr><td> <strong>Observaciones:</strong></td> <td>${resultado.getObservaciones()}</td></tr>
                                        </table>
                                        <br>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="widget widget-table">
                                        <div class="widget-header">
                                            <h3><i class="fa fa-flask"></i>Reactivos utilizados</h3>
                                        </div>
                                        <div class="widget-content">
                                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                                                <thead>
                                                    <tr>
                                                        <th>Nombre</th>
                                                        <th>Tipo</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${resultado.getReactivos_resultado()}" var="reactivo">
                                                        <tr>
                                                            <td>${reactivo.getNombre()}</td>
                                                            <td>${reactivo.getTipo_reactivo().getNombre()}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="widget widget-table">
                                        <div class="widget-header">
                                            <h3><i class="fa fa-gears"></i>Equipos utilizados</h3>
                                        </div>
                                        <div class="widget-content">
                                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                                                <thead>
                                                    <tr>
                                                        <th>Nombre</th>
                                                        <th>Tipo</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${resultado.getEquipos_resultado()}" var="equipo">
                                                        <tr>
                                                            <td>${equipo.getNombre()}</td>
                                                            <td>${equipo.getTipo_equipo().getNombre()}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="widget widget-table">
                                        <div class="widget-header">
                                            <h3><i class="fa fa-flask"></i>Materiales de Referencia Utilizados</h3>
                                        </div>
                                        <div class="widget-content">
                                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                                                <thead>
                                                    <tr>
                                                        <th>Número de Lote/Identificador</th>
                                                        <th>Tipo</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${resultado.getPatrones_resultado()}" var="patron">
                                                        <tr>
                                                            <td>${patron.getNumero_lote()}</td>
                                                            <td>${patron.getTipo().getTipo()}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
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