<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form class="form-horizontal" autocomplete="off" enctype='multipart/form-data' method="post" action="EventoClinico">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_evento" value="${evento.getId_evento()}">
            <input hidden="true" name="accion" value="${accion}">
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
                                        <option value ="${tipo.getId_tipo_evento()},${tipo.getDescripcion()}"  selected> ${tipo.getNombre()}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value ="${tipo.getId_tipo_evento()},${tipo.getDescripcion()}">${tipo.getNombre()}</option>
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
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Por favor seleccione el responsable del evento')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${usuarios_cab_prod}" var="usuario">
                                <c:choose>
                                    <c:when test="${usuario.getId_usuario() == evento.getResponsable().getId_usuario()}" >
                                        <option value="${usuario.getId_usuario()}" selected> ${usuario.getNombre_completo()}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${usuario.getId_usuario()}"> ${usuario.getNombre_completo()}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>  
        </div>
        <div class="col-md-6">
            <label for="descripcion" class="control-label">Descripción</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea rows="5" cols="50" maxlength="500" placeholder="Descripción" class="form-control" name="descripcion" id="descripcion" oninvalid="setCustomValidity('Este campo es requerido ')" oninput="setCustomValidity('')">${evento.getDescripcion()}</textarea>
                    </div>
                </div>
            </div>
            <label for="observaciones" class="control-label">Observaciones</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea rows="5" cols="50" maxlength="500" placeholder="Observaciones del evento" class="form-control" name="observaciones" id="observaciones" oninvalid="setCustomValidity('Este campo es requerido ')" oninput="setCustomValidity('')">${evento.getObservaciones()}</textarea>
                    </div>
                </div>
            </div>
            <label for="asociacion_caballos" class="control-label">Asociación de Caballos</label>
            <div class="form-group opciones">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="checkbox-asociar-caballos" type="checkbox" name="asociacion_caballos" value="true" checked><span> ¿Desea asociar caballos a este evento?</span>
                        <br>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <br>
    <div class="widget widget-table cuadro-opciones">
        <div class="widget-header">
            <h3><i class="fa fa-flask"></i> Asociar Caballos </h3>
        </div>
        <div class="widget-content">
            <div class="col-md-6">
                <c:forEach items="${grupos_caballos}" var="grupo_caballo" begin="0" step="2">
                    <div class="widget widget-table cuadro-opciones">
                        <div class="widget-header">
                            <h3><i class="fa fa-flask"></i> Grupo ${grupo_caballo.getNombre()} </h3>
                            <div class="widget-header-toolbar">
                                <a class="btn btn-primary btn-sm boton-accion seleccionar-todo">Marcar Todos</a>
                            </div>
                        </div>
                        <div class="widget-content">
                            <c:forEach items="${grupo_caballo.getCaballos()}" var="caballo">
                                <div class="col-md-4">
                                    <label class="fancy-checkbox">
                                        <input type="checkbox" value="${caballo.getId_caballo()}" name="caballos" ${(accion == 'Editar' && evento.valididarCaballoEnEvento(caballo)) ? "checked" : ""}>
                                        <span>${caballo.getNombre()} (${caballo.getNumero()}) </span>
                                    </label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="col-md-6">
                <c:forEach items="${grupos_caballos}" var="grupo_caballo" begin="1" step="2">
                    <div class="widget widget-table cuadro-opciones">
                        <div class="widget-header">
                            <h3><i class="fa fa-flask"></i> Grupo ${grupo_caballo.getNombre()} </h3>
                            <div class="widget-header-toolbar">
                                <a class="btn btn-primary btn-sm boton-accion seleccionar-todo">Marcar Todos</a>
                            </div>
                        </div>
                        <div class="widget-content">
                            <c:forEach items="${grupo_caballo.getCaballos()}" var="caballo">
                                <div class="col-md-4">
                                    <label class="fancy-checkbox">
                                        <input type="checkbox" value="${caballo.getId_caballo()}" name="caballos" ${(accion == 'Editar' && evento.valididarCaballoEnEvento(caballo)) ? "checked" : ""}>
                                        <span>${caballo.getNombre()} (${caballo.getNumero()}) </span>
                                    </label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

    </div>
    <div class="widget widget-table">
        <div class="widget-header">
            <h3><i class="fa fa-image"></i> Imagen</h3>
        </div>
        <div class="widget-content">
            <label for="imagen" class="control-label">Seleccione una imagen</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">                
                        <input class='clearable' type="file" id="imagen1" name="imagen" accept="image/*" 
                               oninvalid="setCustomValidity('El tamaño debe ser de 100KB o menos. ')" 
                               onchange="previstaImagen(this, 1)" /> <button type="button" id='botonCancelar1' style="visibility:hidden;" class="btn btn-danger" onclick="eliminarImagen(1)"> Borrar</button>
                        <div><img name='imagenSubida' id="imagenSubida1" src='' height="150" alt=""></div>
                    </div>
                </div>
            </div>
            <c:if test="${!imagenEvento.equals('')}">
                Imagen Actual: <img src="${imagenEvento}" height="100">
            </c:if>
        </div>
    </div>
    <!-- Esta parte es la de los permisos de un rol -->
    <p class="campos-requeridos">
        Los campos marcados con * son requeridos.
    </p>

    <div class="col-md-12">    
        <div class="row">
            <div class="form-group">
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
                    <c:choose>
                        <c:when test= "${accion.equals('Editar')}">
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Evento Clínico</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</form>