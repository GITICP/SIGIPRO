<%-- 
    Document   : Formulario
    Created on : Jun 29, 2015, 5:02:29 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" enctype='multipart/form-data' method="post" action="TipoReactivo">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_tipo_reactivo" value="${tiporeactivo.getId_tipo_reactivo()}">
            <input hidden="true" name="accion" value="${accion}">

            <label for="nombre" class="control-label">* Nombre</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" maxlength="45" placeholder="Nombre del Tipo de Reactivo" class="form-control" name="nombre" value="${tiporeactivo.getNombre()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido')"
                               oninput="setCustomValidity('')" > 
                    </div>
                </div>
            </div>
            <label for="opciones" class="control-label">Certificados</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <label class="fancy-checkbox">
                            <c:choose>
                                <c:when test="${accion == 'Editar' && !tiporeactivo.isCertificable()}">
                                    <input type="checkbox" name="certificable" value="true">
                                    <span> Tipo de Reactivo Certificable</span>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" name="certificable" value="true" checked>
                                    <span> Tipo de Reactivo Certificable</span>
                                </c:otherwise>
                            </c:choose>
                        </label>
                    </div>
                </div>
            </div>
            <c:choose>
                <c:when test="${tiporeactivo.getId_tipo_reactivo()!=0}">
                    <label for="nombre" class="control-label"> Machote de Curva (si no selecciona un archivo, quedará registrado el subido anteriormente)</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="machote" name="machote"  accept=".xlsx"
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <label for="nombre" class="control-label">Machote de Curva</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="machote" name="machote"  accept=".xlsx"
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
                        <textarea rows="5" cols="50" maxlength="510" placeholder="Descripción" class="form-control" name="descripcion" >${tiporeactivo.getDescripcion()}</textarea>
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Tipo de Reactivo</button>
                </c:otherwise>
            </c:choose>    </div>
    </div>


</form>
