<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form class="form-horizontal" autocomplete="off" method="post" action="EventoClinico">
    <div class="col-md-6">
        <input hidden="true" name="id_evento" value="${evento.getId_evento()}">
        <input hidden="true" name="accion" value="${accion}">
        <input hidden='true' id='imagenSubida2' value=''>
        <label for="tipoevento" class="control-label">*Tipo del Evento Clínico</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccionTipoDeEvento" class="select2" name="tipoevento" 
                            style='background-color: #fff;' required
                            oninvalid="setCustomValidity('Por favor seleccione el tipo del evento')"
                            onchange="setCustomValidity('')">
                        <option value=''></option>
                        <c:forEach items="${listatipos}" var="tipo">
                            <c:choose>
                                <c:when test="${tipo.getId_tipo_evento() == evento.getTipo_evento().getId_tipo_evento()}" >
                                    <option value =${tipo.getId_tipo_evento()},${tipo.getDescripcion()}  selected> ${tipo.getNombre()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value =${tipo.getId_tipo_evento()},${tipo.getDescripcion()}>${tipo.getNombre()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>             
        <label for="fecha" class="control-label">*Fecha</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${evento.getFecha()==null}">
                            <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha" data-date-format="dd/mm/yyyy" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   onchange="setCustomValidity('')">
                        </c:when>
                        <c:otherwise>
                            <input type="text"  value="${evento.getFechaAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha" data-date-format="dd/mm/yyyy" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   onchange="setCustomValidity('')">
                            <input hidden="true" name="fecha_ingreso" value="${evento.getFechaAsString()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <label for="responsable" class="control-label">Responsable</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccionResponsable" class="select2" name="responsable"
                            style='background-color: #fff;'
                            onchange="setCustomValidity('')">
                        <option value=''></option>
                        <c:forEach items="${listaresponsables}" var="respon">
                            <c:choose>
                                <c:when test="${respon.getID() == evento.getResponsable().getID()}" >
                                    <option value=${respon.getID()} selected> ${respon.getNombreUsuario()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value=${respon.getID()}>${respon.getNombreUsuario()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>  
    </div>
    <div class="col-md-6">
        <label for="descripcion" class="control-label">*Descripción</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <textarea rows="8" cols="50" maxlength="500" placeholder="Descripción" class="form-control" name="descripcion" id="descripcion" required oninvalid="setCustomValidity('Este campo es requerido ')" oninput="setCustomValidity('')">${evento.getDescripcion()}</textarea>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-12">
        <!-- Esta parte es la de los permisos de un rol -->
        <p class="campos-requeridos">
            Los campos marcados con * son requeridos.
        </p>  

    <div class="form-group">
        <div class="modal-footer">
            <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Caballo</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</div>
</form>