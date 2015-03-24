<%-- 
    Document   : Formulario
    Created on : 24-mar-2015, 11:28:56
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form class="form-horizontal" autocomplete="off" method="post" action="Caballo">
    <div class="col-md-6">
        <input hidden="true" name="id_caballo" value="${caballo.getId_caballo()}">
        <input hidden="true" name="accion" value="${accion}">
        <input hidden='true' id='imagenSubida2' value=''>
        <c:choose>
            <c:when test="${caballo.getId_caballo()!=0}">
                <label for="nombre" class="control-label">*Nombre de caballo</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input type="text" class="form-control" disabled='true' name="nombre" value="${caballo.getNombre()}"> 
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>

            </c:otherwise>
        </c:choose>
        <label for="grupodecaballo" class="control-label">*Grupo del caballo</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccionGrupoDeCaballo" class="select2" name="grupodecaballo"
                        style='background-color: #fff;' required
                        oninvalid="setCustomValidity('Este campo es requerido')"
                        onchange="setCustomValidity('')">
                    <option value=''></option>
                    <c:forEach items="${gruposdecaballos}" var="grupodecaballo">
                        <c:choose>
                            <c:when test="${grupodecaballo.getId_grupo_caballo() == caballo.getGrupo_de_caballos().getId_grupo_caballo()}" >
                                <option value=${grupodecaballo.getId_grupo_caballo()} selected> ${grupodecaballo.getNombre()}</option>
                            </c:when>
                            <c:otherwise>
                                <option value=${grupodecaballo.getId_grupo_caballo()}>${grupodecaballo.getNombre()}</option>
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
                        <c:when test="${caballo.getFecha_ingreso()==null}">
                            <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                onchange="setCustomValidity('')">
                        </c:when>
                        <c:otherwise>
                            <input type="text" disabled='true' value="${caballo.getFecha_ingreso()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                onchange="setCustomValidity('')">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
                       
        <label for="fecha_nacimiento" class="control-label">*Fecha de Nacimiento</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${caballo.getFecha_nacimiento()==null}">
                            <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_nacimiento" data-date-format="dd/mm/yyyy" required
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                onchange="setCustomValidity('')">
                        </c:when>
                        <c:otherwise>
                            <input type="text" disabled='true' value="${caballo.getFecha_nacimiento()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_nacimiento" data-date-format="dd/mm/yyyy" required
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                onchange="setCustomValidity('')">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    <label for="otras_sennas" class="control-label">*Otras señas</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <c:choose>
                    <c:when test="${caballo.getOtras_sennas()==null}">
                        <input type="text" placeholder="Señas importantes" class="form-control" name="otras_sennas" required
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                oninput="setCustomValidity('')"> 
                    </c:when>
                    <c:otherwise>
                        <input type="text" disabled='true' class="form-control" name="otras_sennas" value="${caballo.getOtras_sennas()}"> 
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <label for="color" class="control-label">*Color</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <c:choose>
                    <c:when test="${caballo.getColor()==null}">
                        <input type="text" class="form-control" name="color"placeholder="Cafe" required
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                oninput="setCustomValidity('')"> 
                    </c:when>
                    <c:otherwise>
                        <input type="text" disabled='true' class="form-control" name="color" value="${caballo.getColor()}"> 
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<div class="col-md-6">
    <label for="otras_sennas" class="control-label">*Sexo</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <select id="seleccionSexo" class="select2" name="sexo"
                        style='background-color: #fff;' required
                        oninvalid="setCustomValidity('Por favor seleccione un sexo')"
                        onchange="setCustomValidity('')">
                    <option value=''></option>
                    <option value="Macho">Macho</option>
                    <option value="Hembra">Hembra</option>
                    <option value="Indefinido">Indefinido</option>
                </select>
            </div>
        </div>
    </div>                 
    <label for="talla_cabeza" class="control-label">*Longitud de la Cabeza a la Cloaca</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input id="talla_cabeza" placeholder="Número mayor a uno" type="number" min="1" class="form-control" name="talla_cabeza" value="${serpiente.getTalla_cabeza()}" required 
                  oninvalid="setCustomValidity('La Longitud de la serpiente debe ser mayor que uno. ')"
                  oninput="setCustomValidity('')"> 
        </div>
      </div>
    </div>
    <label for="talla_cola" class="control-label">*Longitud de la Cola</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input id="talla_cola" placeholder="Número mayor a uno" type="number" min="1" class="form-control" name="talla_cola" value="${serpiente.getTalla_cola()}" required 
                  oninvalid="setCustomValidity('La Longitud de la serpiente debe ser mayor que uno. ')"
                  oninput="setCustomValidity('')"> 
        </div>
      </div>
    </div>
    <label for="peso" class="control-label">*Peso</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input id="peso" placeholder="Número mayor a uno" type="number" min="1" class="form-control" name="peso" value="${serpiente.getPeso()}" required 
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