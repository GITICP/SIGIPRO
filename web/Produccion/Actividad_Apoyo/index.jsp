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
                        <li class="active"> 
                            Actividades de Apoyo
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
                            <h3><i class="fa fa-gears"></i> Actividades de Apoyo </h3>
                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 670)}">
                                <div class="btn-group widget-header-toolbar">
                                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=agregar">Agregar Actividad de Apoyo</a>
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
                                        <th>Versión</th>
                                        <th>Categoría</th>
                                        <th>Aprobaciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaActividades}" var="actividad">

                                        <tr id ="${actividad.getId_actividad()}">
                                            <td>
                                                <a href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=ver&id_actividad=${actividad.getId_actividad()}">
                                                    <div style="height:100%;width:100%">
                                                        ${actividad.getNombre()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>
                                                ${actividad.getVersion()}
                                            </td>
                                            <td>
                                                ${actividad.getCategoria().getNombre()}
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${!actividad.isAprobacion_calidad() && !actividad.isAprobacion_regente() && !actividad.isAprobacion_coordinador()}">
                                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 672)}">
                                                            <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${actividad.getId_actividad()}' data-actor='1' data-toggle="modal" data-target="#modalAprobarActividad">[Calidad] Aprobar</a>
                                                            <a class="btn btn-danger btn-sm boton-accion rechazar-Modal" data-id='${actividad.getId_actividad()}' data-actor='Calidad' data-toggle="modal" data-target="#modalRechazarActividad">[Calidad] Rechazar</a>
                                                        </c:if>
                                                    </c:when>
                                                    <c:when test="${actividad.isAprobacion_calidad() && (!actividad.isAprobacion_regente() || !actividad.isAprobacion_coordinador()) && !actividad.isAprobacion_direccion()}">
                                                        <c:if test="${!actividad.isAprobacion_regente()}">
                                                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 673)}">
                                                                <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${actividad.getId_actividad()}' data-actor='2' data-toggle="modal" data-target="#modalAprobarActividad">[Regente] Aprobar</a>
                                                                <a class="btn btn-danger btn-sm boton-accion rechazar-Modal" data-id='${actividad.getId_actividad()}' data-actor='Regente' data-toggle="modal" data-target="#modalRechazarActividad">[Regente] Rechazar</a>
                                                            </c:if>
                                                        </c:if>
                                                        <c:if test="${!actividad.isAprobacion_coordinador()}">
                                                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 674)}">
                                                                <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${actividad.getId_actividad()}' data-actor='3' data-toggle="modal" data-target="#modalAprobarActividad">[Coordinador] Aprobar</a>
                                                                <a class="btn btn-danger btn-sm boton-accion rechazar-Modal" data-id='${actividad.getId_actividad()}' data-actor='Coordinador' data-toggle="modal" data-target="#modalRechazarActividad">[Coordinador] Rechazar</a>
                                                            </c:if>
                                                        </c:if>
                                                    </c:when>
                                                    <c:when test="${actividad.isAprobacion_regente() && actividad.isAprobacion_coordinador() && !actividad.isAprobacion_direccion()}">
                                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 675)}">
                                                            <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${actividad.getId_actividad()}' data-actor='4' data-toggle="modal" data-target="#modalAprobarActividad">[Director] Aprobar</a>
                                                            <a class="btn btn-danger btn-sm boton-accion rechazar-Modal" data-id='${actividad.getId_actividad()}' data-actor='Director' data-toggle="modal" data-target="#modalRechazarActividad">[Director] Rechazar</a>
                                                        </c:if>
                                                    </c:when>
                                                    <c:when test="${actividad.isAprobacion_direccion() && !actividad.isAprobacion_gestion()}">
                                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 680)}">
                                                            <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${actividad.getId_actividad()}' data-actor='5' data-toggle="modal" data-target="#modalAprobarActividad">[Gestión] Aprobar</a>
                                                            <a class="btn btn-danger btn-sm boton-accion rechazar-Modal" data-id='${actividad.getId_actividad()}' data-actor='Gestión de Calidad' data-toggle="modal" data-target="#modalRechazarActividad">[Gestión] Rechazar</a>
                                                        </c:if>
                                                    </c:when>                                                            

                                                    <c:otherwise>
                                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 677)}">
                                                            <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=realizar&id_actividad=${actividad.getId_actividad()}">Realizar</a>
                                                        </c:if>
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
        <script src="/SIGIPRO/recursos/js/sigipro/Produccion/ActividadApoyo/ActividadApoyo.js"></script>    
    </jsp:attribute>

</t:plantilla_general>

<t:modal idModal="modalAprobarActividad" titulo="Aprobar Actividad de Apoyo">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-aprobar-actividad">
            <form class="form-horizontal" id="aprobarActividad" autocomplete="off" method="post" action="Actividad_Apoyo">
                <input hidden="true" name="accion" value="Aprobar">
                <input hidden="true" id='id_actividad' name='id_actividad' value="">
                <input hidden="true" id='actor' name='actor' value="">
                <label for="label" class="control-label">¿Está seguro que desea aprobar la Actividad de Apoyo?</label>
                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Aprobar Actividad</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal>

<t:modal idModal="modalRechazarActividad" titulo="Rechazar Actividad de Apoyo">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-rechazar-actividad">
            <form class="form-horizontal" id="rechazarActividad" autocomplete="off" method="post" action="Actividad_Apoyo">
                <input hidden="true" name="accion" value="Rechazar">
                <input hidden="true" id='id_actividad' name='id_actividad' value="">
                <input hidden="true" id='actor' name='actor' value="">
                <label for="observaciones" class="control-label">¿Razones por las cuáles rechaza la actividad?</label>
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
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Rechazar Actividad</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal> 