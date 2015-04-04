<%-- 
    Document   : Formulario
    Created on : Abr 3, 2015, 10:58:00 AM
    Author     : Bpga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="form-analisis" class="form-horizontal" autocomplete="off" method="post" action="AnalisisParasitologico">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_analisis" value="${analisis.getId_analisis()}">
            <input hidden="true" name="accion" value="${accion}">
            <label for="identificacion" class="control-label">*Número de Informe</label>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <input  type="text" id="num-informe" value="${analisis.getNumero_informe()}" name="numero_informe" required class="form-control"
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                onchange="setCustomValidity('')">
                    </div>
                </div>
            </div>
            <label for="especie" class="control-label">Especie</label>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <select id="select-especie" class="select2" style='background-color: #fff;' value=" " name="especie" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <c:choose>
                                <c:when test="${accion == 'Editar'}">
                                    <option value="True"  ${analisis.isEspecie() ? 'selected' : ''}>Ratones</option>
                                    <option value="False" ${analisis.isEspecie() ? '' : 'selected'}>Conejos</option>
                                </c:when>
                                <c:when test="${accion == 'Agregar'}">
                                    <option value="True"  ${especie ? 'selected' : ''}>Ratones</option>
                                    <option value="False" ${especie ? '' : 'selected'}>Conejos</option>
                                </c:when>
                            </c:choose>
                        </select>
                    </div>
                </div>
            </div>
            <label for="responsable" class="control-label">Responsable</label>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <select id="select-responsable" class="select2" style='background-color: #fff;' value= ' ' name="responsable" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=""></option>
                            <c:forEach items="${usuarios}" var="usuario">
                                <c:choose>
                                    <c:when test="${usuario.getId_usuario() != analisis.getResponsable().getId_usuario()}">
                                        <option value="${usuario.getId_usuario()}">${usuario.getNombre_completo()}</option>
                                    </c:when>
                                    <c:when test="${usuario.getId_usuario() == analisis.getResponsable().getId_usuario()}">
                                        <option value="${usuario.getId_usuario()}" selected>${usuario.getNombre_completo()}</option>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <label for="descripcion" class="control-label">*Resultados</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea rows="5" cols="50" maxlength="500" placeholder="Descripción" class="form-control" name="resultados">${analisis.getResultados()}</textarea>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <label for="fecha_ingreso" class="control-label">Fecha de Tratamiento</label>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha-tratamiento" value="${analisis.getFecha_tratamiento_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_tratamiento" data-date-format="dd/mm/yyyy" required
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                onchange="setCustomValidity('')">      
                    </div>
                </div>
            </div>
            <label for="tratamiento" class="control-label">Tratamiento</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea rows="5" cols="50" maxlength="500" placeholder="Tratamiento" class="form-control" name="tratamiento">${analisis.getTratamiento_dosis()}</textarea>
                    </div>
                </div>
            </div>
            <label for="recetado_por" class="control-label">Recetado Por</label>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <select id="select-recetado-por" class="select2" style='background-color: #fff;' value= ' ' name="recetado_por" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=""></option>
                            <c:forEach items="${usuarios}" var="usuario">
                                <c:choose>
                                    <c:when test="${usuario.getId_usuario() != analisis.getRecetado_por().getId_usuario()}">
                                        <option value="${usuario.getId_usuario()}">${usuario.getNombre_completo()}</option>
                                    </c:when>
                                    <c:when test="${usuario.getId_usuario() == analisis.getRecetado_por().getId_usuario()}">
                                        <option value="${usuario.getId_usuario()}" selected>${usuario.getNombre_completo()}</option>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-12">
        <p>
            Los campos marcados con * son requeridos.
        </p>  
        <div class="row">
            <div class="form-group">
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
                    <c:choose>
                        <c:when test= "${accion.equals('Editar')}">
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Análisis Parasitológico</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

</form>

