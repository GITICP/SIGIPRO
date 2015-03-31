<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="tipoeventoForm" class="form-horizontal" autocomplete="off" method="post" action="TipoEvento">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_tipo_evento" value="${tipoevento.getId_tipo_evento()}">
            <input id="eventos" hidden="true" name="listaEventos" value="">
            <input id="ids-eventos" hidden="true" name="ids_eventos" value="">
            <input hidden="true" name="accion" value="${accion}">
            <label for="nombre" class="control-label">* Nombre del Tipo de Eventos</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" maxlength="45" placeholder="Tipo de evento" class="form-control" name="nombre" value="${tipoevento.getNombre()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               oninput="setCustomValidity('')" > 
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">                     
            <label for="descripcion" class="control-label">Descripción</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea rows="5" cols="50" maxlength="200" placeholder="Descripción" class="form-control" name="descripcion" >${tipoevento.getDescripcion()}</textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <p>                    
        <!-- Esta parte es la de los permisos de un rol -->
        <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
    </p>
    <div class="form-group">
        <div class="modal-footer">
            <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary" onclick="confirmacionAgregarTipo()"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary" onclick="confirmacionAgregarTipo()"><i class="fa fa-check-circle"></i> ${accion} Tipos de eventos</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</form>