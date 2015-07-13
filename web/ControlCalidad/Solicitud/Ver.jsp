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
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> ${solicitud.getNumero_solicitud()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:set var="contienePermisoAnular" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                    <c:if test="${permiso == 1 || permiso == 552}">
                                        <c:set var="contienePermisoAnular" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoAnular}">
                                    <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="anular la solicitud" data-href="/SIGIPRO/ControlCalidad/Solicitud?accion=anular&id_solicitud=${solicitud.getId_solicitud()}">Anular</a>
                                </c:if>

                                <c:set var="contienePermisoEditar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                    <c:if test="${permiso == 1 || permiso == 550}">
                                        <c:set var="contienePermisoEditar" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEditar}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/ControlCalidad/Solicitud?accion=editar&id_solicitud=${solicitud.getId_solicitud()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table>
                                <tr><td> <strong>Número de Solicitud: </strong></td> <td>${solicitud.getNumero_solicitud()} </td></tr>
                                <tr><td> <strong>Usuario Solicitante: </strong> <td>${solicitud.getUsuario_solicitante().getNombre_completo()} </td></tr>
                                <tr><td> <strong>Fecha de Solicitud: </strong> <td>${solicitud.getFecha_solicitudAsString()} </td></tr>
                                <c:if test="${solicitud.getUsuario_recibido()!=null}">
                                    <tr><td> <strong>Usuario Receptor: </strong> <td>${solicitud.getUsuario_recibido().getNombre_completo()} </td></tr>
                                    <tr><td> <strong>Fecha de Recepción: </strong> <td>${solicitud.getFecha_recibidoAsString()} </td></tr>
                                </c:if>
                                <tr><td> <strong>Estado: </strong> <td>${solicitud.getEstado()} </td></tr>
                            </table>
                            <br>
                        </div>
                        <div class="col-md-12">
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-calendar"></i> Análisis Solicitados</h3>
                                </div>
                                <div class="widget-content">
                                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
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
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-calendar"></i> Agrupaciones de muestras</h3>
                                    <div class="btn-group widget-header-toolbar">                                    
                                        <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modal-agregar-grupo">Crear Nueva Agrupación</a>
                                    </div>
                                </div>

                                <div class="widget-content">
                                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                        <!-- Columnas -->
                                        <thead> 
                                            <tr>
                                                <th>Identificadores de Muestras (Tipo)</th>
                                                <th>Análisis Solicitado</th>
                                                <th>Acción</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${solicitud.getAnalisis_solicitud()}" var="ags">
                                                <tr id='${ags.getId_analisis_grupo_solicitud()}'>
                                                    <td>
                                                        <c:forEach items="${ags.getGrupo().getGrupos_muestras()}" var="muestra">
                                                            ${muestra.getIdentificador()} (${muestra.getTipo_muestra().getNombre()})<br>
                                                        </c:forEach>
                                                    </td>
                                                    <td>
                                                        ${ags.getAnalisis().getNombre()}
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${ags.getResultados() == null}">
                                                                <a class="btn btn-primary btn-sm boton-accion" 
                                                                    href="/SIGIPRO/ControlCalidad/Analisis?accion=realizar&id_analisis=${ags.getAnalisis().getId_analisis()}&id_ags=${ags.getId_analisis_grupo_solicitud()}">
                                                                    Realizar
                                                                </a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a class="btn btn-primary btn-sm boton-accion" 
                                                                    href="/SIGIPRO/ControlCalidad/Analisis?accion=realizar&id_analisis=${ags.getAnalisis().getId_analisis()}&id_ags=${ags.getId_analisis_grupo_solicitud()}">
                                                                    Repetir
                                                                </a>
                                                                <a class="btn btn-primary btn-sm boton-accion" 
                                                                    href="/SIGIPRO/ControlCalidad/Resultado?accion=vermultiple&id_analisis=${ags.getAnalisis().getId_analisis()}&id_ags=${ags.getId_analisis_grupo_solicitud()}&id_solicitud=${solicitud.getId_solicitud()}&numero_solicitud=${solicitud.getNumero_solicitud()}">
                                                                    Ver Resultados (${ags.getResultados().size()})
                                                                </a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
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
        <script src="/SIGIPRO/recursos/js/sigipro/SolicitudCC.js"></script>
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



