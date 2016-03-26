<%-- 
    Document   : Ver
    Created on : Jul 11, 2015, 12:19:00 PM
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
                            <a href="/SIGIPRO/ControlCalidad/Solicitud?">Solicitudes</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/ControlCalidad/Solicitud?accion=ver&id_solicitud=${solicitud.getId_solicitud()}">${solicitud.getNumero_solicitud()} </a>
                        </li>
                        <li class="active"> ${accion} Informe</li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->

                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Informe de Solicitud ${solicitud.getNumero_solicitud()} </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Número de Solicitud: </strong></td> <td>${solicitud.getNumero_solicitud()} </td></tr>
                                <tr><td> <strong>Usuario Solicitante: </strong> <td>${solicitud.getUsuario_solicitante().getNombre_completo()} </td></tr>
                                <tr><td> <strong>Fecha de Solicitud: </strong> <td>${solicitud.getFecha_solicitudAsString()} </td></tr>
                                <c:if test="${solicitud.getUsuario_recibido()!=null}">
                                    <tr><td> <strong>Usuario Receptor: </strong> <td>${solicitud.getUsuario_recibido().getNombre_completo()} </td></tr>
                                    <tr><td> <strong>Fecha de Recepción: </strong> <td>${solicitud.getFecha_recibidoAsString()} </td></tr>
                                </c:if>
                                <tr><td> <strong>Estado: </strong> <td>${solicitud.getEstado()} </td></tr>
                                <c:if test="${!solicitud.getObservaciones().equals('')}">
                                    <tr><td> <strong>Observaciones: </strong> <td>${solicitud.getObservaciones()} </td></tr>
                                </c:if>
                            </table>
                            <br>
                            <form id="form-informe" class="form-horizontal" method="post" action="Informe" id="form-informe">
                                <input type="hidden" value="${accion}" name="accion" />
                                <input type="hidden" value="${solicitud.getId_solicitud()}" name="id_solicitud" />
                                <c:if test="${accion == 'Editar'}">
                                    <input type="hidden" value="${solicitud.getInforme().getId_informe()}" name="id_informe" />
                                    <c:choose>
                                        <c:when test="${tipo == 'sangria' || tipo == 'sangria_prueba'}">
                                            <c:forEach items="${caballos_resultado}" var="resultado_caballo">
                                                <input type="hidden" name="caballos_res_${resultado_caballo.getResultado().getId_resultado()}" value="${resultado_caballo.pasarIdsAString()}" />
                                            </c:forEach>
                                        </c:when>
                                    </c:choose>
                                </c:if>

                                <div class="row">
                                    <c:if test="${solicitud.getEstado() != 'Completada'}">
                                        <div class="col-md-6">
                                            <label for="cerrar" class="control-label">Estado</label>
                                            <div class="form-group">
                                                <div class="col-sm-12">
                                                    <div class="input-group">
                                                        <label class="fancy-checkbox">
                                                            <input type="checkbox" value="true" name="cerrar">
                                                            <span>Cerrar Solicitud</span>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                                <c:choose>
                                    <c:when test="${solicitud.tieneTipoAsociacion()}">
                                        <c:choose>
                                            <c:when test="${solicitud.getTipoAsociacionString() == 'sangria'}">
                                                <t:agregar_informe_sangria derecha="false" />
                                            </c:when>
                                            <c:when test="${solicitud.getTipoAsociacionString() == 'sangria_prueba'}">
                                                <t:agregar_informe_sangria_prueba derecha="false" />
                                            </c:when>
                                        </c:choose>
                                    </c:when >
                                    <c:otherwise>
                                        <input type="hidden" name="objeto-relacionado" value="">
                                    </c:otherwise>
                                </c:choose>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="widget widget-table">
                                            <div class="widget-header">
                                                <h3><i class="fa fa-calendar"></i> Resultados Obtenidos</h3>
                                            </div>

                                            <div class="widget-content">
                                                <table id="resultados-obtenidos" class="table table-sorting table-striped table-hover datatable tablaSigipro">
                                                    <thead> 
                                                        <tr>
                                                            <th class="columna-escondida">Control</th>
                                                            <th>Identificadores de Muestras (Tipo)</th>
                                                            <th>Análisis Solicitado</th>
                                                            <th>Resultado</th>
                                                            <th>Repetición</th>
                                                            <th>Acción</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${solicitud.getResultados()}" var="resultado">
                                                            <c:if test="${!solicitud.getInforme().tieneResultado(resultado.getId_resultado())}">
                                                                <tr id="${resultado.getId_resultado()}" class="fila-resultado">
                                                                    <td class="columna-escondida"><input type="checkbox" name="resultados" value="${resultado.getId_resultado()}"></td>
                                                                    <td>
                                                                        <c:forEach items="${resultado.getAgs().getGrupo().getGrupos_muestras()}" var="muestra">
                                                                            <span>${muestra.getIdentificador()} (${muestra.getTipo_muestra().getNombre()})</span><br>
                                                                        </c:forEach>
                                                                    </td>
                                                                    <td>${resultado.getAgs().getAnalisis().getNombre()}</td>
                                                                    <td>${resultado.getResultado()}</td>
                                                                    <td>${resultado.getRepeticion()}</td>
                                                                    <td>
                                                                        <button type="button" class="btn btn-primary btn-sm boton-accion reportar-resultado">Reportar Resultado</button>
                                                                    </td>
                                                                </tr>
                                                            </c:if>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="widget widget-table">
                                            <div class="widget-header">
                                                <h3><i class="fa fa-calendar"></i> Resultados por Reportar</h3>
                                            </div>
                                            <div class="widget-content">
                                                <table id="resultados-por-reportar" class="table table-sorting table-striped table-hover datatable tablaSigipro">
                                                    <!-- Columnas -->
                                                    <thead> 
                                                        <tr>
                                                            <th class="columna-escondida">Control</th>
                                                            <th>ID Muestras (Tipo)</th>
                                                            <th>Análisis Solicitado</th>
                                                            <th>Resultado</th>
                                                            <th>Repetición</th>
                                                            <th>Acción</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:if test="${accion == 'Editar'}">
                                                            <c:forEach items="${solicitud.getInforme().getResultados()}" var="resultado">

                                                                <tr id="${resultado.getId_resultado()}" class="fila-resultado">
                                                                    <td class="columna-escondida"><input type="checkbox" name="resultados" value="${resultado.getId_resultado()}" checked='checked' /></td>
                                                                    <td class="">
                                                                        <c:forEach items="${resultado.getAgs().getGrupo().getGrupos_muestras()}" var="muestra">
                                                                            <span>${muestra.getIdentificador()} (${muestra.getTipo_muestra().getNombre()})</span><br>
                                                                        </c:forEach>
                                                                    </td>
                                                                    <td>${resultado.getAgs().getAnalisis().getNombre()}</td>
                                                                    <td>${resultado.getResultado()}</td>
                                                                    <td>${resultado.getRepeticion()}</td>
                                                                    <td>
                                                                        <button type="button" class="btn btn-danger btn-sm boton-accion eliminar-resultado">Eliminar de informe</button>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </c:if>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <!-- END WIDGET TICKET TABLE -->
                                <div class="form-group">
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
                                        <c:choose>
                                            <c:when test= "${accion.equals('Editar')}">
                                                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Informe</button>
                                            </c:otherwise>
                                        </c:choose>    
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <!-- /main-content -->
                </div>
                <!-- /main -->
            </div>
        </div>

        <div id="flag-asociacion" data-tipo="${tipo}"></div>

        <div id="caballos-numeros">
            <c:forEach items="${caballos_sangria}" var="caballo">
                <c:choose>
                    <c:when test="${accion == 'Generar'}">
                        <div id="${caballo.getId_caballo()}" data-numero="${caballo.getNumero()}" data-selected="false"></div>
                    </c:when>
                    <c:otherwise>
                        <div id="${caballo.getId_caballo()}" data-numero="${caballo.getNumero()}" data-selected="true"></div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

    </jsp:attribute>

    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/informes.js"></script>
    </jsp:attribute>

</t:plantilla_general>

<t:modal idModal="modal-error" titulo="Error en el Formulario">
    <jsp:attribute name="form">

        <form class="form-horizontal">
            <div class="widget-content">
                <label id="label-error" class="control-label"></label>
            </div>
            <br/>

            <div class="form-group">
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cerrar</button>
                </div>
            </div>
        </form>
    </jsp:attribute>
</t:modal>