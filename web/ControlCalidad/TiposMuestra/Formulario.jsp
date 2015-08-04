<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="TiposMuestra">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_tipo_muestra" value="${tipo_muestra.getId_tipo_muestra()}">
            <input hidden="true" id="listaAnalisis" value="${tipo_muestra.getListaAnalisis()}">
            <input hidden="true" name="accion" value="${accion}">

            <label for="nombre" class="control-label">* Nombre del Tipo de Muestra</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" maxlength="50" placeholder="Nombre del Tipo de Muestra" class="form-control" name="nombre" value="${tipo_muestra.getNombre()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido')"
                               oninput="setCustomValidity('')" > 
                    </div>
                </div>
            </div>

            <label for="dias_descarte" class="control-label">* Descarte Habitual (días)</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="number" maxlength="50" placeholder="Cantidad de días de descarte después de recibida la muestra" class="form-control" name="dias_descarte" value="${tipo_muestra.getDias_descarte()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido')"
                               oninput="setCustomValidity('')" > 
                    </div>
                </div>
            </div>
            <label for="analisis" class="control-label"> *Análisis Asociados</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionAnalisis" class="select2" name="analisis" multiple="multiple"
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${listaAnalisis}" var="analisis">
                                <option value=${analisis.getId_analisis()}>${analisis.getNombre()}</option>
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
                        <textarea rows="5" cols="50" maxlength="500" placeholder="Descripción" class="form-control" name="descripcion" >${tipo_muestra.getDescripcion()}</textarea>
                    </div>
                </div>
            </div>            
        </div>
        <!-- Esta parte es la de los permisos de un rol -->
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Tipo de Muestra</button>
                </c:otherwise>
            </c:choose>    </div>
    </div>


</form>