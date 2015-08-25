<%-- 
    Document   : Formulario
    Created on : Jul 1, 2015, 1:39:18 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" enctype='multipart/form-data' method="post" action="Reactivo">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_reactivo" value="${reactivo.getId_reactivo()}">
            <input hidden="true" name="accion" value="${accion}">

            <label for="nombre" class="control-label">*Nombre/Código/Identificador</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" maxlength="45" placeholder="Nombre/Código/Identificador del Reactivo" class="form-control" name="nombre" value="${reactivo.getNombre()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido')"
                               oninput="setCustomValidity('')" > 
                    </div>
                </div>
            </div>
            <label for="tipo_reactivo" class="control-label">*Tipo de Reactivo</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${reactivo.getId_reactivo()==0}">
                                <select id="seleccionTipo" class="select2" name="tipo_reactivo"
                                        style='background-color: #fff;' required
                                        oninvalid="setCustomValidity('Este campo es requerido')"
                                        onchange="setCustomValidity('')">
                                    <option value=''></option>
                                    <c:forEach items="${tiporeactivos}" var="tiporeactivo">
                                        <c:choose>
                                            <c:when test="${tiporeactivo.getId_tipo_reactivo() == reactivo.getTipo_reactivo().getId_tipo_reactivo()}" >
                                                <option value=${tiporeactivo.getId_tipo_reactivo()} data-certificable="${tiporeactivo.isCertificable()}" selected> ${tiporeactivo.getNombre()}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value=${tiporeactivo.getId_tipo_reactivo()} data-certificable="${tiporeactivo.isCertificable()}"> ${tiporeactivo.getNombre()}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </c:when>
                            <c:otherwise>
                                <input type="text" maxlength="45" class="form-control" name="tipo_reactivo" value="${reactivo.getTipo_reactivo().getNombre()}"
                                       disabled > 
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <c:choose>
                <c:when test="${reactivo.getId_reactivo()!=0}">

                </c:when>
                <c:otherwise>
                    <label id="label-certificado" for="nombre" class="control-label">*Certificado</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="certificado" name="certificado"  accept="application/pdf,image/jpeg,image/gif,image/png" required
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${reactivo.getTipo_reactivo()!=null}">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group descargar-Machote">
                                <a href="/SIGIPRO/ControlCalidad/TipoReactivo?accion=archivo&id_tipo_reactivo=${reactivo.getTipo_reactivo().getId_tipo_reactivo()}">Descargar Machote de Curva</a>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group descargar-Machote">
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${reactivo.getId_reactivo()!=0}">
                    <label for="nombre" class="control-label"> Preparación (si no selecciona un archivo, quedará registrado el subido anteriormente)</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="certificado" name="preparacion"  accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <label for="nombre" class="control-label"> Preparación</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="certificado" name="preparacion"  accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Reactivo</button>
                </c:otherwise>
            </c:choose>    </div>
    </div>


</form>

