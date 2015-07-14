<%-- 
    Document   : Formulario
    Created on : Jul 3, 2015, 8:43:02 AM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" id="agregarAnalisis" autocomplete="off" enctype='multipart/form-data' method="post" action="Analisis">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_analisis" value="${analisis.getId_analisis()}">
            <input hidden="true" name="accion" value="${accion}">
            <input hidden="true" name="orden" id="orden">

            <label for="nombre" class="control-label">*Nombre/Código/Identificador</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" maxlength="45" placeholder="Nombre/Código/Identificador del Analisis" class="form-control" name="nombre" value="${analisis.getNombre()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido')"
                               oninput="setCustomValidity('')" > 
                    </div>
                </div>
            </div>
            <c:choose>
                <c:when test="${analisis.getId_analisis()!=0}">
                    <label for="nombre" class="control-label"> Machote (si no selecciona un archivo, quedará registrado el subido anteriormente)</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="machote" name="machote"  accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <label for="nombre" class="control-label">*Machote</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="machote" name="machote"  accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" required
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-6">
            <label for="especie" class="control-label"> *Tipos de Reactivos</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionTipoReactivo" class="select2" name="tiporeactivos" multiple="multiple"
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${tiporeactivos}" var="tipo">
                                <option value=${tipo.getId_tipo_reactivo()}>${tipo.getNombre()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <label for="especie" class="control-label"> *Tipos de Equipo de Medición</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionTipoEquipo" class="select2" name="tipoequipos" multiple="multiple"
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${tipoequipos}" var="tipo">
                                <option value=${tipo.getId_tipo_equipo()}>${tipo.getNombre()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            
            <label for="tipos-muestra" class="control-label"> *Tipos de Muestra Asociados</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccion-tipo-muestra" class="select2" name="tipos-muestra" multiple="multiple"
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${tiposmuestra}" var="tipo">
                                <option value=${tipo.getId_tipo_muestra()}>${tipo.getNombre()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>


        </div>
        <div class="col-md-12">
            <div class="widget widget-table">
                <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> Estructura</h3>
                    <div class="btn-group widget-header-toolbar">

                    </div>
                </div>
                <div class="widget-content">
                    <div class="campos sortable" id="sortable">

                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <br>
                                <button type="button" onclick="agregarCampo()" class="btn btn-primary"><i class="fa fa-plus-circle"></i> Agregar Campo</button>
                                <button type="button" onclick="agregarTabla()" class="btn btn-primary"><i class="fa fa-plus-square"></i> Agregar Tabla</button>
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
                    <button type="button" class="btn btn-primary" onclick="agregarAnalisis()"><i class="fa fa-check-circle"></i> ${accion} Análisis</button>
                </c:otherwise>
            </c:choose>    
        </div>
    </div>


</form>
