<%-- 
    Document   : Formulario
    Created on : Mar 28, 2015, 3:15:40 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Lote">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_lote" value="${lote.getId_lote()}">
            <input hidden="true" name="accion" value="${accion}">
            <input hidden="true" name="listaExtracciones" id="listaExtracciones" value="${listaExtracciones}">
            <label for="lote" class="control-label"> *Identificación del Lote</label>
            <c:choose>
                <c:when test="${lote.getId_lote()!=0}">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="text" class="form-control" name="numero_lote" disabled="true" value="${lote.getNumero_lote()}" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       oninput="setCustomValidity('')"> 
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="text" class="form-control" name="numero_lote" value="${lote.getNumero_lote()}" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       oninput="setCustomValidity('')"> 
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>    
        <div class="col-md-6">
            <label for="especie" class="control-label"> *Especie</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input hidden="true" name='especie' value="${lote.getEspecie().getId_especie()}">
                        <select id="seleccionEspecie" class="select2" name="selectEspecie" disabled="true"
                                style='background-color: #fff;'>
                            <option value='${lote.getEspecie().getId_especie()}' selected>${lote.getEspecie().getGenero_especie()}</option>
                        </select>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <c:if test="${lote.isInterno()}">
            <div class="col-md-6">
                <c:if test="${accion.equals('Agregar')}">
                    <c:set var="checkedExtracción" value="true" />
                    <input id="extraccion" type="checkbox" name="extraccion" value="true" style="width:20px; height:20px;" checked="true" ${checkedExtraccion}><span>  ¿Proviene de extracciones internas?</span>
                </c:if>
                <br>
                <label for="especie" class="control-label"> *Extracciones</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <select id="seleccionExtraccion" class="select2" name="extracciones" multiple="multiple"
                                    style='background-color: #fff;' required
                                    oninvalid="setCustomValidity('Este campo es requerido')"
                                    onchange="setCustomValidity('')">
                                <option value=''></option>
                                <c:forEach items="${extracciones}" var="extraccion">
                                    <option value=${extraccion.getId_extraccion()}>${extraccion.getNumero_extraccion()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${accion.equals('Agregar')}">
            <div class="col-md-6">
                <label for="genero" class="control-label">*Identificador de Control</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input id="numero_control" type="text" maxlength="45" placeholder="Ex: COMPRA-ICP-01-2015" disabled class="form-control" name="numero_control" value="${extraccion.getNumero_extraccion()}"
                                   required
                                   oninvalid="setCustomValidity('Este campo es requerido')"
                                   oninput="setCustomValidity('')" > 
                        </div>
                    </div>
                </div>
                <label for="peso_recuperado" class="control-label">*Cantidad adquirida (G)</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input name="peso_comprado" id="peso_comprado" type="number" step="any" min="0" disabled placeholder="Número de G comprados" class="form-control" value="" required
                                   oninvalid="setCustomValidity('El peso comprado debe ser un número mayor a 0. ')"
                                   oninput="setCustomValidity('')">
                        </div>
                    </div>
                </div>

            </div>
        </c:if>
        <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
    </div>

    <div class="form-group">
        <div class="modal-footer">
            <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Lote</button>
                </c:otherwise>
            </c:choose>    </div>
    </div>


</form>