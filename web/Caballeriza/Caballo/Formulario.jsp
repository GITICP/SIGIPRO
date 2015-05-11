<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="caballosform" enctype='multipart/form-data' class="form-horizontal" autocomplete="off" method="post" action="Caballo">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_caballo" value="${caballo.getId_caballo()}">
            <input hidden="true" name="accion" value="${accion}">
            <input hidden='true' id='imagenSubida2' value=''>
            <label for="nombre" class="control-label">*Nombre</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" placeholder="Nombre del caballo" class="form-control" name="nombre" value="${caballo.getNombre()}" required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               oninput="setCustomValidity('')"> 
                    </div>
                </div>
            </div>
            <label for="numero_microchip" class="control-label">*Número de Caballo</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="number" placeholder="Número de Caballo" class="form-control" name="numero_caballo" value=${(caballo.getNumero()!=0) ? caballo.getNumero() : ""} required
                               oninvalid="setCustomValidity('Este campo es requerido ')" ${(caballo.getNumero()!=0) ? "disabled" : ""}
                               oninput="setCustomValidity('')"> 
                    </div>
                </div>
            </div>
            <label for="numero_microchip" class="control-label">*Número de Microchip</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" placeholder="Ej: 3413 4112" class="form-control" name="numero_microchip" value="${caballo.getNumero_microchip()}" required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               oninput="setCustomValidity('')">
                    </div>
                </div>
            </div>
            <label for="grupodecaballo" class="control-label">Grupo del caballo</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionGrupoDeCaballo" class="select2" name="grupodecaballo"
                                style='background-color: #fff;'
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${listagrupos}" var="grupo">
                                <c:choose>
                                    <c:when test="${grupo.getId_grupo_caballo() == caballo.getGrupo_de_caballos().getId_grupo_caballo()}" >
                                        <option value=${grupo.getId_grupo_caballo()} selected> ${grupo.getNombre()}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value=${grupo.getId_grupo_caballo()}>${grupo.getNombre()}</option>
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
                        <input type="text" value="${(caballo.getFecha_ingreso() != null) ? caballo.getFecha_ingresoAsString() : helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               onchange="setCustomValidity('')">
                    </div>
                </div>
            </div>
            <label for="fecha_nacimiento" class="control-label">Fecha de Nacimiento</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" value="${(caballo.getFecha_nacimiento() != null) ? caballo.getFecha_nacimientoAsString() : helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_nacimiento" data-date-format="dd/mm/yyyy"
                               oninvalid="setCustomValidity('Este campo es requerido ')" 
                               onchange="setCustomValidity('')">
                    </div>
                </div>
            </div>
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
                                    <c:when test="${sexo.equals(caballo.getSexo())}" >
                                        <option  value="${sexo}" selected> ${sexo}</option>
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
            <label for="color" class="control-label">Color</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" placeholder="Ej: Negro" class="form-control" name="color"
                               oninput="setCustomValidity('')"> 
                    </div>
                </div>
            </div>         
            <label for="otras_sennas" class="control-label">Otras señas</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea id="otras_sennas" rows="5" cols="50" maxlength="500" placeholder="Otras señas del caballo" class="form-control" name="otras_sennas">${caballo.getOtras_sennas()}</textarea>
                    </div>
                </div>
            </div>
            <label for="estado" class="control-label">*Estado</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionEstado" class="select2" name="estado" 
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Por favor seleccione un estado')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${estados}" var="estado">
                                <c:choose>
                                    <c:when test="${estado.equals(caballo.getEstado())}" >
                                        <option value="${estado}" selected> ${estado}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${estado}">${estado}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>  
        </div>
                    
       <div class="col-md-12">
            <div class="widget widget-table">
 <div class="widget-header">
   <h3><i class="fa fa-bug"></i> Imagen</h3>
 </div>
    <div class="widget-content">
<label for="imagen" class="control-label">Seleccione una imagen</label>
              <div class="form-group">
                <div class="col-sm-12">
                  <div class="input-group">                
                      <input class='clearable' type="file" id="imagen_Caballo" name="imagen" accept="image/*" 
                           oninvalid="setCustomValidity('El tamaño debe ser de 100KB o menos. ')" 
                           onchange="previewFile()" />
                    <div><img name='imagenSubida' id="imagenSubida" src='' height="200" alt=""></div>
                  </div>
                </div>
              </div>
              <c:if test="${!imagenCaballo.equals('')}">
                  Imagen Actual: <img src="${imagenCaballo}" height="100">
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
            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Caballo</button>
          </c:otherwise>
        </c:choose>
    </div>
</div>
       </div>
</div>
</form>