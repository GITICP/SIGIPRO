<%-- 
    Document   : index
    Created on : Jun 29, 2015, 4:39:43 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Lotes de Producción </h3>
                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 660)}">
                                <div class="btn-group widget-header-toolbar">
                                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Lote?accion=historial">Historial</a>
                                </div>
                            </c:if>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Nombre</th>
                                        <th>Protocolo</th>
                                        <th>Paso</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaLotes}" var="lote">

                                        <tr id ="${lote.getId_lote()}">
                                            <td>
                                                <a href="/SIGIPRO/Produccion/Lote?accion=ver&id_lote=${lote.getId_lote()}">
                                                    <div style="height:100%;width:100%">
                                                        ${lote.getNombre()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>
                                                ${lote.getProtocolo().getNombre()}
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${lote.isAprobacion()}">
                                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 662)}">
                                                            <div class="btn-group widget-header-toolbar">
                                                                <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${lote.getId_lote()}' data-respuesta='${lote.getId_respuesta_actual()}' data-posicion="${lote.getPosicion_actual()}" data-toggle="modal" data-target="#modalAprobarPaso">Aprobar</a>
                                                            </div>
                                                        </c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Lote?accion=realizar&id_lote=${lote.getId_lote()}">${lote.getPosicion_actual()} - ${lote.getPaso_actual().getNombre()}</a>
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