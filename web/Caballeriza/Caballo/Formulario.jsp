<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form class="form-horizontal" autocomplete="off" method="post" action="Caballo">
    <div class="col-md-6">
        <input hidden="true" name="id_caballo" value="${caballo.getId_caballo()}">
        <input hidden="true" name="accion" value="${accion}">
        <input hidden='true' id='imagenSubida2' value=''>
        <label for="nombre" class="control-label">*Nombre</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${caballo.getNombre()==null}">
                            <input type="text" placeholder="Nombre del caballo" class="form-control" name="nombre" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   oninput="setCustomValidity('')"> 
                        </c:when>
                        <c:otherwise>
                            <input type="text" disabled='true' class="form-control" name="nombre" value="${caballo.getNombre()}"> 
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <label for="numero_microchip" class="control-label">Número de Microchip</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <input id="numero_microchip" placeholder="523413" type="number" step="any" class="form-control" name="numero_microchip" value="${caballo.getNumero_microchip()}" 
                           oninput="setCustomValidity('')"> 
                </div>
            </div>
        </div>        
        <label for="grupodecaballo" class="control-label">*Grupo del caballo</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccionGrupoDeCaballo" class="select2" name="grupodecaballo"
                            style='background-color: #fff;' required
                            oninvalid="setCustomValidity('Por favor seleccione un grupo')"
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
                    <c:choose>
                        <c:when test="${caballo.getFecha_ingreso()==null}">
                            <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   onchange="setCustomValidity('')">
                        </c:when>
                        <c:otherwise>
                            <input type="text" disabled='true' value="${caballo.getFecha_ingresoAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   onchange="setCustomValidity('')">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <label for="fecha_nacimiento" class="control-label">Fecha de Nacimiento</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${caballo.getFecha_nacimiento()==null}">
                            <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_nacimiento" data-date-format="dd/mm/yyyy"
                                   onchange="setCustomValidity('')">
                        </c:when>
                        <c:otherwise>
                            <input type="text" disabled='true' value="${caballo.getFecha_nacimientoAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_nacimiento" data-date-format="dd/mm/yyyy"
                                   onchange="setCustomValidity('')">
                        </c:otherwise>
                    </c:choose>
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
        <label for="color" class="control-label">Color</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <input id="color" placeholder="Cafe" type="text" step="any" class="form-control" name="color" value="${caballo.getColor()}" 
                           oninput="setCustomValidity('')"> 
                </div>
            </div>
        </div>
        <label for="otras_sennas" class="control-label">Otras señas</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <input id="otras_sennas" placeholder="Otras señas del caballo" type="text" step="any" class="form-control" name="otras_sennas" value="${caballo.getOtras_sennas()}" 
                           oninput="setCustomValidity('')"> 
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
        <!-- Esta parte es la de los permisos de un rol -->
        <p class="campos-requeridos">
            Los campos marcados con * son requeridos.
        </p>  

        <div class="row">
            <div class="col-md-12">
                <div class="widget widget-table">
                    <div class="widget-header">
                        <h3><i class="fa fa-map-marker"></i> Fotografía (Sin Implementar)</h3>
                    </div>
                    <div class="widget-content">
                        <label for="fotografia" class="control-label">Fotografía</label>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <div class="input-group">                
                                    <input type="file" value='' name="fotografia" accept="image/*" onchange="previewFile()" />
                                    <input type='hidden' name='imagen2' id='fotografia' value=''>
                                    <div><img name='imagenSubida' id="img_newjourney" src='' height="300" alt=""></div>
                                </div>
                            </div>
                        </div>
                        <img src="${caballo.getFotografia()}"
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Caballo</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</div>
</form>