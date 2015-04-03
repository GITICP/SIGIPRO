<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form class="form-horizontal" autocomplete="off" method="post" action="Serpiente">
    <div class="col-md-6">
        <input hidden="true" name="id_serpiente" value="${serpiente.getId_serpiente()}">
        <input hidden="true" name="accion" value="${accion}">
        <input hidden='true' id='imagenSubida2' value=''>
        <c:choose>
            <c:when test="${serpiente.getId_serpiente()!=0}">
                <label for="numero_ingreso" class="control-label">*Numero de Ingreso</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input type="text" class="form-control" disabled='true' name="numero_ingreso" value="${serpiente.getId_serpiente()}"> 
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>

            </c:otherwise>
        </c:choose>
        <label for="especie" class="control-label">*Especie</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test='${serpiente.getEspecie()==null}'>
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
                        </c:when>
                        <c:otherwise>
                            <input hidden="true" name='especie' value="${serpiente.getEspecie().getId_especie()}">
                            <select id="seleccionEspecie" class="select2" name="selectEspecie" disabled="true"
                                style='background-color: #fff;'>
                                <option value='${serpiente.getEspecie().getId_especie()}' selected>${serpiente.getEspecie().getGenero_especie()}</option>
                            </select>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <label for="fecha_ingreso" class="control-label">*Fecha de Ingreso</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${serpiente.getFecha_ingreso()==null}">
                            <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                onchange="setCustomValidity('')">
                        </c:when>
                        <c:otherwise>
                            <input type="text" disabled='true' value="${serpiente.getFecha_ingresoAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
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
                        <input type="text" disabled='true' class="form-control" name="localidad_origen" value="${serpiente.getLocalidad_origen()}"> 
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
                        <input type="text" disabled='true' class="form-control" name="colectada" value="${serpiente.getColectada()}"> 
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <c:choose>
    <c:when test="${serpiente.getRecibida()==null}">
    
    </c:when>
    <c:otherwise>
    <label for="recibida" class="control-label">*Recibida por</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                        <input type="text" disabled='true' class="form-control" name="recibida" value="${serpiente.getRecibida().getNombre_usuario()}">               
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
    <label for="talla_cabeza" class="control-label">Longitud de la Cabeza a la Cloaca (Metros)</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input id="talla_cabeza" placeholder="Número mayor a uno" type="number" min="0" step="any" class="form-control" name="talla_cabeza" value="${serpiente.getTalla_cabeza()}" 
                  oninvalid="setCustomValidity('La Longitud de la serpiente debe ser mayor que uno. ')"
                  oninput="setCustomValidity('')"> 
        </div>
      </div>
    </div>
    <label for="talla_cola" class="control-label">Longitud de la Cola (Metros)</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input id="talla_cola" placeholder="Número mayor a uno" type="number" min="0" step="any" class="form-control" name="talla_cola" value="${serpiente.getTalla_cola()}" 
                  oninvalid="setCustomValidity('La Longitud de la serpiente debe ser mayor que uno. ')"
                  oninput="setCustomValidity('')"> 
        </div>
      </div>
    </div>
    <label for="peso" class="control-label">Peso (Gramos)</label>
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
<!-- Esta parte es la de los permisos de un rol -->
<p class="campos-requeridos">
    Los campos marcados con * son requeridos.
</p>  

    <div class="row">
        <div class="col-md-12">
            <div class="widget widget-table">
                <div class="widget-header">
                    <h3><i class="fa fa-map-marker"></i> Imagen (Sin Implementar)</h3>
                </div>
                <div class="widget-content">
                    <label for="imagen" class="control-label">Imagen</label>
                    <div class="form-group">
                      <div class="col-sm-12">
                        <div class="input-group">                
                          <input type="file" value='' name="imagen" accept="image/*" onchange="previewFile()" />
                          <input type='hidden' name='imagen2' id='imagen' value=''>
                          <div><img name='imagenSubida' id="img_newjourney" src='' height="300" alt=""></div>
                        </div>
                      </div>
                    </div>
                    <img src="${serpiente.getImagen()}"
                </div>
            </div>
        </div>
    </div>
        </div>
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