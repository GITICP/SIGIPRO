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
                            <c:set var="contienePermiso" value="false" />
                            <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                <c:if test="${permiso == 1 || permiso == 550}">
                                    <c:set var="contienePermiso" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:if test="${contienePermiso}">
                                <div class="btn-group widget-header-toolbar">
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
                                        <th>Número de Solicitud</th>
                                        <th>Usuario Solicitante</th>
                                        <th>Fecha de Solicitud</th>
                                        <th>Usuario Receptor</th>
                                        <th>Fecha de Recepción</th>
                                        <th>Estado</th>
                                        <th>Acción</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaSolicitudes}" var="solicitud">

                                        <tr id ="${solicitud.getId_solicitud()}">
                                            <td>
                                                <a href="/SIGIPRO/ControlCalidad/Solicitud?accion=ver&id_solicitud=${solicitud.getId_solicitud()}">
                                                    <div style="height:100%;width:100%">
                                                        ${solicitud.getNumero_solicitud()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>${solicitud.getUsuario_solicitante().getNombre_completo()}</td>
                                            <td>${solicitud.getFecha_solicitudAsString()}</td>
                                            <c:choose>
                                                <c:when test="${solicitud.getUsuario()!=null}">
                                                    <td>${solicitud.getUsuario_recibido().getNombre_completo()}</td>
                                                    <td>${solicitud.getFecha_recibidoAsString()}</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td></td>
                                                    <td></td>
                                                </c:otherwise>
                                            </c:choose>
                                            <td>${solicitud.getEstado()}</td>
                                            <c:choose>
                                                <c:when test="${solicitud.getEstado().equals('Solicitado')}">
                                                    <c:if test="${boolrecibir}">
                                                        <td>
                                                            <a class="btn btn-primary btn-sm boton-accion recibir-Modal" data-id='${solicitud.getId_solicitud()}' data-toggle="modal" data-target="#modalRecibirSolicitud">Recibir</a>
                                                            <a class="btn btn-warning btn-sm boton-accion confirmable" data-texto-confirmacion="anular esta solicitud" data-href="/SIGIPRO/ControlCalidad/Solicitud?accion=anular&id_solicitud=${solicitud.getId_solicitud()}">Anular</a>
                                                        </td>
                                                    </c:if>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${solicitud.getEstado().equals('Recibido')}">
                                                            <c:if test="${boolrealizar}">
                                                                <td>
                                                                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/ControlCalidad/Solicitud?accion=ver&id_solicitud=${solicitud.getId_solicitud()}">Realizar</a>  
                                                                </td>
                                                            </c:if>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td>
                                                                <button class="btn btn-danger btn-sm boton-accion" disabled >Solicitud Completada</button>
                                                            </td>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
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

            <t:modal idModal="modalRechazarSolicitud" titulo="Rechazar Solicitud">
                <jsp:attribute name="form">
                    <div class="widget-content">
                        <form class="form-horizontal" id="rechazarSolicitud" autocomplete="off" method="post" action="SolicitudVeneno">
                            <input hidden="true" name="accion" value="Rechazar">
                            <input hidden="true" id='id_solicitud_rechazar' name='id_solicitud_rechazar' value="">
                            <label for="observaciones" class="control-label">¿Razones por las cuales rechaza la solicitud?</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <textarea rows="5" cols="50" maxlength="200" placeholder="Observaciones" class="form-control" name="observaciones" ></textarea>
                                    </div>
                                </div>
                            </div>


                            <div class="form-group">
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Rechazar Solicitud</button>            </div>
                            </div>
                        </form>
                    </div>

                </jsp:attribute>

            </t:modal>


        </jsp:attribute>
        <jsp:attribute name="scripts">
            <script src="/SIGIPRO/recursos/js/sigipro/SolicitudSerpentario.js"></script>
        </jsp:attribute>

    </t:plantilla_general>

    <t:modal idModal="modalAnularSolicitud" titulo="Anular Solicitud">
        <jsp:attribute name="form">
            <div class="widget-content">
                <form class="form-horizontal" id="rechazarSolicitud" autocomplete="off" method="post" action="SolicitudVeneno">
                    <input hidden="true" name="accion" value="Anular">
                    <input hidden="true" id='id_solicitud_anular' name='id_solicitud_anular' value="">
                    <label for="observaciones" class="control-label">¿Razones por las cuales anula la solicitud?</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <textarea rows="5" cols="50" maxlength="200" placeholder="Observaciones" class="form-control" name="observaciones" ></textarea>
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

    <t:modal idModal="modalAgregarSolicitud" titulo="Agregar Solicitud de Veneno">
        <jsp:attribute name="form">
            <div class="widget-content">
                <form class="form-horizontal" id="agregarSolicitud" autocomplete="off" method="get" action="SolicitudVeneno">
                    <input hidden="true" name="accion" value="Agregar">
                    <label for="veneno" class="control-label">*Primero, elija el Tipo de Veneno que va a solicitar.</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <br>
                                <select id="seleccionEspecie" class="select2" name="id_veneno"
                                        style='background-color: #fff;' required
                                        oninvalid="setCustomValidity('Este campo es requerido')"
                                        onchange="setCustomValidity('')">
                                    <option value=''></option>
                                    <c:forEach items="${venenos}" var="veneno">
                                        <option value=${veneno.getId_veneno()}>${veneno.getEspecie().getGenero_especie()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Ir a Agregar Solicitud</button>            </div>
                    </div>
                </form>
            </div>

        </jsp:attribute>

    </t:modal>

    <t:modal idModal="modalEntregar" titulo="Entregar Solicitud">

        <jsp:attribute name="form">
            <form class="form-horizontal" id="form_modalautorizar" method="post" data-show-auth="${show_modal_auth}" action="SolicitudVeneno">
                ${mensaje_auth}
                <h4> Información sobre la solicitud </h4>
                <div class="form-group">
                    <div class="col-sm-12">
                        <label for="num-sol"> Número de Solicitud </label>  
                        <input type="text"  class='form-control' id="num-sol"  name="num-sol" disabled>
                    </div>
                    <div class="col-sm-12">
                        <label for="usr-sol"> Usuario Solicitante </label>
                        <input type="text"  class='form-control' id="usr-sol"  name="usr-sol" disabled>
                    </div>
                    <div class="col-sm-12">
                        <label for="prd"> Especie  </label>
                        <input type="text"  class='form-control' id="esp"  name="esp" disabled>
                    </div>
                    <div class="col-sm-12">
                        <label for="cnt"> Cantidad Solicitada (mg) </label>
                        <input type="text"  class='form-control' id="cnt"  name="cnt" disabled>
                    </div>

                    <div class="col-sm-12">
                        <label for="cnt"> Cantidad a Entregar (mg) </label>
                        <input type="text"  class='form-control' id="cntEnt"  name="cnt" disabled>
                    </div>
                </div>
                <hr>
                <div><br></div>
                <h5>Para validar la entrega, el usuario recipiente debe iniciar sesión. </h5>

                <input hidden="true" name="id_solicitud_entregar" id="id_solicitud_entregar">
                <input hidden="true" name="accion" id="accion" value="Entregar">
                <input hidden="true" name="lotes" id="lotes">
                <input hidden="true" name="cantidad_entregada" id="cantidad_entregada">
                ${mensaje_auth}

                <label for="usr" class="control-label">Usuario</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group" style="display:table;">
                            <input type="text" id="usr"  name="usuario_recibo" required
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
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Aceptar</button>
                    </div>
                </div>
            </form>


        </jsp:attribute>

    </t:modal>