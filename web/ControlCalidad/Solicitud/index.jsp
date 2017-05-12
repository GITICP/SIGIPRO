<%-- 
    Document   : index
    Created on : Jul 9, 2015, 1:36:00 PM
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
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Control de Calidad</li>
                        <li> 
                            <a href="/SIGIPRO/ControlCalidad/Solicitud?">Solicitudes de Control de Calidad</a>
                        </li>
                    </ul>
                </div>
                <div class="col-md-8 ">
                    <div class="top-content">

                    </div>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-list-alt"></i> Solicitudes de Control de Calidad </h3>
                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 550)}">
                                <div class="btn-group widget-header-toolbar">
                                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/ControlCalidad/Solicitud?accion=historial">Historial</a>
                                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/ControlCalidad/Solicitud?accion=agregar">Agregar Solicitud</a>
                                </div>
                            </c:if>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-desc-filter">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th class="columna-escondida">ID</th>
                                        <th>Número de Solicitud</th>
                                        <th>Información</th>
                                        <th>Tipo de Muestra</th>
                                        <th>Usuario Solicitante</th>
                                        <th>Fecha de Solicitud</th>
                                        <th>Estado</th>
                                            <c:if test="${boolrecibir || boolanular}">
                                            <th>Acción</th>
                                            </c:if>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaSolicitudes}" var="solicitud">

                                        <tr id ="${solicitud.getId_solicitud()}">
                                            <td hidden="true">${solicitud.getId_solicitud()}</td>
                                            <td>
                                                <a href="/SIGIPRO/ControlCalidad/Solicitud?accion=ver&id_solicitud=${solicitud.getId_solicitud()}">
                                                    <div style="height:100%;width:100%">
                                                        ${solicitud.getNumero_solicitud()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>${solicitud.getDescripcion()}</td>
                                            <td>${solicitud.getControl_solicitud().getTiposMuestrasHTML()}</td>
                                            <td>${solicitud.getUsuario_solicitante().getNombre_completo()}</td>
                                            <td>${solicitud.getFecha_solicitudAsString()}</td>
                                            <td>${solicitud.getEstado()}</td>
                                            <c:if test="${boolrecibir || boolanular}">
                                                <c:choose>
                                                    <c:when test="${solicitud.getEstado().equals('Solicitado')}">
                                                        <td>
                                                            <c:if test="${boolrecibir}">
                                                                <a class="btn btn-primary btn-sm boton-accion recibir-Modal" data-id='${solicitud.getId_solicitud()}' data-toggle="modal" data-target="#modalRecibirSolicitud">Recibir</a>
                                                            </c:if>
                                                            <c:if test="${boolanular}">
                                                                <a class="btn btn-danger btn-sm boton-accion anular-Modal" data-id='${solicitud.getId_solicitud()}' data-toggle="modal" data-target="#modalAnularSolicitud">Anular</a>
                                                            </c:if>
                                                        </td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${solicitud.getEstado().equals('Recibido') || solicitud.getEstado().equals('Resultado Parcial')}">
                                                                <c:choose>
                                                                    <c:when test="${boolrealizar}">
                                                                        <td>
                                                                            <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/ControlCalidad/Solicitud?accion=ver&id_solicitud=${solicitud.getId_solicitud()}">Realizar</a>  
                                                                        </td>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <td>
                                                                            <button class="btn btn-danger btn-sm boton-accion" disabled >Solicitud Recibida</button>
                                                                        </td>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td>
                                                                    <button class="btn btn-danger btn-sm boton-accion" disabled >Solicitud Completada</button>
                                                                </td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
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
        <script src="/SIGIPRO/recursos/js/sigipro/SolicitudCC-v2.js"></script>
    </jsp:attribute>

</t:plantilla_general>

<t:modal idModal="modalAnularSolicitud" titulo="Anular Solicitud">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="anularSolicitud" autocomplete="off" method="post" action="Solicitud">
                <input hidden="true" name="accion" value="Anular">
                <input hidden="true" id='id_solicitud_anular' name='id_solicitud_anular' value="">

                <h4> Información sobre la solicitud </h4>
                <table class="tabla-modal">
                    <tr>
                        <td><strong>Número. Solicitud</strong></td><td id="modal_anular_num_solicitud"></td>
                    </tr>
                    <tr>
                        <td><strong>Usuario Solicitante</strong></td><td id="modal_anular_solicitante"></td>
                    </tr>
                    <tr>
                        <td><strong>Tipos de Muestras</strong></td><td id="modal_anular_tipos_muestras"></td>
                    </tr>
                    <tr>
                        <td><strong>Información</strong></td><td id="modal_anular_informacion"></td>
                    </tr>
                </table>
                
                <br>

                <label for="observaciones" class="control-label">¿Razones por las cuales anula la solicitud?</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <textarea rows="5" cols="50" maxlength="500" placeholder="Observaciones" class="form-control" name="observaciones" ></textarea>
                        </div>
                    </div>
                </div>


                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Anular Solicitud</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal>

<t:modal idModal="modalRecibirSolicitud" titulo="Recibir Solicitud">

    <jsp:attribute name="form">
        <form class="form-horizontal" id="form_modalautorizar" method="post" data-show-auth="${show_modal_auth}" action="Solicitud">
            ${mensaje_auth}
            <h4> Información sobre la solicitud </h4>
            <table class="tabla-modal">
                <tr>
                    <td><strong>Número. Solicitud</strong></td><td id="modal_num_solicitud"></td>
                </tr>
                <tr>
                    <td><strong>Usuario Solicitante</strong></td><td id="modal_solicitante"></td>
                </tr>
                <tr>
                    <td><strong>Tipos de Muestras</strong></td><td id="modal_tipos_muestras"></td>
                </tr>
                <tr>
                    <td><strong>Información</strong></td><td id="modal_informacion"></td>
                </tr>
            </table>

            <br><br>
            <h5>Para validar la recepción, el usuario que recibe la solicitud debe iniciar sesión. </h5>

            <input hidden="true" name="id_solicitud_recibir" id="id_solicitud_recibir">
            <input hidden="true" name="accion" id="accion" value="Recibir">

            <label for="usr" class="control-label">Usuario</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group" style="display:table;">
                        <input class="form-control" type="text" id="usr"  name="usuario_recibo" required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               onchange="setCustomValidity('')">
                    </div>
                </div>
            </div>
            <label for="passw" class="control-label">Contraseña</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group" style="display:table;">
                        <input class="form-control" type="password" id="passw" name="passw" required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               onchange="setCustomValidity('')">
                    </div>
                    <p id='mensajeValidación' style='color:red;'><p>
                </div>
            </div>

            <div class="form-group">
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Recibir Solicitud</button>
                </div>
            </div>
        </form>


    </jsp:attribute>

</t:modal>