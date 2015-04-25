<%-- 
    Document   : Formulario
    Created on : Apr 25, 2015, 3:19:53 PM
    Author     : Amed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form id="formGrupohembras" class="form-horizontal" autocomplete="off" method="post" action="Gruposhembras">
    <div class="row">
        <input hidden="true" name="id_grupo" value="${grupo.getId_grupo()}">
        <input hidden="true" name="accion" value="${accion}">
        <div class="col-md-6">
            <label for="identificador" class="control-label">*Identificador del Grupo</label>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <input  type="text" class="form-control" id="identificador" value="${grupo.getIdentificador()}" name="identificador"  required
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                onchange="setCustomValidity('')">      
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <label for="cantidad_espacios" class="control-label">*Cantidad de Espacios</label>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <input  type="number" class="form-control" id="cantidad_espacios" value="${grupo.getCantidad_espacios()}" name="cantidad_espacios" required
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                onchange="setCustomValidity('')">      
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
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Grupo de Hembras</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

</form>

