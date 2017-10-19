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

        <c:forEach items="${solicitud.getAnalisis_solicitud()}" var="ags">
            <c:if test="${!ags.isRealizar()}">
                <c:set var="hayAGSSinRealizar" value="${true}" scope="page" />
            </c:if>
        </c:forEach>

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Control de Calidad</li>
                        <li> 
                            <a href="/SIGIPRO/ControlCalidad/Solicitud?">Solicitudes de Control de Calidad</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/ControlCalidad/Solicitud?accion=ver&id_solicitud=${solicitud.getId_solicitud()}">Solicitud ${solicitud.getNumero_solicitud()}</a>
                        </li>
                        <li class="active"> Informe </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <input hidden="true" id="listaIds" value="${listaIds}">

                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Informe de la Solicitud ${solicitud.getNumero_solicitud()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/ControlCalidad/Solicitud?accion=ver&id_solicitud=${solicitud.getId_solicitud()}">Ver Solicitud</a>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Usuario Solicitante: </strong> <td>${solicitud.getUsuario_solicitante().getNombre_completo()} </td></tr>
                                <tr><td> <strong>Fecha de Solicitud: </strong> <td>${solicitud.getFecha_solicitudAsString()} </td></tr>
                                <tr><td> <strong>Realizado Por: </strong> <td>${solicitud.getInforme().getUsuario().getNombre_completo()} </td></tr>
                                <tr><td> <strong>Fecha de Informe:</strong> <td>${solicitud.getInforme().getFechaAsString()} </td></tr>
                            </table>
                            <br>
                        </div>
                        <div class="col-md-12">
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-check-square-o"></i> Resultados</h3>
                                </div>

                                <div class="widget-content">
                                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                        <!-- Columnas -->
                                        <thead> 
                                            <tr>
                                                <th>Identificadores de Muestras (Tipo)</th>
                                                <th>Análisis Solicitado</th>
                                                <th>Resultado</th>
                                                <th>Fecha Reportado</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${solicitud.getInforme().getResultados()}" var="resultado">
                                                <tr id='${resultado.getId_resultado()}'>
                                                    <td>
                                                        <c:forEach items="${resultado.getAgs().getGrupo().getGrupos_muestras()}" var="muestra">
                                                            ${muestra.getIdentificador()} (${muestra.getTipo_muestra().getNombre()})<br>
                                                        </c:forEach>
                                                    </td>
                                                    <td>
                                                        ${resultado.getAgs().getAnalisis().getNombre()}
                                                    </td>
                                                    <td>
                                                        ${resultado.getResultado()}
                                                    </td>
                                                    <td>
                                                        ${resultado.getFecha_reportado_formateada()}
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <!-- END WIDGET TICKET TABLE -->

                        <c:if test="${hayAGSSinRealizar}">
                            <div class="col-md-12">
                                <div class="widget widget-table">
                                    <div class="widget-header">
                                        <h3><i class="fa fa-check-square-o"></i> Análisis No realizados</h3>
                                    </div>

                                    <div class="widget-content">
                                        <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                            <!-- Columnas -->
                                            <thead> 
                                                <tr>
                                                    <th>Identificador(es) de Muestra(s)</th>
                                                    <th>Tipo de Muestra</th>
                                                    <th>Análisis Solicitado</th>
                                                    <th>Observaciones</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${solicitud.getAnalisis_solicitud()}" var="ags">
                                                    <c:if test="${!ags.isRealizar()}">

                                                    <td>
                                                        <c:forEach items="${ags.getGrupo().getGrupos_muestras()}" var="muestra">
                                                            ${muestra.getIdentificador()}<br>
                                                        </c:forEach>

                                                    </td>

                                                    <td>
                                                        ${ags.getGrupo().getGrupos_muestras().get(0).getTipo_muestra().getNombre()}
                                                    </td>
                                                    <td>
                                                        ${ags.getAnalisis().getNombre()}
                                                    </td>

                                                    <td>
                                                        ${ags.getObservaciones_no_realizar()}
                                                    </td>

                                                </c:if>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                    <!-- /main-content -->
                </div>
                <!-- /main -->
            </div>
        </div>

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/SolicitudCC-v2.js"></script>
    </jsp:attribute>
</t:plantilla_general>


<t:modal idModal="modal-agregar-grupo" titulo="Agregar Grupo">

    <jsp:attribute name="form">

        <form name="form-agregar-agrupaciones" id="form-agregar-agrupaciones" class="form-horizontal" action="Solicitud" method="post">

            <input hidden="true" name="accion" value="agregargrupo" />
            <input hidden="true" name="id_solicitud" value="${solicitud.getId_solicitud()}">

            <label for="ids-muestras" class="control-label">Seleccione el tipo de muestra</label>
            <select id="seleccion-tipo-muestra" class="select2" style="background-color: #fff" name="ids_analisis" required
                    oninvalid="setCustomValidity('El campo de tipo de muestra es requerido.')">
                <option></option>
                <c:forEach items="${solicitud.getControl_solicitud().getAnalisis_tipo_muestras()}" var="atm">
                    <option value="${atm.getAnalisisAsString()}" data-tipo="${atm.getTipo_muestra().getId_tipo_muestra()}">${atm.getTipo_muestra().getNombre()}</option>                       
                </c:forEach>
            </select>

            <br/><br/>

            <label for="ids-muestras" class="control-label">Seleccione las muestras</label>
            <select id="seleccion-muestras" class="select2" style="background-color: #fff" name="ids_muestras" multiple="multiple" required disabled
                    oninvalid="setCustomValidity('El campo de muestras es requerido.')">
                <c:forEach items="${solicitud.obtenerMuestras()}" var="muestra">
                    <option class="opcion-escondida" value="${muestra.getId_muestra()}" data-tipo="${muestra.getTipo_muestra().getId_tipo_muestra()}" hidden>${muestra.getIdentificador()}</option>
                </c:forEach>                
            </select>

            <br/>

            <div class="form-group">
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                    <button id="btn-agregarRol" type="submit" class="btn btn-primary" onclick="agregarProductoInterno()"><i class="fa fa-check-circle"></i> Crear Agrupación</button>
                </div>
            </div>

        </form>

    </jsp:attribute>

</t:modal>

<t:modal idModal="modalAnularSolicitud" titulo="Anular Solicitud">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="anularSolicitud" autocomplete="off" method="post" action="Solicitud">
                <input hidden="true" name="accion" value="Anular">
                <input hidden="true" id='id_solicitud_anular' name='id_solicitud_anular' value="">
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
