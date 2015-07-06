<%-- 
    Document   : Formulario
    Created on : Jul 3, 2015, 8:43:02 AM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" enctype='multipart/form-data' method="post" action="Analisis">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_analisis" value="${analisis.getId_analisis()}">
            <input hidden="true" name="accion" value="${accion}">

            <label for="nombre" class="control-label">*Nombre/Código/Identificador</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" maxlength="45" placeholder="Nombre/Código/Identificador del Analisis" class="form-control" name="nombre" value="${analisis.getNombre()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido')"
                               oninput="setCustomValidity('')" > 
                    </div>
                </div>
            </div>
            <label for="tipo_analisis" class="control-label">*Tipo de Analisis</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${analisis.getId_analisis()==0}">
                                <select id="seleccionTipo" class="select2" name="tipo_analisis"
                                        style='background-color: #fff;' required
                                        oninvalid="setCustomValidity('Este campo es requerido')"
                                        onchange="setCustomValidity('')">
                                    <option value=''></option>
                                    <c:forEach items="${tipoanalisis}" var="tipoanalisis">
                                        <c:choose>
                                            <c:when test="${tipoanalisis.getId_tipo_analisis() == analisis.getTipo_analisis().getId_tipo_analisis()}" >
                                                <option value=${tipoanalisis.getId_tipo_analisis()} selected> ${tipoanalisis.getNombre()}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value=${tipoanalisis.getId_tipo_analisis()}> ${tipoanalisis.getNombre()}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </c:when>
                            <c:otherwise>
                                <input type="text" maxlength="45" class="form-control" name="tipo_analisis" value="${analisis.getTipo_analisis().getNombre()}"
                                       disabled > 
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <c:choose>
                <c:when test="${analisis.getId_analisis()!=0}">

                </c:when>
                <c:otherwise>
                    <label for="nombre" class="control-label">*Certificado</label>
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
                <c:when test="${analisis.getId_analisis()!=0}">
                    <label for="nombre" class="control-label"> Preparación (si no selecciona un archivo, quedará registrado el subido anteriormente)</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="certificado" name="preparacion"  accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <label for="nombre" class="control-label">*Preparación</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="certificado" name="preparacion"  accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" required
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Analisis</button>
                </c:otherwise>
            </c:choose>    </div>
    </div>


</form>

