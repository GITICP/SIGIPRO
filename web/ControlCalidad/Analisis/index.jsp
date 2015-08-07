<%-- 
    Document   : index
    Created on : Jul 2, 2015, 8:43:50 PM
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
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Análisis </h3>
                            <c:if test="${helper_permisos.validarPermiso(listaPermisos, 540)}">
                                <div class="btn-group widget-header-toolbar">
                                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/ControlCalidad/Analisis?accion=agregar">Agregar Análisis</a>
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
                                        <th>Estado</th>
                                        <th>Análisis Pendientes</th>
                                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 541) || helper_permisos.validarPermiso(sessionScope.listaPermisos, 544)}">
                                            <th>Acción</th>
                                            </c:if>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaAnalisis}" var="analisis">
                                        <tr id ="${analisis.getId_analisis()}">
                                            <td>
                                                <a href="/SIGIPRO/ControlCalidad/Analisis?accion=ver&id_analisis=${analisis.getId_analisis()}">
                                                    <div style="height:100%;width:100%">
                                                        ${analisis.getNombre()}
                                                    </div>
                                                </a>
                                            </td>
                                            <c:choose>
                                                <c:when test="${analisis.isAprobado()}">
                                                    <td>Aprobado</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>Pendiente</td>
                                                </c:otherwise>
                                            </c:choose>
                                            <td>${analisis.getCantidad_pendiente()}</td>
                                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 541) || helper_permisos.validarPermiso(listaPermisos, 544)}">
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${analisis.isAprobado()}">
                                                            <c:choose>
                                                                <c:when test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 541)}">
                                                                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/ControlCalidad/Analisis?accion=lista&id_analisis=${analisis.getId_analisis()}">Realizar</a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <button class="btn btn-danger btn-sm boton-accion" disabled >Análisis Aprobado</button>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:choose>
                                                                <c:when test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 544)}">
                                                                    <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${analisis.getId_analisis()}' data-toggle="modal" data-target="#modalAprobarAnalisis">Aprobar</a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <button class="btn btn-danger btn-sm boton-accion" disabled >Sin acción.</button>
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </c:if>
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

<t:modal idModal="modalAprobarAnalisis" titulo="Aprobar Análisis">

    <jsp:attribute name="form">
        <form class="form-horizontal" id="form_modalautorizar" method="post" data-show-auth="${show_modal_auth}" action="Analisis">
            ${mensaje_auth}
            <h4> Información sobre el análisis </h4>

            <h5>Para validar la aprobación, el usuario que recibe la solicitud debe iniciar sesión. </h5>

            <input hidden="true" name="id_analisis_aprobar" id="id_analisis_aprobar">
            <input hidden="true" name="accion" id="accion" value="Aprobar">

            <label for="usr" class="control-label">Usuario</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group" style="display:table;">
                        <input type="text" id="usr"  name="usuario_aprobacion" required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               onchange="setCustomValidity('')">
                    </div>
                </div>
            </div>
            <label for="passw" class="control-label">Contraseña</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group" style="display:table;">
                        <input type="password" id="passw" name="passw" required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               onchange="setCustomValidity('')">
                    </div>
                    <p id='mensajeValidación' style='color:red;'><p>
                </div>
            </div>

            <div class="form-group">
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Aprobar Análisis</button>
                </div>
            </div>
        </form>


    </jsp:attribute>

</t:modal>