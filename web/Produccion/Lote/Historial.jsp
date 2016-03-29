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
                        <li class="active">
                            Historial de Lotes de Producci&#243;n
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
                            <h3><i class="fa fa-gears"></i> Historial de Lotes de Producción </h3>
                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 660)}">
                                <div class="btn-group widget-header-toolbar">
                                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Lote">Inicio</a>
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
                                        <th>Acción</th>
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
                                                    <c:when test="${lote.getFecha_vencimiento()==null}">
                                                        <a class="btn btn-primary btn-sm boton-accion vencimiento-Modal" data-id='${lote.getId_lote()}' data-toggle="modal" data-target="#modalVencimientoLote">Fecha de Vencimiento</a>
                                                    </c:when>
                                                    <c:when test="${lote.getUsuario_distribucion().getId_usuario()==0}">
                                                        <a class="btn btn-primary btn-sm boton-accion distribucion-Modal" data-id='${lote.getId_lote()}' data-toggle="modal" data-target="#modalDistribucionLote">Distribución</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a class="btn btn-warning btn-sm boton-accion" disabled>Distribuido</a>
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

<t:modal idModal="modalDistribucionLote" titulo="Aprobar Distribución del Lote de Producción">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-distribucion-lote">
            <form class="form-horizontal" id="distribuirLote" autocomplete="off" method="post" action="Lote">
                <input hidden="true" name="accion" value="distribucion">
                <input hidden="true" id='id_lote' name='id_lote' value="">
                <label for="label" class="control-label">¿Está seguro que desea aprobar la distribución del Lote de Producción?</label>
                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Aprobar Distribución</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>
</t:modal>

<t:modal idModal="modalVencimientoLote" titulo="Registrar Fecha de Vencimiento al Lote de Producción">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-vencimiento-lote">
            <form class="form-horizontal" id="registrarVencimiento" autocomplete="off" method="post" action="Lote">
                <input hidden="true" name="accion" value="vencimiento">
                <input hidden="true" id='id_lote' name='id_lote' value="">
                <label for="fecha_vencimiento" class="control-label">*Fecha de Vencimiento</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" class="form-control sigiproDatePicker" name="fecha_vencimiento" data-date-format="dd/mm/yyyy" required
                                   oninvalid="setCustomValidity('Este campo es requerido y no pueden ser fechas futuras. ')"
                                   onchange="setCustomValidity('')">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Registrar Fecha de Vencimiento</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>
</t:modal>