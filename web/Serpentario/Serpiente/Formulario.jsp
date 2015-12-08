<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form class="form-horizontal" autocomplete="off" enctype='multipart/form-data' method="post" action="Serpiente">
    <div class="col-md-6">
        <input hidden="true" name="id_serpiente" value="${serpiente.getId_serpiente()}">
        <input hidden="true" name="accion" value="${accion}">
        <c:set var="contienePermisoEditarAdmin" value="false" />
        <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
            <c:if test="${permiso == 1 || permiso == 315}">
                <c:set var="contienePermisoEditarAdmin" value="true" />
            </c:if>
        </c:forEach>


        <c:choose>
            <c:when test="${serpiente.getId_serpiente()!=0}">
                <label for="numero_ingreso" class="control-label">*Número de Ingreso</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input type="text" class="form-control" disabled='true' name="numero_ingreso" value="${serpiente.getNumero_serpiente()}"> 
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <label for="numero_ingreso" class="control-label">*Número de Ingreso</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input type="number" min="0" class="form-control" name="numero_serpiente" value="${siguiente}" required
                                   oninvalid="setCustomValidity('Este campo es requerido y debe ser número entero.')"
                                   oninput="setCustomValidity('')"> 
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
        <label for="especie" class="control-label">*Especie</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccionEspecie" class="select2" name="especie"
                            style='background-color: #fff;' required
                            oninvalid="setCustomValidity('Este campo es requerido')"
                            onchange="setCustomValidity('')">
                        <option value=''></option>
                        <c:forEach items="${especies}" var="especie">
                            <c:choose>
                                <c:when test="${especie.getId_especie() == serpiente.getEspecie().getId_especie()}" >
                                    <option value=${especie.getId_especie()} selected> ${especie.getGenero_especie()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value=${especie.getId_especie()}>${especie.getGenero_especie()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>

                </div>
            </div>
        </div>
        <label for="fecha_ingreso" class="control-label">*Fecha de Ingreso</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${serpiente.getFecha_ingreso()==null}">
                            <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepickerSerpiente" class="form-control sigiproDatePicker" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
                                   oninvalid="setCustomValidity('Este campo es requerido y no pueden ser fechas futuras. ')"
                                   onchange="setCustomValidity('')">
                        </c:when>
                        <c:otherwise>
                            <input type="text" value="${serpiente.getFecha_ingresoAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepickerSerpiente" class="form-control sigiproDatePicker" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   onchange="setCustomValidity('')">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <label for="localidad_origen" class="control-label">*Localidad de Origen</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${serpiente.getLocalidad_origen()==null}">
                            <input type="text" placeholder="Nombre de la Localidad" class="form-control" name="localidad_origen" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   oninput="setCustomValidity('')"> 
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${contienePermisoEditarAdmin}">
                                    <input type="text" placeholder="Nombre de la Localidad" value="${serpiente.getLocalidad_origen()}" class="form-control" name="localidad_origen" required
                                           oninvalid="setCustomValidity('Este campo es requerido ')"
                                           oninput="setCustomValidity('')"> 
                                </c:when>
                                <c:otherwise>
                                    <input type="text" hidden="true" value="${serpiente.getLocalidad_origen()}" name="localidad_origen">
                                    <input type="text" disabled='true' class="form-control" value="${serpiente.getLocalidad_origen()}"> 
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <label for="colectada" class="control-label">*Colectada por</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${serpiente.getColectada()==null}">
                            <input type="text" placeholder="Nombre de la persona" class="form-control" name="colectada" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   oninput="setCustomValidity('')"> 
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${contienePermisoEditarAdmin}">
                                    <input type="text" placeholder="Nombre de la persona" value="${serpiente.getColectada()}" class="form-control" name="colectada" required
                                           oninvalid="setCustomValidity('Este campo es requerido ')"
                                           oninput="setCustomValidity('')"> 
                                </c:when>
                                <c:otherwise>
                                    <input type="text" hidden="true" value="${serpiente.getColectada()}" name="colectada">
                                    <input type="text" disabled='true' class="form-control" value="${serpiente.getColectada()}"> 
                                </c:otherwise>
                            </c:choose>

                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <c:choose>
            <c:when test="${serpiente.getRecibida()==null}">

            </c:when>
            <c:otherwise>
                <label for="recibida" class="control-label">*Registrada al sistema por</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input type="text" disabled='true' class="form-control" name="recibida" value="${serpiente.getRecibida().getNombre_completo()}">               
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="col-md-6">
        <label for="sexo" class="control-label">*Sexo</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccionSexo" class="select2" name="sexo" 
                            style='background-color: #fff;' required
                            oninvalid="setCustomValidity('Por favor seleccione un sexo')"
                            onchange="setCustomValidity('')">
                        <option value=''></option>
                        <c:forEach items="${sexos}" var="sexo">
                            <c:choose>
                                <c:when test="${sexo.equals(serpiente.getSexo())}" >
                                    <option value="${sexo}" selected> ${sexo}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${sexo}">${sexo}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>                 
        <label for="talla_cabeza" class="control-label">Longitud de la Cabeza a la Cloaca (cm)</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <input id="talla_cabeza" placeholder="Número mayor a uno" type="number" min="0" step="any" class="form-control" name="talla_cabeza" value="${serpiente.getTalla_cabeza()}" 
                           oninvalid="setCustomValidity('La Longitud de la serpiente debe ser mayor que uno. ')"
                           oninput="setCustomValidity('')"> 
                </div>
            </div>
        </div>
        <label for="talla_cola" class="control-label">Longitud de la Cola (cm)</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <input id="talla_cola" placeholder="Número mayor a uno" type="number" min="0" step="any" class="form-control" name="talla_cola" value="${serpiente.getTalla_cola()}" 
                           oninvalid="setCustomValidity('La Longitud de la serpiente debe ser mayor que uno. ')"
                           oninput="setCustomValidity('')"> 
                </div>
            </div>
        </div>
        <label for="peso" class="control-label">Peso (g)</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <input id="peso" placeholder="Número mayor a uno" type="number" step="any" min="0" class="form-control" name="peso" value="${serpiente.getPeso()}" 
                           oninvalid="setCustomValidity('El peso de la serpiente debe ser mayor que uno. ')"
                           oninput="setCustomValidity('')"> 
                </div>
            </div>
        </div>
    </div>        
    <div class="col-md-12">
        <div class="widget widget-table">
            <div class="widget-header">
                <h3><i class="fa fa-file-image-o"></i> Imagen</h3>
            </div>
            <div class="widget-content">
                <label for="imagen" class="control-label">Seleccione una imagen</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">                
                            <input class='clearable' type="file" id="imagen_Serpiente" name="imagen" accept="image/*" 
                                   oninvalid="setCustomValidity('El tamaño debe ser de 100KB o menos. ')" 
                                   onchange="previewFile()" /> <button type="button" id='boton_Cancelar' style="visibility:hidden;" class="btn btn-danger" onclick="eliminarImagen()"> Borrar</button>
                            <div><img name='imagenSubida' id="imagenSubida" src='' height="200" alt=""></div>
                        </div>
                    </div>
                </div>
                <c:if test="${!imagenSerpiente.equals('')}">
                    Imagen Actual: <img src="${imagenSerpiente}" height="100">
                </c:if>
            </div>
        </div>

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
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Serpiente</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </div>
</form>