<%-- 
    Document   : Agregar
    Created on : Jun 30, 2015, 8:39:20 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Control de Calidad" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Control de Calidad</li>
                        <li> 
                            <a href="/SIGIPRO/ControlCalidad/Resultado?accion=vermultiple&id_analisis=${analisis.getId_analisis()}&id_ags=${resultado.getAgs().getId_analisis_grupo_solicitud()}&id_solicitud=${solicitud.getId_solicitud()}&numero_solicitud=${solicitud.getNumero_solicitud()}">Resultados</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/ControlCalidad/Resultado?accion=ver&id_resultado=${resultado.getId_resultado()}&id_analisis=${analisis.getId_analisis()}">Resultado #${resultado.getId_resultado()}</a>
                        </li>
                        <li class="active"> Editar Resultado</li>

                    </ul>
                </div>
            </div>

            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-flask"></i> Editar Resultado #${resultado.getId_resultado()}</h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            
                            <table class="tabla-ver">
                                <tr><td><strong>Número de Solicitud:</strong></td><td>${resultado.getAgs().getGrupo().getSolicitud().getNumero_solicitud()}</td></tr>
                                <tr><td><strong>Análisis:</strong></td><td>${analisis.getNombre()}</td></tr>
                                <tr><td><strong>Tipo de muestra: </strong></td><td>${resultado.getAgs().getGrupo().getGrupos_muestras().get(0).getTipo_muestra().getNombre()}</td></tr>
                                <tr><td><strong>${(resultado.getAgs().getGrupo().getGrupos_muestras().size() == 1) ? "Identificador de Muestra:" : "Identificadores de muestras:"}</strong></td>
                                    <td>
                                        ${resultado.getAgs().getGrupo().getGrupos_muestras_Sring()}
                                    </td>
                                </tr>
                            </table>
                            
                            <br>

                            <form method="post" class="form-horizontal" action="Resultado" autocomplete="off" enctype='multipart/form-data'>
                                <input type="hidden" value="editar" name="accion" />
                                <input type="hidden" value="${id_analisis}" name="id_analisis" />
                                <input type="hidden" value="${id_ags}" name="id_ags" />
                                <input type="hidden" value="${resultado.getId_resultado()}" name="id_resultado" />

                                <div class="widget widget-table">
                                    <div class="widget-header">
                                        <h3><i class="fa fa-gears"></i> Reactivos, Equipos, Patrones y Controles Utilizados </h3>
                                    </div>
                                    <div class="widget-content">
                                        <div class="row">
                                            <c:if test="${!reactivos.isEmpty()}">
                                                <div class="col-md-6">
                                                    <label for="reactivos" class="control-label"> *Reactivos Utilizados</label>
                                                    <div class="form-group">
                                                        <div class="col-sm-12">
                                                            <div class="input-group">
                                                                <select id="seleccion-reactivos" class="select2" name="reactivos" multiple="multiple"
                                                                        style='background-color: #fff;' required
                                                                        oninvalid="setCustomValidity('Este campo es requerido')"
                                                                        onchange="setCustomValidity('')">
                                                                    <option value=''></option>
                                                                    <c:forEach items="${reactivos}" var="reactivo">
                                                                        <option value=${reactivo.getId_reactivo()} ${(resultado.tieneReactivo(reactivo)) ? "selected" : ""}>
                                                                            ${reactivo.getNombre()}
                                                                        </option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <label for="preparacion-reactivos" class="control-label" hidden="true">Preparación Reactivo</label>
                                                    <div class="form-group">
                                                        <div class="col-sm-12">
                                                            <div id="espacio-preparacion-reactivos" class="input-group">
                                                                <div></div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                            </c:if>
                                            <c:if test="${!equipos.isEmpty()}">
                                                <div class="col-md-6">
                                                    <label for="especie" class="control-label"> *Equipos de Medición Utilizados</label>
                                                    <div class="form-group">
                                                        <div class="col-sm-12">
                                                            <div class="input-group">
                                                                <select id="seleccion-equipo" class="select2" name="equipos" multiple="multiple"
                                                                        style='background-color: #fff;' required
                                                                        oninvalid="setCustomValidity('Este campo es requerido')"
                                                                        onchange="setCustomValidity('')">
                                                                    <option value=''></option>
                                                                    <c:forEach items="${equipos}" var="equipo">
                                                                        <option value=${equipo.getId_equipo()} ${(resultado.tieneEquipo(equipo)) ? "selected" : ""}>
                                                                            ${equipo.getNombre()}
                                                                        </option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="patrones" class="control-label"> Materiales de Referencia Utilizados</label>
                                                <div class="form-group">
                                                    <div class="col-sm-12">
                                                        <div class="input-group">
                                                            <select id="seleccion-patrones" class="select2" name="patrones" multiple="multiple"
                                                                    style='background-color: #fff;'
                                                                    oninvalid="setCustomValidity('Este campo es requerido')"
                                                                    onchange="setCustomValidity('')">
                                                                <option value=''></option>
                                                                <c:forEach items="${materiales}" var="material">
                                                                    <option value=${material.getId_patron()} ${(resultado.tienePatron(material)) ? "selected" : ""}>
                                                                        ${material.getNumero_lote()} (${material.getTipo().getNombre()})
                                                                    </option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <c:if test="${analisis.tieneMachote()}">
                                            <input type="hidden" name="path" value="${resultado.getPath()}" />

                                            <div class="row">
                                                <div class="col-md-6">
                                                    <label for="nombre" class="control-label"> Machote</label>
                                                    <div class="form-group">
                                                        <div class="col-md-12">
                                                            <div class="input-group">
                                                                <a href="/SIGIPRO/ControlCalidad/Analisis?accion=archivo&id_analisis=${analisis.getId_analisis()}">Descargar Machote</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <label for="nombre" class="control-label"> Resultado</label>
                                                    <div class="form-group">
                                                        <div class="col-md-12">
                                                            <div class="input-group">
                                                                <a href="/SIGIPRO/ControlCalidad/Resultado?accion=archivo&id_resultado=${resultado.getId_resultado()}">Descargar Resultado</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <label for="nombre" class="control-label"> Actualización Resultado</label>
                                                    <div class="form-group">
                                                        <div class="col-sm-12">
                                                            <div class="input-group">
                                                                <input type="file" id="resultado" name="resultado"  accept=".xlsx"
                                                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                                                       onchange="setCustomValidity('')"/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-12">
                                        ${cuerpo_formulario}
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
                                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                                    </div>
                                </div>
                            </form>

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

        <script src="/SIGIPRO/recursos/js/sigipro/Analisis.js" type="text/javascript"></script>

    </jsp:attribute>

</t:plantilla_general>

