<%-- 
    Document   : Ver
    Created on : 25-mar-2015, 18:22:58
    Author     : Walter
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Caballeriza" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />
        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Caballeriza</li>
                        <li> 
                            <a href="/SIGIPRO/Caballeriza/Caballo?">Caballos</a>
                        </li>
                        <li class="active"> ${caballo.getNumero_microchip()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-book"></i> ${caballo.getNumero_microchip()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:set var="contienePermisoEditar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                    <c:if test="${permiso == 1 || permiso == 50}">
                                        <c:set var="contienePermisoEditar" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEditar}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Caballo?accion=editar&id_caballo=${caballo.getId_caballo()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table>
                                <tr><td> <strong>Nombre:</strong> <td>${caballo.getNombre()} </td></tr>
                                <tr><td> <strong>Numero de Microchip:</strong> <td>${caballo.getNumero_microchip()} </td></tr>
                                <tr><td> <strong>Grupo del Caballo:</strong> <td>
                                        <c:set var="val" value=""/>
                                        <c:choose> 
                                            <c:when test="${caballo.getGrupo_de_caballos().getNombre() == null}">
                                                No tiene grupo
                                            </c:when>
                                            <c:otherwise>
                                                ${caballo.getGrupo_de_caballos().getNombre()}
                                            </c:otherwise>
                                        </c:choose>
                                    </td></tr>                
                                <tr><td> <strong>Fecha de Nacimiento:</strong> <td>${caballo.getFecha_nacimientoAsString()} </td></tr>
                                <tr><td> <strong>Fecha de Ingreso:</strong> <td>${caballo.getFecha_ingresoAsString()} </td></tr>
                                <tr><td> <strong>Sexo:</strong> <td>${caballo.getSexo()} </td></tr>
                                <tr><td> <strong>Color:</strong> <td>${caballo.getColor()} </td></tr>
                                <tr><td> <strong>Otras Señas:</strong> <td>${caballo.getOtras_sennas()}</td></tr>
                                <tr><td> <strong>Estado:</strong> <td>${caballo.getEstado()}</td></tr>
                                <tr><td> <strong>Imagen (Sin Implementar):</strong> <td>${caballo.getFotografia()} </td></tr> 
                            </table>
                        </div>
                        <div class="widget widget-table">
                            <div class="widget-header">
                                <h3><i class="fa fa-check"></i> Eventos Clínicos del Caballo </h3>
                            </div>
                            <div class="widget-content">
                                <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Identificador</th>
                                        <th>Fecha del Evento</th>
                                        <th>Tipo de Evento</th>
                                        <th>Usuario responsable</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaEventos}" var="eventos">
                                        <tr id ="${eventos.getId_evento()}">
                                            <td>
                                                <a href="/SIGIPRO/Caballeriza/EventoClinico?accion=ver&id_evento=${eventos.getId_evento()}">
                                                    <div style="height:100%;width:100%">
                                                        ${eventos.getId_evento()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>${eventos.getFechaAsString()}</td>
                                            <td>${eventos.getTipo_evento().getNombre()}</td>
                                            <c:choose>
                                                <c:when test="${eventos.getResponsable()!= null}">
                                                    <td>${eventos.getResponsable()}</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>No Tiene Usuario Responsable</td>
                                                </c:otherwise>
                                            </c:choose>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            </div>
                        </div>
                        <div class="widget widget-table">
                            <div class="widget-header">
                                <h3><i class="fa fa-check"></i> Inóculos del Caballo </h3>
                            </div>
                            <div class="widget-content">
                                <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Identificador</th>
                                        <th>Fecha del Inóculo</th>
                                        <th>M.n.n</th>
                                        <th>B.a -A</th>
                                        <th>B.a -P</th>
                                        <th>C.d.d</th>
                                        <th>L.m.s</th>
                                        <th>Tetox</th>
                                        <th>Otro</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaInoculos}" var="inoculo">
                                        <tr id ="${inoculo.getId_inoculo()}">
                                            <td>
                                                <a href="/SIGIPRO/Caballeriza/Inoculo?accion=ver&id_inoculo=${inoculo.getId_inoculo()}">
                                                    <div style="height:100%;width:100%">
                                                        ${inoculo.getId_inoculo()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>${inoculo.getFechaAsString()}</td>
                                            <td>${inoculo.getMnn()}</td>
                                            <td>${inoculo.getBaa()}</td>
                                            <td>${inoculo.getBap()}</td>
                                            <td>${inoculo.getCdd()}</td>
                                            <td>${inoculo.getLms()}</td>
                                            <td>${inoculo.getTetox()}</td>
                                            <td>${inoculo.getOtro()}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            </div>
                        </div>
                        <div class="widget widget-table">
                            <div class="widget-header">
                                <h3><i class="fa fa-check"></i> Sangrías de Prueba del Caballo </h3>
                            </div>
                            <div class="widget-content">
                                <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Identificador</th>
                                        <th>Muestra </th>
                                        <th>Fecha de Recepción </th>
                                        <th>Hematrocito</th>
                                        <th>Hemoglobina</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaSangriasPruebas}" var="sangriap">
                                        <tr id ="${sangriap.getSangria_prueba().getId_sangria_prueba()}">
                                            <td>
                                                <a href="/SIGIPRO/Caballeriza/SangriaPrueba?accion=ver&id_sangria_prueba=${sangriap.getSangria_prueba().getId_sangria_prueba()}">
                                                    <div style="height:100%;width:100%">
                                                        ${sangriap.getSangria_prueba().getId_sangria_prueba()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>${sangriap.getSangria_prueba().getMuestra()}</td>
                                            <td>${sangriap.getSangria_prueba().getFecha_recepcion_muestraAsString()}</td>
                                            <td>${sangriap.getHematrocito()}</td>
                                            <td>${sangriap.getHemoglobina()}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            </div>
                        </div>
         <div class="widget widget-table">
                            <div class="widget-header">
                                <h3><i class="fa fa-check"></i> Sangrías del Caballo </h3>
                            </div>
                            <div class="widget-content">
                                <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Identificador</th>
                                        <th>Fecha de Inicio</th>
                                        <th>Sangre Total </th>
                                        <th>Plama Total </th>
                                        <th>LaL Total</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaSangrias}" var="sangria">
                                        <tr id ="${sangria.getSangria().getId_sangria()}">
                                            <td>
                                                <a href="/SIGIPRO/Caballeriza/Sangria?accion=ver&id_sangria=${sangria.getSangria().getId_sangria()}">
                                                    <div style="height:100%;width:100%">
                                                        ${sangria.getSangria().getId_sangria()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>${sangria.getSangria().getFecha_dia1AsString()}</td>
                                            <td>${sangria.getSangre_dia1()+sangria.getSangre_dia2()+sangria.getSangre_dia3()}</td>
                                            <td>${sangria.getPlasma_dia1()+sangria.getPlasma_dia2()+sangria.getPlasma_dia3()}</td>
                                            <td>${sangria.getLal_dia1()+sangria.getLal_dia2()+sangria.getLal_dia3()}</td>
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

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/Caballeriza.js"></script>
    </jsp:attribute>

</t:plantilla_general>

<t:modal idModal="modalVerEvento" titulo="Ver Observaciones del Evento">
    <jsp:attribute name="form">
        <div class="widget-content">
            <table>
                <tr><td> <strong>Observaciones:</strong><div id ="observacionesModal"></div> <td></td></tr>
            </table>
        </div>
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
            </div>
        </div>


    </jsp:attribute>

</t:modal>

<t:modal idModal="modalAgregarEvento" titulo="Agregar Eventos">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="agregarEventos" autocomplete="off" method="post" action="Caballeriza">
                <input hidden="true" name="accion" value="Evento">
                <input hidden="true" id='id_serpiente' name='id_caballo' value="">
                <label for="tipo-evento" class="control-label">*Seleccione El Evento:</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <select id="eventoModal" class="select2" name="eventoModal"
                                    style='background-color: #fff;' required
                                    oninvalid="setCustomValidity('Este campo es requerido')"
                                    onchange="setCustomValidity('')">
                                <option value=''></option>
                                <c:forEach items="${listaEventosRestantes}" var="evento">
                                    <option value="${evento.getId_evento()}|${evento.getDescripcion().toString()}"> ID: ${evento.getId_evento()} -- Fecha: ${evento.getFechaAsString()} -- Tipo: ${evento.getTipo_evento().getNombre()}</option>
                                </c:forEach>

                            </select>
                        </div>
                    </div>
                </div>
                <label for="observaciones" class="control-label">Descripción del Evento:</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <BR>
                            <textarea disabled='true' rows="8" cols="50" maxlength="500" placeholder="Descripción Del Evento" class="form-control" name="observaciones" id="observaciones"></textarea>
                        </div>
                    </div>
                </div>


                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button id="btn-agregarCaballoEvento" type="button" class="btn btn-primary" onclick="agregarCaballoEvento()><i class="fa fa-check-circle"></i> Agregar Evento</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal>