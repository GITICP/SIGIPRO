<%-- 
    Document   : Formulario
    Created on : Jun 29, 2015, 4:42:37 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" enctype='multipart/form-data' method="post" action="Patron">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_patron" value="${patron.getId_patron()}">
            <input hidden="true" name="accion" value="${accion}">
            <label for="nombre" class="control-label">* Número de Lote/Identificador</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" maxlength="45" placeholder="Número de Lote o Identificador del Patrón o Control" class="form-control" name="num_lote" value="${patron.getNumero_lote()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido')"
                               oninput="setCustomValidity('')"> 
                    </div>
                </div>
            </div>
            <label for="opciones" class="control-label">Tipo</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionTipo" class="select2" name="id_tipo_patroncontrol"
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${tipos_patronescontroles}" var="tipo_patroncontrol">
                                <c:choose>
                                    <c:when test="${tipo_patroncontrol.getId_tipo_patroncontrol() == patron.getTipo().getId_tipo_patroncontrol()}" >
                                        <option value="${tipo_patroncontrol.getId_tipo_patroncontrol()}"  selected> ${tipo_patroncontrol.getNombre()} (${tipo_patroncontrol.getTipo()})</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${tipo_patroncontrol.getId_tipo_patroncontrol()}" > ${tipo_patroncontrol.getNombre()} (${tipo_patroncontrol.getTipo()})</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <label for="fecha_extraccion" class="control-label">* Fecha de Ingreso</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" value="${(patron.getFecha_ingreso() != null) ? patron.getFecha_ingresoAsString() : helper_fechas.getFecha_hoyAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               onchange="setCustomValidity('')">
                    </div>
                </div>
            </div>
            <label for="fecha_extraccion" class="control-label">* Fecha de Vencimiento</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" value="${(patron.getFecha_vencimiento() != null) ? patron.getFecha_vencimientoAsString() : helper_fechas.getFecha_hoyAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_vencimiento" data-date-format="dd/mm/yyyy" required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               onchange="setCustomValidity('')">
                    </div>
                </div>
            </div>
            <label for="fecha_extraccion" class="control-label">* Fecha de Inicio de Uso</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" value="${(patron.getFecha_inicio_uso() != null) ? patron.getFecha_inicio_usoAsString() : helper_fechas.getFecha_hoyAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_inicio_uso" data-date-format="dd/mm/yyyy" required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               onchange="setCustomValidity('')">
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <c:choose>
                <c:when test="${accion == 'Agregar'}">
                    <label for="nombre" class="control-label">Certificado</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="certificado" name="certificado"  accept="application/pdf,image/jpeg,image/gif,image/png"
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <label for="nombre" class="control-label">Certificado (Si no se selecciona ningún archivo se mantiene el archivo anterior)</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="certificado" name="certificado"  accept="application/pdf,image/jpeg,image/gif,image/png"
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>



            <label for="nombre" class="control-label">* Lugar de Almacenamiento</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" maxlength="45" placeholder="Lugar de Almacenamiento" class="form-control" name="lugar_almacenamiento" value="${patron.getLugar_almacenamiento()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido')"
                               oninput="setCustomValidity('')" > 
                    </div>
                </div>
            </div>
            <label for="nombre" class="control-label">* Condición de Almacenamiento</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea rows="5" cols="50" maxlength="500" placeholder="Condición de Almacenamiento" class="form-control" name="condicion_almacenamiento">${patron.getCondicion_almacenamiento()}</textarea>
                    </div>
                </div>
            </div>
            <label for="descripcion" class="control-label">Observaciones</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea rows="5" cols="50" maxlength="500" placeholder="Observaciones" class="form-control" name="observaciones" >${(patron.getObservaciones() == 'Sin observaciones.') ? "" : patron.getObservaciones()}</textarea>
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Patrón</button>
                </c:otherwise>
            </c:choose>    </div>
    </div>

</form>
