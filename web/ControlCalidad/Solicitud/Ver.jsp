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
        <c:set var="hayAGSSinRealizar" value="${false}" scope="page" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Control de Calidad</li>
                        <li> 
                            <a href="/SIGIPRO/ControlCalidad/Solicitud?">Solicitudes de Control de Calidad</a>
                        </li>
                        <li class="active"> ${solicitud.getNumero_solicitud()} </li>
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
                            <h3><i class="fa fa-gears"></i> Solicitud Número ${solicitud.getNumero_solicitud()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:choose>
                                    <c:when test="${solicitud.getEstado().equals('Solicitado')}">
                                        <c:if test="${booleditar}">
                                            <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/ControlCalidad/Solicitud?accion=editar&id_solicitud=${solicitud.getId_solicitud()}">Editar</a>
                                        </c:if>
                                        <c:if test="${boolrecibir}">
                                            <a class="btn btn-primary btn-sm boton-accion recibir-Modal" data-id='${solicitud.getId_solicitud()}' data-toggle="modal" data-target="#modalRecibirSolicitud">Recibir</a>
                                        </c:if>
                                        <c:if test="${boolanular || solicitud.getUsuario_solicitante().getId_usuario()== sessionScope.idusuario}">
                                            <a class="btn btn-danger btn-sm boton-accion anular-Modal" data-id='${solicitud.getId_solicitud()}' data-toggle="modal" data-target="#modalAnularSolicitud">Anular</a>
                                        </c:if>
                                    </c:when>
                                    <c:when test="${solicitud.getEstado().equals('Anulada')}">

                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${solicitud.getInforme() == null && helper_permisos.validarPermiso(sessionScope.listaPermisos, 557)}">
                                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/ControlCalidad/Informe?accion=generar&id_solicitud=${solicitud.getId_solicitud()}">Generar Informe Parcial</a>
                                                <a class="btn btn-danger btn-sm boton-accion anular-Modal" data-id='${solicitud.getId_solicitud()}' data-toggle="modal" data-target="#modalAnularSolicitud">Anular</a>
                                            </c:when>
                                            <c:when test="${solicitud.getInforme() != null && helper_permisos.validarPermiso(sessionScope.listaPermisos, 558)}">
                                                <c:choose>
                                                    <c:when test="${solicitud.getEstado().equals('Resultado Parcial')}">
                                                        <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/ControlCalidad/Informe?accion=editar&id_solicitud=${solicitud.getId_solicitud()}">Editar Informe Parcial</a>
                                                    </c:when>
                                                </c:choose>
                                            </c:when>
                                            <c:when test="${solicitud.getUsuario_solicitante().getId_usuario()== sessionScope.idusuario && !solicitud.getEstado().equals('Resultado Parcial') && !solicitud.getEstado().equals('Completada')}">
                                                <a class="btn btn-danger btn-sm boton-accion anular-Modal" data-id='${solicitud.getId_solicitud()}' data-toggle="modal" data-target="#modalAnularSolicitud">Anular</a>
                                            </c:when>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </div>
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
                                <c:if test="${solicitud.getFecha_cierre()!=null}">
                                    <tr><td> <strong>Fecha de Cierre: </strong> <td>${solicitud.getFecha_cierreAsString()} </td></tr>
                                </c:if>
                                <tr><td> <strong>Estado: </strong> <td>${solicitud.getEstado()} </td></tr>
                                <c:if test="${!solicitud.getObservaciones().equals('')}">
                                    <tr><td> <strong>Observaciones: </strong> <td>${solicitud.getObservaciones()} </td></tr>
                                </c:if>
                            </table>
                            <br>
                        </div>
                        <div class="col-md-12">
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-calendar"></i> Análisis Solicitados</h3>
                                </div>
                                <div class="widget-content">
                                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro">
                                        <!-- Columnas -->
                                        <thead> 
                                            <tr>
                                                <th>Tipo de Muestras</th>
                                                <th>Cantidad de Muestras</th>
                                                <th>Análisis Solicitado</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${solicitud.getControl_solicitud().getAnalisis_tipo_muestras()}" var="atm">
                                                <tr>
                                                    <td>
                                                        ${atm.getTipo_muestra().getNombre()}
                                                    </td>
                                                    <td>
                                                        ${atm.getCantidad_muestras()}
                                                    </td>
                                                    <td>
                                                        <c:forEach items="${atm.getAnalisis()}" var="analisis">
                                                            ${analisis.getNombre()} <br>
                                                        </c:forEach>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <c:if test="${(solicitud.getEstado().equals('Recibido') || solicitud.getEstado().equals('Resultado Parcial') || solicitud.getEstado().equals('Completada'))  && helper_permisos.validarPermiso(sessionScope.listaPermisos, 541)}">
                                <div class="widget widget-table">
                                    <div class="widget-header">
                                        <h3><i class="fa fa-calendar"></i> Agrupaciones de muestras</h3>
                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 556) && !solicitud.getEstado().equals('Completada')}">
                                            <div class="btn-group widget-header-toolbar">                                    
                                                <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modal-agregar-grupo">Crear Nueva Agrupación</a>
                                            </div>
                                        </c:if>
                                    </div>

                                    <div class="widget-content">
                                        <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                            <!-- Columnas -->
                                            <thead> 
                                                <tr>
                                                    <th>Identificador(es) de Muestra(s)</th>
                                                    <th>Tipo de Muestra</th>
                                                    <th>Análisis Solicitado</th>
                                                    <th>Acción</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${solicitud.getAnalisis_solicitud()}" var="ags">
                                                    <tr id='${ags.getId_analisis_grupo_solicitud()}'>
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
                                                            <c:choose>
                                                                <c:when test="${solicitud.getEstado().equals('Recibido') || solicitud.getEstado().equals('Resultado Parcial') || solicitud.getEstado().equals('Completada')}">
                                                                    <c:choose>
                                                                        <c:when test="${!ags.isRealizar()}">
                                                                            <c:set var="hayAGSSinRealizar" value="${true}" scope="page" />
                                                                            Análisis no se realizará.
                                                                        </c:when>
                                                                        <c:when test="${ags.getResultados() == null}">
                                                                            <a class="btn btn-primary btn-sm boton-accion" 
                                                                               href="/SIGIPRO/ControlCalidad/Analisis?accion=realizar&id_analisis=${ags.getAnalisis().getId_analisis()}&id_ags=${ags.getId_analisis_grupo_solicitud()}${(ags.getAnalisis().getId_analisis() == 2147483647) ? "&identificadores=" += ags.getGrupo().getGrupos_muestras_Sring() : "" }">
                                                                                Realizar
                                                                            </a>
                                                                            <a class="btn btn-warning btn-sm boton-accion" 
                                                                               onclick="abrirModalNoRealizar(${ags.getId_analisis_grupo_solicitud()}, '${ags.getAnalisis().getNombre()}')">
                                                                                No Realizar
                                                                            </a>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <c:if test="${!solicitud.getEstado().equals('Completada')}">
                                                                                <a class="btn btn-primary btn-sm boton-accion" 
                                                                                   href="/SIGIPRO/ControlCalidad/Analisis?accion=realizar&id_analisis=${ags.getAnalisis().getId_analisis()}&id_ags=${ags.getId_analisis_grupo_solicitud()}${(ags.getAnalisis().getId_analisis() == 2147483647) ? "&identificadores=" += ags.getGrupo().getGrupos_muestras_Sring() : "" }">
                                                                                    Repetir
                                                                                </a>
                                                                            </c:if>
                                                                            <a class="btn btn-primary btn-sm boton-accion" 
                                                                               href="/SIGIPRO/ControlCalidad/Resultado?accion=vermultiple&id_analisis=${ags.getAnalisis().getId_analisis()}&id_ags=${ags.getId_analisis_grupo_solicitud()}&id_solicitud=${solicitud.getId_solicitud()}&numero_solicitud=${solicitud.getNumero_solicitud()}">
                                                                                Ver Resultados (${ags.getResultados().size()})
                                                                            </a>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    Solicitud aún no ha sido recibida.
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${(solicitud.getEstado().equals('Completada') || solicitud.getEstado().equals('Resultado Parcial')) && helper_permisos.validarPermiso(sessionScope.listaPermisos, 559)}">
                                <div class="widget widget-table">
                                    <div class="widget-header">
                                        <h3><i class="fa fa-gears"></i> Informe ${(!solicitud.getEstado().equals('Resultado Parcial')) ? "" : "Parcial"} de la Solicitud ${solicitud.getNumero_solicitud()} </h3>
                                    </div>
                                    <div class="widget-content">
                                        <table class="tabla-ver">
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
                                                            <th>Identificador(es) de Muestra(s)</th>
                                                            <th>Tipo de Muestra</th>
                                                            <th>Análisis Solicitado</th>
                                                            <th>Resultado</th>
                                                            <th>Fecha Reportado</th>
                                                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 547)}">
                                                                <th>Acción</th>
                                                                </c:if>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${solicitud.getInforme().getResultados()}" var="resultado">
                                                            <tr id='${resultado.getId_resultado()}'>
                                                                <td>
                                                                    <c:forEach items="${resultado.getAgs().getGrupo().getGrupos_muestras()}" var="muestra">
                                                                        ${muestra.getIdentificador()}<br>
                                                                    </c:forEach>
                                                                </td>
                                                                <td>
                                                                    ${resultado.getAgs().getGrupo().getGrupos_muestras().get(0).getTipo_muestra().getNombre()}
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
                                                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 547)}">
                                                                    <td>
                                                                        <a class="btn btn-warning btn-sm boton-accion " href="/SIGIPRO/ControlCalidad/Resultado?accion=editar&id_resultado=${resultado.getId_resultado()}&id_analisis=${resultado.getAgs().getAnalisis().getId_analisis()}${(resultado.getAgs().getAnalisis().getId_analisis() == 2147483647) ? "&identificadores=" += resultado.getAgs().getGrupo().getGrupos_muestras_Sring() : "" }">Editar</a>
                                                                    </td>
                                                                </c:if>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>

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
                                    <!-- END WIDGET TICKET TABLE -->
                                </div>
                            </c:if>
                        </div>
                        <!-- END WIDGET TICKET TABLE -->
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

            <button id="btn-agrupar-muestras" class="btn btn-primary btn-md boton-accion" style="margin-top:10px;" disabled="true" type="button">Agrupar Todas</button>

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

<t:modal idModal="modalNoRealizar" titulo="No realizar análisis">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="no-realizar-analisis" autocomplete="off" method="post" action="Solicitud">
                <input hidden="true" name="accion" value="norealizar">
                <input hidden="true" id='id_solicitud' name='id_solicitud' value="${solicitud.getId_solicitud()}">
                <input hidden="true" id='id_analisis_no_realizar' name='id_analisis_no_realizar' value="">
                <label id="label-observaciones" for="observaciones" class="control-label"></label>
                <br>
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
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> No realizar análisis</button>            
                    </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal>