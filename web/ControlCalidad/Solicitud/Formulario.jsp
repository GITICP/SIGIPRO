<%-- 
    Document   : Formulario
    Created on : Jul 9, 2015, 2:01:11 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<form class="form-horizontal" autocomplete="off" method="post" action="Solicitud">
    <div class="row">
        <div class="col-md-12">
            <input hidden="true"  name="id_solicitud" value="${solicitud.getId_solicitud()}">
            <input hidden="true" name="accion" value="${accion}">
            <c:forEach items="${tipomuestras}" var="tipomuestra">
                <input hidden="true" id="listaAnalisis_${tipomuestra.getId_tipo_muestra()}" value='${tipomuestra.parseListaAnalisis()}' data-dias-descarte="${tipomuestra.getDias_descarte()}">
            </c:forEach>
            <input hidden="true" id="listaTipoMuestra" value='${tipomuestraparse}'>
            <input hidden="true" id="listaMuestras" name="listaMuestras" value="">
            <input hidden="true" id="listaIds" value="${listaTM}">
            <input hidden="true" id="listaGrupos" name="listaGrupos" value="${listaGrupos}">

            <div class="row">
                <div class="col-md-6">
                    <label for="nombre" class="control-label">*Número de Solicitud</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="text" maxlength="45" placeholder="Identificador de la Solicitud" class="form-control" name="numero_solicitud" value="${numero_solicitud}"
                                       required
                                       oninvalid="setCustomValidity('Este campo es requerido')"
                                       oninput="setCustomValidity('')" > 
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6"></div>
                <div class="col-md-6">
                    <label for="objeto-relacionado" class="control-label"> Asociar a otro objeto</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <select id="seleccion-objeto" class="select2" name="objeto-relacionado"
                                        style='background-color: #fff;'>
                                    <option value=''></option>
                                    <option value="sangria" ${(tipo == 'sangria') ? "selected" : ""}>
                                        Sangría
                                    </option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <c:choose>
                <c:when test="${accion == 'Agregar'}">
                    <div class="col-md-6"></div>
                    <div id="fila-select-sangria" class="row" hidden="true">
                        <div class="col-md-6">
                            <label for="sangria" class="control-label"> Sangría por asociar</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <select id="seleccion-sangria" name="sangria"
                                                style='background-color: #fff;'>
                                            <option value=''></option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6"></div>
                    <div id="fila-select-dia" class="row" hidden="true">
                        <div class="col-md-6">
                            <label for="sangria" class="control-label"> Día por asignar</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <select id="seleccion-dia" name="dia"
                                                style='background-color: #fff;'>
                                            <option value=''></option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${tipo == 'sangria'}">
                            <t:editar_solicitud_sangria derecha="true" />
                        </c:when>
                        <c:otherwise>

                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="col-md-12">
            <div class="widget widget-table">
                <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> Muestras</h3>
                </div>
                <div class="widget-content">
                    <div class="muestras">
                        <c:forEach items="${listaSolicitud}" var="muestra">
                            <div id="${muestra.get(0)}" class="col-sm-12">
                                <div class="col-sm-3">
                                    <label for="tipo_muestra" class="control-label">*Tipo de Muestra</label>
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <div class="input-group">
                                                <input hidden="true" id="editartipomuestra_${muestra.get(0)}" value="${muestra.get(1)}">
                                                <select id="seleccionTipo_${muestra.get(0)}" class="tipomuestra tipomuestra_${muestra.get(0)}" name="tipomuestra_${muestra.get(0)}"
                                                        style='background-color: #fff;' required
                                                        oninvalid="setCustomValidity('Este campo es requerido')"
                                                        onchange="seleccionTipoMuestra(this, '${muestra.get(0)}')">
                                                    <option value=''></option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <label for="nombre" class="control-label">*Identificadores</label>
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <div class="input-group">
                                                <input type="text" placeholder="Separados, por, comas" id="identificadores_${muestra.get(0)}" class="identificadores_${muestra.get(0)}" name="identificadores_${muestra.get(0)}" value="${muestra.get(2)}"
                                                       required 
                                                       oninvalid="setCustomValidity('Este campo es requerido')"
                                                       oninput="setCustomValidity('')" onfocus="eliminarBusqueda()" > 
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <label for="fecha_ingreso" class="control-label">Fecha de Descarte</label>
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <div class="input-group">
                                                <input type="text" id="datepicker_${muestra.get(0)}" class="form-control" name="fechadescarte_${muestra.get(0)}" value="${muestra.get(3)}"
                                                       oninvalid="setCustomValidity('Este campo es requerido y no pueden ser fechas futuras. ')"
                                                       onchange="setCustomValidity('')">
                                            </div>
                                        </div>
                                    </div>
                                </div> 
                                <div class="col-sm-3">
                                    <label for="analisis" class = "control-label" > *An&aacute;lisis </label>
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <div class="input-group">
                                                <input hidden="true" id="editaranalisis_${muestra.get(0)}" value="${muestra.get(4)}">
                                                <select id="seleccionAnalisis_${muestra.get(0)}" class="analisis_${muestra.get(0)}" multiple="multiple" name="analisis_${muestra.get(0)}"
                                                        style='background-color: #fff;' required
                                                        oninvalid="setCustomValidity('Este campo es requerido')"
                                                        onchange="setCustomValidity('')">
                                                    <option value=''></option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-2"> <br>
                                    <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarMuestra('${muestra.get(0)}')" style="margin-left:7px;margin-right:5px;">Eliminar</button>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <br>
                                <button type="button" onclick="agregarMuestra()" class="btn btn-primary"><i class="fa fa-plus-circle"></i> Agregar Muestras</button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <!-- Esta parte es la de los permisos de un rol -->
        <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
    </div>


    <div class="form-group">
        <div class="modal-footer">
            <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Solicitud</button>
                </c:otherwise>
            </c:choose>    
        </div>
    </div>


</form>

