<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form class="form-horizontal" autocomplete="off" method="post" action="SangriaPrueba">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_sangria_prueba" value="${sangriap.getId_sangria_prueba()}">
            <input hidden="true" name="accion" value="${accion}">
            <label for="muestra" class="control-label">*Muestra</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${sangriap.getMuestra()==null}">
                                <input type="text" placeholder="Sangría P0 2" class="form-control" name="muestra" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       oninput="setCustomValidity('')"> 
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="form-control" name="muestra" value="${sangriap.getMuestra()}"> 
                                <input hidden="true" name="muestra" value="${sangriap.getMuestra()}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <label for="num_solicitud" class="control-label">Solicitud N°</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${sangriap.getNum_solicitud()==null||sangriap.getNum_solicitud()==0}">
                                <input type="number" placeholder="6461213" class="form-control" name="num_solicitud" 
                                       oninput="setCustomValidity('')"> 
                            </c:when>
                            <c:otherwise>
                                <input type="number" class="form-control" name="num_solicitud" value="${sangriap.getNum_solicitud()}"> 
                                <input hidden="true" name="num_solicitud" value="${sangriap.getNum_solicitud()}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <label for="num_informe" class="control-label">Informe N°</label>          
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${sangriap.getNum_informe()==null||sangriap.getNum_informe()==0}">
                                <input type="number" placeholder="6461213" class="form-control" name="num_informe"
                                       oninput="setCustomValidity('')"> 
                            </c:when>
                            <c:otherwise>
                                <input type="number" class="form-control" name="num_informe" value="${sangriap.getNum_informe()}"> 
                                <input hidden="true" name="num_informe" value="${sangriap.getNum_informe()}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <label for="responsable" class="control-label">*Responsable</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${sangriap.getResponsable()==null}">
                                <input type="text" placeholder="Persona Responsable" class="form-control" name="responsable" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       oninput="setCustomValidity('')"> 
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="form-control" name="responsable" value="${sangriap.getResponsable()}"> 
                                <input hidden="true" name="responsable" value="${sangriap.getResponsable()}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>            
        </div>
        <div class="col-md-6">
            <label for="fecha_recepcion_muestra" class="control-label">*Fecha de recepción de la muestra</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${sangriap.getFecha_recepcion_muestra()==null}">
                                <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_recepcion_muestra" data-date-format="dd/mm/yyyy" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       onchange="setCustomValidity('')">
                            </c:when>
                            <c:otherwise>
                                <input type="text"  value="${sangriap.getFecha_recepcion_muestraAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_recepcion_muestra" data-date-format="dd/mm/yyyy" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       onchange="setCustomValidity('')">
                                <input hidden="true" name="fecha_recepcion_muestra" value="${sangriap.getFecha_recepcion_muestraAsString()}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <label for="fecha_informe" class="control-label">*Fecha del informe</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${sangriap.getFecha_informe()==null}">
                                <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_informe" data-date-format="dd/mm/yyyy" 
                                       onchange="setCustomValidity('')">
                            </c:when>
                            <c:otherwise>
                                <input type="text"  value="${sangriap.getFecha_informeAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_informe" data-date-format="dd/mm/yyyy"
                                       onchange="setCustomValidity('')">
                                <input hidden="true" name="fecha_informe" value="${sangriap.getFecha_informeAsString()}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>        
            <label for="inoculo" class="control-label">*Inóculo</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionTipoDeEvento" class="select2" name="inoculo" 
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Por favor seleccione el tipo del evento')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${listainoculos}" var="inoculo">
                                <c:choose>
                                    <c:when test="${inoculo.getId_inoculo() == sangriap.getInoculo().getId_inoculo()}" >
                                        <option value ="${inoculo.getId_inoculo()}"  selected> ${inoculo.getId_inoculo()}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value ="${inoculo.getId_inoculo()}">${inoculo.getId_inoculo()}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <label for="perecedero" class="control-label">Asociación de Caballos</label>
            <div class="form-group opciones">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="checkbox" name="perecedero" value="true" ${checkedCuarentena}><span> ¿Desea asociar caballos a este evento?</span>
                        <br>
                    </div>
                </div>
            </div>            
        </div>
    </div>
    <br>


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
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Sangría de Prueba</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</form>