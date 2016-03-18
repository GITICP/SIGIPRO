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
                        <li class="active"> ${lote.getNombre()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-flask"></i> ${lote.getNombre()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 660)}">
                                    <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar el lote de producción" data-href="/SIGIPRO/Produccion/Lote?accion=eliminar&id_lote=${lote.getId_lote()}">Eliminar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Nombre:</strong></td> <td>${lote.getNombre()} </td></tr>
                                <tr><td> <strong>Protocolo:</strong></td> <td>${lote.getProtocolo().getNombre()} </td></tr>
                                <tr><td> <strong>Estado:</strong></td> <td>
                                        <c:choose>
                                            <c:when test="${lote.isEstado()}">
                                                Completo
                                            </c:when>
                                            <c:otherwise>
                                                Pendiente
                                            </c:otherwise>
                                        </c:choose>
                                    </td></tr>
                            </table>
                            <br>
                            <div class="col-sm-12">
                                <div class="widget widget-table">
                                    <div class="widget-header">
                                        <h3><i class="fa fa-flask"></i> Pasos del Lote de Producción </h3>
                                    </div>
                                    <div class="widget-content">
                                        <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                            <!-- Columnas -->
                                            <thead> 
                                                <tr>
                                                    <th>Posición</th>
                                                    <th>Paso</th>
                                                    <th>Respuesta</th>
                                                    <th>Versión</th>
                                                    <th>Usuario Realizar</th>
                                                    <th>Aprobación</th>
                                                    <th>Usuario Aprobar</th>
                                                    <th>Acción</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${lote.getRespuestas()}" var="respuesta">

                                                    <tr id ="${respuesta.getId_respuesta()}">
                                                        <td>${respuesta.getPaso().getPosicion()}</td>
                                                        <td>
                                                            <a href="/SIGIPRO/Produccion/Paso?accion=ver&id_paso=${respuesta.getPaso().getId_paso()}">
                                                                <div style="height:100%;width:100%">
                                                                    Paso ${respuesta.getPaso().getNombre()}
                                                                </div>
                                                            </a>                                                                
                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${respuesta.getId_respuesta() != 0}">
                                                                    <a href="/SIGIPRO/Produccion/Lote?accion=verrespuesta&id_respuesta=${respuesta.getId_respuesta()}">
                                                                        <div style="height:100%;width:100%">
                                                                            Ver Respuesta
                                                                        </div>
                                                                    </a>   
                                                                </c:when>
                                                                <c:otherwise>
                                                                    Pendiente
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </td>
                                                        <td>
                                                            ${respuesta.getVersion()}
                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${respuesta.getUsuario_realizar().getId_usuario() != null}">
                                                                    ${respuesta.getUsuario_realizar().getNombre_completo()}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    Pendiente
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${respuesta.getPaso().isRequiere_ap()}">
                                                                    Sí
                                                                </c:when>
                                                                <c:otherwise>
                                                                    No
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${respuesta.getUsuario_verificar().getId_usuario() != 0}">
                                                                    ${respuesta.getUsuario_verificar().getNombre_completo()}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:choose>
                                                                        <c:when test="${respuesta.getPaso().isRequiere_ap()}">
                                                                            Pendiente
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            No requiere
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${respuesta.getEstado()==1}">

                                                                </c:when>
                                                                <c:when test="${respuesta.getEstado()==2}">
                                                                    <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${lote.getId_lote()}' data-respuesta='${lote.getId_respuesta_actual()}' data-posicion="${lote.getPosicion_actual()}" data-toggle="modal" data-target="#modalAprobarPaso">Aprobar</a>
                                                                </c:when>
                                                                <c:when test="${respuesta.getEstado()==3}">

                                                                </c:when>
                                                                <c:when test="${respuesta.getEstado()==4}">
                                                                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Lote?accion=realizar&id_respuesta=${respuesta.getId_respuesta()}">Realizar</a>
                                                                </c:when>
                                                                <c:when test="${respuesta.getEstado()==5}">
                                                                    <a class="btn btn-warning btn-sm boton-accion " href="/SIGIPRO/Produccion/Lote?accion=completar&id_respuesta=${respuesta.getId_respuesta()}">Completar</a>
                                                                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Lote?accion=repetir&id_respuesta=${respuesta.getId_respuesta()}">Repetir</a>
                                                                    <a class="btn btn-primary btn-sm boton-accion revisar-Modal" data-id='${respuesta.getLote().getId_lote()}' data-respuesta='${respuesta.getId_respuesta()}' data-posicion="${respuesta.getPaso().getPosicion()}" data-toggle="modal" data-target="#modalRevisarPaso">Revisar</a>
                                                                </c:when>
                                                                <c:when test="${respuesta.getEstado()==6}">
                                                                    <a class="btn btn-primary btn-sm boton-accion verificar-Modal" data-id='${respuesta.getLote().getId_lote()}' data-respuesta='${respuesta.getId_respuesta()}' data-posicion="${respuesta.getPaso().getPosicion()}" data-toggle="modal" data-target="#modalVerificarPaso">Verificar</a>
                                                                </c:when>
                                                                <c:when test="${respuesta.getEstado()==7}">
                                                                    Completado
                                                                </c:when>
                                                            </c:choose>
                                                        </td>

                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
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
    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/LoteProduccion.js"></script>
    </jsp:attribute>

</t:plantilla_general>

<t:modal idModal="modalAprobarPaso" titulo="Aprobar Paso de Protocolo de Producción">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-aprobar-paso">
            <form class="form-horizontal" id="aprobarPaso" autocomplete="off" method="post" action="Lote">
                <input hidden="true" name="accion" value="Aprobar">
                <input hidden="true" id='id_lote' name='id_lote' value="">
                <input hidden="true" id='id_respuesta_actual' name='id_respuesta_actual' value="">
                <input hidden="true" id='posicion_actual' name='posicion_actual' value="">
                <label for="label" class="control-label">¿Está seguro que desea aprobar el Paso realizado?</label>
                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Aprobar Paso</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>
</t:modal>

<t:modal idModal="modalRevisarPaso" titulo="Revisar Paso de Protocolo de Producción">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-revisar-paso">
            <form class="form-horizontal" id="revisarPaso" autocomplete="off" method="post" action="Lote">
                <input hidden="true" name="accion" value="Revisar">
                <input hidden="true" id='id_lote' name='id_lote' value="">
                <input hidden="true" id='id_respuesta_actual' name='id_respuesta_actual' value="">
                <input hidden="true" id='posicion_actual' name='posicion_actual' value="">
                <label for="label" class="control-label">¿Está seguro que desea aprobar la revisión del paso realizado?</label>
                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Revisar Paso</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>
</t:modal>

<t:modal idModal="modalVerificarPaso" titulo="Verificar Paso de Protocolo de Producción">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-verificar-paso">
            <form class="form-horizontal" id="verificarPaso" autocomplete="off" method="post" action="Lote">
                <input hidden="true" name="accion" value="Verificar">
                <input hidden="true" id='id_lote' name='id_lote' value="">
                <input hidden="true" id='id_respuesta_actual' name='id_respuesta_actual' value="">
                <input hidden="true" id='posicion_actual' name='posicion_actual' value="">
                <label for="label" class="control-label">¿Está seguro que desea aprobar la verificación del paso realizado?</label>
                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Verificar Paso</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>
</t:modal>