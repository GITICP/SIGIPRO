<%-- 
    Document   : Formulario
    Created on : Jul 9, 2015, 2:01:11 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" enctype='multipart/form-data' method="post" action="Solicitud">
    <div class="row">
        <div class="col-md-12">
            <input hidden="true" name="id_solicitud" value="${analisis.getId_analisis()}">
            <input hidden="true" name="accion" value="${accion}">

            <label for="nombre" class="control-label">*Número de Solicitud</label>
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
        </div>

        <div class="col-md-12">
            <div class="widget widget-table">
                <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> Muestras</h3>
                    <div class="btn-group widget-header-toolbar">

                    </div>
                </div>
                <div class="widget-content">
                    <div class="muestras">

                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <br>
                                <button type="button" onclick="agregarMuestra()" class="btn btn-primary"><i class="fa fa-plus-circle"></i> Agregar Muestras</button>
                            </div>
                        </div>
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Análisis</button>
                </c:otherwise>
            </c:choose>    
        </div>
    </div>


</form>

