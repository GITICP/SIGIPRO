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
                            <a href="/SIGIPRO/Produccion/Protocolo?">Protocolo de Producción</a>
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
                            <h3><i class="fa fa-gears"></i> Protocolo de Producción </h3>
                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 635)}">
                                <div class="btn-group widget-header-toolbar">
                                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Protocolo?accion=agregar">Agregar Protocolo de Producción</a>
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
                                        <th>Descripción</th>
                                        <th>Producto Terminado</th>
                                        <th>Versión</th>
                                        <th>Aprobaciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaProtocolos}" var="protocolo">

                                        <tr id ="${protocolo.getId_protocolo()}">
                                            <td>
                                                <a href="/SIGIPRO/Produccion/Protocolo?accion=ver&id_protocolo=${protocolo.getId_protocolo()}">
                                                    <div style="height:100%;width:100%">
                                                        ${protocolo.getNombre()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>
                                                ${protocolo.getDescripcion()}
                                            </td>
                                            <td>
                                                ${protocolo.getProducto().getNombre()}
                                            </td>
                                            <td>
                                                ${protocolo.getVersion()}
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${protocolo.getAprobacion_calidad()}">
                                                        <c:choose>
                                                            <c:when test="${protocolo.getAprobacion_regente() && protocolo.getAprobacion_coordinador()}">
                                                                <c:choose>
                                                                    <c:when test="${!protocolo.getAprobacion_direccion()}">
                                                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 644)}">
                                                                            <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${protocolo.getId_protocolo()}' data-actor='4' data-toggle="modal" data-target="#modalAprobarProtocolo">[Director] Aprobar</a>
                                                                            <a class="btn btn-danger btn-sm boton-accion rechazar-Modal" data-id='${protocolo.getId_protocolo()}' data-actor='Director' data-toggle="modal" data-target="#modalRechazarProtocolo">[Director] Rechazar</a>
                                                                        </c:if>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <a class="btn btn-primary btn-sm boton-accion lote-Modal" data-id='${protocolo.getId_protocolo()}' data-toggle="modal" data-target="#modalComenzarLote">Iniciar Nuevo Lote</a>

                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:if test="${!protocolo.getAprobacion_regente()}">
                                                                    <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${protocolo.getId_protocolo()}' data-actor='2' data-toggle="modal" data-target="#modalAprobarProtocolo">[Regente] Aprobar</a>
                                                                    <a class="btn btn-danger btn-sm boton-accion rechazar-Modal" data-id='${protocolo.getId_protocolo()}' data-actor='Regente' data-toggle="modal" data-target="#modalRechazarProtocolo">[Regente] Rechazar</a>
                                                                </c:if>
                                                                <c:if test="${!protocolo.getAprobacion_coordinador()}">
                                                                    <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${protocolo.getId_protocolo()}' data-actor='3' data-toggle="modal" data-target="#modalAprobarProtocolo">[Coordinador] Aprobar</a>
                                                                    <a class="btn btn-danger btn-sm boton-accion rechazar-Modal" data-id='${protocolo.getId_protocolo()}' data-actor='Coordinador' data-toggle="modal" data-target="#modalRechazarProtocolo">[Coordinador] Rechazar</a>
                                                                </c:if>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${protocolo.getId_protocolo()}' data-actor='1' data-toggle="modal" data-target="#modalAprobarProtocolo">[Calidad] Aprobar</a>
                                                        <a class="btn btn-danger btn-sm boton-accion rechazar-Modal" data-id='${protocolo.getId_protocolo()}' data-actor='Calidad' data-toggle="modal" data-target="#modalRechazarProtocolo">[Calidad] Rechazar</a>
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
        <script src="/SIGIPRO/recursos/js/sigipro/Protocolo.js"></script>
    </jsp:attribute>

</t:plantilla_general>

<t:modal idModal="modalAprobarProtocolo" titulo="Aprobar Protocolo de Producción">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-aprobar-protocolo">
            <form class="form-horizontal" id="aprobarProtocolo" autocomplete="off" method="get" action="Protocolo">
                <input hidden="true" name="accion" value="Aprobar">
                <input hidden="true" id='id_protocolo' name='id_protocolo' value="">
                <input hidden="true" id='actor' name='actor' value="">
                <label for="label" class="control-label">¿Está seguro que desea aprobar el Protocolo de Producción?</label>
                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Aprobar Protocolo</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal>

<t:modal idModal="modalRechazarProtocolo" titulo="Rechazar Protocolo de Producción">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-rechazar-protocolo">
            <form class="form-horizontal" id="rechazarProtocolo" autocomplete="off" method="post" action="Protocolo">
                <input hidden="true" name="accion" value="Rechazar">
                <input hidden="true" id='id_protocolo' name='id_protocolo' value="">
                <input hidden="true" id='actor' name='actor' value="">
                <label for="observaciones" class="control-label">¿Razones por las cuáles rechaza el protocolo?</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <textarea rows="5" cols="50" maxlength="190" placeholder="Observaciones" class="form-control" name="observaciones" ></textarea>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Rechazar Protocolo</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal>
        
<t:modal idModal="modalComenzarLote" titulo="Iniciar Nuevo Lote de Producción">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-lote-protocolo">
            <form class="form-horizontal" id="loteProtocolo" autocomplete="off" method="post" action="Lote">
                <input hidden="true" name="accion" value="Agregar">
                <input hidden="true" id='id_protocolo' name='id_protocolo' value="">
                <label for="observaciones" class="control-label"> *Nombre/Identificador de Lote de Producción</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input type="text" maxlength="45" placeholder="Nombre/Identificador" class="form-control" name="nombre"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido')"
                               oninput="setCustomValidity('')" >
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Iniciar Lote</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal>        