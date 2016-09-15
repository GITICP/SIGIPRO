<%-- 
    Document   : Formulario
    Created on : Jun 30, 2015, 8:33:15 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" enctype='multipart/form-data' method="post" action="Equipo">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_equipo" value="${equipo.getId_equipo()}">
            <input hidden="true" name="accion" value="${accion}">

            <label for="nombre" class="control-label">*Nombre/Código/Identificador</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" maxlength="45" placeholder="Nombre/Código/Identificador del Equipo" class="form-control" name="nombre" value="${equipo.getNombre()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido')"
                               oninput="setCustomValidity('')" > 
                    </div>
                </div>
            </div>
            <label for="tipo_equipo" class="control-label">*Tipo de Equipo <c:if test="${!equipo.getEditable()}">(No editable ya que se encuentra referenciado a muestras o resultados)</c:if></label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                        <c:choose>
                            <c:when test="${equipo.getEditable()}">
                                <select id="seleccionTipo" class="select2" name="tipo_equipo"
                                        style='background-color: #fff;' required
                                        oninvalid="setCustomValidity('Este campo es requerido')"
                                        onchange="setCustomValidity('')">
                                    <option value=''></option>
                                    <c:forEach items="${tipoequipos}" var="tipoequipo">
                                        <c:choose>
                                            <c:when test="${tipoequipo.getId_tipo_equipo() == equipo.getTipo_equipo().getId_tipo_equipo()}" >
                                                <option value=${tipoequipo.getId_tipo_equipo()} data-certificable="${tipoequipo.isCertificable()}" selected> ${tipoequipo.getNombre()}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value=${tipoequipo.getId_tipo_equipo()} data-certificable="${tipoequipo.isCertificable()}"> ${tipoequipo.getNombre()}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </c:when>
                            <c:otherwise>
                                <input type="text" maxlength="45" class="form-control" value="${equipo.getTipo_equipo().getNombre()}"
                                       disabled > 
                                <input type="hidden" maxlength="45" class="form-control" name="tipo_equipo" value="${equipo.getTipo_equipo().getId_tipo_equipo()}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <c:choose>
                <c:when test="${equipo.getId_equipo()!=0}">

                </c:when>
                <c:otherwise>
                    <label id="label-certificado" for="nombre" class="control-label">Certificado</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="certificado" name="certificado"  accept="application/pdf,image/jpeg,image/gif,image/png"
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-6">
            <label for="descripcion" class="control-label">Descripción</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea rows="5" cols="50" maxlength="510" placeholder="Descripción" class="form-control" name="descripcion" >${equipo.getDescripcion()}</textarea>
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Equipo</button>
                </c:otherwise>
            </c:choose>    </div>
    </div>


</form>
