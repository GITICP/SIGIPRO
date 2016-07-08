<%-- 
    Document   : Ver
    Created on : Jun 10, 2015, 5:39:03 PM
    Author     : Boga
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
                            <a href="/SIGIPRO/ControlCalidad/Solicitud?">Solicitudes</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/ControlCalidad/Solicitud?accion=ver&id_solicitud=${id_solicitud}">Solicitud ${numero_solicitud}</a>
                        </li>
                        <li class="active"> Resultados de ${analisis.getNombre()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Resultados de Solicitud ${numero_solicitud} - ${analisis.getNombre()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/ControlCalidad/Solicitud?accion=ver&id_solicitud=${id_solicitud}">Volver</a>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr>
                                    <td>
                                        <c:choose>
                                            <c:when test="${ags.getGrupo().getGrupos_muestras().size() == 1}">
                                                <strong>Identificador de la Muestra:</strong>
                                            </c:when>
                                            <c:otherwise>
                                                <strong>Identificadores de las Muestras:</strong>    
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:forEach items="${ags.getGrupo().getGrupos_muestras()}" var="muestra">
                                            ${muestra.getIdentificador()} (${muestra.getTipo_muestra().getNombre()})<br>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </table>
                            <br>
                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                <thead>
                                    <tr>
                                        <th>Identificador Resultado</th>
                                        <th>Fecha</th>
                                        <th>Repetición</th>
                                        <th>Resultado</th>
                                        <th>Usuario Encargado</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${resultados}" var="resultado">
                                        <tr>
                                            <td>
                                                <a href="/SIGIPRO/ControlCalidad/Resultado?accion=ver&id_resultado=${resultado.getId_resultado()}&id_analisis=${id_analisis}">
                                                    <div style="height:100%;width:100%">
                                                        ${numero_solicitud}-R${resultado.getId_resultado()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>${resultado.getFecha()}</td>
                                            <td>${resultado.getRepeticion()}</td>
                                            <td>${resultado.getResultado()}</td>
                                            <td>${resultado.getUsuario().getNombre_completo()}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

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