<%-- 
    Document   : Lista
    Created on : Jul 23, 2015, 6:26:06 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                            <a href="/SIGIPRO/ControlCalidad/Analisis?">Análisis</a>
                        </li>
                        <li class="active"> Lista de Solicitudes de ${nombreAnalisis} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Lista de Solicitudes por Análisis - ${nombreAnalisis}</h3>

                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Identificadores de Muestras (Tipo)</th>
                                        <th>Número de Solicitud</th>
                                        <th>Acción</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaAGS}" var="ags">
                                        <tr id ="${ags.getId_analisis_grupo_solicitud()}">
                                            <td>
                                                <c:forEach items="${ags.getGrupo().getGrupos_muestras()}" var="muestra">
                                                    ${muestra.getIdentificador()} (${muestra.getTipo_muestra().getNombre()})<br>
                                                </c:forEach>
                                            </td>
                                            <td>
                                                <a href="/SIGIPRO/ControlCalidad/Solicitud?accion=ver&id_solicitud=${ags.getGrupo().getSolicitud().getId_solicitud()}">
                                                ${ags.getGrupo().getSolicitud().getNumero_solicitud()}</a>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${ags.getGrupo().getSolicitud().getEstado().equals('Recibido')}">
                                                        <c:choose>
                                                            <c:when test="${ags.getResultados().size() == 0}">
                                                                <a class="btn btn-primary btn-sm boton-accion" 
                                                                   href="/SIGIPRO/ControlCalidad/Analisis?accion=realizar&id_analisis=${ags.getAnalisis().getId_analisis()}&id_ags=${ags.getId_analisis_grupo_solicitud()}">
                                                                    Realizar
                                                                </a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a class="btn btn-primary btn-sm boton-accion" 
                                                                   href="/SIGIPRO/ControlCalidad/Analisis?accion=realizar&id_analisis=${ags.getAnalisis().getId_analisis()}&id_ags=${ags.getId_analisis_grupo_solicitud()}">
                                                                    Repetir
                                                                </a>
                                                                <a class="btn btn-primary btn-sm boton-accion" 
                                                                   href="/SIGIPRO/ControlCalidad/Resultado?accion=vermultiple&id_analisis=${ags.getAnalisis().getId_analisis()}&id_ags=${ags.getId_analisis_grupo_solicitud()}&id_solicitud=${ags.getGrupo().getSolicitud().getId_solicitud()}&numero_solicitud=${ags.getGrupo().getSolicitud().getNumero_solicitud()}">
                                                                    Ver Resultados (${ags.getResultados().size()})
                                                                </a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        Solicitud aún no ha sido recibida.
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- END COLUMN FILTER DATA TABLE -->
                </div>
                <!-- /main-content -->
            </div>
            <!-- /main -->
        </div>

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/Analisis.js"></script>
    </jsp:attribute>
</t:plantilla_general>
